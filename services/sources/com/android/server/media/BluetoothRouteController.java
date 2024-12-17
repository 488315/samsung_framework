package com.android.server.media;

import android.media.MediaRoute2Info;
import android.os.UserHandle;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface BluetoothRouteController {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface BluetoothRoutesUpdatedListener {
        void onBluetoothRoutesUpdated();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NoOpBluetoothRouteController implements BluetoothRouteController {
        @Override // com.android.server.media.BluetoothRouteController
        public final List getAllBluetoothRoutes() {
            return Collections.emptyList();
        }

        @Override // com.android.server.media.BluetoothRouteController
        public final MediaRoute2Info getSelectedRoute() {
            return null;
        }

        @Override // com.android.server.media.BluetoothRouteController
        public final List getTransferableRoutes() {
            return Collections.emptyList();
        }

        @Override // com.android.server.media.BluetoothRouteController
        public final void start(UserHandle userHandle) {
        }

        @Override // com.android.server.media.BluetoothRouteController
        public final void stop() {
        }

        @Override // com.android.server.media.BluetoothRouteController
        public final void transferTo(String str) {
        }

        @Override // com.android.server.media.BluetoothRouteController
        public final boolean updateVolumeForDevices(int i, int i2) {
            return false;
        }
    }

    List getAllBluetoothRoutes();

    MediaRoute2Info getSelectedRoute();

    List getTransferableRoutes();

    void start(UserHandle userHandle);

    void stop();

    void transferTo(String str);

    boolean updateVolumeForDevices(int i, int i2);
}
