package com.android.systemui.controls.ui;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class SelectedItem {
    public static final Companion Companion = new Companion(null);
    public static final StructureItem EMPTY_SELECTION;
    public static final ComponentItem EMPTY_SELECTION_COMPONENT;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ComponentItem extends SelectedItem {
        public final CharSequence appName;
        public final ComponentInfo componentInfo;
        public final ComponentName componentName;
        public final boolean hasControls;
        public final CharSequence name;

        public ComponentItem(CharSequence charSequence, ComponentInfo componentInfo) {
            super(null);
            this.appName = charSequence;
            this.componentInfo = componentInfo;
            this.name = charSequence;
            ArrayList arrayList = new ArrayList();
            List list = componentInfo.structureInfos;
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (true ^ ((StructureInfo) next).controls.isEmpty()) {
                    arrayList.add(next);
                }
            }
            this.hasControls = (list.isEmpty() ^ true) && (arrayList.isEmpty() ^ true);
            this.componentName = this.componentInfo.componentName;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ComponentItem)) {
                return false;
            }
            ComponentItem componentItem = (ComponentItem) obj;
            if (Intrinsics.areEqual(this.appName, componentItem.appName) && Intrinsics.areEqual(this.componentInfo, componentItem.componentInfo)) {
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final boolean getHasControls() {
            return this.hasControls;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final CharSequence getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.componentInfo.hashCode() + (this.appName.hashCode() * 31);
        }

        public final String toString() {
            return "ComponentItem(appName=" + ((Object) this.appName) + ", componentInfo=" + this.componentInfo + ")";
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class PanelItem extends SelectedItem {
        public final CharSequence appName;
        public final ComponentName componentName;
        public final boolean hasControls;
        public final CharSequence name;

        public PanelItem(CharSequence charSequence, ComponentName componentName) {
            super(null);
            this.appName = charSequence;
            this.componentName = componentName;
            this.name = charSequence;
            this.hasControls = true;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PanelItem)) {
                return false;
            }
            PanelItem panelItem = (PanelItem) obj;
            if (Intrinsics.areEqual(this.appName, panelItem.appName) && Intrinsics.areEqual(this.componentName, panelItem.componentName)) {
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final boolean getHasControls() {
            return this.hasControls;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final CharSequence getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.componentName.hashCode() + (this.appName.hashCode() * 31);
        }

        public final String toString() {
            return "PanelItem(appName=" + ((Object) this.appName) + ", componentName=" + this.componentName + ")";
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class StructureItem extends SelectedItem {
        public final ComponentName componentName;
        public final boolean hasControls;
        public final CharSequence name;
        public final StructureInfo structure;

        public StructureItem(StructureInfo structureInfo) {
            super(null);
            this.structure = structureInfo;
            this.name = structureInfo.structure;
            this.hasControls = !structureInfo.controls.isEmpty();
            this.componentName = structureInfo.componentName;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof StructureItem) && Intrinsics.areEqual(this.structure, ((StructureItem) obj).structure)) {
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final boolean getHasControls() {
            return this.hasControls;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final CharSequence getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.structure.hashCode();
        }

        public final String toString() {
            return "StructureItem(structure=" + this.structure + ")";
        }
    }

    static {
        StructureInfo.Companion.getClass();
        EMPTY_SELECTION = new StructureItem(StructureInfo.EMPTY_STRUCTURE);
        ComponentInfo.Companion.getClass();
        EMPTY_SELECTION_COMPONENT = new ComponentItem("", ComponentInfo.EMPTY_COMPONENT_INFO);
    }

    private SelectedItem() {
    }

    public /* synthetic */ SelectedItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ComponentName getComponentName();

    public abstract boolean getHasControls();

    public abstract CharSequence getName();
}
