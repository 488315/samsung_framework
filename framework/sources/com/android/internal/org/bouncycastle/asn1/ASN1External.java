package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class ASN1External extends ASN1Primitive {
    protected ASN1Primitive dataValueDescriptor;
    protected ASN1ObjectIdentifier directReference;
    protected int encoding;
    protected ASN1Primitive externalContent;
    protected ASN1Integer indirectReference;

    public ASN1External(ASN1EncodableVector vector) {
        int offset = 0;
        ASN1Primitive enc = getObjFromVector(vector, 0);
        if (enc instanceof ASN1ObjectIdentifier) {
            this.directReference = (ASN1ObjectIdentifier) enc;
            offset = 0 + 1;
            enc = getObjFromVector(vector, offset);
        }
        if (enc instanceof ASN1Integer) {
            this.indirectReference = (ASN1Integer) enc;
            offset++;
            enc = getObjFromVector(vector, offset);
        }
        if (!(enc instanceof ASN1TaggedObject)) {
            this.dataValueDescriptor = enc;
            offset++;
            enc = getObjFromVector(vector, offset);
        }
        if (vector.size() != offset + 1) {
            throw new IllegalArgumentException("input vector too large");
        }
        if (!(enc instanceof ASN1TaggedObject)) {
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        }
        ASN1TaggedObject obj = (ASN1TaggedObject) enc;
        setEncoding(obj.getTagNo());
        this.externalContent = obj.getObject();
    }

    private ASN1Primitive getObjFromVector(ASN1EncodableVector v, int index) {
        if (v.size() <= index) {
            throw new IllegalArgumentException("too few objects in input vector");
        }
        return v.get(index).toASN1Primitive();
    }

    public ASN1External(ASN1ObjectIdentifier directReference, ASN1Integer indirectReference, ASN1Primitive dataValueDescriptor, DERTaggedObject externalData) {
        this(directReference, indirectReference, dataValueDescriptor, externalData.getTagNo(), externalData.toASN1Primitive());
    }

    public ASN1External(ASN1ObjectIdentifier directReference, ASN1Integer indirectReference, ASN1Primitive dataValueDescriptor, int encoding, ASN1Primitive externalData) {
        setDirectReference(directReference);
        setIndirectReference(indirectReference);
        setDataValueDescriptor(dataValueDescriptor);
        setEncoding(encoding);
        setExternalContent(externalData.toASN1Primitive());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return new DERExternal(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return new DLExternal(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        int ret = 0;
        if (this.directReference != null) {
            ret = this.directReference.hashCode();
        }
        if (this.indirectReference != null) {
            ret ^= this.indirectReference.hashCode();
        }
        if (this.dataValueDescriptor != null) {
            ret ^= this.dataValueDescriptor.hashCode();
        }
        return ret ^ this.externalContent.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return true;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws IOException {
        return getEncoded().length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive o) {
        if (!(o instanceof ASN1External)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        ASN1External other = (ASN1External) o;
        if (this.directReference != null && (other.directReference == null || !other.directReference.equals((ASN1Primitive) this.directReference))) {
            return false;
        }
        if (this.indirectReference != null && (other.indirectReference == null || !other.indirectReference.equals((ASN1Primitive) this.indirectReference))) {
            return false;
        }
        if (this.dataValueDescriptor == null || (other.dataValueDescriptor != null && other.dataValueDescriptor.equals(this.dataValueDescriptor))) {
            return this.externalContent.equals(other.externalContent);
        }
        return false;
    }

    public ASN1Primitive getDataValueDescriptor() {
        return this.dataValueDescriptor;
    }

    public ASN1ObjectIdentifier getDirectReference() {
        return this.directReference;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public ASN1Primitive getExternalContent() {
        return this.externalContent;
    }

    public ASN1Integer getIndirectReference() {
        return this.indirectReference;
    }

    private void setDataValueDescriptor(ASN1Primitive dataValueDescriptor) {
        this.dataValueDescriptor = dataValueDescriptor;
    }

    private void setDirectReference(ASN1ObjectIdentifier directReferemce) {
        this.directReference = directReferemce;
    }

    private void setEncoding(int encoding) {
        if (encoding < 0 || encoding > 2) {
            throw new IllegalArgumentException("invalid encoding value: " + encoding);
        }
        this.encoding = encoding;
    }

    private void setExternalContent(ASN1Primitive externalContent) {
        this.externalContent = externalContent;
    }

    private void setIndirectReference(ASN1Integer indirectReference) {
        this.indirectReference = indirectReference;
    }
}
