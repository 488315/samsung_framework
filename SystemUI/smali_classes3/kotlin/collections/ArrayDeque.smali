.class public final Lkotlin/collections/ArrayDeque;
.super Lkotlin/collections/AbstractMutableList;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final Companion:Lkotlin/collections/ArrayDeque$Companion;

.field public static final emptyElementData:[Ljava/lang/Object;


# instance fields
.field public elementData:[Ljava/lang/Object;

.field public head:I

.field public size:I


# direct methods
.method public static constructor <clinit>()V
    .locals 2

    .line 1
    new-instance v0, Lkotlin/collections/ArrayDeque$Companion;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    invoke-direct {v0, v1}, Lkotlin/collections/ArrayDeque$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 5
    .line 6
    .line 7
    sput-object v0, Lkotlin/collections/ArrayDeque;->Companion:Lkotlin/collections/ArrayDeque$Companion;

    .line 8
    .line 9
    const/4 v0, 0x0

    .line 10
    new-array v0, v0, [Ljava/lang/Object;

    .line 11
    .line 12
    sput-object v0, Lkotlin/collections/ArrayDeque;->emptyElementData:[Ljava/lang/Object;

    .line 13
    .line 14
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 16
    invoke-direct {p0}, Lkotlin/collections/AbstractMutableList;-><init>()V

    .line 17
    sget-object v0, Lkotlin/collections/ArrayDeque;->emptyElementData:[Ljava/lang/Object;

    iput-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    return-void
.end method

.method public constructor <init>(I)V
    .locals 1

    .line 1
    invoke-direct {p0}, Lkotlin/collections/AbstractMutableList;-><init>()V

    if-nez p1, :cond_0

    .line 2
    sget-object p1, Lkotlin/collections/ArrayDeque;->emptyElementData:[Ljava/lang/Object;

    goto :goto_0

    :cond_0
    if-lez p1, :cond_1

    .line 3
    new-array p1, p1, [Ljava/lang/Object;

    .line 4
    :goto_0
    iput-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    return-void

    .line 5
    :cond_1
    new-instance p0, Ljava/lang/IllegalArgumentException;

    const-string v0, "Illegal Capacity: "

    .line 6
    invoke-static {v0, p1}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;I)Ljava/lang/String;

    move-result-object p1

    .line 7
    invoke-direct {p0, p1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public constructor <init>(Ljava/util/Collection;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Collection<",
            "Ljava/lang/Object;",
            ">;)V"
        }
    .end annotation

    .line 18
    invoke-direct {p0}, Lkotlin/collections/AbstractMutableList;-><init>()V

    const/4 v0, 0x0

    new-array v1, v0, [Ljava/lang/Object;

    .line 19
    invoke-interface {p1, v1}, Ljava/util/Collection;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object p1

    .line 20
    iput-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 21
    array-length v1, p1

    iput v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 22
    array-length p1, p1

    if-nez p1, :cond_0

    const/4 v0, 0x1

    :cond_0
    if-eqz v0, :cond_1

    sget-object p1, Lkotlin/collections/ArrayDeque;->emptyElementData:[Ljava/lang/Object;

    iput-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    :cond_1
    return-void
.end method


# virtual methods
.method public final add(ILjava/lang/Object;)V
    .locals 7

    .line 2
    sget-object v0, Lkotlin/collections/AbstractList;->Companion:Lkotlin/collections/AbstractList$Companion;

    .line 3
    iget v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 4
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    invoke-static {p1, v1}, Lkotlin/collections/AbstractList$Companion;->checkPositionIndex$kotlin_stdlib(II)V

    .line 5
    iget v0, p0, Lkotlin/collections/ArrayDeque;->size:I

    if-ne p1, v0, :cond_0

    .line 6
    invoke-virtual {p0, p2}, Lkotlin/collections/ArrayDeque;->addLast(Ljava/lang/Object;)V

    return-void

    :cond_0
    const/4 v1, 0x1

    if-nez p1, :cond_2

    add-int/2addr v0, v1

    .line 7
    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->ensureCapacity(I)V

    .line 8
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    if-nez p1, :cond_1

    .line 9
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 10
    array-length p1, p1

    :cond_1
    add-int/lit8 p1, p1, -0x1

    .line 11
    iput p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 12
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    aput-object p2, v0, p1

    .line 13
    iget p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    add-int/2addr p1, v1

    .line 14
    iput p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    return-void

    :cond_2
    add-int/2addr v0, v1

    .line 15
    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->ensureCapacity(I)V

    .line 16
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    add-int/2addr v0, p1

    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    move-result v0

    .line 17
    iget v2, p0, Lkotlin/collections/ArrayDeque;->size:I

    add-int/lit8 v3, v2, 0x1

    shr-int/2addr v3, v1

    const/4 v4, 0x0

    if-ge p1, v3, :cond_6

    if-nez v0, :cond_3

    .line 18
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 19
    array-length p1, p1

    add-int/lit8 p1, p1, -0x1

    goto :goto_0

    :cond_3
    add-int/lit8 p1, v0, -0x1

    .line 20
    :goto_0
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    if-nez v0, :cond_4

    .line 21
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 22
    array-length v2, v2

    add-int/lit8 v2, v2, -0x1

    goto :goto_1

    :cond_4
    add-int/lit8 v2, v0, -0x1

    :goto_1
    if-lt p1, v0, :cond_5

    .line 23
    iget-object v3, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    aget-object v4, v3, v0

    aput-object v4, v3, v2

    add-int/lit8 v4, v0, 0x1

    add-int/lit8 v5, p1, 0x1

    sub-int/2addr v5, v4

    .line 24
    invoke-static {v3, v4, v3, v0, v5}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_2

    .line 25
    :cond_5
    iget-object v3, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    add-int/lit8 v5, v0, -0x1

    array-length v6, v3

    sub-int/2addr v6, v0

    .line 26
    invoke-static {v3, v0, v3, v5, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 27
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v3, v0

    sub-int/2addr v3, v1

    aget-object v5, v0, v4

    aput-object v5, v0, v3

    add-int/lit8 v3, p1, 0x1

    sub-int/2addr v3, v1

    .line 28
    invoke-static {v0, v1, v0, v4, v3}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 29
    :goto_2
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    aput-object p2, v0, p1

    .line 30
    iput v2, p0, Lkotlin/collections/ArrayDeque;->head:I

    goto :goto_4

    .line 31
    :cond_6
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    add-int/2addr v2, p1

    invoke-virtual {p0, v2}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    move-result p1

    if-ge v0, p1, :cond_7

    .line 32
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    add-int/lit8 v3, v0, 0x1

    sub-int/2addr p1, v0

    .line 33
    invoke-static {v2, v0, v2, v3, p1}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_3

    .line 34
    :cond_7
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    sub-int/2addr p1, v4

    .line 35
    invoke-static {v2, v4, v2, v1, p1}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 36
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v2, p1

    sub-int/2addr v2, v1

    aget-object v2, p1, v2

    aput-object v2, p1, v4

    add-int/lit8 v2, v0, 0x1

    .line 37
    array-length v3, p1

    sub-int/2addr v3, v1

    sub-int/2addr v3, v0

    .line 38
    invoke-static {p1, v0, p1, v2, v3}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 39
    :goto_3
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    aput-object p2, p1, v0

    .line 40
    :goto_4
    iget p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    add-int/2addr p1, v1

    .line 41
    iput p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    return-void
.end method

.method public final add(Ljava/lang/Object;)Z
    .locals 0

    .line 1
    invoke-virtual {p0, p1}, Lkotlin/collections/ArrayDeque;->addLast(Ljava/lang/Object;)V

    const/4 p0, 0x1

    return p0
.end method

.method public final addAll(ILjava/util/Collection;)Z
    .locals 8

    .line 7
    sget-object v0, Lkotlin/collections/AbstractList;->Companion:Lkotlin/collections/AbstractList$Companion;

    .line 8
    iget v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 9
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    invoke-static {p1, v1}, Lkotlin/collections/AbstractList$Companion;->checkPositionIndex$kotlin_stdlib(II)V

    .line 10
    invoke-interface {p2}, Ljava/util/Collection;->isEmpty()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    .line 11
    :cond_0
    iget v0, p0, Lkotlin/collections/ArrayDeque;->size:I

    if-ne p1, v0, :cond_1

    .line 12
    invoke-virtual {p0, p2}, Lkotlin/collections/ArrayDeque;->addAll(Ljava/util/Collection;)Z

    move-result p0

    return p0

    .line 13
    :cond_1
    invoke-interface {p2}, Ljava/util/Collection;->size()I

    move-result v2

    add-int/2addr v2, v0

    invoke-virtual {p0, v2}, Lkotlin/collections/ArrayDeque;->ensureCapacity(I)V

    .line 14
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 15
    iget v2, p0, Lkotlin/collections/ArrayDeque;->size:I

    add-int/2addr v2, v0

    .line 16
    invoke-virtual {p0, v2}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    move-result v0

    .line 17
    iget v2, p0, Lkotlin/collections/ArrayDeque;->head:I

    add-int/2addr v2, p1

    invoke-virtual {p0, v2}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    move-result v2

    .line 18
    invoke-interface {p2}, Ljava/util/Collection;->size()I

    move-result v3

    .line 19
    iget v4, p0, Lkotlin/collections/ArrayDeque;->size:I

    const/4 v5, 0x1

    add-int/2addr v4, v5

    shr-int/2addr v4, v5

    if-ge p1, v4, :cond_7

    .line 20
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    sub-int v0, p1, v3

    if-lt v2, p1, :cond_4

    if-ltz v0, :cond_2

    .line 21
    iget-object v1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    sub-int v4, v2, p1

    .line 22
    invoke-static {v1, p1, v1, v0, v4}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_0

    .line 23
    :cond_2
    iget-object v4, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v6, v4

    add-int/2addr v0, v6

    sub-int v6, v2, p1

    .line 24
    array-length v7, v4

    sub-int/2addr v7, v0

    if-lt v7, v6, :cond_3

    .line 25
    invoke-static {v4, p1, v4, v0, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_0

    :cond_3
    add-int v6, p1, v7

    sub-int/2addr v6, p1

    .line 26
    invoke-static {v4, p1, v4, v0, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 27
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    iget v4, p0, Lkotlin/collections/ArrayDeque;->head:I

    add-int/2addr v4, v7

    sub-int v6, v2, v4

    .line 28
    invoke-static {p1, v4, p1, v1, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_0

    .line 29
    :cond_4
    iget-object v4, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v6, v4

    sub-int/2addr v6, p1

    .line 30
    invoke-static {v4, p1, v4, v0, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    if-lt v3, v2, :cond_5

    .line 31
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v4, p1

    sub-int/2addr v4, v3

    add-int/lit8 v6, v2, 0x0

    .line 32
    invoke-static {p1, v1, p1, v4, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_0

    .line 33
    :cond_5
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v4, p1

    sub-int/2addr v4, v3

    add-int/lit8 v6, v3, 0x0

    .line 34
    invoke-static {p1, v1, p1, v4, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 35
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    sub-int v4, v2, v3

    .line 36
    invoke-static {p1, v3, p1, v1, v4}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 37
    :goto_0
    iput v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    sub-int/2addr v2, v3

    if-gez v2, :cond_6

    .line 38
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length p1, p1

    add-int/2addr v2, p1

    .line 39
    :cond_6
    invoke-virtual {p0, v2, p2}, Lkotlin/collections/ArrayDeque;->copyCollectionElements(ILjava/util/Collection;)V

    goto :goto_2

    :cond_7
    add-int p1, v2, v3

    if-ge v2, v0, :cond_a

    add-int/2addr v3, v0

    .line 40
    iget-object v4, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v6, v4

    if-gt v3, v6, :cond_8

    sub-int/2addr v0, v2

    .line 41
    invoke-static {v4, v2, v4, p1, v0}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_1

    .line 42
    :cond_8
    array-length v6, v4

    if-lt p1, v6, :cond_9

    .line 43
    array-length v1, v4

    sub-int/2addr p1, v1

    sub-int/2addr v0, v2

    .line 44
    invoke-static {v4, v2, v4, p1, v0}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_1

    .line 45
    :cond_9
    array-length v6, v4

    sub-int/2addr v3, v6

    sub-int v3, v0, v3

    sub-int/2addr v0, v3

    .line 46
    invoke-static {v4, v3, v4, v1, v0}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 47
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    sub-int/2addr v3, v2

    .line 48
    invoke-static {v0, v2, v0, p1, v3}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_1

    .line 49
    :cond_a
    iget-object v4, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    sub-int/2addr v0, v1

    .line 50
    invoke-static {v4, v1, v4, v3, v0}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 51
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v4, v0

    if-lt p1, v4, :cond_b

    .line 52
    array-length v1, v0

    sub-int/2addr p1, v1

    array-length v1, v0

    sub-int/2addr v1, v2

    .line 53
    invoke-static {v0, v2, v0, p1, v1}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    goto :goto_1

    .line 54
    :cond_b
    array-length v4, v0

    sub-int/2addr v4, v3

    array-length v6, v0

    sub-int/2addr v6, v4

    .line 55
    invoke-static {v0, v4, v0, v1, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 56
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v1, v0

    sub-int/2addr v1, v3

    sub-int/2addr v1, v2

    .line 57
    invoke-static {v0, v2, v0, p1, v1}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 58
    :goto_1
    invoke-virtual {p0, v2, p2}, Lkotlin/collections/ArrayDeque;->copyCollectionElements(ILjava/util/Collection;)V

    :goto_2
    return v5
.end method

.method public final addAll(Ljava/util/Collection;)Z
    .locals 2

    .line 1
    invoke-interface {p1}, Ljava/util/Collection;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return p0

    .line 2
    :cond_0
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    move-result v0

    .line 3
    invoke-interface {p1}, Ljava/util/Collection;->size()I

    move-result v1

    add-int/2addr v1, v0

    invoke-virtual {p0, v1}, Lkotlin/collections/ArrayDeque;->ensureCapacity(I)V

    .line 4
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 5
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    move-result v1

    add-int/2addr v1, v0

    .line 6
    invoke-virtual {p0, v1}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    move-result v0

    invoke-virtual {p0, v0, p1}, Lkotlin/collections/ArrayDeque;->copyCollectionElements(ILjava/util/Collection;)V

    const/4 p0, 0x1

    return p0
.end method

.method public final addLast(Ljava/lang/Object;)V
    .locals 3

    .line 1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    add-int/lit8 v0, v0, 0x1

    .line 6
    .line 7
    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->ensureCapacity(I)V

    .line 8
    .line 9
    .line 10
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 11
    .line 12
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 13
    .line 14
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 15
    .line 16
    .line 17
    move-result v2

    .line 18
    add-int/2addr v2, v1

    .line 19
    invoke-virtual {p0, v2}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 20
    .line 21
    .line 22
    move-result v1

    .line 23
    aput-object p1, v0, v1

    .line 24
    .line 25
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 26
    .line 27
    .line 28
    move-result p1

    .line 29
    add-int/lit8 p1, p1, 0x1

    .line 30
    .line 31
    iput p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 32
    .line 33
    return-void
.end method

.method public final clear()V
    .locals 6

    .line 1
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 2
    .line 3
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 4
    .line 5
    .line 6
    move-result v1

    .line 7
    add-int/2addr v1, v0

    .line 8
    invoke-virtual {p0, v1}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 9
    .line 10
    .line 11
    move-result v0

    .line 12
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 13
    .line 14
    const/4 v2, 0x0

    .line 15
    const/4 v3, 0x0

    .line 16
    if-ge v1, v0, :cond_0

    .line 17
    .line 18
    iget-object v4, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 19
    .line 20
    invoke-static {v4, v1, v0, v3}, Ljava/util/Arrays;->fill([Ljava/lang/Object;IILjava/lang/Object;)V

    .line 21
    .line 22
    .line 23
    goto :goto_0

    .line 24
    :cond_0
    invoke-interface {p0}, Ljava/util/Collection;->isEmpty()Z

    .line 25
    .line 26
    .line 27
    move-result v1

    .line 28
    xor-int/lit8 v1, v1, 0x1

    .line 29
    .line 30
    if-eqz v1, :cond_1

    .line 31
    .line 32
    iget-object v1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 33
    .line 34
    iget v4, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 35
    .line 36
    array-length v5, v1

    .line 37
    invoke-static {v1, v4, v5, v3}, Ljava/util/Arrays;->fill([Ljava/lang/Object;IILjava/lang/Object;)V

    .line 38
    .line 39
    .line 40
    iget-object v1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 41
    .line 42
    invoke-static {v1, v2, v0, v3}, Ljava/util/Arrays;->fill([Ljava/lang/Object;IILjava/lang/Object;)V

    .line 43
    .line 44
    .line 45
    :cond_1
    :goto_0
    iput v2, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 46
    .line 47
    iput v2, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 48
    .line 49
    return-void
.end method

.method public final contains(Ljava/lang/Object;)Z
    .locals 0

    .line 1
    invoke-virtual {p0, p1}, Lkotlin/collections/ArrayDeque;->indexOf(Ljava/lang/Object;)I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    const/4 p1, -0x1

    .line 6
    if-eq p0, p1, :cond_0

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

.method public final copyCollectionElements(ILjava/util/Collection;)V
    .locals 4

    .line 1
    invoke-interface {p2}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    iget-object v1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 6
    .line 7
    array-length v1, v1

    .line 8
    :goto_0
    if-ge p1, v1, :cond_0

    .line 9
    .line 10
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    .line 11
    .line 12
    .line 13
    move-result v2

    .line 14
    if-eqz v2, :cond_0

    .line 15
    .line 16
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 17
    .line 18
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 19
    .line 20
    .line 21
    move-result-object v3

    .line 22
    aput-object v3, v2, p1

    .line 23
    .line 24
    add-int/lit8 p1, p1, 0x1

    .line 25
    .line 26
    goto :goto_0

    .line 27
    :cond_0
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 28
    .line 29
    const/4 v1, 0x0

    .line 30
    :goto_1
    if-ge v1, p1, :cond_1

    .line 31
    .line 32
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    .line 33
    .line 34
    .line 35
    move-result v2

    .line 36
    if-eqz v2, :cond_1

    .line 37
    .line 38
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 39
    .line 40
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 41
    .line 42
    .line 43
    move-result-object v3

    .line 44
    aput-object v3, v2, v1

    .line 45
    .line 46
    add-int/lit8 v1, v1, 0x1

    .line 47
    .line 48
    goto :goto_1

    .line 49
    :cond_1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 50
    .line 51
    .line 52
    move-result p1

    .line 53
    invoke-interface {p2}, Ljava/util/Collection;->size()I

    .line 54
    .line 55
    .line 56
    move-result p2

    .line 57
    add-int/2addr p2, p1

    .line 58
    iput p2, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 59
    .line 60
    return-void
.end method

.method public final ensureCapacity(I)V
    .locals 4

    .line 1
    if-ltz p1, :cond_6

    .line 2
    .line 3
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 4
    .line 5
    array-length v1, v0

    .line 6
    if-gt p1, v1, :cond_0

    .line 7
    .line 8
    return-void

    .line 9
    :cond_0
    sget-object v1, Lkotlin/collections/ArrayDeque;->emptyElementData:[Ljava/lang/Object;

    .line 10
    .line 11
    if-ne v0, v1, :cond_2

    .line 12
    .line 13
    const/16 v0, 0xa

    .line 14
    .line 15
    if-ge p1, v0, :cond_1

    .line 16
    .line 17
    move p1, v0

    .line 18
    :cond_1
    new-array p1, p1, [Ljava/lang/Object;

    .line 19
    .line 20
    iput-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 21
    .line 22
    return-void

    .line 23
    :cond_2
    sget-object v1, Lkotlin/collections/ArrayDeque;->Companion:Lkotlin/collections/ArrayDeque$Companion;

    .line 24
    .line 25
    array-length v0, v0

    .line 26
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 27
    .line 28
    .line 29
    shr-int/lit8 v1, v0, 0x1

    .line 30
    .line 31
    add-int/2addr v0, v1

    .line 32
    sub-int v1, v0, p1

    .line 33
    .line 34
    if-gez v1, :cond_3

    .line 35
    .line 36
    move v0, p1

    .line 37
    :cond_3
    const v1, 0x7ffffff7

    .line 38
    .line 39
    .line 40
    sub-int v2, v0, v1

    .line 41
    .line 42
    if-lez v2, :cond_5

    .line 43
    .line 44
    if-le p1, v1, :cond_4

    .line 45
    .line 46
    const v0, 0x7fffffff

    .line 47
    .line 48
    .line 49
    goto :goto_0

    .line 50
    :cond_4
    move v0, v1

    .line 51
    :cond_5
    :goto_0
    new-array p1, v0, [Ljava/lang/Object;

    .line 52
    .line 53
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 54
    .line 55
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 56
    .line 57
    array-length v2, v0

    .line 58
    sub-int/2addr v2, v1

    .line 59
    const/4 v3, 0x0

    .line 60
    invoke-static {v0, v1, p1, v3, v2}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 61
    .line 62
    .line 63
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 64
    .line 65
    array-length v1, v0

    .line 66
    iget v2, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 67
    .line 68
    sub-int/2addr v1, v2

    .line 69
    sub-int/2addr v2, v3

    .line 70
    invoke-static {v0, v3, p1, v1, v2}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 71
    .line 72
    .line 73
    iput v3, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 74
    .line 75
    iput-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 76
    .line 77
    return-void

    .line 78
    :cond_6
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 79
    .line 80
    const-string p1, "Deque is too big."

    .line 81
    .line 82
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 83
    .line 84
    .line 85
    throw p0
.end method

.method public final get(I)Ljava/lang/Object;
    .locals 2

    .line 1
    sget-object v0, Lkotlin/collections/AbstractList;->Companion:Lkotlin/collections/AbstractList$Companion;

    .line 2
    .line 3
    iget v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 4
    .line 5
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 6
    .line 7
    .line 8
    invoke-static {p1, v1}, Lkotlin/collections/AbstractList$Companion;->checkElementIndex$kotlin_stdlib(II)V

    .line 9
    .line 10
    .line 11
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 12
    .line 13
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 14
    .line 15
    add-int/2addr v1, p1

    .line 16
    invoke-virtual {p0, v1}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 17
    .line 18
    .line 19
    move-result p0

    .line 20
    aget-object p0, v0, p0

    .line 21
    .line 22
    return-object p0
.end method

.method public final getSize()I
    .locals 0

    .line 1
    iget p0, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 2
    .line 3
    return p0
.end method

.method public final indexOf(Ljava/lang/Object;)I
    .locals 4

    .line 1
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 2
    .line 3
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 4
    .line 5
    .line 6
    move-result v1

    .line 7
    add-int/2addr v1, v0

    .line 8
    invoke-virtual {p0, v1}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 9
    .line 10
    .line 11
    move-result v0

    .line 12
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 13
    .line 14
    if-ge v1, v0, :cond_1

    .line 15
    .line 16
    :goto_0
    if-ge v1, v0, :cond_5

    .line 17
    .line 18
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 19
    .line 20
    aget-object v2, v2, v1

    .line 21
    .line 22
    invoke-static {p1, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 23
    .line 24
    .line 25
    move-result v2

    .line 26
    if-eqz v2, :cond_0

    .line 27
    .line 28
    iget p0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 29
    .line 30
    :goto_1
    sub-int/2addr v1, p0

    .line 31
    return v1

    .line 32
    :cond_0
    add-int/lit8 v1, v1, 0x1

    .line 33
    .line 34
    goto :goto_0

    .line 35
    :cond_1
    if-lt v1, v0, :cond_5

    .line 36
    .line 37
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 38
    .line 39
    array-length v2, v2

    .line 40
    :goto_2
    if-ge v1, v2, :cond_3

    .line 41
    .line 42
    iget-object v3, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 43
    .line 44
    aget-object v3, v3, v1

    .line 45
    .line 46
    invoke-static {p1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 47
    .line 48
    .line 49
    move-result v3

    .line 50
    if-eqz v3, :cond_2

    .line 51
    .line 52
    iget p0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 53
    .line 54
    goto :goto_1

    .line 55
    :cond_2
    add-int/lit8 v1, v1, 0x1

    .line 56
    .line 57
    goto :goto_2

    .line 58
    :cond_3
    const/4 v1, 0x0

    .line 59
    :goto_3
    if-ge v1, v0, :cond_5

    .line 60
    .line 61
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 62
    .line 63
    aget-object v2, v2, v1

    .line 64
    .line 65
    invoke-static {p1, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 66
    .line 67
    .line 68
    move-result v2

    .line 69
    if-eqz v2, :cond_4

    .line 70
    .line 71
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 72
    .line 73
    array-length p1, p1

    .line 74
    add-int/2addr v1, p1

    .line 75
    iget p0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 76
    .line 77
    goto :goto_1

    .line 78
    :cond_4
    add-int/lit8 v1, v1, 0x1

    .line 79
    .line 80
    goto :goto_3

    .line 81
    :cond_5
    const/4 p0, -0x1

    .line 82
    return p0
.end method

.method public final isEmpty()Z
    .locals 0

    .line 1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    if-nez p0, :cond_0

    .line 6
    .line 7
    const/4 p0, 0x1

    .line 8
    goto :goto_0

    .line 9
    :cond_0
    const/4 p0, 0x0

    .line 10
    :goto_0
    return p0
.end method

.method public final lastIndexOf(Ljava/lang/Object;)I
    .locals 4

    .line 1
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 2
    .line 3
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    .line 4
    .line 5
    .line 6
    move-result v1

    .line 7
    add-int/2addr v1, v0

    .line 8
    invoke-virtual {p0, v1}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 9
    .line 10
    .line 11
    move-result v0

    .line 12
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 13
    .line 14
    const/4 v2, -0x1

    .line 15
    if-ge v1, v0, :cond_1

    .line 16
    .line 17
    add-int/lit8 v0, v0, -0x1

    .line 18
    .line 19
    if-gt v1, v0, :cond_5

    .line 20
    .line 21
    :goto_0
    iget-object v3, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 22
    .line 23
    aget-object v3, v3, v0

    .line 24
    .line 25
    invoke-static {p1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 26
    .line 27
    .line 28
    move-result v3

    .line 29
    if-eqz v3, :cond_0

    .line 30
    .line 31
    iget p0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 32
    .line 33
    :goto_1
    sub-int/2addr v0, p0

    .line 34
    return v0

    .line 35
    :cond_0
    if-eq v0, v1, :cond_5

    .line 36
    .line 37
    add-int/lit8 v0, v0, -0x1

    .line 38
    .line 39
    goto :goto_0

    .line 40
    :cond_1
    if-le v1, v0, :cond_5

    .line 41
    .line 42
    add-int/lit8 v0, v0, -0x1

    .line 43
    .line 44
    :goto_2
    if-ge v2, v0, :cond_3

    .line 45
    .line 46
    iget-object v1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 47
    .line 48
    aget-object v1, v1, v0

    .line 49
    .line 50
    invoke-static {p1, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 51
    .line 52
    .line 53
    move-result v1

    .line 54
    if-eqz v1, :cond_2

    .line 55
    .line 56
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 57
    .line 58
    array-length p1, p1

    .line 59
    add-int/2addr v0, p1

    .line 60
    iget p0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 61
    .line 62
    goto :goto_1

    .line 63
    :cond_2
    add-int/lit8 v0, v0, -0x1

    .line 64
    .line 65
    goto :goto_2

    .line 66
    :cond_3
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 67
    .line 68
    array-length v0, v0

    .line 69
    add-int/2addr v0, v2

    .line 70
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 71
    .line 72
    if-gt v1, v0, :cond_5

    .line 73
    .line 74
    :goto_3
    iget-object v3, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 75
    .line 76
    aget-object v3, v3, v0

    .line 77
    .line 78
    invoke-static {p1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 79
    .line 80
    .line 81
    move-result v3

    .line 82
    if-eqz v3, :cond_4

    .line 83
    .line 84
    iget p0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 85
    .line 86
    goto :goto_1

    .line 87
    :cond_4
    if-eq v0, v1, :cond_5

    .line 88
    .line 89
    add-int/lit8 v0, v0, -0x1

    .line 90
    .line 91
    goto :goto_3

    .line 92
    :cond_5
    return v2
.end method

.method public final positiveMod(I)I
    .locals 1

    .line 1
    iget-object p0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 2
    .line 3
    array-length v0, p0

    .line 4
    if-lt p1, v0, :cond_0

    .line 5
    .line 6
    array-length p0, p0

    .line 7
    sub-int/2addr p1, p0

    .line 8
    :cond_0
    return p1
.end method

.method public final remove(Ljava/lang/Object;)Z
    .locals 1

    .line 1
    invoke-virtual {p0, p1}, Lkotlin/collections/ArrayDeque;->indexOf(Ljava/lang/Object;)I

    .line 2
    .line 3
    .line 4
    move-result p1

    .line 5
    const/4 v0, -0x1

    .line 6
    if-ne p1, v0, :cond_0

    .line 7
    .line 8
    const/4 p0, 0x0

    .line 9
    return p0

    .line 10
    :cond_0
    invoke-virtual {p0, p1}, Lkotlin/collections/ArrayDeque;->removeAt(I)Ljava/lang/Object;

    .line 11
    .line 12
    .line 13
    const/4 p0, 0x1

    .line 14
    return p0
.end method

.method public final removeAll(Ljava/util/Collection;)Z
    .locals 11

    .line 1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->isEmpty()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x0

    .line 6
    if-nez v0, :cond_b

    .line 7
    .line 8
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 9
    .line 10
    array-length v0, v0

    .line 11
    const/4 v2, 0x1

    .line 12
    if-nez v0, :cond_0

    .line 13
    .line 14
    move v0, v2

    .line 15
    goto :goto_0

    .line 16
    :cond_0
    move v0, v1

    .line 17
    :goto_0
    if-eqz v0, :cond_1

    .line 18
    .line 19
    goto/16 :goto_8

    .line 20
    .line 21
    :cond_1
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 22
    .line 23
    iget v3, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 24
    .line 25
    add-int/2addr v3, v0

    .line 26
    invoke-virtual {p0, v3}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 27
    .line 28
    .line 29
    move-result v0

    .line 30
    iget v3, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 31
    .line 32
    const/4 v4, 0x0

    .line 33
    if-ge v3, v0, :cond_4

    .line 34
    .line 35
    move v5, v3

    .line 36
    :goto_1
    if-ge v3, v0, :cond_3

    .line 37
    .line 38
    iget-object v6, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 39
    .line 40
    aget-object v6, v6, v3

    .line 41
    .line 42
    invoke-interface {p1, v6}, Ljava/util/Collection;->contains(Ljava/lang/Object;)Z

    .line 43
    .line 44
    .line 45
    move-result v7

    .line 46
    xor-int/2addr v7, v2

    .line 47
    if-eqz v7, :cond_2

    .line 48
    .line 49
    iget-object v7, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 50
    .line 51
    add-int/lit8 v8, v5, 0x1

    .line 52
    .line 53
    aput-object v6, v7, v5

    .line 54
    .line 55
    move v5, v8

    .line 56
    goto :goto_2

    .line 57
    :cond_2
    move v1, v2

    .line 58
    :goto_2
    add-int/lit8 v3, v3, 0x1

    .line 59
    .line 60
    goto :goto_1

    .line 61
    :cond_3
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 62
    .line 63
    invoke-static {p1, v5, v0, v4}, Ljava/util/Arrays;->fill([Ljava/lang/Object;IILjava/lang/Object;)V

    .line 64
    .line 65
    .line 66
    goto :goto_7

    .line 67
    :cond_4
    iget-object v5, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 68
    .line 69
    array-length v5, v5

    .line 70
    move v7, v1

    .line 71
    move v6, v3

    .line 72
    :goto_3
    if-ge v3, v5, :cond_6

    .line 73
    .line 74
    iget-object v8, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 75
    .line 76
    aget-object v9, v8, v3

    .line 77
    .line 78
    aput-object v4, v8, v3

    .line 79
    .line 80
    invoke-interface {p1, v9}, Ljava/util/Collection;->contains(Ljava/lang/Object;)Z

    .line 81
    .line 82
    .line 83
    move-result v8

    .line 84
    xor-int/2addr v8, v2

    .line 85
    if-eqz v8, :cond_5

    .line 86
    .line 87
    iget-object v8, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 88
    .line 89
    add-int/lit8 v10, v6, 0x1

    .line 90
    .line 91
    aput-object v9, v8, v6

    .line 92
    .line 93
    move v6, v10

    .line 94
    goto :goto_4

    .line 95
    :cond_5
    move v7, v2

    .line 96
    :goto_4
    add-int/lit8 v3, v3, 0x1

    .line 97
    .line 98
    goto :goto_3

    .line 99
    :cond_6
    invoke-virtual {p0, v6}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 100
    .line 101
    .line 102
    move-result v3

    .line 103
    move v5, v3

    .line 104
    move v3, v1

    .line 105
    :goto_5
    if-ge v3, v0, :cond_9

    .line 106
    .line 107
    iget-object v6, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 108
    .line 109
    aget-object v8, v6, v3

    .line 110
    .line 111
    aput-object v4, v6, v3

    .line 112
    .line 113
    invoke-interface {p1, v8}, Ljava/util/Collection;->contains(Ljava/lang/Object;)Z

    .line 114
    .line 115
    .line 116
    move-result v6

    .line 117
    xor-int/2addr v6, v2

    .line 118
    if-eqz v6, :cond_8

    .line 119
    .line 120
    iget-object v6, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 121
    .line 122
    aput-object v8, v6, v5

    .line 123
    .line 124
    array-length v6, v6

    .line 125
    add-int/lit8 v6, v6, -0x1

    .line 126
    .line 127
    if-ne v5, v6, :cond_7

    .line 128
    .line 129
    move v5, v1

    .line 130
    goto :goto_6

    .line 131
    :cond_7
    add-int/lit8 v5, v5, 0x1

    .line 132
    .line 133
    goto :goto_6

    .line 134
    :cond_8
    move v7, v2

    .line 135
    :goto_6
    add-int/lit8 v3, v3, 0x1

    .line 136
    .line 137
    goto :goto_5

    .line 138
    :cond_9
    move v1, v7

    .line 139
    :goto_7
    if-eqz v1, :cond_b

    .line 140
    .line 141
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 142
    .line 143
    sub-int/2addr v5, p1

    .line 144
    if-gez v5, :cond_a

    .line 145
    .line 146
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 147
    .line 148
    array-length p1, p1

    .line 149
    add-int/2addr v5, p1

    .line 150
    :cond_a
    iput v5, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 151
    .line 152
    :cond_b
    :goto_8
    return v1
.end method

.method public final removeAt(I)Ljava/lang/Object;
    .locals 8

    .line 1
    sget-object v0, Lkotlin/collections/AbstractList;->Companion:Lkotlin/collections/AbstractList$Companion;

    .line 2
    .line 3
    iget v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 4
    .line 5
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 6
    .line 7
    .line 8
    invoke-static {p1, v1}, Lkotlin/collections/AbstractList$Companion;->checkElementIndex$kotlin_stdlib(II)V

    .line 9
    .line 10
    .line 11
    invoke-static {p0}, Lkotlin/collections/CollectionsKt__CollectionsKt;->getLastIndex(Ljava/util/List;)I

    .line 12
    .line 13
    .line 14
    move-result v0

    .line 15
    const/4 v1, 0x0

    .line 16
    if-ne p1, v0, :cond_1

    .line 17
    .line 18
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->isEmpty()Z

    .line 19
    .line 20
    .line 21
    move-result p1

    .line 22
    if-nez p1, :cond_0

    .line 23
    .line 24
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 25
    .line 26
    invoke-static {p0}, Lkotlin/collections/CollectionsKt__CollectionsKt;->getLastIndex(Ljava/util/List;)I

    .line 27
    .line 28
    .line 29
    move-result v0

    .line 30
    add-int/2addr v0, p1

    .line 31
    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 32
    .line 33
    .line 34
    move-result p1

    .line 35
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 36
    .line 37
    aget-object v2, v0, p1

    .line 38
    .line 39
    aput-object v1, v0, p1

    .line 40
    .line 41
    iget p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 42
    .line 43
    add-int/lit8 p1, p1, -0x1

    .line 44
    .line 45
    iput p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 46
    .line 47
    return-object v2

    .line 48
    :cond_0
    new-instance p0, Ljava/util/NoSuchElementException;

    .line 49
    .line 50
    const-string p1, "ArrayDeque is empty."

    .line 51
    .line 52
    invoke-direct {p0, p1}, Ljava/util/NoSuchElementException;-><init>(Ljava/lang/String;)V

    .line 53
    .line 54
    .line 55
    throw p0

    .line 56
    :cond_1
    if-nez p1, :cond_2

    .line 57
    .line 58
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->removeFirst()Ljava/lang/Object;

    .line 59
    .line 60
    .line 61
    move-result-object p0

    .line 62
    return-object p0

    .line 63
    :cond_2
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 64
    .line 65
    add-int/2addr v0, p1

    .line 66
    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 67
    .line 68
    .line 69
    move-result v0

    .line 70
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 71
    .line 72
    aget-object v3, v2, v0

    .line 73
    .line 74
    iget v4, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 75
    .line 76
    const/4 v5, 0x1

    .line 77
    shr-int/2addr v4, v5

    .line 78
    const/4 v6, 0x0

    .line 79
    if-ge p1, v4, :cond_5

    .line 80
    .line 81
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 82
    .line 83
    if-lt v0, p1, :cond_3

    .line 84
    .line 85
    add-int/lit8 v4, p1, 0x1

    .line 86
    .line 87
    sub-int/2addr v0, p1

    .line 88
    invoke-static {v2, p1, v2, v4, v0}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 89
    .line 90
    .line 91
    goto :goto_0

    .line 92
    :cond_3
    sub-int/2addr v0, v6

    .line 93
    invoke-static {v2, v6, v2, v5, v0}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 94
    .line 95
    .line 96
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 97
    .line 98
    array-length v0, p1

    .line 99
    sub-int/2addr v0, v5

    .line 100
    aget-object v0, p1, v0

    .line 101
    .line 102
    aput-object v0, p1, v6

    .line 103
    .line 104
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 105
    .line 106
    add-int/lit8 v2, v0, 0x1

    .line 107
    .line 108
    array-length v4, p1

    .line 109
    sub-int/2addr v4, v5

    .line 110
    sub-int/2addr v4, v0

    .line 111
    invoke-static {p1, v0, p1, v2, v4}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 112
    .line 113
    .line 114
    :goto_0
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 115
    .line 116
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 117
    .line 118
    aput-object v1, p1, v0

    .line 119
    .line 120
    array-length p1, p1

    .line 121
    add-int/lit8 p1, p1, -0x1

    .line 122
    .line 123
    if-ne v0, p1, :cond_4

    .line 124
    .line 125
    goto :goto_1

    .line 126
    :cond_4
    add-int/lit8 v6, v0, 0x1

    .line 127
    .line 128
    :goto_1
    iput v6, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 129
    .line 130
    goto :goto_3

    .line 131
    :cond_5
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 132
    .line 133
    invoke-static {p0}, Lkotlin/collections/CollectionsKt__CollectionsKt;->getLastIndex(Ljava/util/List;)I

    .line 134
    .line 135
    .line 136
    move-result v2

    .line 137
    add-int/2addr v2, p1

    .line 138
    invoke-virtual {p0, v2}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 139
    .line 140
    .line 141
    move-result p1

    .line 142
    if-gt v0, p1, :cond_6

    .line 143
    .line 144
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 145
    .line 146
    add-int/lit8 v4, v0, 0x1

    .line 147
    .line 148
    add-int/lit8 v6, p1, 0x1

    .line 149
    .line 150
    sub-int/2addr v6, v4

    .line 151
    invoke-static {v2, v4, v2, v0, v6}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 152
    .line 153
    .line 154
    goto :goto_2

    .line 155
    :cond_6
    iget-object v2, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 156
    .line 157
    add-int/lit8 v4, v0, 0x1

    .line 158
    .line 159
    array-length v7, v2

    .line 160
    sub-int/2addr v7, v4

    .line 161
    invoke-static {v2, v4, v2, v0, v7}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 162
    .line 163
    .line 164
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 165
    .line 166
    array-length v2, v0

    .line 167
    sub-int/2addr v2, v5

    .line 168
    aget-object v4, v0, v6

    .line 169
    .line 170
    aput-object v4, v0, v2

    .line 171
    .line 172
    add-int/lit8 v2, p1, 0x1

    .line 173
    .line 174
    sub-int/2addr v2, v5

    .line 175
    invoke-static {v0, v5, v0, v6, v2}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 176
    .line 177
    .line 178
    :goto_2
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 179
    .line 180
    aput-object v1, v0, p1

    .line 181
    .line 182
    :goto_3
    iget p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 183
    .line 184
    sub-int/2addr p1, v5

    .line 185
    iput p1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 186
    .line 187
    return-object v3
.end method

.method public final removeFirst()Ljava/lang/Object;
    .locals 4

    .line 1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->isEmpty()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-nez v0, :cond_1

    .line 6
    .line 7
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 8
    .line 9
    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 10
    .line 11
    aget-object v2, v0, v1

    .line 12
    .line 13
    const/4 v3, 0x0

    .line 14
    aput-object v3, v0, v1

    .line 15
    .line 16
    array-length v0, v0

    .line 17
    add-int/lit8 v0, v0, -0x1

    .line 18
    .line 19
    if-ne v1, v0, :cond_0

    .line 20
    .line 21
    const/4 v0, 0x0

    .line 22
    goto :goto_0

    .line 23
    :cond_0
    add-int/lit8 v0, v1, 0x1

    .line 24
    .line 25
    :goto_0
    iput v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 26
    .line 27
    iget v0, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 28
    .line 29
    add-int/lit8 v0, v0, -0x1

    .line 30
    .line 31
    iput v0, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 32
    .line 33
    return-object v2

    .line 34
    :cond_1
    new-instance p0, Ljava/util/NoSuchElementException;

    .line 35
    .line 36
    const-string v0, "ArrayDeque is empty."

    .line 37
    .line 38
    invoke-direct {p0, v0}, Ljava/util/NoSuchElementException;-><init>(Ljava/lang/String;)V

    .line 39
    .line 40
    .line 41
    throw p0
.end method

.method public final retainAll(Ljava/util/Collection;)Z
    .locals 11

    .line 1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->isEmpty()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x0

    .line 6
    if-nez v0, :cond_b

    .line 7
    .line 8
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 9
    .line 10
    array-length v0, v0

    .line 11
    const/4 v2, 0x1

    .line 12
    if-nez v0, :cond_0

    .line 13
    .line 14
    move v0, v2

    .line 15
    goto :goto_0

    .line 16
    :cond_0
    move v0, v1

    .line 17
    :goto_0
    if-eqz v0, :cond_1

    .line 18
    .line 19
    goto/16 :goto_8

    .line 20
    .line 21
    :cond_1
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 22
    .line 23
    iget v3, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 24
    .line 25
    add-int/2addr v3, v0

    .line 26
    invoke-virtual {p0, v3}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 27
    .line 28
    .line 29
    move-result v0

    .line 30
    iget v3, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 31
    .line 32
    const/4 v4, 0x0

    .line 33
    if-ge v3, v0, :cond_4

    .line 34
    .line 35
    move v5, v3

    .line 36
    :goto_1
    if-ge v3, v0, :cond_3

    .line 37
    .line 38
    iget-object v6, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 39
    .line 40
    aget-object v6, v6, v3

    .line 41
    .line 42
    invoke-interface {p1, v6}, Ljava/util/Collection;->contains(Ljava/lang/Object;)Z

    .line 43
    .line 44
    .line 45
    move-result v7

    .line 46
    if-eqz v7, :cond_2

    .line 47
    .line 48
    iget-object v7, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 49
    .line 50
    add-int/lit8 v8, v5, 0x1

    .line 51
    .line 52
    aput-object v6, v7, v5

    .line 53
    .line 54
    move v5, v8

    .line 55
    goto :goto_2

    .line 56
    :cond_2
    move v1, v2

    .line 57
    :goto_2
    add-int/lit8 v3, v3, 0x1

    .line 58
    .line 59
    goto :goto_1

    .line 60
    :cond_3
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 61
    .line 62
    invoke-static {p1, v5, v0, v4}, Ljava/util/Arrays;->fill([Ljava/lang/Object;IILjava/lang/Object;)V

    .line 63
    .line 64
    .line 65
    goto :goto_7

    .line 66
    :cond_4
    iget-object v5, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 67
    .line 68
    array-length v5, v5

    .line 69
    move v7, v1

    .line 70
    move v6, v3

    .line 71
    :goto_3
    if-ge v3, v5, :cond_6

    .line 72
    .line 73
    iget-object v8, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 74
    .line 75
    aget-object v9, v8, v3

    .line 76
    .line 77
    aput-object v4, v8, v3

    .line 78
    .line 79
    invoke-interface {p1, v9}, Ljava/util/Collection;->contains(Ljava/lang/Object;)Z

    .line 80
    .line 81
    .line 82
    move-result v8

    .line 83
    if-eqz v8, :cond_5

    .line 84
    .line 85
    iget-object v8, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 86
    .line 87
    add-int/lit8 v10, v6, 0x1

    .line 88
    .line 89
    aput-object v9, v8, v6

    .line 90
    .line 91
    move v6, v10

    .line 92
    goto :goto_4

    .line 93
    :cond_5
    move v7, v2

    .line 94
    :goto_4
    add-int/lit8 v3, v3, 0x1

    .line 95
    .line 96
    goto :goto_3

    .line 97
    :cond_6
    invoke-virtual {p0, v6}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 98
    .line 99
    .line 100
    move-result v3

    .line 101
    move v5, v3

    .line 102
    move v3, v1

    .line 103
    :goto_5
    if-ge v3, v0, :cond_9

    .line 104
    .line 105
    iget-object v6, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 106
    .line 107
    aget-object v8, v6, v3

    .line 108
    .line 109
    aput-object v4, v6, v3

    .line 110
    .line 111
    invoke-interface {p1, v8}, Ljava/util/Collection;->contains(Ljava/lang/Object;)Z

    .line 112
    .line 113
    .line 114
    move-result v6

    .line 115
    if-eqz v6, :cond_8

    .line 116
    .line 117
    iget-object v6, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 118
    .line 119
    aput-object v8, v6, v5

    .line 120
    .line 121
    array-length v6, v6

    .line 122
    add-int/lit8 v6, v6, -0x1

    .line 123
    .line 124
    if-ne v5, v6, :cond_7

    .line 125
    .line 126
    move v5, v1

    .line 127
    goto :goto_6

    .line 128
    :cond_7
    add-int/lit8 v5, v5, 0x1

    .line 129
    .line 130
    goto :goto_6

    .line 131
    :cond_8
    move v7, v2

    .line 132
    :goto_6
    add-int/lit8 v3, v3, 0x1

    .line 133
    .line 134
    goto :goto_5

    .line 135
    :cond_9
    move v1, v7

    .line 136
    :goto_7
    if-eqz v1, :cond_b

    .line 137
    .line 138
    iget p1, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 139
    .line 140
    sub-int/2addr v5, p1

    .line 141
    if-gez v5, :cond_a

    .line 142
    .line 143
    iget-object p1, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 144
    .line 145
    array-length p1, p1

    .line 146
    add-int/2addr v5, p1

    .line 147
    :cond_a
    iput v5, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 148
    .line 149
    :cond_b
    :goto_8
    return v1
.end method

.method public final set(ILjava/lang/Object;)Ljava/lang/Object;
    .locals 2

    .line 1
    sget-object v0, Lkotlin/collections/AbstractList;->Companion:Lkotlin/collections/AbstractList$Companion;

    .line 2
    .line 3
    iget v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    .line 4
    .line 5
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 6
    .line 7
    .line 8
    invoke-static {p1, v1}, Lkotlin/collections/AbstractList$Companion;->checkElementIndex$kotlin_stdlib(II)V

    .line 9
    .line 10
    .line 11
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 12
    .line 13
    add-int/2addr v0, p1

    .line 14
    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    .line 15
    .line 16
    .line 17
    move-result p1

    .line 18
    iget-object p0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    .line 19
    .line 20
    aget-object v0, p0, p1

    .line 21
    .line 22
    aput-object p2, p0, p1

    .line 23
    .line 24
    return-object v0
.end method

.method public final toArray()[Ljava/lang/Object;
    .locals 1

    .line 1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->getSize()I

    move-result v0

    .line 2
    new-array v0, v0, [Ljava/lang/Object;

    invoke-virtual {p0, v0}, Lkotlin/collections/ArrayDeque;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method public final toArray([Ljava/lang/Object;)[Ljava/lang/Object;
    .locals 6

    .line 3
    array-length v0, p1

    .line 4
    iget v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    if-lt v0, v1, :cond_0

    goto :goto_0

    .line 5
    :cond_0
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Class;->getComponentType()Ljava/lang/Class;

    move-result-object p1

    invoke-static {p1, v1}, Ljava/lang/reflect/Array;->newInstance(Ljava/lang/Class;I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, [Ljava/lang/Object;

    .line 6
    :goto_0
    iget v0, p0, Lkotlin/collections/ArrayDeque;->head:I

    .line 7
    iget v1, p0, Lkotlin/collections/ArrayDeque;->size:I

    add-int/2addr v1, v0

    .line 8
    invoke-virtual {p0, v1}, Lkotlin/collections/ArrayDeque;->positiveMod(I)I

    move-result v4

    .line 9
    iget v3, p0, Lkotlin/collections/ArrayDeque;->head:I

    if-ge v3, v4, :cond_1

    .line 10
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    const/4 v2, 0x0

    const/4 v5, 0x2

    move-object v1, p1

    invoke-static/range {v0 .. v5}, Lkotlin/collections/ArraysKt___ArraysJvmKt;->copyInto$default([Ljava/lang/Object;[Ljava/lang/Object;IIII)V

    goto :goto_1

    .line 11
    :cond_1
    invoke-virtual {p0}, Lkotlin/collections/ArrayDeque;->isEmpty()Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    if-eqz v0, :cond_2

    .line 12
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    iget v1, p0, Lkotlin/collections/ArrayDeque;->head:I

    array-length v2, v0

    sub-int/2addr v2, v1

    const/4 v3, 0x0

    .line 13
    invoke-static {v0, v1, p1, v3, v2}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 14
    iget-object v0, p0, Lkotlin/collections/ArrayDeque;->elementData:[Ljava/lang/Object;

    array-length v1, v0

    iget v2, p0, Lkotlin/collections/ArrayDeque;->head:I

    sub-int/2addr v1, v2

    sub-int/2addr v4, v3

    .line 15
    invoke-static {v0, v3, p1, v1, v4}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 16
    :cond_2
    :goto_1
    array-length v0, p1

    .line 17
    iget p0, p0, Lkotlin/collections/ArrayDeque;->size:I

    if-le v0, p0, :cond_3

    const/4 v0, 0x0

    .line 18
    aput-object v0, p1, p0

    :cond_3
    return-object p1
.end method
