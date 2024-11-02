package android.service.games;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class CreateGameSessionRequest implements Parcelable {
    public static final Parcelable.Creator<CreateGameSessionRequest> CREATOR = new Parcelable.Creator<CreateGameSessionRequest>() { // from class: android.service.games.CreateGameSessionRequest.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CreateGameSessionRequest createFromParcel(Parcel source) {
            return new CreateGameSessionRequest(source.readInt(), source.readString8());
        }

        @Override // android.os.Parcelable.Creator
        public CreateGameSessionRequest[] newArray(int size) {
            return new CreateGameSessionRequest[0];
        }
    };
    private final String mGamePackageName;
    private final int mTaskId;

    /* renamed from: android.service.games.CreateGameSessionRequest$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<CreateGameSessionRequest> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CreateGameSessionRequest createFromParcel(Parcel source) {
            return new CreateGameSessionRequest(source.readInt(), source.readString8());
        }

        @Override // android.os.Parcelable.Creator
        public CreateGameSessionRequest[] newArray(int size) {
            return new CreateGameSessionRequest[0];
        }
    }

    public CreateGameSessionRequest(int taskId, String gamePackageName) {
        this.mTaskId = taskId;
        this.mGamePackageName = gamePackageName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mTaskId);
        dest.writeString8(this.mGamePackageName);
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public String getGamePackageName() {
        return this.mGamePackageName;
    }

    public String toString() {
        return "GameSessionRequest{mTaskId=" + this.mTaskId + ", mGamePackageName='" + this.mGamePackageName + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateGameSessionRequest)) {
            return false;
        }
        CreateGameSessionRequest that = (CreateGameSessionRequest) o;
        return this.mTaskId == that.mTaskId && Objects.equals(this.mGamePackageName, that.mGamePackageName);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTaskId), this.mGamePackageName);
    }
}
