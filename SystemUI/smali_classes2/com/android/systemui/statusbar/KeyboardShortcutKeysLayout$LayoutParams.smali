.class public final Lcom/android/systemui/statusbar/KeyboardShortcutKeysLayout$LayoutParams;
.super Landroid/view/ViewGroup$LayoutParams;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mHorizontalSpacing:I

.field public final mVerticalSpacing:I


# direct methods
.method public constructor <init>(II)V
    .locals 1

    const/4 v0, 0x0

    .line 4
    invoke-direct {p0, v0, v0}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    .line 5
    iput p1, p0, Lcom/android/systemui/statusbar/KeyboardShortcutKeysLayout$LayoutParams;->mHorizontalSpacing:I

    .line 6
    iput p2, p0, Lcom/android/systemui/statusbar/KeyboardShortcutKeysLayout$LayoutParams;->mVerticalSpacing:I

    return-void
.end method

.method public constructor <init>(IILandroid/view/ViewGroup$LayoutParams;)V
    .locals 0

    .line 1
    invoke-direct {p0, p3}, Landroid/view/ViewGroup$LayoutParams;-><init>(Landroid/view/ViewGroup$LayoutParams;)V

    .line 2
    iput p1, p0, Lcom/android/systemui/statusbar/KeyboardShortcutKeysLayout$LayoutParams;->mHorizontalSpacing:I

    .line 3
    iput p2, p0, Lcom/android/systemui/statusbar/KeyboardShortcutKeysLayout$LayoutParams;->mVerticalSpacing:I

    return-void
.end method
