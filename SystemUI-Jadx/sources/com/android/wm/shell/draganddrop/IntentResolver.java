package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class IntentResolver extends BaseResolver {
    public IntentResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
    }

    @Override // com.android.wm.shell.draganddrop.Resolver
    public final Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra) {
        CharSequence label;
        if (clipData.getItemCount() > 0) {
            boolean z = false;
            Intent intent = clipData.getItemAt(0).getIntent();
            if (intent == null) {
                return Optional.empty();
            }
            ArrayList arrayList = new ArrayList();
            if (intent.getData() != null) {
                arrayList.add(intent.getData());
            }
            if (intent.getClipData() != null) {
                intent.getClipData().collectUris(arrayList);
            }
            if (!arrayList.isEmpty() && intent.hasWebURI()) {
                if (clipData.getCallingPackageName() != null && intent.getComponent() != null && clipData.getCallingPackageName().equals(intent.getComponent().getPackageName())) {
                    int callingUserId = clipData.getCallingUserId();
                    ClipDescription description = clipData.getDescription();
                    if (description != null && (label = description.getLabel()) != null && "terrace-image-or-link-drag-label".equals(label.toString())) {
                        z = true;
                    }
                    ArrayList arrayList2 = this.mTempList;
                    if (z) {
                        resolveActivitiesForSBrowser(intent, callingUserId, arrayList2, resultExtra);
                    } else {
                        resolveActivities(intent, callingUserId, arrayList2, resultExtra);
                    }
                    if (arrayList2.isEmpty()) {
                        return Optional.empty();
                    }
                    if (BaseResolver.DEBUG) {
                        Slog.d(this.TAG, "updateFromIntent: resolveList=" + arrayList2);
                    }
                    return Optional.of(new SingleIntentAppResult(intent, arrayList2, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, BaseResolver.calculateContentType(intent), true));
                }
                return Optional.empty();
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
}
