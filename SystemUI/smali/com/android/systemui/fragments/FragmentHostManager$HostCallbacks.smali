.class public final Lcom/android/systemui/fragments/FragmentHostManager$HostCallbacks;
.super Landroid/app/FragmentHostCallback;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/fragments/FragmentHostManager;


# direct methods
.method public constructor <init>(Lcom/android/systemui/fragments/FragmentHostManager;)V
    .locals 2

    .line 1
    iput-object p1, p0, Lcom/android/systemui/fragments/FragmentHostManager$HostCallbacks;->this$0:Lcom/android/systemui/fragments/FragmentHostManager;

    .line 2
    .line 3
    iget-object v0, p1, Lcom/android/systemui/fragments/FragmentHostManager;->mContext:Landroid/content/Context;

    .line 4
    .line 5
    iget-object p1, p1, Lcom/android/systemui/fragments/FragmentHostManager;->mHandler:Landroid/os/Handler;

    .line 6
    .line 7
    const/4 v1, 0x0

    .line 8
    invoke-direct {p0, v0, p1, v1}, Landroid/app/FragmentHostCallback;-><init>(Landroid/content/Context;Landroid/os/Handler;I)V

    .line 9
    .line 10
    .line 11
    return-void
.end method


# virtual methods
.method public final instantiate(Landroid/content/Context;Ljava/lang/String;Landroid/os/Bundle;)Landroid/app/Fragment;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager$HostCallbacks;->this$0:Lcom/android/systemui/fragments/FragmentHostManager;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager;->mPlugins:Lcom/android/systemui/fragments/FragmentHostManager$ExtensionFragmentManager;

    .line 4
    .line 5
    invoke-virtual {p0, p1, p2, p3}, Lcom/android/systemui/fragments/FragmentHostManager$ExtensionFragmentManager;->instantiate(Landroid/content/Context;Ljava/lang/String;Landroid/os/Bundle;)Landroid/app/Fragment;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    return-object p0
.end method

.method public final onAttachFragment(Landroid/app/Fragment;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final onDump(Ljava/lang/String;Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager$HostCallbacks;->this$0:Lcom/android/systemui/fragments/FragmentHostManager;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final onFindViewById(I)Landroid/view/View;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager$HostCallbacks;->this$0:Lcom/android/systemui/fragments/FragmentHostManager;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager;->mRootView:Landroid/view/View;

    .line 4
    .line 5
    invoke-virtual {p0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    return-object p0
.end method

.method public final onGetHost()Ljava/lang/Object;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager$HostCallbacks;->this$0:Lcom/android/systemui/fragments/FragmentHostManager;

    .line 2
    .line 3
    return-object p0
.end method

.method public final onGetLayoutInflater()Landroid/view/LayoutInflater;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager$HostCallbacks;->this$0:Lcom/android/systemui/fragments/FragmentHostManager;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentHostManager;->mContext:Landroid/content/Context;

    .line 4
    .line 5
    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    return-object p0
.end method

.method public final onGetWindowAnimations()I
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final onHasView()Z
    .locals 0

    .line 1
    const/4 p0, 0x1

    .line 2
    return p0
.end method

.method public final onHasWindowAnimations()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final onShouldSaveFragmentState(Landroid/app/Fragment;)Z
    .locals 0

    .line 1
    const/4 p0, 0x1

    .line 2
    return p0
.end method

.method public final onUseFragmentManagerInflaterFactory()Z
    .locals 0

    .line 1
    const/4 p0, 0x1

    .line 2
    return p0
.end method
