package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface QSHost {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface Callback {
        void onTilesChanged();
    }

    static List getDefaultSpecs(Resources resources) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(resources.getString(R.string.quick_settings_tiles_default).split(",")));
        if (Build.IS_DEBUGGABLE) {
            arrayList.add("dbg:mem");
        }
        return arrayList;
    }

    void addCallback(Callback callback);

    void addTile(ComponentName componentName);

    void addTile(ComponentName componentName, boolean z);

    void changeTilesByUser(List list, List list2);

    QSTileView createTileView(Context context, QSTile qSTile, boolean z);

    ArrayList getBarTilesByType(int i, Context context);

    Context getContext();

    String getCustomTileNameFromSpec(String str);

    SecQQSTileHost getQQSTileHost();

    Collection getTiles();

    Context getUserContext();

    int getUserId();

    int indexOf(String str);

    boolean isAvailableCustomTile(String str);

    boolean isAvailableForSearch(String str, boolean z);

    boolean isBarTile(String str);

    boolean isBrightnessBarTile(String str);

    boolean isLargeBarTile(String str);

    boolean isUnsupportedTile(String str);

    void removeCallback(Callback callback);

    void removeTile(String str);

    void removeTileByUser(ComponentName componentName);

    void removeTiles(Collection collection);

    boolean shouldBeHiddenByKnox(String str);

    boolean shouldUnavailableByKnox(String str);

    default void sendTileStatusLog(int i, String str) {
    }
}
