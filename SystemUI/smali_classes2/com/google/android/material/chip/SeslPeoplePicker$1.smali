.class public final Lcom/google/android/material/chip/SeslPeoplePicker$1;
.super Landroid/os/CountDownTimer;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/google/android/material/chip/SeslPeoplePicker;


# direct methods
.method public constructor <init>(Lcom/google/android/material/chip/SeslPeoplePicker;JJ)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/google/android/material/chip/SeslPeoplePicker$1;->this$0:Lcom/google/android/material/chip/SeslPeoplePicker;

    .line 2
    .line 3
    invoke-direct {p0, p2, p3, p4, p5}, Landroid/os/CountDownTimer;-><init>(JJ)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onFinish()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/google/android/material/chip/SeslPeoplePicker$1;->this$0:Lcom/google/android/material/chip/SeslPeoplePicker;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/google/android/material/chip/SeslPeoplePicker;->mContainer:Lcom/google/android/material/chip/SeslExpandableContainer;

    .line 4
    .line 5
    const/4 v0, 0x1

    .line 6
    iput-boolean v0, p0, Lcom/google/android/material/chip/SeslExpandableContainer;->mFloatChangeAllowed:Z

    .line 7
    .line 8
    iget-object p0, p0, Lcom/google/android/material/chip/SeslExpandableContainer;->mExpansionButton:Lcom/google/android/material/chip/SeslExpansionButton;

    .line 9
    .line 10
    const/4 v0, 0x0

    .line 11
    invoke-virtual {p0, v0}, Lcom/google/android/material/chip/SeslExpansionButton;->setFloated(Z)V

    .line 12
    .line 13
    .line 14
    return-void
.end method

.method public final onTick(J)V
    .locals 0

    .line 1
    return-void
.end method
