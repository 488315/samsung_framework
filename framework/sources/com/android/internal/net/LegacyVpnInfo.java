package com.android.internal.net;

import android.app.PendingIntent;
import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class LegacyVpnInfo implements Parcelable {
    public static final Parcelable.Creator<LegacyVpnInfo> CREATOR = new Parcelable.Creator<LegacyVpnInfo>() { // from class: com.android.internal.net.LegacyVpnInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LegacyVpnInfo createFromParcel(Parcel in) {
            LegacyVpnInfo info = new LegacyVpnInfo();
            info.key = in.readString();
            info.state = in.readInt();
            info.intent = (PendingIntent) in.readParcelable(null, PendingIntent.class);
            return info;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LegacyVpnInfo[] newArray(int size) {
            return new LegacyVpnInfo[size];
        }
    };
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_FAILED = 5;
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_TIMEOUT = 4;
    private static final String TAG = "LegacyVpnInfo";
    public PendingIntent intent;
    public String key;
    public int state = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.key);
        out.writeInt(this.state);
        out.writeParcelable(this.intent, flags);
    }

    /* renamed from: com.android.internal.net.LegacyVpnInfo$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState = new int[NetworkInfo.DetailedState.values().length];

        static {
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.CONNECTING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.CONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.DISCONNECTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.FAILED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static int stateFromNetworkInfo(NetworkInfo.DetailedState state) {
        switch (AnonymousClass2.$SwitchMap$android$net$NetworkInfo$DetailedState[state.ordinal()]) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                Log.w(TAG, "Unhandled state " + state + " ; treating as disconnected");
                break;
        }
        return 0;
    }
}
