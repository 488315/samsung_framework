.class public final Lkotlin/jvm/internal/ArrayIntIterator;
.super Lkotlin/collections/IntIterator;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final array:[I

.field public index:I


# direct methods
.method public constructor <init>([I)V
    .locals 0

    .line 1
    invoke-direct {p0}, Lkotlin/collections/IntIterator;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lkotlin/jvm/internal/ArrayIntIterator;->array:[I

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final hasNext()Z
    .locals 1

    .line 1
    iget v0, p0, Lkotlin/jvm/internal/ArrayIntIterator;->index:I

    .line 2
    .line 3
    iget-object p0, p0, Lkotlin/jvm/internal/ArrayIntIterator;->array:[I

    .line 4
    .line 5
    array-length p0, p0

    .line 6
    if-ge v0, p0, :cond_0

    .line 7
    .line 8
    const/4 p0, 0x1

    .line 9
    goto :goto_0

    .line 10
    :cond_0
    const/4 p0, 0x0

    .line 11
    :goto_0
    return p0
.end method

.method public final nextInt()I
    .locals 3

    .line 1
    :try_start_0
    iget-object v0, p0, Lkotlin/jvm/internal/ArrayIntIterator;->array:[I

    .line 2
    .line 3
    iget v1, p0, Lkotlin/jvm/internal/ArrayIntIterator;->index:I

    .line 4
    .line 5
    add-int/lit8 v2, v1, 0x1

    .line 6
    .line 7
    iput v2, p0, Lkotlin/jvm/internal/ArrayIntIterator;->index:I

    .line 8
    .line 9
    aget p0, v0, v1
    :try_end_0
    .catch Ljava/lang/ArrayIndexOutOfBoundsException; {:try_start_0 .. :try_end_0} :catch_0

    .line 10
    .line 11
    return p0

    .line 12
    :catch_0
    move-exception v0

    .line 13
    iget v1, p0, Lkotlin/jvm/internal/ArrayIntIterator;->index:I

    .line 14
    .line 15
    add-int/lit8 v1, v1, -0x1

    .line 16
    .line 17
    iput v1, p0, Lkotlin/jvm/internal/ArrayIntIterator;->index:I

    .line 18
    .line 19
    new-instance p0, Ljava/util/NoSuchElementException;

    .line 20
    .line 21
    invoke-virtual {v0}, Ljava/lang/ArrayIndexOutOfBoundsException;->getMessage()Ljava/lang/String;

    .line 22
    .line 23
    .line 24
    move-result-object v0

    .line 25
    invoke-direct {p0, v0}, Ljava/util/NoSuchElementException;-><init>(Ljava/lang/String;)V

    .line 26
    .line 27
    .line 28
    throw p0
.end method
