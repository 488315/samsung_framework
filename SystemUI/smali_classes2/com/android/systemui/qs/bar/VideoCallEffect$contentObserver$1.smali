.class public final Lcom/android/systemui/qs/bar/VideoCallEffect$contentObserver$1;
.super Landroid/database/ContentObserver;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/qs/bar/VideoCallEffect;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/bar/VideoCallEffect;Landroid/os/Handler;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/qs/bar/VideoCallEffect$contentObserver$1;->this$0:Lcom/android/systemui/qs/bar/VideoCallEffect;

    .line 2
    .line 3
    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onChange(ZLandroid/net/Uri;)V
    .locals 0

    .line 1
    invoke-super {p0, p1, p2}, Landroid/database/ContentObserver;->onChange(ZLandroid/net/Uri;)V

    .line 2
    .line 3
    .line 4
    if-eqz p2, :cond_0

    .line 5
    .line 6
    iget-object p0, p0, Lcom/android/systemui/qs/bar/VideoCallEffect$contentObserver$1;->this$0:Lcom/android/systemui/qs/bar/VideoCallEffect;

    .line 7
    .line 8
    sget-object p1, Lcom/android/systemui/qs/bar/VideoCallEffect;->URI_VSET_APP_STATUS_DATA:Landroid/net/Uri;

    .line 9
    .line 10
    invoke-virtual {p0, p2}, Lcom/android/systemui/qs/bar/VideoCallEffect;->parseVce(Landroid/net/Uri;)V

    .line 11
    .line 12
    .line 13
    :cond_0
    return-void
.end method