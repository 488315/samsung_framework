.class public abstract Lcom/android/systemui/unfold/progress/IUnfoldAnimation$Stub;
.super Landroid/os/Binder;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    invoke-direct {p0}, Landroid/os/Binder;-><init>()V

    .line 2
    .line 3
    .line 4
    const-string v0, "com.android.systemui.unfold.progress.IUnfoldAnimation"

    .line 5
    .line 6
    invoke-virtual {p0, p0, v0}, Landroid/os/Binder;->attachInterface(Landroid/os/IInterface;Ljava/lang/String;)V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final asBinder()Landroid/os/IBinder;
    .locals 0

    .line 1
    return-object p0
.end method

.method public final onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z
    .locals 3

    .line 1
    const-string v0, "com.android.systemui.unfold.progress.IUnfoldAnimation"

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    if-lt p1, v1, :cond_0

    .line 5
    .line 6
    const v2, 0xffffff

    .line 7
    .line 8
    .line 9
    if-gt p1, v2, :cond_0

    .line 10
    .line 11
    invoke-virtual {p2, v0}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 12
    .line 13
    .line 14
    :cond_0
    const v2, 0x5f4e5446

    .line 15
    .line 16
    .line 17
    if-eq p1, v2, :cond_4

    .line 18
    .line 19
    if-eq p1, v1, :cond_1

    .line 20
    .line 21
    invoke-super {p0, p1, p2, p3, p4}, Landroid/os/Binder;->onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    .line 22
    .line 23
    .line 24
    move-result p0

    .line 25
    return p0

    .line 26
    :cond_1
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    .line 27
    .line 28
    .line 29
    move-result-object p1

    .line 30
    if-nez p1, :cond_2

    .line 31
    .line 32
    const/4 p1, 0x0

    .line 33
    goto :goto_0

    .line 34
    :cond_2
    const-string p3, "com.android.systemui.unfold.progress.IUnfoldTransitionListener"

    .line 35
    .line 36
    invoke-interface {p1, p3}, Landroid/os/IBinder;->queryLocalInterface(Ljava/lang/String;)Landroid/os/IInterface;

    .line 37
    .line 38
    .line 39
    move-result-object p3

    .line 40
    if-eqz p3, :cond_3

    .line 41
    .line 42
    instance-of p4, p3, Lcom/android/systemui/unfold/progress/IUnfoldTransitionListener;

    .line 43
    .line 44
    if-eqz p4, :cond_3

    .line 45
    .line 46
    move-object p1, p3

    .line 47
    check-cast p1, Lcom/android/systemui/unfold/progress/IUnfoldTransitionListener;

    .line 48
    .line 49
    goto :goto_0

    .line 50
    :cond_3
    new-instance p3, Lcom/android/systemui/unfold/progress/IUnfoldTransitionListener$Stub$Proxy;

    .line 51
    .line 52
    invoke-direct {p3, p1}, Lcom/android/systemui/unfold/progress/IUnfoldTransitionListener$Stub$Proxy;-><init>(Landroid/os/IBinder;)V

    .line 53
    .line 54
    .line 55
    move-object p1, p3

    .line 56
    :goto_0
    invoke-virtual {p2}, Landroid/os/Parcel;->enforceNoDataAvail()V

    .line 57
    .line 58
    .line 59
    check-cast p0, Lcom/android/systemui/unfold/progress/UnfoldTransitionProgressForwarder;

    .line 60
    .line 61
    iput-object p1, p0, Lcom/android/systemui/unfold/progress/UnfoldTransitionProgressForwarder;->remoteListener:Lcom/android/systemui/unfold/progress/IUnfoldTransitionListener;

    .line 62
    .line 63
    return v1

    .line 64
    :cond_4
    invoke-virtual {p3, v0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 65
    .line 66
    .line 67
    return v1
.end method
