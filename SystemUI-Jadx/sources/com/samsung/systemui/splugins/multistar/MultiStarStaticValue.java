package com.samsung.systemui.splugins.multistar;

import com.samsung.android.multiwindow.MultiWindowCoreState;
import java.util.HashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class MultiStarStaticValue {
    public static final int ACTIVITY_TYPE_ASSISTANT = 4;
    public static final int ACTIVITY_TYPE_HOME = 2;
    public static final int ACTIVITY_TYPE_RECENTS = 3;
    public static final int ACTIVITY_TYPE_STANDARD = 1;
    public static final int ACTIVITY_TYPE_UNDEFINED = 0;
    public static final int DOCKED_BOTTOM = 4;
    public static final int DOCKED_INVALID = -1;
    public static final int DOCKED_LEFT = 1;
    public static final int DOCKED_RIGHT = 3;
    public static final int DOCKED_TOP = 2;
    public static final int PRIVATE_FLAG_NO_MOVE_ANIMATION = 64;
    public static final int SYSTEM_FLAG_SHOW_FOR_ALL_USERS = 16;
    public static final int WINDOWING_MODE_FREEFORM = 5;
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    public static final int WINDOWING_MODE_PINNED = 2;
    public static final int WINDOWING_MODE_UNDEFINED = 0;
    public static HashMap<String, Object> sHashMap = new HashMap<>();

    public static boolean isMultiWindowEnabled() {
        return MultiWindowCoreState.MW_ENABLED;
    }
}
