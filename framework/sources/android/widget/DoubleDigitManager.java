package android.widget;

import android.os.Handler;

/* loaded from: classes4.dex */
class DoubleDigitManager {
    private Integer intermediateDigit;
    private final CallBack mCallBack;
    private final long timeoutInMillis;

    interface CallBack {
        void singleDigitFinal(int i);

        boolean singleDigitIntermediate(int i);

        boolean twoDigitsFinal(int i, int i2);
    }

    public DoubleDigitManager(long timeoutInMillis, CallBack callBack) {
        this.timeoutInMillis = timeoutInMillis;
        this.mCallBack = callBack;
    }

    public void reportDigit(int digit) {
        if (this.intermediateDigit == null) {
            this.intermediateDigit = Integer.valueOf(digit);
            new Handler().postDelayed(new Runnable() { // from class: android.widget.DoubleDigitManager.1
                @Override // java.lang.Runnable
                public void run() {
                    if (DoubleDigitManager.this.intermediateDigit != null) {
                        DoubleDigitManager.this.mCallBack.singleDigitFinal(DoubleDigitManager.this.intermediateDigit.intValue());
                        DoubleDigitManager.this.intermediateDigit = null;
                    }
                }
            }, this.timeoutInMillis);
            if (!this.mCallBack.singleDigitIntermediate(digit)) {
                this.intermediateDigit = null;
                this.mCallBack.singleDigitFinal(digit);
                return;
            }
            return;
        }
        if (this.mCallBack.twoDigitsFinal(this.intermediateDigit.intValue(), digit)) {
            this.intermediateDigit = null;
        }
    }
}
