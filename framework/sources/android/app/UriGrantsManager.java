package android.app;

import android.app.IUriGrantsManager;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Singleton;

/* loaded from: classes.dex */
public class UriGrantsManager {
    private static final Singleton<IUriGrantsManager> IUriGrantsManagerSingleton = new Singleton<IUriGrantsManager>() { // from class: android.app.UriGrantsManager.1
        AnonymousClass1() {
        }

        @Override // android.util.Singleton
        public IUriGrantsManager create() {
            IBinder b = ServiceManager.getService(Context.URI_GRANTS_SERVICE);
            return IUriGrantsManager.Stub.asInterface(b);
        }
    };
    private final Context mContext;

    public UriGrantsManager(Context context, Handler handler) {
        this.mContext = context;
    }

    public static IUriGrantsManager getService() {
        return IUriGrantsManagerSingleton.get();
    }

    /* renamed from: android.app.UriGrantsManager$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 extends Singleton<IUriGrantsManager> {
        AnonymousClass1() {
        }

        @Override // android.util.Singleton
        public IUriGrantsManager create() {
            IBinder b = ServiceManager.getService(Context.URI_GRANTS_SERVICE);
            return IUriGrantsManager.Stub.asInterface(b);
        }
    }

    public void clearGrantedUriPermissions(String packageName) {
        try {
            getService().clearGrantedUriPermissions(packageName, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParceledListSlice<GrantedUriPermission> getGrantedUriPermissions(String packageName) {
        try {
            ParceledListSlice<GrantedUriPermission> castedList = getService().getGrantedUriPermissions(packageName, this.mContext.getUserId());
            return castedList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
