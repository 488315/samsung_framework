package com.android.systemui.screenshot;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.screenshot.ScrollCaptureClient;
import java.util.concurrent.Executor;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ScrollCaptureController {
    public final Executor mBgExecutor;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class LongScreenshot {
        public final ImageTileSet mImageTileSet;

        public LongScreenshot(ScrollCaptureClient.Session session, ImageTileSet imageTileSet) {
            this.mImageTileSet = imageTileSet;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LongScreenshot{w=");
            ImageTileSet imageTileSet = this.mImageTileSet;
            sb.append(imageTileSet.getWidth());
            sb.append(", h=");
            sb.append(imageTileSet.getHeight());
            sb.append("}");
            return sb.toString();
        }
    }

    public ScrollCaptureController(Context context, Executor executor, ScrollCaptureClient scrollCaptureClient, ImageTileSet imageTileSet, UiEventLogger uiEventLogger) {
        this.mBgExecutor = executor;
    }

    public float getTargetTopSizeRatio() {
        return 0.4f;
    }
}
