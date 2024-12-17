package com.android.server.backup.params;

import com.android.server.backup.utils.BackupEligibilityRules;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdbBackupParams extends AdbParams {
    public boolean allApps;
    public BackupEligibilityRules backupEligibilityRules;
    public boolean doCompress;
    public boolean doWidgets;
    public int extraFlag;
    public boolean includeApks;
    public boolean includeKeyValue;
    public boolean includeObbs;
    public boolean includeShared;
    public boolean includeSystem;
    public String[] packages;
    public String[] smartswitchBackupPath;
    public int transportFlags;
}
