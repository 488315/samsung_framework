.class public final synthetic Lcom/android/systemui/globalactions/GlobalActionsImpl$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/content/DialogInterface$OnShowListener;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/globalactions/GlobalActionsImpl;

.field public final synthetic f$1:Lcom/android/systemui/scrim/ScrimDrawable;

.field public final synthetic f$2:Landroid/app/Dialog;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/globalactions/GlobalActionsImpl;Lcom/android/systemui/scrim/ScrimDrawable;Landroid/app/Dialog;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/globalactions/GlobalActionsImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/globalactions/GlobalActionsImpl;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/globalactions/GlobalActionsImpl$$ExternalSyntheticLambda0;->f$1:Lcom/android/systemui/scrim/ScrimDrawable;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/globalactions/GlobalActionsImpl$$ExternalSyntheticLambda0;->f$2:Landroid/app/Dialog;

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final onShow(Landroid/content/DialogInterface;)V
    .locals 3

    .line 1
    iget-object p1, p0, Lcom/android/systemui/globalactions/GlobalActionsImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/globalactions/GlobalActionsImpl;

    .line 2
    .line 3
    iget-object v0, p0, Lcom/android/systemui/globalactions/GlobalActionsImpl$$ExternalSyntheticLambda0;->f$1:Lcom/android/systemui/scrim/ScrimDrawable;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/globalactions/GlobalActionsImpl$$ExternalSyntheticLambda0;->f$2:Landroid/app/Dialog;

    .line 6
    .line 7
    iget-object v1, p1, Lcom/android/systemui/globalactions/GlobalActionsImpl;->mBlurUtils:Lcom/android/systemui/statusbar/BlurUtils;

    .line 8
    .line 9
    invoke-virtual {v1}, Lcom/android/systemui/statusbar/BlurUtils;->supportsBlursOnWindows()Z

    .line 10
    .line 11
    .line 12
    move-result v2

    .line 13
    if-eqz v2, :cond_0

    .line 14
    .line 15
    const/16 p1, 0xff

    .line 16
    .line 17
    invoke-virtual {v0, p1}, Lcom/android/systemui/scrim/ScrimDrawable;->setAlpha(I)V

    .line 18
    .line 19
    .line 20
    invoke-virtual {p0}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    .line 21
    .line 22
    .line 23
    move-result-object p0

    .line 24
    invoke-virtual {p0}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    .line 25
    .line 26
    .line 27
    move-result-object p0

    .line 28
    invoke-virtual {p0}, Landroid/view/View;->getViewRootImpl()Landroid/view/ViewRootImpl;

    .line 29
    .line 30
    .line 31
    move-result-object p0

    .line 32
    const/high16 p1, 0x3f800000    # 1.0f

    .line 33
    .line 34
    invoke-virtual {v1, p1}, Lcom/android/systemui/statusbar/BlurUtils;->blurRadiusOfRatio(F)F

    .line 35
    .line 36
    .line 37
    move-result p1

    .line 38
    float-to-int p1, p1

    .line 39
    const/4 v0, 0x1

    .line 40
    invoke-virtual {v1, p0, p1, v0}, Lcom/android/systemui/statusbar/BlurUtils;->applyBlur(Landroid/view/ViewRootImpl;IZ)V

    .line 41
    .line 42
    .line 43
    goto :goto_0

    .line 44
    :cond_0
    iget-object p0, p1, Lcom/android/systemui/globalactions/GlobalActionsImpl;->mContext:Landroid/content/Context;

    .line 45
    .line 46
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 47
    .line 48
    .line 49
    move-result-object p0

    .line 50
    const p1, 0x7f0711b8

    .line 51
    .line 52
    .line 53
    invoke-virtual {p0, p1}, Landroid/content/res/Resources;->getFloat(I)F

    .line 54
    .line 55
    .line 56
    move-result p0

    .line 57
    const/high16 p1, 0x437f0000    # 255.0f

    .line 58
    .line 59
    mul-float/2addr p0, p1

    .line 60
    float-to-int p0, p0

    .line 61
    invoke-virtual {v0, p0}, Lcom/android/systemui/scrim/ScrimDrawable;->setAlpha(I)V

    .line 62
    .line 63
    .line 64
    :goto_0
    return-void
.end method
