.class public final synthetic Lcom/android/systemui/shade/NotificationPanelViewController$$ExternalSyntheticLambda12;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field public final synthetic f$0:Z


# direct methods
.method public synthetic constructor <init>(Z)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-boolean p1, p0, Lcom/android/systemui/shade/NotificationPanelViewController$$ExternalSyntheticLambda12;->f$0:Z

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    .line 1
    iget-boolean p0, p0, Lcom/android/systemui/shade/NotificationPanelViewController$$ExternalSyntheticLambda12;->f$0:Z

    .line 2
    .line 3
    check-cast p1, Lcom/android/keyguard/KeyguardUnfoldTransition;

    .line 4
    .line 5
    iput-boolean p0, p1, Lcom/android/keyguard/KeyguardUnfoldTransition;->statusViewCentered:Z

    .line 6
    .line 7
    return-void
.end method