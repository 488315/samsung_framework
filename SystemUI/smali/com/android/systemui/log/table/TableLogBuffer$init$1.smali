.class final Lcom/android/systemui/log/table/TableLogBuffer$init$1;
.super Lkotlin/coroutines/jvm/internal/SuspendLambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function2;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/coroutines/jvm/internal/SuspendLambda;",
        "Lkotlin/jvm/functions/Function2;"
    }
.end annotation

.annotation runtime Lkotlin/coroutines/jvm/internal/DebugMetadata;
    c = "com.android.systemui.log.table.TableLogBuffer$init$1"
    f = "TableLogBuffer.kt"
    l = {
        0x7e
    }
    m = "invokeSuspend"
.end annotation


# instance fields
.field label:I

.field final synthetic this$0:Lcom/android/systemui/log/table/TableLogBuffer;


# direct methods
.method public constructor <init>(Lcom/android/systemui/log/table/TableLogBuffer;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/log/table/TableLogBuffer;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/log/table/TableLogBuffer$init$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->this$0:Lcom/android/systemui/log/table/TableLogBuffer;

    .line 2
    .line 3
    const/4 p1, 0x2

    .line 4
    invoke-direct {p0, p1, p2}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    .locals 0

    .line 1
    new-instance p1, Lcom/android/systemui/log/table/TableLogBuffer$init$1;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->this$0:Lcom/android/systemui/log/table/TableLogBuffer;

    .line 4
    .line 5
    invoke-direct {p1, p0, p2}, Lcom/android/systemui/log/table/TableLogBuffer$init$1;-><init>(Lcom/android/systemui/log/table/TableLogBuffer;Lkotlin/coroutines/Continuation;)V

    .line 6
    .line 7
    .line 8
    return-object p1
.end method

.method public final invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Lkotlinx/coroutines/CoroutineScope;

    .line 2
    .line 3
    check-cast p2, Lkotlin/coroutines/Continuation;

    .line 4
    .line 5
    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    check-cast p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;

    .line 10
    .line 11
    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 12
    .line 13
    invoke-virtual {p0, p1}, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 8

    .line 1
    sget-object v0, Lkotlin/coroutines/intrinsics/CoroutineSingletons;->COROUTINE_SUSPENDED:Lkotlin/coroutines/intrinsics/CoroutineSingletons;

    .line 2
    .line 3
    iget v1, p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->label:I

    .line 4
    .line 5
    const/4 v2, 0x1

    .line 6
    if-eqz v1, :cond_1

    .line 7
    .line 8
    if-ne v1, v2, :cond_0

    .line 9
    .line 10
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 11
    .line 12
    .line 13
    goto :goto_1

    .line 14
    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 15
    .line 16
    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    .line 17
    .line 18
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 19
    .line 20
    .line 21
    throw p0

    .line 22
    :cond_1
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 23
    .line 24
    .line 25
    :cond_2
    :goto_0
    iget-object p1, p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->this$0:Lcom/android/systemui/log/table/TableLogBuffer;

    .line 26
    .line 27
    iget-object p1, p1, Lcom/android/systemui/log/table/TableLogBuffer;->logMessageChannel:Lkotlinx/coroutines/channels/AbstractChannel;

    .line 28
    .line 29
    invoke-interface {p1}, Lkotlinx/coroutines/channels/ReceiveChannel;->isClosedForReceive()Z

    .line 30
    .line 31
    .line 32
    move-result p1

    .line 33
    if-nez p1, :cond_5

    .line 34
    .line 35
    iget-object p1, p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->this$0:Lcom/android/systemui/log/table/TableLogBuffer;

    .line 36
    .line 37
    iget-object p1, p1, Lcom/android/systemui/log/table/TableLogBuffer;->logMessageChannel:Lkotlinx/coroutines/channels/AbstractChannel;

    .line 38
    .line 39
    iput v2, p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->label:I

    .line 40
    .line 41
    invoke-virtual {p1, p0}, Lkotlinx/coroutines/channels/AbstractChannel;->receive(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 42
    .line 43
    .line 44
    move-result-object p1

    .line 45
    if-ne p1, v0, :cond_3

    .line 46
    .line 47
    return-object v0

    .line 48
    :cond_3
    :goto_1
    check-cast p1, Lcom/android/systemui/log/table/TableChange;

    .line 49
    .line 50
    iget-object v1, p0, Lcom/android/systemui/log/table/TableLogBuffer$init$1;->this$0:Lcom/android/systemui/log/table/TableLogBuffer;

    .line 51
    .line 52
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 53
    .line 54
    .line 55
    sget-object v3, Lcom/android/systemui/log/LogLevel;->DEBUG:Lcom/android/systemui/log/LogLevel;

    .line 56
    .line 57
    iget-object v4, v1, Lcom/android/systemui/log/table/TableLogBuffer;->logcatEchoTracker:Lcom/android/systemui/log/LogcatEchoTracker;

    .line 58
    .line 59
    iget-object v5, v1, Lcom/android/systemui/log/table/TableLogBuffer;->name:Ljava/lang/String;

    .line 60
    .line 61
    invoke-interface {v4, v5, v3}, Lcom/android/systemui/log/LogcatEchoTracker;->isBufferLoggable(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;)Z

    .line 62
    .line 63
    .line 64
    move-result v6

    .line 65
    if-nez v6, :cond_4

    .line 66
    .line 67
    iget-object v6, p1, Lcom/android/systemui/log/table/TableChange;->columnName:Ljava/lang/String;

    .line 68
    .line 69
    invoke-interface {v4, v6, v3}, Lcom/android/systemui/log/LogcatEchoTracker;->isTagLoggable(Ljava/lang/String;Lcom/android/systemui/log/LogLevel;)Z

    .line 70
    .line 71
    .line 72
    move-result v3

    .line 73
    if-eqz v3, :cond_2

    .line 74
    .line 75
    :cond_4
    invoke-virtual {p1}, Lcom/android/systemui/log/table/TableChange;->hasData()Z

    .line 76
    .line 77
    .line 78
    move-result v3

    .line 79
    if-eqz v3, :cond_2

    .line 80
    .line 81
    sget-object v3, Lcom/android/systemui/log/table/TableLogBufferKt;->TABLE_LOG_DATE_FORMAT:Landroid/icu/text/SimpleDateFormat;

    .line 82
    .line 83
    iget-wide v6, p1, Lcom/android/systemui/log/table/TableChange;->timestamp:J

    .line 84
    .line 85
    invoke-static {v6, v7}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    .line 86
    .line 87
    .line 88
    move-result-object v4

    .line 89
    invoke-virtual {v3, v4}, Landroid/icu/text/SimpleDateFormat;->format(Ljava/lang/Object;)Ljava/lang/String;

    .line 90
    .line 91
    .line 92
    move-result-object v3

    .line 93
    invoke-virtual {p1}, Lcom/android/systemui/log/table/TableChange;->getName()Ljava/lang/String;

    .line 94
    .line 95
    .line 96
    move-result-object v4

    .line 97
    invoke-virtual {p1}, Lcom/android/systemui/log/table/TableChange;->getVal()Ljava/lang/String;

    .line 98
    .line 99
    .line 100
    move-result-object p1

    .line 101
    new-instance v6, Ljava/lang/StringBuilder;

    .line 102
    .line 103
    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    .line 104
    .line 105
    .line 106
    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 107
    .line 108
    .line 109
    const-string/jumbo v3, "|"

    .line 110
    .line 111
    .line 112
    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 113
    .line 114
    .line 115
    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 116
    .line 117
    .line 118
    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 119
    .line 120
    .line 121
    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 122
    .line 123
    .line 124
    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 125
    .line 126
    .line 127
    move-result-object p1

    .line 128
    iget-object v1, v1, Lcom/android/systemui/log/table/TableLogBuffer;->localLogcat:Lcom/android/systemui/log/table/LogProxy;

    .line 129
    .line 130
    check-cast v1, Lcom/android/systemui/log/table/LogProxyDefault;

    .line 131
    .line 132
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 133
    .line 134
    .line 135
    invoke-static {v5, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 136
    .line 137
    .line 138
    goto :goto_0

    .line 139
    :cond_5
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 140
    .line 141
    return-object p0
.end method
