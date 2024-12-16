package android.content.om;

import android.annotation.SystemApi;
import android.compat.Compatibility;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.android.internal.content.om.OverlayManagerImpl;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public class OverlayManager {
    public static final long SELF_TARGETING_OVERLAY = 205919743;
    private static final long THROW_SECURITY_EXCEPTIONS = 147340954;
    private final Context mContext;
    private final OverlayManagerImpl mOverlayManagerImpl;
    private final IOverlayManager mService;

    public OverlayManager(Context context, IOverlayManager service) {
        this.mContext = context;
        this.mService = service;
        this.mOverlayManagerImpl = new OverlayManagerImpl(context);
    }

    public OverlayManager(Context context) {
        this(context, IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")));
    }

    @SystemApi
    public void setEnabledExclusiveInCategory(String packageName, UserHandle user) throws SecurityException, IllegalStateException {
        try {
            if (!this.mService.setEnabledExclusiveInCategory(packageName, user.getIdentifier())) {
                throw new IllegalStateException("setEnabledExclusiveInCategory failed");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            rethrowSecurityException(e2);
        }
    }

    @SystemApi
    public void setEnabled(String packageName, boolean enable, UserHandle user) throws SecurityException, IllegalStateException {
        try {
            if (!this.mService.setEnabled(packageName, enable, user.getIdentifier())) {
                throw new IllegalStateException("setEnabled failed");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            rethrowSecurityException(e2);
        }
    }

    @SystemApi
    public OverlayInfo getOverlayInfo(String packageName, UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfo(packageName, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public OverlayInfo getOverlayInfo(OverlayIdentifier overlay, UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfoByIdentifier(overlay, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<OverlayInfo> getOverlayInfosForTarget(String targetPackageName, UserHandle user) {
        try {
            return this.mService.getOverlayInfosForTarget(targetPackageName, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void invalidateCachesForOverlay(String targetPackageName, UserHandle user) {
        try {
            this.mService.invalidateCachesForOverlay(targetPackageName, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void commitToSystemServer(OverlayManagerTransaction transaction) {
        try {
            this.mService.commit(transaction);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void commit(OverlayManagerTransaction transaction) {
        if (transaction.isSelfTargeting() || this.mService == null || this.mService.asBinder() == null) {
            try {
                commitSelfTarget(transaction);
                return;
            } catch (PackageManager.NameNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        commitToSystemServer(transaction);
    }

    private void rethrowSecurityException(SecurityException e) {
        if (!Compatibility.isChangeEnabled(THROW_SECURITY_EXCEPTIONS)) {
            throw new IllegalStateException(e);
        }
        throw e;
    }

    void commitSelfTarget(OverlayManagerTransaction transaction) throws PackageManager.NameNotFoundException, IOException {
        synchronized (this.mOverlayManagerImpl) {
            this.mOverlayManagerImpl.commit(transaction);
        }
    }

    public List<OverlayInfo> getOverlayInfosForTarget(String targetPackageName) {
        List<OverlayInfo> overlayInfosForTarget;
        synchronized (this.mOverlayManagerImpl) {
            overlayInfosForTarget = this.mOverlayManagerImpl.getOverlayInfosForTarget(targetPackageName);
        }
        return overlayInfosForTarget;
    }
}
