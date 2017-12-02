package com.boxfox.util.vertx.router;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Param {
    String TYPE_QUERY = "TYPE_QUERY";
    String TYPE_PATH = "TYPE_PATH";
    String TYPE_BODY = "TYPE_BODY";

    String type() default TYPE_QUERY;

}