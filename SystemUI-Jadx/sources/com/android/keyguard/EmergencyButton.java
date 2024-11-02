package com.android.keyguard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.systemui.LsRune;
import com.android.systemui.widget.SystemUIButton;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class EmergencyButton extends SystemUIButton {
    public int mDownX;
    public int mDownY;
    public final EmergencyAffordanceManager mEmergencyAffordanceManager;
    public boolean mIsSamsung321Enable;
    public boolean mLongPressWasDragged;
    public TelephonyManager mTelephonyManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Samsung321Task extends AsyncTask {
        public /* synthetic */ Samsung321Task(EmergencyButton emergencyButton, int i) {
            this();
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            return Boolean.valueOf(EmergencyButton.m53$$Nest$misSamsung321Enabled(EmergencyButton.this));
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            EmergencyButton.this.mIsSamsung321Enable = ((Boolean) obj).booleanValue();
        }

        private Samsung321Task() {
        }
    }

    /* renamed from: $r8$lambda$1WBOWOto-KWfPzJyOBffGIcOXMU, reason: not valid java name */
    public static boolean m52$r8$lambda$1WBOWOtoKWfPzJyOBffGIcOXMU(EmergencyButton emergencyButton) {
        emergencyButton.getClass();
        try {
            if (emergencyButton.mIsSamsung321Enable) {
                Intent intent = new Intent();
                intent.setAction("com.srph.emergency321.START");
                intent.setFlags(268468224);
                if (intent.resolveActivity(((Button) emergencyButton).mContext.getPackageManager()) != null) {
                    Log.d("EmergencyButton", "PH Feature, Launching Samsung Emergency 321");
                    ((Button) emergencyButton).mContext.startActivity(intent);
                    return true;
                }
                Log.d("EmergencyButton", "PH Feature, Samsung Emergency 321 not found");
            } else {
                Log.d("EmergencyButton", "PH Feature, Samsung Emergency 321 not enabled in settings");
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("PH Feature, Cannot launch activity : ", e, "EmergencyButton");
        }
        return false;
    }

    /* renamed from: -$$Nest$misSamsung321Enabled, reason: not valid java name */
    public static boolean m53$$Nest$misSamsung321Enabled(EmergencyButton emergencyButton) {
        Signature[] signatureArr;
        int i;
        emergencyButton.getClass();
        try {
            PackageManager packageManager = ((Button) emergencyButton).mContext.getPackageManager();
            if (packageManager == null || packageManager.getPackageInfo("com.srph.emergency321", 0) == null) {
                return false;
            }
            try {
                signatureArr = packageManager.getPackageInfo("com.srph.emergency321", 64).signatures;
            } catch (PackageManager.NameNotFoundException unused) {
                signatureArr = null;
            }
            if (signatureArr == null || signatureArr.length <= 0 || !SecurityUtils.matchSignature(signatureArr[0])) {
                return false;
            }
            Uri parse = Uri.parse("content://com.srph.emergency321.settings/settings/1");
            ContentResolver contentResolver = ((Button) emergencyButton).mContext.getContentResolver();
            boolean z = true;
            Cursor query = contentResolver.query(parse, new String[]{"_ID", "isEnabled"}, null, null, null);
            if (query == null) {
                return false;
            }
            try {
                int columnIndex = query.getColumnIndex("isEnabled");
                if (query.moveToFirst()) {
                    i = query.getInt(columnIndex);
                } else {
                    i = 0;
                }
                Log.d("EmergencyButton", "Samsung321 - Query successful");
                if (i == 0) {
                    z = false;
                }
                query.close();
                return z;
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            return false;
        }
    }

    public EmergencyButton(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.widget.SystemUIButton, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        if (LsRune.LOCKUI_BOTTOM_USIM_TEXT) {
            this.mTelephonyManager = (TelephonyManager) ((Button) this).mContext.getSystemService("phone");
        }
        final int i = 0;
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            setOnLongClickListener(new View.OnLongClickListener(this) { // from class: com.android.keyguard.EmergencyButton$$ExternalSyntheticLambda1
                public final /* synthetic */ EmergencyButton f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    switch (i) {
                        case 0:
                            EmergencyButton emergencyButton = this.f$0;
                            if (!emergencyButton.mLongPressWasDragged && emergencyButton.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
                                emergencyButton.mEmergencyAffordanceManager.performEmergencyCall();
                                return true;
                            }
                            return false;
                        default:
                            return EmergencyButton.m52$r8$lambda$1WBOWOtoKWfPzJyOBffGIcOXMU(this.f$0);
                    }
                }
            });
        }
        new Samsung321Task(this, i).execute(new Void[0]);
        final int i2 = 1;
        setOnLongClickListener(new View.OnLongClickListener(this) { // from class: com.android.keyguard.EmergencyButton$$ExternalSyntheticLambda1
            public final /* synthetic */ EmergencyButton f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                switch (i2) {
                    case 0:
                        EmergencyButton emergencyButton = this.f$0;
                        if (!emergencyButton.mLongPressWasDragged && emergencyButton.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
                            emergencyButton.mEmergencyAffordanceManager.performEmergencyCall();
                            return true;
                        }
                        return false;
                    default:
                        return EmergencyButton.m52$r8$lambda$1WBOWOtoKWfPzJyOBffGIcOXMU(this.f$0);
                }
            }
        });
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (LsRune.LOCKUI_BOTTOM_USIM_TEXT && getAlpha() == 0.0f) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getActionMasked() == 0) {
            this.mDownX = x;
            this.mDownY = y;
            this.mLongPressWasDragged = false;
        } else {
            int abs = Math.abs(x - this.mDownX);
            int abs2 = Math.abs(y - this.mDownY);
            int scaledTouchSlop = ViewConfiguration.get(((Button) this).mContext).getScaledTouchSlop();
            if (Math.abs(abs2) > scaledTouchSlop || Math.abs(abs) > scaledTouchSlop) {
                this.mLongPressWasDragged = true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean performLongClick() {
        return super.performLongClick();
    }

    public EmergencyButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsSamsung321Enable = false;
        ((Button) this).mContext.getResources().getBoolean(17891691);
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
    }
}
