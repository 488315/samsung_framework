.class public abstract Lcom/android/systemui/statusbar/notification/collection/coordinator/RemoteInputCoordinatorKt;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final DEBUG$delegate:Lkotlin/Lazy;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/statusbar/notification/collection/coordinator/RemoteInputCoordinatorKt$DEBUG$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/collection/coordinator/RemoteInputCoordinatorKt$DEBUG$2;

    .line 2
    .line 3
    invoke-static {v0}, Lkotlin/LazyKt__LazyJVMKt;->lazy(Lkotlin/jvm/functions/Function0;)Lkotlin/Lazy;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    sput-object v0, Lcom/android/systemui/statusbar/notification/collection/coordinator/RemoteInputCoordinatorKt;->DEBUG$delegate:Lkotlin/Lazy;

    .line 8
    .line 9
    return-void
.end method

.method public static final access$getDEBUG()Z
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/statusbar/notification/collection/coordinator/RemoteInputCoordinatorKt;->DEBUG$delegate:Lkotlin/Lazy;

    .line 2
    .line 3
    invoke-interface {v0}, Lkotlin/Lazy;->getValue()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, Ljava/lang/Boolean;

    .line 8
    .line 9
    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    .line 10
    .line 11
    .line 12
    move-result v0

    .line 13
    return v0
.end method
