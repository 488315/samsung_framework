.class public final Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroidx/appcompat/view/menu/MenuPresenter;


# instance fields
.field public mCurrentExpandedItem:Landroidx/appcompat/view/menu/MenuItemImpl;

.field public mMenu:Landroidx/appcompat/view/menu/MenuBuilder;

.field public final synthetic this$0:Landroidx/appcompat/widget/Toolbar;


# direct methods
.method public constructor <init>(Landroidx/appcompat/widget/Toolbar;)V
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->this$0:Landroidx/appcompat/widget/Toolbar;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final collapseItemActionView(Landroidx/appcompat/view/menu/MenuItemImpl;)Z
    .locals 4

    .line 1
    iget-object v0, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->this$0:Landroidx/appcompat/widget/Toolbar;

    .line 2
    .line 3
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 4
    .line 5
    instance-of v2, v1, Landroidx/appcompat/view/CollapsibleActionView;

    .line 6
    .line 7
    if-eqz v2, :cond_0

    .line 8
    .line 9
    check-cast v1, Landroidx/appcompat/view/CollapsibleActionView;

    .line 10
    .line 11
    invoke-interface {v1}, Landroidx/appcompat/view/CollapsibleActionView;->onActionViewCollapsed()V

    .line 12
    .line 13
    .line 14
    :cond_0
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 15
    .line 16
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 17
    .line 18
    .line 19
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 20
    .line 21
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 22
    .line 23
    .line 24
    const/4 v1, 0x0

    .line 25
    iput-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 26
    .line 27
    iget-object v2, v0, Landroidx/appcompat/widget/Toolbar;->mHiddenViews:Ljava/util/ArrayList;

    .line 28
    .line 29
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    .line 30
    .line 31
    .line 32
    move-result v2

    .line 33
    :goto_0
    add-int/lit8 v2, v2, -0x1

    .line 34
    .line 35
    if-ltz v2, :cond_1

    .line 36
    .line 37
    iget-object v3, v0, Landroidx/appcompat/widget/Toolbar;->mHiddenViews:Ljava/util/ArrayList;

    .line 38
    .line 39
    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 40
    .line 41
    .line 42
    move-result-object v3

    .line 43
    check-cast v3, Landroid/view/View;

    .line 44
    .line 45
    invoke-virtual {v0, v3}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    .line 46
    .line 47
    .line 48
    goto :goto_0

    .line 49
    :cond_1
    iget-object v2, v0, Landroidx/appcompat/widget/Toolbar;->mHiddenViews:Ljava/util/ArrayList;

    .line 50
    .line 51
    invoke-virtual {v2}, Ljava/util/ArrayList;->clear()V

    .line 52
    .line 53
    .line 54
    iput-object v1, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mCurrentExpandedItem:Landroidx/appcompat/view/menu/MenuItemImpl;

    .line 55
    .line 56
    invoke-virtual {v0}, Landroid/view/ViewGroup;->requestLayout()V

    .line 57
    .line 58
    .line 59
    const/4 p0, 0x0

    .line 60
    iput-boolean p0, p1, Landroidx/appcompat/view/menu/MenuItemImpl;->mIsActionViewExpanded:Z

    .line 61
    .line 62
    iget-object p1, p1, Landroidx/appcompat/view/menu/MenuItemImpl;->mMenu:Landroidx/appcompat/view/menu/MenuBuilder;

    .line 63
    .line 64
    invoke-virtual {p1, p0}, Landroidx/appcompat/view/menu/MenuBuilder;->onItemsChanged(Z)V

    .line 65
    .line 66
    .line 67
    invoke-virtual {v0}, Landroidx/appcompat/widget/Toolbar;->updateBackInvokedCallbackState()V

    .line 68
    .line 69
    .line 70
    const/4 p0, 0x1

    .line 71
    return p0
.end method

.method public final expandItemActionView(Landroidx/appcompat/view/menu/MenuItemImpl;)Z
    .locals 7

    .line 1
    iget-object v0, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->this$0:Landroidx/appcompat/widget/Toolbar;

    .line 2
    .line 3
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 4
    .line 5
    const v2, 0x800003

    .line 6
    .line 7
    .line 8
    const/4 v3, 0x2

    .line 9
    if-nez v1, :cond_0

    .line 10
    .line 11
    new-instance v1, Landroidx/appcompat/widget/AppCompatImageButton;

    .line 12
    .line 13
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getContext()Landroid/content/Context;

    .line 14
    .line 15
    .line 16
    move-result-object v4

    .line 17
    const/4 v5, 0x0

    .line 18
    const v6, 0x7f040711

    .line 19
    .line 20
    .line 21
    invoke-direct {v1, v4, v5, v6}, Landroidx/appcompat/widget/AppCompatImageButton;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 22
    .line 23
    .line 24
    iput-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 25
    .line 26
    iget-object v4, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseIcon:Landroid/graphics/drawable/Drawable;

    .line 27
    .line 28
    invoke-virtual {v1, v4}, Landroidx/appcompat/widget/AppCompatImageButton;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 29
    .line 30
    .line 31
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 32
    .line 33
    iget-object v4, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseDescription:Ljava/lang/CharSequence;

    .line 34
    .line 35
    invoke-virtual {v1, v4}, Landroid/widget/ImageButton;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 36
    .line 37
    .line 38
    invoke-static {}, Landroidx/appcompat/widget/Toolbar;->generateDefaultLayoutParams()Landroidx/appcompat/widget/Toolbar$LayoutParams;

    .line 39
    .line 40
    .line 41
    move-result-object v1

    .line 42
    iget v4, v0, Landroidx/appcompat/widget/Toolbar;->mButtonGravity:I

    .line 43
    .line 44
    and-int/lit8 v4, v4, 0x70

    .line 45
    .line 46
    or-int/2addr v4, v2

    .line 47
    iput v4, v1, Landroidx/appcompat/app/ActionBar$LayoutParams;->gravity:I

    .line 48
    .line 49
    iput v3, v1, Landroidx/appcompat/widget/Toolbar$LayoutParams;->mViewType:I

    .line 50
    .line 51
    iget-object v4, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 52
    .line 53
    invoke-virtual {v4, v1}, Landroid/widget/ImageButton;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 54
    .line 55
    .line 56
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 57
    .line 58
    new-instance v4, Landroidx/appcompat/widget/Toolbar$4;

    .line 59
    .line 60
    invoke-direct {v4, v0}, Landroidx/appcompat/widget/Toolbar$4;-><init>(Landroidx/appcompat/widget/Toolbar;)V

    .line 61
    .line 62
    .line 63
    invoke-virtual {v1, v4}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 64
    .line 65
    .line 66
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 67
    .line 68
    invoke-static {}, Landroidx/reflect/widget/SeslHoverPopupWindowReflector;->getField_TYPE_NONE()I

    .line 69
    .line 70
    .line 71
    move-result v4

    .line 72
    invoke-static {v1, v4}, Landroidx/reflect/view/SeslViewReflector;->semSetHoverPopupType(Landroid/view/View;I)V

    .line 73
    .line 74
    .line 75
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseDescription:Ljava/lang/CharSequence;

    .line 76
    .line 77
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    .line 78
    .line 79
    .line 80
    move-result v1

    .line 81
    if-nez v1, :cond_0

    .line 82
    .line 83
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 84
    .line 85
    iget-object v4, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseDescription:Ljava/lang/CharSequence;

    .line 86
    .line 87
    invoke-virtual {v1, v4}, Landroid/view/View;->setTooltipText(Ljava/lang/CharSequence;)V

    .line 88
    .line 89
    .line 90
    :cond_0
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 91
    .line 92
    invoke-virtual {v1}, Landroid/widget/ImageButton;->getParent()Landroid/view/ViewParent;

    .line 93
    .line 94
    .line 95
    move-result-object v1

    .line 96
    if-eq v1, v0, :cond_2

    .line 97
    .line 98
    instance-of v4, v1, Landroid/view/ViewGroup;

    .line 99
    .line 100
    if-eqz v4, :cond_1

    .line 101
    .line 102
    check-cast v1, Landroid/view/ViewGroup;

    .line 103
    .line 104
    iget-object v4, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 105
    .line 106
    invoke-virtual {v1, v4}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 107
    .line 108
    .line 109
    :cond_1
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mCollapseButtonView:Landroidx/appcompat/widget/AppCompatImageButton;

    .line 110
    .line 111
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    .line 112
    .line 113
    .line 114
    :cond_2
    invoke-virtual {p1}, Landroidx/appcompat/view/menu/MenuItemImpl;->getActionView()Landroid/view/View;

    .line 115
    .line 116
    .line 117
    move-result-object v1

    .line 118
    iput-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 119
    .line 120
    iput-object p1, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mCurrentExpandedItem:Landroidx/appcompat/view/menu/MenuItemImpl;

    .line 121
    .line 122
    invoke-virtual {v1}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    .line 123
    .line 124
    .line 125
    move-result-object p0

    .line 126
    if-eq p0, v0, :cond_4

    .line 127
    .line 128
    instance-of v1, p0, Landroid/view/ViewGroup;

    .line 129
    .line 130
    if-eqz v1, :cond_3

    .line 131
    .line 132
    check-cast p0, Landroid/view/ViewGroup;

    .line 133
    .line 134
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 135
    .line 136
    invoke-virtual {p0, v1}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 137
    .line 138
    .line 139
    :cond_3
    invoke-static {}, Landroidx/appcompat/widget/Toolbar;->generateDefaultLayoutParams()Landroidx/appcompat/widget/Toolbar$LayoutParams;

    .line 140
    .line 141
    .line 142
    move-result-object p0

    .line 143
    iget v1, v0, Landroidx/appcompat/widget/Toolbar;->mButtonGravity:I

    .line 144
    .line 145
    and-int/lit8 v1, v1, 0x70

    .line 146
    .line 147
    or-int/2addr v1, v2

    .line 148
    iput v1, p0, Landroidx/appcompat/app/ActionBar$LayoutParams;->gravity:I

    .line 149
    .line 150
    iput v3, p0, Landroidx/appcompat/widget/Toolbar$LayoutParams;->mViewType:I

    .line 151
    .line 152
    iget-object v1, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 153
    .line 154
    invoke-virtual {v1, p0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 155
    .line 156
    .line 157
    iget-object p0, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 158
    .line 159
    invoke-virtual {v0, p0}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    .line 160
    .line 161
    .line 162
    :cond_4
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getChildCount()I

    .line 163
    .line 164
    .line 165
    move-result p0

    .line 166
    :cond_5
    :goto_0
    add-int/lit8 p0, p0, -0x1

    .line 167
    .line 168
    if-ltz p0, :cond_6

    .line 169
    .line 170
    invoke-virtual {v0, p0}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    .line 171
    .line 172
    .line 173
    move-result-object v1

    .line 174
    invoke-virtual {v1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    .line 175
    .line 176
    .line 177
    move-result-object v2

    .line 178
    check-cast v2, Landroidx/appcompat/widget/Toolbar$LayoutParams;

    .line 179
    .line 180
    iget v2, v2, Landroidx/appcompat/widget/Toolbar$LayoutParams;->mViewType:I

    .line 181
    .line 182
    if-eq v2, v3, :cond_5

    .line 183
    .line 184
    iget-object v2, v0, Landroidx/appcompat/widget/Toolbar;->mMenuView:Landroidx/appcompat/widget/ActionMenuView;

    .line 185
    .line 186
    if-eq v1, v2, :cond_5

    .line 187
    .line 188
    invoke-virtual {v0, p0}, Landroid/view/ViewGroup;->removeViewAt(I)V

    .line 189
    .line 190
    .line 191
    iget-object v2, v0, Landroidx/appcompat/widget/Toolbar;->mHiddenViews:Ljava/util/ArrayList;

    .line 192
    .line 193
    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 194
    .line 195
    .line 196
    goto :goto_0

    .line 197
    :cond_6
    invoke-virtual {v0}, Landroid/view/ViewGroup;->requestLayout()V

    .line 198
    .line 199
    .line 200
    const/4 p0, 0x1

    .line 201
    iput-boolean p0, p1, Landroidx/appcompat/view/menu/MenuItemImpl;->mIsActionViewExpanded:Z

    .line 202
    .line 203
    iget-object p1, p1, Landroidx/appcompat/view/menu/MenuItemImpl;->mMenu:Landroidx/appcompat/view/menu/MenuBuilder;

    .line 204
    .line 205
    const/4 v1, 0x0

    .line 206
    invoke-virtual {p1, v1}, Landroidx/appcompat/view/menu/MenuBuilder;->onItemsChanged(Z)V

    .line 207
    .line 208
    .line 209
    iget-object p1, v0, Landroidx/appcompat/widget/Toolbar;->mExpandedActionView:Landroid/view/View;

    .line 210
    .line 211
    instance-of v1, p1, Landroidx/appcompat/view/CollapsibleActionView;

    .line 212
    .line 213
    if-eqz v1, :cond_7

    .line 214
    .line 215
    check-cast p1, Landroidx/appcompat/view/CollapsibleActionView;

    .line 216
    .line 217
    invoke-interface {p1}, Landroidx/appcompat/view/CollapsibleActionView;->onActionViewExpanded()V

    .line 218
    .line 219
    .line 220
    :cond_7
    invoke-virtual {v0}, Landroidx/appcompat/widget/Toolbar;->updateBackInvokedCallbackState()V

    .line 221
    .line 222
    .line 223
    return p0
.end method

.method public final flagActionItems()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final getId()I
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final initForMenu(Landroid/content/Context;Landroidx/appcompat/view/menu/MenuBuilder;)V
    .locals 1

    .line 1
    iget-object p1, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mMenu:Landroidx/appcompat/view/menu/MenuBuilder;

    .line 2
    .line 3
    if-eqz p1, :cond_0

    .line 4
    .line 5
    iget-object v0, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mCurrentExpandedItem:Landroidx/appcompat/view/menu/MenuItemImpl;

    .line 6
    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    invoke-virtual {p1, v0}, Landroidx/appcompat/view/menu/MenuBuilder;->collapseItemActionView(Landroidx/appcompat/view/menu/MenuItemImpl;)Z

    .line 10
    .line 11
    .line 12
    :cond_0
    iput-object p2, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mMenu:Landroidx/appcompat/view/menu/MenuBuilder;

    .line 13
    .line 14
    return-void
.end method

.method public final onCloseMenu(Landroidx/appcompat/view/menu/MenuBuilder;Z)V
    .locals 0

    .line 1
    return-void
.end method

.method public final onRestoreInstanceState(Landroid/os/Parcelable;)V
    .locals 0

    .line 1
    return-void
.end method

.method public final onSaveInstanceState()Landroid/os/Parcelable;
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return-object p0
.end method

.method public final onSubMenuSelected(Landroidx/appcompat/view/menu/SubMenuBuilder;)Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final updateMenuView(Z)V
    .locals 4

    .line 1
    iget-object p1, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mCurrentExpandedItem:Landroidx/appcompat/view/menu/MenuItemImpl;

    .line 2
    .line 3
    if-eqz p1, :cond_2

    .line 4
    .line 5
    iget-object p1, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mMenu:Landroidx/appcompat/view/menu/MenuBuilder;

    .line 6
    .line 7
    const/4 v0, 0x0

    .line 8
    if-eqz p1, :cond_1

    .line 9
    .line 10
    invoke-virtual {p1}, Landroidx/appcompat/view/menu/MenuBuilder;->size()I

    .line 11
    .line 12
    .line 13
    move-result p1

    .line 14
    move v1, v0

    .line 15
    :goto_0
    if-ge v1, p1, :cond_1

    .line 16
    .line 17
    iget-object v2, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mMenu:Landroidx/appcompat/view/menu/MenuBuilder;

    .line 18
    .line 19
    invoke-virtual {v2, v1}, Landroidx/appcompat/view/menu/MenuBuilder;->getItem(I)Landroid/view/MenuItem;

    .line 20
    .line 21
    .line 22
    move-result-object v2

    .line 23
    iget-object v3, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mCurrentExpandedItem:Landroidx/appcompat/view/menu/MenuItemImpl;

    .line 24
    .line 25
    if-ne v2, v3, :cond_0

    .line 26
    .line 27
    const/4 v0, 0x1

    .line 28
    goto :goto_1

    .line 29
    :cond_0
    add-int/lit8 v1, v1, 0x1

    .line 30
    .line 31
    goto :goto_0

    .line 32
    :cond_1
    :goto_1
    if-nez v0, :cond_2

    .line 33
    .line 34
    iget-object p1, p0, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->mCurrentExpandedItem:Landroidx/appcompat/view/menu/MenuItemImpl;

    .line 35
    .line 36
    invoke-virtual {p0, p1}, Landroidx/appcompat/widget/Toolbar$ExpandedActionViewMenuPresenter;->collapseItemActionView(Landroidx/appcompat/view/menu/MenuItemImpl;)Z

    .line 37
    .line 38
    .line 39
    :cond_2
    return-void
.end method
