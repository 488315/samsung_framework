package androidx.slice.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceActionImpl;
import com.android.systemui.R;
import java.util.Set;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ShortcutView extends SliceChildView {
    public SliceItem mActionItem;
    public IconCompat mIcon;
    public final int mLargeIconSize;
    public ListContent mListContent;
    public Set mLoadingActions;
    public final int mSmallIconSize;

    public ShortcutView(Context context) {
        super(context);
        Resources resources = getResources();
        this.mSmallIconSize = resources.getDimensionPixelSize(R.dimen.abc_slice_icon_size);
        this.mLargeIconSize = resources.getDimensionPixelSize(R.dimen.abc_slice_shortcut_size);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final Set getLoadingActions() {
        return this.mLoadingActions;
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (this.mListContent == null) {
            return false;
        }
        if (!callOnClick()) {
            try {
                SliceItem sliceItem = this.mActionItem;
                if (sliceItem != null) {
                    sliceItem.fireActionInternal(null, null);
                    if (this.mObserver != null) {
                        EventInfo eventInfo = new EventInfo(3, 1, -1, 0);
                        if (this.mActionItem == null) {
                            SliceItem sliceItem2 = this.mListContent.mSliceItem;
                        }
                        this.mObserver.onSliceAction(eventInfo);
                    }
                }
            } catch (PendingIntent.CanceledException e) {
                Log.e("ShortcutView", "PendingIntent for slice cannot be sent", e);
            }
        }
        return true;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void resetView() {
        this.mListContent = null;
        this.mActionItem = null;
        this.mIcon = null;
        setBackground(null);
        removeAllViews();
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setLoadingActions(Set set) {
        this.mLoadingActions = set;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceContent(ListContent listContent) {
        boolean z;
        int i;
        int i2;
        ImageView.ScaleType scaleType;
        resetView();
        this.mListContent = listContent;
        if (listContent == null) {
            return;
        }
        SliceActionImpl sliceActionImpl = (SliceActionImpl) listContent.getShortcut(getContext());
        this.mActionItem = sliceActionImpl.mActionItem;
        this.mIcon = sliceActionImpl.mIcon;
        if (sliceActionImpl.mImageMode == 0) {
            z = true;
        } else {
            z = false;
        }
        SliceItem sliceItem = this.mListContent.mColorItem;
        if (sliceItem != null) {
            i = sliceItem.getInt();
        } else {
            i = -1;
        }
        if (i == -1) {
            i = SliceViewUtil.getColorAttr(android.R.attr.colorAccent, getContext());
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setTint(i);
        ImageView imageView = new ImageView(getContext());
        if (this.mIcon != null && z) {
            imageView.setBackground(shapeDrawable);
        }
        addView(imageView);
        if (this.mIcon != null) {
            if (z) {
                i2 = this.mSmallIconSize;
            } else {
                i2 = this.mLargeIconSize;
            }
            Context context = getContext();
            IconCompat iconCompat = this.mIcon;
            boolean z2 = !z;
            ImageView imageView2 = new ImageView(context);
            imageView2.setImageDrawable(iconCompat.loadDrawable(context));
            if (z2) {
                scaleType = ImageView.ScaleType.CENTER_CROP;
            } else {
                scaleType = ImageView.ScaleType.CENTER_INSIDE;
            }
            imageView2.setScaleType(scaleType);
            addView(imageView2);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView2.getLayoutParams();
            if (z2) {
                Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                imageView2.layout(0, 0, i2, i2);
                imageView2.draw(canvas);
                imageView2.setImageBitmap(SliceViewUtil.getCircularBitmap(createBitmap));
            } else {
                imageView2.setColorFilter(-1);
            }
            layoutParams.width = i2;
            layoutParams.height = i2;
            layoutParams.gravity = 17;
            setClickable(true);
        } else {
            setClickable(false);
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams2.gravity = 17;
        setLayoutParams(layoutParams2);
    }
}
