package android.webkit;

import android.annotation.SystemApi;
import android.graphics.Bitmap;

/* loaded from: classes4.dex */
public abstract class WebHistoryItem implements Cloneable {
    /* renamed from: clone */
    public abstract WebHistoryItem m5868clone();

    public abstract Bitmap getFavicon();

    @SystemApi
    @Deprecated
    public abstract int getId();

    public abstract String getOriginalUrl();

    public abstract String getTitle();

    public abstract String getUrl();
}
