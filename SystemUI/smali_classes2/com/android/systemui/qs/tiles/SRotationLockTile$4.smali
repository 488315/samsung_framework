.class public final Lcom/android/systemui/qs/tiles/SRotationLockTile$4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/RotationLockController$RotationLockControllerCallback;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/qs/tiles/SRotationLockTile;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/tiles/SRotationLockTile;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/SRotationLockTile$4;->this$0:Lcom/android/systemui/qs/tiles/SRotationLockTile;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onRotationLockStateChanged(Z)V
    .locals 1

    .line 1
    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 2
    .line 3
    .line 4
    move-result-object p1

    .line 5
    sget v0, Lcom/android/systemui/qs/tiles/SRotationLockTile;->$r8$clinit:I

    .line 6
    .line 7
    iget-object p0, p0, Lcom/android/systemui/qs/tiles/SRotationLockTile$4;->this$0:Lcom/android/systemui/qs/tiles/SRotationLockTile;

    .line 8
    .line 9
    invoke-virtual {p0, p1}, Lcom/android/systemui/qs/tileimpl/QSTileImpl;->refreshState(Ljava/lang/Object;)V

    .line 10
    .line 11
    .line 12
    return-void
.end method