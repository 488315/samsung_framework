.class public final Lcom/android/systemui/statusbar/notification/dagger/NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljavax/inject/Provider;


# instance fields
.field public final builderProvider:Ljavax/inject/Provider;


# direct methods
.method public constructor <init>(Ljavax/inject/Provider;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljavax/inject/Provider;",
            ")V"
        }
    .end annotation

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/dagger/NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory;->builderProvider:Ljavax/inject/Provider;

    .line 5
    .line 6
    return-void
.end method

.method public static providesPeopleHeaderSubcomponent(Ljavax/inject/Provider;)Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent;
    .locals 1

    .line 1
    sget v0, Lcom/android/systemui/statusbar/notification/dagger/NotificationSectionHeadersModule;->$r8$clinit:I

    .line 2
    .line 3
    invoke-interface {p0}, Ljavax/inject/Provider;->get()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;

    .line 8
    .line 9
    const-string/jumbo v0, "people header"

    .line 10
    .line 11
    .line 12
    invoke-interface {p0, v0}, Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;->nodeLabel(Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;

    .line 13
    .line 14
    .line 15
    move-result-object p0

    .line 16
    const v0, 0x7f130c47

    .line 17
    .line 18
    .line 19
    invoke-interface {p0, v0}, Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;->headerText(I)Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;

    .line 20
    .line 21
    .line 22
    move-result-object p0

    .line 23
    const-string v0, "android.settings.CONVERSATION_SETTINGS"

    .line 24
    .line 25
    invoke-interface {p0, v0}, Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;->clickIntentAction(Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;

    .line 26
    .line 27
    .line 28
    move-result-object p0

    .line 29
    invoke-interface {p0}, Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent$Builder;->build()Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent;

    .line 30
    .line 31
    .line 32
    move-result-object p0

    .line 33
    invoke-static {p0}, Ldagger/internal/Preconditions;->checkNotNullFromProvides(Ljava/lang/Object;)V

    .line 34
    .line 35
    .line 36
    return-object p0
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/dagger/NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory;->builderProvider:Ljavax/inject/Provider;

    .line 2
    .line 3
    invoke-static {p0}, Lcom/android/systemui/statusbar/notification/dagger/NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory;->providesPeopleHeaderSubcomponent(Ljavax/inject/Provider;)Lcom/android/systemui/statusbar/notification/dagger/SectionHeaderControllerSubcomponent;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    return-object p0
.end method