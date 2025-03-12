package com.vulcan.common.utils.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.utils.security
 * @name: EncryptionUtils
 * @Date: 2024/4/28 上午9:21
 * @Description 加密工具类，提供RSA加密解密功能
 */
public class EncryptionUtils {

    public static final String privateKeyBase64 = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA2/2WD+" +
            "Yy4EMJvJQGKw5YxHP8Cr8dRaLL5vEiDumqLdidhCKVxxTTJ3IThPVU2J2FJTZNJZwlWdyZjVCpWzpbPwIDAQABAkAO67M" +
            "q3uUiesmFRdDpSK7SnD9DB1VX7MPBpSc3Nbf6Je/P+S9CyE2AgXkKkJiKFENfXhbZZJo3xMnIBHavs12ZAiEA7bzYnYv2" +
            "0SaduIy03CyRigGoDbQN7dAkJSQqT+N9/C0CIQDs471uApYSixx81QakZ1mXEFnEr3/XViyz8mEcd6zcmwIgGQxRol/R1H" +
            "2GFqC0mZIUj37XLwJuiUTp2gPydVJZvs0CIQDatF/9oxGeySRh8+MQAgLqv0PBnS3wRzU6ZXpW5/6GJQIfEo1V520WaL3C4" +
            "263Zk7YBP4JBf27xhEzXLtIkCEQ8A==";

    static {
        // 添加Bouncy Castle安全提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    public static <T> void decrypt(T loginData, String encryptedField, String decryptedField) throws Exception {
        // 加载私钥
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // 初始化解密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC"); // 或 "RSA/ECB/OAEPPadding" 如果前端使用了OAEP
        if ("RSA/ECB/OAEPPadding".equals(cipher.getAlgorithm())) {
            // 对于OAEP padding，需要设置参数
            OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        }

        try {
            // 使用反射获取字段值
            java.lang.reflect.Field encryptedFieldObj = loginData.getClass().getDeclaredField(encryptedField);
            encryptedFieldObj.setAccessible(true);
            String encryptedValue = (String) encryptedFieldObj.get(loginData);
            
            // 解密数据
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            
            // 设置解密后的值到指定字段
            java.lang.reflect.Field decryptedFieldObj = loginData.getClass().getDeclaredField(decryptedField);
            decryptedFieldObj.setAccessible(true);
            decryptedFieldObj.set(loginData, new String(decryptedBytes, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("解密失败", e);
        }
    }
} 