.class public final synthetic Lcom/airbnb/lottie/LottieDrawable$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/airbnb/lottie/LottieDrawable$LazyCompositionTask;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/airbnb/lottie/LottieDrawable;


# direct methods
.method public synthetic constructor <init>(Lcom/airbnb/lottie/LottieDrawable;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/airbnb/lottie/LottieDrawable$$ExternalSyntheticLambda3;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/airbnb/lottie/LottieDrawable$$ExternalSyntheticLambda3;->f$0:Lcom/airbnb/lottie/LottieDrawable;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget v0, p0, Lcom/airbnb/lottie/LottieDrawable$$ExternalSyntheticLambda3;->$r8$classId:I

    .line 2
    .line 3
    iget-object p0, p0, Lcom/airbnb/lottie/LottieDrawable$$ExternalSyntheticLambda3;->f$0:Lcom/airbnb/lottie/LottieDrawable;

    .line 4
    .line 5
    packed-switch v0, :pswitch_data_0

    .line 6
    .line 7
    .line 8
    goto :goto_0

    .line 9
    :pswitch_0
    invoke-virtual {p0}, Lcom/airbnb/lottie/LottieDrawable;->resumeAnimation()V

    .line 10
    .line 11
    .line 12
    return-void

    .line 13
    :goto_0
    invoke-virtual {p0}, Lcom/airbnb/lottie/LottieDrawable;->playAnimation()V

    .line 14
    .line 15
    .line 16
    return-void

    .line 17
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method
