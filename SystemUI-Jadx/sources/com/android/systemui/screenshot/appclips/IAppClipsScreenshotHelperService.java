package com.android.systemui.screenshot.appclips;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.screenshot.appclips.AppClipsScreenshotHelperService;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface IAppClipsScreenshotHelperService extends IInterface {
    ScreenshotHardwareBufferInternal takeScreenshot(int i);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract class Stub extends Binder implements IAppClipsScreenshotHelperService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public final class Proxy implements IAppClipsScreenshotHelperService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService
            public final ScreenshotHardwareBufferInternal takeScreenshot(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ScreenshotHardwareBufferInternal) obtain2.readTypedObject(ScreenshotHardwareBufferInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
            }
            if (i != 1598968902) {
                if (i != 1) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                ScreenshotHardwareBufferInternal takeScreenshot = ((AppClipsScreenshotHelperService.AnonymousClass1) this).takeScreenshot(readInt);
                parcel2.writeNoException();
                parcel2.writeTypedObject(takeScreenshot, 1);
                return true;
            }
            parcel2.writeString("com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
