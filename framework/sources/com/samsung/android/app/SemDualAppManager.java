package com.samsung.android.app;

import android.appwidget.AppWidgetHostView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.R;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.samsung.android.app.ISemDualAppManager;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class SemDualAppManager {
    private static final String ACTION3_PACKAGE_NAME;
    private static final String ADW_PACKAGE_NAME;
    private static final String[] AFW_CAPABLE_LAUNCHER_APPS;
    private static final String BLACKBERRYMESSENGER_PACKAGE_NAME;
    private static final String[] CHINA_SALES_CODES;
    public static final String DA_PROFILE_ID_PROPERTY_NAME = "sys.dualapp.profile_id";
    private static final String DCM_LIVEUX_PACKAGE_NAME;
    static final String[] DUAL_APP_WHITELIST_PACKAGES;
    static final String[] DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA;
    public static final String DUAL_CALLER_PACKAGE_NAME = "callerPackage";
    public static final String DUAL_ORI_SHORTCUT_COMPONENT = "dual_shortcut_component";
    private static final String FACEBOOKMESSENGER_PACKAGE_NAME;
    private static final String FACEBOOK_PACKAGE_NAME;
    private static final String GOOGLE_QUICKSEARCHBOX_PACKGE_NAME;
    private static final String HIKE_PACKAGE_NAME;
    private static final String HOLO_PACKAGE_NAME;
    private static final String ICQ_PACKAGE_NAME;
    private static final String KAKAOTALK_PACKAGE_NAME;
    private static final String KAKAOTALK_SETTINGS_THEME_URI = "kakaotalk://settings/theme/";
    private static final String KIK_PACKAGE_NAME;
    private static final String LINE_PACKAGE_NAME;
    public static final int MAX_DUALAPP_ID = 99;
    private static final String MICROSOFT_PACKAGE_NAME;
    public static final int MIN_DUALAPP_ID = 95;
    private static final String NOUGAT_PACKAGE_NAME;
    private static final String NOVA_PACKAGE_NAME;
    private static final String QQMOBILECHINA_PACKAGE_NAME;
    private static final String QQMOBILEINTERNATIONAL_PACKAGE_NAME;
    private static final String[] SAMSUNG_LAUNCHER_APPS;
    private static final String SEC_DESKTOP_LAUNCHER_PACKGE_NAME;
    private static final String SEC_EASY_LAUNCHER_PACKGE_NAME;
    private static final String SEC_EMERGENCY_LAUNCHER_PACKGE_NAME;
    private static final String SEC_LAUNCHER_PACKGE_NAME;
    private static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_DUAL_APP = true;
    private static final String SKYPE_PACKAGE_NAME;
    private static final String SMART3_PACKAGE_NAME;
    private static final String SNAPCHAT_PACKAGE_NAME;
    private static final String TAG = "SemDualAppManager";
    private static final String TELEGRAM_PACKAGE_NAME;
    private static final String VIBER_PACKAGE_NAME;
    private static final String WECHAT_PACKAGE_NAME;
    private static final String WEIBO_PACKAGE_NAME;
    private static final String WHATSAPP_PACKAGE_NAME;
    private static final String YAHOOMESSENGER_PACKAGE_NAME;
    private static final String YANDEX_PACKAGE_NAME;
    private static final String ZALO_PACKAGE_NAME;
    private static Context mContext;
    private static boolean mIsChinaModel;
    private static String mSalesCode;
    private Map<ComponentName, Integer> mDuplicateInitialIntents = new HashMap();
    private static SemDualAppManager sDAInstance = null;
    private static ISemDualAppManager mService = null;

    /* loaded from: classes5.dex */
    public interface DualAppVersion {
        public static final int DUAL_APP_VERSION_1_0_0 = 100;
        public static final int DUAL_APP_VERSION_1_1_0 = 110;
        public static final int DUAL_APP_VERSION_2_0_0 = 200;
        public static final int DUAL_APP_VERSION_3_0_0 = 300;
        public static final int DUAL_APP_VERSION_3_1_0 = 310;
        public static final int DUAL_APP_VERSION_3_2_0 = 320;
        public static final int DUAL_APP_VERSION_3_3_0 = 330;
        public static final int DUAL_APP_VERSION_3_4_0 = 340;
        public static final int DUAL_APP_VERSION_NONE = 0;
    }

    /* loaded from: classes5.dex */
    private interface SepVersionInt {
        public static final int SEP_VER_10_0_INT = 100000;
        public static final int SEP_VER_11_0_INT = 110000;
        public static final int SEP_VER_12_0_INT = 120000;
        public static final int SEP_VER_13_0_INT = 130000;
        public static final int SEP_VER_14_0_INT = 140000;
        public static final int SEP_VER_15_0_INT = 150000;
        public static final int SEP_VER_8_1_INT = 80100;
        public static final int SEP_VER_8_5_INT = 80500;
        public static final int SEP_VER_9_0_INT = 90000;
    }

    static {
        String decodeString = decodeString("Y29tLmZhY2Vib29rLmthdGFuYQ==");
        FACEBOOK_PACKAGE_NAME = decodeString;
        String decodeString2 = decodeString("Y29tLndoYXRzYXBw");
        WHATSAPP_PACKAGE_NAME = decodeString2;
        String decodeString3 = decodeString("Y29tLmZhY2Vib29rLm9yY2E=");
        FACEBOOKMESSENGER_PACKAGE_NAME = decodeString3;
        String decodeString4 = decodeString("Y29tLnRlbmNlbnQubW9iaWxlcXE=");
        QQMOBILECHINA_PACKAGE_NAME = decodeString4;
        String decodeString5 = decodeString("Y29tLnRlbmNlbnQubW9iaWxlcXFp");
        QQMOBILEINTERNATIONAL_PACKAGE_NAME = decodeString5;
        String decodeString6 = decodeString("Y29tLnRlbmNlbnQubW0=");
        WECHAT_PACKAGE_NAME = decodeString6;
        String decodeString7 = decodeString("Y29tLnNreXBlLnJhaWRlcg==");
        SKYPE_PACKAGE_NAME = decodeString7;
        String decodeString8 = decodeString("Y29tLnZpYmVyLnZvaXA=");
        VIBER_PACKAGE_NAME = decodeString8;
        String decodeString9 = decodeString("anAubmF2ZXIubGluZS5hbmRyb2lk");
        LINE_PACKAGE_NAME = decodeString9;
        String decodeString10 = decodeString("Y29tLmJibQ==");
        BLACKBERRYMESSENGER_PACKAGE_NAME = decodeString10;
        String decodeString11 = decodeString("b3JnLnRlbGVncmFtLm1lc3Nlbmdlcg==");
        TELEGRAM_PACKAGE_NAME = decodeString11;
        String decodeString12 = decodeString("Y29tLmtha2FvLnRhbGs=");
        KAKAOTALK_PACKAGE_NAME = decodeString12;
        String decodeString13 = decodeString("Y29tLmJzYi5oaWtl");
        HIKE_PACKAGE_NAME = decodeString13;
        String decodeString14 = decodeString("Y29tLmljcS5tb2JpbGUuY2xpZW50");
        ICQ_PACKAGE_NAME = decodeString14;
        String decodeString15 = decodeString("Y29tLnlhaG9vLm1vYmlsZS5jbGllbnQuYW5kcm9pZC5pbQ==");
        YAHOOMESSENGER_PACKAGE_NAME = decodeString15;
        String decodeString16 = decodeString("Y29tLnppbmcuemFsbw==");
        ZALO_PACKAGE_NAME = decodeString16;
        String decodeString17 = decodeString("Y29tLnNuYXBjaGF0LmFuZHJvaWQ=");
        SNAPCHAT_PACKAGE_NAME = decodeString17;
        String decodeString18 = decodeString("Y29tLnNpbmEud2VpYm8=");
        WEIBO_PACKAGE_NAME = decodeString18;
        String decodeString19 = decodeString("a2lrLmFuZHJvaWQ=");
        KIK_PACKAGE_NAME = decodeString19;
        String decodeString20 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5sYXVuY2hlcg==");
        SEC_LAUNCHER_PACKGE_NAME = decodeString20;
        String decodeString21 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5lYXN5bGF1bmNoZXI=");
        SEC_EASY_LAUNCHER_PACKGE_NAME = decodeString21;
        String decodeString22 = decodeString("Y29tLnNlYy5hbmRyb2lkLmVtZXJnZW5jeWxhdW5jaGVy");
        SEC_EMERGENCY_LAUNCHER_PACKGE_NAME = decodeString22;
        String decodeString23 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5kZXNrdG9wbGF1bmNoZXI=");
        SEC_DESKTOP_LAUNCHER_PACKGE_NAME = decodeString23;
        String decodeString24 = decodeString("Y29tLmdvb2dsZS5hbmRyb2lkLmdvb2dsZXF1aWNrc2VhcmNoYm94");
        GOOGLE_QUICKSEARCHBOX_PACKGE_NAME = decodeString24;
        String decodeString25 = decodeString("Y29tLnRlc2xhY29pbHN3LmxhdW5jaGVy");
        NOVA_PACKAGE_NAME = decodeString25;
        String decodeString26 = decodeString("Y29tLm1pY3Jvc29mdC5sYXVuY2hlcg==");
        MICROSOFT_PACKAGE_NAME = decodeString26;
        String decodeString27 = decodeString("b3JnLmFkdy5sYXVuY2hlcg==");
        ADW_PACKAGE_NAME = decodeString27;
        String decodeString28 = decodeString("Y29tLmFjdGlvbmxhdW5jaGVyLnBsYXlzdG9yZQ==");
        ACTION3_PACKAGE_NAME = decodeString28;
        String decodeString29 = decodeString("Y29tLm1vYmludC5ob2xvbGF1bmNoZXI=");
        HOLO_PACKAGE_NAME = decodeString29;
        String decodeString30 = decodeString("Z2lubGVtb24uZmxvd2VyZnJlZQ==");
        SMART3_PACKAGE_NAME = decodeString30;
        String decodeString31 = decodeString("Y29tLmNtbmxhdW5jaGVy");
        NOUGAT_PACKAGE_NAME = decodeString31;
        String decodeString32 = decodeString("Y29tLnlhbmRleC5sYXVuY2hlcg==");
        YANDEX_PACKAGE_NAME = decodeString32;
        String decodeString33 = decodeString("Y29tLm50dGRvY29tby5hbmRyb2lkLmRob21l");
        DCM_LIVEUX_PACKAGE_NAME = decodeString33;
        mSalesCode = SemSystemProperties.getSalesCode();
        CHINA_SALES_CODES = new String[]{"CHN", "CHM", "CBK", "CTC", "CHU", "CHC"};
        mIsChinaModel = isChinaModel();
        DUAL_APP_WHITELIST_PACKAGES = new String[]{decodeString, decodeString2, decodeString3, decodeString4, decodeString5, decodeString6, decodeString18, decodeString7, decodeString8, decodeString9, decodeString10, decodeString11, decodeString12, decodeString13, decodeString14, decodeString15, decodeString16, decodeString17, decodeString19};
        DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA = new String[]{decodeString6, decodeString4, decodeString18};
        AFW_CAPABLE_LAUNCHER_APPS = new String[]{decodeString20, decodeString21, decodeString22, decodeString23, decodeString24, decodeString25, decodeString28, decodeString29, decodeString30, decodeString31, decodeString32, decodeString33, decodeString26, decodeString27};
        SAMSUNG_LAUNCHER_APPS = new String[]{decodeString20, decodeString21, decodeString22, decodeString23};
    }

    private SemDualAppManager() {
    }

    private static ISemDualAppManager getDualAppService() {
        if (mService == null) {
            mService = ISemDualAppManager.Stub.asInterface(ServiceManager.getService("dual_app"));
        }
        return mService;
    }

    public static SemDualAppManager getInstance(Context context) {
        if (sDAInstance == null) {
            synchronized (SemDualAppManager.class) {
                if (sDAInstance == null) {
                    sDAInstance = new SemDualAppManager();
                    mContext = context;
                }
            }
        }
        return sDAInstance;
    }

    public boolean isWhitelistedPackage(String pkgName) {
        String[] apps;
        int currentUserId = UserHandle.myUserId();
        if ((currentUserId == 0 || isDualAppIdInternal(currentUserId)) && pkgName != null && !"".equalsIgnoreCase(pkgName) && (apps = getAllWhitelistedPackages()) != null) {
            for (String pkg : apps) {
                if (pkg.equals(pkgName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSupported() {
        int currentUserId = UserHandle.myUserId();
        if (currentUserId == 0 || isDualAppIdInternal(currentUserId)) {
            return true;
        }
        return false;
    }

    public static boolean isDualAppId(int userId) {
        return userId >= 95 && userId <= 99;
    }

    public static String[] getAllWhitelistedPackages() {
        String[] apps = null;
        ISemDualAppManager sdam = getDualAppService();
        if (sdam != null) {
            try {
                apps = sdam.getAllWhitelistedPackages();
            } catch (RemoteException e) {
                Log.e(TAG, "getAllWhitelistedPackages : RemoteException occured");
            }
        }
        if (apps == null) {
            Log.e(TAG, "getAllWhitelistedPackages : null returned. Return default");
            if (mIsChinaModel) {
                return DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA;
            }
            return DUAL_APP_WHITELIST_PACKAGES;
        }
        return apps;
    }

    public static int getDualAppProfileId() {
        String value = SystemProperties.get(DA_PROFILE_ID_PROPERTY_NAME, null);
        if (value == null) {
            return -10000;
        }
        try {
            if (value.isEmpty()) {
                return -10000;
            }
            int id = Integer.valueOf(value).intValue();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return -10000;
        }
    }

    public static List<String> getAllInstalledWhitelistedPackages() {
        ISemDualAppManager sdam = getDualAppService();
        if (sdam != null) {
            try {
                return sdam.getAllInstalledWhitelistedPackages();
            } catch (RemoteException e) {
                Log.e(TAG, "getAllInstalledWhitelistedPackages : RemoteException occured");
            }
        }
        Log.e(TAG, "getAllInstalledWhitelistedPackages : Can not connect to DualAppManagerService");
        return null;
    }

    public static boolean isInstalledWhitelistedPackage(String pkgName) {
        int currentUserId = UserHandle.myUserId();
        if (currentUserId != 0 && !isDualAppIdInternal(currentUserId)) {
            return false;
        }
        ISemDualAppManager sdam = getDualAppService();
        if (sdam != null) {
            try {
                return sdam.isInstalledWhitelistedPackage(pkgName);
            } catch (RemoteException e) {
                Log.e(TAG, "isInstalledWhitelistedPackage : RemoteException occured");
            }
        }
        Log.e(TAG, "isInstalledWhitelistedPackage : Can not connect to DualAppManagerService");
        return false;
    }

    public static Bundle updateDualAppData(Context ctx, int userId, Bundle bundle) {
        ISemDualAppManager sdam = getDualAppService();
        if (sdam != null) {
            try {
                String pkgName = ctx.getPackageName();
                return sdam.updateDualAppData(pkgName, userId, bundle);
            } catch (RemoteException e) {
                Log.e(TAG, "updateDualAppData : RemoteException occured");
            }
        }
        Log.e(TAG, "updateDualAppData : Can not connect to DualAppManagerService");
        return null;
    }

    private static boolean isDualAppIdInternal(int userId) {
        return userId >= 95 && userId <= 99;
    }

    public static boolean shouldAddUserId(Uri uri, int userId) {
        if (uri == null) {
            return false;
        }
        String uriScheme = uri.getScheme();
        String uriAuthority = uri.getAuthority();
        if (!"content".equals(uriScheme) || "com.android.contacts".equals(uriAuthority) || "com.android.calendar".equals(uriAuthority) || "com.android.providers.downloads.documents".equals(uriAuthority)) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00f8 A[Catch: Exception -> 0x0139, TryCatch #4 {Exception -> 0x0139, blocks: (B:3:0x0004, B:7:0x0010, B:10:0x0027, B:12:0x0044, B:14:0x004b, B:18:0x0057, B:26:0x006b, B:28:0x006f, B:31:0x0076, B:33:0x0088, B:36:0x0091, B:38:0x00f8, B:39:0x0103, B:52:0x00bf, B:50:0x00dd, B:53:0x009b), top: B:2:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addDualAppAccounts(android.widget.LinearLayout r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.SemDualAppManager.addDualAppAccounts(android.widget.LinearLayout, int, int):void");
    }

    public static int getDualAppVersion() {
        return 340;
    }

    public static boolean isDualAppVersionSupported(int dualAppVer) {
        int currentVersion = getDualAppVersion();
        if (currentVersion >= dualAppVer) {
            return true;
        }
        return false;
    }

    public boolean isNeedAddResolveInfoForOtherUser(ActivityInfo ai, Intent ii) {
        int appUserId;
        if (ai == null || (((appUserId = UserHandle.getUserId(ai.applicationInfo.uid)) != 0 && !isDualAppIdInternal(appUserId)) || ((ii instanceof LabeledIntent) && !isInstalledWhitelistedPackage(ai.packageName)))) {
            return false;
        }
        if (this.mDuplicateInitialIntents.containsKey(ai.getComponentName())) {
            Log.w(TAG, "Duplicate activity found for " + ii);
            return false;
        }
        if (isInstalledWhitelistedPackage(ai.packageName) && (Intent.ACTION_SEND.equals(ii.getAction()) || Intent.ACTION_SEND_MULTIPLE.equals(ii.getAction()) || ((ii.getComponent() != null && isChooserRequired(ii.getComponent().getClassName())) || isChinaDualApp(ai.packageName) || ((ii.getData() != null && "mqqapi".equals(ii.getData().getScheme())) || isKakaoThemeIntent(ai.packageName, ii))))) {
            this.mDuplicateInitialIntents.put(ai.getComponentName(), Integer.valueOf(appUserId));
            return true;
        }
        return false;
    }

    public boolean isDuplicateEntry(PackageManager pm, List<DisplayResolveInfo> target, ActivityInfo ai, Intent ii) {
        int appUserId;
        if (ai == null || (((appUserId = UserHandle.getUserId(ai.applicationInfo.uid)) != 0 && !isDualAppIdInternal(appUserId)) || ((ii instanceof LabeledIntent) && !isInstalledWhitelistedPackage(ai.packageName)))) {
            return false;
        }
        if (this.mDuplicateInitialIntents.containsKey(ai.getComponentName())) {
            return true;
        }
        if (isInstalledWhitelistedPackage(ai.packageName) && (Intent.ACTION_SEND.equals(ii.getAction()) || Intent.ACTION_SEND_MULTIPLE.equals(ii.getAction()) || ((ii.getComponent() != null && isChooserRequired(ii.getComponent().getClassName())) || isChinaDualApp(ai.packageName) || ((ii.getData() != null && "mqqapi".equals(ii.getData().getScheme())) || isKakaoThemeIntent(ai.packageName, ii))))) {
            this.mDuplicateInitialIntents.put(ai.getComponentName(), Integer.valueOf(appUserId));
            addResolveInfoFromOtherUser(pm, target, ai, ii);
        }
        return false;
    }

    public void clearDuplicateMaps() {
        this.mDuplicateInitialIntents.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void addResolveInfoFromOtherUser(android.content.pm.PackageManager r17, java.util.List<com.android.internal.app.chooser.DisplayResolveInfo> r18, android.content.pm.ActivityInfo r19, android.content.Intent r20) {
        /*
            r16 = this;
            r1 = r19
            r8 = r20
            int r9 = getDualAppProfileId()
            java.lang.String r0 = "package"
            android.os.IBinder r0 = android.os.ServiceManager.getService(r0)
            android.content.pm.IPackageManager r10 = android.content.pm.IPackageManager.Stub.asInterface(r0)
            if (r1 == 0) goto L92
            boolean r0 = isDualAppId(r9)
            if (r0 != 0) goto L21
            r15 = r17
            r2 = r18
            goto L96
        L21:
            r0 = 0
            android.content.pm.ApplicationInfo r2 = r1.applicationInfo
            int r2 = r2.uid
            int r2 = android.os.UserHandle.getUserId(r2)
            if (r2 != 0) goto L2f
            r0 = r9
            r11 = r0
            goto L30
        L2f:
            r11 = r0
        L30:
            r2 = 0
            android.content.ComponentName r12 = r19.getComponentName()
            if (r12 == 0) goto L4d
            r3 = 0
            android.content.pm.ActivityInfo r0 = r10.getActivityInfo(r12, r3, r11)     // Catch: android.os.RemoteException -> L49
            r1 = r0
            android.content.pm.ResolveInfo r0 = new android.content.pm.ResolveInfo     // Catch: android.os.RemoteException -> L47
            r0.<init>()     // Catch: android.os.RemoteException -> L47
            r2 = r0
            r2.activityInfo = r1     // Catch: android.os.RemoteException -> L47
            goto L4d
        L47:
            r0 = move-exception
            goto L4a
        L49:
            r0 = move-exception
        L4a:
            r0 = r1
            r13 = r2
            goto L4f
        L4d:
            r0 = r1
            r13 = r2
        L4f:
            boolean r1 = r8 instanceof android.content.pm.LabeledIntent
            if (r1 == 0) goto L72
            r1 = r8
            android.content.pm.LabeledIntent r1 = (android.content.pm.LabeledIntent) r1
            java.lang.String r2 = r1.getSourcePackage()
            r13.resolvePackageName = r2
            int r2 = r1.getLabelResource()
            r13.labelRes = r2
            java.lang.CharSequence r2 = r1.getNonLocalizedLabel()
            r13.nonLocalizedLabel = r2
            int r2 = r1.getIconResource()
            r13.icon = r2
            int r2 = r13.icon
            r13.iconResourceId = r2
        L72:
            if (r0 == 0) goto L8d
            com.android.internal.app.chooser.DisplayResolveInfo r14 = new com.android.internal.app.chooser.DisplayResolveInfo
            r15 = r17
            java.lang.CharSequence r4 = r13.loadLabel(r15)
            r5 = 0
            r7 = 0
            r1 = r14
            r2 = r20
            r3 = r13
            r6 = r20
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r2 = r18
            r2.add(r14)
            goto L91
        L8d:
            r15 = r17
            r2 = r18
        L91:
            return
        L92:
            r15 = r17
            r2 = r18
        L96:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.SemDualAppManager.addResolveInfoFromOtherUser(android.content.pm.PackageManager, java.util.List, android.content.pm.ActivityInfo, android.content.Intent):void");
    }

    public static void drawDualAppBadge(final Context context, final AppWidgetHostView view, UserHandle user) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.samsung.android.app.SemDualAppManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ImageView dualAppBadge = new ImageView(Context.this);
                    int density = Context.this.getResources().getDisplayMetrics().densityDpi;
                    Drawable badgeicon = Resources.getSystem().getDrawableForDensity(R.drawable.ic_dualapp_widget_badge, density);
                    if (badgeicon != null) {
                        dualAppBadge.setImageDrawable(badgeicon);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(badgeicon.getIntrinsicWidth(), badgeicon.getIntrinsicHeight());
                        params.gravity = 85;
                        view.addView(dualAppBadge, params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000L);
    }

    public static boolean isAfwSupportLauncher(String launcherPkgName) {
        if (launcherPkgName != null) {
            for (String pkg : AFW_CAPABLE_LAUNCHER_APPS) {
                if (pkg.equals(launcherPkgName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSamsungLauncher(String launcherPkgName) {
        if (launcherPkgName != null) {
            for (String pkg : SAMSUNG_LAUNCHER_APPS) {
                if (pkg.equals(launcherPkgName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isChooserRequired(String clsName) {
        if ("com.tencent.mm.plugin.base.stub.WXEntryActivity".equals(clsName) || "com.tencent.open.agent.AgentActivity".equals(clsName) || "com.tencent.mm.plugin.base.stub.WXPayEntryActivity".equals(clsName) || "com.sina.weibo.SSOActivity".equals(clsName)) {
            return true;
        }
        return false;
    }

    public static boolean isChinaDualApp(String packageName) {
        if (QQMOBILECHINA_PACKAGE_NAME.equals(packageName) || QQMOBILEINTERNATIONAL_PACKAGE_NAME.equals(packageName) || WEIBO_PACKAGE_NAME.equals(packageName) || WECHAT_PACKAGE_NAME.equals(packageName)) {
            return true;
        }
        return false;
    }

    private static boolean isKakaoThemeIntent(String packageName, Intent intent) {
        if (KAKAOTALK_PACKAGE_NAME.equals(packageName) && intent.getDataString() != null && intent.getDataString().contains(KAKAOTALK_SETTINGS_THEME_URI)) {
            return true;
        }
        return false;
    }

    public static boolean isChinaModel() {
        if (mSalesCode != null) {
            for (String code : CHINA_SALES_CODES) {
                if (code.equals(mSalesCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String decodeString(String in) {
        byte[] out = Base64.decode(in, 0);
        return new String(out, StandardCharsets.UTF_8);
    }

    public static boolean shouldRemove(ResolveInfo resolveInfo) {
        if (isDualAppId(resolveInfo.userHandle.getIdentifier())) {
            if (resolveInfo.activityInfo.packageName.equals("com.android.settings") || resolveInfo.activityInfo.packageName.equals("com.android.chrome")) {
                return true;
            }
            return false;
        }
        return false;
    }
}