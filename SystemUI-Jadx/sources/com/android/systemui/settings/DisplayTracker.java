package com.android.systemui.settings;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface DisplayTracker {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface Callback {
        default void onDisplayAdded(int i) {
        }

        default void onDisplayChanged(int i) {
        }

        default void onDisplayRemoved(int i) {
        }
    }
}
