package com.android.server.timezonedetector.location;

import android.os.SystemClock;
import android.service.timezone.TimeZoneProviderEvent;
import android.service.timezone.TimeZoneProviderStatus;
import com.android.server.timezonedetector.ConfigurationInternal;
import com.android.server.timezonedetector.Dumpable;
import com.android.server.timezonedetector.ReferenceWithHistory;
import com.android.server.timezonedetector.location.ThreadingDomain;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public abstract class LocationTimeZoneProvider implements Dumpable {
    public final ThreadingDomain.SingleRunnableQueue mInitializationTimeoutQueue;
    public ProviderListener mProviderListener;
    public final ProviderMetricsLogger mProviderMetricsLogger;
    public final String mProviderName;
    public final boolean mRecordStateChanges;
    public final Object mSharedLock;
    public final ThreadingDomain mThreadingDomain;
    public final TimeZoneProviderEventPreProcessor mTimeZoneProviderEventPreProcessor;
    public final ArrayList mRecordedStates = new ArrayList(0);
    public final ReferenceWithHistory mCurrentState = new ReferenceWithHistory(10);

    /* loaded from: classes3.dex */
    public interface ProviderListener {
        void onProviderStateChange(ProviderState providerState);
    }

    /* loaded from: classes3.dex */
    public interface ProviderMetricsLogger {
        void onProviderStateChanged(int i);
    }

    public abstract void onDestroy();

    public abstract boolean onInitialize();

    public void onSetCurrentState(ProviderState providerState) {
    }

    public abstract void onStartUpdates(Duration duration, Duration duration2);

    public abstract void onStopUpdates();

    /* loaded from: classes3.dex */
    public class ProviderState {
        public final ConfigurationInternal currentUserConfiguration;
        public final TimeZoneProviderEvent event;
        public final String mDebugInfo;
        public final long mStateEntryTimeMillis;
        public final LocationTimeZoneProvider provider;
        public final int stateEnum;

        public ProviderState(LocationTimeZoneProvider locationTimeZoneProvider, int i, TimeZoneProviderEvent timeZoneProviderEvent, ConfigurationInternal configurationInternal, String str) {
            Objects.requireNonNull(locationTimeZoneProvider);
            this.provider = locationTimeZoneProvider;
            this.stateEnum = i;
            this.event = timeZoneProviderEvent;
            this.currentUserConfiguration = configurationInternal;
            this.mStateEntryTimeMillis = SystemClock.elapsedRealtime();
            this.mDebugInfo = str;
        }

        public static ProviderState createStartingState(LocationTimeZoneProvider locationTimeZoneProvider) {
            return new ProviderState(locationTimeZoneProvider, 0, null, null, "Initial state");
        }

        public ProviderState newState(int i, TimeZoneProviderEvent timeZoneProviderEvent, ConfigurationInternal configurationInternal, String str) {
            switch (this.stateEnum) {
                case 0:
                    if (i != 4) {
                        throw new IllegalArgumentException("Must transition from " + prettyPrintStateEnum(0) + " to " + prettyPrintStateEnum(4));
                    }
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    break;
                case 5:
                case 6:
                    throw new IllegalArgumentException("Illegal transition out of " + prettyPrintStateEnum(this.stateEnum));
                default:
                    throw new IllegalArgumentException("Invalid this.stateEnum=" + this.stateEnum);
            }
            switch (i) {
                case 0:
                    throw new IllegalArgumentException("Cannot transition to " + prettyPrintStateEnum(0));
                case 1:
                case 2:
                case 3:
                    if (configurationInternal == null) {
                        throw new IllegalArgumentException("Started state: currentUserConfig must not be null");
                    }
                    break;
                case 4:
                    if (timeZoneProviderEvent != null || configurationInternal != null) {
                        throw new IllegalArgumentException("Stopped state: event and currentUserConfig must be null, event=" + timeZoneProviderEvent + ", currentUserConfig=" + configurationInternal);
                    }
                case 5:
                case 6:
                    if (timeZoneProviderEvent != null || configurationInternal != null) {
                        throw new IllegalArgumentException("Terminal state: event and currentUserConfig must be null, newStateEnum=" + i + ", event=" + timeZoneProviderEvent + ", currentUserConfig=" + configurationInternal);
                    }
                default:
                    throw new IllegalArgumentException("Unknown newStateEnum=" + i);
            }
            return new ProviderState(this.provider, i, timeZoneProviderEvent, configurationInternal, str);
        }

        public boolean isStarted() {
            int i = this.stateEnum;
            return i == 1 || i == 2 || i == 3;
        }

        public boolean isTerminated() {
            int i = this.stateEnum;
            return i == 5 || i == 6;
        }

        public int getProviderStatus() {
            switch (this.stateEnum) {
                case 1:
                    return 2;
                case 2:
                    return 3;
                case 3:
                    return 4;
                case 4:
                case 6:
                    return 2;
                case 5:
                    return 1;
                default:
                    throw new IllegalStateException("Unknown state enum:" + prettyPrintStateEnum(this.stateEnum));
            }
        }

        public TimeZoneProviderStatus getReportedStatus() {
            TimeZoneProviderEvent timeZoneProviderEvent = this.event;
            if (timeZoneProviderEvent == null) {
                return null;
            }
            return timeZoneProviderEvent.getTimeZoneProviderStatus();
        }

        public String toString() {
            return "ProviderState{stateEnum=" + prettyPrintStateEnum(this.stateEnum) + ", event=" + this.event + ", currentUserConfiguration=" + this.currentUserConfiguration + ", mStateEntryTimeMillis=" + this.mStateEntryTimeMillis + ", mDebugInfo=" + this.mDebugInfo + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProviderState providerState = (ProviderState) obj;
            return this.stateEnum == providerState.stateEnum && Objects.equals(this.event, providerState.event) && Objects.equals(this.currentUserConfiguration, providerState.currentUserConfiguration);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.stateEnum), this.event, this.currentUserConfiguration);
        }

        public static String prettyPrintStateEnum(int i) {
            switch (i) {
                case 1:
                    return "Started initializing (1)";
                case 2:
                    return "Started certain (2)";
                case 3:
                    return "Started uncertain (3)";
                case 4:
                    return "Stopped (4)";
                case 5:
                    return "Perm failure (5)";
                case 6:
                    return "Destroyed (6)";
                default:
                    return "Unknown (" + i + ")";
            }
        }
    }

    public LocationTimeZoneProvider(ProviderMetricsLogger providerMetricsLogger, ThreadingDomain threadingDomain, String str, TimeZoneProviderEventPreProcessor timeZoneProviderEventPreProcessor, boolean z) {
        Objects.requireNonNull(threadingDomain);
        this.mThreadingDomain = threadingDomain;
        Objects.requireNonNull(providerMetricsLogger);
        this.mProviderMetricsLogger = providerMetricsLogger;
        this.mInitializationTimeoutQueue = threadingDomain.createSingleRunnableQueue();
        this.mSharedLock = threadingDomain.getLockObject();
        Objects.requireNonNull(str);
        this.mProviderName = str;
        Objects.requireNonNull(timeZoneProviderEventPreProcessor);
        this.mTimeZoneProviderEventPreProcessor = timeZoneProviderEventPreProcessor;
        this.mRecordStateChanges = z;
    }

    public final void initialize(ProviderListener providerListener) {
        String str;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            if (this.mProviderListener != null) {
                throw new IllegalStateException("initialize already called");
            }
            Objects.requireNonNull(providerListener);
            this.mProviderListener = providerListener;
            ProviderState newState = ProviderState.createStartingState(this).newState(4, null, null, "initialize");
            boolean z = false;
            setCurrentState(newState, false);
            try {
                str = "onInitialize() returned false";
                z = onInitialize();
            } catch (RuntimeException e) {
                LocationTimeZoneManagerService.warnLog("Unable to initialize the provider due to exception", e);
                str = "onInitialize() threw exception:" + e.getMessage();
            }
            if (!z) {
                setCurrentState(newState.newState(5, null, null, "Failed to initialize: " + str), true);
            }
        }
    }

    public final void destroy() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            ProviderState providerState = (ProviderState) this.mCurrentState.get();
            if (!providerState.isTerminated()) {
                setCurrentState(providerState.newState(6, null, null, "destroy"), false);
                onDestroy();
            }
        }
    }

    public final void clearRecordedStates() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mRecordedStates.clear();
            this.mRecordedStates.trimToSize();
        }
    }

    public final List getRecordedStates() {
        ArrayList arrayList;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            arrayList = new ArrayList(this.mRecordedStates);
        }
        return arrayList;
    }

    public final void setCurrentState(ProviderState providerState, boolean z) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            ProviderState providerState2 = (ProviderState) this.mCurrentState.get();
            this.mCurrentState.set(providerState);
            onSetCurrentState(providerState);
            if (!Objects.equals(providerState, providerState2)) {
                this.mProviderMetricsLogger.onProviderStateChanged(providerState.stateEnum);
                if (this.mRecordStateChanges) {
                    this.mRecordedStates.add(providerState);
                }
                if (z) {
                    this.mProviderListener.onProviderStateChange(providerState);
                }
            }
        }
    }

    public final ProviderState getCurrentState() {
        ProviderState providerState;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            providerState = (ProviderState) this.mCurrentState.get();
        }
        return providerState;
    }

    public final String getName() {
        this.mThreadingDomain.assertCurrentThread();
        return this.mProviderName;
    }

    public final void startUpdates(ConfigurationInternal configurationInternal, Duration duration, Duration duration2, Duration duration3) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            assertCurrentState(4);
            setCurrentState(((ProviderState) this.mCurrentState.get()).newState(1, null, configurationInternal, "startUpdates"), false);
            this.mInitializationTimeoutQueue.runDelayed(new Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocationTimeZoneProvider.this.handleInitializationTimeout();
                }
            }, duration.plus(duration2).toMillis());
            onStartUpdates(duration, duration3);
        }
    }

    public final void handleInitializationTimeout() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            ProviderState providerState = (ProviderState) this.mCurrentState.get();
            if (providerState.stateEnum == 1) {
                setCurrentState(providerState.newState(3, null, providerState.currentUserConfiguration, "handleInitializationTimeout"), true);
            } else {
                LocationTimeZoneManagerService.warnLog("handleInitializationTimeout: Initialization timeout triggered when in an unexpected state=" + providerState);
            }
        }
    }

    public final void stopUpdates() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            assertIsStarted();
            setCurrentState(((ProviderState) this.mCurrentState.get()).newState(4, null, null, "stopUpdates"), false);
            cancelInitializationTimeoutIfSet();
            onStopUpdates();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00fc, code lost:
    
        r5 = 3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleTimeZoneProviderEvent(android.service.timezone.TimeZoneProviderEvent r10) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.timezonedetector.location.LocationTimeZoneProvider.handleTimeZoneProviderEvent(android.service.timezone.TimeZoneProviderEvent):void");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x0012. Please report as an issue. */
    public final void handleTemporaryFailure(String str) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            ProviderState providerState = (ProviderState) this.mCurrentState.get();
            switch (providerState.stateEnum) {
                case 1:
                case 2:
                case 3:
                    setCurrentState(providerState.newState(3, null, providerState.currentUserConfiguration, "handleTemporaryFailure: reason=" + str + ", currentState=" + ProviderState.prettyPrintStateEnum(providerState.stateEnum)), true);
                    cancelInitializationTimeoutIfSet();
                    break;
                case 4:
                    LocationTimeZoneManagerService.debugLog("handleProviderLost reason=" + str + ", mProviderName=" + this.mProviderName + ", currentState=" + providerState + ": No state change required, provider is stopped.");
                    break;
                case 5:
                case 6:
                    LocationTimeZoneManagerService.debugLog("handleProviderLost reason=" + str + ", mProviderName=" + this.mProviderName + ", currentState=" + providerState + ": No state change required, provider is terminated.");
                    break;
                default:
                    throw new IllegalStateException("Unknown currentState=" + providerState);
            }
        }
    }

    public final void assertIsStarted() {
        ProviderState providerState = (ProviderState) this.mCurrentState.get();
        if (providerState.isStarted()) {
            return;
        }
        throw new IllegalStateException("Required a started state, but was " + providerState);
    }

    public final void assertCurrentState(int i) {
        ProviderState providerState = (ProviderState) this.mCurrentState.get();
        if (providerState.stateEnum == i) {
            return;
        }
        throw new IllegalStateException("Required stateEnum=" + i + ", but was " + providerState);
    }

    public boolean isInitializationTimeoutSet() {
        boolean hasQueued;
        synchronized (this.mSharedLock) {
            hasQueued = this.mInitializationTimeoutQueue.hasQueued();
        }
        return hasQueued;
    }

    public final void cancelInitializationTimeoutIfSet() {
        if (this.mInitializationTimeoutQueue.hasQueued()) {
            this.mInitializationTimeoutQueue.cancel();
        }
    }

    public Duration getInitializationTimeoutDelay() {
        Duration ofMillis;
        synchronized (this.mSharedLock) {
            ofMillis = Duration.ofMillis(this.mInitializationTimeoutQueue.getQueuedDelayMillis());
        }
        return ofMillis;
    }
}
