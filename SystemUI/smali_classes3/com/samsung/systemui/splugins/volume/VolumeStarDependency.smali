.class public interface abstract Lcom/samsung/systemui/splugins/volume/VolumeStarDependency;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# virtual methods
.method public abstract getDefaultMiddlewares()Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/samsung/systemui/splugins/volume/VolumeMiddleware<",
            "**>;>;"
        }
    .end annotation
.end method

.method public abstract getDefaultReducer()Lcom/samsung/systemui/splugins/volume/VolumePanelReducerBase;
.end method

.method public abstract getInfraMediator()Lcom/samsung/systemui/splugins/volume/VolumeInfraMediator;
.end method

.method public abstract getVolumePanel()Lcom/samsung/systemui/splugins/volume/ExtendableVolumePanel;
.end method
