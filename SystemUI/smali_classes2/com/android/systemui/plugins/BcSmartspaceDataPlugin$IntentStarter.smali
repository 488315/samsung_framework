.class public interface abstract Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$IntentStarter;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/plugins/BcSmartspaceDataPlugin;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x609
    name = "IntentStarter"
.end annotation


# virtual methods
.method public startFromAction(Landroid/app/smartspace/SmartspaceAction;Landroid/view/View;Z)V
    .locals 1

    .line 1
    :try_start_0
    invoke-virtual {p1}, Landroid/app/smartspace/SmartspaceAction;->getIntent()Landroid/content/Intent;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 2
    invoke-virtual {p1}, Landroid/app/smartspace/SmartspaceAction;->getIntent()Landroid/content/Intent;

    move-result-object v0

    invoke-interface {p0, p2, v0, p3}, Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$IntentStarter;->startIntent(Landroid/view/View;Landroid/content/Intent;Z)V

    goto :goto_0

    .line 3
    :cond_0
    invoke-virtual {p1}, Landroid/app/smartspace/SmartspaceAction;->getPendingIntent()Landroid/app/PendingIntent;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 4
    invoke-virtual {p1}, Landroid/app/smartspace/SmartspaceAction;->getPendingIntent()Landroid/app/PendingIntent;

    move-result-object v0

    invoke-interface {p0, p2, v0, p3}, Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$IntentStarter;->startPendingIntent(Landroid/view/View;Landroid/app/PendingIntent;Z)V
    :try_end_0
    .catch Landroid/content/ActivityNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    .line 5
    new-instance p2, Ljava/lang/StringBuilder;

    const-string p3, "Could not launch intent for action: "

    invoke-direct {p2, p3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string p2, "BcSmartspaceDataPlugin"

    invoke-static {p2, p1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :cond_1
    :goto_0
    return-void
.end method

.method public startFromAction(Landroid/app/smartspace/uitemplatedata/TapAction;Landroid/view/View;Z)V
    .locals 1

    .line 6
    :try_start_0
    invoke-virtual {p1}, Landroid/app/smartspace/uitemplatedata/TapAction;->getIntent()Landroid/content/Intent;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 7
    invoke-virtual {p1}, Landroid/app/smartspace/uitemplatedata/TapAction;->getIntent()Landroid/content/Intent;

    move-result-object v0

    invoke-interface {p0, p2, v0, p3}, Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$IntentStarter;->startIntent(Landroid/view/View;Landroid/content/Intent;Z)V

    goto :goto_0

    .line 8
    :cond_0
    invoke-virtual {p1}, Landroid/app/smartspace/uitemplatedata/TapAction;->getPendingIntent()Landroid/app/PendingIntent;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 9
    invoke-virtual {p1}, Landroid/app/smartspace/uitemplatedata/TapAction;->getPendingIntent()Landroid/app/PendingIntent;

    move-result-object v0

    invoke-interface {p0, p2, v0, p3}, Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$IntentStarter;->startPendingIntent(Landroid/view/View;Landroid/app/PendingIntent;Z)V
    :try_end_0
    .catch Landroid/content/ActivityNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    .line 10
    new-instance p2, Ljava/lang/StringBuilder;

    const-string p3, "Could not launch intent for action: "

    invoke-direct {p2, p3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string p2, "BcSmartspaceDataPlugin"

    invoke-static {p2, p1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :cond_1
    :goto_0
    return-void
.end method

.method public abstract startIntent(Landroid/view/View;Landroid/content/Intent;Z)V
.end method

.method public abstract startPendingIntent(Landroid/view/View;Landroid/app/PendingIntent;Z)V
.end method
