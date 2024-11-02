package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.common.IconInfo;
import com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils;
import com.android.systemui.media.taptotransfer.common.MediaTttUtils;
import com.android.systemui.media.taptotransfer.sender.SenderEndItem;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.temporarydisplay.ViewPriority;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarEndItem;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class MediaTttSenderCoordinator implements CoreStartable, Dumpable {
    public final ChipbarCoordinator chipbarCoordinator;
    public final CommandQueue commandQueue;
    public final Context context;
    public final int defaultTimeout;
    public final DumpManager dumpManager;
    public final MediaTttSenderLogger logger;
    public final MediaTttFlags mediaTttFlags;
    public final MediaTttSenderUiEventLogger uiEventLogger;
    public final Map stateMap = new LinkedHashMap();
    public final MediaTttSenderCoordinator$commandQueueCallbacks$1 commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1
        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
            MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(MediaTttSenderCoordinator.this, i, mediaRoute2Info, iUndoMediaTransferCallback);
        }
    };
    public final MediaTttSenderCoordinator$displayListener$1 displayListener = new TemporaryViewDisplayController.Listener() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$displayListener$1
        @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController.Listener
        public final void onInfoPermanentlyRemoved(String str, String str2) {
            MediaTttSenderCoordinator.this.removeIdFromStore(str, str2);
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TimeoutLength.values().length];
            try {
                iArr[TimeoutLength.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TimeoutLength.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$displayListener$1] */
    public MediaTttSenderCoordinator(ChipbarCoordinator chipbarCoordinator, CommandQueue commandQueue, Context context, DumpManager dumpManager, MediaTttSenderLogger mediaTttSenderLogger, MediaTttFlags mediaTttFlags, MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger) {
        this.chipbarCoordinator = chipbarCoordinator;
        this.commandQueue = commandQueue;
        this.context = context;
        this.dumpManager = dumpManager;
        this.logger = mediaTttSenderLogger;
        this.mediaTttFlags = mediaTttFlags;
        this.uiEventLogger = mediaTttSenderUiEventLogger;
        this.defaultTimeout = context.getResources().getInteger(R.integer.heads_up_notification_decay);
    }

    public static final void access$updateMediaTapToTransferSenderDisplay(final MediaTttSenderCoordinator mediaTttSenderCoordinator, int i, final MediaRoute2Info mediaRoute2Info, final IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        String str;
        ChipStateSender chipStateSender;
        InstanceId newInstanceId;
        boolean isValidNextState;
        String obj;
        ChipbarEndItem chipbarEndItem;
        String name;
        int i2;
        boolean z;
        mediaTttSenderCoordinator.getClass();
        ChipStateSender.Companion.getClass();
        ChipbarEndItem chipbarEndItem2 = null;
        try {
        } catch (NoSuchElementException e) {
            Log.e("ChipStateSender", "Could not find requested state " + i, e);
            r8 = null;
        }
        for (ChipStateSender chipStateSender2 : ChipStateSender.values()) {
            if (chipStateSender2.getStateInt() == i) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                if (chipStateSender2 == null || (str = chipStateSender2.name()) == null) {
                    str = "Invalid";
                }
                String id = mediaRoute2Info.getId();
                String clientPackageName = mediaRoute2Info.getClientPackageName();
                final MediaTttSenderLogger mediaTttSenderLogger = mediaTttSenderCoordinator.logger;
                mediaTttSenderLogger.getClass();
                MediaTttLoggerUtils.INSTANCE.getClass();
                LogBuffer logBuffer = mediaTttSenderLogger.buffer;
                MediaTttLoggerUtils.logStateChange(logBuffer, "MediaTttSender", str, id, clientPackageName);
                if (chipStateSender2 == null) {
                    MediaTttLoggerUtils.logStateChangeError(logBuffer, "MediaTttSender", i);
                    return;
                }
                Map map = mediaTttSenderCoordinator.stateMap;
                LinkedHashMap linkedHashMap = (LinkedHashMap) map;
                Pair pair = (Pair) linkedHashMap.get(mediaRoute2Info.getId());
                if (pair != null) {
                    chipStateSender = (ChipStateSender) pair.getSecond();
                } else {
                    chipStateSender = null;
                }
                Pair pair2 = (Pair) linkedHashMap.get(mediaRoute2Info.getId());
                ChipbarCoordinator chipbarCoordinator = mediaTttSenderCoordinator.chipbarCoordinator;
                if (pair2 == null || (newInstanceId = (InstanceId) pair2.getFirst()) == null) {
                    newInstanceId = chipbarCoordinator.tempViewUiEventLogger.instanceIdSequence.newInstanceId();
                }
                final InstanceId instanceId = newInstanceId;
                ChipStateSender.Companion.getClass();
                if (chipStateSender == null) {
                    isValidNextState = ChipStateSender.FAR_FROM_RECEIVER.isValidNextState(chipStateSender2);
                } else if (chipStateSender == chipStateSender2) {
                    isValidNextState = true;
                } else {
                    isValidNextState = chipStateSender.isValidNextState(chipStateSender2);
                }
                String str2 = "FAR_FROM_RECEIVER";
                if (!isValidNextState) {
                    if (chipStateSender != null && (name = chipStateSender.name()) != null) {
                        str2 = name;
                    }
                    String name2 = chipStateSender2.name();
                    LogMessage obtain = logBuffer.obtain("MediaTttSender", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderLogger$logInvalidStateTransitionError$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            LogMessage logMessage = (LogMessage) obj2;
                            return MotionLayout$$ExternalSyntheticOutline0.m("Cannot display state=", logMessage.getStr2(), " after state=", logMessage.getStr1(), "; invalid transition");
                        }
                    }, null);
                    CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(obtain, str2, name2, logBuffer, obtain);
                    return;
                }
                mediaTttSenderCoordinator.uiEventLogger.logger.log(chipStateSender2.getUiEvent(), instanceId);
                if (chipStateSender2 == ChipStateSender.FAR_FROM_RECEIVER) {
                    if (chipStateSender != null) {
                        if (chipStateSender.getTransferStatus() != TransferStatus.IN_PROGRESS && chipStateSender.getTransferStatus() != TransferStatus.SUCCEEDED) {
                            mediaTttSenderCoordinator.removeIdFromStore(mediaRoute2Info.getId(), "FAR_FROM_RECEIVER");
                            chipbarCoordinator.removeView(mediaRoute2Info.getId(), "FAR_FROM_RECEIVER");
                            return;
                        } else {
                            String m = KeyAttributes$$ExternalSyntheticOutline0.m("transferStatus=", chipStateSender.getTransferStatus().name());
                            LogMessage obtain2 = logBuffer.obtain("MediaTttSender", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderLogger$logRemovalBypass$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    LogMessage logMessage = (LogMessage) obj2;
                                    return FontProvider$$ExternalSyntheticOutline0.m("Chip removal requested due to ", logMessage.getStr1(), "; however, removal was ignored because ", logMessage.getStr2());
                                }
                            }, null);
                            CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(obtain2, "FAR_FROM_RECEIVER", m, logBuffer, obtain2);
                            return;
                        }
                    }
                    return;
                }
                map.put(mediaRoute2Info.getId(), new Pair(instanceId, chipStateSender2));
                LogMessage obtain3 = logBuffer.obtain("MediaTttSender", LogLevel.DEBUG, MediaTttSenderLogger$logStateMap$2.INSTANCE, null);
                obtain3.setStr1(map.toString());
                logBuffer.commit(obtain3);
                chipbarCoordinator.listeners.add(mediaTttSenderCoordinator.displayListener);
                final String clientPackageName2 = mediaRoute2Info.getClientPackageName();
                boolean isBlank = StringsKt__StringsJVMKt.isBlank(mediaRoute2Info.getName());
                Context context = mediaTttSenderCoordinator.context;
                if (isBlank) {
                    obj = context.getString(R.string.media_ttt_default_device_type);
                } else {
                    obj = mediaRoute2Info.getName().toString();
                }
                MediaTttUtils.Companion companion = MediaTttUtils.Companion;
                Function0 function0 = new Function0() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$createChipbarInfo$icon$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        MediaTttSenderLogger mediaTttSenderLogger2 = MediaTttSenderLogger.this;
                        String str3 = clientPackageName2;
                        mediaTttSenderLogger2.getClass();
                        MediaTttLoggerUtils.INSTANCE.getClass();
                        MediaTttLoggerUtils.logPackageNotFound(mediaTttSenderLogger2.buffer, "MediaTttSender", str3);
                        return Unit.INSTANCE;
                    }
                };
                companion.getClass();
                IconInfo iconInfoFromPackageName = MediaTttUtils.Companion.getIconInfoFromPackageName(context, clientPackageName2, false, function0);
                int i3 = WhenMappings.$EnumSwitchMapping$0[chipStateSender2.getTimeoutLength().ordinal()];
                int i4 = mediaTttSenderCoordinator.defaultTimeout;
                if (i3 != 1) {
                    if (i3 == 2) {
                        i4 *= 2;
                    } else {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                int i5 = i4;
                TintedIcon tintedIcon = iconInfoFromPackageName.toTintedIcon();
                Text chipTextString = chipStateSender2.getChipTextString(context, obj);
                SenderEndItem endItem = chipStateSender2.getEndItem();
                if (endItem != null) {
                    if (endItem instanceof SenderEndItem.Loading) {
                        chipbarEndItem = ChipbarEndItem.Loading.INSTANCE;
                    } else if (endItem instanceof SenderEndItem.Error) {
                        chipbarEndItem = ChipbarEndItem.Error.INSTANCE;
                    } else if (endItem instanceof SenderEndItem.UndoButton) {
                        if (iUndoMediaTransferCallback != null) {
                            final UiEventLogger.UiEventEnum uiEventEnum = ((SenderEndItem.UndoButton) chipStateSender2.getEndItem()).uiEventOnClick;
                            final int i6 = ((SenderEndItem.UndoButton) chipStateSender2.getEndItem()).newState;
                            chipbarEndItem2 = new ChipbarEndItem.Button(new Text.Resource(R.string.media_transfer_undo), new View.OnClickListener() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$getUndoButton$onClickListener$1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    boolean z2;
                                    MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger = MediaTttSenderCoordinator.this.uiEventLogger;
                                    UiEventLogger.UiEventEnum uiEventEnum2 = uiEventEnum;
                                    InstanceId instanceId2 = instanceId;
                                    mediaTttSenderUiEventLogger.getClass();
                                    if (uiEventEnum2 != MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED && uiEventEnum2 != MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED) {
                                        z2 = false;
                                    } else {
                                        z2 = true;
                                    }
                                    if (!z2) {
                                        String simpleName = Reflection.getOrCreateKotlinClass(MediaTttSenderUiEventLogger.class).getSimpleName();
                                        Intrinsics.checkNotNull(simpleName);
                                        Log.w(simpleName, "Must pass an undo-specific UiEvent.");
                                    } else {
                                        mediaTttSenderUiEventLogger.logger.log(uiEventEnum2, instanceId2);
                                    }
                                    iUndoMediaTransferCallback.onUndoTriggered();
                                    MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(MediaTttSenderCoordinator.this, i6, mediaRoute2Info, null);
                                }
                            });
                        }
                    } else {
                        throw new NoWhenBranchMatchedException();
                    }
                    chipbarEndItem2 = chipbarEndItem;
                }
                chipbarCoordinator.displayView(new ChipbarInfo(tintedIcon, chipTextString, chipbarEndItem2, chipStateSender2.getTransferStatus().getVibrationEffect(), true, "Media Transfer Chip View (Sender)", "MEDIA_TRANSFER_ACTIVATED_SENDER", i5, mediaRoute2Info.getId(), ViewPriority.NORMAL, instanceId));
                return;
            }
        }
        throw new NoSuchElementException("Array contains no element matching the predicate.");
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Current sender states:");
        printWriter.println(this.stateMap.toString());
    }

    public final void removeIdFromStore(String str, String str2) {
        MediaTttSenderLogger mediaTttSenderLogger = this.logger;
        mediaTttSenderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaTttSenderLogger$logStateMapRemoval$2 mediaTttSenderLogger$logStateMapRemoval$2 = new Function1() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderLogger$logStateMapRemoval$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("State removal: id=", logMessage.getStr1(), " reason=", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = mediaTttSenderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTttSender", logLevel, mediaTttSenderLogger$logStateMapRemoval$2, null);
        CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(obtain, str, str2, logBuffer, obtain);
        Map map = this.stateMap;
        map.remove(str);
        LogMessage obtain2 = logBuffer.obtain("MediaTttSender", logLevel, MediaTttSenderLogger$logStateMap$2.INSTANCE, null);
        obtain2.setStr1(map.toString());
        logBuffer.commit(obtain2);
        if (map.isEmpty()) {
            this.chipbarCoordinator.listeners.remove(this.displayListener);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        MediaTttFlags mediaTttFlags = this.mediaTttFlags;
        mediaTttFlags.getClass();
        Flags.INSTANCE.getClass();
        if (((FeatureFlagsRelease) mediaTttFlags.featureFlags).isEnabled(Flags.MEDIA_TAP_TO_TRANSFER)) {
            this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
            this.dumpManager.registerNormalDumpable(this);
        }
    }
}
