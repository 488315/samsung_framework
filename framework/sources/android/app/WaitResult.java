package android.app;

import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class WaitResult implements Parcelable {
    public static final Parcelable.Creator<WaitResult> CREATOR = new Parcelable.Creator<WaitResult>() { // from class: android.app.WaitResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WaitResult createFromParcel(Parcel source) {
            return new WaitResult(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WaitResult[] newArray(int size) {
            return new WaitResult[size];
        }
    };
    public static final int INVALID_DELAY = -1;
    public static final int LAUNCH_STATE_COLD = 1;
    public static final int LAUNCH_STATE_HOT = 3;
    public static final int LAUNCH_STATE_RELAUNCH = 4;
    public static final int LAUNCH_STATE_UNKNOWN = 0;
    public static final int LAUNCH_STATE_WARM = 2;
    public int launchState;
    public int result;
    public boolean timeout;
    public long totalTime;
    public ComponentName who;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchState {
    }

    public WaitResult() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeInt(this.timeout ? 1 : 0);
        ComponentName.writeToParcel(this.who, parcel);
        parcel.writeLong(this.totalTime);
        parcel.writeInt(this.launchState);
    }

    private WaitResult(Parcel source) {
        this.result = source.readInt();
        this.timeout = source.readInt() != 0;
        this.who = ComponentName.readFromParcel(source);
        this.totalTime = source.readLong();
        this.launchState = source.readInt();
    }

    public void dump(PrintWriter pw, String prefix) {
        pw.println(prefix + "WaitResult:");
        pw.println(prefix + "  result=" + this.result);
        pw.println(prefix + "  timeout=" + this.timeout);
        pw.println(prefix + "  who=" + this.who);
        pw.println(prefix + "  totalTime=" + this.totalTime);
        pw.println(prefix + "  launchState=" + this.launchState);
    }

    public static String launchStateToString(int type) {
        switch (type) {
            case 1:
                return "COLD";
            case 2:
                return "WARM";
            case 3:
                return "HOT";
            case 4:
                return "RELAUNCH";
            default:
                return "UNKNOWN (" + type + NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
