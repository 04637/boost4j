package dev.aid.boost4j.exp;

/**
 * 数据层异常
 *
 * @author 04637@163.com
 * @date 2020/11/9
 */
public class DaoExp extends BaseException {

    public DaoExp(String msg) {
        super(msg);
    }

    public DaoExp(Throwable throwable) {
        super(throwable);
    }

    public DaoExp() {
        super("数据层处理异常, 请向开发人员反馈 expStack");
    }

}
