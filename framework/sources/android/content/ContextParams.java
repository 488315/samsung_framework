package android.content;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class ContextParams {
    public static final ContextParams EMPTY = new Builder().build();
    private final String mAttributionTag;
    private final AttributionSource mNext;
    private final Set<String> mRenouncedPermissions;
    private final boolean mShouldRegisterAttributionSource;

    private ContextParams(String attributionTag, AttributionSource next, Set<String> renouncedPermissions, boolean shouldRegister) {
        this.mAttributionTag = attributionTag;
        this.mNext = next;
        this.mRenouncedPermissions = renouncedPermissions != null ? renouncedPermissions : Collections.emptySet();
        this.mShouldRegisterAttributionSource = shouldRegister;
    }

    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    @SystemApi
    public Set<String> getRenouncedPermissions() {
        return this.mRenouncedPermissions;
    }

    public boolean isRenouncedPermission(String permission) {
        return this.mRenouncedPermissions.contains(permission);
    }

    public AttributionSource getNextAttributionSource() {
        return this.mNext;
    }

    public boolean shouldRegisterAttributionSource() {
        return this.mShouldRegisterAttributionSource;
    }

    public static final class Builder {
        private String mAttributionTag;
        private AttributionSource mNext;
        private Set<String> mRenouncedPermissions;
        private boolean mShouldRegisterAttributionSource;

        public Builder() {
            this.mRenouncedPermissions = Collections.emptySet();
        }

        public Builder(ContextParams params) {
            this.mRenouncedPermissions = Collections.emptySet();
            Objects.requireNonNull(params);
            this.mAttributionTag = params.mAttributionTag;
            this.mRenouncedPermissions = params.mRenouncedPermissions;
            this.mNext = params.mNext;
        }

        public Builder setAttributionTag(String attributionTag) {
            this.mAttributionTag = attributionTag;
            return this;
        }

        public Builder setNextAttributionSource(AttributionSource next) {
            this.mNext = next;
            return this;
        }

        public Builder setShouldRegisterAttributionSource(boolean shouldRegister) {
            this.mShouldRegisterAttributionSource = shouldRegister;
            return this;
        }

        @SystemApi
        public Builder setRenouncedPermissions(Set<String> renouncedPermissions) {
            if (renouncedPermissions != null && !renouncedPermissions.isEmpty() && ActivityThread.currentApplication().checkSelfPermission(Manifest.permission.RENOUNCE_PERMISSIONS) != 0) {
                throw new SecurityException("Renouncing permissions requires: android.permission.RENOUNCE_PERMISSIONS");
            }
            this.mRenouncedPermissions = renouncedPermissions;
            return this;
        }

        public ContextParams build() {
            return new ContextParams(this.mAttributionTag, this.mNext, this.mRenouncedPermissions, this.mShouldRegisterAttributionSource);
        }
    }
}
