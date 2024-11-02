package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class MenuWrapperICS extends BaseMenuWrapper implements Menu {
    public final SupportMenu mWrappedObject;

    public MenuWrapperICS(Context context, SupportMenu supportMenu) {
        super(context);
        if (supportMenu != null) {
            this.mWrappedObject = supportMenu;
            return;
        }
        throw new IllegalArgumentException("Wrapped Object can not be null.");
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return getMenuItemWrapper(((MenuBuilder) this.mWrappedObject).addInternal(0, 0, 0, charSequence));
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2;
        if (menuItemArr != null) {
            menuItemArr2 = new MenuItem[menuItemArr.length];
        } else {
            menuItemArr2 = null;
        }
        int addIntentOptions = ((MenuBuilder) this.mWrappedObject).addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = getMenuItemWrapper(menuItemArr2[i5]);
            }
        }
        return addIntentOptions;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return ((MenuBuilder) this.mWrappedObject).addSubMenu(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public final void clear() {
        SimpleArrayMap simpleArrayMap = this.mMenuItems;
        if (simpleArrayMap != null) {
            simpleArrayMap.clear();
        }
        ((MenuBuilder) this.mWrappedObject).clear();
    }

    @Override // android.view.Menu
    public final void close() {
        ((MenuBuilder) this.mWrappedObject).close(true);
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i) {
        return getMenuItemWrapper(((MenuBuilder) this.mWrappedObject).findItem(i));
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i) {
        return getMenuItemWrapper(((MenuBuilder) this.mWrappedObject).getItem(i));
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        return ((MenuBuilder) this.mWrappedObject).hasVisibleItems();
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return ((MenuBuilder) this.mWrappedObject).isShortcutKey(i, keyEvent);
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i, int i2) {
        return ((MenuBuilder) this.mWrappedObject).performIdentifierAction(i, i2);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return ((MenuBuilder) this.mWrappedObject).performShortcut(i, keyEvent, i2);
    }

    @Override // android.view.Menu
    public final void removeGroup(int i) {
        if (this.mMenuItems != null) {
            int i2 = 0;
            while (true) {
                SimpleArrayMap simpleArrayMap = this.mMenuItems;
                if (i2 >= simpleArrayMap.size) {
                    break;
                }
                if (((SupportMenuItem) simpleArrayMap.keyAt(i2)).getGroupId() == i) {
                    this.mMenuItems.removeAt(i2);
                    i2--;
                }
                i2++;
            }
        }
        ((MenuBuilder) this.mWrappedObject).removeGroup(i);
    }

    @Override // android.view.Menu
    public final void removeItem(int i) {
        if (this.mMenuItems != null) {
            int i2 = 0;
            while (true) {
                SimpleArrayMap simpleArrayMap = this.mMenuItems;
                if (i2 >= simpleArrayMap.size) {
                    break;
                }
                if (((SupportMenuItem) simpleArrayMap.keyAt(i2)).getItemId() == i) {
                    this.mMenuItems.removeAt(i2);
                    break;
                }
                i2++;
            }
        }
        ((MenuBuilder) this.mWrappedObject).removeItem(i);
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i, boolean z, boolean z2) {
        ((MenuBuilder) this.mWrappedObject).setGroupCheckable(i, z, z2);
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i, boolean z) {
        ((MenuBuilder) this.mWrappedObject).setGroupEnabled(i, z);
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i, boolean z) {
        ((MenuBuilder) this.mWrappedObject).setGroupVisible(i, z);
    }

    @Override // android.view.Menu
    public final void setQwertyMode(boolean z) {
        this.mWrappedObject.setQwertyMode(z);
    }

    @Override // android.view.Menu
    public final int size() {
        return ((MenuBuilder) this.mWrappedObject).size();
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i) {
        return ((MenuBuilder) this.mWrappedObject).addSubMenu(i);
    }

    @Override // android.view.Menu
    public final MenuItem add(int i) {
        return getMenuItemWrapper(((MenuBuilder) this.mWrappedObject).add(i));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return this.mWrappedObject.addSubMenu(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return getMenuItemWrapper(((MenuBuilder) this.mWrappedObject).addInternal(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return ((MenuBuilder) this.mWrappedObject).addSubMenu(i, i2, i3, i4);
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, int i4) {
        return getMenuItemWrapper(((MenuBuilder) this.mWrappedObject).add(i, i2, i3, i4));
    }
}
