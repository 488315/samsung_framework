package android.hardware.gnss.measurement_corrections.V1_0;

import android.internal.hidl.base.V1_0.DebugInfo;
import android.internal.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface IMeasurementCorrectionsCallback extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss.measurement_corrections@1.0::IMeasurementCorrectionsCallback";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void setCapabilitiesCb(int i) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IMeasurementCorrectionsCallback asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IMeasurementCorrectionsCallback)) {
            return (IMeasurementCorrectionsCallback) iface;
        }
        IMeasurementCorrectionsCallback proxy = new Proxy(binder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                String descriptor = it.next();
                if (descriptor.equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException e) {
        }
        return null;
    }

    static IMeasurementCorrectionsCallback castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IMeasurementCorrectionsCallback getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IMeasurementCorrectionsCallback getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IMeasurementCorrectionsCallback getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IMeasurementCorrectionsCallback getService() throws RemoteException {
        return getService("default");
    }

    public static final class Capabilities {
        public static final int EXCESS_PATH_LENGTH = 2;
        public static final int LOS_SATS = 1;
        public static final int REFLECTING_PLANE = 4;

        public static final String toString(int o) {
            if (o == 1) {
                return "LOS_SATS";
            }
            if (o == 2) {
                return "EXCESS_PATH_LENGTH";
            }
            if (o == 4) {
                return "REFLECTING_PLANE";
            }
            return "0x" + Integer.toHexString(o);
        }

        public static final String dumpBitfield(int o) {
            ArrayList<String> list = new ArrayList<>();
            int flipped = 0;
            if ((o & 1) == 1) {
                list.add("LOS_SATS");
                flipped = 0 | 1;
            }
            if ((o & 2) == 2) {
                list.add("EXCESS_PATH_LENGTH");
                flipped |= 2;
            }
            if ((o & 4) == 4) {
                list.add("REFLECTING_PLANE");
                flipped |= 4;
            }
            if (o != flipped) {
                list.add("0x" + Integer.toHexString((~flipped) & o));
            }
            return String.join(" | ", list);
        }
    }

    public static final class Proxy implements IMeasurementCorrectionsCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of android.hardware.gnss.measurement_corrections@1.0::IMeasurementCorrectionsCallback]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback
        public void setCapabilitiesCb(int capabilities) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMeasurementCorrectionsCallback.kInterfaceName);
            _hidl_request.writeInt32(capabilities);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<String> _hidl_out_descriptors = _hidl_reply.readStringVector();
                return _hidl_out_descriptors;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            _hidl_request.writeNativeHandle(fd);
            _hidl_request.writeStringVector(options);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256131655, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public String interfaceDescriptor() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                String _hidl_out_descriptor = _hidl_reply.readString();
                return _hidl_out_descriptor;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256398152, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<byte[]> _hidl_out_hashchain = new ArrayList<>();
                HwBlob _hidl_blob = _hidl_reply.readBuffer(16L);
                int _hidl_vec_size = _hidl_blob.getInt32(8L);
                HwBlob childBlob = _hidl_reply.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
                _hidl_out_hashchain.clear();
                for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                    byte[] _hidl_vec_element = new byte[32];
                    long _hidl_array_offset_1 = _hidl_index_0 * 32;
                    childBlob.copyToInt8Array(_hidl_array_offset_1, _hidl_vec_element, 32);
                    _hidl_out_hashchain.add(_hidl_vec_element);
                }
                return _hidl_out_hashchain;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256462420, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public void ping() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256921159, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257049926, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                DebugInfo _hidl_out_info = new DebugInfo();
                _hidl_out_info.readFromParcel(_hidl_reply);
                return _hidl_out_info;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257120595, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IMeasurementCorrectionsCallback {
        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IMeasurementCorrectionsCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) {
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IMeasurementCorrectionsCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{62, -20, -105, 99, -37, -101, 16, 22, 68, MidiConstants.STATUS_MIDI_TIME_CODE, 65, 117, -73, 124, -103, 84, 4, 116, 69, -92, 104, -23, -57, 67, -3, 64, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 71, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 74, -87, 126}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        @Override // android.hardware.gnss.measurement_corrections.V1_0.IMeasurementCorrectionsCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String descriptor) {
            if (IMeasurementCorrectionsCallback.kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String serviceName) throws RemoteException {
            registerService(serviceName);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // android.os.HwBinder
        public void onTransact(int _hidl_code, HwParcel _hidl_request, HwParcel _hidl_reply, int _hidl_flags) throws RemoteException {
            switch (_hidl_code) {
                case 1:
                    _hidl_request.enforceInterface(IMeasurementCorrectionsCallback.kInterfaceName);
                    int capabilities = _hidl_request.readInt32();
                    setCapabilitiesCb(capabilities);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 256067662:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<String> _hidl_out_descriptors = interfaceChain();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeStringVector(_hidl_out_descriptors);
                    _hidl_reply.send();
                    return;
                case 256131655:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    NativeHandle fd = _hidl_request.readNativeHandle();
                    ArrayList<String> options = _hidl_request.readStringVector();
                    debug(fd, options);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 256136003:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    String _hidl_out_descriptor = interfaceDescriptor();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeString(_hidl_out_descriptor);
                    _hidl_reply.send();
                    return;
                case 256398152:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
                    _hidl_reply.writeStatus(0);
                    HwBlob _hidl_blob = new HwBlob(16);
                    int _hidl_vec_size = _hidl_out_hashchain.size();
                    _hidl_blob.putInt32(8L, _hidl_vec_size);
                    _hidl_blob.putBool(12L, false);
                    HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
                    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                        long _hidl_array_offset_1 = _hidl_index_0 * 32;
                        byte[] _hidl_array_item_1 = _hidl_out_hashchain.get(_hidl_index_0);
                        if (_hidl_array_item_1 == null || _hidl_array_item_1.length != 32) {
                            throw new IllegalArgumentException("Array element is not of the expected length");
                        }
                        childBlob.putInt8Array(_hidl_array_offset_1, _hidl_array_item_1);
                    }
                    _hidl_blob.putBlob(0L, childBlob);
                    _hidl_reply.writeBuffer(_hidl_blob);
                    _hidl_reply.send();
                    return;
                case 256462420:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256660548:
                default:
                    return;
                case 256921159:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ping();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 257049926:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    DebugInfo _hidl_out_info = getDebugInfo();
                    _hidl_reply.writeStatus(0);
                    _hidl_out_info.writeToParcel(_hidl_reply);
                    _hidl_reply.send();
                    return;
                case 257120595:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
            }
        }
    }
}
