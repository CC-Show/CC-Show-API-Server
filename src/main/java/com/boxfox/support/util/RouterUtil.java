package com.boxfox.support.util;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class RouterUtil {

    public static HttpServerResponse putJsonHeader(HttpServerResponse response, JsonObject data) {
        return response.putHeader("content-type", "application/json; charset=utf-8")
                .write(Json.encodePrettily(data));
    }
}
