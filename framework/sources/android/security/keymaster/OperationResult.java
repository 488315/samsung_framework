package android.security.keymaster;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class OperationResult implements Parcelable {
    public static final Parcelable.Creator<OperationResult> CREATOR = new Parcelable.Creator<OperationResult>() { // from class: android.security.keymaster.OperationResult.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public OperationResult createFromParcel(Parcel in) {
            return new OperationResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public OperationResult[] newArray(int length) {
            return new OperationResult[length];
        }
    };
    public final int inputConsumed;
    public final long operationHandle;
    public final KeymasterArguments outParams;
    public final byte[] output;
    public final int resultCode;
    public final IBinder token;

    /* renamed from: android.security.keymaster.OperationResult$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<OperationResult> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public OperationResult createFromParcel(Parcel in) {
            return new OperationResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public OperationResult[] newArray(int length) {
            return new OperationResult[length];
        }
    }

    public OperationResult(int resultCode, IBinder token, long operationHandle, int inputConsumed, byte[] output, KeymasterArguments outParams) {
        this.resultCode = resultCode;
        this.token = token;
        this.operationHandle = operationHandle;
        this.inputConsumed = inputConsumed;
        this.output = output;
        this.outParams = outParams;
    }

    public OperationResult(int resultCode) {
        this(resultCode, null, 0L, 0, null, null);
    }

    protected OperationResult(Parcel in) {
        this.resultCode = in.readInt();
        this.token = in.readStrongBinder();
        this.operationHandle = in.readLong();
        this.inputConsumed = in.readInt();
        this.output = in.createByteArray();
        this.outParams = KeymasterArguments.CREATOR.createFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.resultCode);
        out.writeStrongBinder(this.token);
        out.writeLong(this.operationHandle);
        out.writeInt(this.inputConsumed);
        out.writeByteArray(this.output);
        this.outParams.writeToParcel(out, flags);
    }
}
