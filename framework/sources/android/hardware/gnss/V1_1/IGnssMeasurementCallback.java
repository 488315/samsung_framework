package android.hardware.gnss.V1_1;

import android.hardware.gnss.V1_0.IGnssMeasurementCallback;
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
public interface IGnssMeasurementCallback extends android.hardware.gnss.V1_0.IGnssMeasurementCallback {
    public static final String kInterfaceName = "android.hardware.gnss@1.1::IGnssMeasurementCallback";

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void gnssMeasurementCb(GnssData gnssData) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IGnssMeasurementCallback asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IGnssMeasurementCallback)) {
            return (IGnssMeasurementCallback) iface;
        }
        IGnssMeasurementCallback proxy = new Proxy(binder);
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

    static IGnssMeasurementCallback castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IGnssMeasurementCallback getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IGnssMeasurementCallback getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IGnssMeasurementCallback getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IGnssMeasurementCallback getService() throws RemoteException {
        return getService("default");
    }

    public static final class GnssAccumulatedDeltaRangeState {
        public static final short ADR_STATE_CYCLE_SLIP = 4;
        public static final short ADR_STATE_HALF_CYCLE_RESOLVED = 8;
        public static final short ADR_STATE_RESET = 2;
        public static final short ADR_STATE_UNKNOWN = 0;
        public static final short ADR_STATE_VALID = 1;

        public static final String toString(short o) {
            if (o == 0) {
                return "ADR_STATE_UNKNOWN";
            }
            if (o == 1) {
                return "ADR_STATE_VALID";
            }
            if (o == 2) {
                return "ADR_STATE_RESET";
            }
            if (o == 4) {
                return "ADR_STATE_CYCLE_SLIP";
            }
            if (o == 8) {
                return "ADR_STATE_HALF_CYCLE_RESOLVED";
            }
            return "0x" + Integer.toHexString(Short.toUnsignedInt(o));
        }

        public static final String dumpBitfield(short o) {
            ArrayList<String> list = new ArrayList<>();
            short flipped = 0;
            list.add("ADR_STATE_UNKNOWN");
            if ((o & 1) == 1) {
                list.add("ADR_STATE_VALID");
                flipped = (short) (0 | 1);
            }
            if ((o & 2) == 2) {
                list.add("ADR_STATE_RESET");
                flipped = (short) (flipped | 2);
            }
            if ((o & 4) == 4) {
                list.add("ADR_STATE_CYCLE_SLIP");
                flipped = (short) (flipped | 4);
            }
            if ((o & 8) == 8) {
                list.add("ADR_STATE_HALF_CYCLE_RESOLVED");
                flipped = (short) (flipped | 8);
            }
            if (o != flipped) {
                list.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) ((~flipped) & o))));
            }
            return String.join(" | ", list);
        }
    }

    public static final class GnssMeasurement {
        public short accumulatedDeltaRangeState;
        public IGnssMeasurementCallback.GnssMeasurement v1_0 = new IGnssMeasurementCallback.GnssMeasurement();

        public final boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null || otherObject.getClass() != GnssMeasurement.class) {
                return false;
            }
            GnssMeasurement other = (GnssMeasurement) otherObject;
            if (HidlSupport.deepEquals(this.v1_0, other.v1_0) && HidlSupport.deepEquals(Short.valueOf(this.accumulatedDeltaRangeState), Short.valueOf(other.accumulatedDeltaRangeState))) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.v1_0)), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.accumulatedDeltaRangeState))));
        }

        public final String toString() {
            return "{.v1_0 = " + this.v1_0 + ", .accumulatedDeltaRangeState = " + GnssAccumulatedDeltaRangeState.dumpBitfield(this.accumulatedDeltaRangeState) + "}";
        }

        public final void readFromParcel(HwParcel parcel) {
            HwBlob blob = parcel.readBuffer(152L);
            readEmbeddedFromParcel(parcel, blob, 0L);
        }

        public static final ArrayList<GnssMeasurement> readVectorFromParcel(HwParcel parcel) {
            ArrayList<GnssMeasurement> _hidl_vec = new ArrayList<>();
            HwBlob _hidl_blob = parcel.readBuffer(16L);
            int _hidl_vec_size = _hidl_blob.getInt32(8L);
            HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 152, _hidl_blob.handle(), 0L, true);
            _hidl_vec.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                GnssMeasurement _hidl_vec_element = new GnssMeasurement();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 152);
                _hidl_vec.add(_hidl_vec_element);
            }
            return _hidl_vec;
        }

        public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
            this.v1_0.readEmbeddedFromParcel(parcel, _hidl_blob, 0 + _hidl_offset);
            this.accumulatedDeltaRangeState = _hidl_blob.getInt16(144 + _hidl_offset);
        }

        public final void writeToParcel(HwParcel parcel) {
            HwBlob _hidl_blob = new HwBlob(152);
            writeEmbeddedToBlob(_hidl_blob, 0L);
            parcel.writeBuffer(_hidl_blob);
        }

        public static final void writeVectorToParcel(HwParcel parcel, ArrayList<GnssMeasurement> _hidl_vec) {
            HwBlob _hidl_blob = new HwBlob(16);
            int _hidl_vec_size = _hidl_vec.size();
            _hidl_blob.putInt32(8L, _hidl_vec_size);
            _hidl_blob.putBool(12L, false);
            HwBlob childBlob = new HwBlob(_hidl_vec_size * 152);
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 152);
            }
            _hidl_blob.putBlob(0L, childBlob);
            parcel.writeBuffer(_hidl_blob);
        }

        public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
            this.v1_0.writeEmbeddedToBlob(_hidl_blob, 0 + _hidl_offset);
            _hidl_blob.putInt16(144 + _hidl_offset, this.accumulatedDeltaRangeState);
        }
    }

    public static final class GnssData {
        public ArrayList<GnssMeasurement> measurements = new ArrayList<>();
        public IGnssMeasurementCallback.GnssClock clock = new IGnssMeasurementCallback.GnssClock();

        public final boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null || otherObject.getClass() != GnssData.class) {
                return false;
            }
            GnssData other = (GnssData) otherObject;
            if (HidlSupport.deepEquals(this.measurements, other.measurements) && HidlSupport.deepEquals(this.clock, other.clock)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.measurements)), Integer.valueOf(HidlSupport.deepHashCode(this.clock)));
        }

        public final String toString() {
            return "{.measurements = " + this.measurements + ", .clock = " + this.clock + "}";
        }

        public final void readFromParcel(HwParcel parcel) {
            HwBlob blob = parcel.readBuffer(88L);
            readEmbeddedFromParcel(parcel, blob, 0L);
        }

        public static final ArrayList<GnssData> readVectorFromParcel(HwParcel parcel) {
            ArrayList<GnssData> _hidl_vec = new ArrayList<>();
            HwBlob _hidl_blob = parcel.readBuffer(16L);
            int _hidl_vec_size = _hidl_blob.getInt32(8L);
            HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 88, _hidl_blob.handle(), 0L, true);
            _hidl_vec.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                GnssData _hidl_vec_element = new GnssData();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 88);
                _hidl_vec.add(_hidl_vec_element);
            }
            return _hidl_vec;
        }

        public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
            int _hidl_vec_size = _hidl_blob.getInt32(_hidl_offset + 0 + 8);
            HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 152, _hidl_blob.handle(), _hidl_offset + 0 + 0, true);
            this.measurements.clear();
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                GnssMeasurement _hidl_vec_element = new GnssMeasurement();
                _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 152);
                this.measurements.add(_hidl_vec_element);
            }
            this.clock.readEmbeddedFromParcel(parcel, _hidl_blob, _hidl_offset + 16);
        }

        public final void writeToParcel(HwParcel parcel) {
            HwBlob _hidl_blob = new HwBlob(88);
            writeEmbeddedToBlob(_hidl_blob, 0L);
            parcel.writeBuffer(_hidl_blob);
        }

        public static final void writeVectorToParcel(HwParcel parcel, ArrayList<GnssData> _hidl_vec) {
            HwBlob _hidl_blob = new HwBlob(16);
            int _hidl_vec_size = _hidl_vec.size();
            _hidl_blob.putInt32(8L, _hidl_vec_size);
            _hidl_blob.putBool(12L, false);
            HwBlob childBlob = new HwBlob(_hidl_vec_size * 88);
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 88);
            }
            _hidl_blob.putBlob(0L, childBlob);
            parcel.writeBuffer(_hidl_blob);
        }

        public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
            int _hidl_vec_size = this.measurements.size();
            _hidl_blob.putInt32(_hidl_offset + 0 + 8, _hidl_vec_size);
            _hidl_blob.putBool(_hidl_offset + 0 + 12, false);
            HwBlob childBlob = new HwBlob(_hidl_vec_size * 152);
            for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                this.measurements.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 152);
            }
            _hidl_blob.putBlob(_hidl_offset + 0 + 0, childBlob);
            this.clock.writeEmbeddedToBlob(_hidl_blob, 16 + _hidl_offset);
        }
    }

    public static final class Proxy implements IGnssMeasurementCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of android.hardware.gnss@1.1::IGnssMeasurementCallback]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback
        public void GnssMeasurementCb(IGnssMeasurementCallback.GnssData data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName);
            data.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback
        public void gnssMeasurementCb(GnssData data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IGnssMeasurementCallback.kInterfaceName);
            data.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssMeasurementCallback {
        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssMeasurementCallback.kInterfaceName, android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) {
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssMeasurementCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{122, -30, 2, 86, 98, -29, 14, 105, 10, 63, -6, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, 101, -52, -105, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 98, -105, -90, -122, 56, 23, 64, 85, -61, 60, -65, 61, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 75, -67, -36}, new byte[]{-67, -92, -110, -20, 64, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -47, 56, 105, -34, 114, -67, 111, -116, 21, -59, -125, 123, 120, -42, 19, 107, -115, 83, -114, -2, -59, 50, 5, 115, -91, -20}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String descriptor) {
            if (IGnssMeasurementCallback.kInterfaceName.equals(descriptor)) {
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
                    _hidl_request.enforceInterface(android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName);
                    IGnssMeasurementCallback.GnssData data = new IGnssMeasurementCallback.GnssData();
                    data.readFromParcel(_hidl_request);
                    GnssMeasurementCb(data);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 2:
                    _hidl_request.enforceInterface(IGnssMeasurementCallback.kInterfaceName);
                    GnssData data2 = new GnssData();
                    data2.readFromParcel(_hidl_request);
                    gnssMeasurementCb(data2);
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
