package com.samsung.android.authenticator;

import android.os.IHwBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.samsung.android.authenticator.SemTrustedApplicationExecutor;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import vendor.samsung.hardware.authfw.V1_0.ISehAuthenticationFramework;

/* loaded from: classes5.dex */
public final class HidlHalService implements XidlHalService, IHwBinder.DeathRecipient {
    private static final String TAG = "HHS";
    private ISehAuthenticationFramework mService = null;
    private byte[] mResultBytes = null;

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean isAvailable() {
        return getService() != null;
    }

    private synchronized ISehAuthenticationFramework getService() {
        if (this.mService == null) {
            try {
                ISehAuthenticationFramework service = ISehAuthenticationFramework.getService(true);
                this.mService = service;
                if (service != null) {
                    service.linkToDeath(this, 0L);
                }
            } catch (RemoteException e) {
                return null;
            }
        }
        return this.mService;
    }

    private <T> T checkNotNullState(T reference) {
        if (reference == null) {
            throw new IllegalStateException("can not found service");
        }
        return reference;
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean load(SemTrustedApplicationExecutor.TrustedAppType type, ParcelFileDescriptor fd, long offset, long len) {
        return load(translateTaType(type), fd, offset, len);
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean load(SemTrustedApplicationExecutor.TrustedAppAssetType type, ParcelFileDescriptor fd, long offset, long len) {
        return load(translateTaType(type), fd, offset, len);
    }

    private boolean load(int type, ParcelFileDescriptor fd, long offset, long len) {
        ByteArrayOutputStream bos;
        byte[] buffer;
        if (type == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return false;
        }
        ISehAuthenticationFramework service = (ISehAuthenticationFramework) checkNotNullState(getService());
        ArrayList<Byte> fileBuf = new ArrayList<>();
        if (fd != null) {
            try {
                FileInputStream fis = new ParcelFileDescriptor.AutoCloseInputStream(fd.dup());
                try {
                    try {
                        bos = new ByteArrayOutputStream();
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        try {
                            buffer = new byte[10240];
                        } catch (Throwable th2) {
                            th = th2;
                        }
                        try {
                            fis.skip(offset);
                            while (true) {
                                int count = fis.read(buffer);
                                if (count == -1) {
                                    break;
                                }
                                bos.write(buffer, 0, count);
                            }
                            byte[] temp = bos.toByteArray();
                            for (byte b : temp) {
                                fileBuf.add(Byte.valueOf(b));
                            }
                            bos.close();
                            fis.close();
                        } catch (Throwable th3) {
                            th = th3;
                            Throwable th4 = th;
                            try {
                                bos.close();
                                throw th4;
                            } catch (Throwable th5) {
                                th4.addSuppressed(th5);
                                throw th4;
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        Throwable th7 = th;
                        try {
                            fis.close();
                            throw th7;
                        } catch (Throwable th8) {
                            th7.addSuppressed(th8);
                            throw th7;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    AuthenticatorLog.e(TAG, "save file error. " + e.getMessage());
                    return false;
                }
            } catch (Exception e2) {
                e = e2;
            }
        }
        try {
            boolean ret = service.load(type, fileBuf);
            if (!ret) {
                AuthenticatorLog.e(TAG, "load fail. " + ret);
                return false;
            }
            return true;
        } catch (RemoteException e3) {
            AuthenticatorLog.e(TAG, "initialize failed : " + e3.getMessage());
            e3.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean unload(SemTrustedApplicationExecutor.TrustedAppType type) {
        return unload(translateTaType(type));
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean unload(SemTrustedApplicationExecutor.TrustedAppAssetType type) {
        return unload(translateTaType(type));
    }

    private boolean unload(int type) {
        if (type == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return false;
        }
        ISehAuthenticationFramework service = (ISehAuthenticationFramework) checkNotNullState(getService());
        try {
            boolean ret = service.terminate(type);
            if (!ret) {
                AuthenticatorLog.e(TAG, "unload fail. " + ret);
                return false;
            }
            return true;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminate failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public byte[] execute(SemTrustedApplicationExecutor.TrustedAppType type, byte[] command) {
        return execute(translateTaType(type), command);
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public byte[] execute(SemTrustedApplicationExecutor.TrustedAppAssetType type, byte[] command) {
        return execute(translateTaType(type), command);
    }

    private byte[] execute(int type, byte[] command) {
        if (type == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return null;
        }
        ISehAuthenticationFramework service = (ISehAuthenticationFramework) checkNotNullState(getService());
        ArrayList<Byte> request = new ArrayList<>();
        for (byte b : command) {
            request.add(Byte.valueOf(b));
        }
        this.mResultBytes = null;
        try {
            service.execute(type, request, new ISehAuthenticationFramework.executeCallback() { // from class: com.samsung.android.authenticator.HidlHalService$$ExternalSyntheticLambda0
                @Override // vendor.samsung.hardware.authfw.V1_0.ISehAuthenticationFramework.executeCallback
                public final void onValues(boolean z, ArrayList arrayList) {
                    HidlHalService.this.lambda$execute$0(z, arrayList);
                }
            });
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "process failed : " + e.getMessage());
            e.rethrowFromSystemServer();
        }
        return this.mResultBytes;
    }

    public /* synthetic */ void lambda$execute$0(boolean ret, ArrayList response) {
        AuthenticatorLog.i(TAG, "ret: " + ret + ", " + (response == null ? -1 : response.size()));
        if (response != null && response.size() > 0) {
            this.mResultBytes = new byte[response.size()];
            for (int i = 0; i < response.size(); i++) {
                this.mResultBytes[i] = ((Byte) response.get(i)).byteValue();
            }
        }
    }

    private int translateTaType(SemTrustedApplicationExecutor.TrustedAppType type) {
        switch (AnonymousClass1.$SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType[type.ordinal()]) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    /* renamed from: com.samsung.android.authenticator.HidlHalService$1 */
    /* loaded from: classes5.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppAssetType;
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType;

        static {
            int[] iArr = new int[SemTrustedApplicationExecutor.TrustedAppAssetType.values().length];
            $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppAssetType = iArr;
            try {
                iArr[SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_AUTHENTICATOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppAssetType[SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_ESE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            int[] iArr2 = new int[SemTrustedApplicationExecutor.TrustedAppType.values().length];
            $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType = iArr2;
            try {
                iArr2[SemTrustedApplicationExecutor.TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType[SemTrustedApplicationExecutor.TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType[SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private int translateTaType(SemTrustedApplicationExecutor.TrustedAppAssetType type) {
        switch (AnonymousClass1.$SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppAssetType[type.ordinal()]) {
            case 1:
                return 10000;
            case 2:
                return 10001;
            default:
                return 0;
        }
    }

    @Override // android.os.IHwBinder.DeathRecipient
    public void serviceDied(long cookie) {
        AuthenticatorLog.w(TAG, "service id died");
        this.mService = null;
    }
}
