package com.android.server.location;

import android.location.LocationManagerInternal;
import android.os.PackageTagsList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationManagerService$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocationManagerInternal.LocationPackageTagsListener f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ PackageTagsList f$2;

    public /* synthetic */ LocationManagerService$$ExternalSyntheticLambda12(LocationManagerInternal.LocationPackageTagsListener locationPackageTagsListener, int i, PackageTagsList packageTagsList, int i2) {
        this.$r8$classId = i2;
        this.f$0 = locationPackageTagsListener;
        this.f$1 = i;
        this.f$2 = packageTagsList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onLocationPackageTagsChanged(this.f$1, this.f$2);
                break;
            case 1:
                this.f$0.onLocationPackageTagsChanged(this.f$1, this.f$2);
                break;
            default:
                this.f$0.onLocationPackageTagsChanged(this.f$1, this.f$2);
                break;
        }
    }
}
