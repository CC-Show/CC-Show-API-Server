package com.boxfox.support.data;

import io.vertx.core.json.JsonObject;

public abstract class AbstractDTO {
    public abstract JsonObject toJsonObject();
}
