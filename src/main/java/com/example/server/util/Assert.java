package com.example.server.util;

import com.example.server.common.ResultCode;
import com.example.server.exception.ApiException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 断言工具类，用于业务层参数校验和异常抛出
 */
public class Assert {

    /**
     * 断言对象不为空
     */
    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言对象不为空
     */
    public static void notNull(Object object, ResultCode resultCode) {
        if (Objects.isNull(object)) {
            throw new ApiException(resultCode);
        }
    }

    /**
     * 断言字符串不为空
     */
    public static void notEmpty(String str, String message) {
        if (!StringUtils.hasText(str)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言字符串不为空
     */
    public static void notEmpty(String str, ResultCode resultCode) {
        if (!StringUtils.hasText(str)) {
            throw new ApiException(resultCode);
        }
    }

    /**
     * 断言集合不为空
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言集合不为空
     */
    public static void notEmpty(Collection<?> collection, ResultCode resultCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ApiException(resultCode);
        }
    }

    /**
     * 断言Map不为空
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言Map不为空
     */
    public static void notEmpty(Map<?, ?> map, ResultCode resultCode) {
        if (CollectionUtils.isEmpty(map)) {
            throw new ApiException(resultCode);
        }
    }

    /**
     * 断言表达式为真
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言表达式为真
     */
    public static void isTrue(boolean expression, ResultCode resultCode) {
        if (!expression) {
            throw new ApiException(resultCode);
        }
    }

    /**
     * 断言表达式为真
     */
    public static void isTrue(boolean expression, Integer code, String message) {
        if (!expression) {
            throw new ApiException(code, message);
        }
    }

    /**
     * 断言表达式为假
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言表达式为假
     */
    public static void isFalse(boolean expression, ResultCode resultCode) {
        if (expression) {
            throw new ApiException(resultCode);
        }
    }

    /**
     * 断言表达式为假
     */
    public static void isFalse(boolean expression, Integer code, String message) {
        if (expression) {
            throw new ApiException(code, message);
        }
    }
}