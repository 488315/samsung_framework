.class final Lcom/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic $observer:Lcom/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1;


# direct methods
.method public constructor <init>(Lcom/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$1;->$observer:Lcom/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1;

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$1;->$observer:Lcom/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1;

    .line 2
    .line 3
    const/4 v0, 0x0

    .line 4
    invoke-virtual {p0, v0}, Lcom/android/systemui/qs/SettingObserver;->setListening(Z)V

    .line 5
    .line 6
    .line 7
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 8
    .line 9
    return-object p0
.end method