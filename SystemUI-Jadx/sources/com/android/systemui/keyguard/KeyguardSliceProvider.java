package com.android.systemui.keyguard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.Display;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceProvider;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.slice.builders.impl.TemplateBuilderImpl;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.R;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.systemui.util.wakelock.WakeLockLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class KeyguardSliceProvider extends SliceProvider implements NextAlarmController.NextAlarmChangeCallback, ZenModeController.Callback, NotificationMediaManager.MediaListener, StatusBarStateController.StateListener, SystemUIAppComponentFactoryBase.ContextInitializer {
    static final int ALARM_VISIBILITY_HOURS = 12;
    public static KeyguardSliceProvider sInstance;
    public static final Object sInstanceLock;
    public AlarmManager mAlarmManager;
    public ContentResolver mContentResolver;
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback mContextAvailableCallback;
    public DateFormat mDateFormat;
    public String mDatePattern;
    public DozeParameters mDozeParameters;
    public boolean mDozing;
    public KeyguardBypassController mKeyguardBypassController;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public String mLastText;
    public CharSequence mMediaArtist;
    public boolean mMediaIsVisible;
    public NotificationMediaManager mMediaManager;
    public CharSequence mMediaTitle;
    protected SettableWakeLock mMediaWakeLock;
    public String mNextAlarm;
    public NextAlarmController mNextAlarmController;
    public AlarmManager.AlarmClockInfo mNextAlarmInfo;
    public PendingIntent mPendingIntent;
    public boolean mRegistered;
    public int mStatusBarState;
    public StatusBarStateController mStatusBarStateController;
    public UserTracker mUserTracker;
    public WakeLockLogger mWakeLockLogger;
    public ZenModeController mZenModeController;
    public final Date mCurrentTime = new Date();
    public final KeyguardSliceProvider$$ExternalSyntheticLambda1 mUpdateNextAlarm = new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            KeyguardSliceProvider.this.updateNextAlarm();
        }
    };
    final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.DATE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.updateClockLocked();
                }
            } else if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.cleanDateFormatLocked();
                }
            }
        }
    };
    final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider.2
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTimeChanged() {
            synchronized (this) {
                KeyguardSliceProvider.this.updateClockLocked();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTimeZoneChanged(TimeZone timeZone) {
            synchronized (this) {
                KeyguardSliceProvider.this.cleanDateFormatLocked();
            }
        }
    };
    public final Handler mHandler = new Handler();
    public final Handler mMediaHandler = new Handler();
    public final Uri mSliceUri = Uri.parse("content://com.android.systemui.keyguard/main");
    public final Uri mHeaderUri = Uri.parse("content://com.android.systemui.keyguard/header");
    public final Uri mDateUri = Uri.parse("content://com.android.systemui.keyguard/date");
    public final Uri mAlarmUri = Uri.parse("content://com.android.systemui.keyguard/alarm");
    public final Uri mDndUri = Uri.parse("content://com.android.systemui.keyguard/dnd");
    public final Uri mMediaUri = Uri.parse("content://com.android.systemui.keyguard/media");

    static {
        new StyleSpan(1);
        sInstanceLock = new Object();
    }

    public final void addMediaLocked(ListBuilder listBuilder) {
        Icon icon;
        String str;
        if (TextUtils.isEmpty(this.mMediaTitle)) {
            return;
        }
        ListBuilder.HeaderBuilder headerBuilder = new ListBuilder.HeaderBuilder(this.mHeaderUri);
        headerBuilder.mTitle = this.mMediaTitle;
        final int i = 0;
        headerBuilder.mTitleLoading = false;
        listBuilder.mImpl.setHeader(headerBuilder);
        if (!TextUtils.isEmpty(this.mMediaArtist)) {
            ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mMediaUri);
            rowBuilder.mTitle = this.mMediaArtist;
            rowBuilder.mTitleLoading = false;
            NotificationMediaManager notificationMediaManager = this.mMediaManager;
            IconCompat iconCompat = null;
            if (notificationMediaManager == null || (str = notificationMediaManager.mMediaNotificationKey) == null) {
                icon = null;
            } else {
                Optional map = Optional.ofNullable(notificationMediaManager.mNotifPipeline.getEntry(str)).map(new Function() { // from class: com.android.systemui.statusbar.NotificationMediaManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        switch (i) {
                            case 0:
                                return ((NotificationEntry) obj).mIcons.mShelfIcon;
                            case 1:
                                return ((StatusBarIconView) obj).mIcon.icon;
                            default:
                                return ((Display) obj).getUniqueId();
                        }
                    }
                });
                final int i2 = 1;
                icon = (Icon) map.map(new Function() { // from class: com.android.systemui.statusbar.NotificationMediaManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        switch (i2) {
                            case 0:
                                return ((NotificationEntry) obj).mIcons.mShelfIcon;
                            case 1:
                                return ((StatusBarIconView) obj).mIcon.icon;
                            default:
                                return ((Display) obj).getUniqueId();
                        }
                    }
                }).orElse(null);
            }
            if (icon != null) {
                iconCompat = IconCompat.createFromIcon(getContext(), icon);
            }
            if (iconCompat != null) {
                rowBuilder.addEndItem(iconCompat);
            }
            listBuilder.mImpl.addRow(rowBuilder);
        }
    }

    public void cleanDateFormatLocked() {
        this.mDateFormat = null;
    }

    public boolean isRegistered() {
        boolean z;
        synchronized (this) {
            z = this.mRegistered;
        }
        return z;
    }

    public final boolean needsMediaLocked() {
        boolean z;
        boolean z2;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        if (keyguardBypassController != null && keyguardBypassController.getBypassEnabled() && this.mDozeParameters.getAlwaysOn()) {
            z = true;
        } else {
            z = false;
        }
        if (this.mStatusBarState == 0 && this.mMediaIsVisible) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!TextUtils.isEmpty(this.mMediaTitle) && this.mMediaIsVisible && (this.mDozing || z || z2)) {
            return true;
        }
        return false;
    }

    public final void notifyChange() {
        this.mContentResolver.notifyChange(this.mSliceUri, null);
    }

    @Override // androidx.slice.SliceProvider
    public final Slice onBindSlice() {
        boolean z;
        Slice build;
        Trace.beginSection("KeyguardSliceProvider#onBindSlice");
        synchronized (this) {
            ListBuilder listBuilder = new ListBuilder(getContext(), this.mSliceUri, -1L);
            if (needsMediaLocked()) {
                addMediaLocked(listBuilder);
            } else {
                ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mDateUri);
                rowBuilder.mTitle = this.mLastText;
                rowBuilder.mTitleLoading = false;
                listBuilder.mImpl.addRow(rowBuilder);
            }
            if (!TextUtils.isEmpty(this.mNextAlarm)) {
                IconCompat createWithResource = IconCompat.createWithResource(R.drawable.ic_access_alarms_big, getContext());
                ListBuilder.RowBuilder rowBuilder2 = new ListBuilder.RowBuilder(this.mAlarmUri);
                rowBuilder2.mTitle = this.mNextAlarm;
                rowBuilder2.mTitleLoading = false;
                rowBuilder2.addEndItem(createWithResource);
                listBuilder.mImpl.addRow(rowBuilder2);
            }
            if (((ZenModeControllerImpl) this.mZenModeController).mZenMode != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                ListBuilder.RowBuilder rowBuilder3 = new ListBuilder.RowBuilder(this.mDndUri);
                rowBuilder3.mContentDescription = getContext().getResources().getString(R.string.accessibility_quick_settings_dnd);
                rowBuilder3.addEndItem(IconCompat.createWithResource(R.drawable.stat_sys_dnd, getContext()));
                listBuilder.mImpl.addRow(rowBuilder3);
            }
            SliceAction sliceAction = new SliceAction(this.mPendingIntent, IconCompat.createWithResource(R.drawable.ic_access_alarms_big, getContext()), 0, this.mLastText);
            sliceAction.mSliceAction.mIsActivity = true;
            ListBuilder.RowBuilder rowBuilder4 = new ListBuilder.RowBuilder(Uri.parse("content://com.android.systemui.keyguard/action"));
            rowBuilder4.mPrimaryAction = sliceAction;
            listBuilder.mImpl.addRow(rowBuilder4);
            build = ((TemplateBuilderImpl) listBuilder.mImpl).build();
        }
        Trace.endSection();
        return build;
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onConfigChanged$1() {
        notifyChange();
    }

    @Override // androidx.slice.SliceProvider
    public final void onCreateSliceProvider() {
        PlaybackState playbackState;
        this.mContextAvailableCallback.onContextAvailable(getContext());
        this.mMediaWakeLock = new SettableWakeLock(WakeLock.createPartial(getContext(), this.mWakeLockLogger, "media"), "media");
        synchronized (sInstanceLock) {
            KeyguardSliceProvider keyguardSliceProvider = sInstance;
            if (keyguardSliceProvider != null) {
                keyguardSliceProvider.onDestroy();
            }
            this.mDatePattern = getContext().getString(R.string.system_ui_aod_date_pattern);
            int i = 0;
            this.mPendingIntent = PendingIntent.getActivity(getContext(), 0, new Intent(getContext(), (Class<?>) KeyguardSliceProvider.class), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
            NotificationMediaManager notificationMediaManager = this.mMediaManager;
            notificationMediaManager.mMediaListeners.add(this);
            MediaMetadata mediaMetadata = notificationMediaManager.mMediaMetadata;
            MediaController mediaController = notificationMediaManager.mMediaController;
            if (mediaController != null && (playbackState = mediaController.getPlaybackState()) != null) {
                i = playbackState.getState();
            }
            onPrimaryMetadataOrStateChanged(mediaMetadata, i);
            this.mStatusBarStateController.addCallback(this);
            ((NextAlarmControllerImpl) this.mNextAlarmController).addCallback(this);
            ((ZenModeControllerImpl) this.mZenModeController).addCallback(this);
            sInstance = this;
            registerClockUpdate();
            updateClockLocked();
        }
    }

    public void onDestroy() {
        synchronized (sInstanceLock) {
            ((NextAlarmControllerImpl) this.mNextAlarmController).removeCallback(this);
            ((ZenModeControllerImpl) this.mZenModeController).removeCallback(this);
            this.mMediaWakeLock.setAcquired(false);
            this.mAlarmManager.cancel(this.mUpdateNextAlarm);
            if (this.mRegistered) {
                this.mRegistered = false;
                this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
                getContext().unregisterReceiver(this.mIntentReceiver);
            }
            sInstance = null;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        boolean z2;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mDozing = z;
            if (needsMediaLocked != needsMediaLocked()) {
                z2 = true;
            } else {
                z2 = false;
            }
        }
        if (z2) {
            notifyChange();
        }
    }

    @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        long triggerTime;
        synchronized (this) {
            this.mNextAlarmInfo = alarmClockInfo;
            this.mAlarmManager.cancel(this.mUpdateNextAlarm);
            AlarmManager.AlarmClockInfo alarmClockInfo2 = this.mNextAlarmInfo;
            if (alarmClockInfo2 == null) {
                triggerTime = -1;
            } else {
                triggerTime = alarmClockInfo2.getTriggerTime() - TimeUnit.HOURS.toMillis(12L);
            }
            long j = triggerTime;
            if (j > 0) {
                this.mAlarmManager.setExact(1, j, "lock_screen_next_alarm", this.mUpdateNextAlarm, this.mHandler);
            }
        }
        updateNextAlarm();
    }

    public final void onPrimaryMetadataOrStateChanged(final MediaMetadata mediaMetadata, final int i) {
        synchronized (this) {
            boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
            this.mMediaHandler.removeCallbacksAndMessages(null);
            if (this.mMediaIsVisible && !isPlayingState && this.mStatusBarState != 0) {
                this.mMediaWakeLock.setAcquired(true);
                this.mMediaHandler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSliceProvider keyguardSliceProvider = KeyguardSliceProvider.this;
                        MediaMetadata mediaMetadata2 = mediaMetadata;
                        int i2 = i;
                        synchronized (keyguardSliceProvider) {
                            keyguardSliceProvider.updateMediaStateLocked(mediaMetadata2, i2);
                            keyguardSliceProvider.mMediaWakeLock.setAcquired(false);
                        }
                    }
                }, 2000L);
            } else {
                this.mMediaWakeLock.setAcquired(false);
                updateMediaStateLocked(mediaMetadata, i);
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        boolean z;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mStatusBarState = i;
            if (needsMediaLocked != needsMediaLocked()) {
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            notifyChange();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onZenChanged(int i) {
        notifyChange();
    }

    public void registerClockUpdate() {
        synchronized (this) {
            if (this.mRegistered) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.DATE_CHANGED");
            intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
            getContext().registerReceiver(this.mIntentReceiver, intentFilter, null, null);
            this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
            this.mRegistered = true;
        }
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public final void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.mContextAvailableCallback = contextAvailableCallback;
    }

    public final void updateClockLocked() {
        if (this.mDateFormat == null) {
            DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(this.mDatePattern, Locale.getDefault());
            instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
            this.mDateFormat = instanceForSkeleton;
        }
        this.mCurrentTime.setTime(System.currentTimeMillis());
        String format = this.mDateFormat.format(this.mCurrentTime);
        if (!format.equals(this.mLastText)) {
            this.mLastText = format;
            notifyChange();
        }
    }

    public final void updateMediaStateLocked(MediaMetadata mediaMetadata, int i) {
        CharSequence charSequence;
        boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
        CharSequence charSequence2 = null;
        if (mediaMetadata != null) {
            charSequence = mediaMetadata.getText("android.media.metadata.TITLE");
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = getContext().getResources().getString(R.string.music_controls_no_title);
            }
        } else {
            charSequence = null;
        }
        if (mediaMetadata != null) {
            charSequence2 = mediaMetadata.getText("android.media.metadata.ARTIST");
        }
        if (isPlayingState == this.mMediaIsVisible && TextUtils.equals(charSequence, this.mMediaTitle) && TextUtils.equals(charSequence2, this.mMediaArtist)) {
            return;
        }
        this.mMediaTitle = charSequence;
        this.mMediaArtist = charSequence2;
        this.mMediaIsVisible = isPlayingState;
        notifyChange();
    }

    public final void updateNextAlarm() {
        String str;
        synchronized (this) {
            boolean z = false;
            if (this.mNextAlarmInfo != null) {
                if (this.mNextAlarmInfo.getTriggerTime() <= TimeUnit.HOURS.toMillis(12) + System.currentTimeMillis()) {
                    z = true;
                }
            }
            if (z) {
                if (android.text.format.DateFormat.is24HourFormat(getContext(), ((UserTrackerImpl) this.mUserTracker).getUserId())) {
                    str = "HH:mm";
                } else {
                    str = "h:mm";
                }
                this.mNextAlarm = android.text.format.DateFormat.format(str, this.mNextAlarmInfo.getTriggerTime()).toString();
            } else {
                this.mNextAlarm = "";
            }
        }
        notifyChange();
    }
}
