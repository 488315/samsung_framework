.class public final synthetic Lcom/android/systemui/qs/bar/BarController$$ExternalSyntheticLambda4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Predicate;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/systemui/qs/bar/BarType;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/qs/bar/BarType;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/systemui/qs/bar/BarController$$ExternalSyntheticLambda4;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/qs/bar/BarController$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/qs/bar/BarType;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final test(Ljava/lang/Object;)Z
    .locals 1

    .line 1
    iget v0, p0, Lcom/android/systemui/qs/bar/BarController$$ExternalSyntheticLambda4;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/android/systemui/qs/bar/BarController$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/qs/bar/BarType;

    .line 8
    .line 9
    check-cast p1, Lcom/android/systemui/qs/bar/BarItemImpl;

    .line 10
    .line 11
    invoke-virtual {p0}, Lcom/android/systemui/qs/bar/BarType;->getCls()Ljava/lang/Class;

    .line 12
    .line 13
    .line 14
    move-result-object p0

    .line 15
    invoke-virtual {p0, p1}, Ljava/lang/Class;->isInstance(Ljava/lang/Object;)Z

    .line 16
    .line 17
    .line 18
    move-result p0

    .line 19
    return p0

    .line 20
    :goto_0
    iget-object p0, p0, Lcom/android/systemui/qs/bar/BarController$$ExternalSyntheticLambda4;->f$0:Lcom/android/systemui/qs/bar/BarType;

    .line 21
    .line 22
    check-cast p1, Lcom/android/systemui/qs/bar/BarItemImpl;

    .line 23
    .line 24
    invoke-virtual {p0}, Lcom/android/systemui/qs/bar/BarType;->getCls()Ljava/lang/Class;

    .line 25
    .line 26
    .line 27
    move-result-object p0

    .line 28
    invoke-virtual {p0, p1}, Ljava/lang/Class;->isInstance(Ljava/lang/Object;)Z

    .line 29
    .line 30
    .line 31
    move-result p0

    .line 32
    return p0

    .line 33
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method
