.class public final synthetic Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Landroid/window/IRemoteTransitionFinishedCallback;


# direct methods
.method public synthetic constructor <init>(Landroid/window/IRemoteTransitionFinishedCallback;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda0;->f$0:Landroid/window/IRemoteTransitionFinishedCallback;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget v0, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda0;->f$0:Landroid/window/IRemoteTransitionFinishedCallback;

    .line 8
    .line 9
    invoke-static {p0}, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1;->$r8$lambda$CIhxtG26-IjKoYIDb_bD0EAbO2M(Landroid/window/IRemoteTransitionFinishedCallback;)V

    .line 10
    .line 11
    .line 12
    return-void

    .line 13
    :goto_0
    iget-object p0, p0, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1$$ExternalSyntheticLambda0;->f$0:Landroid/window/IRemoteTransitionFinishedCallback;

    .line 14
    .line 15
    invoke-static {p0}, Lcom/samsung/android/desktopsystemui/sharedlib/system/RemoteTransitionCompat$1;->$r8$lambda$opgUtdb2WIfDCeVcUBBeh4gBRf8(Landroid/window/IRemoteTransitionFinishedCallback;)V

    .line 16
    .line 17
    .line 18
    return-void

    .line 19
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method
