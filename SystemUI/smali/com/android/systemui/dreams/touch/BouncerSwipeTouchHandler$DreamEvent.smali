.class public final enum Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/internal/logging/UiEventLogger$UiEventEnum;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;",
        ">;",
        "Lcom/android/internal/logging/UiEventLogger$UiEventEnum;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

.field public static final enum DREAM_BOUNCER_FULLY_VISIBLE:Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

.field public static final enum DREAM_SWIPED:Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;


# instance fields
.field private final mId:I


# direct methods
.method public static constructor <clinit>()V
    .locals 5

    .line 1
    new-instance v0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const/16 v2, 0x3dc

    .line 5
    .line 6
    const-string v3, "DREAM_SWIPED"

    .line 7
    .line 8
    invoke-direct {v0, v3, v1, v2}, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;-><init>(Ljava/lang/String;II)V

    .line 9
    .line 10
    .line 11
    sput-object v0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;->DREAM_SWIPED:Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 12
    .line 13
    new-instance v1, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    const/16 v3, 0x420

    .line 17
    .line 18
    const-string v4, "DREAM_BOUNCER_FULLY_VISIBLE"

    .line 19
    .line 20
    invoke-direct {v1, v4, v2, v3}, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;-><init>(Ljava/lang/String;II)V

    .line 21
    .line 22
    .line 23
    sput-object v1, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;->DREAM_BOUNCER_FULLY_VISIBLE:Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 24
    .line 25
    filled-new-array {v0, v1}, [Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 26
    .line 27
    .line 28
    move-result-object v0

    .line 29
    sput-object v0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;->$VALUES:[Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 30
    .line 31
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;II)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    iput p3, p0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;->mId:I

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;->$VALUES:[Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getId()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent;->mId:I

    .line 2
    .line 3
    return p0
.end method