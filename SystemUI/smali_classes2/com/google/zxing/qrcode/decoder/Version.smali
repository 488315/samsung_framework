.class public final Lcom/google/zxing/qrcode/decoder/Version;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final VERSIONS:[Lcom/google/zxing/qrcode/decoder/Version;


# instance fields
.field public final ecBlocks:[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

.field public final totalCodewords:I

.field public final versionNumber:I


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    invoke-static {}, Lcom/google/zxing/qrcode/decoder/Version;->buildVersions()[Lcom/google/zxing/qrcode/decoder/Version;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    sput-object v0, Lcom/google/zxing/qrcode/decoder/Version;->VERSIONS:[Lcom/google/zxing/qrcode/decoder/Version;

    .line 6
    .line 7
    return-void
.end method

.method private varargs constructor <init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V
    .locals 4

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput p1, p0, Lcom/google/zxing/qrcode/decoder/Version;->versionNumber:I

    .line 5
    .line 6
    iput-object p3, p0, Lcom/google/zxing/qrcode/decoder/Version;->ecBlocks:[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    .line 7
    .line 8
    const/4 p1, 0x0

    .line 9
    aget-object p2, p3, p1

    .line 10
    .line 11
    iget p3, p2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;->ecCodewordsPerBlock:I

    .line 12
    .line 13
    iget-object p2, p2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;->ecBlocks:[Lcom/google/zxing/qrcode/decoder/Version$ECB;

    .line 14
    .line 15
    array-length v0, p2

    .line 16
    move v1, p1

    .line 17
    :goto_0
    if-ge p1, v0, :cond_0

    .line 18
    .line 19
    aget-object v2, p2, p1

    .line 20
    .line 21
    iget v3, v2, Lcom/google/zxing/qrcode/decoder/Version$ECB;->count:I

    .line 22
    .line 23
    iget v2, v2, Lcom/google/zxing/qrcode/decoder/Version$ECB;->dataCodewords:I

    .line 24
    .line 25
    add-int/2addr v2, p3

    .line 26
    mul-int/2addr v2, v3

    .line 27
    add-int/2addr v1, v2

    .line 28
    add-int/lit8 p1, p1, 0x1

    .line 29
    .line 30
    goto :goto_0

    .line 31
    :cond_0
    iput v1, p0, Lcom/google/zxing/qrcode/decoder/Version;->totalCodewords:I

    .line 32
    .line 33
    return-void
.end method

.method public static buildVersions()[Lcom/google/zxing/qrcode/decoder/Version;
    .locals 50

    .line 1
    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version;

    move-object v0, v1

    const/4 v2, 0x0

    new-array v2, v2, [I

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v15, 0x1

    const/16 v14, 0x13

    invoke-direct {v4, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/4 v13, 0x7

    invoke-direct {v3, v13, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v12, 0x10

    invoke-direct {v5, v15, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v11, 0xa

    invoke-direct {v4, v11, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v10, 0xd

    invoke-direct {v6, v15, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    invoke-direct {v5, v10, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x9

    invoke-direct {v7, v15, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v7

    const/16 v9, 0x11

    invoke-direct {v6, v9, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v3, v4, v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v3

    invoke-direct {v1, v15, v2, v3}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version;

    move-object v1, v2

    const/16 v3, 0x12

    const/4 v8, 0x6

    filled-new-array {v8, v3}, [I

    move-result-object v3

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x22

    invoke-direct {v5, v15, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    invoke-direct {v4, v11, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x1c

    invoke-direct {v6, v15, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    invoke-direct {v5, v12, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v13, 0x16

    invoke-direct {v11, v15, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v11}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v11

    invoke-direct {v6, v13, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    invoke-direct {v14, v15, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v14

    invoke-direct {v11, v7, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v4, v5, v6, v11}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v4

    const/4 v14, 0x2

    invoke-direct {v2, v14, v3, v4}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version;

    move-object v2, v3

    filled-new-array {v8, v13}, [I

    move-result-object v4

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v11, 0x37

    invoke-direct {v6, v15, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v11, 0xf

    invoke-direct {v5, v11, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v11, 0x2c

    invoke-direct {v7, v15, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v7

    const/16 v11, 0x1a

    invoke-direct {v6, v11, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    invoke-direct {v12, v14, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    const/16 v9, 0x12

    invoke-direct {v7, v9, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    invoke-direct {v12, v14, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    invoke-direct {v9, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v5, v6, v7, v9}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v5

    const/4 v12, 0x3

    invoke-direct {v3, v12, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version;

    move-object v3, v4

    filled-new-array {v8, v11}, [I

    move-result-object v5

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x50

    invoke-direct {v7, v15, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v7

    const/16 v9, 0x14

    invoke-direct {v6, v9, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v12, 0x20

    invoke-direct {v9, v14, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v9}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v9

    const/16 v12, 0x12

    invoke-direct {v7, v12, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v10, 0x18

    invoke-direct {v12, v14, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    invoke-direct {v9, v11, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v10, 0x9

    const/4 v14, 0x4

    invoke-direct {v13, v14, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v10

    const/16 v13, 0x10

    invoke-direct {v12, v13, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v6, v7, v9, v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v6

    invoke-direct {v4, v14, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version;

    move-object v4, v5

    const/16 v13, 0x1e

    filled-new-array {v8, v13}, [I

    move-result-object v6

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v10, 0x6c

    invoke-direct {v9, v15, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v9}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v9

    invoke-direct {v7, v11, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v10, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v12, 0x2b

    const/4 v13, 0x2

    invoke-direct {v10, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v10}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v10

    const/16 v12, 0x18

    invoke-direct {v9, v12, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v10, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v11, 0xf

    invoke-direct {v12, v13, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x10

    invoke-direct {v11, v13, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12, v11}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v11

    const/16 v12, 0x12

    invoke-direct {v10, v12, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0xb

    invoke-direct {v12, v13, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0xc

    invoke-direct {v15, v13, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12, v15}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    const/16 v13, 0x16

    invoke-direct {v11, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v7, v9, v10, v11}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v7

    const/4 v15, 0x5

    invoke-direct {v5, v15, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version;

    move-object v5, v6

    const/16 v7, 0x22

    filled-new-array {v8, v7}, [I

    move-result-object v7

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v10, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v11, 0x44

    const/4 v12, 0x2

    invoke-direct {v10, v12, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v10}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v10

    const/16 v11, 0x12

    invoke-direct {v9, v11, v10}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v10, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v12, 0x1b

    const/4 v13, 0x4

    invoke-direct {v11, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v11}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v11

    const/16 v12, 0x10

    invoke-direct {v10, v12, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x13

    invoke-direct {v12, v13, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    const/16 v14, 0x18

    invoke-direct {v11, v14, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0xf

    invoke-direct {v14, v13, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v13

    const/16 v14, 0x1c

    invoke-direct {v12, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v9, v10, v11, v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v9

    invoke-direct {v6, v8, v7, v9}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version;

    move-object v6, v7

    const/16 v9, 0x26

    const/16 v10, 0x16

    filled-new-array {v8, v10, v9}, [I

    move-result-object v9

    new-instance v10, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v12, 0x4e

    const/4 v13, 0x2

    invoke-direct {v11, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v11}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v11

    const/16 v12, 0x14

    invoke-direct {v10, v12, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v13, 0x1f

    const/4 v15, 0x4

    invoke-direct {v12, v15, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    const/16 v13, 0x12

    invoke-direct {v11, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xe

    const/4 v14, 0x2

    invoke-direct {v13, v14, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xf

    invoke-direct {v14, v15, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v8

    const/16 v13, 0x12

    invoke-direct {v12, v13, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0xd

    invoke-direct {v13, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v15, 0x1

    move-object/from16 v40, v0

    const/16 v0, 0xe

    invoke-direct {v14, v15, v0}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v0

    const/16 v13, 0x1a

    invoke-direct {v8, v13, v0}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v10, v11, v12, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v0

    const/4 v8, 0x7

    invoke-direct {v7, v8, v9, v0}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/16 v15, 0x1c

    move-object v7, v0

    const/16 v8, 0x2a

    const/4 v9, 0x6

    const/16 v10, 0x18

    filled-new-array {v9, v10, v8}, [I

    move-result-object v8

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v13, 0x61

    const/4 v14, 0x2

    invoke-direct {v12, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    invoke-direct {v11, v10, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v10, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v13, 0x26

    invoke-direct {v12, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x27

    invoke-direct {v13, v14, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12, v13}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v9

    const/16 v12, 0x16

    invoke-direct {v10, v12, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x12

    const/4 v12, 0x4

    invoke-direct {v13, v12, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v12, 0x13

    invoke-direct {v15, v14, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v15}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    const/16 v13, 0x16

    invoke-direct {v9, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0xe

    const/4 v15, 0x4

    invoke-direct {v13, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v41, v1

    const/16 v1, 0xf

    const/4 v14, 0x2

    invoke-direct {v15, v14, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v15}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v13, 0x1a

    invoke-direct {v12, v13, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v11, v10, v9, v12}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v9, 0x8

    invoke-direct {v0, v9, v8, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/4 v1, 0x6

    const/16 v15, 0xe

    move-object v8, v0

    const/16 v14, 0x2e

    filled-new-array {v1, v13, v14}, [I

    move-result-object v9

    new-instance v10, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v12, 0x74

    const/4 v13, 0x2

    invoke-direct {v11, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v11}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v11

    const/16 v12, 0x1e

    invoke-direct {v10, v12, v11}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x24

    const/4 v15, 0x3

    invoke-direct {v12, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x25

    invoke-direct {v14, v13, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    const/16 v13, 0x16

    invoke-direct {v11, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x10

    const/4 v15, 0x4

    invoke-direct {v13, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v1, 0x11

    invoke-direct {v14, v15, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v13

    const/16 v14, 0x14

    invoke-direct {v12, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v1, 0xc

    invoke-direct {v14, v15, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v42, v2

    const/16 v2, 0xd

    invoke-direct {v1, v15, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v14, v1}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v14, 0x18

    invoke-direct {v13, v14, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v10, v11, v12, v13}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v10, 0x9

    invoke-direct {v0, v10, v9, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/16 v1, 0x11

    move-object v9, v0

    const/16 v10, 0x32

    const/4 v11, 0x6

    const/16 v12, 0x1c

    filled-new-array {v11, v12, v10}, [I

    move-result-object v10

    new-instance v11, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v13, 0x44

    const/4 v14, 0x2

    invoke-direct {v12, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x45

    invoke-direct {v13, v14, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v12, v13}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v12

    const/16 v13, 0x12

    invoke-direct {v11, v13, v12}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x2b

    const/4 v15, 0x4

    invoke-direct {v13, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x2c

    const/4 v2, 0x1

    invoke-direct {v14, v2, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v2

    const/16 v13, 0x1a

    invoke-direct {v12, v13, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v13, 0x6

    const/16 v15, 0x13

    invoke-direct {v14, v13, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v1, 0x14

    const/4 v13, 0x2

    invoke-direct {v15, v13, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v14, v15}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v14, 0x18

    invoke-direct {v2, v14, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v13, 0xf

    const/4 v14, 0x6

    invoke-direct {v15, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v43, v3

    const/4 v3, 0x2

    const/16 v14, 0x10

    invoke-direct {v13, v3, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v15, v13}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v13, 0x1c

    invoke-direct {v1, v13, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v11, v12, v2, v1}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v2, 0xa

    invoke-direct {v0, v2, v10, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/16 v1, 0xd

    const/16 v3, 0x18

    move-object v10, v0

    const/16 v11, 0x36

    const/4 v12, 0x6

    const/16 v13, 0x1e

    filled-new-array {v12, v13, v11}, [I

    move-result-object v11

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x51

    const/4 v2, 0x4

    invoke-direct {v13, v2, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v13

    const/16 v15, 0x14

    invoke-direct {v12, v15, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x32

    const/4 v3, 0x1

    invoke-direct {v15, v3, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x33

    invoke-direct {v3, v2, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v15, v3}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v14, 0x1e

    invoke-direct {v13, v14, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x16

    invoke-direct {v15, v2, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v1, 0x17

    invoke-direct {v14, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v15, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v2, 0x1c

    invoke-direct {v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0xc

    const/4 v15, 0x3

    invoke-direct {v2, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x8

    move-object/from16 v44, v4

    const/16 v4, 0xd

    invoke-direct {v14, v15, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v2, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v2

    const/16 v4, 0x18

    invoke-direct {v1, v4, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v12, v13, v3, v1}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v2, 0xb

    invoke-direct {v0, v2, v11, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/16 v1, 0xa

    const/16 v2, 0xf

    const/16 v3, 0x1a

    move-object v11, v0

    const/16 v4, 0x20

    const/16 v12, 0x3a

    const/4 v13, 0x6

    filled-new-array {v13, v4, v12}, [I

    move-result-object v4

    new-instance v12, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x5c

    const/4 v15, 0x2

    invoke-direct {v13, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v1, 0x5d

    invoke-direct {v14, v15, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v13, 0x18

    invoke-direct {v12, v13, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x24

    const/4 v2, 0x6

    invoke-direct {v13, v2, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x25

    invoke-direct {v2, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v2}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v2

    const/16 v13, 0x16

    invoke-direct {v1, v13, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x14

    const/4 v15, 0x4

    invoke-direct {v13, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x15

    const/4 v3, 0x6

    invoke-direct {v14, v3, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v13, 0x1a

    invoke-direct {v2, v13, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v14, 0x7

    const/16 v15, 0xe

    invoke-direct {v13, v14, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v45, v5

    const/4 v5, 0x4

    const/16 v14, 0xf

    invoke-direct {v15, v5, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v15}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v15, 0x1c

    invoke-direct {v3, v15, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v12, v1, v2, v3}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v2, 0xc

    invoke-direct {v0, v2, v4, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/16 v1, 0x10

    const/4 v2, 0x3

    move-object v12, v0

    const/16 v3, 0x22

    const/16 v4, 0x3e

    const/4 v5, 0x6

    filled-new-array {v5, v3, v4}, [I

    move-result-object v3

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v13, 0x6b

    const/4 v14, 0x4

    invoke-direct {v5, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v13, 0x1a

    invoke-direct {v4, v13, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x8

    const/16 v15, 0x25

    invoke-direct {v13, v14, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x26

    const/4 v1, 0x1

    invoke-direct {v14, v1, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v13, 0x16

    invoke-direct {v5, v13, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x8

    const/16 v15, 0x14

    invoke-direct {v13, v14, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x15

    const/4 v2, 0x4

    invoke-direct {v14, v2, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v13, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v13

    const/16 v14, 0x18

    invoke-direct {v1, v14, v13}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v13, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0xc

    const/16 v2, 0xb

    invoke-direct {v14, v15, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v46, v6

    const/4 v6, 0x4

    invoke-direct {v2, v6, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v14, v2}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v2

    const/16 v6, 0x16

    invoke-direct {v13, v6, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v4, v5, v1, v13}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v2, 0xd

    invoke-direct {v0, v2, v3, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move v2, v6

    const/4 v1, 0x7

    const/16 v3, 0x1e

    move-object v13, v0

    const/16 v4, 0x42

    const/4 v5, 0x6

    const/16 v6, 0x1a

    const/16 v14, 0x2e

    filled-new-array {v5, v6, v14, v4}, [I

    move-result-object v4

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x73

    const/4 v14, 0x3

    invoke-direct {v6, v14, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x74

    const/4 v1, 0x1

    invoke-direct {v14, v1, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    invoke-direct {v5, v3, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v14, 0x28

    const/4 v15, 0x4

    invoke-direct {v6, v15, v14}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x29

    const/4 v3, 0x5

    invoke-direct {v14, v3, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6, v14}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v14, 0x18

    invoke-direct {v1, v14, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0xb

    const/16 v2, 0x10

    invoke-direct {v14, v15, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x11

    invoke-direct {v2, v3, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v14, v2}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v2

    const/16 v14, 0x14

    invoke-direct {v6, v14, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v14, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0xb

    const/16 v3, 0xc

    invoke-direct {v14, v15, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v47, v7

    const/4 v7, 0x5

    const/16 v15, 0xd

    invoke-direct {v3, v7, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v14, v3}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v7, 0x18

    invoke-direct {v2, v7, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v5, v1, v6, v2}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v2, 0xe

    invoke-direct {v0, v2, v4, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/16 v1, 0x13

    const/4 v3, 0x2

    const/4 v4, 0x4

    const/16 v5, 0x2e

    move-object v14, v0

    const/16 v6, 0x30

    const/16 v7, 0x46

    const/16 v2, 0x1a

    const/4 v15, 0x6

    filled-new-array {v15, v2, v6, v7}, [I

    move-result-object v6

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x57

    const/4 v4, 0x5

    invoke-direct {v7, v4, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v3, 0x58

    const/4 v1, 0x1

    invoke-direct {v15, v1, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v15}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v7, 0x16

    invoke-direct {v2, v7, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v15, 0x29

    invoke-direct {v7, v4, v15}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v1, 0x2a

    invoke-direct {v15, v4, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v15}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v7, 0x18

    invoke-direct {v3, v7, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    invoke-direct {v15, v4, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x19

    const/4 v5, 0x7

    invoke-direct {v7, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v15, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v7

    const/16 v15, 0x1e

    invoke-direct {v1, v15, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v15, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0xb

    const/16 v5, 0xc

    invoke-direct {v15, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v48, v8

    const/4 v5, 0x7

    const/16 v8, 0xd

    invoke-direct {v4, v5, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v15, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x18

    invoke-direct {v7, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v1, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v2, 0xf

    invoke-direct {v0, v2, v6, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    const/4 v1, 0x1

    const/16 v2, 0x1c

    const/4 v3, 0x5

    const/16 v4, 0xe

    move-object v15, v0

    const/16 v5, 0x32

    const/16 v6, 0x4a

    const/4 v7, 0x6

    const/16 v8, 0x1a

    filled-new-array {v7, v8, v5, v6}, [I

    move-result-object v5

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x62

    invoke-direct {v7, v3, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x63

    invoke-direct {v8, v1, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v7, 0x18

    invoke-direct {v6, v7, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x2d

    const/4 v3, 0x7

    invoke-direct {v7, v3, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v1, 0x2e

    const/4 v8, 0x3

    invoke-direct {v3, v8, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v3}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    invoke-direct {v4, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x13

    const/16 v8, 0xf

    invoke-direct {v3, v8, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v2, 0x14

    const/4 v8, 0x2

    invoke-direct {v7, v8, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v2

    const/16 v3, 0x18

    invoke-direct {v1, v3, v2}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v7, 0x3

    const/16 v8, 0xf

    invoke-direct {v3, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-object/from16 v49, v9

    const/16 v8, 0x10

    const/16 v9, 0xd

    invoke-direct {v7, v9, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v7, 0x1e

    invoke-direct {v2, v7, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v6, v4, v1, v2}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    invoke-direct {v0, v8, v5, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v16, v0

    const/16 v1, 0x36

    const/16 v2, 0x4e

    const/4 v3, 0x6

    filled-new-array {v3, v7, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x6b

    const/4 v5, 0x1

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x6c

    const/4 v7, 0x5

    invoke-direct {v4, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1c

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0xa

    const/16 v8, 0x2e

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x2f

    invoke-direct {v7, v5, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    invoke-direct {v3, v4, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x16

    invoke-direct {v7, v5, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x17

    const/16 v8, 0xf

    invoke-direct {v5, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    invoke-direct {v6, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0xe

    const/4 v9, 0x2

    invoke-direct {v7, v9, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x11

    invoke-direct {v4, v9, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v7, 0x1c

    invoke-direct {v5, v7, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v6, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    invoke-direct {v0, v9, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v17, v0

    const/16 v1, 0x38

    const/16 v2, 0x52

    const/4 v3, 0x6

    const/16 v4, 0x1e

    filled-new-array {v3, v4, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x78

    const/4 v6, 0x5

    invoke-direct {v3, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x79

    const/4 v7, 0x1

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x9

    const/16 v6, 0x2b

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2c

    const/4 v7, 0x4

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1a

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x11

    const/16 v7, 0x16

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x17

    const/4 v8, 0x1

    invoke-direct {v6, v8, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1c

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v8, 0x2

    const/16 v9, 0xe

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xf

    const/16 v9, 0x13

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1c

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x12

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v18, v0

    const/16 v1, 0x3a

    const/16 v2, 0x56

    const/4 v3, 0x6

    const/16 v4, 0x1e

    filled-new-array {v3, v4, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x71

    const/4 v5, 0x3

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x72

    const/4 v7, 0x4

    invoke-direct {v4, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1c

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2c

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xb

    const/16 v7, 0x2d

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1a

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x15

    const/16 v8, 0x11

    invoke-direct {v6, v8, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x16

    const/4 v9, 0x4

    invoke-direct {v7, v9, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x9

    const/16 v9, 0xd

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0xe

    const/16 v9, 0x10

    invoke-direct {v8, v9, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v7, 0x1a

    invoke-direct {v6, v7, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x13

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v19, v0

    const/16 v1, 0x3e

    const/16 v2, 0x5a

    const/16 v3, 0x22

    const/4 v4, 0x6

    filled-new-array {v4, v3, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x6b

    const/4 v5, 0x3

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x6c

    const/4 v7, 0x5

    invoke-direct {v4, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1c

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x29

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2a

    const/16 v7, 0xd

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1a

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xf

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v8, 0x5

    const/16 v9, 0x19

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v7, 0x1e

    invoke-direct {v4, v7, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    invoke-direct {v7, v6, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xa

    const/16 v9, 0x10

    invoke-direct {v6, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1c

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x14

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v20, v0

    const/16 v1, 0x48

    const/16 v2, 0x5e

    const/16 v3, 0x32

    const/4 v4, 0x6

    filled-new-array {v4, v7, v3, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x74

    const/4 v5, 0x4

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x75

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    invoke-direct {v2, v7, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x2a

    const/16 v6, 0x11

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1a

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x16

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x17

    const/4 v9, 0x6

    invoke-direct {v7, v9, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v7, 0x1c

    invoke-direct {v4, v7, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x10

    const/16 v8, 0x13

    invoke-direct {v7, v8, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x11

    invoke-direct {v6, v9, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x15

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v21, v0

    const/16 v1, 0x4a

    const/16 v2, 0x62

    const/16 v3, 0x32

    const/16 v4, 0x1a

    filled-new-array {v9, v4, v3, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x6f

    const/4 v5, 0x2

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x70

    const/4 v6, 0x7

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1c

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x11

    const/16 v8, 0x2e

    invoke-direct {v5, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    invoke-direct {v3, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x10

    const/16 v9, 0x19

    invoke-direct {v6, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x22

    const/16 v9, 0xd

    invoke-direct {v6, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x16

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v22, v0

    const/16 v1, 0x4e

    const/16 v2, 0x66

    const/16 v3, 0x36

    const/4 v4, 0x6

    const/16 v5, 0x1e

    filled-new-array {v4, v5, v3, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x79

    const/4 v6, 0x4

    invoke-direct {v3, v6, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x7a

    const/4 v8, 0x5

    invoke-direct {v4, v8, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    invoke-direct {v2, v5, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x2f

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x30

    const/16 v7, 0xe

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xb

    const/16 v8, 0x18

    invoke-direct {v5, v6, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xf

    const/16 v9, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    invoke-direct {v6, v7, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v8, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x17

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v23, v0

    const/16 v1, 0x50

    const/16 v2, 0x6a

    const/16 v3, 0x36

    const/4 v4, 0x6

    const/16 v5, 0x1c

    filled-new-array {v4, v5, v3, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x75

    invoke-direct {v3, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x76

    const/4 v7, 0x4

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v5, 0x1e

    invoke-direct {v2, v5, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2d

    invoke-direct {v5, v4, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xe

    const/16 v7, 0x2e

    invoke-direct {v4, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xb

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x10

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    invoke-direct {v8, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v6, 0x2

    const/16 v9, 0x11

    invoke-direct {v7, v6, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v8, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x18

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v24, v0

    const/16 v1, 0x54

    const/16 v2, 0x6e

    const/16 v3, 0x20

    const/16 v4, 0x3a

    const/4 v5, 0x6

    filled-new-array {v5, v3, v4, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x8

    const/16 v5, 0x6a

    invoke-direct {v3, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x6b

    const/4 v6, 0x4

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1a

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x8

    const/16 v6, 0x2f

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x30

    const/16 v7, 0xd

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v6, 0x7

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x16

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xf

    invoke-direct {v9, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xd

    const/16 v8, 0x10

    invoke-direct {v7, v6, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v9, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x19

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v25, v0

    const/16 v1, 0x56

    const/16 v2, 0x72

    const/16 v3, 0x3a

    const/4 v4, 0x6

    filled-new-array {v4, v7, v3, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x72

    const/16 v5, 0xa

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x73

    const/4 v6, 0x2

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1c

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x13

    const/16 v7, 0x2e

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v7, 0x4

    const/16 v8, 0x2f

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    invoke-direct {v3, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x16

    invoke-direct {v6, v4, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x17

    const/4 v9, 0x6

    invoke-direct {v7, v9, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    invoke-direct {v5, v4, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x21

    const/16 v8, 0x10

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x11

    const/4 v9, 0x4

    invoke-direct {v7, v9, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v4, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v5, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x1a

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v26, v0

    const/16 v1, 0x5a

    const/16 v2, 0x76

    const/16 v3, 0x22

    const/16 v4, 0x3e

    const/4 v5, 0x6

    filled-new-array {v5, v3, v4, v1, v2}, [I

    move-result-object v1

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x8

    const/16 v5, 0x7a

    invoke-direct {v3, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x7b

    const/4 v6, 0x4

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x2d

    const/16 v6, 0x16

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v6, 0x3

    const/16 v7, 0x2e

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x8

    const/16 v7, 0x17

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x1a

    const/16 v8, 0x18

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xc

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x1c

    const/16 v9, 0x10

    invoke-direct {v8, v6, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x1b

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v27, v0

    const/4 v1, 0x6

    new-array v2, v1, [I

    fill-array-data v2, :array_0

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x75

    const/4 v5, 0x3

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x76

    const/16 v7, 0xa

    invoke-direct {v4, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2d

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x17

    const/16 v7, 0x2e

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v6, 0x4

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x1f

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xb

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x1f

    const/16 v6, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x1c

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v28, v0

    const/4 v1, 0x6

    new-array v2, v1, [I

    fill-array-data v2, :array_1

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x74

    const/4 v5, 0x7

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x75

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x15

    const/16 v7, 0x2d

    invoke-direct {v4, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x2e

    invoke-direct {v6, v5, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x17

    const/4 v7, 0x1

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x25

    const/16 v8, 0x18

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x13

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x1a

    const/16 v9, 0x10

    invoke-direct {v8, v6, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x1d

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v29, v0

    const/4 v1, 0x6

    new-array v2, v1, [I

    fill-array-data v2, :array_2

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x73

    const/4 v5, 0x5

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x74

    const/16 v6, 0xa

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x13

    const/16 v7, 0x2f

    invoke-direct {v4, v5, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x30

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xf

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x19

    invoke-direct {v7, v8, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v7, 0x1e

    invoke-direct {v4, v7, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v9, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x17

    invoke-direct {v9, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x10

    invoke-direct {v6, v8, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v9, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    invoke-direct {v0, v7, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v30, v0

    const/4 v1, 0x6

    new-array v2, v1, [I

    fill-array-data v2, :array_3

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x73

    const/16 v5, 0xd

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x74

    const/4 v6, 0x3

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v5, 0x2

    const/16 v6, 0x2e

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x1d

    const/16 v7, 0x2f

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2a

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v7, 0x1

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x17

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x1c

    const/16 v9, 0x10

    invoke-direct {v8, v6, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x1f

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v31, v0

    const/4 v1, 0x6

    new-array v2, v1, [I

    fill-array-data v2, :array_4

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x73

    const/16 v5, 0x11

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0xa

    const/16 v6, 0x2e

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x17

    const/16 v8, 0x2f

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v6, 0x1c

    invoke-direct {v3, v6, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x18

    invoke-direct {v6, v5, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x23

    const/16 v8, 0x19

    invoke-direct {v5, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v6, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x13

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x23

    const/16 v6, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x20

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v32, v0

    const/4 v1, 0x6

    new-array v2, v1, [I

    fill-array-data v2, :array_5

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x73

    const/16 v5, 0x11

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x74

    const/4 v6, 0x1

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0xe

    const/16 v6, 0x2e

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x15

    const/16 v7, 0x2f

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x1d

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x13

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xb

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2e

    const/16 v9, 0x10

    invoke-direct {v8, v6, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x21

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v33, v0

    const/4 v1, 0x6

    new-array v2, v1, [I

    fill-array-data v2, :array_6

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x73

    const/16 v6, 0xd

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x74

    invoke-direct {v5, v1, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v4, 0x1e

    invoke-direct {v3, v4, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0xe

    const/16 v6, 0x2e

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x17

    const/16 v7, 0x2f

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v1, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2c

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v7, 0x7

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x3b

    const/16 v9, 0x10

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x11

    const/4 v9, 0x1

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v3, v1, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x22

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v34, v0

    const/4 v1, 0x7

    new-array v2, v1, [I

    fill-array-data v2, :array_7

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0xc

    const/16 v6, 0x79

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x7a

    invoke-direct {v5, v1, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v1

    const/16 v4, 0x1e

    invoke-direct {v3, v4, v1}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0xc

    const/16 v6, 0x2f

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x30

    const/16 v7, 0x1a

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v1, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x27

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0xe

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x16

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x29

    const/16 v6, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v3, v1, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x23

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v35, v0

    const/4 v1, 0x7

    new-array v2, v1, [I

    fill-array-data v2, :array_8

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x79

    const/4 v5, 0x6

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x7a

    const/16 v7, 0xe

    invoke-direct {v4, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2f

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x22

    const/16 v7, 0x30

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x18

    const/16 v7, 0x2e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0xa

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/4 v8, 0x2

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x40

    const/16 v6, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x24

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v36, v0

    const/4 v1, 0x7

    new-array v2, v1, [I

    fill-array-data v2, :array_9

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x7a

    const/16 v5, 0x11

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x7b

    const/4 v6, 0x4

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x1d

    const/16 v6, 0x2e

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0xe

    const/16 v7, 0x2f

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x31

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xa

    const/16 v9, 0x19

    invoke-direct {v6, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0xf

    invoke-direct {v8, v7, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2e

    const/16 v9, 0x10

    invoke-direct {v7, v6, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v8, v7}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x25

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v37, v0

    const/4 v1, 0x7

    new-array v2, v1, [I

    fill-array-data v2, :array_a

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x7a

    const/4 v5, 0x4

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x12

    const/16 v6, 0x7b

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0xd

    const/16 v6, 0x2e

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x20

    const/16 v7, 0x2f

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x30

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0xe

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x2a

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x20

    const/16 v6, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x26

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v38, v0

    const/4 v1, 0x7

    new-array v2, v1, [I

    fill-array-data v2, :array_b

    new-instance v1, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x14

    const/16 v5, 0x75

    invoke-direct {v3, v4, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x76

    const/4 v6, 0x4

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v1, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x28

    const/16 v6, 0x2f

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x30

    const/4 v7, 0x7

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x2b

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x16

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0xa

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x43

    const/16 v6, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v1, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v1

    const/16 v3, 0x27

    invoke-direct {v0, v3, v2, v1}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    new-instance v0, Lcom/google/zxing/qrcode/decoder/Version;

    move-object/from16 v39, v0

    const/4 v1, 0x7

    new-array v1, v1, [I

    fill-array-data v1, :array_c

    new-instance v2, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v4, 0x76

    const/16 v5, 0x13

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x77

    const/4 v6, 0x6

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v3, v4}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-direct {v2, v4, v3}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v3, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v5, 0x12

    const/16 v6, 0x2f

    invoke-direct {v4, v5, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x1f

    const/16 v7, 0x30

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v4

    const/16 v5, 0x1c

    invoke-direct {v3, v5, v4}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v4, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v6, 0x22

    const/16 v7, 0x18

    invoke-direct {v5, v6, v7}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v6, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v7, 0x22

    const/16 v8, 0x19

    invoke-direct {v6, v7, v8}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v5, v6}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v5

    const/16 v6, 0x1e

    invoke-direct {v4, v6, v5}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    new-instance v5, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    new-instance v7, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v8, 0x14

    const/16 v9, 0xf

    invoke-direct {v7, v8, v9}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    new-instance v8, Lcom/google/zxing/qrcode/decoder/Version$ECB;

    const/16 v9, 0x3d

    const/16 v6, 0x10

    invoke-direct {v8, v9, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECB;-><init>(II)V

    filled-new-array {v7, v8}, [Lcom/google/zxing/qrcode/decoder/Version$ECB;

    move-result-object v6

    const/16 v7, 0x1e

    invoke-direct {v5, v7, v6}, Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;-><init>(I[Lcom/google/zxing/qrcode/decoder/Version$ECB;)V

    filled-new-array {v2, v3, v4, v5}, [Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;

    move-result-object v2

    const/16 v3, 0x28

    invoke-direct {v0, v3, v1, v2}, Lcom/google/zxing/qrcode/decoder/Version;-><init>(I[I[Lcom/google/zxing/qrcode/decoder/Version$ECBlocks;)V

    move-object/from16 v0, v40

    move-object/from16 v1, v41

    move-object/from16 v2, v42

    move-object/from16 v3, v43

    move-object/from16 v4, v44

    move-object/from16 v5, v45

    move-object/from16 v6, v46

    move-object/from16 v7, v47

    move-object/from16 v8, v48

    move-object/from16 v9, v49

    filled-new-array/range {v0 .. v39}, [Lcom/google/zxing/qrcode/decoder/Version;

    move-result-object v0

    return-object v0

    nop

    :array_0
    .array-data 4
        0x6
        0x1a
        0x32
        0x4a
        0x62
        0x7a
    .end array-data

    :array_1
    .array-data 4
        0x6
        0x1e
        0x36
        0x4e
        0x66
        0x7e
    .end array-data

    :array_2
    .array-data 4
        0x6
        0x1a
        0x34
        0x4e
        0x68
        0x82
    .end array-data

    :array_3
    .array-data 4
        0x6
        0x1e
        0x38
        0x52
        0x6c
        0x86
    .end array-data

    :array_4
    .array-data 4
        0x6
        0x22
        0x3c
        0x56
        0x70
        0x8a
    .end array-data

    :array_5
    .array-data 4
        0x6
        0x1e
        0x3a
        0x56
        0x72
        0x8e
    .end array-data

    :array_6
    .array-data 4
        0x6
        0x22
        0x3e
        0x5a
        0x76
        0x92
    .end array-data

    :array_7
    .array-data 4
        0x6
        0x1e
        0x36
        0x4e
        0x66
        0x7e
        0x96
    .end array-data

    :array_8
    .array-data 4
        0x6
        0x18
        0x32
        0x4c
        0x66
        0x80
        0x9a
    .end array-data

    :array_9
    .array-data 4
        0x6
        0x1c
        0x36
        0x50
        0x6a
        0x84
        0x9e
    .end array-data

    :array_a
    .array-data 4
        0x6
        0x20
        0x3a
        0x54
        0x6e
        0x88
        0xa2
    .end array-data

    :array_b
    .array-data 4
        0x6
        0x1a
        0x36
        0x52
        0x6e
        0x8a
        0xa6
    .end array-data

    :array_c
    .array-data 4
        0x6
        0x1e
        0x3a
        0x56
        0x72
        0x8e
        0xaa
    .end array-data
.end method


# virtual methods
.method public final toString()Ljava/lang/String;
    .locals 0

    .line 1
    iget p0, p0, Lcom/google/zxing/qrcode/decoder/Version;->versionNumber:I

    .line 2
    .line 3
    invoke-static {p0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method