.class Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/Parcelable$Creator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Landroid/os/Parcelable$Creator<",
        "Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;",
        ">;"
    }
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public createFromParcel(Landroid/os/Parcel;)Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;
    .locals 1

    .line 2
    new-instance p0, Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;-><init>(Landroid/os/Parcel;Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper$1;)V

    return-object p0
.end method

.method public bridge synthetic createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;
    .locals 0

    .line 1
    invoke-virtual {p0, p1}, Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper$1;->createFromParcel(Landroid/os/Parcel;)Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;

    move-result-object p0

    return-object p0
.end method

.method public newArray(I)[Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;
    .locals 0

    .line 2
    new-array p0, p1, [Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;

    return-object p0
.end method

.method public bridge synthetic newArray(I)[Ljava/lang/Object;
    .locals 0

    .line 1
    invoke-virtual {p0, p1}, Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper$1;->newArray(I)[Lcom/samsung/android/desktopsystemui/sharedlib/keyguard/SemWallpaperColorsWrapper;

    move-result-object p0

    return-object p0
.end method
