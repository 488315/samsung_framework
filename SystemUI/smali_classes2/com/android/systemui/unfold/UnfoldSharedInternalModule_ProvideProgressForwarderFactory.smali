.class public final Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljavax/inject/Provider;


# instance fields
.field public final configProvider:Ljavax/inject/Provider;

.field public final module:Lcom/android/systemui/unfold/UnfoldSharedInternalModule;

.field public final progressForwarderProvider:Ljavax/inject/Provider;


# direct methods
.method public constructor <init>(Lcom/android/systemui/unfold/UnfoldSharedInternalModule;Ljavax/inject/Provider;Ljavax/inject/Provider;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/unfold/UnfoldSharedInternalModule;",
            "Ljavax/inject/Provider;",
            "Ljavax/inject/Provider;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;->module:Lcom/android/systemui/unfold/UnfoldSharedInternalModule;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;->configProvider:Ljavax/inject/Provider;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;->progressForwarderProvider:Ljavax/inject/Provider;

    .line 9
    .line 10
    return-void
.end method

.method public static provideProgressForwarder(Lcom/android/systemui/unfold/UnfoldSharedInternalModule;Lcom/android/systemui/unfold/config/UnfoldTransitionConfig;Ljavax/inject/Provider;)Ljava/util/Optional;
    .locals 0

    .line 1
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 2
    .line 3
    .line 4
    check-cast p1, Lcom/android/systemui/unfold/config/ResourceUnfoldTransitionConfig;

    .line 5
    .line 6
    iget-object p0, p1, Lcom/android/systemui/unfold/config/ResourceUnfoldTransitionConfig;->isEnabled$delegate:Lkotlin/Lazy;

    .line 7
    .line 8
    invoke-interface {p0}, Lkotlin/Lazy;->getValue()Ljava/lang/Object;

    .line 9
    .line 10
    .line 11
    move-result-object p0

    .line 12
    check-cast p0, Ljava/lang/Boolean;

    .line 13
    .line 14
    invoke-virtual {p0}, Ljava/lang/Boolean;->booleanValue()Z

    .line 15
    .line 16
    .line 17
    move-result p0

    .line 18
    if-nez p0, :cond_0

    .line 19
    .line 20
    invoke-static {}, Ljava/util/Optional;->empty()Ljava/util/Optional;

    .line 21
    .line 22
    .line 23
    move-result-object p0

    .line 24
    goto :goto_0

    .line 25
    :cond_0
    invoke-interface {p2}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 26
    .line 27
    .line 28
    move-result-object p0

    .line 29
    invoke-static {p0}, Ljava/util/Optional;->of(Ljava/lang/Object;)Ljava/util/Optional;

    .line 30
    .line 31
    .line 32
    move-result-object p0

    .line 33
    :goto_0
    return-object p0
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;->configProvider:Ljavax/inject/Provider;

    .line 2
    .line 3
    invoke-interface {v0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, Lcom/android/systemui/unfold/config/UnfoldTransitionConfig;

    .line 8
    .line 9
    iget-object v1, p0, Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;->progressForwarderProvider:Ljavax/inject/Provider;

    .line 10
    .line 11
    iget-object p0, p0, Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;->module:Lcom/android/systemui/unfold/UnfoldSharedInternalModule;

    .line 12
    .line 13
    invoke-static {p0, v0, v1}, Lcom/android/systemui/unfold/UnfoldSharedInternalModule_ProvideProgressForwarderFactory;->provideProgressForwarder(Lcom/android/systemui/unfold/UnfoldSharedInternalModule;Lcom/android/systemui/unfold/config/UnfoldTransitionConfig;Ljavax/inject/Provider;)Ljava/util/Optional;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method