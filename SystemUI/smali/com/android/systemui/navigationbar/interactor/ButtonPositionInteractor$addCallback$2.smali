.class public final Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor$addCallback$2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/util/SettingsHelper$OnChangedCallback;


# instance fields
.field public final synthetic $action:Ljava/util/function/Consumer;

.field public final synthetic this$0:Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor;


# direct methods
.method public constructor <init>(Ljava/util/function/Consumer;Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/function/Consumer<",
            "Ljava/lang/Object;",
            ">;",
            "Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor;",
            ")V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor$addCallback$2;->$action:Ljava/util/function/Consumer;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor$addCallback$2;->this$0:Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final onChanged(Landroid/net/Uri;)V
    .locals 0

    .line 1
    iget-object p1, p0, Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor$addCallback$2;->$action:Ljava/util/function/Consumer;

    .line 2
    .line 3
    invoke-static {p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor$addCallback$2;->this$0:Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor;

    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/systemui/navigationbar/interactor/ButtonPositionInteractor;->settingsHelper:Lcom/android/systemui/util/SettingsHelper;

    .line 9
    .line 10
    invoke-virtual {p0}, Lcom/android/systemui/util/SettingsHelper;->getNavigationBarAlignPosition()I

    .line 11
    .line 12
    .line 13
    move-result p0

    .line 14
    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 15
    .line 16
    .line 17
    move-result-object p0

    .line 18
    invoke-interface {p1, p0}, Ljava/util/function/Consumer;->accept(Ljava/lang/Object;)V

    .line 19
    .line 20
    .line 21
    return-void
.end method
