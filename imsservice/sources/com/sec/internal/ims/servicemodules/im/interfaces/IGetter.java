package com.sec.internal.ims.servicemodules.im.interfaces;

import android.net.Network;
import com.sec.ims.util.ImsUri;
import com.sec.internal.constants.ims.servicemodules.im.ImDirection;
import com.sec.internal.ims.servicemodules.im.MessageBase;
import com.sec.internal.ims.servicemodules.im.strategy.IMnoStrategy;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public interface IGetter extends IModuleInterface {
    List<MessageBase> getAllPendingMessages(String str);

    MessageBase getMessage(int i);

    MessageBase getMessage(String str, ImDirection imDirection, String str2);

    List<MessageBase> getMessages(Collection<String> collection);

    List<MessageBase> getMessages(Collection<String> collection, ImDirection imDirection, String str);

    Network getNetwork(int i);

    Set<ImsUri> getOwnUris(int i);

    MessageBase getPendingMessage(int i);

    IMnoStrategy getRcsStrategy();

    IMnoStrategy getRcsStrategy(int i);

    ImsUri normalizeUri(ImsUri imsUri);

    String onRequestIncomingFtTransferPath();
}
