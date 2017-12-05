package com.boxfox.core.store;

import com.boxfox.core.store.data.AssetCodeDTO;
import com.boxfox.core.store.data.AssetDTO;
import com.boxfox.core.store.data.AssetStoreDAO;
import com.boxfox.core.store.data.SimpleAssetDTO;
import com.boxfox.support.util.RouterUtil;
import com.boxfox.support.vertx.router.RouteRegistration;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

import static io.vertx.core.http.HttpMethod.*;

public class AssetStoreRouter {

    private AssetStoreDAO assetStoreDAO;

    public AssetStoreRouter() {
        this.assetStoreDAO = new AssetStoreDAO();
    }

    @RouteRegistration(uri = "/store/", method = GET, description = "Asset 조회")
    public void getAssetList(RoutingContext ctx, int page, int sum) {
        SimpleAssetDTO[] assets = assetStoreDAO.getSimpleAssetList(page, sum);
        JsonArray arr = new JsonArray();
        for (SimpleAssetDTO asset : assets)
            arr.add(asset.toJsonObject());
        RouterUtil.writeJsonResponse(ctx.response(), arr).end();
    }

    @RouteRegistration(uri = "/store/asset/", method = DELETE, description = "Asset 삭제")
    public void deleteAsset(RoutingContext ctx, int asset) {
        String uid = (String) ctx.data().get("uid");
        boolean result = assetStoreDAO.deleteAsset(asset);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/store/asset/", method = POST, description = "Asset 생성")
    public void createAsset(RoutingContext ctx) {
        String uid = (String) ctx.data().get("uid");
        AssetDTO asset = assetStoreDAO.createAsset(uid);
        if (asset != null) {
            RouterUtil.writeJsonResponse(ctx.response(), asset.toJsonObject());
            ctx.response().setStatusCode(HttpResponseStatus.OK.code());
        } else {
            ctx.response().setStatusCode(HttpResponseStatus.EXPECTATION_FAILED.code());
        }
        ctx.response().end();
    }

    @RouteRegistration(uri = "/store/asset/", method = PUT, description = "Asset 정보 업데이트")
    public void updateAsset(RoutingContext ctx, int id, String name, String date, String content, String license, boolean openToStore) {
        String uid = (String) ctx.data().get("uid");
        boolean result = assetStoreDAO.updateAsset(id, name, date, content, license, openToStore);

        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/store/asset/", method = PATCH, description = "Asset 코드 업데이트")
    public void updateAssetCode(RoutingContext ctx, int id, String html, String css, String js) {
        String uid = (String) ctx.data().get("uid");
        boolean result = assetStoreDAO.updateAssetCode(id, html, css, js);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/store/asset/", method = GET, description = "Asset 정보 조회")
    public void getAsset(RoutingContext ctx, int id) {
        String uid = (String) ctx.data().get("uid");
        AssetDTO asset = assetStoreDAO.getAsset(id);
        if (asset != null) {
            RouterUtil.writeJsonResponse(ctx.response(), asset.toJsonObject())
                    .setStatusCode(HttpResponseStatus.OK.code());
        } else {
            ctx.response().setStatusCode(HttpResponseStatus.FAILED_DEPENDENCY.code());
        }
        ctx.response().end();
    }

    @RouteRegistration(uri = "/store/asset/code", method = GET, description = "Asset 코드 조회")
    public void getAssetCode(RoutingContext ctx, int id) {
        String uid = (String) ctx.data().get("uid");
        AssetCodeDTO asset = assetStoreDAO.getAssetCode(id);
        if (asset != null) {
            RouterUtil.writeJsonResponse(ctx.response(), asset.toJsonObject())
                    .setStatusCode(HttpResponseStatus.OK.code());
        } else {
            ctx.response().setStatusCode(HttpResponseStatus.FAILED_DEPENDENCY.code());
        }
        ctx.response().end();
    }
}
