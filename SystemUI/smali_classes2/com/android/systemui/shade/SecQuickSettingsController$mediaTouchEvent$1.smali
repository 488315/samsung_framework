.class public final Lcom/android/systemui/shade/SecQuickSettingsController$mediaTouchEvent$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/shade/SecQuickSettingsController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/shade/SecQuickSettingsController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/shade/SecQuickSettingsController$mediaTouchEvent$1;->this$0:Lcom/android/systemui/shade/SecQuickSettingsController;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    .line 1
    check-cast p1, Ljava/lang/Boolean;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/shade/SecQuickSettingsController$mediaTouchEvent$1;->this$0:Lcom/android/systemui/shade/SecQuickSettingsController;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/shade/SecQuickSettingsController;->touchAboveFalsingThresholdConsumer:Ljava/util/function/Consumer;

    .line 6
    .line 7
    invoke-interface {p0, p1}, Ljava/util/function/Consumer;->accept(Ljava/lang/Object;)V

    .line 8
    .line 9
    .line 10
    return-void
.end method