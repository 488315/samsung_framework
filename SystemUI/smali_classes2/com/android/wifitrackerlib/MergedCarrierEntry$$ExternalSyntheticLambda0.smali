.class public final synthetic Lcom/android/wifitrackerlib/MergedCarrierEntry$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wifitrackerlib/MergedCarrierEntry;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wifitrackerlib/MergedCarrierEntry;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wifitrackerlib/MergedCarrierEntry$$ExternalSyntheticLambda0;->f$0:Lcom/android/wifitrackerlib/MergedCarrierEntry;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/wifitrackerlib/MergedCarrierEntry$$ExternalSyntheticLambda0;->f$0:Lcom/android/wifitrackerlib/MergedCarrierEntry;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/wifitrackerlib/WifiEntry;->mConnectCallback:Lcom/android/wifitrackerlib/WifiEntry$ConnectCallback;

    .line 4
    .line 5
    if-eqz p0, :cond_0

    .line 6
    .line 7
    const/4 v0, 0x0

    .line 8
    invoke-interface {p0, v0}, Lcom/android/wifitrackerlib/WifiEntry$ConnectCallback;->onConnectResult(I)V

    .line 9
    .line 10
    .line 11
    :cond_0
    return-void
.end method
