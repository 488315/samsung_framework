package android.service.controls;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.controls.Control;
import android.service.controls.ControlsProviderService;
import android.service.controls.IControlsProvider;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.ControlActionWrapper;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public abstract class ControlsProviderService extends Service {
    public static final String ACTION_ADD_CONTROL = "android.service.controls.action.ADD_CONTROL";
    public static final String CALLBACK_BUNDLE = "CALLBACK_BUNDLE";
    public static final String CALLBACK_TOKEN = "CALLBACK_TOKEN";
    public static final int CONTROLS_SURFACE_ACTIVITY_PANEL = 0;
    public static final int CONTROLS_SURFACE_DREAM = 1;
    public static final String EXTRA_CONTROL = "android.service.controls.extra.CONTROL";
    public static final String EXTRA_CONTROLS = "android.service.controls.extra.CONTROLS";
    public static final String EXTRA_CONTROLS_SURFACE = "android.service.controls.extra.CONTROLS_SURFACE";
    public static final String EXTRA_CONTROL_AUTO_ADD = "android.service.controls.extra.CONTROL_AUTO_ADD";
    public static final String EXTRA_LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS = "android.service.controls.extra.LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS";
    public static final String META_DATA_PANEL_ACTIVITY = "android.service.controls.META_DATA_PANEL_ACTIVITY";
    public static final String SERVICE_CONTROLS = "android.service.controls.ControlsProviderService";
    public static final String TAG = "ControlsProviderService";
    private Supplier<ControlsProviderInfo> mControlsProviderInfoSupplier;
    private RequestHandler mHandler;
    private IBinder mToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ControlsSurface {
    }

    public abstract Flow.Publisher<Control> createPublisherFor(List<String> list);

    public abstract Flow.Publisher<Control> createPublisherForAllAvailable();

    public abstract void performControlAction(String str, ControlAction controlAction, Consumer<Integer> consumer);

    public Flow.Publisher<Control> createPublisherForSuggested() {
        return null;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        this.mHandler = new RequestHandler(Looper.getMainLooper());
        Bundle bundle = intent.getBundleExtra(CALLBACK_BUNDLE);
        this.mToken = bundle.getBinder(CALLBACK_TOKEN);
        return new IControlsProvider.Stub() { // from class: android.service.controls.ControlsProviderService.1
            @Override // android.service.controls.IControlsProvider
            public void load(IControlsSubscriber subscriber) {
                ControlsProviderService.this.mHandler.obtainMessage(1, subscriber).sendToTarget();
            }

            @Override // android.service.controls.IControlsProvider
            public void loadSuggested(IControlsSubscriber subscriber) {
                ControlsProviderService.this.mHandler.obtainMessage(4, subscriber).sendToTarget();
            }

            @Override // android.service.controls.IControlsProvider
            public void subscribe(List<String> controlIds, IControlsSubscriber subscriber) {
                SubscribeMessage msg = new SubscribeMessage(controlIds, subscriber);
                ControlsProviderService.this.mHandler.obtainMessage(2, msg).sendToTarget();
            }

            @Override // android.service.controls.IControlsProvider
            public void action(String controlId, ControlActionWrapper action, IControlsActionCallback cb) {
                ActionMessage msg = new ActionMessage(controlId, action.getWrappedAction(), cb);
                ControlsProviderService.this.mHandler.obtainMessage(3, msg).sendToTarget();
            }

            @Override // android.service.controls.IControlsProvider
            public void loadControlsProviderInfo(IControlsProviderInfoSubscriber cb) {
                ControlsProviderService.this.mHandler.obtainMessage(101, cb).sendToTarget();
            }
        };
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        this.mHandler = null;
        return true;
    }

    public void setControlsProviderInfoSupplier(Supplier<ControlsProviderInfo> supplier) {
        this.mControlsProviderInfoSupplier = supplier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class RequestHandler extends Handler {
        private static final int MSG_ACTION = 3;
        private static final int MSG_LOAD = 1;
        private static final int MSG_LOAD_CONTROLS_PROVIDER_INFO = 101;
        private static final int MSG_LOAD_SUGGESTED = 4;
        private static final int MSG_SUBSCRIBE = 2;

        RequestHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    IControlsSubscriber cs = (IControlsSubscriber) msg.obj;
                    SubscriberProxy proxy = new SubscriberProxy(true, ControlsProviderService.this.mToken, cs);
                    Log.d(ControlsProviderService.TAG, "createPublisherForAllAvailable mToken:" + ControlsProviderService.this.mToken);
                    ControlsProviderService.this.createPublisherForAllAvailable().subscribe(proxy);
                    break;
                case 2:
                    SubscribeMessage sMsg = (SubscribeMessage) msg.obj;
                    SubscriberProxy proxy2 = new SubscriberProxy(ControlsProviderService.this, false, ControlsProviderService.this.mToken, sMsg.mSubscriber);
                    Log.d(ControlsProviderService.TAG, "createPublisherFor mToken:" + ControlsProviderService.this.mToken + ", ControlIds:" + sMsg.mControlIds);
                    ControlsProviderService.this.createPublisherFor(sMsg.mControlIds).subscribe(proxy2);
                    break;
                case 3:
                    ActionMessage aMsg = (ActionMessage) msg.obj;
                    Log.d(ControlsProviderService.TAG, "performControlAction mToken:" + ControlsProviderService.this.mToken + ", ControlId:" + aMsg.mControlId);
                    ControlsProviderService.this.performControlAction(aMsg.mControlId, aMsg.mAction, consumerFor(aMsg.mControlId, aMsg.mCb));
                    break;
                case 4:
                    IControlsSubscriber cs2 = (IControlsSubscriber) msg.obj;
                    SubscriberProxy proxy3 = new SubscriberProxy(true, ControlsProviderService.this.mToken, cs2);
                    Flow.Publisher<Control> publisher = ControlsProviderService.this.createPublisherForSuggested();
                    if (publisher == null) {
                        Log.i(ControlsProviderService.TAG, "No publisher provided for suggested controls");
                        proxy3.onComplete();
                        break;
                    } else {
                        Log.d(ControlsProviderService.TAG, "createPublisherForSuggested mToken:" + ControlsProviderService.this.mToken);
                        publisher.subscribe(proxy3);
                        break;
                    }
            }
            if (msg.what == 101) {
                IControlsProviderInfoSubscriber cpis = (IControlsProviderInfoSubscriber) msg.obj;
                if (ControlsProviderService.this.mControlsProviderInfoSupplier != null) {
                    try {
                        cpis.onNext(ControlsProviderService.this.mToken, (ControlsProviderInfo) ControlsProviderService.this.mControlsProviderInfoSupplier.get());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private Consumer<Integer> consumerFor(final String controlId, final IControlsActionCallback cb) {
            return new Consumer() { // from class: android.service.controls.ControlsProviderService$RequestHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ControlsProviderService.RequestHandler.this.lambda$consumerFor$0(cb, controlId, (Integer) obj);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$consumerFor$0(IControlsActionCallback cb, String controlId, Integer response) {
            Preconditions.checkNotNull(response);
            if (!ControlAction.isValidResponse(response.intValue())) {
                Log.e(ControlsProviderService.TAG, "Not valid response result: " + response);
                response = 0;
            }
            try {
                cb.accept(ControlsProviderService.this.mToken, controlId, response.intValue());
            } catch (RemoteException ex) {
                ex.rethrowAsRuntimeException();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isStatelessControl(Control control) {
        return control.getStatus() == 0 && control.getControlTemplate().getTemplateType() == 0 && TextUtils.isEmpty(control.getStatusText());
    }

    private static class SubscriberProxy implements Flow.Subscriber<Control> {
        private Context mContext;
        private IControlsSubscriber mCs;
        private boolean mEnforceStateless;
        private SubscriptionAdapter mSubscription;
        private IBinder mToken;

        SubscriberProxy(boolean enforceStateless, IBinder token, IControlsSubscriber cs) {
            this.mEnforceStateless = enforceStateless;
            this.mToken = token;
            this.mCs = cs;
        }

        SubscriberProxy(Context context, boolean enforceStateless, IBinder token, IControlsSubscriber cs) {
            this(enforceStateless, token, cs);
            this.mContext = context;
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(Flow.Subscription subscription) {
            try {
                SubscriptionAdapter subscriptionAdapter = new SubscriptionAdapter(subscription);
                this.mCs.onSubscribe(this.mToken, subscriptionAdapter);
                this.mSubscription = subscriptionAdapter;
            } catch (RemoteException ex) {
                handleRemoteException(ex);
            }
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onNext(Control control) {
            Preconditions.checkNotNull(control);
            try {
                if (this.mEnforceStateless && !ControlsProviderService.isStatelessControl(control)) {
                    Log.w(ControlsProviderService.TAG, "onNext(): control is not stateless. Use the Control.StatelessBuilder() to build the control.");
                    control = new Control.StatelessBuilder(control).build();
                }
                if (this.mContext != null) {
                    control.getControlTemplate().prepareTemplateForBinder(this.mContext);
                }
                this.mCs.onNext(this.mToken, control);
            } catch (RemoteException ex) {
                handleRemoteException(ex);
            }
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(Throwable t) {
            try {
                this.mCs.onError(this.mToken, t.toString());
                this.mSubscription = null;
            } catch (RemoteException ex) {
                handleRemoteException(ex);
            }
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
            try {
                this.mCs.onComplete(this.mToken);
                this.mSubscription = null;
            } catch (RemoteException ex) {
                handleRemoteException(ex);
            }
        }

        private void handleRemoteException(RemoteException ex) {
            if (ex instanceof DeadObjectException) {
                SubscriptionAdapter subscriptionAdapter = this.mSubscription;
                if (subscriptionAdapter != null) {
                    subscriptionAdapter.cancel();
                    return;
                }
                return;
            }
            ex.rethrowAsRuntimeException();
        }
    }

    public static void requestAddControl(Context context, ComponentName componentName, Control control) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(componentName);
        Preconditions.checkNotNull(control);
        String controlsPackage = context.getString(R.string.config_controlsPackage);
        Intent intent = new Intent(ACTION_ADD_CONTROL);
        intent.putExtra(Intent.EXTRA_COMPONENT_NAME, componentName);
        intent.setPackage(controlsPackage);
        if (isStatelessControl(control)) {
            intent.putExtra(EXTRA_CONTROL, control);
        } else {
            intent.putExtra(EXTRA_CONTROL, new Control.StatelessBuilder(control).build());
        }
        context.sendBroadcast(intent, Manifest.permission.BIND_CONTROLS);
    }

    public static void requestAddControl(Context context, ComponentName componentName, Control control, boolean autoAdd) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(componentName);
        Preconditions.checkNotNull(control);
        String controlsPackage = context.getString(R.string.config_controlsPackage);
        Intent intent = new Intent(ACTION_ADD_CONTROL);
        intent.putExtra(Intent.EXTRA_COMPONENT_NAME, componentName);
        intent.setPackage(controlsPackage);
        if (isStatelessControl(control)) {
            intent.putExtra(EXTRA_CONTROL, control);
        } else {
            intent.putExtra(EXTRA_CONTROL, new Control.StatelessBuilder(control).build());
        }
        intent.putExtra(EXTRA_CONTROL_AUTO_ADD, autoAdd);
        context.sendBroadcast(intent, Manifest.permission.BIND_CONTROLS);
    }

    public static void requestAddControls(Context context, ComponentName componentName, List<Control> controls, boolean autoAdd) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(componentName);
        Preconditions.checkNotNull(controls);
        String controlsPackage = context.getString(R.string.config_controlsPackage);
        Intent intent = new Intent(ACTION_ADD_CONTROL);
        intent.putExtra(Intent.EXTRA_COMPONENT_NAME, componentName);
        intent.setPackage(controlsPackage);
        ArrayList<Control> controlArrayList = new ArrayList<>();
        for (Control control : controls) {
            if (isStatelessControl(control)) {
                controlArrayList.add(control);
            } else {
                controlArrayList.add(new Control.StatelessBuilder(control).build());
            }
        }
        intent.putParcelableArrayListExtra(EXTRA_CONTROLS, controlArrayList);
        intent.putExtra(EXTRA_CONTROL_AUTO_ADD, autoAdd);
        context.sendBroadcast(intent, Manifest.permission.BIND_CONTROLS);
    }

    private static class SubscriptionAdapter extends IControlsSubscription.Stub {
        final Flow.Subscription mSubscription;

        SubscriptionAdapter(Flow.Subscription s) {
            this.mSubscription = s;
        }

        @Override // android.service.controls.IControlsSubscription
        public void request(long n) {
            this.mSubscription.request(n);
        }

        @Override // android.service.controls.IControlsSubscription
        public void cancel() {
            this.mSubscription.cancel();
        }
    }

    private static class ActionMessage {
        final ControlAction mAction;
        final IControlsActionCallback mCb;
        final String mControlId;

        ActionMessage(String controlId, ControlAction action, IControlsActionCallback cb) {
            this.mControlId = controlId;
            this.mAction = action;
            this.mCb = cb;
        }
    }

    private static class SubscribeMessage {
        final List<String> mControlIds;
        final IControlsSubscriber mSubscriber;

        SubscribeMessage(List<String> controlIds, IControlsSubscriber subscriber) {
            this.mControlIds = controlIds;
            this.mSubscriber = subscriber;
        }
    }
}
