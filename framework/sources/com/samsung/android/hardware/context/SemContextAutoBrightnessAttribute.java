package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextAutoBrightnessAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextAutoBrightnessAttribute> CREATOR = new Parcelable.Creator<SemContextAutoBrightnessAttribute>() { // from class: com.samsung.android.hardware.context.SemContextAutoBrightnessAttribute.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightnessAttribute createFromParcel(Parcel in) {
            return new SemContextAutoBrightnessAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightnessAttribute[] newArray(int size) {
            return new SemContextAutoBrightnessAttribute[size];
        }
    };
    private static final int MODE_CONFIGURATION = 1;
    private static final int MODE_DEVICE_MODE = 0;
    private static final String TAG = "SemContextAutoBrightnessAttribute";
    private int mDeviceMode;
    private byte[] mLuminanceTable;
    private int mMode;

    /* renamed from: com.samsung.android.hardware.context.SemContextAutoBrightnessAttribute$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextAutoBrightnessAttribute> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightnessAttribute createFromParcel(Parcel in) {
            return new SemContextAutoBrightnessAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightnessAttribute[] newArray(int size) {
            return new SemContextAutoBrightnessAttribute[size];
        }
    }

    public SemContextAutoBrightnessAttribute() {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
        setAttribute();
    }

    SemContextAutoBrightnessAttribute(Parcel src) {
        super(src);
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
    }

    public SemContextAutoBrightnessAttribute(int deviceMode) {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
        this.mDeviceMode = deviceMode;
        this.mMode = 0;
        setAttribute();
    }

    public SemContextAutoBrightnessAttribute(byte[] luminanceTable) {
        this.mLuminanceTable = null;
        this.mDeviceMode = 0;
        this.mMode = -1;
        this.mMode = 1;
        if (luminanceTable != null) {
            byte[] bArr = new byte[luminanceTable.length];
            this.mLuminanceTable = bArr;
            System.arraycopy(luminanceTable, 0, bArr, 0, luminanceTable.length);
            setAttribute();
            return;
        }
        Log.e(TAG, "The luminanceTable is wrong.");
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mMode;
        if (i == 0) {
            int i2 = this.mDeviceMode;
            if (i2 < 0 || i2 > 2) {
                Log.e(TAG, "The device mode is wrong.");
                return false;
            }
        } else if (i == 1 && this.mLuminanceTable == null) {
            Log.e(TAG, "The luminance configuration data is null.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("mode", this.mMode);
        int i = this.mMode;
        if (i == 1) {
            attribute.putByteArray("luminance_config_data", this.mLuminanceTable);
        } else if (i == 0) {
            attribute.putInt("device_mode", this.mDeviceMode);
        }
        super.setAttribute(39, attribute);
    }
}
