.class public Lcom/android/systemui/people/widget/PeopleSpaceWidgetPinnedReceiver;
.super Landroid/content/BroadcastReceiver;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final synthetic $r8$clinit:I


# instance fields
.field public final mPeopleSpaceWidgetManager:Lcom/android/systemui/people/widget/PeopleSpaceWidgetManager;


# direct methods
.method public constructor <init>(Lcom/android/systemui/people/widget/PeopleSpaceWidgetManager;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/people/widget/PeopleSpaceWidgetPinnedReceiver;->mPeopleSpaceWidgetManager:Lcom/android/systemui/people/widget/PeopleSpaceWidgetManager;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 4

    .line 1
    if-eqz p1, :cond_3

    .line 2
    .line 3
    if-nez p2, :cond_0

    .line 4
    .line 5
    goto :goto_0

    .line 6
    :cond_0
    const-string p1, "appWidgetId"

    .line 7
    .line 8
    const/4 v0, -0x1

    .line 9
    invoke-virtual {p2, p1, v0}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    .line 10
    .line 11
    .line 12
    move-result p1

    .line 13
    if-ne p1, v0, :cond_1

    .line 14
    .line 15
    return-void

    .line 16
    :cond_1
    const-string v1, "android.intent.extra.shortcut.ID"

    .line 17
    .line 18
    invoke-virtual {p2, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    .line 19
    .line 20
    .line 21
    move-result-object v1

    .line 22
    const-string v2, "android.intent.extra.PACKAGE_NAME"

    .line 23
    .line 24
    invoke-virtual {p2, v2}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    .line 25
    .line 26
    .line 27
    move-result-object v2

    .line 28
    const-string v3, "android.intent.extra.USER_ID"

    .line 29
    .line 30
    invoke-virtual {p2, v3, v0}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    .line 31
    .line 32
    .line 33
    move-result p2

    .line 34
    new-instance v0, Lcom/android/systemui/people/widget/PeopleTileKey;

    .line 35
    .line 36
    invoke-direct {v0, v1, p2, v2}, Lcom/android/systemui/people/widget/PeopleTileKey;-><init>(Ljava/lang/String;ILjava/lang/String;)V

    .line 37
    .line 38
    .line 39
    invoke-static {v0}, Lcom/android/systemui/people/widget/PeopleTileKey;->isValid(Lcom/android/systemui/people/widget/PeopleTileKey;)Z

    .line 40
    .line 41
    .line 42
    move-result p2

    .line 43
    if-nez p2, :cond_2

    .line 44
    .line 45
    return-void

    .line 46
    :cond_2
    iget-object p0, p0, Lcom/android/systemui/people/widget/PeopleSpaceWidgetPinnedReceiver;->mPeopleSpaceWidgetManager:Lcom/android/systemui/people/widget/PeopleSpaceWidgetManager;

    .line 47
    .line 48
    invoke-virtual {p0, p1, v0}, Lcom/android/systemui/people/widget/PeopleSpaceWidgetManager;->addNewWidget(ILcom/android/systemui/people/widget/PeopleTileKey;)V

    .line 49
    .line 50
    .line 51
    :cond_3
    :goto_0
    return-void
.end method
