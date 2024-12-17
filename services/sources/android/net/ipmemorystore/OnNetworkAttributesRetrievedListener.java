package android.net.ipmemorystore;

import android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface OnNetworkAttributesRetrievedListener {
    static IOnNetworkAttributesRetrievedListener toAIDL(OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener) {
        return new IOnNetworkAttributesRetrievedListener.Stub() { // from class: android.net.ipmemorystore.OnNetworkAttributesRetrievedListener.1
            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public final String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }

            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public final int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public final void onNetworkAttributesRetrieved(StatusParcelable statusParcelable, String str, NetworkAttributesParcelable networkAttributesParcelable) {
                OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener2 = OnNetworkAttributesRetrievedListener.this;
                if (onNetworkAttributesRetrievedListener2 != null) {
                    onNetworkAttributesRetrievedListener2.onNetworkAttributesRetrieved(new Status(statusParcelable), str, networkAttributesParcelable == null ? null : new NetworkAttributes(networkAttributesParcelable));
                }
            }
        };
    }

    void onNetworkAttributesRetrieved(Status status, String str, NetworkAttributes networkAttributes);
}
