package com.example.travel.user.interceptor;

import java.lang.annotation.*;

/**
 * @author yijiyin
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
    // 默认验证
    AuthorityType value() default AuthorityType.NOCHECK;
}
