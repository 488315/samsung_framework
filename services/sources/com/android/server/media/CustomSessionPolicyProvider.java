package com.android.server.media;

import android.content.Context;
import com.samsung.android.server.audio.AudioSettingsHelper;

/* loaded from: classes2.dex */
public class CustomSessionPolicyProvider extends MediaSessionPolicyProvider {
    private final AudioSettingsHelper mAudioSettingsHelper;

    public CustomSessionPolicyProvider(Context context) {
        super(context);
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
    }

    @Override // com.android.server.media.MediaSessionPolicyProvider
    public int getSessionPoliciesForApplication(int i, String str) {
        if (this.mAudioSettingsHelper.checkAppCategory(str, "media_button_deny")) {
            return 1;
        }
        return super.getSessionPoliciesForApplication(i, str);
    }
}
