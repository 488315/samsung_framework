.class public final Lcom/android/wm/shell/fullscreen/AffordanceAnimController$AnimTarget$1;
.super Landroid/util/FloatProperty;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public constructor <init>(Ljava/lang/String;)V
    .locals 0

    .line 1
    invoke-direct {p0, p1}, Landroid/util/FloatProperty;-><init>(Ljava/lang/String;)V

    .line 2
    .line 3
    .line 4
    return-void
.end method


# virtual methods
.method public final get(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lcom/android/wm/shell/fullscreen/AffordanceAnimController$AnimTarget;

    .line 2
    .line 3
    iget p0, p1, Lcom/android/wm/shell/fullscreen/AffordanceAnimController$AnimTarget;->x:F

    .line 4
    .line 5
    invoke-static {p0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    return-object p0
.end method

.method public final setValue(Ljava/lang/Object;F)V
    .locals 0

    .line 1
    check-cast p1, Lcom/android/wm/shell/fullscreen/AffordanceAnimController$AnimTarget;

    .line 2
    .line 3
    iput p2, p1, Lcom/android/wm/shell/fullscreen/AffordanceAnimController$AnimTarget;->x:F

    .line 4
    .line 5
    return-void
.end method
