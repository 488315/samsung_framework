.class final Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder$bindData$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function1;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder;->bindData(Landroidx/picker/model/viewdata/ViewData;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function1;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder;


# direct methods
.method public constructor <init>(Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder$bindData$1;->this$0:Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder;

    .line 2
    .line 3
    const/4 p1, 0x1

    .line 4
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Ljava/lang/Boolean;

    .line 2
    .line 3
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    .line 4
    .line 5
    .line 6
    move-result p1

    .line 7
    iget-object p0, p0, Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder$bindData$1;->this$0:Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder;

    .line 8
    .line 9
    invoke-static {p0}, Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder;->access$getSwitch$p(Landroidx/picker/features/composable/widget/ComposableSwitchViewHolder;)Landroidx/appcompat/widget/SwitchCompat;

    .line 10
    .line 11
    .line 12
    move-result-object p0

    .line 13
    invoke-virtual {p0, p1}, Landroidx/appcompat/widget/SwitchCompat;->setChecked(Z)V

    .line 14
    .line 15
    .line 16
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 17
    .line 18
    return-object p0
.end method
