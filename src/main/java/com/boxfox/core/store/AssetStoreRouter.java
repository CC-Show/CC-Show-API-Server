package com.boxfox.core.store;

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

    @RouteRegistration(uri = "/store/", method = DELETE, description = "Asset 삭제")
    public void deleteAsset(RoutingContext ctx, int asset) {
        String uid = (String) ctx.data().get("uid");
        boolean result = assetStoreDAO.deleteAsset(asset);
        ctx.response().setStatusCode(result ? HttpResponseStatus.OK.code() : HttpResponseStatus.FAILED_DEPENDENCY.code());
        ctx.response().end();
    }

    @RouteRegistration(uri = "/store/", method = POST, description = "Asset 생성")
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
}
