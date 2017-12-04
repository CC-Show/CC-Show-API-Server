package com.boxfox.core.store;

import com.boxfox.core.store.data.AssetStoreDAO;
import com.boxfox.support.vertx.router.RouteRegistration;
import io.vertx.ext.web.RoutingContext;

import static io.vertx.core.http.HttpMethod.*;

public class AssetStoreRouter {

    private AssetStoreDAO assetStoreDAO;

    public AssetStoreRouter() {
        this.assetStoreDAO = new AssetStoreDAO();
    }

    @RouteRegistration(uri = "/store/", method = GET)
    public void getAssetList(RoutingContext ctx, int page, int sum) {

    }
}
