.class public final Lcom/samsung/android/knox/kpm/IKnoxPushServiceCallback$Default;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/samsung/android/knox/kpm/IKnoxPushServiceCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/knox/kpm/IKnoxPushServiceCallback;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Default"
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final asBinder()Landroid/os/IBinder;
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return-object p0
.end method

.method public final onRegistrationFinished(Lcom/samsung/android/knox/kpm/KnoxPushServiceResult;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final onRegistrationStatus(Lcom/samsung/android/knox/kpm/KnoxPushServiceResult;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final onUnRegistrationFinished(Lcom/samsung/android/knox/kpm/KnoxPushServiceResult;)V
    .locals 0

    .line 1
    return-void
.end method