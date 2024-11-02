package com.android.systemui.privacy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.R$styleable;
import com.android.systemui.animation.LaunchableFrameLayout;
import com.android.systemui.statusbar.events.BackgroundAnimatableView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class OngoingPrivacyChip extends LaunchableFrameLayout implements BackgroundAnimatableView {
    public int firstIconStartMargin;
    public int iconColor;
    public int iconMargin;
    public final LinearLayout iconsContainer;
    public List privacyList;

    public OngoingPrivacyChip(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final int getChipWidth() {
        return getMeasuredWidth();
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final View getContentView() {
        return null;
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final void setBoundsForAnimation(int i, int i2, int i3, int i4) {
        this.iconsContainer.setLeftTopRightBottom(i - getLeft(), i2 - getTop(), i3 - getLeft(), i4 - getTop());
    }

    public final void setPrivacyList(List list) {
        this.privacyList = list;
        PrivacyChipBuilder privacyChipBuilder = new PrivacyChipBuilder(getContext(), this.privacyList);
        if (!this.privacyList.isEmpty()) {
            setContentDescription(getContext().getString(R.string.ongoing_privacy_chip_content_multiple_apps, privacyChipBuilder.joinTypes()));
            LinearLayout linearLayout = this.iconsContainer;
            linearLayout.removeAllViews();
            List list2 = privacyChipBuilder.types;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((PrivacyType) it.next()).getIcon(privacyChipBuilder.context));
            }
            boolean z = true;
            int i = 0;
            if (arrayList.size() <= 1) {
                z = false;
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                int i2 = i + 1;
                if (i >= 0) {
                    Drawable drawable = (Drawable) next;
                    drawable.mutate();
                    drawable.setTint(this.iconColor);
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(drawable);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    linearLayout.addView(imageView);
                    if (i == 0 && z) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                        marginLayoutParams.setMarginStart(this.firstIconStartMargin);
                        imageView.setLayoutParams(marginLayoutParams);
                    }
                    if (i != 0) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                        marginLayoutParams2.setMarginStart(this.iconMargin);
                        imageView.setLayoutParams(marginLayoutParams2);
                    }
                    i = i2;
                } else {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
            }
        } else {
            this.iconsContainer.removeAllViews();
        }
        requestLayout();
    }

    public final void updateResources() {
        this.iconColor = getContext().getColor(R.color.privacy_chip_icon_color);
        this.iconMargin = getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_appops_chip_icon_margin);
        this.firstIconStartMargin = getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_appops_chip_first_icon_start_margin);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.privacyList = EmptyList.INSTANCE;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.OngoingPrivacyChip, i, i2);
        try {
            int resourceId = obtainStyledAttributes.getResourceId(0, R.layout.ongoing_privacy_chip);
            obtainStyledAttributes.recycle();
            FrameLayout.inflate(context, resourceId, this);
            setId(R.id.privacy_chip);
            setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388629));
            setClipChildren(true);
            setClipToPadding(true);
            this.iconsContainer = (LinearLayout) requireViewById(R.id.icons_container);
            updateResources();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final View getView() {
        return this;
    }
}
