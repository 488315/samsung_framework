.class public final Lcom/android/systemui/volume/store/StoreInteractor$sendAction$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $action:Lcom/samsung/systemui/splugins/volume/VolumePanelAction;

.field public final synthetic $store:Lcom/android/systemui/volume/store/VolumePanelStore;


# direct methods
.method public constructor <init>(Lcom/android/systemui/volume/store/VolumePanelStore;Lcom/samsung/systemui/splugins/volume/VolumePanelAction;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/volume/store/StoreInteractor$sendAction$1;->$store:Lcom/android/systemui/volume/store/VolumePanelStore;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/volume/store/StoreInteractor$sendAction$1;->$action:Lcom/samsung/systemui/splugins/volume/VolumePanelAction;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/volume/store/StoreInteractor$sendAction$1;->$store:Lcom/android/systemui/volume/store/VolumePanelStore;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/volume/store/StoreInteractor$sendAction$1;->$action:Lcom/samsung/systemui/splugins/volume/VolumePanelAction;

    .line 4
    .line 5
    invoke-virtual {v0, p0}, Lcom/android/systemui/volume/store/VolumePanelStore;->onChanged(Lcom/samsung/systemui/splugins/volume/VolumePanelAction;)V

    .line 6
    .line 7
    .line 8
    return-void
.end method