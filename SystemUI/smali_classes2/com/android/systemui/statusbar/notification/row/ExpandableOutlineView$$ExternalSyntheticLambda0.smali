.class public final synthetic Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView;

.field public final synthetic f$1:Landroid/util/IndentingPrintWriter;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView;Landroid/util/IndentingPrintWriter;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView$$ExternalSyntheticLambda0;->f$1:Landroid/util/IndentingPrintWriter;

    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView$$ExternalSyntheticLambda0;->f$1:Landroid/util/IndentingPrintWriter;

    .line 4
    .line 5
    iget-object v0, v0, Lcom/android/systemui/statusbar/notification/row/ExpandableOutlineView;->mRoundableState:Lcom/android/systemui/statusbar/notification/RoundableState;

    .line 6
    .line 7
    invoke-virtual {v0}, Lcom/android/systemui/statusbar/notification/RoundableState;->debugString()Ljava/lang/String;

    .line 8
    .line 9
    .line 10
    move-result-object v0

    .line 11
    invoke-virtual {p0, v0}, Landroid/util/IndentingPrintWriter;->println(Ljava/lang/String;)V

    .line 12
    .line 13
    .line 14
    return-void
.end method