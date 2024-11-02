package com.android.systemui.qs.external;

import android.service.quicksettings.Tile;
import org.json.JSONObject;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class CustomTileStatePersisterKt {
    public static final String getStringOrNull(JSONObject jSONObject, String str) {
        if (jSONObject.has(str)) {
            return jSONObject.getString(str);
        }
        return null;
    }

    public static final Tile readTileFromString(String str) {
        JSONObject jSONObject = new JSONObject(str);
        Tile tile = new Tile();
        tile.setState(jSONObject.getInt("state"));
        tile.setLabel(getStringOrNull(jSONObject, "label"));
        tile.setSubtitle(getStringOrNull(jSONObject, "subtitle"));
        tile.setContentDescription(getStringOrNull(jSONObject, "content_description"));
        tile.setStateDescription(getStringOrNull(jSONObject, "state_description"));
        return tile;
    }

    public static final String writeToString(Tile tile) {
        return new JSONObject().put("state", tile.getState()).put("label", tile.getLabel()).put("subtitle", tile.getSubtitle()).put("content_description", tile.getContentDescription()).put("state_description", tile.getStateDescription()).toString();
    }
}
