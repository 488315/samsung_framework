.class public final Lcom/android/systemui/shade/SecQsMediaTouchHelper$onTouch$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $event:Landroid/view/MotionEvent;

.field public final synthetic this$0:Lcom/android/systemui/shade/SecQsMediaTouchHelper;


# direct methods
.method public constructor <init>(Lcom/android/systemui/shade/SecQsMediaTouchHelper;Landroid/view/MotionEvent;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/shade/SecQsMediaTouchHelper$onTouch$1;->this$0:Lcom/android/systemui/shade/SecQsMediaTouchHelper;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/shade/SecQsMediaTouchHelper$onTouch$1;->$event:Landroid/view/MotionEvent;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/shade/SecQsMediaTouchHelper$onTouch$1;->this$0:Lcom/android/systemui/shade/SecQsMediaTouchHelper;

    .line 2
    .line 3
    iget-object v0, v0, Lcom/android/systemui/shade/SecQsMediaTouchHelper;->notificationStackScrollLayoutController:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/shade/SecQsMediaTouchHelper$onTouch$1;->$event:Landroid/view/MotionEvent;

    .line 6
    .line 7
    iget-object v0, v0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;->mView:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;

    .line 8
    .line 9
    invoke-virtual {v0, p0}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->onMediaPlayerScroll(Landroid/view/MotionEvent;)V

    .line 10
    .line 11
    .line 12
    return-void
.end method