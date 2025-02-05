.class public final Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/samsung/android/globalactions/presentation/view/ContentViewFactoryBase;


# instance fields
.field public final mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

.field public final mContext:Landroid/content/Context;

.field public final mCoverUtilWrapper:Lcom/android/systemui/basic/util/CoverUtilWrapper;

.field public final mFeatureFactory:Lcom/samsung/android/globalactions/presentation/features/FeatureFactory;

.field public final mHandlerUtil:Lcom/samsung/android/globalactions/util/HandlerUtil;

.field public final mLogWrapper:Lcom/samsung/android/globalactions/util/LogWrapper;

.field public final mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

.field public final mPresenter:Lcom/samsung/android/globalactions/presentation/SamsungGlobalActionsPresenter;

.field public final mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

.field public final mToastController:Lcom/samsung/android/globalactions/util/ToastController;

.field public final mWindowManagerUtil:Lcom/samsung/android/globalactions/util/WindowManagerUtils;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;Lcom/samsung/android/globalactions/presentation/features/FeatureFactory;Lcom/samsung/android/globalactions/util/ConditionChecker;Lcom/samsung/android/globalactions/util/WindowManagerUtils;Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;Lcom/android/systemui/basic/util/CoverUtilWrapper;Lcom/samsung/android/globalactions/util/LogWrapper;Lcom/samsung/android/globalactions/util/HandlerUtil;Lcom/samsung/android/globalactions/util/ToastController;Lcom/samsung/android/globalactions/presentation/SamsungGlobalActionsPresenter;Z)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mContext:Landroid/content/Context;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mFeatureFactory:Lcom/samsung/android/globalactions/presentation/features/FeatureFactory;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 11
    .line 12
    iput-object p6, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 13
    .line 14
    iput-object p7, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mCoverUtilWrapper:Lcom/android/systemui/basic/util/CoverUtilWrapper;

    .line 15
    .line 16
    iput-object p8, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mLogWrapper:Lcom/samsung/android/globalactions/util/LogWrapper;

    .line 17
    .line 18
    iput-object p9, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mHandlerUtil:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 19
    .line 20
    iput-object p5, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mWindowManagerUtil:Lcom/samsung/android/globalactions/util/WindowManagerUtils;

    .line 21
    .line 22
    iput-object p10, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mToastController:Lcom/samsung/android/globalactions/util/ToastController;

    .line 23
    .line 24
    iput-object p11, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mPresenter:Lcom/samsung/android/globalactions/presentation/SamsungGlobalActionsPresenter;

    .line 25
    .line 26
    return-void
.end method


# virtual methods
.method public final createContentView(Landroid/app/Dialog;)Lcom/samsung/android/globalactions/presentation/view/ContentView;
    .locals 12

    .line 1
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 2
    .line 3
    sget-object v1, Lcom/android/systemui/globalactions/util/SystemUIConditions;->IS_CLEAR_SIDE_VIEW_COVER_CLOSED:Lcom/android/systemui/globalactions/util/SystemUIConditions;

    .line 4
    .line 5
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 6
    .line 7
    .line 8
    move-result v0

    .line 9
    if-eqz v0, :cond_0

    .line 10
    .line 11
    new-instance v0, Lcom/android/systemui/globalactions/presentation/view/SideCoverContentView;

    .line 12
    .line 13
    iget-object v2, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mContext:Landroid/content/Context;

    .line 14
    .line 15
    iget-object v3, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 16
    .line 17
    iget-object v4, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 18
    .line 19
    iget-object v5, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 20
    .line 21
    iget-object v6, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mCoverUtilWrapper:Lcom/android/systemui/basic/util/CoverUtilWrapper;

    .line 22
    .line 23
    iget-object v7, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mLogWrapper:Lcom/samsung/android/globalactions/util/LogWrapper;

    .line 24
    .line 25
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mHandlerUtil:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 26
    .line 27
    iget-object v9, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mToastController:Lcom/samsung/android/globalactions/util/ToastController;

    .line 28
    .line 29
    move-object v1, v0

    .line 30
    move-object v10, p1

    .line 31
    invoke-direct/range {v1 .. v10}, Lcom/android/systemui/globalactions/presentation/view/SideCoverContentView;-><init>(Landroid/content/Context;Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;Lcom/samsung/android/globalactions/util/ConditionChecker;Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;Lcom/android/systemui/basic/util/CoverUtilWrapper;Lcom/samsung/android/globalactions/util/LogWrapper;Lcom/samsung/android/globalactions/util/HandlerUtil;Lcom/samsung/android/globalactions/util/ToastController;Landroid/app/Dialog;)V

    .line 32
    .line 33
    .line 34
    goto/16 :goto_0

    .line 35
    .line 36
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 37
    .line 38
    sget-object v1, Lcom/android/systemui/globalactions/util/SystemUIConditions;->IS_MINI_SVIEW_COVER_CLOSED:Lcom/android/systemui/globalactions/util/SystemUIConditions;

    .line 39
    .line 40
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 41
    .line 42
    .line 43
    move-result v0

    .line 44
    if-eqz v0, :cond_1

    .line 45
    .line 46
    new-instance v0, Lcom/android/systemui/globalactions/presentation/view/MiniSViewCoverContentView;

    .line 47
    .line 48
    iget-object v2, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mContext:Landroid/content/Context;

    .line 49
    .line 50
    iget-object v3, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 51
    .line 52
    iget-object v4, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 53
    .line 54
    iget-object v5, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 55
    .line 56
    iget-object v6, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mCoverUtilWrapper:Lcom/android/systemui/basic/util/CoverUtilWrapper;

    .line 57
    .line 58
    iget-object v7, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mLogWrapper:Lcom/samsung/android/globalactions/util/LogWrapper;

    .line 59
    .line 60
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mHandlerUtil:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 61
    .line 62
    iget-object v9, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mToastController:Lcom/samsung/android/globalactions/util/ToastController;

    .line 63
    .line 64
    move-object v1, v0

    .line 65
    move-object v10, p1

    .line 66
    invoke-direct/range {v1 .. v10}, Lcom/android/systemui/globalactions/presentation/view/MiniSViewCoverContentView;-><init>(Landroid/content/Context;Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;Lcom/samsung/android/globalactions/util/ConditionChecker;Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;Lcom/android/systemui/basic/util/CoverUtilWrapper;Lcom/samsung/android/globalactions/util/LogWrapper;Lcom/samsung/android/globalactions/util/HandlerUtil;Lcom/samsung/android/globalactions/util/ToastController;Landroid/app/Dialog;)V

    .line 67
    .line 68
    .line 69
    goto/16 :goto_0

    .line 70
    .line 71
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 72
    .line 73
    sget-object v1, Lcom/samsung/android/globalactions/util/SystemConditions;->FRONT_LARGE_COVER_DISPLAY:Lcom/samsung/android/globalactions/util/SystemConditions;

    .line 74
    .line 75
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 76
    .line 77
    .line 78
    move-result v0

    .line 79
    if-nez v0, :cond_2

    .line 80
    .line 81
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 82
    .line 83
    sget-object v1, Lcom/samsung/android/globalactions/util/SystemConditions;->SUPPORT_SECONDARY_DISPLAY_AS_COVER:Lcom/samsung/android/globalactions/util/SystemConditions;

    .line 84
    .line 85
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 86
    .line 87
    .line 88
    move-result v0

    .line 89
    if-eqz v0, :cond_2

    .line 90
    .line 91
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 92
    .line 93
    sget-object v1, Lcom/samsung/android/globalactions/util/SystemConditions;->IS_FOLDED:Lcom/samsung/android/globalactions/util/SystemConditions;

    .line 94
    .line 95
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 96
    .line 97
    .line 98
    move-result v0

    .line 99
    if-nez v0, :cond_3

    .line 100
    .line 101
    :cond_2
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 102
    .line 103
    sget-object v1, Lcom/android/systemui/globalactions/util/SystemUIConditions;->IS_CLEAR_CAMERA_VIEW_COVER_CLOSED:Lcom/android/systemui/globalactions/util/SystemUIConditions;

    .line 104
    .line 105
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 106
    .line 107
    .line 108
    move-result v0

    .line 109
    if-eqz v0, :cond_4

    .line 110
    .line 111
    :cond_3
    new-instance v0, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;

    .line 112
    .line 113
    iget-object v2, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mContext:Landroid/content/Context;

    .line 114
    .line 115
    iget-object v3, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 116
    .line 117
    iget-object v4, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 118
    .line 119
    iget-object v5, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 120
    .line 121
    iget-object v6, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mLogWrapper:Lcom/samsung/android/globalactions/util/LogWrapper;

    .line 122
    .line 123
    iget-object v7, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mHandlerUtil:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 124
    .line 125
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mToastController:Lcom/samsung/android/globalactions/util/ToastController;

    .line 126
    .line 127
    iget-object v9, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mPresenter:Lcom/samsung/android/globalactions/presentation/SamsungGlobalActionsPresenter;

    .line 128
    .line 129
    move-object v1, v0

    .line 130
    invoke-direct/range {v1 .. v9}, Lcom/android/systemui/globalactions/presentation/view/FrontCoverContentView;-><init>(Landroid/content/Context;Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;Lcom/samsung/android/globalactions/util/ConditionChecker;Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;Lcom/samsung/android/globalactions/util/LogWrapper;Lcom/samsung/android/globalactions/util/HandlerUtil;Lcom/samsung/android/globalactions/util/ToastController;Lcom/samsung/android/globalactions/presentation/SamsungGlobalActionsPresenter;)V

    .line 131
    .line 132
    .line 133
    goto :goto_0

    .line 134
    :cond_4
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 135
    .line 136
    sget-object v1, Lcom/samsung/android/globalactions/util/SystemConditions;->FRONT_LARGE_COVER_DISPLAY:Lcom/samsung/android/globalactions/util/SystemConditions;

    .line 137
    .line 138
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 139
    .line 140
    .line 141
    move-result v0

    .line 142
    if-eqz v0, :cond_5

    .line 143
    .line 144
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 145
    .line 146
    sget-object v1, Lcom/samsung/android/globalactions/util/SystemConditions;->SUPPORT_SECONDARY_DISPLAY_AS_COVER:Lcom/samsung/android/globalactions/util/SystemConditions;

    .line 147
    .line 148
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 149
    .line 150
    .line 151
    move-result v0

    .line 152
    if-eqz v0, :cond_5

    .line 153
    .line 154
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 155
    .line 156
    sget-object v1, Lcom/samsung/android/globalactions/util/SystemConditions;->IS_FOLDED:Lcom/samsung/android/globalactions/util/SystemConditions;

    .line 157
    .line 158
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 159
    .line 160
    .line 161
    move-result v0

    .line 162
    if-eqz v0, :cond_5

    .line 163
    .line 164
    new-instance v0, Lcom/android/systemui/globalactions/presentation/view/FrontLargeCoverContentView;

    .line 165
    .line 166
    iget-object v2, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mContext:Landroid/content/Context;

    .line 167
    .line 168
    iget-object v3, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 169
    .line 170
    iget-object v4, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mFeatureFactory:Lcom/samsung/android/globalactions/presentation/features/FeatureFactory;

    .line 171
    .line 172
    iget-object v5, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 173
    .line 174
    iget-object v6, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 175
    .line 176
    iget-object v7, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mLogWrapper:Lcom/samsung/android/globalactions/util/LogWrapper;

    .line 177
    .line 178
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mHandlerUtil:Lcom/samsung/android/globalactions/util/HandlerUtil;

    .line 179
    .line 180
    iget-object v9, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mToastController:Lcom/samsung/android/globalactions/util/ToastController;

    .line 181
    .line 182
    iget-object v10, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mPresenter:Lcom/samsung/android/globalactions/presentation/SamsungGlobalActionsPresenter;

    .line 183
    .line 184
    move-object v1, v0

    .line 185
    invoke-direct/range {v1 .. v10}, Lcom/android/systemui/globalactions/presentation/view/FrontLargeCoverContentView;-><init>(Landroid/content/Context;Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;Lcom/samsung/android/globalactions/presentation/features/FeatureFactory;Lcom/samsung/android/globalactions/util/ConditionChecker;Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;Lcom/samsung/android/globalactions/util/LogWrapper;Lcom/samsung/android/globalactions/util/HandlerUtil;Lcom/samsung/android/globalactions/util/ToastController;Lcom/samsung/android/globalactions/presentation/SamsungGlobalActionsPresenter;)V

    .line 186
    .line 187
    .line 188
    goto :goto_0

    .line 189
    :cond_5
    iget-object v0, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 190
    .line 191
    sget-object v1, Lcom/android/systemui/globalactions/util/SystemUIConditions;->IS_CLEAR_COVER_CLOSED:Lcom/android/systemui/globalactions/util/SystemUIConditions;

    .line 192
    .line 193
    invoke-interface {v0, v1}, Lcom/samsung/android/globalactions/util/ConditionChecker;->isEnabled(Ljava/lang/Object;)Z

    .line 194
    .line 195
    .line 196
    move-result v11

    .line 197
    new-instance v0, Lcom/samsung/android/globalactions/presentation/view/GlobalActionsContentView;

    .line 198
    .line 199
    iget-object v3, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mContext:Landroid/content/Context;

    .line 200
    .line 201
    iget-object v4, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mParentView:Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;

    .line 202
    .line 203
    iget-object v5, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mFeatureFactory:Lcom/samsung/android/globalactions/presentation/features/FeatureFactory;

    .line 204
    .line 205
    iget-object v6, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mConditionChecker:Lcom/samsung/android/globalactions/util/ConditionChecker;

    .line 206
    .line 207
    iget-object v7, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mWindowManagerUtil:Lcom/samsung/android/globalactions/util/WindowManagerUtils;

    .line 208
    .line 209
    iget-object v8, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mResourceFactory:Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;

    .line 210
    .line 211
    iget-object v9, p0, Lcom/android/systemui/globalactions/presentation/view/ContentViewFactory;->mLogWrapper:Lcom/samsung/android/globalactions/util/LogWrapper;

    .line 212
    .line 213
    move-object v2, v0

    .line 214
    move-object v10, p1

    .line 215
    invoke-direct/range {v2 .. v11}, Lcom/samsung/android/globalactions/presentation/view/GlobalActionsContentView;-><init>(Landroid/content/Context;Lcom/samsung/android/globalactions/presentation/view/ExtendableGlobalActionsView;Lcom/samsung/android/globalactions/presentation/features/FeatureFactory;Lcom/samsung/android/globalactions/util/ConditionChecker;Lcom/samsung/android/globalactions/util/WindowManagerUtils;Lcom/samsung/android/globalactions/presentation/view/ResourceFactory;Lcom/samsung/android/globalactions/util/LogWrapper;Landroid/app/Dialog;Z)V

    .line 216
    .line 217
    .line 218
    :goto_0
    return-object v0
.end method
