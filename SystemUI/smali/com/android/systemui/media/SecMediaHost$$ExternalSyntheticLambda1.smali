.class public final synthetic Lcom/android/systemui/media/SecMediaHost$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/BiFunction;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/systemui/media/SecMediaHost;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/media/SecMediaHost;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/systemui/media/SecMediaHost$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/media/SecMediaHost$$ExternalSyntheticLambda1;->f$0:Lcom/android/systemui/media/SecMediaHost;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    .line 1
    iget v0, p0, Lcom/android/systemui/media/SecMediaHost$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    :pswitch_0
    iget-object p0, p0, Lcom/android/systemui/media/SecMediaHost$$ExternalSyntheticLambda1;->f$0:Lcom/android/systemui/media/SecMediaHost;

    .line 7
    .line 8
    check-cast p1, Ljava/lang/Boolean;

    .line 9
    .line 10
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    .line 11
    .line 12
    .line 13
    move-result p1

    .line 14
    check-cast p2, Lcom/android/systemui/media/MediaType;

    .line 15
    .line 16
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/media/SecMediaHost;->getMediaPlayerNum(ZLcom/android/systemui/media/MediaType;)I

    .line 17
    .line 18
    .line 19
    move-result p0

    .line 20
    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    .line 21
    .line 22
    .line 23
    move-result-object p0

    .line 24
    return-object p0

    .line 25
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method
