package android.view.inputmethod;

import android.os.CancellationSignal;
import android.os.CancellationSignalBeamer;
import android.os.IBinder;

/* loaded from: classes4.dex */
public abstract class CancellableHandwritingGesture extends HandwritingGesture {
    CancellationSignal mCancellationSignal;
    IBinder mCancellationSignalToken;

    public void setCancellationSignal(CancellationSignal cancellationSignal) {
        this.mCancellationSignal = cancellationSignal;
    }

    CancellationSignal getCancellationSignal() {
        return this.mCancellationSignal;
    }

    public void unbeamCancellationSignal(CancellationSignalBeamer.Receiver receiver) {
        if (this.mCancellationSignalToken != null) {
            this.mCancellationSignal = receiver.unbeam(this.mCancellationSignalToken);
            this.mCancellationSignalToken = null;
        }
    }
}
