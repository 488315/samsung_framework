.class public interface abstract Lcom/android/systemui/plugins/qs/DetailAdapter;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation runtime Lcom/android/systemui/plugins/annotations/ProvidesInterface;
    version = 0x1
.end annotation


# static fields
.field public static final INVALID:Lcom/android/internal/logging/UiEventLogger$UiEventEnum;

.field public static final VERSION:I = 0x1


# direct methods
.method public static synthetic $r8$lambda$f_ZUbX7OnQsH-TlUZDJjRKvAVqM()I
    .locals 1

    .line 1
    invoke-static {}, Lcom/android/systemui/plugins/qs/DetailAdapter;->lambda$static$0()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    return v0
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/plugins/qs/DetailAdapter$$ExternalSyntheticLambda0;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/plugins/qs/DetailAdapter$$ExternalSyntheticLambda0;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/systemui/plugins/qs/DetailAdapter;->INVALID:Lcom/android/internal/logging/UiEventLogger$UiEventEnum;

    .line 7
    .line 8
    return-void
.end method

.method private static synthetic lambda$static$0()I
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    return v0
.end method


# virtual methods
.method public closeDetailEvent()Lcom/android/internal/logging/UiEventLogger$UiEventEnum;
    .locals 0

    .line 1
    sget-object p0, Lcom/android/systemui/plugins/qs/DetailAdapter;->INVALID:Lcom/android/internal/logging/UiEventLogger$UiEventEnum;

    .line 2
    .line 3
    return-object p0
.end method

.method public abstract createDetailView(Landroid/content/Context;Landroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
.end method

.method public disallowStartSettingsIntent()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public dismissListPopupWindow()V
    .locals 0

    .line 1
    return-void
.end method

.method public getDetailAdapterSummary()Ljava/lang/String;
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return-object p0
.end method

.method public getDoneText()I
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public abstract getMetricsCategory()I
.end method

.method public abstract getSettingsIntent()Landroid/content/Intent;
.end method

.method public getSettingsText()I
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public abstract getTitle()Ljava/lang/CharSequence;
.end method

.method public getToggleEnabled()Z
    .locals 0

    .line 1
    const/4 p0, 0x1

    .line 2
    return p0
.end method

.method public abstract getToggleState()Ljava/lang/Boolean;
.end method

.method public hasHeader()Z
    .locals 0

    .line 1
    const/4 p0, 0x1

    .line 2
    return p0
.end method

.method public moreSettingsEvent()Lcom/android/internal/logging/UiEventLogger$UiEventEnum;
    .locals 0

    .line 1
    sget-object p0, Lcom/android/systemui/plugins/qs/DetailAdapter;->INVALID:Lcom/android/internal/logging/UiEventLogger$UiEventEnum;

    .line 2
    .line 3
    return-object p0
.end method

.method public onDoneButtonClicked()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method

.method public openDetailEvent()Lcom/android/internal/logging/UiEventLogger$UiEventEnum;
    .locals 0

    .line 1
    sget-object p0, Lcom/android/systemui/plugins/qs/DetailAdapter;->INVALID:Lcom/android/internal/logging/UiEventLogger$UiEventEnum;

    .line 2
    .line 3
    return-object p0
.end method

.method public abstract setToggleState(Z)V
.end method

.method public shouldAnimate()Z
    .locals 0

    .line 1
    const/4 p0, 0x1

    .line 2
    return p0
.end method

.method public shouldUseFullScreen()Z
    .locals 0

    .line 1
    const/4 p0, 0x0

    .line 2
    return p0
.end method
