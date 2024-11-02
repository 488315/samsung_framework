package com.android.systemui.navigationbar.bandaid;

import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class Band {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Consumer afterAction;
    public final BandAid bandAidDependency;
    public final List moduleDependencies;
    public final Function patchAction;
    public final int priority;
    public final boolean runeDependency;
    public final String sPluginTag;
    public final int targetDisplayId;
    public final List targetEvents;
    public final List targetModules;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Builder {
        public Consumer afterAction;
        public BandAid bandAidDependency;
        public List moduleDependencies;
        public Function patchAction;
        public int priority;
        public String sPluginTag;
        public List targetEvents;
        public List targetModules;
        public boolean runeDependency = BasicRune.NAVBAR_ENABLED;
        public final int targetDisplayId = -1;

        public Builder() {
            EmptyList emptyList = EmptyList.INSTANCE;
            this.targetEvents = emptyList;
            this.targetModules = emptyList;
            this.moduleDependencies = emptyList;
            this.priority = 1;
            this.patchAction = new Function() { // from class: com.android.systemui.navigationbar.bandaid.Band$Builder$patchAction$1
                @Override // java.util.function.Function
                public final /* bridge */ /* synthetic */ Object apply(Object obj) {
                    return Unit.INSTANCE;
                }
            };
            this.sPluginTag = "";
        }

        public final Band build() {
            return new Band(this, null);
        }
    }

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
    public final class Kit {
        public final int displayId;
        public final EventTypeFactory.EventType event;
        public final NavBarStateManager manager;
        public final NavBarStateManager.States states;

        public Kit(EventTypeFactory.EventType eventType, NavBarStateManager navBarStateManager, NavBarStateManager.States states, int i) {
            this.event = eventType;
            this.manager = navBarStateManager;
            this.states = states;
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Kit)) {
                return false;
            }
            Kit kit = (Kit) obj;
            if (Intrinsics.areEqual(this.event, kit.event) && Intrinsics.areEqual(this.manager, kit.manager) && Intrinsics.areEqual(this.states, kit.states) && this.displayId == kit.displayId) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId) + ((this.states.hashCode() + ((this.manager.hashCode() + (this.event.hashCode() * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "Kit(event=" + this.event + ", manager=" + this.manager + ", states=" + this.states + ", displayId=" + this.displayId + ")";
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ Band(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public Band(BandAid bandAid, boolean z, int i, List<? extends Type> list, List<? extends Type> list2, List<? extends Type> list3, int i2, Function<Kit, Object> function, Consumer<Kit> consumer, String str) {
        this.bandAidDependency = bandAid;
        this.runeDependency = z;
        this.targetDisplayId = i;
        this.targetEvents = list;
        this.targetModules = list2;
        this.moduleDependencies = list3;
        this.priority = i2;
        this.patchAction = function;
        this.afterAction = consumer;
        this.sPluginTag = str;
    }

    private Band(Builder builder) {
        this(builder.bandAidDependency, builder.runeDependency, builder.targetDisplayId, builder.targetEvents, builder.targetModules, builder.moduleDependencies, builder.priority, builder.patchAction, builder.afterAction, builder.sPluginTag);
        boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
    }
}
