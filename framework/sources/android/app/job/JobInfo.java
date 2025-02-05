package android.app.job;

import android.app.AlarmManager;
import android.compat.Compatibility;
import android.content.ClipData;
import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.ArraySet;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.util.TimeUtils;
import com.android.internal.hidden_from_bootclasspath.android.app.job.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public class JobInfo implements Parcelable {
    public static final int BACKOFF_POLICY_EXPONENTIAL = 1;
    public static final int BACKOFF_POLICY_LINEAR = 0;
    public static final int BIAS_ADJ_ALWAYS_RUNNING = -80;
    public static final int BIAS_ADJ_OFTEN_RUNNING = -40;
    public static final int BIAS_BOUND_FOREGROUND_SERVICE = 30;
    public static final int BIAS_DEFAULT = 0;
    public static final int BIAS_FOREGROUND_SERVICE = 35;
    public static final int BIAS_SYNC_EXPEDITED = 10;
    public static final int BIAS_SYNC_INITIALIZATION = 20;
    public static final int BIAS_TOP_APP = 40;
    public static final int CONSTRAINT_FLAG_BATTERY_NOT_LOW = 2;
    public static final int CONSTRAINT_FLAG_CHARGING = 1;
    public static final int CONSTRAINT_FLAG_DEVICE_IDLE = 4;
    public static final int CONSTRAINT_FLAG_STORAGE_NOT_LOW = 8;
    public static final int DEFAULT_BACKOFF_POLICY = 1;
    public static final long DEFAULT_INITIAL_BACKOFF_MILLIS = 30000;
    public static final long DISALLOW_DEADLINES_FOR_PREFETCH_JOBS = 194532703;
    public static final long ENFORCE_MINIMUM_TIME_WINDOWS = 311402873;
    public static final int FLAG_EXEMPT_FROM_APP_STANDBY = 8;
    public static final int FLAG_EXPEDITED = 16;
    public static final int FLAG_IMPORTANT_WHILE_FOREGROUND = 2;
    public static final int FLAG_PREFETCH = 4;
    public static final int FLAG_USER_INITIATED = 32;
    public static final int FLAG_WILL_BE_FOREGROUND = 1;
    public static final long MAX_BACKOFF_DELAY_MILLIS = 18000000;
    public static final int MAX_DEBUG_TAG_LENGTH = 127;
    public static final int MAX_NUM_DEBUG_TAGS = 32;
    public static final int MAX_TRACE_TAG_LENGTH = 127;
    private static final long MIN_ALLOWED_TIME_WINDOW_MILLIS = 900000;
    public static final long MIN_BACKOFF_MILLIS = 10000;
    private static final long MIN_FLEX_MILLIS = 300000;
    private static final long MIN_PERIOD_MILLIS = 900000;
    public static final int NETWORK_BYTES_UNKNOWN = -1;
    public static final int NETWORK_TYPE_ANY = 1;
    public static final int NETWORK_TYPE_CELLULAR = 4;

    @Deprecated
    public static final int NETWORK_TYPE_METERED = 4;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_NOT_ROAMING = 3;
    public static final int NETWORK_TYPE_UNMETERED = 2;
    public static final int PRIORITY_DEFAULT = 300;
    public static final int PRIORITY_FOREGROUND_APP = 30;
    public static final int PRIORITY_FOREGROUND_SERVICE = 35;
    public static final int PRIORITY_HIGH = 400;
    public static final int PRIORITY_LOW = 200;
    public static final int PRIORITY_MAX = 500;
    public static final int PRIORITY_MIN = 100;
    public static final long REJECT_NEGATIVE_DELAYS_AND_DEADLINES = 323349338;
    public static final long REJECT_NEGATIVE_NETWORK_ESTIMATES = 253665015;
    public static final long THROW_ON_INVALID_PRIORITY_VALUE = 140852299;
    private final int backoffPolicy;
    private final ClipData clipData;
    private final int clipGrantFlags;
    private final int constraintFlags;
    private final PersistableBundle extras;
    private final int flags;
    private final long flexMillis;
    private final boolean hasEarlyConstraint;
    private final boolean hasLateConstraint;
    private final long initialBackoffMillis;
    private final long intervalMillis;
    private final boolean isPeriodic;
    private final boolean isPersisted;
    private final int jobId;
    private final int mBias;
    private final ArraySet<String> mDebugTags;
    private final int mPriority;
    private final String mTraceTag;
    private final long maxExecutionDelayMillis;
    private final long minLatencyMillis;
    private final long minimumNetworkChunkBytes;
    private final long networkDownloadBytes;
    private final NetworkRequest networkRequest;
    private final long networkUploadBytes;
    private final ComponentName service;
    private final Bundle transientExtras;
    private final long triggerContentMaxDelay;
    private final long triggerContentUpdateDelay;
    private final TriggerContentUri[] triggerContentUris;
    private static String TAG = "JobInfo";
    public static final Parcelable.Creator<JobInfo> CREATOR = new Parcelable.Creator<JobInfo>() { // from class: android.app.job.JobInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobInfo createFromParcel(Parcel in) {
            return new JobInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobInfo[] newArray(int size) {
            return new JobInfo[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackoffPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    public static final long getMinPeriodMillis() {
        return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
    }

    public static final long getMinFlexMillis() {
        return 300000L;
    }

    public static final long getMinBackoffMillis() {
        return MIN_BACKOFF_MILLIS;
    }

    public int getId() {
        return this.jobId;
    }

    public PersistableBundle getExtras() {
        return this.extras;
    }

    public Bundle getTransientExtras() {
        return this.transientExtras;
    }

    public ClipData getClipData() {
        return this.clipData;
    }

    public int getClipGrantFlags() {
        return this.clipGrantFlags;
    }

    public ComponentName getService() {
        return this.service;
    }

    public int getBias() {
        return this.mBias;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public int getFlags() {
        return this.flags;
    }

    public boolean isExemptedFromAppStandby() {
        return ((this.flags & 8) == 0 || isPeriodic()) ? false : true;
    }

    public boolean isRequireCharging() {
        return (this.constraintFlags & 1) != 0;
    }

    public boolean isRequireBatteryNotLow() {
        return (this.constraintFlags & 2) != 0;
    }

    public boolean isRequireDeviceIdle() {
        return (this.constraintFlags & 4) != 0;
    }

    public boolean isRequireStorageNotLow() {
        return (this.constraintFlags & 8) != 0;
    }

    public int getConstraintFlags() {
        return this.constraintFlags;
    }

    public TriggerContentUri[] getTriggerContentUris() {
        return this.triggerContentUris;
    }

    public long getTriggerContentUpdateDelay() {
        return this.triggerContentUpdateDelay;
    }

    public long getTriggerContentMaxDelay() {
        return this.triggerContentMaxDelay;
    }

    @Deprecated
    public int getNetworkType() {
        if (this.networkRequest == null) {
            return 0;
        }
        if (this.networkRequest.hasCapability(11)) {
            return 2;
        }
        if (this.networkRequest.hasCapability(18)) {
            return 3;
        }
        if (this.networkRequest.hasTransport(0)) {
            return 4;
        }
        return 1;
    }

    public NetworkRequest getRequiredNetwork() {
        return this.networkRequest;
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.networkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.networkUploadBytes;
    }

    public long getMinimumNetworkChunkBytes() {
        return this.minimumNetworkChunkBytes;
    }

    public long getMinLatencyMillis() {
        return Math.max(0L, this.minLatencyMillis);
    }

    public long getMaxExecutionDelayMillis() {
        return Math.max(0L, this.maxExecutionDelayMillis);
    }

    public boolean isPeriodic() {
        return this.isPeriodic;
    }

    public boolean isPersisted() {
        return this.isPersisted;
    }

    public long getIntervalMillis() {
        return this.intervalMillis;
    }

    public long getFlexMillis() {
        return this.flexMillis;
    }

    public long getInitialBackoffMillis() {
        return this.initialBackoffMillis;
    }

    public int getBackoffPolicy() {
        return this.backoffPolicy;
    }

    public Set<String> getDebugTags() {
        return Collections.unmodifiableSet(this.mDebugTags);
    }

    public ArraySet<String> getDebugTagsArraySet() {
        return this.mDebugTags;
    }

    public String getTraceTag() {
        return this.mTraceTag;
    }

    public boolean isExpedited() {
        return (this.flags & 16) != 0;
    }

    public boolean isUserInitiated() {
        return (this.flags & 32) != 0;
    }

    public boolean isImportantWhileForeground() {
        return (this.flags & 2) != 0;
    }

    public boolean isPrefetch() {
        return (this.flags & 4) != 0;
    }

    public boolean hasEarlyConstraint() {
        return this.hasEarlyConstraint;
    }

    public boolean hasLateConstraint() {
        return this.hasLateConstraint;
    }

    public boolean equals(Object o) {
        if (!(o instanceof JobInfo)) {
            return false;
        }
        JobInfo j = (JobInfo) o;
        return this.jobId == j.jobId && BaseBundle.kindofEquals(this.extras, j.extras) && BaseBundle.kindofEquals(this.transientExtras, j.transientExtras) && this.clipData == j.clipData && this.clipGrantFlags == j.clipGrantFlags && Objects.equals(this.service, j.service) && this.constraintFlags == j.constraintFlags && Arrays.equals(this.triggerContentUris, j.triggerContentUris) && this.triggerContentUpdateDelay == j.triggerContentUpdateDelay && this.triggerContentMaxDelay == j.triggerContentMaxDelay && this.hasEarlyConstraint == j.hasEarlyConstraint && this.hasLateConstraint == j.hasLateConstraint && Objects.equals(this.networkRequest, j.networkRequest) && this.networkDownloadBytes == j.networkDownloadBytes && this.networkUploadBytes == j.networkUploadBytes && this.minimumNetworkChunkBytes == j.minimumNetworkChunkBytes && this.minLatencyMillis == j.minLatencyMillis && this.maxExecutionDelayMillis == j.maxExecutionDelayMillis && this.isPeriodic == j.isPeriodic && this.isPersisted == j.isPersisted && this.intervalMillis == j.intervalMillis && this.flexMillis == j.flexMillis && this.initialBackoffMillis == j.initialBackoffMillis && this.backoffPolicy == j.backoffPolicy && this.mBias == j.mBias && this.mPriority == j.mPriority && this.flags == j.flags && this.mDebugTags.equals(j.mDebugTags) && Objects.equals(this.mTraceTag, j.mTraceTag);
    }

    public int hashCode() {
        int hashCode = this.jobId;
        if (this.extras != null) {
            hashCode = (hashCode * 31) + this.extras.hashCode();
        }
        if (this.transientExtras != null) {
            hashCode = (hashCode * 31) + this.transientExtras.hashCode();
        }
        if (this.clipData != null) {
            hashCode = (hashCode * 31) + this.clipData.hashCode();
        }
        int hashCode2 = (hashCode * 31) + this.clipGrantFlags;
        if (this.service != null) {
            hashCode2 = (hashCode2 * 31) + this.service.hashCode();
        }
        int hashCode3 = (hashCode2 * 31) + this.constraintFlags;
        if (this.triggerContentUris != null) {
            hashCode3 = (hashCode3 * 31) + Arrays.hashCode(this.triggerContentUris);
        }
        int hashCode4 = (((((((hashCode3 * 31) + Long.hashCode(this.triggerContentUpdateDelay)) * 31) + Long.hashCode(this.triggerContentMaxDelay)) * 31) + Boolean.hashCode(this.hasEarlyConstraint)) * 31) + Boolean.hashCode(this.hasLateConstraint);
        if (this.networkRequest != null) {
            hashCode4 = (hashCode4 * 31) + this.networkRequest.hashCode();
        }
        int hashCode5 = (((((((((((((((((((((((((((hashCode4 * 31) + Long.hashCode(this.networkDownloadBytes)) * 31) + Long.hashCode(this.networkUploadBytes)) * 31) + Long.hashCode(this.minimumNetworkChunkBytes)) * 31) + Long.hashCode(this.minLatencyMillis)) * 31) + Long.hashCode(this.maxExecutionDelayMillis)) * 31) + Boolean.hashCode(this.isPeriodic)) * 31) + Boolean.hashCode(this.isPersisted)) * 31) + Long.hashCode(this.intervalMillis)) * 31) + Long.hashCode(this.flexMillis)) * 31) + Long.hashCode(this.initialBackoffMillis)) * 31) + this.backoffPolicy) * 31) + this.mBias) * 31) + this.mPriority) * 31) + this.flags;
        if (this.mDebugTags.size() > 0) {
            hashCode5 = (hashCode5 * 31) + this.mDebugTags.hashCode();
        }
        if (this.mTraceTag != null) {
            return (hashCode5 * 31) + this.mTraceTag.hashCode();
        }
        return hashCode5;
    }

    private JobInfo(Parcel in) {
        this.jobId = in.readInt();
        PersistableBundle persistableExtras = in.readPersistableBundle();
        this.extras = persistableExtras != null ? persistableExtras : PersistableBundle.EMPTY;
        this.transientExtras = in.readBundle();
        if (in.readInt() != 0) {
            this.clipData = ClipData.CREATOR.createFromParcel(in);
            this.clipGrantFlags = in.readInt();
        } else {
            this.clipData = null;
            this.clipGrantFlags = 0;
        }
        this.service = (ComponentName) in.readParcelable(null);
        this.constraintFlags = in.readInt();
        this.triggerContentUris = (TriggerContentUri[]) in.createTypedArray(TriggerContentUri.CREATOR);
        this.triggerContentUpdateDelay = in.readLong();
        this.triggerContentMaxDelay = in.readLong();
        if (in.readInt() != 0) {
            this.networkRequest = (NetworkRequest) NetworkRequest.CREATOR.createFromParcel(in);
        } else {
            this.networkRequest = null;
        }
        this.networkDownloadBytes = in.readLong();
        this.networkUploadBytes = in.readLong();
        this.minimumNetworkChunkBytes = in.readLong();
        this.minLatencyMillis = in.readLong();
        this.maxExecutionDelayMillis = in.readLong();
        this.isPeriodic = in.readInt() == 1;
        this.isPersisted = in.readInt() == 1;
        this.intervalMillis = in.readLong();
        this.flexMillis = in.readLong();
        this.initialBackoffMillis = in.readLong();
        this.backoffPolicy = in.readInt();
        this.hasEarlyConstraint = in.readInt() == 1;
        this.hasLateConstraint = in.readInt() == 1;
        this.mBias = in.readInt();
        this.mPriority = in.readInt();
        this.flags = in.readInt();
        int numDebugTags = in.readInt();
        this.mDebugTags = new ArraySet<>();
        for (int i = 0; i < numDebugTags; i++) {
            String tag = in.readString();
            if (tag == null) {
                throw new IllegalStateException("malformed parcel");
            }
            this.mDebugTags.add(tag.intern());
        }
        String traceTag = in.readString();
        this.mTraceTag = traceTag != null ? traceTag.intern() : null;
    }

    private JobInfo(Builder b) {
        TriggerContentUri[] triggerContentUriArr;
        this.jobId = b.mJobId;
        this.extras = b.mExtras.deepCopy();
        this.transientExtras = b.mTransientExtras.deepCopy();
        this.clipData = b.mClipData;
        this.clipGrantFlags = b.mClipGrantFlags;
        this.service = b.mJobService;
        this.constraintFlags = b.mConstraintFlags;
        if (b.mTriggerContentUris != null) {
            triggerContentUriArr = (TriggerContentUri[]) b.mTriggerContentUris.toArray(new TriggerContentUri[b.mTriggerContentUris.size()]);
        } else {
            triggerContentUriArr = null;
        }
        this.triggerContentUris = triggerContentUriArr;
        this.triggerContentUpdateDelay = b.mTriggerContentUpdateDelay;
        this.triggerContentMaxDelay = b.mTriggerContentMaxDelay;
        this.networkRequest = b.mNetworkRequest;
        this.networkDownloadBytes = b.mNetworkDownloadBytes;
        this.networkUploadBytes = b.mNetworkUploadBytes;
        this.minimumNetworkChunkBytes = b.mMinimumNetworkChunkBytes;
        this.minLatencyMillis = b.mMinLatencyMillis;
        this.maxExecutionDelayMillis = b.mMaxExecutionDelayMillis;
        this.isPeriodic = b.mIsPeriodic;
        this.isPersisted = b.mIsPersisted;
        this.intervalMillis = b.mIntervalMillis;
        this.flexMillis = b.mFlexMillis;
        this.initialBackoffMillis = b.mInitialBackoffMillis;
        this.backoffPolicy = b.mBackoffPolicy;
        this.hasEarlyConstraint = b.mHasEarlyConstraint;
        this.hasLateConstraint = b.mHasLateConstraint;
        this.mBias = b.mBias;
        this.mPriority = b.mPriority;
        this.flags = b.mFlags;
        this.mDebugTags = b.mDebugTags;
        this.mTraceTag = b.mTraceTag;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.jobId);
        parcel.writePersistableBundle(this.extras);
        parcel.writeBundle(this.transientExtras);
        if (this.clipData != null) {
            parcel.writeInt(1);
            this.clipData.writeToParcel(parcel, i);
            parcel.writeInt(this.clipGrantFlags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeParcelable(this.service, i);
        parcel.writeInt(this.constraintFlags);
        parcel.writeTypedArray(this.triggerContentUris, i);
        parcel.writeLong(this.triggerContentUpdateDelay);
        parcel.writeLong(this.triggerContentMaxDelay);
        if (this.networkRequest != null) {
            parcel.writeInt(1);
            this.networkRequest.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.networkDownloadBytes);
        parcel.writeLong(this.networkUploadBytes);
        parcel.writeLong(this.minimumNetworkChunkBytes);
        parcel.writeLong(this.minLatencyMillis);
        parcel.writeLong(this.maxExecutionDelayMillis);
        parcel.writeInt(this.isPeriodic ? 1 : 0);
        parcel.writeInt(this.isPersisted ? 1 : 0);
        parcel.writeLong(this.intervalMillis);
        parcel.writeLong(this.flexMillis);
        parcel.writeLong(this.initialBackoffMillis);
        parcel.writeInt(this.backoffPolicy);
        parcel.writeInt(this.hasEarlyConstraint ? 1 : 0);
        parcel.writeInt(this.hasLateConstraint ? 1 : 0);
        parcel.writeInt(this.mBias);
        parcel.writeInt(this.mPriority);
        parcel.writeInt(this.flags);
        int size = this.mDebugTags.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString(this.mDebugTags.valueAt(i2));
        }
        parcel.writeString(this.mTraceTag);
    }

    public String toString() {
        return "(job:" + this.jobId + "/" + this.service.flattenToShortString() + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static final class TriggerContentUri implements Parcelable {
        public static final Parcelable.Creator<TriggerContentUri> CREATOR = new Parcelable.Creator<TriggerContentUri>() { // from class: android.app.job.JobInfo.TriggerContentUri.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TriggerContentUri createFromParcel(Parcel in) {
                return new TriggerContentUri(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TriggerContentUri[] newArray(int size) {
                return new TriggerContentUri[size];
            }
        };
        public static final int FLAG_NOTIFY_FOR_DESCENDANTS = 1;
        private final int mFlags;
        private final Uri mUri;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        public TriggerContentUri(Uri uri, int flags) {
            this.mUri = (Uri) Objects.requireNonNull(uri);
            this.mFlags = flags;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean equals(Object o) {
            if (!(o instanceof TriggerContentUri)) {
                return false;
            }
            TriggerContentUri t = (TriggerContentUri) o;
            return Objects.equals(t.mUri, this.mUri) && t.mFlags == this.mFlags;
        }

        public int hashCode() {
            return (this.mUri == null ? 0 : this.mUri.hashCode()) ^ this.mFlags;
        }

        private TriggerContentUri(Parcel in) {
            this.mUri = Uri.CREATOR.createFromParcel(in);
            this.mFlags = in.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            this.mUri.writeToParcel(out, flags);
            out.writeInt(this.mFlags);
        }
    }

    public static final class Builder {
        private int mBackoffPolicy;
        private boolean mBackoffPolicySet;
        private int mBias;
        private ClipData mClipData;
        private int mClipGrantFlags;
        private int mConstraintFlags;
        private final ArraySet<String> mDebugTags;
        private PersistableBundle mExtras;
        private int mFlags;
        private long mFlexMillis;
        private boolean mHasEarlyConstraint;
        private boolean mHasLateConstraint;
        private long mInitialBackoffMillis;
        private long mIntervalMillis;
        private boolean mIsPeriodic;
        private boolean mIsPersisted;
        private final int mJobId;
        private final ComponentName mJobService;
        private long mMaxExecutionDelayMillis;
        private long mMinLatencyMillis;
        private long mMinimumNetworkChunkBytes;
        private long mNetworkDownloadBytes;
        private NetworkRequest mNetworkRequest;
        private long mNetworkUploadBytes;
        private int mPriority;
        private String mTraceTag;
        private Bundle mTransientExtras;
        private long mTriggerContentMaxDelay;
        private long mTriggerContentUpdateDelay;
        private ArrayList<TriggerContentUri> mTriggerContentUris;

        public Builder(int jobId, ComponentName jobService) {
            this.mExtras = PersistableBundle.EMPTY;
            this.mTransientExtras = Bundle.EMPTY;
            this.mBias = 0;
            this.mPriority = 300;
            this.mNetworkDownloadBytes = -1L;
            this.mNetworkUploadBytes = -1L;
            this.mMinimumNetworkChunkBytes = -1L;
            this.mTriggerContentUpdateDelay = -1L;
            this.mTriggerContentMaxDelay = -1L;
            this.mInitialBackoffMillis = 30000L;
            this.mBackoffPolicy = 1;
            this.mBackoffPolicySet = false;
            this.mDebugTags = new ArraySet<>();
            this.mJobService = jobService;
            this.mJobId = jobId;
        }

        public Builder(JobInfo job) {
            this.mExtras = PersistableBundle.EMPTY;
            this.mTransientExtras = Bundle.EMPTY;
            this.mBias = 0;
            this.mPriority = 300;
            this.mNetworkDownloadBytes = -1L;
            this.mNetworkUploadBytes = -1L;
            this.mMinimumNetworkChunkBytes = -1L;
            this.mTriggerContentUpdateDelay = -1L;
            this.mTriggerContentMaxDelay = -1L;
            this.mInitialBackoffMillis = 30000L;
            this.mBackoffPolicy = 1;
            this.mBackoffPolicySet = false;
            this.mDebugTags = new ArraySet<>();
            this.mJobId = job.getId();
            this.mJobService = job.getService();
            this.mExtras = job.getExtras();
            this.mTransientExtras = job.getTransientExtras();
            this.mClipData = job.getClipData();
            this.mClipGrantFlags = job.getClipGrantFlags();
            this.mBias = job.getBias();
            this.mFlags = job.getFlags();
            this.mConstraintFlags = job.getConstraintFlags();
            this.mNetworkRequest = job.getRequiredNetwork();
            this.mNetworkDownloadBytes = job.getEstimatedNetworkDownloadBytes();
            this.mNetworkUploadBytes = job.getEstimatedNetworkUploadBytes();
            this.mMinimumNetworkChunkBytes = job.getMinimumNetworkChunkBytes();
            this.mTriggerContentUris = job.getTriggerContentUris() != null ? new ArrayList<>(Arrays.asList(job.getTriggerContentUris())) : null;
            this.mTriggerContentUpdateDelay = job.getTriggerContentUpdateDelay();
            this.mTriggerContentMaxDelay = job.getTriggerContentMaxDelay();
            this.mIsPersisted = job.isPersisted();
            this.mMinLatencyMillis = job.getMinLatencyMillis();
            this.mMaxExecutionDelayMillis = job.getMaxExecutionDelayMillis();
            this.mIsPeriodic = job.isPeriodic();
            this.mHasEarlyConstraint = job.hasEarlyConstraint();
            this.mHasLateConstraint = job.hasLateConstraint();
            this.mIntervalMillis = job.getIntervalMillis();
            this.mFlexMillis = job.getFlexMillis();
            this.mInitialBackoffMillis = job.getInitialBackoffMillis();
            this.mBackoffPolicy = job.getBackoffPolicy();
            this.mPriority = job.getPriority();
        }

        public Builder addDebugTag(String tag) {
            this.mDebugTags.add(JobInfo.validateDebugTag(tag));
            return this;
        }

        public void addDebugTags(Set<String> tags) {
            this.mDebugTags.addAll(tags);
        }

        public Builder removeDebugTag(String tag) {
            this.mDebugTags.remove(tag);
            return this;
        }

        public Builder setBias(int bias) {
            this.mBias = bias;
            return this;
        }

        public Builder setPriority(int priority) {
            if (priority > 500 || priority < 100) {
                if (Compatibility.isChangeEnabled(JobInfo.THROW_ON_INVALID_PRIORITY_VALUE)) {
                    throw new IllegalArgumentException("Invalid priority value");
                }
                return this;
            }
            this.mPriority = priority;
            return this;
        }

        public Builder setFlags(int flags) {
            this.mFlags = flags;
            return this;
        }

        public Builder setExtras(PersistableBundle extras) {
            this.mExtras = extras;
            return this;
        }

        public Builder setTransientExtras(Bundle extras) {
            this.mTransientExtras = extras;
            return this;
        }

        public Builder setClipData(ClipData clip, int grantFlags) {
            this.mClipData = clip;
            this.mClipGrantFlags = grantFlags;
            return this;
        }

        public Builder setRequiredNetworkType(int networkType) {
            if (networkType == 0) {
                return setRequiredNetwork(null);
            }
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addCapability(16);
            builder.removeCapability(15);
            builder.removeCapability(13);
            if (networkType != 1) {
                if (networkType == 2) {
                    builder.addCapability(11);
                } else if (networkType == 3) {
                    builder.addCapability(18);
                } else if (networkType == 4) {
                    builder.addTransportType(0);
                }
            }
            return setRequiredNetwork(builder.build());
        }

        public Builder setRequiredNetwork(NetworkRequest networkRequest) {
            this.mNetworkRequest = networkRequest;
            return this;
        }

        public Builder setEstimatedNetworkBytes(long downloadBytes, long uploadBytes) {
            this.mNetworkDownloadBytes = downloadBytes;
            this.mNetworkUploadBytes = uploadBytes;
            return this;
        }

        public Builder setMinimumNetworkChunkBytes(long chunkSizeBytes) {
            if (chunkSizeBytes != -1 && chunkSizeBytes <= 0) {
                throw new IllegalArgumentException("Minimum chunk size must be positive");
            }
            this.mMinimumNetworkChunkBytes = chunkSizeBytes;
            return this;
        }

        public Builder setRequiresCharging(boolean z) {
            this.mConstraintFlags = (this.mConstraintFlags & (-2)) | (z ? 1 : 0);
            return this;
        }

        public Builder setRequiresBatteryNotLow(boolean batteryNotLow) {
            this.mConstraintFlags = (this.mConstraintFlags & (-3)) | (batteryNotLow ? 2 : 0);
            return this;
        }

        public Builder setRequiresDeviceIdle(boolean requiresDeviceIdle) {
            this.mConstraintFlags = (this.mConstraintFlags & (-5)) | (requiresDeviceIdle ? 4 : 0);
            return this;
        }

        public Builder setRequiresStorageNotLow(boolean storageNotLow) {
            this.mConstraintFlags = (this.mConstraintFlags & (-9)) | (storageNotLow ? 8 : 0);
            return this;
        }

        public Builder addTriggerContentUri(TriggerContentUri uri) {
            if (this.mTriggerContentUris == null) {
                this.mTriggerContentUris = new ArrayList<>();
            }
            this.mTriggerContentUris.add(uri);
            return this;
        }

        public Builder setTriggerContentUpdateDelay(long durationMs) {
            this.mTriggerContentUpdateDelay = durationMs;
            return this;
        }

        public Builder setTriggerContentMaxDelay(long durationMs) {
            this.mTriggerContentMaxDelay = durationMs;
            return this;
        }

        public Builder setPeriodic(long intervalMillis) {
            return setPeriodic(intervalMillis, intervalMillis);
        }

        public Builder setPeriodic(long intervalMillis, long flexMillis) {
            long minPeriod = JobInfo.getMinPeriodMillis();
            if (intervalMillis < minPeriod) {
                Log.w(JobInfo.TAG, "Requested interval " + TimeUtils.formatDuration(intervalMillis) + " for job " + this.mJobId + " is too small; raising to " + TimeUtils.formatDuration(minPeriod));
                intervalMillis = minPeriod;
            }
            long percentClamp = (5 * intervalMillis) / 100;
            long minFlex = Math.max(percentClamp, JobInfo.getMinFlexMillis());
            if (flexMillis < minFlex) {
                Log.w(JobInfo.TAG, "Requested flex " + TimeUtils.formatDuration(flexMillis) + " for job " + this.mJobId + " is too small; raising to " + TimeUtils.formatDuration(minFlex));
                flexMillis = minFlex;
            }
            this.mIsPeriodic = true;
            this.mIntervalMillis = intervalMillis;
            this.mFlexMillis = flexMillis;
            this.mHasLateConstraint = true;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public Builder setMinimumLatency(long minLatencyMillis) {
            this.mMinLatencyMillis = minLatencyMillis;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public Builder setOverrideDeadline(long maxExecutionDelayMillis) {
            this.mMaxExecutionDelayMillis = maxExecutionDelayMillis;
            this.mHasLateConstraint = true;
            return this;
        }

        public Builder setBackoffCriteria(long initialBackoffMillis, int backoffPolicy) {
            long minBackoff = JobInfo.getMinBackoffMillis();
            if (initialBackoffMillis < minBackoff) {
                Log.w(JobInfo.TAG, "Requested backoff " + TimeUtils.formatDuration(initialBackoffMillis) + " for job " + this.mJobId + " is too small; raising to " + TimeUtils.formatDuration(minBackoff));
                initialBackoffMillis = minBackoff;
            }
            this.mBackoffPolicySet = true;
            this.mInitialBackoffMillis = initialBackoffMillis;
            this.mBackoffPolicy = backoffPolicy;
            return this;
        }

        public Builder setExpedited(boolean expedited) {
            if (expedited) {
                this.mFlags |= 16;
                if (this.mPriority == 300) {
                    this.mPriority = 500;
                }
            } else {
                if (this.mPriority == 500 && (this.mFlags & 16) != 0) {
                    this.mPriority = 300;
                }
                this.mFlags &= -17;
            }
            return this;
        }

        public Builder setUserInitiated(boolean userInitiated) {
            if (userInitiated) {
                this.mFlags |= 32;
                if (this.mPriority == 300) {
                    this.mPriority = 500;
                }
            } else {
                if (this.mPriority == 500 && (this.mFlags & 32) != 0) {
                    this.mPriority = 300;
                }
                this.mFlags &= -33;
            }
            return this;
        }

        @Deprecated
        public Builder setImportantWhileForeground(boolean importantWhileForeground) {
            if (importantWhileForeground) {
                this.mFlags |= 2;
                if (this.mPriority == 300) {
                    this.mPriority = 400;
                }
            } else {
                if (this.mPriority == 400 && (this.mFlags & 2) != 0) {
                    this.mPriority = 300;
                }
                this.mFlags &= -3;
            }
            return this;
        }

        public Builder setPrefetch(boolean prefetch) {
            if (prefetch) {
                this.mFlags |= 4;
            } else {
                this.mFlags &= -5;
            }
            return this;
        }

        public Builder setPersisted(boolean isPersisted) {
            this.mIsPersisted = isPersisted;
            return this;
        }

        public Builder setTraceTag(String traceTag) {
            this.mTraceTag = JobInfo.validateTraceTag(traceTag);
            return this;
        }

        public JobInfo build() {
            return build(Compatibility.isChangeEnabled(JobInfo.DISALLOW_DEADLINES_FOR_PREFETCH_JOBS), Compatibility.isChangeEnabled(JobInfo.REJECT_NEGATIVE_NETWORK_ESTIMATES), Compatibility.isChangeEnabled(JobInfo.ENFORCE_MINIMUM_TIME_WINDOWS), Compatibility.isChangeEnabled(JobInfo.REJECT_NEGATIVE_DELAYS_AND_DEADLINES));
        }

        public JobInfo build(boolean disallowPrefetchDeadlines, boolean rejectNegativeNetworkEstimates, boolean enforceMinimumTimeWindows, boolean rejectNegativeDelaysAndDeadlines) {
            if (this.mBackoffPolicySet && (this.mConstraintFlags & 4) != 0) {
                throw new IllegalArgumentException("An idle mode job will not respect any back-off policy, so calling setBackoffCriteria with setRequiresDeviceIdle is an error.");
            }
            JobInfo jobInfo = new JobInfo(this);
            jobInfo.enforceValidity(disallowPrefetchDeadlines, rejectNegativeNetworkEstimates, enforceMinimumTimeWindows, rejectNegativeDelaysAndDeadlines);
            return jobInfo;
        }

        public String summarize() {
            String service;
            if (this.mJobService != null) {
                service = this.mJobService.flattenToShortString();
            } else {
                service = "null";
            }
            return "JobInfo.Builder{job:" + this.mJobId + "/" + service + "}";
        }
    }

    public final void enforceValidity(boolean disallowPrefetchDeadlines, boolean rejectNegativeNetworkEstimates, boolean enforceMinimumTimeWindows, boolean rejectNegativeDelaysAndDeadlines) {
        long estimatedTransfer;
        if ((this.networkDownloadBytes > 0 || this.networkUploadBytes > 0 || this.minimumNetworkChunkBytes > 0) && this.networkRequest == null) {
            throw new IllegalArgumentException("Can't provide estimated network usage without requiring a network");
        }
        if (this.networkRequest != null && rejectNegativeNetworkEstimates) {
            if (this.networkUploadBytes != -1 && this.networkUploadBytes < 0) {
                throw new IllegalArgumentException("Invalid network upload bytes: " + this.networkUploadBytes);
            }
            if (this.networkDownloadBytes != -1 && this.networkDownloadBytes < 0) {
                throw new IllegalArgumentException("Invalid network download bytes: " + this.networkDownloadBytes);
            }
        }
        if (this.networkUploadBytes == -1) {
            estimatedTransfer = this.networkDownloadBytes;
        } else {
            long estimatedTransfer2 = this.networkUploadBytes;
            estimatedTransfer = estimatedTransfer2 + (this.networkDownloadBytes == -1 ? 0L : this.networkDownloadBytes);
        }
        if (this.minimumNetworkChunkBytes != -1 && estimatedTransfer != -1 && this.minimumNetworkChunkBytes > estimatedTransfer) {
            throw new IllegalArgumentException("Minimum chunk size can't be greater than estimated network usage");
        }
        if (this.minimumNetworkChunkBytes != -1 && this.minimumNetworkChunkBytes <= 0) {
            throw new IllegalArgumentException("Minimum chunk size must be positive");
        }
        if (rejectNegativeDelaysAndDeadlines) {
            if (this.minLatencyMillis < 0) {
                throw new IllegalArgumentException("Minimum latency is negative: " + this.minLatencyMillis);
            }
            if (this.maxExecutionDelayMillis < 0) {
                throw new IllegalArgumentException("Override deadline is negative: " + this.maxExecutionDelayMillis);
            }
        }
        boolean hasFunctionalConstraint = false;
        boolean hasDeadline = this.maxExecutionDelayMillis != 0;
        if (this.isPeriodic) {
            if (hasDeadline) {
                throw new IllegalArgumentException("Can't call setOverrideDeadline() on a periodic job.");
            }
            if (this.minLatencyMillis != 0) {
                throw new IllegalArgumentException("Can't call setMinimumLatency() on a periodic job");
            }
            if (this.triggerContentUris != null) {
                throw new IllegalArgumentException("Can't call addTriggerContentUri() on a periodic job");
            }
        }
        if (disallowPrefetchDeadlines && hasDeadline && (this.flags & 4) != 0) {
            throw new IllegalArgumentException("Can't call setOverrideDeadline() on a prefetch job.");
        }
        if (this.isPersisted) {
            if (this.networkRequest != null && this.networkRequest.getNetworkSpecifier() != null) {
                throw new IllegalArgumentException("Network specifiers aren't supported for persistent jobs");
            }
            if (this.triggerContentUris != null) {
                throw new IllegalArgumentException("Can't call addTriggerContentUri() on a persisted job");
            }
            if (!this.transientExtras.isEmpty()) {
                throw new IllegalArgumentException("Can't call setTransientExtras() on a persisted job");
            }
            if (this.clipData != null) {
                throw new IllegalArgumentException("Can't call setClipData() on a persisted job");
            }
        }
        if ((this.flags & 2) != 0) {
            if (this.hasEarlyConstraint) {
                throw new IllegalArgumentException("An important while foreground job cannot have a time delay");
            }
            if (this.mPriority != 400 && this.mPriority != 300) {
                throw new IllegalArgumentException("An important while foreground job must be high or default priority. Don't mark unimportant tasks as important while foreground.");
            }
        }
        boolean isExpedited = (this.flags & 16) != 0;
        boolean isUserInitiated = (this.flags & 32) != 0;
        switch (this.mPriority) {
            case 100:
            case 200:
            case 300:
                break;
            case 400:
                if ((this.flags & 4) != 0) {
                    throw new IllegalArgumentException("Prefetch jobs cannot be high priority");
                }
                if (this.isPeriodic) {
                    throw new IllegalArgumentException("Periodic jobs cannot be high priority");
                }
                break;
            case 500:
                if (!isExpedited && !isUserInitiated) {
                    throw new IllegalArgumentException("Only expedited or user-initiated jobs can have max priority");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid priority level provided: " + this.mPriority);
        }
        if (this.networkRequest != null || this.constraintFlags != 0 || (this.triggerContentUris != null && this.triggerContentUris.length > 0)) {
            hasFunctionalConstraint = true;
        }
        if (this.hasLateConstraint && !this.isPeriodic) {
            if (!hasFunctionalConstraint) {
                Log.w(TAG, "Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has a deadline with no functional constraints. The deadline won't improve job execution latency. Consider removing the deadline.");
            } else {
                long windowStart = this.hasEarlyConstraint ? this.minLatencyMillis : 0L;
                if (this.maxExecutionDelayMillis - windowStart < AlarmManager.INTERVAL_FIFTEEN_MINUTES) {
                    if (!enforceMinimumTimeWindows || !Flags.enforceMinimumTimeWindows()) {
                        Log.w(TAG, "Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has a deadline with functional constraints and an extremely short time window of " + (this.maxExecutionDelayMillis - windowStart) + " ms (delay=" + windowStart + ", deadline=" + this.maxExecutionDelayMillis + "). The functional constraints are not likely to be satisfied when the job runs.");
                    } else {
                        throw new IllegalArgumentException("Time window too short. Constraints unlikely to be satisfied. Increase deadline to a reasonable duration. Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has delay=" + windowStart + ", deadline=" + this.maxExecutionDelayMillis);
                    }
                }
            }
        }
        if (isExpedited) {
            if (this.hasEarlyConstraint) {
                throw new IllegalArgumentException("An expedited job cannot have a time delay");
            }
            if (this.hasLateConstraint) {
                throw new IllegalArgumentException("An expedited job cannot have a deadline");
            }
            if (this.isPeriodic) {
                throw new IllegalArgumentException("An expedited job cannot be periodic");
            }
            if (!isUserInitiated) {
                if (this.mPriority != 500 && this.mPriority != 400) {
                    throw new IllegalArgumentException("An expedited job must be high or max priority. Don't use expedited jobs for unimportant tasks.");
                }
                if ((this.constraintFlags & (-9)) != 0 || (this.flags & (-25)) != 0) {
                    throw new IllegalArgumentException("An expedited job can only have network and storage-not-low constraints");
                }
                if (this.triggerContentUris != null && this.triggerContentUris.length > 0) {
                    throw new IllegalArgumentException("Can't call addTriggerContentUri() on an expedited job");
                }
            } else {
                throw new IllegalArgumentException("An expedited job cannot be user-initiated");
            }
        }
        if (isUserInitiated) {
            if (this.hasEarlyConstraint) {
                throw new IllegalArgumentException("A user-initiated job cannot have a time delay");
            }
            if (this.hasLateConstraint) {
                throw new IllegalArgumentException("A user-initiated job cannot have a deadline");
            }
            if (this.isPeriodic) {
                throw new IllegalArgumentException("A user-initiated job cannot be periodic");
            }
            if ((this.flags & 4) == 0) {
                if (this.mPriority != 500) {
                    throw new IllegalArgumentException("A user-initiated job must be max priority.");
                }
                if ((this.constraintFlags & 4) != 0) {
                    throw new IllegalArgumentException("A user-initiated job cannot have a device-idle constraint");
                }
                if (this.triggerContentUris != null && this.triggerContentUris.length > 0) {
                    throw new IllegalArgumentException("Can't call addTriggerContentUri() on a user-initiated job");
                }
                if (this.networkRequest == null) {
                    throw new IllegalArgumentException("A user-initiated data transfer job must specify a valid network type");
                }
            } else {
                throw new IllegalArgumentException("A user-initiated job cannot also be a prefetch job");
            }
        }
        if (this.mDebugTags.size() > 32) {
            throw new IllegalArgumentException("Can't have more than 32 tags");
        }
        ArraySet<String> validatedDebugTags = new ArraySet<>();
        for (int i = 0; i < this.mDebugTags.size(); i++) {
            validatedDebugTags.add(validateDebugTag(this.mDebugTags.valueAt(i)));
        }
        this.mDebugTags.clear();
        this.mDebugTags.addAll((ArraySet<? extends String>) validatedDebugTags);
        validateTraceTag(this.mTraceTag);
    }

    public static String validateDebugTag(String debugTag) {
        if (debugTag == null) {
            throw new NullPointerException("debug tag cannot be null");
        }
        String debugTag2 = debugTag.trim();
        if (debugTag2.isEmpty()) {
            throw new IllegalArgumentException("debug tag cannot be empty");
        }
        if (debugTag2.length() > 127) {
            throw new IllegalArgumentException("debug tag cannot be more than 127 characters");
        }
        return debugTag2.intern();
    }

    public static String validateTraceTag(String traceTag) {
        if (traceTag == null) {
            return null;
        }
        String traceTag2 = traceTag.trim();
        if (traceTag2.isEmpty()) {
            throw new IllegalArgumentException("trace tag cannot be empty");
        }
        if (traceTag2.length() > 127) {
            throw new IllegalArgumentException("traceTag tag cannot be more than 127 characters");
        }
        if (traceTag2.contains(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) || traceTag2.contains("\n") || traceTag2.contains("\u0000")) {
            throw new IllegalArgumentException("Trace tag cannot contain |, \\n, or \\0");
        }
        return traceTag2.intern();
    }

    public static String getBiasString(int bias) {
        switch (bias) {
            case 0:
                return "0 [DEFAULT]";
            case 10:
                return "10 [SYNC_EXPEDITED]";
            case 20:
                return "20 [SYNC_INITIALIZATION]";
            case 30:
                return "30 [BFGS_APP]";
            case 35:
                return "35 [FGS_APP]";
            case 40:
                return "40 [TOP_APP]";
            default:
                return bias + " [UNKNOWN]";
        }
    }

    public static String getPriorityString(int priority) {
        switch (priority) {
            case 100:
                return priority + " [MIN]";
            case 200:
                return priority + " [LOW]";
            case 300:
                return priority + " [DEFAULT]";
            case 400:
                return priority + " [HIGH]";
            case 500:
                return priority + " [MAX]";
            default:
                return priority + " [UNKNOWN]";
        }
    }
}
