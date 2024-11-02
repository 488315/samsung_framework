package com.samsung.android.sdk.command.action;

import android.os.Bundle;
import android.util.Log;
import java.util.UUID;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class CommandAction {
    public static final AnonymousClass1 ERROR_ACTION = new CommandAction() { // from class: com.samsung.android.sdk.command.action.CommandAction.1
        @Override // com.samsung.android.sdk.command.action.CommandAction
        public final int getActionType() {
            return 0;
        }
    };
    public String mActionId;
    public final CommandParam mCommandParam;
    public final String mTemplateId;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.sdk.command.action.CommandAction$1] */
    static {
        new CommandAction() { // from class: com.samsung.android.sdk.command.action.CommandAction.2
            @Override // com.samsung.android.sdk.command.action.CommandAction
            public final int getActionType() {
                return 98;
            }
        };
    }

    public CommandAction() {
        this.mTemplateId = "";
        this.mCommandParam = new CommandParam();
    }

    public static CommandAction createActionFromBundle(Bundle bundle) {
        AnonymousClass1 anonymousClass1 = ERROR_ACTION;
        if (bundle == null) {
            return anonymousClass1;
        }
        int i = bundle.getInt("key_action_type", 0);
        try {
            if (i != 98) {
                if (i != 99) {
                    switch (i) {
                        case 1:
                            return new BooleanAction(bundle);
                        case 2:
                            return new FloatAction(bundle);
                        case 3:
                            return new IntentAction(bundle);
                        case 4:
                            return new StringAction(bundle);
                        case 5:
                            return new JSONStringAction(bundle);
                        case 6:
                            return new ModeAction(bundle);
                        default:
                            return anonymousClass1;
                    }
                }
                return new TriggerAction(bundle);
            }
            return new DefaultAction(bundle);
        } catch (Exception unused) {
            return anonymousClass1;
        }
    }

    public String getActionTemplateId() {
        return this.mTemplateId;
    }

    public abstract int getActionType();

    public Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        if (this.mActionId == null) {
            this.mActionId = UUID.randomUUID().toString();
        }
        bundle.putString("key_action_id", this.mActionId);
        bundle.putInt("key_action_type", getActionType());
        bundle.putString("key_template_id", getActionTemplateId());
        CommandParam commandParam = this.mCommandParam;
        commandParam.getClass();
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("dex_mode", commandParam.mDexMode);
        bundle.putBundle("command_param", bundle2);
        return bundle;
    }

    public CommandAction(Bundle bundle) {
        this.mActionId = bundle.getString("key_action_id");
        this.mTemplateId = bundle.getString("key_template_id");
        CommandParam commandParam = new CommandParam();
        Bundle bundle2 = bundle.getBundle("command_param");
        if (bundle2 == null) {
            Log.w("CommandLib/CommandParam", "commandParamBundle is empty");
        } else {
            commandParam.mDexMode = bundle2.getBoolean("dex_mode", false);
        }
        this.mCommandParam = commandParam;
    }

    public CommandAction(String str) {
        this.mTemplateId = str;
        this.mCommandParam = new CommandParam();
    }
}
