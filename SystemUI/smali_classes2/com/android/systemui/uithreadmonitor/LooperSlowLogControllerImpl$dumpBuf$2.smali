.class final Lcom/android/systemui/uithreadmonitor/LooperSlowLogControllerImpl$dumpBuf$2;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/uithreadmonitor/LooperSlowLogControllerImpl;-><init>(Landroid/os/Looper;Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/CoroutineDispatcher;Ldagger/Lazy;Lcom/android/systemui/dump/DumpManager;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# static fields
.field public static final INSTANCE:Lcom/android/systemui/uithreadmonitor/LooperSlowLogControllerImpl$dumpBuf$2;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/uithreadmonitor/LooperSlowLogControllerImpl$dumpBuf$2;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/uithreadmonitor/LooperSlowLogControllerImpl$dumpBuf$2;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/systemui/uithreadmonitor/LooperSlowLogControllerImpl$dumpBuf$2;->INSTANCE:Lcom/android/systemui/uithreadmonitor/LooperSlowLogControllerImpl$dumpBuf$2;

    .line 7
    .line 8
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    invoke-direct {p0, v0}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 3
    .line 4
    .line 5
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 0

    .line 1
    new-instance p0, Lkotlin/collections/ArrayDeque;

    .line 2
    .line 3
    invoke-direct {p0}, Lkotlin/collections/ArrayDeque;-><init>()V

    .line 4
    .line 5
    .line 6
    return-object p0
.end method