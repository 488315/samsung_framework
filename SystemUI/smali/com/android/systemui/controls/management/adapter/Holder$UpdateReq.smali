.class public final enum Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

.field public static final enum UPDATE_DIM_STATUS:Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;


# direct methods
.method public static constructor <clinit>()V
    .locals 3

    .line 1
    new-instance v0, Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 2
    .line 3
    const-string v1, "UPDATE_DIM_STATUS"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;->UPDATE_DIM_STATUS:Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 10
    .line 11
    filled-new-array {v0}, [Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 12
    .line 13
    .line 14
    move-result-object v0

    .line 15
    sput-object v0, Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;->$VALUES:[Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 16
    .line 17
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;->$VALUES:[Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/controls/management/adapter/Holder$UpdateReq;

    .line 8
    .line 9
    return-object v0
.end method
