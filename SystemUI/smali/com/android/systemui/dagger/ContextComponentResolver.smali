.class public final Lcom/android/systemui/dagger/ContextComponentResolver;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/dagger/ContextComponentHelper;


# instance fields
.field public final mActivityCreators:Ljava/util/Map;

.field public final mBroadcastReceiverCreators:Ljava/util/Map;

.field public final mRecentsCreators:Ljava/util/Map;

.field public final mServiceCreators:Ljava/util/Map;


# direct methods
.method public constructor <init>(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Map<",
            "Ljava/lang/Class<",
            "*>;",
            "Ljavax/inject/Provider;",
            ">;",
            "Ljava/util/Map<",
            "Ljava/lang/Class<",
            "*>;",
            "Ljavax/inject/Provider;",
            ">;",
            "Ljava/util/Map<",
            "Ljava/lang/Class<",
            "*>;",
            "Ljavax/inject/Provider;",
            ">;",
            "Ljava/util/Map<",
            "Ljava/lang/Class<",
            "*>;",
            "Ljavax/inject/Provider;",
            ">;)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/dagger/ContextComponentResolver;->mActivityCreators:Ljava/util/Map;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/dagger/ContextComponentResolver;->mServiceCreators:Ljava/util/Map;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/dagger/ContextComponentResolver;->mRecentsCreators:Ljava/util/Map;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/dagger/ContextComponentResolver;->mBroadcastReceiverCreators:Ljava/util/Map;

    .line 11
    .line 12
    return-void
.end method

.method public static resolve(Ljava/lang/String;Ljava/util/Map;)Ljava/lang/Object;
    .locals 0

    .line 1
    :try_start_0
    invoke-static {p0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    invoke-interface {p1, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Ljavax/inject/Provider;

    .line 10
    .line 11
    if-nez p0, :cond_0

    .line 12
    .line 13
    goto :goto_0

    .line 14
    :cond_0
    invoke-interface {p0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 15
    .line 16
    .line 17
    move-result-object p0
    :try_end_0
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 18
    goto :goto_1

    .line 19
    :catch_0
    :goto_0
    const/4 p0, 0x0

    .line 20
    :goto_1
    return-object p0
.end method
