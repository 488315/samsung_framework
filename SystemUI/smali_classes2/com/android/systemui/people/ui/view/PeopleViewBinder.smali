.class public final Lcom/android/systemui/people/ui/view/PeopleViewBinder;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final INSTANCE:Lcom/android/systemui/people/ui/view/PeopleViewBinder;

.field public static final ViewOutlineProvider:Lcom/android/systemui/people/ui/view/PeopleViewBinder$ViewOutlineProvider$1;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/people/ui/view/PeopleViewBinder;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/people/ui/view/PeopleViewBinder;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/systemui/people/ui/view/PeopleViewBinder;->INSTANCE:Lcom/android/systemui/people/ui/view/PeopleViewBinder;

    .line 7
    .line 8
    new-instance v0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$ViewOutlineProvider$1;

    .line 9
    .line 10
    invoke-direct {v0}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$ViewOutlineProvider$1;-><init>()V

    .line 11
    .line 12
    .line 13
    sput-object v0, Lcom/android/systemui/people/ui/view/PeopleViewBinder;->ViewOutlineProvider:Lcom/android/systemui/people/ui/view/PeopleViewBinder$ViewOutlineProvider$1;

    .line 14
    .line 15
    return-void
.end method

.method private constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static final bind(Landroid/view/ViewGroup;Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;Landroidx/lifecycle/LifecycleOwner;Lcom/android/systemui/people/PeopleSpaceActivity$$ExternalSyntheticLambda0;)V
    .locals 3

    .line 1
    invoke-static {p2}, Landroidx/lifecycle/LifecycleOwnerKt;->getLifecycleScope(Landroidx/lifecycle/LifecycleOwner;)Landroidx/lifecycle/LifecycleCoroutineScopeImpl;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    new-instance v1, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$1;

    .line 6
    .line 7
    const/4 v2, 0x0

    .line 8
    invoke-direct {v1, p2, p1, p3, v2}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$1;-><init>(Landroidx/lifecycle/LifecycleOwner;Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V

    .line 9
    .line 10
    .line 11
    const/4 p3, 0x3

    .line 12
    invoke-static {v0, v2, v2, v1, p3}, Lkotlinx/coroutines/BuildersKt;->launch$default(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;I)Lkotlinx/coroutines/StandaloneCoroutine;

    .line 13
    .line 14
    .line 15
    invoke-static {p2}, Landroidx/lifecycle/LifecycleOwnerKt;->getLifecycleScope(Landroidx/lifecycle/LifecycleOwner;)Landroidx/lifecycle/LifecycleCoroutineScopeImpl;

    .line 16
    .line 17
    .line 18
    move-result-object v0

    .line 19
    new-instance v1, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2;

    .line 20
    .line 21
    invoke-direct {v1, p2, p1, p0, v2}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$2;-><init>(Landroidx/lifecycle/LifecycleOwner;Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;Landroid/view/ViewGroup;Lkotlin/coroutines/Continuation;)V

    .line 22
    .line 23
    .line 24
    invoke-static {v0, v2, v2, v1, p3}, Lkotlinx/coroutines/BuildersKt;->launch$default(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;I)Lkotlinx/coroutines/StandaloneCoroutine;

    .line 25
    .line 26
    .line 27
    invoke-static {p2}, Landroidx/lifecycle/LifecycleOwnerKt;->getLifecycleScope(Landroidx/lifecycle/LifecycleOwner;)Landroidx/lifecycle/LifecycleCoroutineScopeImpl;

    .line 28
    .line 29
    .line 30
    move-result-object p0

    .line 31
    new-instance v0, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$3;

    .line 32
    .line 33
    invoke-direct {v0, p2, p1, v2}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bind$3;-><init>(Landroidx/lifecycle/LifecycleOwner;Lcom/android/systemui/people/ui/viewmodel/PeopleViewModel;Lkotlin/coroutines/Continuation;)V

    .line 34
    .line 35
    .line 36
    invoke-static {p0, v2, v2, v0, p3}, Lkotlinx/coroutines/BuildersKt;->launch$default(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;I)Lkotlinx/coroutines/StandaloneCoroutine;

    .line 37
    .line 38
    .line 39
    return-void
.end method

.method public static setTileViews(Landroid/view/View;IILjava/util/List;Lkotlin/jvm/functions/Function1;)V
    .locals 9

    .line 1
    invoke-virtual {p0, p2}, Landroid/view/View;->requireViewById(I)Landroid/view/View;

    .line 2
    .line 3
    .line 4
    move-result-object p2

    .line 5
    check-cast p2, Landroid/view/ViewGroup;

    .line 6
    .line 7
    invoke-virtual {p2}, Landroid/view/ViewGroup;->removeAllViews()V

    .line 8
    .line 9
    .line 10
    sget-object v0, Lcom/android/systemui/people/ui/view/PeopleViewBinder;->ViewOutlineProvider:Lcom/android/systemui/people/ui/view/PeopleViewBinder$ViewOutlineProvider$1;

    .line 11
    .line 12
    invoke-virtual {p2, v0}, Landroid/view/ViewGroup;->setOutlineProvider(Landroid/view/ViewOutlineProvider;)V

    .line 13
    .line 14
    .line 15
    invoke-virtual {p0, p1}, Landroid/view/View;->requireViewById(I)Landroid/view/View;

    .line 16
    .line 17
    .line 18
    move-result-object p1

    .line 19
    check-cast p1, Landroid/widget/LinearLayout;

    .line 20
    .line 21
    invoke-interface {p3}, Ljava/util/List;->isEmpty()Z

    .line 22
    .line 23
    .line 24
    move-result v0

    .line 25
    if-eqz v0, :cond_0

    .line 26
    .line 27
    const/16 p0, 0x8

    .line 28
    .line 29
    invoke-virtual {p1, p0}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 30
    .line 31
    .line 32
    return-void

    .line 33
    :cond_0
    const/4 v0, 0x0

    .line 34
    invoke-virtual {p1, v0}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 35
    .line 36
    .line 37
    invoke-interface {p3}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    .line 38
    .line 39
    .line 40
    move-result-object p1

    .line 41
    move v1, v0

    .line 42
    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    .line 43
    .line 44
    .line 45
    move-result v2

    .line 46
    if-eqz v2, :cond_3

    .line 47
    .line 48
    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 49
    .line 50
    .line 51
    move-result-object v2

    .line 52
    add-int/lit8 v3, v1, 0x1

    .line 53
    .line 54
    if-ltz v1, :cond_2

    .line 55
    .line 56
    check-cast v2, Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;

    .line 57
    .line 58
    new-instance v4, Lcom/android/systemui/people/PeopleSpaceTileView;

    .line 59
    .line 60
    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    .line 61
    .line 62
    .line 63
    move-result-object v5

    .line 64
    iget-object v6, v2, Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;->key:Lcom/android/systemui/people/widget/PeopleTileKey;

    .line 65
    .line 66
    iget-object v6, v6, Lcom/android/systemui/people/widget/PeopleTileKey;->mShortcutId:Ljava/lang/String;

    .line 67
    .line 68
    invoke-interface {p3}, Ljava/util/List;->size()I

    .line 69
    .line 70
    .line 71
    move-result v7

    .line 72
    const/4 v8, 0x1

    .line 73
    sub-int/2addr v7, v8

    .line 74
    if-ne v1, v7, :cond_1

    .line 75
    .line 76
    goto :goto_1

    .line 77
    :cond_1
    move v8, v0

    .line 78
    :goto_1
    invoke-direct {v4, v5, p2, v6, v8}, Lcom/android/systemui/people/PeopleSpaceTileView;-><init>(Landroid/content/Context;Landroid/view/ViewGroup;Ljava/lang/String;Z)V

    .line 79
    .line 80
    .line 81
    sget-object v1, Lcom/android/systemui/people/ui/view/PeopleViewBinder;->INSTANCE:Lcom/android/systemui/people/ui/view/PeopleViewBinder;

    .line 82
    .line 83
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 84
    .line 85
    .line 86
    :try_start_0
    iget-object v1, v2, Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;->username:Ljava/lang/String;

    .line 87
    .line 88
    iget-object v5, v4, Lcom/android/systemui/people/PeopleSpaceTileView;->mNameView:Landroid/widget/TextView;

    .line 89
    .line 90
    invoke-virtual {v5, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 91
    .line 92
    .line 93
    iget-object v1, v2, Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;->icon:Landroid/graphics/Bitmap;

    .line 94
    .line 95
    iget-object v5, v4, Lcom/android/systemui/people/PeopleSpaceTileView;->mPersonIconView:Landroid/widget/ImageView;

    .line 96
    .line 97
    invoke-virtual {v5, v1}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 98
    .line 99
    .line 100
    new-instance v1, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bindTileView$1;

    .line 101
    .line 102
    invoke-direct {v1, p4, v2}, Lcom/android/systemui/people/ui/view/PeopleViewBinder$bindTileView$1;-><init>(Lkotlin/jvm/functions/Function1;Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;)V

    .line 103
    .line 104
    .line 105
    invoke-virtual {v4, v1}, Lcom/android/systemui/people/PeopleSpaceTileView;->setOnClickListener(Landroid/view/View$OnClickListener;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 106
    .line 107
    .line 108
    goto :goto_2

    .line 109
    :catch_0
    move-exception v1

    .line 110
    const-string v2, "PeopleViewBinder"

    .line 111
    .line 112
    const-string v4, "Couldn\'t retrieve shortcut information"

    .line 113
    .line 114
    invoke-static {v2, v4, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 115
    .line 116
    .line 117
    :goto_2
    move v1, v3

    .line 118
    goto :goto_0

    .line 119
    :cond_2
    invoke-static {}, Lkotlin/collections/CollectionsKt__CollectionsKt;->throwIndexOverflow()V

    .line 120
    .line 121
    .line 122
    const/4 p0, 0x0

    .line 123
    throw p0

    .line 124
    :cond_3
    return-void
.end method
