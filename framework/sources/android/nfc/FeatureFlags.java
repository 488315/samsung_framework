package android.nfc;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean enableNfcCharging();

    boolean enableNfcMainline();

    boolean enableNfcReaderOption();

    boolean enableNfcSetDiscoveryTech();

    boolean enableNfcUserRestriction();

    boolean enableTagDetectionBroadcasts();

    boolean nfcObserveMode();

    boolean nfcObserveModeStShim();

    boolean nfcOemExtension();

    boolean nfcReadPollingLoop();

    boolean nfcReadPollingLoopStShim();

    boolean nfcSetDefaultDiscTech();

    boolean nfcStateChange();

    boolean nfcVendorCmd();
}
