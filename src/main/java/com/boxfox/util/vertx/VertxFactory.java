package com.boxfox.util.vertx;

import io.vertx.core.Vertx;

public class VertxFactory {

    public static Vertx createGenericVertx() {
        Vertx vertx = Vertx.factory.vertx();
        RouteRegister routeRegister = RouteRegister.routing(vertx);
        routeRegister.getRouter();
        return vertx;
    }
}
