package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1", f = "NotificationShelfViewBinder.kt", l = {88}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class NotificationShelfViewBinder$bind$1$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ NotificationShelf $shelf;
    final /* synthetic */ NotificationShelf $this_apply;
    final /* synthetic */ NotificationShelfViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    @DebugMetadata(c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1", f = "NotificationShelfViewBinder.kt", l = {95}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationShelf $shelf;
        final /* synthetic */ NotificationShelf $this_apply;
        final /* synthetic */ NotificationShelfViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        @DebugMetadata(c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$1", f = "NotificationShelfViewBinder.kt", l = {90}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$1, reason: invalid class name and collision with other inner class name */
        /* loaded from: classes2.dex */
        public final class C00831 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationShelf $this_apply;
            final /* synthetic */ NotificationShelfViewModel $viewModel;
            int label;

            /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
            /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
            /* loaded from: classes2.dex */
            public final /* synthetic */ class C00841 implements FlowCollector, FunctionAdapter {
                public final /* synthetic */ NotificationShelf $tmp0;

                public C00841(NotificationShelf notificationShelf) {
                    this.$tmp0 = notificationShelf;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    NotificationShelf notificationShelf = this.$tmp0;
                    if (notificationShelf.mShelfRefactorFlagEnabled) {
                        notificationShelf.mCanModifyColorOfNotifications = booleanValue;
                    }
                    Unit unit = Unit.INSTANCE;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    return unit;
                }

                public final boolean equals(Object obj) {
                    if (!(obj instanceof FlowCollector) || !(obj instanceof FunctionAdapter)) {
                        return false;
                    }
                    return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
                }

                @Override // kotlin.jvm.internal.FunctionAdapter
                public final Function getFunctionDelegate() {
                    return new AdaptedFunctionReference(2, this.$tmp0, NotificationShelf.class, "setCanModifyColorOfNotifications", "setCanModifyColorOfNotifications(Z)V", 4);
                }

                public final int hashCode() {
                    return getFunctionDelegate().hashCode();
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00831(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, Continuation<? super C00831> continuation) {
                super(2, continuation);
                this.$viewModel = notificationShelfViewModel;
                this.$this_apply = notificationShelf;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00831(this.$viewModel, this.$this_apply, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00831) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShelfStatic = this.$viewModel.interactor.isShelfStatic();
                    Flow flow = new Flow() { // from class: com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1

                        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2, reason: invalid class name */
                        /* loaded from: classes2.dex */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
                            @DebugMetadata(c = "com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2", f = "NotificationShelfViewModel.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m = "emit")
                            /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                            /* loaded from: classes2.dex */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L48
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    java.lang.Boolean r5 = (java.lang.Boolean) r5
                                    boolean r5 = r5.booleanValue()
                                    r5 = r5 ^ r3
                                    java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L48
                                    return r1
                                L48:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
                                return collect;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    C00841 c00841 = new C00841(this.$this_apply);
                    this.label = 1;
                    if (flow.collect(c00841, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        @DebugMetadata(c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$2", f = "NotificationShelfViewBinder.kt", l = {94}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$2, reason: invalid class name */
        /* loaded from: classes2.dex */
        public final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationShelf $this_apply;
            final /* synthetic */ NotificationShelfViewModel $viewModel;
            int label;

            /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
            /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$2$1, reason: invalid class name and collision with other inner class name */
            /* loaded from: classes2.dex */
            public final /* synthetic */ class C00851 implements FlowCollector, FunctionAdapter {
                public final /* synthetic */ NotificationShelf $tmp0;

                public C00851(NotificationShelf notificationShelf) {
                    this.$tmp0 = notificationShelf;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    NotificationShelf notificationShelf = this.$tmp0;
                    if (notificationShelf.mShelfRefactorFlagEnabled) {
                        notificationShelf.mCanInteract = booleanValue;
                        notificationShelf.updateInteractiveness();
                    }
                    Unit unit = Unit.INSTANCE;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    return unit;
                }

                public final boolean equals(Object obj) {
                    if (!(obj instanceof FlowCollector) || !(obj instanceof FunctionAdapter)) {
                        return false;
                    }
                    return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
                }

                @Override // kotlin.jvm.internal.FunctionAdapter
                public final Function getFunctionDelegate() {
                    return new AdaptedFunctionReference(2, this.$tmp0, NotificationShelf.class, "setCanInteract", "setCanInteract(Z)V", 4);
                }

                public final int hashCode() {
                    return getFunctionDelegate().hashCode();
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = notificationShelfViewModel;
                this.$this_apply = notificationShelf;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$this_apply, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    Flow flow = ((KeyguardRepositoryImpl) this.$viewModel.interactor.keyguardRepository).isKeyguardShowing;
                    C00851 c00851 = new C00851(this.$this_apply);
                    this.label = 1;
                    if (flow.collect(c00851, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NotificationShelf notificationShelf, NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$shelf = notificationShelf;
            this.$viewModel = notificationShelfViewModel;
            this.$this_apply = notificationShelf2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$shelf, this.$viewModel, this.$this_apply, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new C00831(this.$viewModel, this.$this_apply, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$this_apply, null), 3);
                NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                NotificationShelf notificationShelf = this.$shelf;
                NotificationShelfViewModel notificationShelfViewModel = this.$viewModel;
                this.label = 1;
                if (NotificationShelfViewBinder.access$registerViewListenersWhileAttached(notificationShelfViewBinder, notificationShelf, notificationShelfViewModel, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$bind$1$1(NotificationShelf notificationShelf, NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf2, Continuation<? super NotificationShelfViewBinder$bind$1$1> continuation) {
        super(3, continuation);
        this.$shelf = notificationShelf;
        this.$viewModel = notificationShelfViewModel;
        this.$this_apply = notificationShelf2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationShelfViewBinder$bind$1$1 notificationShelfViewBinder$bind$1$1 = new NotificationShelfViewBinder$bind$1$1(this.$shelf, this.$viewModel, this.$this_apply, (Continuation) obj3);
        notificationShelfViewBinder$bind$1$1.L$0 = (LifecycleOwner) obj;
        return notificationShelfViewBinder$bind$1$1.invokeSuspend(Unit.INSTANCE);
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
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$shelf, this.$viewModel, this.$this_apply, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
