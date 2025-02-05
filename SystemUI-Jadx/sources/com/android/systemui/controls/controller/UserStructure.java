package com.android.systemui.controls.controller;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import java.io.File;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class UserStructure {
    public final File auxiliaryFile;
    public final File file;
    public final Context userContext;

    public UserStructure(Context context, UserHandle userHandle, UserFileManager userFileManager) {
        this.userContext = context.createContextAsUser(userHandle, 0);
        UserFileManagerImpl userFileManagerImpl = (UserFileManagerImpl) userFileManager;
        this.file = userFileManagerImpl.getFile(userHandle.getIdentifier(), "controls_favorites.xml");
        this.auxiliaryFile = userFileManagerImpl.getFile(userHandle.getIdentifier(), "aux_controls_favorites.xml");
    }
}
