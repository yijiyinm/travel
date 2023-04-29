package com.example.travel.util;

import java.util.Random;

/**
 * @author yijiyin
 */
public class GenerateCodeUtil {

    static String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public static String createCode (int n){
        Random random=new Random();

        StringBuffer sb=new StringBuffer();


        for(int i=0; i<n; ++i){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }

        //将承载的字符转换成字符串

        return sb.toString();
    }

}
