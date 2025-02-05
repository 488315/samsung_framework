package com.sec.internal.ims.servicemodules.tapi.service.broadcaster;

import android.os.UserHandle;
import com.gsma.services.rcs.contact.ContactId;
import com.gsma.services.rcs.sharing.geoloc.GeolocSharing;
import java.util.List;

/* loaded from: classes.dex */
public interface IGeolocSharingEventBroadcaster {
    void broadcastDeleted(ContactId contactId, List<String> list);

    void broadcastGeolocSharingInvitation(String str, UserHandle userHandle);

    void broadcastGeolocSharingStateChanged(ContactId contactId, String str, GeolocSharing.State state, GeolocSharing.ReasonCode reasonCode);

    void broadcastGeolocSharingprogress(ContactId contactId, String str, long j, long j2);
}
