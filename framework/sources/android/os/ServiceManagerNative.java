package android.os;

/* loaded from: classes3.dex */
public final class ServiceManagerNative {
    private ServiceManagerNative() {
    }

    public static IServiceManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        return new ServiceManagerProxy(obj);
    }
}
