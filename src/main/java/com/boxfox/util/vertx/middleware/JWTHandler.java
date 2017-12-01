package com.boxfox.util.vertx.middleware;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface JWTHandler extends Handler<RoutingContext> {

    static JWTHandler create() {
        return new JWTHandlerImpl();
    }

}