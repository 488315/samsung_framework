.class public final Lcom/android/systemui/mdm/MdmOverlayContainer$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/plugins/statusbar/StatusBarStateController$StateListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/mdm/MdmOverlayContainer;


# direct methods
.method public constructor <init>(Lcom/android/systemui/mdm/MdmOverlayContainer;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/mdm/MdmOverlayContainer$1;->this$0:Lcom/android/systemui/mdm/MdmOverlayContainer;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onStateChanged(I)V
    .locals 2

    .line 1
    iget-object p0, p0, Lcom/android/systemui/mdm/MdmOverlayContainer$1;->this$0:Lcom/android/systemui/mdm/MdmOverlayContainer;

    .line 2
    .line 3
    iget v0, p0, Lcom/android/systemui/mdm/MdmOverlayContainer;->mPreviousState:I

    .line 4
    .line 5
    const/4 v1, 0x2

    .line 6
    if-ne v0, v1, :cond_0

    .line 7
    .line 8
    const/4 v0, 0x1

    .line 9
    if-ne p1, v0, :cond_0

    .line 10
    .line 11
    invoke-virtual {p0}, Lcom/android/systemui/mdm/MdmOverlayContainer;->updateMdmPolicy()V

    .line 12
    .line 13
    .line 14
    :cond_0
    iput p1, p0, Lcom/android/systemui/mdm/MdmOverlayContainer;->mPreviousState:I

    .line 15
    .line 16
    return-void
.end method