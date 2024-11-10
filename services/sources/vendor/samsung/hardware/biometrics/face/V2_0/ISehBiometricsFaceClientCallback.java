package vendor.samsung.hardware.biometrics.face.V2_0;

import android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlMemory;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.att.iqi.lib.metrics.hw.HwConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback;

/* loaded from: classes2.dex */
public interface ISehBiometricsFaceClientCallback extends IBiometricsFaceClientCallback {
    @Override // android.hidl.base.V1_0.IBase
    IHwBinder asBinder();

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback, android.hidl.base.V1_0.IBase
    ArrayList interfaceChain();

    void sehOnAuthenticated(long j, int i, int i2, ArrayList arrayList, ArrayList arrayList2);

    void sehOnPreviewFrame(HidlMemory hidlMemory, int i, int i2, int i3, int i4);

    void sehOnPreviewUpdated(ArrayList arrayList, int i, int i2, int i3, int i4);

    static ISehBiometricsFaceClientCallback asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISehBiometricsFaceClientCallback)) {
            return (ISehBiometricsFaceClientCallback) queryLocalInterface;
        }
        Proxy proxy = new Proxy(iHwBinder);
        try {
            Iterator it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback")) {
                    return proxy;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    /* loaded from: classes2.dex */
    public final class Proxy implements ISehBiometricsFaceClientCallback {
        public IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            Objects.requireNonNull(iHwBinder);
            this.mRemote = iHwBinder;
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback, android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onEnrollResult(long j, int i, int i2, int i3) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onAuthenticated(long j, int i, int i2, ArrayList arrayList) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onAcquired(long j, int i, int i2, int i3) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onError(long j, int i, int i2, int i3) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onRemoved(long j, ArrayList arrayList, int i) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32Vector(arrayList);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onEnumerate(long j, ArrayList arrayList, int i) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32Vector(arrayList);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback, android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback, android.hidl.base.V1_0.IBase
        public ArrayList interfaceChain() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public String interfaceDescriptor() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public ArrayList getHashChain() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList arrayList = new ArrayList();
                HwBlob readBuffer = hwParcel2.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public void setHALInstrumentation() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hidl.base.V1_0.IBase
        public void ping() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                DebugInfo debugInfo = new DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    /* loaded from: classes2.dex */
    public abstract class Stub extends HwBinder implements ISehBiometricsFaceClientCallback {
        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback, android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList arrayList) {
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback";
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback, android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback, android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            return new ArrayList(Arrays.asList("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback", "android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback", IBase.kInterfaceName));
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList getHashChain() {
            return new ArrayList(Arrays.asList(new byte[]{7, -105, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, -41, -109, 10, -37, -16, 7, 84, -68, -123, -102, 31, -43, -64, 113, -104, 15, -96, -117, 53, 40, -51, -29, -57, -101, -68, -11, -115, -14, 74}, new byte[]{-74, -27, 93, 119, -107, -69, -81, -48, 17, -5, -107, -93, -74, -45, -107, 75, -10, 108, 52, -98, 20, -49, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, Byte.MAX_VALUE, 59, 114, 3, 44, -29, -50, -76, 72}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override // android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public IHwInterface queryLocalInterface(String str) {
            if ("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback".equals(str)) {
                return this;
            }
            return null;
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        public static /* synthetic */ HidlMemory lambda$onTransact$0(HwParcel hwParcel) {
            try {
                return hwParcel.readHidlMemory().dup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                    onEnrollResult(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 2:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                    onAuthenticated(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 3:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                    onAcquired(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 4:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                    onError(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 5:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                    onRemoved(hwParcel.readInt64(), hwParcel.readInt32Vector(), hwParcel.readInt32());
                    return;
                case 6:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                    onEnumerate(hwParcel.readInt64(), hwParcel.readInt32Vector(), hwParcel.readInt32());
                    return;
                case 7:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                    onLockoutChanged(hwParcel.readInt64());
                    return;
                case 8:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
                    sehOnPreviewUpdated(hwParcel.readInt8Vector(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
                    sehOnAuthenticated(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt8Vector(), hwParcel.readInt8Vector());
                    return;
                case 10:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
                    sehOnPreviewFrame((HidlMemory) new Function() { // from class: vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            HidlMemory lambda$onTransact$0;
                            lambda$onTransact$0 = ISehBiometricsFaceClientCallback.Stub.lambda$onTransact$0((HwParcel) obj);
                            return lambda$onTransact$0;
                        }
                    }.apply(hwParcel), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                default:
                    switch (i) {
                        case 256067662:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList interfaceChain = interfaceChain();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeStringVector(interfaceChain);
                            hwParcel2.send();
                            return;
                        case 256131655:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 256136003:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            String interfaceDescriptor = interfaceDescriptor();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(interfaceDescriptor);
                            hwParcel2.send();
                            return;
                        case 256398152:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList hashChain = getHashChain();
                            hwParcel2.writeStatus(0);
                            HwBlob hwBlob = new HwBlob(16);
                            int size = hashChain.size();
                            hwBlob.putInt32(8L, size);
                            hwBlob.putBool(12L, false);
                            HwBlob hwBlob2 = new HwBlob(size * 32);
                            for (int i3 = 0; i3 < size; i3++) {
                                long j = i3 * 32;
                                byte[] bArr = (byte[]) hashChain.get(i3);
                                if (bArr == null || bArr.length != 32) {
                                    throw new IllegalArgumentException("Array element is not of the expected length");
                                }
                                hwBlob2.putInt8Array(j, bArr);
                            }
                            hwBlob.putBlob(0L, hwBlob2);
                            hwParcel2.writeBuffer(hwBlob);
                            hwParcel2.send();
                            return;
                        case 256462420:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            setHALInstrumentation();
                            return;
                        case 256921159:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ping();
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 257049926:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            DebugInfo debugInfo = getDebugInfo();
                            hwParcel2.writeStatus(0);
                            debugInfo.writeToParcel(hwParcel2);
                            hwParcel2.send();
                            return;
                        case 257120595:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            notifySyspropsChanged();
                            return;
                        default:
                            return;
                    }
            }
        }
    }
}
