.class final Lcom/android/systemui/animation/view/LaunchableLinearLayout$delegate$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function1;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/animation/view/LaunchableLinearLayout;-><init>(Landroid/content/Context;)V
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
.field final synthetic this$0:Lcom/android/systemui/animation/view/LaunchableLinearLayout;


# direct methods
.method public constructor <init>(Lcom/android/systemui/animation/view/LaunchableLinearLayout;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/animation/view/LaunchableLinearLayout$delegate$1;->this$0:Lcom/android/systemui/animation/view/LaunchableLinearLayout;

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
    check-cast p1, Ljava/lang/Number;

    .line 2
    .line 3
    invoke-virtual {p1}, Ljava/lang/Number;->intValue()I

    .line 4
    .line 5
    .line 6
    move-result p1

    .line 7
    iget-object p0, p0, Lcom/android/systemui/animation/view/LaunchableLinearLayout$delegate$1;->this$0:Lcom/android/systemui/animation/view/LaunchableLinearLayout;

    .line 8
    .line 9
    invoke-static {p0, p1}, Lcom/android/systemui/animation/view/LaunchableLinearLayout;->access$setVisibility$s1127291599(Lcom/android/systemui/animation/view/LaunchableLinearLayout;I)V

    .line 10
    .line 11
    .line 12
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 13
    .line 14
    return-object p0
.end method