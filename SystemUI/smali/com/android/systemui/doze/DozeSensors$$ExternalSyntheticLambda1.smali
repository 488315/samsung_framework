.class public final synthetic Lcom/android/systemui/doze/DozeSensors$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/util/sensors/ThresholdSensor$Listener;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/doze/DozeSensors;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/doze/DozeSensors;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/doze/DozeSensors$$ExternalSyntheticLambda1;->f$0:Lcom/android/systemui/doze/DozeSensors;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onThresholdCrossed(Lcom/android/systemui/util/sensors/ThresholdSensorEvent;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/doze/DozeSensors$$ExternalSyntheticLambda1;->f$0:Lcom/android/systemui/doze/DozeSensors;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    if-eqz p1, :cond_0

    .line 7
    .line 8
    iget-boolean p1, p1, Lcom/android/systemui/util/sensors/ThresholdSensorEvent;->mBelow:Z

    .line 9
    .line 10
    xor-int/lit8 p1, p1, 0x1

    .line 11
    .line 12
    invoke-static {p1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 13
    .line 14
    .line 15
    move-result-object p1

    .line 16
    iget-object p0, p0, Lcom/android/systemui/doze/DozeSensors;->mProxCallback:Ljava/util/function/Consumer;

    .line 17
    .line 18
    invoke-interface {p0, p1}, Ljava/util/function/Consumer;->accept(Ljava/lang/Object;)V

    .line 19
    .line 20
    .line 21
    :cond_0
    return-void
.end method
