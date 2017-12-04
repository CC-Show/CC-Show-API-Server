package com.boxfox.core.show.data;

import com.boxfox.support.data.AbstractDTO;
import io.vertx.core.json.JsonObject;

public class ShowDTO extends AbstractDTO {
    private int showId, idx, selectedSlide;
    private String uid, name, sizeUnit, positionUnit;

    public ShowDTO(int showId, String uid, int idx, int selectedSlide, String name, String sizeUnit, String positionUnit) {
        this.showId = showId;
        this.uid = uid;
        this.idx = idx;
        this.selectedSlide = selectedSlide;
        this.name = name;
        this.sizeUnit = sizeUnit;
        this.positionUnit = positionUnit;
    }

    public int getShowId() {
        return showId;
    }

    public int getIdx() {
        return idx;
    }

    public int getSelectedSlide() {
        return selectedSlide;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public String getPositionUnit() {
        return positionUnit;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("idx", idx);
        jsonObject.put("name", name);
        jsonObject.put("selectedSlide", selectedSlide);
        jsonObject.put("sizeUnit", sizeUnit);
        jsonObject.put("positionUnit", positionUnit);
        return jsonObject;
    }
}
