.class public final enum Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/knox/container/KnoxContainerManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ConfigType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

.field public static final enum BBC:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

.field public static final enum KIOSK:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

.field public static final enum LAUNCHER:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

.field public static final enum LIGHTWEIGHT:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

.field public static final enum SECUREFOLDER:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;


# instance fields
.field private final mTypeString:Ljava/lang/String;


# direct methods
.method public static synthetic $values()[Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;
    .locals 5

    .line 1
    sget-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->LIGHTWEIGHT:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->KIOSK:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 4
    .line 5
    sget-object v2, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->LAUNCHER:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 6
    .line 7
    sget-object v3, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->BBC:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 8
    .line 9
    sget-object v4, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->SECUREFOLDER:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 10
    .line 11
    filled-new-array {v0, v1, v2, v3, v4}, [Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 12
    .line 13
    .line 14
    move-result-object v0

    .line 15
    return-object v0
.end method

.method public static constructor <clinit>()V
    .locals 4

    .line 1
    new-instance v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const-string v2, "lightweight"

    .line 5
    .line 6
    const-string v3, "LIGHTWEIGHT"

    .line 7
    .line 8
    invoke-direct {v0, v3, v1, v2}, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    .line 9
    .line 10
    .line 11
    sput-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->LIGHTWEIGHT:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 12
    .line 13
    new-instance v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 14
    .line 15
    const/4 v1, 0x1

    .line 16
    const-string v2, "kiosk"

    .line 17
    .line 18
    const-string v3, "KIOSK"

    .line 19
    .line 20
    invoke-direct {v0, v3, v1, v2}, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    .line 21
    .line 22
    .line 23
    sput-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->KIOSK:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 24
    .line 25
    new-instance v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 26
    .line 27
    const/4 v1, 0x2

    .line 28
    const-string v2, "launcher"

    .line 29
    .line 30
    const-string v3, "LAUNCHER"

    .line 31
    .line 32
    invoke-direct {v0, v3, v1, v2}, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    .line 33
    .line 34
    .line 35
    sput-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->LAUNCHER:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 36
    .line 37
    new-instance v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 38
    .line 39
    const/4 v1, 0x3

    .line 40
    const-string v2, "bbc"

    .line 41
    .line 42
    const-string v3, "BBC"

    .line 43
    .line 44
    invoke-direct {v0, v3, v1, v2}, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    .line 45
    .line 46
    .line 47
    sput-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->BBC:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 48
    .line 49
    new-instance v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 50
    .line 51
    const/4 v1, 0x4

    .line 52
    const-string v2, "securefolder"

    .line 53
    .line 54
    const-string v3, "SECUREFOLDER"

    .line 55
    .line 56
    invoke-direct {v0, v3, v1, v2}, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    .line 57
    .line 58
    .line 59
    sput-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->SECUREFOLDER:Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 60
    .line 61
    invoke-static {}, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->$values()[Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 62
    .line 63
    .line 64
    move-result-object v0

    .line 65
    sput-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->$VALUES:[Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 66
    .line 67
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;ILjava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    iput-object p3, p0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->mTypeString:Ljava/lang/String;

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->$VALUES:[Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getType(Ljava/lang/String;)Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;
    .locals 4

    .line 1
    invoke-static {}, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->values()[Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    array-length v0, p0

    .line 6
    const/4 v1, 0x0

    .line 7
    :goto_0
    if-ge v1, v0, :cond_1

    .line 8
    .line 9
    aget-object v2, p0, v1

    .line 10
    .line 11
    iget-object v3, v2, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->mTypeString:Ljava/lang/String;

    .line 12
    .line 13
    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    .line 14
    .line 15
    .line 16
    move-result v3

    .line 17
    if-eqz v3, :cond_0

    .line 18
    .line 19
    return-object v2

    .line 20
    :cond_0
    add-int/lit8 v1, v1, 0x1

    .line 21
    .line 22
    goto :goto_0

    .line 23
    :cond_1
    const/4 p0, 0x0

    .line 24
    return-object p0
.end method

.method public final toString()Ljava/lang/String;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/container/KnoxContainerManager$ConfigType;->mTypeString:Ljava/lang/String;

    .line 2
    .line 3
    return-object p0
.end method
