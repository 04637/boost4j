package dev.aid.boost4j.auth;

/**
 * 鉴权专用用户
 * 可在自己的用户实体类中实现  public void toAuthUser(); 方法, 方便转换使用
 *
 * @author 04637@163.com
 * @date 2020/11/24
 */
public class AuthUser {
    private String userId;
    private UserRole[] userRoles;

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserRoles(UserRole[] userRoles) {
        this.userRoles = userRoles;
    }

    public UserRole[] getUserRoles() {
        return userRoles;
    }
}
