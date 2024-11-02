package com.android.systemui.controls.controller;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import com.android.systemui.backup.BackupHelper;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class AuxiliaryPersistenceWrapper {
    public List favorites;
    public final ControlsFavoritePersistenceWrapper persistenceWrapper;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AuxiliaryPersistenceWrapper(ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper) {
        this.persistenceWrapper = controlsFavoritePersistenceWrapper;
        this.favorites = EmptyList.INSTANCE;
        initialize();
    }

    public final List getCachedFavoritesAndRemoveFor(ComponentName componentName) {
        ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = this.persistenceWrapper;
        if (!controlsFavoritePersistenceWrapper.file.exists()) {
            return EmptyList.INSTANCE;
        }
        List list = this.favorites;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (Intrinsics.areEqual(((StructureInfo) obj).componentName, componentName)) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list2 = (List) pair.component1();
        List list3 = (List) pair.component2();
        this.favorites = list3;
        if (!list3.isEmpty()) {
            controlsFavoritePersistenceWrapper.storeFavorites(list3);
        } else {
            controlsFavoritePersistenceWrapper.file.delete();
        }
        return list2;
    }

    public final void initialize() {
        List list;
        ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = this.persistenceWrapper;
        if (controlsFavoritePersistenceWrapper.file.exists()) {
            list = controlsFavoritePersistenceWrapper.readFavorites();
        } else {
            list = EmptyList.INSTANCE;
        }
        this.favorites = list;
    }

    public AuxiliaryPersistenceWrapper(File file, Executor executor, SecureSettings secureSettings) {
        this(new ControlsFavoritePersistenceWrapper(file, executor, null, secureSettings, 4, null));
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class DeletionJobService extends JobService {
        public static final Companion Companion = new Companion(null);
        public static final int DELETE_FILE_JOB_ID = 1000;
        public static final String USER = "USER";
        public static final long WEEK_IN_MILLIS = TimeUnit.DAYS.toMillis(7);

        public final void attachContext(Context context) {
            attachBaseContext(context);
        }

        @Override // android.app.job.JobService
        public final boolean onStartJob(JobParameters jobParameters) {
            int i;
            PersistableBundle extras = jobParameters.getExtras();
            if (extras != null) {
                i = extras.getInt(USER, 0);
            } else {
                i = 0;
            }
            BackupHelper.Companion.getClass();
            synchronized (BackupHelper.controlsDataLock) {
                UserFileManagerImpl.Companion.getClass();
                getBaseContext().deleteFile(UserFileManagerImpl.Companion.createFile(i, "aux_controls_favorites.xml").getPath());
            }
            return false;
        }

        @Override // android.app.job.JobService
        public final boolean onStopJob(JobParameters jobParameters) {
            return true;
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public static /* synthetic */ void getDELETE_FILE_JOB_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
            }

            public static /* synthetic */ void getUSER$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
            }
        }
    }
}
