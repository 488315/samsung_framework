package android.app.time;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeState implements Parcelable {
    public static final Parcelable.Creator<TimeState> CREATOR = new Parcelable.Creator<TimeState>() { // from class: android.app.time.TimeState.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TimeState createFromParcel(Parcel in) {
            return TimeState.createFromParcel(in);
        }

        @Override // android.os.Parcelable.Creator
        public TimeState[] newArray(int size) {
            return new TimeState[size];
        }
    };
    private final UnixEpochTime mUnixEpochTime;
    private final boolean mUserShouldConfirmTime;

    /* renamed from: android.app.time.TimeState$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<TimeState> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TimeState createFromParcel(Parcel in) {
            return TimeState.createFromParcel(in);
        }

        @Override // android.os.Parcelable.Creator
        public TimeState[] newArray(int size) {
            return new TimeState[size];
        }
    }

    public TimeState(UnixEpochTime unixEpochTime, boolean userShouldConfirmTime) {
        this.mUnixEpochTime = (UnixEpochTime) Objects.requireNonNull(unixEpochTime);
        this.mUserShouldConfirmTime = userShouldConfirmTime;
    }

    public static TimeState createFromParcel(Parcel in) {
        UnixEpochTime unixEpochTime = (UnixEpochTime) in.readParcelable(null, UnixEpochTime.class);
        boolean userShouldConfirmId = in.readBoolean();
        return new TimeState(unixEpochTime, userShouldConfirmId);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mUnixEpochTime, 0);
        dest.writeBoolean(this.mUserShouldConfirmTime);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x000e. Please report as an issue. */
    public static TimeState parseCommandLineArgs(ShellCommand cmd) {
        char c;
        Long elapsedRealtimeMillis = null;
        Long unixEpochTimeMillis = null;
        Boolean userShouldConfirmTime = null;
        while (true) {
            String opt = cmd.getNextArg();
            if (opt != null) {
                switch (opt.hashCode()) {
                    case 48316014:
                        if (opt.equals("--elapsed_realtime")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 410278458:
                        if (opt.equals("--unix_epoch_time")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 663918372:
                        if (opt.equals("--user_should_confirm_time")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        elapsedRealtimeMillis = Long.valueOf(Long.parseLong(cmd.getNextArgRequired()));
                        break;
                    case 1:
                        unixEpochTimeMillis = Long.valueOf(Long.parseLong(cmd.getNextArgRequired()));
                        break;
                    case 2:
                        userShouldConfirmTime = Boolean.valueOf(Boolean.parseBoolean(cmd.getNextArgRequired()));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown option: " + opt);
                }
            } else {
                if (elapsedRealtimeMillis == null) {
                    throw new IllegalArgumentException("No elapsedRealtimeMillis specified.");
                }
                if (unixEpochTimeMillis == null) {
                    throw new IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                if (userShouldConfirmTime == null) {
                    throw new IllegalArgumentException("No userShouldConfirmTime specified.");
                }
                UnixEpochTime unixEpochTime = new UnixEpochTime(elapsedRealtimeMillis.longValue(), unixEpochTimeMillis.longValue());
                return new TimeState(unixEpochTime, userShouldConfirmTime.booleanValue());
            }
        }
    }

    public static void printCommandLineOpts(PrintWriter pw) {
        pw.println("TimeState options:");
        pw.println("  --elapsed_realtime <elapsed realtime millis>");
        pw.println("  --unix_epoch_time <Unix epoch time millis>");
        pw.println("  --user_should_confirm_time {true|false}");
        pw.println();
        pw.println("See " + TimeState.class.getName() + " for more information");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UnixEpochTime getUnixEpochTime() {
        return this.mUnixEpochTime;
    }

    public boolean getUserShouldConfirmTime() {
        return this.mUserShouldConfirmTime;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeState that = (TimeState) o;
        if (Objects.equals(this.mUnixEpochTime, that.mUnixEpochTime) && this.mUserShouldConfirmTime == that.mUserShouldConfirmTime) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mUnixEpochTime, Boolean.valueOf(this.mUserShouldConfirmTime));
    }

    public String toString() {
        return "TimeState{mUnixEpochTime=" + this.mUnixEpochTime + ", mUserShouldConfirmTime=" + this.mUserShouldConfirmTime + '}';
    }
}
