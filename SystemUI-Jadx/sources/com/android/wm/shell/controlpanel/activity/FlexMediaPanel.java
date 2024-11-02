package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class FlexMediaPanel {
    public final SparseArray mActionViewIdMap;
    public final Context mContext;
    public TextView mCurrentText;
    public int mDisplayX;
    public int mDisplayY;
    public TextView mDurationText;
    public final LinearLayout mFloatingPanelView;
    public final H mHandler;
    public TextView mMediaArtistText;
    public MediaController mMediaController;
    public ImageView mMediaImageView;
    public LinearLayout mMediaNextButton;
    public MediaPanelPopup mMediaPanelPopup;
    public int mMediaPanelPopupType;
    public LinearLayout mMediaPauseButton;
    public LinearLayout mMediaPreviousButton;
    public LinearLayout mMediaResumeButton;
    public SeslSeekBar mMediaSeekBarView;
    public LinearLayout mMediaSeekNextButton;
    public LinearLayout mMediaSeekPreviousButton;
    public TextView mMediaTitleText;
    public PlaybackState mPlaybackState;
    public final AnonymousClass1 mSeekBarControlListener;
    public TextView mSeekBarText;
    public long mSeekPosition;
    public final FlexMediaPanel$$ExternalSyntheticLambda0 mUpdateTimer;
    public boolean mMetadataChanged = false;
    public boolean mSeekBarFromUser = false;
    public boolean mSeekBarEnabled = false;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class H extends Handler {
        public H() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                FlexMediaPanel flexMediaPanel = FlexMediaPanel.this;
                flexMediaPanel.mMediaPanelPopupType = 0;
                flexMediaPanel.mSeekPosition = 0L;
                MediaPanelPopup mediaPanelPopup = flexMediaPanel.mMediaPanelPopup;
                if (mediaPanelPopup != null) {
                    mediaPanelPopup.removeView();
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.appcompat.widget.SeslSeekBar$OnSeekBarChangeListener, com.android.wm.shell.controlpanel.activity.FlexMediaPanel$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda0] */
    public FlexMediaPanel(Context context, LinearLayout linearLayout, MediaController mediaController) {
        ?? r2 = new SeslSeekBar.OnSeekBarChangeListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel.1
            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
                if (z) {
                    Log.i("FlexMediaPanel", "MediaPanel mSeekBarControlListener onProgressChanged");
                    FlexMediaPanel flexMediaPanel = FlexMediaPanel.this;
                    flexMediaPanel.mSeekBarFromUser = true;
                    if (((AccessibilityManager) flexMediaPanel.mContext.getSystemService("accessibility")).semIsScreenReaderEnabled()) {
                        flexMediaPanel.mMediaController.getTransportControls().seekTo(seslSeekBar.getProgress());
                    }
                    flexMediaPanel.mSeekBarText.setText(FlexMediaPanel.makeCurrentText(seslSeekBar.getProgress()));
                    SeslSeekBar seslSeekBar2 = flexMediaPanel.mMediaSeekBarView;
                    int width = (seslSeekBar2.getWidth() - seslSeekBar2.getPaddingLeft()) - seslSeekBar2.getPaddingRight();
                    int progress = seslSeekBar2.getProgress() / 1000;
                    float max = ((width * progress) / (seslSeekBar2.getMax() / 1000)) + seslSeekBar2.getPaddingLeft() + seslSeekBar2.getLeft();
                    if (max - (flexMediaPanel.mSeekBarText.getWidth() / 2.0f) < 0.0f) {
                        flexMediaPanel.mSeekBarText.setX(0.0f);
                    } else if ((flexMediaPanel.mSeekBarText.getWidth() / 2) + max > ControlPanelUtils.getDisplayX(r6)) {
                        flexMediaPanel.mSeekBarText.setX(ControlPanelUtils.getDisplayX(r6) - flexMediaPanel.mSeekBarText.getWidth());
                    } else {
                        flexMediaPanel.mSeekBarText.setX(max - (r3.getWidth() / 2));
                    }
                }
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
                MediaMetadata metadata;
                Log.i("FlexMediaPanel", "MediaPanel mSeekBarControlListener onStartTrackingTouch");
                FlexMediaPanel flexMediaPanel = FlexMediaPanel.this;
                flexMediaPanel.mHandler.removeCallbacks(flexMediaPanel.mUpdateTimer);
                MediaController mediaController2 = flexMediaPanel.mMediaController;
                if (mediaController2 != null && (metadata = mediaController2.getMetadata()) != null) {
                    flexMediaPanel.mMediaSeekBarView.setMax((int) metadata.getLong("android.media.metadata.DURATION"));
                }
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
                Log.i("FlexMediaPanel", "MediaPanel mSeekBarControlListener onStopTrackingTouch : " + seslSeekBar.getProgress());
                FlexMediaPanel flexMediaPanel = FlexMediaPanel.this;
                MediaController mediaController2 = flexMediaPanel.mMediaController;
                if (mediaController2 == null) {
                    return;
                }
                mediaController2.getTransportControls().seekTo(seslSeekBar.getProgress());
                flexMediaPanel.checkPlaybackPosition(0L);
                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                    MediaController mediaController3 = flexMediaPanel.mMediaController;
                    HashMap hashMap = new HashMap();
                    hashMap.put("packageName", mediaController3.getPackageName());
                    ControlPanelUtils.eventLogging("F003", flexMediaPanel.mContext.getString(R.string.seekbar_sa_logging), hashMap);
                }
                flexMediaPanel.mSeekBarText.setText("");
            }
        };
        this.mSeekBarControlListener = r2;
        this.mUpdateTimer = new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FlexMediaPanel flexMediaPanel = FlexMediaPanel.this;
                if (flexMediaPanel.mSeekBarEnabled) {
                    flexMediaPanel.updateSeekbarPosition();
                }
                PlaybackState playbackState = flexMediaPanel.mPlaybackState;
                if (playbackState != null && playbackState.getState() == 3) {
                    Log.i("FlexMediaPanel", "MediaPanel mUpdateTimer PlaybackState.STATE_PLAYING");
                    flexMediaPanel.checkPlaybackPosition(1000L);
                } else {
                    Log.i("FlexMediaPanel", "MediaPanel mUpdateTimer else");
                    flexMediaPanel.mHandler.removeCallbacks(flexMediaPanel.mUpdateTimer);
                }
            }
        };
        SparseArray sparseArray = new SparseArray();
        this.mActionViewIdMap = sparseArray;
        sparseArray.put(R.id.action_skip_previous, 16L);
        sparseArray.put(R.id.action_skip_next, 32L);
        sparseArray.put(R.id.action_resume, 516L);
        sparseArray.put(R.id.action_pause, 514L);
        sparseArray.put(R.id.action_seek_previous, 256L);
        sparseArray.put(R.id.action_seek_next, 256L);
        this.mContext = context;
        this.mFloatingPanelView = linearLayout;
        this.mHandler = new H();
        this.mMediaController = mediaController;
        this.mMediaSeekBarView = (SeslSeekBar) linearLayout.findViewById(R.id.media_seekbar);
        this.mMediaResumeButton = (LinearLayout) linearLayout.findViewById(R.id.action_resume);
        this.mMediaPauseButton = (LinearLayout) linearLayout.findViewById(R.id.action_pause);
        this.mMediaPreviousButton = (LinearLayout) linearLayout.findViewById(R.id.action_skip_previous);
        this.mMediaNextButton = (LinearLayout) linearLayout.findViewById(R.id.action_skip_next);
        this.mMediaSeekBarView.mOnSeekBarChangeListener = r2;
        this.mMediaTitleText = (TextView) linearLayout.findViewById(R.id.title_text);
        this.mMediaArtistText = (TextView) linearLayout.findViewById(R.id.artist_text);
        this.mCurrentText = (TextView) linearLayout.findViewById(R.id.current_time);
        this.mDurationText = (TextView) linearLayout.findViewById(R.id.duration_time);
        this.mSeekBarText = (TextView) linearLayout.findViewById(R.id.seekbar_value);
        this.mMediaImageView = (ImageView) linearLayout.findViewById(R.id.media_image_view);
        this.mMediaSeekPreviousButton = (LinearLayout) linearLayout.findViewById(R.id.action_seek_previous);
        this.mMediaSeekNextButton = (LinearLayout) linearLayout.findViewById(R.id.action_seek_next);
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        this.mDisplayX = point.x;
        this.mDisplayY = point.y;
        if (ControlPanelUtils.isTypeFold()) {
            linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    FlexMediaPanel flexMediaPanel = FlexMediaPanel.this;
                    int dimensionPixelSize = flexMediaPanel.mContext.getResources().getDimensionPixelSize(R.dimen.basic_panel_top_margin) + ((int) ((flexMediaPanel.mDisplayY * 18.55d) / 100.0d));
                    flexMediaPanel.mFloatingPanelView.setY(dimensionPixelSize - (r5.getHeight() / 2));
                }
            });
            linearLayout.findViewById(R.id.seekbar_value_top_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 2.6d));
            linearLayout.findViewById(R.id.seekbar_value_bottom_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 1.1d));
            linearLayout.findViewById(R.id.media_panel_button_top_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 1.33d));
            this.mMediaResumeButton.setLayoutParams(getRatioLayoutParams(6.24d, 7.54d));
            this.mMediaPauseButton.setLayoutParams(getRatioLayoutParams(6.24d, 7.54d));
            this.mMediaPreviousButton.setLayoutParams(getRatioLayoutParams(3.77d, 4.55d));
            this.mMediaNextButton.setLayoutParams(getRatioLayoutParams(3.77d, 4.55d));
            this.mMediaSeekPreviousButton.setLayoutParams(getRatioLayoutParams(4.68d, 5.59d));
            this.mMediaSeekNextButton.setLayoutParams(getRatioLayoutParams(4.68d, 5.59d));
            linearLayout.findViewById(R.id.pause_image).setLayoutParams(getRatioLayoutParams(4.8d, 5.8d));
            linearLayout.findViewById(R.id.resume_image).setLayoutParams(getRatioLayoutParams(4.8d, 5.8d));
            linearLayout.findViewById(R.id.skip_image).setLayoutParams(getRatioLayoutParams(2.9d, 3.5d));
            linearLayout.findViewById(R.id.prev_image).setLayoutParams(getRatioLayoutParams(2.9d, 3.5d));
            linearLayout.findViewById(R.id.skip_image2).setLayoutParams(getRatioLayoutParams(3.6d, 4.3d));
            linearLayout.findViewById(R.id.prev_image2).setLayoutParams(getRatioLayoutParams(3.6d, 4.3d));
            linearLayout.findViewById(R.id.button_margin1).setLayoutParams(getRatioLayoutParams(5.525d, 0.0d));
            linearLayout.findViewById(R.id.button_margin2).setLayoutParams(getRatioLayoutParams(5.345d, 0.0d));
            linearLayout.findViewById(R.id.button_margin3).setLayoutParams(getRatioLayoutParams(5.345d, 0.0d));
            linearLayout.findViewById(R.id.button_margin4).setLayoutParams(getRatioLayoutParams(5.525d, 0.0d));
        } else {
            ControlPanelUtils.setRatioPadding(context, this.mMediaSeekBarView, 8.35d, 0.0d, 8.35d, 0.0d);
            linearLayout.findViewById(R.id.media_text_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 0.5d));
            linearLayout.findViewById(R.id.seekbar_value_top_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 3.1d));
            linearLayout.findViewById(R.id.seekbar_value_bottom_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 1.1d));
            linearLayout.findViewById(R.id.media_panel_button_top_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 2.7d));
            this.mMediaResumeButton.setLayoutParams(getRatioLayoutParams(14.43d, 5.98d));
            this.mMediaPauseButton.setLayoutParams(getRatioLayoutParams(14.43d, 5.98d));
            this.mMediaPreviousButton.setLayoutParams(getRatioLayoutParams(10.14d, 4.16d));
            this.mMediaNextButton.setLayoutParams(getRatioLayoutParams(10.14d, 4.16d));
            this.mMediaSeekPreviousButton.setLayoutParams(getRatioLayoutParams(10.79d, 4.42d));
            this.mMediaSeekNextButton.setLayoutParams(getRatioLayoutParams(10.79d, 4.42d));
            linearLayout.findViewById(R.id.pause_image).setLayoutParams(getRatioLayoutParams(11.1d, 4.6d));
            linearLayout.findViewById(R.id.resume_image).setLayoutParams(getRatioLayoutParams(11.1d, 4.6d));
            linearLayout.findViewById(R.id.skip_image).setLayoutParams(getRatioLayoutParams(7.8d, 3.2d));
            linearLayout.findViewById(R.id.prev_image).setLayoutParams(getRatioLayoutParams(7.8d, 3.2d));
            linearLayout.findViewById(R.id.skip_image2).setLayoutParams(getRatioLayoutParams(8.3d, 3.4d));
            linearLayout.findViewById(R.id.prev_image2).setLayoutParams(getRatioLayoutParams(8.3d, 3.4d));
            linearLayout.findViewById(R.id.button_margin1).setLayoutParams(getRatioLayoutParams(7.585d, 0.0d));
            linearLayout.findViewById(R.id.button_margin2).setLayoutParams(getRatioLayoutParams(7.165d, 0.0d));
            linearLayout.findViewById(R.id.button_margin3).setLayoutParams(getRatioLayoutParams(7.165d, 0.0d));
            linearLayout.findViewById(R.id.button_margin4).setLayoutParams(getRatioLayoutParams(7.585d, 0.0d));
        }
        setupButtons();
    }

    public static String makeCurrentText(int i) {
        String str;
        String str2;
        String valueOf;
        int i2;
        int i3 = i / 1000;
        int i4 = i3 / 3600;
        if (i4 == 0) {
            str = "";
        } else {
            str = i4 + ":";
        }
        if (!str.equals("") && (i2 = (i3 / 60) % 60) < 10) {
            str2 = LocaleListCompatWrapper$$ExternalSyntheticOutline0.m(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, i2, ":");
        } else {
            str2 = ((i3 / 60) % 60) + ":";
        }
        int i5 = i3 % 60;
        if (i5 < 10) {
            valueOf = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, i5);
        } else {
            valueOf = String.valueOf(i5);
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, str2, valueOf);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String makeDurationText(long r11) {
        /*
            r0 = 1000(0x3e8, double:4.94E-321)
            long r11 = r11 / r0
            r0 = 3600(0xe10, double:1.7786E-320)
            long r0 = r11 / r0
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            java.lang.String r3 = ""
            java.lang.String r4 = ":"
            if (r2 == 0) goto L21
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r4)
            java.lang.String r0 = r2.toString()
            goto L22
        L21:
            r0 = r3
        L22:
            boolean r1 = r0.equals(r3)
            java.lang.String r2 = "0"
            r5 = 10
            r7 = 60
            if (r1 != 0) goto L3b
            long r9 = r11 / r7
            long r9 = r9 % r7
            int r1 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r1 >= 0) goto L3b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r2)
            goto L43
        L3b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            long r9 = r11 / r7
            long r9 = r9 % r7
        L43:
            r1.append(r9)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            long r11 = r11 % r7
            int r3 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r3 >= 0) goto L57
            java.lang.String r11 = androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0.m(r2, r11)
            goto L5b
        L57:
            java.lang.String r11 = java.lang.String.valueOf(r11)
        L5b:
            java.lang.String r11 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r0, r1, r11)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.controlpanel.activity.FlexMediaPanel.makeDurationText(long):java.lang.String");
    }

    public final void checkPlaybackPosition(long j) {
        Log.i("FlexMediaPanel", "MediaPanel checkPlaybackPosition");
        H h = this.mHandler;
        FlexMediaPanel$$ExternalSyntheticLambda0 flexMediaPanel$$ExternalSyntheticLambda0 = this.mUpdateTimer;
        if (!h.hasCallbacks(flexMediaPanel$$ExternalSyntheticLambda0)) {
            h.postDelayed(flexMediaPanel$$ExternalSyntheticLambda0, j);
        }
    }

    public final void clearController() {
        Log.i("FlexMediaPanel", "MediaPanel clearController");
        if (this.mMediaController != null) {
            this.mMediaController = null;
        }
        this.mHandler.removeCallbacks(this.mUpdateTimer);
    }

    public final LinearLayout.LayoutParams getRatioLayoutParams(double d, double d2) {
        return new LinearLayout.LayoutParams((int) ((this.mDisplayX * d) / 100.0d), (int) ((this.mDisplayY * d2) / 100.0d));
    }

    public final void onClickButton(int i, int i2) {
        MediaController mediaController = this.mMediaController;
        if (mediaController != null && mediaController.getPlaybackState() != null) {
            this.mPlaybackState = this.mMediaController.getPlaybackState();
            StringBuilder sb = new StringBuilder("MediaPanel onClick mMediaPauseButton mMediaController : ");
            sb.append(this.mMediaController.getPackageName());
            sb.append(", logging : ");
            Context context = this.mContext;
            sb.append(context.getResources().getString(i2));
            sb.append(", mPlaybackState : ");
            sb.append(this.mPlaybackState);
            Log.i("FlexMediaPanel", sb.toString());
            if (i == R.id.action_skip_previous) {
                this.mMediaController.getTransportControls().skipToPrevious();
            } else if (i == R.id.action_skip_next) {
                this.mMediaController.getTransportControls().skipToNext();
            } else if (i == R.id.action_resume) {
                this.mMediaController.getTransportControls().play();
            } else if (i == R.id.action_pause) {
                this.mMediaController.getTransportControls().pause();
            } else if (i == R.id.action_seek_next) {
                MediaMetadata metadata = this.mMediaController.getMetadata();
                if (this.mSeekBarEnabled && metadata != null) {
                    long position = this.mPlaybackState.getPosition();
                    long j = metadata.getLong("android.media.metadata.DURATION");
                    long j2 = this.mSeekPosition;
                    if (j2 >= 0) {
                        long j3 = position + 10000;
                        if (j3 < j) {
                            this.mSeekPosition = j2 + 10;
                            if (j3 > j) {
                                this.mMediaController.getTransportControls().seekTo(j);
                            } else {
                                this.mMediaController.getTransportControls().seekTo(this.mPlaybackState.getPosition() + 10000);
                            }
                            onMediaPanelPopupShow(1);
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else if (i == R.id.action_seek_previous) {
                long position2 = this.mPlaybackState.getPosition();
                long j4 = this.mSeekPosition;
                if (j4 <= 0) {
                    long j5 = position2 - 10000;
                    if (j5 > 0) {
                        this.mSeekPosition = j4 - 10;
                        if (j5 < 0) {
                            this.mMediaController.getTransportControls().seekTo(0L);
                        } else {
                            this.mMediaController.getTransportControls().seekTo(this.mPlaybackState.getPosition() - 10000);
                        }
                        onMediaPanelPopupShow(2);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                MediaController mediaController2 = this.mMediaController;
                HashMap hashMap = new HashMap();
                hashMap.put("packageName", mediaController2.getPackageName());
                ControlPanelUtils.eventLogging("F003", context.getString(i2), hashMap);
                return;
            }
            return;
        }
        Log.e("FlexMediaPanel", "MediaPanel onClickButton mMediaController or getPlaybackState == null");
    }

    public final void onMediaPanelPopupShow(int i) {
        boolean z;
        int i2;
        int i3 = this.mMediaPanelPopupType;
        Context context = this.mContext;
        if (i3 != i || this.mMediaPanelPopup == null) {
            MediaPanelPopup mediaPanelPopup = this.mMediaPanelPopup;
            if (mediaPanelPopup != null) {
                mediaPanelPopup.removeView();
            }
            if (i == 1) {
                z = true;
            } else {
                z = false;
            }
            MediaPanelPopup mediaPanelPopup2 = new MediaPanelPopup(context, z);
            this.mMediaPanelPopup = mediaPanelPopup2;
            mediaPanelPopup2.showView();
            this.mMediaPanelPopup.mLottieAnimationView.playAnimation();
        }
        H h = this.mHandler;
        h.removeMessages(1);
        this.mMediaPanelPopupType = i;
        if (i == 1) {
            i2 = (int) this.mSeekPosition;
        } else {
            i2 = ((int) this.mSeekPosition) * (-1);
        }
        this.mMediaPanelPopup.mSeekTextView.setText(context.getResources().getQuantityString(R.plurals.cross_fade_sec, i2, Integer.valueOf(i2)));
        h.sendEmptyMessageDelayed(1, 700L);
    }

    public final void setupButtons() {
        Log.i("FlexMediaPanel", "MediaPanel setupButtons");
        final int i = 0;
        this.mMediaPauseButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda2
            public final /* synthetic */ FlexMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        return;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        return;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        return;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        return;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        return;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        return;
                }
            }
        });
        final int i2 = 1;
        this.mMediaResumeButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda2
            public final /* synthetic */ FlexMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        return;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        return;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        return;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        return;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        return;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        return;
                }
            }
        });
        final int i3 = 2;
        this.mMediaPreviousButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda2
            public final /* synthetic */ FlexMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i3) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        return;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        return;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        return;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        return;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        return;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        return;
                }
            }
        });
        final int i4 = 3;
        this.mMediaNextButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda2
            public final /* synthetic */ FlexMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i4) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        return;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        return;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        return;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        return;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        return;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        return;
                }
            }
        });
        final int i5 = 4;
        this.mMediaSeekPreviousButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda2
            public final /* synthetic */ FlexMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i5) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        return;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        return;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        return;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        return;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        return;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        return;
                }
            }
        });
        final int i6 = 5;
        this.mMediaSeekNextButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexMediaPanel$$ExternalSyntheticLambda2
            public final /* synthetic */ FlexMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i6) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        return;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        return;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        return;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        return;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        return;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        return;
                }
            }
        });
    }

    public final void updateImmersiveState(boolean z) {
        TextView textView = this.mMediaTitleText;
        if (textView == null) {
            return;
        }
        if (!z && textView.getEllipsize() == TextUtils.TruncateAt.END) {
            this.mMediaTitleText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else if (z && this.mMediaTitleText.getEllipsize() == TextUtils.TruncateAt.MARQUEE) {
            this.mMediaTitleText.setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    public final void updateMediaPanel() {
        boolean z;
        boolean z2;
        boolean z3;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            Log.e("FlexMediaPanel", "MediaPanel updateMediaPanel mMediaController == null");
            return;
        }
        PlaybackState playbackState = mediaController.getPlaybackState();
        this.mPlaybackState = playbackState;
        if (playbackState == null) {
            Log.e("FlexMediaPanel", "MediaPanel updateMediaPanel getPlaybackState == null");
            return;
        }
        Log.i("FlexMediaPanel", "MediaPanel updateMediaPanel playbackState.getState : " + this.mPlaybackState.getState() + ", mPlaybackState.getPosition : " + this.mPlaybackState.getPosition());
        MediaMetadata metadata = this.mMediaController.getMetadata();
        Context context = this.mContext;
        if (metadata != null) {
            this.mMediaArtistText.setText(metadata.getString("android.media.metadata.ARTIST"));
            Bitmap bitmap = metadata.getBitmap("android.media.metadata.ALBUM_ART");
            if (bitmap != null) {
                this.mMediaImageView.setImageBitmap(bitmap);
                this.mMediaImageView.setClipToOutline(true);
            } else {
                this.mMediaImageView.setImageDrawable(context.getDrawable(R.drawable.default_media_image));
            }
            String string = metadata.getString("android.media.metadata.TITLE");
            if (string == null) {
                string = metadata.getString("android.media.metadata.DISPLAY_TITLE");
            }
            if (!this.mMediaTitleText.getText().equals(string)) {
                this.mMediaTitleText.setText(string);
                this.mMediaTitleText.setSingleLine(true);
                this.mMediaTitleText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                this.mMediaTitleText.setSelected(true);
            }
        }
        long actions = this.mPlaybackState.getActions();
        long j = 0;
        LinearLayout linearLayout = this.mFloatingPanelView;
        if (linearLayout != null) {
            SparseArray sparseArray = this.mActionViewIdMap;
            int size = sparseArray.size();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < size) {
                int keyAt = sparseArray.keyAt(i);
                long longValue = ((Long) sparseArray.valueAt(i)).longValue();
                LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(keyAt);
                long j2 = actions & longValue;
                if (j2 != j) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                linearLayout2.setEnabled(z2);
                if (j2 != j) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    linearLayout2.setAlpha(1.0f);
                    arrayList.add(linearLayout2.getContentDescription());
                } else {
                    linearLayout2.setAlpha(0.4f);
                }
                i++;
                j = 0;
            }
            Log.i("FlexMediaPanel", "action count : " + size + " enable buttons : " + arrayList);
            MediaMetadata metadata2 = this.mMediaController.getMetadata();
            if (metadata2 != null) {
                updateSeekButton((int) this.mPlaybackState.getPosition(), metadata2.getLong("android.media.metadata.DURATION"));
            }
        }
        try {
            if (this.mPlaybackState.getState() == 0) {
                this.mMediaPauseButton.setVisibility(8);
                this.mMediaResumeButton.setVisibility(0);
                this.mMediaResumeButton.setAlpha(0.4f);
            } else {
                if (this.mPlaybackState.getState() != 3 && this.mPlaybackState.getState() != 6) {
                    this.mMediaPauseButton.setVisibility(8);
                    this.mMediaResumeButton.setVisibility(0);
                }
                this.mMediaResumeButton.setVisibility(8);
                this.mMediaPauseButton.setVisibility(0);
            }
        } catch (NullPointerException unused) {
            Log.e("FlexMediaPanel", "MediaPanel updatePlayPauseIcon mPlaybackState.getState is null");
        }
        MediaMetadata metadata3 = this.mMediaController.getMetadata();
        if ((this.mPlaybackState.getActions() & 256) != 0) {
            z = true;
        } else {
            z = false;
        }
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m("updateSeekbarInfo isSupportSeekBar : ", z, "FlexMediaPanel");
        if (metadata3 != null && z) {
            long j3 = metadata3.getLong("android.media.metadata.DURATION");
            int position = (int) this.mPlaybackState.getPosition();
            if (j3 > 0) {
                this.mCurrentText.setText(makeCurrentText(position));
                this.mDurationText.setText(makeDurationText(j3));
            } else {
                this.mCurrentText.setText("");
                this.mDurationText.setText("");
            }
            Log.i("FlexMediaPanel", "MediaPanel updateSeekbarInfo duration : " + j3);
            if (j3 <= 100) {
                Log.i("FlexMediaPanel", "MediaPanel updateSeekbarInfo hide seekbar");
                this.mSeekBarEnabled = false;
                this.mMediaSeekBarView.setThumbTintList(ContextCompat.getColorStateList(R.color.seekbar_thumb_disable, context));
                this.mMediaSeekBarView.setEnabled(false);
                this.mMediaSeekBarView.setProgress(0);
            } else {
                this.mSeekBarEnabled = true;
                this.mMediaSeekBarView.setThumbTintList(ContextCompat.getColorStateList(R.color.seekbar_color_expand, context));
                this.mMediaSeekBarView.setEnabled(true);
                this.mMediaSeekBarView.setMax((int) j3);
                if (this.mMetadataChanged) {
                    this.mMediaSeekBarView.setProgress(0);
                    this.mMetadataChanged = false;
                }
                updateSeekbarPosition();
            }
            PlaybackState playbackState2 = this.mPlaybackState;
            if (playbackState2 != null && playbackState2.getState() == 3) {
                checkPlaybackPosition(0L);
            }
        } else {
            Log.i("FlexMediaPanel", "MediaFloatingUI updateSeekbarInfo mediaMetadata is null");
            this.mSeekBarEnabled = false;
            this.mMediaSeekBarView.setThumbTintList(ContextCompat.getColorStateList(R.color.seekbar_thumb_disable, context));
            this.mMediaSeekBarView.setEnabled(false);
            this.mMediaSeekBarView.setProgress(0);
        }
        setupButtons();
    }

    public final void updateSeekButton(int i, long j) {
        LinearLayout linearLayout = this.mFloatingPanelView;
        LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(R.id.action_seek_previous);
        if (i > 100 && j != 0 && this.mSeekBarEnabled) {
            linearLayout2.setAlpha(1.0f);
        } else {
            linearLayout2.setAlpha(0.4f);
        }
        LinearLayout linearLayout3 = (LinearLayout) linearLayout.findViewById(R.id.action_seek_next);
        if (i + 1000 < j && j != 0 && this.mSeekBarEnabled) {
            linearLayout3.setAlpha(1.0f);
        } else {
            linearLayout3.setAlpha(0.4f);
        }
    }

    public final void updateSeekbarPosition() {
        MediaMetadata metadata = this.mMediaController.getMetadata();
        if (metadata == null) {
            return;
        }
        this.mPlaybackState = this.mMediaController.getPlaybackState();
        StringBuilder sb = new StringBuilder("MediaPanel updateSeekbarPosition mMediaController : ");
        sb.append(this.mMediaController.getPackageName());
        sb.append(", mPlaybackState : ");
        sb.append(this.mPlaybackState);
        sb.append(", mSeekbarFromUser : ");
        NotificationListener$$ExternalSyntheticOutline0.m(sb, this.mSeekBarFromUser, "FlexMediaPanel");
        PlaybackState playbackState = this.mPlaybackState;
        if (playbackState != null && !this.mSeekBarFromUser) {
            int position = (int) playbackState.getPosition();
            int i = position / 1000;
            int max = this.mMediaSeekBarView.getMax();
            long j = metadata.getLong("android.media.metadata.DURATION");
            Log.i("FlexMediaPanel", "MediaPanel updateSeekbarPosition mPlaybackState.getState() : " + this.mPlaybackState.getState() + ", currentPosition : " + position + ", current : " + i + ", max : " + max);
            if (position > max) {
                this.mMediaSeekBarView.setProgress(i);
            } else {
                if (j > 0) {
                    this.mCurrentText.setText(makeCurrentText(position));
                    this.mDurationText.setText(makeDurationText(j));
                } else {
                    this.mCurrentText.setText("");
                    this.mDurationText.setText("");
                }
                this.mMediaSeekBarView.setProgress(position);
            }
            updateSeekButton(position, j);
        }
        this.mSeekBarFromUser = false;
    }
}
