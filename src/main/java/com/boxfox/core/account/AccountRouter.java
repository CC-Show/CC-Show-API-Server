package com.boxfox.core.account;

import com.boxfox.util.data.Database;
import com.boxfox.util.vertx.router.RouteRegistration;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRouter {

    @RouteRegistration(uri = "/account/login", method = HttpMethod.POST)
    public void login(RoutingContext ctx, String email, String password) {
        String loginQuery = Database.getQueryFromResource("login.sql");
        try {
            ResultSet rs = Database.executeQuery(loginQuery, email, password);
            if (rs.getInt(1) == 1) {
                ctx.response().setStatusCode(HttpResponseStatus.OK.code());
                ctx.response().write(Buffer.buffer(createJWT(email, password)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code());
        }
        ctx.response().end();
    }

    @RouteRegistration(uri = "/account/login", method = HttpMethod.POST)
    public void register(RoutingContext ctx, String email, String password, String nickname) {
        String registerQuery = Database.getQueryFromResource("register.sql");
        try {
            int result = Database.executeUpdate(registerQuery, email, password);
            if (result > 0) {
                ctx.response().setStatusCode(HttpResponseStatus.OK.code());
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.response().setStatusCode(HttpResponseStatus.PRECONDITION_FAILED.code());
        }
    }

    private String createJWT(String email, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        return jsonObject.toString();
    }
}
