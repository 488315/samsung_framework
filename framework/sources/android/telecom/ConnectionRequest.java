package android.telecom;

import android.annotation.SystemApi;
import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.telecom.Connection;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class ConnectionRequest implements Parcelable {
    public static final Parcelable.Creator<ConnectionRequest> CREATOR = new Parcelable.Creator<ConnectionRequest>() { // from class: android.telecom.ConnectionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectionRequest createFromParcel(Parcel source) {
            return new ConnectionRequest(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectionRequest[] newArray(int size) {
            return new ConnectionRequest[size];
        }
    };
    private final PhoneAccountHandle mAccountHandle;
    private final Uri mAddress;
    private final Bundle mExtras;
    private final boolean mIsAdhocConference;
    private List<Uri> mParticipants;
    private final ParcelFileDescriptor mRttPipeFromInCall;
    private final ParcelFileDescriptor mRttPipeToInCall;
    private Connection.RttTextStream mRttTextStream;
    private final boolean mShouldShowIncomingCallUi;
    private final String mTelecomCallId;
    private final int mVideoState;

    public static final class Builder {
        private PhoneAccountHandle mAccountHandle;
        private Uri mAddress;
        private Bundle mExtras;
        private List<Uri> mParticipants;
        private ParcelFileDescriptor mRttPipeFromInCall;
        private ParcelFileDescriptor mRttPipeToInCall;
        private String mTelecomCallId;
        private int mVideoState = 0;
        private boolean mShouldShowIncomingCallUi = false;
        private boolean mIsAdhocConference = false;

        public Builder setAccountHandle(PhoneAccountHandle accountHandle) {
            this.mAccountHandle = accountHandle;
            return this;
        }

        public Builder setParticipants(List<Uri> participants) {
            this.mParticipants = participants;
            return this;
        }

        public Builder setAddress(Uri address) {
            this.mAddress = address;
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public Builder setVideoState(int videoState) {
            this.mVideoState = videoState;
            return this;
        }

        public Builder setTelecomCallId(String telecomCallId) {
            this.mTelecomCallId = telecomCallId;
            return this;
        }

        public Builder setShouldShowIncomingCallUi(boolean shouldShowIncomingCallUi) {
            this.mShouldShowIncomingCallUi = shouldShowIncomingCallUi;
            return this;
        }

        public Builder setIsAdhocConferenceCall(boolean isAdhocConference) {
            this.mIsAdhocConference = isAdhocConference;
            return this;
        }

        public Builder setRttPipeFromInCall(ParcelFileDescriptor rttPipeFromInCall) {
            this.mRttPipeFromInCall = rttPipeFromInCall;
            return this;
        }

        public Builder setRttPipeToInCall(ParcelFileDescriptor rttPipeToInCall) {
            this.mRttPipeToInCall = rttPipeToInCall;
            return this;
        }

        public ConnectionRequest build() {
            return new ConnectionRequest(this.mAccountHandle, this.mAddress, this.mExtras, this.mVideoState, this.mTelecomCallId, this.mShouldShowIncomingCallUi, this.mRttPipeFromInCall, this.mRttPipeToInCall, this.mParticipants, this.mIsAdhocConference);
        }
    }

    public ConnectionRequest(PhoneAccountHandle accountHandle, Uri handle, Bundle extras) {
        this(accountHandle, handle, extras, 0, null, false, null, null);
    }

    public ConnectionRequest(PhoneAccountHandle accountHandle, Uri handle, Bundle extras, int videoState) {
        this(accountHandle, handle, extras, videoState, null, false, null, null);
    }

    public ConnectionRequest(PhoneAccountHandle accountHandle, Uri handle, Bundle extras, int videoState, String telecomCallId, boolean shouldShowIncomingCallUi) {
        this(accountHandle, handle, extras, videoState, telecomCallId, shouldShowIncomingCallUi, null, null);
    }

    private ConnectionRequest(PhoneAccountHandle accountHandle, Uri handle, Bundle extras, int videoState, String telecomCallId, boolean shouldShowIncomingCallUi, ParcelFileDescriptor rttPipeFromInCall, ParcelFileDescriptor rttPipeToInCall) {
        this(accountHandle, handle, extras, videoState, telecomCallId, shouldShowIncomingCallUi, rttPipeFromInCall, rttPipeToInCall, null, false);
    }

    private ConnectionRequest(PhoneAccountHandle accountHandle, Uri handle, Bundle extras, int videoState, String telecomCallId, boolean shouldShowIncomingCallUi, ParcelFileDescriptor rttPipeFromInCall, ParcelFileDescriptor rttPipeToInCall, List<Uri> participants, boolean isAdhocConference) {
        this.mAccountHandle = accountHandle;
        this.mAddress = handle;
        this.mExtras = extras;
        this.mVideoState = videoState;
        this.mTelecomCallId = telecomCallId;
        this.mShouldShowIncomingCallUi = shouldShowIncomingCallUi;
        this.mRttPipeFromInCall = rttPipeFromInCall;
        this.mRttPipeToInCall = rttPipeToInCall;
        this.mParticipants = participants;
        this.mIsAdhocConference = isAdhocConference;
    }

    private ConnectionRequest(Parcel in) {
        this.mAccountHandle = (PhoneAccountHandle) in.readParcelable(getClass().getClassLoader(), PhoneAccountHandle.class);
        this.mAddress = (Uri) in.readParcelable(getClass().getClassLoader(), Uri.class);
        this.mExtras = (Bundle) in.readParcelable(getClass().getClassLoader(), Bundle.class);
        this.mVideoState = in.readInt();
        this.mTelecomCallId = in.readString();
        this.mShouldShowIncomingCallUi = in.readInt() == 1;
        this.mRttPipeFromInCall = (ParcelFileDescriptor) in.readParcelable(getClass().getClassLoader(), ParcelFileDescriptor.class);
        this.mRttPipeToInCall = (ParcelFileDescriptor) in.readParcelable(getClass().getClassLoader(), ParcelFileDescriptor.class);
        this.mParticipants = new ArrayList();
        in.readList(this.mParticipants, getClass().getClassLoader(), Uri.class);
        this.mIsAdhocConference = in.readInt() == 1;
    }

    public PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public List<Uri> getParticipants() {
        return this.mParticipants;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    @SystemApi
    public String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public boolean shouldShowIncomingCallUi() {
        return this.mShouldShowIncomingCallUi;
    }

    public boolean isAdhocConferenceCall() {
        return this.mIsAdhocConference;
    }

    public ParcelFileDescriptor getRttPipeToInCall() {
        return this.mRttPipeToInCall;
    }

    public ParcelFileDescriptor getRttPipeFromInCall() {
        return this.mRttPipeFromInCall;
    }

    public Connection.RttTextStream getRttTextStream() {
        if (isRequestingRtt()) {
            if (this.mRttTextStream == null) {
                this.mRttTextStream = new Connection.RttTextStream(this.mRttPipeToInCall, this.mRttPipeFromInCall);
            }
            return this.mRttTextStream;
        }
        return null;
    }

    public boolean isRequestingRtt() {
        return (this.mRttPipeFromInCall == null || this.mRttPipeToInCall == null) ? false : true;
    }

    public String toString() {
        Object logSafePhoneNumber;
        if (this.mAddress == null) {
            logSafePhoneNumber = Uri.EMPTY;
        } else {
            logSafePhoneNumber = Connection.toLogSafePhoneNumber(this.mAddress.toString());
        }
        return String.format("ConnectionRequest %s %s isAdhocConf: %s", logSafePhoneNumber, Log.maskPii(bundleToString(this.mExtras)), isAdhocConferenceCall() ? GnssSignalType.CODE_TYPE_Y : GnssSignalType.CODE_TYPE_N);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static String bundleToString(Bundle extras) {
        char c;
        if (extras == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Bundle[");
        for (String key : extras.keySet()) {
            sb.append(key);
            sb.append("=");
            switch (key.hashCode()) {
                case -1582002592:
                    if (key.equals(TelecomManager.EXTRA_UNKNOWN_CALL_HANDLE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1513984200:
                    if (key.equals(TelecomManager.EXTRA_INCOMING_CALL_ADDRESS)) {
                        c = 0;
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
                case 1:
                    sb.append(Log.pii(extras.get(key)));
                    break;
                default:
                    sb.append(extras.get(key));
                    break;
            }
            sb.append(", ");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mAccountHandle, 0);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeParcelable(this.mExtras, 0);
        parcel.writeInt(this.mVideoState);
        parcel.writeString(this.mTelecomCallId);
        parcel.writeInt(this.mShouldShowIncomingCallUi ? 1 : 0);
        parcel.writeParcelable(this.mRttPipeFromInCall, 0);
        parcel.writeParcelable(this.mRttPipeToInCall, 0);
        parcel.writeList(this.mParticipants);
        parcel.writeInt(this.mIsAdhocConference ? 1 : 0);
    }
}
