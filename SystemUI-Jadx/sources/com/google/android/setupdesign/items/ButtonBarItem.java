package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.google.android.setupdesign.items.ItemInflater;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class ButtonBarItem extends AbstractItem implements ItemInflater.ItemParent {
    public final ArrayList buttons;
    public final boolean visible;

    public ButtonBarItem() {
        this.buttons = new ArrayList();
        this.visible = true;
    }

    @Override // com.google.android.setupdesign.items.ItemInflater.ItemParent
    public final void addChild(ItemHierarchy itemHierarchy) {
        if (itemHierarchy instanceof ButtonItem) {
            this.buttons.add((ButtonItem) itemHierarchy);
            return;
        }
        throw new UnsupportedOperationException("Cannot add non-button item to Button Bar");
    }

    @Override // com.google.android.setupdesign.items.AbstractItem, com.google.android.setupdesign.items.ItemHierarchy
    public final int getCount() {
        return this.visible ? 1 : 0;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public final int getLayoutResource() {
        return R.layout.sud_items_button_bar;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public final boolean isEnabled() {
        return false;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public final void onBindView(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        linearLayout.removeAllViews();
        Iterator it = this.buttons.iterator();
        while (it.hasNext()) {
            ButtonItem buttonItem = (ButtonItem) it.next();
            Button button = buttonItem.button;
            if (button == null) {
                Context context = linearLayout.getContext();
                if (buttonItem.theme != 0) {
                    context = new ContextThemeWrapper(context, buttonItem.theme);
                }
                Button button2 = (Button) LayoutInflater.from(context).inflate(R.layout.sud_button, (ViewGroup) null, false);
                buttonItem.button = button2;
                button2.setOnClickListener(buttonItem);
            } else if (button.getParent() instanceof ViewGroup) {
                ((ViewGroup) buttonItem.button.getParent()).removeView(buttonItem.button);
            }
            buttonItem.button.setEnabled(buttonItem.enabled);
            buttonItem.button.setText(buttonItem.text);
            buttonItem.button.setId(buttonItem.id);
            linearLayout.addView(buttonItem.button);
        }
        view.setId(this.id);
    }

    public ButtonBarItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.buttons = new ArrayList();
        this.visible = true;
    }
}
