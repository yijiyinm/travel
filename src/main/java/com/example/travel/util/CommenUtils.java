package com.example.travel.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yijiyin
 */
@Slf4j
public class CommenUtils {

    public static Map<String,String> decryptUserIdAndTokenByStr(String encryptStr){
        String gameAesKey = "Ae980019bc59fd40f0ef34798e11210a";
        String decryptStr = CommenUtils.jdkAESDecrypt(gameAesKey,encryptStr);

        Map<String,String> map = new HashMap<>(2);
        String[] split = decryptStr.split(",");
        if (split.length != 2){
            return null;
        }
        map.put("userId",split[0]);
        map.put("token",split[1]);
        return map;
    }

    public static String jdkAESEncrypt(String HexStringKey,String content){
        try {
            byte[] bytesKey = Hex.decodeHex(HexStringKey.toCharArray());

            Key convertSecretKey = new SecretKeySpec(bytesKey,"AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,convertSecretKey);
            byte[] encryptResult = cipher.doFinal(content.getBytes());
            String encryptResultStr = Hex.encodeHexString(encryptResult);
            log.info("jdk des encrypt:"+ encryptResultStr);
            return encryptResultStr;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String jdkAESDecrypt(String HexStringKey,String encryptResultStr){
        try {
            byte[] bytesKey = Hex.decodeHex(HexStringKey.toCharArray());

            Key convertSecretKey = new SecretKeySpec(bytesKey,"AES");

            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,convertSecretKey);
            byte[] encryptResult = Hex.decodeHex(encryptResultStr.toCharArray());
            byte[] decryptResult = cipher.doFinal(encryptResult);
            String decryptStr = new String(decryptResult);
            log.info("jdk des decrypt:"+decryptStr);
            return decryptStr;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
