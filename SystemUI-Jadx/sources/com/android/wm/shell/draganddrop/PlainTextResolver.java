package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Slog;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class PlainTextResolver extends BaseResolver {
    public PlainTextResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
    }

    @Override // com.android.wm.shell.draganddrop.Resolver
    public final Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra) {
        if (clipData.getItemCount() == 0) {
            return Optional.empty();
        }
        CharSequence text = clipData.getItemAt(0).getText();
        boolean z = BaseResolver.DEBUG;
        String str = this.TAG;
        if (text == null) {
            if (z) {
                Slog.d(str, "null text.");
            }
            return Optional.empty();
        }
        String replaceAll = text.toString().replaceAll("\u0000", "");
        if (TextUtils.isEmpty(replaceAll)) {
            if (z) {
                Slog.d(str, "empty text.");
            }
            return Optional.empty();
        }
        Intent intent = new Intent();
        Intent type = intent.setAction("android.intent.action.SEND").setType("text/plain");
        if (!TextUtils.isEmpty(replaceAll) && replaceAll.length() >= 100000) {
            Slog.d(str, "Truncating oversized query (" + replaceAll.length() + ").");
            replaceAll = replaceAll.substring(0, 100000) + "…";
        }
        type.putExtra("android.intent.extra.TEXT", (CharSequence) replaceAll);
        if (z) {
            Slog.d(str, "No actions. set default=" + intent);
        }
        String calculateContentType = BaseResolver.calculateContentType(intent);
        int callingUserId = clipData.getCallingUserId();
        ArrayList arrayList = this.mTempList;
        resolveActivities(intent, callingUserId, arrayList, resultExtra);
        if (arrayList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new SingleIntentAppResult(intent, arrayList, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, calculateContentType));
    }
}
