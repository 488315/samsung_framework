.class public final Lkotlin/ranges/ClosedFloatRange;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final _endInclusive:F

.field public final _start:F


# direct methods
.method public constructor <init>(FF)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput p1, p0, Lkotlin/ranges/ClosedFloatRange;->_start:F

    .line 5
    .line 6
    iput p2, p0, Lkotlin/ranges/ClosedFloatRange;->_endInclusive:F

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final equals(Ljava/lang/Object;)Z
    .locals 5

    .line 1
    instance-of v0, p1, Lkotlin/ranges/ClosedFloatRange;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    if-eqz v0, :cond_6

    .line 5
    .line 6
    iget v0, p0, Lkotlin/ranges/ClosedFloatRange;->_start:F

    .line 7
    .line 8
    iget p0, p0, Lkotlin/ranges/ClosedFloatRange;->_endInclusive:F

    .line 9
    .line 10
    cmpg-float v2, v0, p0

    .line 11
    .line 12
    const/4 v3, 0x1

    .line 13
    if-lez v2, :cond_0

    .line 14
    .line 15
    move v2, v3

    .line 16
    goto :goto_0

    .line 17
    :cond_0
    move v2, v1

    .line 18
    :goto_0
    if-eqz v2, :cond_2

    .line 19
    .line 20
    move-object v2, p1

    .line 21
    check-cast v2, Lkotlin/ranges/ClosedFloatRange;

    .line 22
    .line 23
    iget v4, v2, Lkotlin/ranges/ClosedFloatRange;->_start:F

    .line 24
    .line 25
    iget v2, v2, Lkotlin/ranges/ClosedFloatRange;->_endInclusive:F

    .line 26
    .line 27
    cmpg-float v2, v4, v2

    .line 28
    .line 29
    if-lez v2, :cond_1

    .line 30
    .line 31
    move v2, v3

    .line 32
    goto :goto_1

    .line 33
    :cond_1
    move v2, v1

    .line 34
    :goto_1
    if-nez v2, :cond_5

    .line 35
    .line 36
    :cond_2
    check-cast p1, Lkotlin/ranges/ClosedFloatRange;

    .line 37
    .line 38
    iget v2, p1, Lkotlin/ranges/ClosedFloatRange;->_start:F

    .line 39
    .line 40
    cmpg-float v0, v0, v2

    .line 41
    .line 42
    if-nez v0, :cond_3

    .line 43
    .line 44
    move v0, v3

    .line 45
    goto :goto_2

    .line 46
    :cond_3
    move v0, v1

    .line 47
    :goto_2
    if-eqz v0, :cond_6

    .line 48
    .line 49
    iget p1, p1, Lkotlin/ranges/ClosedFloatRange;->_endInclusive:F

    .line 50
    .line 51
    cmpg-float p0, p0, p1

    .line 52
    .line 53
    if-nez p0, :cond_4

    .line 54
    .line 55
    move p0, v3

    .line 56
    goto :goto_3

    .line 57
    :cond_4
    move p0, v1

    .line 58
    :goto_3
    if-eqz p0, :cond_6

    .line 59
    .line 60
    :cond_5
    move v1, v3

    .line 61
    :cond_6
    return v1
.end method

.method public final hashCode()I
    .locals 2

    .line 1
    iget v0, p0, Lkotlin/ranges/ClosedFloatRange;->_start:F

    .line 2
    .line 3
    iget p0, p0, Lkotlin/ranges/ClosedFloatRange;->_endInclusive:F

    .line 4
    .line 5
    cmpg-float v1, v0, p0

    .line 6
    .line 7
    if-lez v1, :cond_0

    .line 8
    .line 9
    const/4 v1, 0x1

    .line 10
    goto :goto_0

    .line 11
    :cond_0
    const/4 v1, 0x0

    .line 12
    :goto_0
    if-eqz v1, :cond_1

    .line 13
    .line 14
    const/4 p0, -0x1

    .line 15
    goto :goto_1

    .line 16
    :cond_1
    invoke-static {v0}, Ljava/lang/Float;->hashCode(F)I

    .line 17
    .line 18
    .line 19
    move-result v0

    .line 20
    mul-int/lit8 v0, v0, 0x1f

    .line 21
    .line 22
    invoke-static {p0}, Ljava/lang/Float;->hashCode(F)I

    .line 23
    .line 24
    .line 25
    move-result p0

    .line 26
    add-int/2addr p0, v0

    .line 27
    :goto_1
    return p0
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 4
    .line 5
    .line 6
    iget v1, p0, Lkotlin/ranges/ClosedFloatRange;->_start:F

    .line 7
    .line 8
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 9
    .line 10
    .line 11
    const-string v1, ".."

    .line 12
    .line 13
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 14
    .line 15
    .line 16
    iget p0, p0, Lkotlin/ranges/ClosedFloatRange;->_endInclusive:F

    .line 17
    .line 18
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 19
    .line 20
    .line 21
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 22
    .line 23
    .line 24
    move-result-object p0

    .line 25
    return-object p0
.end method
