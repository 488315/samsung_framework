package androidx.picker.helper;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.picker.common.log.LogTag;
import androidx.reflect.content.SeslContextReflector;
import androidx.reflect.os.SeslUserHandleReflector;
import java.util.HashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class PackageManagerHelperImpl implements PackageManagerHelper, LogTag {
    public final Context context;
    public final String logTag = "PackageManagerHelperImpl";
    public final HashMap pmList = new HashMap();

    public PackageManagerHelperImpl(Context context) {
        this.context = context;
    }

    @Override // androidx.picker.common.log.LogTag
    public final String getLogTag() {
        return this.logTag;
    }

    public final PackageManager getPackageManager(int i, String str) {
        PackageManager packageManager;
        Context context = this.context;
        HashMap hashMap = this.pmList;
        Integer valueOf = Integer.valueOf(i);
        Object obj = hashMap.get(valueOf);
        if (obj == null) {
            try {
                packageManager = SeslContextReflector.createPackageContextAsUser(context, str, SeslUserHandleReflector.of(i)).getPackageManager();
            } catch (IllegalAccessError unused) {
                packageManager = context.getPackageManager();
            } catch (InstantiationError unused2) {
                packageManager = context.getPackageManager();
            } catch (NoSuchMethodError unused3) {
                packageManager = context.getPackageManager();
            } catch (RuntimeException unused4) {
                packageManager = context.getPackageManager();
            }
            obj = packageManager;
            hashMap.put(valueOf, obj);
        }
        return (PackageManager) obj;
    }
}
