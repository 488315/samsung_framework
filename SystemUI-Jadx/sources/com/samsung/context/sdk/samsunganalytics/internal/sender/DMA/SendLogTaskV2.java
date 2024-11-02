package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class SendLogTaskV2 implements AsyncTaskClient {
    public final int Type;
    public final ContentValues cv;
    public final Context mContext;
    public final Uri commonUri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/common");
    public final Uri logUri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
    public Uri returnUri = null;

    public SendLogTaskV2(Context context, int i, ContentValues contentValues) {
        this.mContext = context;
        this.Type = i;
        this.cv = contentValues;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        try {
            Uri uri = this.returnUri;
            if (uri != null) {
                int parseInt = Integer.parseInt(uri.getLastPathSegment());
                Debug.LogD("SendLog Result = " + parseInt);
                boolean z = true;
                if (this.Type == 1) {
                    if (parseInt != 0) {
                        z = false;
                    }
                    Preferences.getPreferences(this.mContext).edit().putBoolean("sendCommonSuccess", z).apply();
                    Debug.LogD("Save Result = " + z);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        try {
            int i = this.Type;
            ContentValues contentValues = this.cv;
            Context context = this.mContext;
            if (i == 1) {
                this.returnUri = context.getContentResolver().insert(this.commonUri, contentValues);
            } else if (i == 2) {
                this.returnUri = context.getContentResolver().insert(this.logUri, contentValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
