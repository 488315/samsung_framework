.class public final Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroidx/appcompat/widget/SeslSeekBar$OnSeekBarChangeListener;


# instance fields
.field public final synthetic this$0:Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;


# direct methods
.method public constructor <init>(Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel$1;->this$0:Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onProgressChanged(Landroidx/appcompat/widget/SeslSeekBar;IZ)V
    .locals 3

    .line 1
    if-eqz p3, :cond_3

    .line 2
    .line 3
    const-string p2, "FlexMediaPanel"

    .line 4
    .line 5
    const-string p3, "MediaPanel mSeekBarControlListener onProgressChanged"

    .line 6
    .line 7
    invoke-static {p2, p3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 8
    .line 9
    .line 10
    const/4 p2, 0x1

    .line 11
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel$1;->this$0:Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;

    .line 12
    .line 13
    iput-boolean p2, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarFromUser:Z

    .line 14
    .line 15
    const-string p2, "accessibility"

    .line 16
    .line 17
    iget-object p3, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mContext:Landroid/content/Context;

    .line 18
    .line 19
    invoke-virtual {p3, p2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    .line 20
    .line 21
    .line 22
    move-result-object p2

    .line 23
    check-cast p2, Landroid/view/accessibility/AccessibilityManager;

    .line 24
    .line 25
    invoke-virtual {p2}, Landroid/view/accessibility/AccessibilityManager;->semIsScreenReaderEnabled()Z

    .line 26
    .line 27
    .line 28
    move-result p2

    .line 29
    if-eqz p2, :cond_0

    .line 30
    .line 31
    iget-object p2, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 32
    .line 33
    invoke-virtual {p2}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    .line 34
    .line 35
    .line 36
    move-result-object p2

    .line 37
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslAbsSeekBar;->getProgress()I

    .line 38
    .line 39
    .line 40
    move-result v0

    .line 41
    int-to-long v0, v0

    .line 42
    invoke-virtual {p2, v0, v1}, Landroid/media/session/MediaController$TransportControls;->seekTo(J)V

    .line 43
    .line 44
    .line 45
    :cond_0
    iget-object p2, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 46
    .line 47
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslAbsSeekBar;->getProgress()I

    .line 48
    .line 49
    .line 50
    move-result p1

    .line 51
    invoke-static {p1}, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->makeCurrentText(I)Ljava/lang/String;

    .line 52
    .line 53
    .line 54
    move-result-object p1

    .line 55
    invoke-virtual {p2, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 56
    .line 57
    .line 58
    iget-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mMediaSeekBarView:Landroidx/appcompat/widget/SeslSeekBar;

    .line 59
    .line 60
    invoke-virtual {p1}, Landroid/view/View;->getWidth()I

    .line 61
    .line 62
    .line 63
    move-result p2

    .line 64
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslProgressBar;->getPaddingLeft()I

    .line 65
    .line 66
    .line 67
    move-result v0

    .line 68
    sub-int/2addr p2, v0

    .line 69
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslProgressBar;->getPaddingRight()I

    .line 70
    .line 71
    .line 72
    move-result v0

    .line 73
    sub-int/2addr p2, v0

    .line 74
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslAbsSeekBar;->getProgress()I

    .line 75
    .line 76
    .line 77
    move-result v0

    .line 78
    div-int/lit16 v0, v0, 0x3e8

    .line 79
    .line 80
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslAbsSeekBar;->getMax()I

    .line 81
    .line 82
    .line 83
    move-result v1

    .line 84
    div-int/lit16 v1, v1, 0x3e8

    .line 85
    .line 86
    invoke-virtual {p1}, Landroid/view/View;->getLeft()I

    .line 87
    .line 88
    .line 89
    move-result v2

    .line 90
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslProgressBar;->getPaddingLeft()I

    .line 91
    .line 92
    .line 93
    move-result p1

    .line 94
    add-int/2addr p1, v2

    .line 95
    mul-int/2addr p2, v0

    .line 96
    div-int/2addr p2, v1

    .line 97
    add-int/2addr p2, p1

    .line 98
    int-to-float p1, p2

    .line 99
    iget-object p2, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 100
    .line 101
    invoke-virtual {p2}, Landroid/widget/TextView;->getWidth()I

    .line 102
    .line 103
    .line 104
    move-result p2

    .line 105
    int-to-float p2, p2

    .line 106
    const/high16 v0, 0x40000000    # 2.0f

    .line 107
    .line 108
    div-float/2addr p2, v0

    .line 109
    sub-float p2, p1, p2

    .line 110
    .line 111
    const/4 v0, 0x0

    .line 112
    cmpg-float p2, p2, v0

    .line 113
    .line 114
    if-gez p2, :cond_1

    .line 115
    .line 116
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 117
    .line 118
    invoke-virtual {p0, v0}, Landroid/widget/TextView;->setX(F)V

    .line 119
    .line 120
    .line 121
    goto :goto_0

    .line 122
    :cond_1
    iget-object p2, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 123
    .line 124
    invoke-virtual {p2}, Landroid/widget/TextView;->getWidth()I

    .line 125
    .line 126
    .line 127
    move-result p2

    .line 128
    div-int/lit8 p2, p2, 0x2

    .line 129
    .line 130
    int-to-float p2, p2

    .line 131
    add-float/2addr p2, p1

    .line 132
    invoke-static {p3}, Lcom/android/wm/shell/controlpanel/utils/ControlPanelUtils;->getDisplayX(Landroid/content/Context;)I

    .line 133
    .line 134
    .line 135
    move-result v0

    .line 136
    int-to-float v0, v0

    .line 137
    cmpl-float p2, p2, v0

    .line 138
    .line 139
    if-lez p2, :cond_2

    .line 140
    .line 141
    iget-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 142
    .line 143
    invoke-static {p3}, Lcom/android/wm/shell/controlpanel/utils/ControlPanelUtils;->getDisplayX(Landroid/content/Context;)I

    .line 144
    .line 145
    .line 146
    move-result p2

    .line 147
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 148
    .line 149
    invoke-virtual {p0}, Landroid/widget/TextView;->getWidth()I

    .line 150
    .line 151
    .line 152
    move-result p0

    .line 153
    sub-int/2addr p2, p0

    .line 154
    int-to-float p0, p2

    .line 155
    invoke-virtual {p1, p0}, Landroid/widget/TextView;->setX(F)V

    .line 156
    .line 157
    .line 158
    goto :goto_0

    .line 159
    :cond_2
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 160
    .line 161
    invoke-virtual {p0}, Landroid/widget/TextView;->getWidth()I

    .line 162
    .line 163
    .line 164
    move-result p2

    .line 165
    div-int/lit8 p2, p2, 0x2

    .line 166
    .line 167
    int-to-float p2, p2

    .line 168
    sub-float/2addr p1, p2

    .line 169
    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setX(F)V

    .line 170
    .line 171
    .line 172
    :cond_3
    :goto_0
    return-void
.end method

.method public final onStartTrackingTouch(Landroidx/appcompat/widget/SeslSeekBar;)V
    .locals 2

    .line 1
    const-string p1, "FlexMediaPanel"

    .line 2
    .line 3
    const-string v0, "MediaPanel mSeekBarControlListener onStartTrackingTouch"

    .line 4
    .line 5
    invoke-static {p1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 6
    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel$1;->this$0:Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;

    .line 9
    .line 10
    iget-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mHandler:Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel$H;

    .line 11
    .line 12
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mUpdateTimer:Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel$$ExternalSyntheticLambda0;

    .line 13
    .line 14
    invoke-virtual {p1, v0}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 15
    .line 16
    .line 17
    iget-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 18
    .line 19
    if-nez p1, :cond_0

    .line 20
    .line 21
    return-void

    .line 22
    :cond_0
    invoke-virtual {p1}, Landroid/media/session/MediaController;->getMetadata()Landroid/media/MediaMetadata;

    .line 23
    .line 24
    .line 25
    move-result-object p1

    .line 26
    if-eqz p1, :cond_1

    .line 27
    .line 28
    const-string v0, "android.media.metadata.DURATION"

    .line 29
    .line 30
    invoke-virtual {p1, v0}, Landroid/media/MediaMetadata;->getLong(Ljava/lang/String;)J

    .line 31
    .line 32
    .line 33
    move-result-wide v0

    .line 34
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mMediaSeekBarView:Landroidx/appcompat/widget/SeslSeekBar;

    .line 35
    .line 36
    long-to-int p1, v0

    .line 37
    invoke-virtual {p0, p1}, Landroidx/appcompat/widget/SeslAbsSeekBar;->setMax(I)V

    .line 38
    .line 39
    .line 40
    :cond_1
    return-void
.end method

.method public final onStopTrackingTouch(Landroidx/appcompat/widget/SeslSeekBar;)V
    .locals 3

    .line 1
    new-instance v0, Ljava/lang/StringBuilder;

    .line 2
    .line 3
    const-string v1, "MediaPanel mSeekBarControlListener onStopTrackingTouch : "

    .line 4
    .line 5
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslAbsSeekBar;->getProgress()I

    .line 9
    .line 10
    .line 11
    move-result v1

    .line 12
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 13
    .line 14
    .line 15
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 16
    .line 17
    .line 18
    move-result-object v0

    .line 19
    const-string v1, "FlexMediaPanel"

    .line 20
    .line 21
    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 22
    .line 23
    .line 24
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel$1;->this$0:Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;

    .line 25
    .line 26
    iget-object v0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 27
    .line 28
    if-nez v0, :cond_0

    .line 29
    .line 30
    return-void

    .line 31
    :cond_0
    invoke-virtual {v0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    .line 32
    .line 33
    .line 34
    move-result-object v0

    .line 35
    invoke-virtual {p1}, Landroidx/appcompat/widget/SeslAbsSeekBar;->getProgress()I

    .line 36
    .line 37
    .line 38
    move-result p1

    .line 39
    int-to-long v1, p1

    .line 40
    invoke-virtual {v0, v1, v2}, Landroid/media/session/MediaController$TransportControls;->seekTo(J)V

    .line 41
    .line 42
    .line 43
    const-wide/16 v0, 0x0

    .line 44
    .line 45
    invoke-virtual {p0, v0, v1}, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->checkPlaybackPosition(J)V

    .line 46
    .line 47
    .line 48
    sget-boolean p1, Lcom/samsung/android/rune/CoreRune;->MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING:Z

    .line 49
    .line 50
    if-eqz p1, :cond_1

    .line 51
    .line 52
    iget-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mMediaController:Landroid/media/session/MediaController;

    .line 53
    .line 54
    new-instance v0, Ljava/util/HashMap;

    .line 55
    .line 56
    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    .line 57
    .line 58
    .line 59
    const-string/jumbo v1, "packageName"

    .line 60
    .line 61
    .line 62
    invoke-virtual {p1}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    .line 63
    .line 64
    .line 65
    move-result-object p1

    .line 66
    invoke-virtual {v0, v1, p1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 67
    .line 68
    .line 69
    iget-object p1, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mContext:Landroid/content/Context;

    .line 70
    .line 71
    const v1, 0x7f130fa2

    .line 72
    .line 73
    .line 74
    invoke-virtual {p1, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 75
    .line 76
    .line 77
    move-result-object p1

    .line 78
    const-string v1, "F003"

    .line 79
    .line 80
    invoke-static {v1, p1, v0}, Lcom/android/wm/shell/controlpanel/utils/ControlPanelUtils;->eventLogging(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;)V

    .line 81
    .line 82
    .line 83
    :cond_1
    iget-object p0, p0, Lcom/android/wm/shell/controlpanel/activity/FlexMediaPanel;->mSeekBarText:Landroid/widget/TextView;

    .line 84
    .line 85
    const-string p1, ""

    .line 86
    .line 87
    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 88
    .line 89
    .line 90
    return-void
.end method