.class public final Lcom/android/systemui/navigationbar/bandaid/pack/ConfigurationPack$5$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Function;


# instance fields
.field public final synthetic $store:Lcom/android/systemui/navigationbar/store/NavBarStore;


# direct methods
.method public constructor <init>(Lcom/android/systemui/navigationbar/store/NavBarStore;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/navigationbar/bandaid/pack/ConfigurationPack$5$1;->$store:Lcom/android/systemui/navigationbar/store/NavBarStore;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 3

    .line 1
    check-cast p1, Lcom/android/systemui/navigationbar/bandaid/Band$Kit;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/navigationbar/bandaid/pack/ConfigurationPack$5$1;->$store:Lcom/android/systemui/navigationbar/store/NavBarStore;

    .line 4
    .line 5
    iget-object v0, p1, Lcom/android/systemui/navigationbar/bandaid/Band$Kit;->manager:Lcom/android/systemui/navigationbar/store/NavBarStateManager;

    .line 6
    .line 7
    invoke-virtual {v0}, Lcom/android/systemui/navigationbar/store/NavBarStateManager;->updateUseThemeDefault()V

    .line 8
    .line 9
    .line 10
    new-instance v0, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$UpdateNavBarOpaqueColor;

    .line 11
    .line 12
    const/4 v1, 0x0

    .line 13
    const/4 v2, 0x1

    .line 14
    invoke-direct {v0, v1, v2, v1}, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$UpdateNavBarOpaqueColor;-><init>(Lcom/android/systemui/navigationbar/store/NavBarStoreAction$Action;ILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 15
    .line 16
    .line 17
    check-cast p0, Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;

    .line 18
    .line 19
    invoke-virtual {p0, p1, v0}, Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;->apply(Lcom/android/systemui/navigationbar/bandaid/Band$Kit;Lcom/android/systemui/navigationbar/store/NavBarStoreAction;)Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;

    .line 20
    .line 21
    .line 22
    new-instance v0, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$UpdateNavBarIconAndHints;

    .line 23
    .line 24
    invoke-direct {v0, v1, v2, v1}, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$UpdateNavBarIconAndHints;-><init>(Lcom/android/systemui/navigationbar/store/NavBarStoreAction$Action;ILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 25
    .line 26
    .line 27
    invoke-virtual {p0, p1, v0}, Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;->apply(Lcom/android/systemui/navigationbar/bandaid/Band$Kit;Lcom/android/systemui/navigationbar/store/NavBarStoreAction;)Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;

    .line 28
    .line 29
    .line 30
    return-object p0
.end method
