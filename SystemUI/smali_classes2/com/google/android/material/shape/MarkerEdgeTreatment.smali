.class public final Lcom/google/android/material/shape/MarkerEdgeTreatment;
.super Lcom/google/android/material/shape/EdgeTreatment;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final radius:F


# direct methods
.method public constructor <init>(F)V
    .locals 1

    .line 1
    invoke-direct {p0}, Lcom/google/android/material/shape/EdgeTreatment;-><init>()V

    .line 2
    .line 3
    .line 4
    const v0, 0x3a83126f    # 0.001f

    .line 5
    .line 6
    .line 7
    sub-float/2addr p1, v0

    .line 8
    iput p1, p0, Lcom/google/android/material/shape/MarkerEdgeTreatment;->radius:F

    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final getEdgePath(FFFLcom/google/android/material/shape/ShapePath;)V
    .locals 7

    .line 1
    iget p0, p0, Lcom/google/android/material/shape/MarkerEdgeTreatment;->radius:F

    .line 2
    .line 3
    float-to-double p0, p0

    .line 4
    const-wide/high16 v0, 0x4000000000000000L    # 2.0

    .line 5
    .line 6
    invoke-static {v0, v1}, Ljava/lang/Math;->sqrt(D)D

    .line 7
    .line 8
    .line 9
    move-result-wide v2

    .line 10
    mul-double/2addr v2, p0

    .line 11
    div-double/2addr v2, v0

    .line 12
    double-to-float p3, v2

    .line 13
    invoke-static {p0, p1, v0, v1}, Ljava/lang/Math;->pow(DD)D

    .line 14
    .line 15
    .line 16
    move-result-wide v2

    .line 17
    float-to-double v4, p3

    .line 18
    invoke-static {v4, v5, v0, v1}, Ljava/lang/Math;->pow(DD)D

    .line 19
    .line 20
    .line 21
    move-result-wide v4

    .line 22
    sub-double/2addr v2, v4

    .line 23
    invoke-static {v2, v3}, Ljava/lang/Math;->sqrt(D)D

    .line 24
    .line 25
    .line 26
    move-result-wide v2

    .line 27
    double-to-float v2, v2

    .line 28
    sub-float v3, p2, p3

    .line 29
    .line 30
    invoke-static {v0, v1}, Ljava/lang/Math;->sqrt(D)D

    .line 31
    .line 32
    .line 33
    move-result-wide v4

    .line 34
    mul-double/2addr v4, p0

    .line 35
    sub-double/2addr v4, p0

    .line 36
    neg-double v4, v4

    .line 37
    double-to-float v4, v4

    .line 38
    add-float/2addr v4, v2

    .line 39
    const/high16 v5, 0x43870000    # 270.0f

    .line 40
    .line 41
    const/4 v6, 0x0

    .line 42
    invoke-virtual {p4, v3, v4, v5, v6}, Lcom/google/android/material/shape/ShapePath;->reset(FFFF)V

    .line 43
    .line 44
    .line 45
    invoke-static {v0, v1}, Ljava/lang/Math;->sqrt(D)D

    .line 46
    .line 47
    .line 48
    move-result-wide v3

    .line 49
    mul-double/2addr v3, p0

    .line 50
    sub-double/2addr v3, p0

    .line 51
    neg-double v3, v3

    .line 52
    double-to-float v3, v3

    .line 53
    invoke-virtual {p4, p2, v3}, Lcom/google/android/material/shape/ShapePath;->lineTo(FF)V

    .line 54
    .line 55
    .line 56
    add-float/2addr p2, p3

    .line 57
    invoke-static {v0, v1}, Ljava/lang/Math;->sqrt(D)D

    .line 58
    .line 59
    .line 60
    move-result-wide v0

    .line 61
    mul-double/2addr v0, p0

    .line 62
    sub-double/2addr v0, p0

    .line 63
    neg-double p0, v0

    .line 64
    double-to-float p0, p0

    .line 65
    add-float/2addr p0, v2

    .line 66
    invoke-virtual {p4, p2, p0}, Lcom/google/android/material/shape/ShapePath;->lineTo(FF)V

    .line 67
    .line 68
    .line 69
    return-void
.end method
