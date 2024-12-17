package com.samsung.android.hardware.secinputdev.taas;

import android.content.Context;

/* loaded from: classes.dex */
public interface SemInputTaasTestCase {
    public static final String TAAS_CASEA = "CASA";
    public static final String TAAS_CASEB = "CASB";

    void create(Context context, ExternalApi externalApi);

    void destroy();
}
