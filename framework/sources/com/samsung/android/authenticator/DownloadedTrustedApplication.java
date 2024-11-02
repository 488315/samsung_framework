package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;
import com.samsung.android.authenticator.SemTrustedApplicationExecutor;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class DownloadedTrustedApplication implements TrustedApplication {
    private static final String TAG = "DATA";
    private final int mHandle;
    private final long mLen;
    private final long mOffset;
    private final ParcelFileDescriptor mPfd;
    private final SemTrustedApplicationExecutor.TrustedAppAssetType mType;

    public DownloadedTrustedApplication(int handle, SemTrustedApplicationExecutor.TrustedAppAssetType type, ParcelFileDescriptor pfd, long offset, long len) {
        this.mHandle = handle;
        this.mType = type;
        this.mPfd = pfd;
        this.mOffset = offset;
        this.mLen = len;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int load() {
        if (!HalService.load(this.mType, this.mPfd, this.mOffset, this.mLen)) {
            AuthenticatorLog.e(TAG, "tl failed. " + this.mType);
            return -1;
        }
        return this.mHandle;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public byte[] execute(byte[] command) {
        return HalService.execute(this.mType, command);
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int unload() {
        if (!HalService.unload(this.mType)) {
            AuthenticatorLog.e(TAG, "tu failed. " + this.mType);
            return -1;
        }
        return 0;
    }

    @Override // com.samsung.android.authenticator.TrustedApplication
    public int getHandle() {
        return this.mHandle;
    }
}
