.class public final Lcom/samsung/android/knox/ucm/core/SecureChannelManager;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final BUNDLE_EXTRA_SCP_ENCRYPTION:Ljava/lang/String; = "scp_encryption"

.field public static final BUNDLE_EXTRA_SCP_KEY_ID:Ljava/lang/String; = "scp_key_id"

.field public static final BUNDLE_EXTRA_SCP_KEY_LENGTH:Ljava/lang/String; = "scp_key_length"

.field public static final BUNDLE_EXTRA_SCP_KEY_PARAM_DH_G:Ljava/lang/String; = "scp_key_agreement_param_dh_g"

.field public static final BUNDLE_EXTRA_SCP_KEY_PARAM_DH_P:Ljava/lang/String; = "scp_key_agreement_param_dh_p"

.field public static final BUNDLE_EXTRA_SCP_KEY_PARAM_ECC:Ljava/lang/String; = "scp_key_agreement_param_ecc"

.field public static final BUNDLE_EXTRA_SCP_KEY_VERSION:Ljava/lang/String; = "scp_key_version"

.field public static final BUNDLE_EXTRA_SCP_MAC:Ljava/lang/String; = "scp_mac"

.field public static final BUNDLE_EXTRA_SCP_PROTOCOL:Ljava/lang/String; = "scp_protocol"

.field public static final BUNDLE_SCP_KEY_PARAM_ECC_FRP_P256:I = 0x40

.field public static final BUNDLE_SCP_KEY_PARAM_ECC_NIST_P256:I = 0x0

.field public static final DBG:Z = true

.field public static final ERROR_APDU_PARSING:I = 0x34

.field public static final ERROR_CA_CERT_NOT_FOUND:I = 0x37

.field public static final ERROR_CHANNEL_NOT_FOUND:I = 0x35

.field public static final ERROR_DEVICE_COMPROMISED:I = 0x3e

.field public static final ERROR_INTERNAL:I = 0x32

.field public static final ERROR_INTERNAL_CRYPTO_FAILED:I = 0x39

.field public static final ERROR_INVALID_MESSAGE_TYPE:I = 0x40

.field public static final ERROR_INVALID_PERMISSION:I = 0x41

.field public static final ERROR_INVALID_PROTOCOL:I = 0x3f

.field public static final ERROR_INVALID_SD_CERT:I = 0x3b

.field public static final ERROR_INVALID_SD_MAC:I = 0x3d

.field public static final ERROR_INVALID_SD_RECEIPT:I = 0x3c

.field public static final ERROR_NOT_SUPPORTED_CURVE:I = 0x3a

.field public static final ERROR_NO_INTERNAL_MEMORY:I = 0x36

.field public static final ERROR_SD_CERT_NOT_FOUND:I = 0x38

.field public static final ERROR_TLV_PARSING:I = 0x33

.field public static final ERROR_TZ_APP_CONNECTION_FAILED:I = 0x42

.field public static final MESSAGE_TYPE_COMMAND:I = 0xc8

.field public static final MESSAGE_TYPE_FORWARD_TO_SD:I = 0x190

.field public static final MESSAGE_TYPE_PROCESSING_COMPLETED:I = 0x191

.field public static final MESSAGE_TYPE_RESPONSE:I = 0xc9

.field public static final MESSAGE_TYPE_SECURE_CHANNEL:I = 0xca

.field public static final PROTOCOL_TYPE_SCP11A:I = 0x64

.field public static final PROTOCOL_TYPE_SCP11B:I = 0x65

.field public static final PROTOCOL_TYPE_SCP_CUSTOM:I = 0x66

.field public static final PROTOCOL_TYPE_SCP_OTHER:I = 0x67

.field public static final SERVICE_NAME:Ljava/lang/String; = "com.samsung.ucs.ucsservice"

.field public static final STATUS_FAILURE:I = 0x1

.field public static final STATUS_SC_CONSTRUCTED:I = 0x12c

.field public static final STATUS_SC_REQUIRED:I = 0x12d

.field public static final STATUS_SUCCESS:I = 0x0

.field public static final TAG:Ljava/lang/String; = "SecureChannelManager"


# instance fields
.field public final mBinder:Lcom/samsung/android/knox/ucm/core/IUcmService;

.field public mContextInfo:Lcom/samsung/android/knox/ContextInfo;


# direct methods
.method private constructor <init>(Lcom/samsung/android/knox/ucm/core/IUcmService;)V
    .locals 1

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/samsung/android/knox/ucm/core/SecureChannelManager;->mBinder:Lcom/samsung/android/knox/ucm/core/IUcmService;

    .line 5
    .line 6
    new-instance p1, Lcom/samsung/android/knox/ContextInfo;

    .line 7
    .line 8
    invoke-static {}, Landroid/os/Process;->myUid()I

    .line 9
    .line 10
    .line 11
    move-result v0

    .line 12
    invoke-direct {p1, v0}, Lcom/samsung/android/knox/ContextInfo;-><init>(I)V

    .line 13
    .line 14
    .line 15
    iput-object p1, p0, Lcom/samsung/android/knox/ucm/core/SecureChannelManager;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 16
    .line 17
    return-void
.end method

.method public static getInstance()Lcom/samsung/android/knox/ucm/core/SecureChannelManager;
    .locals 2

    .line 1
    const-string v0, "com.samsung.ucs.ucsservice"

    .line 2
    .line 3
    invoke-static {v0}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    invoke-static {v0}, Lcom/samsung/android/knox/ucm/core/IUcmService$Stub;->asInterface(Landroid/os/IBinder;)Lcom/samsung/android/knox/ucm/core/IUcmService;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    if-nez v0, :cond_0

    .line 12
    .line 13
    const/4 v0, 0x0

    .line 14
    return-object v0

    .line 15
    :cond_0
    new-instance v1, Lcom/samsung/android/knox/ucm/core/SecureChannelManager;

    .line 16
    .line 17
    invoke-direct {v1, v0}, Lcom/samsung/android/knox/ucm/core/SecureChannelManager;-><init>(Lcom/samsung/android/knox/ucm/core/IUcmService;)V

    .line 18
    .line 19
    .line 20
    return-object v1
.end method


# virtual methods
.method public final createSecureChannel(ILandroid/os/Bundle;)Lcom/samsung/android/knox/ucm/core/ApduMessage;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/ucm/core/SecureChannelManager;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 2
    .line 3
    const-string p1, "SecureChannelManager.createSecureChannel"

    .line 4
    .line 5
    invoke-static {p0, p1}, Lcom/samsung/android/knox/license/EnterpriseLicenseManager;->log(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    const-string p0, "SecureChannelManager"

    .line 9
    .line 10
    const-string p1, "createSecureChannel is deprecated from Knox 3.10, not supported anymore."

    .line 11
    .line 12
    invoke-static {p0, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 13
    .line 14
    .line 15
    const/4 p0, 0x0

    .line 16
    return-object p0
.end method

.method public final destroySecureChannel()I
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/ucm/core/SecureChannelManager;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 2
    .line 3
    const-string v0, "SecureChannelManager.destroySecureChannel"

    .line 4
    .line 5
    invoke-static {p0, v0}, Lcom/samsung/android/knox/license/EnterpriseLicenseManager;->log(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    const-string p0, "SecureChannelManager"

    .line 9
    .line 10
    const-string v0, "destroySecureChannel is deprecated from Knox 3.10, not supported anymore"

    .line 11
    .line 12
    invoke-static {p0, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 13
    .line 14
    .line 15
    const/4 p0, 0x1

    .line 16
    return p0
.end method

.method public final processMessage(I[B)Lcom/samsung/android/knox/ucm/core/ApduMessage;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/samsung/android/knox/ucm/core/SecureChannelManager;->mContextInfo:Lcom/samsung/android/knox/ContextInfo;

    .line 2
    .line 3
    const-string p1, "SecureChannelManager.processMessage"

    .line 4
    .line 5
    invoke-static {p0, p1}, Lcom/samsung/android/knox/license/EnterpriseLicenseManager;->log(Lcom/samsung/android/knox/ContextInfo;Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    const-string p0, "SecureChannelManager"

    .line 9
    .line 10
    const-string p1, "processMessage is deprecated from Knox 3.10, not supported anymore"

    .line 11
    .line 12
    invoke-static {p0, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 13
    .line 14
    .line 15
    const/4 p0, 0x0

    .line 16
    return-object p0
.end method
