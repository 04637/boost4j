package dev.aid.boost4j.util;

import org.apache.commons.lang3.StringUtils;

import dev.aid.boost4j.exp.ParamExp;

/**
 * 断言式参数检查
 *
 * @author 04637@163.com
 * @date 2020/11/10
 */
public class AssertUtil {

    /**
     * 检查表达式是否为 true, 否抛出异常 {@link ParamExp}
     *
     * @param expression 待检查表达式
     * @param paramName  待提示参数名 例: ${param} 不能为 false
     */
    public static void isTrue(boolean expression, String paramName) {
        if (!expression) {
            throw new ParamExp(paramName + " 不能为 false");
        }
    }


    /**
     * 检查表达式是否为 true, 否抛出异常 {@link ParamExp}
     *
     * @param expression 待检查表达式
     * @param format     提示信息格式化字符串
     * @param args       格式化参数
     */
    public static void isTrue(boolean expression, String format, Object... args) {
        if (!expression) {
            throw new ParamExp(String.format(format, args));
        }
    }

    /**
     * 检查表达式是否为 false, 否抛出异常 {@link ParamExp}
     *
     * @param expression 待检查表达式
     * @param paramName  待提示参数名 例: ${param} 不能为 true
     */
    public static void isFalse(boolean expression, String paramName) {
        if (expression) {
            throw new ParamExp(paramName + " 不能为 true");
        }
    }

    /**
     * 检查表达式是否为 false, 否抛出异常 {@link ParamExp}
     *
     * @param expression 待检查表达式
     * @param format     提示信息格式化字符串
     * @param args       格式化参数
     */
    public static void isFalse(boolean expression, String format, Object... args) {
        if (expression) {
            throw new ParamExp(String.format(format, args));
        }
    }

    /**
     * 检查obj是否为null, 否则抛出异常 {@link ParamExp}
     *
     * @param object    待检查类
     * @param paramName 待提示参数名 例: ${param} 不能为 null
     */
    public static void notNull(Object object, String paramName) {
        if (object == null) {
            throw new ParamExp(paramName + " 不能为 null");
        }
    }

    /**
     * 检查字符序列是否为空, 是则抛出异常 {@link ParamExp}
     *
     * @param sequence  待检查字符序列
     * @param paramName 待提示参数名 例: ${param} 不能为空
     */
    public static void notEmpty(CharSequence sequence, String paramName) {
        if (StringUtils.isEmpty(sequence)) {
            throw new ParamExp(paramName + " 不能为空");
        }
    }

    /**
     * 检查字符序列是否为空白, 是则抛出异常 {@link ParamExp}
     *
     * @param sequence  待检查字符序列
     * @param paramName 待提示参数名 例: ${param} 不能为空白
     */
    public static void notBlank(CharSequence sequence, String paramName) {
        if (StringUtils.isBlank(sequence)) {
            throw new ParamExp(paramName + " 不能为空白");
        }
    }
}
