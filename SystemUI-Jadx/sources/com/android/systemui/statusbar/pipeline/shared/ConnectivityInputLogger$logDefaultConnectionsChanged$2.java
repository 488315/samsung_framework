package com.android.systemui.statusbar.pipeline.shared;

import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
final /* synthetic */ class ConnectivityInputLogger$logDefaultConnectionsChanged$2 extends FunctionReferenceImpl implements Function1 {
    public ConnectivityInputLogger$logDefaultConnectionsChanged$2(Object obj) {
        super(1, obj, DefaultConnectionModel.class, "messagePrinter", "messagePrinter(Lcom/android/systemui/log/LogMessage;)Ljava/lang/String;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        String str;
        LogMessage logMessage = (LogMessage) obj;
        ((DefaultConnectionModel) this.receiver).getClass();
        boolean bool1 = logMessage.getBool1();
        boolean bool2 = logMessage.getBool2();
        boolean bool3 = logMessage.getBool3();
        boolean bool4 = logMessage.getBool4();
        boolean bool5 = logMessage.getBool5();
        if (logMessage.getInt1() == 1) {
            str = "true";
        } else {
            str = "false";
        }
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("DefaultConnectionModel(wifi.isDefault=", bool1, ", mobile.isDefault=", bool2, ", carrierMerged.isDefault=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m(m, bool3, ", ethernet.isDefault=", bool4, ", btTether.isDefault=");
        m.append(bool5);
        m.append(", isValidated=");
        m.append(str);
        m.append(")");
        return m.toString();
    }
}
