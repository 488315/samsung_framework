package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class DEROctetStringParser implements ASN1OctetStringParser {
    private DefiniteLengthInputStream stream;

    DEROctetStringParser(DefiniteLengthInputStream stream) {
        this.stream = stream;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser
    public InputStream getOctetStream() {
        return this.stream;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return new DEROctetString(this.stream.toByteArray());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }
}
