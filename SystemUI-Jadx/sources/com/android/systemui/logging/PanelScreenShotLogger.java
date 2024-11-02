package com.android.systemui.logging;

import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class PanelScreenShotLogger implements Dumpable {
    public static final PanelScreenShotLogger INSTANCE = new PanelScreenShotLogger();
    public static final ArrayList assembledLogs = new ArrayList();
    public static final Map providers = new LinkedHashMap();
    public static final PanelScreenShotBufferLogger panelScreenShotBufferLogger = (PanelScreenShotBufferLogger) Dependency.get(PanelScreenShotBufferLogger.class);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface LogProvider {
        ArrayList gatherState();
    }

    private PanelScreenShotLogger() {
    }

    public static void addHeaderLine(String str, ArrayList arrayList) {
        arrayList.add("\n\n\n############################################");
        arrayList.add("    ".concat(str));
        arrayList.add("############################################\n\n\n");
    }

    public static void addLogItem(ArrayList arrayList, String str, Object obj) {
        arrayList.add(str + " = " + obj);
    }

    public static void makeScreenShotLog() {
        ArrayList arrayList = assembledLogs;
        arrayList.clear();
        arrayList.add("\n");
        Iterator it = ((LinkedHashMap) providers).values().iterator();
        while (it.hasNext()) {
            arrayList.addAll(((LogProvider) it.next()).gatherState());
            arrayList.add("\n");
        }
        INSTANCE.getClass();
        StringBuilder sb = new StringBuilder();
        IntProgressionIterator it2 = new IntRange(0, arrayList.size() - 1).iterator();
        while (it2.hasNext) {
            sb.append((String) arrayList.get(it2.nextInt()));
            sb.append("\n");
        }
        String sb2 = sb.toString();
        PanelScreenShotBufferLogger panelScreenShotBufferLogger2 = panelScreenShotBufferLogger;
        panelScreenShotBufferLogger2.getClass();
        LogLevel logLevel = LogLevel.INFO;
        PanelScreenShotBufferLogger$logPanelScreenShotInfo$2 panelScreenShotBufferLogger$logPanelScreenShotInfo$2 = new Function1() { // from class: com.android.systemui.logging.PanelScreenShotBufferLogger$logPanelScreenShotInfo$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return String.valueOf(((LogMessage) obj).getStr2());
            }
        };
        LogBuffer logBuffer = panelScreenShotBufferLogger2.buffer;
        LogMessage obtain = logBuffer.obtain("PanelScreenShotLog", logLevel, panelScreenShotBufferLogger$logPanelScreenShotInfo$2, null);
        obtain.setStr2(sb2);
        logBuffer.commit(obtain);
    }

    public final synchronized void addLogProvider(String str, LogProvider logProvider) {
        providers.put(str, logProvider);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Iterator it = assembledLogs.iterator();
        while (it.hasNext()) {
            printWriter.println((String) it.next());
        }
    }
}
