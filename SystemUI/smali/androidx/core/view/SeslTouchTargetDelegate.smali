.class public final Landroidx/core/view/SeslTouchTargetDelegate;
.super Landroid/view/TouchDelegate;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final synthetic $r8$clinit:I


# instance fields
.field public final mAnchorView:Landroid/view/View;

.field public final mTouchDelegateSet:Ljava/util/HashSet;


# direct methods
.method public constructor <init>(Landroid/view/View;)V
    .locals 1

    .line 1
    new-instance v0, Landroid/graphics/Rect;

    .line 2
    .line 3
    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    .line 4
    .line 5
    .line 6
    invoke-direct {p0, v0, p1}, Landroid/view/TouchDelegate;-><init>(Landroid/graphics/Rect;Landroid/view/View;)V

    .line 7
    .line 8
    .line 9
    new-instance v0, Ljava/util/HashSet;

    .line 10
    .line 11
    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    .line 12
    .line 13
    .line 14
    iput-object v0, p0, Landroidx/core/view/SeslTouchTargetDelegate;->mTouchDelegateSet:Ljava/util/HashSet;

    .line 15
    .line 16
    iput-object p1, p0, Landroidx/core/view/SeslTouchTargetDelegate;->mAnchorView:Landroid/view/View;

    .line 17
    .line 18
    return-void
.end method

.method public static calculateViewBounds(Landroid/view/View;Landroid/view/View;)Landroid/graphics/Rect;
    .locals 4

    .line 1
    new-instance v0, Landroid/graphics/Rect;

    .line 2
    .line 3
    invoke-virtual {p1}, Landroid/view/View;->getWidth()I

    .line 4
    .line 5
    .line 6
    move-result v1

    .line 7
    invoke-virtual {p1}, Landroid/view/View;->getHeight()I

    .line 8
    .line 9
    .line 10
    move-result v2

    .line 11
    const/4 v3, 0x0

    .line 12
    invoke-direct {v0, v3, v3, v1, v2}, Landroid/graphics/Rect;-><init>(IIII)V

    .line 13
    .line 14
    .line 15
    new-instance v1, Landroid/graphics/Rect;

    .line 16
    .line 17
    invoke-direct {v1}, Landroid/graphics/Rect;-><init>()V

    .line 18
    .line 19
    .line 20
    :goto_0
    invoke-virtual {p1, p0}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    .line 21
    .line 22
    .line 23
    move-result v2

    .line 24
    if-nez v2, :cond_1

    .line 25
    .line 26
    invoke-virtual {p1, v1}, Landroid/view/View;->getHitRect(Landroid/graphics/Rect;)V

    .line 27
    .line 28
    .line 29
    iget v2, v0, Landroid/graphics/Rect;->left:I

    .line 30
    .line 31
    iget v3, v1, Landroid/graphics/Rect;->left:I

    .line 32
    .line 33
    add-int/2addr v2, v3

    .line 34
    iput v2, v0, Landroid/graphics/Rect;->left:I

    .line 35
    .line 36
    iget v2, v0, Landroid/graphics/Rect;->right:I

    .line 37
    .line 38
    iget v3, v1, Landroid/graphics/Rect;->left:I

    .line 39
    .line 40
    add-int/2addr v2, v3

    .line 41
    iput v2, v0, Landroid/graphics/Rect;->right:I

    .line 42
    .line 43
    iget v2, v0, Landroid/graphics/Rect;->top:I

    .line 44
    .line 45
    iget v3, v1, Landroid/graphics/Rect;->top:I

    .line 46
    .line 47
    add-int/2addr v2, v3

    .line 48
    iput v2, v0, Landroid/graphics/Rect;->top:I

    .line 49
    .line 50
    iget v2, v0, Landroid/graphics/Rect;->bottom:I

    .line 51
    .line 52
    iget v3, v1, Landroid/graphics/Rect;->top:I

    .line 53
    .line 54
    add-int/2addr v2, v3

    .line 55
    iput v2, v0, Landroid/graphics/Rect;->bottom:I

    .line 56
    .line 57
    invoke-virtual {p1}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    .line 58
    .line 59
    .line 60
    move-result-object v2

    .line 61
    instance-of v3, v2, Landroid/view/View;

    .line 62
    .line 63
    if-nez v3, :cond_0

    .line 64
    .line 65
    goto :goto_1

    .line 66
    :cond_0
    move-object p1, v2

    .line 67
    check-cast p1, Landroid/view/View;

    .line 68
    .line 69
    goto :goto_0

    .line 70
    :cond_1
    :goto_1
    invoke-virtual {p1, p0}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    .line 71
    .line 72
    .line 73
    move-result p0

    .line 74
    if-eqz p0, :cond_2

    .line 75
    .line 76
    return-object v0

    .line 77
    :cond_2
    new-instance p0, Landroidx/core/view/SeslTouchTargetDelegate$InvalidDelegateViewException;

    .line 78
    .line 79
    invoke-direct {p0}, Landroidx/core/view/SeslTouchTargetDelegate$InvalidDelegateViewException;-><init>()V

    .line 80
    .line 81
    .line 82
    throw p0
.end method


# virtual methods
.method public final addTouchDelegate(Landroid/view/View;Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;)V
    .locals 3

    .line 1
    :try_start_0
    iget-object v0, p0, Landroidx/core/view/SeslTouchTargetDelegate;->mAnchorView:Landroid/view/View;

    .line 2
    .line 3
    invoke-static {v0, p1}, Landroidx/core/view/SeslTouchTargetDelegate;->calculateViewBounds(Landroid/view/View;Landroid/view/View;)Landroid/graphics/Rect;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    if-eqz p2, :cond_0

    .line 8
    .line 9
    iget v1, v0, Landroid/graphics/Rect;->left:I

    .line 10
    .line 11
    iget v2, p2, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->left:I

    .line 12
    .line 13
    sub-int/2addr v1, v2

    .line 14
    iput v1, v0, Landroid/graphics/Rect;->left:I

    .line 15
    .line 16
    iget v1, v0, Landroid/graphics/Rect;->top:I

    .line 17
    .line 18
    iget v2, p2, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->top:I

    .line 19
    .line 20
    sub-int/2addr v1, v2

    .line 21
    iput v1, v0, Landroid/graphics/Rect;->top:I

    .line 22
    .line 23
    iget v1, v0, Landroid/graphics/Rect;->right:I

    .line 24
    .line 25
    iget v2, p2, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->right:I

    .line 26
    .line 27
    add-int/2addr v1, v2

    .line 28
    iput v1, v0, Landroid/graphics/Rect;->right:I

    .line 29
    .line 30
    iget v1, v0, Landroid/graphics/Rect;->bottom:I

    .line 31
    .line 32
    iget p2, p2, Landroidx/core/view/SeslTouchTargetDelegate$ExtraInsets;->bottom:I

    .line 33
    .line 34
    add-int/2addr v1, p2

    .line 35
    iput v1, v0, Landroid/graphics/Rect;->bottom:I

    .line 36
    .line 37
    :cond_0
    new-instance p2, Landroidx/core/view/SeslTouchTargetDelegate$CapturedTouchDelegate;

    .line 38
    .line 39
    invoke-direct {p2, v0, p1}, Landroidx/core/view/SeslTouchTargetDelegate$CapturedTouchDelegate;-><init>(Landroid/graphics/Rect;Landroid/view/View;)V

    .line 40
    .line 41
    .line 42
    iget-object p0, p0, Landroidx/core/view/SeslTouchTargetDelegate;->mTouchDelegateSet:Ljava/util/HashSet;

    .line 43
    .line 44
    invoke-virtual {p0, p2}, Ljava/util/HashSet;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catch Landroidx/core/view/SeslTouchTargetDelegate$InvalidDelegateViewException; {:try_start_0 .. :try_end_0} :catch_0

    .line 45
    .line 46
    .line 47
    return-void

    .line 48
    :catch_0
    move-exception p0

    .line 49
    const-string p1, "SeslTouchTargetDelegate"

    .line 50
    .line 51
    const-string p2, "delegateView must be child of anchorView"

    .line 52
    .line 53
    invoke-static {p1, p2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 54
    .line 55
    .line 56
    invoke-virtual {p0}, Ljava/lang/RuntimeException;->printStackTrace()V

    .line 57
    .line 58
    .line 59
    return-void
.end method

.method public final getTouchDelegateInfo()Landroid/view/accessibility/AccessibilityNodeInfo$TouchDelegateInfo;
    .locals 2

    .line 1
    const-string v0, "SeslTouchTargetDelegate"

    .line 2
    .line 3
    const-string v1, "SeslTouchTargetDelegate does not support accessibility because it cannot support multi-touch delegation with AOSP View"

    .line 4
    .line 5
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 6
    .line 7
    .line 8
    new-instance v0, Landroid/util/ArrayMap;

    .line 9
    .line 10
    const/4 v1, 0x1

    .line 11
    invoke-direct {v0, v1}, Landroid/util/ArrayMap;-><init>(I)V

    .line 12
    .line 13
    .line 14
    new-instance v1, Landroid/graphics/Region;

    .line 15
    .line 16
    invoke-direct {v1}, Landroid/graphics/Region;-><init>()V

    .line 17
    .line 18
    .line 19
    iget-object p0, p0, Landroidx/core/view/SeslTouchTargetDelegate;->mAnchorView:Landroid/view/View;

    .line 20
    .line 21
    invoke-virtual {v0, v1, p0}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 22
    .line 23
    .line 24
    new-instance p0, Landroid/view/accessibility/AccessibilityNodeInfo$TouchDelegateInfo;

    .line 25
    .line 26
    invoke-direct {p0, v0}, Landroid/view/accessibility/AccessibilityNodeInfo$TouchDelegateInfo;-><init>(Ljava/util/Map;)V

    .line 27
    .line 28
    .line 29
    return-object p0
.end method

.method public final onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 3

    .line 1
    iget-object p0, p0, Landroidx/core/view/SeslTouchTargetDelegate;->mTouchDelegateSet:Ljava/util/HashSet;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/util/HashSet;->iterator()Ljava/util/Iterator;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    :cond_0
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    .line 8
    .line 9
    .line 10
    move-result v0

    .line 11
    if-eqz v0, :cond_2

    .line 12
    .line 13
    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object v0

    .line 17
    check-cast v0, Landroidx/core/view/SeslTouchTargetDelegate$CapturedTouchDelegate;

    .line 18
    .line 19
    iget-object v1, v0, Landroidx/core/view/SeslTouchTargetDelegate$CapturedTouchDelegate;->mView:Landroid/view/View;

    .line 20
    .line 21
    invoke-virtual {v1}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    .line 22
    .line 23
    .line 24
    move-result-object v1

    .line 25
    if-nez v1, :cond_1

    .line 26
    .line 27
    new-instance v1, Ljava/lang/StringBuilder;

    .line 28
    .line 29
    const-string v2, "delegate view("

    .line 30
    .line 31
    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 32
    .line 33
    .line 34
    iget-object v0, v0, Landroidx/core/view/SeslTouchTargetDelegate$CapturedTouchDelegate;->mView:Landroid/view/View;

    .line 35
    .line 36
    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 37
    .line 38
    .line 39
    const-string v0, ")\'s getParent() is null"

    .line 40
    .line 41
    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 42
    .line 43
    .line 44
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 45
    .line 46
    .line 47
    move-result-object v0

    .line 48
    const-string v1, "SeslTouchTargetDelegate"

    .line 49
    .line 50
    invoke-static {v1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 51
    .line 52
    .line 53
    goto :goto_0

    .line 54
    :cond_1
    invoke-virtual {v0, p1}, Landroid/view/TouchDelegate;->onTouchEvent(Landroid/view/MotionEvent;)Z

    .line 55
    .line 56
    .line 57
    move-result v0

    .line 58
    if-eqz v0, :cond_0

    .line 59
    .line 60
    const/4 p0, 0x1

    .line 61
    return p0

    .line 62
    :cond_2
    const/4 p0, 0x0

    .line 63
    return p0
.end method

.method public final onTouchExplorationHoverEvent(Landroid/view/MotionEvent;)Z
    .locals 0

    .line 1
    const-string p0, "SeslTouchTargetDelegate"

    .line 2
    .line 3
    const-string p1, "SeslTouchTargetDelegate does not support accessibility because it cannot support multi-touch delegation with AOSP View"

    .line 4
    .line 5
    invoke-static {p0, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 6
    .line 7
    .line 8
    const/4 p0, 0x0

    .line 9
    return p0
.end method
