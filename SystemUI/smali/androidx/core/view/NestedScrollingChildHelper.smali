.class public final Landroidx/core/view/NestedScrollingChildHelper;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public mIsNestedScrollingEnabled:Z

.field public mNestedScrollingParentNonTouch:Landroid/view/ViewParent;

.field public mNestedScrollingParentTouch:Landroid/view/ViewParent;

.field public mTempNestedScrollConsumed:[I

.field public final mView:Landroid/view/View;


# direct methods
.method public constructor <init>(Landroid/view/View;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final dispatchNestedFling(FFZ)Z
    .locals 2

    .line 1
    iget-boolean v0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mIsNestedScrollingEnabled:Z

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    if-eqz v0, :cond_0

    .line 5
    .line 6
    invoke-virtual {p0, v1}, Landroidx/core/view/NestedScrollingChildHelper;->getNestedScrollingParentForType(I)Landroid/view/ViewParent;

    .line 7
    .line 8
    .line 9
    move-result-object v0

    .line 10
    if-eqz v0, :cond_0

    .line 11
    .line 12
    iget-object p0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 13
    .line 14
    :try_start_0
    invoke-interface {v0, p0, p1, p2, p3}, Landroid/view/ViewParent;->onNestedFling(Landroid/view/View;FFZ)Z

    .line 15
    .line 16
    .line 17
    move-result v1
    :try_end_0
    .catch Ljava/lang/AbstractMethodError; {:try_start_0 .. :try_end_0} :catch_0

    .line 18
    goto :goto_0

    .line 19
    :catch_0
    move-exception p0

    .line 20
    new-instance p1, Ljava/lang/StringBuilder;

    .line 21
    .line 22
    const-string p2, "ViewParent "

    .line 23
    .line 24
    invoke-direct {p1, p2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 25
    .line 26
    .line 27
    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 28
    .line 29
    .line 30
    const-string p2, " does not implement interface method onNestedFling"

    .line 31
    .line 32
    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 33
    .line 34
    .line 35
    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 36
    .line 37
    .line 38
    move-result-object p1

    .line 39
    const-string p2, "ViewParentCompat"

    .line 40
    .line 41
    invoke-static {p2, p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 42
    .line 43
    .line 44
    :cond_0
    :goto_0
    return v1
.end method

.method public final dispatchNestedPreFling(FF)Z
    .locals 2

    .line 1
    iget-boolean v0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mIsNestedScrollingEnabled:Z

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    if-eqz v0, :cond_0

    .line 5
    .line 6
    invoke-virtual {p0, v1}, Landroidx/core/view/NestedScrollingChildHelper;->getNestedScrollingParentForType(I)Landroid/view/ViewParent;

    .line 7
    .line 8
    .line 9
    move-result-object v0

    .line 10
    if-eqz v0, :cond_0

    .line 11
    .line 12
    iget-object p0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 13
    .line 14
    :try_start_0
    invoke-interface {v0, p0, p1, p2}, Landroid/view/ViewParent;->onNestedPreFling(Landroid/view/View;FF)Z

    .line 15
    .line 16
    .line 17
    move-result v1
    :try_end_0
    .catch Ljava/lang/AbstractMethodError; {:try_start_0 .. :try_end_0} :catch_0

    .line 18
    goto :goto_0

    .line 19
    :catch_0
    move-exception p0

    .line 20
    new-instance p1, Ljava/lang/StringBuilder;

    .line 21
    .line 22
    const-string p2, "ViewParent "

    .line 23
    .line 24
    invoke-direct {p1, p2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 25
    .line 26
    .line 27
    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 28
    .line 29
    .line 30
    const-string p2, " does not implement interface method onNestedPreFling"

    .line 31
    .line 32
    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 33
    .line 34
    .line 35
    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 36
    .line 37
    .line 38
    move-result-object p1

    .line 39
    const-string p2, "ViewParentCompat"

    .line 40
    .line 41
    invoke-static {p2, p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 42
    .line 43
    .line 44
    :cond_0
    :goto_0
    return v1
.end method

.method public final dispatchNestedPreScroll(III[I[I)Z
    .locals 13

    .line 1
    move-object v0, p0

    .line 2
    move v2, p1

    .line 3
    move v3, p2

    .line 4
    move/from16 v5, p3

    .line 5
    .line 6
    move-object/from16 v6, p5

    .line 7
    .line 8
    iget-boolean v1, v0, Landroidx/core/view/NestedScrollingChildHelper;->mIsNestedScrollingEnabled:Z

    .line 9
    .line 10
    const/4 v7, 0x0

    .line 11
    if-eqz v1, :cond_a

    .line 12
    .line 13
    invoke-virtual {p0, v5}, Landroidx/core/view/NestedScrollingChildHelper;->getNestedScrollingParentForType(I)Landroid/view/ViewParent;

    .line 14
    .line 15
    .line 16
    move-result-object v1

    .line 17
    if-nez v1, :cond_0

    .line 18
    .line 19
    return v7

    .line 20
    :cond_0
    const/4 v8, 0x1

    .line 21
    if-nez v2, :cond_2

    .line 22
    .line 23
    if-eqz v3, :cond_1

    .line 24
    .line 25
    goto :goto_0

    .line 26
    :cond_1
    if-eqz v6, :cond_a

    .line 27
    .line 28
    aput v7, v6, v7

    .line 29
    .line 30
    aput v7, v6, v8

    .line 31
    .line 32
    goto/16 :goto_4

    .line 33
    .line 34
    :cond_2
    :goto_0
    iget-object v9, v0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 35
    .line 36
    if-eqz v6, :cond_3

    .line 37
    .line 38
    invoke-virtual {v9, v6}, Landroid/view/View;->getLocationInWindow([I)V

    .line 39
    .line 40
    .line 41
    aget v4, v6, v7

    .line 42
    .line 43
    aget v10, v6, v8

    .line 44
    .line 45
    move v11, v10

    .line 46
    move v10, v4

    .line 47
    goto :goto_1

    .line 48
    :cond_3
    move v10, v7

    .line 49
    move v11, v10

    .line 50
    :goto_1
    if-nez p4, :cond_5

    .line 51
    .line 52
    iget-object v4, v0, Landroidx/core/view/NestedScrollingChildHelper;->mTempNestedScrollConsumed:[I

    .line 53
    .line 54
    if-nez v4, :cond_4

    .line 55
    .line 56
    const/4 v4, 0x2

    .line 57
    new-array v4, v4, [I

    .line 58
    .line 59
    iput-object v4, v0, Landroidx/core/view/NestedScrollingChildHelper;->mTempNestedScrollConsumed:[I

    .line 60
    .line 61
    :cond_4
    iget-object v4, v0, Landroidx/core/view/NestedScrollingChildHelper;->mTempNestedScrollConsumed:[I

    .line 62
    .line 63
    move-object v12, v4

    .line 64
    goto :goto_2

    .line 65
    :cond_5
    move-object/from16 v12, p4

    .line 66
    .line 67
    :goto_2
    aput v7, v12, v7

    .line 68
    .line 69
    aput v7, v12, v8

    .line 70
    .line 71
    iget-object v4, v0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 72
    .line 73
    instance-of v0, v1, Landroidx/core/view/NestedScrollingParent2;

    .line 74
    .line 75
    if-eqz v0, :cond_6

    .line 76
    .line 77
    move-object v0, v1

    .line 78
    check-cast v0, Landroidx/core/view/NestedScrollingParent2;

    .line 79
    .line 80
    move-object v1, v4

    .line 81
    move v2, p1

    .line 82
    move v3, p2

    .line 83
    move-object v4, v12

    .line 84
    move/from16 v5, p3

    .line 85
    .line 86
    invoke-interface/range {v0 .. v5}, Landroidx/core/view/NestedScrollingParent2;->onNestedPreScroll(Landroid/view/View;II[II)V

    .line 87
    .line 88
    .line 89
    goto :goto_3

    .line 90
    :cond_6
    if-nez v5, :cond_7

    .line 91
    .line 92
    :try_start_0
    invoke-interface {v1, v4, p1, p2, v12}, Landroid/view/ViewParent;->onNestedPreScroll(Landroid/view/View;II[I)V
    :try_end_0
    .catch Ljava/lang/AbstractMethodError; {:try_start_0 .. :try_end_0} :catch_0

    .line 93
    .line 94
    .line 95
    goto :goto_3

    .line 96
    :catch_0
    move-exception v0

    .line 97
    move-object v2, v0

    .line 98
    new-instance v0, Ljava/lang/StringBuilder;

    .line 99
    .line 100
    const-string v3, "ViewParent "

    .line 101
    .line 102
    invoke-direct {v0, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 103
    .line 104
    .line 105
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 106
    .line 107
    .line 108
    const-string v1, " does not implement interface method onNestedPreScroll"

    .line 109
    .line 110
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 111
    .line 112
    .line 113
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 114
    .line 115
    .line 116
    move-result-object v0

    .line 117
    const-string v1, "ViewParentCompat"

    .line 118
    .line 119
    invoke-static {v1, v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 120
    .line 121
    .line 122
    :cond_7
    :goto_3
    if-eqz v6, :cond_8

    .line 123
    .line 124
    invoke-virtual {v9, v6}, Landroid/view/View;->getLocationInWindow([I)V

    .line 125
    .line 126
    .line 127
    aget v0, v6, v7

    .line 128
    .line 129
    sub-int/2addr v0, v10

    .line 130
    aput v0, v6, v7

    .line 131
    .line 132
    aget v0, v6, v8

    .line 133
    .line 134
    sub-int/2addr v0, v11

    .line 135
    aput v0, v6, v8

    .line 136
    .line 137
    :cond_8
    aget v0, v12, v7

    .line 138
    .line 139
    if-nez v0, :cond_9

    .line 140
    .line 141
    aget v0, v12, v8

    .line 142
    .line 143
    if-eqz v0, :cond_a

    .line 144
    .line 145
    :cond_9
    move v7, v8

    .line 146
    :cond_a
    :goto_4
    return v7
.end method

.method public final dispatchNestedScrollInternal(IIII[II[I)Z
    .locals 15

    .line 1
    move-object v0, p0

    .line 2
    move-object/from16 v8, p5

    .line 3
    .line 4
    move/from16 v6, p6

    .line 5
    .line 6
    iget-boolean v1, v0, Landroidx/core/view/NestedScrollingChildHelper;->mIsNestedScrollingEnabled:Z

    .line 7
    .line 8
    const/4 v9, 0x0

    .line 9
    if-eqz v1, :cond_a

    .line 10
    .line 11
    invoke-virtual {p0, v6}, Landroidx/core/view/NestedScrollingChildHelper;->getNestedScrollingParentForType(I)Landroid/view/ViewParent;

    .line 12
    .line 13
    .line 14
    move-result-object v7

    .line 15
    if-nez v7, :cond_0

    .line 16
    .line 17
    return v9

    .line 18
    :cond_0
    const/4 v10, 0x1

    .line 19
    if-nez p1, :cond_2

    .line 20
    .line 21
    if-nez p2, :cond_2

    .line 22
    .line 23
    if-nez p3, :cond_2

    .line 24
    .line 25
    if-eqz p4, :cond_1

    .line 26
    .line 27
    goto :goto_0

    .line 28
    :cond_1
    if-eqz v8, :cond_a

    .line 29
    .line 30
    aput v9, v8, v9

    .line 31
    .line 32
    aput v9, v8, v10

    .line 33
    .line 34
    goto/16 :goto_4

    .line 35
    .line 36
    :cond_2
    :goto_0
    iget-object v11, v0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 37
    .line 38
    if-eqz v8, :cond_3

    .line 39
    .line 40
    invoke-virtual {v11, v8}, Landroid/view/View;->getLocationInWindow([I)V

    .line 41
    .line 42
    .line 43
    aget v1, v8, v9

    .line 44
    .line 45
    aget v2, v8, v10

    .line 46
    .line 47
    move v12, v1

    .line 48
    move v13, v2

    .line 49
    goto :goto_1

    .line 50
    :cond_3
    move v12, v9

    .line 51
    move v13, v12

    .line 52
    :goto_1
    if-nez p7, :cond_5

    .line 53
    .line 54
    iget-object v1, v0, Landroidx/core/view/NestedScrollingChildHelper;->mTempNestedScrollConsumed:[I

    .line 55
    .line 56
    if-nez v1, :cond_4

    .line 57
    .line 58
    const/4 v1, 0x2

    .line 59
    new-array v1, v1, [I

    .line 60
    .line 61
    iput-object v1, v0, Landroidx/core/view/NestedScrollingChildHelper;->mTempNestedScrollConsumed:[I

    .line 62
    .line 63
    :cond_4
    iget-object v1, v0, Landroidx/core/view/NestedScrollingChildHelper;->mTempNestedScrollConsumed:[I

    .line 64
    .line 65
    aput v9, v1, v9

    .line 66
    .line 67
    aput v9, v1, v10

    .line 68
    .line 69
    move-object v14, v1

    .line 70
    goto :goto_2

    .line 71
    :cond_5
    move-object/from16 v14, p7

    .line 72
    .line 73
    :goto_2
    iget-object v1, v0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 74
    .line 75
    instance-of v0, v7, Landroidx/core/view/NestedScrollingParent3;

    .line 76
    .line 77
    if-eqz v0, :cond_6

    .line 78
    .line 79
    move-object v0, v7

    .line 80
    check-cast v0, Landroidx/core/view/NestedScrollingParent3;

    .line 81
    .line 82
    move/from16 v2, p1

    .line 83
    .line 84
    move/from16 v3, p2

    .line 85
    .line 86
    move/from16 v4, p3

    .line 87
    .line 88
    move/from16 v5, p4

    .line 89
    .line 90
    move/from16 v6, p6

    .line 91
    .line 92
    move-object v7, v14

    .line 93
    invoke-interface/range {v0 .. v7}, Landroidx/core/view/NestedScrollingParent3;->onNestedScroll(Landroid/view/View;IIIII[I)V

    .line 94
    .line 95
    .line 96
    goto :goto_3

    .line 97
    :cond_6
    aget v0, v14, v9

    .line 98
    .line 99
    add-int v0, v0, p3

    .line 100
    .line 101
    aput v0, v14, v9

    .line 102
    .line 103
    aget v0, v14, v10

    .line 104
    .line 105
    add-int v0, v0, p4

    .line 106
    .line 107
    aput v0, v14, v10

    .line 108
    .line 109
    instance-of v0, v7, Landroidx/core/view/NestedScrollingParent2;

    .line 110
    .line 111
    if-eqz v0, :cond_7

    .line 112
    .line 113
    move-object v0, v7

    .line 114
    check-cast v0, Landroidx/core/view/NestedScrollingParent2;

    .line 115
    .line 116
    move/from16 v2, p1

    .line 117
    .line 118
    move/from16 v3, p2

    .line 119
    .line 120
    move/from16 v4, p3

    .line 121
    .line 122
    move/from16 v5, p4

    .line 123
    .line 124
    move/from16 v6, p6

    .line 125
    .line 126
    invoke-interface/range {v0 .. v6}, Landroidx/core/view/NestedScrollingParent2;->onNestedScroll(Landroid/view/View;IIIII)V

    .line 127
    .line 128
    .line 129
    goto :goto_3

    .line 130
    :cond_7
    if-nez v6, :cond_8

    .line 131
    .line 132
    move-object v0, v7

    .line 133
    move/from16 v2, p1

    .line 134
    .line 135
    move/from16 v3, p2

    .line 136
    .line 137
    move/from16 v4, p3

    .line 138
    .line 139
    move/from16 v5, p4

    .line 140
    .line 141
    :try_start_0
    invoke-interface/range {v0 .. v5}, Landroid/view/ViewParent;->onNestedScroll(Landroid/view/View;IIII)V
    :try_end_0
    .catch Ljava/lang/AbstractMethodError; {:try_start_0 .. :try_end_0} :catch_0

    .line 142
    .line 143
    .line 144
    goto :goto_3

    .line 145
    :catch_0
    move-exception v0

    .line 146
    move-object v1, v0

    .line 147
    new-instance v0, Ljava/lang/StringBuilder;

    .line 148
    .line 149
    const-string v2, "ViewParent "

    .line 150
    .line 151
    invoke-direct {v0, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 152
    .line 153
    .line 154
    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 155
    .line 156
    .line 157
    const-string v2, " does not implement interface method onNestedScroll"

    .line 158
    .line 159
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 160
    .line 161
    .line 162
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 163
    .line 164
    .line 165
    move-result-object v0

    .line 166
    const-string v2, "ViewParentCompat"

    .line 167
    .line 168
    invoke-static {v2, v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 169
    .line 170
    .line 171
    :cond_8
    :goto_3
    if-eqz v8, :cond_9

    .line 172
    .line 173
    invoke-virtual {v11, v8}, Landroid/view/View;->getLocationInWindow([I)V

    .line 174
    .line 175
    .line 176
    aget v0, v8, v9

    .line 177
    .line 178
    sub-int/2addr v0, v12

    .line 179
    aput v0, v8, v9

    .line 180
    .line 181
    aget v0, v8, v10

    .line 182
    .line 183
    sub-int/2addr v0, v13

    .line 184
    aput v0, v8, v10

    .line 185
    .line 186
    :cond_9
    return v10

    .line 187
    :cond_a
    :goto_4
    return v9
.end method

.method public final getNestedScrollingParentForType(I)Landroid/view/ViewParent;
    .locals 1

    .line 1
    if-eqz p1, :cond_1

    .line 2
    .line 3
    const/4 v0, 0x1

    .line 4
    if-eq p1, v0, :cond_0

    .line 5
    .line 6
    const/4 p0, 0x0

    .line 7
    return-object p0

    .line 8
    :cond_0
    iget-object p0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mNestedScrollingParentNonTouch:Landroid/view/ViewParent;

    .line 9
    .line 10
    return-object p0

    .line 11
    :cond_1
    iget-object p0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mNestedScrollingParentTouch:Landroid/view/ViewParent;

    .line 12
    .line 13
    return-object p0
.end method

.method public final startNestedScroll(II)Z
    .locals 11

    .line 1
    invoke-virtual {p0, p2}, Landroidx/core/view/NestedScrollingChildHelper;->getNestedScrollingParentForType(I)Landroid/view/ViewParent;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    const/4 v1, 0x0

    .line 6
    const/4 v2, 0x1

    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    move v0, v2

    .line 10
    goto :goto_0

    .line 11
    :cond_0
    move v0, v1

    .line 12
    :goto_0
    if-eqz v0, :cond_1

    .line 13
    .line 14
    return v2

    .line 15
    :cond_1
    iget-boolean v0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mIsNestedScrollingEnabled:Z

    .line 16
    .line 17
    if-eqz v0, :cond_a

    .line 18
    .line 19
    iget-object v0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 20
    .line 21
    invoke-virtual {v0}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    .line 22
    .line 23
    .line 24
    move-result-object v3

    .line 25
    move-object v4, v0

    .line 26
    :goto_1
    if-eqz v3, :cond_a

    .line 27
    .line 28
    instance-of v5, v3, Landroidx/core/view/NestedScrollingParent2;

    .line 29
    .line 30
    const-string v6, "ViewParentCompat"

    .line 31
    .line 32
    const-string v7, "ViewParent "

    .line 33
    .line 34
    if-eqz v5, :cond_2

    .line 35
    .line 36
    move-object v8, v3

    .line 37
    check-cast v8, Landroidx/core/view/NestedScrollingParent2;

    .line 38
    .line 39
    invoke-interface {v8, v4, v0, p1, p2}, Landroidx/core/view/NestedScrollingParent2;->onStartNestedScroll(Landroid/view/View;Landroid/view/View;II)Z

    .line 40
    .line 41
    .line 42
    move-result v8

    .line 43
    goto :goto_2

    .line 44
    :cond_2
    if-nez p2, :cond_3

    .line 45
    .line 46
    :try_start_0
    invoke-interface {v3, v4, v0, p1}, Landroid/view/ViewParent;->onStartNestedScroll(Landroid/view/View;Landroid/view/View;I)Z

    .line 47
    .line 48
    .line 49
    move-result v8
    :try_end_0
    .catch Ljava/lang/AbstractMethodError; {:try_start_0 .. :try_end_0} :catch_0

    .line 50
    goto :goto_2

    .line 51
    :catch_0
    move-exception v8

    .line 52
    new-instance v9, Ljava/lang/StringBuilder;

    .line 53
    .line 54
    invoke-direct {v9, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 55
    .line 56
    .line 57
    invoke-virtual {v9, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 58
    .line 59
    .line 60
    const-string v10, " does not implement interface method onStartNestedScroll"

    .line 61
    .line 62
    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 63
    .line 64
    .line 65
    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 66
    .line 67
    .line 68
    move-result-object v9

    .line 69
    invoke-static {v6, v9, v8}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 70
    .line 71
    .line 72
    :cond_3
    move v8, v1

    .line 73
    :goto_2
    if-eqz v8, :cond_8

    .line 74
    .line 75
    if-eqz p2, :cond_5

    .line 76
    .line 77
    if-eq p2, v2, :cond_4

    .line 78
    .line 79
    goto :goto_3

    .line 80
    :cond_4
    iput-object v3, p0, Landroidx/core/view/NestedScrollingChildHelper;->mNestedScrollingParentNonTouch:Landroid/view/ViewParent;

    .line 81
    .line 82
    goto :goto_3

    .line 83
    :cond_5
    iput-object v3, p0, Landroidx/core/view/NestedScrollingChildHelper;->mNestedScrollingParentTouch:Landroid/view/ViewParent;

    .line 84
    .line 85
    :goto_3
    if-eqz v5, :cond_6

    .line 86
    .line 87
    check-cast v3, Landroidx/core/view/NestedScrollingParent2;

    .line 88
    .line 89
    invoke-interface {v3, v4, v0, p1, p2}, Landroidx/core/view/NestedScrollingParent2;->onNestedScrollAccepted(Landroid/view/View;Landroid/view/View;II)V

    .line 90
    .line 91
    .line 92
    goto :goto_4

    .line 93
    :cond_6
    if-nez p2, :cond_7

    .line 94
    .line 95
    :try_start_1
    invoke-interface {v3, v4, v0, p1}, Landroid/view/ViewParent;->onNestedScrollAccepted(Landroid/view/View;Landroid/view/View;I)V
    :try_end_1
    .catch Ljava/lang/AbstractMethodError; {:try_start_1 .. :try_end_1} :catch_1

    .line 96
    .line 97
    .line 98
    goto :goto_4

    .line 99
    :catch_1
    move-exception p0

    .line 100
    new-instance p1, Ljava/lang/StringBuilder;

    .line 101
    .line 102
    invoke-direct {p1, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 103
    .line 104
    .line 105
    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 106
    .line 107
    .line 108
    const-string p2, " does not implement interface method onNestedScrollAccepted"

    .line 109
    .line 110
    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 111
    .line 112
    .line 113
    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 114
    .line 115
    .line 116
    move-result-object p1

    .line 117
    invoke-static {v6, p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 118
    .line 119
    .line 120
    :cond_7
    :goto_4
    return v2

    .line 121
    :cond_8
    instance-of v5, v3, Landroid/view/View;

    .line 122
    .line 123
    if-eqz v5, :cond_9

    .line 124
    .line 125
    move-object v4, v3

    .line 126
    check-cast v4, Landroid/view/View;

    .line 127
    .line 128
    :cond_9
    invoke-interface {v3}, Landroid/view/ViewParent;->getParent()Landroid/view/ViewParent;

    .line 129
    .line 130
    .line 131
    move-result-object v3

    .line 132
    goto :goto_1

    .line 133
    :cond_a
    return v1
.end method

.method public final stopNestedScroll(I)V
    .locals 4

    .line 1
    invoke-virtual {p0, p1}, Landroidx/core/view/NestedScrollingChildHelper;->getNestedScrollingParentForType(I)Landroid/view/ViewParent;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    if-eqz v0, :cond_4

    .line 6
    .line 7
    instance-of v1, v0, Landroidx/core/view/NestedScrollingParent2;

    .line 8
    .line 9
    iget-object v2, p0, Landroidx/core/view/NestedScrollingChildHelper;->mView:Landroid/view/View;

    .line 10
    .line 11
    if-eqz v1, :cond_0

    .line 12
    .line 13
    check-cast v0, Landroidx/core/view/NestedScrollingParent2;

    .line 14
    .line 15
    invoke-interface {v0, v2, p1}, Landroidx/core/view/NestedScrollingParent2;->onStopNestedScroll(Landroid/view/View;I)V

    .line 16
    .line 17
    .line 18
    goto :goto_0

    .line 19
    :cond_0
    if-nez p1, :cond_1

    .line 20
    .line 21
    :try_start_0
    invoke-interface {v0, v2}, Landroid/view/ViewParent;->onStopNestedScroll(Landroid/view/View;)V
    :try_end_0
    .catch Ljava/lang/AbstractMethodError; {:try_start_0 .. :try_end_0} :catch_0

    .line 22
    .line 23
    .line 24
    goto :goto_0

    .line 25
    :catch_0
    move-exception v1

    .line 26
    new-instance v2, Ljava/lang/StringBuilder;

    .line 27
    .line 28
    const-string v3, "ViewParent "

    .line 29
    .line 30
    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 31
    .line 32
    .line 33
    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 34
    .line 35
    .line 36
    const-string v0, " does not implement interface method onStopNestedScroll"

    .line 37
    .line 38
    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 39
    .line 40
    .line 41
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 42
    .line 43
    .line 44
    move-result-object v0

    .line 45
    const-string v2, "ViewParentCompat"

    .line 46
    .line 47
    invoke-static {v2, v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 48
    .line 49
    .line 50
    :cond_1
    :goto_0
    const/4 v0, 0x0

    .line 51
    if-eqz p1, :cond_3

    .line 52
    .line 53
    const/4 v1, 0x1

    .line 54
    if-eq p1, v1, :cond_2

    .line 55
    .line 56
    goto :goto_1

    .line 57
    :cond_2
    iput-object v0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mNestedScrollingParentNonTouch:Landroid/view/ViewParent;

    .line 58
    .line 59
    goto :goto_1

    .line 60
    :cond_3
    iput-object v0, p0, Landroidx/core/view/NestedScrollingChildHelper;->mNestedScrollingParentTouch:Landroid/view/ViewParent;

    .line 61
    .line 62
    :cond_4
    :goto_1
    return-void
.end method
