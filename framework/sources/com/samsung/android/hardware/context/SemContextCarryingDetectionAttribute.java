package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextCarryingDetectionAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextCarryingDetectionAttribute> CREATOR = new Parcelable.Creator<SemContextCarryingDetectionAttribute>() { // from class: com.samsung.android.hardware.context.SemContextCarryingDetectionAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCarryingDetectionAttribute createFromParcel(Parcel in) {
            return new SemContextCarryingDetectionAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextCarryingDetectionAttribute[] newArray(int size) {
            return new SemContextCarryingDetectionAttribute[size];
        }
    };
    private static final int DATA = 1;
    private static final int MODE = 2;
    private static final String TAG = "SemContextCarryingDetection";
    private int mData;
    private int mDpcmHighData;
    private int mDpcmLowData;
    private int mMode;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SemContextCarryingDetectionAttribute() {
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
        setAttribute();
    }

    SemContextCarryingDetectionAttribute(Parcel src) {
        super(src);
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
    }

    public SemContextCarryingDetectionAttribute(int mode, int data) {
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
        this.mMode = mode;
        this.mData = data;
        setAttribute();
    }

    public SemContextCarryingDetectionAttribute(int mode, int dpcmLowData, int dpcmHighData) {
        this.mMode = 2;
        this.mData = 1;
        this.mDpcmLowData = -1;
        this.mDpcmHighData = -1;
        this.mMode = mode;
        this.mDpcmLowData = dpcmLowData;
        this.mDpcmHighData = dpcmHighData;
        Bundle attribute = new Bundle();
        attribute.putInt("dpcm_mode", this.mMode);
        if (mode == 9) {
            attribute.putInt("dpcm_lowlux", dpcmLowData);
            attribute.putInt("dpcm_highlux", dpcmHighData);
        } else if (mode == 10) {
            attribute.putInt("dpcm_lowcnt", dpcmLowData);
            attribute.putInt("dpcm_highcnt", dpcmHighData);
        }
        super.setAttribute(51, attribute);
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i;
        int i2 = this.mMode;
        if (i2 == 9) {
            int i3 = this.mDpcmLowData;
            if (i3 < -2 || i3 > 1000000 || (i = this.mDpcmHighData) < -2 || i > 1000000) {
                Log.d(TAG, "DPCM Data value is wrong");
                return false;
            }
        } else if (i2 == 10 && (this.mDpcmLowData < 0 || this.mDpcmHighData < 0)) {
            Log.d(TAG, "DPCM Data value is wrong");
            return false;
        }
        if (i2 < 1 || i2 > 12) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (i2 == 9 || i2 == 11 || i2 == 10 || i2 == 12) {
            return true;
        }
        int i4 = this.mData;
        if (i4 > 0 && i4 <= 127) {
            return true;
        }
        Log.d(TAG, "Data value is wrong!!");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("dpcm_mode", this.mMode);
        attribute.putInt("dpcm_data", this.mData);
        super.setAttribute(51, attribute);
    }
}
