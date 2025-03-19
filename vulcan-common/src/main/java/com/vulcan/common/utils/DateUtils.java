package com.vulcan.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 *
 * @author Y
 */
@Slf4j
public class DateUtils {

    /**
     * 日期格式缓存，提高性能
     */
    private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new HashMap<>();

    /**
     * 根据格式获取DateTimeFormatter
     *
     * @param pattern 格式
     * @return DateTimeFormatter
     */
    public static DateTimeFormatter getFormatter(String pattern) {
        return FORMATTER_CACHE.computeIfAbsent(pattern, DateTimeFormatter::ofPattern);
    }

    /**
     * 格式化日期
     *
     * @param dateTime 日期时间
     * @param pattern 格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        try {
            if (dateTime == null || pattern == null || pattern.isEmpty()) {
                return null;
            }
            return dateTime.format(getFormatter(pattern));
        } catch (Exception e) {
            log.error("日期格式化异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @param date 日期
     * @param pattern 格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDate date, String pattern) {
        try {
            if (date == null || pattern == null || pattern.isEmpty()) {
                return null;
            }
            return date.format(getFormatter(pattern));
        } catch (Exception e) {
            log.error("日期格式化异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析日期字符串
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return 日期
     */
    public static LocalDate parseDate(String dateStr, String pattern) {
        try {
            if (dateStr == null || pattern == null || pattern.isEmpty()) {
                return null;
            }
            return LocalDate.parse(dateStr, getFormatter(pattern));
        } catch (Exception e) {
            log.error("日期解析异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析日期时间字符串
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern 格式
     * @return 日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        try {
            if (dateTimeStr == null || pattern == null || pattern.isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(dateTimeStr, getFormatter(pattern));
        } catch (Exception e) {
            log.error("日期时间解析异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取当前日期的格式化字符串
     *
     * @param pattern 格式
     * @return 格式化后的字符串
     */
    public static String getCurrentDateStr(String pattern) {
        return format(LocalDate.now(), pattern);
    }

    /**
     * 获取当前日期时间的格式化字符串
     *
     * @param pattern 格式
     * @return 格式化后的字符串
     */
    public static String getCurrentDateTimeStr(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }
} 