.class public final Landroidx/viewpager2/adapter/FragmentStateAdapter$4;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic this$0:Landroidx/viewpager2/adapter/FragmentStateAdapter;


# direct methods
.method public constructor <init>(Landroidx/viewpager2/adapter/FragmentStateAdapter;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/viewpager2/adapter/FragmentStateAdapter$4;->this$0:Landroidx/viewpager2/adapter/FragmentStateAdapter;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object p0, p0, Landroidx/viewpager2/adapter/FragmentStateAdapter$4;->this$0:Landroidx/viewpager2/adapter/FragmentStateAdapter;

    .line 2
    .line 3
    const/4 v0, 0x0

    .line 4
    iput-boolean v0, p0, Landroidx/viewpager2/adapter/FragmentStateAdapter;->mIsInGracePeriod:Z

    .line 5
    .line 6
    invoke-virtual {p0}, Landroidx/viewpager2/adapter/FragmentStateAdapter;->gcFragments()V

    .line 7
    .line 8
    .line 9
    return-void
.end method