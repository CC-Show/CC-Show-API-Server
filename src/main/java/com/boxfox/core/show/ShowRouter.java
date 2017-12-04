package com.boxfox.core.show;

import com.boxfox.core.show.data.ShowDAO;
import com.boxfox.core.show.data.ShowDTO;
import com.boxfox.core.show.data.SlideDTO;
import com.boxfox.support.util.RouterUtil;
import com.boxfox.support.vertx.router.RouteRegistration;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public class ShowRouter {
    private ShowDAO showDAO;

    public ShowRouter() {
        this.showDAO = new ShowDAO();
    }

    @RouteRegistration(uri = "/show/", method = HttpMethod.POST, description = "Show 생성")
    public void createShow(RoutingContext ctx, String showName) {
        String uid = (String) ctx.data().get("userKey");
        boolean result = showDAO.create(uid, showName);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.EXPECTATION_FAILED.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = HttpMethod.GET, description = "Show 조회")
    public void lookupShow(RoutingContext ctx, String user, int show) {
        int showId = showDAO.getShowId(user, show);
        ShowDTO result = showDAO.lookup(showId);
        if (result != null) {
            ctx.response().setStatusCode(HttpResponseStatus.OK.code());
            RouterUtil.putJsonHeader(ctx.response(), result.toJsonObject());
        } else {
            ctx.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code());
        }
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = HttpMethod.PUT, description = "Show 정보 업데이트")
    public void updateShow(RoutingContext ctx, String user, int show, String name, String sizeUnit, String positionUnit) {
        int showId = showDAO.getShowId(user, show);
        boolean result = showDAO.update(showId, name, sizeUnit, positionUnit);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = HttpMethod.DELETE, description = "Show 삭제")
    public void deleteShow(RoutingContext ctx, String user,int show) {
        int showId = showDAO.getShowId(user, show);
        boolean result = showDAO.delete(showId);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/slide/:user/:show", method = HttpMethod.GET)
    public void getSlide(RoutingContext ctx, String user, int show, int slide) {
        int showId = showDAO.getShowId(user, show);
        SlideDTO result = showDAO.slide(showId, slide);
        if (result != null) {
            ctx.response().setStatusCode(HttpResponseStatus.OK.code());
            RouterUtil.putJsonHeader(ctx.response(), result.toJsonObject());
        } else {
            ctx.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code());
        }
        ctx.response().end();
    }
}
