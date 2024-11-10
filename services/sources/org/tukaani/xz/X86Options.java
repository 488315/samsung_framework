package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.X86;

/* loaded from: classes2.dex */
public class X86Options extends BCJOptions {
    @Override // org.tukaani.xz.BCJOptions
    public /* bridge */ /* synthetic */ Object clone() {
        return super.clone();
    }

    public X86Options() {
        super(1);
    }

    @Override // org.tukaani.xz.FilterOptions
    public InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) {
        return new SimpleInputStream(inputStream, new X86(false, this.startOffset));
    }
}
