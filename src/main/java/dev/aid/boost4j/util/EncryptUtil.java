package dev.aid.boost4j.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 加密模块, 包含md5加密, rsa加解密
 * rsa 参考: https://blog.csdn.net/qy20115549/article/details/83105736
 *
 * @author 04637@163.com
 * @date 2020/11/10
 */
public class EncryptUtil {
    /**
     * md5 utf8字节码加密
     *
     * @param str 待加密字符串
     * @return 加密后字符串
     */
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * RSA 公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 加密后字符串
     */
    public static String rsaE(String str, String publicKey) {
        try {
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
            // 加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.encodeBase64String(cipher.doFinal(str
                    .getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA 私钥解密
     *
     * @param str        待解密字符串
     * @param privateKey 私钥
     * @return 解密后字符串
     */
    public static String rsaD(String str, String privateKey) {
        try {
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            // 解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
