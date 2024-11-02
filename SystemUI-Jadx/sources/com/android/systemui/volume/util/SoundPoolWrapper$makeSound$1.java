package com.android.systemui.volume.util;

import android.media.AudioAttributes;
import android.media.SoundPool;
import com.android.systemui.BasicRune;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SoundPoolWrapper$makeSound$1 implements Runnable {
    public final /* synthetic */ SoundPoolWrapper this$0;

    public SoundPoolWrapper$makeSound$1(SoundPoolWrapper soundPoolWrapper) {
        this.this$0 = soundPoolWrapper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SoundPoolWrapper soundPoolWrapper = this.this$0;
        SoundPool build = new SoundPool.Builder().setMaxStreams(4).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        SoundPoolWrapper soundPoolWrapper2 = this.this$0;
        if (BasicRune.VOLUME_HOME_IOT) {
            int length = SoundPoolWrapper.HOME_HUB_FILES.length;
            for (int i = 0; i < length; i++) {
                soundPoolWrapper2.soundIDs[i] = build.load(SoundPoolWrapper.HOME_HUB_FILES[i], 0);
            }
        } else if (BasicRune.SUPPORT_SOUND_THEME) {
            int length2 = SoundPoolWrapper.SOUND_THEME_FILES.length;
            for (int i2 = 0; i2 < length2; i2++) {
                soundPoolWrapper2.soundIDs[i2] = build.load(SoundPoolWrapper.SOUND_THEME_FILES[i2], 0);
            }
        } else {
            soundPoolWrapper2.soundID = build.load("system/media/audio/ui/TW_Volume_control.ogg", 0);
        }
        soundPoolWrapper.soundPool = build;
    }
}
