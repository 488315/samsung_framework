package com.sec.internal.interfaces.ims.core;

import com.sec.internal.constants.ims.gls.LocationInfo;
import com.sec.internal.helper.os.ServiceStateWrapper;

/* loaded from: classes.dex */
public interface IGeolocationController extends ISequentialInitializable {
    LocationInfo getGeolocation();

    String getLastAccessedNetworkCountryIso(int i);

    boolean isCountryCodeLoaded(int i);

    boolean isLocationServiceEnabled();

    void notifyEpdgAvailable(int i, int i2);

    void notifyServiceStateChanged(int i, ServiceStateWrapper serviceStateWrapper);

    boolean startGeolocationUpdate(int i, boolean z);

    boolean startGeolocationUpdate(int i, boolean z, int i2);

    void stopGeolocationUpdate();

    void stopPeriodicLocationUpdate(int i);

    boolean updateGeolocationFromLastKnown(int i);
}
