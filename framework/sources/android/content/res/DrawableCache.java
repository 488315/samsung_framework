package android.content.res;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/* loaded from: classes.dex */
class DrawableCache extends ThemedResourceCache<Drawable.ConstantState> {
    DrawableCache() {
    }

    public Drawable getInstance(long key, Resources resources, Resources.Theme theme) {
        Drawable.ConstantState entry = get(key, theme);
        if (entry != null) {
            return entry.newDrawable(resources, theme);
        }
        return null;
    }

    @Override // android.content.res.ThemedResourceCache
    public boolean shouldInvalidateEntry(Drawable.ConstantState entry, int configChanges) {
        return Configuration.needNewResources(configChanges, entry.getChangingConfigurations());
    }
}
