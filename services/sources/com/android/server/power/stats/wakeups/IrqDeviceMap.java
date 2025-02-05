package com.android.server.power.stats.wakeups;

import android.content.res.XmlResourceParser;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.LongSparseArray;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IrqDeviceMap {
    public static final LongSparseArray sInstanceMap = new LongSparseArray(1);
    public final ArrayMap mSubsystemsForDevice = new ArrayMap();

    public IrqDeviceMap(XmlResourceParser xmlResourceParser) {
        try {
            try {
                try {
                    XmlUtils.beginDocument(xmlResourceParser, "irq-device-map");
                    ArraySet arraySet = new ArraySet();
                    String str = null;
                    while (true) {
                        int eventType = xmlResourceParser.getEventType();
                        if (eventType == 1) {
                            xmlResourceParser.close();
                            return;
                        }
                        if (eventType == 2 && xmlResourceParser.getName().equals("device")) {
                            str = xmlResourceParser.getAttributeValue(null, "name");
                        }
                        if (str != null && eventType == 3 && xmlResourceParser.getName().equals("device")) {
                            if (arraySet.size() > 0) {
                                this.mSubsystemsForDevice.put(str, Collections.unmodifiableList(new ArrayList(arraySet)));
                            }
                            arraySet.clear();
                            str = null;
                        }
                        if (str != null && eventType == 2 && xmlResourceParser.getName().equals("subsystem")) {
                            xmlResourceParser.next();
                            if (xmlResourceParser.getEventType() == 4) {
                                arraySet.add(xmlResourceParser.getText());
                            }
                        }
                        xmlResourceParser.next();
                    }
                } catch (XmlPullParserException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            xmlResourceParser.close();
            throw th;
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        LongSparseArray longSparseArray;
        indentingPrintWriter.println("Irq device map:");
        indentingPrintWriter.increaseIndent();
        synchronized (IrqDeviceMap.class) {
            longSparseArray = sInstanceMap;
        }
        int indexOfValue = longSparseArray.indexOfValue(this);
        indentingPrintWriter.println("Loaded from xml resource: " + (indexOfValue >= 0 ? "0x" + Long.toHexString(longSparseArray.keyAt(indexOfValue)) : null));
        indentingPrintWriter.println("Map:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mSubsystemsForDevice.size(); i++) {
            indentingPrintWriter.print(((String) this.mSubsystemsForDevice.keyAt(i)) + ": ");
            indentingPrintWriter.println(this.mSubsystemsForDevice.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }
}
