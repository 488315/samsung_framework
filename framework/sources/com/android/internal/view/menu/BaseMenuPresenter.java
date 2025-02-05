package com.android.internal.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public abstract class BaseMenuPresenter implements MenuPresenter {
    private MenuPresenter.Callback mCallback;
    protected Context mContext;
    private int mId;
    protected LayoutInflater mInflater;
    private int mItemLayoutRes;
    protected MenuBuilder mMenu;
    private int mMenuLayoutRes;
    protected MenuView mMenuView;
    protected Context mSystemContext;
    protected LayoutInflater mSystemInflater;

    public abstract void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView);

    public BaseMenuPresenter(Context context, int menuLayoutRes, int itemLayoutRes) {
        this.mSystemContext = context;
        this.mSystemInflater = LayoutInflater.from(context);
        this.mMenuLayoutRes = menuLayoutRes;
        this.mItemLayoutRes = itemLayoutRes;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menu) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mMenu = menu;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup root) {
        if (this.mMenuView == null) {
            this.mMenuView = (MenuView) this.mSystemInflater.inflate(this.mMenuLayoutRes, root, false);
            this.mMenuView.initialize(this.mMenu);
            updateMenuView(true);
        }
        return this.mMenuView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean cleared) {
        ViewGroup parent = (ViewGroup) this.mMenuView;
        if (parent == null) {
            return;
        }
        int childIndex = 0;
        if (this.mMenu != null) {
            this.mMenu.flagActionItems();
            ArrayList<MenuItemImpl> visibleItems = this.mMenu.getVisibleItems();
            int itemCount = visibleItems.size();
            for (int i = 0; i < itemCount; i++) {
                MenuItemImpl item = visibleItems.get(i);
                if (shouldIncludeItem(childIndex, item)) {
                    View childAt = parent.getChildAt(childIndex);
                    MenuItemImpl oldItem = childAt instanceof MenuView.ItemView ? ((MenuView.ItemView) childAt).getItemData() : null;
                    View itemView = getItemView(item, childAt, parent);
                    if (item != oldItem) {
                        itemView.setPressed(false);
                        itemView.jumpDrawablesToCurrentState();
                    }
                    if (itemView != childAt) {
                        addItemView(itemView, childIndex);
                    }
                    childIndex++;
                }
            }
        }
        while (childIndex < parent.getChildCount()) {
            if (!filterLeftoverView(parent, childIndex)) {
                childIndex++;
            }
        }
    }

    protected void addItemView(View itemView, int childIndex) {
        ViewGroup currentParent = (ViewGroup) itemView.getParent();
        if (currentParent != null) {
            currentParent.removeView(itemView);
        }
        ((ViewGroup) this.mMenuView).addView(itemView, childIndex);
    }

    protected boolean filterLeftoverView(ViewGroup parent, int childIndex) {
        parent.removeViewAt(childIndex);
        return true;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback cb) {
        this.mCallback = cb;
    }

    public MenuPresenter.Callback getCallback() {
        return this.mCallback;
    }

    public MenuView.ItemView createItemView(ViewGroup parent) {
        return (MenuView.ItemView) this.mSystemInflater.inflate(this.mItemLayoutRes, parent, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View getItemView(MenuItemImpl item, View view, ViewGroup parent) {
        MenuView.ItemView itemView;
        if (view instanceof MenuView.ItemView) {
            itemView = (MenuView.ItemView) view;
        } else {
            itemView = createItemView(parent);
        }
        bindItemView(item, itemView);
        return (View) itemView;
    }

    public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
        return true;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menu, allMenusAreClosing);
        }
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder menu) {
        if (this.mCallback != null) {
            return this.mCallback.onOpenSubMenu(menu);
        }
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    protected void setMenuLayoutResources(int menuLayoutRes, int itemLayoutRes) {
        this.mMenuLayoutRes = menuLayoutRes;
        this.mItemLayoutRes = itemLayoutRes;
    }
}
