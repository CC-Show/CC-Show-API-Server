package com.boxfox.core.store.data;

import com.boxfox.support.data.AbstractDTO;
import io.vertx.core.json.JsonObject;

public class AssetCodeDTO extends AbstractDTO {
    private String html, css, js;

    public AssetCodeDTO(String html, String css, String js) {
        this.html = html;
        this.css = css;
        this.js = js;
    }

    public String getHtml() {
        return html;
    }

    public String getCss() {
        return css;
    }

    public String getJs() {
        return js;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        object.put("html", html);
        object.put("css", css);
        object.put("js", js);
        return object;
    }
}
