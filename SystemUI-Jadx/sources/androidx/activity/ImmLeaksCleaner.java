package androidx.activity;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Field;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
final class ImmLeaksCleaner implements LifecycleEventObserver {
    public static Field sHField;
    public static Field sNextServedViewField;
    public static int sReflectedFieldsInitialized;
    public static Field sServedViewField;
    public final Activity mActivity;

    public ImmLeaksCleaner(Activity activity) {
        this.mActivity = activity;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event != Lifecycle.Event.ON_DESTROY) {
            return;
        }
        if (sReflectedFieldsInitialized == 0) {
            try {
                sReflectedFieldsInitialized = 2;
                Field declaredField = InputMethodManager.class.getDeclaredField("mServedView");
                sServedViewField = declaredField;
                declaredField.setAccessible(true);
                Field declaredField2 = InputMethodManager.class.getDeclaredField("mNextServedView");
                sNextServedViewField = declaredField2;
                declaredField2.setAccessible(true);
                Field declaredField3 = InputMethodManager.class.getDeclaredField("mH");
                sHField = declaredField3;
                declaredField3.setAccessible(true);
                sReflectedFieldsInitialized = 1;
            } catch (NoSuchFieldException unused) {
            }
        }
        if (sReflectedFieldsInitialized == 1) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.mActivity.getSystemService("input_method");
            try {
                Object obj = sHField.get(inputMethodManager);
                if (obj == null) {
                    return;
                }
                synchronized (obj) {
                    try {
                        try {
                            View view = (View) sServedViewField.get(inputMethodManager);
                            if (view == null) {
                                return;
                            }
                            if (view.isAttachedToWindow()) {
                                return;
                            }
                            try {
                                sNextServedViewField.set(inputMethodManager, null);
                                inputMethodManager.isActive();
                            } catch (IllegalAccessException unused2) {
                            }
                        } catch (ClassCastException unused3) {
                        } catch (IllegalAccessException unused4) {
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (IllegalAccessException unused5) {
            }
        }
    }
}
