.class public final enum Lcom/samsung/android/knox/cmfa/AuthActionType;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/Parcelable;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/android/knox/cmfa/AuthActionType;",
        ">;",
        "Landroid/os/Parcelable;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/samsung/android/knox/cmfa/AuthActionType;

.field public static final enum CONTAINER_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

.field public static final enum CONTAINER_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lcom/samsung/android/knox/cmfa/AuthActionType;",
            ">;"
        }
    .end annotation
.end field

.field public static final enum LAPTOP_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

.field public static final enum LAPTOP_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

.field public static final enum PHONE_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

.field public static final enum PHONE_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;


# direct methods
.method public static synthetic $values()[Lcom/samsung/android/knox/cmfa/AuthActionType;
    .locals 6

    .line 1
    sget-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->PHONE_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/android/knox/cmfa/AuthActionType;->PHONE_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 4
    .line 5
    sget-object v2, Lcom/samsung/android/knox/cmfa/AuthActionType;->CONTAINER_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 6
    .line 7
    sget-object v3, Lcom/samsung/android/knox/cmfa/AuthActionType;->CONTAINER_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 8
    .line 9
    sget-object v4, Lcom/samsung/android/knox/cmfa/AuthActionType;->LAPTOP_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 10
    .line 11
    sget-object v5, Lcom/samsung/android/knox/cmfa/AuthActionType;->LAPTOP_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 12
    .line 13
    filled-new-array/range {v0 .. v5}, [Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 14
    .line 15
    .line 16
    move-result-object v0

    .line 17
    return-object v0
.end method

.method public static constructor <clinit>()V
    .locals 3

    .line 1
    new-instance v0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 2
    .line 3
    const-string v1, "PHONE_LOCK"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/cmfa/AuthActionType;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->PHONE_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 10
    .line 11
    new-instance v0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 12
    .line 13
    const-string v1, "PHONE_UNLOCK"

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/cmfa/AuthActionType;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->PHONE_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 20
    .line 21
    new-instance v0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 22
    .line 23
    const-string v1, "CONTAINER_LOCK"

    .line 24
    .line 25
    const/4 v2, 0x2

    .line 26
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/cmfa/AuthActionType;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->CONTAINER_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 30
    .line 31
    new-instance v0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 32
    .line 33
    const-string v1, "CONTAINER_UNLOCK"

    .line 34
    .line 35
    const/4 v2, 0x3

    .line 36
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/cmfa/AuthActionType;-><init>(Ljava/lang/String;I)V

    .line 37
    .line 38
    .line 39
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->CONTAINER_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 40
    .line 41
    new-instance v0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 42
    .line 43
    const-string v1, "LAPTOP_LOCK"

    .line 44
    .line 45
    const/4 v2, 0x4

    .line 46
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/cmfa/AuthActionType;-><init>(Ljava/lang/String;I)V

    .line 47
    .line 48
    .line 49
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->LAPTOP_LOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 50
    .line 51
    new-instance v0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 52
    .line 53
    const-string v1, "LAPTOP_UNLOCK"

    .line 54
    .line 55
    const/4 v2, 0x5

    .line 56
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/cmfa/AuthActionType;-><init>(Ljava/lang/String;I)V

    .line 57
    .line 58
    .line 59
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->LAPTOP_UNLOCK:Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 60
    .line 61
    invoke-static {}, Lcom/samsung/android/knox/cmfa/AuthActionType;->$values()[Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 62
    .line 63
    .line 64
    move-result-object v0

    .line 65
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->$VALUES:[Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 66
    .line 67
    new-instance v0, Lcom/samsung/android/knox/cmfa/AuthActionType$1;

    .line 68
    .line 69
    invoke-direct {v0}, Lcom/samsung/android/knox/cmfa/AuthActionType$1;-><init>()V

    .line 70
    .line 71
    .line 72
    sput-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->CREATOR:Landroid/os/Parcelable$Creator;

    .line 73
    .line 74
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

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/android/knox/cmfa/AuthActionType;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/samsung/android/knox/cmfa/AuthActionType;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/android/knox/cmfa/AuthActionType;->$VALUES:[Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/samsung/android/knox/cmfa/AuthActionType;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/android/knox/cmfa/AuthActionType;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final describeContents()I
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final writeToParcel(Landroid/os/Parcel;I)V
    .locals 0

    .line 1
    invoke-virtual {p0}, Ljava/lang/Enum;->name()Ljava/lang/String;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    invoke-virtual {p1, p0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    return-void
.end method
