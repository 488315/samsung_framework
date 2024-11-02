package androidx.collection.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class LruHashMap {
    public final LinkedHashMap map;

    public LruHashMap() {
        this(0, 0.0f, 3, null);
    }

    public LruHashMap(int i, float f) {
        this.map = new LinkedHashMap(i, f, true);
    }

    public /* synthetic */ LruHashMap(int i, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i, (i2 & 2) != 0 ? 0.75f : f);
    }

    public LruHashMap(LruHashMap lruHashMap) {
        this(0, 0.0f, 3, null);
        for (Map.Entry entry : lruHashMap.map.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue());
        }
    }
}
