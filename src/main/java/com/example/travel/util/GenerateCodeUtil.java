package com.example.travel.util;

import java.util.Random;

/**
 * @author yijiyin
 */
public class GenerateCodeUtil {

    public static String createCode (int n){
        String codes = "";
        Random r = new Random();
        // 20个字符
        for (int i=0; i<n;i++){
            int num = r.nextInt(3);
            switch (num){
                case 0:
                    codes+=r.nextInt(10);
                    break;
                case 1:
                    char ch=(char)(r.nextInt(26)+65);
                    codes+=ch;
                case 2:
                    char ch1 = (char)(r.nextInt(26)+97);
                    codes+=ch1;
            }

        }
        return codes;
    }

}
