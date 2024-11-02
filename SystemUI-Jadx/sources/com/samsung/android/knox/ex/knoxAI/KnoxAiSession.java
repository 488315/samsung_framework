package com.samsung.android.knox.ex.knoxAI;

import android.util.Log;
import com.samsung.android.knox.ex.knoxAI.KnoxAiManager;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class KnoxAiSession {
    public static final String TAG = "KnoxAiSession";
    public final KnoxAiManagerInternal mKnoxAiManagerInternal;
    public final long mSessionID;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum CompUnit {
        CPU,
        GPU,
        DSP,
        NPU;

        public final int getValue() {
            return ordinal();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum DataFormat {
        NCHW(0),
        NHWC(1);

        private final byte value;

        DataFormat(int i) {
            this.value = (byte) i;
        }

        public final byte getValue() {
            return this.value;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum DataType {
        FLOAT32(0),
        FLOAT16(1),
        BYTE(2),
        INT64(3),
        STRING(4),
        SEQUENCE_MAP(5),
        INT32(6);

        private final byte value;

        DataType(int i) {
            this.value = (byte) i;
        }

        public final byte getValue() {
            return this.value;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum ExecType {
        FLOAT32,
        FLOAT16,
        QASYMM16,
        QASYMM8,
        BFLOAT16;

        public final int getValue() {
            return ordinal();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum Mode {
        DEBUG,
        RELEASE;

        public final int getValue() {
            return ordinal();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum ModelInputType {
        FILEPATH,
        FD,
        BUFFER;

        public final int getValue() {
            return ordinal();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum ModelType {
        CAFFE,
        SNPE,
        EDEN,
        TENSORFLOW,
        TENSORFLOWLITE,
        OFI,
        SNF,
        HVXNN,
        ONNX,
        SNAPLITE,
        TVM;

        public final int getValue() {
            return ordinal();
        }
    }

    public KnoxAiSession(KnoxAiManagerInternal knoxAiManagerInternal, long j) {
        Log.d(TAG, "KnoxAiSession session init");
        this.mKnoxAiManagerInternal = knoxAiManagerInternal;
        this.mSessionID = j;
    }

    public final KnoxAiManager.ErrorCodes close() {
        KnoxAiManager.ErrorCodes errorCodes = KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        KnoxAiManagerInternal knoxAiManagerInternal = this.mKnoxAiManagerInternal;
        if (knoxAiManagerInternal == null) {
            Log.e(TAG, "ERROR: Invalid Session, Create session via KnoxAiManager instead");
            return errorCodes;
        }
        return knoxAiManagerInternal.close(this.mSessionID);
    }

    public final KnoxAiManager.ErrorCodes execute(DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) {
        KnoxAiManager.ErrorCodes errorCodes = KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        KnoxAiManagerInternal knoxAiManagerInternal = this.mKnoxAiManagerInternal;
        if (knoxAiManagerInternal == null) {
            Log.e(TAG, "ERROR: Invalid Session, Create session via KnoxAiManager instead");
            return errorCodes;
        }
        return knoxAiManagerInternal.execute(this.mSessionID, dataBufferArr, dataBufferArr2);
    }

    public final KnoxAiManager.ErrorCodes getModelInputShape(int i, int[] iArr) {
        KnoxAiManager.ErrorCodes errorCodes = KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        KnoxAiManagerInternal knoxAiManagerInternal = this.mKnoxAiManagerInternal;
        if (knoxAiManagerInternal == null) {
            Log.e(TAG, "ERROR: Invalid Session, Create session via KnoxAiManager instead");
            return errorCodes;
        }
        return knoxAiManagerInternal.getModelInputShape(this.mSessionID, i, iArr);
    }

    public final long getSessionID() {
        return this.mSessionID;
    }

    public final KnoxAiManager.ErrorCodes open(KfaOptions kfaOptions) {
        KnoxAiManager.ErrorCodes errorCodes = KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        KnoxAiManagerInternal knoxAiManagerInternal = this.mKnoxAiManagerInternal;
        if (knoxAiManagerInternal == null) {
            Log.e(TAG, "ERROR: Invalid Session, Create session via KnoxAiManager instead");
            return errorCodes;
        }
        return knoxAiManagerInternal.open(this.mSessionID, kfaOptions);
    }

    public KnoxAiSession() {
        this.mKnoxAiManagerInternal = null;
        this.mSessionID = -1L;
    }
}
