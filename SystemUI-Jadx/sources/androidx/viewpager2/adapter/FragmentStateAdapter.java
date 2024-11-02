package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.activity.ComponentActivity;
import androidx.collection.ArraySet;
import androidx.collection.LongSparseArray;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment$5$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentLifecycleCallbacksDispatcher;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStateManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class FragmentStateAdapter extends RecyclerView.Adapter implements StatefulAdapter {
    public final FragmentManager mFragmentManager;
    public FragmentMaxLifecycleEnforcer mFragmentMaxLifecycleEnforcer;
    public final LongSparseArray mFragments;
    public boolean mHasStaleFragments;
    public boolean mIsInGracePeriod;
    public final LongSparseArray mItemIdToViewHolder;
    public final Lifecycle mLifecycle;
    public final LongSparseArray mSavedStates;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
        private DataSetChangeObserver() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public abstract void onChanged();

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2, Object obj) {
            onChanged();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class FragmentMaxLifecycleEnforcer {
        public AnonymousClass2 mDataObserver;
        public LifecycleEventObserver mLifecycleObserver;
        public AnonymousClass1 mPageChangeCallback;
        public long mPrimaryItemId = -1;
        public ViewPager2 mViewPager;

        public FragmentMaxLifecycleEnforcer() {
        }

        public static ViewPager2 inferViewPager(RecyclerView recyclerView) {
            ViewParent parent = recyclerView.getParent();
            if (parent instanceof ViewPager2) {
                return (ViewPager2) parent;
            }
            throw new IllegalStateException("Expected ViewPager2 instance. Got: " + parent);
        }

        public final void updateFragmentMaxLifecycle(boolean z) {
            boolean z2;
            int i;
            Fragment fragment;
            boolean z3;
            FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
            if (fragmentStateAdapter.mFragmentManager.isStateSaved() || this.mViewPager.mScrollEventAdapter.mScrollState != 0) {
                return;
            }
            LongSparseArray longSparseArray = fragmentStateAdapter.mFragments;
            if (longSparseArray.size() == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 || fragmentStateAdapter.getItemCount() == 0 || (i = this.mViewPager.mCurrentItem) >= fragmentStateAdapter.getItemCount()) {
                return;
            }
            long j = i;
            if ((j != this.mPrimaryItemId || z) && (fragment = (Fragment) longSparseArray.get(j)) != null && fragment.isAdded()) {
                this.mPrimaryItemId = j;
                FragmentManager fragmentManager = fragmentStateAdapter.mFragmentManager;
                fragmentManager.getClass();
                BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
                Fragment fragment2 = null;
                for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                    long keyAt = longSparseArray.keyAt(i2);
                    Fragment fragment3 = (Fragment) longSparseArray.valueAt(i2);
                    if (fragment3.isAdded()) {
                        if (keyAt != this.mPrimaryItemId) {
                            backStackRecord.setMaxLifecycle(fragment3, Lifecycle.State.STARTED);
                        } else {
                            fragment2 = fragment3;
                        }
                        if (keyAt == this.mPrimaryItemId) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (fragment3.mMenuVisible != z3) {
                            fragment3.mMenuVisible = z3;
                            if (fragment3.mHasMenu && fragment3.isAdded() && !fragment3.isHidden()) {
                                fragment3.mHost.onSupportInvalidateOptionsMenu();
                            }
                        }
                    }
                }
                if (fragment2 != null) {
                    backStackRecord.setMaxLifecycle(fragment2, Lifecycle.State.RESUMED);
                }
                if (!backStackRecord.mOps.isEmpty()) {
                    backStackRecord.commitNow();
                }
            }
        }
    }

    public FragmentStateAdapter(FragmentActivity fragmentActivity) {
        this(fragmentActivity.getSupportFragmentManager(), ((ComponentActivity) fragmentActivity).mLifecycleRegistry);
    }

    public static void addViewToContainer(View view, FrameLayout frameLayout) {
        if (frameLayout.getChildCount() <= 1) {
            if (view.getParent() == frameLayout) {
                return;
            }
            if (frameLayout.getChildCount() > 0) {
                frameLayout.removeAllViews();
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            frameLayout.addView(view);
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    public final boolean containsItem(long j) {
        if (j >= 0 && j < getItemCount()) {
            return true;
        }
        return false;
    }

    public abstract Fragment createFragment();

    public final void gcFragments() {
        LongSparseArray longSparseArray;
        LongSparseArray longSparseArray2;
        boolean z;
        Fragment fragment;
        View view;
        if (this.mHasStaleFragments && !this.mFragmentManager.isStateSaved()) {
            ArraySet arraySet = new ArraySet();
            int i = 0;
            while (true) {
                longSparseArray = this.mFragments;
                int size = longSparseArray.size();
                longSparseArray2 = this.mItemIdToViewHolder;
                if (i >= size) {
                    break;
                }
                long keyAt = longSparseArray.keyAt(i);
                if (!containsItem(keyAt)) {
                    arraySet.add(Long.valueOf(keyAt));
                    longSparseArray2.remove(keyAt);
                }
                i++;
            }
            if (!this.mIsInGracePeriod) {
                this.mHasStaleFragments = false;
                for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                    long keyAt2 = longSparseArray.keyAt(i2);
                    boolean z2 = true;
                    if (longSparseArray2.indexOfKey(keyAt2) >= 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z && ((fragment = (Fragment) longSparseArray.get(keyAt2)) == null || (view = fragment.mView) == null || view.getParent() == null)) {
                        z2 = false;
                    }
                    if (!z2) {
                        arraySet.add(Long.valueOf(keyAt2));
                    }
                }
            }
            ArraySet.ElementIterator elementIterator = new ArraySet.ElementIterator();
            while (elementIterator.hasNext()) {
                removeFragment(((Long) elementIterator.next()).longValue());
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return i;
    }

    public final Long itemForViewHolder(int i) {
        Long l = null;
        int i2 = 0;
        while (true) {
            LongSparseArray longSparseArray = this.mItemIdToViewHolder;
            if (i2 < longSparseArray.size()) {
                if (((Integer) longSparseArray.valueAt(i2)).intValue() == i) {
                    if (l == null) {
                        l = Long.valueOf(longSparseArray.keyAt(i2));
                    } else {
                        throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
                    }
                }
                i2++;
            } else {
                return l;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Object, androidx.viewpager2.adapter.FragmentStateAdapter$FragmentMaxLifecycleEnforcer$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.recyclerview.widget.RecyclerView$AdapterDataObserver, androidx.viewpager2.adapter.FragmentStateAdapter$FragmentMaxLifecycleEnforcer$2] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(RecyclerView recyclerView) {
        boolean z;
        if (this.mFragmentMaxLifecycleEnforcer == null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            final FragmentMaxLifecycleEnforcer fragmentMaxLifecycleEnforcer = new FragmentMaxLifecycleEnforcer();
            this.mFragmentMaxLifecycleEnforcer = fragmentMaxLifecycleEnforcer;
            fragmentMaxLifecycleEnforcer.mViewPager = FragmentMaxLifecycleEnforcer.inferViewPager(recyclerView);
            ?? r1 = new ViewPager2.OnPageChangeCallback() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.FragmentMaxLifecycleEnforcer.1
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public final void onPageScrollStateChanged(int i) {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
                }

                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public final void onPageSelected(int i) {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
                }
            };
            fragmentMaxLifecycleEnforcer.mPageChangeCallback = r1;
            ((ArrayList) fragmentMaxLifecycleEnforcer.mViewPager.mExternalPageChangeCallbacks.mCallbacks).add(r1);
            ?? r12 = new DataSetChangeObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.FragmentMaxLifecycleEnforcer.2
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onChanged() {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(true);
                }
            };
            fragmentMaxLifecycleEnforcer.mDataObserver = r12;
            FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
            fragmentStateAdapter.registerAdapterDataObserver(r12);
            LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.FragmentMaxLifecycleEnforcer.3
                @Override // androidx.lifecycle.LifecycleEventObserver
                public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
                }
            };
            fragmentMaxLifecycleEnforcer.mLifecycleObserver = lifecycleEventObserver;
            fragmentStateAdapter.mLifecycle.addObserver(lifecycleEventObserver);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        Bundle bundle;
        final FragmentViewHolder fragmentViewHolder = (FragmentViewHolder) viewHolder;
        long j = fragmentViewHolder.mItemId;
        final FrameLayout frameLayout = (FrameLayout) fragmentViewHolder.itemView;
        int id = frameLayout.getId();
        Long itemForViewHolder = itemForViewHolder(id);
        LongSparseArray longSparseArray = this.mItemIdToViewHolder;
        if (itemForViewHolder != null && itemForViewHolder.longValue() != j) {
            removeFragment(itemForViewHolder.longValue());
            longSparseArray.remove(itemForViewHolder.longValue());
        }
        longSparseArray.put(j, Integer.valueOf(id));
        long j2 = i;
        LongSparseArray longSparseArray2 = this.mFragments;
        if (longSparseArray2.indexOfKey(j2) >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            Fragment createFragment = createFragment();
            Fragment.SavedState savedState = (Fragment.SavedState) this.mSavedStates.get(j2);
            if (createFragment.mFragmentManager == null) {
                if (savedState == null || (bundle = savedState.mState) == null) {
                    bundle = null;
                }
                createFragment.mSavedFragmentState = bundle;
                longSparseArray2.put(j2, createFragment);
            } else {
                throw new IllegalStateException("Fragment already added");
            }
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isAttachedToWindow(frameLayout)) {
            if (frameLayout.getParent() == null) {
                frameLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                        if (frameLayout.getParent() != null) {
                            frameLayout.removeOnLayoutChangeListener(this);
                            FragmentStateAdapter.this.placeFragmentInViewHolder(fragmentViewHolder);
                        }
                    }
                });
            } else {
                throw new IllegalStateException("Design assumption violated.");
            }
        }
        gcFragments();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return FragmentViewHolder.create(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        FragmentMaxLifecycleEnforcer fragmentMaxLifecycleEnforcer = this.mFragmentMaxLifecycleEnforcer;
        fragmentMaxLifecycleEnforcer.getClass();
        ViewPager2 inferViewPager = FragmentMaxLifecycleEnforcer.inferViewPager(recyclerView);
        ((ArrayList) inferViewPager.mExternalPageChangeCallbacks.mCallbacks).remove(fragmentMaxLifecycleEnforcer.mPageChangeCallback);
        FragmentMaxLifecycleEnforcer.AnonymousClass2 anonymousClass2 = fragmentMaxLifecycleEnforcer.mDataObserver;
        FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
        fragmentStateAdapter.mObservable.unregisterObserver(anonymousClass2);
        fragmentStateAdapter.mLifecycle.removeObserver(fragmentMaxLifecycleEnforcer.mLifecycleObserver);
        fragmentMaxLifecycleEnforcer.mViewPager = null;
        this.mFragmentMaxLifecycleEnforcer = null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final /* bridge */ /* synthetic */ boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        placeFragmentInViewHolder((FragmentViewHolder) viewHolder);
        gcFragments();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        Long itemForViewHolder = itemForViewHolder(((FrameLayout) ((FragmentViewHolder) viewHolder).itemView).getId());
        if (itemForViewHolder != null) {
            removeFragment(itemForViewHolder.longValue());
            this.mItemIdToViewHolder.remove(itemForViewHolder.longValue());
        }
    }

    public final void placeFragmentInViewHolder(final FragmentViewHolder fragmentViewHolder) {
        final Fragment fragment = (Fragment) this.mFragments.get(fragmentViewHolder.mItemId);
        if (fragment != null) {
            final FrameLayout frameLayout = (FrameLayout) fragmentViewHolder.itemView;
            View view = fragment.mView;
            if (!fragment.isAdded() && view != null) {
                throw new IllegalStateException("Design assumption violated.");
            }
            boolean isAdded = fragment.isAdded();
            FragmentManager fragmentManager = this.mFragmentManager;
            if (isAdded && view == null) {
                fragmentManager.mLifecycleCallbacksDispatcher.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksDispatcher.FragmentLifecycleCallbacksHolder(new FragmentManager.FragmentLifecycleCallbacks() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.3
                    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
                    public final void onFragmentViewCreated(FragmentManager fragmentManager2, Fragment fragment2, View view2) {
                        if (fragment2 == fragment) {
                            FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = fragmentManager2.mLifecycleCallbacksDispatcher;
                            synchronized (fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks) {
                                int size = fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks.size();
                                int i = 0;
                                while (true) {
                                    if (i >= size) {
                                        break;
                                    }
                                    if (((FragmentLifecycleCallbacksDispatcher.FragmentLifecycleCallbacksHolder) fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks.get(i)).mCallback == this) {
                                        fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks.remove(i);
                                        break;
                                    }
                                    i++;
                                }
                            }
                            FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
                            FrameLayout frameLayout2 = frameLayout;
                            fragmentStateAdapter.getClass();
                            FragmentStateAdapter.addViewToContainer(view2, frameLayout2);
                        }
                    }
                }, false));
                return;
            }
            if (fragment.isAdded() && view.getParent() != null) {
                if (view.getParent() != frameLayout) {
                    addViewToContainer(view, frameLayout);
                    return;
                }
                return;
            }
            if (fragment.isAdded()) {
                addViewToContainer(view, frameLayout);
                return;
            }
            if (!fragmentManager.isStateSaved()) {
                fragmentManager.mLifecycleCallbacksDispatcher.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksDispatcher.FragmentLifecycleCallbacksHolder(new FragmentManager.FragmentLifecycleCallbacks() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.3
                    @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
                    public final void onFragmentViewCreated(FragmentManager fragmentManager2, Fragment fragment2, View view2) {
                        if (fragment2 == fragment) {
                            FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = fragmentManager2.mLifecycleCallbacksDispatcher;
                            synchronized (fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks) {
                                int size = fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks.size();
                                int i = 0;
                                while (true) {
                                    if (i >= size) {
                                        break;
                                    }
                                    if (((FragmentLifecycleCallbacksDispatcher.FragmentLifecycleCallbacksHolder) fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks.get(i)).mCallback == this) {
                                        fragmentLifecycleCallbacksDispatcher.mLifecycleCallbacks.remove(i);
                                        break;
                                    }
                                    i++;
                                }
                            }
                            FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
                            FrameLayout frameLayout2 = frameLayout;
                            fragmentStateAdapter.getClass();
                            FragmentStateAdapter.addViewToContainer(view2, frameLayout2);
                        }
                    }
                }, false));
                fragmentManager.getClass();
                BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
                backStackRecord.doAddOp(0, fragment, "f" + fragmentViewHolder.mItemId, 1);
                backStackRecord.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
                backStackRecord.commitNow();
                this.mFragmentMaxLifecycleEnforcer.updateFragmentMaxLifecycle(false);
                return;
            }
            if (fragmentManager.mDestroyed) {
                return;
            }
            this.mLifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.2
                @Override // androidx.lifecycle.LifecycleEventObserver
                public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
                    if (fragmentStateAdapter.mFragmentManager.isStateSaved()) {
                        return;
                    }
                    lifecycleOwner.getLifecycle().removeObserver(this);
                    FragmentViewHolder fragmentViewHolder2 = fragmentViewHolder;
                    FrameLayout frameLayout2 = (FrameLayout) fragmentViewHolder2.itemView;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api19Impl.isAttachedToWindow(frameLayout2)) {
                        fragmentStateAdapter.placeFragmentInViewHolder(fragmentViewHolder2);
                    }
                }
            });
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    public final void removeFragment(long j) {
        Bundle saveBasicState;
        ViewParent parent;
        LongSparseArray longSparseArray = this.mFragments;
        Fragment fragment = (Fragment) longSparseArray.get(j);
        if (fragment == null) {
            return;
        }
        View view = fragment.mView;
        if (view != null && (parent = view.getParent()) != null) {
            ((FrameLayout) parent).removeAllViews();
        }
        boolean containsItem = containsItem(j);
        LongSparseArray longSparseArray2 = this.mSavedStates;
        if (!containsItem) {
            longSparseArray2.remove(j);
        }
        if (!fragment.isAdded()) {
            longSparseArray.remove(j);
            return;
        }
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager.isStateSaved()) {
            this.mHasStaleFragments = true;
            return;
        }
        if (fragment.isAdded() && containsItem(j)) {
            fragmentManager.getClass();
            FragmentStateManager fragmentStateManager = (FragmentStateManager) fragmentManager.mFragmentStore.mActive.get(fragment.mWho);
            Fragment.SavedState savedState = null;
            if (fragmentStateManager != null) {
                Fragment fragment2 = fragmentStateManager.mFragment;
                if (fragment2.equals(fragment)) {
                    if (fragment2.mState > -1 && (saveBasicState = fragmentStateManager.saveBasicState()) != null) {
                        savedState = new Fragment.SavedState(saveBasicState);
                    }
                    longSparseArray2.put(j, savedState);
                }
            }
            fragmentManager.throwException(new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m("Fragment ", fragment, " is not currently in the FragmentManager")));
            throw null;
        }
        fragmentManager.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
        backStackRecord.remove(fragment);
        backStackRecord.commitNow();
        longSparseArray.remove(j);
    }

    public final void restoreState(Parcelable parcelable) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        LongSparseArray longSparseArray = this.mSavedStates;
        boolean z5 = false;
        if (longSparseArray.size() == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            LongSparseArray longSparseArray2 = this.mFragments;
            if (longSparseArray2.size() == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                Bundle bundle = (Bundle) parcelable;
                if (bundle.getClassLoader() == null) {
                    bundle.setClassLoader(FragmentStateAdapter.class.getClassLoader());
                }
                for (String str : bundle.keySet()) {
                    if (str.startsWith("f#") && str.length() > 2) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        long parseLong = Long.parseLong(str.substring(2));
                        FragmentManager fragmentManager = this.mFragmentManager;
                        fragmentManager.getClass();
                        String string = bundle.getString(str);
                        Fragment fragment = null;
                        if (string != null) {
                            Fragment findActiveFragment = fragmentManager.findActiveFragment(string);
                            if (findActiveFragment != null) {
                                fragment = findActiveFragment;
                            } else {
                                fragmentManager.throwException(new IllegalStateException(FontProvider$$ExternalSyntheticOutline0.m("Fragment no longer exists for key ", str, ": unique id ", string)));
                                throw null;
                            }
                        }
                        longSparseArray2.put(parseLong, fragment);
                    } else {
                        if (str.startsWith("s#") && str.length() > 2) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        if (z4) {
                            long parseLong2 = Long.parseLong(str.substring(2));
                            Fragment.SavedState savedState = (Fragment.SavedState) bundle.getParcelable(str);
                            if (containsItem(parseLong2)) {
                                longSparseArray.put(parseLong2, savedState);
                            }
                        } else {
                            throw new IllegalArgumentException("Unexpected key in savedState: ".concat(str));
                        }
                    }
                }
                if (longSparseArray2.size() == 0) {
                    z5 = true;
                }
                if (!z5) {
                    this.mHasStaleFragments = true;
                    this.mIsInGracePeriod = true;
                    gcFragments();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    final Runnable runnable = new Runnable() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.4
                        @Override // java.lang.Runnable
                        public final void run() {
                            FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
                            fragmentStateAdapter.mIsInGracePeriod = false;
                            fragmentStateAdapter.gcFragments();
                        }
                    };
                    this.mLifecycle.addObserver(new LifecycleEventObserver(this) { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.5
                        @Override // androidx.lifecycle.LifecycleEventObserver
                        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                            if (event == Lifecycle.Event.ON_DESTROY) {
                                handler.removeCallbacks(runnable);
                                lifecycleOwner.getLifecycle().removeObserver(this);
                            }
                        }
                    });
                    handler.postDelayed(runnable, 10000L);
                    return;
                }
                return;
            }
        }
        throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
    }

    public FragmentStateAdapter(Fragment fragment) {
        this(fragment.getChildFragmentManager(), fragment.mLifecycleRegistry);
    }

    public FragmentStateAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        this.mFragments = new LongSparseArray();
        this.mSavedStates = new LongSparseArray();
        this.mItemIdToViewHolder = new LongSparseArray();
        this.mIsInGracePeriod = false;
        this.mHasStaleFragments = false;
        this.mFragmentManager = fragmentManager;
        this.mLifecycle = lifecycle;
        setHasStableIds(true);
    }
}
