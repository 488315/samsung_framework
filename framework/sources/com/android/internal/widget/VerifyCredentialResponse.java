package com.android.internal.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.gatekeeper.GateKeeperResponse;
import android.util.Slog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes5.dex */
public final class VerifyCredentialResponse implements Parcelable {
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    public static final int RESPONSE_SKIP = 1;
    private static final String TAG = "VerifyCredentialResponse";
    private final byte[] mGatekeeperHAT;
    private final long mGatekeeperPasswordHandle;
    private final int mResponseCode;
    private byte[] mSecret;
    private final int mTimeout;
    public static final VerifyCredentialResponse OK = new Builder().build();
    public static final VerifyCredentialResponse ERROR = fromError();
    public static final VerifyCredentialResponse SKIP = fromTimeout(0);
    public static final Parcelable.Creator<VerifyCredentialResponse> CREATOR = new Parcelable.Creator<VerifyCredentialResponse>() { // from class: com.android.internal.widget.VerifyCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifyCredentialResponse createFromParcel(Parcel source) {
            int responseCode = source.readInt();
            int timeout = source.readInt();
            byte[] gatekeeperHAT = source.createByteArray();
            long gatekeeperPasswordHandle = source.readLong();
            return new VerifyCredentialResponse(responseCode, timeout, gatekeeperHAT, gatekeeperPasswordHandle);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifyCredentialResponse[] newArray(int size) {
            return new VerifyCredentialResponse[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    @interface ResponseCode {
    }

    public static class Builder {
        private byte[] mGatekeeperHAT;
        private long mGatekeeperPasswordHandle;

        public Builder setGatekeeperHAT(byte[] gatekeeperHAT) {
            this.mGatekeeperHAT = gatekeeperHAT;
            return this;
        }

        public Builder setGatekeeperPasswordHandle(long gatekeeperPasswordHandle) {
            this.mGatekeeperPasswordHandle = gatekeeperPasswordHandle;
            return this;
        }

        public VerifyCredentialResponse build() {
            return new VerifyCredentialResponse(0, 0, this.mGatekeeperHAT, this.mGatekeeperPasswordHandle);
        }
    }

    public static VerifyCredentialResponse fromTimeout(int timeout) {
        return new VerifyCredentialResponse(1, timeout, null, 0L);
    }

    public static VerifyCredentialResponse fromError() {
        return new VerifyCredentialResponse(-1, 0, null, 0L);
    }

    private VerifyCredentialResponse(int responseCode, int timeout, byte[] gatekeeperHAT, long gatekeeperPasswordHandle) {
        this.mResponseCode = responseCode;
        this.mTimeout = timeout;
        this.mGatekeeperHAT = gatekeeperHAT;
        this.mGatekeeperPasswordHandle = gatekeeperPasswordHandle;
    }

    private VerifyCredentialResponse(int responseCode, int timeout, byte[] gatekeeperHAT, long gatekeeperPasswordHandle, byte[] secret) {
        this.mResponseCode = responseCode;
        this.mTimeout = timeout;
        this.mGatekeeperHAT = gatekeeperHAT;
        this.mGatekeeperPasswordHandle = gatekeeperPasswordHandle;
        this.mSecret = secret;
    }

    public VerifyCredentialResponse stripPayload() {
        return new VerifyCredentialResponse(this.mResponseCode, this.mTimeout, null, 0L);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mResponseCode);
        dest.writeInt(this.mTimeout);
        dest.writeByteArray(this.mGatekeeperHAT);
        dest.writeLong(this.mGatekeeperPasswordHandle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte[] getGatekeeperHAT() {
        return this.mGatekeeperHAT;
    }

    public long getGatekeeperPasswordHandle() {
        return this.mGatekeeperPasswordHandle;
    }

    public boolean containsGatekeeperPasswordHandle() {
        return this.mGatekeeperPasswordHandle != 0;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public boolean isMatched() {
        return this.mResponseCode == 0;
    }

    public String toString() {
        return "Response: " + this.mResponseCode + ", GK HAT: " + (this.mGatekeeperHAT != null) + ", GK PW: " + (this.mGatekeeperPasswordHandle != 0);
    }

    public static VerifyCredentialResponse fromGateKeeperResponse(GateKeeperResponse gateKeeperResponse) {
        int responseCode = gateKeeperResponse.getResponseCode();
        if (responseCode == 1) {
            return fromTimeout(gateKeeperResponse.getTimeout());
        }
        if (responseCode == 0) {
            byte[] token = gateKeeperResponse.getPayload();
            if (token == null) {
                Slog.e(TAG, "verifyChallenge response had no associated payload");
                return fromError();
            }
            return new Builder().setGatekeeperHAT(token).build();
        }
        return fromError();
    }

    public byte[] getSecret() {
        return this.mSecret;
    }

    public void setSecret(byte[] secret) {
        this.mSecret = secret;
    }

    public void destroy() {
        if (this.mSecret != null) {
            Arrays.fill(this.mSecret, 0, this.mSecret.length, (byte) 0);
        }
    }
}
