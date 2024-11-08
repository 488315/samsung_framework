.class public final Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/OnHeadsUpChangedListener;
.implements Lcom/android/systemui/plugins/statusbar/StatusBarStateController$StateListener;
.implements Lcom/android/systemui/shade/ShadeExpansionListener;
.implements Lcom/android/systemui/Dumpable;


# static fields
.field public static final delayedDozeAmount:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1;

.field public static final notificationVisibility:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$notificationVisibility$1;


# instance fields
.field public final bypassController:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

.field public collapsedEnoughToHide:Z

.field public delayedDozeAmountAnimator:Landroidx/core/animation/ObjectAnimator;

.field public delayedDozeAmountOverride:F

.field public final dozeAmountInterpolator:Landroid/view/animation/Interpolator;

.field public final dozeParameters:Lcom/android/systemui/statusbar/phone/DozeParameters;

.field public fullyAwake:Z

.field public hardDozeAmountOverride:Ljava/lang/Float;

.field public hardDozeAmountOverrideSource:Ljava/lang/String;

.field public inputEasedDozeAmount:F

.field public inputLinearDozeAmount:F

.field public final logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

.field public final mEntrySetToClearWhenFinished:Ljava/util/Set;

.field public final mHeadsUpManager:Lcom/android/systemui/statusbar/policy/HeadsUpManager;

.field public mLinearVisibilityAmount:F

.field public mNotificationsVisible:Z

.field public mNotificationsVisibleForExpansion:Z

.field public mStackScrollerController:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;

.field public mVisibilityAmount:F

.field public mVisibilityAnimator:Landroidx/core/animation/ObjectAnimator;

.field public mVisibilityInterpolator:Landroid/view/animation/Interpolator;

.field public notificationsFullyHidden:Z

.field public outputEasedDozeAmount:F

.field public outputLinearDozeAmount:F

.field public pulseExpanding:Z

.field public pulsing:Z

.field public final screenOffAnimationController:Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;

.field public state:I

.field public final statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

.field public final wakeUpListeners:Ljava/util/ArrayList;

.field public wakingUp:Z

.field public willWakeUp:Z


# direct methods
.method public static constructor <clinit>()V
    .locals 2

    .line 1
    new-instance v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    invoke-direct {v0, v1}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 5
    .line 6
    .line 7
    new-instance v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$notificationVisibility$1;

    .line 8
    .line 9
    invoke-direct {v0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$notificationVisibility$1;-><init>()V

    .line 10
    .line 11
    .line 12
    sput-object v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->notificationVisibility:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$notificationVisibility$1;

    .line 13
    .line 14
    new-instance v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1;

    .line 15
    .line 16
    invoke-direct {v0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1;-><init>()V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->delayedDozeAmount:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1;

    .line 20
    .line 21
    return-void
.end method

.method public constructor <init>(Lcom/android/systemui/dump/DumpManager;Lcom/android/systemui/statusbar/policy/HeadsUpManager;Lcom/android/systemui/plugins/statusbar/StatusBarStateController;Lcom/android/systemui/statusbar/phone/KeyguardBypassController;Lcom/android/systemui/statusbar/phone/DozeParameters;Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mHeadsUpManager:Lcom/android/systemui/statusbar/policy/HeadsUpManager;

    .line 5
    .line 6
    iput-object p3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 7
    .line 8
    iput-object p4, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->bypassController:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

    .line 9
    .line 10
    iput-object p5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->dozeParameters:Lcom/android/systemui/statusbar/phone/DozeParameters;

    .line 11
    .line 12
    iput-object p6, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->screenOffAnimationController:Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;

    .line 13
    .line 14
    iput-object p7, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 15
    .line 16
    sget-object p5, Lcom/android/app/animation/Interpolators;->FAST_OUT_SLOW_IN_REVERSE:Landroid/view/animation/Interpolator;

    .line 17
    .line 18
    iput-object p5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityInterpolator:Landroid/view/animation/Interpolator;

    .line 19
    .line 20
    const-string p5, "n/a"

    .line 21
    .line 22
    iput-object p5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverrideSource:Ljava/lang/String;

    .line 23
    .line 24
    sget-object p5, Lcom/android/app/animation/Interpolators;->FAST_OUT_SLOW_IN:Landroid/view/animation/Interpolator;

    .line 25
    .line 26
    iput-object p5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->dozeAmountInterpolator:Landroid/view/animation/Interpolator;

    .line 27
    .line 28
    new-instance p5, Ljava/util/LinkedHashSet;

    .line 29
    .line 30
    invoke-direct {p5}, Ljava/util/LinkedHashSet;-><init>()V

    .line 31
    .line 32
    .line 33
    iput-object p5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mEntrySetToClearWhenFinished:Ljava/util/Set;

    .line 34
    .line 35
    new-instance p5, Ljava/util/ArrayList;

    .line 36
    .line 37
    invoke-direct {p5}, Ljava/util/ArrayList;-><init>()V

    .line 38
    .line 39
    .line 40
    iput-object p5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->wakeUpListeners:Ljava/util/ArrayList;

    .line 41
    .line 42
    const/4 p6, 0x1

    .line 43
    iput p6, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 44
    .line 45
    new-instance p6, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$bypassStateChangedListener$1;

    .line 46
    .line 47
    invoke-direct {p6, p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$bypassStateChangedListener$1;-><init>(Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;)V

    .line 48
    .line 49
    .line 50
    invoke-virtual {p1, p0}, Lcom/android/systemui/dump/DumpManager;->registerDumpable(Lcom/android/systemui/Dumpable;)V

    .line 51
    .line 52
    .line 53
    invoke-virtual {p2, p0}, Lcom/android/systemui/statusbar/policy/HeadsUpManager;->addListener(Lcom/android/systemui/statusbar/policy/OnHeadsUpChangedListener;)V

    .line 54
    .line 55
    .line 56
    invoke-interface {p3, p0}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->addCallback(Lcom/android/systemui/plugins/statusbar/StatusBarStateController$StateListener;)V

    .line 57
    .line 58
    .line 59
    invoke-virtual {p4, p6}, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->registerOnBypassStateChangedListener(Lcom/android/systemui/statusbar/phone/KeyguardBypassController$OnBypassStateChangedListener;)V

    .line 60
    .line 61
    .line 62
    new-instance p1, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$1;

    .line 63
    .line 64
    invoke-direct {p1, p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$1;-><init>(Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;)V

    .line 65
    .line 66
    .line 67
    invoke-virtual {p5, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 68
    .line 69
    .line 70
    return-void
.end method

.method public static synthetic getDozeAmountInterpolator$annotations()V
    .locals 0

    .line 1
    return-void
.end method

.method public static synthetic getStatusBarState$annotations()V
    .locals 0

    .line 1
    return-void
.end method


# virtual methods
.method public final clearHardDozeAmountOverride()Z
    .locals 2

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverride:Ljava/lang/Float;

    .line 2
    .line 3
    if-nez v0, :cond_0

    .line 4
    .line 5
    const/4 p0, 0x0

    .line 6
    return p0

    .line 7
    :cond_0
    const/4 v0, 0x0

    .line 8
    iput-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverride:Ljava/lang/Float;

    .line 9
    .line 10
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverrideSource:Ljava/lang/String;

    .line 11
    .line 12
    const-string v1, "Cleared: "

    .line 13
    .line 14
    invoke-static {v1, v0}, Landroidx/constraintlayout/motion/widget/KeyAttributes$$ExternalSyntheticOutline0;->m(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    .line 15
    .line 16
    .line 17
    move-result-object v0

    .line 18
    iput-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverrideSource:Ljava/lang/String;

    .line 19
    .line 20
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateDozeAmount()V

    .line 21
    .line 22
    .line 23
    const/4 p0, 0x1

    .line 24
    return p0
.end method

.method public final dump(Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .locals 2

    .line 1
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->inputLinearDozeAmount:F

    .line 2
    .line 3
    new-instance v0, Ljava/lang/StringBuilder;

    .line 4
    .line 5
    const-string v1, "inputLinearDozeAmount: "

    .line 6
    .line 7
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 8
    .line 9
    .line 10
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 11
    .line 12
    .line 13
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 14
    .line 15
    .line 16
    move-result-object p2

    .line 17
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 18
    .line 19
    .line 20
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->inputEasedDozeAmount:F

    .line 21
    .line 22
    new-instance v0, Ljava/lang/StringBuilder;

    .line 23
    .line 24
    const-string v1, "inputEasedDozeAmount: "

    .line 25
    .line 26
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 27
    .line 28
    .line 29
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 30
    .line 31
    .line 32
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 33
    .line 34
    .line 35
    move-result-object p2

    .line 36
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 37
    .line 38
    .line 39
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->delayedDozeAmountOverride:F

    .line 40
    .line 41
    new-instance v0, Ljava/lang/StringBuilder;

    .line 42
    .line 43
    const-string v1, "delayedDozeAmountOverride: "

    .line 44
    .line 45
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 46
    .line 47
    .line 48
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 49
    .line 50
    .line 51
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 52
    .line 53
    .line 54
    move-result-object p2

    .line 55
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 56
    .line 57
    .line 58
    iget-object p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverride:Ljava/lang/Float;

    .line 59
    .line 60
    new-instance v0, Ljava/lang/StringBuilder;

    .line 61
    .line 62
    const-string v1, "hardDozeAmountOverride: "

    .line 63
    .line 64
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 65
    .line 66
    .line 67
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 68
    .line 69
    .line 70
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 71
    .line 72
    .line 73
    move-result-object p2

    .line 74
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 75
    .line 76
    .line 77
    iget-object p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverrideSource:Ljava/lang/String;

    .line 78
    .line 79
    const-string v0, "hardDozeAmountOverrideSource: "

    .line 80
    .line 81
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;Ljava/lang/String;Ljava/io/PrintWriter;)V

    .line 82
    .line 83
    .line 84
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 85
    .line 86
    new-instance v0, Ljava/lang/StringBuilder;

    .line 87
    .line 88
    const-string/jumbo v1, "outputLinearDozeAmount: "

    .line 89
    .line 90
    .line 91
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 92
    .line 93
    .line 94
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 95
    .line 96
    .line 97
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 98
    .line 99
    .line 100
    move-result-object p2

    .line 101
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 102
    .line 103
    .line 104
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputEasedDozeAmount:F

    .line 105
    .line 106
    new-instance v0, Ljava/lang/StringBuilder;

    .line 107
    .line 108
    const-string/jumbo v1, "outputEasedDozeAmount: "

    .line 109
    .line 110
    .line 111
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 112
    .line 113
    .line 114
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 115
    .line 116
    .line 117
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 118
    .line 119
    .line 120
    move-result-object p2

    .line 121
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 122
    .line 123
    .line 124
    const-string p2, "mNotificationVisibleAmount: 0.0"

    .line 125
    .line 126
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 127
    .line 128
    .line 129
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 130
    .line 131
    const-string v0, "mNotificationsVisible: "

    .line 132
    .line 133
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 134
    .line 135
    .line 136
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisibleForExpansion:Z

    .line 137
    .line 138
    const-string v0, "mNotificationsVisibleForExpansion: "

    .line 139
    .line 140
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 141
    .line 142
    .line 143
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityAmount:F

    .line 144
    .line 145
    new-instance v0, Ljava/lang/StringBuilder;

    .line 146
    .line 147
    const-string v1, "mVisibilityAmount: "

    .line 148
    .line 149
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 150
    .line 151
    .line 152
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 153
    .line 154
    .line 155
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 156
    .line 157
    .line 158
    move-result-object p2

    .line 159
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 160
    .line 161
    .line 162
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mLinearVisibilityAmount:F

    .line 163
    .line 164
    new-instance v0, Ljava/lang/StringBuilder;

    .line 165
    .line 166
    const-string v1, "mLinearVisibilityAmount: "

    .line 167
    .line 168
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 169
    .line 170
    .line 171
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    .line 172
    .line 173
    .line 174
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 175
    .line 176
    .line 177
    move-result-object p2

    .line 178
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 179
    .line 180
    .line 181
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->pulseExpanding:Z

    .line 182
    .line 183
    const-string/jumbo v0, "pulseExpanding: "

    .line 184
    .line 185
    .line 186
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 187
    .line 188
    .line 189
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 190
    .line 191
    invoke-static {p2}, Lcom/android/systemui/statusbar/StatusBarState;->toString(I)Ljava/lang/String;

    .line 192
    .line 193
    .line 194
    move-result-object p2

    .line 195
    const-string/jumbo v0, "state: "

    .line 196
    .line 197
    .line 198
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;Ljava/lang/String;Ljava/io/PrintWriter;)V

    .line 199
    .line 200
    .line 201
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->fullyAwake:Z

    .line 202
    .line 203
    const-string v0, "fullyAwake: "

    .line 204
    .line 205
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 206
    .line 207
    .line 208
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->wakingUp:Z

    .line 209
    .line 210
    const-string/jumbo v0, "wakingUp: "

    .line 211
    .line 212
    .line 213
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 214
    .line 215
    .line 216
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->willWakeUp:Z

    .line 217
    .line 218
    const-string/jumbo v0, "willWakeUp: "

    .line 219
    .line 220
    .line 221
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 222
    .line 223
    .line 224
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->collapsedEnoughToHide:Z

    .line 225
    .line 226
    const-string v0, "collapsedEnoughToHide: "

    .line 227
    .line 228
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 229
    .line 230
    .line 231
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->pulsing:Z

    .line 232
    .line 233
    const-string/jumbo v0, "pulsing: "

    .line 234
    .line 235
    .line 236
    invoke-static {v0, p2, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 237
    .line 238
    .line 239
    iget-boolean p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->notificationsFullyHidden:Z

    .line 240
    .line 241
    new-instance v0, Ljava/lang/StringBuilder;

    .line 242
    .line 243
    const-string v1, "notificationsFullyHidden: "

    .line 244
    .line 245
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 246
    .line 247
    .line 248
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 249
    .line 250
    .line 251
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 252
    .line 253
    .line 254
    move-result-object p2

    .line 255
    invoke-virtual {p1, p2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 256
    .line 257
    .line 258
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->getCanShowPulsingHuns()Z

    .line 259
    .line 260
    .line 261
    move-result p0

    .line 262
    const-string p2, "canShowPulsingHuns: "

    .line 263
    .line 264
    invoke-static {p2, p0, p1}, Lcom/android/keyguard/ActiveUnlockConfig$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/io/PrintWriter;)V

    .line 265
    .line 266
    .line 267
    return-void
.end method

.method public final getCanShowPulsingHuns()Z
    .locals 3

    .line 1
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->pulsing:Z

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->bypassController:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

    .line 4
    .line 5
    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->getBypassEnabled()Z

    .line 6
    .line 7
    .line 8
    move-result v1

    .line 9
    if-eqz v1, :cond_3

    .line 10
    .line 11
    const/4 v1, 0x0

    .line 12
    const/4 v2, 0x1

    .line 13
    if-nez v0, :cond_2

    .line 14
    .line 15
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->wakingUp:Z

    .line 16
    .line 17
    if-nez v0, :cond_0

    .line 18
    .line 19
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->willWakeUp:Z

    .line 20
    .line 21
    if-nez v0, :cond_0

    .line 22
    .line 23
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->fullyAwake:Z

    .line 24
    .line 25
    if-eqz v0, :cond_1

    .line 26
    .line 27
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 28
    .line 29
    invoke-interface {v0}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->getState()I

    .line 30
    .line 31
    .line 32
    move-result v0

    .line 33
    if-ne v0, v2, :cond_1

    .line 34
    .line 35
    goto :goto_0

    .line 36
    :cond_1
    move v0, v1

    .line 37
    goto :goto_1

    .line 38
    :cond_2
    :goto_0
    move v0, v2

    .line 39
    :goto_1
    iget-boolean p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->collapsedEnoughToHide:Z

    .line 40
    .line 41
    if-eqz p0, :cond_3

    .line 42
    .line 43
    move v0, v1

    .line 44
    :cond_3
    return v0
.end method

.method public final logDelayingClockWakeUpAnimation(Z)V
    .locals 4

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    sget-object v0, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 7
    .line 8
    sget-object v1, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logDelayingClockWakeUpAnimation$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logDelayingClockWakeUpAnimation$2;

    .line 9
    .line 10
    const-string v2, "NotificationWakeUpCoordinator"

    .line 11
    .line 12
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 13
    .line 14
    const/4 v3, 0x0

    .line 15
    invoke-virtual {p0, v2, v0, v1, v3}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 16
    .line 17
    .line 18
    move-result-object v0

    .line 19
    invoke-interface {v0, p1}, Lcom/android/systemui/log/LogMessage;->setBool1(Z)V

    .line 20
    .line 21
    .line 22
    invoke-virtual {p0, v0}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 23
    .line 24
    .line 25
    return-void
.end method

.method public final maybeClearHardDozeAmountOverrideHidingNotifs()V
    .locals 10

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverride:Ljava/lang/Float;

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    const/4 v2, 0x0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    .line 8
    .line 9
    .line 10
    move-result v0

    .line 11
    const/high16 v3, 0x3f800000    # 1.0f

    .line 12
    .line 13
    cmpl-float v0, v0, v3

    .line 14
    .line 15
    if-nez v0, :cond_0

    .line 16
    .line 17
    move v0, v1

    .line 18
    goto :goto_0

    .line 19
    :cond_0
    move v0, v2

    .line 20
    :goto_0
    if-eqz v0, :cond_4

    .line 21
    .line 22
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 23
    .line 24
    invoke-interface {v0}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->getState()I

    .line 25
    .line 26
    .line 27
    move-result v3

    .line 28
    if-ne v3, v1, :cond_1

    .line 29
    .line 30
    move v3, v1

    .line 31
    goto :goto_1

    .line 32
    :cond_1
    move v3, v2

    .line 33
    :goto_1
    invoke-interface {v0}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->isDozing()Z

    .line 34
    .line 35
    .line 36
    move-result v0

    .line 37
    iget-object v4, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->bypassController:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

    .line 38
    .line 39
    invoke-virtual {v4}, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->getBypassEnabled()Z

    .line 40
    .line 41
    .line 42
    move-result v4

    .line 43
    iget-object v5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->screenOffAnimationController:Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;

    .line 44
    .line 45
    invoke-virtual {v5}, Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;->overrideNotificationsFullyDozingOnKeyguard()Z

    .line 46
    .line 47
    .line 48
    move-result v5

    .line 49
    if-eqz v3, :cond_2

    .line 50
    .line 51
    if-nez v0, :cond_3

    .line 52
    .line 53
    :cond_2
    if-nez v4, :cond_3

    .line 54
    .line 55
    if-nez v5, :cond_3

    .line 56
    .line 57
    goto :goto_2

    .line 58
    :cond_3
    move v1, v2

    .line 59
    :goto_2
    iget-object v2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 60
    .line 61
    invoke-virtual {v2}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 62
    .line 63
    .line 64
    sget-object v6, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 65
    .line 66
    sget-object v7, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logMaybeClearHardDozeAmountOverrideHidingNotifs$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logMaybeClearHardDozeAmountOverrideHidingNotifs$2;

    .line 67
    .line 68
    const/4 v8, 0x0

    .line 69
    iget-object v2, v2, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 70
    .line 71
    const-string v9, "NotificationWakeUpCoordinator"

    .line 72
    .line 73
    invoke-virtual {v2, v9, v6, v7, v8}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 74
    .line 75
    .line 76
    move-result-object v6

    .line 77
    const-string/jumbo v7, "willRemove="

    .line 78
    .line 79
    .line 80
    const-string v8, " onKeyguard="

    .line 81
    .line 82
    const-string v9, " dozing="

    .line 83
    .line 84
    invoke-static {v7, v1, v8, v3, v9}, Lcom/android/keyguard/KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;->m(Ljava/lang/String;ZLjava/lang/String;ZLjava/lang/String;)Ljava/lang/StringBuilder;

    .line 85
    .line 86
    .line 87
    move-result-object v3

    .line 88
    const-string v7, " bypass="

    .line 89
    .line 90
    const-string v8, " animating="

    .line 91
    .line 92
    invoke-static {v3, v0, v7, v4, v8}, Lcom/android/keyguard/KeyguardFaceListenModel$$ExternalSyntheticOutline0;->m(Ljava/lang/StringBuilder;ZLjava/lang/String;ZLjava/lang/String;)V

    .line 93
    .line 94
    .line 95
    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 96
    .line 97
    .line 98
    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 99
    .line 100
    .line 101
    move-result-object v0

    .line 102
    invoke-interface {v6, v0}, Lcom/android/systemui/log/LogMessage;->setStr1(Ljava/lang/String;)V

    .line 103
    .line 104
    .line 105
    invoke-virtual {v2, v6}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 106
    .line 107
    .line 108
    if-eqz v1, :cond_4

    .line 109
    .line 110
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->clearHardDozeAmountOverride()Z

    .line 111
    .line 112
    .line 113
    :cond_4
    return-void
.end method

.method public final notifyAnimationStart(Z)V
    .locals 2

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mStackScrollerController:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;

    .line 2
    .line 3
    if-nez p0, :cond_0

    .line 4
    .line 5
    const/4 p0, 0x0

    .line 6
    :cond_0
    xor-int/lit8 p1, p1, 0x1

    .line 7
    .line 8
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;->mView:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;

    .line 9
    .line 10
    iget v0, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mInterpolatedHideAmount:F

    .line 11
    .line 12
    const/4 v1, 0x0

    .line 13
    cmpl-float v1, v0, v1

    .line 14
    .line 15
    if-eqz v1, :cond_1

    .line 16
    .line 17
    const/high16 v1, 0x3f800000    # 1.0f

    .line 18
    .line 19
    cmpl-float v0, v0, v1

    .line 20
    .line 21
    if-nez v0, :cond_4

    .line 22
    .line 23
    :cond_1
    if-eqz p1, :cond_2

    .line 24
    .line 25
    const v0, 0x3fe66666    # 1.8f

    .line 26
    .line 27
    .line 28
    goto :goto_0

    .line 29
    :cond_2
    const/high16 v0, 0x3fc00000    # 1.5f

    .line 30
    .line 31
    :goto_0
    iput v0, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mBackgroundXFactor:F

    .line 32
    .line 33
    if-eqz p1, :cond_3

    .line 34
    .line 35
    sget-object p1, Lcom/android/app/animation/Interpolators;->FAST_OUT_SLOW_IN_REVERSE:Landroid/view/animation/Interpolator;

    .line 36
    .line 37
    goto :goto_1

    .line 38
    :cond_3
    sget-object p1, Lcom/android/app/animation/Interpolators;->FAST_OUT_SLOW_IN:Landroid/view/animation/Interpolator;

    .line 39
    .line 40
    :goto_1
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mHideXInterpolator:Landroid/view/animation/Interpolator;

    .line 41
    .line 42
    :cond_4
    return-void
.end method

.method public final onDozeAmountChanged(FF)V
    .locals 7

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    const/high16 v1, 0x3f800000    # 1.0f

    .line 7
    .line 8
    cmpg-float v1, p1, v1

    .line 9
    .line 10
    const/4 v2, 0x1

    .line 11
    const/4 v3, 0x0

    .line 12
    if-nez v1, :cond_0

    .line 13
    .line 14
    move v1, v2

    .line 15
    goto :goto_0

    .line 16
    :cond_0
    move v1, v3

    .line 17
    :goto_0
    if-nez v1, :cond_2

    .line 18
    .line 19
    const/4 v1, 0x0

    .line 20
    cmpg-float v1, p1, v1

    .line 21
    .line 22
    if-nez v1, :cond_1

    .line 23
    .line 24
    move v1, v2

    .line 25
    goto :goto_1

    .line 26
    :cond_1
    move v1, v3

    .line 27
    :goto_1
    if-nez v1, :cond_2

    .line 28
    .line 29
    move v1, v2

    .line 30
    goto :goto_2

    .line 31
    :cond_2
    move v1, v3

    .line 32
    :goto_2
    iget-boolean v4, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastOnDozeAmountChangedLogWasFractional:Z

    .line 33
    .line 34
    if-eqz v4, :cond_3

    .line 35
    .line 36
    if-eqz v1, :cond_3

    .line 37
    .line 38
    iget-boolean v4, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->allowThrottle:Z

    .line 39
    .line 40
    if-eqz v4, :cond_3

    .line 41
    .line 42
    goto :goto_3

    .line 43
    :cond_3
    iput-boolean v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastOnDozeAmountChangedLogWasFractional:Z

    .line 44
    .line 45
    sget-object v1, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 46
    .line 47
    sget-object v4, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logOnDozeAmountChanged$2;

    .line 48
    .line 49
    const/4 v5, 0x0

    .line 50
    iget-object v0, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 51
    .line 52
    const-string v6, "NotificationWakeUpCoordinator"

    .line 53
    .line 54
    invoke-virtual {v0, v6, v1, v4, v5}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 55
    .line 56
    .line 57
    move-result-object v1

    .line 58
    float-to-double v4, p1

    .line 59
    invoke-interface {v1, v4, v5}, Lcom/android/systemui/log/LogMessage;->setDouble1(D)V

    .line 60
    .line 61
    .line 62
    invoke-static {p2}, Ljava/lang/String;->valueOf(F)Ljava/lang/String;

    .line 63
    .line 64
    .line 65
    move-result-object v4

    .line 66
    invoke-interface {v1, v4}, Lcom/android/systemui/log/LogMessage;->setStr2(Ljava/lang/String;)V

    .line 67
    .line 68
    .line 69
    invoke-virtual {v0, v1}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 70
    .line 71
    .line 72
    :goto_3
    iput p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->inputLinearDozeAmount:F

    .line 73
    .line 74
    iput p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->inputEasedDozeAmount:F

    .line 75
    .line 76
    iget-object p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->screenOffAnimationController:Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;

    .line 77
    .line 78
    invoke-virtual {p1}, Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;->overrideNotificationsFullyDozingOnKeyguard()Z

    .line 79
    .line 80
    .line 81
    move-result p1

    .line 82
    if-eqz p1, :cond_4

    .line 83
    .line 84
    const-string p1, "Override: animating screen off"

    .line 85
    .line 86
    invoke-virtual {p0, p1, v2}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setHardDozeAmountOverride(Ljava/lang/String;Z)V

    .line 87
    .line 88
    .line 89
    goto :goto_4

    .line 90
    :cond_4
    move v2, v3

    .line 91
    :goto_4
    if-eqz v2, :cond_5

    .line 92
    .line 93
    return-void

    .line 94
    :cond_5
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->overrideDozeAmountIfBypass()Z

    .line 95
    .line 96
    .line 97
    move-result p1

    .line 98
    if-eqz p1, :cond_6

    .line 99
    .line 100
    return-void

    .line 101
    :cond_6
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->clearHardDozeAmountOverride()Z

    .line 102
    .line 103
    .line 104
    move-result p1

    .line 105
    if-eqz p1, :cond_7

    .line 106
    .line 107
    return-void

    .line 108
    :cond_7
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateDozeAmount()V

    .line 109
    .line 110
    .line 111
    return-void
.end method

.method public final onDozingChanged(Z)V
    .locals 0

    .line 1
    if-eqz p1, :cond_0

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    invoke-virtual {p0, p1, p1, p1}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setNotificationsVisible(ZZZ)V

    .line 5
    .line 6
    .line 7
    :cond_0
    return-void
.end method

.method public final onHeadsUpStateChanged(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;Z)V
    .locals 5

    .line 1
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->shouldAnimateVisibility()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mEntrySetToClearWhenFinished:Ljava/util/Set;

    .line 6
    .line 7
    const/4 v2, 0x0

    .line 8
    if-nez p2, :cond_5

    .line 9
    .line 10
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 11
    .line 12
    const/4 v3, 0x0

    .line 13
    cmpg-float p2, p2, v3

    .line 14
    .line 15
    const/4 v4, 0x1

    .line 16
    if-nez p2, :cond_0

    .line 17
    .line 18
    move p2, v4

    .line 19
    goto :goto_0

    .line 20
    :cond_0
    move p2, v2

    .line 21
    :goto_0
    if-nez p2, :cond_6

    .line 22
    .line 23
    iget p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mLinearVisibilityAmount:F

    .line 24
    .line 25
    cmpg-float p2, p2, v3

    .line 26
    .line 27
    if-nez p2, :cond_1

    .line 28
    .line 29
    move p2, v4

    .line 30
    goto :goto_1

    .line 31
    :cond_1
    move p2, v2

    .line 32
    :goto_1
    if-nez p2, :cond_6

    .line 33
    .line 34
    iget-object p2, p1, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->row:Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;

    .line 35
    .line 36
    if-eqz p2, :cond_2

    .line 37
    .line 38
    iget-boolean v3, p2, Lcom/android/systemui/statusbar/notification/row/ActivatableNotificationView;->mDismissed:Z

    .line 39
    .line 40
    if-eqz v3, :cond_2

    .line 41
    .line 42
    move v3, v4

    .line 43
    goto :goto_2

    .line 44
    :cond_2
    move v3, v2

    .line 45
    :goto_2
    if-eqz v3, :cond_3

    .line 46
    .line 47
    move v0, v2

    .line 48
    goto :goto_3

    .line 49
    :cond_3
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->wakingUp:Z

    .line 50
    .line 51
    if-nez v3, :cond_6

    .line 52
    .line 53
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->willWakeUp:Z

    .line 54
    .line 55
    if-nez v3, :cond_6

    .line 56
    .line 57
    if-eqz p2, :cond_4

    .line 58
    .line 59
    invoke-virtual {p2, v4}, Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;->setHeadsUpAnimatingAway(Z)V

    .line 60
    .line 61
    .line 62
    :cond_4
    invoke-interface {v1, p1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 63
    .line 64
    .line 65
    goto :goto_3

    .line 66
    :cond_5
    invoke-interface {v1, p1}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    .line 67
    .line 68
    .line 69
    move-result p2

    .line 70
    if-eqz p2, :cond_6

    .line 71
    .line 72
    invoke-interface {v1, p1}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    .line 73
    .line 74
    .line 75
    iget-object p1, p1, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->row:Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;

    .line 76
    .line 77
    if-eqz p1, :cond_6

    .line 78
    .line 79
    invoke-virtual {p1, v2}, Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;->setHeadsUpAnimatingAway(Z)V

    .line 80
    .line 81
    .line 82
    :cond_6
    :goto_3
    invoke-virtual {p0, v0, v2}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateNotificationVisibility(ZZ)V

    .line 83
    .line 84
    .line 85
    return-void
.end method

.method public final onPanelExpansionChanged(Lcom/android/systemui/shade/ShadeExpansionChangeEvent;)V
    .locals 2

    .line 1
    const v0, 0x3f666666    # 0.9f

    .line 2
    .line 3
    .line 4
    iget p1, p1, Lcom/android/systemui/shade/ShadeExpansionChangeEvent;->fraction:F

    .line 5
    .line 6
    cmpg-float p1, p1, v0

    .line 7
    .line 8
    const/4 v0, 0x1

    .line 9
    if-gtz p1, :cond_0

    .line 10
    .line 11
    move p1, v0

    .line 12
    goto :goto_0

    .line 13
    :cond_0
    const/4 p1, 0x0

    .line 14
    :goto_0
    iget-boolean v1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->collapsedEnoughToHide:Z

    .line 15
    .line 16
    if-eq p1, v1, :cond_1

    .line 17
    .line 18
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->getCanShowPulsingHuns()Z

    .line 19
    .line 20
    .line 21
    move-result v1

    .line 22
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->collapsedEnoughToHide:Z

    .line 23
    .line 24
    if-eqz v1, :cond_1

    .line 25
    .line 26
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->getCanShowPulsingHuns()Z

    .line 27
    .line 28
    .line 29
    move-result p1

    .line 30
    if-nez p1, :cond_1

    .line 31
    .line 32
    invoke-virtual {p0, v0, v0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateNotificationVisibility(ZZ)V

    .line 33
    .line 34
    .line 35
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mHeadsUpManager:Lcom/android/systemui/statusbar/policy/HeadsUpManager;

    .line 36
    .line 37
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/AlertingNotificationManager;->releaseAllImmediately()V

    .line 38
    .line 39
    .line 40
    :cond_1
    return-void
.end method

.method public final onStateChanged(I)V
    .locals 6

    .line 1
    iget v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 4
    .line 5
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 6
    .line 7
    .line 8
    sget-object v2, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 9
    .line 10
    sget-object v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logOnStateChanged$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logOnStateChanged$2;

    .line 11
    .line 12
    const/4 v4, 0x0

    .line 13
    iget-object v1, v1, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 14
    .line 15
    const-string v5, "NotificationWakeUpCoordinator"

    .line 16
    .line 17
    invoke-virtual {v1, v5, v2, v3, v4}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 18
    .line 19
    .line 20
    move-result-object v2

    .line 21
    invoke-interface {v2, p1}, Lcom/android/systemui/log/LogMessage;->setInt1(I)V

    .line 22
    .line 23
    .line 24
    invoke-interface {v2, v0}, Lcom/android/systemui/log/LogMessage;->setInt2(I)V

    .line 25
    .line 26
    .line 27
    invoke-virtual {v1, v2}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 28
    .line 29
    .line 30
    iget v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 31
    .line 32
    const/4 v1, 0x0

    .line 33
    if-nez v0, :cond_0

    .line 34
    .line 35
    if-nez p1, :cond_0

    .line 36
    .line 37
    const-string v0, "Override: Shade->Shade (lock cancelled by unlock)"

    .line 38
    .line 39
    invoke-virtual {p0, v0, v1}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setHardDozeAmountOverride(Ljava/lang/String;Z)V

    .line 40
    .line 41
    .line 42
    iput p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 43
    .line 44
    return-void

    .line 45
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->screenOffAnimationController:Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;

    .line 46
    .line 47
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/ScreenOffAnimationController;->overrideNotificationsFullyDozingOnKeyguard()Z

    .line 48
    .line 49
    .line 50
    move-result v0

    .line 51
    if-eqz v0, :cond_1

    .line 52
    .line 53
    const-string v0, "Override: animating screen off"

    .line 54
    .line 55
    const/4 v1, 0x1

    .line 56
    invoke-virtual {p0, v0, v1}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setHardDozeAmountOverride(Ljava/lang/String;Z)V

    .line 57
    .line 58
    .line 59
    :cond_1
    if-eqz v1, :cond_2

    .line 60
    .line 61
    iput p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 62
    .line 63
    return-void

    .line 64
    :cond_2
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->overrideDozeAmountIfBypass()Z

    .line 65
    .line 66
    .line 67
    move-result v0

    .line 68
    if-eqz v0, :cond_3

    .line 69
    .line 70
    iput p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 71
    .line 72
    return-void

    .line 73
    :cond_3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->maybeClearHardDozeAmountOverrideHidingNotifs()V

    .line 74
    .line 75
    .line 76
    iput p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->state:I

    .line 77
    .line 78
    return-void
.end method

.method public final overrideDozeAmountIfBypass()Z
    .locals 3

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->bypassController:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

    .line 2
    .line 3
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->getBypassEnabled()Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    const/4 v1, 0x0

    .line 8
    if-eqz v0, :cond_1

    .line 9
    .line 10
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 11
    .line 12
    invoke-interface {v0}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->getState()I

    .line 13
    .line 14
    .line 15
    move-result v0

    .line 16
    const/4 v2, 0x1

    .line 17
    if-ne v0, v2, :cond_0

    .line 18
    .line 19
    const-string v0, "Override: bypass (keyguard)"

    .line 20
    .line 21
    invoke-virtual {p0, v0, v2}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setHardDozeAmountOverride(Ljava/lang/String;Z)V

    .line 22
    .line 23
    .line 24
    goto :goto_0

    .line 25
    :cond_0
    const-string v0, "Override: bypass (shade)"

    .line 26
    .line 27
    invoke-virtual {p0, v0, v1}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setHardDozeAmountOverride(Ljava/lang/String;Z)V

    .line 28
    .line 29
    .line 30
    :goto_0
    return v2

    .line 31
    :cond_1
    return v1
.end method

.method public final setHardDozeAmountOverride(Ljava/lang/String;Z)V
    .locals 5

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    sget-object v1, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 7
    .line 8
    sget-object v2, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetDozeAmountOverride$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetDozeAmountOverride$2;

    .line 9
    .line 10
    const/4 v3, 0x0

    .line 11
    iget-object v0, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 12
    .line 13
    const-string v4, "NotificationWakeUpCoordinator"

    .line 14
    .line 15
    invoke-virtual {v0, v4, v1, v2, v3}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 16
    .line 17
    .line 18
    move-result-object v1

    .line 19
    invoke-interface {v1, p2}, Lcom/android/systemui/log/LogMessage;->setBool1(Z)V

    .line 20
    .line 21
    .line 22
    invoke-interface {v1, p1}, Lcom/android/systemui/log/LogMessage;->setStr1(Ljava/lang/String;)V

    .line 23
    .line 24
    .line 25
    invoke-virtual {v0, v1}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 26
    .line 27
    .line 28
    if-eqz p2, :cond_0

    .line 29
    .line 30
    const/high16 p2, 0x3f800000    # 1.0f

    .line 31
    .line 32
    goto :goto_0

    .line 33
    :cond_0
    const/4 p2, 0x0

    .line 34
    :goto_0
    invoke-static {p2}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    .line 35
    .line 36
    .line 37
    move-result-object p2

    .line 38
    iput-object p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverride:Ljava/lang/Float;

    .line 39
    .line 40
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverrideSource:Ljava/lang/String;

    .line 41
    .line 42
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateDozeAmount()V

    .line 43
    .line 44
    .line 45
    return-void
.end method

.method public final setNotificationsVisible(ZZZ)V
    .locals 2

    .line 1
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 2
    .line 3
    if-ne v0, p1, :cond_0

    .line 4
    .line 5
    return-void

    .line 6
    :cond_0
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 7
    .line 8
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityAnimator:Landroidx/core/animation/ObjectAnimator;

    .line 9
    .line 10
    if-eqz v0, :cond_1

    .line 11
    .line 12
    invoke-virtual {v0}, Landroidx/core/animation/ValueAnimator;->cancel()V

    .line 13
    .line 14
    .line 15
    :cond_1
    const/high16 v0, 0x3f800000    # 1.0f

    .line 16
    .line 17
    const/4 v1, 0x0

    .line 18
    if-eqz p2, :cond_5

    .line 19
    .line 20
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->notifyAnimationStart(Z)V

    .line 21
    .line 22
    .line 23
    iget-boolean p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 24
    .line 25
    if-eqz p1, :cond_2

    .line 26
    .line 27
    sget-object p2, Lcom/android/app/animation/Interpolators;->TOUCH_RESPONSE:Landroid/view/animation/Interpolator;

    .line 28
    .line 29
    goto :goto_0

    .line 30
    :cond_2
    sget-object p2, Lcom/android/app/animation/Interpolators;->FAST_OUT_SLOW_IN_REVERSE:Landroid/view/animation/Interpolator;

    .line 31
    .line 32
    :goto_0
    iput-object p2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityInterpolator:Landroid/view/animation/Interpolator;

    .line 33
    .line 34
    if-eqz p1, :cond_3

    .line 35
    .line 36
    goto :goto_1

    .line 37
    :cond_3
    move v0, v1

    .line 38
    :goto_1
    const/4 p1, 0x1

    .line 39
    new-array p1, p1, [F

    .line 40
    .line 41
    const/4 p2, 0x0

    .line 42
    aput v0, p1, p2

    .line 43
    .line 44
    sget-object p2, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->notificationVisibility:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$notificationVisibility$1;

    .line 45
    .line 46
    invoke-static {p0, p2, p1}, Landroidx/core/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Landroid/util/Property;[F)Landroidx/core/animation/ObjectAnimator;

    .line 47
    .line 48
    .line 49
    move-result-object p1

    .line 50
    sget-object p2, Lcom/android/app/animation/InterpolatorsAndroidX;->LINEAR:Landroidx/core/animation/LinearInterpolator;

    .line 51
    .line 52
    invoke-virtual {p1, p2}, Landroidx/core/animation/ValueAnimator;->setInterpolator(Landroidx/core/animation/Interpolator;)V

    .line 53
    .line 54
    .line 55
    const-wide/16 v0, 0x1f4

    .line 56
    .line 57
    if-eqz p3, :cond_4

    .line 58
    .line 59
    long-to-float p2, v0

    .line 60
    const/high16 p3, 0x3fc00000    # 1.5f

    .line 61
    .line 62
    div-float/2addr p2, p3

    .line 63
    float-to-long v0, p2

    .line 64
    :cond_4
    invoke-virtual {p1, v0, v1}, Landroidx/core/animation/ObjectAnimator;->setDuration(J)V

    .line 65
    .line 66
    .line 67
    invoke-virtual {p1}, Landroidx/core/animation/ObjectAnimator;->start()V

    .line 68
    .line 69
    .line 70
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityAnimator:Landroidx/core/animation/ObjectAnimator;

    .line 71
    .line 72
    goto :goto_3

    .line 73
    :cond_5
    if-eqz p1, :cond_6

    .line 74
    .line 75
    goto :goto_2

    .line 76
    :cond_6
    move v0, v1

    .line 77
    :goto_2
    invoke-virtual {p0, v0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setVisibilityAmount(F)V

    .line 78
    .line 79
    .line 80
    :goto_3
    return-void
.end method

.method public final setNotificationsVisibleForExpansion(ZZZ)V
    .locals 0

    .line 1
    iput-boolean p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisibleForExpansion:Z

    .line 2
    .line 3
    invoke-virtual {p0, p2, p3}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateNotificationVisibility(ZZ)V

    .line 4
    .line 5
    .line 6
    if-nez p1, :cond_0

    .line 7
    .line 8
    iget-boolean p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 9
    .line 10
    if-eqz p1, :cond_0

    .line 11
    .line 12
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mHeadsUpManager:Lcom/android/systemui/statusbar/policy/HeadsUpManager;

    .line 13
    .line 14
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/AlertingNotificationManager;->releaseAllImmediately()V

    .line 15
    .line 16
    .line 17
    :cond_0
    return-void
.end method

.method public final setVisibilityAmount(F)V
    .locals 8

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    const/high16 v1, 0x3f800000    # 1.0f

    .line 7
    .line 8
    cmpg-float v1, p1, v1

    .line 9
    .line 10
    const/4 v2, 0x0

    .line 11
    const/4 v3, 0x1

    .line 12
    if-nez v1, :cond_0

    .line 13
    .line 14
    move v1, v3

    .line 15
    goto :goto_0

    .line 16
    :cond_0
    move v1, v2

    .line 17
    :goto_0
    const/4 v4, 0x0

    .line 18
    if-nez v1, :cond_2

    .line 19
    .line 20
    cmpg-float v1, p1, v4

    .line 21
    .line 22
    if-nez v1, :cond_1

    .line 23
    .line 24
    move v1, v3

    .line 25
    goto :goto_1

    .line 26
    :cond_1
    move v1, v2

    .line 27
    :goto_1
    if-nez v1, :cond_2

    .line 28
    .line 29
    move v1, v3

    .line 30
    goto :goto_2

    .line 31
    :cond_2
    move v1, v2

    .line 32
    :goto_2
    iget-boolean v5, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetVisibilityAmountLogWasFractional:Z

    .line 33
    .line 34
    if-eqz v5, :cond_3

    .line 35
    .line 36
    if-eqz v1, :cond_3

    .line 37
    .line 38
    iget-boolean v5, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->allowThrottle:Z

    .line 39
    .line 40
    if-eqz v5, :cond_3

    .line 41
    .line 42
    goto :goto_3

    .line 43
    :cond_3
    iput-boolean v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetVisibilityAmountLogWasFractional:Z

    .line 44
    .line 45
    sget-object v1, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 46
    .line 47
    sget-object v5, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetVisibilityAmount$2;

    .line 48
    .line 49
    const/4 v6, 0x0

    .line 50
    iget-object v0, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 51
    .line 52
    const-string v7, "NotificationWakeUpCoordinator"

    .line 53
    .line 54
    invoke-virtual {v0, v7, v1, v5, v6}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 55
    .line 56
    .line 57
    move-result-object v1

    .line 58
    float-to-double v5, p1

    .line 59
    invoke-interface {v1, v5, v6}, Lcom/android/systemui/log/LogMessage;->setDouble1(D)V

    .line 60
    .line 61
    .line 62
    invoke-virtual {v0, v1}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 63
    .line 64
    .line 65
    :goto_3
    iput p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mLinearVisibilityAmount:F

    .line 66
    .line 67
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityInterpolator:Landroid/view/animation/Interpolator;

    .line 68
    .line 69
    invoke-interface {v0, p1}, Landroid/view/animation/Interpolator;->getInterpolation(F)F

    .line 70
    .line 71
    .line 72
    move-result p1

    .line 73
    iput p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityAmount:F

    .line 74
    .line 75
    iget p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 76
    .line 77
    cmpg-float p1, p1, v4

    .line 78
    .line 79
    if-nez p1, :cond_4

    .line 80
    .line 81
    move p1, v3

    .line 82
    goto :goto_4

    .line 83
    :cond_4
    move p1, v2

    .line 84
    :goto_4
    if-nez p1, :cond_6

    .line 85
    .line 86
    iget p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mLinearVisibilityAmount:F

    .line 87
    .line 88
    cmpg-float p1, p1, v4

    .line 89
    .line 90
    if-nez p1, :cond_5

    .line 91
    .line 92
    goto :goto_5

    .line 93
    :cond_5
    move v3, v2

    .line 94
    :goto_5
    if-eqz v3, :cond_9

    .line 95
    .line 96
    :cond_6
    iget-object p1, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mEntrySetToClearWhenFinished:Ljava/util/Set;

    .line 97
    .line 98
    invoke-interface {p1}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    .line 99
    .line 100
    .line 101
    move-result-object v0

    .line 102
    :cond_7
    :goto_6
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    .line 103
    .line 104
    .line 105
    move-result v1

    .line 106
    if-eqz v1, :cond_8

    .line 107
    .line 108
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 109
    .line 110
    .line 111
    move-result-object v1

    .line 112
    check-cast v1, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;

    .line 113
    .line 114
    iget-object v1, v1, Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;->row:Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;

    .line 115
    .line 116
    if-eqz v1, :cond_7

    .line 117
    .line 118
    invoke-virtual {v1, v2}, Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;->setHeadsUpAnimatingAway(Z)V

    .line 119
    .line 120
    .line 121
    goto :goto_6

    .line 122
    :cond_8
    invoke-interface {p1}, Ljava/util/Set;->clear()V

    .line 123
    .line 124
    .line 125
    :cond_9
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateHideAmount()V

    .line 126
    .line 127
    .line 128
    return-void
.end method

.method public final setWakingUp(ZZ)V
    .locals 16

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    move/from16 v1, p1

    .line 4
    .line 5
    move/from16 v2, p2

    .line 6
    .line 7
    iget-object v3, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 8
    .line 9
    invoke-virtual {v3}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 10
    .line 11
    .line 12
    sget-object v4, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 13
    .line 14
    sget-object v5, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetWakingUp$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetWakingUp$2;

    .line 15
    .line 16
    iget-object v3, v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 17
    .line 18
    const-string v6, "NotificationWakeUpCoordinator"

    .line 19
    .line 20
    const/4 v7, 0x0

    .line 21
    invoke-virtual {v3, v6, v4, v5, v7}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 22
    .line 23
    .line 24
    move-result-object v4

    .line 25
    invoke-interface {v4, v1}, Lcom/android/systemui/log/LogMessage;->setBool1(Z)V

    .line 26
    .line 27
    .line 28
    invoke-interface {v4, v2}, Lcom/android/systemui/log/LogMessage;->setBool2(Z)V

    .line 29
    .line 30
    .line 31
    invoke-virtual {v3, v4}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 32
    .line 33
    .line 34
    iput-boolean v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->wakingUp:Z

    .line 35
    .line 36
    const/4 v4, 0x0

    .line 37
    iput-boolean v4, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->willWakeUp:Z

    .line 38
    .line 39
    const/4 v8, 0x1

    .line 40
    if-eqz v1, :cond_a

    .line 41
    .line 42
    iget-boolean v9, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 43
    .line 44
    iget-object v10, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->bypassController:Lcom/android/systemui/statusbar/phone/KeyguardBypassController;

    .line 45
    .line 46
    if-eqz v9, :cond_9

    .line 47
    .line 48
    iget-boolean v9, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisibleForExpansion:Z

    .line 49
    .line 50
    if-nez v9, :cond_9

    .line 51
    .line 52
    invoke-virtual {v10}, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->getBypassEnabled()Z

    .line 53
    .line 54
    .line 55
    move-result v9

    .line 56
    if-nez v9, :cond_9

    .line 57
    .line 58
    iget-object v9, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mStackScrollerController:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;

    .line 59
    .line 60
    if-nez v9, :cond_0

    .line 61
    .line 62
    move-object v9, v7

    .line 63
    :cond_0
    iget-object v9, v9, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;->mView:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;

    .line 64
    .line 65
    invoke-virtual {v9}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->getFirstChildWithBackground()Lcom/android/systemui/statusbar/notification/row/ExpandableView;

    .line 66
    .line 67
    .line 68
    move-result-object v11

    .line 69
    if-eqz v11, :cond_2

    .line 70
    .line 71
    iget-boolean v12, v9, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mKeyguardBypassEnabled:Z

    .line 72
    .line 73
    if-eqz v12, :cond_1

    .line 74
    .line 75
    invoke-virtual {v11}, Lcom/android/systemui/statusbar/notification/row/ExpandableView;->getHeadsUpHeightWithoutHeader()I

    .line 76
    .line 77
    .line 78
    move-result v11

    .line 79
    goto :goto_0

    .line 80
    :cond_1
    invoke-virtual {v11}, Lcom/android/systemui/statusbar/notification/row/ExpandableView;->getCollapsedHeight()I

    .line 81
    .line 82
    .line 83
    move-result v11

    .line 84
    :goto_0
    int-to-float v11, v11

    .line 85
    goto :goto_1

    .line 86
    :cond_2
    const/4 v11, 0x0

    .line 87
    :goto_1
    invoke-virtual {v9, v11}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->setPulseHeight(F)F

    .line 88
    .line 89
    .line 90
    invoke-virtual {v9}, Landroid/view/ViewGroup;->getChildCount()I

    .line 91
    .line 92
    .line 93
    move-result v11

    .line 94
    const/high16 v12, -0x40800000    # -1.0f

    .line 95
    .line 96
    move v13, v4

    .line 97
    move v14, v8

    .line 98
    :goto_2
    if-ge v13, v11, :cond_8

    .line 99
    .line 100
    invoke-virtual {v9, v13}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->getChildAtIndex(I)Lcom/android/systemui/statusbar/notification/row/ExpandableView;

    .line 101
    .line 102
    .line 103
    move-result-object v15

    .line 104
    invoke-virtual {v15}, Landroid/widget/FrameLayout;->getVisibility()I

    .line 105
    .line 106
    .line 107
    move-result v5

    .line 108
    const/16 v7, 0x8

    .line 109
    .line 110
    if-ne v5, v7, :cond_3

    .line 111
    .line 112
    goto :goto_4

    .line 113
    :cond_3
    iget-object v5, v9, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mShelf:Lcom/android/systemui/statusbar/NotificationShelf;

    .line 114
    .line 115
    if-ne v15, v5, :cond_4

    .line 116
    .line 117
    move v5, v8

    .line 118
    goto :goto_3

    .line 119
    :cond_4
    move v5, v4

    .line 120
    :goto_3
    instance-of v7, v15, Lcom/android/systemui/statusbar/notification/row/ExpandableNotificationRow;

    .line 121
    .line 122
    if-nez v7, :cond_5

    .line 123
    .line 124
    if-nez v5, :cond_5

    .line 125
    .line 126
    goto :goto_4

    .line 127
    :cond_5
    invoke-virtual {v15}, Landroid/widget/FrameLayout;->getVisibility()I

    .line 128
    .line 129
    .line 130
    move-result v7

    .line 131
    if-nez v7, :cond_6

    .line 132
    .line 133
    if-nez v5, :cond_6

    .line 134
    .line 135
    if-eqz v14, :cond_7

    .line 136
    .line 137
    invoke-virtual {v15}, Landroid/widget/FrameLayout;->getTranslationY()F

    .line 138
    .line 139
    .line 140
    move-result v5

    .line 141
    iget v7, v15, Lcom/android/systemui/statusbar/notification/row/ExpandableView;->mActualHeight:I

    .line 142
    .line 143
    int-to-float v7, v7

    .line 144
    add-float/2addr v5, v7

    .line 145
    iget-object v7, v9, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mShelf:Lcom/android/systemui/statusbar/NotificationShelf;

    .line 146
    .line 147
    invoke-virtual {v7}, Landroid/widget/FrameLayout;->getHeight()I

    .line 148
    .line 149
    .line 150
    move-result v7

    .line 151
    int-to-float v7, v7

    .line 152
    sub-float/2addr v5, v7

    .line 153
    move v14, v4

    .line 154
    move v12, v5

    .line 155
    goto :goto_4

    .line 156
    :cond_6
    if-nez v14, :cond_7

    .line 157
    .line 158
    invoke-virtual {v15, v12}, Landroid/widget/FrameLayout;->setTranslationY(F)V

    .line 159
    .line 160
    .line 161
    :cond_7
    :goto_4
    add-int/lit8 v13, v13, 0x1

    .line 162
    .line 163
    const/4 v7, 0x0

    .line 164
    goto :goto_2

    .line 165
    :cond_8
    iput-boolean v8, v9, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mDimmedNeedsAnimation:Z

    .line 166
    .line 167
    :cond_9
    invoke-virtual {v10}, Lcom/android/systemui/statusbar/phone/KeyguardBypassController;->getBypassEnabled()Z

    .line 168
    .line 169
    .line 170
    move-result v5

    .line 171
    if-eqz v5, :cond_a

    .line 172
    .line 173
    iget-boolean v5, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 174
    .line 175
    if-nez v5, :cond_a

    .line 176
    .line 177
    invoke-virtual/range {p0 .. p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->shouldAnimateVisibility()Z

    .line 178
    .line 179
    .line 180
    move-result v5

    .line 181
    invoke-virtual {v0, v5, v4}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateNotificationVisibility(ZZ)V

    .line 182
    .line 183
    .line 184
    :cond_a
    if-eqz v1, :cond_d

    .line 185
    .line 186
    if-eqz v2, :cond_d

    .line 187
    .line 188
    iget-object v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->delayedDozeAmountAnimator:Landroidx/core/animation/ObjectAnimator;

    .line 189
    .line 190
    if-eqz v1, :cond_b

    .line 191
    .line 192
    move v1, v8

    .line 193
    goto :goto_5

    .line 194
    :cond_b
    move v1, v4

    .line 195
    :goto_5
    sget-object v2, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 196
    .line 197
    sget-object v5, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logStartDelayedDozeAmountAnimation$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logStartDelayedDozeAmountAnimation$2;

    .line 198
    .line 199
    const/4 v7, 0x0

    .line 200
    invoke-virtual {v3, v6, v2, v5, v7}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 201
    .line 202
    .line 203
    move-result-object v2

    .line 204
    invoke-interface {v2, v1}, Lcom/android/systemui/log/LogMessage;->setBool1(Z)V

    .line 205
    .line 206
    .line 207
    invoke-virtual {v3, v2}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 208
    .line 209
    .line 210
    if-eqz v1, :cond_c

    .line 211
    .line 212
    goto :goto_6

    .line 213
    :cond_c
    sget-object v1, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->delayedDozeAmount:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1;

    .line 214
    .line 215
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 216
    .line 217
    .line 218
    const/high16 v2, 0x3f800000    # 1.0f

    .line 219
    .line 220
    invoke-static {v0, v2}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1;->setValue(Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;F)V

    .line 221
    .line 222
    .line 223
    new-array v2, v8, [F

    .line 224
    .line 225
    const/4 v3, 0x0

    .line 226
    aput v3, v2, v4

    .line 227
    .line 228
    invoke-static {v0, v1, v2}, Landroidx/core/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Landroid/util/Property;[F)Landroidx/core/animation/ObjectAnimator;

    .line 229
    .line 230
    .line 231
    move-result-object v1

    .line 232
    sget-object v2, Lcom/android/app/animation/InterpolatorsAndroidX;->LINEAR:Landroidx/core/animation/LinearInterpolator;

    .line 233
    .line 234
    invoke-virtual {v1, v2}, Landroidx/core/animation/ValueAnimator;->setInterpolator(Landroidx/core/animation/Interpolator;)V

    .line 235
    .line 236
    .line 237
    const-wide/16 v2, 0x1f4

    .line 238
    .line 239
    invoke-virtual {v1, v2, v3}, Landroidx/core/animation/ObjectAnimator;->setDuration(J)V

    .line 240
    .line 241
    .line 242
    const-wide/16 v2, 0xfa

    .line 243
    .line 244
    invoke-virtual {v1, v2, v3}, Landroidx/core/animation/ValueAnimator;->setStartDelay(J)V

    .line 245
    .line 246
    .line 247
    new-instance v2, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnStart$1;

    .line 248
    .line 249
    invoke-direct {v2, v0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnStart$1;-><init>(Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;)V

    .line 250
    .line 251
    .line 252
    invoke-virtual {v1, v2}, Landroidx/core/animation/Animator;->addListener(Landroidx/core/animation/Animator$AnimatorListener;)V

    .line 253
    .line 254
    .line 255
    new-instance v2, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnEnd$1;

    .line 256
    .line 257
    invoke-direct {v2, v0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$scheduleDelayedDozeAmountAnimation$lambda$4$$inlined$doOnEnd$1;-><init>(Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;)V

    .line 258
    .line 259
    .line 260
    invoke-virtual {v1, v2}, Landroidx/core/animation/Animator;->addListener(Landroidx/core/animation/Animator$AnimatorListener;)V

    .line 261
    .line 262
    .line 263
    invoke-virtual {v1}, Landroidx/core/animation/ObjectAnimator;->start()V

    .line 264
    .line 265
    .line 266
    iput-object v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->delayedDozeAmountAnimator:Landroidx/core/animation/ObjectAnimator;

    .line 267
    .line 268
    :cond_d
    :goto_6
    return-void
.end method

.method public final shouldAnimateVisibility()Z
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->dozeParameters:Lcom/android/systemui/statusbar/phone/DozeParameters;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/DozeParameters;->getAlwaysOn()Z

    .line 4
    .line 5
    .line 6
    move-result v0

    .line 7
    if-eqz v0, :cond_0

    .line 8
    .line 9
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/DozeParameters;->getDisplayNeedsBlanking()Z

    .line 10
    .line 11
    .line 12
    move-result p0

    .line 13
    if-nez p0, :cond_0

    .line 14
    .line 15
    const/4 p0, 0x1

    .line 16
    goto :goto_0

    .line 17
    :cond_0
    const/4 p0, 0x0

    .line 18
    :goto_0
    return p0
.end method

.method public final updateDozeAmount()V
    .locals 17

    .line 1
    move-object/from16 v0, p0

    .line 2
    .line 3
    iget-object v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverride:Ljava/lang/Float;

    .line 4
    .line 5
    if-eqz v1, :cond_0

    .line 6
    .line 7
    invoke-virtual {v1}, Ljava/lang/Float;->floatValue()F

    .line 8
    .line 9
    .line 10
    move-result v1

    .line 11
    goto :goto_0

    .line 12
    :cond_0
    iget v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->inputLinearDozeAmount:F

    .line 13
    .line 14
    iget v2, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->delayedDozeAmountOverride:F

    .line 15
    .line 16
    invoke-static {v1, v2}, Ljava/lang/Math;->max(FF)F

    .line 17
    .line 18
    .line 19
    move-result v1

    .line 20
    :goto_0
    iget v2, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 21
    .line 22
    cmpg-float v3, v2, v1

    .line 23
    .line 24
    const/4 v4, 0x1

    .line 25
    const/4 v5, 0x0

    .line 26
    if-nez v3, :cond_1

    .line 27
    .line 28
    move v3, v4

    .line 29
    goto :goto_1

    .line 30
    :cond_1
    move v3, v5

    .line 31
    :goto_1
    xor-int/2addr v3, v4

    .line 32
    const/high16 v6, 0x3f800000    # 1.0f

    .line 33
    .line 34
    cmpg-float v7, v1, v6

    .line 35
    .line 36
    if-nez v7, :cond_2

    .line 37
    .line 38
    move v7, v4

    .line 39
    goto :goto_2

    .line 40
    :cond_2
    move v7, v5

    .line 41
    :goto_2
    const/4 v8, 0x0

    .line 42
    if-nez v7, :cond_8

    .line 43
    .line 44
    cmpg-float v7, v1, v8

    .line 45
    .line 46
    if-nez v7, :cond_3

    .line 47
    .line 48
    move v7, v4

    .line 49
    goto :goto_3

    .line 50
    :cond_3
    move v7, v5

    .line 51
    :goto_3
    if-nez v7, :cond_8

    .line 52
    .line 53
    cmpg-float v7, v2, v8

    .line 54
    .line 55
    if-nez v7, :cond_4

    .line 56
    .line 57
    move v7, v4

    .line 58
    goto :goto_4

    .line 59
    :cond_4
    move v7, v5

    .line 60
    :goto_4
    if-nez v7, :cond_6

    .line 61
    .line 62
    cmpg-float v7, v2, v6

    .line 63
    .line 64
    if-nez v7, :cond_5

    .line 65
    .line 66
    move v7, v4

    .line 67
    goto :goto_5

    .line 68
    :cond_5
    move v7, v5

    .line 69
    :goto_5
    if-eqz v7, :cond_8

    .line 70
    .line 71
    :cond_6
    cmpg-float v2, v2, v6

    .line 72
    .line 73
    if-nez v2, :cond_7

    .line 74
    .line 75
    move v2, v4

    .line 76
    goto :goto_6

    .line 77
    :cond_7
    move v2, v5

    .line 78
    :goto_6
    invoke-virtual {v0, v2}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->notifyAnimationStart(Z)V

    .line 79
    .line 80
    .line 81
    :cond_8
    iput v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 82
    .line 83
    iget-object v2, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->dozeAmountInterpolator:Landroid/view/animation/Interpolator;

    .line 84
    .line 85
    check-cast v2, Landroid/view/animation/PathInterpolator;

    .line 86
    .line 87
    invoke-virtual {v2, v1}, Landroid/view/animation/PathInterpolator;->getInterpolation(F)F

    .line 88
    .line 89
    .line 90
    move-result v1

    .line 91
    iput v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputEasedDozeAmount:F

    .line 92
    .line 93
    iget v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->inputLinearDozeAmount:F

    .line 94
    .line 95
    iget v2, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->delayedDozeAmountOverride:F

    .line 96
    .line 97
    iget-object v7, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->hardDozeAmountOverride:Ljava/lang/Float;

    .line 98
    .line 99
    iget v9, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 100
    .line 101
    iget-object v10, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->statusBarStateController:Lcom/android/systemui/plugins/statusbar/StatusBarStateController;

    .line 102
    .line 103
    invoke-interface {v10}, Lcom/android/systemui/plugins/statusbar/StatusBarStateController;->getState()I

    .line 104
    .line 105
    .line 106
    move-result v10

    .line 107
    iget-object v11, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 108
    .line 109
    invoke-virtual {v11}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 110
    .line 111
    .line 112
    cmpg-float v12, v1, v6

    .line 113
    .line 114
    if-nez v12, :cond_9

    .line 115
    .line 116
    move v12, v4

    .line 117
    goto :goto_7

    .line 118
    :cond_9
    move v12, v5

    .line 119
    :goto_7
    if-nez v12, :cond_b

    .line 120
    .line 121
    cmpg-float v12, v1, v8

    .line 122
    .line 123
    if-nez v12, :cond_a

    .line 124
    .line 125
    move v12, v4

    .line 126
    goto :goto_8

    .line 127
    :cond_a
    move v12, v5

    .line 128
    :goto_8
    if-nez v12, :cond_b

    .line 129
    .line 130
    move v12, v4

    .line 131
    goto :goto_9

    .line 132
    :cond_b
    move v12, v5

    .line 133
    :goto_9
    cmpg-float v13, v2, v6

    .line 134
    .line 135
    if-nez v13, :cond_c

    .line 136
    .line 137
    move v13, v4

    .line 138
    goto :goto_a

    .line 139
    :cond_c
    move v13, v5

    .line 140
    :goto_a
    if-nez v13, :cond_e

    .line 141
    .line 142
    cmpg-float v13, v2, v8

    .line 143
    .line 144
    if-nez v13, :cond_d

    .line 145
    .line 146
    move v13, v4

    .line 147
    goto :goto_b

    .line 148
    :cond_d
    move v13, v5

    .line 149
    :goto_b
    if-nez v13, :cond_e

    .line 150
    .line 151
    move v13, v4

    .line 152
    goto :goto_c

    .line 153
    :cond_e
    move v13, v5

    .line 154
    :goto_c
    const/4 v14, 0x0

    .line 155
    if-nez v12, :cond_f

    .line 156
    .line 157
    if-eqz v13, :cond_12

    .line 158
    .line 159
    :cond_f
    iget-boolean v15, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetDozeAmountLogInputWasFractional:Z

    .line 160
    .line 161
    if-ne v15, v12, :cond_12

    .line 162
    .line 163
    iget-boolean v15, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetDozeAmountLogDelayWasFractional:Z

    .line 164
    .line 165
    if-ne v15, v13, :cond_12

    .line 166
    .line 167
    iget v15, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetDozeAmountLogState:I

    .line 168
    .line 169
    if-ne v15, v10, :cond_12

    .line 170
    .line 171
    iget-object v15, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetHardOverride:Ljava/lang/Float;

    .line 172
    .line 173
    if-nez v15, :cond_10

    .line 174
    .line 175
    if-nez v7, :cond_11

    .line 176
    .line 177
    goto :goto_d

    .line 178
    :cond_10
    if-eqz v7, :cond_11

    .line 179
    .line 180
    invoke-virtual {v15}, Ljava/lang/Float;->floatValue()F

    .line 181
    .line 182
    .line 183
    move-result v15

    .line 184
    invoke-virtual {v7}, Ljava/lang/Float;->floatValue()F

    .line 185
    .line 186
    .line 187
    move-result v16

    .line 188
    cmpl-float v15, v15, v16

    .line 189
    .line 190
    if-nez v15, :cond_11

    .line 191
    .line 192
    :goto_d
    move v15, v4

    .line 193
    goto :goto_e

    .line 194
    :cond_11
    move v15, v5

    .line 195
    :goto_e
    if-eqz v15, :cond_12

    .line 196
    .line 197
    iget-boolean v15, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->allowThrottle:Z

    .line 198
    .line 199
    if-eqz v15, :cond_12

    .line 200
    .line 201
    goto :goto_f

    .line 202
    :cond_12
    iput-boolean v12, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetDozeAmountLogInputWasFractional:Z

    .line 203
    .line 204
    iput-boolean v13, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetDozeAmountLogDelayWasFractional:Z

    .line 205
    .line 206
    iput v10, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetDozeAmountLogState:I

    .line 207
    .line 208
    iput-object v7, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetHardOverride:Ljava/lang/Float;

    .line 209
    .line 210
    sget-object v12, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 211
    .line 212
    sget-object v13, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logUpdateDozeAmount$2;

    .line 213
    .line 214
    iget-object v11, v11, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 215
    .line 216
    const-string v15, "NotificationWakeUpCoordinator"

    .line 217
    .line 218
    invoke-virtual {v11, v15, v12, v13, v14}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 219
    .line 220
    .line 221
    move-result-object v12

    .line 222
    float-to-double v14, v1

    .line 223
    invoke-interface {v12, v14, v15}, Lcom/android/systemui/log/LogMessage;->setDouble1(D)V

    .line 224
    .line 225
    .line 226
    invoke-static {v7}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    .line 227
    .line 228
    .line 229
    move-result-object v1

    .line 230
    invoke-interface {v12, v1}, Lcom/android/systemui/log/LogMessage;->setStr1(Ljava/lang/String;)V

    .line 231
    .line 232
    .line 233
    invoke-static {v9}, Ljava/lang/String;->valueOf(F)Ljava/lang/String;

    .line 234
    .line 235
    .line 236
    move-result-object v1

    .line 237
    invoke-interface {v12, v1}, Lcom/android/systemui/log/LogMessage;->setStr2(Ljava/lang/String;)V

    .line 238
    .line 239
    .line 240
    invoke-static {v2}, Ljava/lang/String;->valueOf(F)Ljava/lang/String;

    .line 241
    .line 242
    .line 243
    move-result-object v1

    .line 244
    invoke-interface {v12, v1}, Lcom/android/systemui/log/LogMessage;->setStr3(Ljava/lang/String;)V

    .line 245
    .line 246
    .line 247
    invoke-interface {v12, v10}, Lcom/android/systemui/log/LogMessage;->setInt1(I)V

    .line 248
    .line 249
    .line 250
    invoke-interface {v12, v3}, Lcom/android/systemui/log/LogMessage;->setBool1(Z)V

    .line 251
    .line 252
    .line 253
    invoke-virtual {v11, v12}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 254
    .line 255
    .line 256
    :goto_f
    iget-object v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mStackScrollerController:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;

    .line 257
    .line 258
    if-nez v1, :cond_13

    .line 259
    .line 260
    const/4 v14, 0x0

    .line 261
    goto :goto_10

    .line 262
    :cond_13
    move-object v14, v1

    .line 263
    :goto_10
    iget v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputEasedDozeAmount:F

    .line 264
    .line 265
    iget-object v2, v14, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;->mView:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;

    .line 266
    .line 267
    iget-object v7, v2, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mAmbientState:Lcom/android/systemui/statusbar/notification/stack/AmbientState;

    .line 268
    .line 269
    iget v9, v7, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mDozeAmount:F

    .line 270
    .line 271
    cmpl-float v9, v1, v9

    .line 272
    .line 273
    if-eqz v9, :cond_15

    .line 274
    .line 275
    iput v1, v7, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mDozeAmount:F

    .line 276
    .line 277
    cmpl-float v9, v1, v8

    .line 278
    .line 279
    if-eqz v9, :cond_14

    .line 280
    .line 281
    cmpl-float v1, v1, v6

    .line 282
    .line 283
    if-nez v1, :cond_15

    .line 284
    .line 285
    :cond_14
    iget v1, v7, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mPulseHeight:F

    .line 286
    .line 287
    const v6, 0x47c35000    # 100000.0f

    .line 288
    .line 289
    .line 290
    cmpl-float v1, v6, v1

    .line 291
    .line 292
    if-eqz v1, :cond_15

    .line 293
    .line 294
    iput v6, v7, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mPulseHeight:F

    .line 295
    .line 296
    iget-object v1, v7, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mOnPulseHeightChangedListener:Ljava/lang/Runnable;

    .line 297
    .line 298
    if-eqz v1, :cond_15

    .line 299
    .line 300
    invoke-interface {v1}, Ljava/lang/Runnable;->run()V

    .line 301
    .line 302
    .line 303
    :cond_15
    invoke-virtual {v2}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->updateContinuousBackgroundDrawing()V

    .line 304
    .line 305
    .line 306
    invoke-virtual {v2, v5}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->updateStackPosition(Z)V

    .line 307
    .line 308
    .line 309
    invoke-virtual {v2}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->requestChildrenUpdate()V

    .line 310
    .line 311
    .line 312
    invoke-virtual/range {p0 .. p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->updateHideAmount()V

    .line 313
    .line 314
    .line 315
    if-eqz v3, :cond_17

    .line 316
    .line 317
    iget v1, v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 318
    .line 319
    cmpg-float v1, v1, v8

    .line 320
    .line 321
    if-nez v1, :cond_16

    .line 322
    .line 323
    goto :goto_11

    .line 324
    :cond_16
    move v4, v5

    .line 325
    :goto_11
    if-eqz v4, :cond_17

    .line 326
    .line 327
    invoke-virtual {v0, v5, v5, v5}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setNotificationsVisible(ZZZ)V

    .line 328
    .line 329
    .line 330
    invoke-virtual {v0, v5, v5, v5}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setNotificationsVisibleForExpansion(ZZZ)V

    .line 331
    .line 332
    .line 333
    :cond_17
    return-void
.end method

.method public final updateHideAmount()V
    .locals 11

    .line 1
    iget v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mLinearVisibilityAmount:F

    .line 2
    .line 3
    const/high16 v1, 0x3f800000    # 1.0f

    .line 4
    .line 5
    sub-float v0, v1, v0

    .line 6
    .line 7
    iget v2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 8
    .line 9
    invoke-static {v0, v2}, Ljava/lang/Math;->min(FF)F

    .line 10
    .line 11
    .line 12
    move-result v0

    .line 13
    iget v2, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mVisibilityAmount:F

    .line 14
    .line 15
    sub-float v2, v1, v2

    .line 16
    .line 17
    iget v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputEasedDozeAmount:F

    .line 18
    .line 19
    invoke-static {v2, v3}, Ljava/lang/Math;->min(FF)F

    .line 20
    .line 21
    .line 22
    move-result v2

    .line 23
    iget-object v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->logger:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;

    .line 24
    .line 25
    iget v4, v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetHideAmount:F

    .line 26
    .line 27
    cmpg-float v4, v4, v0

    .line 28
    .line 29
    const/4 v5, 0x0

    .line 30
    const/4 v6, 0x1

    .line 31
    if-nez v4, :cond_0

    .line 32
    .line 33
    move v4, v6

    .line 34
    goto :goto_0

    .line 35
    :cond_0
    move v4, v5

    .line 36
    :goto_0
    const/4 v7, 0x0

    .line 37
    iget-boolean v8, v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->allowThrottle:Z

    .line 38
    .line 39
    if-eqz v4, :cond_1

    .line 40
    .line 41
    if-eqz v8, :cond_1

    .line 42
    .line 43
    goto :goto_4

    .line 44
    :cond_1
    iput v0, v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetHideAmount:F

    .line 45
    .line 46
    cmpg-float v4, v0, v1

    .line 47
    .line 48
    if-nez v4, :cond_2

    .line 49
    .line 50
    move v4, v6

    .line 51
    goto :goto_1

    .line 52
    :cond_2
    move v4, v5

    .line 53
    :goto_1
    if-nez v4, :cond_4

    .line 54
    .line 55
    const/4 v4, 0x0

    .line 56
    cmpg-float v4, v0, v4

    .line 57
    .line 58
    if-nez v4, :cond_3

    .line 59
    .line 60
    move v4, v6

    .line 61
    goto :goto_2

    .line 62
    :cond_3
    move v4, v5

    .line 63
    :goto_2
    if-nez v4, :cond_4

    .line 64
    .line 65
    move v4, v6

    .line 66
    goto :goto_3

    .line 67
    :cond_4
    move v4, v5

    .line 68
    :goto_3
    iget-boolean v9, v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetHideAmountLogWasFractional:Z

    .line 69
    .line 70
    if-eqz v9, :cond_5

    .line 71
    .line 72
    if-eqz v4, :cond_5

    .line 73
    .line 74
    if-eqz v8, :cond_5

    .line 75
    .line 76
    goto :goto_4

    .line 77
    :cond_5
    iput-boolean v4, v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->lastSetHideAmountLogWasFractional:Z

    .line 78
    .line 79
    sget-object v4, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 80
    .line 81
    sget-object v8, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetHideAmount$2;->INSTANCE:Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger$logSetHideAmount$2;

    .line 82
    .line 83
    iget-object v3, v3, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinatorLogger;->buffer:Lcom/android/systemui/log/LogBuffer;

    .line 84
    .line 85
    const-string v9, "NotificationWakeUpCoordinator"

    .line 86
    .line 87
    invoke-virtual {v3, v9, v4, v8, v7}, Lcom/android/systemui/log/LogBuffer;->obtain(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)Lcom/android/systemui/log/LogMessage;

    .line 88
    .line 89
    .line 90
    move-result-object v4

    .line 91
    float-to-double v8, v0

    .line 92
    invoke-interface {v4, v8, v9}, Lcom/android/systemui/log/LogMessage;->setDouble1(D)V

    .line 93
    .line 94
    .line 95
    invoke-virtual {v3, v4}, Lcom/android/systemui/log/LogBuffer;->commit(Lcom/android/systemui/log/LogMessage;)V

    .line 96
    .line 97
    .line 98
    :goto_4
    iget-object v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mStackScrollerController:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;

    .line 99
    .line 100
    if-nez v3, :cond_6

    .line 101
    .line 102
    goto :goto_5

    .line 103
    :cond_6
    move-object v7, v3

    .line 104
    :goto_5
    iget-object v3, v7, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayoutController;->mView:Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;

    .line 105
    .line 106
    iput v0, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mLinearHideAmount:F

    .line 107
    .line 108
    iput v2, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mInterpolatedHideAmount:F

    .line 109
    .line 110
    iget-object v4, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mAmbientState:Lcom/android/systemui/statusbar/notification/stack/AmbientState;

    .line 111
    .line 112
    invoke-virtual {v4}, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->isFullyHidden()Z

    .line 113
    .line 114
    .line 115
    move-result v4

    .line 116
    iget-object v7, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mAmbientState:Lcom/android/systemui/statusbar/notification/stack/AmbientState;

    .line 117
    .line 118
    invoke-virtual {v7}, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->isHiddenAtAll()Z

    .line 119
    .line 120
    .line 121
    move-result v7

    .line 122
    iget-object v8, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mAmbientState:Lcom/android/systemui/statusbar/notification/stack/AmbientState;

    .line 123
    .line 124
    cmpl-float v9, v2, v1

    .line 125
    .line 126
    if-nez v9, :cond_7

    .line 127
    .line 128
    iget v9, v8, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mHideAmount:F

    .line 129
    .line 130
    cmpl-float v9, v9, v2

    .line 131
    .line 132
    if-eqz v9, :cond_7

    .line 133
    .line 134
    iget v9, v8, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mPulseHeight:F

    .line 135
    .line 136
    const v10, 0x47c35000    # 100000.0f

    .line 137
    .line 138
    .line 139
    cmpl-float v9, v10, v9

    .line 140
    .line 141
    if-eqz v9, :cond_7

    .line 142
    .line 143
    iput v10, v8, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mPulseHeight:F

    .line 144
    .line 145
    iget-object v9, v8, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mOnPulseHeightChangedListener:Ljava/lang/Runnable;

    .line 146
    .line 147
    if-eqz v9, :cond_7

    .line 148
    .line 149
    invoke-interface {v9}, Ljava/lang/Runnable;->run()V

    .line 150
    .line 151
    .line 152
    :cond_7
    iput v2, v8, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->mHideAmount:F

    .line 153
    .line 154
    iget-object v2, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mAmbientState:Lcom/android/systemui/statusbar/notification/stack/AmbientState;

    .line 155
    .line 156
    invoke-virtual {v2}, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->isFullyHidden()Z

    .line 157
    .line 158
    .line 159
    move-result v2

    .line 160
    iget-object v8, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mAmbientState:Lcom/android/systemui/statusbar/notification/stack/AmbientState;

    .line 161
    .line 162
    invoke-virtual {v8}, Lcom/android/systemui/statusbar/notification/stack/AmbientState;->isHiddenAtAll()Z

    .line 163
    .line 164
    .line 165
    move-result v8

    .line 166
    if-eq v2, v4, :cond_8

    .line 167
    .line 168
    invoke-virtual {v3}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->updateVisibility()V

    .line 169
    .line 170
    .line 171
    invoke-virtual {v3}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->resetAllSwipeState()V

    .line 172
    .line 173
    .line 174
    :cond_8
    if-nez v7, :cond_9

    .line 175
    .line 176
    if-eqz v8, :cond_9

    .line 177
    .line 178
    iget-object v9, v3, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->mSwipeHelper:Lcom/android/systemui/statusbar/notification/stack/NotificationSwipeHelper;

    .line 179
    .line 180
    invoke-virtual {v9, v6, v6}, Lcom/android/systemui/statusbar/notification/stack/NotificationSwipeHelper;->resetExposedMenuView(ZZ)V

    .line 181
    .line 182
    .line 183
    :cond_9
    if-ne v2, v4, :cond_a

    .line 184
    .line 185
    if-eq v7, v8, :cond_b

    .line 186
    .line 187
    :cond_a
    invoke-virtual {v3}, Landroid/view/ViewGroup;->invalidateOutline()V

    .line 188
    .line 189
    .line 190
    :cond_b
    invoke-virtual {v3}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->updateAlgorithmHeightAndPadding()V

    .line 191
    .line 192
    .line 193
    invoke-virtual {v3}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->updateBackgroundDimming()V

    .line 194
    .line 195
    .line 196
    invoke-virtual {v3}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->requestChildrenUpdate()V

    .line 197
    .line 198
    .line 199
    invoke-virtual {v3}, Lcom/android/systemui/statusbar/notification/stack/NotificationStackScrollLayout;->updateOwnTranslationZ()V

    .line 200
    .line 201
    .line 202
    cmpg-float v0, v0, v1

    .line 203
    .line 204
    if-nez v0, :cond_c

    .line 205
    .line 206
    move v5, v6

    .line 207
    :cond_c
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->notificationsFullyHidden:Z

    .line 208
    .line 209
    if-eq v0, v5, :cond_d

    .line 210
    .line 211
    iput-boolean v5, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->notificationsFullyHidden:Z

    .line 212
    .line 213
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->wakeUpListeners:Ljava/util/ArrayList;

    .line 214
    .line 215
    invoke-virtual {p0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 216
    .line 217
    .line 218
    move-result-object p0

    .line 219
    :goto_6
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    .line 220
    .line 221
    .line 222
    move-result v0

    .line 223
    if-eqz v0, :cond_d

    .line 224
    .line 225
    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 226
    .line 227
    .line 228
    move-result-object v0

    .line 229
    check-cast v0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$WakeUpListener;

    .line 230
    .line 231
    invoke-interface {v0, v5}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator$WakeUpListener;->onFullyHiddenChanged(Z)V

    .line 232
    .line 233
    .line 234
    goto :goto_6

    .line 235
    :cond_d
    return-void
.end method

.method public final updateNotificationVisibility(ZZ)V
    .locals 5

    .line 1
    iget-boolean v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisibleForExpansion:Z

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const/4 v2, 0x1

    .line 5
    if-nez v0, :cond_1

    .line 6
    .line 7
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mHeadsUpManager:Lcom/android/systemui/statusbar/policy/HeadsUpManager;

    .line 8
    .line 9
    iget-object v0, v0, Lcom/android/systemui/statusbar/AlertingNotificationManager;->mAlertEntries:Landroid/util/ArrayMap;

    .line 10
    .line 11
    invoke-virtual {v0}, Landroid/util/ArrayMap;->isEmpty()Z

    .line 12
    .line 13
    .line 14
    move-result v0

    .line 15
    xor-int/2addr v0, v2

    .line 16
    if-eqz v0, :cond_0

    .line 17
    .line 18
    goto :goto_0

    .line 19
    :cond_0
    move v0, v1

    .line 20
    goto :goto_1

    .line 21
    :cond_1
    :goto_0
    move v0, v2

    .line 22
    :goto_1
    if-eqz v0, :cond_2

    .line 23
    .line 24
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->getCanShowPulsingHuns()Z

    .line 25
    .line 26
    .line 27
    move-result v0

    .line 28
    if-eqz v0, :cond_2

    .line 29
    .line 30
    move v0, v2

    .line 31
    goto :goto_2

    .line 32
    :cond_2
    move v0, v1

    .line 33
    :goto_2
    if-nez v0, :cond_5

    .line 34
    .line 35
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->mNotificationsVisible:Z

    .line 36
    .line 37
    if-eqz v3, :cond_5

    .line 38
    .line 39
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->wakingUp:Z

    .line 40
    .line 41
    if-nez v3, :cond_3

    .line 42
    .line 43
    iget-boolean v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->willWakeUp:Z

    .line 44
    .line 45
    if-eqz v3, :cond_5

    .line 46
    .line 47
    :cond_3
    iget v3, p0, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->outputLinearDozeAmount:F

    .line 48
    .line 49
    const/4 v4, 0x0

    .line 50
    cmpg-float v3, v3, v4

    .line 51
    .line 52
    if-nez v3, :cond_4

    .line 53
    .line 54
    move v1, v2

    .line 55
    :cond_4
    if-nez v1, :cond_5

    .line 56
    .line 57
    return-void

    .line 58
    :cond_5
    invoke-virtual {p0, v0, p1, p2}, Lcom/android/systemui/statusbar/notification/NotificationWakeUpCoordinator;->setNotificationsVisible(ZZZ)V

    .line 59
    .line 60
    .line 61
    return-void
.end method
