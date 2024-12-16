package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsRegistrationAttributes;
import android.telephony.ims.SipDetails;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import android.util.Log;
import com.android.internal.telephony.util.RemoteCallbackListExt;
import com.android.internal.telephony.util.TelephonyUtils;
import com.android.internal.util.ArrayUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class ImsRegistrationImplBase {
    private static final String LOG_TAG = "ImsRegistrationImplBase";
    public static final int REASON_ALLOWED_NETWORK_TYPES_CHANGED = 3;
    public static final int REASON_HANDOVER_FAILED = 6;
    public static final int REASON_NON_IMS_CAPABLE_NETWORK = 4;
    public static final int REASON_RADIO_POWER_OFF = 5;
    public static final int REASON_SIM_REFRESH = 2;
    public static final int REASON_SIM_REMOVED = 1;
    public static final int REASON_UNKNOWN = 0;
    public static final int REASON_VOPS_NOT_SUPPORTED = 7;
    private static final int REGISTRATION_STATE_UNKNOWN = -1;
    public static final int REGISTRATION_TECH_3G = 4;
    public static final int REGISTRATION_TECH_CROSS_SIM = 2;
    public static final int REGISTRATION_TECH_IWLAN = 1;
    public static final int REGISTRATION_TECH_LTE = 0;
    public static final int REGISTRATION_TECH_MAX = 5;
    public static final int REGISTRATION_TECH_NONE = -1;
    public static final int REGISTRATION_TECH_NR = 3;
    private final IImsRegistration mBinder;
    private final RemoteCallbackListExt<IImsRegistrationCallback> mCallbacks;
    private final RemoteCallbackListExt<IImsRegistrationCallback> mEmergencyCallbacks;
    private ImsReasonInfo mEmergencyLastDisconnectCause;
    private int mEmergencyLastDisconnectRadioTech;
    private int mEmergencyLastDisconnectSuggestedAction;
    private ImsRegistrationAttributes mEmergencyRegistrationAttributes;
    private int mEmergencyRegistrationState;
    private Executor mExecutor;
    private ImsReasonInfo mLastDisconnectCause;
    private int mLastDisconnectRadioTech;
    private int mLastDisconnectSuggestedAction;
    private final Object mLock;
    private ImsRegistrationAttributes mRegistrationAttributes;
    private int mRegistrationState;
    private Uri[] mUris;
    private boolean mUrisSet;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsDeregistrationReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsRegistrationTech {
    }

    @SystemApi
    public ImsRegistrationImplBase() {
        this.mBinder = new AnonymousClass1();
        this.mCallbacks = new RemoteCallbackListExt<>();
        this.mEmergencyCallbacks = new RemoteCallbackListExt<>();
        this.mLock = new Object();
        this.mRegistrationState = -1;
        this.mEmergencyRegistrationState = -1;
        this.mLastDisconnectCause = new ImsReasonInfo();
        this.mEmergencyLastDisconnectCause = new ImsReasonInfo();
        this.mLastDisconnectSuggestedAction = 0;
        this.mEmergencyLastDisconnectSuggestedAction = 0;
        this.mLastDisconnectRadioTech = -1;
        this.mEmergencyLastDisconnectRadioTech = -1;
        this.mUris = new Uri[0];
        this.mUrisSet = false;
    }

    @SystemApi
    public ImsRegistrationImplBase(Executor executor) {
        this.mBinder = new AnonymousClass1();
        this.mCallbacks = new RemoteCallbackListExt<>();
        this.mEmergencyCallbacks = new RemoteCallbackListExt<>();
        this.mLock = new Object();
        this.mRegistrationState = -1;
        this.mEmergencyRegistrationState = -1;
        this.mLastDisconnectCause = new ImsReasonInfo();
        this.mEmergencyLastDisconnectCause = new ImsReasonInfo();
        this.mLastDisconnectSuggestedAction = 0;
        this.mEmergencyLastDisconnectSuggestedAction = 0;
        this.mLastDisconnectRadioTech = -1;
        this.mEmergencyLastDisconnectRadioTech = -1;
        this.mUris = new Uri[0];
        this.mUrisSet = false;
        this.mExecutor = executor;
    }

    /* renamed from: android.telephony.ims.stub.ImsRegistrationImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends IImsRegistration.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$getRegistrationTechnology$0() {
            return Integer.valueOf(ImsRegistrationImplBase.this.mRegistrationAttributes == null ? -1 : ImsRegistrationImplBase.this.mRegistrationAttributes.getRegistrationTechnology());
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public int getRegistrationTechnology() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$getRegistrationTechnology$0;
                    lambda$getRegistrationTechnology$0 = ImsRegistrationImplBase.AnonymousClass1.this.lambda$getRegistrationTechnology$0();
                    return lambda$getRegistrationTechnology$0;
                }
            }, "getRegistrationTechnology")).intValue();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addRegistrationCallback(final IImsRegistrationCallback c) throws RemoteException {
            final AtomicReference<RemoteException> exceptionRef = new AtomicReference<>();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$addRegistrationCallback$1(c, exceptionRef);
                }
            }, "addRegistrationCallback");
            if (exceptionRef.get() != null) {
                throw exceptionRef.get();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addRegistrationCallback$1(IImsRegistrationCallback c, AtomicReference exceptionRef) {
            try {
                ImsRegistrationImplBase.this.addRegistrationCallback(c);
            } catch (RemoteException e) {
                exceptionRef.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addEmergencyRegistrationCallback(final IImsRegistrationCallback c) throws RemoteException {
            final AtomicReference<RemoteException> exceptionRef = new AtomicReference<>();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$addEmergencyRegistrationCallback$2(c, exceptionRef);
                }
            }, "addEmergencyRegistrationCallback");
            if (exceptionRef.get() != null) {
                throw exceptionRef.get();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addEmergencyRegistrationCallback$2(IImsRegistrationCallback c, AtomicReference exceptionRef) {
            try {
                ImsRegistrationImplBase.this.addEmergencyRegistrationCallback(c);
            } catch (RemoteException e) {
                exceptionRef.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeEmergencyRegistrationCallback(final IImsRegistrationCallback c) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$removeEmergencyRegistrationCallback$3(c);
                }
            }, "removeEmergencyRegistrationCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeEmergencyRegistrationCallback$3(IImsRegistrationCallback c) {
            ImsRegistrationImplBase.this.removeEmergencyRegistrationCallback(c);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeRegistrationCallback$4(IImsRegistrationCallback c) {
            ImsRegistrationImplBase.this.removeRegistrationCallback(c);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeRegistrationCallback(final IImsRegistrationCallback c) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$removeRegistrationCallback$4(c);
                }
            }, "removeRegistrationCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerFullNetworkRegistration$5(int sipCode, String sipReason) {
            ImsRegistrationImplBase.this.triggerFullNetworkRegistration(sipCode, sipReason);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerFullNetworkRegistration(final int sipCode, final String sipReason) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerFullNetworkRegistration$5(sipCode, sipReason);
                }
            }, "triggerFullNetworkRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerUpdateSipDelegateRegistration$6() {
            ImsRegistrationImplBase.this.updateSipDelegateRegistration();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerUpdateSipDelegateRegistration() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerUpdateSipDelegateRegistration$6();
                }
            }, "triggerUpdateSipDelegateRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerSipDelegateDeregistration$7() {
            ImsRegistrationImplBase.this.triggerSipDelegateDeregistration();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerSipDelegateDeregistration() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerSipDelegateDeregistration$7();
                }
            }, "triggerSipDelegateDeregistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerDeregistration$8(int reason) {
            ImsRegistrationImplBase.this.triggerDeregistration(reason);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerDeregistration(final int reason) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerDeregistration$8(reason);
                }
            }, "triggerDeregistration");
        }

        private void executeMethodAsync(final Runnable r, String errorLogName) throws RemoteException {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(r);
                    }
                }, ImsRegistrationImplBase.this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + errorLogName + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }

        private void executeMethodAsyncNoException(final Runnable r, String errorLogName) {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(r);
                    }
                }, ImsRegistrationImplBase.this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + errorLogName + " exception: " + e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final Supplier<T> r, String errorLogName) throws RemoteException {
            CompletableFuture<T> future = CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    Object runWithCleanCallingIdentity;
                    runWithCleanCallingIdentity = TelephonyUtils.runWithCleanCallingIdentity((Supplier<Object>) r);
                    return runWithCleanCallingIdentity;
                }
            }, ImsRegistrationImplBase.this.mExecutor);
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + errorLogName + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }
    }

    public final IImsRegistration getBinder() {
        return this.mBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRegistrationCallback(IImsRegistrationCallback c) throws RemoteException {
        this.mCallbacks.register(c);
        updateNewCallbackWithState(c, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRegistrationCallback(IImsRegistrationCallback c) {
        this.mCallbacks.unregister(c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEmergencyRegistrationCallback(IImsRegistrationCallback c) throws RemoteException {
        this.mEmergencyCallbacks.register(c);
        updateNewCallbackWithState(c, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeEmergencyRegistrationCallback(IImsRegistrationCallback c) {
        this.mEmergencyCallbacks.unregister(c);
    }

    @SystemApi
    public void updateSipDelegateRegistration() {
    }

    @SystemApi
    public void triggerSipDelegateDeregistration() {
    }

    @SystemApi
    public void triggerFullNetworkRegistration(int sipCode, String sipReason) {
    }

    public void triggerDeregistration(int reason) {
    }

    @SystemApi
    public final void onRegistered(int imsRadioTech) {
        onRegistered(new ImsRegistrationAttributes.Builder(imsRadioTech).build());
    }

    @SystemApi
    public final void onRegistered(final ImsRegistrationAttributes attributes) {
        boolean isEmergency = isEmergency(attributes);
        if (isEmergency) {
            updateToEmergencyState(attributes, 2);
        } else {
            updateToState(attributes, 2);
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onRegistered$0(ImsRegistrationAttributes.this, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onRegistered$0(ImsRegistrationAttributes attributes, IImsRegistrationCallback c) {
        try {
            c.onRegistered(attributes);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onRegistered(int, Set) - Skipping callback.");
        }
    }

    @SystemApi
    public final void onRegistering(int imsRadioTech) {
        onRegistering(new ImsRegistrationAttributes.Builder(imsRadioTech).build());
    }

    @SystemApi
    public final void onRegistering(final ImsRegistrationAttributes attributes) {
        boolean isEmergency = isEmergency(attributes);
        if (isEmergency) {
            updateToEmergencyState(attributes, 1);
        } else {
            updateToState(attributes, 1);
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onRegistering$1(ImsRegistrationAttributes.this, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onRegistering$1(ImsRegistrationAttributes attributes, IImsRegistrationCallback c) {
        try {
            c.onRegistering(attributes);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onRegistering(int, Set) - Skipping callback.");
        }
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo info) {
        onDeregistered(info, 0, -1);
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo info, int suggestedAction, int imsRadioTech) {
        ImsRegistrationAttributes attributes;
        if (this.mRegistrationAttributes != null) {
            attributes = new ImsRegistrationAttributes(imsRadioTech, this.mRegistrationAttributes.getTransportType(), this.mRegistrationAttributes.getAttributeFlags(), this.mRegistrationAttributes.getFeatureTags());
        } else {
            attributes = new ImsRegistrationAttributes.Builder(imsRadioTech).build();
        }
        onDeregistered(info, suggestedAction, attributes);
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo info, final int suggestedAction, ImsRegistrationAttributes attributes) {
        boolean isEmergency = isEmergency(attributes);
        final int imsRadioTech = attributes.getRegistrationTechnology();
        if (isEmergency) {
            updateToDisconnectedEmergencyState(info, suggestedAction, imsRadioTech);
        } else {
            updateToDisconnectedState(info, suggestedAction, imsRadioTech);
        }
        final ImsReasonInfo reasonInfo = info != null ? info : new ImsReasonInfo();
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onDeregistered$2(ImsReasonInfo.this, suggestedAction, imsRadioTech, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onDeregistered$2(ImsReasonInfo reasonInfo, int suggestedAction, int imsRadioTech, IImsRegistrationCallback c) {
        try {
            c.onDeregistered(reasonInfo, suggestedAction, imsRadioTech);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onDeregistered() - Skipping callback.");
        }
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo info, SipDetails details) {
        onDeregistered(info, 0, -1, details);
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo info, final int suggestedAction, final int imsRadioTech, final SipDetails details) {
        updateToDisconnectedState(info, suggestedAction, imsRadioTech);
        final ImsReasonInfo reasonInfo = info != null ? info : new ImsReasonInfo();
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onDeregistered$3(ImsReasonInfo.this, suggestedAction, imsRadioTech, details, (IImsRegistrationCallback) obj);
            }
        }, false);
    }

    static /* synthetic */ void lambda$onDeregistered$3(ImsReasonInfo reasonInfo, int suggestedAction, int imsRadioTech, SipDetails details, IImsRegistrationCallback c) {
        try {
            c.onDeregisteredWithDetails(reasonInfo, suggestedAction, imsRadioTech, details);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onDeregistered() - Skipping callback.");
        }
    }

    @SystemApi
    public final void onTechnologyChangeFailed(int imsRadioTech, ImsReasonInfo info) {
        ImsRegistrationAttributes attributes;
        if (this.mRegistrationAttributes != null) {
            attributes = new ImsRegistrationAttributes(imsRadioTech, this.mRegistrationAttributes.getTransportType(), this.mRegistrationAttributes.getAttributeFlags(), this.mRegistrationAttributes.getFeatureTags());
        } else {
            attributes = new ImsRegistrationAttributes.Builder(imsRadioTech).build();
        }
        onTechnologyChangeFailed(info, attributes);
    }

    @SystemApi
    public final void onTechnologyChangeFailed(ImsReasonInfo info, ImsRegistrationAttributes attributes) {
        boolean isEmergency = isEmergency(attributes);
        final int imsRadioTech = attributes.getRegistrationTechnology();
        final ImsReasonInfo reasonInfo = info != null ? info : new ImsReasonInfo();
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onTechnologyChangeFailed$4(imsRadioTech, reasonInfo, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onTechnologyChangeFailed$4(int imsRadioTech, ImsReasonInfo reasonInfo, IImsRegistrationCallback c) {
        try {
            c.onTechnologyChangeFailed(imsRadioTech, reasonInfo);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onTechnologyChangeFailed() - Skipping callback.");
        }
    }

    @SystemApi
    public final void onSubscriberAssociatedUriChanged(final Uri[] uris) {
        synchronized (this.mLock) {
            this.mUris = (Uri[]) ArrayUtils.cloneOrNull(uris);
            this.mUrisSet = true;
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.this.lambda$onSubscriberAssociatedUriChanged$5(uris, (IImsRegistrationCallback) obj);
            }
        }, false);
    }

    private boolean isEmergency(ImsRegistrationAttributes attributes) {
        return (attributes == null || (attributes.getAttributeFlags() & 2) == 0) ? false : true;
    }

    private void broadcastToCallbacksLocked(Consumer<IImsRegistrationCallback> c, boolean isEmergency) {
        if (isEmergency) {
            synchronized (this.mEmergencyCallbacks) {
                this.mEmergencyCallbacks.broadcastAction(c);
            }
        } else {
            synchronized (this.mCallbacks) {
                this.mCallbacks.broadcastAction(c);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSubscriberAssociatedUriChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$onSubscriberAssociatedUriChanged$5(IImsRegistrationCallback callback, Uri[] uris) {
        try {
            callback.onSubscriberAssociatedUriChanged(uris);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onSubscriberAssociatedUriChanged() - Skipping callback.");
        }
    }

    private void updateToState(ImsRegistrationAttributes attributes, int newState) {
        synchronized (this.mLock) {
            this.mRegistrationAttributes = attributes;
            this.mRegistrationState = newState;
            this.mLastDisconnectCause = null;
            this.mLastDisconnectSuggestedAction = 0;
            this.mLastDisconnectRadioTech = -1;
        }
    }

    private void updateToEmergencyState(ImsRegistrationAttributes attributes, int newState) {
        synchronized (this.mLock) {
            this.mEmergencyRegistrationAttributes = attributes;
            this.mEmergencyRegistrationState = newState;
            this.mEmergencyLastDisconnectCause = null;
            this.mEmergencyLastDisconnectSuggestedAction = 0;
            this.mEmergencyLastDisconnectRadioTech = -1;
        }
    }

    private void updateToDisconnectedState(ImsReasonInfo info, int suggestedAction, int imsRadioTech) {
        synchronized (this.mLock) {
            this.mUrisSet = false;
            this.mUris = null;
            updateToState(new ImsRegistrationAttributes.Builder(-1).build(), 0);
            if (info != null) {
                this.mLastDisconnectCause = info;
                this.mLastDisconnectSuggestedAction = suggestedAction;
                this.mLastDisconnectRadioTech = imsRadioTech;
            } else {
                Log.w(LOG_TAG, "updateToDisconnectedState: no ImsReasonInfo provided.");
                this.mLastDisconnectCause = new ImsReasonInfo();
            }
        }
    }

    private void updateToDisconnectedEmergencyState(ImsReasonInfo info, int suggestedAction, int imsRadioTech) {
        synchronized (this.mLock) {
            this.mUrisSet = false;
            this.mUris = null;
            updateToEmergencyState(new ImsRegistrationAttributes.Builder(-1).build(), 0);
            if (info != null) {
                this.mEmergencyLastDisconnectCause = info;
                this.mEmergencyLastDisconnectSuggestedAction = suggestedAction;
                this.mEmergencyLastDisconnectRadioTech = imsRadioTech;
            } else {
                Log.w(LOG_TAG, "updateToDisconnectedState: no ImsReasonInfo provided.");
                this.mEmergencyLastDisconnectCause = new ImsReasonInfo();
            }
        }
    }

    private void updateNewCallbackWithState(IImsRegistrationCallback c, boolean isEmergencyCallback) throws RemoteException {
        int state;
        ImsRegistrationAttributes attributes;
        ImsReasonInfo disconnectInfo;
        int suggestedAction;
        int imsDisconnectRadioTech;
        boolean urisSet;
        Uri[] uris;
        synchronized (this.mLock) {
            state = isEmergencyCallback ? this.mEmergencyRegistrationState : this.mRegistrationState;
            attributes = isEmergencyCallback ? this.mEmergencyRegistrationAttributes : this.mRegistrationAttributes;
            disconnectInfo = isEmergencyCallback ? this.mEmergencyLastDisconnectCause : this.mLastDisconnectCause;
            suggestedAction = isEmergencyCallback ? this.mEmergencyLastDisconnectSuggestedAction : this.mLastDisconnectSuggestedAction;
            imsDisconnectRadioTech = isEmergencyCallback ? this.mEmergencyLastDisconnectRadioTech : this.mLastDisconnectRadioTech;
            urisSet = this.mUrisSet;
            uris = this.mUris;
        }
        switch (state) {
            case 0:
                c.onDeregistered(disconnectInfo, suggestedAction, imsDisconnectRadioTech);
                break;
            case 1:
                c.onRegistering(attributes);
                break;
            case 2:
                c.onRegistered(attributes);
                break;
        }
        if (urisSet) {
            lambda$onSubscriberAssociatedUriChanged$5(c, uris);
        }
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }

    public final void clearRegistrationCache() {
        synchronized (this.mLock) {
            this.mUris = null;
            this.mUrisSet = false;
        }
    }
}
