package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.DownloadProgressListener;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.DownloadStatusListener;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.FileServiceInfo;
import android.telephony.mbms.IDownloadProgressListener;
import android.telephony.mbms.IDownloadStatusListener;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import android.telephony.mbms.MbmsDownloadSessionCallback;
import android.telephony.mbms.vendor.IMbmsDownloadService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SystemApi
/* loaded from: classes3.dex */
public class MbmsDownloadServiceBase extends IMbmsDownloadService.Stub {
    private final Map<IBinder, DownloadStatusListener> mDownloadStatusListenerBinderMap = new HashMap();
    private final Map<IBinder, DownloadProgressListener> mDownloadProgressListenerBinderMap = new HashMap();
    private final Map<IBinder, IBinder.DeathRecipient> mDownloadCallbackDeathRecipients = new HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static abstract class VendorDownloadStatusListener extends DownloadStatusListener {
        private final IDownloadStatusListener mListener;

        protected abstract void onRemoteException(RemoteException remoteException);

        public VendorDownloadStatusListener(IDownloadStatusListener listener) {
            this.mListener = listener;
        }

        @Override // android.telephony.mbms.DownloadStatusListener
        public void onStatusUpdated(DownloadRequest request, FileInfo fileInfo, int state) {
            try {
                this.mListener.onStatusUpdated(request, fileInfo, state);
            } catch (RemoteException e) {
                onRemoteException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static abstract class VendorDownloadProgressListener extends DownloadProgressListener {
        private final IDownloadProgressListener mListener;

        protected abstract void onRemoteException(RemoteException remoteException);

        public VendorDownloadProgressListener(IDownloadProgressListener listener) {
            this.mListener = listener;
        }

        @Override // android.telephony.mbms.DownloadProgressListener
        public void onProgressUpdated(DownloadRequest request, FileInfo fileInfo, int currentDownloadSize, int fullDownloadSize, int currentDecodedSize, int fullDecodedSize) {
            try {
                this.mListener.onProgressUpdated(request, fileInfo, currentDownloadSize, fullDownloadSize, currentDecodedSize, fullDecodedSize);
            } catch (RemoteException e) {
                onRemoteException(e);
            }
        }
    }

    public int initialize(int subscriptionId, MbmsDownloadSessionCallback callback) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int initialize(int subscriptionId, IMbmsDownloadSessionCallback callback) throws RemoteException {
        if (callback == null) {
            throw new NullPointerException("Callback must not be null");
        }
        int uid = Binder.getCallingUid();
        int result = initialize(subscriptionId, new MbmsDownloadSessionCallback() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.1
            final /* synthetic */ IMbmsDownloadSessionCallback val$callback;
            final /* synthetic */ int val$subscriptionId;
            final /* synthetic */ int val$uid;

            AnonymousClass1(IMbmsDownloadSessionCallback callback2, int uid2, int subscriptionId2) {
                callback = callback2;
                uid = uid2;
                subscriptionId = subscriptionId2;
            }

            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onError(int errorCode, String message) {
                try {
                    if (errorCode == -1) {
                        throw new IllegalArgumentException("Middleware cannot send an unknown error.");
                    }
                    callback.onError(errorCode, message);
                } catch (RemoteException e) {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
                }
            }

            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onFileServicesUpdated(List<FileServiceInfo> services) {
                try {
                    callback.onFileServicesUpdated(services);
                } catch (RemoteException e) {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
                }
            }

            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onMiddlewareReady() {
                try {
                    callback.onMiddlewareReady();
                } catch (RemoteException e) {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
                }
            }
        });
        if (result == 0) {
            callback2.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.2
                final /* synthetic */ int val$subscriptionId;
                final /* synthetic */ int val$uid;

                AnonymousClass2(int uid2, int subscriptionId2) {
                    uid = uid2;
                    subscriptionId = subscriptionId2;
                }

                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
                }
            }, 0);
        }
        return result;
    }

    /* renamed from: android.telephony.mbms.vendor.MbmsDownloadServiceBase$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 extends MbmsDownloadSessionCallback {
        final /* synthetic */ IMbmsDownloadSessionCallback val$callback;
        final /* synthetic */ int val$subscriptionId;
        final /* synthetic */ int val$uid;

        AnonymousClass1(IMbmsDownloadSessionCallback callback2, int uid2, int subscriptionId2) {
            callback = callback2;
            uid = uid2;
            subscriptionId = subscriptionId2;
        }

        @Override // android.telephony.mbms.MbmsDownloadSessionCallback
        public void onError(int errorCode, String message) {
            try {
                if (errorCode == -1) {
                    throw new IllegalArgumentException("Middleware cannot send an unknown error.");
                }
                callback.onError(errorCode, message);
            } catch (RemoteException e) {
                MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
            }
        }

        @Override // android.telephony.mbms.MbmsDownloadSessionCallback
        public void onFileServicesUpdated(List<FileServiceInfo> services) {
            try {
                callback.onFileServicesUpdated(services);
            } catch (RemoteException e) {
                MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
            }
        }

        @Override // android.telephony.mbms.MbmsDownloadSessionCallback
        public void onMiddlewareReady() {
            try {
                callback.onMiddlewareReady();
            } catch (RemoteException e) {
                MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
            }
        }
    }

    /* renamed from: android.telephony.mbms.vendor.MbmsDownloadServiceBase$2 */
    /* loaded from: classes3.dex */
    class AnonymousClass2 implements IBinder.DeathRecipient {
        final /* synthetic */ int val$subscriptionId;
        final /* synthetic */ int val$uid;

        AnonymousClass2(int uid2, int subscriptionId2) {
            uid = uid2;
            subscriptionId = subscriptionId2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MbmsDownloadServiceBase.this.onAppCallbackDied(uid, subscriptionId);
        }
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int requestUpdateFileServices(int subscriptionId, List<String> serviceClasses) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int setTempFileRootDirectory(int subscriptionId, String rootDirectoryPath) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int addServiceAnnouncement(int subscriptionId, byte[] contents) {
        throw new UnsupportedOperationException("addServiceAnnouncement not supported by this middleware.");
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int download(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    public int addStatusListener(DownloadRequest downloadRequest, DownloadStatusListener listener) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
        int uid = Binder.getCallingUid();
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (listener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        DownloadStatusListener exposedCallback = new VendorDownloadStatusListener(listener) { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.3
            final /* synthetic */ DownloadRequest val$downloadRequest;
            final /* synthetic */ int val$uid;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(IDownloadStatusListener listener2, int uid2, DownloadRequest downloadRequest2) {
                super(listener2);
                uid = uid2;
                downloadRequest = downloadRequest2;
            }

            @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadStatusListener
            protected void onRemoteException(RemoteException e) {
                MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
            }
        };
        int result = addStatusListener(downloadRequest2, exposedCallback);
        if (result == 0) {
            IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.4
                final /* synthetic */ DownloadRequest val$downloadRequest;
                final /* synthetic */ IDownloadStatusListener val$listener;
                final /* synthetic */ int val$uid;

                AnonymousClass4(int uid2, DownloadRequest downloadRequest2, IDownloadStatusListener listener2) {
                    uid = uid2;
                    downloadRequest = downloadRequest2;
                    listener = listener2;
                }

                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
                    MbmsDownloadServiceBase.this.mDownloadStatusListenerBinderMap.remove(listener.asBinder());
                    MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(listener.asBinder());
                }
            };
            this.mDownloadCallbackDeathRecipients.put(listener2.asBinder(), deathRecipient);
            listener2.asBinder().linkToDeath(deathRecipient, 0);
            this.mDownloadStatusListenerBinderMap.put(listener2.asBinder(), exposedCallback);
        }
        return result;
    }

    /* renamed from: android.telephony.mbms.vendor.MbmsDownloadServiceBase$3 */
    /* loaded from: classes3.dex */
    class AnonymousClass3 extends VendorDownloadStatusListener {
        final /* synthetic */ DownloadRequest val$downloadRequest;
        final /* synthetic */ int val$uid;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(IDownloadStatusListener listener2, int uid2, DownloadRequest downloadRequest2) {
            super(listener2);
            uid = uid2;
            downloadRequest = downloadRequest2;
        }

        @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadStatusListener
        protected void onRemoteException(RemoteException e) {
            MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
        }
    }

    /* renamed from: android.telephony.mbms.vendor.MbmsDownloadServiceBase$4 */
    /* loaded from: classes3.dex */
    class AnonymousClass4 implements IBinder.DeathRecipient {
        final /* synthetic */ DownloadRequest val$downloadRequest;
        final /* synthetic */ IDownloadStatusListener val$listener;
        final /* synthetic */ int val$uid;

        AnonymousClass4(int uid2, DownloadRequest downloadRequest2, IDownloadStatusListener listener2) {
            uid = uid2;
            downloadRequest = downloadRequest2;
            listener = listener2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
            MbmsDownloadServiceBase.this.mDownloadStatusListenerBinderMap.remove(listener.asBinder());
            MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(listener.asBinder());
        }
    }

    public int removeStatusListener(DownloadRequest downloadRequest, DownloadStatusListener listener) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener listener) throws RemoteException {
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (listener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        IBinder.DeathRecipient deathRecipient = this.mDownloadCallbackDeathRecipients.remove(listener.asBinder());
        if (deathRecipient == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        listener.asBinder().unlinkToDeath(deathRecipient, 0);
        DownloadStatusListener exposedCallback = this.mDownloadStatusListenerBinderMap.remove(listener.asBinder());
        if (exposedCallback == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        return removeStatusListener(downloadRequest, exposedCallback);
    }

    public int addProgressListener(DownloadRequest downloadRequest, DownloadProgressListener listener) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
        int uid = Binder.getCallingUid();
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (listener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        DownloadProgressListener exposedCallback = new VendorDownloadProgressListener(listener) { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.5
            final /* synthetic */ DownloadRequest val$downloadRequest;
            final /* synthetic */ int val$uid;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass5(IDownloadProgressListener listener2, int uid2, DownloadRequest downloadRequest2) {
                super(listener2);
                uid = uid2;
                downloadRequest = downloadRequest2;
            }

            @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadProgressListener
            protected void onRemoteException(RemoteException e) {
                MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
            }
        };
        int result = addProgressListener(downloadRequest2, exposedCallback);
        if (result == 0) {
            IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.6
                final /* synthetic */ DownloadRequest val$downloadRequest;
                final /* synthetic */ IDownloadProgressListener val$listener;
                final /* synthetic */ int val$uid;

                AnonymousClass6(int uid2, DownloadRequest downloadRequest2, IDownloadProgressListener listener2) {
                    uid = uid2;
                    downloadRequest = downloadRequest2;
                    listener = listener2;
                }

                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
                    MbmsDownloadServiceBase.this.mDownloadProgressListenerBinderMap.remove(listener.asBinder());
                    MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(listener.asBinder());
                }
            };
            this.mDownloadCallbackDeathRecipients.put(listener2.asBinder(), deathRecipient);
            listener2.asBinder().linkToDeath(deathRecipient, 0);
            this.mDownloadProgressListenerBinderMap.put(listener2.asBinder(), exposedCallback);
        }
        return result;
    }

    /* renamed from: android.telephony.mbms.vendor.MbmsDownloadServiceBase$5 */
    /* loaded from: classes3.dex */
    class AnonymousClass5 extends VendorDownloadProgressListener {
        final /* synthetic */ DownloadRequest val$downloadRequest;
        final /* synthetic */ int val$uid;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(IDownloadProgressListener listener2, int uid2, DownloadRequest downloadRequest2) {
            super(listener2);
            uid = uid2;
            downloadRequest = downloadRequest2;
        }

        @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadProgressListener
        protected void onRemoteException(RemoteException e) {
            MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
        }
    }

    /* renamed from: android.telephony.mbms.vendor.MbmsDownloadServiceBase$6 */
    /* loaded from: classes3.dex */
    class AnonymousClass6 implements IBinder.DeathRecipient {
        final /* synthetic */ DownloadRequest val$downloadRequest;
        final /* synthetic */ IDownloadProgressListener val$listener;
        final /* synthetic */ int val$uid;

        AnonymousClass6(int uid2, DownloadRequest downloadRequest2, IDownloadProgressListener listener2) {
            uid = uid2;
            downloadRequest = downloadRequest2;
            listener = listener2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MbmsDownloadServiceBase.this.onAppCallbackDied(uid, downloadRequest.getSubscriptionId());
            MbmsDownloadServiceBase.this.mDownloadProgressListenerBinderMap.remove(listener.asBinder());
            MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(listener.asBinder());
        }
    }

    public int removeProgressListener(DownloadRequest downloadRequest, DownloadProgressListener listener) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener listener) throws RemoteException {
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (listener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        IBinder.DeathRecipient deathRecipient = this.mDownloadCallbackDeathRecipients.remove(listener.asBinder());
        if (deathRecipient == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        listener.asBinder().unlinkToDeath(deathRecipient, 0);
        DownloadProgressListener exposedCallback = this.mDownloadProgressListenerBinderMap.remove(listener.asBinder());
        if (exposedCallback == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        return removeProgressListener(downloadRequest, exposedCallback);
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public List<DownloadRequest> listPendingDownloads(int subscriptionId) throws RemoteException {
        return null;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public void dispose(int subscriptionId) throws RemoteException {
    }

    public void onAppCallbackDied(int uid, int subscriptionId) {
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService.Stub, android.os.IInterface
    @SystemApi
    public IBinder asBinder() {
        return super.asBinder();
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService.Stub, android.os.Binder
    @SystemApi
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return super.onTransact(code, data, reply, flags);
    }
}
