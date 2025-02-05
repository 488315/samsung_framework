package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.setupdesign.template.RecyclerMixin;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class SetupWizardPreferenceLayout extends SetupWizardRecyclerLayout {
    public SetupWizardPreferenceLayout(Context context) {
        super(context);
    }

    @Override // com.google.android.setupdesign.SetupWizardRecyclerLayout, com.google.android.setupdesign.SetupWizardLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    @Override // com.google.android.setupdesign.SetupWizardRecyclerLayout, com.google.android.setupdesign.SetupWizardLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = R.layout.sud_preference_template;
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    @Override // com.google.android.setupdesign.SetupWizardRecyclerLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final void onTemplateInflated() {
        this.recyclerMixin = new RecyclerMixin(this, (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.sud_preference_recycler_view, (ViewGroup) this, false));
    }

    public SetupWizardPreferenceLayout(Context context, int i, int i2) {
        super(context, i, i2);
    }

    public SetupWizardPreferenceLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SetupWizardPreferenceLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
