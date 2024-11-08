package android.security.identity;

import java.security.cert.X509Certificate;

/* loaded from: classes3.dex */
public class AccessControlProfile {
    private AccessControlProfileId mAccessControlProfileId;
    private X509Certificate mReaderCertificate;
    private boolean mUserAuthenticationRequired;
    private long mUserAuthenticationTimeout;

    /* synthetic */ AccessControlProfile(AccessControlProfileIA accessControlProfileIA) {
        this();
    }

    private AccessControlProfile() {
        this.mAccessControlProfileId = new AccessControlProfileId(0);
        this.mReaderCertificate = null;
        this.mUserAuthenticationRequired = true;
        this.mUserAuthenticationTimeout = 0L;
    }

    public AccessControlProfileId getAccessControlProfileId() {
        return this.mAccessControlProfileId;
    }

    public long getUserAuthenticationTimeout() {
        return this.mUserAuthenticationTimeout;
    }

    public boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    public X509Certificate getReaderCertificate() {
        return this.mReaderCertificate;
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private AccessControlProfile mProfile;

        public Builder(AccessControlProfileId accessControlProfileId) {
            AccessControlProfile accessControlProfile = new AccessControlProfile();
            this.mProfile = accessControlProfile;
            accessControlProfile.mAccessControlProfileId = accessControlProfileId;
        }

        public Builder setUserAuthenticationRequired(boolean userAuthenticationRequired) {
            this.mProfile.mUserAuthenticationRequired = userAuthenticationRequired;
            return this;
        }

        public Builder setUserAuthenticationTimeout(long userAuthenticationTimeoutMillis) {
            this.mProfile.mUserAuthenticationTimeout = userAuthenticationTimeoutMillis;
            return this;
        }

        public Builder setReaderCertificate(X509Certificate readerCertificate) {
            this.mProfile.mReaderCertificate = readerCertificate;
            return this;
        }

        public AccessControlProfile build() {
            return this.mProfile;
        }
    }
}
