package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouter;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class RegisteredMediaRouteProvider extends MediaRouteProvider implements ServiceConnection {
    public static final boolean DEBUG = Log.isLoggable("MediaRouteProviderProxy", 3);
    public Connection mActiveConnection;
    public boolean mBound;
    public final ComponentName mComponentName;
    public boolean mConnectionReady;
    public RegisteredMediaRouteProviderWatcher$$ExternalSyntheticLambda0 mControllerCallback;
    public final ArrayList mControllerConnections;
    public final PrivateHandler mPrivateHandler;
    public boolean mStarted;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Connection implements IBinder.DeathRecipient {
        public int mPendingRegisterRequestId;
        public final ReceiveHandler mReceiveHandler;
        public final Messenger mReceiveMessenger;
        public final Messenger mServiceMessenger;
        public int mServiceVersion;
        public int mNextRequestId = 1;
        public int mNextControllerId = 1;
        public final SparseArray mPendingCallbacks = new SparseArray();

        public Connection(Messenger messenger) {
            this.mServiceMessenger = messenger;
            ReceiveHandler receiveHandler = new ReceiveHandler(this);
            this.mReceiveHandler = receiveHandler;
            this.mReceiveMessenger = new Messenger(receiveHandler);
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.Connection.2
                @Override // java.lang.Runnable
                public final void run() {
                    Connection connection = Connection.this;
                    RegisteredMediaRouteProvider registeredMediaRouteProvider = RegisteredMediaRouteProvider.this;
                    if (registeredMediaRouteProvider.mActiveConnection == connection) {
                        if (RegisteredMediaRouteProvider.DEBUG) {
                            Log.d("MediaRouteProviderProxy", registeredMediaRouteProvider + ": Service connection died");
                        }
                        registeredMediaRouteProvider.disconnect();
                    }
                }
            });
        }

        public final void selectRoute(int i) {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(5, i2, i, null, null);
        }

        public final boolean sendRequest(int i, int i2, int i3, Object obj, Bundle bundle) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = i2;
            obtain.arg2 = i3;
            obtain.obj = obj;
            obtain.setData(bundle);
            obtain.replyTo = this.mReceiveMessenger;
            try {
                this.mServiceMessenger.send(obtain);
                return true;
            } catch (DeadObjectException unused) {
                return false;
            } catch (RemoteException e) {
                if (i != 2) {
                    Log.e("MediaRouteProviderProxy", "Could not send message to service.", e);
                    return false;
                }
                return false;
            }
        }

        public final void setVolume(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt("volume", i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(7, i3, i, null, bundle);
        }

        public final void updateVolume(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt("volume", i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(8, i3, i, null, bundle);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface ControllerConnection {
        void attachConnection(Connection connection);

        void detachConnection();

        int getControllerId();
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class PrivateHandler extends Handler {
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ReceiveHandler extends Handler {
        public final WeakReference mConnectionRef;

        public ReceiveHandler(Connection connection) {
            this.mConnectionRef = new WeakReference(connection);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            MediaRouteDescriptor mediaRouteDescriptor;
            MediaRouteDescriptor mediaRouteDescriptor2;
            MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor;
            Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                int i = message.what;
                int i2 = message.arg1;
                int i3 = message.arg2;
                Object obj = message.obj;
                Bundle peekData = message.peekData();
                boolean z = true;
                String str = null;
                ControllerConnection controllerConnection = null;
                ControllerConnection controllerConnection2 = null;
                switch (i) {
                    case 0:
                        if (i2 == connection.mPendingRegisterRequestId) {
                            connection.mPendingRegisterRequestId = 0;
                            RegisteredMediaRouteProvider registeredMediaRouteProvider = RegisteredMediaRouteProvider.this;
                            if (registeredMediaRouteProvider.mActiveConnection == connection) {
                                if (RegisteredMediaRouteProvider.DEBUG) {
                                    Log.d("MediaRouteProviderProxy", registeredMediaRouteProvider + ": Service connection error - Registration failed");
                                }
                                registeredMediaRouteProvider.unbind();
                            }
                        }
                        MediaRouter.ControlRequestCallback controlRequestCallback = (MediaRouter.ControlRequestCallback) connection.mPendingCallbacks.get(i2);
                        if (controlRequestCallback != null) {
                            connection.mPendingCallbacks.remove(i2);
                            controlRequestCallback.onError(null, null);
                            break;
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        if (obj == null || (obj instanceof Bundle)) {
                            Bundle bundle = (Bundle) obj;
                            if (connection.mServiceVersion == 0 && i2 == connection.mPendingRegisterRequestId && i3 >= 1) {
                                connection.mPendingRegisterRequestId = 0;
                                connection.mServiceVersion = i3;
                                RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(connection, MediaRouteProviderDescriptor.fromBundle(bundle));
                                RegisteredMediaRouteProvider registeredMediaRouteProvider2 = RegisteredMediaRouteProvider.this;
                                if (registeredMediaRouteProvider2.mActiveConnection == connection) {
                                    registeredMediaRouteProvider2.mConnectionReady = true;
                                    int size = registeredMediaRouteProvider2.mControllerConnections.size();
                                    for (int i4 = 0; i4 < size; i4++) {
                                        ((ControllerConnection) registeredMediaRouteProvider2.mControllerConnections.get(i4)).attachConnection(registeredMediaRouteProvider2.mActiveConnection);
                                    }
                                    MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest = registeredMediaRouteProvider2.mDiscoveryRequest;
                                    if (mediaRouteDiscoveryRequest != null) {
                                        Connection connection2 = registeredMediaRouteProvider2.mActiveConnection;
                                        int i5 = connection2.mNextRequestId;
                                        connection2.mNextRequestId = i5 + 1;
                                        connection2.sendRequest(10, i5, 0, mediaRouteDiscoveryRequest.mBundle, null);
                                        break;
                                    }
                                }
                            }
                        }
                        z = false;
                        break;
                    case 3:
                        if (obj == null || (obj instanceof Bundle)) {
                            Bundle bundle2 = (Bundle) obj;
                            MediaRouter.ControlRequestCallback controlRequestCallback2 = (MediaRouter.ControlRequestCallback) connection.mPendingCallbacks.get(i2);
                            if (controlRequestCallback2 != null) {
                                connection.mPendingCallbacks.remove(i2);
                                controlRequestCallback2.onResult(bundle2);
                                break;
                            }
                        }
                        z = false;
                        break;
                    case 4:
                        if (obj == null || (obj instanceof Bundle)) {
                            if (peekData != null) {
                                str = peekData.getString("error");
                            }
                            Bundle bundle3 = (Bundle) obj;
                            MediaRouter.ControlRequestCallback controlRequestCallback3 = (MediaRouter.ControlRequestCallback) connection.mPendingCallbacks.get(i2);
                            if (controlRequestCallback3 != null) {
                                connection.mPendingCallbacks.remove(i2);
                                controlRequestCallback3.onError(str, bundle3);
                                break;
                            }
                        }
                        z = false;
                        break;
                    case 5:
                        if (obj == null || (obj instanceof Bundle)) {
                            Bundle bundle4 = (Bundle) obj;
                            if (connection.mServiceVersion != 0) {
                                RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(connection, MediaRouteProviderDescriptor.fromBundle(bundle4));
                                break;
                            }
                        }
                        z = false;
                        break;
                    case 6:
                        if (obj instanceof Bundle) {
                            Bundle bundle5 = (Bundle) obj;
                            MediaRouter.ControlRequestCallback controlRequestCallback4 = (MediaRouter.ControlRequestCallback) connection.mPendingCallbacks.get(i2);
                            if (bundle5 != null && bundle5.containsKey("routeId")) {
                                connection.mPendingCallbacks.remove(i2);
                                controlRequestCallback4.onResult(bundle5);
                            } else {
                                controlRequestCallback4.onError("DynamicGroupRouteController is created without valid route id.", bundle5);
                            }
                        } else {
                            Log.w("MediaRouteProviderProxy", "No further information on the dynamic group controller");
                        }
                        z = false;
                        break;
                    case 7:
                        if (obj == null || (obj instanceof Bundle)) {
                            Bundle bundle6 = (Bundle) obj;
                            if (connection.mServiceVersion != 0) {
                                Bundle bundle7 = (Bundle) bundle6.getParcelable("groupRoute");
                                if (bundle7 != null) {
                                    mediaRouteDescriptor = new MediaRouteDescriptor(bundle7);
                                } else {
                                    mediaRouteDescriptor = null;
                                }
                                ArrayList parcelableArrayList = bundle6.getParcelableArrayList("dynamicRoutes");
                                ArrayList arrayList = new ArrayList();
                                Iterator it = parcelableArrayList.iterator();
                                while (it.hasNext()) {
                                    Bundle bundle8 = (Bundle) it.next();
                                    if (bundle8 == null) {
                                        dynamicRouteDescriptor = null;
                                    } else {
                                        Bundle bundle9 = bundle8.getBundle("mrDescriptor");
                                        if (bundle9 != null) {
                                            mediaRouteDescriptor2 = new MediaRouteDescriptor(bundle9);
                                        } else {
                                            mediaRouteDescriptor2 = null;
                                        }
                                        dynamicRouteDescriptor = new MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor(mediaRouteDescriptor2, bundle8.getInt("selectionState", 1), bundle8.getBoolean("isUnselectable", false), bundle8.getBoolean("isGroupable", false), bundle8.getBoolean("isTransferable", false));
                                    }
                                    arrayList.add(dynamicRouteDescriptor);
                                }
                                RegisteredMediaRouteProvider registeredMediaRouteProvider3 = RegisteredMediaRouteProvider.this;
                                if (registeredMediaRouteProvider3.mActiveConnection == connection) {
                                    if (RegisteredMediaRouteProvider.DEBUG) {
                                        Log.d("MediaRouteProviderProxy", registeredMediaRouteProvider3 + ": DynamicRouteDescriptors changed, descriptors=" + arrayList);
                                    }
                                    Iterator it2 = registeredMediaRouteProvider3.mControllerConnections.iterator();
                                    while (true) {
                                        if (it2.hasNext()) {
                                            ControllerConnection controllerConnection3 = (ControllerConnection) it2.next();
                                            if (controllerConnection3.getControllerId() == i3) {
                                                controllerConnection2 = controllerConnection3;
                                            }
                                        }
                                    }
                                    if (controllerConnection2 instanceof RegisteredDynamicController) {
                                        ((RegisteredDynamicController) controllerConnection2).notifyDynamicRoutesChanged(mediaRouteDescriptor, arrayList);
                                        break;
                                    }
                                }
                            }
                        }
                        z = false;
                        break;
                    case 8:
                        RegisteredMediaRouteProvider registeredMediaRouteProvider4 = RegisteredMediaRouteProvider.this;
                        if (registeredMediaRouteProvider4.mActiveConnection == connection) {
                            Iterator it3 = registeredMediaRouteProvider4.mControllerConnections.iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    ControllerConnection controllerConnection4 = (ControllerConnection) it3.next();
                                    if (controllerConnection4.getControllerId() == i3) {
                                        controllerConnection = controllerConnection4;
                                    }
                                }
                            }
                            RegisteredMediaRouteProviderWatcher$$ExternalSyntheticLambda0 registeredMediaRouteProviderWatcher$$ExternalSyntheticLambda0 = registeredMediaRouteProvider4.mControllerCallback;
                            if (registeredMediaRouteProviderWatcher$$ExternalSyntheticLambda0 != null && (controllerConnection instanceof MediaRouteProvider.RouteController)) {
                                MediaRouteProvider.RouteController routeController = (MediaRouteProvider.RouteController) controllerConnection;
                                MediaRouter.GlobalMediaRouter globalMediaRouter = (MediaRouter.GlobalMediaRouter) registeredMediaRouteProviderWatcher$$ExternalSyntheticLambda0.f$0.mCallback;
                                if (globalMediaRouter.mSelectedRouteController == routeController) {
                                    globalMediaRouter.selectRoute(globalMediaRouter.chooseFallbackRoute(), 2);
                                }
                            }
                            registeredMediaRouteProvider4.mControllerConnections.remove(controllerConnection);
                            controllerConnection.detachConnection();
                            registeredMediaRouteProvider4.updateBinding();
                        }
                        z = false;
                        break;
                    default:
                        z = false;
                        break;
                }
                if (!z && RegisteredMediaRouteProvider.DEBUG) {
                    Log.d("MediaRouteProviderProxy", "Unhandled message from server: " + message);
                }
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class RegisteredDynamicController extends MediaRouteProvider.DynamicGroupRouteController implements ControllerConnection {
        public Connection mConnection;
        public String mGroupableSectionTitle;
        public final String mInitialMemberRouteId;
        public int mPendingUpdateVolumeDelta;
        public boolean mSelected;
        public String mTransferableSectionTitle;
        public int mPendingSetVolume = -1;
        public int mControllerId = -1;

        public RegisteredDynamicController(String str) {
            this.mInitialMemberRouteId = str;
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public final void attachConnection(Connection connection) {
            MediaRouter.ControlRequestCallback controlRequestCallback = new MediaRouter.ControlRequestCallback() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.RegisteredDynamicController.1
                @Override // androidx.mediarouter.media.MediaRouter.ControlRequestCallback
                public final void onError(String str, Bundle bundle) {
                    Log.d("MediaRouteProviderProxy", "Error: " + str + ", data: " + bundle);
                }

                @Override // androidx.mediarouter.media.MediaRouter.ControlRequestCallback
                public final void onResult(Bundle bundle) {
                    String string = bundle.getString("groupableTitle");
                    RegisteredDynamicController registeredDynamicController = RegisteredDynamicController.this;
                    registeredDynamicController.mGroupableSectionTitle = string;
                    registeredDynamicController.mTransferableSectionTitle = bundle.getString("transferableTitle");
                }
            };
            this.mConnection = connection;
            int i = connection.mNextControllerId;
            connection.mNextControllerId = i + 1;
            int i2 = connection.mNextRequestId;
            connection.mNextRequestId = i2 + 1;
            Bundle bundle = new Bundle();
            bundle.putString("memberRouteId", this.mInitialMemberRouteId);
            connection.sendRequest(11, i2, i, null, bundle);
            connection.mPendingCallbacks.put(i2, controlRequestCallback);
            this.mControllerId = i;
            if (this.mSelected) {
                connection.selectRoute(i);
                int i3 = this.mPendingSetVolume;
                if (i3 >= 0) {
                    connection.setVolume(this.mControllerId, i3);
                    this.mPendingSetVolume = -1;
                }
                int i4 = this.mPendingUpdateVolumeDelta;
                if (i4 != 0) {
                    connection.updateVolume(this.mControllerId, i4);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public final void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(4, i2, i, null, null);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public final int getControllerId() {
            return this.mControllerId;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final String getGroupableSelectionTitle() {
            return this.mGroupableSectionTitle;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final String getTransferableSectionTitle() {
            return this.mTransferableSectionTitle;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final void onAddMemberRoute(String str) {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                connection.getClass();
                Bundle bundle = new Bundle();
                bundle.putString("memberRouteId", str);
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(12, i2, i, null, bundle);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onRelease() {
            RegisteredMediaRouteProvider registeredMediaRouteProvider = RegisteredMediaRouteProvider.this;
            registeredMediaRouteProvider.mControllerConnections.remove(this);
            detachConnection();
            registeredMediaRouteProvider.updateBinding();
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final void onRemoveMemberRoute(String str) {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                connection.getClass();
                Bundle bundle = new Bundle();
                bundle.putString("memberRouteId", str);
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(13, i2, i, null, bundle);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onSetVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, i);
            } else {
                this.mPendingSetVolume = i;
                this.mPendingUpdateVolumeDelta = 0;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUnselect() {
            onUnselect(0);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final void onUpdateMemberRoutes(List list) {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                connection.getClass();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("memberRouteIds", new ArrayList<>(list));
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(14, i2, i, null, bundle);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUpdateVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, i);
            } else {
                this.mPendingUpdateVolumeDelta += i;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUnselect(int i) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                int i2 = this.mControllerId;
                Bundle bundle = new Bundle();
                bundle.putInt("unselectReason", i);
                int i3 = connection.mNextRequestId;
                connection.mNextRequestId = i3 + 1;
                connection.sendRequest(6, i3, i2, null, bundle);
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class RegisteredRouteController extends MediaRouteProvider.RouteController implements ControllerConnection {
        public Connection mConnection;
        public int mControllerId;
        public int mPendingSetVolume = -1;
        public int mPendingUpdateVolumeDelta;
        public final String mRouteGroupId;
        public final String mRouteId;
        public boolean mSelected;

        public RegisteredRouteController(String str, String str2) {
            this.mRouteId = str;
            this.mRouteGroupId = str2;
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public final void attachConnection(Connection connection) {
            this.mConnection = connection;
            int i = connection.mNextControllerId;
            connection.mNextControllerId = i + 1;
            Bundle bundle = new Bundle();
            bundle.putString("routeId", this.mRouteId);
            bundle.putString("routeGroupId", this.mRouteGroupId);
            int i2 = connection.mNextRequestId;
            connection.mNextRequestId = i2 + 1;
            connection.sendRequest(3, i2, i, null, bundle);
            this.mControllerId = i;
            if (this.mSelected) {
                connection.selectRoute(i);
                int i3 = this.mPendingSetVolume;
                if (i3 >= 0) {
                    connection.setVolume(this.mControllerId, i3);
                    this.mPendingSetVolume = -1;
                }
                int i4 = this.mPendingUpdateVolumeDelta;
                if (i4 != 0) {
                    connection.updateVolume(this.mControllerId, i4);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public final void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(4, i2, i, null, null);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        @Override // androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection
        public final int getControllerId() {
            return this.mControllerId;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onRelease() {
            RegisteredMediaRouteProvider registeredMediaRouteProvider = RegisteredMediaRouteProvider.this;
            registeredMediaRouteProvider.mControllerConnections.remove(this);
            detachConnection();
            registeredMediaRouteProvider.updateBinding();
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onSetVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, i);
            } else {
                this.mPendingSetVolume = i;
                this.mPendingUpdateVolumeDelta = 0;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUnselect() {
            onUnselect(0);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUpdateVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, i);
            } else {
                this.mPendingUpdateVolumeDelta += i;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUnselect(int i) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                int i2 = this.mControllerId;
                Bundle bundle = new Bundle();
                bundle.putInt("unselectReason", i);
                int i3 = connection.mNextRequestId;
                connection.mNextRequestId = i3 + 1;
                connection.sendRequest(6, i3, i2, null, bundle);
            }
        }
    }

    public RegisteredMediaRouteProvider(Context context, ComponentName componentName) {
        super(context, new MediaRouteProvider.ProviderMetadata(componentName));
        this.mControllerConnections = new ArrayList();
        this.mComponentName = componentName;
        this.mPrivateHandler = new PrivateHandler();
    }

    public final void bind() {
        if (!this.mBound) {
            boolean z = DEBUG;
            if (z) {
                Log.d("MediaRouteProviderProxy", this + ": Binding");
            }
            Intent intent = new Intent("android.media.MediaRouteProviderService");
            intent.setComponent(this.mComponentName);
            try {
                boolean bindService = this.mContext.bindService(intent, this, PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_NOT_FOUND);
                this.mBound = bindService;
                if (!bindService && z) {
                    Log.d("MediaRouteProviderProxy", this + ": Bind failed");
                }
            } catch (SecurityException e) {
                if (DEBUG) {
                    Log.d("MediaRouteProviderProxy", this + ": Bind failed", e);
                }
            }
        }
    }

    public final RegisteredRouteController createRouteController(String str, String str2) {
        MediaRouteProviderDescriptor mediaRouteProviderDescriptor = this.mDescriptor;
        if (mediaRouteProviderDescriptor != null) {
            List list = mediaRouteProviderDescriptor.mRoutes;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (((MediaRouteDescriptor) list.get(i)).getId().equals(str)) {
                    RegisteredRouteController registeredRouteController = new RegisteredRouteController(str, str2);
                    this.mControllerConnections.add(registeredRouteController);
                    if (this.mConnectionReady) {
                        registeredRouteController.attachConnection(this.mActiveConnection);
                    }
                    updateBinding();
                    return registeredRouteController;
                }
            }
            return null;
        }
        return null;
    }

    public final void disconnect() {
        if (this.mActiveConnection != null) {
            setDescriptor(null);
            this.mConnectionReady = false;
            int size = this.mControllerConnections.size();
            for (int i = 0; i < size; i++) {
                ((ControllerConnection) this.mControllerConnections.get(i)).detachConnection();
            }
            final Connection connection = this.mActiveConnection;
            connection.sendRequest(2, 0, 0, null, null);
            connection.mReceiveHandler.mConnectionRef.clear();
            connection.mServiceMessenger.getBinder().unlinkToDeath(connection, 0);
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() { // from class: androidx.mediarouter.media.RegisteredMediaRouteProvider.Connection.1
                @Override // java.lang.Runnable
                public final void run() {
                    Connection connection2 = Connection.this;
                    int size2 = connection2.mPendingCallbacks.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((MediaRouter.ControlRequestCallback) connection2.mPendingCallbacks.valueAt(i2)).onError(null, null);
                    }
                    connection2.mPendingCallbacks.clear();
                }
            });
            this.mActiveConnection = null;
        }
    }

    public final void onConnectionDescriptorChanged(Connection connection, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d("MediaRouteProviderProxy", this + ": Descriptor changed, descriptor=" + mediaRouteProviderDescriptor);
            }
            setDescriptor(mediaRouteProviderDescriptor);
        }
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final MediaRouteProvider.DynamicGroupRouteController onCreateDynamicGroupRouteController(String str) {
        if (str != null) {
            MediaRouteProviderDescriptor mediaRouteProviderDescriptor = this.mDescriptor;
            if (mediaRouteProviderDescriptor != null) {
                List list = mediaRouteProviderDescriptor.mRoutes;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (((MediaRouteDescriptor) list.get(i)).getId().equals(str)) {
                        RegisteredDynamicController registeredDynamicController = new RegisteredDynamicController(str);
                        this.mControllerConnections.add(registeredDynamicController);
                        if (this.mConnectionReady) {
                            registeredDynamicController.attachConnection(this.mActiveConnection);
                        }
                        updateBinding();
                        return registeredDynamicController;
                    }
                }
            }
            return null;
        }
        throw new IllegalArgumentException("initialMemberRouteId cannot be null.");
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final MediaRouteProvider.RouteController onCreateRouteController(String str) {
        if (str != null) {
            return createRouteController(str, null);
        }
        throw new IllegalArgumentException("routeId cannot be null");
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        Bundle bundle;
        if (this.mConnectionReady) {
            Connection connection = this.mActiveConnection;
            int i = connection.mNextRequestId;
            connection.mNextRequestId = i + 1;
            if (mediaRouteDiscoveryRequest != null) {
                bundle = mediaRouteDiscoveryRequest.mBundle;
            } else {
                bundle = null;
            }
            connection.sendRequest(10, i, 0, bundle, null);
        }
        updateBinding();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007e  */
    @Override // android.content.ServiceConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onServiceConnected(android.content.ComponentName r10, android.os.IBinder r11) {
        /*
            r9 = this;
            boolean r10 = androidx.mediarouter.media.RegisteredMediaRouteProvider.DEBUG
            java.lang.String r0 = "MediaRouteProviderProxy"
            if (r10 == 0) goto L1a
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            java.lang.String r1 = ": Connected"
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            android.util.Log.d(r0, r10)
        L1a:
            boolean r10 = r9.mBound
            if (r10 == 0) goto L92
            r9.disconnect()
            if (r11 == 0) goto L29
            android.os.Messenger r10 = new android.os.Messenger
            r10.<init>(r11)
            goto L2a
        L29:
            r10 = 0
        L2a:
            r11 = 0
            r1 = 1
            if (r10 == 0) goto L36
            android.os.IBinder r2 = r10.getBinder()     // Catch: java.lang.NullPointerException -> L36
            if (r2 == 0) goto L36
            r2 = r1
            goto L37
        L36:
            r2 = r11
        L37:
            if (r2 == 0) goto L7e
            androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r2 = new androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection
            r2.<init>(r10)
            int r5 = r2.mNextRequestId
            int r10 = r5 + 1
            r2.mNextRequestId = r10
            r2.mPendingRegisterRequestId = r5
            r4 = 1
            r6 = 4
            r7 = 0
            r8 = 0
            r3 = r2
            boolean r10 = r3.sendRequest(r4, r5, r6, r7, r8)
            if (r10 != 0) goto L52
            goto L60
        L52:
            android.os.Messenger r10 = r2.mServiceMessenger     // Catch: android.os.RemoteException -> L5d
            android.os.IBinder r10 = r10.getBinder()     // Catch: android.os.RemoteException -> L5d
            r10.linkToDeath(r2, r11)     // Catch: android.os.RemoteException -> L5d
            r11 = r1
            goto L60
        L5d:
            r2.binderDied()
        L60:
            if (r11 == 0) goto L65
            r9.mActiveConnection = r2
            goto L92
        L65:
            boolean r10 = androidx.mediarouter.media.RegisteredMediaRouteProvider.DEBUG
            if (r10 == 0) goto L92
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            java.lang.String r9 = ": Registration failed"
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.d(r0, r9)
            goto L92
        L7e:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            java.lang.String r9 = ": Service returned invalid messenger binder"
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.e(r0, r9)
        L92:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.RegisteredMediaRouteProvider.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        if (DEBUG) {
            Log.d("MediaRouteProviderProxy", this + ": Service disconnected");
        }
        disconnect();
    }

    public final void start() {
        if (!this.mStarted) {
            if (DEBUG) {
                Log.d("MediaRouteProviderProxy", this + ": Starting");
            }
            this.mStarted = true;
            updateBinding();
        }
    }

    public final String toString() {
        return "Service connection " + this.mComponentName.flattenToShortString();
    }

    public final void unbind() {
        if (this.mBound) {
            if (DEBUG) {
                Log.d("MediaRouteProviderProxy", this + ": Unbinding");
            }
            this.mBound = false;
            disconnect();
            try {
                this.mContext.unbindService(this);
            } catch (IllegalArgumentException e) {
                Log.e("MediaRouteProviderProxy", this + ": unbindService failed", e);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0010, code lost:
    
        if (r2.mControllerConnections.isEmpty() == false) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBinding() {
        /*
            r2 = this;
            boolean r0 = r2.mStarted
            if (r0 == 0) goto L13
            androidx.mediarouter.media.MediaRouteDiscoveryRequest r0 = r2.mDiscoveryRequest
            r1 = 1
            if (r0 == 0) goto La
            goto L14
        La:
            java.util.ArrayList r0 = r2.mControllerConnections
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L13
            goto L14
        L13:
            r1 = 0
        L14:
            if (r1 == 0) goto L1a
            r2.bind()
            goto L1d
        L1a:
            r2.unbind()
        L1d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.RegisteredMediaRouteProvider.updateBinding():void");
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final MediaRouteProvider.RouteController onCreateRouteController(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        }
        if (str2 != null) {
            return createRouteController(str, str2);
        }
        throw new IllegalArgumentException("routeGroupId cannot be null");
    }
}
