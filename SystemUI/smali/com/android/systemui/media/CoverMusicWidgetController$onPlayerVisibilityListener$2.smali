.class final Lcom/android/systemui/media/CoverMusicWidgetController$onPlayerVisibilityListener$2;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/media/CoverMusicWidgetController;-><init>(Ljava/util/function/Consumer;Ljava/util/function/Consumer;Lcom/android/systemui/subscreen/SubScreenManager;Lcom/android/systemui/keyguard/WakefulnessLifecycle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/media/CoverMusicWidgetController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/media/CoverMusicWidgetController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/media/CoverMusicWidgetController$onPlayerVisibilityListener$2;->this$0:Lcom/android/systemui/media/CoverMusicWidgetController;

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/media/CoverMusicWidgetController$onPlayerVisibilityListener$2$1;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/media/CoverMusicWidgetController$onPlayerVisibilityListener$2;->this$0:Lcom/android/systemui/media/CoverMusicWidgetController;

    .line 4
    .line 5
    invoke-direct {v0, p0}, Lcom/android/systemui/media/CoverMusicWidgetController$onPlayerVisibilityListener$2$1;-><init>(Lcom/android/systemui/media/CoverMusicWidgetController;)V

    .line 6
    .line 7
    .line 8
    return-object v0
.end method
