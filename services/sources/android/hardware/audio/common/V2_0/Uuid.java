package android.hardware.audio.common.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Uuid {
    public int timeLow = 0;
    public short timeMid = 0;
    public short versionAndTimeHigh = 0;
    public short variantAndClockSeqHigh = 0;
    public byte[] node = new byte[6];

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Uuid.class) {
            return false;
        }
        Uuid uuid = (Uuid) obj;
        return this.timeLow == uuid.timeLow && this.timeMid == uuid.timeMid && this.versionAndTimeHigh == uuid.versionAndTimeHigh && this.variantAndClockSeqHigh == uuid.variantAndClockSeqHigh && HidlSupport.deepEquals(this.node, uuid.node);
    }

    public final int hashCode() {
        return Objects.hash(AudioConfig$$ExternalSyntheticOutline0.m(this.timeLow), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.timeMid))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.versionAndTimeHigh))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.variantAndClockSeqHigh))), Integer.valueOf(HidlSupport.deepHashCode(this.node)));
    }

    public final String toString() {
        return "{.timeLow = " + this.timeLow + ", .timeMid = " + ((int) this.timeMid) + ", .versionAndTimeHigh = " + ((int) this.versionAndTimeHigh) + ", .variantAndClockSeqHigh = " + ((int) this.variantAndClockSeqHigh) + ", .node = " + Arrays.toString(this.node) + "}";
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.timeLow);
        hwBlob.putInt16(4 + j, this.timeMid);
        hwBlob.putInt16(6 + j, this.versionAndTimeHigh);
        hwBlob.putInt16(8 + j, this.variantAndClockSeqHigh);
        long j2 = j + 10;
        byte[] bArr = this.node;
        if (bArr == null || bArr.length != 6) {
            throw new IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putInt8Array(j2, bArr);
    }
}
