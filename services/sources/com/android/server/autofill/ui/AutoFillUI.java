package com.android.server.autofill.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.autofill.Dataset;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveInfo;
import android.service.autofill.ValueFinder;
import android.text.TextUtils;
import android.util.Slog;
import android.view.Display;
import android.view.KeyEvent;
import android.view.autofill.AutofillId;
import android.view.autofill.IAutofillWindowPresenter;
import android.widget.Toast;
import com.android.internal.logging.MetricsLogger;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerInternal;
import com.android.server.UiThread;
import com.android.server.autofill.Helper;
import com.android.server.autofill.PresentationStatsEventLogger;
import com.android.server.autofill.SaveEventLogger;
import com.android.server.autofill.ui.DialogFillUi;
import com.android.server.autofill.ui.FillUi;
import com.android.server.autofill.ui.SaveUi;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.utils.Slogf;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class AutoFillUI {
    public AutoFillUiCallback mCallback;
    public final Context mContext;
    public Context mContextForResources;
    public Runnable mCreateFillUiRunnable;
    public DialogFillUi mFillDialog;
    public FillUi mFillUi;
    public final OverlayControl mOverlayControl;
    public SaveUi mSaveUi;
    public AutoFillUiCallback mSaveUiCallback;
    public final Handler mHandler = UiThread.getHandler();
    public final MetricsLogger mMetricsLogger = new MetricsLogger();
    public final UiModeManagerInternal mUiModeMgr = (UiModeManagerInternal) LocalServices.getService(UiModeManagerInternal.class);

    /* loaded from: classes.dex */
    public interface AutoFillUiCallback {
        void authenticate(int i, int i2, IntentSender intentSender, Bundle bundle, int i3);

        void cancelSave();

        void cancelSession();

        void dispatchUnhandledKey(AutofillId autofillId, KeyEvent keyEvent);

        void fill(int i, int i2, Dataset dataset, int i3);

        void onShown(int i);

        void requestFallbackFromFillDialog();

        void requestHideFillUi(AutofillId autofillId);

        void requestShowFillUi(AutofillId autofillId, int i, int i2, IAutofillWindowPresenter iAutofillWindowPresenter);

        void requestShowSoftInput(AutofillId autofillId);

        void save();

        void startIntentSender(IntentSender intentSender, Intent intent);

        void startIntentSenderAndFinishSession(IntentSender intentSender);
    }

    public AutoFillUI(Context context) {
        this.mContext = context;
        this.mOverlayControl = new OverlayControl(context);
        this.mContextForResources = getContextForResources(context, 0);
    }

    public void setCallback(final AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$setCallback$0(autoFillUiCallback);
            }
        });
    }

    public /* synthetic */ void lambda$setCallback$0(AutoFillUiCallback autoFillUiCallback) {
        AutoFillUiCallback autoFillUiCallback2 = this.mCallback;
        if (autoFillUiCallback2 != autoFillUiCallback) {
            if (autoFillUiCallback2 != null) {
                if (isSaveUiShowing()) {
                    hideFillUiUiThread(autoFillUiCallback, true);
                } else {
                    lambda$hideAll$10(this.mCallback);
                }
            }
            this.mCallback = autoFillUiCallback;
        }
    }

    public void clearCallback(final AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$clearCallback$1(autoFillUiCallback);
            }
        });
    }

    public /* synthetic */ void lambda$clearCallback$1(AutoFillUiCallback autoFillUiCallback) {
        if (this.mCallback == autoFillUiCallback) {
            lambda$hideAll$10(autoFillUiCallback);
            this.mCallback = null;
        }
    }

    public void showError(int i, AutoFillUiCallback autoFillUiCallback) {
        showError(this.mContext.getString(i), autoFillUiCallback);
    }

    public void showError(final CharSequence charSequence, final AutoFillUiCallback autoFillUiCallback) {
        Slog.w("AutofillUI", "showError(): " + ((Object) charSequence));
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$showError$2(autoFillUiCallback, charSequence);
            }
        });
    }

    public /* synthetic */ void lambda$showError$2(AutoFillUiCallback autoFillUiCallback, CharSequence charSequence) {
        if (this.mCallback != autoFillUiCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        Toast.makeText(this.mContext, charSequence, 1).show();
    }

    public /* synthetic */ void lambda$hideFillUi$3(AutoFillUiCallback autoFillUiCallback) {
        hideFillUiUiThread(autoFillUiCallback, true);
    }

    public void hideFillUi(final AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$hideFillUi$3(autoFillUiCallback);
            }
        });
    }

    public void hideFillDialog(final AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$hideFillDialog$4(autoFillUiCallback);
            }
        });
    }

    public void filterFillUi(final String str, final AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$filterFillUi$5(autoFillUiCallback, str);
            }
        });
    }

    public /* synthetic */ void lambda$filterFillUi$5(AutoFillUiCallback autoFillUiCallback, String str) {
        FillUi fillUi;
        if (autoFillUiCallback == this.mCallback && (fillUi = this.mFillUi) != null) {
            fillUi.setFilterText(str);
        }
    }

    public void showFillUi(final AutofillId autofillId, final FillResponse fillResponse, final String str, String str2, ComponentName componentName, final CharSequence charSequence, final Drawable drawable, final AutoFillUiCallback autoFillUiCallback, Context context, int i, boolean z, final int i2) {
        if (Helper.sDebug) {
            Slogf.d("AutofillUI", "showFillUi(): id=%s, filter=%d chars, displayId=%d", autofillId, Integer.valueOf(str == null ? 0 : str.length()), Integer.valueOf(context.getDisplayId()));
        }
        final LogMaker addTaggedData = Helper.newLogMaker(910, componentName, str2, i, z).addTaggedData(911, Integer.valueOf(str == null ? 0 : str.length())).addTaggedData(909, Integer.valueOf(fillResponse.getDatasets() != null ? fillResponse.getDatasets().size() : 0));
        Runnable runnable = new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$showFillUi$6(autoFillUiCallback, i2, fillResponse, autofillId, str, charSequence, drawable, addTaggedData);
            }
        };
        if (isSaveUiShowing()) {
            if (Helper.sDebug) {
                Slog.d("AutofillUI", "postpone fill UI request..");
            }
            this.mCreateFillUiRunnable = runnable;
            return;
        }
        this.mHandler.post(runnable);
    }

    public /* synthetic */ void lambda$showFillUi$6(AutoFillUiCallback autoFillUiCallback, int i, FillResponse fillResponse, AutofillId autofillId, String str, CharSequence charSequence, Drawable drawable, LogMaker logMaker) {
        if (autoFillUiCallback != this.mCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        this.mContextForResources = getContextForResources(this.mContext, i);
        this.mFillUi = new FillUi(this.mContextForResources, fillResponse, autofillId, str, this.mOverlayControl, charSequence, drawable, this.mUiModeMgr.isNightMode(), new FillUi.Callback() { // from class: com.android.server.autofill.ui.AutoFillUI.1
            public final /* synthetic */ AutoFillUiCallback val$callback;
            public final /* synthetic */ AutofillId val$focusedId;
            public final /* synthetic */ LogMaker val$log;
            public final /* synthetic */ FillResponse val$response;

            public AnonymousClass1(LogMaker logMaker2, AutoFillUiCallback autoFillUiCallback2, FillResponse fillResponse2, AutofillId autofillId2) {
                r2 = logMaker2;
                r3 = autoFillUiCallback2;
                r4 = fillResponse2;
                r5 = autofillId2;
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onResponsePicked(FillResponse fillResponse2) {
                r2.setType(3);
                AutoFillUI.this.hideFillUiUiThread(r3, true);
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.authenticate(fillResponse2.getRequestId(), GnssNative.GNSS_AIDING_TYPE_ALL, fillResponse2.getAuthentication(), fillResponse2.getClientState(), 1);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onShown() {
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.onShown(1);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onDatasetPicked(Dataset dataset) {
                r2.setType(4);
                AutoFillUI.this.hideFillUiUiThread(r3, true);
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.fill(r4.getRequestId(), r4.getDatasets().indexOf(dataset), dataset, 1);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onCanceled() {
                r2.setType(5);
                AutoFillUI.this.hideFillUiUiThread(r3, true);
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onDestroy() {
                if (r2.getType() == 0) {
                    r2.setType(2);
                }
                AutoFillUI.this.mMetricsLogger.write(r2);
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void requestShowFillUi(int i2, int i3, IAutofillWindowPresenter iAutofillWindowPresenter) {
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.requestShowFillUi(r5, i2, i3, iAutofillWindowPresenter);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void requestHideFillUi() {
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.requestHideFillUi(r5);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void startIntentSender(IntentSender intentSender) {
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.startIntentSenderAndFinishSession(intentSender);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void dispatchUnhandledKey(KeyEvent keyEvent) {
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.dispatchUnhandledKey(r5, keyEvent);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void cancelSession() {
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.cancelSession();
                }
            }
        });
    }

    /* renamed from: com.android.server.autofill.ui.AutoFillUI$1 */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements FillUi.Callback {
        public final /* synthetic */ AutoFillUiCallback val$callback;
        public final /* synthetic */ AutofillId val$focusedId;
        public final /* synthetic */ LogMaker val$log;
        public final /* synthetic */ FillResponse val$response;

        public AnonymousClass1(LogMaker logMaker2, AutoFillUiCallback autoFillUiCallback2, FillResponse fillResponse2, AutofillId autofillId2) {
            r2 = logMaker2;
            r3 = autoFillUiCallback2;
            r4 = fillResponse2;
            r5 = autofillId2;
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void onResponsePicked(FillResponse fillResponse2) {
            r2.setType(3);
            AutoFillUI.this.hideFillUiUiThread(r3, true);
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.authenticate(fillResponse2.getRequestId(), GnssNative.GNSS_AIDING_TYPE_ALL, fillResponse2.getAuthentication(), fillResponse2.getClientState(), 1);
            }
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void onShown() {
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.onShown(1);
            }
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void onDatasetPicked(Dataset dataset) {
            r2.setType(4);
            AutoFillUI.this.hideFillUiUiThread(r3, true);
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.fill(r4.getRequestId(), r4.getDatasets().indexOf(dataset), dataset, 1);
            }
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void onCanceled() {
            r2.setType(5);
            AutoFillUI.this.hideFillUiUiThread(r3, true);
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void onDestroy() {
            if (r2.getType() == 0) {
                r2.setType(2);
            }
            AutoFillUI.this.mMetricsLogger.write(r2);
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void requestShowFillUi(int i2, int i3, IAutofillWindowPresenter iAutofillWindowPresenter) {
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.requestShowFillUi(r5, i2, i3, iAutofillWindowPresenter);
            }
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void requestHideFillUi() {
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.requestHideFillUi(r5);
            }
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void startIntentSender(IntentSender intentSender) {
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.startIntentSenderAndFinishSession(intentSender);
            }
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void dispatchUnhandledKey(KeyEvent keyEvent) {
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.dispatchUnhandledKey(r5, keyEvent);
            }
        }

        @Override // com.android.server.autofill.ui.FillUi.Callback
        public void cancelSession() {
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.cancelSession();
            }
        }
    }

    public void showSaveUi(final CharSequence charSequence, final Drawable drawable, final String str, final SaveInfo saveInfo, final ValueFinder valueFinder, final ComponentName componentName, final AutoFillUiCallback autoFillUiCallback, Context context, final PendingUi pendingUi, final boolean z, final boolean z2, final boolean z3, final SaveEventLogger saveEventLogger, final int i) {
        if (Helper.sVerbose) {
            Slogf.v("AutofillUI", "showSaveUi(update=%b) for %s and display %d: %s", Boolean.valueOf(z), componentName.toShortString(), Integer.valueOf(context.getDisplayId()), saveInfo);
        }
        final LogMaker addTaggedData = Helper.newLogMaker(916, componentName, str, pendingUi.sessionId, z2).addTaggedData(917, Integer.valueOf((saveInfo.getRequiredIds() == null ? 0 : saveInfo.getRequiredIds().length) + 0 + (saveInfo.getOptionalIds() != null ? saveInfo.getOptionalIds().length : 0)));
        if (z) {
            addTaggedData.addTaggedData(1555, 1);
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$showSaveUi$7(autoFillUiCallback, i, pendingUi, charSequence, drawable, str, componentName, saveInfo, valueFinder, addTaggedData, saveEventLogger, z, z2, z3);
            }
        });
    }

    public /* synthetic */ void lambda$showSaveUi$7(AutoFillUiCallback autoFillUiCallback, int i, PendingUi pendingUi, CharSequence charSequence, Drawable drawable, String str, ComponentName componentName, SaveInfo saveInfo, ValueFinder valueFinder, LogMaker logMaker, SaveEventLogger saveEventLogger, boolean z, boolean z2, boolean z3) {
        if (autoFillUiCallback != this.mCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        this.mSaveUiCallback = autoFillUiCallback;
        Context contextForResources = getContextForResources(this.mContext, i);
        this.mContextForResources = contextForResources;
        this.mSaveUi = new SaveUi(contextForResources, pendingUi, charSequence, drawable, str, componentName, saveInfo, valueFinder, this.mOverlayControl, new SaveUi.OnSaveListener() { // from class: com.android.server.autofill.ui.AutoFillUI.2
            public final /* synthetic */ AutoFillUiCallback val$callback;
            public final /* synthetic */ LogMaker val$log;
            public final /* synthetic */ SaveEventLogger val$mSaveEventLogger;
            public final /* synthetic */ PendingUi val$pendingSaveUi;

            public AnonymousClass2(LogMaker logMaker2, SaveEventLogger saveEventLogger2, AutoFillUiCallback autoFillUiCallback2, PendingUi pendingUi2) {
                r2 = logMaker2;
                r3 = saveEventLogger2;
                r4 = autoFillUiCallback2;
                r5 = pendingUi2;
            }

            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void onSave() {
                r2.setType(4);
                SaveEventLogger saveEventLogger2 = r3;
                if (saveEventLogger2 != null) {
                    saveEventLogger2.maybeSetSaveButtonClicked(true);
                }
                AutoFillUI.this.hideSaveUiUiThread(r4);
                r4.save();
                AutoFillUI.this.destroySaveUiUiThread(r5, true);
            }

            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void onCancel(IntentSender intentSender) {
                r2.setType(5);
                SaveEventLogger saveEventLogger2 = r3;
                if (saveEventLogger2 != null) {
                    saveEventLogger2.maybeSetCancelButtonClicked(true);
                }
                AutoFillUI.this.hideSaveUiUiThread(r4);
                if (intentSender != null) {
                    try {
                        intentSender.sendIntent(AutoFillUI.this.mContext, 0, null, null, null);
                    } catch (IntentSender.SendIntentException e) {
                        Slog.e("AutofillUI", "Error starting negative action listener: " + intentSender, e);
                    }
                }
                r4.cancelSave();
                AutoFillUI.this.destroySaveUiUiThread(r5, true);
            }

            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void onDestroy() {
                if (r2.getType() == 0) {
                    r2.setType(2);
                    r4.cancelSave();
                }
                AutoFillUI.this.mMetricsLogger.write(r2);
                SaveEventLogger saveEventLogger2 = r3;
                if (saveEventLogger2 != null) {
                    saveEventLogger2.maybeSetDialogDismissed(true);
                }
            }

            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void startIntentSender(IntentSender intentSender, Intent intent) {
                r4.startIntentSender(intentSender, intent);
            }
        }, this.mUiModeMgr.isNightMode(), z, z2, z3);
    }

    /* renamed from: com.android.server.autofill.ui.AutoFillUI$2 */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements SaveUi.OnSaveListener {
        public final /* synthetic */ AutoFillUiCallback val$callback;
        public final /* synthetic */ LogMaker val$log;
        public final /* synthetic */ SaveEventLogger val$mSaveEventLogger;
        public final /* synthetic */ PendingUi val$pendingSaveUi;

        public AnonymousClass2(LogMaker logMaker2, SaveEventLogger saveEventLogger2, AutoFillUiCallback autoFillUiCallback2, PendingUi pendingUi2) {
            r2 = logMaker2;
            r3 = saveEventLogger2;
            r4 = autoFillUiCallback2;
            r5 = pendingUi2;
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onSave() {
            r2.setType(4);
            SaveEventLogger saveEventLogger2 = r3;
            if (saveEventLogger2 != null) {
                saveEventLogger2.maybeSetSaveButtonClicked(true);
            }
            AutoFillUI.this.hideSaveUiUiThread(r4);
            r4.save();
            AutoFillUI.this.destroySaveUiUiThread(r5, true);
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onCancel(IntentSender intentSender) {
            r2.setType(5);
            SaveEventLogger saveEventLogger2 = r3;
            if (saveEventLogger2 != null) {
                saveEventLogger2.maybeSetCancelButtonClicked(true);
            }
            AutoFillUI.this.hideSaveUiUiThread(r4);
            if (intentSender != null) {
                try {
                    intentSender.sendIntent(AutoFillUI.this.mContext, 0, null, null, null);
                } catch (IntentSender.SendIntentException e) {
                    Slog.e("AutofillUI", "Error starting negative action listener: " + intentSender, e);
                }
            }
            r4.cancelSave();
            AutoFillUI.this.destroySaveUiUiThread(r5, true);
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onDestroy() {
            if (r2.getType() == 0) {
                r2.setType(2);
                r4.cancelSave();
            }
            AutoFillUI.this.mMetricsLogger.write(r2);
            SaveEventLogger saveEventLogger2 = r3;
            if (saveEventLogger2 != null) {
                saveEventLogger2.maybeSetDialogDismissed(true);
            }
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void startIntentSender(IntentSender intentSender, Intent intent) {
            r4.startIntentSender(intentSender, intent);
        }
    }

    public void showFillDialog(final AutofillId autofillId, final FillResponse fillResponse, final String str, final String str2, final ComponentName componentName, final Drawable drawable, final AutoFillUiCallback autoFillUiCallback, int i, boolean z, final PresentationStatsEventLogger presentationStatsEventLogger) {
        if (Helper.sVerbose) {
            Slog.v("AutofillUI", "showFillDialog for " + componentName.toShortString() + ": " + fillResponse);
        }
        final LogMaker addTaggedData = Helper.newLogMaker(910, componentName, str2, i, z).addTaggedData(911, Integer.valueOf(str == null ? 0 : str.length())).addTaggedData(909, Integer.valueOf(fillResponse.getDatasets() != null ? fillResponse.getDatasets().size() : 0));
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$showFillDialog$8(autoFillUiCallback, fillResponse, autofillId, str, drawable, str2, componentName, presentationStatsEventLogger, addTaggedData);
            }
        });
    }

    public /* synthetic */ void lambda$showFillDialog$8(AutoFillUiCallback autoFillUiCallback, FillResponse fillResponse, AutofillId autofillId, String str, Drawable drawable, String str2, ComponentName componentName, PresentationStatsEventLogger presentationStatsEventLogger, LogMaker logMaker) {
        if (autoFillUiCallback != this.mCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        this.mFillDialog = new DialogFillUi(this.mContext, fillResponse, autofillId, str, drawable, str2, componentName, this.mOverlayControl, this.mUiModeMgr.isNightMode(), new DialogFillUi.UiCallback() { // from class: com.android.server.autofill.ui.AutoFillUI.3
            public final /* synthetic */ AutoFillUiCallback val$callback;
            public final /* synthetic */ AutofillId val$focusedId;
            public final /* synthetic */ LogMaker val$log;
            public final /* synthetic */ PresentationStatsEventLogger val$mPresentationStatsEventLogger;
            public final /* synthetic */ FillResponse val$response;

            public AnonymousClass3(AutoFillUiCallback autoFillUiCallback2, PresentationStatsEventLogger presentationStatsEventLogger2, FillResponse fillResponse2, AutofillId autofillId2, LogMaker logMaker2) {
                r2 = autoFillUiCallback2;
                r3 = presentationStatsEventLogger2;
                r4 = fillResponse2;
                r5 = autofillId2;
                r6 = logMaker2;
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onResponsePicked(FillResponse fillResponse2) {
                log(3);
                AutoFillUI.this.lambda$hideFillDialog$4(r2);
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.authenticate(fillResponse2.getRequestId(), GnssNative.GNSS_AIDING_TYPE_ALL, fillResponse2.getAuthentication(), fillResponse2.getClientState(), 3);
                }
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onShown() {
                r2.onShown(3);
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onDatasetPicked(Dataset dataset) {
                log(4);
                PresentationStatsEventLogger presentationStatsEventLogger2 = r3;
                if (presentationStatsEventLogger2 != null) {
                    presentationStatsEventLogger2.maybeSetPositiveCtaButtonClicked(true);
                }
                AutoFillUI.this.lambda$hideFillDialog$4(r2);
                if (AutoFillUI.this.mCallback != null) {
                    AutoFillUI.this.mCallback.fill(r4.getRequestId(), r4.getDatasets().indexOf(dataset), dataset, 3);
                }
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onDismissed() {
                log(5);
                PresentationStatsEventLogger presentationStatsEventLogger2 = r3;
                if (presentationStatsEventLogger2 != null) {
                    presentationStatsEventLogger2.maybeSetDialogDismissed(true);
                }
                AutoFillUI.this.lambda$hideFillDialog$4(r2);
                r2.requestShowSoftInput(r5);
                r2.requestFallbackFromFillDialog();
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onCanceled() {
                log(2);
                PresentationStatsEventLogger presentationStatsEventLogger2 = r3;
                if (presentationStatsEventLogger2 != null) {
                    presentationStatsEventLogger2.maybeSetNegativeCtaButtonClicked(true);
                }
                AutoFillUI.this.lambda$hideFillDialog$4(r2);
                r2.requestShowSoftInput(r5);
                r2.requestFallbackFromFillDialog();
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void startIntentSender(IntentSender intentSender) {
                AutoFillUI.this.mCallback.startIntentSenderAndFinishSession(intentSender);
            }

            public final void log(int i) {
                r6.setType(i);
                AutoFillUI.this.mMetricsLogger.write(r6);
            }
        });
    }

    /* renamed from: com.android.server.autofill.ui.AutoFillUI$3 */
    /* loaded from: classes.dex */
    public class AnonymousClass3 implements DialogFillUi.UiCallback {
        public final /* synthetic */ AutoFillUiCallback val$callback;
        public final /* synthetic */ AutofillId val$focusedId;
        public final /* synthetic */ LogMaker val$log;
        public final /* synthetic */ PresentationStatsEventLogger val$mPresentationStatsEventLogger;
        public final /* synthetic */ FillResponse val$response;

        public AnonymousClass3(AutoFillUiCallback autoFillUiCallback2, PresentationStatsEventLogger presentationStatsEventLogger2, FillResponse fillResponse2, AutofillId autofillId2, LogMaker logMaker2) {
            r2 = autoFillUiCallback2;
            r3 = presentationStatsEventLogger2;
            r4 = fillResponse2;
            r5 = autofillId2;
            r6 = logMaker2;
        }

        @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
        public void onResponsePicked(FillResponse fillResponse2) {
            log(3);
            AutoFillUI.this.lambda$hideFillDialog$4(r2);
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.authenticate(fillResponse2.getRequestId(), GnssNative.GNSS_AIDING_TYPE_ALL, fillResponse2.getAuthentication(), fillResponse2.getClientState(), 3);
            }
        }

        @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
        public void onShown() {
            r2.onShown(3);
        }

        @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
        public void onDatasetPicked(Dataset dataset) {
            log(4);
            PresentationStatsEventLogger presentationStatsEventLogger2 = r3;
            if (presentationStatsEventLogger2 != null) {
                presentationStatsEventLogger2.maybeSetPositiveCtaButtonClicked(true);
            }
            AutoFillUI.this.lambda$hideFillDialog$4(r2);
            if (AutoFillUI.this.mCallback != null) {
                AutoFillUI.this.mCallback.fill(r4.getRequestId(), r4.getDatasets().indexOf(dataset), dataset, 3);
            }
        }

        @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
        public void onDismissed() {
            log(5);
            PresentationStatsEventLogger presentationStatsEventLogger2 = r3;
            if (presentationStatsEventLogger2 != null) {
                presentationStatsEventLogger2.maybeSetDialogDismissed(true);
            }
            AutoFillUI.this.lambda$hideFillDialog$4(r2);
            r2.requestShowSoftInput(r5);
            r2.requestFallbackFromFillDialog();
        }

        @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
        public void onCanceled() {
            log(2);
            PresentationStatsEventLogger presentationStatsEventLogger2 = r3;
            if (presentationStatsEventLogger2 != null) {
                presentationStatsEventLogger2.maybeSetNegativeCtaButtonClicked(true);
            }
            AutoFillUI.this.lambda$hideFillDialog$4(r2);
            r2.requestShowSoftInput(r5);
            r2.requestFallbackFromFillDialog();
        }

        @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
        public void startIntentSender(IntentSender intentSender) {
            AutoFillUI.this.mCallback.startIntentSenderAndFinishSession(intentSender);
        }

        public final void log(int i) {
            r6.setType(i);
            AutoFillUI.this.mMetricsLogger.write(r6);
        }
    }

    public void onPendingSaveUi(final int i, final IBinder iBinder) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$onPendingSaveUi$9(i, iBinder);
            }
        });
    }

    public /* synthetic */ void lambda$onPendingSaveUi$9(int i, IBinder iBinder) {
        SaveUi saveUi = this.mSaveUi;
        if (saveUi != null) {
            saveUi.onPendingUi(i, iBinder);
            return;
        }
        Slog.w("AutofillUI", "onPendingSaveUi(" + i + "): no save ui");
    }

    public void hideAll(final AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$hideAll$10(autoFillUiCallback);
            }
        });
    }

    public void destroyAll(final PendingUi pendingUi, final AutoFillUiCallback autoFillUiCallback, final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AutoFillUI.this.lambda$destroyAll$11(pendingUi, autoFillUiCallback, z);
            }
        });
    }

    public boolean isSaveUiShowing() {
        SaveUi saveUi = this.mSaveUi;
        if (saveUi == null) {
            return false;
        }
        return saveUi.isShowing();
    }

    public boolean isFillDialogShowing() {
        DialogFillUi dialogFillUi = this.mFillDialog;
        if (dialogFillUi == null) {
            return false;
        }
        return dialogFillUi.isShowing();
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("Autofill UI");
        printWriter.print("  ");
        printWriter.print("Night mode: ");
        printWriter.println(this.mUiModeMgr.isNightMode());
        if (this.mFillUi != null) {
            printWriter.print("  ");
            printWriter.println("showsFillUi: true");
            this.mFillUi.dump(printWriter, "    ");
        } else {
            printWriter.print("  ");
            printWriter.println("showsFillUi: false");
        }
        if (this.mSaveUi != null) {
            printWriter.print("  ");
            printWriter.println("showsSaveUi: true");
            this.mSaveUi.dump(printWriter, "    ");
        } else {
            printWriter.print("  ");
            printWriter.println("showsSaveUi: false");
        }
        if (this.mFillDialog != null) {
            printWriter.print("  ");
            printWriter.println("showsFillDialog: true");
            this.mFillDialog.dump(printWriter, "    ");
        } else {
            printWriter.print("  ");
            printWriter.println("showsFillDialog: false");
        }
    }

    public final Context getContextForResources(Context context, int i) {
        DisplayManager displayManager;
        Display display;
        return (i == 0 || (displayManager = (DisplayManager) context.getSystemService("display")) == null || (display = displayManager.getDisplay(i)) == null) ? context : context.createDisplayContext(display);
    }

    public final void hideFillUiUiThread(AutoFillUiCallback autoFillUiCallback, boolean z) {
        FillUi fillUi = this.mFillUi;
        if (fillUi != null) {
            if (autoFillUiCallback == null || autoFillUiCallback == this.mCallback) {
                fillUi.destroy(z);
                this.mFillUi = null;
            }
        }
    }

    public final PendingUi hideSaveUiUiThread(AutoFillUiCallback autoFillUiCallback) {
        if (Helper.sVerbose) {
            Slog.v("AutofillUI", "hideSaveUiUiThread(): mSaveUi=" + this.mSaveUi + ", callback=" + autoFillUiCallback + ", mCallback=" + this.mCallback);
        }
        SaveUi saveUi = this.mSaveUi;
        if (saveUi == null || this.mSaveUiCallback != autoFillUiCallback) {
            return null;
        }
        return saveUi.hide();
    }

    /* renamed from: hideFillDialogUiThread */
    public final void lambda$hideFillDialog$4(AutoFillUiCallback autoFillUiCallback) {
        DialogFillUi dialogFillUi = this.mFillDialog;
        if (dialogFillUi != null) {
            if (autoFillUiCallback == null || autoFillUiCallback == this.mCallback) {
                dialogFillUi.destroy();
                this.mFillDialog = null;
            }
        }
    }

    public final void destroySaveUiUiThread(PendingUi pendingUi, boolean z) {
        if (this.mSaveUi == null) {
            if (Helper.sDebug) {
                Slog.d("AutofillUI", "destroySaveUiUiThread(): already destroyed");
                return;
            }
            return;
        }
        if (Helper.sDebug) {
            Slog.d("AutofillUI", "destroySaveUiUiThread(): " + pendingUi);
        }
        this.mSaveUi.destroy();
        this.mSaveUi = null;
        this.mSaveUiCallback = null;
        if (pendingUi != null && z) {
            try {
                if (Helper.sDebug) {
                    Slog.d("AutofillUI", "destroySaveUiUiThread(): notifying client");
                }
                pendingUi.client.setSaveUiState(pendingUi.sessionId, false);
            } catch (RemoteException e) {
                Slog.e("AutofillUI", "Error notifying client to set save UI state to hidden: " + e);
            }
        }
        if (this.mCreateFillUiRunnable != null) {
            if (Helper.sDebug) {
                Slog.d("AutofillUI", "start the pending fill UI request..");
            }
            this.mHandler.post(this.mCreateFillUiRunnable);
            this.mCreateFillUiRunnable = null;
        }
    }

    /* renamed from: destroyAllUiThread */
    public final void lambda$destroyAll$11(PendingUi pendingUi, AutoFillUiCallback autoFillUiCallback, boolean z) {
        hideFillUiUiThread(autoFillUiCallback, z);
        lambda$hideFillDialog$4(autoFillUiCallback);
        destroySaveUiUiThread(pendingUi, z);
    }

    /* renamed from: hideAllUiThread */
    public final void lambda$hideAll$10(AutoFillUiCallback autoFillUiCallback) {
        hideFillUiUiThread(autoFillUiCallback, true);
        lambda$hideFillDialog$4(autoFillUiCallback);
        PendingUi hideSaveUiUiThread = hideSaveUiUiThread(autoFillUiCallback);
        if (hideSaveUiUiThread == null || hideSaveUiUiThread.getState() != 4) {
            return;
        }
        if (Helper.sDebug) {
            Slog.d("AutofillUI", "hideAllUiThread(): destroying Save UI because pending restoration is finished");
        }
        destroySaveUiUiThread(hideSaveUiUiThread, true);
    }
}
