.class public final Lcom/android/systemui/controls/ui/ControlViewHolder$findBehaviorClass$4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Supplier;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/controls/ui/ControlViewHolder;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/ui/ControlViewHolder;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/ui/ControlViewHolder$findBehaviorClass$4;->this$0:Lcom/android/systemui/controls/ui/ControlViewHolder;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/controls/ui/ThumbnailBehavior;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/controls/ui/ControlViewHolder$findBehaviorClass$4;->this$0:Lcom/android/systemui/controls/ui/ControlViewHolder;

    .line 4
    .line 5
    iget p0, p0, Lcom/android/systemui/controls/ui/ControlViewHolder;->currentUserId:I

    .line 6
    .line 7
    invoke-direct {v0, p0}, Lcom/android/systemui/controls/ui/ThumbnailBehavior;-><init>(I)V

    .line 8
    .line 9
    .line 10
    return-object v0
.end method