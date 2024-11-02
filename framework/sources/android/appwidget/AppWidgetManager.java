package android.appwidget;

import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.os.BackgroundThread;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class AppWidgetManager {
    public static final String ACTION_APPWIDGET_BIND = "android.appwidget.action.APPWIDGET_BIND";
    public static final String ACTION_APPWIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE";
    public static final String ACTION_APPWIDGET_DELETED = "android.appwidget.action.APPWIDGET_DELETED";
    public static final String ACTION_APPWIDGET_DISABLED = "android.appwidget.action.APPWIDGET_DISABLED";
    public static final String ACTION_APPWIDGET_ENABLED = "android.appwidget.action.APPWIDGET_ENABLED";
    public static final String ACTION_APPWIDGET_ENABLE_AND_UPDATE = "android.appwidget.action.APPWIDGET_ENABLE_AND_UPDATE";
    public static final String ACTION_APPWIDGET_HOST_RESTORED = "android.appwidget.action.APPWIDGET_HOST_RESTORED";
    public static final String ACTION_APPWIDGET_OPTIONS_CHANGED = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS";
    public static final String ACTION_APPWIDGET_PICK = "android.appwidget.action.APPWIDGET_PICK";
    public static final String ACTION_APPWIDGET_RESTORED = "android.appwidget.action.APPWIDGET_RESTORED";
    public static final String ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ACTION_KEYGUARD_APPWIDGET_PICK = "android.appwidget.action.KEYGUARD_APPWIDGET_PICK";
    public static final String EXTRA_APPWIDGET_ID = "appWidgetId";
    public static final String EXTRA_APPWIDGET_IDS = "appWidgetIds";
    public static final String EXTRA_APPWIDGET_OLD_IDS = "appWidgetOldIds";
    public static final String EXTRA_APPWIDGET_OPTIONS = "appWidgetOptions";
    public static final String EXTRA_APPWIDGET_PREVIEW = "appWidgetPreview";
    public static final String EXTRA_APPWIDGET_PROVIDER = "appWidgetProvider";
    public static final String EXTRA_APPWIDGET_PROVIDER_PROFILE = "appWidgetProviderProfile";
    public static final String EXTRA_CATEGORY_FILTER = "categoryFilter";
    public static final String EXTRA_CUSTOM_EXTRAS = "customExtras";
    public static final String EXTRA_CUSTOM_INFO = "customInfo";
    public static final String EXTRA_CUSTOM_SORT = "customSort";
    public static final String EXTRA_HOST_ID = "hostId";
    public static final int INVALID_APPWIDGET_ID = 0;
    public static final String META_DATA_APPWIDGET_PROVIDER = "android.appwidget.provider";
    public static final String OPTION_APPWIDGET_HOST_CATEGORY = "appWidgetCategory";
    public static final String OPTION_APPWIDGET_MAX_HEIGHT = "appWidgetMaxHeight";
    public static final String OPTION_APPWIDGET_MAX_WIDTH = "appWidgetMaxWidth";
    public static final String OPTION_APPWIDGET_MIN_HEIGHT = "appWidgetMinHeight";
    public static final String OPTION_APPWIDGET_MIN_WIDTH = "appWidgetMinWidth";
    public static final String OPTION_APPWIDGET_RESTORE_COMPLETED = "appWidgetRestoreCompleted";
    public static final String OPTION_APPWIDGET_SIZES = "appWidgetSizes";
    public static final String SEM_ACTION_APPWIDGET_CONFIGURE = "android.appwidget.action.SEM_APPWIDGET_CONFIGURE";
    public static final String SEM_ACTION_APPWIDGET_UNBIND = "com.samsung.android.appwidget.action.APPWIDGET_UNBIND";
    public static int SEM_APPWIDGET_LOCATION_LEFT = 0;
    public static int SEM_APPWIDGET_LOCATION_RIGHT = 1;
    public static String SEM_APPWIDGET_STYLE_COMPLICATION = "complication";
    public static final String SEM_EXTRA_APPWIDGET_PACKAGENAME = "appWidgetPackageName";
    public static final String SEM_META_DATA_CONFIGURE_ACTIVITY = "android.appwidget.provider.semConfigureActivity";
    public static final String SEM_META_DATA_UPDATE_SCREENSIZE_CHANGED = "UpdateForScreenSizeChange";
    public static final String SEM_OPTION_APPWIDGET_COLUMN_SPAN = "semAppWidgetColumnSpan";
    public static final String SEM_OPTION_APPWIDGET_LOCATION = "semAppWidgetLocation";
    public static final String SEM_OPTION_APPWIDGET_ROW_SPAN = "semAppWidgetRowSpan";
    public static final String SEM_OPTION_APPWIDGET_STYLE = "widgetStyle";
    private static final String TAG = "AppWidgetManager";
    private final Context mContext;
    private final DisplayMetrics mDisplayMetrics;
    private final String mPackageName;
    private final IAppWidgetService mService;

    public static AppWidgetManager getInstance(Context context) {
        return (AppWidgetManager) context.getSystemService(Context.APPWIDGET_SERVICE);
    }

    public AppWidgetManager(Context context, IAppWidgetService service) {
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = service;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        if (service == null) {
            return;
        }
        BackgroundThread.getExecutor().execute(new Runnable() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AppWidgetManager.this.lambda$new$3();
            }
        });
    }

    public /* synthetic */ void lambda$new$3() {
        try {
            this.mService.notifyProviderInheritance((ComponentName[]) getInstalledProvidersForPackage(this.mPackageName, null).stream().filter(new Predicate() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((AppWidgetProviderInfo) obj);
                }
            }).map(new Function() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    ComponentName componentName;
                    componentName = ((AppWidgetProviderInfo) obj).provider;
                    return componentName;
                }
            }).filter(new Predicate() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return AppWidgetManager.lambda$new$1((ComponentName) obj);
                }
            }).toArray(new IntFunction() { // from class: android.appwidget.AppWidgetManager$$ExternalSyntheticLambda3
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return AppWidgetManager.lambda$new$2(i);
                }
            }));
        } catch (Exception e) {
            Log.e(TAG, "Nofity service of inheritance info", e);
        }
    }

    public static /* synthetic */ boolean lambda$new$1(ComponentName p) {
        try {
            Class clazz = Class.forName(p.getClassName());
            return AppWidgetProvider.class.isAssignableFrom(clazz);
        } catch (Exception e) {
            return false;
        }
    }

    public static /* synthetic */ ComponentName[] lambda$new$2(int x$0) {
        return new ComponentName[x$0];
    }

    public void updateAppWidget(int[] appWidgetIds, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        try {
            Log.i(TAG, "updateAppWidget() appWidgetIds = " + Arrays.toString(appWidgetIds));
            this.mService.updateAppWidgetIds(this.mPackageName, appWidgetIds, views);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetSkipPackageChanged(String packageName) {
        Context context;
        if (this.mService == null || (context = this.mContext) == null || !packageName.equals(context.getPackageName())) {
            return;
        }
        try {
            this.mService.semSetSkipPackageChanged(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semChangeHostIds(int[] appWidgetIds, int hostId) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.changeHostIds(this.mPackageName, appWidgetIds, hostId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidgetOptions(int appWidgetId, Bundle options) {
        if (this.mService == null) {
            return;
        }
        try {
            Log.d(TAG, "updateAppWidgetOptions() appWidgetId = " + appWidgetId + ", options = " + options);
            this.mService.updateAppWidgetOptions(this.mPackageName, appWidgetId, options);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getAppWidgetOptions(int appWidgetId) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return Bundle.EMPTY;
        }
        try {
            return iAppWidgetService.getAppWidgetOptions(this.mPackageName, appWidgetId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidget(int appWidgetId, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        updateAppWidget(new int[]{appWidgetId}, views);
    }

    public void partiallyUpdateAppWidget(int[] appWidgetIds, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        try {
            Log.d(TAG, "partiallyUpdateAppWidget() appWidgetIds = " + Arrays.toString(appWidgetIds));
            if (appWidgetIds != null && appWidgetIds.length != 0) {
                this.mService.partiallyUpdateAppWidgetIds(this.mPackageName, appWidgetIds, views);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void partiallyUpdateAppWidget(int appWidgetId, RemoteViews views) {
        if (this.mService == null) {
            return;
        }
        partiallyUpdateAppWidget(new int[]{appWidgetId}, views);
    }

    public void updateAppWidget(ComponentName provider, RemoteViews views) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.updateAppWidgetProvider(provider, views);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateAppWidgetProviderInfo(ComponentName provider, String metaDataKey) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.updateAppWidgetProviderInfo(provider, metaDataKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAppWidgetViewDataChanged(int[] appWidgetIds, int viewId) {
        if (this.mService == null) {
            return;
        }
        try {
            Log.i(TAG, "notifyAppWidgetViewDataChanged() appWidgetIds = " + Arrays.toString(appWidgetIds) + ", viewId = " + viewId);
            this.mService.notifyAppWidgetViewDataChanged(this.mPackageName, appWidgetIds, viewId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAppWidgetViewDataChanged(int appWidgetId, int viewId) {
        if (this.mService == null) {
            return;
        }
        notifyAppWidgetViewDataChanged(new int[]{appWidgetId}, viewId);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(UserHandle profile) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, profile, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(int categoryFilter, UserHandle profile) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(categoryFilter, profile, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForPackage(String packageName, UserHandle profile) {
        if (packageName == null) {
            throw new NullPointerException("A non-null package must be passed to this method. If you want all widgets regardless of package, see getInstalledProvidersForProfile(UserHandle)");
        }
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, profile, packageName);
    }

    public List<AppWidgetProviderInfo> getInstalledProviders() {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(1, null, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProviders(int categoryFilter) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return getInstalledProvidersForProfile(categoryFilter, null, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(int categoryFilter, UserHandle profile, String packageName) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        if (profile == null) {
            profile = this.mContext.getUser();
        }
        try {
            ParceledListSlice<AppWidgetProviderInfo> providers = this.mService.getInstalledProvidersForProfile(categoryFilter, profile.getIdentifier(), packageName);
            if (providers == null) {
                return Collections.emptyList();
            }
            for (AppWidgetProviderInfo info : providers.getList()) {
                info.updateDimensions(this.mDisplayMetrics);
            }
            return providers.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AppWidgetProviderInfo getAppWidgetInfo(int appWidgetId) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return null;
        }
        try {
            AppWidgetProviderInfo info = iAppWidgetService.getAppWidgetInfo(this.mPackageName, appWidgetId);
            if (info != null) {
                info.updateDimensions(this.mDisplayMetrics);
            }
            return info;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void bindAppWidgetId(int appWidgetId, ComponentName provider) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetId(appWidgetId, provider, null);
    }

    public void bindAppWidgetId(int appWidgetId, ComponentName provider, Bundle options) {
        if (this.mService == null) {
            return;
        }
        bindAppWidgetIdIfAllowed(appWidgetId, this.mContext.getUser(), provider, options);
    }

    public boolean bindAppWidgetIdIfAllowed(int appWidgetId, ComponentName provider) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(appWidgetId, this.mContext.getUserId(), provider, (Bundle) null);
    }

    public boolean bindAppWidgetIdIfAllowed(int appWidgetId, ComponentName provider, Bundle options) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(appWidgetId, this.mContext.getUserId(), provider, options);
    }

    public boolean bindAppWidgetIdIfAllowed(int appWidgetId, UserHandle user, ComponentName provider, Bundle options) {
        if (this.mService == null) {
            return false;
        }
        return bindAppWidgetIdIfAllowed(appWidgetId, user.getIdentifier(), provider, options);
    }

    public boolean hasBindAppWidgetPermission(String packageName, int userId) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.hasBindAppWidgetPermission(packageName, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasBindAppWidgetPermission(String packageName) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.hasBindAppWidgetPermission(packageName, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBindAppWidgetPermission(String packageName, boolean permission) {
        if (this.mService == null) {
            return;
        }
        setBindAppWidgetPermission(packageName, this.mContext.getUserId(), permission);
    }

    public void setBindAppWidgetPermission(String packageName, int userId, boolean permission) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.setBindAppWidgetPermission(packageName, userId, permission);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean bindRemoteViewsService(Context context, int appWidgetId, Intent intent, IServiceConnection connection, int flags) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.bindRemoteViewsService(context.getOpPackageName(), appWidgetId, intent, context.getIApplicationThread(), context.getActivityToken(), connection, Integer.toUnsignedLong(flags));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getAppWidgetIds(ComponentName provider) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return new int[0];
        }
        try {
            return iAppWidgetService.getAppWidgetIds(provider);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBoundWidgetPackage(String packageName, int userId) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            return iAppWidgetService.isBoundWidgetPackage(packageName, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean bindAppWidgetIdIfAllowed(int appWidgetId, int profileId, ComponentName provider, Bundle options) {
        if (this.mService != null) {
            try {
                Log.d(TAG, "bindAppWidgetIdIfAllowed() appWidgetIds = " + appWidgetId + ", provider = " + provider);
                Log.d(TAG, "Stack:", new Throwable("stack dump"));
                return this.mService.bindAppWidgetId(this.mPackageName, appWidgetId, profileId, provider, options);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isRequestPinAppWidgetSupported() {
        try {
            return this.mService.isRequestPinAppWidgetSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestPinAppWidget(ComponentName provider, PendingIntent successCallback) {
        return requestPinAppWidget(provider, null, successCallback);
    }

    public boolean requestPinAppWidget(ComponentName provider, Bundle extras, PendingIntent successCallback) {
        try {
            return this.mService.requestPinAppWidget(this.mPackageName, provider, extras, successCallback == null ? null : successCallback.getIntentSender());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void noteAppWidgetTapped(int appWidgetId) {
        try {
            this.mService.noteAppWidgetTapped(this.mPackageName, appWidgetId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
