.class public final synthetic Lcom/android/wm/shell/common/split/DividerView$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/View$OnHoverListener;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/wm/shell/common/split/DividerView;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/common/split/DividerView;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/wm/shell/common/split/DividerView$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/wm/shell/common/split/DividerView$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/common/split/DividerView;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final onHover(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 1

    .line 1
    iget p1, p0, Lcom/android/wm/shell/common/split/DividerView$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    const/4 v0, 0x0

    .line 4
    packed-switch p1, :pswitch_data_0

    .line 5
    .line 6
    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/android/wm/shell/common/split/DividerView$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/common/split/DividerView;

    .line 8
    .line 9
    invoke-static {p0, p2}, Lcom/android/wm/shell/common/split/DividerView;->$r8$lambda$xjbJZubBp3a6wlufPhmpfDd1Ohw(Lcom/android/wm/shell/common/split/DividerView;Landroid/view/MotionEvent;)V

    .line 10
    .line 11
    .line 12
    return v0

    .line 13
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method
