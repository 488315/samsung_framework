.class public final Lcom/android/systemui/aod/AODTouchModeManager$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/keyguard/WakefulnessLifecycle$Observer;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/aod/AODTouchModeManager;


# direct methods
.method public constructor <init>(Lcom/android/systemui/aod/AODTouchModeManager;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/aod/AODTouchModeManager$1;->this$0:Lcom/android/systemui/aod/AODTouchModeManager;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onStartedWakingUp()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/aod/AODTouchModeManager$1;->this$0:Lcom/android/systemui/aod/AODTouchModeManager;

    .line 2
    .line 3
    const/4 v0, 0x1

    .line 4
    invoke-virtual {p0, v0}, Lcom/android/systemui/aod/AODTouchModeManager;->setTouchMode(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method
