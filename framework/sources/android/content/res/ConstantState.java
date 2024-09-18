package android.content.res;

import android.content.res.Resources;

/* loaded from: classes.dex */
public abstract class ConstantState<T> {
    public abstract int getChangingConfigurations();

    /* renamed from: newInstance */
    public abstract T newInstance2();

    public T newInstance(Resources res) {
        return newInstance2();
    }

    /* renamed from: newInstance */
    public T newInstance2(Resources res, Resources.Theme theme) {
        return newInstance(res);
    }
}
