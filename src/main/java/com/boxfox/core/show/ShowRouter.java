package com.boxfox.core.show;

import com.boxfox.support.data.Database;
import com.boxfox.support.vertx.router.RouteRegistration;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowRouter {

    @RouteRegistration(uri = "/show/", method = HttpMethod.GET, description = "Show 조회")
    public void lookupShow(RoutingContext ctx) {
        String query = Database.getQueryFromResource("lookup.sql");

    }


    @RouteRegistration(uri = "/show/", method = HttpMethod.POST, description = "Show 생성")
    public void createShow(RoutingContext ctx) {
        String query = Database.getQueryFromResource("create.sql");
        try {
            ResultSet rs = Database.executeQuery(query, ctx.data().get("userKey"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @RouteRegistration(uri = "/show/", method = HttpMethod.PUT, description = "Show 업데이트")
    public void updateShow(RoutingContext ctx) {

    }

    @RouteRegistration(uri = "/show/", method = HttpMethod.DELETE, description = "Show 삭제")
    public void deleteShow(RoutingContext ctx, int showNum) {

    }

}
