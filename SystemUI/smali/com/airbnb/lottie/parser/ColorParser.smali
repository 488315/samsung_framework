.class public final Lcom/airbnb/lottie/parser/ColorParser;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/airbnb/lottie/parser/ValueParser;


# static fields
.field public static final INSTANCE:Lcom/airbnb/lottie/parser/ColorParser;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/airbnb/lottie/parser/ColorParser;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/airbnb/lottie/parser/ColorParser;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/airbnb/lottie/parser/ColorParser;->INSTANCE:Lcom/airbnb/lottie/parser/ColorParser;

    .line 7
    .line 8
    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    return-void
.end method


# virtual methods
.method public final parse(Lcom/airbnb/lottie/parser/moshi/JsonReader;F)Ljava/lang/Object;
    .locals 11

    .line 1
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->peek()Lcom/airbnb/lottie/parser/moshi/JsonReader$Token;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    sget-object p2, Lcom/airbnb/lottie/parser/moshi/JsonReader$Token;->BEGIN_ARRAY:Lcom/airbnb/lottie/parser/moshi/JsonReader$Token;

    .line 6
    .line 7
    if-ne p0, p2, :cond_0

    .line 8
    .line 9
    const/4 p0, 0x1

    .line 10
    goto :goto_0

    .line 11
    :cond_0
    const/4 p0, 0x0

    .line 12
    :goto_0
    if-eqz p0, :cond_1

    .line 13
    .line 14
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->beginArray()V

    .line 15
    .line 16
    .line 17
    :cond_1
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->nextDouble()D

    .line 18
    .line 19
    .line 20
    move-result-wide v0

    .line 21
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->nextDouble()D

    .line 22
    .line 23
    .line 24
    move-result-wide v2

    .line 25
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->nextDouble()D

    .line 26
    .line 27
    .line 28
    move-result-wide v4

    .line 29
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->peek()Lcom/airbnb/lottie/parser/moshi/JsonReader$Token;

    .line 30
    .line 31
    .line 32
    move-result-object p2

    .line 33
    sget-object v6, Lcom/airbnb/lottie/parser/moshi/JsonReader$Token;->NUMBER:Lcom/airbnb/lottie/parser/moshi/JsonReader$Token;

    .line 34
    .line 35
    const-wide/high16 v7, 0x3ff0000000000000L    # 1.0

    .line 36
    .line 37
    if-ne p2, v6, :cond_2

    .line 38
    .line 39
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->nextDouble()D

    .line 40
    .line 41
    .line 42
    move-result-wide v9

    .line 43
    goto :goto_1

    .line 44
    :cond_2
    move-wide v9, v7

    .line 45
    :goto_1
    if-eqz p0, :cond_3

    .line 46
    .line 47
    invoke-virtual {p1}, Lcom/airbnb/lottie/parser/moshi/JsonReader;->endArray()V

    .line 48
    .line 49
    .line 50
    :cond_3
    cmpg-double p0, v0, v7

    .line 51
    .line 52
    if-gtz p0, :cond_4

    .line 53
    .line 54
    cmpg-double p0, v2, v7

    .line 55
    .line 56
    if-gtz p0, :cond_4

    .line 57
    .line 58
    cmpg-double p0, v4, v7

    .line 59
    .line 60
    if-gtz p0, :cond_4

    .line 61
    .line 62
    const-wide p0, 0x406fe00000000000L    # 255.0

    .line 63
    .line 64
    .line 65
    .line 66
    .line 67
    mul-double/2addr v0, p0

    .line 68
    mul-double/2addr v2, p0

    .line 69
    mul-double/2addr v4, p0

    .line 70
    cmpg-double p2, v9, v7

    .line 71
    .line 72
    if-gtz p2, :cond_4

    .line 73
    .line 74
    mul-double/2addr v9, p0

    .line 75
    :cond_4
    double-to-int p0, v9

    .line 76
    double-to-int p1, v0

    .line 77
    double-to-int p2, v2

    .line 78
    double-to-int v0, v4

    .line 79
    invoke-static {p0, p1, p2, v0}, Landroid/graphics/Color;->argb(IIII)I

    .line 80
    .line 81
    .line 82
    move-result p0

    .line 83
    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 84
    .line 85
    .line 86
    move-result-object p0

    .line 87
    return-object p0
.end method
