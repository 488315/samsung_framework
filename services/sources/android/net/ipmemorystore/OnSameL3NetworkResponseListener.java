package android.net.ipmemorystore;

import android.net.ipmemorystore.IOnSameL3NetworkResponseListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface OnSameL3NetworkResponseListener {
    static IOnSameL3NetworkResponseListener toAIDL(OnSameL3NetworkResponseListener onSameL3NetworkResponseListener) {
        return new IOnSameL3NetworkResponseListener.Stub() { // from class: android.net.ipmemorystore.OnSameL3NetworkResponseListener.1
            @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
            public final String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }

            @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
            public final int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnSameL3NetworkResponseListener
            public final void onSameL3NetworkResponse(StatusParcelable statusParcelable, SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable) {
                OnSameL3NetworkResponseListener onSameL3NetworkResponseListener2 = OnSameL3NetworkResponseListener.this;
                if (onSameL3NetworkResponseListener2 != null) {
                    onSameL3NetworkResponseListener2.onSameL3NetworkResponse(new Status(statusParcelable), new SameL3NetworkResponse(sameL3NetworkResponseParcelable));
                }
            }
        };
    }

    void onSameL3NetworkResponse(Status status, SameL3NetworkResponse sameL3NetworkResponse);
}
