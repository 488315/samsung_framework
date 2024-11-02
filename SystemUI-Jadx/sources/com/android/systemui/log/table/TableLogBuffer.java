package com.android.systemui.log.table;

import android.os.Trace;
import com.android.systemui.Dumpable;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.table.TableChange;
import com.android.systemui.plugins.log.TableLogBufferBase;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.AbstractChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class TableLogBuffer implements Dumpable, TableLogBufferBase {
    public final CoroutineDispatcher bgDispatcher;
    public final RingBuffer buffer;
    public final CoroutineScope coroutineScope;
    public final Map lastEvictedValues;
    public final LogProxy localLogcat;
    public final AbstractChannel logMessageChannel;
    public final LogcatEchoTracker logcatEchoTracker;
    public final String name;
    public final SystemClock systemClock;
    public final TableRowLoggerImpl tempRow;

    public TableLogBuffer(int i, String str, SystemClock systemClock, LogcatEchoTracker logcatEchoTracker, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, LogProxy logProxy) {
        this.name = str;
        this.systemClock = systemClock;
        this.logcatEchoTracker = logcatEchoTracker;
        this.bgDispatcher = coroutineDispatcher;
        this.coroutineScope = coroutineScope;
        this.localLogcat = logProxy;
        if (i > 0) {
            this.logMessageChannel = ChannelKt.Channel$default(10, null, 6);
            this.buffer = new RingBuffer(i, new Function0() { // from class: com.android.systemui.log.table.TableLogBuffer$buffer$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new TableChange(0L, null, null, false, null, false, null, null, 255, null);
                }
            });
            this.lastEvictedValues = new LinkedHashMap();
            this.tempRow = new TableRowLoggerImpl(0L, "", false, this);
            return;
        }
        throw new IllegalArgumentException("maxSize must be > 0");
    }

    public static void verifyValidName(String str, String str2) {
        if (!StringsKt__StringsKt.contains(str, "|", false)) {
            if (!StringsKt__StringsKt.contains(str2, "|", false)) {
                return;
            } else {
                throw new IllegalArgumentException("columnName cannot contain | but was ".concat(str2));
            }
        }
        throw new IllegalArgumentException("columnPrefix cannot contain | but was ".concat(str));
    }

    @Override // com.android.systemui.Dumpable
    public final synchronized void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SystemUI StateChangeTableSection START: " + this.name);
        printWriter.println("version 1");
        Iterator it = CollectionsKt___CollectionsKt.sortedWith(((LinkedHashMap) this.lastEvictedValues).values(), new Comparator() { // from class: com.android.systemui.log.table.TableLogBuffer$dump$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((TableChange) obj).timestamp), Long.valueOf(((TableChange) obj2).timestamp));
            }
        }).iterator();
        while (it.hasNext()) {
            dump((TableChange) it.next(), printWriter);
        }
        int size = this.buffer.getSize();
        for (int i = 0; i < size; i++) {
            dump((TableChange) this.buffer.get(i), printWriter);
        }
        printWriter.println("SystemUI StateChangeTableSection END: " + this.name);
    }

    @Override // com.android.systemui.plugins.log.TableLogBufferBase
    public final void logChange(String str, String str2, Integer num) {
        TableLogBufferBase.DefaultImpls.logChange(this, str, str2, num);
    }

    public final synchronized TableChange obtain(long j, String str, String str2, boolean z) {
        TableChange tableChange;
        verifyValidName(str, str2);
        tableChange = (TableChange) this.buffer.advance();
        if (tableChange.hasData()) {
            saveEvictedValue(tableChange);
        }
        tableChange.reset(j, str, str2, z);
        return tableChange;
    }

    public final void saveEvictedValue(TableChange tableChange) {
        String str;
        Trace.beginSection("TableLogBuffer#saveEvictedValue");
        String name = tableChange.getName();
        Map map = this.lastEvictedValues;
        TableChange tableChange2 = (TableChange) ((LinkedHashMap) map).get(name);
        if (tableChange2 == null) {
            tableChange2 = new TableChange(0L, null, null, false, null, false, null, null, 255, null);
            map.put(name, tableChange2);
        }
        tableChange2.reset(tableChange.timestamp, tableChange.columnPrefix, tableChange.columnName, tableChange.isInitial);
        int i = TableChange.WhenMappings.$EnumSwitchMapping$0[tableChange.type.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    boolean z = tableChange.bool;
                    tableChange2.type = TableChange.DataType.BOOLEAN;
                    tableChange2.bool = z;
                }
            } else {
                Integer num = tableChange.f5int;
                tableChange2.type = TableChange.DataType.INT;
                tableChange2.f5int = num;
            }
        } else {
            String str2 = tableChange.str;
            tableChange2.type = TableChange.DataType.STRING;
            if (str2 != null) {
                str = StringsKt___StringsKt.take(str2);
            } else {
                str = null;
            }
            tableChange2.str = str;
        }
        Trace.endSection();
    }

    @Override // com.android.systemui.plugins.log.TableLogBufferBase
    public final void logChange(String str, String str2, String str3) {
        TableLogBufferBase.DefaultImpls.logChange(this, str, str2, str3);
    }

    @Override // com.android.systemui.plugins.log.TableLogBufferBase
    public final void logChange(String str, String str2, boolean z) {
        TableLogBufferBase.DefaultImpls.logChange(this, str, str2, z);
    }

    @Override // com.android.systemui.plugins.log.TableLogBufferBase
    public final void logChange(String str, String str2, String str3, boolean z) {
        ((SystemClockImpl) this.systemClock).getClass();
        long currentTimeMillis = System.currentTimeMillis();
        Trace.beginSection("TableLogBuffer#logChange(string)");
        TableChange obtain = obtain(currentTimeMillis, str, str2, z);
        obtain.type = TableChange.DataType.STRING;
        obtain.str = str3 != null ? StringsKt___StringsKt.take(str3) : null;
        this.logMessageChannel.mo2584trySendJP2dKIU(obtain);
        Trace.endSection();
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class TableRowLoggerImpl {
        public String columnPrefix;
        public boolean isInitial;
        public final TableLogBuffer tableLogBuffer;
        public long timestamp;

        public TableRowLoggerImpl(long j, String str, boolean z, TableLogBuffer tableLogBuffer) {
            this.timestamp = j;
            this.columnPrefix = str;
            this.isInitial = z;
            this.tableLogBuffer = tableLogBuffer;
        }

        public final void logChange(String str, String str2) {
            long j = this.timestamp;
            String str3 = this.columnPrefix;
            boolean z = this.isInitial;
            TableLogBuffer tableLogBuffer = this.tableLogBuffer;
            tableLogBuffer.getClass();
            Trace.beginSection("TableLogBuffer#logChange(string)");
            TableChange obtain = tableLogBuffer.obtain(j, str3, str, z);
            obtain.type = TableChange.DataType.STRING;
            obtain.str = str2 != null ? StringsKt___StringsKt.take(str2) : null;
            tableLogBuffer.logMessageChannel.mo2584trySendJP2dKIU(obtain);
            Trace.endSection();
        }

        public final void logChange(String str, boolean z) {
            long j = this.timestamp;
            String str2 = this.columnPrefix;
            boolean z2 = this.isInitial;
            TableLogBuffer tableLogBuffer = this.tableLogBuffer;
            tableLogBuffer.getClass();
            Trace.beginSection("TableLogBuffer#logChange(boolean)");
            TableChange obtain = tableLogBuffer.obtain(j, str2, str, z2);
            obtain.type = TableChange.DataType.BOOLEAN;
            obtain.bool = z;
            tableLogBuffer.logMessageChannel.mo2584trySendJP2dKIU(obtain);
            Trace.endSection();
        }

        public final void logChange(int i, String str) {
            long j = this.timestamp;
            String str2 = this.columnPrefix;
            Integer valueOf = Integer.valueOf(i);
            boolean z = this.isInitial;
            TableLogBuffer tableLogBuffer = this.tableLogBuffer;
            tableLogBuffer.getClass();
            Trace.beginSection("TableLogBuffer#logChange(int)");
            TableChange obtain = tableLogBuffer.obtain(j, str2, str, z);
            obtain.type = TableChange.DataType.INT;
            obtain.f5int = valueOf;
            tableLogBuffer.logMessageChannel.mo2584trySendJP2dKIU(obtain);
            Trace.endSection();
        }
    }

    public static void dump(TableChange tableChange, PrintWriter printWriter) {
        if (tableChange.hasData()) {
            printWriter.print(TableLogBufferKt.TABLE_LOG_DATE_FORMAT.format(Long.valueOf(tableChange.timestamp)));
            printWriter.print("|");
            printWriter.print(tableChange.getName());
            printWriter.print("|");
            printWriter.print(tableChange.getVal());
            printWriter.println();
        }
    }

    @Override // com.android.systemui.plugins.log.TableLogBufferBase
    public final void logChange(String str, String str2, boolean z, boolean z2) {
        ((SystemClockImpl) this.systemClock).getClass();
        long currentTimeMillis = System.currentTimeMillis();
        Trace.beginSection("TableLogBuffer#logChange(boolean)");
        TableChange obtain = obtain(currentTimeMillis, str, str2, z2);
        obtain.type = TableChange.DataType.BOOLEAN;
        obtain.bool = z;
        this.logMessageChannel.mo2584trySendJP2dKIU(obtain);
        Trace.endSection();
    }

    public /* synthetic */ TableLogBuffer(int i, String str, SystemClock systemClock, LogcatEchoTracker logcatEchoTracker, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, LogProxy logProxy, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, systemClock, logcatEchoTracker, coroutineDispatcher, coroutineScope, (i2 & 64) != 0 ? new LogProxyDefault() : logProxy);
    }

    @Override // com.android.systemui.plugins.log.TableLogBufferBase
    public final void logChange(String str, String str2, Integer num, boolean z) {
        ((SystemClockImpl) this.systemClock).getClass();
        long currentTimeMillis = System.currentTimeMillis();
        Trace.beginSection("TableLogBuffer#logChange(int)");
        TableChange obtain = obtain(currentTimeMillis, str, str2, z);
        obtain.type = TableChange.DataType.INT;
        obtain.f5int = num;
        this.logMessageChannel.mo2584trySendJP2dKIU(obtain);
        Trace.endSection();
    }
}
