package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Slog;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class UriResolver extends BaseResolver {
    public boolean mIsClipDataFromSBrowser;
    public final ExecutableAppHolder.MimeTypeBlockList mMimeTypeBlockList;

    public UriResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
        this.mMimeTypeBlockList = new ExecutableAppHolder.MimeTypeBlockList(context);
    }

    @Override // com.android.wm.shell.draganddrop.Resolver
    public final Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra) {
        CharSequence label;
        Uri uri;
        CharSequence text;
        int itemCount = clipData.getItemCount();
        boolean z = false;
        boolean z2 = BaseResolver.DEBUG;
        String str = this.TAG;
        Uri uri2 = null;
        if (itemCount > 0) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            ArrayList arrayList = new ArrayList();
            if (itemAt.getUri() != null) {
                arrayList.add(itemAt.getUri());
            }
            Intent intent = itemAt.getIntent();
            if (intent != null) {
                if (intent.getData() != null) {
                    arrayList.add(intent.getData());
                }
                if (intent.getClipData() != null) {
                    intent.getClipData().collectUris(arrayList);
                }
            }
            if (arrayList.isEmpty()) {
                uri = null;
            } else {
                uri = (Uri) arrayList.get(0);
            }
            if (uri == null && (text = itemAt.getText()) != null) {
                try {
                    uri = Uri.parse(URI.create(text.toString()).toString());
                    if (uri.getScheme() == null) {
                        uri = null;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            if (uri != null) {
                if (z2) {
                    Slog.d(str, "extractUriFromClipData: found uri=" + uri);
                }
                uri2 = uri;
            }
        }
        if (uri2 == null) {
            if (z2) {
                Slog.d(str, "There is no uri.");
            }
            return Optional.empty();
        }
        ClipDescription description = clipData.getDescription();
        if (description != null && (label = description.getLabel()) != null && "terrace-image-or-link-drag-label".equals(label.toString())) {
            z = true;
        }
        this.mIsClipDataFromSBrowser = z;
        Intent intent2 = new Intent();
        intent2.setAction("android.intent.action.VIEW");
        if ("content".equals(uri2.getScheme())) {
            intent2.setDataAndType(uri2, this.mContext.getContentResolver().getType(uri2));
            intent2.addFlags(i);
            intent2.addFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED);
        } else {
            intent2.setData(uri2);
        }
        if (intent2.hasWebURI()) {
            intent2.addCategory("android.intent.category.BROWSABLE");
        }
        if (this.mIsClipDataFromSBrowser) {
            intent2.putExtra("terrace-image-or-link-drag-label", true);
        }
        String calculateContentType = BaseResolver.calculateContentType(intent2);
        if (z2) {
            Slog.d(str, "resolveType=" + calculateContentType);
        }
        if (this.mMimeTypeBlockList.mBlockList.contains(calculateContentType)) {
            if (z2) {
                Slog.d(str, "type blocked");
            }
            return Optional.empty();
        }
        int callingUserId = clipData.getCallingUserId();
        boolean z3 = this.mIsClipDataFromSBrowser;
        ArrayList arrayList2 = this.mTempList;
        if (z3) {
            resolveActivitiesForSBrowser(intent2, callingUserId, arrayList2, resultExtra);
        } else {
            resolveActivities(intent2, callingUserId, arrayList2, resultExtra);
        }
        if (arrayList2.isEmpty()) {
            return Optional.empty();
        }
        if (z2) {
            Slog.d(str, "resolveList=" + arrayList2);
        }
        return Optional.of(new SingleIntentAppResult(intent2, arrayList2, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, calculateContentType, true));
    }
}
