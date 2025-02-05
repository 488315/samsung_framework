package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class DLTaggedObject extends ASN1TaggedObject {
    public DLTaggedObject(boolean explicit, int tagNo, ASN1Encodable obj) {
        super(explicit, tagNo, obj);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return this.explicit || this.obj.toASN1Primitive().toDLObject().isConstructed();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws IOException {
        int length = this.obj.toASN1Primitive().toDLObject().encodedLength();
        if (this.explicit) {
            return StreamUtil.calculateTagLength(this.tagNo) + StreamUtil.calculateBodyLength(length) + length;
        }
        return StreamUtil.calculateTagLength(this.tagNo) + (length - 1);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        ASN1Primitive primitive = this.obj.toASN1Primitive().toDLObject();
        int flags = 128;
        if (this.explicit || primitive.isConstructed()) {
            flags = 128 | 32;
        }
        out.writeTag(withTag, flags, this.tagNo);
        if (this.explicit) {
            out.writeLength(primitive.encodedLength());
        }
        out.getDLSubStream().writePrimitive(primitive, this.explicit);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }
}
