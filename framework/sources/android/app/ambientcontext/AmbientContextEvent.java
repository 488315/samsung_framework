package android.app.ambientcontext;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;

@SystemApi
/* loaded from: classes.dex */
public final class AmbientContextEvent implements Parcelable {
    public static final Parcelable.Creator<AmbientContextEvent> CREATOR;
    public static final int EVENT_BACK_DOUBLE_TAP = 3;
    public static final int EVENT_COUGH = 1;
    public static final int EVENT_SNORE = 2;
    public static final int EVENT_UNKNOWN = 0;
    public static final int EVENT_VENDOR_WEARABLE_START = 100000;
    public static final String KEY_VENDOR_WEARABLE_EVENT_NAME = "wearable_event_name";
    public static final int LEVEL_HIGH = 5;
    public static final int LEVEL_LOW = 1;
    public static final int LEVEL_MEDIUM = 3;
    public static final int LEVEL_MEDIUM_HIGH = 4;
    public static final int LEVEL_MEDIUM_LOW = 2;
    public static final int LEVEL_UNKNOWN = 0;
    static Parcelling<Instant> sParcellingForEndTime;
    static Parcelling<Instant> sParcellingForStartTime;
    private final int mConfidenceLevel;
    private final int mDensityLevel;
    private final Instant mEndTime;
    private final int mEventType;
    private final Instant mStartTime;
    private final PersistableBundle mVendorData;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LevelValue {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEventType() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Instant defaultStartTime() {
        return Instant.MIN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Instant defaultEndTime() {
        return Instant.MAX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDensityLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PersistableBundle defaultVendorData() {
        return new PersistableBundle();
    }

    public static String eventToString(int value) {
        switch (value) {
            case 0:
                return "EVENT_UNKNOWN";
            case 1:
                return "EVENT_COUGH";
            case 2:
                return "EVENT_SNORE";
            case 3:
                return "EVENT_BACK_DOUBLE_TAP";
            case 100000:
                return "EVENT_VENDOR_WEARABLE_START";
            default:
                return Integer.toHexString(value);
        }
    }

    public static String levelToString(int value) {
        switch (value) {
            case 0:
                return "LEVEL_UNKNOWN";
            case 1:
                return "LEVEL_LOW";
            case 2:
                return "LEVEL_MEDIUM_LOW";
            case 3:
                return "LEVEL_MEDIUM";
            case 4:
                return "LEVEL_MEDIUM_HIGH";
            case 5:
                return "LEVEL_HIGH";
            default:
                return Integer.toHexString(value);
        }
    }

    AmbientContextEvent(int eventType, Instant startTime, Instant endTime, int confidenceLevel, int densityLevel, PersistableBundle vendorData) {
        this.mEventType = eventType;
        AnnotationValidations.validate((Class<? extends Annotation>) EventCode.class, (Annotation) null, this.mEventType);
        this.mStartTime = startTime;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mStartTime);
        this.mEndTime = endTime;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mEndTime);
        this.mConfidenceLevel = confidenceLevel;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, this.mConfidenceLevel);
        this.mDensityLevel = densityLevel;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, this.mDensityLevel);
        this.mVendorData = vendorData;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mVendorData);
    }

    public int getEventType() {
        return this.mEventType;
    }

    public Instant getStartTime() {
        return this.mStartTime;
    }

    public Instant getEndTime() {
        return this.mEndTime;
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public int getDensityLevel() {
        return this.mDensityLevel;
    }

    public PersistableBundle getVendorData() {
        return this.mVendorData;
    }

    public String toString() {
        return "AmbientContextEvent { eventType = " + this.mEventType + ", startTime = " + this.mStartTime + ", endTime = " + this.mEndTime + ", confidenceLevel = " + this.mConfidenceLevel + ", densityLevel = " + this.mDensityLevel + ", vendorData = " + this.mVendorData + " }";
    }

    static {
        sParcellingForStartTime = Parcelling.Cache.get(Parcelling.BuiltIn.ForInstant.class);
        if (sParcellingForStartTime == null) {
            sParcellingForStartTime = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInstant());
        }
        sParcellingForEndTime = Parcelling.Cache.get(Parcelling.BuiltIn.ForInstant.class);
        if (sParcellingForEndTime == null) {
            sParcellingForEndTime = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInstant());
        }
        CREATOR = new Parcelable.Creator<AmbientContextEvent>() { // from class: android.app.ambientcontext.AmbientContextEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AmbientContextEvent[] newArray(int size) {
                return new AmbientContextEvent[size];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AmbientContextEvent createFromParcel(Parcel in) {
                return new AmbientContextEvent(in);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mEventType);
        sParcellingForStartTime.parcel(this.mStartTime, dest, flags);
        sParcellingForEndTime.parcel(this.mEndTime, dest, flags);
        dest.writeInt(this.mConfidenceLevel);
        dest.writeInt(this.mDensityLevel);
        dest.writeTypedObject(this.mVendorData, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AmbientContextEvent(Parcel in) {
        int eventType = in.readInt();
        Instant startTime = sParcellingForStartTime.unparcel(in);
        Instant endTime = sParcellingForEndTime.unparcel(in);
        int confidenceLevel = in.readInt();
        int densityLevel = in.readInt();
        PersistableBundle vendorData = (PersistableBundle) in.readTypedObject(PersistableBundle.CREATOR);
        this.mEventType = eventType;
        AnnotationValidations.validate((Class<? extends Annotation>) EventCode.class, (Annotation) null, this.mEventType);
        this.mStartTime = startTime;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mStartTime);
        this.mEndTime = endTime;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mEndTime);
        this.mConfidenceLevel = confidenceLevel;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, this.mConfidenceLevel);
        this.mDensityLevel = densityLevel;
        AnnotationValidations.validate((Class<? extends Annotation>) LevelValue.class, (Annotation) null, this.mDensityLevel);
        this.mVendorData = vendorData;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mVendorData);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;
        private int mDensityLevel;
        private Instant mEndTime;
        private int mEventType;
        private Instant mStartTime;
        private PersistableBundle mVendorData;

        public Builder setEventType(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mEventType = value;
            return this;
        }

        public Builder setStartTime(Instant value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mStartTime = value;
            return this;
        }

        public Builder setEndTime(Instant value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mEndTime = value;
            return this;
        }

        public Builder setConfidenceLevel(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mConfidenceLevel = value;
            return this;
        }

        public Builder setDensityLevel(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mDensityLevel = value;
            return this;
        }

        public Builder setVendorData(PersistableBundle value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mVendorData = value;
            return this;
        }

        public AmbientContextEvent build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mEventType = AmbientContextEvent.defaultEventType();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mStartTime = AmbientContextEvent.defaultStartTime();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mEndTime = AmbientContextEvent.defaultEndTime();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mConfidenceLevel = AmbientContextEvent.defaultConfidenceLevel();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mDensityLevel = AmbientContextEvent.defaultDensityLevel();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mVendorData = AmbientContextEvent.defaultVendorData();
            }
            AmbientContextEvent o = new AmbientContextEvent(this.mEventType, this.mStartTime, this.mEndTime, this.mConfidenceLevel, this.mDensityLevel, this.mVendorData);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
