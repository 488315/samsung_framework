.class final Lcom/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1;
.super Lkotlin/coroutines/jvm/internal/ContinuationImpl;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation runtime Lkotlin/coroutines/jvm/internal/DebugMetadata;
    c = "com.android.systemui.screenshot.ScreenshotPolicyImpl"
    f = "ScreenshotPolicyImpl.kt"
    l = {
        0x4d
    }
    m = "isManagedProfile$suspendImpl"
.end annotation


# instance fields
.field label:I

.field synthetic result:Ljava/lang/Object;

.field final synthetic this$0:Lcom/android/systemui/screenshot/ScreenshotPolicyImpl;


# direct methods
.method public constructor <init>(Lcom/android/systemui/screenshot/ScreenshotPolicyImpl;Lkotlin/coroutines/Continuation;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/android/systemui/screenshot/ScreenshotPolicyImpl;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1;",
            ">;)V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lcom/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1;->this$0:Lcom/android/systemui/screenshot/ScreenshotPolicyImpl;

    .line 2
    .line 3
    invoke-direct {p0, p2}, Lkotlin/coroutines/jvm/internal/ContinuationImpl;-><init>(Lkotlin/coroutines/Continuation;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1

    .line 1
    iput-object p1, p0, Lcom/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1;->result:Ljava/lang/Object;

    .line 2
    .line 3
    iget p1, p0, Lcom/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1;->label:I

    .line 4
    .line 5
    const/high16 v0, -0x80000000

    .line 6
    .line 7
    or-int/2addr p1, v0

    .line 8
    iput p1, p0, Lcom/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1;->label:I

    .line 9
    .line 10
    iget-object p1, p0, Lcom/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1;->this$0:Lcom/android/systemui/screenshot/ScreenshotPolicyImpl;

    .line 11
    .line 12
    const/4 v0, 0x0

    .line 13
    invoke-static {p1, v0, p0}, Lcom/android/systemui/screenshot/ScreenshotPolicyImpl;->isManagedProfile$suspendImpl(Lcom/android/systemui/screenshot/ScreenshotPolicyImpl;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    return-object p0
.end method
