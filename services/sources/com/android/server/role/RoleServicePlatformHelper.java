package com.android.server.role;

import android.annotation.SystemApi;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface RoleServicePlatformHelper {
    String computePackageStateHash(int i);

    Map getLegacyRoleState(int i);
}
