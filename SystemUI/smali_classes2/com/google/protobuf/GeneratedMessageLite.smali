.class public abstract Lcom/google/protobuf/GeneratedMessageLite;
.super Lcom/google/protobuf/AbstractMessageLite;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field private static final MEMOIZED_SERIALIZED_SIZE_MASK:I = 0x7fffffff

.field private static final MUTABLE_FLAG_MASK:I = -0x80000000

.field static final UNINITIALIZED_HASH_CODE:I = 0x0

.field static final UNINITIALIZED_SERIALIZED_SIZE:I = 0x7fffffff

.field private static defaultInstanceMap:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Object;",
            "Lcom/google/protobuf/GeneratedMessageLite;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private memoizedSerializedSize:I

.field protected unknownFields:Lcom/google/protobuf/UnknownFieldSetLite;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    .line 2
    .line 3
    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/google/protobuf/GeneratedMessageLite;->defaultInstanceMap:Ljava/util/Map;

    .line 7
    .line 8
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Lcom/google/protobuf/AbstractMessageLite;-><init>()V

    .line 2
    .line 3
    .line 4
    const/4 v0, -0x1

    .line 5
    iput v0, p0, Lcom/google/protobuf/GeneratedMessageLite;->memoizedSerializedSize:I

    .line 6
    .line 7
    sget-object v0, Lcom/google/protobuf/UnknownFieldSetLite;->DEFAULT_INSTANCE:Lcom/google/protobuf/UnknownFieldSetLite;

    .line 8
    .line 9
    iput-object v0, p0, Lcom/google/protobuf/GeneratedMessageLite;->unknownFields:Lcom/google/protobuf/UnknownFieldSetLite;

    .line 10
    .line 11
    return-void
.end method

.method public static getDefaultInstance(Ljava/lang/Class;)Lcom/google/protobuf/GeneratedMessageLite;
    .locals 3

    .line 1
    sget-object v0, Lcom/google/protobuf/GeneratedMessageLite;->defaultInstanceMap:Ljava/util/Map;

    .line 2
    .line 3
    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, Lcom/google/protobuf/GeneratedMessageLite;

    .line 8
    .line 9
    if-nez v0, :cond_0

    .line 10
    .line 11
    :try_start_0
    invoke-virtual {p0}, Ljava/lang/Class;->getName()Ljava/lang/String;

    .line 12
    .line 13
    .line 14
    move-result-object v0

    .line 15
    invoke-virtual {p0}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    .line 16
    .line 17
    .line 18
    move-result-object v1

    .line 19
    const/4 v2, 0x1

    .line 20
    invoke-static {v0, v2, v1}, Ljava/lang/Class;->forName(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
    :try_end_0
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 21
    .line 22
    .line 23
    sget-object v0, Lcom/google/protobuf/GeneratedMessageLite;->defaultInstanceMap:Ljava/util/Map;

    .line 24
    .line 25
    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    .line 26
    .line 27
    .line 28
    move-result-object v0

    .line 29
    check-cast v0, Lcom/google/protobuf/GeneratedMessageLite;

    .line 30
    .line 31
    goto :goto_0

    .line 32
    :catch_0
    move-exception p0

    .line 33
    new-instance v0, Ljava/lang/IllegalStateException;

    .line 34
    .line 35
    const-string v1, "Class initialization cannot fail."

    .line 36
    .line 37
    invoke-direct {v0, v1, p0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 38
    .line 39
    .line 40
    throw v0

    .line 41
    :cond_0
    :goto_0
    if-nez v0, :cond_2

    .line 42
    .line 43
    invoke-static {p0}, Lcom/google/protobuf/UnsafeUtil;->allocateInstance(Ljava/lang/Class;)Ljava/lang/Object;

    .line 44
    .line 45
    .line 46
    move-result-object v0

    .line 47
    check-cast v0, Lcom/google/protobuf/GeneratedMessageLite;

    .line 48
    .line 49
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 50
    .line 51
    .line 52
    sget-object v1, Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;->GET_DEFAULT_INSTANCE:Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;

    .line 53
    .line 54
    invoke-virtual {v0, v1}, Lcom/google/protobuf/GeneratedMessageLite;->dynamicMethod(Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;)Ljava/lang/Object;

    .line 55
    .line 56
    .line 57
    move-result-object v0

    .line 58
    check-cast v0, Lcom/google/protobuf/GeneratedMessageLite;

    .line 59
    .line 60
    if-eqz v0, :cond_1

    .line 61
    .line 62
    sget-object v1, Lcom/google/protobuf/GeneratedMessageLite;->defaultInstanceMap:Ljava/util/Map;

    .line 63
    .line 64
    invoke-interface {v1, p0, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 65
    .line 66
    .line 67
    goto :goto_1

    .line 68
    :cond_1
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 69
    .line 70
    invoke-direct {p0}, Ljava/lang/IllegalStateException;-><init>()V

    .line 71
    .line 72
    .line 73
    throw p0

    .line 74
    :cond_2
    :goto_1
    return-object v0
.end method

.method public static varargs invokeOrDie(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    :try_start_0
    invoke-virtual {p1, p0, p2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 2
    .line 3
    .line 4
    move-result-object p0
    :try_end_0
    .catch Ljava/lang/IllegalAccessException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/reflect/InvocationTargetException; {:try_start_0 .. :try_end_0} :catch_0

    .line 5
    return-object p0

    .line 6
    :catch_0
    move-exception p0

    .line 7
    invoke-virtual {p0}, Ljava/lang/reflect/InvocationTargetException;->getCause()Ljava/lang/Throwable;

    .line 8
    .line 9
    .line 10
    move-result-object p0

    .line 11
    instance-of p1, p0, Ljava/lang/RuntimeException;

    .line 12
    .line 13
    if-nez p1, :cond_1

    .line 14
    .line 15
    instance-of p1, p0, Ljava/lang/Error;

    .line 16
    .line 17
    if-eqz p1, :cond_0

    .line 18
    .line 19
    check-cast p0, Ljava/lang/Error;

    .line 20
    .line 21
    throw p0

    .line 22
    :cond_0
    new-instance p1, Ljava/lang/RuntimeException;

    .line 23
    .line 24
    const-string p2, "Unexpected exception thrown by generated accessor method."

    .line 25
    .line 26
    invoke-direct {p1, p2, p0}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 27
    .line 28
    .line 29
    throw p1

    .line 30
    :cond_1
    check-cast p0, Ljava/lang/RuntimeException;

    .line 31
    .line 32
    throw p0

    .line 33
    :catch_1
    move-exception p0

    .line 34
    new-instance p1, Ljava/lang/RuntimeException;

    .line 35
    .line 36
    const-string p2, "Couldn\'t use Java reflection to implement protocol message reflection."

    .line 37
    .line 38
    invoke-direct {p1, p2, p0}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 39
    .line 40
    .line 41
    throw p1
.end method

.method public static final isInitialized(Lcom/google/protobuf/GeneratedMessageLite;Z)Z
    .locals 2

    .line 2
    sget-object v0, Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;->GET_MEMOIZED_IS_INITIALIZED:Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;

    .line 3
    invoke-virtual {p0, v0}, Lcom/google/protobuf/GeneratedMessageLite;->dynamicMethod(Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;)Ljava/lang/Object;

    move-result-object v0

    .line 4
    check-cast v0, Ljava/lang/Byte;

    invoke-virtual {v0}, Ljava/lang/Byte;->byteValue()B

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    return v1

    :cond_0
    if-nez v0, :cond_1

    const/4 p0, 0x0

    return p0

    .line 5
    :cond_1
    sget-object v0, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 6
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 7
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    move-result-object v0

    .line 8
    invoke-interface {v0, p0}, Lcom/google/protobuf/Schema;->isInitialized(Ljava/lang/Object;)Z

    move-result v0

    if-eqz p1, :cond_2

    .line 9
    sget-object p1, Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;->SET_MEMOIZED_IS_INITIALIZED:Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;

    .line 10
    invoke-virtual {p0, p1}, Lcom/google/protobuf/GeneratedMessageLite;->dynamicMethod(Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;)Ljava/lang/Object;

    :cond_2
    return v0
.end method

.method public static mutableCopy(Lcom/google/protobuf/Internal$ProtobufList;)Lcom/google/protobuf/Internal$ProtobufList;
    .locals 1

    .line 1
    invoke-interface {p0}, Ljava/util/List;->size()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-nez v0, :cond_0

    .line 6
    .line 7
    const/16 v0, 0xa

    .line 8
    .line 9
    goto :goto_0

    .line 10
    :cond_0
    mul-int/lit8 v0, v0, 0x2

    .line 11
    .line 12
    :goto_0
    invoke-interface {p0, v0}, Lcom/google/protobuf/Internal$ProtobufList;->mutableCopyWithCapacity(I)Lcom/google/protobuf/Internal$ProtobufList;

    .line 13
    .line 14
    .line 15
    move-result-object p0

    .line 16
    return-object p0
.end method

.method public static registerDefaultInstance(Ljava/lang/Class;Lcom/google/protobuf/GeneratedMessageLite;)V
    .locals 1

    .line 1
    sget-object v0, Lcom/google/protobuf/GeneratedMessageLite;->defaultInstanceMap:Ljava/util/Map;

    .line 2
    .line 3
    invoke-interface {v0, p0, p1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    invoke-virtual {p1}, Lcom/google/protobuf/GeneratedMessageLite;->makeImmutable()V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final createBuilder()Lcom/google/protobuf/GeneratedMessageLite$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;->NEW_BUILDER:Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;

    .line 2
    .line 3
    invoke-virtual {p0, v0}, Lcom/google/protobuf/GeneratedMessageLite;->dynamicMethod(Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;)Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/google/protobuf/GeneratedMessageLite$Builder;

    .line 8
    .line 9
    return-object p0
.end method

.method public abstract dynamicMethod(Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;)Ljava/lang/Object;
.end method

.method public final equals(Ljava/lang/Object;)Z
    .locals 3

    .line 1
    if-ne p0, p1, :cond_0

    .line 2
    .line 3
    const/4 p0, 0x1

    .line 4
    return p0

    .line 5
    :cond_0
    const/4 v0, 0x0

    .line 6
    if-nez p1, :cond_1

    .line 7
    .line 8
    return v0

    .line 9
    :cond_1
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 10
    .line 11
    .line 12
    move-result-object v1

    .line 13
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 14
    .line 15
    .line 16
    move-result-object v2

    .line 17
    if-eq v1, v2, :cond_2

    .line 18
    .line 19
    return v0

    .line 20
    :cond_2
    sget-object v0, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 21
    .line 22
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 23
    .line 24
    .line 25
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 26
    .line 27
    .line 28
    move-result-object v1

    .line 29
    invoke-virtual {v0, v1}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    .line 30
    .line 31
    .line 32
    move-result-object v0

    .line 33
    check-cast p1, Lcom/google/protobuf/GeneratedMessageLite;

    .line 34
    .line 35
    invoke-interface {v0, p0, p1}, Lcom/google/protobuf/Schema;->equals(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 36
    .line 37
    .line 38
    move-result p0

    .line 39
    return p0
.end method

.method public final getMemoizedSerializedSize()I
    .locals 1

    .line 1
    iget p0, p0, Lcom/google/protobuf/GeneratedMessageLite;->memoizedSerializedSize:I

    .line 2
    .line 3
    const v0, 0x7fffffff

    .line 4
    .line 5
    .line 6
    and-int/2addr p0, v0

    .line 7
    return p0
.end method

.method public final getSerializedSize()I
    .locals 1

    const/4 v0, 0x0

    .line 34
    invoke-virtual {p0, v0}, Lcom/google/protobuf/GeneratedMessageLite;->getSerializedSize(Lcom/google/protobuf/Schema;)I

    move-result p0

    return p0
.end method

.method public final getSerializedSize(Lcom/google/protobuf/Schema;)I
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/google/protobuf/GeneratedMessageLite;->isMutable()Z

    move-result v0

    if-eqz v0, :cond_2

    if-nez p1, :cond_0

    .line 2
    sget-object p1, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 3
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {p1, v0}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    move-result-object p1

    .line 5
    invoke-interface {p1, p0}, Lcom/google/protobuf/Schema;->getSerializedSize(Ljava/lang/Object;)I

    move-result p0

    goto :goto_0

    .line 6
    :cond_0
    invoke-interface {p1, p0}, Lcom/google/protobuf/Schema;->getSerializedSize(Ljava/lang/Object;)I

    move-result p0

    :goto_0
    if-ltz p0, :cond_1

    return p0

    .line 7
    :cond_1
    new-instance p1, Ljava/lang/IllegalStateException;

    const-string/jumbo v0, "serialized size must be non-negative, was "

    .line 8
    invoke-static {v0, p0}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;I)Ljava/lang/String;

    move-result-object p0

    .line 9
    invoke-direct {p1, p0}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p1

    .line 10
    :cond_2
    invoke-virtual {p0}, Lcom/google/protobuf/GeneratedMessageLite;->getMemoizedSerializedSize()I

    move-result v0

    const v1, 0x7fffffff

    if-eq v0, v1, :cond_3

    .line 11
    invoke-virtual {p0}, Lcom/google/protobuf/GeneratedMessageLite;->getMemoizedSerializedSize()I

    move-result p0

    return p0

    :cond_3
    if-nez p1, :cond_4

    .line 12
    sget-object p1, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 13
    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 14
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {p1, v0}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    move-result-object p1

    .line 15
    invoke-interface {p1, p0}, Lcom/google/protobuf/Schema;->getSerializedSize(Ljava/lang/Object;)I

    move-result p1

    goto :goto_1

    .line 16
    :cond_4
    invoke-interface {p1, p0}, Lcom/google/protobuf/Schema;->getSerializedSize(Ljava/lang/Object;)I

    move-result p1

    .line 17
    :goto_1
    invoke-virtual {p0, p1}, Lcom/google/protobuf/GeneratedMessageLite;->setMemoizedSerializedSize(I)V

    return p1
.end method

.method public final hashCode()I
    .locals 2

    .line 1
    invoke-virtual {p0}, Lcom/google/protobuf/GeneratedMessageLite;->isMutable()Z

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    sget-object v0, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 8
    .line 9
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 10
    .line 11
    .line 12
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 13
    .line 14
    .line 15
    move-result-object v1

    .line 16
    invoke-virtual {v0, v1}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    .line 17
    .line 18
    .line 19
    move-result-object v0

    .line 20
    invoke-interface {v0, p0}, Lcom/google/protobuf/Schema;->hashCode(Ljava/lang/Object;)I

    .line 21
    .line 22
    .line 23
    move-result p0

    .line 24
    return p0

    .line 25
    :cond_0
    iget v0, p0, Lcom/google/protobuf/AbstractMessageLite;->memoizedHashCode:I

    .line 26
    .line 27
    if-nez v0, :cond_1

    .line 28
    .line 29
    const/4 v0, 0x1

    .line 30
    goto :goto_0

    .line 31
    :cond_1
    const/4 v0, 0x0

    .line 32
    :goto_0
    if-eqz v0, :cond_2

    .line 33
    .line 34
    sget-object v0, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 35
    .line 36
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 37
    .line 38
    .line 39
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 40
    .line 41
    .line 42
    move-result-object v1

    .line 43
    invoke-virtual {v0, v1}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    .line 44
    .line 45
    .line 46
    move-result-object v0

    .line 47
    invoke-interface {v0, p0}, Lcom/google/protobuf/Schema;->hashCode(Ljava/lang/Object;)I

    .line 48
    .line 49
    .line 50
    move-result v0

    .line 51
    iput v0, p0, Lcom/google/protobuf/AbstractMessageLite;->memoizedHashCode:I

    .line 52
    .line 53
    :cond_2
    iget p0, p0, Lcom/google/protobuf/AbstractMessageLite;->memoizedHashCode:I

    .line 54
    .line 55
    return p0
.end method

.method public final isInitialized()Z
    .locals 1

    const/4 v0, 0x1

    .line 1
    invoke-static {p0, v0}, Lcom/google/protobuf/GeneratedMessageLite;->isInitialized(Lcom/google/protobuf/GeneratedMessageLite;Z)Z

    move-result p0

    return p0
.end method

.method public final isMutable()Z
    .locals 1

    .line 1
    iget p0, p0, Lcom/google/protobuf/GeneratedMessageLite;->memoizedSerializedSize:I

    .line 2
    .line 3
    const/high16 v0, -0x80000000

    .line 4
    .line 5
    and-int/2addr p0, v0

    .line 6
    if-eqz p0, :cond_0

    .line 7
    .line 8
    const/4 p0, 0x1

    .line 9
    goto :goto_0

    .line 10
    :cond_0
    const/4 p0, 0x0

    .line 11
    :goto_0
    return p0
.end method

.method public final makeImmutable()V
    .locals 2

    .line 1
    sget-object v0, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 7
    .line 8
    .line 9
    move-result-object v1

    .line 10
    invoke-virtual {v0, v1}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    .line 11
    .line 12
    .line 13
    move-result-object v0

    .line 14
    invoke-interface {v0, p0}, Lcom/google/protobuf/Schema;->makeImmutable(Ljava/lang/Object;)V

    .line 15
    .line 16
    .line 17
    invoke-virtual {p0}, Lcom/google/protobuf/GeneratedMessageLite;->markImmutable()V

    .line 18
    .line 19
    .line 20
    return-void
.end method

.method public final markImmutable()V
    .locals 2

    .line 1
    iget v0, p0, Lcom/google/protobuf/GeneratedMessageLite;->memoizedSerializedSize:I

    .line 2
    .line 3
    const v1, 0x7fffffff

    .line 4
    .line 5
    .line 6
    and-int/2addr v0, v1

    .line 7
    iput v0, p0, Lcom/google/protobuf/GeneratedMessageLite;->memoizedSerializedSize:I

    .line 8
    .line 9
    return-void
.end method

.method public final setMemoizedSerializedSize(I)V
    .locals 2

    .line 1
    if-ltz p1, :cond_0

    .line 2
    .line 3
    iget v0, p0, Lcom/google/protobuf/GeneratedMessageLite;->memoizedSerializedSize:I

    .line 4
    .line 5
    const/high16 v1, -0x80000000

    .line 6
    .line 7
    and-int/2addr v0, v1

    .line 8
    const v1, 0x7fffffff

    .line 9
    .line 10
    .line 11
    and-int/2addr p1, v1

    .line 12
    or-int/2addr p1, v0

    .line 13
    iput p1, p0, Lcom/google/protobuf/GeneratedMessageLite;->memoizedSerializedSize:I

    .line 14
    .line 15
    return-void

    .line 16
    :cond_0
    new-instance p0, Ljava/lang/IllegalStateException;

    .line 17
    .line 18
    const-string/jumbo v0, "serialized size must be non-negative, was "

    .line 19
    .line 20
    .line 21
    invoke-static {v0, p1}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;I)Ljava/lang/String;

    .line 22
    .line 23
    .line 24
    move-result-object p1

    .line 25
    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    .line 26
    .line 27
    .line 28
    throw p0
.end method

.method public final toBuilder()Lcom/google/protobuf/GeneratedMessageLite$Builder;
    .locals 1

    .line 1
    sget-object v0, Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;->NEW_BUILDER:Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;

    .line 2
    .line 3
    invoke-virtual {p0, v0}, Lcom/google/protobuf/GeneratedMessageLite;->dynamicMethod(Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;)Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, Lcom/google/protobuf/GeneratedMessageLite$Builder;

    .line 8
    .line 9
    invoke-virtual {v0, p0}, Lcom/google/protobuf/GeneratedMessageLite$Builder;->mergeFrom(Lcom/google/protobuf/GeneratedMessageLite;)V

    .line 10
    .line 11
    .line 12
    return-object v0
.end method

.method public final toString()Ljava/lang/String;
    .locals 3

    .line 1
    invoke-super {p0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    .line 2
    .line 3
    .line 4
    move-result-object v0

    .line 5
    sget-object v1, Lcom/google/protobuf/MessageLiteToString;->INDENT_BUFFER:[C

    .line 6
    .line 7
    new-instance v1, Ljava/lang/StringBuilder;

    .line 8
    .line 9
    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    .line 10
    .line 11
    .line 12
    const-string v2, "# "

    .line 13
    .line 14
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 15
    .line 16
    .line 17
    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 18
    .line 19
    .line 20
    const/4 v0, 0x0

    .line 21
    invoke-static {p0, v1, v0}, Lcom/google/protobuf/MessageLiteToString;->reflectivePrintWithIndent(Lcom/google/protobuf/MessageLite;Ljava/lang/StringBuilder;I)V

    .line 22
    .line 23
    .line 24
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 25
    .line 26
    .line 27
    move-result-object p0

    .line 28
    return-object p0
.end method

.method public final writeTo(Lcom/google/protobuf/CodedOutputStream;)V
    .locals 2

    .line 1
    sget-object v0, Lcom/google/protobuf/Protobuf;->INSTANCE:Lcom/google/protobuf/Protobuf;

    .line 2
    .line 3
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 7
    .line 8
    .line 9
    move-result-object v1

    .line 10
    invoke-virtual {v0, v1}, Lcom/google/protobuf/Protobuf;->schemaFor(Ljava/lang/Class;)Lcom/google/protobuf/Schema;

    .line 11
    .line 12
    .line 13
    move-result-object v0

    .line 14
    invoke-static {p1}, Lcom/google/protobuf/CodedOutputStreamWriter;->forCodedOutput(Lcom/google/protobuf/CodedOutputStream;)Lcom/google/protobuf/CodedOutputStreamWriter;

    .line 15
    .line 16
    .line 17
    move-result-object p1

    .line 18
    invoke-interface {v0, p0, p1}, Lcom/google/protobuf/Schema;->writeTo(Ljava/lang/Object;Lcom/google/protobuf/CodedOutputStreamWriter;)V

    .line 19
    .line 20
    .line 21
    return-void
.end method
