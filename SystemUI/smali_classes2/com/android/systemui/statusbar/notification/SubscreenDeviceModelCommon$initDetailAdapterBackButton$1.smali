.class public final Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelCommon$initDetailAdapterBackButton$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelCommon;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelCommon;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelCommon$initDetailAdapterBackButton$1;->this$0:Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelCommon;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onClick(Landroid/view/View;)V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelCommon$initDetailAdapterBackButton$1;->this$0:Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelCommon;

    .line 2
    .line 3
    const/16 p1, 0x12c

    .line 4
    .line 5
    const/4 v0, 0x0

    .line 6
    invoke-virtual {p0, p1, v0}, Lcom/android/systemui/statusbar/notification/SubscreenDeviceModelParent;->hideDetailNotificationAnimated(IZ)V

    .line 7
    .line 8
    .line 9
    return-void
.end method