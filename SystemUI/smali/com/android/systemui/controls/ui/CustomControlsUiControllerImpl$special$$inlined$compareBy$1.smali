.class public final Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$special$$inlined$compareBy$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/Comparator;


# instance fields
.field public final synthetic $comparator:Ljava/util/Comparator;


# direct methods
.method public constructor <init>(Ljava/util/Comparator;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$special$$inlined$compareBy$1;->$comparator:Ljava/util/Comparator;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final compare(Ljava/lang/Object;Ljava/lang/Object;)I
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/controls/ui/CustomControlsUiControllerImpl$special$$inlined$compareBy$1;->$comparator:Ljava/util/Comparator;

    .line 2
    .line 3
    check-cast p1, Lcom/android/systemui/controls/ui/ControlsSelectionItem;

    .line 4
    .line 5
    invoke-virtual {p1}, Lcom/android/systemui/controls/ui/ControlsSelectionItem;->getAppName()Ljava/lang/CharSequence;

    .line 6
    .line 7
    .line 8
    move-result-object p1

    .line 9
    check-cast p2, Lcom/android/systemui/controls/ui/ControlsSelectionItem;

    .line 10
    .line 11
    invoke-virtual {p2}, Lcom/android/systemui/controls/ui/ControlsSelectionItem;->getAppName()Ljava/lang/CharSequence;

    .line 12
    .line 13
    .line 14
    move-result-object p2

    .line 15
    invoke-interface {p0, p1, p2}, Ljava/util/Comparator;->compare(Ljava/lang/Object;Ljava/lang/Object;)I

    .line 16
    .line 17
    .line 18
    move-result p0

    .line 19
    return p0
.end method
