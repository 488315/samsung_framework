.class public final Lcom/android/systemui/searcle/SearcleManager$startSearcleByHomeKey$1$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/searcle/SearcleManager;


# direct methods
.method public constructor <init>(Lcom/android/systemui/searcle/SearcleManager;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/searcle/SearcleManager$startSearcleByHomeKey$1$1;->this$0:Lcom/android/systemui/searcle/SearcleManager;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 2

    .line 1
    check-cast p1, Ljava/lang/Boolean;

    .line 2
    .line 3
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    .line 4
    .line 5
    .line 6
    move-result p1

    .line 7
    iget-object p0, p0, Lcom/android/systemui/searcle/SearcleManager$startSearcleByHomeKey$1$1;->this$0:Lcom/android/systemui/searcle/SearcleManager;

    .line 8
    .line 9
    if-eqz p1, :cond_1

    .line 10
    .line 11
    sget p1, Lcom/android/systemui/searcle/SearcleManager;->$r8$clinit:I

    .line 12
    .line 13
    invoke-virtual {p0}, Lcom/android/systemui/searcle/SearcleManager;->isUnavailableSearchAppCheck()Z

    .line 14
    .line 15
    .line 16
    move-result p1

    .line 17
    iput-boolean p1, p0, Lcom/android/systemui/searcle/SearcleManager;->isUnavailableSearchApp:Z

    .line 18
    .line 19
    if-eqz p1, :cond_0

    .line 20
    .line 21
    new-instance v0, Ljava/lang/StringBuilder;

    .line 22
    .line 23
    const-string/jumbo v1, "startSearcleByHomeKey retry isUnavailableSearchApp = "

    .line 24
    .line 25
    .line 26
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 27
    .line 28
    .line 29
    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 30
    .line 31
    .line 32
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 33
    .line 34
    .line 35
    move-result-object p1

    .line 36
    const-string v0, "SearcleManager"

    .line 37
    .line 38
    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 39
    .line 40
    .line 41
    invoke-virtual {p0}, Lcom/android/systemui/searcle/SearcleManager;->showToast()V

    .line 42
    .line 43
    .line 44
    goto :goto_0

    .line 45
    :cond_0
    invoke-virtual {p0}, Lcom/android/systemui/searcle/SearcleManager;->invokeSearcle()V

    .line 46
    .line 47
    .line 48
    goto :goto_0

    .line 49
    :cond_1
    iget-object p1, p0, Lcom/android/systemui/searcle/SearcleManager;->context:Landroid/content/Context;

    .line 50
    .line 51
    const v0, 0x7f130ede

    .line 52
    .line 53
    .line 54
    invoke-virtual {p1, v0}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    .line 55
    .line 56
    .line 57
    move-result-object p1

    .line 58
    iput-object p1, p0, Lcom/android/systemui/searcle/SearcleManager;->toastMsg:Ljava/lang/CharSequence;

    .line 59
    .line 60
    invoke-virtual {p0}, Lcom/android/systemui/searcle/SearcleManager;->showToast()V

    .line 61
    .line 62
    .line 63
    :goto_0
    return-void
.end method