package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzflv {
    public static final int[] zzpvy = new int[0];
    public static final long[] zzpvz = new long[0];
    public static final float[] zzpwa = new float[0];
    public static final double[] zzpwb = new double[0];
    public static final boolean[] zzpwc = new boolean[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final byte[][] zzpwd = new byte[0][];
    public static final byte[] zzpwe = new byte[0];

    public static final int zzb(zzflj zzfljVar, int i) throws IOException {
        int position = zzfljVar.getPosition();
        zzfljVar.zzlg(i);
        int i2 = 1;
        while (zzfljVar.zzcxx() == i) {
            zzfljVar.zzlg(i);
            i2++;
        }
        zzfljVar.zzap(position, i);
        return i2;
    }
}
