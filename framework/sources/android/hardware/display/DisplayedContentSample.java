package android.hardware.display;

/* loaded from: classes2.dex */
public final class DisplayedContentSample {
    private long mNumFrames;
    private long[] mSamplesComponent0;
    private long[] mSamplesComponent1;
    private long[] mSamplesComponent2;
    private long[] mSamplesComponent3;

    /* loaded from: classes2.dex */
    public enum ColorComponent {
        CHANNEL0,
        CHANNEL1,
        CHANNEL2,
        CHANNEL3
    }

    public DisplayedContentSample(long numFrames, long[] sampleComponent0, long[] sampleComponent1, long[] sampleComponent2, long[] sampleComponent3) {
        this.mNumFrames = numFrames;
        this.mSamplesComponent0 = sampleComponent0;
        this.mSamplesComponent1 = sampleComponent1;
        this.mSamplesComponent2 = sampleComponent2;
        this.mSamplesComponent3 = sampleComponent3;
    }

    /* renamed from: android.hardware.display.DisplayedContentSample$1 */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$hardware$display$DisplayedContentSample$ColorComponent;

        static {
            int[] iArr = new int[ColorComponent.values().length];
            $SwitchMap$android$hardware$display$DisplayedContentSample$ColorComponent = iArr;
            try {
                iArr[ColorComponent.CHANNEL0.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$hardware$display$DisplayedContentSample$ColorComponent[ColorComponent.CHANNEL1.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$hardware$display$DisplayedContentSample$ColorComponent[ColorComponent.CHANNEL2.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$hardware$display$DisplayedContentSample$ColorComponent[ColorComponent.CHANNEL3.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public long[] getSampleComponent(ColorComponent component) {
        switch (AnonymousClass1.$SwitchMap$android$hardware$display$DisplayedContentSample$ColorComponent[component.ordinal()]) {
            case 1:
                return this.mSamplesComponent0;
            case 2:
                return this.mSamplesComponent1;
            case 3:
                return this.mSamplesComponent2;
            case 4:
                return this.mSamplesComponent3;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }

    public long getNumFrames() {
        return this.mNumFrames;
    }
}
