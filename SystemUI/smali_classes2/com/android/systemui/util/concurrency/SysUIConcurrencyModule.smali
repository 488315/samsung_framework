.class public abstract Lcom/android/systemui/util/concurrency/SysUIConcurrencyModule;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final BG_SLOW_DELIVERY_THRESHOLD:Ljava/lang/Long;

.field public static final BG_SLOW_DISPATCH_THRESHOLD:Ljava/lang/Long;

.field public static final BROADCAST_SLOW_DELIVERY_THRESHOLD:Ljava/lang/Long;

.field public static final BROADCAST_SLOW_DISPATCH_THRESHOLD:Ljava/lang/Long;

.field public static final LONG_SLOW_DELIVERY_THRESHOLD:Ljava/lang/Long;

.field public static final LONG_SLOW_DISPATCH_THRESHOLD:Ljava/lang/Long;


# direct methods
.method public static constructor <clinit>()V
    .locals 3

    .line 1
    const-wide/16 v0, 0x3e8

    .line 2
    .line 3
    invoke-static {v0, v1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    sput-object v0, Lcom/android/systemui/util/concurrency/SysUIConcurrencyModule;->BG_SLOW_DISPATCH_THRESHOLD:Ljava/lang/Long;

    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/util/concurrency/SysUIConcurrencyModule;->BG_SLOW_DELIVERY_THRESHOLD:Ljava/lang/Long;

    .line 10
    .line 11
    const-wide/16 v1, 0x9c4

    .line 12
    .line 13
    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 14
    .line 15
    .line 16
    move-result-object v1

    .line 17
    sput-object v1, Lcom/android/systemui/util/concurrency/SysUIConcurrencyModule;->LONG_SLOW_DISPATCH_THRESHOLD:Ljava/lang/Long;

    .line 18
    .line 19
    sput-object v1, Lcom/android/systemui/util/concurrency/SysUIConcurrencyModule;->LONG_SLOW_DELIVERY_THRESHOLD:Ljava/lang/Long;

    .line 20
    .line 21
    sput-object v0, Lcom/android/systemui/util/concurrency/SysUIConcurrencyModule;->BROADCAST_SLOW_DISPATCH_THRESHOLD:Ljava/lang/Long;

    .line 22
    .line 23
    sput-object v0, Lcom/android/systemui/util/concurrency/SysUIConcurrencyModule;->BROADCAST_SLOW_DELIVERY_THRESHOLD:Ljava/lang/Long;

    .line 24
    .line 25
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method