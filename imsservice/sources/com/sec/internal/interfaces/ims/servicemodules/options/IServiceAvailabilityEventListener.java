package com.sec.internal.interfaces.ims.servicemodules.options;

import com.sec.ims.util.ImsUri;
import java.util.Date;

/* loaded from: classes.dex */
public interface IServiceAvailabilityEventListener {
    void onServiceAvailabilityUpdate(String str, ImsUri imsUri, Date date);
}
