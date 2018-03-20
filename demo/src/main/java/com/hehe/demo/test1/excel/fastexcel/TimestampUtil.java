package com.hehe.demo.test1.excel.fastexcel;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Date: 2017/10/11
 * Time: 上午11:34
 * Author: xieqinghe .
 */
public final class TimestampUtil {
    private static final LocalDateTime EPOCH_1900 = LocalDateTime.of(1900, 1, 1, 0, 0, 0, 0);

    private TimestampUtil() {
    }

    public static Double convertDate(Date date) {
        return convertDate(date, ZoneId.systemDefault());
    }

    public static Double convertDate(LocalDate date) {
        return Double.valueOf((double) ChronoUnit.DAYS.between(EPOCH_1900.toLocalDate(), date) + 2.0D);
    }

    public static Double convertZonedDateTime(ChronoZonedDateTime zdt) {
        return convertDate(Date.from(zdt.toInstant()), zdt.getZone());
    }

    private static Double convertDate(Date date, ZoneId timezone) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), timezone);
        Duration duration = Duration.between(EPOCH_1900, ldt);
        return Double.valueOf((double)duration.getSeconds() / 86400.0D + (double)duration.getNano() / 8.64E13D + 2.0D);
    }
}

