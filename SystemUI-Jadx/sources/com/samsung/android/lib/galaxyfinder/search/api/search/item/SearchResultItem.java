package com.samsung.android.lib.galaxyfinder.search.api.search.item;

import android.net.Uri;
import android.text.TextUtils;
import com.samsung.android.lib.galaxyfinder.search.api.payload.IntentResultItemPayload;
import com.samsung.android.lib.galaxyfinder.search.api.payload.ResultItemPayload;
import com.samsung.android.lib.galaxyfinder.search.util.SearchLog;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class SearchResultItem {
    public final List actions = new ArrayList();
    public final Uri icon;
    public final String itemKey;
    public final IntentResultItemPayload payload;
    public final String text;
    public final String text2;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public final class LabeledPayload {
        public final String label;
        public final ResultItemPayload payload;

        private LabeledPayload(String str, ResultItemPayload resultItemPayload) {
            this.label = str;
            this.payload = resultItemPayload;
        }
    }

    public SearchResultItem(String str, Uri uri, String str2, String str3, IntentResultItemPayload intentResultItemPayload) {
        if (!TextUtils.isEmpty(str)) {
            this.itemKey = str;
            this.text = str2;
            this.text2 = str3;
            this.icon = uri;
            this.payload = intentResultItemPayload;
            if (intentResultItemPayload != null) {
                return;
            } else {
                throw new IllegalArgumentException("view payload must be not null");
            }
        }
        throw new IllegalArgumentException("itemKey must be not null or empty");
    }

    public final String getActionLabel(int i) {
        try {
            return ((LabeledPayload) ((ArrayList) this.actions).get(i)).label;
        } catch (Exception e) {
            SearchLog.d("SearchResultItem", "fail to get action label: " + e);
            return null;
        }
    }

    public final String getActionPayloadStr(int i) {
        try {
            return ((IntentResultItemPayload) ((LabeledPayload) ((ArrayList) this.actions).get(i)).payload).getStringFromPayload();
        } catch (Exception e) {
            SearchLog.d("SearchResultItem", "fail to get action payload: " + e);
            return null;
        }
    }

    public abstract String getGroup();
}
