.class public final Lcom/airbnb/lottie/model/content/RectangleShape;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/airbnb/lottie/model/content/ContentModel;


# instance fields
.field public final cornerRadius:Lcom/airbnb/lottie/model/animatable/AnimatableFloatValue;

.field public final hidden:Z

.field public final name:Ljava/lang/String;

.field public final position:Lcom/airbnb/lottie/model/animatable/AnimatableValue;

.field public final size:Lcom/airbnb/lottie/model/animatable/AnimatableValue;


# direct methods
.method public constructor <init>(Ljava/lang/String;Lcom/airbnb/lottie/model/animatable/AnimatableValue;Lcom/airbnb/lottie/model/animatable/AnimatableValue;Lcom/airbnb/lottie/model/animatable/AnimatableFloatValue;Z)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Lcom/airbnb/lottie/model/animatable/AnimatableValue;",
            "Lcom/airbnb/lottie/model/animatable/AnimatableValue;",
            "Lcom/airbnb/lottie/model/animatable/AnimatableFloatValue;",
            "Z)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/airbnb/lottie/model/content/RectangleShape;->name:Ljava/lang/String;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/airbnb/lottie/model/content/RectangleShape;->position:Lcom/airbnb/lottie/model/animatable/AnimatableValue;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/airbnb/lottie/model/content/RectangleShape;->size:Lcom/airbnb/lottie/model/animatable/AnimatableValue;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/airbnb/lottie/model/content/RectangleShape;->cornerRadius:Lcom/airbnb/lottie/model/animatable/AnimatableFloatValue;

    .line 11
    .line 12
    iput-boolean p5, p0, Lcom/airbnb/lottie/model/content/RectangleShape;->hidden:Z

    .line 13
    .line 14
    return-void
.end method


# virtual methods
.method public final toContent(Lcom/airbnb/lottie/LottieDrawable;Lcom/airbnb/lottie/LottieComposition;Lcom/airbnb/lottie/model/layer/BaseLayer;)Lcom/airbnb/lottie/animation/content/Content;
    .locals 0

    .line 1
    new-instance p2, Lcom/airbnb/lottie/animation/content/RectangleContent;

    .line 2
    .line 3
    invoke-direct {p2, p1, p3, p0}, Lcom/airbnb/lottie/animation/content/RectangleContent;-><init>(Lcom/airbnb/lottie/LottieDrawable;Lcom/airbnb/lottie/model/layer/BaseLayer;Lcom/airbnb/lottie/model/content/RectangleShape;)V

    .line 4
    .line 5
    .line 6
    return-object p2
.end method

.method public final toString()Ljava/lang/String;
    .locals 2

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "RectangleShape{position="

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    iget-object v1, p0, Lcom/airbnb/lottie/model/content/RectangleShape;->position:Lcom/airbnb/lottie/model/animatable/AnimatableValue;

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    const-string v1, ", size="

    .line 14
    .line 15
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 16
    .line 17
    .line 18
    iget-object p0, p0, Lcom/airbnb/lottie/model/content/RectangleShape;->size:Lcom/airbnb/lottie/model/animatable/AnimatableValue;

    .line 19
    .line 20
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 21
    .line 22
    .line 23
    const/16 p0, 0x7d

    .line 24
    .line 25
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 26
    .line 27
    .line 28
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 29
    .line 30
    .line 31
    move-result-object p0

    .line 32
    return-object p0
.end method
