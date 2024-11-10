package com.android.server.display;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayDeviceRepository;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.layout.DisplayIdProducer;
import com.android.server.display.layout.Layout;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class LogicalDisplayMapper implements DisplayDeviceRepository.Listener {
    public static int sNextNonDefaultDisplayId = 5;
    public boolean mBootCompleted;
    public Layout mCurrentLayout;
    public final SparseIntArray mDeviceDisplayGroupIds;
    public int mDeviceState;
    public int mDeviceStateToBeAppliedAfterBoot;
    public final DeviceStateToLayoutMap mDeviceStateToLayoutMap;
    public final SparseBooleanArray mDeviceStatesOnWhichToSleep;
    public final SparseBooleanArray mDeviceStatesOnWhichToWakeUp;
    public final DisplayDeviceRepository mDisplayDeviceRepo;
    public final ArrayMap mDisplayGroupIdsByName;
    public final SparseArray mDisplayGroups;
    public final SparseIntArray mDisplayGroupsToUpdate;
    public final LogicalDisplayMapperHandler mHandler;
    public LogicalDisplay mHiddenSpaceDisplay;
    public final DisplayIdProducer mIdProducer;
    public boolean mInteractive;
    public final Listener mListener;
    public final SparseArray mLogicalDisplays;
    public final SparseIntArray mLogicalDisplaysToUpdate;
    public int mNextNonDefaultGroupId;
    public int mPendingDeviceState;
    public final PowerManager mPowerManager;
    public final boolean mSingleDisplayDemoMode;
    public final boolean mSupportsConcurrentInternalDisplays;
    public final DisplayManagerService.SyncRoot mSyncRoot;
    public final DisplayInfo mTempDisplayInfo;
    public final DisplayInfo mTempNonOverrideDisplayInfo;
    public final SparseIntArray mUpdatedDisplayGroups;
    public final SparseIntArray mUpdatedLogicalDisplays;
    public final ArrayMap mVirtualDeviceDisplayMapping;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onDisplayGroupEventLocked(int i, int i2);

        void onLogicalDisplayEventLocked(LogicalDisplay logicalDisplay, int i);

        void onTraversalRequested();
    }

    public final int assignLayerStackLocked(int i) {
        return i;
    }

    public static /* synthetic */ int lambda$new$0(boolean z) {
        if (z) {
            return 0;
        }
        int i = sNextNonDefaultDisplayId;
        sNextNonDefaultDisplayId = i + 1;
        return i;
    }

    public LogicalDisplayMapper(Context context, DisplayDeviceRepository displayDeviceRepository, Listener listener, DisplayManagerService.SyncRoot syncRoot, Handler handler) {
        this(context, displayDeviceRepository, listener, syncRoot, handler, new DeviceStateToLayoutMap(new DisplayIdProducer() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda2
            @Override // com.android.server.display.layout.DisplayIdProducer
            public final int getId(boolean z) {
                int lambda$new$1;
                lambda$new$1 = LogicalDisplayMapper.lambda$new$1(z);
                return lambda$new$1;
            }
        }));
    }

    public static /* synthetic */ int lambda$new$1(boolean z) {
        if (z) {
            return 0;
        }
        int i = sNextNonDefaultDisplayId;
        sNextNonDefaultDisplayId = i + 1;
        return i;
    }

    public LogicalDisplayMapper(Context context, DisplayDeviceRepository displayDeviceRepository, Listener listener, DisplayManagerService.SyncRoot syncRoot, Handler handler, DeviceStateToLayoutMap deviceStateToLayoutMap) {
        this.mTempDisplayInfo = new DisplayInfo();
        this.mTempNonOverrideDisplayInfo = new DisplayInfo();
        this.mLogicalDisplays = new SparseArray();
        this.mDisplayGroups = new SparseArray();
        this.mDeviceDisplayGroupIds = new SparseIntArray();
        this.mDisplayGroupIdsByName = new ArrayMap();
        this.mHiddenSpaceDisplay = null;
        this.mUpdatedLogicalDisplays = new SparseIntArray();
        this.mUpdatedDisplayGroups = new SparseIntArray();
        this.mLogicalDisplaysToUpdate = new SparseIntArray();
        this.mDisplayGroupsToUpdate = new SparseIntArray();
        this.mVirtualDeviceDisplayMapping = new ArrayMap();
        this.mNextNonDefaultGroupId = 5;
        this.mIdProducer = new DisplayIdProducer() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda3
            @Override // com.android.server.display.layout.DisplayIdProducer
            public final int getId(boolean z) {
                int lambda$new$0;
                lambda$new$0 = LogicalDisplayMapper.lambda$new$0(z);
                return lambda$new$0;
            }
        };
        this.mCurrentLayout = null;
        this.mDeviceState = -1;
        this.mPendingDeviceState = -1;
        this.mDeviceStateToBeAppliedAfterBoot = -1;
        this.mBootCompleted = false;
        this.mSyncRoot = syncRoot;
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mPowerManager = powerManager;
        this.mInteractive = powerManager.isInteractive();
        this.mHandler = new LogicalDisplayMapperHandler(handler.getLooper());
        this.mDisplayDeviceRepo = displayDeviceRepository;
        this.mListener = listener;
        this.mSingleDisplayDemoMode = SystemProperties.getBoolean("persist.demo.singledisplay", false);
        this.mSupportsConcurrentInternalDisplays = context.getResources().getBoolean(17891862);
        this.mDeviceStatesOnWhichToWakeUp = toSparseBooleanArray(context.getResources().getIntArray(17236166));
        this.mDeviceStatesOnWhichToSleep = toSparseBooleanArray(context.getResources().getIntArray(17236165));
        displayDeviceRepository.addListener(this);
        this.mDeviceStateToLayoutMap = deviceStateToLayoutMap;
    }

    @Override // com.android.server.display.DisplayDeviceRepository.Listener
    public void onDisplayDeviceEventLocked(DisplayDevice displayDevice, int i) {
        if (i == 1) {
            handleDisplayDeviceAddedLocked(displayDevice);
        } else {
            if (i != 3) {
                return;
            }
            handleDisplayDeviceRemovedLocked(displayDevice);
            updateLogicalDisplaysLocked();
        }
    }

    @Override // com.android.server.display.DisplayDeviceRepository.Listener
    public void onDisplayDeviceChangedLocked(DisplayDevice displayDevice, int i) {
        finishStateTransitionLocked(false);
        updateLogicalDisplaysLocked(i);
    }

    @Override // com.android.server.display.DisplayDeviceRepository.Listener
    public void onTraversalRequested() {
        this.mListener.onTraversalRequested();
    }

    public LogicalDisplay getDisplayLocked(int i) {
        return getDisplayLocked(i, true);
    }

    public LogicalDisplay getDisplayLocked(int i, boolean z) {
        LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.get(i);
        if (logicalDisplay == null || logicalDisplay.isEnabledLocked() || z) {
            return logicalDisplay;
        }
        return null;
    }

    public LogicalDisplay getDisplayLocked(DisplayDevice displayDevice) {
        return getDisplayLocked(displayDevice, true);
    }

    public LogicalDisplay getDisplayLocked(DisplayDevice displayDevice, boolean z) {
        if (displayDevice == null) {
            return null;
        }
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            if (logicalDisplay.getPrimaryDisplayDeviceLocked() == displayDevice) {
                if (logicalDisplay.isEnabledLocked() || z) {
                    return logicalDisplay;
                }
                return null;
            }
        }
        return null;
    }

    public int[] getDisplayIdsLocked(int i, boolean z) {
        int size = this.mLogicalDisplays.size();
        int[] iArr = new int[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i3);
            if ((logicalDisplay.isEnabledLocked() || z) && logicalDisplay.getDisplayInfoLocked().hasAccess(i)) {
                iArr[i2] = this.mLogicalDisplays.keyAt(i3);
                i2++;
            }
        }
        return i2 != size ? Arrays.copyOfRange(iArr, 0, i2) : iArr;
    }

    public void forEachLocked(Consumer consumer) {
        forEachLocked(consumer, true);
    }

    public void forEachLocked(Consumer consumer, boolean z) {
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            if (logicalDisplay.isEnabledLocked() || z) {
                consumer.accept(logicalDisplay);
            }
        }
    }

    public LogicalDisplay getHiddenDisplayLocked() {
        LogicalDisplay logicalDisplay = this.mHiddenSpaceDisplay;
        if (logicalDisplay == null || !logicalDisplay.hasContentLocked()) {
            return null;
        }
        return this.mHiddenSpaceDisplay;
    }

    public int getDisplayGroupIdFromDisplayIdLocked(int i) {
        LogicalDisplay displayLocked = getDisplayLocked(i);
        if (displayLocked == null) {
            return -1;
        }
        int size = this.mDisplayGroups.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((DisplayGroup) this.mDisplayGroups.valueAt(i2)).containsLocked(displayLocked)) {
                return this.mDisplayGroups.keyAt(i2);
            }
        }
        return -1;
    }

    public DisplayGroup getDisplayGroupLocked(int i) {
        return (DisplayGroup) this.mDisplayGroups.get(i);
    }

    public DisplayInfo getDisplayInfoForStateLocked(int i, int i2) {
        Layout.Display byId;
        Layout layout = this.mDeviceStateToLayoutMap.get(i);
        if (layout == null || (byId = layout.getById(i2)) == null) {
            return null;
        }
        DisplayDevice byAddressLocked = this.mDisplayDeviceRepo.getByAddressLocked(byId.getAddress());
        if (byAddressLocked == null) {
            Slog.w("LogicalDisplayMapper", "The display device (" + byId.getAddress() + "), is not available for the display state " + this.mDeviceState);
            return null;
        }
        LogicalDisplay displayLocked = getDisplayLocked(byAddressLocked, true);
        if (displayLocked == null) {
            Slog.w("LogicalDisplayMapper", "The logical display associated with address (" + byId.getAddress() + "), is not available for the display state " + this.mDeviceState);
            return null;
        }
        DisplayInfo displayInfo = new DisplayInfo(displayLocked.getDisplayInfoLocked());
        displayInfo.displayId = i2;
        return displayInfo;
    }

    public void dumpLocked(PrintWriter printWriter) {
        printWriter.println("LogicalDisplayMapper:");
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mSingleDisplayDemoMode=" + this.mSingleDisplayDemoMode);
        indentingPrintWriter.println("mCurrentLayout=" + this.mCurrentLayout);
        indentingPrintWriter.println("mDeviceStatesOnWhichToWakeUp=" + this.mDeviceStatesOnWhichToWakeUp);
        indentingPrintWriter.println("mDeviceStatesOnWhichToSleep=" + this.mDeviceStatesOnWhichToSleep);
        indentingPrintWriter.println("mInteractive=" + this.mInteractive);
        indentingPrintWriter.println("mBootCompleted=" + this.mBootCompleted);
        indentingPrintWriter.println();
        indentingPrintWriter.println("mDeviceState=" + this.mDeviceState);
        indentingPrintWriter.println("mPendingDeviceState=" + this.mPendingDeviceState);
        indentingPrintWriter.println("mDeviceStateToBeAppliedAfterBoot=" + this.mDeviceStateToBeAppliedAfterBoot);
        int size = this.mLogicalDisplays.size();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Logical Displays: size=" + size);
        for (int i = 0; i < size; i++) {
            int keyAt = this.mLogicalDisplays.keyAt(i);
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            indentingPrintWriter.println("Display " + keyAt + XmlUtils.STRING_ARRAY_SEPARATOR);
            indentingPrintWriter.increaseIndent();
            logicalDisplay.dumpLocked(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
        this.mDeviceStateToLayoutMap.dumpLocked(indentingPrintWriter);
    }

    public void associateDisplayDeviceWithVirtualDevice(DisplayDevice displayDevice, int i) {
        this.mVirtualDeviceDisplayMapping.put(displayDevice.getUniqueId(), Integer.valueOf(i));
    }

    public void setDeviceStateLocked(int i, boolean z) {
        if (!this.mBootCompleted) {
            this.mDeviceStateToBeAppliedAfterBoot = i;
            return;
        }
        Slog.i("LogicalDisplayMapper", "Requesting Transition to state: " + i + ", from state=" + this.mDeviceState + ", interactive=" + this.mInteractive + ", mBootCompleted=" + this.mBootCompleted);
        resetLayoutLocked(this.mDeviceState, i, true);
        this.mPendingDeviceState = i;
        this.mDeviceStateToBeAppliedAfterBoot = -1;
        boolean shouldDeviceBeWoken = shouldDeviceBeWoken(i, this.mDeviceState, this.mInteractive, this.mBootCompleted);
        boolean shouldDeviceBePutToSleep = shouldDeviceBePutToSleep(this.mPendingDeviceState, this.mDeviceState, z, this.mInteractive, this.mBootCompleted);
        if (areAllTransitioningDisplaysOffLocked() && !shouldDeviceBeWoken && !shouldDeviceBePutToSleep) {
            transitionToPendingStateLocked();
            return;
        }
        updateLogicalDisplaysLocked();
        if (shouldDeviceBeWoken || shouldDeviceBePutToSleep) {
            if (shouldDeviceBeWoken) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicalDisplayMapper.this.lambda$setDeviceStateLocked$2();
                    }
                });
            } else if (shouldDeviceBePutToSleep) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogicalDisplayMapper.this.lambda$setDeviceStateLocked$3();
                    }
                });
            }
        }
        this.mHandler.sendEmptyMessageDelayed(1, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceStateLocked$2() {
        this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 12, "server.display:unfold");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceStateLocked$3() {
        this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 13, 2);
    }

    public void onBootCompleted() {
        synchronized (this.mSyncRoot) {
            this.mBootCompleted = true;
            int i = this.mDeviceStateToBeAppliedAfterBoot;
            if (i != -1) {
                setDeviceStateLocked(i, false);
            }
        }
    }

    public void onEarlyInteractivityChange(boolean z) {
        synchronized (this.mSyncRoot) {
            if (this.mInteractive != z) {
                this.mInteractive = z;
                finishStateTransitionLocked(false);
            }
        }
    }

    public boolean shouldDeviceBeWoken(int i, int i2, boolean z, boolean z2) {
        return this.mDeviceStatesOnWhichToWakeUp.get(i) && !this.mDeviceStatesOnWhichToWakeUp.get(i2) && !z && z2;
    }

    public boolean shouldDeviceBePutToSleep(int i, int i2, boolean z, boolean z2, boolean z3) {
        return i2 != -1 && this.mDeviceStatesOnWhichToSleep.get(i) && !this.mDeviceStatesOnWhichToSleep.get(i2) && !z && z2 && z3;
    }

    public final boolean areAllTransitioningDisplaysOffLocked() {
        DisplayDevice primaryDisplayDeviceLocked;
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i);
            if (logicalDisplay.isInTransitionLocked() && (primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked()) != null && primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().state != 1) {
                return false;
            }
        }
        return true;
    }

    public final void transitionToPendingStateLocked() {
        resetLayoutLocked(this.mDeviceState, this.mPendingDeviceState, false);
        this.mDeviceState = this.mPendingDeviceState;
        this.mPendingDeviceState = -1;
        applyLayoutLocked();
        updateLogicalDisplaysLocked();
    }

    public final void finishStateTransitionLocked(boolean z) {
        int i = this.mPendingDeviceState;
        if (i == -1) {
            return;
        }
        boolean z2 = false;
        boolean z3 = this.mDeviceStatesOnWhichToWakeUp.get(i) && !this.mDeviceStatesOnWhichToWakeUp.get(this.mDeviceState) && !this.mInteractive && this.mBootCompleted;
        boolean z4 = this.mDeviceStatesOnWhichToSleep.get(this.mPendingDeviceState) && !this.mDeviceStatesOnWhichToSleep.get(this.mDeviceState) && this.mInteractive && this.mBootCompleted;
        if (areAllTransitioningDisplaysOffLocked() && !z3 && !z4) {
            z2 = true;
        }
        if (z2 || z) {
            transitionToPendingStateLocked();
            this.mHandler.removeMessages(1);
        }
    }

    public final void handleDisplayDeviceAddedLocked(DisplayDevice displayDevice) {
        DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
        if ((displayDeviceInfoLocked.flags & 1) != 0) {
            initializeDefaultDisplayDeviceLocked(displayDevice);
        }
        int i = displayDeviceInfoLocked.flags;
        if ((1048576 & i) != 0) {
            assignDisplayGroupLocked(createNewLogicalDisplayLocked(displayDevice, 2));
        } else if ((8388608 & i) != 0) {
            createNewLogicalDisplayLocked(displayDevice, 4);
        } else if ((i & 16777216) != 0) {
            createNewLogicalDisplayLocked(displayDevice, 1);
        } else {
            LogicalDisplay createNewLogicalDisplayLocked = createNewLogicalDisplayLocked(displayDevice, this.mIdProducer.getId(false));
            if ((displayDeviceInfoLocked.flags & 4194304) != 0) {
                this.mHiddenSpaceDisplay = createNewLogicalDisplayLocked;
            }
        }
        applyLayoutLocked();
        updateLogicalDisplaysLocked();
    }

    public final void handleDisplayDeviceRemovedLocked(DisplayDevice displayDevice) {
        Layout layout = this.mDeviceStateToLayoutMap.get(-1);
        Layout.Display byId = layout.getById(0);
        if (byId == null) {
            return;
        }
        DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
        this.mVirtualDeviceDisplayMapping.remove(displayDevice.getUniqueId());
        if (byId.getAddress().equals(displayDeviceInfoLocked.address)) {
            layout.removeDisplayLocked(0);
            for (int i = 0; i < this.mLogicalDisplays.size(); i++) {
                DisplayDevice primaryDisplayDeviceLocked = ((LogicalDisplay) this.mLogicalDisplays.valueAt(i)).getPrimaryDisplayDeviceLocked();
                if (primaryDisplayDeviceLocked != null) {
                    DisplayDeviceInfo displayDeviceInfoLocked2 = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked();
                    if ((displayDeviceInfoLocked2.flags & 1) != 0 && !displayDeviceInfoLocked2.address.equals(displayDeviceInfoLocked.address)) {
                        layout.createDefaultDisplayLocked(displayDeviceInfoLocked2.address, this.mIdProducer);
                        applyLayoutLocked();
                        return;
                    }
                }
            }
        }
    }

    public final void updateLogicalDisplaysLocked() {
        updateLogicalDisplaysLocked(-1);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0188  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLogicalDisplaysLocked(int r17) {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.LogicalDisplayMapper.updateLogicalDisplaysLocked(int):void");
    }

    public final void sendUpdatesForDisplaysLocked(int i) {
        for (int size = this.mLogicalDisplaysToUpdate.size() - 1; size >= 0; size--) {
            if (this.mLogicalDisplaysToUpdate.valueAt(size) == i) {
                int keyAt = this.mLogicalDisplaysToUpdate.keyAt(size);
                this.mListener.onLogicalDisplayEventLocked(getDisplayLocked(keyAt), i);
                if (i == 3) {
                    this.mLogicalDisplays.delete(keyAt);
                    LogicalDisplay logicalDisplay = this.mHiddenSpaceDisplay;
                    if (logicalDisplay != null && logicalDisplay.getDisplayIdLocked() == keyAt) {
                        this.mHiddenSpaceDisplay = null;
                    }
                }
            }
        }
    }

    public final void sendUpdatesForGroupsLocked(int i) {
        for (int size = this.mDisplayGroupsToUpdate.size() - 1; size >= 0; size--) {
            if (this.mDisplayGroupsToUpdate.valueAt(size) == i) {
                int keyAt = this.mDisplayGroupsToUpdate.keyAt(size);
                this.mListener.onDisplayGroupEventLocked(keyAt, i);
                if (i == 3) {
                    this.mDisplayGroups.delete(keyAt);
                    int indexOfValue = this.mDeviceDisplayGroupIds.indexOfValue(keyAt);
                    if (indexOfValue >= 0) {
                        this.mDeviceDisplayGroupIds.removeAt(indexOfValue);
                    }
                }
            }
        }
    }

    public final void assignDisplayGroupLocked(LogicalDisplay logicalDisplay) {
        if (logicalDisplay.isValidLocked()) {
            DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
            int displayIdLocked = logicalDisplay.getDisplayIdLocked();
            Integer num = (Integer) this.mVirtualDeviceDisplayMapping.get(primaryDisplayDeviceLocked.getUniqueId());
            int displayGroupIdFromDisplayIdLocked = getDisplayGroupIdFromDisplayIdLocked(displayIdLocked);
            Integer valueOf = (num == null || this.mDeviceDisplayGroupIds.indexOfKey(num.intValue()) <= 0) ? null : Integer.valueOf(this.mDeviceDisplayGroupIds.get(num.intValue()));
            DisplayGroup displayGroupLocked = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
            String displayGroupNameLocked = logicalDisplay.getDisplayGroupNameLocked();
            DisplayDeviceInfo displayDeviceInfoLocked = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked();
            boolean z = false;
            boolean z2 = ((displayDeviceInfoLocked.flags & 16384) == 0 && TextUtils.isEmpty(displayGroupNameLocked) && displayGroupIdFromDisplayIdLocked != 2) ? false : true;
            boolean z3 = displayGroupIdFromDisplayIdLocked != 0;
            boolean z4 = (z2 || num == null) ? false : true;
            if (valueOf != null && displayGroupIdFromDisplayIdLocked == valueOf.intValue()) {
                z = true;
            }
            if (displayGroupIdFromDisplayIdLocked == -1 || z3 != z2 || z != z4) {
                if (displayIdLocked == 2) {
                    displayGroupIdFromDisplayIdLocked = 2;
                } else {
                    displayGroupIdFromDisplayIdLocked = (!CoreRune.CARLIFE_DISPLAY_GROUP || (displayDeviceInfoLocked.flags & Integer.MIN_VALUE) == 0) ? assignDisplayGroupIdLocked(z2, logicalDisplay.getDisplayGroupNameLocked(), z4, num) : 4;
                }
            }
            DisplayGroup displayGroupLocked2 = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
            if (displayGroupLocked2 == null) {
                displayGroupLocked2 = new DisplayGroup(displayGroupIdFromDisplayIdLocked);
                this.mDisplayGroups.append(displayGroupIdFromDisplayIdLocked, displayGroupLocked2);
            }
            if (displayGroupLocked != displayGroupLocked2) {
                if (displayGroupLocked != null) {
                    displayGroupLocked.removeDisplayLocked(logicalDisplay);
                }
                displayGroupLocked2.addDisplayLocked(logicalDisplay);
                logicalDisplay.updateDisplayGroupIdLocked(displayGroupIdFromDisplayIdLocked);
                StringBuilder sb = new StringBuilder();
                sb.append("Setting new display group ");
                sb.append(displayGroupIdFromDisplayIdLocked);
                sb.append(" for display ");
                sb.append(displayIdLocked);
                sb.append(", from previous group: ");
                sb.append(displayGroupLocked != null ? Integer.valueOf(displayGroupLocked.getGroupId()) : "null");
                Slog.i("LogicalDisplayMapper", sb.toString());
            }
        }
    }

    public final void resetLayoutLocked(int i, int i2, boolean z) {
        Layout layout = this.mDeviceStateToLayoutMap.get(i);
        Layout layout2 = this.mDeviceStateToLayoutMap.get(i2);
        int size = this.mLogicalDisplays.size();
        for (int i3 = 0; i3 < size; i3++) {
            LogicalDisplay logicalDisplay = (LogicalDisplay) this.mLogicalDisplays.valueAt(i3);
            int displayIdLocked = logicalDisplay.getDisplayIdLocked();
            DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
            if (primaryDisplayDeviceLocked != null) {
                DisplayAddress displayAddress = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().address;
                Layout.Display byAddress = displayAddress != null ? layout.getByAddress(displayAddress) : null;
                Layout.Display byAddress2 = displayAddress != null ? layout2.getByAddress(displayAddress) : null;
                if (logicalDisplay.isInTransitionLocked() || (byAddress == null || byAddress.isEnabled()) != (byAddress2 == null || byAddress2.isEnabled()) || (byAddress != null && byAddress2 != null && byAddress.getLogicalDisplayId() != byAddress2.getLogicalDisplayId()) || ((byAddress == null) != (byAddress2 == null))) {
                    if (z != logicalDisplay.isInTransitionLocked()) {
                        Slog.i("LogicalDisplayMapper", "Set isInTransition on display " + displayIdLocked + ": " + z);
                    }
                    logicalDisplay.setIsInTransitionLocked(z);
                    this.mUpdatedLogicalDisplays.put(displayIdLocked, 1);
                }
            }
        }
    }

    public final void applyLayoutLocked() {
        Layout layout = this.mCurrentLayout;
        this.mCurrentLayout = this.mDeviceStateToLayoutMap.get(this.mDeviceState);
        Slog.i("LogicalDisplayMapper", "Applying layout: " + this.mCurrentLayout + ", Previous layout: " + layout);
        int size = this.mCurrentLayout.size();
        for (int i = 0; i < size; i++) {
            Layout.Display at = this.mCurrentLayout.getAt(i);
            DisplayAddress address = at.getAddress();
            DisplayDevice byAddressLocked = this.mDisplayDeviceRepo.getByAddressLocked(address);
            if (byAddressLocked == null) {
                Slog.w("LogicalDisplayMapper", "The display device (" + address + "), is not available for the display state " + this.mDeviceState);
            } else {
                int logicalDisplayId = at.getLogicalDisplayId();
                LogicalDisplay displayLocked = getDisplayLocked(logicalDisplayId);
                if (displayLocked == null) {
                    displayLocked = createNewLogicalDisplayLocked(null, logicalDisplayId);
                }
                LogicalDisplay displayLocked2 = getDisplayLocked(byAddressLocked);
                if (displayLocked != displayLocked2) {
                    displayLocked.swapDisplaysLocked(displayLocked2);
                }
                DisplayDeviceConfig displayDeviceConfig = byAddressLocked.getDisplayDeviceConfig();
                displayLocked.setDevicePositionLocked(at.getPosition());
                displayLocked.setLeadDisplayLocked(at.getLeadDisplayId());
                displayLocked.updateLayoutLimitedRefreshRateLocked(displayDeviceConfig.getRefreshRange(at.getRefreshRateZoneId()));
                displayLocked.updateThermalRefreshRateThrottling(displayDeviceConfig.getThermalRefreshRateThrottlingData(at.getRefreshRateThermalThrottlingMapId()));
                setEnabledLocked(displayLocked, at.isEnabled());
                displayLocked.setThermalBrightnessThrottlingDataIdLocked(at.getThermalBrightnessThrottlingMapId() == null ? "default" : at.getThermalBrightnessThrottlingMapId());
                displayLocked.setDisplayGroupNameLocked(at.getDisplayGroupName());
            }
        }
    }

    public final LogicalDisplay createNewLogicalDisplayLocked(DisplayDevice displayDevice, int i) {
        LogicalDisplay logicalDisplay = new LogicalDisplay(i, assignLayerStackLocked(i), displayDevice);
        logicalDisplay.updateLocked(this.mDisplayDeviceRepo);
        if (logicalDisplay.getDisplayInfoLocked().type == 1 && this.mDeviceStateToLayoutMap.size() > 1) {
            logicalDisplay.setEnabledLocked(false);
        }
        this.mLogicalDisplays.put(i, logicalDisplay);
        return logicalDisplay;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
    
        if (r3 != 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setEnabledLocked(com.android.server.display.LogicalDisplay r4, boolean r5) {
        /*
            r3 = this;
            int r0 = r4.getDisplayIdLocked()
            android.view.DisplayInfo r1 = r4.getDisplayInfoLocked()
            boolean r3 = r3.mSingleDisplayDemoMode
            r2 = 0
            if (r3 == 0) goto L13
            int r3 = r1.type
            r1 = 1
            if (r3 == r1) goto L13
            goto L14
        L13:
            r1 = r2
        L14:
            java.lang.String r3 = "LogicalDisplayMapper"
            if (r5 == 0) goto L33
            if (r1 == 0) goto L33
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r1 = "Not creating a logical display for a secondary display because single display demo mode is enabled: "
            r5.append(r1)
            android.view.DisplayInfo r1 = r4.getDisplayInfoLocked()
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            android.util.Slog.i(r3, r5)
            r5 = r2
        L33:
            boolean r1 = r4.isEnabledLocked()
            if (r1 == r5) goto L58
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "SetEnabled on display "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = ": "
            r1.append(r0)
            r1.append(r5)
            java.lang.String r0 = r1.toString()
            android.util.Slog.i(r3, r0)
            r4.setEnabledLocked(r5)
        L58:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.LogicalDisplayMapper.setEnabledLocked(com.android.server.display.LogicalDisplay, boolean):void");
    }

    public final int assignDisplayGroupIdLocked(boolean z, String str, boolean z2, Integer num) {
        if (z2 && num != null) {
            int i = this.mDeviceDisplayGroupIds.get(num.intValue());
            if (i != 0) {
                return i;
            }
            int i2 = this.mNextNonDefaultGroupId;
            this.mNextNonDefaultGroupId = i2 + 1;
            this.mDeviceDisplayGroupIds.put(num.intValue(), i2);
            return i2;
        }
        if (!z) {
            return 0;
        }
        Integer num2 = (Integer) this.mDisplayGroupIdsByName.get(str);
        if (num2 == null) {
            int i3 = this.mNextNonDefaultGroupId;
            this.mNextNonDefaultGroupId = i3 + 1;
            num2 = Integer.valueOf(i3);
            this.mDisplayGroupIdsByName.put(str, num2);
        }
        return num2.intValue();
    }

    public final void initializeDefaultDisplayDeviceLocked(DisplayDevice displayDevice) {
        Layout layout = this.mDeviceStateToLayoutMap.get(-1);
        if (layout.getById(0) != null) {
            return;
        }
        layout.createDefaultDisplayLocked(displayDevice.getDisplayDeviceInfoLocked().address, this.mIdProducer);
    }

    public final SparseBooleanArray toSparseBooleanArray(int[] iArr) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(2);
        for (int i = 0; iArr != null && i < iArr.length; i++) {
            sparseBooleanArray.put(iArr[i], true);
        }
        return sparseBooleanArray;
    }

    public final boolean canMoveToDisplayGroupLocked(LogicalDisplay logicalDisplay) {
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        if (displayIdLocked == 0 || displayIdLocked == 2 || logicalDisplay.getPrimaryDisplayDeviceLocked() == null || getDisplayGroupIdFromDisplayIdLocked(2) == -1) {
            return false;
        }
        DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
        DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
        DisplayDeviceInfo displayDeviceInfoLocked = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked();
        if ((displayInfoLocked.flags & 256) != 0) {
            return false;
        }
        return (displayInfoLocked.type == 5 && !logicalDisplay.hasContentLocked() && primaryDisplayDeviceLocked.getDisplayIdToMirrorLocked() == 2) || this.mDisplayDeviceRepo.isExternalDisplayDeviceForDexLocked(displayDeviceInfoLocked);
    }

    public final void moveToNewDisplayGroupLocked(LogicalDisplay logicalDisplay) {
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        int displayGroupIdFromDisplayIdLocked = getDisplayGroupIdFromDisplayIdLocked(displayIdLocked);
        DisplayGroup displayGroupLocked = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
        boolean isDexDisplayDeviceEnabledLocked = this.mDisplayDeviceRepo.isDexDisplayDeviceEnabledLocked();
        if (isDexDisplayDeviceEnabledLocked && displayGroupIdFromDisplayIdLocked == 0) {
            displayGroupIdFromDisplayIdLocked = 2;
        } else if (!isDexDisplayDeviceEnabledLocked && displayGroupIdFromDisplayIdLocked == 2) {
            displayGroupIdFromDisplayIdLocked = 0;
        }
        DisplayGroup displayGroupLocked2 = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
        if (displayGroupLocked2 == null || displayGroupLocked == displayGroupLocked2) {
            return;
        }
        if (displayGroupLocked != null) {
            displayGroupLocked.removeDisplayLocked(logicalDisplay);
        }
        displayGroupLocked2.addDisplayLocked(logicalDisplay);
        logicalDisplay.updateDisplayGroupIdLocked(displayGroupIdFromDisplayIdLocked);
        StringBuilder sb = new StringBuilder();
        sb.append("moveToDisplayGroupLocked: new display group ");
        sb.append(displayGroupIdFromDisplayIdLocked);
        sb.append(" for display ");
        sb.append(displayIdLocked);
        sb.append(", from previous group: ");
        sb.append(displayGroupLocked != null ? Integer.valueOf(displayGroupLocked.getGroupId()) : "null");
        Slog.i("LogicalDisplayMapper", sb.toString());
    }

    public LogicalDisplay getDexLogicalDisplayLocked() {
        return (LogicalDisplay) this.mLogicalDisplays.get(2);
    }

    /* loaded from: classes2.dex */
    public class LogicalDisplayMapperHandler extends Handler {
        public LogicalDisplayMapperHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            synchronized (LogicalDisplayMapper.this.mSyncRoot) {
                LogicalDisplayMapper.this.finishStateTransitionLocked(true);
            }
        }
    }
}
