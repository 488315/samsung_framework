package org.xbill.DNS;

/* loaded from: classes.dex */
public class PTRRecord extends SingleCompressedNameBase {
    private static final long serialVersionUID = -8321636610425434192L;

    PTRRecord() {
    }

    @Override // org.xbill.DNS.Record
    Record getObject() {
        return new PTRRecord();
    }
}
