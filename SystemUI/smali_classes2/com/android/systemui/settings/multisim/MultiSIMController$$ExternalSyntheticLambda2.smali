.class public final synthetic Lcom/android/systemui/settings/multisim/MultiSIMController$$ExternalSyntheticLambda2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Function;


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lcom/android/systemui/settings/multisim/MultiSIMController$KoreanSimCarrier;

    .line 2
    .line 3
    invoke-virtual {p1}, Lcom/android/systemui/settings/multisim/MultiSIMController$KoreanSimCarrier;->getNumeric()Ljava/lang/String;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method
