package com.boxfox.core;

import com.boxfox.util.vertx.middleware.CORSHandler;
import com.boxfox.util.vertx.middleware.JWTHandler;
import com.boxfox.util.vertx.router.RouteRegister;
import com.boxfox.util.vertx.router.RouterPackageLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;

public class MainVerticle extends AbstractVerticle {

    private HttpServer server;

    @Override
    public void start(Future<Void> future) {
        RouteRegister routeRegister = RouteRegister.routing(vertx);
        routeRegister.route(CORSHandler.create());
        routeRegister.route(JWTHandler.create());
        routeRegister.route(RouterPackageLoader.getRoutingPackages());
        server = vertx.createHttpServer().requestHandler(routeRegister.getRouter()::accept).listen(8999);
        future.complete();
    }

    @Override
    public void stop(Future<Void> stopFuture) {
        server.close();
        stopFuture.complete();
    }
}
