package androidx.picker.features.composable.left;

import androidx.picker.features.composable.ComposableFrame;
import androidx.picker.features.composable.ComposableViewHolder;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public enum LeftFrame implements ComposableFrame {
    Radio(R.layout.picker_app_composable_frame_radiobutton, ComposableRadioButtonViewHolder.class),
    CheckBox(R.layout.picker_app_composable_frame_checkbox, ComposableCheckBoxViewHolder.class);

    private final int layoutResId;
    private final Class<? extends ComposableViewHolder> viewHolderClass;

    LeftFrame(int i, Class cls) {
        this.layoutResId = i;
        this.viewHolderClass = cls;
    }

    @Override // androidx.picker.features.composable.ComposableFrame
    public final int getLayoutResId() {
        return this.layoutResId;
    }

    @Override // androidx.picker.features.composable.ComposableFrame
    public final Class getViewHolderClass() {
        return this.viewHolderClass;
    }
}