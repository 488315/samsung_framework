.class public final Lcom/android/systemui/power/SecPowerNotificationWarnings$5;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/power/SecPowerNotificationWarnings;


# direct methods
.method public constructor <init>(Lcom/android/systemui/power/SecPowerNotificationWarnings;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/power/SecPowerNotificationWarnings$5;->this$0:Lcom/android/systemui/power/SecPowerNotificationWarnings;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/power/SecPowerNotificationWarnings$5;->this$0:Lcom/android/systemui/power/SecPowerNotificationWarnings;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/power/SecPowerNotificationWarnings;->showBatteryHealthInterruptionPopUp()V

    .line 4
    .line 5
    .line 6
    return-void
.end method
