package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceLocalUserSelectionManager implements KeyguardQuickAffordanceSelectionManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final Lazy defaults$delegate;
    public final ChannelFlowTransformLatest selections;
    public SharedPreferences sharedPrefs;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardQuickAffordanceLocalUserSelectionManager(Context context, UserFileManager userFileManager, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.sharedPrefs = ((UserFileManagerImpl) userFileManager).getSharedPreferences(((UserTrackerImpl) userTracker).getUserId(), "quick_affordance_selections");
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardQuickAffordanceLocalUserSelectionManager$userId$1 keyguardQuickAffordanceLocalUserSelectionManager$userId$1 = new KeyguardQuickAffordanceLocalUserSelectionManager$userId$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(keyguardQuickAffordanceLocalUserSelectionManager$userId$1);
        this.defaults$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$defaults$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z;
                String[] stringArray = KeyguardQuickAffordanceLocalUserSelectionManager.this.context.getResources().getStringArray(R.array.config_keyguardQuickAffordanceDefaults);
                int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(stringArray.length);
                if (mapCapacity < 16) {
                    mapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                for (String str : stringArray) {
                    List split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, 0, 6);
                    if (split$default.size() == 2) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        Pair pair = new Pair((String) split$default.get(0), StringsKt__StringsKt.split$default((CharSequence) split$default.get(1), new String[]{","}, 0, 6));
                        linkedHashMap.put(pair.getFirst(), pair.getSecond());
                    } else {
                        throw new IllegalStateException("Check failed.".toString());
                    }
                }
                return linkedHashMap;
            }
        });
        this.selections = FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(conflatedCallbackFlow2, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardQuickAffordanceLocalUserSelectionManager$selections$1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 4, "com.android.systemui.permission.SELF", 2)), new KeyguardQuickAffordanceLocalUserSelectionManager$selections$2(null)), new KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    /* renamed from: getSelections, reason: collision with other method in class */
    public final Flow mo1285getSelections() {
        return this.selections;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final void setSelections(String str, List list) {
        this.sharedPrefs.edit().putString("slot_".concat(str), CollectionsKt___CollectionsKt.joinToString$default(list, ",", null, null, null, 62)).apply();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final Map getSelections() {
        Object obj;
        boolean z = this.context.getResources().getBoolean(R.bool.custom_lockscreen_shortcuts_enabled);
        Lazy lazy = this.defaults$delegate;
        if (!z) {
            return (Map) lazy.getValue();
        }
        Set<String> keySet = this.sharedPrefs.getAll().keySet();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : keySet) {
            if (((String) obj2).startsWith("slot_")) {
                arrayList.add(obj2);
            }
        }
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            String substring = str.substring(5);
            String string = this.sharedPrefs.getString(str, null);
            if (!(string == null || string.length() == 0)) {
                obj = StringsKt__StringsKt.split$default(string, new String[]{","}, 0, 6);
            } else {
                obj = EmptyList.INSTANCE;
            }
            Pair pair = new Pair(substring, obj);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        for (Map.Entry entry : ((Map) lazy.getValue()).entrySet()) {
            String str2 = (String) entry.getKey();
            List list = (List) entry.getValue();
            if (!linkedHashMap2.containsKey(str2)) {
                linkedHashMap2.put(str2, list);
            }
        }
        return linkedHashMap2;
    }
}
