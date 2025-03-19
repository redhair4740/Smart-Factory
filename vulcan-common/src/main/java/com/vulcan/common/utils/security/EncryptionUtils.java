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

    public static final String privateKeyBase64 = "MIICXAIBAAKBgQCgax6uY6BIWCykCyVhlTpaC8jdkr7GzhpOkhzdZYi/sRr7kBy41x7o8xDp4e5NpWTWF49EQxuRKObpNOfLXugYvUKxgc9UY8vWILjOg3oyN+GNKMxZAnOPPbxS2eNFjZYQleM/y8MyVdKqxfnS4Dtfe2Tj0VR63rQ2IAZ5YCBO8wIDAQABAoGAAgmqT5vLXu/jzUeo92beYWNx70KccskOc34OqM9P3+N/jL+FZeGxrlLcHaR1kv3tXHzbIIpbsMncuQynfyFbAGQ7U0XxpNOUI/jwer3j81hULggrj8Q+YngNJICMq7l1aMs3Lt21ejm+SqIDWRDkfCohRhuALM/3cAAPmAFBHeUCQQDuup81667jSqmhGv4GynbB6xi6NjORl4sGl5Cxh+V+zV3zM7ky23j77wGn7+HB5IA9WQRkswuUaTZL74DVeuQ9AkEArAYl5OZUtnt4HuuO38xpbJN6746hu9hC0BgiWTKL1ngNnoqZis7WCQJzDlyjF2edF8fXMOMlIymlHT9jSxvC7wJAUrtqIY2j9jERyDtKfZ/8uAd5ck8GYBZcn9Q22M93wRH61SW/sNhOfUC/GiwP2NoPdzM6SouiH7S2lGpUA6erIQJABLrei96xcZsPgHoloY2zUGL1vGSFme6mV6ZyO1WidrEyXquc8S4iwHiqxThS26/jp+W8ywFT4hWhDp7Rkh0fTQJBAKA60Gqtfi6aKkl90a9eBIIawO7Tfso2UQ0PZBFVM29a2AG5rvAjB1Et3cf0a7xf76XyF7LM4O03+h+mIlksY4c=";

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