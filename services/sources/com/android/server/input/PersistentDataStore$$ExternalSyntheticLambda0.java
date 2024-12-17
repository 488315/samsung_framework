package com.android.server.input;

import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import com.android.server.input.PersistentDataStore;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PersistentDataStore$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IndentingPrintWriter f$0;

    public /* synthetic */ PersistentDataStore$$ExternalSyntheticLambda0(IndentingPrintWriter indentingPrintWriter, int i) {
        this.$r8$classId = i;
        this.f$0 = indentingPrintWriter;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        IndentingPrintWriter indentingPrintWriter = this.f$0;
        String str = (String) obj;
        switch (i) {
            case 0:
                PersistentDataStore.InputDeviceState inputDeviceState = (PersistentDataStore.InputDeviceState) obj2;
                indentingPrintWriter.println("InputDevice (" + str + ")");
                indentingPrintWriter.increaseIndent();
                if (!((ArrayMap) inputDeviceState.mKeyboardLayoutMap).isEmpty()) {
                    indentingPrintWriter.println("Keyboard layout map:");
                    ((ArrayMap) inputDeviceState.mKeyboardLayoutMap).forEach(new PersistentDataStore$$ExternalSyntheticLambda0(indentingPrintWriter, 1));
                }
                if (inputDeviceState.mSelectedKeyboardLayouts != null) {
                    indentingPrintWriter.println("Selected keyboard layouts:");
                    Iterator it = ((HashSet) inputDeviceState.mSelectedKeyboardLayouts).iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println((String) it.next());
                    }
                }
                indentingPrintWriter.decreaseIndent();
                break;
            default:
                indentingPrintWriter.println("  key: " + str);
                indentingPrintWriter.println("  layout: " + ((String) obj2));
                break;
        }
    }
}
