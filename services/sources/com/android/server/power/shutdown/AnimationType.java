package com.android.server.power.shutdown;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
final class AnimationType {
    public static final /* synthetic */ AnimationType[] $VALUES;
    public static final AnimationType MAIN;
    public static final AnimationType MAIN_LOOP;
    public static final AnimationType SUB;
    public static final AnimationType SUB_LOOP;

    static {
        AnimationType animationType = new AnimationType("MAIN", 0);
        MAIN = animationType;
        AnimationType animationType2 = new AnimationType("SUB", 1);
        SUB = animationType2;
        AnimationType animationType3 = new AnimationType("MAIN_LOOP", 2);
        MAIN_LOOP = animationType3;
        AnimationType animationType4 = new AnimationType("SUB_LOOP", 3);
        SUB_LOOP = animationType4;
        $VALUES = new AnimationType[]{animationType, animationType2, animationType3, animationType4};
    }

    public static AnimationType valueOf(String str) {
        return (AnimationType) Enum.valueOf(AnimationType.class, str);
    }

    public static AnimationType[] values() {
        return (AnimationType[]) $VALUES.clone();
    }
}
