.class public final Landroidx/mediarouter/app/MediaRouteDialogHelper;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static getDialogWidth(Landroid/content/Context;)I
    .locals 4

    .line 1
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    .line 6
    .line 7
    .line 8
    move-result-object v0

    .line 9
    iget v1, v0, Landroid/util/DisplayMetrics;->widthPixels:I

    .line 10
    .line 11
    iget v2, v0, Landroid/util/DisplayMetrics;->heightPixels:I

    .line 12
    .line 13
    const/4 v3, 0x1

    .line 14
    if-ge v1, v2, :cond_0

    .line 15
    .line 16
    move v1, v3

    .line 17
    goto :goto_0

    .line 18
    :cond_0
    const/4 v1, 0x0

    .line 19
    :goto_0
    new-instance v2, Landroid/util/TypedValue;

    .line 20
    .line 21
    invoke-direct {v2}, Landroid/util/TypedValue;-><init>()V

    .line 22
    .line 23
    .line 24
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 25
    .line 26
    .line 27
    move-result-object p0

    .line 28
    if-eqz v1, :cond_1

    .line 29
    .line 30
    const v1, 0x7f07087e

    .line 31
    .line 32
    .line 33
    goto :goto_1

    .line 34
    :cond_1
    const v1, 0x7f07087d

    .line 35
    .line 36
    .line 37
    :goto_1
    invoke-virtual {p0, v1, v2, v3}, Landroid/content/res/Resources;->getValue(ILandroid/util/TypedValue;Z)V

    .line 38
    .line 39
    .line 40
    iget p0, v2, Landroid/util/TypedValue;->type:I

    .line 41
    .line 42
    const/4 v1, 0x5

    .line 43
    if-ne p0, v1, :cond_2

    .line 44
    .line 45
    invoke-virtual {v2, v0}, Landroid/util/TypedValue;->getDimension(Landroid/util/DisplayMetrics;)F

    .line 46
    .line 47
    .line 48
    move-result p0

    .line 49
    :goto_2
    float-to-int p0, p0

    .line 50
    return p0

    .line 51
    :cond_2
    const/4 v1, 0x6

    .line 52
    if-ne p0, v1, :cond_3

    .line 53
    .line 54
    iget p0, v0, Landroid/util/DisplayMetrics;->widthPixels:I

    .line 55
    .line 56
    int-to-float v0, p0

    .line 57
    int-to-float p0, p0

    .line 58
    invoke-virtual {v2, v0, p0}, Landroid/util/TypedValue;->getFraction(FF)F

    .line 59
    .line 60
    .line 61
    move-result p0

    .line 62
    goto :goto_2

    .line 63
    :cond_3
    const/4 p0, -0x2

    .line 64
    return p0
.end method