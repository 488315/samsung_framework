.class public final synthetic Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $tmp0:Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl;


# direct methods
.method public constructor <init>(Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1;->$tmp0:Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1;->$tmp0:Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/keyguard/KeyguardViewMediatorHelperImpl;->notifyDrawn()V

    .line 4
    .line 5
    .line 6
    return-void
.end method