.class final Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic $listener:Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$listener$1;

.field final synthetic $this_headsUpEvents:Lcom/android/systemui/statusbar/policy/HeadsUpManager;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/policy/HeadsUpManager;Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$listener$1;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$1;->$this_headsUpEvents:Lcom/android/systemui/statusbar/policy/HeadsUpManager;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$1;->$listener:Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$listener$1;

    .line 4
    .line 5
    const/4 p1, 0x0

    .line 6
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$1;->$this_headsUpEvents:Lcom/android/systemui/statusbar/policy/HeadsUpManager;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$1;->$listener:Lcom/android/systemui/statusbar/policy/HeadsUpManagerExtKt$headsUpEvents$1$listener$1;

    .line 4
    .line 5
    iget-object v0, v0, Lcom/android/systemui/statusbar/policy/HeadsUpManager;->mListeners:Lcom/android/systemui/util/ListenerSet;

    .line 6
    .line 7
    invoke-virtual {v0, p0}, Lcom/android/systemui/util/ListenerSet;->remove(Ljava/lang/Object;)Z

    .line 8
    .line 9
    .line 10
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 11
    .line 12
    return-object p0
.end method