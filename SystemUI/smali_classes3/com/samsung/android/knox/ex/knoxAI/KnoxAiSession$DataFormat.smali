.class public final enum Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "DataFormat"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

.field public static final enum NCHW:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

.field public static final enum NHWC:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;


# instance fields
.field private final value:B


# direct methods
.method public static synthetic $values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;
    .locals 2

    .line 1
    sget-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->NCHW:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->NHWC:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 4
    .line 5
    filled-new-array {v0, v1}, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    return-object v0
.end method

.method public static constructor <clinit>()V
    .locals 3

    .line 1
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 2
    .line 3
    const-string v1, "NCHW"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;-><init>(Ljava/lang/String;II)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->NCHW:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 10
    .line 11
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 12
    .line 13
    const-string v1, "NHWC"

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    invoke-direct {v0, v1, v2, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;-><init>(Ljava/lang/String;II)V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->NHWC:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 20
    .line 21
    invoke-static {}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->$values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 22
    .line 23
    .line 24
    move-result-object v0

    .line 25
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->$VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 26
    .line 27
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;II)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    int-to-byte p1, p3

    .line 5
    iput-byte p1, p0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->value:B

    .line 6
    .line 7
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->$VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getValue()B
    .locals 0

    .line 1
    iget-byte p0, p0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$DataFormat;->value:B

    .line 2
    .line 3
    return p0
.end method