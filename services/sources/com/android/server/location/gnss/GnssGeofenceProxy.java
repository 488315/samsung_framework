package com.android.server.location.gnss;

import android.location.IGpsGeofenceHardware;
import android.util.SparseArray;
import com.android.server.location.gnss.hal.GnssNative;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GnssGeofenceProxy extends IGpsGeofenceHardware.Stub implements GnssNative.BaseCallbacks {
    public final GnssNative mGnssNative;
    public final Object mLock = new Object();
    public final SparseArray mGeofenceEntries = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GeofenceEntry {
        public int geofenceId;
        public int lastTransition;
        public double latitude;
        public double longitude;
        public int monitorTransitions;
        public int notificationResponsiveness;
        public boolean paused;
        public double radius;
        public int unknownTimer;
    }

    public GnssGeofenceProxy(GnssNative gnssNative) {
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
    }

    public final boolean addCircularHardwareGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) {
        boolean addGeofence;
        synchronized (this.mLock) {
            try {
                addGeofence = this.mGnssNative.addGeofence(i, d, d2, d3, i2, i3, i4, i5);
                if (addGeofence) {
                    GeofenceEntry geofenceEntry = new GeofenceEntry();
                    geofenceEntry.geofenceId = i;
                    geofenceEntry.latitude = d;
                    geofenceEntry.longitude = d2;
                    geofenceEntry.radius = d3;
                    geofenceEntry.lastTransition = i2;
                    geofenceEntry.monitorTransitions = i3;
                    geofenceEntry.notificationResponsiveness = i4;
                    geofenceEntry.unknownTimer = i5;
                    this.mGeofenceEntries.put(i, geofenceEntry);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return addGeofence;
    }

    public final boolean isHardwareGeofenceSupported() {
        synchronized (this.mLock) {
        }
        return false;
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public final void onHalRestarted() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mGeofenceEntries.size(); i++) {
                try {
                    GeofenceEntry geofenceEntry = (GeofenceEntry) this.mGeofenceEntries.valueAt(i);
                    if (this.mGnssNative.addGeofence(geofenceEntry.geofenceId, geofenceEntry.latitude, geofenceEntry.longitude, geofenceEntry.radius, geofenceEntry.lastTransition, geofenceEntry.monitorTransitions, geofenceEntry.notificationResponsiveness, geofenceEntry.unknownTimer) && geofenceEntry.paused) {
                        this.mGnssNative.pauseGeofence(geofenceEntry.geofenceId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final boolean pauseHardwareGeofence(int i) {
        boolean pauseGeofence;
        GeofenceEntry geofenceEntry;
        synchronized (this.mLock) {
            try {
                pauseGeofence = this.mGnssNative.pauseGeofence(i);
                if (pauseGeofence && (geofenceEntry = (GeofenceEntry) this.mGeofenceEntries.get(i)) != null) {
                    geofenceEntry.paused = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return pauseGeofence;
    }

    public final boolean removeHardwareGeofence(int i) {
        boolean removeGeofence;
        synchronized (this.mLock) {
            try {
                removeGeofence = this.mGnssNative.removeGeofence(i);
                if (removeGeofence) {
                    this.mGeofenceEntries.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return removeGeofence;
    }

    public final boolean resumeHardwareGeofence(int i, int i2) {
        boolean resumeGeofence;
        GeofenceEntry geofenceEntry;
        synchronized (this.mLock) {
            try {
                resumeGeofence = this.mGnssNative.resumeGeofence(i, i2);
                if (resumeGeofence && (geofenceEntry = (GeofenceEntry) this.mGeofenceEntries.get(i)) != null) {
                    geofenceEntry.paused = false;
                    geofenceEntry.monitorTransitions = i2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return resumeGeofence;
    }
}
