.class final Lcom/android/systemui/biometrics/data/repository/RearDisplayStateRepositoryImpl$isInRearDisplayMode$1$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic $callback:Landroid/hardware/devicestate/DeviceStateManager$DeviceStateCallback;

.field final synthetic $deviceStateManager:Landroid/hardware/devicestate/DeviceStateManager;


# direct methods
.method public constructor <init>(Landroid/hardware/devicestate/DeviceStateManager;Landroid/hardware/devicestate/DeviceStateManager$DeviceStateCallback;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/biometrics/data/repository/RearDisplayStateRepositoryImpl$isInRearDisplayMode$1$1;->$deviceStateManager:Landroid/hardware/devicestate/DeviceStateManager;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/biometrics/data/repository/RearDisplayStateRepositoryImpl$isInRearDisplayMode$1$1;->$callback:Landroid/hardware/devicestate/DeviceStateManager$DeviceStateCallback;

    .line 4
    .line 5
    const/4 p1, 0x0

    .line 6
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/biometrics/data/repository/RearDisplayStateRepositoryImpl$isInRearDisplayMode$1$1;->$deviceStateManager:Landroid/hardware/devicestate/DeviceStateManager;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/biometrics/data/repository/RearDisplayStateRepositoryImpl$isInRearDisplayMode$1$1;->$callback:Landroid/hardware/devicestate/DeviceStateManager$DeviceStateCallback;

    .line 4
    .line 5
    invoke-virtual {v0, p0}, Landroid/hardware/devicestate/DeviceStateManager;->unregisterCallback(Landroid/hardware/devicestate/DeviceStateManager$DeviceStateCallback;)V

    .line 6
    .line 7
    .line 8
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 9
    .line 10
    return-object p0
.end method