.class public final Lcom/android/systemui/unfold/UnfoldHapticsPlayer$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/unfold/updates/FoldProvider$FoldCallback;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/unfold/UnfoldHapticsPlayer;


# direct methods
.method public constructor <init>(Lcom/android/systemui/unfold/UnfoldHapticsPlayer;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/unfold/UnfoldHapticsPlayer$1;->this$0:Lcom/android/systemui/unfold/UnfoldHapticsPlayer;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onFoldUpdated(Z)V
    .locals 0

    .line 1
    if-eqz p1, :cond_0

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/unfold/UnfoldHapticsPlayer$1;->this$0:Lcom/android/systemui/unfold/UnfoldHapticsPlayer;

    .line 4
    .line 5
    const/4 p1, 0x1

    .line 6
    iput-boolean p1, p0, Lcom/android/systemui/unfold/UnfoldHapticsPlayer;->isFirstAnimationAfterUnfold:Z

    .line 7
    .line 8
    :cond_0
    return-void
.end method