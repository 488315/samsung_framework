.class public final Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId$QuitDevices;
.super Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final INSTANCE:Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId$QuitDevices;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId$QuitDevices;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId$QuitDevices;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId$QuitDevices;->INSTANCE:Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId$QuitDevices;

    .line 7
    .line 8
    return-void
.end method

.method private constructor <init>()V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    invoke-direct {p0, v0}, Lcom/android/systemui/controls/ui/util/SystemUIAnalyticsWrapper$EventId;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 3
    .line 4
    .line 5
    return-void
.end method


# virtual methods
.method public final getEventId()Ljava/lang/String;
    .locals 0

    .line 1
    const-string p0, "Dvcs920"

    .line 2
    .line 3
    return-object p0
.end method
