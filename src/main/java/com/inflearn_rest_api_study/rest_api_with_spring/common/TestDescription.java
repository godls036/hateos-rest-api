package com.inflearn_rest_api_study.rest_api_with_spring.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.SOURCE)
public @interface TestDescription {
    // INFO 애노테이션 사용안함
    String value();
}
