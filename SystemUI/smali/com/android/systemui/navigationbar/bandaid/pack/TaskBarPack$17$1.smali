.class public final Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack$17$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Function;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack;


# direct methods
.method public constructor <init>(Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack$17$1;->this$0:Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final apply(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 44

    .line 1
    move-object/from16 v0, p1

    .line 2
    .line 3
    check-cast v0, Lcom/android/systemui/navigationbar/bandaid/Band$Kit;

    .line 4
    .line 5
    move-object/from16 v1, p0

    .line 6
    .line 7
    iget-object v1, v1, Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack$17$1;->this$0:Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack;

    .line 8
    .line 9
    iget-object v2, v0, Lcom/android/systemui/navigationbar/bandaid/Band$Kit;->event:Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType;

    .line 10
    .line 11
    check-cast v2, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$OnUpdateSideBackGestureInsets;

    .line 12
    .line 13
    new-instance v15, Lcom/android/systemui/shared/navigationbar/NavBarEvents;

    .line 14
    .line 15
    move-object/from16 v37, v15

    .line 16
    .line 17
    const/4 v4, 0x0

    .line 18
    const/4 v5, 0x0

    .line 19
    const/4 v6, 0x0

    .line 20
    const/4 v7, 0x0

    .line 21
    const/4 v8, 0x0

    .line 22
    const/4 v9, 0x0

    .line 23
    const/4 v10, 0x0

    .line 24
    const/4 v11, 0x0

    .line 25
    const/4 v12, 0x0

    .line 26
    const/4 v13, 0x0

    .line 27
    const/4 v14, 0x0

    .line 28
    const/16 v16, 0x0

    .line 29
    .line 30
    const/16 v17, 0xfff

    .line 31
    .line 32
    const/16 v18, 0x0

    .line 33
    .line 34
    move-object v3, v15

    .line 35
    move-object/from16 v43, v15

    .line 36
    .line 37
    move-object/from16 v15, v16

    .line 38
    .line 39
    move/from16 v16, v17

    .line 40
    .line 41
    move-object/from16 v17, v18

    .line 42
    .line 43
    invoke-direct/range {v3 .. v17}, Lcom/android/systemui/shared/navigationbar/NavBarEvents;-><init>(Lcom/android/systemui/shared/navigationbar/NavBarEvents$EventType;Lcom/android/systemui/shared/navigationbar/NavBarEvents$IconType;Landroid/os/Bundle;Landroid/os/Bundle;ZIZZILandroid/os/Bundle;ZLandroid/os/Bundle;ILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 44
    .line 45
    .line 46
    new-instance v3, Landroid/os/Bundle;

    .line 47
    .line 48
    invoke-direct {v3}, Landroid/os/Bundle;-><init>()V

    .line 49
    .line 50
    .line 51
    iget v4, v2, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$OnUpdateSideBackGestureInsets;->leftWidth:I

    .line 52
    .line 53
    const-string v5, "leftWidth"

    .line 54
    .line 55
    invoke-virtual {v3, v5, v4}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 56
    .line 57
    .line 58
    iget v2, v2, Lcom/android/systemui/navigationbar/store/EventTypeFactory$EventType$OnUpdateSideBackGestureInsets;->rightWidth:I

    .line 59
    .line 60
    const-string/jumbo v4, "rightWidth"

    .line 61
    .line 62
    .line 63
    invoke-virtual {v3, v4, v2}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 64
    .line 65
    .line 66
    sget-object v2, Lcom/android/systemui/shared/navigationbar/NavBarEvents$EventType;->ON_UPDATE_SIDE_BACK_GESTURE_INSETS:Lcom/android/systemui/shared/navigationbar/NavBarEvents$EventType;

    .line 67
    .line 68
    move-object/from16 v4, v43

    .line 69
    .line 70
    iput-object v2, v4, Lcom/android/systemui/shared/navigationbar/NavBarEvents;->eventType:Lcom/android/systemui/shared/navigationbar/NavBarEvents$EventType;

    .line 71
    .line 72
    iput-object v3, v4, Lcom/android/systemui/shared/navigationbar/NavBarEvents;->insetsBundle:Landroid/os/Bundle;

    .line 73
    .line 74
    iget-object v1, v1, Lcom/android/systemui/navigationbar/bandaid/pack/TaskBarPack;->store:Lcom/android/systemui/navigationbar/store/NavBarStore;

    .line 75
    .line 76
    new-instance v2, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$UpdateTaskBarNavBarEvents;

    .line 77
    .line 78
    new-instance v3, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$Action;

    .line 79
    .line 80
    move-object/from16 v18, v3

    .line 81
    .line 82
    const/16 v19, 0x0

    .line 83
    .line 84
    const/16 v20, 0x0

    .line 85
    .line 86
    const/16 v21, 0x0

    .line 87
    .line 88
    const/16 v22, 0x0

    .line 89
    .line 90
    const/16 v23, 0x0

    .line 91
    .line 92
    const/16 v24, 0x0

    .line 93
    .line 94
    const/16 v25, 0x0

    .line 95
    .line 96
    const/16 v26, 0x0

    .line 97
    .line 98
    const/16 v27, 0x0

    .line 99
    .line 100
    const/16 v28, 0x0

    .line 101
    .line 102
    const/16 v30, 0x0

    .line 103
    .line 104
    move/from16 v29, v30

    .line 105
    .line 106
    const/16 v31, 0x0

    .line 107
    .line 108
    const/16 v32, 0x0

    .line 109
    .line 110
    const/16 v33, 0x0

    .line 111
    .line 112
    const/16 v34, 0x0

    .line 113
    .line 114
    const/16 v35, 0x0

    .line 115
    .line 116
    const/16 v36, 0x0

    .line 117
    .line 118
    const/16 v38, 0x0

    .line 119
    .line 120
    const/16 v39, 0x0

    .line 121
    .line 122
    const/16 v40, 0x0

    .line 123
    .line 124
    const v41, 0x3bffff

    .line 125
    .line 126
    .line 127
    const/16 v42, 0x0

    .line 128
    .line 129
    invoke-direct/range {v18 .. v42}, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$Action;-><init>(Lcom/android/systemui/navigationbar/store/NavBarStoreAction$OneHandModeInfo;Lcom/android/systemui/navigationbar/store/NavBarStoreAction$NavBarLayoutInfo;Landroid/widget/LinearLayout;Landroid/widget/LinearLayout;ZFLcom/android/systemui/navigationbar/store/NavBarStoreAction$RemoteViewShortcut;ZFIZZIZLcom/android/systemui/navigationbar/store/NavBarStoreAction$GestureHintVIInfo;Ljava/util/List;ZZLcom/android/systemui/shared/navigationbar/NavBarEvents;FFIILkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 130
    .line 131
    .line 132
    invoke-direct {v2, v3}, Lcom/android/systemui/navigationbar/store/NavBarStoreAction$UpdateTaskBarNavBarEvents;-><init>(Lcom/android/systemui/navigationbar/store/NavBarStoreAction$Action;)V

    .line 133
    .line 134
    .line 135
    check-cast v1, Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;

    .line 136
    .line 137
    invoke-virtual {v1, v0, v2}, Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;->apply(Lcom/android/systemui/navigationbar/bandaid/Band$Kit;Lcom/android/systemui/navigationbar/store/NavBarStoreAction;)Lcom/android/systemui/navigationbar/store/NavBarStoreImpl;

    .line 138
    .line 139
    .line 140
    return-object v1
.end method