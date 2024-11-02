package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class PanelInteractorImpl implements PanelInteractor {
    public final Optional centralSurfaces;

    public PanelInteractorImpl(Optional<CentralSurfaces> optional) {
        this.centralSurfaces = optional;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor
    public final void collapsePanels() {
        this.centralSurfaces.ifPresent(new Consumer() { // from class: com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl$collapsePanels$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).postAnimateCollapsePanels();
            }
        });
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor
    public final void forceCollapsePanels() {
        this.centralSurfaces.ifPresent(new Consumer() { // from class: com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl$forceCollapsePanels$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).postAnimateForceCollapsePanels();
            }
        });
    }
}
