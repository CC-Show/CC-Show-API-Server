package com.boxfox.core.store.data;

import com.boxfox.support.data.AbstractDTO;
import io.vertx.core.json.JsonObject;

public class AssetDTO extends AbstractDTO {
    private String name, date, content, license, email;
    private int view, price, id;
    private boolean openToStore;
    private AssetCodeDTO codeDTO;

    public AssetDTO(int id, String name, String date, String content, String license, String email, int view, int price, boolean openToStore, String html, String css, String js) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.content = content;
        this.license = license;
        this.email = email;
        this.view = view;
        this.price = price;
        this.openToStore = openToStore;
        this.codeDTO = new AssetCodeDTO(html, css, js);
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        object.put("id", id);
        object.put("name", name);
        object.put("date", date);
        object.put("content", content);
        object.put("license", license);
        object.put("email", email);
        object.put("view", view);
        object.put("price", price);
        object.put("openToStore", openToStore);
        object.put("html", codeDTO.getHtml());
        object.put("css", codeDTO.getCss());
        object.put("js", codeDTO.getJs());
        return object;
    }
}
