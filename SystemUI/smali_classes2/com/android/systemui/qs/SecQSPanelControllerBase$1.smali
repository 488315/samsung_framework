.class public final Lcom/android/systemui/qs/SecQSPanelControllerBase$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/qs/SecQSPanel$OnConfigurationChangedListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/qs/SecQSPanelControllerBase;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/SecQSPanelControllerBase;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/qs/SecQSPanelControllerBase$1;->this$0:Lcom/android/systemui/qs/SecQSPanelControllerBase;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onConfigurationChange(Landroid/content/res/Configuration;)V
    .locals 2

    .line 1
    const-string v0, "SecQSPanelControllerBase"

    .line 2
    .line 3
    const-string v1, "OnConfigurationChangedListener ,onConfigurationChange event"

    .line 4
    .line 5
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 6
    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/systemui/qs/SecQSPanelControllerBase$1;->this$0:Lcom/android/systemui/qs/SecQSPanelControllerBase;

    .line 9
    .line 10
    invoke-virtual {p0, p1}, Lcom/android/systemui/qs/SecQSPanelControllerBase;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 11
    .line 12
    .line 13
    return-void
.end method