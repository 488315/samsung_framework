.class public final Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$bypassStateChangedListener$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/phone/KeyguardBypassController$OnBypassStateChangedListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$bypassStateChangedListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onBypassStateChanged(Z)V
    .locals 0

    .line 1
    sget p1, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->$r8$clinit:I

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$bypassStateChangedListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 4
    .line 5
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->updateBypassEnabled()V

    .line 6
    .line 7
    .line 8
    return-void
.end method