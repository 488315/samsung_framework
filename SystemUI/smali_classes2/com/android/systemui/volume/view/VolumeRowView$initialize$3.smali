.class public final Lcom/android/systemui/volume/view/VolumeRowView$initialize$3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroidx/dynamicanimation/animation/DynamicAnimation$OnAnimationUpdateListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/volume/view/VolumeRowView;


# direct methods
.method public constructor <init>(Lcom/android/systemui/volume/view/VolumeRowView;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/volume/view/VolumeRowView$initialize$3;->this$0:Lcom/android/systemui/volume/view/VolumeRowView;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onAnimationUpdate(FF)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/volume/view/VolumeRowView$initialize$3;->this$0:Lcom/android/systemui/volume/view/VolumeRowView;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/volume/view/VolumeRowView;->seekBar:Lcom/android/systemui/volume/view/VolumeSeekBar;

    .line 4
    .line 5
    if-nez p0, :cond_0

    .line 6
    .line 7
    const/4 p0, 0x0

    .line 8
    :cond_0
    float-to-int p1, p1

    .line 9
    invoke-virtual {p0, p1}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 10
    .line 11
    .line 12
    return-void
.end method
