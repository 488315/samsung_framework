.class public final Lcom/android/systemui/media/dialog/MediaItem;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final mMediaDeviceOptional:Ljava/util/Optional;

.field public final mMediaItemType:I

.field public final mTitle:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    invoke-static {}, Ljava/util/Optional;->empty()Ljava/util/Optional;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/media/dialog/MediaItem;->mMediaDeviceOptional:Ljava/util/Optional;

    const/4 v0, 0x0

    .line 3
    iput-object v0, p0, Lcom/android/systemui/media/dialog/MediaItem;->mTitle:Ljava/lang/String;

    const/4 v0, 0x2

    .line 4
    iput v0, p0, Lcom/android/systemui/media/dialog/MediaItem;->mMediaItemType:I

    return-void
.end method

.method public constructor <init>(Lcom/android/settingslib/media/MediaDevice;)V
    .locals 1

    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 10
    invoke-static {p1}, Ljava/util/Optional;->of(Ljava/lang/Object;)Ljava/util/Optional;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/media/dialog/MediaItem;->mMediaDeviceOptional:Ljava/util/Optional;

    .line 11
    invoke-virtual {p1}, Lcom/android/settingslib/media/MediaDevice;->getName()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/android/systemui/media/dialog/MediaItem;->mTitle:Ljava/lang/String;

    const/4 p1, 0x0

    .line 12
    iput p1, p0, Lcom/android/systemui/media/dialog/MediaItem;->mMediaItemType:I

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;I)V
    .locals 1

    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    invoke-static {}, Ljava/util/Optional;->empty()Ljava/util/Optional;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/media/dialog/MediaItem;->mMediaDeviceOptional:Ljava/util/Optional;

    .line 7
    iput-object p1, p0, Lcom/android/systemui/media/dialog/MediaItem;->mTitle:Ljava/lang/String;

    .line 8
    iput p2, p0, Lcom/android/systemui/media/dialog/MediaItem;->mMediaItemType:I

    return-void
.end method
