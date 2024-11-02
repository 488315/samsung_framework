package com.android.systemui.decor;

import android.content.res.Resources;
import android.os.FactoryTest;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.systemui.util.DeviceType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class CutoutDecorProviderFactory extends DecorProviderFactory {
    public final Display display;
    public final DisplayInfo displayInfo = new DisplayInfo();
    public boolean isCameraProtectionVisible;
    public final Resources res;
    public boolean shouldFillUDCDisplayCutout;

    public CutoutDecorProviderFactory(Resources resources, Display display) {
        this.res = resources;
        this.display = display;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        DisplayInfo displayInfo = this.displayInfo;
        Display display = this.display;
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        } else {
            Log.w("CutoutDecorProviderFactory", "display is null, can't update displayInfo");
        }
        int i = DeviceType.supportTablet;
        if (!FactoryTest.isFactoryBinary() && (DisplayCutout.getFillBuiltInDisplayCutout(this.res, displayInfo.uniqueId) || this.isCameraProtectionVisible || this.shouldFillUDCDisplayCutout)) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        if (!getHasProviders()) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        DisplayInfo displayInfo = this.displayInfo;
        DisplayCutout displayCutout = displayInfo.displayCutout;
        if (displayCutout != null) {
            Iterator it = ((ArrayList) FaceScanningProviderFactoryKt.getBoundBaseOnCurrentRotation(displayCutout)).iterator();
            while (it.hasNext()) {
                arrayList.add(new CutoutDecorProviderImpl(FaceScanningProviderFactoryKt.baseOnRotation0(((Number) it.next()).intValue(), displayInfo.rotation)));
            }
        } else if (this.shouldFillUDCDisplayCutout) {
            arrayList.add(new CutoutDecorProviderImpl(1));
        }
        return arrayList;
    }
}
