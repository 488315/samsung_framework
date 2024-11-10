package org.tukaani.xz.delta;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;

/* loaded from: classes2.dex */
public class DeltaDecoder extends DeltaCoder {
    public DeltaDecoder(int i) {
        super(i);
    }

    public void decode(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            byte b = bArr[i];
            byte[] bArr2 = this.history;
            int i4 = this.distance;
            int i5 = this.pos;
            byte b2 = (byte) (b + bArr2[(i4 + i5) & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT]);
            bArr[i] = b2;
            this.pos = i5 - 1;
            bArr2[i5 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT] = b2;
            i++;
        }
    }
}
