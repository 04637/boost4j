package dev.aid.boost4j.exp;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数异常测试辅助
 *
 * @author 04637@163.com
 * @date 2020/11/9
 */
public class ArgTest {

    @NotBlank(message = "该参数不能为空哦")
    private String notEmpty;

    @Size(max = 3, message = "长度超长了")
    private String maxLength;

    @Max(10)
    private int bindFailed;

    public String getNotEmpty() {
        return notEmpty;
    }

    public ArgTest setNotEmpty(String notEmpty) {
        this.notEmpty = notEmpty;
        return this;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public ArgTest setMaxLength(String maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public int getBindFailed() {
        return bindFailed;
    }

    public ArgTest setBindFailed(int bindFailed) {
        this.bindFailed = bindFailed;
        return this;
    }
}
