.class public final Landroidx/picker/model/appdata/GroupAppData;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroidx/picker/model/AppData;


# instance fields
.field public final appDataList:Ljava/util/List;

.field public final appInfo:Landroidx/picker/model/AppInfo;

.field public final group:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroidx/picker/model/AppInfo;)V
    .locals 6

    .line 1
    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x6

    const/4 v5, 0x0

    move-object v0, p0

    move-object v1, p1

    invoke-direct/range {v0 .. v5}, Landroidx/picker/model/appdata/GroupAppData;-><init>(Landroidx/picker/model/AppInfo;Ljava/lang/String;Ljava/util/List;ILkotlin/jvm/internal/DefaultConstructorMarker;)V

    return-void
.end method

.method public constructor <init>(Landroidx/picker/model/AppInfo;Ljava/lang/String;)V
    .locals 6

    .line 2
    const/4 v3, 0x0

    const/4 v4, 0x4

    const/4 v5, 0x0

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    invoke-direct/range {v0 .. v5}, Landroidx/picker/model/appdata/GroupAppData;-><init>(Landroidx/picker/model/AppInfo;Ljava/lang/String;Ljava/util/List;ILkotlin/jvm/internal/DefaultConstructorMarker;)V

    return-void
.end method

.method public constructor <init>(Landroidx/picker/model/AppInfo;Ljava/lang/String;Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroidx/picker/model/AppInfo;",
            "Ljava/lang/String;",
            "Ljava/util/List<",
            "+",
            "Landroidx/picker/model/AppData;",
            ">;)V"
        }
    .end annotation

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    iput-object p1, p0, Landroidx/picker/model/appdata/GroupAppData;->appInfo:Landroidx/picker/model/AppInfo;

    .line 5
    iput-object p2, p0, Landroidx/picker/model/appdata/GroupAppData;->group:Ljava/lang/String;

    .line 6
    iput-object p3, p0, Landroidx/picker/model/appdata/GroupAppData;->appDataList:Ljava/util/List;

    return-void
.end method

.method public constructor <init>(Landroidx/picker/model/AppInfo;Ljava/lang/String;Ljava/util/List;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .locals 0

    and-int/lit8 p5, p4, 0x2

    if-eqz p5, :cond_0

    const-string p2, ""

    :cond_0
    and-int/lit8 p4, p4, 0x4

    if-eqz p4, :cond_1

    .line 11
    sget-object p3, Lkotlin/collections/EmptyList;->INSTANCE:Lkotlin/collections/EmptyList;

    .line 12
    :cond_1
    invoke-direct {p0, p1, p2, p3}, Landroidx/picker/model/appdata/GroupAppData;-><init>(Landroidx/picker/model/AppInfo;Ljava/lang/String;Ljava/util/List;)V

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;I)V
    .locals 7

    .line 8
    sget-object v0, Landroidx/picker/model/AppInfo;->Companion:Landroidx/picker/model/AppInfo$Companion;

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 9
    new-instance v2, Landroidx/picker/model/AppInfo;

    invoke-direct {v2, p1, p2, p3}, Landroidx/picker/model/AppInfo;-><init>(Ljava/lang/String;Ljava/lang/String;I)V

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x6

    const/4 v6, 0x0

    move-object v1, p0

    .line 10
    invoke-direct/range {v1 .. v6}, Landroidx/picker/model/appdata/GroupAppData;-><init>(Landroidx/picker/model/AppInfo;Ljava/lang/String;Ljava/util/List;ILkotlin/jvm/internal/DefaultConstructorMarker;)V

    return-void
.end method

.method public synthetic constructor <init>(Ljava/lang/String;Ljava/lang/String;IILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .locals 0

    and-int/lit8 p4, p4, 0x4

    if-eqz p4, :cond_0

    const/4 p3, 0x0

    .line 7
    :cond_0
    invoke-direct {p0, p1, p2, p3}, Landroidx/picker/model/appdata/GroupAppData;-><init>(Ljava/lang/String;Ljava/lang/String;I)V

    return-void
.end method


# virtual methods
.method public final getAppInfo()Landroidx/picker/model/AppInfo;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/picker/model/appdata/GroupAppData;->appInfo:Landroidx/picker/model/AppInfo;

    .line 2
    .line 3
    return-object p0
.end method
