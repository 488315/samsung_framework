.class public final enum Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ModelInputType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

.field public static final enum BUFFER:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

.field public static final enum FD:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

.field public static final enum FILEPATH:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;


# direct methods
.method public static synthetic $values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;
    .locals 3

    .line 1
    sget-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->FILEPATH:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->FD:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 4
    .line 5
    sget-object v2, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->BUFFER:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 6
    .line 7
    filled-new-array {v0, v1, v2}, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

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
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 2
    .line 3
    const-string v1, "FILEPATH"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->FILEPATH:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 10
    .line 11
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 12
    .line 13
    const-string v1, "FD"

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->FD:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 20
    .line 21
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 22
    .line 23
    const-string v1, "BUFFER"

    .line 24
    .line 25
    const/4 v2, 0x2

    .line 26
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->BUFFER:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 30
    .line 31
    invoke-static {}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->$values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 32
    .line 33
    .line 34
    move-result-object v0

    .line 35
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->$VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

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

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->$VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ModelInputType;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getValue()I
    .locals 0

    .line 1
    invoke-virtual {p0}, Ljava/lang/Enum;->ordinal()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    return p0
.end method