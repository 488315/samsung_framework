.class public final synthetic Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/systemui/shared/condition/Monitor;

.field public final synthetic f$1:Lcom/android/systemui/shared/condition/Monitor$Subscription$Token;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/shared/condition/Monitor;Lcom/android/systemui/shared/condition/Monitor$Subscription$Token;I)V
    .locals 0

    .line 1
    iput p3, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/shared/condition/Monitor;

    .line 4
    .line 5
    iput-object p2, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->f$1:Lcom/android/systemui/shared/condition/Monitor$Subscription$Token;

    .line 6
    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 8
    .line 9
    .line 10
    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 5

    .line 1
    iget v0, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->$r8$classId:I

    .line 2
    .line 3
    const/4 v1, 0x3

    .line 4
    packed-switch v0, :pswitch_data_0

    .line 5
    .line 6
    .line 7
    goto :goto_1

    .line 8
    :pswitch_0
    iget-object v0, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/shared/condition/Monitor;

    .line 9
    .line 10
    iget-object p0, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->f$1:Lcom/android/systemui/shared/condition/Monitor$Subscription$Token;

    .line 11
    .line 12
    check-cast p1, Lcom/android/systemui/shared/condition/Condition;

    .line 13
    .line 14
    iget-object v2, v0, Lcom/android/systemui/shared/condition/Monitor;->mConditions:Ljava/util/HashMap;

    .line 15
    .line 16
    invoke-virtual {v2, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    .line 17
    .line 18
    .line 19
    move-result v3

    .line 20
    if-nez v3, :cond_2

    .line 21
    .line 22
    new-instance v3, Landroid/util/ArraySet;

    .line 23
    .line 24
    invoke-direct {v3}, Landroid/util/ArraySet;-><init>()V

    .line 25
    .line 26
    .line 27
    invoke-virtual {v2, p1, v3}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 28
    .line 29
    .line 30
    iget-object v3, p1, Lcom/android/systemui/shared/condition/Condition;->mTag:Ljava/lang/String;

    .line 31
    .line 32
    invoke-static {v3, v1}, Landroid/util/Log;->isLoggable(Ljava/lang/String;I)Z

    .line 33
    .line 34
    .line 35
    move-result v1

    .line 36
    if-eqz v1, :cond_0

    .line 37
    .line 38
    iget-object v1, p1, Lcom/android/systemui/shared/condition/Condition;->mTag:Ljava/lang/String;

    .line 39
    .line 40
    const-string v3, "adding callback"

    .line 41
    .line 42
    invoke-static {v1, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 43
    .line 44
    .line 45
    :cond_0
    iget-object v1, p1, Lcom/android/systemui/shared/condition/Condition;->mCallbacks:Ljava/util/ArrayList;

    .line 46
    .line 47
    new-instance v3, Ljava/lang/ref/WeakReference;

    .line 48
    .line 49
    iget-object v0, v0, Lcom/android/systemui/shared/condition/Monitor;->mConditionCallback:Lcom/android/systemui/shared/condition/Monitor$1;

    .line 50
    .line 51
    invoke-direct {v3, v0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    .line 52
    .line 53
    .line 54
    invoke-virtual {v1, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 55
    .line 56
    .line 57
    iget-boolean v1, p1, Lcom/android/systemui/shared/condition/Condition;->mStarted:Z

    .line 58
    .line 59
    const/4 v3, 0x1

    .line 60
    if-eqz v1, :cond_1

    .line 61
    .line 62
    iget-object v1, v0, Lcom/android/systemui/shared/condition/Monitor$1;->this$0:Lcom/android/systemui/shared/condition/Monitor;

    .line 63
    .line 64
    iget-object v1, v1, Lcom/android/systemui/shared/condition/Monitor;->mExecutor:Ljava/util/concurrent/Executor;

    .line 65
    .line 66
    new-instance v4, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda2;

    .line 67
    .line 68
    invoke-direct {v4, v3, v0, p1}, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda2;-><init>(ILjava/lang/Object;Ljava/lang/Object;)V

    .line 69
    .line 70
    .line 71
    invoke-interface {v1, v4}, Ljava/util/concurrent/Executor;->execute(Ljava/lang/Runnable;)V

    .line 72
    .line 73
    .line 74
    goto :goto_0

    .line 75
    :cond_1
    invoke-virtual {p1}, Lcom/android/systemui/shared/condition/Condition;->start()V

    .line 76
    .line 77
    .line 78
    iput-boolean v3, p1, Lcom/android/systemui/shared/condition/Condition;->mStarted:Z

    .line 79
    .line 80
    :cond_2
    :goto_0
    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 81
    .line 82
    .line 83
    move-result-object p1

    .line 84
    check-cast p1, Landroid/util/ArraySet;

    .line 85
    .line 86
    invoke-virtual {p1, p0}, Landroid/util/ArraySet;->add(Ljava/lang/Object;)Z

    .line 87
    .line 88
    .line 89
    return-void

    .line 90
    :goto_1
    iget-object v0, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/shared/condition/Monitor;

    .line 91
    .line 92
    iget-object p0, p0, Lcom/android/systemui/shared/condition/Monitor$$ExternalSyntheticLambda3;->f$1:Lcom/android/systemui/shared/condition/Monitor$Subscription$Token;

    .line 93
    .line 94
    check-cast p1, Lcom/android/systemui/shared/condition/Condition;

    .line 95
    .line 96
    iget-object v2, v0, Lcom/android/systemui/shared/condition/Monitor;->mConditions:Ljava/util/HashMap;

    .line 97
    .line 98
    invoke-virtual {v2, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    .line 99
    .line 100
    .line 101
    move-result v3

    .line 102
    if-nez v3, :cond_3

    .line 103
    .line 104
    new-instance p0, Ljava/lang/StringBuilder;

    .line 105
    .line 106
    const-string v1, "condition not present:"

    .line 107
    .line 108
    invoke-direct {p0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 109
    .line 110
    .line 111
    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 112
    .line 113
    .line 114
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 115
    .line 116
    .line 117
    move-result-object p0

    .line 118
    iget-object p1, v0, Lcom/android/systemui/shared/condition/Monitor;->mTag:Ljava/lang/String;

    .line 119
    .line 120
    invoke-static {p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 121
    .line 122
    .line 123
    goto :goto_4

    .line 124
    :cond_3
    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 125
    .line 126
    .line 127
    move-result-object v3

    .line 128
    check-cast v3, Ljava/util/Set;

    .line 129
    .line 130
    invoke-interface {v3, p0}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    .line 131
    .line 132
    .line 133
    invoke-interface {v3}, Ljava/util/Set;->isEmpty()Z

    .line 134
    .line 135
    .line 136
    move-result p0

    .line 137
    if-eqz p0, :cond_a

    .line 138
    .line 139
    iget-object p0, p1, Lcom/android/systemui/shared/condition/Condition;->mTag:Ljava/lang/String;

    .line 140
    .line 141
    invoke-static {p0, v1}, Landroid/util/Log;->isLoggable(Ljava/lang/String;I)Z

    .line 142
    .line 143
    .line 144
    move-result p0

    .line 145
    if-eqz p0, :cond_4

    .line 146
    .line 147
    iget-object p0, p1, Lcom/android/systemui/shared/condition/Condition;->mTag:Ljava/lang/String;

    .line 148
    .line 149
    const-string/jumbo v1, "removing callback"

    .line 150
    .line 151
    .line 152
    invoke-static {p0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 153
    .line 154
    .line 155
    :cond_4
    iget-object p0, p1, Lcom/android/systemui/shared/condition/Condition;->mCallbacks:Ljava/util/ArrayList;

    .line 156
    .line 157
    invoke-virtual {p0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    .line 158
    .line 159
    .line 160
    move-result-object v1

    .line 161
    :cond_5
    :goto_2
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    .line 162
    .line 163
    .line 164
    move-result v3

    .line 165
    if-eqz v3, :cond_7

    .line 166
    .line 167
    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 168
    .line 169
    .line 170
    move-result-object v3

    .line 171
    check-cast v3, Ljava/lang/ref/WeakReference;

    .line 172
    .line 173
    invoke-virtual {v3}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    .line 174
    .line 175
    .line 176
    move-result-object v3

    .line 177
    check-cast v3, Lcom/android/systemui/shared/condition/Monitor$1;

    .line 178
    .line 179
    if-eqz v3, :cond_6

    .line 180
    .line 181
    iget-object v4, v0, Lcom/android/systemui/shared/condition/Monitor;->mConditionCallback:Lcom/android/systemui/shared/condition/Monitor$1;

    .line 182
    .line 183
    if-ne v3, v4, :cond_5

    .line 184
    .line 185
    :cond_6
    invoke-interface {v1}, Ljava/util/Iterator;->remove()V

    .line 186
    .line 187
    .line 188
    goto :goto_2

    .line 189
    :cond_7
    invoke-virtual {p0}, Ljava/util/ArrayList;->isEmpty()Z

    .line 190
    .line 191
    .line 192
    move-result p0

    .line 193
    if-eqz p0, :cond_9

    .line 194
    .line 195
    iget-boolean p0, p1, Lcom/android/systemui/shared/condition/Condition;->mStarted:Z

    .line 196
    .line 197
    if-nez p0, :cond_8

    .line 198
    .line 199
    goto :goto_3

    .line 200
    :cond_8
    invoke-virtual {p1}, Lcom/android/systemui/shared/condition/Condition;->stop()V

    .line 201
    .line 202
    .line 203
    const/4 p0, 0x0

    .line 204
    iput-boolean p0, p1, Lcom/android/systemui/shared/condition/Condition;->mStarted:Z

    .line 205
    .line 206
    :cond_9
    :goto_3
    invoke-virtual {v2, p1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 207
    .line 208
    .line 209
    :cond_a
    :goto_4
    return-void

    .line 210
    nop

    .line 211
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method
