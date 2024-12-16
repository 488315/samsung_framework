package com.android.internal.app;

import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.AppPredictionServiceResolverComparator;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.TargetInfo;
import com.google.android.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
class AppPredictionServiceResolverComparator extends AbstractResolverComparator {
    private static final String TAG = "APSResolverComparator";
    private final AppPredictor mAppPredictor;
    private ResolverComparatorModel mComparatorModel;
    private final Context mContext;
    private final Intent mIntent;
    private final ModelBuilder mModelBuilder;
    private final String mReferrerPackage;
    private ResolverRankerServiceResolverComparator mResolverRankerService;
    private ResolverAppPredictorCallback mSortingCallback;
    private final UserHandle mUser;

    AppPredictionServiceResolverComparator(Context context, Intent intent, String referrerPackage, AppPredictor appPredictor, UserHandle user, ChooserActivityLogger chooserActivityLogger) {
        super(context, intent, Lists.newArrayList(user));
        this.mContext = context;
        this.mIntent = intent;
        this.mAppPredictor = appPredictor;
        this.mUser = user;
        this.mReferrerPackage = referrerPackage;
        setChooserActivityLogger(chooserActivityLogger);
        this.mModelBuilder = new ModelBuilder(appPredictor, user);
        this.mComparatorModel = this.mModelBuilder.buildFromRankedList(Collections.emptyList());
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void destroy() {
        if (this.mResolverRankerService != null) {
            this.mResolverRankerService.destroy();
            this.mResolverRankerService = null;
            this.mComparatorModel = this.mModelBuilder.buildFallbackModel(this.mResolverRankerService);
        }
        if (this.mSortingCallback != null) {
            this.mSortingCallback.destroy();
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    int compare(ResolveInfo lhs, ResolveInfo rhs) {
        return this.mComparatorModel.getComparator().compare(lhs, rhs);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    float getScore(TargetInfo targetInfo) {
        return this.mComparatorModel.getScore(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void updateModel(TargetInfo targetInfo) {
        this.mComparatorModel.notifyOnTargetSelected(targetInfo);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void handleResultMessage(Message msg) {
        if (msg.what == 0 && msg.obj != null) {
            this.mComparatorModel = this.mModelBuilder.buildFromRankedList((List) msg.obj);
        } else if (msg.obj == null && this.mResolverRankerService == null) {
            Log.e(TAG, "Unexpected null result");
        }
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    void doCompute(final List<ResolverActivity.ResolvedComponentInfo> targets) {
        if (targets.isEmpty()) {
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        List<AppTarget> appTargets = new ArrayList<>();
        for (ResolverActivity.ResolvedComponentInfo target : targets) {
            appTargets.add(new AppTarget.Builder(new AppTargetId(target.name.flattenToString()), target.name.getPackageName(), this.mUser).setClassName(target.name.getClassName()).build());
        }
        if (this.mSortingCallback != null) {
            this.mSortingCallback.destroy();
        }
        this.mSortingCallback = new ResolverAppPredictorCallback(new Consumer() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppPredictionServiceResolverComparator.this.lambda$doCompute$0(targets, (List) obj);
            }
        });
        this.mAppPredictor.sortTargets(appTargets, Executors.newSingleThreadExecutor(), this.mSortingCallback.asConsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doCompute$0(List targets, List sortedAppTargets) {
        if (sortedAppTargets.isEmpty()) {
            Log.i(TAG, "AppPredictionService disabled. Using resolver.");
            setupFallbackModel(targets);
        } else {
            Log.i(TAG, "AppPredictionService response received");
            handleResult(sortedAppTargets);
        }
    }

    private void setupFallbackModel(List<ResolverActivity.ResolvedComponentInfo> targets) {
        this.mResolverRankerService = new ResolverRankerServiceResolverComparator(this.mContext, this.mIntent, this.mReferrerPackage, new AbstractResolverComparator.AfterCompute() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$$ExternalSyntheticLambda1
            @Override // com.android.internal.app.AbstractResolverComparator.AfterCompute
            public final void afterCompute() {
                AppPredictionServiceResolverComparator.this.lambda$setupFallbackModel$1();
            }
        }, getChooserActivityLogger(), this.mUser);
        this.mComparatorModel = this.mModelBuilder.buildFallbackModel(this.mResolverRankerService);
        this.mResolverRankerService.compute(targets);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupFallbackModel$1() {
        this.mHandler.sendEmptyMessage(0);
    }

    private void handleResult(List<AppTarget> sortedAppTargets) {
        if (this.mHandler.hasMessages(1)) {
            this.mComparatorModel = this.mModelBuilder.buildFromRankedList(sortedAppTargets);
            this.mHandler.removeMessages(1);
            afterCompute();
        }
    }

    static class ModelBuilder {
        private final AppPredictor mAppPredictor;
        private final UserHandle mUser;

        ModelBuilder(AppPredictor appPredictor, UserHandle user) {
            this.mAppPredictor = appPredictor;
            this.mUser = user;
        }

        ResolverComparatorModel buildFromRankedList(List<AppTarget> sortedAppTargets) {
            return new AppPredictionServiceComparatorModel(this.mAppPredictor, this.mUser, buildTargetRanksMapFromSortedTargets(sortedAppTargets));
        }

        ResolverComparatorModel buildFallbackModel(ResolverRankerServiceResolverComparator fallback) {
            return adaptLegacyResolverComparatorToComparatorModel(fallback);
        }

        private Map<ComponentName, Integer> buildTargetRanksMapFromSortedTargets(List<AppTarget> sortedAppTargets) {
            Map<ComponentName, Integer> targetRanks = new HashMap<>();
            for (int i = 0; i < sortedAppTargets.size(); i++) {
                ComponentName componentName = new ComponentName(sortedAppTargets.get(i).getPackageName(), sortedAppTargets.get(i).getClassName());
                targetRanks.put(componentName, Integer.valueOf(i));
                Log.i(AppPredictionServiceResolverComparator.TAG, "handleSortedAppTargets, sortedAppTargets #" + i + ": " + componentName);
            }
            return targetRanks;
        }

        /* renamed from: com.android.internal.app.AppPredictionServiceResolverComparator$ModelBuilder$1, reason: invalid class name */
        class AnonymousClass1 implements ResolverComparatorModel {
            final /* synthetic */ AbstractResolverComparator val$comparator;

            AnonymousClass1(AbstractResolverComparator abstractResolverComparator) {
                this.val$comparator = abstractResolverComparator;
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public Comparator<ResolveInfo> getComparator() {
                final AbstractResolverComparator abstractResolverComparator = this.val$comparator;
                return new Comparator() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$ModelBuilder$1$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compare;
                        compare = AbstractResolverComparator.this.compare((ResolveInfo) obj, (ResolveInfo) obj2);
                        return compare;
                    }
                };
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public float getScore(TargetInfo targetInfo) {
                return this.val$comparator.getScore(targetInfo);
            }

            @Override // com.android.internal.app.ResolverComparatorModel
            public void notifyOnTargetSelected(TargetInfo targetInfo) {
                this.val$comparator.updateModel(targetInfo);
            }
        }

        private ResolverComparatorModel adaptLegacyResolverComparatorToComparatorModel(AbstractResolverComparator comparator) {
            return new AnonymousClass1(comparator);
        }
    }

    static class AppPredictionServiceComparatorModel implements ResolverComparatorModel {
        private final AppPredictor mAppPredictor;
        private final Map<ComponentName, Integer> mTargetRanks;
        private final UserHandle mUser;

        AppPredictionServiceComparatorModel(AppPredictor appPredictor, UserHandle user, Map<ComponentName, Integer> targetRanks) {
            this.mAppPredictor = appPredictor;
            this.mUser = user;
            this.mTargetRanks = targetRanks;
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public Comparator<ResolveInfo> getComparator() {
            return new Comparator() { // from class: com.android.internal.app.AppPredictionServiceResolverComparator$AppPredictionServiceComparatorModel$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$getComparator$0;
                    lambda$getComparator$0 = AppPredictionServiceResolverComparator.AppPredictionServiceComparatorModel.this.lambda$getComparator$0((ResolveInfo) obj, (ResolveInfo) obj2);
                    return lambda$getComparator$0;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getComparator$0(ResolveInfo lhs, ResolveInfo rhs) {
            Integer lhsRank = this.mTargetRanks.get(new ComponentName(lhs.activityInfo.packageName, lhs.activityInfo.name));
            Integer rhsRank = this.mTargetRanks.get(new ComponentName(rhs.activityInfo.packageName, rhs.activityInfo.name));
            if (lhsRank == null && rhsRank == null) {
                return 0;
            }
            if (lhsRank == null) {
                return -1;
            }
            if (rhsRank == null) {
                return 1;
            }
            return lhsRank.intValue() - rhsRank.intValue();
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public float getScore(TargetInfo targetInfo) {
            Integer rank = this.mTargetRanks.get(targetInfo.getResolvedComponentName());
            if (rank == null) {
                Log.w(AppPredictionServiceResolverComparator.TAG, "Score requested for unknown component. Did you call compute yet?");
                return 0.0f;
            }
            int consecutiveSumOfRanks = ((this.mTargetRanks.size() - 1) * this.mTargetRanks.size()) / 2;
            return 1.0f - (rank.intValue() / consecutiveSumOfRanks);
        }

        @Override // com.android.internal.app.ResolverComparatorModel
        public void notifyOnTargetSelected(TargetInfo targetInfo) {
            this.mAppPredictor.notifyAppTargetEvent(new AppTargetEvent.Builder(new AppTarget.Builder(new AppTargetId(targetInfo.getResolvedComponentName().toString()), targetInfo.getResolvedComponentName().getPackageName(), this.mUser).setClassName(targetInfo.getResolvedComponentName().getClassName()).build(), 1).build());
        }
    }
}
