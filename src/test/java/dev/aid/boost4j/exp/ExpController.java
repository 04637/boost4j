package dev.aid.boost4j.exp;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;
import javax.validation.Valid;

import dev.aid.boost4j.common.Code;
import dev.aid.boost4j.common.Resp;
import dev.aid.boost4j.util.AssertUtil;

/**
 * 异常测试用controller
 *
 * @author 04637@163.com
 * @date 2020/11/9
 */
@RestController
@RequestMapping("/expTest")
@Validated
public class ExpController {

    @RequestMapping("/service")
    public Resp serviceExp() {
        throw new ServiceExp();
    }

    @RequestMapping("/serviceWithMsg")
    public Resp serviceExpMsg(String msg) {
        throw new ServiceExp(msg);
    }

    @RequestMapping("/serviceExp")
    public Resp serviceExpThrow() {
        throw new ServiceExp(new NoPermissionException()).setExpCode(Code.PERMISSION_DENIED);
    }

    @RequestMapping("/otherExp")
    public Resp otherExp() throws NoPermissionException {
        throw new NoPermissionException();
    }

    @RequestMapping("/paramExp")
    public Resp paramExp() {
        AssertUtil.notEmpty("", "mama");
        return Resp.ok("im ok");
    }

    @RequestMapping("/dao")
    public Resp daoExp() {
        throw new DaoExp();
    }

    @RequestMapping("/daoWithMsg")
    public Resp daoExpMsg(String msg) {
        throw new DaoExp(msg);
    }

    @RequestMapping("/arg")
    public Resp argumentExp(@Valid @RequestBody ArgTest arg) {
        return Resp.ok(arg);
    }
}
