.class public final Lcom/android/systemui/qs/user/UserSwitchDialogController;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final USER_SETTINGS_INTENT:Landroid/content/Intent;


# instance fields
.field public final activityStarter:Lcom/android/systemui/plugins/ActivityStarter;

.field public final dialogFactory:Lkotlin/jvm/functions/Function1;

.field public final dialogLaunchAnimator:Lcom/android/systemui/animation/DialogLaunchAnimator;

.field public final falsingManager:Lcom/android/systemui/plugins/FalsingManager;

.field public final uiEventLogger:Lcom/android/internal/logging/UiEventLogger;

.field public final userDetailViewAdapterProvider:Ljavax/inject/Provider;


# direct methods
.method public static constructor <clinit>()V
    .locals 2

    .line 1
    new-instance v0, Lcom/android/systemui/qs/user/UserSwitchDialogController$Companion;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    invoke-direct {v0, v1}, Lcom/android/systemui/qs/user/UserSwitchDialogController$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 5
    .line 6
    .line 7
    new-instance v0, Landroid/content/Intent;

    .line 8
    .line 9
    const-string v1, "android.settings.USER_SETTINGS"

    .line 10
    .line 11
    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 12
    .line 13
    .line 14
    sput-object v0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->USER_SETTINGS_INTENT:Landroid/content/Intent;

    .line 15
    .line 16
    return-void
.end method

.method public constructor <init>(Ljavax/inject/Provider;Lcom/android/systemui/plugins/ActivityStarter;Lcom/android/systemui/plugins/FalsingManager;Lcom/android/systemui/animation/DialogLaunchAnimator;Lcom/android/internal/logging/UiEventLogger;)V
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljavax/inject/Provider;",
            "Lcom/android/systemui/plugins/ActivityStarter;",
            "Lcom/android/systemui/plugins/FalsingManager;",
            "Lcom/android/systemui/animation/DialogLaunchAnimator;",
            "Lcom/android/internal/logging/UiEventLogger;",
            ")V"
        }
    .end annotation

    .line 8
    sget-object v6, Lcom/android/systemui/qs/user/UserSwitchDialogController$1;->INSTANCE:Lcom/android/systemui/qs/user/UserSwitchDialogController$1;

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    move-object v3, p3

    move-object v4, p4

    move-object v5, p5

    .line 9
    invoke-direct/range {v0 .. v6}, Lcom/android/systemui/qs/user/UserSwitchDialogController;-><init>(Ljavax/inject/Provider;Lcom/android/systemui/plugins/ActivityStarter;Lcom/android/systemui/plugins/FalsingManager;Lcom/android/systemui/animation/DialogLaunchAnimator;Lcom/android/internal/logging/UiEventLogger;Lkotlin/jvm/functions/Function1;)V

    return-void
.end method

.method public constructor <init>(Ljavax/inject/Provider;Lcom/android/systemui/plugins/ActivityStarter;Lcom/android/systemui/plugins/FalsingManager;Lcom/android/systemui/animation/DialogLaunchAnimator;Lcom/android/internal/logging/UiEventLogger;Lkotlin/jvm/functions/Function1;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljavax/inject/Provider;",
            "Lcom/android/systemui/plugins/ActivityStarter;",
            "Lcom/android/systemui/plugins/FalsingManager;",
            "Lcom/android/systemui/animation/DialogLaunchAnimator;",
            "Lcom/android/internal/logging/UiEventLogger;",
            "Lkotlin/jvm/functions/Function1;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    iput-object p1, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->userDetailViewAdapterProvider:Ljavax/inject/Provider;

    .line 3
    iput-object p2, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->activityStarter:Lcom/android/systemui/plugins/ActivityStarter;

    .line 4
    iput-object p3, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->falsingManager:Lcom/android/systemui/plugins/FalsingManager;

    .line 5
    iput-object p4, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->dialogLaunchAnimator:Lcom/android/systemui/animation/DialogLaunchAnimator;

    .line 6
    iput-object p5, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->uiEventLogger:Lcom/android/internal/logging/UiEventLogger;

    .line 7
    iput-object p6, p0, Lcom/android/systemui/qs/user/UserSwitchDialogController;->dialogFactory:Lkotlin/jvm/functions/Function1;

    return-void
.end method
