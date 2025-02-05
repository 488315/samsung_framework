package android.service.quickaccesswallet;

import android.Manifest;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
class QuickAccessWalletServiceInfo {
    private static final String TAG = "QAWalletSInfo";
    private static final String TAG_WALLET_SERVICE = "quickaccesswallet-service";
    private final ServiceInfo mServiceInfo;
    private final ServiceMetadata mServiceMetadata;
    private final TileServiceMetadata mTileServiceMetadata;

    private QuickAccessWalletServiceInfo(ServiceInfo serviceInfo, ServiceMetadata metadata, TileServiceMetadata tileServiceMetadata) {
        this.mServiceInfo = serviceInfo;
        this.mServiceMetadata = metadata;
        this.mTileServiceMetadata = tileServiceMetadata;
    }

    static QuickAccessWalletServiceInfo tryCreate(Context context) {
        String defaultAppPackageName;
        if (isWalletRoleAvailable(context)) {
            defaultAppPackageName = getDefaultWalletApp(context);
        } else {
            ComponentName defaultPaymentApp = getDefaultPaymentApp(context);
            if (defaultPaymentApp == null) {
                return null;
            }
            defaultAppPackageName = defaultPaymentApp.getPackageName();
        }
        ServiceInfo serviceInfo = getWalletServiceInfo(context, defaultAppPackageName);
        if (serviceInfo == null) {
            return null;
        }
        if (!Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE.equals(serviceInfo.permission)) {
            Log.w(TAG, String.format("%s.%s does not require permission %s", serviceInfo.packageName, serviceInfo.name, Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE));
            return null;
        }
        ServiceMetadata metadata = parseServiceMetadata(context, serviceInfo);
        TileServiceMetadata tileServiceMetadata = new TileServiceMetadata(parseTileServiceMetadata(context, serviceInfo));
        return new QuickAccessWalletServiceInfo(serviceInfo, metadata, tileServiceMetadata);
    }

    private static String getDefaultWalletApp(Context context) {
        long token = Binder.clearCallingIdentity();
        try {
            RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
            List<String> roleHolders = roleManager.getRoleHolders("android.app.role.WALLET");
            return roleHolders.isEmpty() ? null : roleHolders.get(0);
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    private static boolean isWalletRoleAvailable(Context context) {
        long token = Binder.clearCallingIdentity();
        try {
            RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
            return roleManager.isRoleAvailable("android.app.role.WALLET");
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    private static ComponentName getDefaultPaymentApp(Context context) {
        ContentResolver cr = context.getContentResolver();
        String comp = Settings.Secure.getString(cr, "nfc_payment_default_component");
        if (comp == null) {
            return null;
        }
        return ComponentName.unflattenFromString(comp);
    }

    private static ServiceInfo getWalletServiceInfo(Context context, String packageName) {
        Intent intent = new Intent(QuickAccessWalletService.SERVICE_INTERFACE);
        intent.setPackage(packageName);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentServices(intent, 852096);
        if (resolveInfos.isEmpty()) {
            return null;
        }
        return resolveInfos.get(0).serviceInfo;
    }

    private static class TileServiceMetadata {
        private final Drawable mTileIcon;

        private TileServiceMetadata(Drawable tileIcon) {
            this.mTileIcon = tileIcon;
        }
    }

    private static Drawable parseTileServiceMetadata(Context context, ServiceInfo serviceInfo) {
        PackageManager pm = context.getPackageManager();
        int tileIconDrawableId = serviceInfo.metaData.getInt(QuickAccessWalletService.TILE_SERVICE_META_DATA);
        if (tileIconDrawableId != 0) {
            try {
                Resources resources = pm.getResourcesForApplication(serviceInfo.applicationInfo);
                return resources.getDrawable(tileIconDrawableId, null);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Error parsing quickaccesswallet tile service meta-data", e);
            }
        }
        return null;
    }

    static class ServiceMetadata {
        private final String mSettingsActivity;
        private final CharSequence mShortcutLongLabel;
        private final CharSequence mShortcutShortLabel;
        private final String mTargetActivity;

        /* JADX INFO: Access modifiers changed from: private */
        public static ServiceMetadata empty() {
            return new ServiceMetadata(null, null, null, null);
        }

        private ServiceMetadata(String targetActivity, String settingsActivity, CharSequence shortcutShortLabel, CharSequence shortcutLongLabel) {
            this.mTargetActivity = targetActivity;
            this.mSettingsActivity = settingsActivity;
            this.mShortcutShortLabel = shortcutShortLabel;
            this.mShortcutLongLabel = shortcutLongLabel;
        }
    }

    static ServiceMetadata parseServiceMetadata(Context context, ServiceInfo serviceInfo) {
        Resources resources;
        PackageManager pm = context.getPackageManager();
        XmlResourceParser parser = serviceInfo.loadXmlMetaData(pm, QuickAccessWalletService.SERVICE_META_DATA);
        if (parser == null) {
            return ServiceMetadata.empty();
        }
        try {
            resources = pm.getResourcesForApplication(serviceInfo.applicationInfo);
            for (int type = 0; type != 1 && type != 2; type = parser.next()) {
            }
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            Log.e(TAG, "Error parsing quickaccesswallet service meta-data", e);
        }
        if (TAG_WALLET_SERVICE.equals(parser.getName())) {
            AttributeSet allAttributes = Xml.asAttributeSet(parser);
            TypedArray afsAttributes = null;
            try {
                afsAttributes = resources.obtainAttributes(allAttributes, R.styleable.QuickAccessWalletService);
                String targetActivity = afsAttributes.getString(0);
                String settingsActivity = afsAttributes.getString(1);
                CharSequence shortcutShortLabel = afsAttributes.getText(2);
                CharSequence shortcutLongLabel = afsAttributes.getText(3);
                return new ServiceMetadata(targetActivity, settingsActivity, shortcutShortLabel, shortcutLongLabel);
            } finally {
                if (afsAttributes != null) {
                    afsAttributes.recycle();
                }
            }
        }
        Log.e(TAG, "Meta-data does not start with quickaccesswallet-service tag");
        return ServiceMetadata.empty();
    }

    ComponentName getComponentName() {
        return this.mServiceInfo.getComponentName();
    }

    String getWalletActivity() {
        return this.mServiceMetadata.mTargetActivity;
    }

    String getSettingsActivity() {
        return this.mServiceMetadata.mSettingsActivity;
    }

    Drawable getWalletLogo(Context context) {
        Drawable drawable = this.mServiceInfo.loadLogo(context.getPackageManager());
        if (drawable != null) {
            return drawable;
        }
        return this.mServiceInfo.loadIcon(context.getPackageManager());
    }

    Drawable getTileIcon() {
        return this.mTileServiceMetadata.mTileIcon;
    }

    CharSequence getShortcutShortLabel(Context context) {
        if (!TextUtils.isEmpty(this.mServiceMetadata.mShortcutShortLabel)) {
            return this.mServiceMetadata.mShortcutShortLabel;
        }
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }

    CharSequence getShortcutLongLabel(Context context) {
        if (!TextUtils.isEmpty(this.mServiceMetadata.mShortcutLongLabel)) {
            return this.mServiceMetadata.mShortcutLongLabel;
        }
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }

    CharSequence getServiceLabel(Context context) {
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }
}
