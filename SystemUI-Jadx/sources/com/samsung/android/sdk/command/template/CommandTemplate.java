package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class CommandTemplate {
    public final String mTemplateId;
    public static final AnonymousClass1 NO_TEMPLATE = new CommandTemplate() { // from class: com.samsung.android.sdk.command.template.CommandTemplate.1
        @Override // com.samsung.android.sdk.command.template.CommandTemplate
        public final int getTemplateType() {
            return 1;
        }
    };
    public static final AnonymousClass2 ERROR_TEMPLATE = new CommandTemplate() { // from class: com.samsung.android.sdk.command.template.CommandTemplate.2
        @Override // com.samsung.android.sdk.command.template.CommandTemplate
        public final int getTemplateType() {
            return 0;
        }
    };

    public CommandTemplate() {
        this.mTemplateId = "";
    }

    public Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("key_template_id", this.mTemplateId);
        bundle.putInt("key_template_type", getTemplateType());
        return bundle;
    }

    public int getTemplateType() {
        return 0;
    }

    public CommandTemplate(Bundle bundle) {
        this.mTemplateId = bundle.getString("key_template_id", "");
    }

    public CommandTemplate(String str) {
        this.mTemplateId = str;
    }
}
