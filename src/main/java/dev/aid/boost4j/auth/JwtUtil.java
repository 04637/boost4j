package dev.aid.boost4j.auth;

import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import java.util.stream.Collectors;

import dev.aid.boost4j.exp.ServiceExp;

/**
 * jwt签发及解析工具
 *
 * @author 04637@163.com
 * @date 2020/11/23
 */
public class JwtUtil {
    private static RsaJsonWebKey rsaJsonWebKey = null;
    private static final String ISSUER = "dev.aid.boost4j";
    private static final String AUDIENCE = "dev.aid.boost4j";
    private static final Integer EXPIRATION_MINUTES = 1440;
    private static final String SUBJECT = "boost4jAuth";
    private static final Integer NOT_BEFORE_MINUTES = 2;

    private static RsaJsonWebKey getRsaJsonWebKey() {
        try {
            if (rsaJsonWebKey == null) {
                rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
                rsaJsonWebKey.setKeyId("deva");
            }
            return rsaJsonWebKey;
        } catch (JoseException e) {
            throw new ServiceExp("JWT 秘钥生成出错: " + e.getMessage()).setOriginalExp(e);
        }
    }

    /**
     * 签发token
     *
     * @param userId 用户ID
     * @param role   单一角色
     * @return 带有角色及用户ID的token
     */
    public static String sign(String userId, UserRole role) {
        return sign(userId, new UserRole[]{role});
    }

    /**
     * 签发token
     *
     * @param userId 用户ID
     * @param roles  指定多角色
     * @return 带有多角色及用户ID的token
     */
    public static String sign(String userId, UserRole[] roles) {
        RsaJsonWebKey rsaJsonWebKey = getRsaJsonWebKey();
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(ISSUER);
        claims.setAudience(AUDIENCE);
        claims.setExpirationTimeMinutesInTheFuture(EXPIRATION_MINUTES);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(NOT_BEFORE_MINUTES);
        claims.setSubject(SUBJECT);
        claims.setClaim("userId", userId);
        claims.setClaim("userRole", roles);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_PSS_USING_SHA256);

        try {
            return jws.getCompactSerialization();
        } catch (JoseException e) {
            throw new ServiceExp("JWT sign 异常: " + e.getMessage())
                    .setOriginalExp(e);
        }
    }

    private static JwtClaims checkJwt(String jwt) throws InvalidJwtException {
        JwtConsumer jwtConsumer =
                new JwtConsumerBuilder()
                        .setRequireExpirationTime()
                        .setAllowedClockSkewInSeconds(30)
                        .setExpectedSubject(SUBJECT)
                        .setExpectedIssuer(ISSUER)
                        .setExpectedAudience(AUDIENCE)
                        .setVerificationKey(getRsaJsonWebKey().getKey())
                        .setJwsAlgorithmConstraints(
                                new AlgorithmConstraints(
                                        AlgorithmConstraints.ConstraintType.WHITELIST,
                                        AlgorithmIdentifiers.RSA_PSS_USING_SHA256))
                        .build();

        return jwtConsumer.processToClaims(jwt);
    }

    /**
     * 验证 token 并将 token 解析为 user
     *
     * @param token 待解析 token
     * @return 解析出的 UserInfoVO; 校验失败时 return null
     */
    static User parseToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            JwtClaims jwtClaims = JwtUtil.checkJwt(token);
            User user = new User();
            user.setUserId(jwtClaims.getClaimValueAsString("userId"));
            user.setUserRoles(jwtClaims.getStringListClaimValue("userRole")
                    .stream().map(UserRole::valueOf).collect(Collectors.toSet()).toArray(UserRole.values()));
            return user;
        } catch (InvalidJwtException | MalformedClaimException e) {
            return null;
        }
    }

    private JwtUtil() {
    }
}

class User {
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