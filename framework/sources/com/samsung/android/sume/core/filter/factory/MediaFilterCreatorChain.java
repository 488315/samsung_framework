package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda12;
import com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda6;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain;
import com.samsung.android.sume.core.plugin.PluginStore;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class MediaFilterCreatorChain implements MediaFilterCreator {
    private List<MediaFilterCreator> creators;
    private DescriptorFinder descriptorFinder;
    private final List<Enum<?>> types;

    /* loaded from: classes4.dex */
    public interface DescriptorFinder {
        MFDescriptor find(MFDescriptor mFDescriptor);
    }

    public MediaFilterCreatorChain(Enum<?>... types) {
        this.types = Arrays.asList(types);
    }

    public void prepare(final Map<Class<?>, List<MediaFilterCreator>> registry) {
        List<MediaFilterCreator> list = (List) this.types.stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterCreatorChain.lambda$prepare$0(registry, (Enum) obj);
            }
        }).collect(Collectors.toList());
        this.creators = list;
        this.descriptorFinder = (DescriptorFinder) list.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaFilterCreatorChain.lambda$prepare$1((MediaFilterCreator) obj);
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterCreatorChain.lambda$prepare$3((MediaFilterCreator) obj);
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return MediaFilterCreatorChain.lambda$prepare$5();
            }
        });
    }

    public static /* synthetic */ MediaFilterCreator lambda$prepare$0(Map registry, Enum e) {
        return (MediaFilterCreator) ((List) Optional.ofNullable((List) registry.get(e)).orElseGet(new ChannelRouterBase$$ExternalSyntheticLambda6())).stream().findFirst().orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
    }

    public static /* synthetic */ boolean lambda$prepare$1(MediaFilterCreator e) {
        return e instanceof PluginFilterCreator;
    }

    public static /* synthetic */ DescriptorFinder lambda$prepare$3(final MediaFilterCreator e) {
        return new DescriptorFinder() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda8
            @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain.DescriptorFinder
            public final MFDescriptor find(MFDescriptor mFDescriptor) {
                return MediaFilterCreatorChain.lambda$prepare$2(MediaFilterCreator.this, mFDescriptor);
            }
        };
    }

    public static /* synthetic */ MFDescriptor lambda$prepare$2(MediaFilterCreator e, MFDescriptor descriptor) {
        PluginStore store = ((PluginFilterCreator) e).getPluginStore();
        return (MFDescriptor) Optional.ofNullable(store.get(descriptor)).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((PluginStore.Entry) obj).getDescriptor();
            }
        }).orElse(null);
    }

    public static /* synthetic */ MFDescriptor lambda$prepare$4(MFDescriptor descriptor) {
        return null;
    }

    public static /* synthetic */ DescriptorFinder lambda$prepare$5() {
        return new DescriptorFinder() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda4
            @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain.DescriptorFinder
            public final MFDescriptor find(MFDescriptor mFDescriptor) {
                return MediaFilterCreatorChain.lambda$prepare$4(mFDescriptor);
            }
        };
    }

    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(final MediaFilterFactory factory, final MFDescriptor descriptor, final MediaFilter successor) {
        return (MediaFilter) Optional.ofNullable(this.descriptorFinder).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                MFDescriptor find;
                find = ((MediaFilterCreatorChain.DescriptorFinder) obj).find(MFDescriptor.this);
                return find;
            }
        }).map(new Function() { // from class: com.samsung.android.sume.core.filter.factory.MediaFilterCreatorChain$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MediaFilterCreatorChain.this.m8771xeb0b27c7(factory, successor, (MFDescriptor) obj);
            }
        }).orElse(null);
    }

    /* renamed from: lambda$newFilter$7$com-samsung-android-sume-core-filter-factory-MediaFilterCreatorChain */
    public /* synthetic */ MediaFilter m8771xeb0b27c7(MediaFilterFactory factory, MediaFilter successor, MFDescriptor e) {
        MediaFilter mediaFilter = null;
        for (MediaFilterCreator creator : this.creators) {
            mediaFilter = creator.newFilter(factory, e, successor);
        }
        return mediaFilter;
    }
}
