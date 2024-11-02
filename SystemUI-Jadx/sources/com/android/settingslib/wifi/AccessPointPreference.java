package com.android.settingslib.wifi;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.wifi.WifiConfiguration;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class AccessPointPreference extends Preference {
    public final AccessPoint mAccessPoint;
    public Drawable mBadge;
    public final UserBadgeCache mBadgeCache;
    public final int mBadgePadding;
    public CharSequence mContentDescription;
    public final int mDefaultIconResId;
    public final boolean mForSavedNetworks;
    public final StateListDrawable mFrictionSld;
    public final IconInjector mIconInjector;
    public int mLevel;
    public final AnonymousClass1 mNotifyChanged;
    public TextView mTitleView;
    public int mWifiSpeed;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_METERED = {R.attr.state_metered};
    public static final int[] FRICTION_ATTRS = {R.attr.wifi_friction};
    public static final int[] WIFI_CONNECTION_STRENGTH = {R.string.accessibility_no_wifi, R.string.accessibility_wifi_one_bar, R.string.accessibility_wifi_two_bars, R.string.accessibility_wifi_three_bars, R.string.accessibility_wifi_signal_full};

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class IconInjector {
        public final Context mContext;

        public IconInjector(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class UserBadgeCache {
        public final SparseArray mBadges = new SparseArray();
        public final PackageManager mPm;

        public UserBadgeCache(PackageManager packageManager) {
            this.mPm = packageManager;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AccessPointPreference(com.android.settingslib.wifi.AccessPoint r11, android.content.Context r12, com.android.settingslib.wifi.AccessPointPreference.UserBadgeCache r13, int r14, boolean r15) {
        /*
            r10 = this;
            r0 = 0
            android.content.res.Resources$Theme r1 = r12.getTheme()     // Catch: android.content.res.Resources.NotFoundException -> Lc
            int[] r2 = com.android.settingslib.wifi.AccessPointPreference.FRICTION_ATTRS     // Catch: android.content.res.Resources.NotFoundException -> Lc
            android.content.res.TypedArray r1 = r1.obtainStyledAttributes(r2)     // Catch: android.content.res.Resources.NotFoundException -> Lc
            goto Ld
        Lc:
            r1 = r0
        Ld:
            if (r1 == 0) goto L16
            r0 = 0
            android.graphics.drawable.Drawable r0 = r1.getDrawable(r0)
            android.graphics.drawable.StateListDrawable r0 = (android.graphics.drawable.StateListDrawable) r0
        L16:
            r7 = r0
            r8 = -1
            com.android.settingslib.wifi.AccessPointPreference$IconInjector r9 = new com.android.settingslib.wifi.AccessPointPreference$IconInjector
            r9.<init>(r12)
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.wifi.AccessPointPreference.<init>(com.android.settingslib.wifi.AccessPoint, android.content.Context, com.android.settingslib.wifi.AccessPointPreference$UserBadgeCache, int, boolean):void");
    }

    public static CharSequence buildContentDescription(Context context, Preference preference, AccessPoint accessPoint) {
        String string;
        CharSequence title = preference.getTitle();
        CharSequence summary = preference.getSummary();
        if (!TextUtils.isEmpty(summary)) {
            title = TextUtils.concat(title, ",", summary);
        }
        int level = accessPoint.getLevel();
        if (level >= 0 && level < 5) {
            title = TextUtils.concat(title, ",", context.getString(WIFI_CONNECTION_STRENGTH[level]));
        }
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = title;
        charSequenceArr[1] = ",";
        if (accessPoint.security == 0) {
            string = context.getString(R.string.accessibility_wifi_security_type_none);
        } else {
            string = context.getString(R.string.accessibility_wifi_security_type_secured);
        }
        charSequenceArr[2] = string;
        return TextUtils.concat(charSequenceArr);
    }

    public static void setTitle(AccessPointPreference accessPointPreference, AccessPoint accessPoint) {
        accessPointPreference.setTitle(accessPoint.getTitle());
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.post(this.mNotifyChanged);
                return;
            }
            return;
        }
        super.notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        StateListDrawable stateListDrawable;
        boolean z;
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mAccessPoint == null) {
            return;
        }
        Drawable icon = getIcon();
        if (icon != null) {
            icon.setLevel(this.mLevel);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        this.mTitleView = textView;
        if (textView != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mBadge, (Drawable) null);
            this.mTitleView.setCompoundDrawablePadding(this.mBadgePadding);
        }
        preferenceViewHolder.itemView.setContentDescription(this.mContentDescription);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.friction_icon);
        if (imageView != null && (stateListDrawable = this.mFrictionSld) != null) {
            AccessPoint accessPoint = this.mAccessPoint;
            int i = accessPoint.security;
            if (i != 0 && i != 4) {
                stateListDrawable.setState(STATE_SECURED);
            } else {
                if (!accessPoint.mIsScoredNetworkMetered && !WifiConfiguration.isMetered(accessPoint.mConfig, accessPoint.mInfo)) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    this.mFrictionSld.setState(STATE_METERED);
                }
            }
            imageView.setImageDrawable(this.mFrictionSld.getCurrent());
        }
        preferenceViewHolder.findViewById(R.id.two_target_divider).setVisibility(4);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.wifi.AccessPointPreference$1] */
    public AccessPointPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mForSavedNetworks = false;
        this.mWifiSpeed = 0;
        this.mNotifyChanged = new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
            @Override // java.lang.Runnable
            public final void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        this.mFrictionSld = null;
        this.mBadgePadding = 0;
        this.mBadgeCache = null;
        this.mIconInjector = new IconInjector(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x055f, code lost:
    
        if ((r9 != 0) == false) goto L246;
     */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0317 A[Catch: Exception -> 0x0354, TryCatch #2 {Exception -> 0x0354, blocks: (B:158:0x0305, B:160:0x0309, B:162:0x0313, B:164:0x0317, B:165:0x0324, B:167:0x0332), top: B:157:0x0305 }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0332 A[Catch: Exception -> 0x0354, TRY_LEAVE, TryCatch #2 {Exception -> 0x0354, blocks: (B:158:0x0305, B:160:0x0309, B:162:0x0313, B:164:0x0317, B:165:0x0324, B:167:0x0332), top: B:157:0x0305 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AccessPointPreference(com.android.settingslib.wifi.AccessPoint r19, android.content.Context r20, com.android.settingslib.wifi.AccessPointPreference.UserBadgeCache r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 1503
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.wifi.AccessPointPreference.<init>(com.android.settingslib.wifi.AccessPoint, android.content.Context, com.android.settingslib.wifi.AccessPointPreference$UserBadgeCache, boolean):void");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settingslib.wifi.AccessPointPreference$1] */
    public AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, int i, boolean z, StateListDrawable stateListDrawable, int i2, IconInjector iconInjector) {
        super(context);
        this.mForSavedNetworks = false;
        this.mWifiSpeed = 0;
        this.mNotifyChanged = new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
            @Override // java.lang.Runnable
            public final void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        this.mLayoutResId = R.layout.preference_access_point;
        this.mWidgetLayoutResId = R.layout.access_point_friction_widget;
        this.mBadgeCache = userBadgeCache;
        this.mAccessPoint = accessPoint;
        this.mForSavedNetworks = z;
        accessPoint.getClass();
        this.mLevel = i2;
        this.mDefaultIconResId = i;
        this.mFrictionSld = stateListDrawable;
        this.mIconInjector = iconInjector;
        this.mBadgePadding = context.getResources().getDimensionPixelSize(R.dimen.wifi_preference_badge_padding);
    }
}
