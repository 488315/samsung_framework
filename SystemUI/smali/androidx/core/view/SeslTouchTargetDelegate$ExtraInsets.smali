.class public final Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final NONE:Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;


# instance fields
.field public final bottom:I

.field public final left:I

.field public final right:I

.field public final top:I


# direct methods
.method public static constructor <clinit>()V
    .locals 2

    .line 1
    new-instance v0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    invoke-direct {v0, v1, v1, v1, v1}, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;-><init>(IIII)V

    .line 5
    .line 6
    .line 7
    sput-object v0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->NONE:Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;

    .line 8
    .line 9
    return-void
.end method

.method private constructor <init>(IIII)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput p1, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->left:I

    .line 5
    .line 6
    iput p2, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->top:I

    .line 7
    .line 8
    iput p3, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->right:I

    .line 9
    .line 10
    iput p4, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->bottom:I

    .line 11
    .line 12
    return-void
.end method

.method public static of(IIII)Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;
    .locals 1

    .line 1
    if-nez p0, :cond_0

    .line 2
    .line 3
    if-nez p1, :cond_0

    .line 4
    .line 5
    if-nez p2, :cond_0

    .line 6
    .line 7
    if-nez p3, :cond_0

    .line 8
    .line 9
    sget-object p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->NONE:Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;

    .line 10
    .line 11
    return-object p0

    .line 12
    :cond_0
    new-instance v0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;

    .line 13
    .line 14
    invoke-direct {v0, p0, p1, p2, p3}, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;-><init>(IIII)V

    .line 15
    .line 16
    .line 17
    return-object v0
.end method


# virtual methods
.method public final equals(Ljava/lang/Object;)Z
    .locals 4

    .line 1
    const/4 v0, 0x1

    .line 2
    if-ne p0, p1, :cond_0

    .line 3
    .line 4
    return v0

    .line 5
    :cond_0
    const/4 v1, 0x0

    .line 6
    if-eqz p1, :cond_6

    .line 7
    .line 8
    const-class v2, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;

    .line 9
    .line 10
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 11
    .line 12
    .line 13
    move-result-object v3

    .line 14
    if-eq v2, v3, :cond_1

    .line 15
    .line 16
    goto :goto_0

    .line 17
    :cond_1
    check-cast p1, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;

    .line 18
    .line 19
    iget v2, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->bottom:I

    .line 20
    .line 21
    iget v3, p1, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->bottom:I

    .line 22
    .line 23
    if-eq v2, v3, :cond_2

    .line 24
    .line 25
    return v1

    .line 26
    :cond_2
    iget v2, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->left:I

    .line 27
    .line 28
    iget v3, p1, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->left:I

    .line 29
    .line 30
    if-eq v2, v3, :cond_3

    .line 31
    .line 32
    return v1

    .line 33
    :cond_3
    iget v2, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->right:I

    .line 34
    .line 35
    iget v3, p1, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->right:I

    .line 36
    .line 37
    if-eq v2, v3, :cond_4

    .line 38
    .line 39
    return v1

    .line 40
    :cond_4
    iget p0, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->top:I

    .line 41
    .line 42
    iget p1, p1, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->top:I

    .line 43
    .line 44
    if-eq p0, p1, :cond_5

    .line 45
    .line 46
    return v1

    .line 47
    :cond_5
    return v0

    .line 48
    :cond_6
    :goto_0
    return v1
.end method

.method public final hashCode()I
    .locals 2

    .line 1
    iget v0, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->left:I

    .line 2
    .line 3
    mul-int/lit8 v0, v0, 0x1f

    .line 4
    .line 5
    iget v1, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->top:I

    .line 6
    .line 7
    add-int/2addr v0, v1

    .line 8
    mul-int/lit8 v0, v0, 0x1f

    .line 9
    .line 10
    iget v1, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->right:I

    .line 11
    .line 12
    add-int/2addr v0, v1

    .line 13
    mul-int/lit8 v0, v0, 0x1f

    .line 14
    .line 15
    iget p0, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->bottom:I

    .line 16
    .line 17
    add-int/2addr v0, p0

    .line 18
    return v0
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "ExtraInsets{left="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget v1, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->left:I

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", top="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget v1, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->top:I

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", right="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget v1, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->right:I

    .line 29
    .line 30
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 31
    .line 32
    .line 33
    const-string v1, ", bottom="

    .line 34
    .line 35
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 36
    .line 37
    .line 38
    iget p0, p0, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->bottom:I

    .line 39
    .line 40
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 41
    .line 42
    .line 43
    const/16 p0, 0x7d

    .line 44
    .line 45
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 46
    .line 47
    .line 48
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 49
    .line 50
    .line 51
    move-result-object p0

    .line 52
    return-object p0
.end method
