.class public interface abstract Lcom/android/systemui/plugins/statusbar/NotificationSwipeActionHelper;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation runtime Lcom/android/systemui/plugins/annotations/DependsOn;
    target = Lcom/android/systemui/plugins/statusbar/NotificationSwipeActionHelper$SnoozeOption;
.end annotation

.annotation runtime Lcom/android/systemui/plugins/annotations/ProvidesInterface;
    version = 0x1
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/plugins/statusbar/NotificationSwipeActionHelper$SnoozeOption;
    }
.end annotation


# static fields
.field public static final ACTION:Ljava/lang/String; = "com.android.systemui.action.PLUGIN_NOTIFICATION_SWIPE_ACTION"

.field public static final VERSION:I = 0x1


# virtual methods
.method public abstract dismiss(Landroid/view/View;F)V
.end method

.method public abstract getMinDismissVelocity()F
.end method

.method public abstract isDismissGesture(Landroid/view/MotionEvent;)Z
.end method

.method public abstract isFalseGesture()Z
.end method

.method public abstract snapOpen(Landroid/view/View;IF)V
.end method

.method public abstract snooze(Landroid/service/notification/StatusBarNotification;Lcom/android/systemui/plugins/statusbar/NotificationSwipeActionHelper$SnoozeOption;)V
.end method