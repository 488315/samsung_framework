.class public final Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener$wakefulnessLifecycleObserver$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/keyguard/WakefulnessLifecycle$Observer;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener$wakefulnessLifecycleObserver$1;->this$0:Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onFinishedGoingToSleep()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener$wakefulnessLifecycleObserver$1;->this$0:Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener;

    .line 2
    .line 3
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/phone/IndicatorMarqueeGardener;->updateMarqueeValues()V

    .line 4
    .line 5
    .line 6
    return-void
.end method