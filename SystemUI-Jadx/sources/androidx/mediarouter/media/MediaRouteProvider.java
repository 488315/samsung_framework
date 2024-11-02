package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.mediarouter.media.MediaRouter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class MediaRouteProvider {
    public Callback mCallback;
    public final Context mContext;
    public MediaRouteProviderDescriptor mDescriptor;
    public MediaRouteDiscoveryRequest mDiscoveryRequest;
    public final ProviderHandler mHandler;
    public final ProviderMetadata mMetadata;
    public boolean mPendingDescriptorChange;
    public boolean mPendingDiscoveryRequestChange;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ProviderHandler extends Handler {
        public ProviderHandler() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 2) {
                    MediaRouteProvider mediaRouteProvider = MediaRouteProvider.this;
                    mediaRouteProvider.mPendingDiscoveryRequestChange = false;
                    mediaRouteProvider.onDiscoveryRequestChanged(mediaRouteProvider.mDiscoveryRequest);
                    return;
                }
                return;
            }
            MediaRouteProvider mediaRouteProvider2 = MediaRouteProvider.this;
            mediaRouteProvider2.mPendingDescriptorChange = false;
            Callback callback = mediaRouteProvider2.mCallback;
            if (callback != null) {
                callback.onDescriptorChanged(mediaRouteProvider2, mediaRouteProvider2.mDescriptor);
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ProviderMetadata {
        public final ComponentName mComponentName;

        public ProviderMetadata(ComponentName componentName) {
            if (componentName != null) {
                this.mComponentName = componentName;
                return;
            }
            throw new IllegalArgumentException("componentName must not be null");
        }

        public final String toString() {
            return "ProviderMetadata{ componentName=" + this.mComponentName.flattenToShortString() + " }";
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class RouteController {
        public void onUnselect() {
        }

        public void onUnselect(int i) {
            onUnselect();
        }

        public void onSetVolume(int i) {
        }

        public void onUpdateVolume(int i) {
        }

        public void onRelease() {
        }

        public void onSelect() {
        }
    }

    public MediaRouteProvider(Context context) {
        this(context, null);
    }

    public DynamicGroupRouteController onCreateDynamicGroupRouteController(String str) {
        if (str != null) {
            return null;
        }
        throw new IllegalArgumentException("initialMemberRouteId cannot be null.");
    }

    public RouteController onCreateRouteController(String str) {
        if (str != null) {
            return null;
        }
        throw new IllegalArgumentException("routeId cannot be null");
    }

    public final void setDescriptor(MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        MediaRouter.checkCallingThread();
        if (this.mDescriptor != mediaRouteProviderDescriptor) {
            this.mDescriptor = mediaRouteProviderDescriptor;
            if (!this.mPendingDescriptorChange) {
                this.mPendingDescriptorChange = true;
                this.mHandler.sendEmptyMessage(1);
            }
        }
    }

    public final void setDiscoveryRequest(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        MediaRouter.checkCallingThread();
        if (Objects.equals(this.mDiscoveryRequest, mediaRouteDiscoveryRequest)) {
            return;
        }
        this.mDiscoveryRequest = mediaRouteDiscoveryRequest;
        if (!this.mPendingDiscoveryRequestChange) {
            this.mPendingDiscoveryRequestChange = true;
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public MediaRouteProvider(Context context, ProviderMetadata providerMetadata) {
        this.mHandler = new ProviderHandler();
        if (context != null) {
            this.mContext = context;
            if (providerMetadata == null) {
                this.mMetadata = new ProviderMetadata(new ComponentName(context, getClass()));
                return;
            } else {
                this.mMetadata = providerMetadata;
                return;
            }
        }
        throw new IllegalArgumentException("context must not be null");
    }

    public RouteController onCreateRouteController(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        }
        if (str2 != null) {
            return onCreateRouteController(str);
        }
        throw new IllegalArgumentException("routeGroupId cannot be null");
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class DynamicGroupRouteController extends RouteController {
        public Executor mExecutor;
        public OnDynamicRoutesChangedListener mListener;
        public final Object mLock = new Object();
        public MediaRouteDescriptor mPendingGroupRoute;
        public Collection mPendingRoutes;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public interface OnDynamicRoutesChangedListener {
        }

        public String getGroupableSelectionTitle() {
            return null;
        }

        public String getTransferableSectionTitle() {
            return null;
        }

        public final void notifyDynamicRoutesChanged(final MediaRouteDescriptor mediaRouteDescriptor, final Collection collection) {
            if (mediaRouteDescriptor != null) {
                synchronized (this.mLock) {
                    Executor executor = this.mExecutor;
                    if (executor != null) {
                        final OnDynamicRoutesChangedListener onDynamicRoutesChangedListener = this.mListener;
                        executor.execute(new Runnable() { // from class: androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController.3
                            @Override // java.lang.Runnable
                            public final void run() {
                                ((MediaRouter.GlobalMediaRouter.AnonymousClass3) onDynamicRoutesChangedListener).onRoutesChanged(DynamicGroupRouteController.this, mediaRouteDescriptor, collection);
                            }
                        });
                    } else {
                        this.mPendingGroupRoute = mediaRouteDescriptor;
                        this.mPendingRoutes = new ArrayList(collection);
                    }
                }
                return;
            }
            throw new NullPointerException("groupRoute must not be null");
        }

        public abstract void onAddMemberRoute(String str);

        public abstract void onRemoveMemberRoute(String str);

        public abstract void onUpdateMemberRoutes(List list);

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class DynamicRouteDescriptor {
            public final boolean mIsGroupable;
            public final boolean mIsTransferable;
            public final boolean mIsUnselectable;
            public final MediaRouteDescriptor mMediaRouteDescriptor;
            public final int mSelectionState;

            public DynamicRouteDescriptor(MediaRouteDescriptor mediaRouteDescriptor, int i, boolean z, boolean z2, boolean z3) {
                this.mMediaRouteDescriptor = mediaRouteDescriptor;
                this.mSelectionState = i;
                this.mIsUnselectable = z;
                this.mIsGroupable = z2;
                this.mIsTransferable = z3;
            }

            /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
            /* loaded from: classes.dex */
            public final class Builder {
                public boolean mIsGroupable;
                public final MediaRouteDescriptor mRouteDescriptor;
                public int mSelectionState;

                public Builder(MediaRouteDescriptor mediaRouteDescriptor) {
                    this.mSelectionState = 1;
                    this.mIsGroupable = false;
                    if (mediaRouteDescriptor != null) {
                        this.mRouteDescriptor = mediaRouteDescriptor;
                        return;
                    }
                    throw new NullPointerException("descriptor must not be null");
                }

                public Builder(DynamicRouteDescriptor dynamicRouteDescriptor) {
                    this.mSelectionState = 1;
                    this.mIsGroupable = false;
                    if (dynamicRouteDescriptor != null) {
                        this.mRouteDescriptor = dynamicRouteDescriptor.mMediaRouteDescriptor;
                        this.mSelectionState = dynamicRouteDescriptor.mSelectionState;
                        this.mIsGroupable = dynamicRouteDescriptor.mIsGroupable;
                        return;
                    }
                    throw new NullPointerException("dynamicRouteDescriptor must not be null");
                }
            }
        }
    }

    public void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class Callback {
        public void onDescriptorChanged(MediaRouteProvider mediaRouteProvider, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        }
    }
}
