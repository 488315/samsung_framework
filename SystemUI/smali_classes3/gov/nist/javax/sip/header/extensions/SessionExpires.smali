.class public final Lgov/nist/javax/sip/header/extensions/SessionExpires;
.super Lgov/nist/javax/sip/header/ParametersHeader;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field private static final serialVersionUID:J = 0x79a63e78c9b0a324L


# instance fields
.field public expires:I


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    const-string v0, "Session-Expires"

    .line 2
    .line 3
    invoke-direct {p0, v0}, Lgov/nist/javax/sip/header/ParametersHeader;-><init>(Ljava/lang/String;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final encodeBody()Ljava/lang/String;
    .locals 2

    .line 1
    iget v0, p0, Lgov/nist/javax/sip/header/extensions/SessionExpires;->expires:I

    .line 2
    .line 3
    invoke-static {v0}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    iget-object v1, p0, Lgov/nist/javax/sip/header/ParametersHeader;->parameters:Lgov/nist/core/NameValueList;

    .line 8
    .line 9
    invoke-virtual {v1}, Lgov/nist/core/NameValueList;->isEmpty()Z

    .line 10
    .line 11
    .line 12
    move-result v1

    .line 13
    if-nez v1, :cond_0

    .line 14
    .line 15
    const-string v1, ";"

    .line 16
    .line 17
    invoke-static {v0, v1}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 18
    .line 19
    .line 20
    move-result-object v0

    .line 21
    iget-object p0, p0, Lgov/nist/javax/sip/header/ParametersHeader;->parameters:Lgov/nist/core/NameValueList;

    .line 22
    .line 23
    invoke-virtual {p0}, Lgov/nist/core/NameValueList;->encode()Ljava/lang/String;

    .line 24
    .line 25
    .line 26
    move-result-object p0

    .line 27
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 28
    .line 29
    .line 30
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 31
    .line 32
    .line 33
    move-result-object v0

    .line 34
    :cond_0
    return-object v0
.end method

.method public final setExpires(I)V
    .locals 1

    .line 1
    if-ltz p1, :cond_0

    .line 2
    .line 3
    iput p1, p0, Lgov/nist/javax/sip/header/extensions/SessionExpires;->expires:I

    .line 4
    .line 5
    return-void

    .line 6
    :cond_0
    new-instance p0, Ljavax/sip/InvalidArgumentException;

    .line 7
    .line 8
    const-string v0, "bad argument "

    .line 9
    .line 10
    invoke-static {v0, p1}, Landroid/support/v4/media/MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;->m(Ljava/lang/String;I)Ljava/lang/String;

    .line 11
    .line 12
    .line 13
    move-result-object p1

    .line 14
    invoke-direct {p0, p1}, Ljavax/sip/InvalidArgumentException;-><init>(Ljava/lang/String;)V

    .line 15
    .line 16
    .line 17
    throw p0
.end method
