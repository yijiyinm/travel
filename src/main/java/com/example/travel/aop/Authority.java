package com.example.travel.aop;

import java.lang.annotation.*;

/**
 * @author yijiyin
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
    // 默认验证
    AuthorityType authoritytype() default AuthorityType.NOCHECK;
}
