.class public final Lcom/android/systemui/qs/buttons/QSMumButton$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/View$OnLongClickListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/qs/buttons/QSMumButton;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/buttons/QSMumButton;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/qs/buttons/QSMumButton$1;->this$0:Lcom/android/systemui/qs/buttons/QSMumButton;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onLongClick(Landroid/view/View;)Z
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/qs/buttons/QSMumButton$1;->this$0:Lcom/android/systemui/qs/buttons/QSMumButton;

    .line 2
    .line 3
    iget-object v0, v0, Lcom/android/systemui/qs/buttons/QSMumButton;->mTipWindow:Lcom/android/systemui/qs/buttons/QSTooltipWindow;

    .line 4
    .line 5
    invoke-virtual {v0}, Lcom/android/systemui/qs/buttons/QSTooltipWindow;->isTooltipShown()Z

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    if-nez v0, :cond_0

    .line 10
    .line 11
    iget-object v0, p0, Lcom/android/systemui/qs/buttons/QSMumButton$1;->this$0:Lcom/android/systemui/qs/buttons/QSMumButton;

    .line 12
    .line 13
    iget-object v1, v0, Lcom/android/systemui/qs/buttons/QSMumButton;->mTipWindow:Lcom/android/systemui/qs/buttons/QSTooltipWindow;

    .line 14
    .line 15
    iget v0, v0, Lcom/android/systemui/qs/buttons/QSMumButton;->mToolTipString:I

    .line 16
    .line 17
    invoke-virtual {v1, p1, v0}, Lcom/android/systemui/qs/buttons/QSTooltipWindow;->showToolTip(Landroid/view/View;I)V

    .line 18
    .line 19
    .line 20
    iget-object p1, p0, Lcom/android/systemui/qs/buttons/QSMumButton$1;->this$0:Lcom/android/systemui/qs/buttons/QSMumButton;

    .line 21
    .line 22
    invoke-virtual {p1}, Landroid/widget/FrameLayout;->getParent()Landroid/view/ViewParent;

    .line 23
    .line 24
    .line 25
    move-result-object p1

    .line 26
    check-cast p1, Lcom/android/systemui/qs/buttons/QSButtonsContainer;

    .line 27
    .line 28
    iget-object p0, p0, Lcom/android/systemui/qs/buttons/QSMumButton$1;->this$0:Lcom/android/systemui/qs/buttons/QSMumButton;

    .line 29
    .line 30
    iput-object p0, p1, Lcom/android/systemui/qs/buttons/QSButtonsContainer;->mCloseTooltipWindow:Lcom/android/systemui/qs/buttons/QSButtonsContainer$CloseTooltipWindow;

    .line 31
    .line 32
    :cond_0
    const/4 p0, 0x1

    .line 33
    return p0
.end method