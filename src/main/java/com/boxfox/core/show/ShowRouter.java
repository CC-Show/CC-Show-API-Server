package com.boxfox.core.show;

import com.boxfox.core.show.data.ShowDAO;
import com.boxfox.core.show.data.ShowDTO;
import com.boxfox.core.show.data.SlideDTO;
import com.boxfox.support.util.RouterUtil;
import com.boxfox.support.vertx.router.Param;
import com.boxfox.support.vertx.router.RouteRegistration;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import static com.boxfox.support.vertx.router.Param.*;

public class ShowRouter {
    private ShowDAO showDAO;

    public ShowRouter() {
        this.showDAO = new ShowDAO();
    }

    @RouteRegistration(uri = "/show/", method = HttpMethod.POST, description = "Show 생성")
    public void createShow(RoutingContext ctx, @Param String showName) {
        String uid = (String) ctx.data().get("userKey");
        boolean result = showDAO.create(uid, showName);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.EXPECTATION_FAILED.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = HttpMethod.GET, description = "Show 조회", paramDefaultType = TYPE_PATH)
    public void lookupShow(RoutingContext ctx, @Param String user, @Param int show) {
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
    public void updateShow(RoutingContext ctx, @Param(type = TYPE_PATH) String user, @Param(type = TYPE_PATH) int show, @Param String name, @Param String sizeUnit, @Param String positionUnit) {
        int showId = showDAO.getShowId(user, show);
        boolean result = showDAO.update(showId, name, sizeUnit, positionUnit);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = HttpMethod.DELETE, description = "Show 삭제", paramDefaultType = TYPE_PATH)
    public void deleteShow(RoutingContext ctx, @Param String user, @Param int show) {
        int showId = showDAO.getShowId(user, show);
        boolean result = showDAO.delete(showId);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/slide/:user/:show", method = HttpMethod.GET, paramDefaultType = TYPE_PATH)
    public void getSlide(RoutingContext ctx, @Param String user, @Param int show, @Param(type = TYPE_QUERY) int slide) {
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
