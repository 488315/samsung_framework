.class public final synthetic Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper;->mLocale:Ljava/util/Locale;

    .line 4
    .line 5
    invoke-static {p0}, Lcom/android/internal/app/LocalePicker;->updateLocale(Ljava/util/Locale;)V

    .line 6
    .line 7
    .line 8
    return-void
.end method