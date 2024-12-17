package android.hardware.tv.cec.V1_1;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy$$ExternalSyntheticOutline0;
import android.hardware.tv.cec.V1_0.HdmiPortInfo;
import android.hardware.tv.cec.V1_0.IHdmiCec;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.server.hdmi.HdmiCecController;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IHdmiCec extends android.hardware.tv.cec.V1_0.IHdmiCec {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Proxy implements IHdmiCec {
        public IHwBinder mRemote;

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final int addLogicalAddress(int i) {
            HwParcel m = ITunerSession$Proxy$$ExternalSyntheticOutline0.m(i, "android.hardware.tv.cec@1.0::IHdmiCec");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final void clearLogicalAddress() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.tv.cec@1.0::IHdmiCec");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(2, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName, nativeHandle, arrayList);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256131655, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final void enableAudioReturnChannel(int i, boolean z) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final int getCecVersion() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.tv.cec@1.0::IHdmiCec");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(6, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(257049926, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                DebugInfo debugInfo = new DebugInfo();
                debugInfo.readFromParcel(hwParcel);
                return debugInfo;
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList getHashChain() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256398152, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                ArrayList arrayList = new ArrayList();
                HwBlob readBuffer = hwParcel.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final void getPhysicalAddress(IHdmiCec.getPhysicalAddressCallback getphysicaladdresscallback) {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.tv.cec@1.0::IHdmiCec");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(3, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                getphysicaladdresscallback.onValues(hwParcel.readInt32(), hwParcel.readInt16());
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final ArrayList getPortInfo() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.tv.cec@1.0::IHdmiCec");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(8, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return HdmiPortInfo.readVectorFromParcel(hwParcel);
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final int getVendorId() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.tv.cec@1.0::IHdmiCec");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(7, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        public final int hashCode() {
            return this.mRemote.hashCode();
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256067662, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readStringVector();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256136003, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readString();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final boolean isConnected(int i) {
            HwParcel m = ITunerSession$Proxy$$ExternalSyntheticOutline0.m(i, "android.hardware.tv.cec@1.0::IHdmiCec");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(12, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readBool();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(257120595, m, hwParcel, 1);
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256921159, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final int sendMessage(android.hardware.tv.cec.V1_0.CecMessage cecMessage) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
            cecMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        public final int sendMessage_1_1(CecMessage cecMessage) {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.tv.cec@1.1::IHdmiCec");
            HwBlob hwBlob = new HwBlob(24);
            hwBlob.putInt32(0L, cecMessage.initiator);
            hwBlob.putInt32(4L, cecMessage.destination);
            int size = cecMessage.body.size();
            hwBlob.putInt32(16L, size);
            hwBlob.putBool(20L, false);
            HwBlob hwBlob2 = new HwBlob(size);
            for (int i = 0; i < size; i++) {
                hwBlob2.putInt8(i, ((Byte) cecMessage.body.get(i)).byteValue());
            }
            hwBlob.putBlob(8L, hwBlob2);
            m.writeBuffer(hwBlob);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(14, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final void setCallback(HdmiCecController.HdmiCecCallback10 hdmiCecCallback10) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
            hwParcel.writeStrongBinder(hdmiCecCallback10);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public final void setCallback_1_1(HdmiCecController.HdmiCecCallback10 hdmiCecCallback10) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.1::IHdmiCec");
            hwParcel.writeStrongBinder(hdmiCecCallback10);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256462420, m, hwParcel, 1);
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final void setLanguage(String str) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec
        public final void setOption(int i, boolean z) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.tv.cec@1.0::IHdmiCec");
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public final String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.tv.cec@1.1::IHdmiCec]@Proxy";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    static IHdmiCec getService() {
        IHwBinder service = HwBinder.getService("android.hardware.tv.cec@1.1::IHdmiCec", "default", true);
        if (service == null) {
            return null;
        }
        IHwInterface queryLocalInterface = service.queryLocalInterface("android.hardware.tv.cec@1.1::IHdmiCec");
        if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiCec)) {
            return (IHdmiCec) queryLocalInterface;
        }
        Proxy proxy = new Proxy();
        proxy.mRemote = service;
        try {
            Iterator it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals("android.hardware.tv.cec@1.1::IHdmiCec")) {
                    return proxy;
                }
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }
}
