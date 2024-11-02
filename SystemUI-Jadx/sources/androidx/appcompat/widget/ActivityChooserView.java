package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class ActivityChooserView extends ViewGroup {
    public final View mActivityChooserContent;
    public final ActivityChooserViewAdapter mAdapter;
    public final Callbacks mCallbacks;
    public final FrameLayout mDefaultActivityButton;
    public final FrameLayout mExpandActivityOverflowButton;
    public final int mInitialActivityCount;
    public boolean mIsAttachedToWindow;
    public ListPopupWindow mListPopupWindow;
    public final AnonymousClass1 mModelDataSetObserver;
    public final AnonymousClass2 mOnGlobalLayoutListener;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ActivityChooserViewAdapter extends BaseAdapter {
        public ActivityChooserViewAdapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            throw null;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            throw null;
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            getItemViewType(i);
            if (view == null || view.getId() != R.id.list_item) {
                view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
            }
            ActivityChooserView.this.getContext().getPackageManager();
            getItem(i);
            throw null;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getViewTypeCount() {
            return 3;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Callbacks implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
        public Callbacks() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view != activityChooserView.mDefaultActivityButton) {
                if (view == activityChooserView.mExpandActivityOverflowButton) {
                    activityChooserView.mAdapter.getClass();
                    throw new IllegalStateException("No data model. Did you call #setDataModel?");
                }
                throw new IllegalArgumentException();
            }
            activityChooserView.dismissPopup();
            ActivityChooserView.this.mAdapter.getClass();
            throw null;
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public final void onDismiss() {
            ActivityChooserView.this.getClass();
            ActivityChooserView.this.getClass();
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
            ((ActivityChooserViewAdapter) adapterView.getAdapter()).getClass();
            ActivityChooserView.this.dismissPopup();
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            activityChooserView.getClass();
            activityChooserView.mAdapter.getClass();
            throw null;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view == activityChooserView.mDefaultActivityButton) {
                activityChooserView.mAdapter.getClass();
                throw null;
            }
            throw new IllegalArgumentException();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public static class InnerLayout extends LinearLayout {
        public static final int[] TINT_ATTRS = {android.R.attr.background};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, TINT_ATTRS);
            setBackgroundDrawable(obtainStyledAttributes.getDrawable(0));
            obtainStyledAttributes.recycle();
        }
    }

    public ActivityChooserView(Context context) {
        this(context, null);
    }

    public final void dismissPopup() {
        if (getListPopupWindow().isShowing()) {
            getListPopupWindow().dismiss();
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
            }
        }
    }

    public final ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
            this.mListPopupWindow = listPopupWindow;
            listPopupWindow.setAdapter(this.mAdapter);
            ListPopupWindow listPopupWindow2 = this.mListPopupWindow;
            listPopupWindow2.mDropDownAnchorView = this;
            listPopupWindow2.mModal = true;
            listPopupWindow2.mPopup.setFocusable(true);
            ListPopupWindow listPopupWindow3 = this.mListPopupWindow;
            Callbacks callbacks = this.mCallbacks;
            listPopupWindow3.mItemClickListener = callbacks;
            listPopupWindow3.mPopup.setOnDismissListener(callbacks);
        }
        return this.mListPopupWindow;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAdapter.getClass();
        this.mIsAttachedToWindow = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAdapter.getClass();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (getListPopupWindow().isShowing()) {
            dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mActivityChooserContent.layout(0, 0, i3 - i, i4 - i2);
        if (!getListPopupWindow().isShowing()) {
            dismissPopup();
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        View view = this.mActivityChooserContent;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        }
        measureChild(view, i, i2);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.appcompat.widget.ActivityChooserView$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.appcompat.widget.ActivityChooserView$2] */
    public ActivityChooserView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mModelDataSetObserver = new DataSetObserver() { // from class: androidx.appcompat.widget.ActivityChooserView.1
            @Override // android.database.DataSetObserver
            public final void onChanged() {
                super.onChanged();
                ActivityChooserView.this.mAdapter.notifyDataSetChanged();
            }

            @Override // android.database.DataSetObserver
            public final void onInvalidated() {
                super.onInvalidated();
                ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
            }
        };
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.ActivityChooserView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                if (ActivityChooserView.this.getListPopupWindow().isShowing()) {
                    if (!ActivityChooserView.this.isShown()) {
                        ActivityChooserView.this.getListPopupWindow().dismiss();
                    } else {
                        ActivityChooserView.this.getListPopupWindow().show();
                        ActivityChooserView.this.getClass();
                    }
                }
            }
        };
        this.mInitialActivityCount = 4;
        int[] iArr = R$styleable.ActivityChooserView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        this.mInitialActivityCount = obtainStyledAttributes.getInt(1, 4);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(getContext()).inflate(R.layout.abc_activity_chooser_view, (ViewGroup) this, true);
        Callbacks callbacks = new Callbacks();
        this.mCallbacks = callbacks;
        View findViewById = findViewById(R.id.activity_chooser_view_content);
        this.mActivityChooserContent = findViewById;
        findViewById.getBackground();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.default_activity_button);
        this.mDefaultActivityButton = frameLayout;
        frameLayout.setOnClickListener(callbacks);
        frameLayout.setOnLongClickListener(callbacks);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.expand_activities_button);
        frameLayout2.setOnClickListener(callbacks);
        frameLayout2.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: androidx.appcompat.widget.ActivityChooserView.3
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).mInfo.setCanOpenPopup(true);
            }
        });
        frameLayout2.setOnTouchListener(new ForwardingListener(frameLayout2) { // from class: androidx.appcompat.widget.ActivityChooserView.4
            @Override // androidx.appcompat.widget.ForwardingListener
            public final ShowableListMenu getPopup() {
                return ActivityChooserView.this.getListPopupWindow();
            }

            @Override // androidx.appcompat.widget.ForwardingListener
            public final boolean onForwardingStarted() {
                ActivityChooserView activityChooserView = ActivityChooserView.this;
                if (!activityChooserView.getListPopupWindow().isShowing() && activityChooserView.mIsAttachedToWindow) {
                    activityChooserView.mAdapter.getClass();
                    throw new IllegalStateException("No data model. Did you call #setDataModel?");
                }
                return true;
            }

            @Override // androidx.appcompat.widget.ForwardingListener
            public final void onForwardingStopped() {
                ActivityChooserView.this.dismissPopup();
            }
        });
        this.mExpandActivityOverflowButton = frameLayout2;
        ((ImageView) frameLayout2.findViewById(R.id.image)).setImageDrawable(drawable);
        ActivityChooserViewAdapter activityChooserViewAdapter = new ActivityChooserViewAdapter();
        this.mAdapter = activityChooserViewAdapter;
        activityChooserViewAdapter.registerDataSetObserver(new DataSetObserver() { // from class: androidx.appcompat.widget.ActivityChooserView.5
            @Override // android.database.DataSetObserver
            public final void onChanged() {
                super.onChanged();
                ActivityChooserView.this.mAdapter.getClass();
                throw null;
            }
        });
        Resources resources = context.getResources();
        Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.sesl_config_prefDialogWidth));
    }
}
