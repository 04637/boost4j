package dev.aid.boost4j.exp;

/**
 * 基础自定义异常
 *
 * @author 04637@163.com
 * @date 2020/11/9
 */
public class BaseException extends RuntimeException {

    // 源异常, 记录原始异常方便问题追踪
    private Throwable originalExp;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(Throwable originalExp) {
        super(originalExp.getMessage());
        this.originalExp = originalExp;
    }

    public BaseException setOriginalExp(Throwable originalExp) {
        this.originalExp = originalExp;
        return this;
    }

    public Throwable getOriginalExp() {
        return originalExp;
    }
}
