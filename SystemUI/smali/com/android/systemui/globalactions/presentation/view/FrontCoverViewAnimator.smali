.class public final Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/samsung/android/globalactions/presentation/view/GlobalActionsAnimator;


# instance fields
.field public mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

.field public final mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

.field public mConfirmDescriptionView:Landroid/widget/TextView;

.field public mConfirmIconLabelView:Landroid/view/ViewGroup;

.field public mConfirmView:Landroid/view/ViewGroup;

.field public final mContext:Landroid/content/Context;

.field public final mDismissDuringSecureConfirm:Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$1;

.field public final mHandler:Lcom/samsung/android/globalactions/util/HandlerUtil;

.field public mOriginalConfirmLocationX:F

.field public mOriginalConfirmLocationY:F

.field public final mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

.field public mRootView:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$RootView;

.field public mSelectedActionView:Landroid/view/ViewGroup;

.field public final mViewStateController:Lcom/samsung/android/globalactions/presentation/view/ViewStateController;

.field public mViewTreeObserverListener:Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$$ExternalSyntheticLambda1;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/samsung/android/globalactions/util/ConditionChecker;Lcom/samsung/android/globalactions/util/LogWrapper;Lcom/samsung/android/globalactions/util/HandlerUtil;Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;Lcom/samsung/android/globalactions/presentation/view/ViewStateController;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    new-instance p3, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$1;

    .line 5
    .line 6
    invoke-direct {p3, p0}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$1;-><init>(Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;)V

    .line 7
    .line 8
    .line 9
    iput-object p3, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mDismissDuringSecureConfirm:Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$1;

    .line 10
    .line 11
    iput-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mContext:Landroid/content/Context;

    .line 12
    .line 13
    iput-object p2, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 14
    .line 15
    iput-object p4, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mHandler:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 16
    .line 17
    iput-object p5, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 18
    .line 19
    iput-object p6, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewStateController:Lcom/samsung/android/globalactions/presentation/view/ViewStateController;

    .line 20
    .line 21
    return-void
.end method


# virtual methods
.method public final cancelHideConfirmAnimation()V
    .locals 0

    .line 1
    return-void
.end method

.method public final cancelShowConfirmAnimation()V
    .locals 0

    .line 1
    return-void
.end method

.method public final getDefaultConfirmAnimatorSet(Z)Landroid/animation/AnimatorSet;
    .locals 14

    .line 1
    new-instance v0, Landroid/animation/AnimatorSet;

    .line 2
    .line 3
    invoke-direct {v0}, Landroid/animation/AnimatorSet;-><init>()V

    .line 4
    .line 5
    .line 6
    iget-object v1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmIconLabelView:Landroid/view/ViewGroup;

    .line 7
    .line 8
    const/4 v2, 0x2

    .line 9
    new-array v3, v2, [F

    .line 10
    .line 11
    invoke-virtual {v1}, Landroid/view/ViewGroup;->getY()F

    .line 12
    .line 13
    .line 14
    move-result v4

    .line 15
    const/4 v5, 0x0

    .line 16
    aput v4, v3, v5

    .line 17
    .line 18
    const/4 v4, 0x1

    .line 19
    if-eqz p1, :cond_0

    .line 20
    .line 21
    iget v6, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mOriginalConfirmLocationY:F

    .line 22
    .line 23
    goto :goto_0

    .line 24
    :cond_0
    iget-object v6, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mSelectedActionView:Landroid/view/ViewGroup;

    .line 25
    .line 26
    new-array v7, v2, [I

    .line 27
    .line 28
    invoke-virtual {v6, v7}, Landroid/view/View;->getLocationOnScreen([I)V

    .line 29
    .line 30
    .line 31
    aget v6, v7, v4

    .line 32
    .line 33
    int-to-float v6, v6

    .line 34
    :goto_0
    aput v6, v3, v4

    .line 35
    .line 36
    const-string/jumbo v6, "y"

    .line 37
    .line 38
    .line 39
    invoke-static {v1, v6, v3}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    .line 40
    .line 41
    .line 42
    move-result-object v1

    .line 43
    iget-object v3, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmIconLabelView:Landroid/view/ViewGroup;

    .line 44
    .line 45
    new-array v6, v2, [F

    .line 46
    .line 47
    invoke-virtual {v3}, Landroid/view/ViewGroup;->getX()F

    .line 48
    .line 49
    .line 50
    move-result v7

    .line 51
    aput v7, v6, v5

    .line 52
    .line 53
    if-eqz p1, :cond_1

    .line 54
    .line 55
    iget v7, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mOriginalConfirmLocationX:F

    .line 56
    .line 57
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mRootView:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$RootView;

    .line 58
    .line 59
    invoke-virtual {v8}, Landroid/view/ViewGroup;->getPaddingLeft()I

    .line 60
    .line 61
    .line 62
    move-result v8

    .line 63
    int-to-float v8, v8

    .line 64
    sub-float/2addr v7, v8

    .line 65
    goto :goto_1

    .line 66
    :cond_1
    iget-object v7, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 67
    .line 68
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mSelectedActionView:Landroid/view/ViewGroup;

    .line 69
    .line 70
    invoke-virtual {v7, v8}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->getConfirmIconLabelView(Landroid/view/ViewGroup;)Landroid/view/ViewGroup;

    .line 71
    .line 72
    .line 73
    move-result-object v7

    .line 74
    new-array v8, v2, [I

    .line 75
    .line 76
    invoke-virtual {v7, v8}, Landroid/view/View;->getLocationOnScreen([I)V

    .line 77
    .line 78
    .line 79
    aget v7, v8, v5

    .line 80
    .line 81
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mRootView:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$RootView;

    .line 82
    .line 83
    invoke-virtual {v8}, Landroid/view/ViewGroup;->getPaddingLeft()I

    .line 84
    .line 85
    .line 86
    move-result v8

    .line 87
    sub-int/2addr v7, v8

    .line 88
    int-to-float v7, v7

    .line 89
    :goto_1
    aput v7, v6, v4

    .line 90
    .line 91
    const-string/jumbo v7, "x"

    .line 92
    .line 93
    .line 94
    invoke-static {v3, v7, v6}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    .line 95
    .line 96
    .line 97
    move-result-object v3

    .line 98
    iget-object v6, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 99
    .line 100
    new-array v7, v2, [F

    .line 101
    .line 102
    const/4 v8, 0x0

    .line 103
    const/high16 v9, 0x3f800000    # 1.0f

    .line 104
    .line 105
    if-eqz p1, :cond_2

    .line 106
    .line 107
    move v10, v8

    .line 108
    goto :goto_2

    .line 109
    :cond_2
    move v10, v9

    .line 110
    :goto_2
    aput v10, v7, v5

    .line 111
    .line 112
    if-eqz p1, :cond_3

    .line 113
    .line 114
    move v8, v9

    .line 115
    :cond_3
    aput v8, v7, v4

    .line 116
    .line 117
    const-string v8, "alpha"

    .line 118
    .line 119
    invoke-static {v6, v8, v7}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    .line 120
    .line 121
    .line 122
    move-result-object v6

    .line 123
    const-wide/16 v7, 0xc8

    .line 124
    .line 125
    invoke-virtual {v6, v7, v8}, Landroid/animation/ObjectAnimator;->setDuration(J)Landroid/animation/ObjectAnimator;

    .line 126
    .line 127
    .line 128
    iget-object v10, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 129
    .line 130
    iget-object v10, v10, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 131
    .line 132
    iget-boolean v10, v10, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mIsIconOnly:Z

    .line 133
    .line 134
    if-eqz v10, :cond_6

    .line 135
    .line 136
    iget-object v10, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmIconLabelView:Landroid/view/ViewGroup;

    .line 137
    .line 138
    new-array v11, v2, [F

    .line 139
    .line 140
    invoke-virtual {v10}, Landroid/view/ViewGroup;->getScaleX()F

    .line 141
    .line 142
    .line 143
    move-result v12

    .line 144
    aput v12, v11, v5

    .line 145
    .line 146
    const/high16 v12, 0x3f400000    # 0.75f

    .line 147
    .line 148
    if-eqz p1, :cond_4

    .line 149
    .line 150
    move v13, v12

    .line 151
    goto :goto_3

    .line 152
    :cond_4
    move v13, v9

    .line 153
    :goto_3
    aput v13, v11, v4

    .line 154
    .line 155
    const-string/jumbo v13, "scaleX"

    .line 156
    .line 157
    .line 158
    invoke-static {v10, v13, v11}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    .line 159
    .line 160
    .line 161
    move-result-object v10

    .line 162
    iget-object v11, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmIconLabelView:Landroid/view/ViewGroup;

    .line 163
    .line 164
    new-array v2, v2, [F

    .line 165
    .line 166
    invoke-virtual {v11}, Landroid/view/ViewGroup;->getScaleY()F

    .line 167
    .line 168
    .line 169
    move-result v13

    .line 170
    aput v13, v2, v5

    .line 171
    .line 172
    if-eqz p1, :cond_5

    .line 173
    .line 174
    move v9, v12

    .line 175
    :cond_5
    aput v9, v2, v4

    .line 176
    .line 177
    const-string/jumbo v4, "scaleY"

    .line 178
    .line 179
    .line 180
    invoke-static {v11, v4, v2}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    .line 181
    .line 182
    .line 183
    move-result-object v2

    .line 184
    filled-new-array {v1, v3, v6, v10, v2}, [Landroid/animation/Animator;

    .line 185
    .line 186
    .line 187
    move-result-object v1

    .line 188
    invoke-virtual {v0, v1}, Landroid/animation/AnimatorSet;->playTogether([Landroid/animation/Animator;)V

    .line 189
    .line 190
    .line 191
    goto :goto_4

    .line 192
    :cond_6
    filled-new-array {v1, v3, v6}, [Landroid/animation/Animator;

    .line 193
    .line 194
    .line 195
    move-result-object v1

    .line 196
    invoke-virtual {v0, v1}, Landroid/animation/AnimatorSet;->playTogether([Landroid/animation/Animator;)V

    .line 197
    .line 198
    .line 199
    :goto_4
    new-instance v1, Landroid/view/animation/DecelerateInterpolator;

    .line 200
    .line 201
    invoke-direct {v1}, Landroid/view/animation/DecelerateInterpolator;-><init>()V

    .line 202
    .line 203
    .line 204
    invoke-virtual {v0, v1}, Landroid/animation/AnimatorSet;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 205
    .line 206
    .line 207
    invoke-virtual {v0, v7, v8}, Landroid/animation/AnimatorSet;->setDuration(J)Landroid/animation/AnimatorSet;

    .line 208
    .line 209
    .line 210
    if-eqz p1, :cond_7

    .line 211
    .line 212
    new-instance p1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$2;

    .line 213
    .line 214
    invoke-direct {p1, p0}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$2;-><init>(Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;)V

    .line 215
    .line 216
    .line 217
    invoke-virtual {v0, p1}, Landroid/animation/AnimatorSet;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 218
    .line 219
    .line 220
    goto :goto_5

    .line 221
    :cond_7
    new-instance p1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$3;

    .line 222
    .line 223
    invoke-direct {p1, p0}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$3;-><init>(Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;)V

    .line 224
    .line 225
    .line 226
    invoke-virtual {v0, p1}, Landroid/animation/AnimatorSet;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 227
    .line 228
    .line 229
    :goto_5
    return-object v0
.end method

.method public final initializeSelectedActionView()V
    .locals 0

    .line 1
    return-void
.end method

.method public final isHideConfirmAnimationRunning()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final isSafeModeConfirm()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final isShowConfirmAnimationRunning()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public final setListViewLand()V
    .locals 0

    .line 1
    return-void
.end method

.method public final setListViewPort()V
    .locals 0

    .line 1
    return-void
.end method

.method public final showMainViewLand()V
    .locals 0

    .line 1
    return-void
.end method

.method public final showMainViewPort()V
    .locals 0

    .line 1
    return-void
.end method

.method public final startDismissAnimation(Z)V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewStateController:Lcom/samsung/android/globalactions/presentation/view/ViewStateController;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;->DISMISS_ANIMATE:Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;

    .line 4
    .line 5
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/presentation/view/ViewStateController;->setState(Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;)V

    .line 6
    .line 7
    .line 8
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 9
    .line 10
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 11
    .line 12
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mAdapter:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$ContentAdapter;

    .line 13
    .line 14
    const/4 v2, 0x0

    .line 15
    iput-boolean v2, v1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$ContentAdapter;->mIsConfirmView:Z

    .line 16
    .line 17
    if-eqz p1, :cond_3

    .line 18
    .line 19
    iget-object p1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 20
    .line 21
    const/4 v1, 0x1

    .line 22
    invoke-interface {p1, v1}, Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;->setCoverSecureConfirmState(Z)V

    .line 23
    .line 24
    .line 25
    iput-boolean v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mForceDismiss:Z

    .line 26
    .line 27
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mRootView:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$RootView;

    .line 28
    .line 29
    const v0, 0x7f0a0426

    .line 30
    .line 31
    .line 32
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 33
    .line 34
    .line 35
    move-result-object p1

    .line 36
    const/16 v0, 0x8

    .line 37
    .line 38
    invoke-virtual {p1, v0}, Landroid/view/View;->setVisibility(I)V

    .line 39
    .line 40
    .line 41
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmView:Landroid/view/ViewGroup;

    .line 42
    .line 43
    iget-object v1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 44
    .line 45
    sget-object v3, Lcom/samsung/android/globalactions/presentation/view/ResourceType;->ID_COVER_BTN_BACKGROUND:Lcom/samsung/android/globalactions/presentation/view/ResourceType;

    .line 46
    .line 47
    invoke-interface {v1, v3}, Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;->get(Lcom/samsung/android/globalactions/presentation/view/ResourceType;)I

    .line 48
    .line 49
    .line 50
    move-result v1

    .line 51
    invoke-virtual {p1, v1}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 52
    .line 53
    .line 54
    move-result-object p1

    .line 55
    check-cast p1, Landroid/view/ViewGroup;

    .line 56
    .line 57
    iput-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmIconLabelView:Landroid/view/ViewGroup;

    .line 58
    .line 59
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 60
    .line 61
    .line 62
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmView:Landroid/view/ViewGroup;

    .line 63
    .line 64
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 65
    .line 66
    sget-object v1, Lcom/samsung/android/globalactions/presentation/view/ResourceType;->ID_COVER_TEXT:Lcom/samsung/android/globalactions/presentation/view/ResourceType;

    .line 67
    .line 68
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;->get(Lcom/samsung/android/globalactions/presentation/view/ResourceType;)I

    .line 69
    .line 70
    .line 71
    move-result v0

    .line 72
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 73
    .line 74
    .line 75
    move-result-object p1

    .line 76
    check-cast p1, Landroid/widget/TextView;

    .line 77
    .line 78
    iput-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 79
    .line 80
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 81
    .line 82
    sget-object v0, Lcom/android/systemui/globalactions/util/SystemUIConditions;->IS_CLEAR_CAMERA_VIEW_COVER_CLOSED:Lcom/android/systemui/globalactions/util/SystemUIConditions;

    .line 83
    .line 84
    invoke-interface {p1, v0}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 85
    .line 86
    .line 87
    move-result p1

    .line 88
    if-eqz p1, :cond_1

    .line 89
    .line 90
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mContext:Landroid/content/Context;

    .line 91
    .line 92
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 93
    .line 94
    .line 95
    move-result-object p1

    .line 96
    const v0, 0x7f0703b0

    .line 97
    .line 98
    .line 99
    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 100
    .line 101
    .line 102
    move-result p1

    .line 103
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 104
    .line 105
    invoke-virtual {v0, p1, v2, p1, v2}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 106
    .line 107
    .line 108
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 109
    .line 110
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mContext:Landroid/content/Context;

    .line 111
    .line 112
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 113
    .line 114
    .line 115
    move-result-object v0

    .line 116
    const v1, 0x7f0703af

    .line 117
    .line 118
    .line 119
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelOffset(I)I

    .line 120
    .line 121
    .line 122
    move-result v0

    .line 123
    int-to-float v0, v0

    .line 124
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextSize(F)V

    .line 125
    .line 126
    .line 127
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 128
    .line 129
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mContext:Landroid/content/Context;

    .line 130
    .line 131
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 132
    .line 133
    .line 134
    move-result-object v0

    .line 135
    const v1, 0x7f060582

    .line 136
    .line 137
    .line 138
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    .line 139
    .line 140
    .line 141
    move-result v0

    .line 142
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setTextColor(I)V

    .line 143
    .line 144
    .line 145
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 146
    .line 147
    iget-object p1, p1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 148
    .line 149
    iget-object p1, p1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mSelectedViewModel:Lcom/samsung/android/globalactions/presentation/viewmodel/ActionViewModel;

    .line 150
    .line 151
    invoke-interface {p1}, Lcom/samsung/android/globalactions/presentation/viewmodel/ActionViewModel;->getActionInfo()Lcom/samsung/android/globalactions/presentation/viewmodel/ActionInfo;

    .line 152
    .line 153
    .line 154
    move-result-object p1

    .line 155
    invoke-virtual {p1}, Lcom/samsung/android/globalactions/presentation/viewmodel/ActionInfo;->getName()Ljava/lang/String;

    .line 156
    .line 157
    .line 158
    move-result-object p1

    .line 159
    const-string/jumbo v0, "power"

    .line 160
    .line 161
    .line 162
    if-ne p1, v0, :cond_0

    .line 163
    .line 164
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 165
    .line 166
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mContext:Landroid/content/Context;

    .line 167
    .line 168
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 169
    .line 170
    .line 171
    move-result-object v0

    .line 172
    const v1, 0x10405bd

    .line 173
    .line 174
    .line 175
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 176
    .line 177
    .line 178
    move-result-object v0

    .line 179
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 180
    .line 181
    .line 182
    goto :goto_0

    .line 183
    :cond_0
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 184
    .line 185
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mContext:Landroid/content/Context;

    .line 186
    .line 187
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 188
    .line 189
    .line 190
    move-result-object v0

    .line 191
    const v1, 0x10405be

    .line 192
    .line 193
    .line 194
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 195
    .line 196
    .line 197
    move-result-object v0

    .line 198
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 199
    .line 200
    .line 201
    goto :goto_0

    .line 202
    :cond_1
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 203
    .line 204
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mContext:Landroid/content/Context;

    .line 205
    .line 206
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 207
    .line 208
    .line 209
    move-result-object v0

    .line 210
    const v1, 0x10405bf

    .line 211
    .line 212
    .line 213
    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    .line 214
    .line 215
    .line 216
    move-result-object v0

    .line 217
    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 218
    .line 219
    .line 220
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 221
    .line 222
    iget-object p1, p1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 223
    .line 224
    iget-boolean p1, p1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mIsIconOnly:Z

    .line 225
    .line 226
    if-nez p1, :cond_2

    .line 227
    .line 228
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmView:Landroid/view/ViewGroup;

    .line 229
    .line 230
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 231
    .line 232
    sget-object v1, Lcom/samsung/android/globalactions/presentation/view/ResourceType;->ID_COVER_ANIM:Lcom/samsung/android/globalactions/presentation/view/ResourceType;

    .line 233
    .line 234
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;->get(Lcom/samsung/android/globalactions/presentation/view/ResourceType;)I

    .line 235
    .line 236
    .line 237
    move-result v0

    .line 238
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 239
    .line 240
    .line 241
    move-result-object p1

    .line 242
    invoke-virtual {p1, v2}, Landroid/view/View;->setVisibility(I)V

    .line 243
    .line 244
    .line 245
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmView:Landroid/view/ViewGroup;

    .line 246
    .line 247
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 248
    .line 249
    sget-object v1, Lcom/samsung/android/globalactions/presentation/view/ResourceType;->ID_COVER_TEXT:Lcom/samsung/android/globalactions/presentation/view/ResourceType;

    .line 250
    .line 251
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;->get(Lcom/samsung/android/globalactions/presentation/view/ResourceType;)I

    .line 252
    .line 253
    .line 254
    move-result v0

    .line 255
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 256
    .line 257
    .line 258
    move-result-object p1

    .line 259
    check-cast p1, Landroid/widget/TextView;

    .line 260
    .line 261
    const/high16 v0, 0x41f00000    # 30.0f

    .line 262
    .line 263
    invoke-virtual {p1, v2, v0}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 264
    .line 265
    .line 266
    :cond_2
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mHandler:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 267
    .line 268
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mDismissDuringSecureConfirm:Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$1;

    .line 269
    .line 270
    const-wide/16 v1, 0xbb8

    .line 271
    .line 272
    invoke-virtual {p1, v0, v1, v2}, Lcom/samsung/android/globalactions/util/HandlerUtil;->postDelayed(Ljava/lang/Runnable;J)V

    .line 273
    .line 274
    .line 275
    :goto_0
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mHandler:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 276
    .line 277
    new-instance v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$$ExternalSyntheticLambda0;

    .line 278
    .line 279
    invoke-direct {v0, p0}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;)V

    .line 280
    .line 281
    .line 282
    const-wide/16 v1, 0xc8

    .line 283
    .line 284
    invoke-virtual {p1, v0, v1, v2}, Lcom/samsung/android/globalactions/util/HandlerUtil;->postDelayed(Ljava/lang/Runnable;J)V

    .line 285
    .line 286
    .line 287
    goto :goto_1

    .line 288
    :cond_3
    iget-object p1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 289
    .line 290
    invoke-interface {p1, v2}, Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;->setCoverSecureConfirmState(Z)V

    .line 291
    .line 292
    .line 293
    iput-boolean v2, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mForceDismiss:Z

    .line 294
    .line 295
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mHandler:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 296
    .line 297
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mDismissDuringSecureConfirm:Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$1;

    .line 298
    .line 299
    invoke-virtual {p1, v0}, Lcom/samsung/android/globalactions/util/HandlerUtil;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 300
    .line 301
    .line 302
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewStateController:Lcom/samsung/android/globalactions/presentation/view/ViewStateController;

    .line 303
    .line 304
    sget-object v0, Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;->IDLE:Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;

    .line 305
    .line 306
    invoke-interface {p1, v0}, Lcom/samsung/android/globalactions/presentation/view/ViewStateController;->setState(Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;)V

    .line 307
    .line 308
    .line 309
    iget-object p0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 310
    .line 311
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 312
    .line 313
    .line 314
    iget-object p0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 315
    .line 316
    iget-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 317
    .line 318
    invoke-interface {p1}, Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;->dismiss()V

    .line 319
    .line 320
    .line 321
    iget-object p0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mDialog:Landroid/app/Presentation;

    .line 322
    .line 323
    invoke-virtual {p0}, Landroid/app/Presentation;->dismiss()V

    .line 324
    .line 325
    .line 326
    :goto_1
    return-void
.end method

.method public final startDismissConfirmAnimation()V
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 2
    .line 3
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 4
    .line 5
    iget-boolean v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mForceDismiss:Z

    .line 6
    .line 7
    const/4 v2, 0x0

    .line 8
    if-eqz v1, :cond_0

    .line 9
    .line 10
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 11
    .line 12
    invoke-interface {v1, v2}, Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;->setCoverSecureConfirmState(Z)V

    .line 13
    .line 14
    .line 15
    iput-boolean v2, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mForceDismiss:Z

    .line 16
    .line 17
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 18
    .line 19
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 20
    .line 21
    .line 22
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 23
    .line 24
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 25
    .line 26
    invoke-interface {v1}, Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;->dismiss()V

    .line 27
    .line 28
    .line 29
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mDialog:Landroid/app/Presentation;

    .line 30
    .line 31
    invoke-virtual {v0}, Landroid/app/Presentation;->dismiss()V

    .line 32
    .line 33
    .line 34
    iget-object p0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewStateController:Lcom/samsung/android/globalactions/presentation/view/ViewStateController;

    .line 35
    .line 36
    sget-object v0, Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;->IDLE:Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;

    .line 37
    .line 38
    invoke-interface {p0, v0}, Lcom/samsung/android/globalactions/presentation/view/ViewStateController;->setState(Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;)V

    .line 39
    .line 40
    .line 41
    return-void

    .line 42
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewStateController:Lcom/samsung/android/globalactions/presentation/view/ViewStateController;

    .line 43
    .line 44
    sget-object v1, Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;->DISMISS_ANIMATE:Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;

    .line 45
    .line 46
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/presentation/view/ViewStateController;->setState(Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;)V

    .line 47
    .line 48
    .line 49
    invoke-virtual {p0, v2}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->getDefaultConfirmAnimatorSet(Z)Landroid/animation/AnimatorSet;

    .line 50
    .line 51
    .line 52
    move-result-object p0

    .line 53
    invoke-virtual {p0}, Landroid/animation/AnimatorSet;->start()V

    .line 54
    .line 55
    .line 56
    return-void
.end method

.method public final startDismissSafeModeAnimation()V
    .locals 0

    .line 1
    return-void
.end method

.method public final startSetSafeModeAnimation()V
    .locals 0

    .line 1
    return-void
.end method

.method public final startShowAnimation()V
    .locals 0

    .line 1
    return-void
.end method

.method public final startShowConfirmAnimation()V
    .locals 9

    .line 1
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 2
    .line 3
    new-instance v8, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView;

    .line 4
    .line 5
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 6
    .line 7
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mDialog:Landroid/app/Presentation;

    .line 8
    .line 9
    invoke-virtual {v1}, Landroid/app/Presentation;->getContext()Landroid/content/Context;

    .line 10
    .line 11
    .line 12
    move-result-object v2

    .line 13
    iget-object v3, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mSelectedViewModel:Lcom/samsung/android/globalactions/presentation/viewmodel/ActionViewModel;

    .line 14
    .line 15
    iget-object v4, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mConfirmView:Landroid/view/ViewGroup;

    .line 16
    .line 17
    iget-boolean v5, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mIsIconOnly:Z

    .line 18
    .line 19
    iget-boolean v6, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mIsCameraViewCover:Z

    .line 20
    .line 21
    iget-object v7, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 22
    .line 23
    move-object v1, v8

    .line 24
    invoke-direct/range {v1 .. v7}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView;-><init>(Landroid/content/Context;Lcom/samsung/android/globalactions/presentation/viewmodel/ActionViewModel;Landroid/view/ViewGroup;ZZLcom/samsung/android/globalactions/presentation/view/ResourceFactory;)V

    .line 25
    .line 26
    .line 27
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mConfirmView:Landroid/view/ViewGroup;

    .line 28
    .line 29
    invoke-virtual {v1}, Landroid/view/ViewGroup;->removeAllViews()V

    .line 30
    .line 31
    .line 32
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mConfirmView:Landroid/view/ViewGroup;

    .line 33
    .line 34
    iget-object v2, v8, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView;->mContext:Landroid/content/Context;

    .line 35
    .line 36
    invoke-static {v2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    .line 37
    .line 38
    .line 39
    move-result-object v2

    .line 40
    sget-object v3, Lcom/samsung/android/globalactions/presentation/view/ResourceType;->LAYOUT_FRONT_COVER_ITEM:Lcom/samsung/android/globalactions/presentation/view/ResourceType;

    .line 41
    .line 42
    iget-object v4, v8, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 43
    .line 44
    invoke-interface {v4, v3}, Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;->get(Lcom/samsung/android/globalactions/presentation/view/ResourceType;)I

    .line 45
    .line 46
    .line 47
    move-result v3

    .line 48
    iget-object v5, v8, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView;->mParent:Landroid/view/ViewGroup;

    .line 49
    .line 50
    const/4 v6, 0x0

    .line 51
    invoke-virtual {v2, v3, v5, v6}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    .line 52
    .line 53
    .line 54
    move-result-object v2

    .line 55
    const/4 v3, 0x1

    .line 56
    invoke-virtual {v8, v2, v3}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView;->setViewAttrs(Landroid/view/View;Z)V

    .line 57
    .line 58
    .line 59
    new-instance v3, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView$$ExternalSyntheticLambda1;

    .line 60
    .line 61
    invoke-direct {v3}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentItemView$$ExternalSyntheticLambda1;-><init>()V

    .line 62
    .line 63
    .line 64
    invoke-virtual {v2, v3}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 65
    .line 66
    .line 67
    invoke-virtual {v2, v6}, Landroid/view/View;->setClickable(Z)V

    .line 68
    .line 69
    .line 70
    sget-object v3, Lcom/samsung/android/globalactions/presentation/view/ResourceType;->ID_COVER_TEXT:Lcom/samsung/android/globalactions/presentation/view/ResourceType;

    .line 71
    .line 72
    invoke-interface {v4, v3}, Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;->get(Lcom/samsung/android/globalactions/presentation/view/ResourceType;)I

    .line 73
    .line 74
    .line 75
    move-result v3

    .line 76
    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    .line 77
    .line 78
    .line 79
    move-result-object v3

    .line 80
    check-cast v3, Landroid/widget/TextView;

    .line 81
    .line 82
    invoke-virtual {v3, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 83
    .line 84
    .line 85
    invoke-virtual {v1, v2}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    .line 86
    .line 87
    .line 88
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mConfirmView:Landroid/view/ViewGroup;

    .line 89
    .line 90
    invoke-virtual {v0, v6}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 91
    .line 92
    .line 93
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 94
    .line 95
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 96
    .line 97
    iget-object v1, v1, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mConfirmView:Landroid/view/ViewGroup;

    .line 98
    .line 99
    iput-object v1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmView:Landroid/view/ViewGroup;

    .line 100
    .line 101
    invoke-virtual {v0, v1}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->getConfirmIconLabelView(Landroid/view/ViewGroup;)Landroid/view/ViewGroup;

    .line 102
    .line 103
    .line 104
    move-result-object v0

    .line 105
    iput-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmIconLabelView:Landroid/view/ViewGroup;

    .line 106
    .line 107
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 108
    .line 109
    iget-object v1, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmView:Landroid/view/ViewGroup;

    .line 110
    .line 111
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 112
    .line 113
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 114
    .line 115
    sget-object v2, Lcom/samsung/android/globalactions/presentation/view/ResourceType;->ID_COVER_TEXT:Lcom/samsung/android/globalactions/presentation/view/ResourceType;

    .line 116
    .line 117
    invoke-interface {v0, v2}, Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;->get(Lcom/samsung/android/globalactions/presentation/view/ResourceType;)I

    .line 118
    .line 119
    .line 120
    move-result v0

    .line 121
    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->findViewById(I)Landroid/view/View;

    .line 122
    .line 123
    .line 124
    move-result-object v0

    .line 125
    check-cast v0, Landroid/widget/TextView;

    .line 126
    .line 127
    iput-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmDescriptionView:Landroid/widget/TextView;

    .line 128
    .line 129
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mCallback:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;

    .line 130
    .line 131
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$2;->this$0:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 132
    .line 133
    iget-object v1, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mListView:Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView$ContentGridView;

    .line 134
    .line 135
    iget-object v0, v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;->mSelectedViewModel:Lcom/samsung/android/globalactions/presentation/viewmodel/ActionViewModel;

    .line 136
    .line 137
    invoke-interface {v0}, Lcom/samsung/android/globalactions/presentation/viewmodel/ActionViewModel;->getActionInfo()Lcom/samsung/android/globalactions/presentation/viewmodel/ActionInfo;

    .line 138
    .line 139
    .line 140
    move-result-object v0

    .line 141
    invoke-virtual {v0}, Lcom/samsung/android/globalactions/presentation/viewmodel/ActionInfo;->getViewIndex()I

    .line 142
    .line 143
    .line 144
    move-result v0

    .line 145
    invoke-virtual {v1, v0}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    .line 146
    .line 147
    .line 148
    move-result-object v0

    .line 149
    check-cast v0, Landroid/view/ViewGroup;

    .line 150
    .line 151
    iput-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mSelectedActionView:Landroid/view/ViewGroup;

    .line 152
    .line 153
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewStateController:Lcom/samsung/android/globalactions/presentation/view/ViewStateController;

    .line 154
    .line 155
    sget-object v1, Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;->SHOW_ANIMATE:Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;

    .line 156
    .line 157
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/presentation/view/ViewStateController;->setState(Lcom/samsung/android/globalactions/presentation/view/ViewAnimationState;)V

    .line 158
    .line 159
    .line 160
    new-instance v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$$ExternalSyntheticLambda1;

    .line 161
    .line 162
    invoke-direct {v0, p0}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$$ExternalSyntheticLambda1;-><init>(Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;)V

    .line 163
    .line 164
    .line 165
    iput-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewTreeObserverListener:Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$$ExternalSyntheticLambda1;

    .line 166
    .line 167
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mConfirmView:Landroid/view/ViewGroup;

    .line 168
    .line 169
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    .line 170
    .line 171
    .line 172
    move-result-object v0

    .line 173
    iget-object p0, p0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator;->mViewTreeObserverListener:Lcom/android/systemui/globalactions/presentation/view/FrontCoverViewAnimator$$ExternalSyntheticLambda1;

    .line 174
    .line 175
    invoke-virtual {v0, p0}, Landroid/view/ViewTreeObserver;->addOnGlobalLayoutListener(Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;)V

    .line 176
    .line 177
    .line 178
    return-void
.end method

.method public final startShowSafeModeAnimation()V
    .locals 0

    .line 1
    return-void
.end method