package com.example.travel.util.Key;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/23 13:33
 */

public class GenerateKey
{
    static public void main(String args[]) throws Exception {
        String keyFilename = "jiami";
        String algorithm = "DES";

        // 生成密匙
        SecureRandom sr = new SecureRandom();
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        kg.init(sr);
        SecretKey key = kg.generateKey();

        // 把密匙数据保存到文件
        Util.writeFile(keyFilename, key.getEncoded());
    }
}