.class public Lgov/nist/javax/sip/header/Expires;
.super Lgov/nist/javax/sip/header/SIPHeader;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field private static final serialVersionUID:J = 0x2b7f6e1819e3cbcbL


# instance fields
.field protected expires:I


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 1
    const-string v0, "Expires"

    .line 2
    .line 3
    invoke-direct {p0, v0}, Lgov/nist/javax/sip/header/SIPHeader;-><init>(Ljava/lang/String;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final encodeBody()Ljava/lang/String;
    .locals 1

    .line 1
    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    .line 2
    iget p0, p0, Lgov/nist/javax/sip/header/Expires;->expires:I

    invoke-virtual {v0, p0}, Ljava/lang/StringBuffer;->append(I)Ljava/lang/StringBuffer;

    .line 3
    invoke-virtual {v0}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public final encodeBody(Ljava/lang/StringBuffer;)V
    .locals 0

    .line 4
    iget p0, p0, Lgov/nist/javax/sip/header/Expires;->expires:I

    invoke-virtual {p1, p0}, Ljava/lang/StringBuffer;->append(I)Ljava/lang/StringBuffer;

    return-void
.end method

.method public final setExpires(I)V
    .locals 1

    .line 1
    if-ltz p1, :cond_0

    .line 2
    .line 3
    iput p1, p0, Lgov/nist/javax/sip/header/Expires;->expires:I

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