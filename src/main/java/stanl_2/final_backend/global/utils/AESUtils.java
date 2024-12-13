package stanl_2.final_backend.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Slf4j
@Component
public class AESUtils {

    @Value("${encryption.algorithm}")
    private String algorithm;

    @Value("${encryption.transformation}")
    private String transformation;

    @Value("${encryption.secret-key}")
    private String secretKeyValue;

    /**
     * AES 대칭키를 사용하여 문자열을 암호화합니다.
     */
    public String encrypt(String data) throws GeneralSecurityException {

        if(data == null){
            return null;
        }

        Cipher cipher = Cipher.getInstance(transformation);
        SecretKey secretKey = new SecretKeySpec(secretKeyValue.getBytes(StandardCharsets.UTF_8), algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(encryptedBytes);
    }

    /**
     * AES 대칭키를 사용하여 암호화된 문자열을 복호화합니다.
     */
    public String decrypt(String encryptedData) throws GeneralSecurityException {

        if(encryptedData == null){
            return null;
        }


        Cipher cipher = Cipher.getInstance(transformation);
        SecretKey secretKey = new SecretKeySpec(secretKeyValue.getBytes(StandardCharsets.UTF_8), algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.decodeBase64(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
