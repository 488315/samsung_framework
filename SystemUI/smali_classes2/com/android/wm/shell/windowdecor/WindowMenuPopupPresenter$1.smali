.class public final Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic this$0:Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter;


# direct methods
.method public constructor <init>(Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter$1;->this$0:Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter;

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
    iget-object p0, p0, Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter$1;->this$0:Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/wm/shell/windowdecor/WindowMenuPopupPresenter;->mPinButton:Lcom/android/wm/shell/windowdecor/WindowMenuAnimationView;

    .line 4
    .line 5
    if-eqz p0, :cond_0

    .line 6
    .line 7
    invoke-virtual {p0}, Lcom/airbnb/lottie/LottieAnimationView;->playAnimation()V

    .line 8
    .line 9
    .line 10
    :cond_0
    return-void
.end method