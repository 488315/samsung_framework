.class final Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper$conditions$2;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper;-><init>(Lcom/android/systemui/aod/AODAmbientWallpaperHelper;Ldagger/Lazy;Ldagger/Lazy;Ldagger/Lazy;Lcom/android/systemui/util/SettingsHelper;Lcom/android/systemui/statusbar/StatusBarStateControllerImpl;Landroid/content/Context;Lcom/android/systemui/statusbar/policy/KeyguardStateController;Lcom/android/systemui/keyguard/KeyguardVisibilityMonitor;Lcom/android/systemui/keyguard/WakefulnessLifecycle;Ldagger/Lazy;Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/CoroutineDispatcher;Lkotlinx/coroutines/CoroutineDispatcher;Ldagger/Lazy;Landroid/os/Handler;Landroid/view/WindowManager;Landroid/app/WallpaperManager;Ldagger/Lazy;Ldagger/Lazy;Lcom/android/systemui/keyguard/ScreenLifecycle;Ldagger/Lazy;Lcom/android/systemui/aod/AODTouchModeManager;Ldagger/Lazy;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper$conditions$2;->this$0:Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper;

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper$conditions$2;->this$0:Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/SecUnlockedScreenOffAnimationHelper;->keyguardUpdateMonitorLazy:Ldagger/Lazy;

    .line 4
    .line 5
    invoke-interface {p0}, Ldagger/Lazy;->get()Ljava/lang/Object;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 10
    .line 11
    iget-boolean p0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor;->mDeviceProvisioned:Z

    .line 12
    .line 13
    xor-int/lit8 p0, p0, 0x1

    .line 14
    .line 15
    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 16
    .line 17
    .line 18
    move-result-object p0

    .line 19
    return-object p0
.end method
