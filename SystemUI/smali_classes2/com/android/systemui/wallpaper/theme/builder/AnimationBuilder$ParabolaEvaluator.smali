.class public final Lcom/android/systemui/wallpaper/theme/builder/AnimationBuilder$ParabolaEvaluator;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/animation/TypeEvaluator;


# instance fields
.field public final key:F

.field public final pX:F

.field public final pY:F


# direct methods
.method public constructor <init>(FFF)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput p1, p0, Lcom/android/systemui/wallpaper/theme/builder/AnimationBuilder$ParabolaEvaluator;->key:F

    .line 5
    .line 6
    iput p2, p0, Lcom/android/systemui/wallpaper/theme/builder/AnimationBuilder$ParabolaEvaluator;->pX:F

    .line 7
    .line 8
    iput p3, p0, Lcom/android/systemui/wallpaper/theme/builder/AnimationBuilder$ParabolaEvaluator;->pY:F

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final evaluate(FLjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p2, Ljava/lang/Number;

    .line 2
    .line 3
    invoke-virtual {p2}, Ljava/lang/Number;->floatValue()F

    .line 4
    .line 5
    .line 6
    move-result p2

    .line 7
    check-cast p3, Ljava/lang/Number;

    .line 8
    .line 9
    invoke-virtual {p3}, Ljava/lang/Number;->floatValue()F

    .line 10
    .line 11
    .line 12
    move-result p3

    .line 13
    sub-float/2addr p3, p2

    .line 14
    mul-float/2addr p3, p1

    .line 15
    add-float/2addr p3, p2

    .line 16
    iget p1, p0, Lcom/android/systemui/wallpaper/theme/builder/AnimationBuilder$ParabolaEvaluator;->pX:F

    .line 17
    .line 18
    add-float/2addr p3, p1

    .line 19
    iget p1, p0, Lcom/android/systemui/wallpaper/theme/builder/AnimationBuilder$ParabolaEvaluator;->key:F

    .line 20
    .line 21
    mul-float/2addr p1, p3

    .line 22
    mul-float/2addr p1, p3

    .line 23
    iget p0, p0, Lcom/android/systemui/wallpaper/theme/builder/AnimationBuilder$ParabolaEvaluator;->pY:F

    .line 24
    .line 25
    add-float/2addr p1, p0

    .line 26
    invoke-static {p1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    .line 27
    .line 28
    .line 29
    move-result-object p0

    .line 30
    return-object p0
.end method