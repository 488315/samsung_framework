.class public final synthetic Lcom/android/systemui/qs/SecQSPanelControllerBase$$ExternalSyntheticLambda2;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Function;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/qs/SecQSPanelControllerBase;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/qs/SecQSPanelControllerBase;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/qs/SecQSPanelControllerBase$$ExternalSyntheticLambda2;->f$0:Lcom/android/systemui/qs/SecQSPanelControllerBase;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/qs/SecQSPanelControllerBase$$ExternalSyntheticLambda2;->f$0:Lcom/android/systemui/qs/SecQSPanelControllerBase;

    .line 2
    .line 3
    check-cast p1, Lcom/android/systemui/qs/SecQSPanelControllerBase$TileRecord;

    .line 4
    .line 5
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 6
    .line 7
    .line 8
    new-instance v0, Lcom/android/systemui/qs/SecQSPanelControllerBase$2;

    .line 9
    .line 10
    invoke-direct {v0, p0, p1}, Lcom/android/systemui/qs/SecQSPanelControllerBase$2;-><init>(Lcom/android/systemui/qs/SecQSPanelControllerBase;Lcom/android/systemui/qs/SecQSPanelControllerBase$TileRecord;)V

    .line 11
    .line 12
    .line 13
    return-object v0
.end method