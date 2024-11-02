package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.functional.PlaceHolder;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public final class MFDescriptorHolder<T extends MFDescriptor> extends MFDescriptorBase implements PlaceHolder<T> {
    T descriptor;
    Function<Object[], MFDescriptor> mfDescriptorProvider;
    Object[] vararg;

    /* JADX WARN: Multi-variable type inference failed */
    public MFDescriptorHolder(MFDescriptor mFDescriptor, Object... args) {
        this.vararg = args;
        this.descriptor = mFDescriptor;
        setFilterId(mFDescriptor.getFilterId());
        setFilterType(mFDescriptor.getFilterType());
    }

    public MFDescriptorHolder(Function<Object[], MFDescriptor> mfDescriptorProvider, Object... args) {
        this.vararg = args;
        this.mfDescriptorProvider = mfDescriptorProvider;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public void put(T instance) {
        this.descriptor = instance;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public Object[] getParameters() {
        return this.vararg;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public T reset() {
        T t = (T) Optional.ofNullable(this.descriptor).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.descriptor.MFDescriptorHolder$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return MFDescriptorHolder.this.m8741xea657ff5();
            }
        });
        this.vararg = null;
        this.descriptor = null;
        this.mfDescriptorProvider = null;
        if (t != 0) {
            copyTo((MediaFilter.Option) t);
        }
        return t;
    }

    /* renamed from: lambda$reset$1$com-samsung-android-sume-core-descriptor-MFDescriptorHolder */
    public /* synthetic */ MFDescriptor m8741xea657ff5() {
        return (MFDescriptor) Optional.ofNullable(this.mfDescriptorProvider).map(new Function() { // from class: com.samsung.android.sume.core.descriptor.MFDescriptorHolder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MFDescriptorHolder.this.m8740x2fefdf74((Function) obj);
            }
        }).orElse(null);
    }

    /* renamed from: lambda$reset$0$com-samsung-android-sume-core-descriptor-MFDescriptorHolder */
    public /* synthetic */ MFDescriptor m8740x2fefdf74(Function it) {
        return (MFDescriptor) it.apply(this.vararg);
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public boolean isEmpty() {
        return this.descriptor == null;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public boolean isNotEmpty() {
        return this.descriptor != null;
    }
}
