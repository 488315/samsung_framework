package com.android.systemui.wallpaper.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.google.gson.Gson;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class IntelligentCropHelper {
    public static Rect getNearestCropHint(Point point, ArrayList arrayList) {
        if (point == null) {
            return null;
        }
        int i = point.x;
        int i2 = point.y;
        float f = i2 / i;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        float f2 = 10000.0f;
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            Rect rect = (Rect) arrayList.get(i4);
            float abs = Math.abs(f - (rect.height() / rect.width()));
            if (f2 > abs) {
                i3 = i4;
                f2 = abs;
            }
        }
        Rect rect2 = (Rect) arrayList.get(i3);
        StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m("selectProperCropHint: baseW = ", i, ", baseH = ", i2, ", ");
        m.append(rect2);
        Log.i("IntelligentCropHelper", m.toString());
        return rect2;
    }

    public static ArrayList parseCropHints(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = (ArrayList) new Gson().fromJson(str, new TypeToken(C$Gson$Types.newParameterizedTypeWithOwner(ArrayList.class, Rect.class)).type);
        Log.i("IntelligentCropHelper", "getIntelligentCropHints : cropHints = " + arrayList);
        return arrayList;
    }
}
