package com.sec.internal.constants.ims.servicemodules.im.params;

import android.net.Uri;

/* loaded from: classes.dex */
public class GroupChatInfoParams {
    private final String mOwnImsi;
    private final Uri mUri;

    public GroupChatInfoParams(Uri uri, String str) {
        this.mUri = uri;
        this.mOwnImsi = str;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String getOwnImsi() {
        return this.mOwnImsi;
    }
}
