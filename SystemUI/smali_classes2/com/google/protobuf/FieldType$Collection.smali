.class final enum Lcom/google/protobuf/FieldType$Collection;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/google/protobuf/FieldType;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "Collection"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/google/protobuf/FieldType$Collection;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/google/protobuf/FieldType$Collection;

.field public static final enum MAP:Lcom/google/protobuf/FieldType$Collection;

.field public static final enum PACKED_VECTOR:Lcom/google/protobuf/FieldType$Collection;

.field public static final enum SCALAR:Lcom/google/protobuf/FieldType$Collection;

.field public static final enum VECTOR:Lcom/google/protobuf/FieldType$Collection;


# instance fields
.field private final isList:Z


# direct methods
.method public static constructor <clinit>()V
    .locals 7

    .line 1
    new-instance v0, Lcom/google/protobuf/FieldType$Collection;

    .line 2
    .line 3
    const-string v1, "SCALAR"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2, v2}, Lcom/google/protobuf/FieldType$Collection;-><init>(Ljava/lang/String;IZ)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/google/protobuf/FieldType$Collection;->SCALAR:Lcom/google/protobuf/FieldType$Collection;

    .line 10
    .line 11
    new-instance v1, Lcom/google/protobuf/FieldType$Collection;

    .line 12
    .line 13
    const-string v3, "VECTOR"

    .line 14
    .line 15
    const/4 v4, 0x1

    .line 16
    invoke-direct {v1, v3, v4, v4}, Lcom/google/protobuf/FieldType$Collection;-><init>(Ljava/lang/String;IZ)V

    .line 17
    .line 18
    .line 19
    sput-object v1, Lcom/google/protobuf/FieldType$Collection;->VECTOR:Lcom/google/protobuf/FieldType$Collection;

    .line 20
    .line 21
    new-instance v3, Lcom/google/protobuf/FieldType$Collection;

    .line 22
    .line 23
    const-string v5, "PACKED_VECTOR"

    .line 24
    .line 25
    const/4 v6, 0x2

    .line 26
    invoke-direct {v3, v5, v6, v4}, Lcom/google/protobuf/FieldType$Collection;-><init>(Ljava/lang/String;IZ)V

    .line 27
    .line 28
    .line 29
    sput-object v3, Lcom/google/protobuf/FieldType$Collection;->PACKED_VECTOR:Lcom/google/protobuf/FieldType$Collection;

    .line 30
    .line 31
    new-instance v4, Lcom/google/protobuf/FieldType$Collection;

    .line 32
    .line 33
    const-string v5, "MAP"

    .line 34
    .line 35
    const/4 v6, 0x3

    .line 36
    invoke-direct {v4, v5, v6, v2}, Lcom/google/protobuf/FieldType$Collection;-><init>(Ljava/lang/String;IZ)V

    .line 37
    .line 38
    .line 39
    sput-object v4, Lcom/google/protobuf/FieldType$Collection;->MAP:Lcom/google/protobuf/FieldType$Collection;

    .line 40
    .line 41
    filled-new-array {v0, v1, v3, v4}, [Lcom/google/protobuf/FieldType$Collection;

    .line 42
    .line 43
    .line 44
    move-result-object v0

    .line 45
    sput-object v0, Lcom/google/protobuf/FieldType$Collection;->$VALUES:[Lcom/google/protobuf/FieldType$Collection;

    .line 46
    .line 47
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;IZ)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(Z)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    iput-boolean p3, p0, Lcom/google/protobuf/FieldType$Collection;->isList:Z

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/google/protobuf/FieldType$Collection;
    .locals 1

    .line 1
    const-class v0, Lcom/google/protobuf/FieldType$Collection;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/google/protobuf/FieldType$Collection;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/google/protobuf/FieldType$Collection;
    .locals 1

    .line 1
    sget-object v0, Lcom/google/protobuf/FieldType$Collection;->$VALUES:[Lcom/google/protobuf/FieldType$Collection;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/google/protobuf/FieldType$Collection;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/google/protobuf/FieldType$Collection;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final isList()Z
    .locals 0

    .line 1
    iget-boolean p0, p0, Lcom/google/protobuf/FieldType$Collection;->isList:Z

    .line 2
    .line 3
    return p0
.end method