.class final Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$updateControl$isAllSmallType$2;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function1;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function1;"
    }
.end annotation


# static fields
.field public static final INSTANCE:Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$updateControl$isAllSmallType$2;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$updateControl$isAllSmallType$2;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$updateControl$isAllSmallType$2;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$updateControl$isAllSmallType$2;->INSTANCE:Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$updateControl$isAllSmallType$2;

    .line 7
    .line 8
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 1
    const/4 v0, 0x1

    .line 2
    invoke-direct {p0, v0}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 3
    .line 4
    .line 5
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lcom/android/systemui/controls/management/model/MainControlModel;

    .line 2
    .line 3
    invoke-virtual {p1}, Lcom/android/systemui/controls/management/model/MainControlModel;->getType()Lcom/android/systemui/controls/management/model/MainModel$Type;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    sget-object p1, Lcom/android/systemui/controls/management/model/MainModel$Type;->SMALL_CONTROL:Lcom/android/systemui/controls/management/model/MainModel$Type;

    .line 8
    .line 9
    if-ne p0, p1, :cond_0

    .line 10
    .line 11
    const/4 p0, 0x1

    .line 12
    goto :goto_0

    .line 13
    :cond_0
    const/4 p0, 0x0

    .line 14
    :goto_0
    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 15
    .line 16
    .line 17
    move-result-object p0

    .line 18
    return-object p0
.end method