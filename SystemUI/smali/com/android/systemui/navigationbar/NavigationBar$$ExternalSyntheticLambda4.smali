.class public final synthetic Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/systemui/navigationbar/NavigationBar;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/navigationbar/NavigationBar;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda4;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/navigationbar/NavigationBar;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final onClick(Landroid/view/View;)V
    .locals 2

    .line 1
    iget v0, p0, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda4;->$r8$classId:I

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    packed-switch v0, :pswitch_data_0

    .line 5
    .line 6
    .line 7
    goto :goto_1

    .line 8
    :pswitch_0
    iget-object p0, p0, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/navigationbar/NavigationBar;

    .line 9
    .line 10
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 11
    .line 12
    .line 13
    invoke-virtual {p1}, Landroid/view/View;->getDisplay()Landroid/view/Display;

    .line 14
    .line 15
    .line 16
    move-result-object p1

    .line 17
    if-eqz p1, :cond_0

    .line 18
    .line 19
    invoke-virtual {p1}, Landroid/view/Display;->getDisplayId()I

    .line 20
    .line 21
    .line 22
    move-result p1

    .line 23
    goto :goto_0

    .line 24
    :cond_0
    iget-object p1, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mDisplayTracker:Lcom/android/systemui/settings/DisplayTracker;

    .line 25
    .line 26
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 27
    .line 28
    .line 29
    const/4 p1, 0x0

    .line 30
    :goto_0
    iget-object p0, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mAccessibilityManager:Landroid/view/accessibility/AccessibilityManager;

    .line 31
    .line 32
    invoke-virtual {p0, p1}, Landroid/view/accessibility/AccessibilityManager;->notifyAccessibilityButtonClicked(I)V

    .line 33
    .line 34
    .line 35
    return-void

    .line 36
    :pswitch_1
    iget-object p0, p0, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/navigationbar/NavigationBar;

    .line 37
    .line 38
    iget-object p1, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mContext:Landroid/content/Context;

    .line 39
    .line 40
    invoke-static {p1}, Lcom/android/internal/util/LatencyTracker;->isEnabled(Landroid/content/Context;)Z

    .line 41
    .line 42
    .line 43
    move-result v0

    .line 44
    if-eqz v0, :cond_1

    .line 45
    .line 46
    invoke-static {p1}, Lcom/android/internal/util/LatencyTracker;->getInstance(Landroid/content/Context;)Lcom/android/internal/util/LatencyTracker;

    .line 47
    .line 48
    .line 49
    move-result-object p1

    .line 50
    invoke-virtual {p1, v1}, Lcom/android/internal/util/LatencyTracker;->onActionStart(I)V

    .line 51
    .line 52
    .line 53
    :cond_1
    iget-object p1, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mCentralSurfacesOptionalLazy:Ldagger/Lazy;

    .line 54
    .line 55
    invoke-interface {p1}, Ldagger/Lazy;->get()Ljava/lang/Object;

    .line 56
    .line 57
    .line 58
    move-result-object p1

    .line 59
    check-cast p1, Ljava/util/Optional;

    .line 60
    .line 61
    new-instance v0, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda0;

    .line 62
    .line 63
    const/4 v1, 0x3

    .line 64
    invoke-direct {v0, v1}, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda0;-><init>(I)V

    .line 65
    .line 66
    .line 67
    invoke-virtual {p1, v0}, Ljava/util/Optional;->ifPresent(Ljava/util/function/Consumer;)V

    .line 68
    .line 69
    .line 70
    iget-object p0, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mCommandQueue:Lcom/android/systemui/statusbar/CommandQueue;

    .line 71
    .line 72
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/CommandQueue;->toggleRecentApps()V

    .line 73
    .line 74
    .line 75
    return-void

    .line 76
    :goto_1
    iget-object p0, p0, Lcom/android/systemui/navigationbar/NavigationBar$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/navigationbar/NavigationBar;

    .line 77
    .line 78
    iget p1, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mDisplayId:I

    .line 79
    .line 80
    iget-object v0, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mInputMethodManager:Landroid/view/inputmethod/InputMethodManager;

    .line 81
    .line 82
    invoke-virtual {v0, v1, p1}, Landroid/view/inputmethod/InputMethodManager;->showInputMethodPickerFromSystem(ZI)V

    .line 83
    .line 84
    .line 85
    iget-object p0, p0, Lcom/android/systemui/navigationbar/NavigationBar;->mUiEventLogger:Lcom/android/internal/logging/UiEventLogger;

    .line 86
    .line 87
    sget-object p1, Lcom/android/systemui/navigationbar/buttons/KeyButtonView$NavBarButtonEvent;->NAVBAR_IME_SWITCHER_BUTTON_TAP:Lcom/android/systemui/navigationbar/buttons/KeyButtonView$NavBarButtonEvent;

    .line 88
    .line 89
    invoke-interface {p0, p1}, Lcom/android/internal/logging/UiEventLogger;->log(Lcom/android/internal/logging/UiEventLogger$UiEventEnum;)V

    .line 90
    .line 91
    .line 92
    return-void

    .line 93
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
