package com.android.systemui.statusbar.notification.collection.render;

import android.content.Context;
import android.view.View;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ShadeViewManager implements PipelineDumpable {
    public final RootNodeController rootController;
    public final NodeSpecBuilder specBuilder;
    public final NotifStackController stackController;
    public final NotifViewBarn viewBarn;
    public final ShadeViewDiffer viewDiffer;
    public final ShadeViewManager$viewRenderer$1 viewRenderer;

    public ShadeViewManager(Context context, NotificationListContainer notificationListContainer, NotifStackController notifStackController, MediaContainerController mediaContainerController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, NodeSpecBuilderLogger nodeSpecBuilderLogger, ShadeViewDifferLogger shadeViewDifferLogger, NotifViewBarn notifViewBarn) {
        this.stackController = notifStackController;
        this.viewBarn = notifViewBarn;
        RootNodeController rootNodeController = new RootNodeController(notificationListContainer, new View(context));
        this.rootController = rootNodeController;
        this.specBuilder = new NodeSpecBuilder(mediaContainerController, notificationSectionsFeatureManager, sectionHeaderVisibilityProvider, notifViewBarn, nodeSpecBuilderLogger);
        this.viewDiffer = new ShadeViewDiffer(rootNodeController, shadeViewDifferLogger);
        this.viewRenderer = new ShadeViewManager$viewRenderer$1(this);
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.rootController, "rootController");
        pipelineDumper.dump(this.specBuilder, "specBuilder");
        pipelineDumper.dump(this.viewDiffer, "viewDiffer");
    }
}
