.class public final Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public columnPrefix:Ljava/lang/String;

.field public isInitial:Z

.field public final tableLogBuffer:Lcom/android/systemui/log/table/TableLogBuffer;

.field public timestamp:J


# direct methods
.method public constructor <init>(JLjava/lang/String;ZLcom/android/systemui/log/table/TableLogBuffer;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-wide p1, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->timestamp:J

    .line 5
    .line 6
    iput-object p3, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->columnPrefix:Ljava/lang/String;

    .line 7
    .line 8
    iput-boolean p4, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->isInitial:Z

    .line 9
    .line 10
    iput-object p5, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->tableLogBuffer:Lcom/android/systemui/log/table/TableLogBuffer;

    .line 11
    .line 12
    return-void
.end method


# virtual methods
.method public final logChange(ILjava/lang/String;)V
    .locals 6

    .line 17
    iget-wide v1, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->timestamp:J

    iget-object v3, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->columnPrefix:Ljava/lang/String;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    iget-boolean v5, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->isInitial:Z

    .line 18
    iget-object p0, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->tableLogBuffer:Lcom/android/systemui/log/table/TableLogBuffer;

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    const-string v0, "TableLogBuffer#logChange(int)"

    .line 19
    invoke-static {v0}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    move-object v0, p0

    move-object v4, p2

    .line 20
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/log/table/TableLogBuffer;->obtain(JLjava/lang/String;Ljava/lang/String;Z)Lcom/android/systemui/log/table/TableChange;

    move-result-object p2

    .line 21
    sget-object v0, Lcom/android/systemui/log/table/TableChange$DataType;->INT:Lcom/android/systemui/log/table/TableChange$DataType;

    iput-object v0, p2, Lcom/android/systemui/log/table/TableChange;->type:Lcom/android/systemui/log/table/TableChange$DataType;

    .line 22
    iput-object p1, p2, Lcom/android/systemui/log/table/TableChange;->int:Ljava/lang/Integer;

    .line 23
    iget-object p0, p0, Lcom/android/systemui/log/table/TableLogBuffer;->logMessageChannel:Lkotlinx/coroutines/channels/AbstractChannel;

    invoke-virtual {p0, p2}, Lkotlinx/coroutines/channels/AbstractSendChannel;->trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;

    .line 24
    invoke-static {}, Landroid/os/Trace;->endSection()V

    return-void
.end method

.method public final logChange(Ljava/lang/String;Ljava/lang/String;)V
    .locals 6

    .line 1
    iget-wide v1, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->timestamp:J

    iget-object v3, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->columnPrefix:Ljava/lang/String;

    iget-boolean v5, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->isInitial:Z

    .line 2
    iget-object p0, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->tableLogBuffer:Lcom/android/systemui/log/table/TableLogBuffer;

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    const-string v0, "TableLogBuffer#logChange(string)"

    .line 3
    invoke-static {v0}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    move-object v0, p0

    move-object v4, p1

    .line 4
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/log/table/TableLogBuffer;->obtain(JLjava/lang/String;Ljava/lang/String;Z)Lcom/android/systemui/log/table/TableChange;

    move-result-object p1

    .line 5
    sget-object v0, Lcom/android/systemui/log/table/TableChange$DataType;->STRING:Lcom/android/systemui/log/table/TableChange$DataType;

    iput-object v0, p1, Lcom/android/systemui/log/table/TableChange;->type:Lcom/android/systemui/log/table/TableChange$DataType;

    if-eqz p2, :cond_0

    .line 6
    invoke-static {p2}, Lkotlin/text/StringsKt___StringsKt;->take(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p2

    goto :goto_0

    :cond_0
    const/4 p2, 0x0

    :goto_0
    iput-object p2, p1, Lcom/android/systemui/log/table/TableChange;->str:Ljava/lang/String;

    .line 7
    iget-object p0, p0, Lcom/android/systemui/log/table/TableLogBuffer;->logMessageChannel:Lkotlinx/coroutines/channels/AbstractChannel;

    invoke-virtual {p0, p1}, Lkotlinx/coroutines/channels/AbstractSendChannel;->trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;

    .line 8
    invoke-static {}, Landroid/os/Trace;->endSection()V

    return-void
.end method

.method public final logChange(Ljava/lang/String;Z)V
    .locals 6

    .line 9
    iget-wide v1, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->timestamp:J

    iget-object v3, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->columnPrefix:Ljava/lang/String;

    iget-boolean v5, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->isInitial:Z

    .line 10
    iget-object p0, p0, Lcom/android/systemui/log/table/TableLogBuffer$TableRowLoggerImpl;->tableLogBuffer:Lcom/android/systemui/log/table/TableLogBuffer;

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    const-string v0, "TableLogBuffer#logChange(boolean)"

    .line 11
    invoke-static {v0}, Landroid/os/Trace;->beginSection(Ljava/lang/String;)V

    move-object v0, p0

    move-object v4, p1

    .line 12
    invoke-virtual/range {v0 .. v5}, Lcom/android/systemui/log/table/TableLogBuffer;->obtain(JLjava/lang/String;Ljava/lang/String;Z)Lcom/android/systemui/log/table/TableChange;

    move-result-object p1

    .line 13
    sget-object v0, Lcom/android/systemui/log/table/TableChange$DataType;->BOOLEAN:Lcom/android/systemui/log/table/TableChange$DataType;

    iput-object v0, p1, Lcom/android/systemui/log/table/TableChange;->type:Lcom/android/systemui/log/table/TableChange$DataType;

    .line 14
    iput-boolean p2, p1, Lcom/android/systemui/log/table/TableChange;->bool:Z

    .line 15
    iget-object p0, p0, Lcom/android/systemui/log/table/TableLogBuffer;->logMessageChannel:Lkotlinx/coroutines/channels/AbstractChannel;

    invoke-virtual {p0, p1}, Lkotlinx/coroutines/channels/AbstractSendChannel;->trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;

    .line 16
    invoke-static {}, Landroid/os/Trace;->endSection()V

    return-void
.end method
