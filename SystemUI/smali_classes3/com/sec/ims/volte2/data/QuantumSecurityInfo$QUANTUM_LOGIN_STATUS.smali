.class public Lcom/sec/ims/volte2/data/QuantumSecurityInfo$QUANTUM_LOGIN_STATUS;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/sec/ims/volte2/data/QuantumSecurityInfo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "QUANTUM_LOGIN_STATUS"
.end annotation


# static fields
.field public static final LOGIN_STATUS_CHANGE_LOGIN_FAIL:I = 0x194

.field public static final LOGIN_STATUS_GET_CHANGE_FAIL:I = 0x191

.field public static final LOGIN_STATUS_GET_KEY_HANDLE_FAIL:I = 0x192

.field public static final LOGIN_STATUS_INVALID:I = -0x1

.field public static final LOGIN_STATUS_RANDOM_ENCRYPT_FAIL:I = 0x193

.field public static final LOGIN_STATUS_SUCCESS:I = 0x0

.field public static final LOGIN_STATUS_TOKEN_STORAGE_FAIL:I = 0x195


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
