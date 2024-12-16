package android.telephony.mbms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes4.dex */
public class MbmsDownloadSessionCallback {

    @Retention(RetentionPolicy.SOURCE)
    private @interface DownloadError {
    }

    public void onError(int errorCode, String message) {
    }

    public void onFileServicesUpdated(List<FileServiceInfo> services) {
    }

    public void onMiddlewareReady() {
    }
}
