.class public final enum Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

.field public static final enum LARGE:Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

.field public static final enum MEDIUM:Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

.field public static final enum SMALL:Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;


# direct methods
.method public static constructor <clinit>()V
    .locals 5

    .line 1
    new-instance v0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 2
    .line 3
    const-string v1, "SMALL"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;->SMALL:Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 10
    .line 11
    new-instance v1, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 12
    .line 13
    const-string v2, "MEDIUM"

    .line 14
    .line 15
    const/4 v3, 0x1

    .line 16
    invoke-direct {v1, v2, v3}, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v1, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;->MEDIUM:Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 20
    .line 21
    new-instance v2, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 22
    .line 23
    const-string v3, "LARGE"

    .line 24
    .line 25
    const/4 v4, 0x2

    .line 26
    invoke-direct {v2, v3, v4}, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v2, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;->LARGE:Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 30
    .line 31
    filled-new-array {v0, v1, v2}, [Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 32
    .line 33
    .line 34
    move-result-object v0

    .line 35
    sput-object v0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;->$VALUES:[Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 36
    .line 37
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;->$VALUES:[Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/biometrics/ui/viewmodel/PromptSize;

    .line 8
    .line 9
    return-object v0
.end method