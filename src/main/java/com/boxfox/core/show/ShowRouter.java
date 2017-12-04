package com.boxfox.core.show;

import com.boxfox.core.show.data.ShowDAO;
import com.boxfox.core.show.data.ShowDTO;
import com.boxfox.core.show.data.SlideDTO;
import com.boxfox.support.util.RouterUtil;
import com.boxfox.support.vertx.router.RouteRegistration;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;

import static io.vertx.core.http.HttpMethod.*;

public class ShowRouter {
    private ShowDAO showDAO;

    public ShowRouter() {
        this.showDAO = new ShowDAO();
    }

    @RouteRegistration(uri = "/show/", method = POST, description = "Show 생성")
    public void createShow(RoutingContext ctx, String showName) {
        String uid = (String) ctx.data().get("userKey");
        boolean result = showDAO.create(uid, showName);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.EXPECTATION_FAILED.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = GET, description = "Show 조회")
    public void lookupShow(RoutingContext ctx, String user, Integer show) {
        int showId = showDAO.getShowId(user, show);
        ShowDTO result = showDAO.lookup(showId);
        if (result != null) {
            ctx.response().setStatusCode(HttpResponseStatus.OK.code());
            RouterUtil.writeJsonResponse(ctx.response(), result.toJsonObject());
        } else {
            ctx.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code());
        }
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = PUT, description = "Show 정보 업데이트")
    public void updateShow(RoutingContext ctx, String user, Integer show, String name, String sizeUnit, String positionUnit) {
        int showId = showDAO.getShowId(user, show);
        boolean result = showDAO.update(showId, name, sizeUnit, positionUnit);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/show/:user/:show", method = DELETE, description = "Show 삭제")
    public void deleteShow(RoutingContext ctx, String user, Integer show) {
        int showId = showDAO.getShowId(user, show);
        boolean result = showDAO.delete(showId);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/slide/:user/:show", method = POST, description = "Slide 생성")
    public void createSlide(RoutingContext ctx, String user, Integer show, String name) {
        int showId = showDAO.getShowId(user, show);
        ctx.response().setStatusCode(HttpResponseStatus.EXPECTATION_FAILED.code());
        if (showId != -1) {
            int createdId = showDAO.createSlide(showId, name);
            SlideDTO slide = showDAO.slide(showId, createdId);
            if (slide != null) {
                ctx.response().setStatusCode(HttpResponseStatus.OK.code());
                RouterUtil.writeJsonResponse(ctx.response(), slide.toJsonObject());
            }
        }
        ctx.response().end();
    }

    @RouteRegistration(uri = "/slide/:user/:show", method = DELETE, description = "Show 삭제")
    public void deleteSlide(RoutingContext ctx, String user, Integer show, Integer slide) {
        int showId = showDAO.getShowId(user, show);
        boolean result = showDAO.deleteSlide(showId, slide);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/slide/:user/:show", method = GET, description = "Slide 조회")
    public void getSlide(RoutingContext ctx, String user, Integer show, Integer slide) {
        int showId = showDAO.getShowId(user, show);
        if (showId == -1) {
            ctx.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code());
        } else if (slide == null) {
            SlideDTO[] result = showDAO.slides(showId);
            if (result != null) {
                JsonArray arr = new JsonArray();
                Arrays.stream(result).forEach(slideDTO -> arr.add(slideDTO.toJsonObject()));
                ctx.response().setStatusCode(HttpResponseStatus.OK.code());
                RouterUtil.writeJsonResponse(ctx.response(), arr);
            } else {
                ctx.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code());
            }
        } else {
            SlideDTO result = showDAO.slide(showId, slide);
            if (result != null) {
                ctx.response().setStatusCode(HttpResponseStatus.OK.code());
                RouterUtil.writeJsonResponse(ctx.response(), result.toJsonObject());
            } else {
                ctx.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code());
            }
        }
        ctx.response().end();
    }

    @RouteRegistration(uri = "/slide/:user/:show", method = PUT, description = "slide 정보 update")
    public void updateSlide(RoutingContext ctx, String user, Integer show, Integer slide, String type, String data) {
        int showId = showDAO.getShowId(user, show);
        boolean result = false;
        switch (type.toLowerCase()) {
            case "name":
                result = showDAO.updateSlideName(showId, slide, data);
                break;
            case "note":
                result = showDAO.updateSlideNote(showId, slide, data);
                break;
            case "assets":
                result = showDAO.updateSlideAssets(showId, slide, data);
        }
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.EXPECTATION_FAILED.code());
        ctx.response().end();
    }
}
