package android.telephony.satellite.stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.satellite.stub.ISatelliteGateway;
import com.android.telephony.Rlog;

/* loaded from: classes3.dex */
public abstract class SatelliteGatewayService extends Service {
    public static final String SERVICE_INTERFACE = "android.telephony.satellite.SatelliteGatewayService";
    private static final String TAG = "SatelliteGatewayService";
    private final IBinder mBinder = new ISatelliteGateway.Stub() { // from class: android.telephony.satellite.stub.SatelliteGatewayService.1
        AnonymousClass1() {
        }
    };

    /* renamed from: android.telephony.satellite.stub.SatelliteGatewayService$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 extends ISatelliteGateway.Stub {
        AnonymousClass1() {
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            Rlog.d(TAG, "SatelliteGatewayService bound");
            return this.mBinder;
        }
        return null;
    }

    public final IBinder getBinder() {
        return this.mBinder;
    }
}
