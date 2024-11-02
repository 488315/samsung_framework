package com.android.systemui.qs.customize;

import android.content.res.Resources;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public enum CustomActionId {
    /* JADX INFO: Fake field, exist only in values array */
    NONE { // from class: com.android.systemui.qs.customize.CustomActionId.NONE
        @Override // com.android.systemui.qs.customize.CustomActionId
        public final int getId() {
            return -1;
        }

        @Override // com.android.systemui.qs.customize.CustomActionId
        public final String getName(Resources resources) {
            return null;
        }
    },
    MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE
        @Override // com.android.systemui.qs.customize.CustomActionId
        public final int getId() {
            return R.id.custom_action_move_item_from_available_to_active;
        }

        @Override // com.android.systemui.qs.customize.CustomActionId
        public final String getName(Resources resources) {
            return resources.getString(R.string.qs_custom_action_move_button);
        }
    },
    MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE
        @Override // com.android.systemui.qs.customize.CustomActionId
        public final int getId() {
            return R.id.custom_action_move_item_from_active_to_available;
        }

        @Override // com.android.systemui.qs.customize.CustomActionId
        public final String getName(Resources resources) {
            return resources.getString(R.string.qs_custom_action_move_button);
        }
    };

    /* synthetic */ CustomActionId(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract int getId();

    public abstract String getName(Resources resources);
}
