.class public final Lcom/android/systemui/util/concurrency/ThreadFactoryImpl_Factory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljavax/inject/Provider;


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static newInstance()Lcom/android/systemui/util/concurrency/ThreadFactoryImpl;
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/util/concurrency/ThreadFactoryImpl;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/util/concurrency/ThreadFactoryImpl;-><init>()V

    .line 4
    .line 5
    .line 6
    return-object v0
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 0

    .line 1
    new-instance p0, Lcom/android/systemui/util/concurrency/ThreadFactoryImpl;

    .line 2
    .line 3
    invoke-direct {p0}, Lcom/android/systemui/util/concurrency/ThreadFactoryImpl;-><init>()V

    .line 4
    .line 5
    .line 6
    return-object p0
.end method
