.class public final Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/view/View$OnAttachStateChangeListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onViewAttachedToWindow(Landroid/view/View;)V
    .locals 1

    .line 1
    check-cast p1, Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$SmartspaceView;

    .line 2
    .line 3
    iget-object v0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 4
    .line 5
    iget-boolean v0, v0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->mSplitShadeEnabled:Z

    .line 6
    .line 7
    invoke-interface {p1, v0}, Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$SmartspaceView;->setSplitShadeEnabled(Z)V

    .line 8
    .line 9
    .line 10
    iget-object v0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 11
    .line 12
    iget-object v0, v0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->smartspaceViews:Ljava/util/Set;

    .line 13
    .line 14
    invoke-interface {v0, p1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 15
    .line 16
    .line 17
    iget-object p1, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 18
    .line 19
    invoke-virtual {p1}, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->connectSession()V

    .line 20
    .line 21
    .line 22
    iget-object p1, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 23
    .line 24
    invoke-static {p1}, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->access$updateTextColorFromWallpaper(Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;)V

    .line 25
    .line 26
    .line 27
    iget-object p0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 28
    .line 29
    iget-object p1, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->statusBarStateListener:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$statusBarStateListener$1;

    .line 30
    .line 31
    iget-object p0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 32
    .line 33
    invoke-interface {p0}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->getDozeAmount()F

    .line 34
    .line 35
    .line 36
    move-result p0

    .line 37
    const/4 v0, 0x0

    .line 38
    invoke-virtual {p1, v0, p0}, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$statusBarStateListener$1;->onDozeAmountChanged(FF)V

    .line 39
    .line 40
    .line 41
    return-void
.end method

.method public final onViewDetachedFromWindow(Landroid/view/View;)V
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 2
    .line 3
    iget-object v0, v0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->smartspaceViews:Ljava/util/Set;

    .line 4
    .line 5
    move-object v1, p1

    .line 6
    check-cast v1, Lcom/android/systemui/plugins/BcSmartspaceDataPlugin$SmartspaceView;

    .line 7
    .line 8
    invoke-interface {v0, v1}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    .line 9
    .line 10
    .line 11
    iget-object v0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 12
    .line 13
    iget-object v0, v0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->regionSamplers:Ljava/util/Map;

    .line 14
    .line 15
    check-cast v0, Ljava/util/LinkedHashMap;

    .line 16
    .line 17
    invoke-virtual {v0, p1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 18
    .line 19
    .line 20
    move-result-object p1

    .line 21
    check-cast p1, Lcom/android/systemui/shared/regionsampling/RegionSampler;

    .line 22
    .line 23
    if-eqz p1, :cond_1

    .line 24
    .line 25
    iget-object v0, p1, Lcom/android/systemui/shared/regionsampling/RegionSampler;->wallpaperManager:Landroid/app/WallpaperManager;

    .line 26
    .line 27
    if-eqz v0, :cond_0

    .line 28
    .line 29
    invoke-virtual {v0, p1}, Landroid/app/WallpaperManager;->removeOnColorsChangedListener(Landroid/app/WallpaperManager$LocalWallpaperColorConsumer;)V

    .line 30
    .line 31
    .line 32
    :cond_0
    iget-object v0, p1, Lcom/android/systemui/shared/regionsampling/RegionSampler;->sampledView:Landroid/view/View;

    .line 33
    .line 34
    iget-object p1, p1, Lcom/android/systemui/shared/regionsampling/RegionSampler;->layoutChangedListener:Lcom/android/systemui/shared/regionsampling/RegionSampler$layoutChangedListener$1;

    .line 35
    .line 36
    invoke-virtual {v0, p1}, Landroid/view/View;->removeOnLayoutChangeListener(Landroid/view/View$OnLayoutChangeListener;)V

    .line 37
    .line 38
    .line 39
    :cond_1
    iget-object p1, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 40
    .line 41
    iget-object p1, p1, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->regionSamplers:Ljava/util/Map;

    .line 42
    .line 43
    invoke-interface {p1, v1}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 44
    .line 45
    .line 46
    iget-object p1, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 47
    .line 48
    iget-object p1, p1, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->smartspaceViews:Ljava/util/Set;

    .line 49
    .line 50
    invoke-interface {p1}, Ljava/util/Set;->isEmpty()Z

    .line 51
    .line 52
    .line 53
    move-result p1

    .line 54
    if-eqz p1, :cond_2

    .line 55
    .line 56
    iget-object p0, p0, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController$stateChangeListener$1;->this$0:Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;

    .line 57
    .line 58
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/lockscreen/LockscreenSmartspaceController;->disconnect()V

    .line 59
    .line 60
    .line 61
    :cond_2
    return-void
.end method
