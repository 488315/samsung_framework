.class public final Lcom/android/app/viewcapture/SimpleViewCapture$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/concurrent/Callable;


# static fields
.field public static final INSTANCE:Lcom/android/app/viewcapture/SimpleViewCapture$1;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/app/viewcapture/SimpleViewCapture$1;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/app/viewcapture/SimpleViewCapture$1;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/app/viewcapture/SimpleViewCapture$1;->INSTANCE:Lcom/android/app/viewcapture/SimpleViewCapture$1;

    .line 7
    .line 8
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final call()Ljava/lang/Object;
    .locals 0

    .line 1
    invoke-static {}, Landroid/view/Choreographer;->getInstance()Landroid/view/Choreographer;

    .line 2
    .line 3
    .line 4
    move-result-object p0

    .line 5
    return-object p0
.end method