.class public abstract Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;
.super Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VObject;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mChangingConfigurations:I

.field public mFillRule:I

.field public mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

.field public mPathName:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 1

    const/4 v0, 0x0

    .line 1
    invoke-direct {p0, v0}, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VObject;-><init>(Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$1;)V

    .line 2
    iput-object v0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    const/4 v0, 0x0

    .line 3
    iput v0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mFillRule:I

    return-void
.end method

.method public constructor <init>(Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;)V
    .locals 1

    const/4 v0, 0x0

    .line 4
    invoke-direct {p0, v0}, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VObject;-><init>(Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$1;)V

    .line 5
    iput-object v0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    const/4 v0, 0x0

    .line 6
    iput v0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mFillRule:I

    .line 7
    iget-object v0, p1, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mPathName:Ljava/lang/String;

    iput-object v0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mPathName:Ljava/lang/String;

    .line 8
    iget v0, p1, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mChangingConfigurations:I

    iput v0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mChangingConfigurations:I

    .line 9
    iget-object p1, p1, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    invoke-static {p1}, Landroidx/core/graphics/PathParser;->deepCopyNodes([Landroidx/core/graphics/PathParser$PathDataNode;)[Landroidx/core/graphics/PathParser$PathDataNode;

    move-result-object p1

    iput-object p1, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    return-void
.end method


# virtual methods
.method public getPathData()[Landroidx/core/graphics/PathParser$PathDataNode;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    .line 2
    .line 3
    return-object p0
.end method

.method public getPathName()Ljava/lang/String;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mPathName:Ljava/lang/String;

    .line 2
    .line 3
    return-object p0
.end method

.method public setPathData([Landroidx/core/graphics/PathParser$PathDataNode;)V
    .locals 5

    .line 1
    iget-object v0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    .line 2
    .line 3
    invoke-static {v0, p1}, Landroidx/core/graphics/PathParser;->canMorph([Landroidx/core/graphics/PathParser$PathDataNode;[Landroidx/core/graphics/PathParser$PathDataNode;)Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    if-nez v0, :cond_0

    .line 8
    .line 9
    invoke-static {p1}, Landroidx/core/graphics/PathParser;->deepCopyNodes([Landroidx/core/graphics/PathParser$PathDataNode;)[Landroidx/core/graphics/PathParser$PathDataNode;

    .line 10
    .line 11
    .line 12
    move-result-object p1

    .line 13
    iput-object p1, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    .line 14
    .line 15
    goto :goto_2

    .line 16
    :cond_0
    iget-object p0, p0, Landroidx/vectordrawable/graphics/drawable/VectorDrawableCompat$VPath;->mNodes:[Landroidx/core/graphics/PathParser$PathDataNode;

    .line 17
    .line 18
    const/4 v0, 0x0

    .line 19
    move v1, v0

    .line 20
    :goto_0
    array-length v2, p1

    .line 21
    if-ge v1, v2, :cond_2

    .line 22
    .line 23
    aget-object v2, p0, v1

    .line 24
    .line 25
    aget-object v3, p1, v1

    .line 26
    .line 27
    iget-char v3, v3, Landroidx/core/graphics/PathParser$PathDataNode;->mType:C

    .line 28
    .line 29
    iput-char v3, v2, Landroidx/core/graphics/PathParser$PathDataNode;->mType:C

    .line 30
    .line 31
    move v2, v0

    .line 32
    :goto_1
    aget-object v3, p1, v1

    .line 33
    .line 34
    iget-object v3, v3, Landroidx/core/graphics/PathParser$PathDataNode;->mParams:[F

    .line 35
    .line 36
    array-length v4, v3

    .line 37
    if-ge v2, v4, :cond_1

    .line 38
    .line 39
    aget-object v4, p0, v1

    .line 40
    .line 41
    iget-object v4, v4, Landroidx/core/graphics/PathParser$PathDataNode;->mParams:[F

    .line 42
    .line 43
    aget v3, v3, v2

    .line 44
    .line 45
    aput v3, v4, v2

    .line 46
    .line 47
    add-int/lit8 v2, v2, 0x1

    .line 48
    .line 49
    goto :goto_1

    .line 50
    :cond_1
    add-int/lit8 v1, v1, 0x1

    .line 51
    .line 52
    goto :goto_0

    .line 53
    :cond_2
    :goto_2
    return-void
.end method
