package com.android.systemui.volume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import com.android.systemui.volume.store.StoreInteractor;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class VolumeSeekBar extends SeekBar implements VolumeObserver<VolumePanelState> {
    public int currentProgress;
    public boolean enabled;
    public boolean isTracking;
    public final float scaledTouchSlop;
    public SeekBar.OnSeekBarChangeListener seekbarChangeListener;
    public final StoreInteractor storeInteractor;
    public int stream;
    public float touchedX;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class VolumeSeekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public VolumeSeekbarChangeListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            VolumeSeekBar volumeSeekBar = VolumeSeekBar.this;
            if (volumeSeekBar.isTracking || z) {
                volumeSeekBar.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(VolumeSeekBar.this.stream).progress(i).isFromOutside(true).build(), false);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            VolumeSeekBar.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_START_SLIDER_TRACKING).stream(VolumeSeekBar.this.stream).isFromOutside(true).build(), false);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            VolumeSeekBar.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STOP_SLIDER_TRACKING).stream(VolumeSeekBar.this.stream).isFromOutside(true).build(), true);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public VolumeSeekBar(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        this.stream = -1;
        this.enabled = true;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        if (WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()] == 1) {
            this.storeInteractor.dispose();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.storeInteractor.dispose();
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.enabled) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action != 2) {
                    if (action != 3) {
                        return super.onTouchEvent(motionEvent);
                    }
                } else {
                    float x = motionEvent.getX() - this.touchedX;
                    if (this.isTracking) {
                        trackTouchEvent(x);
                    } else if (Math.abs(x) > this.scaledTouchSlop) {
                        trackTouchEvent(x);
                        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEEKBAR_START_PROGRESS).stream(this.stream).build(), false);
                    }
                    return true;
                }
            }
            this.isTracking = false;
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.seekbarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onStopTrackingTouch(this);
            }
            this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_UP).stream(this.stream).build(), true);
            return true;
        }
        this.touchedX = motionEvent.getX();
        this.currentProgress = getProgress();
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = this.seekbarChangeListener;
        if (onSeekBarChangeListener2 != null) {
            onSeekBarChangeListener2.onStartTrackingTouch(this);
        }
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_DOWN).stream(this.stream).build(), false);
        return true;
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        float f;
        this.enabled = z;
        if (z) {
            f = 1.0f;
        } else {
            f = 0.7f;
        }
        setAlpha(f);
    }

    @Override // android.widget.SeekBar
    public final void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.seekbarChangeListener = onSeekBarChangeListener;
        super.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    public final void trackTouchEvent(float f) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        this.isTracking = true;
        setProgress(Math.round(((f / ((getWidth() - getPaddingLeft()) - getPaddingRight())) * (getMax() - getMin())) + this.currentProgress));
    }

    public VolumeSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.stream = -1;
        this.enabled = true;
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
        setOnSeekBarChangeListener(new VolumeSeekbarChangeListener());
    }
}
