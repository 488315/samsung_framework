.class public final enum Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/knox/net/wifi/WifiAdminProfile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "PolicyState"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

.field public static final enum DEFAULT:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

.field public static final enum FALSE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

.field public static final enum TRUE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;


# direct methods
.method public static synthetic $values()[Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;
    .locals 3

    .line 1
    sget-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->FALSE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->TRUE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 4
    .line 5
    sget-object v2, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->DEFAULT:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 6
    .line 7
    filled-new-array {v0, v1, v2}, [Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    return-object v0
.end method

.method public static constructor <clinit>()V
    .locals 3

    .line 1
    new-instance v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 2
    .line 3
    const-string v1, "FALSE"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->FALSE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 10
    .line 11
    new-instance v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 12
    .line 13
    const-string v1, "TRUE"

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->TRUE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 20
    .line 21
    new-instance v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 22
    .line 23
    const-string v1, "DEFAULT"

    .line 24
    .line 25
    const/4 v2, 0x2

    .line 26
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->DEFAULT:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 30
    .line 31
    invoke-static {}, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->$values()[Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 32
    .line 33
    .line 34
    move-result-object v0

    .line 35
    sput-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->$VALUES:[Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 36
    .line 37
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

.method public static valueOf(I)Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;
    .locals 2

    .line 2
    sget-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->FALSE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    invoke-virtual {v0}, Ljava/lang/Enum;->ordinal()I

    move-result v1

    if-ne v1, p0, :cond_0

    return-object v0

    .line 3
    :cond_0
    sget-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->TRUE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    invoke-virtual {v0}, Ljava/lang/Enum;->ordinal()I

    move-result v1

    if-ne v1, p0, :cond_1

    return-object v0

    .line 4
    :cond_1
    sget-object p0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->DEFAULT:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    return-object p0
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    return-object p0
.end method

.method public static valueOf(Z)Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;
    .locals 1

    const/4 v0, 0x1

    if-ne v0, p0, :cond_0

    .line 5
    sget-object p0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->TRUE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    return-object p0

    .line 6
    :cond_0
    sget-object p0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->FALSE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    return-object p0
.end method

.method public static values()[Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->$VALUES:[Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final valueOf()Z
    .locals 1

    .line 7
    sget-object v0, Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;->TRUE:Lcom/samsung/android/knox/net/wifi/WifiAdminProfile$PolicyState;

    if-ne p0, v0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    const/4 p0, 0x0

    return p0
.end method