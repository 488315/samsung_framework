.class public final synthetic Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout$$ExternalSyntheticLambda4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/Comparator;


# virtual methods
.method public final compare(Ljava/lang/Object;Ljava/lang/Object;)I
    .locals 0

    .line 1
    check-cast p1, Lcom/android/systemui/statusbar/notification/row/ExpandableView;

    .line 2
    .line 3
    check-cast p2, Lcom/android/systemui/statusbar/notification/row/ExpandableView;

    .line 4
    .line 5
    sget p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->$r8$clinit:I

    .line 6
    .line 7
    invoke-virtual {p1}, Landroid/widget/FrameLayout;->getTranslationY()F

    .line 8
    .line 9
    .line 10
    move-result p0

    .line 11
    iget p1, p1, Lcom/android/systemui/statusbar/notification/row/ExpandableView;->mActualHeight:I

    .line 12
    .line 13
    int-to-float p1, p1

    .line 14
    add-float/2addr p0, p1

    .line 15
    invoke-virtual {p2}, Landroid/widget/FrameLayout;->getTranslationY()F

    .line 16
    .line 17
    .line 18
    move-result p1

    .line 19
    iget p2, p2, Lcom/android/systemui/statusbar/notification/row/ExpandableView;->mActualHeight:I

    .line 20
    .line 21
    int-to-float p2, p2

    .line 22
    add-float/2addr p1, p2

    .line 23
    invoke-static {p0, p1}, Ljava/lang/Float;->compare(FF)I

    .line 24
    .line 25
    .line 26
    move-result p0

    .line 27
    return p0
.end method
