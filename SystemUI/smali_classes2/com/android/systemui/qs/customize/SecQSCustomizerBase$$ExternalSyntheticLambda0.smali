.class public final synthetic Lcom/android/systemui/qs/customize/SecQSCustomizerBase$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/qs/customize/SecQSCustomizerBase;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/qs/customize/SecQSCustomizerBase;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerBase$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/qs/customize/SecQSCustomizerBase;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerBase$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/qs/customize/SecQSCustomizerBase;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerBase;->mSummary:Landroid/widget/LinearLayout;

    .line 4
    .line 5
    const/4 v0, 0x1

    .line 6
    invoke-virtual {p0, v0}, Landroid/widget/LinearLayout;->setSelected(Z)V

    .line 7
    .line 8
    .line 9
    return-void
.end method