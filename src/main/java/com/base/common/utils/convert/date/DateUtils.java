package com.base.common.utils.convert.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import com.base.common.utils.convert.ConvertOption;
import com.base.common.utils.convert.IConvertHandler;
import com.base.common.utils.convert.IConvertUtils;
import com.base.common.utils.convert.object.ObjectUtils;

public class DateUtils implements IConvertUtils<Date> {
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ISO_TIME_FORMAT = "HH:mm:ss.SSSXXX";

    static ConvertOption[] patterns = {
            new ConvertOption(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$"),
                    new IConvertHandler<Date>() {

                        @Override
                        public Date handler(Object data) {
                            Date date = null;
                            try {
                                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(data.toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return date;
                        }
                    }),
            new ConvertOption(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[ +]{1}\\d{2}:\\d{2}$"),
                    new IConvertHandler<Date>() {

                        @Override
                        public Date handler(Object data) {
                            // TODO Auto-generated method stub
                            throw new UnsupportedOperationException("Unimplemented method 'handler'");
                        }
                    }),
            new ConvertOption(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}[ +]{1}\\d{2}:\\d{2}$"),
                    new IConvertHandler<Date>() {

                        @Override
                        public Date handler(Object data) {
                            // TODO Auto-generated method stub
                            throw new UnsupportedOperationException("Unimplemented method 'handler'");
                        }
                    }),
            new ConvertOption(
                    Pattern.compile(
                            "^(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2}).\\d{3}[ +]{1}(\\d{2})(\\d{2})$"),
                    new IConvertHandler<Date>() {

                        @Override
                        public Date handler(Object data) {
                            // TODO Auto-generated method stub
                            throw new UnsupportedOperationException("Unimplemented method 'handler'");
                        }
                    }),
            new ConvertOption(Pattern.compile("^(\\d+)$"), new IConvertHandler<Date>() {

                @Override
                public Date handler(Object data) {
                    Long epoch = Long.parseLong(data.toString());
                    return new Date(epoch);
                }
            }) };

    @Override
    public Date parse(Object data, ConvertOption[] convertOptions) {
        if (ObjectUtils.isEmpty(convertOptions))
            convertOptions = DateUtils.patterns;
        Date __value = null;
        for (ConvertOption convertOption : convertOptions) {
            Pattern pattern = convertOption.pattern;
            if (pattern.matcher(data.toString()).matches()) {
                __value = (Date) (convertOption.handler.handler(data));
            }
        }
        return __value;
    }

    static Date startOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    static Date endOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
