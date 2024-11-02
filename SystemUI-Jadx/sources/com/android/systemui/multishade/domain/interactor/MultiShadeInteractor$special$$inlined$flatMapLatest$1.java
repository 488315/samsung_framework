package com.android.systemui.multishade.domain.interactor;

import com.android.systemui.multishade.shared.model.ShadeConfig;
import com.android.systemui.multishade.shared.model.ShadeId;
import com.android.systemui.multishade.shared.model.ShadeModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$flatMapLatest$1", f = "MultiShadeInteractor.kt", l = {190}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class MultiShadeInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MultiShadeInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiShadeInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, MultiShadeInteractor multiShadeInteractor) {
        super(3, continuation);
        this.this$0 = multiShadeInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MultiShadeInteractor$special$$inlined$flatMapLatest$1 multiShadeInteractor$special$$inlined$flatMapLatest$1 = new MultiShadeInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        multiShadeInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        multiShadeInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return multiShadeInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
            } else {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } else {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ShadeConfig shadeConfig = (ShadeConfig) this.L$1;
            MultiShadeInteractor multiShadeInteractor = this.this$0;
            multiShadeInteractor.getClass();
            List<ShadeId> list = shadeConfig.shadeIds;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            for (ShadeId shadeId : list) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) multiShadeInteractor.repository.stateByShade;
                Object obj2 = linkedHashMap.get(shadeId);
                if (obj2 == null) {
                    obj2 = StateFlowKt.MutableStateFlow(new ShadeModel(shadeId, 0.0f, 2, null));
                    linkedHashMap.put(shadeId, obj2);
                }
                arrayList.add(FlowKt.asStateFlow((MutableStateFlow) obj2));
            }
            final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
            Flow flow = new Flow() { // from class: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$maxShadeExpansion$lambda$2$$inlined$combine$1

                /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                @DebugMetadata(c = "com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$maxShadeExpansion$lambda$2$$inlined$combine$1$3", f = "MultiShadeInteractor.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_startTcpDump}, m = "invokeSuspend")
                /* renamed from: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$maxShadeExpansion$lambda$2$$inlined$combine$1$3, reason: invalid class name */
                /* loaded from: classes.dex */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public AnonymousClass3(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                        anonymousClass3.L$0 = (FlowCollector) obj;
                        anonymousClass3.L$1 = (Object[]) obj2;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        boolean z;
                        Float f;
                        float f2;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i != 0) {
                            if (i == 1) {
                                ResultKt.throwOnFailure(obj);
                            } else {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                        } else {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            ShadeModel[] shadeModelArr = (ShadeModel[]) ((Object[]) this.L$1);
                            if (shadeModelArr.length == 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (z) {
                                f = null;
                            } else {
                                float f3 = shadeModelArr[0].expansion;
                                IntProgressionIterator it = new IntRange(1, shadeModelArr.length - 1).iterator();
                                while (it.hasNext) {
                                    f3 = Math.max(f3, shadeModelArr[it.nextInt()].expansion);
                                }
                                f = new Float(f3);
                            }
                            if (f != null) {
                                f2 = f.floatValue();
                            } else {
                                f2 = 0.0f;
                            }
                            Float f4 = new Float(f2);
                            this.label = 1;
                            if (flowCollector.emit(f4, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    final Flow[] flowArr2 = flowArr;
                    Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$maxShadeExpansion$lambda$2$$inlined$combine$1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return new ShadeModel[flowArr2.length];
                        }
                    }, new AnonymousClass3(null), flowCollector2, continuation);
                    if (combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED) {
                        return combineInternal;
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
