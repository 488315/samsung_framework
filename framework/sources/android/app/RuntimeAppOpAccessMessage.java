package android.app;

import android.annotation.IntRange;
import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

@SystemApi
/* loaded from: classes.dex */
public final class RuntimeAppOpAccessMessage implements Parcelable {
    public static final Parcelable.Creator<RuntimeAppOpAccessMessage> CREATOR = new Parcelable.Creator<RuntimeAppOpAccessMessage>() { // from class: android.app.RuntimeAppOpAccessMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimeAppOpAccessMessage[] newArray(int size) {
            return new RuntimeAppOpAccessMessage[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimeAppOpAccessMessage createFromParcel(Parcel in) {
            return new RuntimeAppOpAccessMessage(in);
        }
    };
    private final String mAttributionTag;
    private final String mMessage;
    private final int mOpCode;
    private final String mPackageName;
    private final int mSamplingStrategy;
    private final int mUid;

    public String getOp() {
        return AppOpsManager.opToPublicName(this.mOpCode);
    }

    public RuntimeAppOpAccessMessage(int uid, int opCode, String packageName, String attributionTag, String message, int samplingStrategy) {
        this.mUid = uid;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, this.mUid, "from", 0L);
        this.mOpCode = opCode;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, this.mOpCode, "from", 0L, "to", 148L);
        this.mPackageName = packageName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPackageName);
        this.mAttributionTag = attributionTag;
        this.mMessage = message;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mMessage);
        this.mSamplingStrategy = samplingStrategy;
        AnnotationValidations.validate((Class<? extends Annotation>) AppOpsManager.SamplingStrategy.class, (Annotation) null, this.mSamplingStrategy);
    }

    public int getUid() {
        return this.mUid;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getSamplingStrategy() {
        return this.mSamplingStrategy;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mAttributionTag != null ? (byte) (0 | 8) : (byte) 0;
        dest.writeByte(flg);
        dest.writeInt(this.mUid);
        dest.writeInt(this.mOpCode);
        dest.writeString(this.mPackageName);
        if (this.mAttributionTag != null) {
            dest.writeString(this.mAttributionTag);
        }
        dest.writeString(this.mMessage);
        dest.writeInt(this.mSamplingStrategy);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RuntimeAppOpAccessMessage(Parcel in) {
        byte flg = in.readByte();
        int uid = in.readInt();
        int opCode = in.readInt();
        String packageName = in.readString();
        String attributionTag = (flg & 8) == 0 ? null : in.readString();
        String message = in.readString();
        int samplingStrategy = in.readInt();
        this.mUid = uid;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, this.mUid, "from", 0L);
        this.mOpCode = opCode;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, this.mOpCode, "from", 0L, "to", 148L);
        this.mPackageName = packageName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPackageName);
        this.mAttributionTag = attributionTag;
        this.mMessage = message;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mMessage);
        this.mSamplingStrategy = samplingStrategy;
        AnnotationValidations.validate((Class<? extends Annotation>) AppOpsManager.SamplingStrategy.class, (Annotation) null, this.mSamplingStrategy);
    }

    @Deprecated
    private void __metadata() {
    }
}
