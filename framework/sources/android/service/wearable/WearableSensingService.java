package android.service.wearable;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.ambientcontext.AmbientContextEventRequest;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.SharedMemory;
import android.service.ambientcontext.AmbientContextDetectionResult;
import android.service.ambientcontext.AmbientContextDetectionServiceStatus;
import android.service.wearable.IWearableSensingService;
import android.service.wearable.WearableSensingService;
import android.util.Slog;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class WearableSensingService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.wearable.WearableSensingService";
    public static final String STATUS_RESPONSE_BUNDLE_KEY = "android.app.wearable.WearableSensingStatusBundleKey";
    private static final String TAG = WearableSensingService.class.getSimpleName();

    public abstract void onDataProvided(PersistableBundle persistableBundle, SharedMemory sharedMemory, Consumer<Integer> consumer);

    public abstract void onDataStreamProvided(ParcelFileDescriptor parcelFileDescriptor, Consumer<Integer> consumer);

    public abstract void onQueryServiceStatus(Set<Integer> set, String str, Consumer<AmbientContextDetectionServiceStatus> consumer);

    public abstract void onStartDetection(AmbientContextEventRequest ambientContextEventRequest, String str, Consumer<AmbientContextDetectionServiceStatus> consumer, Consumer<AmbientContextDetectionResult> consumer2);

    public abstract void onStopDetection(String str);

    /* renamed from: android.service.wearable.WearableSensingService$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 extends IWearableSensingService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, final RemoteCallback callback) {
            Objects.requireNonNull(parcelFileDescriptor);
            Consumer<Integer> consumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$provideDataStream$0(RemoteCallback.this, (Integer) obj);
                }
            };
            WearableSensingService.this.onDataStreamProvided(parcelFileDescriptor, consumer);
        }

        public static /* synthetic */ void lambda$provideDataStream$0(RemoteCallback callback, Integer response) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", response.intValue());
            callback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideData(PersistableBundle data, SharedMemory sharedMemory, final RemoteCallback callback) {
            Objects.requireNonNull(data);
            Consumer<Integer> consumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$provideData$1(RemoteCallback.this, (Integer) obj);
                }
            };
            WearableSensingService.this.onDataProvided(data, sharedMemory, consumer);
        }

        public static /* synthetic */ void lambda$provideData$1(RemoteCallback callback, Integer response) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", response.intValue());
            callback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startDetection(AmbientContextEventRequest request, String packageName, final RemoteCallback detectionResultCallback, final RemoteCallback statusCallback) {
            Objects.requireNonNull(request);
            Objects.requireNonNull(packageName);
            Objects.requireNonNull(detectionResultCallback);
            Objects.requireNonNull(statusCallback);
            Consumer<AmbientContextDetectionResult> detectionResultConsumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startDetection$2(RemoteCallback.this, (AmbientContextDetectionResult) obj);
                }
            };
            Consumer<AmbientContextDetectionServiceStatus> statusConsumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$startDetection$3(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            };
            WearableSensingService.this.onStartDetection(request, packageName, statusConsumer, detectionResultConsumer);
            Slog.d(WearableSensingService.TAG, "startDetection " + request);
        }

        public static /* synthetic */ void lambda$startDetection$2(RemoteCallback detectionResultCallback, AmbientContextDetectionResult result) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionResult.RESULT_RESPONSE_BUNDLE_KEY, result);
            detectionResultCallback.sendResult(bundle);
        }

        public static /* synthetic */ void lambda$startDetection$3(RemoteCallback statusCallback, AmbientContextDetectionServiceStatus status) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, status);
            statusCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopDetection(String packageName) {
            Objects.requireNonNull(packageName);
            WearableSensingService.this.onStopDetection(packageName);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void queryServiceStatus(int[] eventTypes, String packageName, final RemoteCallback callback) {
            Objects.requireNonNull(eventTypes);
            Objects.requireNonNull(packageName);
            Objects.requireNonNull(callback);
            Consumer<AmbientContextDetectionServiceStatus> consumer = new Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WearableSensingService.AnonymousClass1.lambda$queryServiceStatus$4(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            };
            Integer[] events = WearableSensingService.intArrayToIntegerArray(eventTypes);
            WearableSensingService.this.onQueryServiceStatus(new HashSet(Arrays.asList(events)), packageName, consumer);
        }

        public static /* synthetic */ void lambda$queryServiceStatus$4(RemoteCallback callback, AmbientContextDetectionServiceStatus response) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, response);
            callback.sendResult(bundle);
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new AnonymousClass1();
        }
        Slog.w(TAG, "Incorrect service interface, returning null.");
        return null;
    }

    public static Integer[] intArrayToIntegerArray(int[] integerSet) {
        Integer[] intArray = new Integer[integerSet.length];
        int i = 0;
        int length = integerSet.length;
        int i2 = 0;
        while (i2 < length) {
            Integer type = Integer.valueOf(integerSet[i2]);
            intArray[i] = type;
            i2++;
            i++;
        }
        return intArray;
    }
}
