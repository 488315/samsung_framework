package com.samsung.android.media;

import android.media.AudioSystem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes5.dex */
public class SemAudioSystem {
    public static final int FORCE_EARPIECE = 10001;
    public static final Set<Integer> MULTI_SOUND_PRIMARY_DEVICE_SET;
    private static final int OFFSET_FOR_SAMSUNG_AUDIO = 10000;
    public static final int STREAM_MULTI_SOUND = 10003;

    static {
        HashSet hashSet = new HashSet();
        MULTI_SOUND_PRIMARY_DEVICE_SET = hashSet;
        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(16384);
        hashSet.add(67108864);
    }

    public static String getEffectParameters(String keys) {
        return AudioSystem.getParameters("g_effect_param_key;" + keys);
    }

    public static int setEffectParameters(String keyValuePairs) {
        return AudioSystem.setParameters("g_effect_param_key;" + keyValuePairs);
    }

    public static String getPolicyParameters(String keys) {
        return AudioSystem.getParameters("audioParam;" + keys);
    }

    public static int setPolicyParameters(String keyValuePairs) {
        return AudioSystem.setParameters("audioParam;" + keyValuePairs);
    }

    public static int makeDeviceBit(Set<Integer> deviceSet) {
        int devices = 0;
        Iterator<Integer> it = deviceSet.iterator();
        while (it.hasNext()) {
            int device = it.next().intValue();
            devices |= device;
        }
        return devices;
    }
}
