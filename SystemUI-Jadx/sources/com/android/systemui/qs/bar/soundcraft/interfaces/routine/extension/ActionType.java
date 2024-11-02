package com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public enum ActionType {
    SPATIAL_AUDIO { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.SPATIAL_AUDIO
        @Override // com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_spatial_audio");
        }
    },
    HEAD_TRACKING { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.HEAD_TRACKING
        @Override // com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_head_tracking");
        }
    },
    EQUALIZER { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.EQUALIZER
        @Override // com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_equalizer");
        }
    },
    VOICE_BOOST { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.VOICE_BOOST
        @Override // com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_boost_voice");
        }
    },
    VOLUME_NORMALIZATION { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType.VOLUME_NORMALIZATION
        @Override // com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType
        public final String getTag(String str) {
            return str.concat("_loudness_normalization");
        }
    };

    /* synthetic */ ActionType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String getTag(String str);
}
