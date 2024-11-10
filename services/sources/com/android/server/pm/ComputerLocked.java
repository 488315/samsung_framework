package com.android.server.pm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import com.android.server.pm.PackageManagerService;

/* loaded from: classes3.dex */
public final class ComputerLocked extends ComputerEngine {
    public ComputerLocked(PackageManagerService.Snapshot snapshot) {
        super(snapshot, -1);
    }

    @Override // com.android.server.pm.ComputerEngine
    public ComponentName resolveComponentName() {
        return this.mService.getResolveComponentName();
    }

    @Override // com.android.server.pm.ComputerEngine
    public ActivityInfo instantAppInstallerActivity() {
        return this.mService.mInstantAppInstallerActivity;
    }

    @Override // com.android.server.pm.ComputerEngine
    public ApplicationInfo androidApplication() {
        return this.mService.getCoreAndroidApplication();
    }
}
