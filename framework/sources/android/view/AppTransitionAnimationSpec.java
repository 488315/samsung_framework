package android.view;

import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class AppTransitionAnimationSpec implements Parcelable {
    public static final Parcelable.Creator<AppTransitionAnimationSpec> CREATOR = new Parcelable.Creator<AppTransitionAnimationSpec>() { // from class: android.view.AppTransitionAnimationSpec.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AppTransitionAnimationSpec createFromParcel(Parcel in) {
            return new AppTransitionAnimationSpec(in);
        }

        @Override // android.os.Parcelable.Creator
        public AppTransitionAnimationSpec[] newArray(int size) {
            return new AppTransitionAnimationSpec[size];
        }
    };
    public final HardwareBuffer buffer;
    public final Rect rect;
    public final int taskId;

    public AppTransitionAnimationSpec(int taskId, HardwareBuffer buffer, Rect rect) {
        this.taskId = taskId;
        this.rect = rect;
        this.buffer = buffer;
    }

    public AppTransitionAnimationSpec(Parcel in) {
        this.taskId = in.readInt();
        this.rect = (Rect) in.readTypedObject(Rect.CREATOR);
        this.buffer = (HardwareBuffer) in.readTypedObject(HardwareBuffer.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskId);
        dest.writeTypedObject(this.rect, 0);
        dest.writeTypedObject(this.buffer, 0);
    }

    /* renamed from: android.view.AppTransitionAnimationSpec$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<AppTransitionAnimationSpec> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AppTransitionAnimationSpec createFromParcel(Parcel in) {
            return new AppTransitionAnimationSpec(in);
        }

        @Override // android.os.Parcelable.Creator
        public AppTransitionAnimationSpec[] newArray(int size) {
            return new AppTransitionAnimationSpec[size];
        }
    }

    public String toString() {
        return "{taskId: " + this.taskId + ", buffer: " + this.buffer + ", rect: " + this.rect + "}";
    }
}
