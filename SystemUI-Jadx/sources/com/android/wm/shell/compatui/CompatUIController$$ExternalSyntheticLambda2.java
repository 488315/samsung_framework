package com.android.wm.shell.compatui;

import java.util.function.Predicate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((CompatUIWindowManagerAbstract) obj).mDisplayId == 0) {
                    return false;
                }
                return true;
            case 1:
                return true;
            default:
                if (((CompatUIWindowManagerAbstract) obj).mDisplayId != 0) {
                    return false;
                }
                return true;
        }
    }
}
