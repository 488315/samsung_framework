package android.chre.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean abortIfNoContextHubFound();

    boolean bugFixReduceLockHoldingPeriod();

    boolean contextHubCallbackUuidEnabled();

    boolean flagLogNanoappLoadMetrics();

    boolean metricsReporterInTheDaemon();

    boolean reconnectHostEndpointsAfterHalRestart();

    boolean reduceLockHoldingPeriod();

    boolean reliableMessage();

    boolean reliableMessageDuplicateDetectionService();

    boolean reliableMessageImplementation();

    boolean reliableMessageRetrySupportService();

    boolean reliableMessageTestModeBehavior();

    boolean removeApWakeupMetricReportLimit();

    boolean waitForPreloadedNanoappStart();
}
