.class public final Landroidx/viewpager2/widget/ViewPager2$2;
.super Landroidx/viewpager2/widget/ViewPager2$OnPageChangeCallback;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Landroidx/viewpager2/widget/ViewPager2;


# direct methods
.method public constructor <init>(Landroidx/viewpager2/widget/ViewPager2;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/viewpager2/widget/ViewPager2$2;->this$0:Landroidx/viewpager2/widget/ViewPager2;

    .line 2
    .line 3
    invoke-direct {p0}, Landroidx/viewpager2/widget/ViewPager2$OnPageChangeCallback;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onPageScrollStateChanged(I)V
    .locals 0

    .line 1
    if-nez p1, :cond_0

    .line 2
    .line 3
    iget-object p0, p0, Landroidx/viewpager2/widget/ViewPager2$2;->this$0:Landroidx/viewpager2/widget/ViewPager2;

    .line 4
    .line 5
    invoke-virtual {p0}, Landroidx/viewpager2/widget/ViewPager2;->updateCurrentItem()V

    .line 6
    .line 7
    .line 8
    :cond_0
    return-void
.end method

.method public final onPageSelected(I)V
    .locals 1

    .line 1
    iget-object p0, p0, Landroidx/viewpager2/widget/ViewPager2$2;->this$0:Landroidx/viewpager2/widget/ViewPager2;

    .line 2
    .line 3
    iget v0, p0, Landroidx/viewpager2/widget/ViewPager2;->mCurrentItem:I

    .line 4
    .line 5
    if-eq v0, p1, :cond_0

    .line 6
    .line 7
    iput p1, p0, Landroidx/viewpager2/widget/ViewPager2;->mCurrentItem:I

    .line 8
    .line 9
    iget-object p0, p0, Landroidx/viewpager2/widget/ViewPager2;->mAccessibilityProvider:Landroidx/viewpager2/widget/ViewPager2$PageAwareAccessibilityProvider;

    .line 10
    .line 11
    invoke-virtual {p0}, Landroidx/viewpager2/widget/ViewPager2$PageAwareAccessibilityProvider;->updatePageAccessibilityActions()V

    .line 12
    .line 13
    .line 14
    :cond_0
    return-void
.end method
