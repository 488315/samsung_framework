.class public Lcom/sec/ims/volte2/data/ImsCallInfo$Qci;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/sec/ims/volte2/data/ImsCallInfo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Qci"
.end annotation


# static fields
.field public static final QCI_AUDIO:I = 0x1

.field public static final QCI_VIDEO_GBR:I = 0x2

.field public static final QCI_VIDEO_NGBR:I = 0x8

.field public static final QCI_VIDEO_NGBR_7:I = 0x7


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
