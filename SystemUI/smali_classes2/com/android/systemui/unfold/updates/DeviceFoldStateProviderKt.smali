.class public abstract Lcom/android/systemui/unfold/updates/DeviceFoldStateProviderKt;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final DEBUG:Z


# direct methods
.method public static constructor <clinit>()V
    .locals 2

    .line 1
    const-string v0, "DeviceFoldProvider"

    .line 2
    .line 3
    const/4 v1, 0x3

    .line 4
    invoke-static {v0, v1}, Landroid/util/Log;->isLoggable(Ljava/lang/String;I)Z

    .line 5
    .line 6
    .line 7
    move-result v0

    .line 8
    sput-boolean v0, Lcom/android/systemui/unfold/updates/DeviceFoldStateProviderKt;->DEBUG:Z

    .line 9
    .line 10
    return-void
.end method

.method public static synthetic getFULLY_OPEN_THRESHOLD_DEGREES$annotations()V
    .locals 0

    .line 1
    return-void
.end method

.method public static synthetic getHINGE_ANGLE_CHANGE_THRESHOLD_DEGREES$annotations()V
    .locals 0

    .line 1
    return-void
.end method

.method public static synthetic getSTART_CLOSING_ON_APPS_THRESHOLD_DEGREES$annotations()V
    .locals 0

    .line 1
    return-void
.end method

.method public static final name(I)Ljava/lang/String;
    .locals 1

    .line 1
    if-eqz p0, :cond_4

    .line 2
    .line 3
    const/4 v0, 0x1

    .line 4
    if-eq p0, v0, :cond_3

    .line 5
    .line 6
    const/4 v0, 0x2

    .line 7
    if-eq p0, v0, :cond_2

    .line 8
    .line 9
    const/4 v0, 0x3

    .line 10
    if-eq p0, v0, :cond_1

    .line 11
    .line 12
    const/4 v0, 0x4

    .line 13
    if-eq p0, v0, :cond_0

    .line 14
    .line 15
    const-string p0, "UNKNOWN"

    .line 16
    .line 17
    goto :goto_0

    .line 18
    :cond_0
    const-string p0, "FINISH_CLOSED"

    .line 19
    .line 20
    goto :goto_0

    .line 21
    :cond_1
    const-string p0, "FINISH_FULL_OPEN"

    .line 22
    .line 23
    goto :goto_0

    .line 24
    :cond_2
    const-string p0, "FINISH_HALF_OPEN"

    .line 25
    .line 26
    goto :goto_0

    .line 27
    :cond_3
    const-string p0, "START_CLOSING"

    .line 28
    .line 29
    goto :goto_0

    .line 30
    :cond_4
    const-string p0, "START_OPENING"

    .line 31
    .line 32
    :goto_0
    return-object p0
.end method
