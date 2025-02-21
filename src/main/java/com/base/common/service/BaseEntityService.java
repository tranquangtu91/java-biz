package com.base.common.service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.base.common.dto.data_table_filter.DataTableFilter;
import com.base.common.dto.data_table_filter.Filter;
import com.base.common.dto.data_table_filter.DataTableFilter.GlobalSearchParam;
import com.base.common.dto.data_table_filter.DataTableFilter.SortOrder;
import com.base.common.dto.data_table_response.DataTableResponse;
import com.base.common.repository.BaseCrudRepository;
import com.base.common.utils.convert.date.DateUtils;
import com.base.common.utils.convert.object.ReflectUtils;
import com.base.common.utils.sql.CriteriaBuilderHandlerFactory;
import com.base.common.utils.sql.ICriteriaBuilderHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityService<T> implements IEntityService<T> {
    public BaseCrudRepository<T, Long> entityRepository;

    public String PROPS_FIELD_NAME = "props";

    public static Boolean verbose = false;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AutowireCapableBeanFactory beanFactory;

    @Autowired
    ApplicationContext applicationContext;

    static ICriteriaBuilderHandler criteriaBuilderHandler = CriteriaBuilderHandlerFactory
            .getCriteriaBuilderHandler(null);

    static DateUtils dateUtils = new DateUtils();

    @Override
    public List<T> list() {
        List<T> rs = new ArrayList<>();
        entityRepository.findAll().forEach(rs::add);
        return rs;
    }

    @Override
    public T get(Long id, Map<String, Object> options) {
        Optional<T> optional = entityRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public T save(T item) {
        return entityRepository.save(item);
    }

    @Override
    public T update(Long id, T item, Map<String, Object> options) {
        item = entityRepository.save(item);
        return item;
    }

    @Override
    public T delete(Long id) {
        Optional<T> optional = entityRepository.findById(id);
        if (!optional.isPresent())
            return null;

        T obj = optional.get();
        entityRepository.delete(obj);

        return obj;
    }

    @Override
    public DataTableResponse<?> loadDataTable(DataTableFilter dataTableFilter, LoadDataTableOptions options) {
        options = options == null ? new LoadDataTableOptions() : options;

        List<String> fields = options.fields == null ? new ArrayList<>() : options.fields;
        Class<?> domainClass = options.domainClass == null ? Object.class : options.domainClass;

        int rows = dataTableFilter.getRows() == null ? 100 : dataTableFilter.getRows();
        int first = dataTableFilter.getFirst() == null ? 0 : dataTableFilter.getFirst();
        String sortField = "id";
        String sortOrder = "desc";

        List<String> declaredFieldNames = ReflectUtils.getDeclaredFieldNames(domainClass);

        if (declaredFieldNames.contains(dataTableFilter.getSortField())) {
            sortField = dataTableFilter.getSortField();
            sortOrder = dataTableFilter.getSortOrder() == SortOrder.ASC.getVal() ? "asc" : "desc";
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<?> cq;
        if (fields.size() > 0)
            cq = cb.createQuery(Object.class);
        else
            cq = cb.createQuery(domainClass);
        Root<?> objectRoot = cq.from(domainClass);
        if (fields.size() > 0) {
            List<Selection<?>> selections = fields.stream().map(objectRoot::get).collect(Collectors.toList());
            cq.multiselect(selections);
        }
        cq.where(getPredicates(dataTableFilter, options, cb, objectRoot));
        if (sortOrder.equals("desc")) {
            cq.orderBy(cb.desc(objectRoot.get(sortField)));
        } else {
            cq.orderBy(cb.asc(objectRoot.get(sortField)));
        }

        TypedQuery<?> query = entityManager.createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(rows);
        List<?> items = query.getResultList();

        CriteriaQuery<Long> __cq = cb.createQuery(Long.class);
        Root<?> __objectRoot = __cq.from(domainClass);
        __cq.select(cb.count(__objectRoot));
        __cq.where(getPredicates(dataTableFilter, options, cb, __objectRoot));
        Long totalRows = entityManager.createQuery(__cq).getSingleResult();

        DataTableResponse<Object> dtr = new DataTableResponse<>();
        dtr.rows = rows;
        dtr.first = first;
        dtr.totalRows = totalRows;
        dtr.items = (List<Object>) items;
        return dtr;
    }

    /**
     * Get predicates for the query
     * @param dataTableFilter
     * @param options
     * @param cb
     * @param objectRoot
     * @return
     */
    private Predicate[] getPredicates(DataTableFilter dataTableFilter, LoadDataTableOptions options, CriteriaBuilder cb,
            Root<?> objectRoot) {
        List<Predicate> predicates = new ArrayList<>();

        Class<?> domainClass = options.domainClass == null ? Object.class : options.domainClass;
        Map<String, Filter> filters = dataTableFilter.getFilters() == null ? new HashMap<>()
                : dataTableFilter.getFilters();
        List<String> declaredFieldNames = ReflectUtils.getDeclaredFieldNames(domainClass);

        for (Entry<String, Filter> entry : filters.entrySet()) {
            String key = entry.getKey();
            Filter filter = entry.getValue();
            Object __value = filter.getValue();
            String[] __keys = key.split("\\.");

            if (__value == null)
                continue;

            Boolean hasField = declaredFieldNames.contains(__keys[0]);
            Boolean searchInProps = false;
            Class<?> fieldType = null;
            if (hasField) {
                fieldType = Objects.requireNonNull(ReflectUtils.getField(domainClass, key)).getType();

                if (fieldType == String.class && !(__value instanceof String)) {
                    __value = __value.toString();
                } else if (fieldType == Long.class && !(__value instanceof Long)) {
                    __value = Long.parseLong(__value.toString());
                } else if (fieldType == Integer.class && !(__value instanceof Integer)) {
                    __value = Integer.parseInt(__value.toString());
                }
            } else if (declaredFieldNames.contains(PROPS_FIELD_NAME)) {
                searchInProps = true;
                __value = __value.toString();
            }

            Predicate predicate = null;
            Expression<?> expression;

            switch (filter.getMatchMode()) {
                case BETWEEN:
                    Date fromValue = null, toValue = null;
                    if (fieldType == Date.class) {
                        fromValue = tryParseDate(((ArrayList<?>) __value).get(0).toString());
                        toValue = tryParseDate(((ArrayList<?>) __value).get(1).toString());
                    }
                    if (hasField) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        predicate = cb.between((Expression<Date>) expression, fromValue, toValue);
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = cb.between((Expression<Date>) expression, fromValue, toValue);
                    }
                    break;
                case CONTAINS:
                    __value = "%" + __value + "%";
                    if (hasField) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        predicate = cb.like((Expression<String>) expression, (String) __value);
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = cb.like((Expression<String>) expression, (String) __value);
                    }
                    break;
                case ENDS_WITH:
                    __value = "%" + __value;
                    if (hasField) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        predicate = cb.like((Expression<String>) expression, (String) __value);
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = cb.like((Expression<String>) expression, (String) __value);
                    }
                    break;
                case EQUALS:
                    if (hasField) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        predicate = cb.equal(expression, __value);
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = cb.equal(expression, __value);
                    }
                    break;
                case GREATER_THAN:
                    if (hasField) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        if (fieldType == String.class) {
                            predicate = cb.greaterThan((Expression<String>) expression, (String) __value);
                        } else if (fieldType == Long.class) {
                            predicate = cb.greaterThan((Expression<Long>) expression, (Long) __value);
                        } else if (fieldType == Integer.class) {
                            predicate = cb.greaterThan((Expression<Integer>) expression, (Integer) __value);
                        }
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = cb.greaterThan((Expression<String>) expression, (String) __value);
                    }
                    break;
                case GREATER_THAN_OR_EQUALS:
                    break;
                case IN_LIST:
                    __value = new JSONArray(__value).toList();
                    if (hasField) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        predicate = expression.in(__value);
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = expression.in(__value);
                    }
                    break;
                case LOWERS_THAN:
                    break;
                case LOWERS_THAN_OR_EQUALS:
                    break;
                case NOT:
                    break;
                case NOT_IN_LIST:
                    __value = new JSONArray(__value).toList();
                    if (declaredFieldNames.contains(key)) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        predicate = cb.not(expression.in(__value));
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = cb.not(expression.in(__value));
                    }
                    break;
                case STARTS_WITH:
                    __value = __value + "%";
                    if (hasField) {
                        expression = criteriaBuilderHandler.getKey(objectRoot, key);
                        predicate = cb.like((Expression<String>) expression, (String) __value);
                    } else if (searchInProps) {
                        expression = criteriaBuilderHandler.functionJsonExtract(cb, objectRoot, PROPS_FIELD_NAME, key,
                                String.class);
                        predicate = cb.like((Expression<String>) expression, (String) __value);
                    }
                    break;
                default:
                    break;
            }

            if (predicate != null) {
                if (searchInProps) {
                    predicate = cb.and(cb.isNotNull(criteriaBuilderHandler.getKey(objectRoot, PROPS_FIELD_NAME)),
                            predicate);
                }
                predicates.add(predicate);
            }
        }

        if (declaredFieldNames.contains("deleted")) {
            predicates.add(0, cb.or(cb.equal(objectRoot.get("deleted"), false), cb.isNull(objectRoot.get("deleted"))));
        }

        GlobalSearchParam gsp = dataTableFilter.getGlobalSearchParam();
        if (gsp != null) {
            List<Predicate> predicateOR = new ArrayList<>();
            for (String __key : gsp.keys) {
                String val = "%" + gsp.value + "%";
                Expression<?> expression;
                if (declaredFieldNames.contains(__key)) {
                    expression = objectRoot.get(__key);
                    predicateOR.add(cb.like((Expression<String>) expression, val));
                }
            }
            predicates.add(cb.or(predicateOR.toArray(new Predicate[0])));
        }

        return predicates.toArray(new Predicate[0]);
    }

    static Date tryParseDate(String dateStr) {
        return dateUtils.parse(dateStr, null);
    }
}
