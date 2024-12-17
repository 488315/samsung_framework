package com.android.server.pm;

import android.content.pm.IPackageInstallObserver2;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiPackageVerifyingSession {
    public final List mChildVerifyingSessions;
    public final IPackageInstallObserver2 mObserver;
    public final Set mVerificationState;

    public MultiPackageVerifyingSession(VerifyingSession verifyingSession, List list) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.size() == 0) {
            throw new PackageManagerException("No child sessions found!", -21);
        }
        this.mChildVerifyingSessions = list;
        for (int i = 0; i < arrayList.size(); i++) {
            ((VerifyingSession) arrayList.get(i)).mParentVerifyingSession = this;
        }
        this.mVerificationState = new ArraySet(this.mChildVerifyingSessions.size());
        this.mObserver = verifyingSession.mObserver;
    }

    public final String toString() {
        return "MultiPackageVerifyingSession{" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
