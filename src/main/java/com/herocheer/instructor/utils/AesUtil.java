package com.herocheer.instructor.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @author chenwf
 * @desc
 * @date 2021/2/25
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
public class AesUtil {
    private static final String ENCODE = "UTF-8";
    private static final String FORMAT = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    public static final String secretKey = "e6a24eb4a7a741a795ecc0a3e35caef2";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    /**
     * 加密
     * @param content
     * @return
     */
    public static String encrypt(String content){
        try{
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] raw = secretKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(ENCODE));
            return Base64Utils.encodeToString(encrypted);
        } catch (Exception e) {
            log.error("解密异常 ",e);
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String str){
        try {

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(ENCODE), FORMAT);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] result = cipher.doFinal(Base64Utils.decodeFromString(str));
            return new String(result, ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "6m3IuNPhrWNFN8C6NGhFcy+r790j+OxpNFpLsAeg/QI=";
        String a = decrypt(str);
        System.out.println(a);
//        System.out.println(decrypt(a));
    }
}
