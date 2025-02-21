package com.base.common.utils.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.base.common.dto.form_field.FormField;
import com.base.common.dto.general_response.GeneralResponse;
import com.base.common.dto.general_response.GeneralResponseErrorDetail;
import com.base.common.dto.general_response.GeneralResponseTemp;
import com.base.common.utils.convert.object.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ValidatorUtils
        implements ICheckNullObject, ICheckMissingKey, ICheckTooLong, ICheckTooShort, ICheckPattern, ICheckLessThanMin,
        ICheckMoreThanMax, ICheckNotInList {

    public static Map<String, List<FormField>> constraintStore = new HashMap<>();
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void registryConstraint(String code, List<FormField> constraints) {
        ValidatorUtils.constraintStore.put(code, constraints);
    }

    public GeneralResponse validate(List<FormField> constraints, Map<String, Object> params) {
        if (ObjectUtils.isEmpty(constraints))
            return new GeneralResponse();

        Map<String, Object> errorDetails = new HashMap<>();
        List<String> errFields = new ArrayList<>();
        List<String> nullObjectFields = new ArrayList<>();
        List<String> tooLongFields = new ArrayList<>();
        List<String> tooShortFields = new ArrayList<>();
        List<String> notInListFields = new ArrayList<>();
        List<String> patternErrorFields = new ArrayList<>();
        List<String> missingKeyFields = new ArrayList<>();

        constraints.forEach(constraint -> {
            List<GeneralResponseTemp> __grts = new ArrayList<>();

            Object value = modifyData(constraint, params);

            if (checkMissingKey(constraint, params))
                __grts.add(ConstraintError.MISSING_KEY);

            if (checkNullObject(constraint, params))
                __grts.add(ConstraintError.NULL_OBJECT);

            if (checkTooLong(constraint, params))
                __grts.add(ConstraintError.TOO_LONG);

            if (checkTooShort(constraint, params))
                __grts.add(ConstraintError.TOO_SHORT);

            if (checkPattern(constraint, params))
                __grts.add(ConstraintError.PATTERN_ERROR);

            if (checkLessThanMin(constraint, params))
                __grts.add(ConstraintError.LESS_THAN_MIN);

            if (checkMoreThanMax(constraint, params))
                __grts.add(ConstraintError.MORE_THAN_MAX);

            if (checkNotInList(constraint, params))
                __grts.add(ConstraintError.NOT_IN_LIST);

            List<String> errorCodeList = __grts.stream().map(it -> it.errorCode).collect(Collectors.toList());
            List<String> errorMessageList = __grts.stream().map(it -> it.message).collect(Collectors.toList());

            Map<String, Object> ed = new HashMap<>();
            ed.put("value", value);

            ed.put("errorCodeList", errorCodeList);
            ed.put("errorMessageList", errorMessageList);
            errorDetails.put(constraint.code, ed);

            if (errorCodeList.size() > 0) {
                errFields.add(constraint.code);
            }
            if (errorCodeList.contains(ConstraintError.NULL_OBJECT.errorCode)) {
                nullObjectFields.add(constraint.code);
            }
            if (errorCodeList.contains(ConstraintError.TOO_LONG.errorCode)) {
                tooLongFields.add(constraint.code);
            }
            if (errorCodeList.contains(ConstraintError.TOO_SHORT.errorCode)) {
                tooShortFields.add(constraint.code);
            }
            if (errorCodeList.contains(ConstraintError.NOT_IN_LIST.errorCode)) {
                notInListFields.add(constraint.code);
            }
            if (errorCodeList.contains(ConstraintError.PATTERN_ERROR.errorCode)) {
                patternErrorFields.add(constraint.code);
            }
            if (errorCodeList.contains(ConstraintError.MISSING_KEY.errorCode)) {
                missingKeyFields.add(constraint.code);
            }
        });

        GeneralResponse generalResponse = errFields.size() > 0
                ? GeneralResponse.getInstance(GeneralResponseErrorDetail.PARAMS_VALIDATION_ERROR)
                : new GeneralResponse();
        Map<String, Object> responseValue = new HashMap<>();
        responseValue.put("errorDetail", errorDetails);
        responseValue.put("errFields", errFields);
        responseValue.put("nullObjectFields", nullObjectFields);
        responseValue.put("missingKeyFields", missingKeyFields);
        responseValue.put("tooLongFields", tooLongFields);
        responseValue.put("tooShortFields", tooShortFields);
        responseValue.put("notInListFields", notInListFields);
        responseValue.put("patternErrorFields", patternErrorFields);
        generalResponse.value = responseValue;

        return generalResponse;
    }

    public GeneralResponse validate(String constraintCode, Map<String, Object> params) {
        List<FormField> constraints = constraintStore.get(constraintCode);
        return validate(constraints, params);
    }

    /**
     * Modify data trước khi validate
     */
    private Object modifyData(FormField constraint, Map<String, Object> params) {
        Object value = ObjectUtils.getFieldValue(params, constraint.code);
        if (ObjectUtils.isEmpty(value))
            return value;

        if (constraint.trim != null && constraint.trim && value instanceof String) {
            value = ((String) value).trim();
        }
        if (constraint.removeAccent != null && constraint.removeAccent && value instanceof String) {
            value = normalizeName((String) value);
        }
        ObjectUtils.setFieldValue(params, constraint.code, value);
        return value;
    }

    private String normalizeName(String value) {
        return value;
    }

    @Override
    public Boolean checkMissingKey(FormField constraint, Map<String, Object> params) {
        return !ObjectUtils.containsKey((Object) params, constraint.code) && constraint.required;
    }

    @Override
    public Boolean checkNullObject(FormField constraint, Map<String, Object> params) {
        Object __value = ObjectUtils.getFieldValue(params, constraint.code);
        return constraint.required && ObjectUtils.isEmpty(__value) && !(__value instanceof Boolean);
    }

    @Override
    public Boolean checkTooLong(FormField constraint, Map<String, Object> params) {
        if (constraint.maxLength != null && constraint.maxLength >= 0) {
            Object value = ObjectUtils.getFieldValue(params, constraint.code);
            if (value instanceof String) {
                return ((String) value).length() > constraint.maxLength;
            } else if (ObjectUtils.isArray(value)) {
                return ((List<?>) value).size() > constraint.maxLength;
            } else if (value instanceof Map) {
                return ((Map<?, ?>) value).keySet().size() > constraint.maxLength;
            }
        }
        return false;
    }

    @Override
    public Boolean checkTooShort(FormField constraint, Map<String, Object> params) {
        if (constraint.minLength != null && constraint.minLength >= 0) {
            Object value = ObjectUtils.getFieldValue(params, constraint.code);
            if (value instanceof String) {
                return ((String) value).length() < constraint.minLength;
            } else if (ObjectUtils.isArray(value)) {
                return ((List<?>) value).size() < constraint.minLength;
            } else if (value instanceof Map) {
                return ((Map<?, ?>) value).keySet().size() < constraint.minLength;
            }
        }
        return false;
    }

    @Override
    public Boolean checkPattern(FormField constraint, Map<String, Object> params) {
        if (!ObjectUtils.isEmpty(constraint.pattern)) {
            Object value = ObjectUtils.getFieldValue(params, constraint.code);
            value = (value == null) ? "" : value.toString();
            Pattern pattern = Pattern.compile(constraint.pattern);
            return ObjectUtils.isEmpty(value) || !pattern.matcher(value.toString()).find();
        }
        return false;
    }

    @Override
    public Boolean checkLessThanMin(FormField constraint, Map<String, Object> params) {
        Object value = ObjectUtils.getFieldValue(params, constraint.code);
        if (constraint.min != null && !Double.isNaN(constraint.min) && !ObjectUtils.isEmpty(value)) {
            try {
                Double __value = objectMapper.convertValue(value, Double.class);
                return __value < constraint.min;
            } catch (Exception ex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean checkMoreThanMax(FormField constraint, Map<String, Object> params) {
        Object value = ObjectUtils.getFieldValue(params, constraint.code);
        if (constraint.max != null && !Double.isNaN(constraint.max) && !ObjectUtils.isEmpty(value)) {
            try {
                Double __value = objectMapper.convertValue(value, Double.class);
                return __value > constraint.max;
            } catch (Exception ex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean checkNotInList(FormField constraint, Map<String, Object> params) {
        if (!ObjectUtils.isEmpty(constraint.options)) {
            Object value = ObjectUtils.getFieldValue(params, constraint.code);
            List<Object> __values = constraint.options.stream().map(it -> it.value).collect(Collectors.toList());
            return !__values.contains(value);
        }
        return false;
    }
}
