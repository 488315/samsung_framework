.class public final enum Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/internal/logging/UiEventLogger$UiEventEnum;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;",
        ">;",
        "Lcom/android/internal/logging/UiEventLogger$UiEventEnum;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;


# instance fields
.field private final mId:I


# direct methods
.method public static constructor <clinit>()V
    .locals 4

    .line 1
    new-instance v0, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;

    .line 2
    .line 3
    const/16 v1, 0x4bc

    .line 4
    .line 5
    const-string v2, "DREAM_HOME_CONTROLS_TAPPED"

    .line 6
    .line 7
    const/4 v3, 0x0

    .line 8
    invoke-direct {v0, v2, v3, v1}, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;-><init>(Ljava/lang/String;II)V

    .line 9
    .line 10
    .line 11
    filled-new-array {v0}, [Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;

    .line 12
    .line 13
    .line 14
    move-result-object v0

    .line 15
    sput-object v0, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;->$VALUES:[Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;

    .line 16
    .line 17
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
    iput p3, p0, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;->mId:I

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;->$VALUES:[Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getId()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent;->mId:I

    .line 2
    .line 3
    return p0
.end method