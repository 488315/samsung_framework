.class final Lcom/android/keyguard/KeyguardStatusContainer$dispatchDraw$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function1;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function1;"
    }
.end annotation


# instance fields
.field final synthetic $canvas:Landroid/graphics/Canvas;

.field final synthetic this$0:Lcom/android/keyguard/KeyguardStatusContainer;


# direct methods
.method public constructor <init>(Lcom/android/keyguard/KeyguardStatusContainer;Landroid/graphics/Canvas;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/keyguard/KeyguardStatusContainer$dispatchDraw$1;->this$0:Lcom/android/keyguard/KeyguardStatusContainer;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/keyguard/KeyguardStatusContainer$dispatchDraw$1;->$canvas:Landroid/graphics/Canvas;

    .line 4
    .line 5
    const/4 p1, 0x1

    .line 6
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Landroid/graphics/Canvas;

    .line 2
    .line 3
    iget-object p1, p0, Lcom/android/keyguard/KeyguardStatusContainer$dispatchDraw$1;->this$0:Lcom/android/keyguard/KeyguardStatusContainer;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/keyguard/KeyguardStatusContainer$dispatchDraw$1;->$canvas:Landroid/graphics/Canvas;

    .line 6
    .line 7
    invoke-static {p1, p0}, Lcom/android/keyguard/KeyguardStatusContainer;->access$dispatchDraw$s1127291599(Lcom/android/keyguard/KeyguardStatusContainer;Landroid/graphics/Canvas;)V

    .line 8
    .line 9
    .line 10
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 11
    .line 12
    return-object p0
.end method
