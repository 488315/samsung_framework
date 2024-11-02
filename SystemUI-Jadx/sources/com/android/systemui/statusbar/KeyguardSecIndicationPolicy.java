package com.android.systemui.statusbar;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class KeyguardSecIndicationPolicy implements Dumpable {
    public final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public int mItemIdSeq = 0;
    public final HashMap mTopItemMap = new HashMap();
    public final HashMap mIndicationMap = new HashMap();
    public final ArrayList mListenerList = new ArrayList();
    public final List mDumpLogs = new ArrayList();

    public final void addIndicationEvent(IndicationPosition indicationPosition, IndicationEventType indicationEventType, CharSequence charSequence, ColorStateList colorStateList, long j, boolean z) {
        if (indicationEventType != IndicationEventType.EMPTY_LOW && indicationEventType != IndicationEventType.EMPTY_HIGH && TextUtils.isEmpty(charSequence)) {
            addLogs(String.format("%12s pos = %7s, type = %20s", "remove e mpty", indicationPosition, indicationEventType));
            removeIndicationEvent(indicationPosition, indicationEventType);
            return;
        }
        int i = this.mItemIdSeq;
        this.mItemIdSeq = i + 1;
        PriorityQueue indicationList = getIndicationList(indicationPosition);
        if (j == -1) {
            if (indicationList.stream().filter(new KeyguardSecIndicationPolicy$$ExternalSyntheticLambda0(indicationEventType, 1)).count() > 0) {
                addLogs(String.format("%12s pos = %7s, type = %20s, id = %5d, text = %s", "update Event", indicationPosition, indicationEventType, Integer.valueOf(i), charSequence));
                indicationList.removeIf(new KeyguardSecIndicationPolicy$$ExternalSyntheticLambda0(indicationEventType, 2));
            } else {
                addLogs(String.format("%12s pos = %7s, type = %20s, id = %5d, text = %s", "add Event", indicationPosition, indicationEventType, Integer.valueOf(i), charSequence));
            }
            indicationList.add(new IndicationItem(i, indicationEventType, charSequence, colorStateList, -1L, z));
        } else {
            addLogs(String.format("%12s pos = %7s, type = %20s, id = %5d, text = %s, duration = %d", "add T Event", indicationPosition, indicationEventType, Integer.valueOf(i), charSequence, Long.valueOf(j)));
            indicationList.add(new IndicationItem(i, indicationEventType, charSequence, colorStateList, j, z));
        }
        updateTopItem(indicationPosition);
    }

    public final void addLogs(String str) {
        List list = this.mDumpLogs;
        ((ArrayList) list).add(this.mDateFormat.format(new Date(System.currentTimeMillis())) + " " + str);
        if (((ArrayList) list).size() > 100) {
            ((ArrayList) list).remove(0);
        }
        if (!str.contains("OWNER_INFO")) {
            Log.d("KeyguardSecIndicationPolicy", str);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardSecIndicationPolicy history");
        Iterator it = ((ArrayList) this.mDumpLogs).iterator();
        while (it.hasNext()) {
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m("  ", (String) it.next(), printWriter);
        }
    }

    public final PriorityQueue getIndicationList(IndicationPosition indicationPosition) {
        if (!this.mIndicationMap.containsKey(indicationPosition)) {
            synchronized (this.mIndicationMap) {
                if (!this.mIndicationMap.containsKey(indicationPosition)) {
                    PriorityQueue priorityQueue = new PriorityQueue();
                    this.mIndicationMap.put(indicationPosition, priorityQueue);
                    return priorityQueue;
                }
            }
        }
        return (PriorityQueue) this.mIndicationMap.get(indicationPosition);
    }

    public final void removeAllIndications() {
        addLogs(String.format("%12s", "removeAll"));
        IndicationPosition[] indicationPositionArr = {IndicationPosition.UPPER, IndicationPosition.DEFAULT};
        for (int i = 0; i < 2; i++) {
            IndicationPosition indicationPosition = indicationPositionArr[i];
            getIndicationList(indicationPosition).removeIf(new KeyguardSecIndicationPolicy$$ExternalSyntheticLambda2());
            updateTopItem(indicationPosition);
        }
    }

    public final void removeIndicationEvent(IndicationPosition indicationPosition, IndicationEventType indicationEventType) {
        addLogs(String.format("%12s pos = %7s, type = %20s", "remove Item", indicationPosition, indicationEventType));
        getIndicationList(indicationPosition).removeIf(new KeyguardSecIndicationPolicy$$ExternalSyntheticLambda0(indicationEventType, 0));
        updateTopItem(indicationPosition);
    }

    public final void updateTopItem(final IndicationPosition indicationPosition) {
        final IndicationItem indicationItem;
        PriorityQueue indicationList = getIndicationList(indicationPosition);
        HashMap hashMap = this.mTopItemMap;
        IndicationItem indicationItem2 = (IndicationItem) hashMap.get(indicationPosition);
        if (indicationList.size() > 0) {
            indicationItem = (IndicationItem) indicationList.peek();
        } else {
            indicationItem = null;
        }
        if (indicationItem2 != indicationItem || (indicationItem2 != null && indicationItem != null && indicationItem2.mItemId != indicationItem.mItemId)) {
            addLogs(String.format("%12s pos = %7s, item = %s -> %s", "update top", indicationPosition, indicationItem2, indicationItem));
            hashMap.put(indicationPosition, indicationItem);
            boolean z = true;
            this.mListenerList.stream().forEach(new KeyguardSecIndicationPolicy$$ExternalSyntheticLambda1(1, indicationPosition, indicationItem));
            if (indicationItem != null) {
                long j = indicationItem.mDurationTime;
                if (j != -1) {
                    z = false;
                }
                if (!z) {
                    Completable.timer(j, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationPolicy$$ExternalSyntheticLambda3
                        @Override // io.reactivex.functions.Action
                        public final void run() {
                            KeyguardSecIndicationPolicy keyguardSecIndicationPolicy = KeyguardSecIndicationPolicy.this;
                            keyguardSecIndicationPolicy.getClass();
                            final int i = indicationItem.mItemId;
                            Integer valueOf = Integer.valueOf(i);
                            IndicationPosition indicationPosition2 = indicationPosition;
                            keyguardSecIndicationPolicy.addLogs(String.format("%12s pos = %7s %28s, id = %5d, ", "remove Item", indicationPosition2, "", valueOf));
                            keyguardSecIndicationPolicy.getIndicationList(indicationPosition2).removeIf(new Predicate() { // from class: com.android.systemui.statusbar.KeyguardSecIndicationPolicy$$ExternalSyntheticLambda4
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    if (((IndicationItem) obj).mItemId == i) {
                                        return true;
                                    }
                                    return false;
                                }
                            });
                            keyguardSecIndicationPolicy.updateTopItem(indicationPosition2);
                        }
                    });
                }
            }
        }
    }
}
