.class public final Landroidx/picker/model/AppData$GridAppDataBuilder;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field private final appInfo:Landroidx/picker/model/AppInfo;

.field private icon:Landroid/graphics/drawable/Drawable;

.field private label:Ljava/lang/String;

.field private subIcon:Landroid/graphics/drawable/Drawable;

.field private subLabel:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroidx/picker/model/AppInfo;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    iput-object p1, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->appInfo:Landroidx/picker/model/AppInfo;

    return-void
.end method

.method public constructor <init>(Landroidx/picker/model/AppInfoData;)V
    .locals 1

    .line 3
    invoke-interface {p1}, Landroidx/picker/model/AppData;->getAppInfo()Landroidx/picker/model/AppInfo;

    move-result-object v0

    invoke-direct {p0, v0}, Landroidx/picker/model/AppData$GridAppDataBuilder;-><init>(Landroidx/picker/model/AppInfo;)V

    .line 4
    invoke-interface {p1}, Landroidx/picker/model/AppInfoData;->getIcon()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroidx/picker/model/AppData$GridAppDataBuilder;->setIcon(Landroid/graphics/drawable/Drawable;)Landroidx/picker/model/AppData$GridAppDataBuilder;

    .line 5
    invoke-interface {p1}, Landroidx/picker/model/AppInfoData;->getSubIcon()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroidx/picker/model/AppData$GridAppDataBuilder;->setSubIcon(Landroid/graphics/drawable/Drawable;)Landroidx/picker/model/AppData$GridAppDataBuilder;

    .line 6
    invoke-interface {p1}, Landroidx/picker/model/AppInfoData;->getLabel()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Landroidx/picker/model/AppData$GridAppDataBuilder;->setLabel(Ljava/lang/String;)Landroidx/picker/model/AppData$GridAppDataBuilder;

    .line 7
    invoke-interface {p1}, Landroidx/picker/model/AppInfoData;->getSubLabel()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Landroidx/picker/model/AppData$GridAppDataBuilder;->setSubLabel(Ljava/lang/String;)Landroidx/picker/model/AppData$GridAppDataBuilder;

    return-void
.end method


# virtual methods
.method public bridge synthetic build()Landroidx/picker/model/AppData;
    .locals 0

    .line 1
    invoke-virtual {p0}, Landroidx/picker/model/AppData$GridAppDataBuilder;->build()Landroidx/picker/model/AppInfoData;

    move-result-object p0

    return-object p0
.end method

.method public build()Landroidx/picker/model/AppInfoData;
    .locals 15

    .line 2
    new-instance v14, Landroidx/picker/model/AppInfoDataImpl;

    .line 3
    iget-object v1, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->appInfo:Landroidx/picker/model/AppInfo;

    const/4 v2, 0x0

    .line 4
    iget-object v3, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->icon:Landroid/graphics/drawable/Drawable;

    .line 5
    iget-object v4, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->subIcon:Landroid/graphics/drawable/Drawable;

    .line 6
    iget-object v5, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->label:Ljava/lang/String;

    .line 7
    iget-object v6, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->subLabel:Ljava/lang/String;

    const/4 v7, 0x0

    const/4 v8, 0x0

    const/4 v9, 0x0

    const/4 v10, 0x0

    const/4 v11, 0x0

    const/16 v12, 0x7c0

    const/4 v13, 0x0

    move-object v0, v14

    .line 8
    invoke-direct/range {v0 .. v13}, Landroidx/picker/model/AppInfoDataImpl;-><init>(Landroidx/picker/model/AppInfo;ILandroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/drawable/Drawable;ZZZILkotlin/jvm/internal/DefaultConstructorMarker;)V

    return-object v14
.end method

.method public final getAppInfo()Landroidx/picker/model/AppInfo;
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->appInfo:Landroidx/picker/model/AppInfo;

    .line 2
    .line 3
    return-object p0
.end method

.method public final setIcon(Landroid/graphics/drawable/Drawable;)Landroidx/picker/model/AppData$GridAppDataBuilder;
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->icon:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    return-object p0
.end method

.method public final setLabel(Ljava/lang/String;)Landroidx/picker/model/AppData$GridAppDataBuilder;
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->label:Ljava/lang/String;

    .line 2
    .line 3
    return-object p0
.end method

.method public final setSubIcon(Landroid/graphics/drawable/Drawable;)Landroidx/picker/model/AppData$GridAppDataBuilder;
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->subIcon:Landroid/graphics/drawable/Drawable;

    .line 2
    .line 3
    return-object p0
.end method

.method public final setSubLabel(Ljava/lang/String;)Landroidx/picker/model/AppData$GridAppDataBuilder;
    .locals 0

    .line 1
    iput-object p1, p0, Landroidx/picker/model/AppData$GridAppDataBuilder;->subLabel:Ljava/lang/String;

    .line 2
    .line 3
    return-object p0
.end method
