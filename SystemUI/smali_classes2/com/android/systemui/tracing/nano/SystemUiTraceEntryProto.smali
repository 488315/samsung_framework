.class public final Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;
.super Lcom/google/protobuf/nano/MessageNano;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field private static volatile _emptyArray:[Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;


# instance fields
.field public elapsedRealtimeNanos:J

.field public systemUi:Lcom/android/systemui/tracing/nano/SystemUiTraceProto;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 1
    invoke-direct {p0}, Lcom/google/protobuf/nano/MessageNano;-><init>()V

    .line 2
    .line 3
    .line 4
    invoke-virtual {p0}, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->clear()Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;

    .line 5
    .line 6
    .line 7
    return-void
.end method

.method public static emptyArray()[Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;
    .locals 2

    .line 1
    sget-object v0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->_emptyArray:[Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;

    .line 2
    .line 3
    if-nez v0, :cond_1

    .line 4
    .line 5
    sget-object v0, Lcom/google/protobuf/nano/InternalNano;->LAZY_INIT_LOCK:Ljava/lang/Object;

    .line 6
    .line 7
    monitor-enter v0

    .line 8
    :try_start_0
    sget-object v1, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->_emptyArray:[Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;

    .line 9
    .line 10
    if-nez v1, :cond_0

    .line 11
    .line 12
    const/4 v1, 0x0

    .line 13
    new-array v1, v1, [Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;

    .line 14
    .line 15
    sput-object v1, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->_emptyArray:[Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;

    .line 16
    .line 17
    :cond_0
    monitor-exit v0

    .line 18
    goto :goto_0

    .line 19
    :catchall_0
    move-exception v1

    .line 20
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 21
    throw v1

    .line 22
    :cond_1
    :goto_0
    sget-object v0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->_emptyArray:[Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;

    .line 23
    .line 24
    return-object v0
.end method


# virtual methods
.method public clear()Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;
    .locals 2

    .line 1
    const-wide/16 v0, 0x0

    .line 2
    .line 3
    iput-wide v0, p0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->elapsedRealtimeNanos:J

    .line 4
    .line 5
    const/4 v0, 0x0

    .line 6
    iput-object v0, p0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->systemUi:Lcom/android/systemui/tracing/nano/SystemUiTraceProto;

    .line 7
    .line 8
    const/4 v0, -0x1

    .line 9
    iput v0, p0, Lcom/google/protobuf/nano/MessageNano;->cachedSize:I

    .line 10
    .line 11
    return-object p0
.end method

.method public computeSerializedSize()I
    .locals 4

    .line 1
    iget-wide v0, p0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->elapsedRealtimeNanos:J

    .line 2
    .line 3
    const-wide/16 v2, 0x0

    .line 4
    .line 5
    cmp-long v0, v0, v2

    .line 6
    .line 7
    const/4 v1, 0x0

    .line 8
    if-eqz v0, :cond_0

    .line 9
    .line 10
    const/4 v0, 0x1

    .line 11
    invoke-static {v0}, Lcom/google/protobuf/nano/CodedOutputByteBufferNano;->computeTagSize(I)I

    .line 12
    .line 13
    .line 14
    move-result v0

    .line 15
    add-int/lit8 v0, v0, 0x8

    .line 16
    .line 17
    add-int/2addr v1, v0

    .line 18
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->systemUi:Lcom/android/systemui/tracing/nano/SystemUiTraceProto;

    .line 19
    .line 20
    if-eqz p0, :cond_1

    .line 21
    .line 22
    const/4 v0, 0x3

    .line 23
    invoke-static {v0, p0}, Lcom/google/protobuf/nano/CodedOutputByteBufferNano;->computeMessageSize(ILcom/google/protobuf/nano/MessageNano;)I

    .line 24
    .line 25
    .line 26
    move-result p0

    .line 27
    add-int/2addr v1, p0

    .line 28
    :cond_1
    return v1
.end method

.method public writeTo(Lcom/google/protobuf/nano/CodedOutputByteBufferNano;)V
    .locals 4

    .line 1
    iget-wide v0, p0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->elapsedRealtimeNanos:J

    .line 2
    .line 3
    const-wide/16 v2, 0x0

    .line 4
    .line 5
    cmp-long v2, v0, v2

    .line 6
    .line 7
    if-eqz v2, :cond_0

    .line 8
    .line 9
    const/4 v2, 0x1

    .line 10
    invoke-virtual {p1, v2, v0, v1}, Lcom/google/protobuf/nano/CodedOutputByteBufferNano;->writeFixed64(IJ)V

    .line 11
    .line 12
    .line 13
    :cond_0
    iget-object p0, p0, Lcom/android/systemui/tracing/nano/SystemUiTraceEntryProto;->systemUi:Lcom/android/systemui/tracing/nano/SystemUiTraceProto;

    .line 14
    .line 15
    if-eqz p0, :cond_1

    .line 16
    .line 17
    const/4 v0, 0x3

    .line 18
    invoke-virtual {p1, v0, p0}, Lcom/google/protobuf/nano/CodedOutputByteBufferNano;->writeMessage(ILcom/google/protobuf/nano/MessageNano;)V

    .line 19
    .line 20
    .line 21
    :cond_1
    return-void
.end method
