.class public final Lcom/android/systemui/tuner/TunablePadding_TunablePaddingService_Factory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljavax/inject/Provider;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljavax/inject/Provider;"
    }
.end annotation


# instance fields
.field public final tunerServiceProvider:Ljavax/inject/Provider;


# direct methods
.method public constructor <init>(Ljavax/inject/Provider;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljavax/inject/Provider;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/tuner/TunablePadding_TunablePaddingService_Factory;->tunerServiceProvider:Ljavax/inject/Provider;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/tuner/TunablePadding_TunablePaddingService_Factory;->tunerServiceProvider:Ljavax/inject/Provider;

    .line 2
    .line 3
    invoke-interface {p0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/tuner/TunerService;

    .line 8
    .line 9
    new-instance v0, Lcom/android/systemui/tuner/TunablePadding$TunablePaddingService;

    .line 10
    .line 11
    invoke-direct {v0, p0}, Lcom/android/systemui/tuner/TunablePadding$TunablePaddingService;-><init>(Lcom/android/systemui/tuner/TunerService;)V

    .line 12
    .line 13
    .line 14
    return-object v0
.end method