.class public final Lcom/samsung/android/knox/ex/peripheral/PeripheralManager$9;
.super Lcom/samsung/android/knox/ex/peripheral/IInfoListener$Stub;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/samsung/android/knox/ex/peripheral/PeripheralManager;->registerInfoListener(Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;)I
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = null
.end annotation


# instance fields
.field public final synthetic this$0:Lcom/samsung/android/knox/ex/peripheral/PeripheralManager;

.field public final synthetic val$listener:Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;


# direct methods
.method public constructor <init>(Lcom/samsung/android/knox/ex/peripheral/PeripheralManager;Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/samsung/android/knox/ex/peripheral/PeripheralManager$9;->this$0:Lcom/samsung/android/knox/ex/peripheral/PeripheralManager;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/samsung/android/knox/ex/peripheral/PeripheralManager$9;->val$listener:Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;

    .line 4
    .line 5
    invoke-direct {p0}, Lcom/samsung/android/knox/ex/peripheral/IInfoListener$Stub;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final getHashCode()J
    .locals 2

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/ex/peripheral/PeripheralManager$9;->val$listener:Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/lang/Object;->hashCode()I

    .line 4
    .line 5
    .line 6
    move-result p0

    .line 7
    int-to-long v0, p0

    .line 8
    return-wide v0
.end method

.method public final onFail(ILjava/lang/String;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/ex/peripheral/PeripheralManager$9;->val$listener:Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;

    .line 2
    .line 3
    invoke-interface {p0, p1, p2}, Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;->onFail(ILjava/lang/String;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final onReceive(Landroid/os/Bundle;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/ex/peripheral/PeripheralManager$9;->val$listener:Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;

    .line 2
    .line 3
    invoke-interface {p0, p1}, Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;->onReceive(Landroid/os/Bundle;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final onSuccess()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/ex/peripheral/PeripheralManager$9;->val$listener:Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;

    .line 2
    .line 3
    invoke-interface {p0}, Lcom/samsung/android/knox/ex/peripheral/PeripheralInfoListener;->onSuccess()V

    .line 4
    .line 5
    .line 6
    return-void
.end method
