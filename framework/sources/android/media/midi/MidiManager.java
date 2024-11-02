package android.media.midi;

import android.bluetooth.BluetoothDevice;
import android.media.midi.IMidiDeviceListener;
import android.media.midi.IMidiDeviceOpenCallback;
import android.media.midi.MidiDeviceServer;
import android.media.midi.MidiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class MidiManager {
    public static final String BLUETOOTH_MIDI_SERVICE_CLASS = "com.android.bluetoothmidiservice.BluetoothMidiService";
    public static final String BLUETOOTH_MIDI_SERVICE_INTENT = "android.media.midi.BluetoothMidiService";
    public static final String BLUETOOTH_MIDI_SERVICE_PACKAGE = "com.android.bluetoothmidiservice";
    private static final String TAG = "MidiManager";
    public static final int TRANSPORT_MIDI_BYTE_STREAM = 1;
    public static final int TRANSPORT_UNIVERSAL_MIDI_PACKETS = 2;
    private final IMidiManager mService;
    private final IBinder mToken = new Binder();
    private ConcurrentHashMap<DeviceCallback, DeviceListener> mDeviceListeners = new ConcurrentHashMap<>();

    /* loaded from: classes2.dex */
    public interface OnDeviceOpenedListener {
        void onDeviceOpened(MidiDevice midiDevice);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Transport {
    }

    /* loaded from: classes2.dex */
    public class DeviceListener extends IMidiDeviceListener.Stub {
        private final DeviceCallback mCallback;
        private final Executor mExecutor;
        private final int mTransport;

        DeviceListener(DeviceCallback callback, Executor executor, int transport) {
            this.mCallback = callback;
            this.mExecutor = executor;
            this.mTransport = transport;
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceAdded(final MidiDeviceInfo device) {
            if (shouldInvokeCallback(device)) {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MidiManager.DeviceListener.this.lambda$onDeviceAdded$0(device);
                        }
                    });
                } else {
                    this.mCallback.onDeviceAdded(device);
                }
            }
        }

        public /* synthetic */ void lambda$onDeviceAdded$0(MidiDeviceInfo device) {
            this.mCallback.onDeviceAdded(device);
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceRemoved(final MidiDeviceInfo device) {
            if (shouldInvokeCallback(device)) {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MidiManager.DeviceListener.this.lambda$onDeviceRemoved$1(device);
                        }
                    });
                } else {
                    this.mCallback.onDeviceRemoved(device);
                }
            }
        }

        public /* synthetic */ void lambda$onDeviceRemoved$1(MidiDeviceInfo device) {
            this.mCallback.onDeviceRemoved(device);
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceStatusChanged(final MidiDeviceStatus status) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MidiManager.DeviceListener.this.lambda$onDeviceStatusChanged$2(status);
                    }
                });
            } else {
                this.mCallback.onDeviceStatusChanged(status);
            }
        }

        public /* synthetic */ void lambda$onDeviceStatusChanged$2(MidiDeviceStatus status) {
            this.mCallback.onDeviceStatusChanged(status);
        }

        private boolean shouldInvokeCallback(MidiDeviceInfo device) {
            int i = this.mTransport;
            if (i == 2) {
                return device.getDefaultProtocol() != -1;
            }
            if (i == 1) {
                return device.getDefaultProtocol() == -1;
            }
            Log.e(MidiManager.TAG, "Invalid transport type: " + this.mTransport);
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public static class DeviceCallback {
        public void onDeviceAdded(MidiDeviceInfo device) {
        }

        public void onDeviceRemoved(MidiDeviceInfo device) {
        }

        public void onDeviceStatusChanged(MidiDeviceStatus status) {
        }
    }

    public MidiManager(IMidiManager service) {
        this.mService = service;
    }

    @Deprecated
    public void registerDeviceCallback(DeviceCallback callback, Handler handler) {
        Executor executor = null;
        if (handler != null) {
            Objects.requireNonNull(handler);
            executor = new MidiManager$$ExternalSyntheticLambda0(handler);
        }
        DeviceListener deviceListener = new DeviceListener(callback, executor, 1);
        try {
            this.mService.registerListener(this.mToken, deviceListener);
            this.mDeviceListeners.put(callback, deviceListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerDeviceCallback(int transport, Executor executor, DeviceCallback callback) {
        Objects.requireNonNull(executor);
        DeviceListener deviceListener = new DeviceListener(callback, executor, transport);
        try {
            this.mService.registerListener(this.mToken, deviceListener);
            this.mDeviceListeners.put(callback, deviceListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterDeviceCallback(DeviceCallback callback) {
        DeviceListener deviceListener = this.mDeviceListeners.remove(callback);
        if (deviceListener != null) {
            try {
                this.mService.unregisterListener(this.mToken, deviceListener);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public MidiDeviceInfo[] getDevices() {
        try {
            return this.mService.getDevices();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<MidiDeviceInfo> getDevicesForTransport(int transport) {
        try {
            MidiDeviceInfo[] devices = this.mService.getDevicesForTransport(transport);
            if (devices == null) {
                return Collections.emptySet();
            }
            return new ArraySet(devices);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.media.midi.MidiManager$1 */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 implements Runnable {
        final /* synthetic */ MidiDevice val$device;
        final /* synthetic */ OnDeviceOpenedListener val$listener;

        AnonymousClass1(OnDeviceOpenedListener onDeviceOpenedListener, MidiDevice midiDevice) {
            listener = onDeviceOpenedListener;
            device = midiDevice;
        }

        @Override // java.lang.Runnable
        public void run() {
            listener.onDeviceOpened(device);
        }
    }

    public void sendOpenDeviceResponse(MidiDevice device, OnDeviceOpenedListener listener, Handler handler) {
        if (handler != null) {
            handler.post(new Runnable() { // from class: android.media.midi.MidiManager.1
                final /* synthetic */ MidiDevice val$device;
                final /* synthetic */ OnDeviceOpenedListener val$listener;

                AnonymousClass1(OnDeviceOpenedListener listener2, MidiDevice device2) {
                    listener = listener2;
                    device = device2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    listener.onDeviceOpened(device);
                }
            });
        } else {
            listener2.onDeviceOpened(device2);
        }
    }

    public void openDevice(MidiDeviceInfo deviceInfo, OnDeviceOpenedListener listener, Handler handler) {
        IMidiDeviceOpenCallback callback = new IMidiDeviceOpenCallback.Stub() { // from class: android.media.midi.MidiManager.2
            final /* synthetic */ MidiDeviceInfo val$deviceInfoF;
            final /* synthetic */ Handler val$handlerF;
            final /* synthetic */ OnDeviceOpenedListener val$listenerF;

            AnonymousClass2(MidiDeviceInfo deviceInfo2, OnDeviceOpenedListener listener2, Handler handler2) {
                deviceInfo = deviceInfo2;
                listener = listener2;
                handler = handler2;
            }

            @Override // android.media.midi.IMidiDeviceOpenCallback
            public void onDeviceOpened(IMidiDeviceServer server, IBinder deviceToken) {
                MidiDevice device;
                if (server != null) {
                    device = new MidiDevice(deviceInfo, server, MidiManager.this.mService, MidiManager.this.mToken, deviceToken);
                } else {
                    device = null;
                }
                MidiManager.this.sendOpenDeviceResponse(device, listener, handler);
            }
        };
        try {
            this.mService.openDevice(this.mToken, deviceInfo2, callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.media.midi.MidiManager$2 */
    /* loaded from: classes2.dex */
    class AnonymousClass2 extends IMidiDeviceOpenCallback.Stub {
        final /* synthetic */ MidiDeviceInfo val$deviceInfoF;
        final /* synthetic */ Handler val$handlerF;
        final /* synthetic */ OnDeviceOpenedListener val$listenerF;

        AnonymousClass2(MidiDeviceInfo deviceInfo2, OnDeviceOpenedListener listener2, Handler handler2) {
            deviceInfo = deviceInfo2;
            listener = listener2;
            handler = handler2;
        }

        @Override // android.media.midi.IMidiDeviceOpenCallback
        public void onDeviceOpened(IMidiDeviceServer server, IBinder deviceToken) {
            MidiDevice device;
            if (server != null) {
                device = new MidiDevice(deviceInfo, server, MidiManager.this.mService, MidiManager.this.mToken, deviceToken);
            } else {
                device = null;
            }
            MidiManager.this.sendOpenDeviceResponse(device, listener, handler);
        }
    }

    public void openBluetoothDevice(BluetoothDevice bluetoothDevice, OnDeviceOpenedListener listener, Handler handler) {
        Log.d(TAG, "openBluetoothDevice() " + bluetoothDevice);
        IMidiDeviceOpenCallback callback = new IMidiDeviceOpenCallback.Stub() { // from class: android.media.midi.MidiManager.3
            final /* synthetic */ Handler val$handlerF;
            final /* synthetic */ OnDeviceOpenedListener val$listenerF;

            AnonymousClass3(OnDeviceOpenedListener listener2, Handler handler2) {
                listener = listener2;
                handler = handler2;
            }

            @Override // android.media.midi.IMidiDeviceOpenCallback
            public void onDeviceOpened(IMidiDeviceServer server, IBinder deviceToken) {
                Log.d(MidiManager.TAG, "onDeviceOpened() server:" + server);
                MidiDevice device = null;
                if (server != null) {
                    try {
                        MidiDeviceInfo deviceInfo = server.getDeviceInfo();
                        device = new MidiDevice(deviceInfo, server, MidiManager.this.mService, MidiManager.this.mToken, deviceToken);
                    } catch (RemoteException e) {
                        Log.e(MidiManager.TAG, "remote exception in getDeviceInfo()");
                    }
                }
                MidiManager.this.sendOpenDeviceResponse(device, listener, handler);
            }
        };
        try {
            this.mService.openBluetoothDevice(this.mToken, bluetoothDevice, callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.media.midi.MidiManager$3 */
    /* loaded from: classes2.dex */
    class AnonymousClass3 extends IMidiDeviceOpenCallback.Stub {
        final /* synthetic */ Handler val$handlerF;
        final /* synthetic */ OnDeviceOpenedListener val$listenerF;

        AnonymousClass3(OnDeviceOpenedListener listener2, Handler handler2) {
            listener = listener2;
            handler = handler2;
        }

        @Override // android.media.midi.IMidiDeviceOpenCallback
        public void onDeviceOpened(IMidiDeviceServer server, IBinder deviceToken) {
            Log.d(MidiManager.TAG, "onDeviceOpened() server:" + server);
            MidiDevice device = null;
            if (server != null) {
                try {
                    MidiDeviceInfo deviceInfo = server.getDeviceInfo();
                    device = new MidiDevice(deviceInfo, server, MidiManager.this.mService, MidiManager.this.mToken, deviceToken);
                } catch (RemoteException e) {
                    Log.e(MidiManager.TAG, "remote exception in getDeviceInfo()");
                }
            }
            MidiManager.this.sendOpenDeviceResponse(device, listener, handler);
        }
    }

    public void closeBluetoothDevice(MidiDevice midiDevice) {
        try {
            midiDevice.close();
        } catch (IOException ex) {
            Log.e(TAG, "Exception closing BLE-MIDI device" + ex);
        }
    }

    public MidiDeviceServer createDeviceServer(MidiReceiver[] inputPortReceivers, int numOutputPorts, String[] inputPortNames, String[] outputPortNames, Bundle properties, int type, int defaultProtocol, MidiDeviceServer.Callback callback) {
        try {
        } catch (RemoteException e) {
            e = e;
        }
        try {
            MidiDeviceServer server = new MidiDeviceServer(this.mService, inputPortReceivers, numOutputPorts, callback);
            MidiDeviceInfo deviceInfo = this.mService.registerDeviceServer(server.getBinderInterface(), inputPortReceivers.length, numOutputPorts, inputPortNames, outputPortNames, properties, type, defaultProtocol);
            if (deviceInfo == null) {
                Log.e(TAG, "registerVirtualDevice failed");
                return null;
            }
            return server;
        } catch (RemoteException e2) {
            e = e2;
            throw e.rethrowFromSystemServer();
        }
    }
}
