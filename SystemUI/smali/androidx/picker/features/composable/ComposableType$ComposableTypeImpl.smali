.class public final Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroidx/picker/features/composable/ComposableType;


# instance fields
.field public final iconFrame:Landroidx/picker/features/composable/ComposableFrame;

.field public final leftFrame:Landroidx/picker/features/composable/ComposableFrame;

.field public final titleFrame:Landroidx/picker/features/composable/ComposableFrame;

.field public final widgetFrame:Landroidx/picker/features/composable/ComposableFrame;


# direct methods
.method public constructor <init>(Landroidx/picker/features/composable/ComposableFrame;Landroidx/picker/features/composable/ComposableFrame;Landroidx/picker/features/composable/ComposableFrame;Landroidx/picker/features/composable/ComposableFrame;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->leftFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 5
    .line 6
    iput-object p2, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->iconFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 7
    .line 8
    iput-object p3, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->titleFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 9
    .line 10
    iput-object p4, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->widgetFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 11
    .line 12
    return-void
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
    instance-of v1, p1, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;

    .line 6
    .line 7
    const/4 v2, 0x0

    .line 8
    if-nez v1, :cond_1

    .line 9
    .line 10
    return v2

    .line 11
    :cond_1
    check-cast p1, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;

    .line 12
    .line 13
    iget-object v1, p1, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->leftFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 14
    .line 15
    iget-object v3, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->leftFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 16
    .line 17
    invoke-static {v3, v1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 18
    .line 19
    .line 20
    move-result v1

    .line 21
    if-nez v1, :cond_2

    .line 22
    .line 23
    return v2

    .line 24
    :cond_2
    iget-object v1, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->iconFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 25
    .line 26
    iget-object v3, p1, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->iconFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 27
    .line 28
    invoke-static {v1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 29
    .line 30
    .line 31
    move-result v1

    .line 32
    if-nez v1, :cond_3

    .line 33
    .line 34
    return v2

    .line 35
    :cond_3
    iget-object v1, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->titleFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 36
    .line 37
    iget-object v3, p1, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->titleFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 38
    .line 39
    invoke-static {v1, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 40
    .line 41
    .line 42
    move-result v1

    .line 43
    if-nez v1, :cond_4

    .line 44
    .line 45
    return v2

    .line 46
    :cond_4
    iget-object p0, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->widgetFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 47
    .line 48
    iget-object p1, p1, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->widgetFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 49
    .line 50
    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 51
    .line 52
    .line 53
    move-result p0

    .line 54
    if-nez p0, :cond_5

    .line 55
    .line 56
    return v2

    .line 57
    :cond_5
    return v0
.end method

.method public final getIconFrame()Landroidx/picker/features/composable/ComposableFrame;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->iconFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 2
    .line 3
    return-object p0
.end method

.method public final getLeftFrame()Landroidx/picker/features/composable/ComposableFrame;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->leftFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 2
    .line 3
    return-object p0
.end method

.method public final getTitleFrame()Landroidx/picker/features/composable/ComposableFrame;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->titleFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 2
    .line 3
    return-object p0
.end method

.method public final getWidgetFrame()Landroidx/picker/features/composable/ComposableFrame;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->widgetFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 2
    .line 3
    return-object p0
.end method

.method public final hashCode()I
    .locals 3

    .line 1
    const/4 v0, 0x0

    .line 2
    iget-object v1, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->leftFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 3
    .line 4
    if-nez v1, :cond_0

    .line 5
    .line 6
    move v1, v0

    .line 7
    goto :goto_0

    .line 8
    :cond_0
    invoke-virtual {v1}, Ljava/lang/Object;->hashCode()I

    .line 9
    .line 10
    .line 11
    move-result v1

    .line 12
    :goto_0
    mul-int/lit8 v1, v1, 0x1f

    .line 13
    .line 14
    iget-object v2, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->iconFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 15
    .line 16
    if-nez v2, :cond_1

    .line 17
    .line 18
    move v2, v0

    .line 19
    goto :goto_1

    .line 20
    :cond_1
    invoke-virtual {v2}, Ljava/lang/Object;->hashCode()I

    .line 21
    .line 22
    .line 23
    move-result v2

    .line 24
    :goto_1
    add-int/2addr v1, v2

    .line 25
    mul-int/lit8 v1, v1, 0x1f

    .line 26
    .line 27
    iget-object v2, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->titleFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 28
    .line 29
    if-nez v2, :cond_2

    .line 30
    .line 31
    move v2, v0

    .line 32
    goto :goto_2

    .line 33
    :cond_2
    invoke-virtual {v2}, Ljava/lang/Object;->hashCode()I

    .line 34
    .line 35
    .line 36
    move-result v2

    .line 37
    :goto_2
    add-int/2addr v1, v2

    .line 38
    mul-int/lit8 v1, v1, 0x1f

    .line 39
    .line 40
    iget-object p0, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->widgetFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 41
    .line 42
    if-nez p0, :cond_3

    .line 43
    .line 44
    goto :goto_3

    .line 45
    :cond_3
    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    .line 46
    .line 47
    .line 48
    move-result v0

    .line 49
    :goto_3
    add-int/2addr v1, v0

    .line 50
    return v1
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "ComposableTypeImpl(leftFrame="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget-object v1, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->leftFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", iconFrame="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget-object v1, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->iconFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 19
    .line 20
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const-string v1, ", titleFrame="

    .line 24
    .line 25
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    iget-object v1, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->titleFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 29
    .line 30
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 31
    .line 32
    .line 33
    const-string v1, ", widgetFrame="

    .line 34
    .line 35
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 36
    .line 37
    .line 38
    iget-object p0, p0, Landroidx/picker/features/composable/ComposableType$ComposableTypeImpl;->widgetFrame:Landroidx/picker/features/composable/ComposableFrame;

    .line 39
    .line 40
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 41
    .line 42
    .line 43
    const/16 p0, 0x29

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