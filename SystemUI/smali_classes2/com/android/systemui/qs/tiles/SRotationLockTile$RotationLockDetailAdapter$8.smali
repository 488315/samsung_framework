.class public final Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter$8;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/widget/CompoundButton$OnCheckedChangeListener;


# instance fields
.field public final synthetic this$1:Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter$8;->this$1:Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onCheckedChanged(Landroid/widget/CompoundButton;Z)V
    .locals 4

    .line 1
    iget-object p1, p0, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter$8;->this$1:Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;

    .line 2
    .line 3
    sget v0, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;->$r8$clinit:I

    .line 4
    .line 5
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 6
    .line 7
    .line 8
    sget v0, Lcom/android/systemui/qs/tiles/SRotationLockTile;->$r8$clinit:I

    .line 9
    .line 10
    iget-object v0, p1, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/SRotationLockTile;

    .line 11
    .line 12
    iget-object v0, v0, Lcom/android/systemui/qs/tileimpl/SQSTileImpl;->mHandler:Lcom/android/systemui/qs/tileimpl/SQSTileImpl$SHandler;

    .line 13
    .line 14
    new-instance v1, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter$2;

    .line 15
    .line 16
    invoke-direct {v1, p1, p2}, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter$2;-><init>(Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;Z)V

    .line 17
    .line 18
    .line 19
    const-wide/16 v2, 0x1e

    .line 20
    .line 21
    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 22
    .line 23
    .line 24
    iget-object p0, p0, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter$8;->this$1:Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;

    .line 25
    .line 26
    iget-object p1, p0, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;->mLockSwitch:Landroidx/appcompat/widget/SwitchCompat;

    .line 27
    .line 28
    iget-object p0, p0, Lcom/android/systemui/qs/tiles/SRotationLockTile$RotationLockDetailAdapter;->this$0:Lcom/android/systemui/qs/tiles/SRotationLockTile;

    .line 29
    .line 30
    if-eqz p2, :cond_0

    .line 31
    .line 32
    iget-object p0, p0, Lcom/android/systemui/qs/tileimpl/QSTileImpl;->mContext:Landroid/content/Context;

    .line 33
    .line 34
    const p2, 0x7f131117

    .line 35
    .line 36
    .line 37
    goto :goto_0

    .line 38
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/qs/tileimpl/QSTileImpl;->mContext:Landroid/content/Context;

    .line 39
    .line 40
    const p2, 0x7f131116

    .line 41
    .line 42
    .line 43
    :goto_0
    invoke-virtual {p0, p2}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 44
    .line 45
    .line 46
    move-result-object p0

    .line 47
    invoke-virtual {p1, p0}, Landroid/widget/CompoundButton;->announceForAccessibility(Ljava/lang/CharSequence;)V

    .line 48
    .line 49
    .line 50
    return-void
.end method