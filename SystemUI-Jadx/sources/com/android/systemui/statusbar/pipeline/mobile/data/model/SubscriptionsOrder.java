package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubscriptionsOrder {
    public final SubscriptionManager subscriptionManager;
    public final Map subscriptionsOrder = new LinkedHashMap();

    public SubscriptionsOrder(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    public final int getSimOrder(int i, List list) {
        int slotIndex;
        Map map = this.subscriptionsOrder;
        ((LinkedHashMap) map).clear();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            if (subscriptionInfo != null) {
                arrayList.add(subscriptionInfo);
            }
        }
        if (arrayList.isEmpty()) {
            return 0;
        }
        Iterator it2 = arrayList.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            if (((SubscriptionInfo) it2.next()).isEmbedded()) {
                i2++;
            }
        }
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 == 2) {
                    if (((SubscriptionInfo) arrayList.get(0)).getSubscriptionId() > ((SubscriptionInfo) arrayList.get(1)).getSubscriptionId()) {
                        map.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(0)).getSubscriptionId()), 1);
                        map.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(1)).getSubscriptionId()), 0);
                    } else {
                        map.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(0)).getSubscriptionId()), 0);
                        map.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(1)).getSubscriptionId()), 1);
                    }
                }
            } else if (arrayList.size() == 1) {
                map.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(0)).getSubscriptionId()), 0);
            } else if (arrayList.size() == 2) {
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) it3.next();
                    if (!subscriptionInfo2.isEmbedded()) {
                        map.put(Integer.valueOf(subscriptionInfo2.getSubscriptionId()), 0);
                    } else {
                        map.put(Integer.valueOf(subscriptionInfo2.getSubscriptionId()), 1);
                    }
                }
            }
        } else {
            Iterator it4 = arrayList.iterator();
            while (it4.hasNext()) {
                SubscriptionInfo subscriptionInfo3 = (SubscriptionInfo) it4.next();
                Integer valueOf = Integer.valueOf(subscriptionInfo3.getSubscriptionId());
                if (SubscriptionManager.getSlotIndex(subscriptionInfo3.getSubscriptionId()) == -1) {
                    slotIndex = 0;
                } else {
                    slotIndex = SubscriptionManager.getSlotIndex(subscriptionInfo3.getSubscriptionId());
                }
                map.put(valueOf, Integer.valueOf(slotIndex));
            }
        }
        if (!(true ^ map.isEmpty())) {
            return 0;
        }
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        if (linkedHashMap.get(Integer.valueOf(i)) == null) {
            return 0;
        }
        Object obj = linkedHashMap.get(Integer.valueOf(i));
        Intrinsics.checkNotNull(obj);
        return ((Number) obj).intValue();
    }

    public final int getSimOrderByIds(int i, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SubscriptionInfo activeSubscriptionInfo = this.subscriptionManager.getActiveSubscriptionInfo(((Number) it.next()).intValue());
            if (activeSubscriptionInfo != null) {
                arrayList.add(activeSubscriptionInfo);
            }
        }
        return getSimOrder(i, arrayList);
    }
}
