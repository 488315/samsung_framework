package com.android.systemui.plugins.qs;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@ProvidesInterface(version = 1)
/* loaded from: classes2.dex */
public interface DetailAdapter {
    public static final UiEventLogger.UiEventEnum INVALID = new DetailAdapter$$ExternalSyntheticLambda0();
    public static final int VERSION = 1;

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ int lambda$static$0() {
        return 0;
    }

    default UiEventLogger.UiEventEnum closeDetailEvent() {
        return INVALID;
    }

    View createDetailView(Context context, View view, ViewGroup viewGroup);

    default boolean disallowStartSettingsIntent() {
        return false;
    }

    default String getDetailAdapterSummary() {
        return null;
    }

    default int getDoneText() {
        return 0;
    }

    int getMetricsCategory();

    Intent getSettingsIntent();

    default int getSettingsText() {
        return 0;
    }

    CharSequence getTitle();

    default boolean getToggleEnabled() {
        return true;
    }

    Boolean getToggleState();

    default boolean hasHeader() {
        return true;
    }

    default UiEventLogger.UiEventEnum moreSettingsEvent() {
        return INVALID;
    }

    default boolean onDoneButtonClicked() {
        return false;
    }

    default UiEventLogger.UiEventEnum openDetailEvent() {
        return INVALID;
    }

    void setToggleState(boolean z);

    default boolean shouldAnimate() {
        return true;
    }

    default boolean shouldUseFullScreen() {
        return false;
    }

    default void dismissListPopupWindow() {
    }
}
