package com.google.zxing.oned;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.QuantumSecurityInfo;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class Code39Reader extends OneDReader {
    public static final int[] CHARACTER_ENCODINGS;

    static {
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".toCharArray();
        CHARACTER_ENCODINGS = new int[]{52, IKnoxCustomManager.Stub.TRANSACTION_setAsoc, 97, QuantumSecurityInfo.QUANTUM_KEY_STATUS.KEY_STATUS_EXCEPTION, 49, 304, 112, 37, IKnoxCustomManager.Stub.TRANSACTION_startTcpDump, 100, 265, 73, 328, 25, IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView, 88, 13, 268, 76, 28, 259, 67, 322, 19, IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastInternal, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 148, 168, 162, 138, 42};
    }

    public Code39Reader() {
        this(false);
    }

    public Code39Reader(boolean z) {
        this(z, false);
    }

    public Code39Reader(boolean z, boolean z2) {
    }
}
