.class public final Lgov/nist/javax/sip/header/ViaList;
.super Lgov/nist/javax/sip/header/SIPHeaderList;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lgov/nist/javax/sip/header/SIPHeaderList<",
        "Lgov/nist/javax/sip/header/Via;",
        ">;"
    }
.end annotation


# static fields
.field private static final serialVersionUID:J = 0x361e71bad25641f9L


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 1
    const-class v0, Lgov/nist/javax/sip/header/Via;

    .line 2
    .line 3
    const-string v1, "Via"

    .line 4
    .line 5
    invoke-direct {p0, v0, v1}, Lgov/nist/javax/sip/header/SIPHeaderList;-><init>(Ljava/lang/Class;Ljava/lang/String;)V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final clone()Ljava/lang/Object;
    .locals 1

    .line 1
    new-instance v0, Lgov/nist/javax/sip/header/ViaList;

    .line 2
    .line 3
    invoke-direct {v0}, Lgov/nist/javax/sip/header/ViaList;-><init>()V

    .line 4
    .line 5
    .line 6
    iget-object p0, p0, Lgov/nist/javax/sip/header/SIPHeaderList;->hlist:Ljava/util/List;

    .line 7
    .line 8
    invoke-virtual {v0, p0}, Lgov/nist/javax/sip/header/SIPHeaderList;->clonehlist(Ljava/util/List;)V

    .line 9
    .line 10
    .line 11
    return-object v0
.end method
