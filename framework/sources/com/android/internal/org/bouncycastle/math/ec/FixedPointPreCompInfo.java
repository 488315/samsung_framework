package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes5.dex */
public class FixedPointPreCompInfo implements PreCompInfo {
    protected ECPoint offset = null;
    protected ECLookupTable lookupTable = null;
    protected int width = -1;

    public ECLookupTable getLookupTable() {
        return this.lookupTable;
    }

    public void setLookupTable(ECLookupTable lookupTable) {
        this.lookupTable = lookupTable;
    }

    public ECPoint getOffset() {
        return this.offset;
    }

    public void setOffset(ECPoint offset) {
        this.offset = offset;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
