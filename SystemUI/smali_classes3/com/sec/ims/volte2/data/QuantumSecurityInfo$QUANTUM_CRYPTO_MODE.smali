.class public Lcom/sec/ims/volte2/data/QuantumSecurityInfo$QUANTUM_CRYPTO_MODE;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/sec/ims/volte2/data/QuantumSecurityInfo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "QUANTUM_CRYPTO_MODE"
.end annotation


# static fields
.field public static final CRYPTO_MODE_INVALID:I = 0x0

.field public static final CRYPTO_MODE_SM4_CBC:I = 0x2

.field public static final CRYPTO_MODE_SM4_ECB:I = 0x1


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
