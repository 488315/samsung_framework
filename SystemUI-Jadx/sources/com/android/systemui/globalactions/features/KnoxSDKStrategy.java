package com.android.systemui.globalactions.features;

import com.android.systemui.globalactions.util.KnoxCustomManagerWrapper;
import com.android.systemui.globalactions.util.PowerItemWrapper;
import com.android.systemui.globalactions.util.ProKioskManagerWrapper;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.InitializationStrategy;
import com.samsung.android.globalactions.presentation.strategies.OnKeyListenerStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.knox.custom.PowerItem;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class KnoxSDKStrategy implements ActionsCreationStrategy, InitializationStrategy, DefaultActionsCreationStrategy, OnKeyListenerStrategy {
    public final ConditionChecker mConditionChecker;
    public final SamsungGlobalActions mGlobalActions;
    public final KnoxCustomManagerWrapper mKnoxCustomManagerWrapper;
    public final LogWrapper mLogWrapper;
    public final ProKioskManagerWrapper mProKioskManagerWrapper;
    public final ActionViewModelFactory mViewModelFactory;

    public KnoxSDKStrategy(ActionViewModelFactory actionViewModelFactory, ConditionChecker conditionChecker, KnoxCustomManagerWrapper knoxCustomManagerWrapper, ProKioskManagerWrapper proKioskManagerWrapper, LogWrapper logWrapper, SamsungGlobalActions samsungGlobalActions) {
        this.mGlobalActions = samsungGlobalActions;
        this.mViewModelFactory = actionViewModelFactory;
        this.mConditionChecker = conditionChecker;
        this.mProKioskManagerWrapper = proKioskManagerWrapper;
        this.mKnoxCustomManagerWrapper = knoxCustomManagerWrapper;
        this.mLogWrapper = logWrapper;
    }

    public final void onCreateActions(SamsungGlobalActions samsungGlobalActions) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = false;
        if (this.mConditionChecker.isEnabled(SystemUIConditions.GET_PROKIOSK_STATE)) {
            this.mProKioskManagerWrapper.mProKioskOptionShown = false;
            samsungGlobalActions.setOverrideDefaultActions(true);
            int powerDialogItems = this.mProKioskManagerWrapper.mProKioskManager.getPowerDialogItems();
            if ((4 | powerDialogItems) == powerDialogItems) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "power"));
            }
            int powerDialogItems2 = this.mProKioskManagerWrapper.mProKioskManager.getPowerDialogItems();
            if ((64 | powerDialogItems2) == powerDialogItems2) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "restart"));
            }
            int powerDialogItems3 = this.mProKioskManagerWrapper.mProKioskManager.getPowerDialogItems();
            if ((128 | powerDialogItems3) == powerDialogItems3) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3) {
                samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, ImsProfile.PDN_EMERGENCY));
            }
            int powerDialogItems4 = this.mProKioskManagerWrapper.mProKioskManager.getPowerDialogItems();
            if ((256 | powerDialogItems4) == powerDialogItems4) {
                z4 = true;
            }
            if (z4) {
                ActionViewModel createActionViewModel = this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "bug_report");
                createActionViewModel.getActionInfo().setViewType(ViewType.BOTTOM_BTN_LIST_VIEW);
                samsungGlobalActions.addAction(createActionViewModel);
            }
            if (this.mProKioskManagerWrapper.mProKioskManager.getPowerDialogOptionMode() == 2) {
                this.mProKioskManagerWrapper.mProKioskOptionShown = true;
                samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "pro_kiosk"));
            }
        } else {
            samsungGlobalActions.setOverrideDefaultActions(false);
        }
        if (this.mConditionChecker.isEnabled(SystemUIConditions.GET_POWER_DIALOG_CUSTOM_ITEMS_STATE)) {
            samsungGlobalActions.clearActions("knox_custom");
            KnoxCustomManagerWrapper knoxCustomManagerWrapper = this.mKnoxCustomManagerWrapper;
            knoxCustomManagerWrapper.getClass();
            ArrayList arrayList = new ArrayList();
            List<PowerItem> powerDialogCustomItems = knoxCustomManagerWrapper.mKnoxCustomManager.getPowerDialogCustomItems();
            if (powerDialogCustomItems != null) {
                Iterator<PowerItem> it = powerDialogCustomItems.iterator();
                while (it.hasNext()) {
                    arrayList.add(new PowerItemWrapper(it.next()));
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                PowerItemWrapper powerItemWrapper = (PowerItemWrapper) it2.next();
                ActionViewModel createActionViewModel2 = this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "knox_custom");
                createActionViewModel2.setIcon(powerItemWrapper.mPowerItem.mIcon);
                PowerItem powerItem = powerItemWrapper.mPowerItem;
                createActionViewModel2.setText(powerItem.mText);
                createActionViewModel2.setIntent(powerItem.mIntent);
                createActionViewModel2.setIntentAction(powerItem.mIntentAction);
                samsungGlobalActions.addAction(createActionViewModel2);
            }
        }
    }

    public final boolean onCreateEmergencyAction() {
        if (!this.mConditionChecker.isEnabled(SystemUIConditions.IS_KIOSK_MODE) && !this.mConditionChecker.isEnabled(SystemUIConditions.IS_DO_PROVISIONING_MODE) && !this.mConditionChecker.isEnabled(SystemUIConditions.PWD_CHANGE_ENFORCED)) {
            return true;
        }
        return false;
    }

    public final void onInitialize(boolean z) {
        if (!this.mConditionChecker.isEnabled(SystemUIConditions.IS_ALLOWED_SHOW_ACTIONS) && z) {
            this.mLogWrapper.i("KnoxSDKStrategy", "Presenter has been locked by Knox SDK.");
            this.mGlobalActions.setDisabled();
        }
    }

    public final boolean onKeyListenerAction(int i, int i2) {
        if (!this.mGlobalActions.isActionConfirming() && this.mConditionChecker.isEnabled(SystemUIConditions.GET_PROKIOSK_STATE) && this.mProKioskManagerWrapper.mProKioskManager.getPowerDialogOptionMode() == 3 && i == 0 && i2 == 24) {
            ProKioskManagerWrapper proKioskManagerWrapper = this.mProKioskManagerWrapper;
            if (!proKioskManagerWrapper.mProKioskOptionShown) {
                proKioskManagerWrapper.mProKioskOptionShown = true;
                SamsungGlobalActions samsungGlobalActions = this.mGlobalActions;
                samsungGlobalActions.addAction(this.mViewModelFactory.createActionViewModel(samsungGlobalActions, "pro_kiosk"));
                this.mGlobalActions.getGlobalActionsView().updateViewList();
                this.mGlobalActions.getGlobalActionsView().forceRequestLayout();
                return true;
            }
            return false;
        }
        return false;
    }
}
