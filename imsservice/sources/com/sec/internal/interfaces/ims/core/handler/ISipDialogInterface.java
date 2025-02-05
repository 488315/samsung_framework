package com.sec.internal.interfaces.ims.core.handler;

import android.os.Handler;
import android.os.Message;

/* loaded from: classes.dex */
public interface ISipDialogInterface {
    void openSipDialog(boolean z);

    void registerForIncomingMessages(Handler handler, int i, Object obj);

    void registerForOutgoingMessages(Handler handler, int i, Object obj);

    boolean sendSip(int i, String str, Message message);

    void unregisterForEvent(Handler handler);
}
