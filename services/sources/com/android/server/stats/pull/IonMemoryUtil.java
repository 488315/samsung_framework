package com.android.server.stats.pull;

import android.util.Slog;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class IonMemoryUtil {
    public static final Pattern ION_HEAP_SIZE_IN_BYTES = Pattern.compile("\n\\s*total\\s*(\\d+)\\s*\n");
    public static final Pattern PROCESS_ION_HEAP_SIZE_IN_BYTES = Pattern.compile("\n\\s+\\S+\\s+(\\d+)\\s+(\\d+)");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IonAllocations {
        public int count;
        public long maxSizeInBytes;
        public int pid;
        public long totalSizeInBytes;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || IonAllocations.class != obj.getClass()) {
                return false;
            }
            IonAllocations ionAllocations = (IonAllocations) obj;
            return this.pid == ionAllocations.pid && this.totalSizeInBytes == ionAllocations.totalSizeInBytes && this.count == ionAllocations.count && this.maxSizeInBytes == ionAllocations.maxSizeInBytes;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.pid), Long.valueOf(this.totalSizeInBytes), Integer.valueOf(this.count), Long.valueOf(this.maxSizeInBytes));
        }

        public final String toString() {
            return "IonAllocations{pid=" + this.pid + ", totalSizeInBytes=" + this.totalSizeInBytes + ", count=" + this.count + ", maxSizeInBytes=" + this.maxSizeInBytes + '}';
        }
    }

    public static long parseIonHeapSizeFromDebugfs(String str) {
        if (str.isEmpty()) {
            return 0L;
        }
        Matcher matcher = ION_HEAP_SIZE_IN_BYTES.matcher(str);
        try {
            if (matcher.find()) {
                return Long.parseLong(matcher.group(1));
            }
            return 0L;
        } catch (NumberFormatException e) {
            Slog.e("IonMemoryUtil", "Failed to parse value", e);
            return 0L;
        }
    }

    public static List parseProcessIonHeapSizesFromDebugfs(String str) {
        if (str.isEmpty()) {
            return Collections.emptyList();
        }
        Matcher matcher = PROCESS_ION_HEAP_SIZE_IN_BYTES.matcher(str);
        SparseArray sparseArray = new SparseArray();
        while (matcher.find()) {
            try {
                int parseInt = Integer.parseInt(matcher.group(1));
                long parseLong = Long.parseLong(matcher.group(2));
                IonAllocations ionAllocations = (IonAllocations) sparseArray.get(parseInt);
                if (ionAllocations == null) {
                    ionAllocations = new IonAllocations();
                    sparseArray.put(parseInt, ionAllocations);
                }
                ionAllocations.pid = parseInt;
                ionAllocations.totalSizeInBytes += parseLong;
                ionAllocations.count++;
                ionAllocations.maxSizeInBytes = Math.max(ionAllocations.maxSizeInBytes, parseLong);
            } catch (NumberFormatException e) {
                Slog.e("IonMemoryUtil", "Failed to parse value", e);
            }
        }
        ArrayList arrayList = new ArrayList(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add((IonAllocations) sparseArray.valueAt(i));
        }
        return arrayList;
    }
}
