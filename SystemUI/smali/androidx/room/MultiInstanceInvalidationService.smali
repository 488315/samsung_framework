.class public Landroidx/room/MultiInstanceInvalidationService;
.super Landroid/app/Service;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mBinder:Landroidx/room/MultiInstanceInvalidationService$2;

.field public final mCallbackList:Landroidx/room/MultiInstanceInvalidationService$1;

.field public final mClientNames:Ljava/util/HashMap;

.field public mMaxClientId:I


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Landroid/app/Service;-><init>()V

    .line 2
    .line 3
    .line 4
    const/4 v0, 0x0

    .line 5
    iput v0, p0, Landroidx/room/MultiInstanceInvalidationService;->mMaxClientId:I

    .line 6
    .line 7
    new-instance v0, Ljava/util/HashMap;

    .line 8
    .line 9
    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    .line 10
    .line 11
    .line 12
    iput-object v0, p0, Landroidx/room/MultiInstanceInvalidationService;->mClientNames:Ljava/util/HashMap;

    .line 13
    .line 14
    new-instance v0, Landroidx/room/MultiInstanceInvalidationService$1;

    .line 15
    .line 16
    invoke-direct {v0, p0}, Landroidx/room/MultiInstanceInvalidationService$1;-><init>(Landroidx/room/MultiInstanceInvalidationService;)V

    .line 17
    .line 18
    .line 19
    iput-object v0, p0, Landroidx/room/MultiInstanceInvalidationService;->mCallbackList:Landroidx/room/MultiInstanceInvalidationService$1;

    .line 20
    .line 21
    new-instance v0, Landroidx/room/MultiInstanceInvalidationService$2;

    .line 22
    .line 23
    invoke-direct {v0, p0}, Landroidx/room/MultiInstanceInvalidationService$2;-><init>(Landroidx/room/MultiInstanceInvalidationService;)V

    .line 24
    .line 25
    .line 26
    iput-object v0, p0, Landroidx/room/MultiInstanceInvalidationService;->mBinder:Landroidx/room/MultiInstanceInvalidationService$2;

    .line 27
    .line 28
    return-void
.end method


# virtual methods
.method public final onBind(Landroid/content/Intent;)Landroid/os/IBinder;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/room/MultiInstanceInvalidationService;->mBinder:Landroidx/room/MultiInstanceInvalidationService$2;

    .line 2
    .line 3
    return-object p0
.end method