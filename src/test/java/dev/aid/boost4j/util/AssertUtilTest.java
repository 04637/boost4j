package dev.aid.boost4j.util;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import dev.aid.boost4j.exp.ParamExp;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 断言工具类测试
 *
 * @author 04637@163.com
 * @date 2020/11/10
 */
public class AssertUtilTest {

    @Test
    @DisplayName("断言布尔值")
    public void testBoolean() {
        boolean q = false;
        try {
            AssertUtil.isTrue(q, "q");
        } catch (ParamExp e) {
            assertEquals("q 不能为 false", e.getMessage());
        }

        try {
            AssertUtil.isTrue(q, "该情况下 %s 不能为 false", "q");
        } catch (ParamExp e) {
            assertEquals("该情况下 q 不能为 false", e.getMessage());
        }
        AssertUtil.isTrue(!q, "ok");

        boolean d = true;
        try {
            AssertUtil.isFalse(d, "d");
        } catch (ParamExp e) {
            assertEquals("d 不能为 true", e.getMessage());
        }
        try {
            AssertUtil.isFalse(d, "该情况下 %s 不能为 true", "d");
        } catch (ParamExp e) {
            assertEquals("该情况下 d 不能为 true", e.getMessage());
        }
        AssertUtil.isFalse(!d, "ok");
    }

    @Test
    @DisplayName("empty测试")
    public void testEmpty() {
        String isBlank = " ";
        String notBlank = "bob";
        String isEmpty = "";
        String isEmpty2 = null;

        AssertUtil.notBlank(notBlank, "ok");
        AssertUtil.notEmpty(notBlank, "ok");
        try {
            AssertUtil.notEmpty(isEmpty, "isEmpty");
        } catch (ParamExp e) {
            assertEquals("isEmpty 不能为空", e.getMessage());
        }

        try {
            AssertUtil.notEmpty(isEmpty2, "isEmpty2");
        } catch (ParamExp e) {
            assertEquals("isEmpty2 不能为空", e.getMessage());
        }

        try {
            AssertUtil.notBlank(isBlank, "isBlank");
        } catch (ParamExp e) {
            assertEquals("isBlank 不能为空白", e.getMessage());
        }

        try {
            AssertUtil.notBlank(isEmpty, "isEmpty");
        } catch (ParamExp e) {
            assertEquals("isEmpty 不能为空白", e.getMessage());
        }

        try {
            AssertUtil.notBlank(isEmpty2, "isEmpty2");
        } catch (ParamExp e) {
            assertEquals("isEmpty2 不能为空白", e.getMessage());
        }
    }

    @Test
    @DisplayName("null测试")
    public void testNull() {
        String s = null;
        try {
            AssertUtil.notNull(s, "s");
        } catch (ParamExp e) {
            assertEquals("s 不能为 null", e.getMessage());
        }
        AssertUtil.notNull("1233", "ok");
    }

}
