.class public final Lcom/android/systemui/statusbar/phone/KeyguardBypassController$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/DevicePostureController$Callback;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/phone/KeyguardBypassController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/KeyguardBypassController$1;->this$0:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onPostureChanged(I)V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/KeyguardBypassController$1;->this$0:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

    .line 2
    .line 3
    iget v0, p0, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->postureState:I

    .line 4
    .line 5
    if-eq v0, p1, :cond_0

    .line 6
    .line 7
    iput p1, p0, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->postureState:I

    .line 8
    .line 9
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->notifyListeners()V

    .line 10
    .line 11
    .line 12
    :cond_0
    return-void
.end method