package androidx.fragment.app;

import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class FragmentFactory {
    public static final SimpleArrayMap sClassCacheMap = new SimpleArrayMap();

    public static Class loadClass(ClassLoader classLoader, String str) {
        SimpleArrayMap simpleArrayMap = sClassCacheMap;
        SimpleArrayMap simpleArrayMap2 = (SimpleArrayMap) simpleArrayMap.get(classLoader);
        if (simpleArrayMap2 == null) {
            simpleArrayMap2 = new SimpleArrayMap();
            simpleArrayMap.put(classLoader, simpleArrayMap2);
        }
        Class cls = (Class) simpleArrayMap2.get(str);
        if (cls == null) {
            Class<?> cls2 = Class.forName(str, false, classLoader);
            simpleArrayMap2.put(str, cls2);
            return cls2;
        }
        return cls;
    }

    public static Class loadFragmentClass(ClassLoader classLoader, String str) {
        try {
            return loadClass(classLoader, str);
        } catch (ClassCastException e) {
            throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": make sure class is a valid subclass of Fragment"), e);
        } catch (ClassNotFoundException e2) {
            throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": make sure class name exists"), e2);
        }
    }

    public Fragment instantiate(ClassLoader classLoader, String str) {
        try {
            return (Fragment) loadFragmentClass(classLoader, str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (IllegalAccessException e) {
            throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e);
        } catch (InstantiationException e2) {
            throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e2);
        } catch (NoSuchMethodException e3) {
            throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": could not find Fragment constructor"), e3);
        } catch (InvocationTargetException e4) {
            throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception"), e4);
        }
    }
}
