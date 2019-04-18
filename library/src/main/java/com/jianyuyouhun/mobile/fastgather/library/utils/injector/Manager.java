package com.jianyuyouhun.mobile.fastgather.library.utils.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * manager注解
 * Created by wangyu on 2017/12/7.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Manager {
}
