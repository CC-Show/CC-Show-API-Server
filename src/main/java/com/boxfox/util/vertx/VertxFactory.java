package com.boxfox.util.vertx;

import com.boxfox.util.vertx.middleware.CORSHandler;
import com.boxfox.util.vertx.middleware.JWTHandler;
import io.vertx.core.Vertx;

public class VertxFactory {

    public static Vertx createGenericVertx() {
        Vertx vertx = Vertx.factory.vertx();
        RouteRegister routeRegister = RouteRegister.routing(vertx);
        routeRegister.route(CORSHandler.create());
        routeRegister.route(JWTHandler.create());
        return vertx;
    }
}
