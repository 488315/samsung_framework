package com.android.systemui.volume.util;

import android.content.Context;
import android.media.AudioManager;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class AudioManagerWrapper {
    public final AudioManager am;

    public AudioManagerWrapper(Context context) {
        SystemServiceExtension.INSTANCE.getClass();
        this.am = (AudioManager) context.getSystemService(AudioManager.class);
    }
}