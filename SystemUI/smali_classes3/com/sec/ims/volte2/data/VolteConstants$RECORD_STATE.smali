.class public Lcom/sec/ims/volte2/data/VolteConstants$RECORD_STATE;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/sec/ims/volte2/data/VolteConstants;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "RECORD_STATE"
.end annotation


# static fields
.field public static final START_FAILURE:I = 0x1

.field public static final START_FAILURE_NO_SPACE:I = 0x2

.field public static final START_SUCCESS:I = 0x0

.field public static final STOP_FAILURE:I = 0x4

.field public static final STOP_NO_SPACE:I = 0x5

.field public static final STOP_SUCCESS:I = 0x3


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method