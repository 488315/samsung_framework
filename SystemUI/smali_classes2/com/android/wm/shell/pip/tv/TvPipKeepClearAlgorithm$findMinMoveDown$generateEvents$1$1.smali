.class final Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function1;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function1;"
    }
.end annotation


# instance fields
.field final synthetic $events:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$SweepLineEvent;",
            ">;"
        }
    .end annotation
.end field

.field final synthetic $pipBounds:Landroid/graphics/Rect;

.field final synthetic $unrestricted:Z

.field final synthetic this$0:Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm;


# direct methods
.method public constructor <init>(Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm;Landroid/graphics/Rect;Ljava/util/List;Z)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm;",
            "Landroid/graphics/Rect;",
            "Ljava/util/List<",
            "Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$SweepLineEvent;",
            ">;Z)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->this$0:Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$pipBounds:Landroid/graphics/Rect;

    .line 4
    .line 5
    iput-object p3, p0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$events:Ljava/util/List;

    .line 6
    .line 7
    iput-boolean p4, p0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$unrestricted:Z

    .line 8
    .line 9
    const/4 p1, 0x1

    .line 10
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 11
    .line 12
    .line 13
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 18

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    move-object/from16 v1, p1

    .line 4
    .line 5
    check-cast v1, Landroid/graphics/Rect;

    .line 6
    .line 7
    iget-object v2, v0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->this$0:Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm;

    .line 8
    .line 9
    iget-object v3, v0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$pipBounds:Landroid/graphics/Rect;

    .line 10
    .line 11
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 12
    .line 13
    .line 14
    invoke-static {v3, v1}, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm;->intersectsX(Landroid/graphics/Rect;Landroid/graphics/Rect;)Z

    .line 15
    .line 16
    .line 17
    move-result v2

    .line 18
    if-eqz v2, :cond_0

    .line 19
    .line 20
    iget-object v2, v0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$events:Ljava/util/List;

    .line 21
    .line 22
    new-instance v10, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$SweepLineEvent;

    .line 23
    .line 24
    const/4 v4, 0x1

    .line 25
    iget v3, v1, Landroid/graphics/Rect;->top:I

    .line 26
    .line 27
    neg-int v5, v3

    .line 28
    iget-boolean v6, v0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$unrestricted:Z

    .line 29
    .line 30
    const/4 v15, 0x0

    .line 31
    const/16 v16, 0x8

    .line 32
    .line 33
    const/16 v17, 0x0

    .line 34
    .line 35
    const/4 v7, 0x0

    .line 36
    const/16 v8, 0x8

    .line 37
    .line 38
    const/4 v9, 0x0

    .line 39
    move-object v3, v10

    .line 40
    invoke-direct/range {v3 .. v9}, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$SweepLineEvent;-><init>(ZIZZILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 41
    .line 42
    .line 43
    invoke-interface {v2, v10}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 44
    .line 45
    .line 46
    iget-object v2, v0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$events:Ljava/util/List;

    .line 47
    .line 48
    new-instance v3, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$SweepLineEvent;

    .line 49
    .line 50
    const/4 v12, 0x0

    .line 51
    iget v1, v1, Landroid/graphics/Rect;->bottom:I

    .line 52
    .line 53
    neg-int v13, v1

    .line 54
    iget-boolean v14, v0, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1$1;->$unrestricted:Z

    .line 55
    .line 56
    move-object v11, v3

    .line 57
    invoke-direct/range {v11 .. v17}, Lcom/android/wm/shell/pip/tv/TvPipKeepClearAlgorithm$SweepLineEvent;-><init>(ZIZZILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 58
    .line 59
    .line 60
    invoke-interface {v2, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 61
    .line 62
    .line 63
    :cond_0
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 64
    .line 65
    return-object v0
.end method