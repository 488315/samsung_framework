.class public final Lcom/samsung/systemui/splugins/navigationbar/LayoutProviderContainer$DefaultImpls;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/systemui/splugins/navigationbar/LayoutProviderContainer;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "DefaultImpls"
.end annotation


# direct methods
.method public static updateLayoutProvider(Lcom/samsung/systemui/splugins/navigationbar/LayoutProviderContainer;ZZ)Lcom/samsung/systemui/splugins/navigationbar/LayoutProvider;
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return-object p0
.end method

.method public static synthetic updateLayoutProvider$default(Lcom/samsung/systemui/splugins/navigationbar/LayoutProviderContainer;ZZILjava/lang/Object;)Lcom/samsung/systemui/splugins/navigationbar/LayoutProvider;
    .locals 0

    .line 1
    if-nez p4, :cond_1

    .line 2
    .line 3
    and-int/lit8 p3, p3, 0x1

    .line 4
    .line 5
    if-eqz p3, :cond_0

    .line 6
    .line 7
    const/4 p1, 0x0

    .line 8
    :cond_0
    invoke-interface {p0, p1, p2}, Lcom/samsung/systemui/splugins/navigationbar/LayoutProviderContainer;->updateLayoutProvider(ZZ)Lcom/samsung/systemui/splugins/navigationbar/LayoutProvider;

    .line 9
    .line 10
    .line 11
    move-result-object p0

    .line 12
    return-object p0

    .line 13
    :cond_1
    new-instance p0, Ljava/lang/UnsupportedOperationException;

    .line 14
    .line 15
    const-string p1, "Super calls with default arguments not supported in this target, function: updateLayoutProvider"

    .line 16
    .line 17
    invoke-direct {p0, p1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    .line 18
    .line 19
    .line 20
    throw p0
.end method
