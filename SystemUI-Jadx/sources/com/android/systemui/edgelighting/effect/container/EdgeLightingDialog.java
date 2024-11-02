package com.android.systemui.edgelighting.effect.container;

import android.R;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Slog;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback;
import com.android.systemui.edgelighting.effect.view.MorphView;
import com.android.systemui.edgelighting.plus.NotificationELPlusEffect;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.util.SemLog;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class EdgeLightingDialog extends Dialog implements IEdgeLightingController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AODShowState mAODShowState;
    public ApplicationEffect mApplicationEffect;
    public RelativeLayout mDialogMain;
    public boolean mDozeDraw;
    public final AnonymousClass4 mEdgeAnimationListener;
    public final AnonymousClass1 mHandler;
    public boolean mIsUsingELPlusEffect;
    public NotificationEffect mLightingPreview;
    public NotificationEffect mNotificationEffect;
    public Bundle mPlusEffectBundle;
    public boolean mTransparent;
    public boolean mUsingBlackBG;
    public IEdgeLightingWindowCallback mWindowCallback;
    public final int mWindowType;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.edgelighting.effect.container.EdgeLightingDialog$4, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass4 {
        public AnonymousClass4() {
        }

        public final void onFinishAnimation() {
            EdgeLightingDialog edgeLightingDialog = EdgeLightingDialog.this;
            ApplicationEffect applicationEffect = edgeLightingDialog.mApplicationEffect;
            if (applicationEffect == null || (applicationEffect != null && !applicationEffect.isShown())) {
                NotificationEffect notificationEffect = edgeLightingDialog.mNotificationEffect;
                if (notificationEffect == null || (notificationEffect != null && !notificationEffect.isShown())) {
                    EdgeLightingDialog.m1243$$Nest$mselfDismissWindow(edgeLightingDialog);
                }
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class AODShowState extends ContentObserver {
        public AODShowState(Handler handler) {
            super(handler);
        }

        public final boolean isEnable() {
            if (Settings.System.getInt(EdgeLightingDialog.this.getContext().getContentResolver(), "aod_show_state", 0) != 1) {
                return false;
            }
            return true;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (!EdgeLightingDialog.this.mTransparent && isEnable()) {
                EdgeLightingDialog edgeLightingDialog = EdgeLightingDialog.this;
                edgeLightingDialog.updateBackground(edgeLightingDialog.getWindow());
            }
            if (EdgeLightingDialog.this.mNotificationEffect != null && !isEnable()) {
                NotificationEffect notificationEffect = EdgeLightingDialog.this.mNotificationEffect;
                MorphView morphView = notificationEffect.mMorphView;
                if (morphView != null) {
                    morphView.updateMargin(morphView.getRootWindowInsets());
                    notificationEffect.addTouchInsector();
                }
                NotificationEffect notificationEffect2 = EdgeLightingDialog.this.mNotificationEffect;
                if (notificationEffect2 != null && notificationEffect2.isTouchable()) {
                    int i = EdgeLightingDialog.$r8$clinit;
                    Slog.d("EdgeLightingDialog", " AOD_SHOW_STATE changed " + isEnable());
                    EdgeLightingDialog.this.getWindow().clearFlags(16);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mdismissInternal, reason: not valid java name */
    public static void m1242$$Nest$mdismissInternal(EdgeLightingDialog edgeLightingDialog) {
        if (edgeLightingDialog.mAODShowState != null) {
            edgeLightingDialog.getContext().getContentResolver().unregisterContentObserver(edgeLightingDialog.mAODShowState);
            edgeLightingDialog.mAODShowState = null;
        }
        if (edgeLightingDialog.getWindow() != null) {
            Slog.i("EdgeLightingDialog", "dismissInternal ");
            NotificationEffect notificationEffect = edgeLightingDialog.mNotificationEffect;
            if (notificationEffect != null) {
                edgeLightingDialog.mDialogMain.removeView(notificationEffect);
                edgeLightingDialog.mNotificationEffect = null;
            }
            ApplicationEffect applicationEffect = edgeLightingDialog.mApplicationEffect;
            if (applicationEffect != null) {
                edgeLightingDialog.mDialogMain.removeView(applicationEffect);
                edgeLightingDialog.mApplicationEffect = null;
            }
            NotificationEffect notificationEffect2 = edgeLightingDialog.mLightingPreview;
            if (notificationEffect2 != null) {
                edgeLightingDialog.mDialogMain.removeView(notificationEffect2);
                edgeLightingDialog.mLightingPreview = null;
            }
            edgeLightingDialog.dismiss();
            System.gc();
        }
    }

    /* renamed from: -$$Nest$mselfDismissWindow, reason: not valid java name */
    public static void m1243$$Nest$mselfDismissWindow(EdgeLightingDialog edgeLightingDialog) {
        IEdgeLightingWindowCallback iEdgeLightingWindowCallback = edgeLightingDialog.mWindowCallback;
        if (iEdgeLightingWindowCallback != null) {
            iEdgeLightingWindowCallback.onDismissEdgeWindow();
        }
        Window window = edgeLightingDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (edgeLightingDialog.mUsingBlackBG) {
                attributes.screenBrightness = -1.0f;
            }
            window.setAttributes(attributes);
        }
        edgeLightingDialog.mHandler.removeMessages(1);
        edgeLightingDialog.mHandler.sendEmptyMessageDelayed(1, 500L);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.edgelighting.effect.container.EdgeLightingDialog$1] */
    public EdgeLightingDialog(Context context) {
        super(context, R.style.Theme.Translucent.NoTitleBar.Fullscreen);
        this.mUsingBlackBG = false;
        this.mTransparent = false;
        this.mDozeDraw = false;
        this.mWindowType = 2228;
        this.mIsUsingELPlusEffect = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.edgelighting.effect.container.EdgeLightingDialog.1
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                if (message.what == 1) {
                    EdgeLightingDialog.m1242$$Nest$mdismissInternal(EdgeLightingDialog.this);
                }
            }
        };
        this.mEdgeAnimationListener = new AnonymousClass4();
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final boolean isUsingELPlusEffect() {
        return this.mIsUsingELPlusEffect;
    }

    public final NotificationEffect makeEffectType(EdgeEffectInfo edgeEffectInfo, boolean z) {
        NotificationEffect notificationEffect;
        switch (edgeEffectInfo.mEffectType) {
            case 1:
                notificationEffect = new NotificationNormalEffect(getContext());
                break;
            case 2:
                notificationEffect = new NotificationLineGradationEffect(getContext(), 2);
                break;
            case 3:
                notificationEffect = new NotificationGlowEffect(getContext());
                break;
            case 4:
                notificationEffect = new NotificationReflectEffect(getContext());
                break;
            case 5:
                notificationEffect = new NotificationWaveEffect(getContext());
                break;
            case 6:
                notificationEffect = new NotificationBubbleEffect(getContext());
                break;
            case 7:
                notificationEffect = new NotificationHeartEffect(getContext());
                break;
            case 8:
                notificationEffect = new NotificationFireworksEffect(getContext());
                break;
            case 9:
                notificationEffect = new NotificationEclipseEffect(getContext());
                break;
            case 10:
                notificationEffect = new NotificationEchoEffect(getContext());
                break;
            case 11:
                notificationEffect = new NotificationSpotlightEffect(getContext());
                break;
            default:
                NotificationNormalEffect notificationNormalEffect = new NotificationNormalEffect(getContext());
                notificationNormalEffect.mLightEffectView.mIsNoFrame = true;
                notificationEffect = notificationNormalEffect;
                break;
        }
        NotificationEffect notificationEffect2 = notificationEffect;
        if (edgeEffectInfo.mEffectType == 100) {
            Bundle bundle = edgeEffectInfo.mPlusEffectBundle;
            this.mPlusEffectBundle = bundle;
            boolean z2 = bundle.getBoolean("isUsingCustomEffect", false);
            this.mIsUsingELPlusEffect = z2;
            NotificationEffect notificationEffect3 = notificationEffect;
            notificationEffect2 = notificationEffect;
            if (z) {
                if (z2) {
                    NotificationELPlusEffect notificationELPlusEffect = new NotificationELPlusEffect(getContext(), this.mPlusEffectBundle, edgeEffectInfo);
                    notificationELPlusEffect.mIsUsedAppIconForEdgeLightingPlus = this.mPlusEffectBundle.getBoolean("isUsedAppIcon", false);
                    notificationEffect3 = notificationELPlusEffect;
                }
                notificationEffect3.mIsHideBriefPopupForEdgeLightingPlus = this.mPlusEffectBundle.getBoolean("isHideBriefPopup", false);
                notificationEffect3.mIsSingleTapDisabledForEdgeLightingPlus = this.mPlusEffectBundle.getBoolean("isDisableSingleTap", false);
                notificationEffect3.mIsSwipeDownDisabledForEdgeLightingPlus = this.mPlusEffectBundle.getBoolean("isDisableSwipeDown", false);
                notificationEffect2 = notificationEffect3;
            }
        }
        notificationEffect2.setIsMultiResolutionSupoorted(edgeEffectInfo.mIsMultiResolutionSupoorted);
        return notificationEffect2;
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationEffect notificationEffect = this.mNotificationEffect;
        if (notificationEffect != null && notificationEffect.isTouchable()) {
            Slog.i("EdgeLightingDialog", " onAttached Window clear FLAG NOT TOUCHABLE");
            getWindow().clearFlags(16);
        }
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(com.android.systemui.R.layout.edge_light_main);
        Window window = getWindow();
        window.setType(this.mWindowType);
        window.setLayout(-1, -1);
        window.addFlags(16778496);
        window.clearFlags(65536);
        window.addFlags(24);
        window.getDecorView().setSystemUiVisibility(1536);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.windowAnimations = 0;
        attributes.semAddExtensionFlags(131072);
        attributes.layoutInDisplayCutoutMode = 3;
        attributes.setTitle("EdgeLightingWindow");
        this.mDialogMain = (RelativeLayout) findViewById(com.android.systemui.R.id.dialog_main);
        if (Build.VERSION.SEM_PLATFORM_INT >= 100000) {
            getWindow().getDecorView().semSetRoundedCorners(0);
        }
        getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(this) { // from class: com.android.systemui.edgelighting.effect.container.EdgeLightingDialog.2
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return new WindowInsets.Builder(windowInsets).setStableInsets(Insets.of(windowInsets.getStableInsetLeft(), 0, windowInsets.getStableInsetRight(), windowInsets.getStableInsetBottom())).build();
            }
        });
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void registerEdgeWindowCallback(IEdgeLightingWindowCallback iEdgeLightingWindowCallback) {
        if (iEdgeLightingWindowCallback != null) {
            this.mWindowCallback = iEdgeLightingWindowCallback;
        }
    }

    @Override // android.app.Dialog
    public final void show() {
        if (this.mAODShowState == null) {
            this.mAODShowState = new AODShowState(new Handler());
            ContentResolver contentResolver = getContext().getContentResolver();
            this.mAODShowState.getClass();
            contentResolver.registerContentObserver(Settings.System.getUriFor("aod_show_state"), false, this.mAODShowState);
        }
        updateBackground(getWindow());
        super.show();
        IEdgeLightingWindowCallback iEdgeLightingWindowCallback = this.mWindowCallback;
        if (iEdgeLightingWindowCallback != null) {
            iEdgeLightingWindowCallback.onShowEdgeWindow();
        }
        removeMessages(1);
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void showPreview(EdgeEffectInfo edgeEffectInfo, boolean z) {
        this.mUsingBlackBG = false;
        show();
        if (this.mDialogMain == null) {
            this.mDialogMain = (RelativeLayout) findViewById(com.android.systemui.R.id.dialog_main);
        }
        RelativeLayout relativeLayout = this.mDialogMain;
        if (relativeLayout != null) {
            NotificationEffect notificationEffect = this.mLightingPreview;
            if (notificationEffect != null) {
                relativeLayout.removeView(notificationEffect);
                this.mLightingPreview = null;
            }
            NotificationEffect makeEffectType = makeEffectType(edgeEffectInfo, false);
            this.mLightingPreview = makeEffectType;
            this.mDialogMain.addView(makeEffectType, -1, -1);
            this.mLightingPreview.mEdgeListener = this.mEdgeAnimationListener;
            getWindow().addFlags(16);
        }
        this.mLightingPreview.setEdgeEffectInfo(edgeEffectInfo);
        if (!z) {
            this.mLightingPreview.mIsShowMorphView = false;
        }
        this.mLightingPreview.show();
    }

    public final void stopEdgeEffect() {
        SemLog.i("EdgeLightingDialog", "stopEdgeEffect");
        NotificationEffect notificationEffect = this.mNotificationEffect;
        if (notificationEffect != null) {
            notificationEffect.dismiss();
            getContext().sendBroadcast(new Intent("com.android.systemui.edgelighting.stop"));
            Slog.i("EdgeLightingDialog", "send broadcast : com.android.systemui.edgelighting.stop");
        }
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void stopPreview() {
        NotificationEffect notificationEffect = this.mLightingPreview;
        if (notificationEffect != null) {
            notificationEffect.dismiss();
        }
        new Handler().postDelayed(new Runnable() { // from class: com.android.systemui.edgelighting.effect.container.EdgeLightingDialog.3
            @Override // java.lang.Runnable
            public final void run() {
                EdgeLightingDialog.m1243$$Nest$mselfDismissWindow(EdgeLightingDialog.this);
            }
        }, 500L);
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void unRegisterEdgeWindowCallback() {
        this.mWindowCallback = null;
    }

    public final void updateBackground(Window window) {
        if (this.mUsingBlackBG) {
            Slog.i("EdgeLightingDialog", "updateBackground : OPAQUE(usingBlack)");
            window.setFormat(-3);
            window.getDecorView().setBackgroundColor(EmergencyPhoneWidget.BG_COLOR);
            this.mTransparent = false;
        } else {
            Slog.i("EdgeLightingDialog", "updateBackground : TRANSPARENT");
            window.setFormat(-3);
            window.getDecorView().setBackgroundColor(0);
            this.mTransparent = true;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (this.mUsingBlackBG) {
            attributes.screenBrightness = 1.0f;
        } else {
            attributes.screenBrightness = -1.0f;
        }
        if (this.mDozeDraw) {
            attributes.semAddExtensionFlags(262144);
        } else {
            attributes.semClearExtensionFlags(262144);
        }
        attributes.semAddPrivateFlags(16);
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void updatePreview(EdgeEffectInfo edgeEffectInfo) {
        NotificationEffect notificationEffect = this.mLightingPreview;
        if (notificationEffect != null) {
            notificationEffect.setEdgeEffectInfo(edgeEffectInfo);
            this.mLightingPreview.update();
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.edgelighting.effect.container.EdgeLightingDialog$1] */
    public EdgeLightingDialog(Context context, int i) {
        super(context, R.style.Theme.Translucent.NoTitleBar.Fullscreen);
        this.mUsingBlackBG = false;
        this.mTransparent = false;
        this.mDozeDraw = false;
        this.mWindowType = 2228;
        this.mIsUsingELPlusEffect = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.edgelighting.effect.container.EdgeLightingDialog.1
            @Override // android.os.Handler
            public final void dispatchMessage(Message message) {
                if (message.what == 1) {
                    EdgeLightingDialog.m1242$$Nest$mdismissInternal(EdgeLightingDialog.this);
                }
            }
        };
        this.mEdgeAnimationListener = new AnonymousClass4();
        this.mWindowType = i;
    }
}
