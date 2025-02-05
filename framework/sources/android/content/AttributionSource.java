package android.content;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArraySet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class AttributionSource implements Parcelable {
    private final AttributionSourceState mAttributionSourceState;
    private AttributionSource mNextCached;
    private Set<String> mRenouncedPermissionsCached;
    private static final String DESCRIPTOR = "android.content.AttributionSource";
    private static final Binder sDefaultToken = new Binder(DESCRIPTOR);
    public static final Parcelable.Creator<AttributionSource> CREATOR = new Parcelable.Creator<AttributionSource>() { // from class: android.content.AttributionSource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttributionSource[] newArray(int size) {
            return new AttributionSource[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttributionSource createFromParcel(Parcel in) {
            return new AttributionSource(in);
        }
    };

    public AttributionSource(int uid, String packageName, String attributionTag) {
        this(uid, -1, packageName, attributionTag, sDefaultToken);
    }

    public AttributionSource(int uid, String packageName, String attributionTag, int virtualDeviceId) {
        this(uid, -1, packageName, attributionTag, sDefaultToken, null, virtualDeviceId, null);
    }

    public AttributionSource(int uid, int pid, String packageName, String attributionTag) {
        this(uid, pid, packageName, attributionTag, sDefaultToken);
    }

    public AttributionSource(int uid, String packageName, String attributionTag, IBinder token) {
        this(uid, -1, packageName, attributionTag, token, null, 0, null);
    }

    public AttributionSource(int uid, int pid, String packageName, String attributionTag, IBinder token) {
        this(uid, pid, packageName, attributionTag, token, null, 0, null);
    }

    public AttributionSource(int uid, String packageName, String attributionTag, Set<String> renouncedPermissions, AttributionSource next) {
        this(uid, -1, packageName, attributionTag, sDefaultToken, renouncedPermissions != null ? (String[]) renouncedPermissions.toArray(new String[0]) : null, 0, next);
    }

    public AttributionSource(AttributionSource current, AttributionSource next) {
        this(current.getUid(), current.getPid(), current.getPackageName(), current.getAttributionTag(), current.getToken(), current.mAttributionSourceState.renouncedPermissions, current.getDeviceId(), next);
    }

    public AttributionSource(int uid, int pid, String packageName, String attributionTag, String[] renouncedPermissions, int deviceId, AttributionSource next) {
        this(uid, pid, packageName, attributionTag, sDefaultToken, renouncedPermissions, deviceId, next);
    }

    public AttributionSource(int uid, int pid, String packageName, String attributionTag, IBinder token, String[] renouncedPermissions, int deviceId, AttributionSource next) {
        this.mAttributionSourceState = new AttributionSourceState();
        this.mAttributionSourceState.uid = uid;
        this.mAttributionSourceState.pid = pid;
        this.mAttributionSourceState.token = token;
        this.mAttributionSourceState.packageName = packageName;
        this.mAttributionSourceState.attributionTag = attributionTag;
        this.mAttributionSourceState.renouncedPermissions = renouncedPermissions;
        this.mAttributionSourceState.deviceId = deviceId;
        this.mAttributionSourceState.next = next != null ? new AttributionSourceState[]{next.mAttributionSourceState} : new AttributionSourceState[0];
    }

    AttributionSource(Parcel in) {
        this(AttributionSourceState.CREATOR.createFromParcel(in));
        if (!Binder.isDirectlyHandlingTransaction()) {
            throw new SecurityException("AttributionSource should be unparceled during a binder transaction for proper verification.");
        }
        enforceCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingPid == 0) {
            this.mAttributionSourceState.pid = -1;
        }
        enforceCallingPid();
    }

    public AttributionSource(AttributionSourceState attributionSourceState) {
        this.mAttributionSourceState = attributionSourceState;
    }

    public AttributionSource withNextAttributionSource(AttributionSource next) {
        return new AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), next);
    }

    public AttributionSource withPackageName(String packageName) {
        return new AttributionSource(getUid(), getPid(), packageName, getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public AttributionSource withToken(IBinder token) {
        return new AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), token, this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public AttributionSource withDefaultToken() {
        return withToken(sDefaultToken);
    }

    public AttributionSource withPid(int pid) {
        return new AttributionSource(getUid(), pid, getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public AttributionSource withDeviceId(int deviceId) {
        return new AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, deviceId, getNext());
    }

    public AttributionSourceState asState() {
        return this.mAttributionSourceState;
    }

    public ScopedParcelState asScopedParcelState() {
        return new ScopedParcelState(this);
    }

    public static AttributionSource myAttributionSource() {
        AttributionSource globalSource = ActivityThread.currentAttributionSource();
        if (globalSource != null) {
            return globalSource;
        }
        int uid = Process.myUid();
        if (uid == 0) {
            uid = 1000;
        }
        try {
            return new Builder(uid).setPid(Process.myPid()).setDeviceId(0).setPackageName(AppGlobals.getPackageManager().getPackagesForUid(uid)[0]).build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to resolve AttributionSource");
        }
    }

    public static class ScopedParcelState implements AutoCloseable {
        private final Parcel mParcel = Parcel.obtain();

        public Parcel getParcel() {
            return this.mParcel;
        }

        public ScopedParcelState(AttributionSource attributionSource) {
            attributionSource.writeToParcel(this.mParcel, 0);
            this.mParcel.setDataPosition(0);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mParcel.recycle();
        }
    }

    public void enforceCallingUid() {
        if (!checkCallingUid()) {
            throw new SecurityException("Calling uid: " + Binder.getCallingUid() + " doesn't match source uid: " + this.mAttributionSourceState.uid);
        }
    }

    public boolean checkCallingUid() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && UserHandle.getAppId(callingUid) != 1000 && callingUid != this.mAttributionSourceState.uid) {
            return false;
        }
        return true;
    }

    public void enforceCallingPid() {
        if (!checkCallingPid()) {
            if (Binder.getCallingPid() == 0) {
                throw new SecurityException("Calling pid unavailable due to oneway Binder call.");
            }
            throw new SecurityException("Calling pid: " + Binder.getCallingPid() + " doesn't match source pid: " + this.mAttributionSourceState.pid);
        }
    }

    private boolean checkCallingPid() {
        int callingPid = Binder.getCallingPid();
        if (this.mAttributionSourceState.pid != -1 && callingPid != this.mAttributionSourceState.pid) {
            return false;
        }
        return true;
    }

    public String toString() {
        boolean z = Build.IS_DEBUGGABLE;
        return "AttributionSource { uid = " + this.mAttributionSourceState.uid + ", packageName = " + this.mAttributionSourceState.packageName + ", attributionTag = " + this.mAttributionSourceState.attributionTag + ", token = " + this.mAttributionSourceState.token + ", deviceId = " + this.mAttributionSourceState.deviceId + ", next = " + ((this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) ? null : new AttributionSource(this.mAttributionSourceState.next[0]).toString()) + " }";
    }

    public int getNextUid() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].uid;
        }
        return -1;
    }

    public String getNextPackageName() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].packageName;
        }
        return null;
    }

    public String getNextAttributionTag() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].attributionTag;
        }
        return null;
    }

    public IBinder getNextToken() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].token;
        }
        return null;
    }

    public int getNextDeviceId() {
        if (this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) {
            return 0;
        }
        return this.mAttributionSourceState.next[0].deviceId;
    }

    public boolean isTrusted(Context context) {
        return this.mAttributionSourceState.token != null && ((PermissionManager) context.getSystemService(PermissionManager.class)).isRegisteredAttributionSource(this);
    }

    @SystemApi
    public Set<String> getRenouncedPermissions() {
        if (this.mRenouncedPermissionsCached == null) {
            if (this.mAttributionSourceState.renouncedPermissions != null) {
                this.mRenouncedPermissionsCached = new ArraySet(this.mAttributionSourceState.renouncedPermissions);
            } else {
                this.mRenouncedPermissionsCached = Collections.emptySet();
            }
        }
        return this.mRenouncedPermissionsCached;
    }

    public int getUid() {
        return this.mAttributionSourceState.uid;
    }

    public int getPid() {
        return this.mAttributionSourceState.pid;
    }

    public String getPackageName() {
        return this.mAttributionSourceState.packageName;
    }

    public String getAttributionTag() {
        return this.mAttributionSourceState.attributionTag;
    }

    public int getDeviceId() {
        return this.mAttributionSourceState.deviceId;
    }

    public IBinder getToken() {
        return this.mAttributionSourceState.token;
    }

    public AttributionSource getNext() {
        if (this.mNextCached == null && this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            this.mNextCached = new AttributionSource(this.mAttributionSourceState.next[0]);
        }
        return this.mNextCached;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttributionSource that = (AttributionSource) o;
        if (equalsExceptToken(that) && Objects.equals(this.mAttributionSourceState.token, that.mAttributionSourceState.token)) {
            return true;
        }
        return false;
    }

    public boolean equalsExceptToken(AttributionSource o) {
        return o != null && this.mAttributionSourceState.uid == o.mAttributionSourceState.uid && Objects.equals(this.mAttributionSourceState.packageName, o.mAttributionSourceState.packageName) && Objects.equals(this.mAttributionSourceState.attributionTag, o.mAttributionSourceState.attributionTag) && Arrays.equals(this.mAttributionSourceState.renouncedPermissions, o.mAttributionSourceState.renouncedPermissions) && Objects.equals(getNext(), o.getNext());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAttributionSourceState.uid), this.mAttributionSourceState.packageName, this.mAttributionSourceState.attributionTag, this.mAttributionSourceState.token, Integer.valueOf(Arrays.hashCode(this.mAttributionSourceState.renouncedPermissions)), getNext());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mAttributionSourceState.writeToParcel(dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private final AttributionSourceState mAttributionSourceState = new AttributionSourceState();
        private long mBuilderFieldsSet = 0;

        public Builder(int uid) {
            this.mAttributionSourceState.uid = uid;
        }

        public Builder(AttributionSource current) {
            if (current == null) {
                throw new IllegalArgumentException("current AttributionSource can not be null");
            }
            this.mAttributionSourceState.uid = current.getUid();
            this.mAttributionSourceState.pid = current.getPid();
            this.mAttributionSourceState.packageName = current.getPackageName();
            this.mAttributionSourceState.attributionTag = current.getAttributionTag();
            this.mAttributionSourceState.token = current.getToken();
            this.mAttributionSourceState.renouncedPermissions = current.mAttributionSourceState.renouncedPermissions;
        }

        public Builder setPid(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mAttributionSourceState.pid = value;
            return this;
        }

        public Builder setPackageName(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mAttributionSourceState.packageName = value;
            return this;
        }

        public Builder setAttributionTag(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mAttributionSourceState.attributionTag = value;
            return this;
        }

        @SystemApi
        public Builder setRenouncedPermissions(Set<String> value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mAttributionSourceState.renouncedPermissions = value != null ? (String[]) value.toArray(new String[0]) : null;
            return this;
        }

        public Builder setDeviceId(int deviceId) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 18;
            this.mAttributionSourceState.deviceId = deviceId;
            return this;
        }

        public Builder setNext(AttributionSource value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAttributionSourceState.next = value != null ? new AttributionSourceState[]{value.mAttributionSourceState} : this.mAttributionSourceState.next;
            return this;
        }

        public Builder setNextAttributionSource(AttributionSource value) {
            checkNotUsed();
            if (value == null) {
                throw new IllegalArgumentException("Null AttributionSource not permitted.");
            }
            this.mBuilderFieldsSet |= 32;
            this.mAttributionSourceState.next = new AttributionSourceState[]{value.mAttributionSourceState};
            return this;
        }

        public AttributionSource build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mAttributionSourceState.pid = -1;
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mAttributionSourceState.packageName = null;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mAttributionSourceState.attributionTag = null;
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mAttributionSourceState.renouncedPermissions = null;
            }
            if ((this.mBuilderFieldsSet & 18) == 0) {
                this.mAttributionSourceState.deviceId = 0;
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mAttributionSourceState.next = null;
            }
            this.mAttributionSourceState.token = AttributionSource.sDefaultToken;
            if (this.mAttributionSourceState.next == null) {
                this.mAttributionSourceState.next = new AttributionSourceState[0];
            }
            return new AttributionSource(this.mAttributionSourceState);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
