package com.boxfox.util.vertx.middleware;

import com.boxfox.util.secure.AES256;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class JWTHandlerImpl implements JWTHandler {

    @Override
    public void handle(RoutingContext ctx) {
        JsonObject jwt = new JsonObject(AES256.decrypt(ctx.getCookie("auth-token").getValue()));
        ctx.data().put("id", jwt.getString("id"));
        ctx.data().put("uid", jwt.getString("uid"));
    }

}
