.class public final Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector;


# direct methods
.method public constructor <init>(Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector$1;->this$0:Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector$1;->this$0:Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/edgelighting/effectservice/EffectServiceCollector;->mEdgeLightingStyleList:Ljava/util/ArrayList;

    .line 4
    .line 5
    invoke-virtual {p0}, Ljava/util/ArrayList;->clear()V

    .line 6
    .line 7
    .line 8
    const/4 p0, 0x0

    .line 9
    throw p0
.end method
