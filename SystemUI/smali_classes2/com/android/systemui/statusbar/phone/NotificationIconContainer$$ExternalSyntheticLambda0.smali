.class public final synthetic Lcom/android/systemui/statusbar/phone/NotificationIconContainer$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/statusbar/phone/NotificationIconContainer;

.field public final synthetic f$1:Lcom/android/systemui/statusbar/StatusBarIconView;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/phone/NotificationIconContainer;Lcom/android/systemui/statusbar/StatusBarIconView;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NotificationIconContainer$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/phone/NotificationIconContainer;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/statusbar/phone/NotificationIconContainer$$ExternalSyntheticLambda0;->f$1:Lcom/android/systemui/statusbar/StatusBarIconView;

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NotificationIconContainer$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/phone/NotificationIconContainer;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/NotificationIconContainer$$ExternalSyntheticLambda0;->f$1:Lcom/android/systemui/statusbar/StatusBarIconView;

    .line 4
    .line 5
    sget-object v1, Lcom/android/systemui/statusbar/phone/NotificationIconContainer;->DOT_ANIMATION_PROPERTIES:Lcom/android/systemui/statusbar/phone/NotificationIconContainer$1;

    .line 6
    .line 7
    invoke-virtual {v0, p0}, Landroid/view/ViewGroup;->removeTransientView(Landroid/view/View;)V

    .line 8
    .line 9
    .line 10
    return-void
.end method