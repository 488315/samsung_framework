package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.descriptor.nn.NNDescriptor;
import java.util.function.Consumer;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ModelSelector {

    /* loaded from: classes4.dex */
    public static class Item {
        public Consumer<NNDescriptor> descriptorUpdater;
        public String name;
    }

    Item select(MediaBuffer mediaBuffer);
}