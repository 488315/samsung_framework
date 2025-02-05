package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class DLSequence extends ASN1Sequence {
    private int bodyLength;

    public DLSequence() {
        this.bodyLength = -1;
    }

    public DLSequence(ASN1Encodable element) {
        super(element);
        this.bodyLength = -1;
    }

    public DLSequence(ASN1EncodableVector elementVector) {
        super(elementVector);
        this.bodyLength = -1;
    }

    public DLSequence(ASN1Encodable[] elements) {
        super(elements);
        this.bodyLength = -1;
    }

    DLSequence(ASN1Encodable[] elements, boolean clone) {
        super(elements, clone);
        this.bodyLength = -1;
    }

    private int getBodyLength() throws IOException {
        if (this.bodyLength < 0) {
            int count = this.elements.length;
            int totalLength = 0;
            for (int i = 0; i < count; i++) {
                ASN1Primitive dlObject = this.elements[i].toASN1Primitive().toDLObject();
                totalLength += dlObject.encodedLength();
            }
            this.bodyLength = totalLength;
        }
        int count2 = this.bodyLength;
        return count2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws IOException {
        int length = getBodyLength();
        return StreamUtil.calculateBodyLength(length) + 1 + length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        if (withTag) {
            out.write(48);
        }
        ASN1OutputStream dlOut = out.getDLSubStream();
        int count = this.elements.length;
        if (this.bodyLength >= 0 || count > 16) {
            int totalLength = getBodyLength();
            out.writeLength(totalLength);
            for (int i = 0; i < count; i++) {
                dlOut.writePrimitive(this.elements[i].toASN1Primitive(), true);
            }
            return;
        }
        int totalLength2 = 0;
        ASN1Primitive[] dlObjects = new ASN1Primitive[count];
        for (int i2 = 0; i2 < count; i2++) {
            ASN1Primitive dlObject = this.elements[i2].toASN1Primitive().toDLObject();
            dlObjects[i2] = dlObject;
            totalLength2 += dlObject.encodedLength();
        }
        this.bodyLength = totalLength2;
        out.writeLength(totalLength2);
        for (int i3 = 0; i3 < count; i3++) {
            dlOut.writePrimitive(dlObjects[i3], true);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }
}
