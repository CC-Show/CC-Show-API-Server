package com.boxfox.core.store.data;

import com.boxfox.support.data.AbstractDTO;
import io.vertx.core.json.JsonObject;

public class AssetDTO extends AbstractDTO {
    private String name, date, content, license, uid;
    private int view, price, id;
    private boolean openToStore;

    public AssetDTO(int id, String name, String date, String content, String license, String uid, int view, int price, boolean openToStore) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.content = content;
        this.license = license;
        this.uid = uid;
        this.view = view;
        this.price = price;
        this.openToStore = openToStore;
    }

    @Override
    public JsonObject toJsonObject() {
        return null;
    }
}
