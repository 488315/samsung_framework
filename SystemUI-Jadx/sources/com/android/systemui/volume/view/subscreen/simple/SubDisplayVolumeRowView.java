package com.android.systemui.volume.view.subscreen.simple;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.view.ViewLevelConverter;
import com.android.systemui.volume.view.icon.VolumeIcon;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class SubDisplayVolumeRowView extends LinearLayout implements VolumeObserver<VolumePanelState> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mEarProtectLevel;
    public HandlerWrapper mHandlerWrapper;
    public VolumeIcon mIcon;
    public final ColorStateList mIconActiveBgColor;
    public final ColorStateList mIconActiveColor;
    public TextView mLabelTextView;
    public ObjectAnimator mProgressBarAnimator;
    public final SubDisplayVolumeRowView$$ExternalSyntheticLambda0 mRecheckCallback;
    public final Resources mResources;
    public SeekBar mSeekBar;
    public final StoreInteractor mStoreInteractor;
    public int mStream;
    public int mTargetProgressLevel;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType = iArr;
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR_LATER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_STOP_SLIDER_TRACKING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_ARROW_RIGHT_CLICKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_ARROW_LEFT_CLICKED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public /* synthetic */ VolumeSeekBarChangeListener(SubDisplayVolumeRowView subDisplayVolumeRowView, int i) {
            this();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                SubDisplayVolumeRowView.this.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(SubDisplayVolumeRowView.this.mStream).progress(i).isFromOutside(true).build(), false);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            SubDisplayVolumeRowView.this.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_START_SLIDER_TRACKING).stream(SubDisplayVolumeRowView.this.mStream).isFromOutside(true).build(), false);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            SubDisplayVolumeRowView.this.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STOP_SLIDER_TRACKING).stream(SubDisplayVolumeRowView.this.mStream).isFromOutside(true).build(), true);
        }

        private VolumeSeekBarChangeListener() {
        }
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda0] */
    public SubDisplayVolumeRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStream = -1;
        this.mStoreInteractor = new StoreInteractor(this, null);
        this.mRecheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SubDisplayVolumeRowView subDisplayVolumeRowView = SubDisplayVolumeRowView.this;
                subDisplayVolumeRowView.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(subDisplayVolumeRowView.mStream).progress(subDisplayVolumeRowView.mSeekBar.getProgress()).build(), false);
            }
        };
        this.mIconActiveColor = new ColorStateList(new int[][]{new int[0]}, new int[]{getContext().getResources().getColor(R.color.sub_display_volume_progress_color, null)});
        this.mIconActiveBgColor = new ColorStateList(new int[][]{new int[0]}, new int[]{getContext().getResources().getColor(R.color.sub_display_volume_progress_bg_color, null)});
        this.mResources = getContext().getResources();
    }

    public final String getStreamLabel(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        String str;
        String smartViewLabel = volumePanelRow.getSmartViewLabel();
        if (!smartViewLabel.isEmpty()) {
            return smartViewLabel;
        }
        String remoteLabel = volumePanelRow.getRemoteLabel();
        if (volumePanelRow.isDynamic()) {
            return remoteLabel;
        }
        try {
            String nameRes = volumePanelRow.getNameRes();
            Resources resources = this.mResources;
            str = resources.getString(resources.getIdentifier(nameRes, null, null));
        } catch (Exception unused) {
            str = "";
        }
        if (volumePanelState.isRemoteMic()) {
            if (volumePanelRow.getStreamType() == 6) {
                str = getContext().getResources().getString(R.string.volume_amplify_ambient_sound_title);
            } else if (volumePanelRow.getStreamType() == 3 && !volumePanelState.isBtScoOn()) {
                str = getContext().getResources().getString(R.string.volume_amplify_ambient_sound_title);
            }
        }
        if (!remoteLabel.isEmpty()) {
            int streamType = volumePanelRow.getStreamType();
            if (streamType == 3 || streamType == 22 || streamType == 6 || streamType == 21) {
                return str + " (" + remoteLabel + ")";
            }
            return str;
        }
        return str;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        final VolumePanelState volumePanelState2 = volumePanelState;
        final int i = 0;
        final int i2 = 1;
        switch (AnonymousClass1.$SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                final int i3 = 2;
                volumePanelState2.getVolumeRowList().stream().filter(new Predicate(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda1
                    public final /* synthetic */ SubDisplayVolumeRowView f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i3) {
                            case 0:
                                SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                int i4 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView.mStream) {
                                    return true;
                                }
                                return false;
                            case 1:
                                SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView2.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView2.mStream) {
                                    return true;
                                }
                                return false;
                            case 2:
                                SubDisplayVolumeRowView subDisplayVolumeRowView3 = this.f$0;
                                int i6 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView3.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView3.mStream) {
                                    return true;
                                }
                                return false;
                            default:
                                SubDisplayVolumeRowView subDisplayVolumeRowView4 = this.f$0;
                                int i7 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView4.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView4.mStream) {
                                    return true;
                                }
                                return false;
                        }
                    }
                }).forEach(new Consumer(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda2
                    public final /* synthetic */ SubDisplayVolumeRowView f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i2) {
                            case 0:
                                SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                                int i4 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView.getClass();
                                int viewRealLevel = ViewLevelConverter.viewRealLevel(volumePanelRow);
                                if (volumePanelRow.isVisible()) {
                                    ObjectAnimator objectAnimator = subDisplayVolumeRowView.mProgressBarAnimator;
                                    if (objectAnimator == null || !objectAnimator.isRunning() || subDisplayVolumeRowView.mTargetProgressLevel != viewRealLevel) {
                                        ObjectAnimator objectAnimator2 = subDisplayVolumeRowView.mProgressBarAnimator;
                                        if (objectAnimator2 == null) {
                                            SeekBar seekBar = subDisplayVolumeRowView.mSeekBar;
                                            ObjectAnimator ofInt = ObjectAnimator.ofInt(seekBar, "progress", seekBar.getProgress(), viewRealLevel);
                                            subDisplayVolumeRowView.mProgressBarAnimator = ofInt;
                                            ofInt.setInterpolator(new DecelerateInterpolator());
                                        } else {
                                            objectAnimator2.cancel();
                                            subDisplayVolumeRowView.mProgressBarAnimator.setIntValues(subDisplayVolumeRowView.mSeekBar.getProgress(), viewRealLevel);
                                        }
                                        subDisplayVolumeRowView.mTargetProgressLevel = viewRealLevel;
                                        subDisplayVolumeRowView.mProgressBarAnimator.setDuration(80L);
                                        subDisplayVolumeRowView.mProgressBarAnimator.start();
                                        return;
                                    }
                                    return;
                                }
                                ObjectAnimator objectAnimator3 = subDisplayVolumeRowView.mProgressBarAnimator;
                                if (objectAnimator3 != null) {
                                    objectAnimator3.cancel();
                                }
                                subDisplayVolumeRowView.mSeekBar.setProgress(viewRealLevel);
                                return;
                            default:
                                SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView2.getClass();
                                int earProtectLevel = ((VolumePanelRow) obj).getEarProtectLevel();
                                if (earProtectLevel != subDisplayVolumeRowView2.mEarProtectLevel) {
                                    subDisplayVolumeRowView2.mEarProtectLevel = earProtectLevel;
                                    subDisplayVolumeRowView2.mSeekBar.semSetOverlapPointForDualColor(earProtectLevel);
                                    return;
                                }
                                return;
                        }
                    }
                });
                final int i4 = 3;
                volumePanelState2.getVolumeRowList().stream().filter(new Predicate(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda1
                    public final /* synthetic */ SubDisplayVolumeRowView f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i4) {
                            case 0:
                                SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                int i42 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView.mStream) {
                                    return true;
                                }
                                return false;
                            case 1:
                                SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView2.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView2.mStream) {
                                    return true;
                                }
                                return false;
                            case 2:
                                SubDisplayVolumeRowView subDisplayVolumeRowView3 = this.f$0;
                                int i6 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView3.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView3.mStream) {
                                    return true;
                                }
                                return false;
                            default:
                                SubDisplayVolumeRowView subDisplayVolumeRowView4 = this.f$0;
                                int i7 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView4.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView4.mStream) {
                                    return true;
                                }
                                return false;
                        }
                    }
                }).forEach(new Consumer(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda3
                    public final /* synthetic */ SubDisplayVolumeRowView f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i2) {
                            case 0:
                                SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                VolumePanelState volumePanelState3 = volumePanelState2;
                                int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView.getClass();
                                subDisplayVolumeRowView.updateContentDescription((VolumePanelRow) obj, volumePanelState3.isHasVibrator());
                                return;
                            default:
                                SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                VolumePanelState volumePanelState4 = volumePanelState2;
                                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                                int i6 = SubDisplayVolumeRowView.$r8$clinit;
                                String streamLabel = subDisplayVolumeRowView2.getStreamLabel(volumePanelState4, volumePanelRow);
                                if (streamLabel != null && !streamLabel.contentEquals(subDisplayVolumeRowView2.mLabelTextView.getText())) {
                                    subDisplayVolumeRowView2.mLabelTextView.setText(subDisplayVolumeRowView2.getStreamLabel(volumePanelState4, volumePanelRow));
                                    return;
                                }
                                return;
                        }
                    }
                });
                volumePanelState2.getVolumeRowList().stream().filter(new Predicate(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda1
                    public final /* synthetic */ SubDisplayVolumeRowView f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i2) {
                            case 0:
                                SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                int i42 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView.mStream) {
                                    return true;
                                }
                                return false;
                            case 1:
                                SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView2.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView2.mStream) {
                                    return true;
                                }
                                return false;
                            case 2:
                                SubDisplayVolumeRowView subDisplayVolumeRowView3 = this.f$0;
                                int i6 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView3.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView3.mStream) {
                                    return true;
                                }
                                return false;
                            default:
                                SubDisplayVolumeRowView subDisplayVolumeRowView4 = this.f$0;
                                int i7 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView4.getClass();
                                if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView4.mStream) {
                                    return true;
                                }
                                return false;
                        }
                    }
                }).forEach(new Consumer(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda3
                    public final /* synthetic */ SubDisplayVolumeRowView f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i) {
                            case 0:
                                SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                VolumePanelState volumePanelState3 = volumePanelState2;
                                int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                subDisplayVolumeRowView.getClass();
                                subDisplayVolumeRowView.updateContentDescription((VolumePanelRow) obj, volumePanelState3.isHasVibrator());
                                return;
                            default:
                                SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                VolumePanelState volumePanelState4 = volumePanelState2;
                                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                                int i6 = SubDisplayVolumeRowView.$r8$clinit;
                                String streamLabel = subDisplayVolumeRowView2.getStreamLabel(volumePanelState4, volumePanelRow);
                                if (streamLabel != null && !streamLabel.contentEquals(subDisplayVolumeRowView2.mLabelTextView.getText())) {
                                    subDisplayVolumeRowView2.mLabelTextView.setText(subDisplayVolumeRowView2.getStreamLabel(volumePanelState4, volumePanelRow));
                                    return;
                                }
                                return;
                        }
                    }
                });
                this.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(this.mStream).progress(this.mSeekBar.getProgress()).build(), true);
                return;
            case 2:
                if (this.mStream == volumePanelState2.getStream()) {
                    volumePanelState2.getVolumeRowList().stream().filter(new Predicate(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda1
                        public final /* synthetic */ SubDisplayVolumeRowView f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            switch (i) {
                                case 0:
                                    SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                    int i42 = SubDisplayVolumeRowView.$r8$clinit;
                                    subDisplayVolumeRowView.getClass();
                                    if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView.mStream) {
                                        return true;
                                    }
                                    return false;
                                case 1:
                                    SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                    int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                    subDisplayVolumeRowView2.getClass();
                                    if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView2.mStream) {
                                        return true;
                                    }
                                    return false;
                                case 2:
                                    SubDisplayVolumeRowView subDisplayVolumeRowView3 = this.f$0;
                                    int i6 = SubDisplayVolumeRowView.$r8$clinit;
                                    subDisplayVolumeRowView3.getClass();
                                    if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView3.mStream) {
                                        return true;
                                    }
                                    return false;
                                default:
                                    SubDisplayVolumeRowView subDisplayVolumeRowView4 = this.f$0;
                                    int i7 = SubDisplayVolumeRowView.$r8$clinit;
                                    subDisplayVolumeRowView4.getClass();
                                    if (((VolumePanelRow) obj).getStreamType() == subDisplayVolumeRowView4.mStream) {
                                        return true;
                                    }
                                    return false;
                            }
                        }
                    }).forEach(new Consumer(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView$$ExternalSyntheticLambda2
                        public final /* synthetic */ SubDisplayVolumeRowView f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            switch (i) {
                                case 0:
                                    SubDisplayVolumeRowView subDisplayVolumeRowView = this.f$0;
                                    VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                                    int i42 = SubDisplayVolumeRowView.$r8$clinit;
                                    subDisplayVolumeRowView.getClass();
                                    int viewRealLevel = ViewLevelConverter.viewRealLevel(volumePanelRow);
                                    if (volumePanelRow.isVisible()) {
                                        ObjectAnimator objectAnimator = subDisplayVolumeRowView.mProgressBarAnimator;
                                        if (objectAnimator == null || !objectAnimator.isRunning() || subDisplayVolumeRowView.mTargetProgressLevel != viewRealLevel) {
                                            ObjectAnimator objectAnimator2 = subDisplayVolumeRowView.mProgressBarAnimator;
                                            if (objectAnimator2 == null) {
                                                SeekBar seekBar = subDisplayVolumeRowView.mSeekBar;
                                                ObjectAnimator ofInt = ObjectAnimator.ofInt(seekBar, "progress", seekBar.getProgress(), viewRealLevel);
                                                subDisplayVolumeRowView.mProgressBarAnimator = ofInt;
                                                ofInt.setInterpolator(new DecelerateInterpolator());
                                            } else {
                                                objectAnimator2.cancel();
                                                subDisplayVolumeRowView.mProgressBarAnimator.setIntValues(subDisplayVolumeRowView.mSeekBar.getProgress(), viewRealLevel);
                                            }
                                            subDisplayVolumeRowView.mTargetProgressLevel = viewRealLevel;
                                            subDisplayVolumeRowView.mProgressBarAnimator.setDuration(80L);
                                            subDisplayVolumeRowView.mProgressBarAnimator.start();
                                            return;
                                        }
                                        return;
                                    }
                                    ObjectAnimator objectAnimator3 = subDisplayVolumeRowView.mProgressBarAnimator;
                                    if (objectAnimator3 != null) {
                                        objectAnimator3.cancel();
                                    }
                                    subDisplayVolumeRowView.mSeekBar.setProgress(viewRealLevel);
                                    return;
                                default:
                                    SubDisplayVolumeRowView subDisplayVolumeRowView2 = this.f$0;
                                    int i5 = SubDisplayVolumeRowView.$r8$clinit;
                                    subDisplayVolumeRowView2.getClass();
                                    int earProtectLevel = ((VolumePanelRow) obj).getEarProtectLevel();
                                    if (earProtectLevel != subDisplayVolumeRowView2.mEarProtectLevel) {
                                        subDisplayVolumeRowView2.mEarProtectLevel = earProtectLevel;
                                        subDisplayVolumeRowView2.mSeekBar.semSetOverlapPointForDualColor(earProtectLevel);
                                        return;
                                    }
                                    return;
                            }
                        }
                    });
                    return;
                }
                return;
            case 3:
                if (this.mStream == volumePanelState2.getStream()) {
                    this.mHandlerWrapper.remove(this.mRecheckCallback);
                    this.mHandlerWrapper.postDelayed(1000L, this.mRecheckCallback);
                    return;
                }
                return;
            case 4:
                if (this.mStream == volumePanelState2.getStream()) {
                    this.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(this.mStream).progress(this.mSeekBar.getProgress()).build(), true);
                    this.mHandlerWrapper.remove(this.mRecheckCallback);
                    this.mHandlerWrapper.postDelayed(1000L, this.mRecheckCallback);
                    return;
                }
                return;
            case 5:
                if (this.mStream == 22) {
                    setVisibility(0);
                    return;
                } else {
                    setVisibility(8);
                    return;
                }
            case 6:
                if (this.mStream == 22) {
                    setVisibility(8);
                    return;
                } else {
                    setVisibility(0);
                    return;
                }
            case 7:
                this.mStoreInteractor.dispose();
                return;
            default:
                return;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mStoreInteractor.dispose();
    }

    public final void updateContentDescription(VolumePanelRow volumePanelRow, boolean z) {
        String string;
        int iconType = volumePanelRow.getIconType();
        if (this.mStream == 2) {
            if (iconType == 0) {
                string = getContext().getString(R.string.volume_icon_content_description_ringtone_to_sound);
            } else if (z) {
                string = getContext().getString(R.string.volume_icon_content_description_ringtone_to_vib);
            } else {
                string = getContext().getString(R.string.volume_icon_content_description_ringtone_to_mute);
            }
        } else if (iconType != 1 && !volumePanelRow.isMuted() && volumePanelRow.getRealLevel() != 0) {
            string = getContext().getString(R.string.volume_icon_content_description_to_mute, this.mLabelTextView.getText());
        } else {
            string = getContext().getString(R.string.volume_icon_content_description_to_unmute, this.mLabelTextView.getText());
        }
        VolumeIcon volumeIcon = this.mIcon;
        if (volumeIcon != null) {
            volumeIcon.setContentDescription(string);
        } else {
            ((ImageButton) findViewById(R.id.volume_row_icon_with_label)).setContentDescription(string);
        }
    }
}
