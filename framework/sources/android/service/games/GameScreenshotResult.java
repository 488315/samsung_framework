package android.service.games;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class GameScreenshotResult implements Parcelable {
    public static final Parcelable.Creator<GameScreenshotResult> CREATOR = new Parcelable.Creator<GameScreenshotResult>() { // from class: android.service.games.GameScreenshotResult.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GameScreenshotResult createFromParcel(Parcel source) {
            return new GameScreenshotResult(source.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public GameScreenshotResult[] newArray(int size) {
            return new GameScreenshotResult[0];
        }
    };
    public static final int GAME_SCREENSHOT_ERROR_INTERNAL_ERROR = 1;
    public static final int GAME_SCREENSHOT_SUCCESS = 0;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface GameScreenshotStatus {
    }

    /* synthetic */ GameScreenshotResult(int i, GameScreenshotResultIA gameScreenshotResultIA) {
        this(i);
    }

    /* renamed from: android.service.games.GameScreenshotResult$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<GameScreenshotResult> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GameScreenshotResult createFromParcel(Parcel source) {
            return new GameScreenshotResult(source.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public GameScreenshotResult[] newArray(int size) {
            return new GameScreenshotResult[0];
        }
    }

    public static GameScreenshotResult createSuccessResult() {
        return new GameScreenshotResult(0);
    }

    public static GameScreenshotResult createInternalErrorResult() {
        return new GameScreenshotResult(1);
    }

    private GameScreenshotResult(int status) {
        this.mStatus = status;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mStatus);
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String toString() {
        return "GameScreenshotResult{mStatus=" + this.mStatus + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameScreenshotResult)) {
            return false;
        }
        GameScreenshotResult that = (GameScreenshotResult) o;
        return this.mStatus == that.mStatus;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStatus));
    }
}
