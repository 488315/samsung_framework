.class public abstract Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage$DefaultImpls;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public static getMessage(Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage;)Ljava/lang/String;
    .locals 1

    .line 1
    instance-of v0, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage$Error;

    .line 2
    .line 3
    if-eqz v0, :cond_0

    .line 4
    .line 5
    check-cast p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage$Error;

    .line 6
    .line 7
    iget-object p0, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage$Error;->errorMessage:Ljava/lang/String;

    .line 8
    .line 9
    goto :goto_0

    .line 10
    :cond_0
    instance-of v0, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage$Help;

    .line 11
    .line 12
    if-eqz v0, :cond_1

    .line 13
    .line 14
    check-cast p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage$Help;

    .line 15
    .line 16
    iget-object p0, p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptMessage$Help;->helpMessage:Ljava/lang/String;

    .line 17
    .line 18
    goto :goto_0

    .line 19
    :cond_1
    const-string p0, ""

    .line 20
    .line 21
    :goto_0
    return-object p0
.end method