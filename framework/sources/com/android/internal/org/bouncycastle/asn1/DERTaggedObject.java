package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class DERTaggedObject extends ASN1TaggedObject {
    public DERTaggedObject(boolean explicit, int tagNo, ASN1Encodable obj) {
        super(explicit, tagNo, obj);
    }

    public DERTaggedObject(int tagNo, ASN1Encodable encodable) {
        super(true, tagNo, encodable);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return this.explicit || this.obj.toASN1Primitive().toDERObject().isConstructed();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws IOException {
        ASN1Primitive primitive = this.obj.toASN1Primitive().toDERObject();
        int length = primitive.encodedLength();
        if (this.explicit) {
            return StreamUtil.calculateTagLength(this.tagNo) + StreamUtil.calculateBodyLength(length) + length;
        }
        return StreamUtil.calculateTagLength(this.tagNo) + (length - 1);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        ASN1Primitive primitive = this.obj.toASN1Primitive().toDERObject();
        int flags = 128;
        if (this.explicit || primitive.isConstructed()) {
            flags = 128 | 32;
        }
        out.writeTag(withTag, flags, this.tagNo);
        if (this.explicit) {
            out.writeLength(primitive.encodedLength());
        }
        primitive.encode(out.getDERSubStream(), this.explicit);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }
}
