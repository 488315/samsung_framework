.class public final synthetic Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController;

.field public final synthetic f$1:Landroid/content/Intent;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController;Landroid/content/Intent;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7;->f$0:Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7;->f$1:Landroid/content/Intent;

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7;->f$0:Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController$$ExternalSyntheticLambda7;->f$1:Landroid/content/Intent;

    .line 4
    .line 5
    iget-object v0, v0, Lcom/android/systemui/subscreen/SubScreenQuickPanelWindowController;->mContext:Landroid/content/Context;

    .line 6
    .line 7
    sget-object v1, Landroid/os/UserHandle;->ALL:Landroid/os/UserHandle;

    .line 8
    .line 9
    invoke-virtual {v0, p0, v1}, Landroid/content/Context;->sendBroadcastAsUser(Landroid/content/Intent;Landroid/os/UserHandle;)V

    .line 10
    .line 11
    .line 12
    return-void
.end method
