package vendor.samsung.hardware.thermal.V1_0;

import android.hardware.thermal.V1_0.CoolingDevice;
import android.hardware.thermal.V1_0.CpuUsage;
import android.hardware.thermal.V1_0.IThermal;
import android.hardware.thermal.V1_0.Temperature;
import android.hardware.thermal.V1_0.ThermalStatus;
import android.hardware.thermal.V2_0.IThermal;
import android.hardware.thermal.V2_0.IThermalChangedCallback;
import android.hardware.thermal.V2_0.TemperatureThreshold;
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
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes6.dex */
public interface ISehThermal extends IThermal {
    public static final String kInterfaceName = "vendor.samsung.hardware.thermal@1.0::ISehThermal";

    @FunctionalInterface
    /* loaded from: classes6.dex */
    public interface sehGetTemperaturesCallback {
        void onValues(ThermalStatus thermalStatus, ArrayList<SehTemperature> arrayList);
    }

    @FunctionalInterface
    /* loaded from: classes6.dex */
    public interface sehGetTypeTemperaturesCallback {
        void onValues(ThermalStatus thermalStatus, ArrayList<SehTemperature> arrayList);
    }

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void sehGetTemperatures(sehGetTemperaturesCallback sehgettemperaturescallback) throws RemoteException;

    void sehGetTypeTemperatures(int i, sehGetTypeTemperaturesCallback sehgettypetemperaturescallback) throws RemoteException;

    ThermalStatus sehRegisterThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException;

    ThermalStatus sehUnregisterThermalChangedCallback(ISehThermalChangedCallback iSehThermalChangedCallback) throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static ISehThermal asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof ISehThermal)) {
            return (ISehThermal) iface;
        }
        ISehThermal proxy = new Proxy(binder);
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

    static ISehThermal castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static ISehThermal getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static ISehThermal getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static ISehThermal getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static ISehThermal getService() throws RemoteException {
        return getService("default");
    }

    /* loaded from: classes6.dex */
    public static final class Proxy implements ISehThermal {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of vendor.samsung.hardware.thermal@1.0::ISehThermal]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getTemperatures(IThermal.getTemperaturesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<Temperature> _hidl_out_temperatures = Temperature.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_temperatures);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getCpuUsages(IThermal.getCpuUsagesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<CpuUsage> _hidl_out_cpuUsages = CpuUsage.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_cpuUsages);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getCoolingDevices(IThermal.getCoolingDevicesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<CoolingDevice> _hidl_out_devices = CoolingDevice.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_devices);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public void getCurrentTemperatures(boolean filterType, int type, IThermal.getCurrentTemperaturesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
            _hidl_request.writeBool(filterType);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<android.hardware.thermal.V2_0.Temperature> _hidl_out_temperatures = android.hardware.thermal.V2_0.Temperature.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_temperatures);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public void getTemperatureThresholds(boolean filterType, int type, IThermal.getTemperatureThresholdsCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
            _hidl_request.writeBool(filterType);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<TemperatureThreshold> _hidl_out_temperatureThresholds = TemperatureThreshold.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_temperatureThresholds);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public ThermalStatus registerThermalChangedCallback(IThermalChangedCallback callback, boolean filterType, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            _hidl_request.writeBool(filterType);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                return _hidl_out_status;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public ThermalStatus unregisterThermalChangedCallback(IThermalChangedCallback callback) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                return _hidl_out_status;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public void getCurrentCoolingDevices(boolean filterType, int type, IThermal.getCurrentCoolingDevicesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
            _hidl_request.writeBool(filterType);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<android.hardware.thermal.V2_0.CoolingDevice> _hidl_out_devices = android.hardware.thermal.V2_0.CoolingDevice.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_devices);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal
        public void sehGetTemperatures(sehGetTemperaturesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehThermal.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<SehTemperature> _hidl_out_temperatures = SehTemperature.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_temperatures);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal
        public void sehGetTypeTemperatures(int type, sehGetTypeTemperaturesCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehThermal.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                ArrayList<SehTemperature> _hidl_out_temperatures = SehTemperature.readVectorFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_status, _hidl_out_temperatures);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal
        public ThermalStatus sehRegisterThermalChangedCallback(ISehThermalChangedCallback callback) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehThermal.kInterfaceName);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                return _hidl_out_status;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal
        public ThermalStatus sehUnregisterThermalChangedCallback(ISehThermalChangedCallback callback) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehThermal.kInterfaceName);
            _hidl_request.writeStrongBinder(callback == null ? null : callback.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ThermalStatus _hidl_out_status = new ThermalStatus();
                _hidl_out_status.readFromParcel(_hidl_reply);
                return _hidl_out_status;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    /* loaded from: classes6.dex */
    public static abstract class Stub extends HwBinder implements ISehThermal {
        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(ISehThermal.kInterfaceName, android.hardware.thermal.V2_0.IThermal.kInterfaceName, android.hardware.thermal.V1_0.IThermal.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) {
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ISehThermal.kInterfaceName;
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{123, -116, -42, -115, 105, -90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 92, 6, -16, MidiConstants.STATUS_NOTE_ON, -6, SprAnimatorBase.INTERPOLATOR_TYPE_CUBICEASEIN, -57, -95, -77, 6, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -81, -16, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -83, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, -118, 109, 52, -115, 50, 9, 50, 62, -101}, new byte[]{-67, -120, -76, -122, 57, -54, -29, 9, -126, 2, 16, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -30, 35, 113, 7, 108, 118, -6, -88, 70, 110, 56, -54, 89, -123, 41, 69, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, SprAttributeBase.TYPE_ANIMATOR_SET, -114, -82}, new byte[]{-105, MidiConstants.STATUS_MIDI_TIME_CODE, -20, 68, SprAttributeBase.TYPE_DURATION, 67, -68, 90, 102, 69, -73, 69, 41, -90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 100, -106, -67, -77, 94, 10, -18, 65, -19, -91, 92, -71, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 81, -21, 120, 2}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, SprAnimatorBase.INTERPOLATOR_TYPE_CUBICEASEIN, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT, -54, 76}));
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal, android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String descriptor) {
            if (ISehThermal.kInterfaceName.equals(descriptor)) {
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
                    _hidl_request.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                    getTemperatures(new IThermal.getTemperaturesCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.1
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass1(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback
                        public void onValues(ThermalStatus status, ArrayList<Temperature> temperatures) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            Temperature.writeVectorToParcel(_hidl_reply, temperatures);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 2:
                    _hidl_request.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                    getCpuUsages(new IThermal.getCpuUsagesCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.2
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass2(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // android.hardware.thermal.V1_0.IThermal.getCpuUsagesCallback
                        public void onValues(ThermalStatus status, ArrayList<CpuUsage> cpuUsages) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            CpuUsage.writeVectorToParcel(_hidl_reply, cpuUsages);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 3:
                    _hidl_request.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                    getCoolingDevices(new IThermal.getCoolingDevicesCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.3
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass3(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback
                        public void onValues(ThermalStatus status, ArrayList<CoolingDevice> devices) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            CoolingDevice.writeVectorToParcel(_hidl_reply, devices);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 4:
                    _hidl_request.enforceInterface(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
                    boolean filterType = _hidl_request.readBool();
                    int type = _hidl_request.readInt32();
                    getCurrentTemperatures(filterType, type, new IThermal.getCurrentTemperaturesCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.4
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass4(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // android.hardware.thermal.V2_0.IThermal.getCurrentTemperaturesCallback
                        public void onValues(ThermalStatus status, ArrayList<android.hardware.thermal.V2_0.Temperature> temperatures) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            android.hardware.thermal.V2_0.Temperature.writeVectorToParcel(_hidl_reply, temperatures);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 5:
                    _hidl_request.enforceInterface(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
                    boolean filterType2 = _hidl_request.readBool();
                    int type2 = _hidl_request.readInt32();
                    getTemperatureThresholds(filterType2, type2, new IThermal.getTemperatureThresholdsCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.5
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass5(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // android.hardware.thermal.V2_0.IThermal.getTemperatureThresholdsCallback
                        public void onValues(ThermalStatus status, ArrayList<TemperatureThreshold> temperatureThresholds) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            TemperatureThreshold.writeVectorToParcel(_hidl_reply, temperatureThresholds);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 6:
                    _hidl_request.enforceInterface(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
                    IThermalChangedCallback callback = IThermalChangedCallback.asInterface(_hidl_request.readStrongBinder());
                    boolean filterType3 = _hidl_request.readBool();
                    int type3 = _hidl_request.readInt32();
                    ThermalStatus _hidl_out_status = registerThermalChangedCallback(callback, filterType3, type3);
                    _hidl_reply2.writeStatus(0);
                    _hidl_out_status.writeToParcel(_hidl_reply2);
                    _hidl_reply2.send();
                    return;
                case 7:
                    _hidl_request.enforceInterface(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
                    IThermalChangedCallback callback2 = IThermalChangedCallback.asInterface(_hidl_request.readStrongBinder());
                    ThermalStatus _hidl_out_status2 = unregisterThermalChangedCallback(callback2);
                    _hidl_reply2.writeStatus(0);
                    _hidl_out_status2.writeToParcel(_hidl_reply2);
                    _hidl_reply2.send();
                    return;
                case 8:
                    _hidl_request.enforceInterface(android.hardware.thermal.V2_0.IThermal.kInterfaceName);
                    boolean filterType4 = _hidl_request.readBool();
                    int type4 = _hidl_request.readInt32();
                    getCurrentCoolingDevices(filterType4, type4, new IThermal.getCurrentCoolingDevicesCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.6
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass6(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // android.hardware.thermal.V2_0.IThermal.getCurrentCoolingDevicesCallback
                        public void onValues(ThermalStatus status, ArrayList<android.hardware.thermal.V2_0.CoolingDevice> devices) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            android.hardware.thermal.V2_0.CoolingDevice.writeVectorToParcel(_hidl_reply, devices);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 9:
                    _hidl_request.enforceInterface(ISehThermal.kInterfaceName);
                    sehGetTemperatures(new sehGetTemperaturesCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.7
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass7(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal.sehGetTemperaturesCallback
                        public void onValues(ThermalStatus status, ArrayList<SehTemperature> temperatures) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            SehTemperature.writeVectorToParcel(_hidl_reply, temperatures);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 10:
                    _hidl_request.enforceInterface(ISehThermal.kInterfaceName);
                    int type5 = _hidl_request.readInt32();
                    sehGetTypeTemperatures(type5, new sehGetTypeTemperaturesCallback() { // from class: vendor.samsung.hardware.thermal.V1_0.ISehThermal.Stub.8
                        final /* synthetic */ HwParcel val$_hidl_reply;

                        AnonymousClass8(HwParcel _hidl_reply2) {
                            _hidl_reply = _hidl_reply2;
                        }

                        @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal.sehGetTypeTemperaturesCallback
                        public void onValues(ThermalStatus status, ArrayList<SehTemperature> temperatures) {
                            _hidl_reply.writeStatus(0);
                            status.writeToParcel(_hidl_reply);
                            SehTemperature.writeVectorToParcel(_hidl_reply, temperatures);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 11:
                    _hidl_request.enforceInterface(ISehThermal.kInterfaceName);
                    ISehThermalChangedCallback callback3 = ISehThermalChangedCallback.asInterface(_hidl_request.readStrongBinder());
                    ThermalStatus _hidl_out_status3 = sehRegisterThermalChangedCallback(callback3);
                    _hidl_reply2.writeStatus(0);
                    _hidl_out_status3.writeToParcel(_hidl_reply2);
                    _hidl_reply2.send();
                    return;
                case 12:
                    _hidl_request.enforceInterface(ISehThermal.kInterfaceName);
                    ISehThermalChangedCallback callback4 = ISehThermalChangedCallback.asInterface(_hidl_request.readStrongBinder());
                    ThermalStatus _hidl_out_status4 = sehUnregisterThermalChangedCallback(callback4);
                    _hidl_reply2.writeStatus(0);
                    _hidl_out_status4.writeToParcel(_hidl_reply2);
                    _hidl_reply2.send();
                    return;
                case 256067662:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<String> _hidl_out_descriptors = interfaceChain();
                    _hidl_reply2.writeStatus(0);
                    _hidl_reply2.writeStringVector(_hidl_out_descriptors);
                    _hidl_reply2.send();
                    return;
                case 256131655:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    NativeHandle fd = _hidl_request.readNativeHandle();
                    ArrayList<String> options = _hidl_request.readStringVector();
                    debug(fd, options);
                    _hidl_reply2.writeStatus(0);
                    _hidl_reply2.send();
                    return;
                case 256136003:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    String _hidl_out_descriptor = interfaceDescriptor();
                    _hidl_reply2.writeStatus(0);
                    _hidl_reply2.writeString(_hidl_out_descriptor);
                    _hidl_reply2.send();
                    return;
                case 256398152:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
                    _hidl_reply2.writeStatus(0);
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
                    _hidl_reply2.writeBuffer(_hidl_blob);
                    _hidl_reply2.send();
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
                    _hidl_reply2.writeStatus(0);
                    _hidl_reply2.send();
                    return;
                case 257049926:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    DebugInfo _hidl_out_info = getDebugInfo();
                    _hidl_reply2.writeStatus(0);
                    _hidl_out_info.writeToParcel(_hidl_reply2);
                    _hidl_reply2.send();
                    return;
                case 257120595:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$1 */
        /* loaded from: classes6.dex */
        class AnonymousClass1 implements IThermal.getTemperaturesCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass1(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback
            public void onValues(ThermalStatus status, ArrayList<Temperature> temperatures) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                Temperature.writeVectorToParcel(_hidl_reply, temperatures);
                _hidl_reply.send();
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$2 */
        /* loaded from: classes6.dex */
        class AnonymousClass2 implements IThermal.getCpuUsagesCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass2(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // android.hardware.thermal.V1_0.IThermal.getCpuUsagesCallback
            public void onValues(ThermalStatus status, ArrayList<CpuUsage> cpuUsages) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                CpuUsage.writeVectorToParcel(_hidl_reply, cpuUsages);
                _hidl_reply.send();
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$3 */
        /* loaded from: classes6.dex */
        class AnonymousClass3 implements IThermal.getCoolingDevicesCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass3(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback
            public void onValues(ThermalStatus status, ArrayList<CoolingDevice> devices) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                CoolingDevice.writeVectorToParcel(_hidl_reply, devices);
                _hidl_reply.send();
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$4 */
        /* loaded from: classes6.dex */
        class AnonymousClass4 implements IThermal.getCurrentTemperaturesCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass4(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // android.hardware.thermal.V2_0.IThermal.getCurrentTemperaturesCallback
            public void onValues(ThermalStatus status, ArrayList<android.hardware.thermal.V2_0.Temperature> temperatures) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                android.hardware.thermal.V2_0.Temperature.writeVectorToParcel(_hidl_reply, temperatures);
                _hidl_reply.send();
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$5 */
        /* loaded from: classes6.dex */
        class AnonymousClass5 implements IThermal.getTemperatureThresholdsCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass5(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // android.hardware.thermal.V2_0.IThermal.getTemperatureThresholdsCallback
            public void onValues(ThermalStatus status, ArrayList<TemperatureThreshold> temperatureThresholds) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                TemperatureThreshold.writeVectorToParcel(_hidl_reply, temperatureThresholds);
                _hidl_reply.send();
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$6 */
        /* loaded from: classes6.dex */
        class AnonymousClass6 implements IThermal.getCurrentCoolingDevicesCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass6(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // android.hardware.thermal.V2_0.IThermal.getCurrentCoolingDevicesCallback
            public void onValues(ThermalStatus status, ArrayList<android.hardware.thermal.V2_0.CoolingDevice> devices) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                android.hardware.thermal.V2_0.CoolingDevice.writeVectorToParcel(_hidl_reply, devices);
                _hidl_reply.send();
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$7 */
        /* loaded from: classes6.dex */
        class AnonymousClass7 implements sehGetTemperaturesCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass7(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal.sehGetTemperaturesCallback
            public void onValues(ThermalStatus status, ArrayList<SehTemperature> temperatures) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                SehTemperature.writeVectorToParcel(_hidl_reply, temperatures);
                _hidl_reply.send();
            }
        }

        /* renamed from: vendor.samsung.hardware.thermal.V1_0.ISehThermal$Stub$8 */
        /* loaded from: classes6.dex */
        class AnonymousClass8 implements sehGetTypeTemperaturesCallback {
            final /* synthetic */ HwParcel val$_hidl_reply;

            AnonymousClass8(HwParcel _hidl_reply2) {
                _hidl_reply = _hidl_reply2;
            }

            @Override // vendor.samsung.hardware.thermal.V1_0.ISehThermal.sehGetTypeTemperaturesCallback
            public void onValues(ThermalStatus status, ArrayList<SehTemperature> temperatures) {
                _hidl_reply.writeStatus(0);
                status.writeToParcel(_hidl_reply);
                SehTemperature.writeVectorToParcel(_hidl_reply, temperatures);
                _hidl_reply.send();
            }
        }
    }
}
