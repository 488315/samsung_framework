package android.telecom;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import com.android.internal.telecom.IConnectionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes3.dex */
public final class RemoteConference {
    private final Set<CallbackRecord<Callback>> mCallbackRecords;
    private final List<RemoteConnection> mChildConnections;
    private final List<RemoteConnection> mConferenceableConnections;
    private int mConnectionCapabilities;
    private int mConnectionProperties;
    private final IConnectionService mConnectionService;
    private DisconnectCause mDisconnectCause;
    private Bundle mExtras;
    private final String mId;
    private int mState;
    private final List<RemoteConnection> mUnmodifiableChildConnections;
    private final List<RemoteConnection> mUnmodifiableConferenceableConnections;

    public static abstract class Callback {
        public void onStateChanged(RemoteConference conference, int oldState, int newState) {
        }

        public void onDisconnected(RemoteConference conference, DisconnectCause disconnectCause) {
        }

        public void onConnectionAdded(RemoteConference conference, RemoteConnection connection) {
        }

        public void onConnectionRemoved(RemoteConference conference, RemoteConnection connection) {
        }

        public void onConnectionCapabilitiesChanged(RemoteConference conference, int connectionCapabilities) {
        }

        public void onConnectionPropertiesChanged(RemoteConference conference, int connectionProperties) {
        }

        public void onConferenceableConnectionsChanged(RemoteConference conference, List<RemoteConnection> conferenceableConnections) {
        }

        public void onDestroyed(RemoteConference conference) {
        }

        public void onExtrasChanged(RemoteConference conference, Bundle extras) {
        }
    }

    RemoteConference(String id, IConnectionService connectionService) {
        this.mCallbackRecords = new CopyOnWriteArraySet();
        this.mChildConnections = new CopyOnWriteArrayList();
        this.mUnmodifiableChildConnections = Collections.unmodifiableList(this.mChildConnections);
        this.mConferenceableConnections = new ArrayList();
        this.mUnmodifiableConferenceableConnections = Collections.unmodifiableList(this.mConferenceableConnections);
        this.mState = 1;
        this.mId = id;
        this.mConnectionService = connectionService;
    }

    RemoteConference(DisconnectCause disconnectCause) {
        this.mCallbackRecords = new CopyOnWriteArraySet();
        this.mChildConnections = new CopyOnWriteArrayList();
        this.mUnmodifiableChildConnections = Collections.unmodifiableList(this.mChildConnections);
        this.mConferenceableConnections = new ArrayList();
        this.mUnmodifiableConferenceableConnections = Collections.unmodifiableList(this.mConferenceableConnections);
        this.mState = 1;
        this.mId = "NULL";
        this.mConnectionService = null;
        this.mState = 6;
        this.mDisconnectCause = disconnectCause;
    }

    String getId() {
        return this.mId;
    }

    void setDestroyed() {
        for (RemoteConnection connection : this.mChildConnections) {
            connection.setConference(null);
        }
        for (CallbackRecord<Callback> record : this.mCallbackRecords) {
            final Callback callback = record.getCallback();
            record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.1
                @Override // java.lang.Runnable
                public void run() {
                    callback.onDestroyed(conference);
                }
            });
        }
    }

    void setState(final int newState) {
        if (newState != 4 && newState != 5 && newState != 6) {
            Log.w(this, "Unsupported state transition for Conference call.", Connection.stateToString(newState));
            return;
        }
        if (this.mState != newState) {
            final int oldState = this.mState;
            this.mState = newState;
            for (CallbackRecord<Callback> record : this.mCallbackRecords) {
                final Callback callback = record.getCallback();
                record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.2
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onStateChanged(conference, oldState, newState);
                    }
                });
            }
        }
    }

    void addConnection(final RemoteConnection connection) {
        if (!this.mChildConnections.contains(connection)) {
            this.mChildConnections.add(connection);
            connection.setConference(this);
            for (CallbackRecord<Callback> record : this.mCallbackRecords) {
                final Callback callback = record.getCallback();
                record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.3
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionAdded(conference, connection);
                    }
                });
            }
        }
    }

    void removeConnection(final RemoteConnection connection) {
        if (this.mChildConnections.contains(connection)) {
            this.mChildConnections.remove(connection);
            connection.setConference(null);
            for (CallbackRecord<Callback> record : this.mCallbackRecords) {
                final Callback callback = record.getCallback();
                record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.4
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionRemoved(conference, connection);
                    }
                });
            }
        }
    }

    void setConnectionCapabilities(int connectionCapabilities) {
        if (this.mConnectionCapabilities != connectionCapabilities) {
            this.mConnectionCapabilities = connectionCapabilities;
            for (CallbackRecord<Callback> record : this.mCallbackRecords) {
                final Callback callback = record.getCallback();
                record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.5
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionCapabilitiesChanged(conference, RemoteConference.this.mConnectionCapabilities);
                    }
                });
            }
        }
    }

    void setConnectionProperties(int connectionProperties) {
        if (this.mConnectionProperties != connectionProperties) {
            this.mConnectionProperties = connectionProperties;
            for (CallbackRecord<Callback> record : this.mCallbackRecords) {
                final Callback callback = record.getCallback();
                record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.6
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConnectionPropertiesChanged(conference, RemoteConference.this.mConnectionProperties);
                    }
                });
            }
        }
    }

    void setConferenceableConnections(List<RemoteConnection> conferenceableConnections) {
        this.mConferenceableConnections.clear();
        this.mConferenceableConnections.addAll(conferenceableConnections);
        for (CallbackRecord<Callback> record : this.mCallbackRecords) {
            final Callback callback = record.getCallback();
            record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.7
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConferenceableConnectionsChanged(conference, RemoteConference.this.mUnmodifiableConferenceableConnections);
                }
            });
        }
    }

    void setDisconnected(final DisconnectCause disconnectCause) {
        if (this.mState != 6) {
            this.mDisconnectCause = disconnectCause;
            setState(6);
            for (CallbackRecord<Callback> record : this.mCallbackRecords) {
                final Callback callback = record.getCallback();
                record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.8
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onDisconnected(conference, disconnectCause);
                    }
                });
            }
        }
    }

    void putExtras(Bundle extras) {
        if (extras == null) {
            return;
        }
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putAll(extras);
        notifyExtrasChanged();
    }

    void removeExtras(List<String> keys) {
        if (this.mExtras == null || keys == null || keys.isEmpty()) {
            return;
        }
        for (String key : keys) {
            this.mExtras.remove(key);
        }
        notifyExtrasChanged();
    }

    private void notifyExtrasChanged() {
        for (CallbackRecord<Callback> record : this.mCallbackRecords) {
            final Callback callback = record.getCallback();
            record.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConference.9
                @Override // java.lang.Runnable
                public void run() {
                    callback.onExtrasChanged(conference, RemoteConference.this.mExtras);
                }
            });
        }
    }

    public final List<RemoteConnection> getConnections() {
        return this.mUnmodifiableChildConnections;
    }

    public final int getState() {
        return this.mState;
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    public void disconnect() {
        try {
            this.mConnectionService.disconnect(this.mId, null);
        } catch (RemoteException e) {
        }
    }

    public void separate(RemoteConnection connection) {
        if (this.mChildConnections.contains(connection)) {
            try {
                this.mConnectionService.splitFromConference(connection.getId(), null);
            } catch (RemoteException e) {
            }
        }
    }

    public void merge() {
        try {
            this.mConnectionService.mergeConference(this.mId, null);
        } catch (RemoteException e) {
        }
    }

    public void swap() {
        try {
            this.mConnectionService.swapConference(this.mId, null);
        } catch (RemoteException e) {
        }
    }

    public void hold() {
        try {
            this.mConnectionService.hold(this.mId, null);
        } catch (RemoteException e) {
        }
    }

    public void unhold() {
        try {
            this.mConnectionService.unhold(this.mId, null);
        } catch (RemoteException e) {
        }
    }

    public DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public void playDtmfTone(char digit) {
        try {
            this.mConnectionService.playDtmfTone(this.mId, digit, null);
        } catch (RemoteException e) {
        }
    }

    public void stopDtmfTone() {
        try {
            this.mConnectionService.stopDtmfTone(this.mId, null);
        } catch (RemoteException e) {
        }
    }

    @SystemApi
    @Deprecated
    public void setAudioState(AudioState state) {
        setCallAudioState(new CallAudioState(state));
    }

    public void setCallAudioState(CallAudioState state) {
        try {
            this.mConnectionService.onCallAudioStateChanged(this.mId, state, null);
        } catch (RemoteException e) {
        }
    }

    public List<RemoteConnection> getConferenceableConnections() {
        return this.mUnmodifiableConferenceableConnections;
    }

    public final void registerCallback(Callback callback) {
        registerCallback(callback, new Handler());
    }

    public final void registerCallback(Callback callback, Handler handler) {
        unregisterCallback(callback);
        if (callback != null && handler != null) {
            this.mCallbackRecords.add(new CallbackRecord<>(callback, handler));
        }
    }

    public final void unregisterCallback(Callback callback) {
        if (callback != null) {
            for (CallbackRecord<Callback> record : this.mCallbackRecords) {
                if (record.getCallback() == callback) {
                    this.mCallbackRecords.remove(record);
                    return;
                }
            }
        }
    }

    public static RemoteConference failure(DisconnectCause disconnectCause) {
        return new RemoteConference(disconnectCause);
    }
}
