.class public final Lcom/android/systemui/controls/management/ControlsFavoritingActivity;
.super Landroidx/activity/ComponentActivity;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public appName:Ljava/lang/CharSequence;

.field public cancelLoadRunnable:Ljava/lang/Runnable;

.field public comparator:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$onCreate$$inlined$compareBy$1;

.field public component:Landroid/content/ComponentName;

.field public final controller:Lcom/android/systemui/controls/controller/ControlsControllerImpl;

.field public final controlsModelCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$controlsModelCallback$1;

.field public doneButton:Landroid/view/View;

.field public final executor:Ljava/util/concurrent/Executor;

.field public final isNewFlowEnabled:Z

.field public isPagerLoaded:Z

.field public listOfStructures:Ljava/util/List;

.field public final listingCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$listingCallback$1;

.field public final listingController:Lcom/android/systemui/controls/management/ControlsListingController;

.field public final mOnBackInvokedCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$mOnBackInvokedCallback$1;

.field public mTooltipManager:Lcom/android/systemui/controls/TooltipManager;

.field public openSource:B

.field public otherAppsButton:Landroid/view/View;

.field public pageIndicator:Lcom/android/systemui/controls/management/ManagementPageIndicator;

.field public rearrangeButton:Landroid/widget/Button;

.field public statusText:Landroid/widget/TextView;

.field public structureExtra:Ljava/lang/CharSequence;

.field public structurePager:Landroidx/viewpager2/widget/ViewPager2;

.field public subtitleView:Landroid/widget/TextView;

.field public titleView:Landroid/widget/TextView;

.field public final userTracker:Lcom/android/systemui/settings/UserTracker;

.field public final userTrackerCallback:Lcom/android/systemui/settings/UserTracker$Callback;


# direct methods
.method public static constructor <clinit>()V
    .locals 2

    .line 1
    new-instance v0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$Companion;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    invoke-direct {v0, v1}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 5
    .line 6
    .line 7
    return-void
.end method

.method public constructor <init>(Lcom/android/systemui/flags/FeatureFlags;Ljava/util/concurrent/Executor;Lcom/android/systemui/controls/controller/ControlsControllerImpl;Lcom/android/systemui/controls/management/ControlsListingController;Lcom/android/systemui/settings/UserTracker;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Landroidx/activity/ComponentActivity;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p2, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->executor:Ljava/util/concurrent/Executor;

    .line 5
    .line 6
    iput-object p3, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->controller:Lcom/android/systemui/controls/controller/ControlsControllerImpl;

    .line 7
    .line 8
    iput-object p4, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listingController:Lcom/android/systemui/controls/management/ControlsListingController;

    .line 9
    .line 10
    iput-object p5, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->userTracker:Lcom/android/systemui/settings/UserTracker;

    .line 11
    .line 12
    sget-object p2, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 13
    .line 14
    iput-object p2, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listOfStructures:Ljava/util/List;

    .line 15
    .line 16
    sget-object p2, Lcom/android/systemui/flags/Flags;->CONTROLS_MANAGEMENT_NEW_FLOWS:Lcom/android/systemui/flags/ReleasedFlag;

    .line 17
    .line 18
    check-cast p1, Lcom/android/systemui/flags/FeatureFlagsRelease;

    .line 19
    .line 20
    invoke-virtual {p1, p2}, Lcom/android/systemui/flags/FeatureFlagsRelease;->isEnabled(Lcom/android/systemui/flags/ReleasedFlag;)Z

    .line 21
    .line 22
    .line 23
    move-result p1

    .line 24
    iput-boolean p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->isNewFlowEnabled:Z

    .line 25
    .line 26
    new-instance p1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$userTrackerCallback$1;

    .line 27
    .line 28
    invoke-direct {p1, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$userTrackerCallback$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 29
    .line 30
    .line 31
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->userTrackerCallback:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 32
    .line 33
    new-instance p1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$mOnBackInvokedCallback$1;

    .line 34
    .line 35
    invoke-direct {p1, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$mOnBackInvokedCallback$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 36
    .line 37
    .line 38
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->mOnBackInvokedCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$mOnBackInvokedCallback$1;

    .line 39
    .line 40
    new-instance p1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$listingCallback$1;

    .line 41
    .line 42
    invoke-direct {p1, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$listingCallback$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 43
    .line 44
    .line 45
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listingCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$listingCallback$1;

    .line 46
    .line 47
    new-instance p1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$controlsModelCallback$1;

    .line 48
    .line 49
    invoke-direct {p1, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$controlsModelCallback$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 50
    .line 51
    .line 52
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->controlsModelCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$controlsModelCallback$1;

    .line 53
    .line 54
    return-void
.end method

.method public static final access$saveFavorites(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V
    .locals 5

    .line 1
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listOfStructures:Ljava/util/List;

    .line 2
    .line 3
    invoke-interface {v0}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    .line 8
    .line 9
    .line 10
    move-result v1

    .line 11
    if-eqz v1, :cond_1

    .line 12
    .line 13
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object v1

    .line 17
    check-cast v1, Lcom/android/systemui/controls/management/StructureContainer;

    .line 18
    .line 19
    iget-object v2, v1, Lcom/android/systemui/controls/management/StructureContainer;->model:Lcom/android/systemui/controls/management/ControlsModel;

    .line 20
    .line 21
    invoke-interface {v2}, Lcom/android/systemui/controls/management/ControlsModel;->getFavorites()Ljava/util/List;

    .line 22
    .line 23
    .line 24
    move-result-object v2

    .line 25
    new-instance v3, Lcom/android/systemui/controls/controller/StructureInfo;

    .line 26
    .line 27
    iget-object v4, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->component:Landroid/content/ComponentName;

    .line 28
    .line 29
    invoke-static {v4}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    .line 30
    .line 31
    .line 32
    iget-object v1, v1, Lcom/android/systemui/controls/management/StructureContainer;->structureName:Ljava/lang/CharSequence;

    .line 33
    .line 34
    invoke-direct {v3, v4, v1, v2}, Lcom/android/systemui/controls/controller/StructureInfo;-><init>(Landroid/content/ComponentName;Ljava/lang/CharSequence;Ljava/util/List;)V

    .line 35
    .line 36
    .line 37
    iget-object v1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->controller:Lcom/android/systemui/controls/controller/ControlsControllerImpl;

    .line 38
    .line 39
    invoke-virtual {v1}, Lcom/android/systemui/controls/controller/ControlsControllerImpl;->confirmAvailability()Z

    .line 40
    .line 41
    .line 42
    move-result v2

    .line 43
    if-nez v2, :cond_0

    .line 44
    .line 45
    goto :goto_0

    .line 46
    :cond_0
    new-instance v2, Lcom/android/systemui/controls/controller/ControlsControllerImpl$replaceFavoritesForStructure$1;

    .line 47
    .line 48
    invoke-direct {v2, v3, v1}, Lcom/android/systemui/controls/controller/ControlsControllerImpl$replaceFavoritesForStructure$1;-><init>(Lcom/android/systemui/controls/controller/StructureInfo;Lcom/android/systemui/controls/controller/ControlsControllerImpl;)V

    .line 49
    .line 50
    .line 51
    iget-object v1, v1, Lcom/android/systemui/controls/controller/ControlsControllerImpl;->executor:Lcom/android/systemui/util/concurrency/DelayableExecutor;

    .line 52
    .line 53
    check-cast v1, Lcom/android/systemui/util/concurrency/ExecutorImpl;

    .line 54
    .line 55
    invoke-virtual {v1, v2}, Lcom/android/systemui/util/concurrency/ExecutorImpl;->execute(Ljava/lang/Runnable;)V

    .line 56
    .line 57
    .line 58
    goto :goto_0

    .line 59
    :cond_1
    return-void
.end method


# virtual methods
.method public animateExitAndFinish$frameworks__base__packages__SystemUI__android_common__SystemUI_core()V
    .locals 2

    .line 1
    const v0, 0x7f0a02b9

    .line 2
    .line 3
    .line 4
    invoke-virtual {p0, v0}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 5
    .line 6
    .line 7
    move-result-object v0

    .line 8
    check-cast v0, Landroid/view/ViewGroup;

    .line 9
    .line 10
    new-instance v1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$animateExitAndFinish$1;

    .line 11
    .line 12
    invoke-direct {v1, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$animateExitAndFinish$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 13
    .line 14
    .line 15
    invoke-static {v0, v1}, Lcom/android/systemui/controls/management/ControlsAnimations;->exitAnimation(Landroid/view/View;Ljava/lang/Runnable;)Landroid/animation/Animator;

    .line 16
    .line 17
    .line 18
    move-result-object p0

    .line 19
    invoke-virtual {p0}, Landroid/animation/Animator;->start()V

    .line 20
    .line 21
    .line 22
    return-void
.end method

.method public final onBackPressed()V
    .locals 4

    .line 1
    iget-byte v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->openSource:B

    .line 2
    .line 3
    const/4 v1, 0x2

    .line 4
    const/4 v2, 0x1

    .line 5
    const/4 v3, 0x0

    .line 6
    if-ne v0, v1, :cond_0

    .line 7
    .line 8
    move v0, v2

    .line 9
    goto :goto_0

    .line 10
    :cond_0
    move v0, v3

    .line 11
    :goto_0
    if-eqz v0, :cond_1

    .line 12
    .line 13
    invoke-virtual {p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->animateExitAndFinish$frameworks__base__packages__SystemUI__android_common__SystemUI_core()V

    .line 14
    .line 15
    .line 16
    :cond_1
    iget-byte v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->openSource:B

    .line 17
    .line 18
    if-ne v0, v2, :cond_2

    .line 19
    .line 20
    goto :goto_1

    .line 21
    :cond_2
    move v2, v3

    .line 22
    :goto_1
    if-nez v2, :cond_3

    .line 23
    .line 24
    new-instance v0, Landroid/content/Intent;

    .line 25
    .line 26
    invoke-virtual {p0}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    .line 27
    .line 28
    .line 29
    move-result-object v1

    .line 30
    const-class v2, Lcom/android/systemui/controls/ui/ControlsActivity;

    .line 31
    .line 32
    invoke-direct {v0, v1, v2}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 33
    .line 34
    .line 35
    new-array v1, v3, [Landroid/util/Pair;

    .line 36
    .line 37
    invoke-static {p0, v1}, Landroid/app/ActivityOptions;->makeSceneTransitionAnimation(Landroid/app/Activity;[Landroid/util/Pair;)Landroid/app/ActivityOptions;

    .line 38
    .line 39
    .line 40
    move-result-object v1

    .line 41
    invoke-virtual {v1}, Landroid/app/ActivityOptions;->toBundle()Landroid/os/Bundle;

    .line 42
    .line 43
    .line 44
    move-result-object v1

    .line 45
    invoke-virtual {p0, v0, v1}, Landroid/app/Activity;->startActivity(Landroid/content/Intent;Landroid/os/Bundle;)V

    .line 46
    .line 47
    .line 48
    :cond_3
    invoke-virtual {p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->animateExitAndFinish$frameworks__base__packages__SystemUI__android_common__SystemUI_core()V

    .line 49
    .line 50
    .line 51
    return-void
.end method

.method public final onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 0

    .line 1
    invoke-super {p0, p1}, Landroidx/activity/ComponentActivity;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 2
    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->mTooltipManager:Lcom/android/systemui/controls/TooltipManager;

    .line 5
    .line 6
    if-eqz p0, :cond_0

    .line 7
    .line 8
    const/4 p1, 0x0

    .line 9
    invoke-virtual {p0, p1}, Lcom/android/systemui/controls/TooltipManager;->hide(Z)V

    .line 10
    .line 11
    .line 12
    :cond_0
    return-void
.end method

.method public final onCreate(Landroid/os/Bundle;)V
    .locals 12

    .line 1
    invoke-super {p0, p1}, Landroidx/activity/ComponentActivity;->onCreate(Landroid/os/Bundle;)V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    .line 5
    .line 6
    .line 7
    move-result-object p1

    .line 8
    invoke-virtual {p1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    .line 9
    .line 10
    .line 11
    move-result-object p1

    .line 12
    invoke-virtual {p1}, Landroid/content/res/Configuration;->getLocales()Landroid/os/LocaleList;

    .line 13
    .line 14
    .line 15
    move-result-object p1

    .line 16
    const/4 v0, 0x0

    .line 17
    invoke-virtual {p1, v0}, Landroid/os/LocaleList;->get(I)Ljava/util/Locale;

    .line 18
    .line 19
    .line 20
    move-result-object p1

    .line 21
    invoke-static {p1}, Ljava/text/Collator;->getInstance(Ljava/util/Locale;)Ljava/text/Collator;

    .line 22
    .line 23
    .line 24
    move-result-object p1

    .line 25
    new-instance v1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$onCreate$$inlined$compareBy$1;

    .line 26
    .line 27
    invoke-direct {v1, p1}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$onCreate$$inlined$compareBy$1;-><init>(Ljava/util/Comparator;)V

    .line 28
    .line 29
    .line 30
    iput-object v1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->comparator:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$onCreate$$inlined$compareBy$1;

    .line 31
    .line 32
    invoke-virtual {p0}, Landroid/app/Activity;->getIntent()Landroid/content/Intent;

    .line 33
    .line 34
    .line 35
    move-result-object p1

    .line 36
    const-string v1, "extra_app_label"

    .line 37
    .line 38
    invoke-virtual {p1, v1}, Landroid/content/Intent;->getCharSequenceExtra(Ljava/lang/String;)Ljava/lang/CharSequence;

    .line 39
    .line 40
    .line 41
    move-result-object p1

    .line 42
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->appName:Ljava/lang/CharSequence;

    .line 43
    .line 44
    invoke-virtual {p0}, Landroid/app/Activity;->getIntent()Landroid/content/Intent;

    .line 45
    .line 46
    .line 47
    move-result-object p1

    .line 48
    const-string v1, "extra_structure"

    .line 49
    .line 50
    invoke-virtual {p1, v1}, Landroid/content/Intent;->getCharSequenceExtra(Ljava/lang/String;)Ljava/lang/CharSequence;

    .line 51
    .line 52
    .line 53
    move-result-object p1

    .line 54
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->structureExtra:Ljava/lang/CharSequence;

    .line 55
    .line 56
    invoke-virtual {p0}, Landroid/app/Activity;->getIntent()Landroid/content/Intent;

    .line 57
    .line 58
    .line 59
    move-result-object p1

    .line 60
    const-string v1, "android.intent.extra.COMPONENT_NAME"

    .line 61
    .line 62
    invoke-virtual {p1, v1}, Landroid/content/Intent;->getParcelableExtra(Ljava/lang/String;)Landroid/os/Parcelable;

    .line 63
    .line 64
    .line 65
    move-result-object p1

    .line 66
    check-cast p1, Landroid/content/ComponentName;

    .line 67
    .line 68
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->component:Landroid/content/ComponentName;

    .line 69
    .line 70
    invoke-virtual {p0}, Landroid/app/Activity;->getIntent()Landroid/content/Intent;

    .line 71
    .line 72
    .line 73
    move-result-object p1

    .line 74
    const-string v1, "extra_source"

    .line 75
    .line 76
    invoke-virtual {p1, v1, v0}, Landroid/content/Intent;->getByteExtra(Ljava/lang/String;B)B

    .line 77
    .line 78
    .line 79
    move-result p1

    .line 80
    iput-byte p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->openSource:B

    .line 81
    .line 82
    const p1, 0x7f0d009e

    .line 83
    .line 84
    .line 85
    invoke-virtual {p0, p1}, Landroidx/activity/ComponentActivity;->setContentView(I)V

    .line 86
    .line 87
    .line 88
    sget-object p1, Lcom/android/systemui/controls/management/ControlsAnimations;->INSTANCE:Lcom/android/systemui/controls/management/ControlsAnimations;

    .line 89
    .line 90
    const v1, 0x7f0a02b9

    .line 91
    .line 92
    .line 93
    invoke-virtual {p0, v1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 94
    .line 95
    .line 96
    move-result-object v1

    .line 97
    check-cast v1, Landroid/view/ViewGroup;

    .line 98
    .line 99
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    .line 100
    .line 101
    .line 102
    move-result-object v2

    .line 103
    invoke-virtual {p0}, Landroid/app/Activity;->getIntent()Landroid/content/Intent;

    .line 104
    .line 105
    .line 106
    move-result-object v3

    .line 107
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 108
    .line 109
    .line 110
    new-instance p1, Lcom/android/systemui/controls/management/ControlsAnimations$observerForAnimations$1;

    .line 111
    .line 112
    const/4 v4, 0x1

    .line 113
    invoke-direct {p1, v3, v1, v4, v2}, Lcom/android/systemui/controls/management/ControlsAnimations$observerForAnimations$1;-><init>(Landroid/content/Intent;Landroid/view/ViewGroup;ZLandroid/view/Window;)V

    .line 114
    .line 115
    .line 116
    iget-object v1, p0, Landroidx/activity/ComponentActivity;->mLifecycleRegistry:Landroidx/lifecycle/LifecycleRegistry;

    .line 117
    .line 118
    invoke-virtual {v1, p1}, Landroidx/lifecycle/LifecycleRegistry;->addObserver(Landroidx/lifecycle/LifecycleObserver;)V

    .line 119
    .line 120
    .line 121
    const p1, 0x7f0a0ae8

    .line 122
    .line 123
    .line 124
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 125
    .line 126
    .line 127
    move-result-object p1

    .line 128
    check-cast p1, Landroid/view/ViewStub;

    .line 129
    .line 130
    const v1, 0x7f0d00a1

    .line 131
    .line 132
    .line 133
    invoke-virtual {p1, v1}, Landroid/view/ViewStub;->setLayoutResource(I)V

    .line 134
    .line 135
    .line 136
    invoke-virtual {p1}, Landroid/view/ViewStub;->inflate()Landroid/view/View;

    .line 137
    .line 138
    .line 139
    const p1, 0x7f0a0add

    .line 140
    .line 141
    .line 142
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 143
    .line 144
    .line 145
    move-result-object p1

    .line 146
    check-cast p1, Landroid/widget/TextView;

    .line 147
    .line 148
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->statusText:Landroid/widget/TextView;

    .line 149
    .line 150
    invoke-virtual {p0}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    .line 151
    .line 152
    .line 153
    move-result-object p1

    .line 154
    const-string v1, "ControlsStructureSwipeTooltipCount"

    .line 155
    .line 156
    invoke-static {p1, v1, v0}, Lcom/android/systemui/Prefs;->getInt(Landroid/content/Context;Ljava/lang/String;I)I

    .line 157
    .line 158
    .line 159
    move-result p1

    .line 160
    const/4 v1, 0x2

    .line 161
    if-ge p1, v1, :cond_0

    .line 162
    .line 163
    move p1, v4

    .line 164
    goto :goto_0

    .line 165
    :cond_0
    move p1, v0

    .line 166
    :goto_0
    if-eqz p1, :cond_2

    .line 167
    .line 168
    new-instance p1, Lcom/android/systemui/controls/TooltipManager;

    .line 169
    .line 170
    iget-object v2, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->statusText:Landroid/widget/TextView;

    .line 171
    .line 172
    if-nez v2, :cond_1

    .line 173
    .line 174
    const/4 v2, 0x0

    .line 175
    :cond_1
    invoke-virtual {v2}, Landroid/widget/TextView;->getContext()Landroid/content/Context;

    .line 176
    .line 177
    .line 178
    move-result-object v6

    .line 179
    const-string v7, "ControlsStructureSwipeTooltipCount"

    .line 180
    .line 181
    const/4 v8, 0x2

    .line 182
    const/4 v9, 0x0

    .line 183
    const/16 v10, 0x8

    .line 184
    .line 185
    const/4 v11, 0x0

    .line 186
    move-object v5, p1

    .line 187
    invoke-direct/range {v5 .. v11}, Lcom/android/systemui/controls/TooltipManager;-><init>(Landroid/content/Context;Ljava/lang/String;IZILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 188
    .line 189
    .line 190
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->mTooltipManager:Lcom/android/systemui/controls/TooltipManager;

    .line 191
    .line 192
    new-instance v2, Landroid/widget/FrameLayout$LayoutParams;

    .line 193
    .line 194
    const/16 v3, 0x33

    .line 195
    .line 196
    const/4 v5, -0x2

    .line 197
    invoke-direct {v2, v5, v5, v3}, Landroid/widget/FrameLayout$LayoutParams;-><init>(III)V

    .line 198
    .line 199
    .line 200
    iget-object p1, p1, Lcom/android/systemui/controls/TooltipManager;->layout:Landroid/view/ViewGroup;

    .line 201
    .line 202
    invoke-virtual {p0, p1, v2}, Landroidx/activity/ComponentActivity;->addContentView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 203
    .line 204
    .line 205
    :cond_2
    const p1, 0x7f0a0ae5

    .line 206
    .line 207
    .line 208
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 209
    .line 210
    .line 211
    move-result-object p1

    .line 212
    check-cast p1, Lcom/android/systemui/controls/management/ManagementPageIndicator;

    .line 213
    .line 214
    new-instance v2, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindViews$2$1;

    .line 215
    .line 216
    invoke-direct {v2, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindViews$2$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 217
    .line 218
    .line 219
    iput-object v2, p1, Lcom/android/systemui/controls/management/ManagementPageIndicator;->visibilityListener:Lkotlin/jvm/functions/Function1;

    .line 220
    .line 221
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->pageIndicator:Lcom/android/systemui/controls/management/ManagementPageIndicator;

    .line 222
    .line 223
    iget-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->structureExtra:Ljava/lang/CharSequence;

    .line 224
    .line 225
    if-nez p1, :cond_3

    .line 226
    .line 227
    iget-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->appName:Ljava/lang/CharSequence;

    .line 228
    .line 229
    if-nez p1, :cond_3

    .line 230
    .line 231
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    .line 232
    .line 233
    .line 234
    move-result-object p1

    .line 235
    const v2, 0x7f1303bf

    .line 236
    .line 237
    .line 238
    invoke-virtual {p1, v2}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    .line 239
    .line 240
    .line 241
    move-result-object p1

    .line 242
    :cond_3
    const v2, 0x7f0a0bd9

    .line 243
    .line 244
    .line 245
    invoke-virtual {p0, v2}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 246
    .line 247
    .line 248
    move-result-object v2

    .line 249
    check-cast v2, Landroid/widget/TextView;

    .line 250
    .line 251
    invoke-virtual {v2, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 252
    .line 253
    .line 254
    iput-object v2, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->titleView:Landroid/widget/TextView;

    .line 255
    .line 256
    const p1, 0x7f0a0b4d

    .line 257
    .line 258
    .line 259
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 260
    .line 261
    .line 262
    move-result-object p1

    .line 263
    check-cast p1, Landroid/widget/TextView;

    .line 264
    .line 265
    invoke-virtual {p1}, Landroid/widget/TextView;->getResources()Landroid/content/res/Resources;

    .line 266
    .line 267
    .line 268
    move-result-object v2

    .line 269
    const v3, 0x7f1303c8

    .line 270
    .line 271
    .line 272
    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    .line 273
    .line 274
    .line 275
    move-result-object v2

    .line 276
    invoke-virtual {p1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 277
    .line 278
    .line 279
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->subtitleView:Landroid/widget/TextView;

    .line 280
    .line 281
    const p1, 0x7f0a0ae6

    .line 282
    .line 283
    .line 284
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 285
    .line 286
    .line 287
    move-result-object p1

    .line 288
    check-cast p1, Landroidx/viewpager2/widget/ViewPager2;

    .line 289
    .line 290
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->structurePager:Landroidx/viewpager2/widget/ViewPager2;

    .line 291
    .line 292
    new-instance v2, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindViews$5;

    .line 293
    .line 294
    invoke-direct {v2, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindViews$5;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 295
    .line 296
    .line 297
    iget-object p1, p1, Landroidx/viewpager2/widget/ViewPager2;->mExternalPageChangeCallbacks:Landroidx/viewpager2/widget/CompositeOnPageChangeCallback;

    .line 298
    .line 299
    iget-object p1, p1, Landroidx/viewpager2/widget/CompositeOnPageChangeCallback;->mCallbacks:Ljava/util/List;

    .line 300
    .line 301
    check-cast p1, Ljava/util/ArrayList;

    .line 302
    .line 303
    invoke-virtual {p1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 304
    .line 305
    .line 306
    const p1, 0x7f0a089b

    .line 307
    .line 308
    .line 309
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 310
    .line 311
    .line 312
    move-result-object p1

    .line 313
    check-cast p1, Landroid/widget/Button;

    .line 314
    .line 315
    iget-byte v2, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->openSource:B

    .line 316
    .line 317
    if-ne v2, v1, :cond_4

    .line 318
    .line 319
    goto :goto_1

    .line 320
    :cond_4
    move v4, v0

    .line 321
    :goto_1
    if-eqz v4, :cond_5

    .line 322
    .line 323
    const v1, 0x7f1303be

    .line 324
    .line 325
    .line 326
    invoke-virtual {p0, v1}, Landroid/app/Activity;->getString(I)Ljava/lang/String;

    .line 327
    .line 328
    .line 329
    move-result-object v1

    .line 330
    goto :goto_2

    .line 331
    :cond_5
    const v1, 0x7f1303c5

    .line 332
    .line 333
    .line 334
    invoke-virtual {p0, v1}, Landroid/app/Activity;->getString(I)Ljava/lang/String;

    .line 335
    .line 336
    .line 337
    move-result-object v1

    .line 338
    :goto_2
    invoke-virtual {p1, v1}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 339
    .line 340
    .line 341
    invoke-virtual {p1, v0}, Landroid/widget/Button;->setEnabled(Z)V

    .line 342
    .line 343
    .line 344
    iget-boolean v1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->isNewFlowEnabled:Z

    .line 345
    .line 346
    if-eqz v1, :cond_6

    .line 347
    .line 348
    move v1, v0

    .line 349
    goto :goto_3

    .line 350
    :cond_6
    const/16 v1, 0x8

    .line 351
    .line 352
    :goto_3
    invoke-virtual {p1, v1}, Landroid/widget/Button;->setVisibility(I)V

    .line 353
    .line 354
    .line 355
    new-instance v1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindButtons$1$1;

    .line 356
    .line 357
    invoke-direct {v1, p0, p1}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindButtons$1$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;Landroid/widget/Button;)V

    .line 358
    .line 359
    .line 360
    invoke-virtual {p1, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 361
    .line 362
    .line 363
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->rearrangeButton:Landroid/widget/Button;

    .line 364
    .line 365
    const p1, 0x7f0a079f

    .line 366
    .line 367
    .line 368
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 369
    .line 370
    .line 371
    move-result-object p1

    .line 372
    move-object v1, p1

    .line 373
    check-cast v1, Landroid/widget/Button;

    .line 374
    .line 375
    new-instance v2, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindButtons$2$1;

    .line 376
    .line 377
    invoke-direct {v2, p0, v1}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindButtons$2$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;Landroid/widget/Button;)V

    .line 378
    .line 379
    .line 380
    invoke-virtual {v1, v2}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 381
    .line 382
    .line 383
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->otherAppsButton:Landroid/view/View;

    .line 384
    .line 385
    const p1, 0x7f0a035e

    .line 386
    .line 387
    .line 388
    invoke-virtual {p0, p1}, Landroid/app/Activity;->requireViewById(I)Landroid/view/View;

    .line 389
    .line 390
    .line 391
    move-result-object p1

    .line 392
    move-object v1, p1

    .line 393
    check-cast v1, Landroid/widget/Button;

    .line 394
    .line 395
    invoke-virtual {v1, v0}, Landroid/widget/Button;->setEnabled(Z)V

    .line 396
    .line 397
    .line 398
    new-instance v0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindButtons$3$1;

    .line 399
    .line 400
    invoke-direct {v0, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$bindButtons$3$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 401
    .line 402
    .line 403
    invoke-virtual {v1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 404
    .line 405
    .line 406
    iput-object p1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->doneButton:Landroid/view/View;

    .line 407
    .line 408
    return-void
.end method

.method public final onDestroy()V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->cancelLoadRunnable:Ljava/lang/Runnable;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    invoke-interface {v0}, Ljava/lang/Runnable;->run()V

    .line 6
    .line 7
    .line 8
    :cond_0
    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V

    .line 9
    .line 10
    .line 11
    return-void
.end method

.method public final onPause()V
    .locals 1

    .line 1
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    .line 2
    .line 3
    .line 4
    iget-object p0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->mTooltipManager:Lcom/android/systemui/controls/TooltipManager;

    .line 5
    .line 6
    if-eqz p0, :cond_0

    .line 7
    .line 8
    const/4 v0, 0x0

    .line 9
    invoke-virtual {p0, v0}, Lcom/android/systemui/controls/TooltipManager;->hide(Z)V

    .line 10
    .line 11
    .line 12
    :cond_0
    return-void
.end method

.method public final onResume()V
    .locals 5

    .line 1
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 2
    .line 3
    .line 4
    iget-boolean v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->isPagerLoaded:Z

    .line 5
    .line 6
    if-nez v0, :cond_5

    .line 7
    .line 8
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->structurePager:Landroidx/viewpager2/widget/ViewPager2;

    .line 9
    .line 10
    const/4 v1, 0x0

    .line 11
    if-nez v0, :cond_0

    .line 12
    .line 13
    move-object v0, v1

    .line 14
    :cond_0
    const/4 v2, 0x0

    .line 15
    invoke-virtual {v0, v2}, Landroid/view/ViewGroup;->setAlpha(F)V

    .line 16
    .line 17
    .line 18
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->pageIndicator:Lcom/android/systemui/controls/management/ManagementPageIndicator;

    .line 19
    .line 20
    if-nez v0, :cond_1

    .line 21
    .line 22
    move-object v0, v1

    .line 23
    :cond_1
    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setAlpha(F)V

    .line 24
    .line 25
    .line 26
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->structurePager:Landroidx/viewpager2/widget/ViewPager2;

    .line 27
    .line 28
    if-nez v0, :cond_2

    .line 29
    .line 30
    move-object v0, v1

    .line 31
    :cond_2
    new-instance v2, Lcom/android/systemui/controls/management/StructureAdapter;

    .line 32
    .line 33
    sget-object v3, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 34
    .line 35
    iget-object v4, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->userTracker:Lcom/android/systemui/settings/UserTracker;

    .line 36
    .line 37
    check-cast v4, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 38
    .line 39
    invoke-virtual {v4}, Lcom/android/systemui/settings/UserTrackerImpl;->getUserId()I

    .line 40
    .line 41
    .line 42
    move-result v4

    .line 43
    invoke-direct {v2, v3, v4}, Lcom/android/systemui/controls/management/StructureAdapter;-><init>(Ljava/util/List;I)V

    .line 44
    .line 45
    .line 46
    invoke-virtual {v0, v2}, Landroidx/viewpager2/widget/ViewPager2;->setAdapter(Lcom/android/systemui/controls/management/StructureAdapter;)V

    .line 47
    .line 48
    .line 49
    new-instance v2, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$setUpPager$1$1;

    .line 50
    .line 51
    invoke-direct {v2, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$setUpPager$1$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 52
    .line 53
    .line 54
    iget-object v0, v0, Landroidx/viewpager2/widget/ViewPager2;->mExternalPageChangeCallbacks:Landroidx/viewpager2/widget/CompositeOnPageChangeCallback;

    .line 55
    .line 56
    iget-object v0, v0, Landroidx/viewpager2/widget/CompositeOnPageChangeCallback;->mCallbacks:Ljava/util/List;

    .line 57
    .line 58
    check-cast v0, Ljava/util/ArrayList;

    .line 59
    .line 60
    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 61
    .line 62
    .line 63
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->component:Landroid/content/ComponentName;

    .line 64
    .line 65
    if-eqz v0, :cond_4

    .line 66
    .line 67
    iget-object v2, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->statusText:Landroid/widget/TextView;

    .line 68
    .line 69
    if-nez v2, :cond_3

    .line 70
    .line 71
    goto :goto_0

    .line 72
    :cond_3
    move-object v1, v2

    .line 73
    :goto_0
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    .line 74
    .line 75
    .line 76
    move-result-object v2

    .line 77
    const v3, 0x10406b7

    .line 78
    .line 79
    .line 80
    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    .line 81
    .line 82
    .line 83
    move-result-object v2

    .line 84
    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 85
    .line 86
    .line 87
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    .line 88
    .line 89
    .line 90
    move-result-object v1

    .line 91
    const v2, 0x7f1303c3

    .line 92
    .line 93
    .line 94
    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    .line 95
    .line 96
    .line 97
    move-result-object v1

    .line 98
    new-instance v2, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$loadControls$1$1;

    .line 99
    .line 100
    invoke-direct {v2, p0, v1}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$loadControls$1$1;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;Ljava/lang/CharSequence;)V

    .line 101
    .line 102
    .line 103
    new-instance v1, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$loadControls$1$2;

    .line 104
    .line 105
    invoke-direct {v1, p0}, Lcom/android/systemui/controls/management/ControlsFavoritingActivity$loadControls$1$2;-><init>(Lcom/android/systemui/controls/management/ControlsFavoritingActivity;)V

    .line 106
    .line 107
    .line 108
    iget-object v3, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->controller:Lcom/android/systemui/controls/controller/ControlsControllerImpl;

    .line 109
    .line 110
    invoke-virtual {v3, v0, v2, v1}, Lcom/android/systemui/controls/controller/ControlsControllerImpl;->loadForComponent(Landroid/content/ComponentName;Ljava/util/function/Consumer;Ljava/util/function/Consumer;)V

    .line 111
    .line 112
    .line 113
    :cond_4
    const/4 v0, 0x1

    .line 114
    iput-boolean v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->isPagerLoaded:Z

    .line 115
    .line 116
    :cond_5
    return-void
.end method

.method public final onStart()V
    .locals 3

    .line 1
    invoke-super {p0}, Landroid/app/Activity;->onStart()V

    .line 2
    .line 3
    .line 4
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listingController:Lcom/android/systemui/controls/management/ControlsListingController;

    .line 5
    .line 6
    check-cast v0, Lcom/android/systemui/controls/management/ControlsListingControllerImpl;

    .line 7
    .line 8
    iget-object v1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listingCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$listingCallback$1;

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Lcom/android/systemui/controls/management/ControlsListingControllerImpl;->addCallback(Ljava/lang/Object;)V

    .line 11
    .line 12
    .line 13
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->userTrackerCallback:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 14
    .line 15
    iget-object v1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->userTracker:Lcom/android/systemui/settings/UserTracker;

    .line 16
    .line 17
    check-cast v1, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 18
    .line 19
    iget-object v2, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->executor:Ljava/util/concurrent/Executor;

    .line 20
    .line 21
    invoke-virtual {v1, v0, v2}, Lcom/android/systemui/settings/UserTrackerImpl;->addCallback(Lcom/android/systemui/settings/UserTracker$Callback;Ljava/util/concurrent/Executor;)V

    .line 22
    .line 23
    .line 24
    invoke-virtual {p0}, Landroid/app/Activity;->getOnBackInvokedDispatcher()Landroid/window/OnBackInvokedDispatcher;

    .line 25
    .line 26
    .line 27
    move-result-object v0

    .line 28
    iget-object p0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->mOnBackInvokedCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$mOnBackInvokedCallback$1;

    .line 29
    .line 30
    const/4 v1, 0x0

    .line 31
    invoke-interface {v0, v1, p0}, Landroid/window/OnBackInvokedDispatcher;->registerOnBackInvokedCallback(ILandroid/window/OnBackInvokedCallback;)V

    .line 32
    .line 33
    .line 34
    return-void
.end method

.method public final onStop()V
    .locals 2

    .line 1
    invoke-super {p0}, Landroid/app/Activity;->onStop()V

    .line 2
    .line 3
    .line 4
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listingController:Lcom/android/systemui/controls/management/ControlsListingController;

    .line 5
    .line 6
    check-cast v0, Lcom/android/systemui/controls/management/ControlsListingControllerImpl;

    .line 7
    .line 8
    iget-object v1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->listingCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$listingCallback$1;

    .line 9
    .line 10
    invoke-virtual {v0, v1}, Lcom/android/systemui/controls/management/ControlsListingControllerImpl;->removeCallback(Ljava/lang/Object;)V

    .line 11
    .line 12
    .line 13
    iget-object v0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->userTrackerCallback:Lcom/android/systemui/settings/UserTracker$Callback;

    .line 14
    .line 15
    iget-object v1, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->userTracker:Lcom/android/systemui/settings/UserTracker;

    .line 16
    .line 17
    check-cast v1, Lcom/android/systemui/settings/UserTrackerImpl;

    .line 18
    .line 19
    invoke-virtual {v1, v0}, Lcom/android/systemui/settings/UserTrackerImpl;->removeCallback(Lcom/android/systemui/settings/UserTracker$Callback;)V

    .line 20
    .line 21
    .line 22
    invoke-virtual {p0}, Landroid/app/Activity;->getOnBackInvokedDispatcher()Landroid/window/OnBackInvokedDispatcher;

    .line 23
    .line 24
    .line 25
    move-result-object v0

    .line 26
    iget-object p0, p0, Lcom/android/systemui/controls/management/ControlsFavoritingActivity;->mOnBackInvokedCallback:Lcom/android/systemui/controls/management/ControlsFavoritingActivity$mOnBackInvokedCallback$1;

    .line 27
    .line 28
    invoke-interface {v0, p0}, Landroid/window/OnBackInvokedDispatcher;->unregisterOnBackInvokedCallback(Landroid/window/OnBackInvokedCallback;)V

    .line 29
    .line 30
    .line 31
    return-void
.end method
