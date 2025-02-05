package com.android.internal.widget.floatingtoolbar;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import com.android.internal.util.Preconditions;
import com.samsung.android.rune.ViewRune;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class FloatingToolbar {
    public static final String FLOATING_TOOLBAR_TAG = "floating_toolbar";
    private static final MenuItem.OnMenuItemClickListener NO_OP_MENUITEM_CLICK_LISTENER = new MenuItem.OnMenuItemClickListener() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar$$ExternalSyntheticLambda1
        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(MenuItem menuItem) {
            return FloatingToolbar.lambda$static$0(menuItem);
        }
    };
    private Menu mMenu;
    private int mOrientation;
    private final FloatingToolbarPopup mPopup;
    private final Window mWindow;
    private final Rect mContentRect = new Rect();
    private MenuItem.OnMenuItemClickListener mMenuItemClickListener = NO_OP_MENUITEM_CLICK_LISTENER;
    private final View.OnLayoutChangeListener mOrientationChangeHandler = new View.OnLayoutChangeListener() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar.1
        private final Rect mNewRect = new Rect();
        private final Rect mOldRect = new Rect();

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int newLeft, int newRight, int newTop, int newBottom, int oldLeft, int oldRight, int oldTop, int oldBottom) {
            int orientation = view.getContext().getResources().getConfiguration().orientation;
            if (FloatingToolbar.this.mOrientation != orientation) {
                FloatingToolbar.this.mPopup.setIsMovingStarted(false);
            }
            FloatingToolbar.this.mOrientation = orientation;
            this.mNewRect.set(newLeft, newRight, newTop, newBottom);
            this.mOldRect.set(oldLeft, oldRight, oldTop, oldBottom);
            if (!FloatingToolbar.this.mPopup.isDismissed() && !this.mNewRect.equals(this.mOldRect)) {
                FloatingToolbar.this.mPopup.setWidthChanged(true);
                FloatingToolbar.this.updateLayout();
            }
        }
    };
    private final Comparator<MenuItem> mMenuItemComparator = new Comparator() { // from class: com.android.internal.widget.floatingtoolbar.FloatingToolbar$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return FloatingToolbar.lambda$new$1((MenuItem) obj, (MenuItem) obj2);
        }
    };

    static /* synthetic */ boolean lambda$static$0(MenuItem item) {
        return false;
    }

    static /* synthetic */ int lambda$new$1(MenuItem menuItem, MenuItem menuItem2) {
        if (menuItem.getItemId() == 16908353) {
            return menuItem2.getItemId() == 16908353 ? 0 : -1;
        }
        if (menuItem2.getItemId() == 16908353) {
            return 1;
        }
        if (menuItem.requiresActionButton()) {
            return menuItem2.requiresActionButton() ? 0 : -1;
        }
        if (menuItem2.requiresActionButton()) {
            return 1;
        }
        if (menuItem.requiresOverflow()) {
            return !menuItem2.requiresOverflow() ? 1 : 0;
        }
        if (menuItem2.requiresOverflow()) {
            return -1;
        }
        return menuItem.getOrder() - menuItem2.getOrder();
    }

    public FloatingToolbar(Window window) {
        this.mWindow = (Window) Objects.requireNonNull(window);
        this.mPopup = FloatingToolbarPopup.createInstance(window.getContext(), window.getDecorView(), false);
    }

    public FloatingToolbar(Window window, boolean isSemTypeFloating) {
        this.mWindow = (Window) Preconditions.checkNotNull(window);
        this.mPopup = FloatingToolbarPopup.createInstance(window.getContext(), window.getDecorView(), isSemTypeFloating);
    }

    public FloatingToolbar setMenu(Menu menu) {
        this.mMenu = (Menu) Objects.requireNonNull(menu);
        return this;
    }

    public FloatingToolbar setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener) {
        if (menuItemClickListener != null) {
            this.mMenuItemClickListener = menuItemClickListener;
        } else {
            this.mMenuItemClickListener = NO_OP_MENUITEM_CLICK_LISTENER;
        }
        return this;
    }

    public FloatingToolbar setContentRect(Rect rect) {
        this.mContentRect.set((Rect) Objects.requireNonNull(rect));
        return this;
    }

    public FloatingToolbar setSuggestedWidth(int suggestedWidth) {
        this.mPopup.setSuggestedWidth(suggestedWidth);
        return this;
    }

    public FloatingToolbar show() {
        registerOrientationHandler();
        doShow();
        return this;
    }

    public FloatingToolbar updateLayout() {
        if (this.mPopup.isShowing()) {
            doShow();
        }
        return this;
    }

    public void dismiss() {
        unregisterOrientationHandler();
        this.mPopup.dismiss();
    }

    public void hide() {
        this.mPopup.hide();
    }

    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isHidden() {
        return this.mPopup.isHidden();
    }

    public boolean isMovingStarted() {
        return this.mPopup.isMovingStarted();
    }

    public void setIsMovingStarted(boolean isMovingStarted) {
        this.mPopup.setIsMovingStarted(isMovingStarted);
    }

    public Point getMovedPos() {
        return this.mPopup.getMovedPos();
    }

    public boolean isDiscardTouch() {
        return this.mPopup.isDiscardTouch();
    }

    public void setOutsideTouchable(boolean outsideTouchable, PopupWindow.OnDismissListener onDismiss) {
        this.mPopup.setOutsideTouchable(outsideTouchable, onDismiss);
    }

    private void doShow() {
        List<MenuItem> menuItems = getVisibleAndEnabledMenuItems(this.mMenu);
        if (!ViewRune.SUPPORT_WRITING_TOOLKIT) {
            tidy(menuItems);
        }
        this.mPopup.show(menuItems, this.mMenuItemClickListener, this.mContentRect);
    }

    private static List<MenuItem> getVisibleAndEnabledMenuItems(Menu menu) {
        List<MenuItem> menuItems = new ArrayList<>();
        for (int i = 0; menu != null && i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isVisible() && menuItem.isEnabled()) {
                Menu subMenu = menuItem.getSubMenu();
                if (subMenu != null) {
                    menuItems.addAll(getVisibleAndEnabledMenuItems(subMenu));
                } else {
                    menuItems.add(menuItem);
                }
            }
        }
        return menuItems;
    }

    private void registerOrientationHandler() {
        unregisterOrientationHandler();
        this.mWindow.getDecorView().addOnLayoutChangeListener(this.mOrientationChangeHandler);
    }

    private void unregisterOrientationHandler() {
        this.mWindow.getDecorView().removeOnLayoutChangeListener(this.mOrientationChangeHandler);
    }

    private void tidy(List<MenuItem> menuItems) {
        int assistItemIndex = -1;
        Drawable assistItemDrawable = null;
        int size = menuItems.size();
        for (int i = 0; i < size; i++) {
            MenuItem menuItem = menuItems.get(i);
            if (menuItem.getItemId() == 16908353) {
                assistItemIndex = i;
                assistItemDrawable = menuItem.getIcon();
            }
            if (!TextUtils.isEmpty(menuItem.getTitle())) {
                menuItem.setIcon((Drawable) null);
            }
        }
        if (assistItemIndex > -1) {
            MenuItem assistMenuItem = menuItems.remove(assistItemIndex);
            assistMenuItem.setIcon(assistItemDrawable);
            menuItems.add(0, assistMenuItem);
        }
    }
}
