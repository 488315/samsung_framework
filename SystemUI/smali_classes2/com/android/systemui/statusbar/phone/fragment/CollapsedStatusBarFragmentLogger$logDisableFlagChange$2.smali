.class final Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger$logDisableFlagChange$2;
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
.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger$logDisableFlagChange$2;->this$0:Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger;

    .line 2
    .line 3
    const/4 p1, 0x1

    .line 4
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 2

    .line 1
    check-cast p1, Lcom/android/systemui/log/LogMessage;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger$logDisableFlagChange$2;->this$0:Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/systemui/statusbar/phone/fragment/CollapsedStatusBarFragmentLogger;->disableFlagsLogger:Lcom/android/systemui/statusbar/disableflags/DisableFlagsLogger;

    .line 6
    .line 7
    new-instance v0, Lcom/android/systemui/statusbar/disableflags/DisableFlagsLogger$DisableState;

    .line 8
    .line 9
    invoke-interface {p1}, Lcom/android/systemui/log/LogMessage;->getInt1()I

    .line 10
    .line 11
    .line 12
    move-result v1

    .line 13
    invoke-interface {p1}, Lcom/android/systemui/log/LogMessage;->getInt2()I

    .line 14
    .line 15
    .line 16
    move-result p1

    .line 17
    invoke-direct {v0, v1, p1}, Lcom/android/systemui/statusbar/disableflags/DisableFlagsLogger$DisableState;-><init>(II)V

    .line 18
    .line 19
    .line 20
    const/4 p1, 0x0

    .line 21
    invoke-virtual {p0, p1, v0, p1}, Lcom/android/systemui/statusbar/disableflags/DisableFlagsLogger;->getDisableFlagsString(Lcom/android/systemui/statusbar/disableflags/DisableFlagsLogger$DisableState;Lcom/android/systemui/statusbar/disableflags/DisableFlagsLogger$DisableState;Lcom/android/systemui/statusbar/disableflags/DisableFlagsLogger$DisableState;)Ljava/lang/String;

    .line 22
    .line 23
    .line 24
    move-result-object p0

    .line 25
    return-object p0
.end method
