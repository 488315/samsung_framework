package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.IBinder;
import android.os.IIncidentAuthListener;
import android.os.IIncidentCompanion;
import android.os.IIncidentDumpCallback;
import android.os.IIncidentManager;
import android.os.IncidentManager;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Slog;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class IncidentManager {
    public static final int FLAG_ALLOW_CONSENTLESS_BUGREPORT = 2;
    public static final int FLAG_CONFIRMATION_DIALOG = 1;
    public static final int PRIVACY_POLICY_AUTO = 200;
    public static final int PRIVACY_POLICY_EXPLICIT = 100;
    public static final int PRIVACY_POLICY_LOCAL = 0;
    private static final String TAG = "IncidentManager";
    public static final String URI_AUTHORITY = "android.os.IncidentManager";
    public static final String URI_PARAM_CALLING_PACKAGE = "pkg";
    public static final String URI_PARAM_FLAGS = "flags";
    public static final String URI_PARAM_ID = "id";
    public static final String URI_PARAM_RECEIVER_CLASS = "receiver";
    public static final String URI_PARAM_REPORT_ID = "r";
    public static final String URI_PARAM_TIMESTAMP = "t";
    public static final String URI_PATH = "/pending";
    public static final String URI_SCHEME = "content";
    private IIncidentCompanion mCompanionService;
    private final Context mContext;
    private IIncidentManager mIncidentService;
    private Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface PendingReportFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrivacyPolicy {
    }

    @SystemApi
    public static class PendingReport {
        private final int mFlags;
        private final String mRequestingPackage;
        private final long mTimestamp;
        private final Uri mUri;

        public PendingReport(Uri uri) {
            try {
                int flags = Integer.parseInt(uri.getQueryParameter("flags"));
                this.mFlags = flags;
                String requestingPackage = uri.getQueryParameter("pkg");
                if (requestingPackage == null) {
                    throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
                }
                this.mRequestingPackage = requestingPackage;
                try {
                    long timestamp = Long.parseLong(uri.getQueryParameter("t"));
                    this.mTimestamp = timestamp;
                    this.mUri = uri;
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid URI: No t parameter. " + uri);
                }
            } catch (NumberFormatException e2) {
                throw new RuntimeException("Invalid URI: No flags parameter. " + uri);
            }
        }

        public String getRequestingPackage() {
            return this.mRequestingPackage;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public String toString() {
            return "PendingReport(" + getUri().toString() + NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PendingReport)) {
                return false;
            }
            PendingReport that = (PendingReport) obj;
            return this.mUri.equals(that.mUri) && this.mFlags == that.mFlags && this.mRequestingPackage.equals(that.mRequestingPackage) && this.mTimestamp == that.mTimestamp;
        }
    }

    @SystemApi
    public static class IncidentReport implements Parcelable, Closeable {
        public static final Parcelable.Creator<IncidentReport> CREATOR = new Parcelable.Creator() { // from class: android.os.IncidentManager.IncidentReport.1
            @Override // android.os.Parcelable.Creator
            public IncidentReport[] newArray(int size) {
                return new IncidentReport[size];
            }

            @Override // android.os.Parcelable.Creator
            public IncidentReport createFromParcel(Parcel in) {
                return new IncidentReport(in);
            }
        };
        private ParcelFileDescriptor mFileDescriptor;
        private final int mPrivacyPolicy;
        private final long mTimestampNs;

        public IncidentReport(Parcel in) {
            this.mTimestampNs = in.readLong();
            this.mPrivacyPolicy = in.readInt();
            if (in.readInt() != 0) {
                this.mFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(in);
            } else {
                this.mFileDescriptor = null;
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                if (this.mFileDescriptor != null) {
                    this.mFileDescriptor.close();
                    this.mFileDescriptor = null;
                }
            } catch (IOException e) {
            }
        }

        public long getTimestamp() {
            return this.mTimestampNs / 1000000;
        }

        public long getPrivacyPolicy() {
            return this.mPrivacyPolicy;
        }

        public InputStream getInputStream() throws IOException {
            if (this.mFileDescriptor == null) {
                return null;
            }
            return new ParcelFileDescriptor.AutoCloseInputStream(this.mFileDescriptor);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return this.mFileDescriptor != null ? 1 : 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(this.mTimestampNs);
            out.writeInt(this.mPrivacyPolicy);
            if (this.mFileDescriptor != null) {
                out.writeInt(1);
                this.mFileDescriptor.writeToParcel(out, flags);
            } else {
                out.writeInt(0);
            }
        }
    }

    public static class AuthListener {
        IIncidentAuthListener.Stub mBinder = new AnonymousClass1();
        Executor mExecutor;

        /* renamed from: android.os.IncidentManager$AuthListener$1, reason: invalid class name */
        class AnonymousClass1 extends IIncidentAuthListener.Stub {
            AnonymousClass1() {
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportApproved() {
                if (AuthListener.this.mExecutor != null) {
                    AuthListener.this.mExecutor.execute(new Runnable() { // from class: android.os.IncidentManager$AuthListener$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            IncidentManager.AuthListener.AnonymousClass1.this.lambda$onReportApproved$0();
                        }
                    });
                } else {
                    AuthListener.this.onReportApproved();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReportApproved$0() {
                AuthListener.this.onReportApproved();
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportDenied() {
                if (AuthListener.this.mExecutor != null) {
                    AuthListener.this.mExecutor.execute(new Runnable() { // from class: android.os.IncidentManager$AuthListener$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            IncidentManager.AuthListener.AnonymousClass1.this.lambda$onReportDenied$1();
                        }
                    });
                } else {
                    AuthListener.this.onReportDenied();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReportDenied$1() {
                AuthListener.this.onReportDenied();
            }
        }

        public void onReportApproved() {
        }

        public void onReportDenied() {
        }
    }

    public static class DumpCallback {
        IIncidentDumpCallback.Stub mBinder = new AnonymousClass1();
        private Executor mExecutor;
        private int mId;

        /* renamed from: android.os.IncidentManager$DumpCallback$1, reason: invalid class name */
        class AnonymousClass1 extends IIncidentDumpCallback.Stub {
            AnonymousClass1() {
            }

            @Override // android.os.IIncidentDumpCallback
            public void onDumpSection(final ParcelFileDescriptor pfd) {
                if (DumpCallback.this.mExecutor != null) {
                    DumpCallback.this.mExecutor.execute(new Runnable() { // from class: android.os.IncidentManager$DumpCallback$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            IncidentManager.DumpCallback.AnonymousClass1.this.lambda$onDumpSection$0(pfd);
                        }
                    });
                } else {
                    DumpCallback.this.onDumpSection(DumpCallback.this.mId, new ParcelFileDescriptor.AutoCloseOutputStream(pfd));
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDumpSection$0(ParcelFileDescriptor pfd) {
                DumpCallback.this.onDumpSection(DumpCallback.this.mId, new ParcelFileDescriptor.AutoCloseOutputStream(pfd));
            }
        }

        public void onDumpSection(int id, OutputStream out) {
        }
    }

    public IncidentManager(Context context) {
        this.mContext = context;
    }

    public void reportIncident(IncidentReportArgs args) {
        reportIncidentInternal(args);
    }

    public void requestAuthorization(int callingUid, String callingPackage, int flags, AuthListener listener) {
        requestAuthorization(callingUid, callingPackage, flags, this.mContext.getMainExecutor(), listener);
    }

    public void requestAuthorization(int callingUid, String callingPackage, int flags, Executor executor, AuthListener listener) {
        try {
            if (listener.mExecutor != null) {
                throw new RuntimeException("Do not reuse AuthListener objects when calling requestAuthorization");
            }
            listener.mExecutor = executor;
            getCompanionServiceLocked().authorizeReport(callingUid, callingPackage, null, null, flags, listener.mBinder);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void cancelAuthorization(AuthListener listener) {
        try {
            getCompanionServiceLocked().cancelAuthorization(listener.mBinder);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<PendingReport> getPendingReports() {
        try {
            List<String> strings = getCompanionServiceLocked().getPendingReports();
            int size = strings.size();
            ArrayList<PendingReport> result = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                result.add(new PendingReport(Uri.parse(strings.get(i))));
            }
            return result;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void approveReport(Uri uri) {
        try {
            getCompanionServiceLocked().approveReport(uri.toString());
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void denyReport(Uri uri) {
        try {
            getCompanionServiceLocked().denyReport(uri.toString());
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerSection(int id, String name, Executor executor, DumpCallback callback) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(callback, "callback cannot be null");
        try {
            if (callback.mExecutor != null) {
                throw new RuntimeException("Do not reuse DumpCallback objects when calling registerSection");
            }
            callback.mExecutor = executor;
            callback.mId = id;
            IIncidentManager service = getIIncidentManagerLocked();
            if (service == null) {
                Slog.e(TAG, "registerSection can't find incident binder service");
            } else {
                service.registerSection(id, name, callback.mBinder);
            }
        } catch (RemoteException ex) {
            Slog.e(TAG, "registerSection failed", ex);
        }
    }

    public void unregisterSection(int id) {
        try {
            IIncidentManager service = getIIncidentManagerLocked();
            if (service == null) {
                Slog.e(TAG, "unregisterSection can't find incident binder service");
            } else {
                service.unregisterSection(id);
            }
        } catch (RemoteException ex) {
            Slog.e(TAG, "unregisterSection failed", ex);
        }
    }

    public List<Uri> getIncidentReportList(String receiverClass) {
        try {
            List<String> strings = getCompanionServiceLocked().getIncidentReportList(this.mContext.getPackageName(), receiverClass);
            int size = strings.size();
            ArrayList<Uri> result = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                result.add(Uri.parse(strings.get(i)));
            }
            return result;
        } catch (RemoteException ex) {
            throw new RuntimeException("System server or incidentd going down", ex);
        }
    }

    public IncidentReport getIncidentReport(Uri uri) {
        String id = uri.getQueryParameter("r");
        if (id == null) {
            return null;
        }
        String pkg = uri.getQueryParameter("pkg");
        if (pkg == null) {
            throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
        }
        String cls = uri.getQueryParameter("receiver");
        if (cls == null) {
            throw new RuntimeException("Invalid URI: No receiver parameter. " + uri);
        }
        try {
            return getCompanionServiceLocked().getIncidentReport(pkg, cls, id);
        } catch (RemoteException ex) {
            throw new RuntimeException("System server or incidentd going down", ex);
        }
    }

    public void deleteIncidentReports(Uri uri) {
        if (uri == null) {
            try {
                getCompanionServiceLocked().deleteAllIncidentReports(this.mContext.getPackageName());
                return;
            } catch (RemoteException ex) {
                throw new RuntimeException("System server or incidentd going down", ex);
            }
        }
        String pkg = uri.getQueryParameter("pkg");
        if (pkg == null) {
            throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
        }
        String cls = uri.getQueryParameter("receiver");
        if (cls == null) {
            throw new RuntimeException("Invalid URI: No receiver parameter. " + uri);
        }
        String id = uri.getQueryParameter("r");
        if (id == null) {
            throw new RuntimeException("Invalid URI: No r parameter. " + uri);
        }
        try {
            getCompanionServiceLocked().deleteIncidentReports(pkg, cls, id);
        } catch (RemoteException ex2) {
            throw new RuntimeException("System server or incidentd going down", ex2);
        }
    }

    private void reportIncidentInternal(IncidentReportArgs args) {
        try {
            IIncidentManager service = getIIncidentManagerLocked();
            if (service == null) {
                Slog.e(TAG, "reportIncident can't find incident binder service");
            } else {
                service.reportIncident(args);
            }
        } catch (RemoteException ex) {
            Slog.e(TAG, "reportIncident failed", ex);
        }
    }

    private IIncidentManager getIIncidentManagerLocked() throws RemoteException {
        if (this.mIncidentService != null) {
            return this.mIncidentService;
        }
        synchronized (this.mLock) {
            if (this.mIncidentService != null) {
                return this.mIncidentService;
            }
            this.mIncidentService = IIncidentManager.Stub.asInterface(ServiceManager.getService(Context.INCIDENT_SERVICE));
            if (this.mIncidentService != null) {
                this.mIncidentService.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.os.IncidentManager$$ExternalSyntheticLambda1
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        IncidentManager.this.lambda$getIIncidentManagerLocked$0();
                    }
                }, 0);
            }
            return this.mIncidentService;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getIIncidentManagerLocked$0() {
        synchronized (this.mLock) {
            this.mIncidentService = null;
        }
    }

    private IIncidentCompanion getCompanionServiceLocked() throws RemoteException {
        if (this.mCompanionService != null) {
            return this.mCompanionService;
        }
        synchronized (this) {
            if (this.mCompanionService != null) {
                return this.mCompanionService;
            }
            this.mCompanionService = IIncidentCompanion.Stub.asInterface(ServiceManager.getService(Context.INCIDENT_COMPANION_SERVICE));
            if (this.mCompanionService != null) {
                this.mCompanionService.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.os.IncidentManager$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        IncidentManager.this.lambda$getCompanionServiceLocked$1();
                    }
                }, 0);
            }
            return this.mCompanionService;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCompanionServiceLocked$1() {
        synchronized (this.mLock) {
            this.mCompanionService = null;
        }
    }
}
