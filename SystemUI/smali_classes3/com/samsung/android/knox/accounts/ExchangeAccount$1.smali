.class public final Lcom/samsung/android/knox/accounts/ExchangeAccount$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/Parcelable$Creator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/knox/accounts/ExchangeAccount;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Landroid/os/Parcelable$Creator<",
        "Lcom/samsung/android/knox/accounts/ExchangeAccount;",
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
.method public final createFromParcel(Landroid/os/Parcel;)Lcom/samsung/android/knox/accounts/ExchangeAccount;
    .locals 0

    .line 1
    new-instance p0, Lcom/samsung/android/knox/accounts/ExchangeAccount;

    invoke-direct {p0, p1}, Lcom/samsung/android/knox/accounts/ExchangeAccount;-><init>(Landroid/os/Parcel;)V

    return-object p0
.end method

.method public final createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;
    .locals 0

    .line 2
    new-instance p0, Lcom/samsung/android/knox/accounts/ExchangeAccount;

    invoke-direct {p0, p1}, Lcom/samsung/android/knox/accounts/ExchangeAccount;-><init>(Landroid/os/Parcel;)V

    return-object p0
.end method

.method public final newArray(I)[Lcom/samsung/android/knox/accounts/ExchangeAccount;
    .locals 0

    .line 1
    new-array p0, p1, [Lcom/samsung/android/knox/accounts/ExchangeAccount;

    return-object p0
.end method

.method public final newArray(I)[Ljava/lang/Object;
    .locals 0

    .line 2
    new-array p0, p1, [Lcom/samsung/android/knox/accounts/ExchangeAccount;

    return-object p0
.end method
