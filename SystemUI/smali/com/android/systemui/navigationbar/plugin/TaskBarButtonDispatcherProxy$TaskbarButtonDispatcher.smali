.class public final Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy$TaskbarButtonDispatcher;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public id:I

.field public final view:Landroid/view/View;


# direct methods
.method public constructor <init>(Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy;ILandroid/view/View;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Landroid/view/View;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p2, p0, Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy$TaskbarButtonDispatcher;->id:I

    iput-object p3, p0, Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy$TaskbarButtonDispatcher;->view:Landroid/view/View;

    return-void
.end method

.method public constructor <init>(Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy;ILandroid/view/View;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .locals 0

    and-int/lit8 p4, p4, 0x2

    if-eqz p4, :cond_0

    .line 2
    new-instance p3, Landroid/view/View;

    .line 3
    iget-object p4, p1, Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy;->context:Landroid/content/Context;

    .line 4
    invoke-direct {p3, p4}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    :cond_0
    invoke-direct {p0, p1, p2, p3}, Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy$TaskbarButtonDispatcher;-><init>(Lcom/android/systemui/navigationbar/plugin/TaskBarButtonDispatcherProxy;ILandroid/view/View;)V

    return-void
.end method
