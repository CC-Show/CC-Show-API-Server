package com.boxfox.core.show.data;

import com.boxfox.support.data.AbstractDTO;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class SlideDTO extends AbstractDTO {
    private String name, note;
    private int showid, idx, selectedAsset;
    private JsonArray assets;

    public SlideDTO(int showid, int idx, String name, String note, int selectedAsset, JsonArray assets) {
        this.name = name;
        this.note = note;
        this.showid = showid;
        this.idx = idx;
        this.selectedAsset = selectedAsset;
        this.assets = assets;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public int getShowid() {
        return showid;
    }

    public int getIdx() {
        return idx;
    }

    public int getSelectedAsset() {
        return selectedAsset;
    }

    public JsonArray getAssets() {
        return assets;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("idx", idx);
        jsonObject.put("name", name);
        jsonObject.put("note", note);
        jsonObject.put("selectedAsset", selectedAsset);
        jsonObject.put("assets", assets);
        return jsonObject;
    }
}
