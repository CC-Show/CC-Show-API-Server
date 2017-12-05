package com.boxfox.core.store.data;

import com.boxfox.support.data.AbstractDTO;
import io.vertx.core.json.JsonObject;

public class SimpleAssetDTO extends AbstractDTO {
    private int id;
    private String name, date, email;


    public SimpleAssetDTO(int id, String name, String date, String email) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.email = email;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        object.put("id", id);
        object.put("name", name);
        object.put("date", date);
        object.put("email", email);
        return object;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }
}
