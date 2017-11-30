package com.boxfox.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Date;

public class MainVerticle extends AbstractVerticle {

    private HttpServer server;

    @Override
    public void start(Future<Void> future) {
        /*scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            scriptEngine.eval(vertx.fileSystem().readFileBlocking("static/js/nashorn-polyfill.js").toString());
        } catch (ScriptException ex) {
            future.fail(ex);
            return;
        }
        vertx.fileSystem().readFile("static/js/app.js", fileResult -> {
            try {
                scriptEngine.eval(fileResult.result().toString());
                startServer(future);
            } catch (ScriptException ex) {
                future.fail(ex);
            }
        });*/
    }

    @Override
    public void stop(Future<Void> stopFuture) {
        server.close();
        stopFuture.complete();
    }

    void startServer(Future<Void> future) {
        Router router = Router.router(vertx);

        router.get("/web/*").handler(this::serverSideRenderer);

        server = vertx.createHttpServer().requestHandler(router::accept).listen(8999);
        future.complete();
    }

    void serverSideRenderer(RoutingContext context) {
    }
}
