package com.att.iqi.libs;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Xml;
import com.att.iqi.libs.PreferenceStore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public class PreferenceStore {
    private static final String IQI_PREF_FILE = "att_prefs.xml";
    public static final String PREF_BRIDGE_LIBRARY_PATH = "bridge_path";
    public static final String PREF_DISABLE_MCC_MNC_VALIDATION = "disable_mcc_mnc_validation";
    public static final String PREF_LOGS_ENABLED = "log_enabled";
    public static final String PREF_SERVICE_STATE = "service_state";
    public static final int SERVICE_STATE_OFF = 0;
    public static final int SERVICE_STATE_ON = 1;
    private static final Object mLock = new Object();
    private static PreferenceStore sInstance;
    private static final Map sPreferences;
    private final List mPreferenceChangeListeners = new ArrayList();
    private final File mPreferenceFile = new File(new File(Environment.getDataDirectory(), "system"), IQI_PREF_FILE);

    /* loaded from: classes3.dex */
    public interface PreferenceChangeListener {
        void onPreferenceChanged(String str);
    }

    static {
        HashMap hashMap = new HashMap();
        sPreferences = hashMap;
        Boolean bool = Boolean.FALSE;
        hashMap.put(PREF_DISABLE_MCC_MNC_VALIDATION, bool);
        hashMap.put(PREF_LOGS_ENABLED, bool);
        hashMap.put(PREF_SERVICE_STATE, 1);
        hashMap.put(PREF_BRIDGE_LIBRARY_PATH, "");
    }

    private PreferenceStore() {
        loadPrefsFromFileLocked();
    }

    public static PreferenceStore getInstance() {
        PreferenceStore preferenceStore;
        synchronized (mLock) {
            if (sInstance == null) {
                sInstance = new PreferenceStore();
            }
            preferenceStore = sInstance;
        }
        return preferenceStore;
    }

    private boolean createPrefStoreLocked() {
        try {
            if (!this.mPreferenceFile.createNewFile()) {
                if (LogUtil.canLog()) {
                    LogUtil.loge("Failed to create prefs file!");
                }
                return false;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(this.mPreferenceFile);
            try {
                XmlSerializer newSerializer = Xml.newSerializer();
                newSerializer.setOutput(fileOutputStream, "UTF-8");
                newSerializer.startDocument(null, Boolean.TRUE);
                newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                newSerializer.startTag(null, "map");
                newSerializer.attribute(null, "name", "prefs");
                newSerializer.startTag(null, "boolean");
                newSerializer.attribute(null, "name", PREF_DISABLE_MCC_MNC_VALIDATION);
                newSerializer.attribute(null, "value", "false");
                newSerializer.endTag(null, "boolean");
                newSerializer.startTag(null, "boolean");
                newSerializer.attribute(null, "name", PREF_LOGS_ENABLED);
                newSerializer.attribute(null, "value", "false");
                newSerializer.endTag(null, "boolean");
                newSerializer.startTag(null, "int");
                newSerializer.attribute(null, "name", PREF_SERVICE_STATE);
                newSerializer.attribute(null, "value", "1");
                newSerializer.endTag(null, "int");
                newSerializer.startTag(null, "string");
                newSerializer.attribute(null, "name", PREF_BRIDGE_LIBRARY_PATH);
                newSerializer.text("");
                newSerializer.endTag(null, "string");
                newSerializer.endTag(null, "map");
                newSerializer.endDocument();
                newSerializer.flush();
                return true;
            } catch (Throwable th) {
                try {
                    if (LogUtil.canLog()) {
                        LogUtil.loge("Exception thrown while creating pref file", th);
                    }
                    try {
                        fileOutputStream.close();
                    } catch (Exception e) {
                        if (LogUtil.canLog()) {
                            LogUtil.loge("Error occurred while closing output stream", e);
                        }
                    }
                    return false;
                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        if (LogUtil.canLog()) {
                            LogUtil.loge("Error occurred while closing output stream", e2);
                        }
                    }
                }
            }
        } catch (IOException e3) {
            if (LogUtil.canLog()) {
                LogUtil.loge("Unable to open stream to write prefs", e3);
            }
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x009d A[Catch: all -> 0x0125, Exception -> 0x0127, TRY_ENTER, TryCatch #4 {Exception -> 0x0127, blocks: (B:14:0x0023, B:19:0x0039, B:21:0x003f, B:23:0x004e, B:25:0x0057, B:28:0x0060, B:29:0x008b, B:32:0x009d, B:39:0x00c6, B:41:0x00cc, B:45:0x00e1, B:46:0x00e7, B:47:0x00f5, B:48:0x0103, B:49:0x00a5, B:52:0x00ad, B:55:0x00b5, B:60:0x0065, B:62:0x006b, B:63:0x007f, B:67:0x0084, B:42:0x0110), top: B:13:0x0023, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0103 A[Catch: all -> 0x0125, Exception -> 0x0127, TryCatch #4 {Exception -> 0x0127, blocks: (B:14:0x0023, B:19:0x0039, B:21:0x003f, B:23:0x004e, B:25:0x0057, B:28:0x0060, B:29:0x008b, B:32:0x009d, B:39:0x00c6, B:41:0x00cc, B:45:0x00e1, B:46:0x00e7, B:47:0x00f5, B:48:0x0103, B:49:0x00a5, B:52:0x00ad, B:55:0x00b5, B:60:0x0065, B:62:0x006b, B:63:0x007f, B:67:0x0084, B:42:0x0110), top: B:13:0x0023, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a5 A[Catch: all -> 0x0125, Exception -> 0x0127, TryCatch #4 {Exception -> 0x0127, blocks: (B:14:0x0023, B:19:0x0039, B:21:0x003f, B:23:0x004e, B:25:0x0057, B:28:0x0060, B:29:0x008b, B:32:0x009d, B:39:0x00c6, B:41:0x00cc, B:45:0x00e1, B:46:0x00e7, B:47:0x00f5, B:48:0x0103, B:49:0x00a5, B:52:0x00ad, B:55:0x00b5, B:60:0x0065, B:62:0x006b, B:63:0x007f, B:67:0x0084, B:42:0x0110), top: B:13:0x0023, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ad A[Catch: all -> 0x0125, Exception -> 0x0127, TryCatch #4 {Exception -> 0x0127, blocks: (B:14:0x0023, B:19:0x0039, B:21:0x003f, B:23:0x004e, B:25:0x0057, B:28:0x0060, B:29:0x008b, B:32:0x009d, B:39:0x00c6, B:41:0x00cc, B:45:0x00e1, B:46:0x00e7, B:47:0x00f5, B:48:0x0103, B:49:0x00a5, B:52:0x00ad, B:55:0x00b5, B:60:0x0065, B:62:0x006b, B:63:0x007f, B:67:0x0084, B:42:0x0110), top: B:13:0x0023, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b5 A[Catch: all -> 0x0125, Exception -> 0x0127, TryCatch #4 {Exception -> 0x0127, blocks: (B:14:0x0023, B:19:0x0039, B:21:0x003f, B:23:0x004e, B:25:0x0057, B:28:0x0060, B:29:0x008b, B:32:0x009d, B:39:0x00c6, B:41:0x00cc, B:45:0x00e1, B:46:0x00e7, B:47:0x00f5, B:48:0x0103, B:49:0x00a5, B:52:0x00ad, B:55:0x00b5, B:60:0x0065, B:62:0x006b, B:63:0x007f, B:67:0x0084, B:42:0x0110), top: B:13:0x0023, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadPrefsFromFileLocked() {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.att.iqi.libs.PreferenceStore.loadPrefsFromFileLocked():void");
    }

    public void setBoolean(final String str, final boolean z) {
        synchronized (mLock) {
            Map map = sPreferences;
            if (map.containsKey(str)) {
                map.put(str, Boolean.valueOf(z));
                WorkerThread.getHandler().post(new Runnable() { // from class: com.att.iqi.libs.PreferenceStore$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PreferenceStore.this.lambda$setBoolean$0(str, z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBoolean$0(String str, boolean z) {
        lambda$setString$2(str, Boolean.valueOf(z));
    }

    public void setInteger(final String str, final int i) {
        synchronized (mLock) {
            Map map = sPreferences;
            if (map.containsKey(str)) {
                map.put(str, Integer.valueOf(i));
                WorkerThread.getHandler().post(new Runnable() { // from class: com.att.iqi.libs.PreferenceStore$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PreferenceStore.this.lambda$setInteger$1(str, i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setInteger$1(String str, int i) {
        lambda$setString$2(str, Integer.valueOf(i));
    }

    public void setString(final String str, final String str2) {
        synchronized (mLock) {
            Map map = sPreferences;
            if (map.containsKey(str)) {
                map.put(str, str2);
                WorkerThread.getHandler().post(new Runnable() { // from class: com.att.iqi.libs.PreferenceStore$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        PreferenceStore.this.lambda$setString$2(str, str2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updatePreference, reason: merged with bridge method [inline-methods] */
    public void lambda$setString$2(String str, Object obj) {
        String str2;
        String str3;
        boolean z;
        try {
            synchronized (mLock) {
                if (obj instanceof Boolean) {
                    str2 = "boolean";
                    str3 = ((Boolean) obj).booleanValue() ? "true" : "false";
                } else if (obj instanceof Integer) {
                    str2 = "int";
                    str3 = String.valueOf(obj);
                } else if (obj instanceof String) {
                    str2 = "string";
                    str3 = (String) obj;
                } else {
                    if (LogUtil.canLog()) {
                        LogUtil.loge("Tried to write an unsupported preference type " + obj.getClass());
                    }
                    return;
                }
                if (!this.mPreferenceFile.exists() && !createPrefStoreLocked()) {
                    if (LogUtil.canLog()) {
                        LogUtil.loge("Unable to persist preference " + str + " in storage!");
                    }
                    broadcastPreferenceChange(str);
                    return;
                }
                Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.mPreferenceFile);
                Iterator it = iterable(parse.getElementsByTagName(str2)).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    Node node = (Node) it.next();
                    if (TextUtils.equals(node.getAttributes().getNamedItem("name").getNodeValue(), str)) {
                        if (LogUtil.canLog()) {
                            LogUtil.loge("Found pref " + str);
                        }
                        Node namedItem = node.getAttributes().getNamedItem("value");
                        if (namedItem != null) {
                            namedItem.setNodeValue(str3);
                        } else {
                            node.setTextContent(str3);
                        }
                        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(parse), new StreamResult(this.mPreferenceFile));
                        if (LogUtil.canLog()) {
                            LogUtil.loge("Pref file updated [" + str + "] ==> " + obj);
                        }
                        broadcastPreferenceChange(str);
                        z = true;
                    }
                }
                if (!z && LogUtil.canLog()) {
                    LogUtil.loge("Preference " + str + " was not found!");
                }
            }
        } catch (Throwable th) {
            if (LogUtil.canLog()) {
                LogUtil.loge("An exception was thrown while trying to update preference", th);
            }
        }
    }

    private void broadcastPreferenceChange(final String str) {
        for (final PreferenceChangeListener preferenceChangeListener : this.mPreferenceChangeListeners) {
            WorkerThread.getHandler().post(new Runnable() { // from class: com.att.iqi.libs.PreferenceStore$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PreferenceStore.PreferenceChangeListener.this.onPreferenceChanged(str);
                }
            });
        }
    }

    private static Iterable iterable(final NodeList nodeList) {
        return new Iterable() { // from class: com.att.iqi.libs.PreferenceStore$$ExternalSyntheticLambda5
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                Iterator lambda$iterable$4;
                lambda$iterable$4 = PreferenceStore.lambda$iterable$4(nodeList);
                return lambda$iterable$4;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterator lambda$iterable$4(final NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength()).mapToObj(new IntFunction() { // from class: com.att.iqi.libs.PreferenceStore$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return nodeList.item(i);
            }
        }).iterator();
    }

    public boolean getBoolean(String str, boolean z) {
        synchronized (mLock) {
            Boolean bool = (Boolean) sPreferences.get(str);
            if (bool != null) {
                z = bool.booleanValue();
            }
        }
        return z;
    }

    public int getInteger(String str, int i) {
        synchronized (mLock) {
            Integer num = (Integer) sPreferences.get(str);
            if (num != null) {
                i = num.intValue();
            }
        }
        return i;
    }

    public String getString(String str, String str2) {
        synchronized (mLock) {
            String str3 = (String) sPreferences.get(str);
            if (str3 != null) {
                str2 = str3;
            }
        }
        return str2;
    }

    public void registerPreferenceChangeListener(PreferenceChangeListener preferenceChangeListener) {
        this.mPreferenceChangeListeners.add(preferenceChangeListener);
    }

    public void unregisterPreferenceChangeListener(PreferenceChangeListener preferenceChangeListener) {
        this.mPreferenceChangeListeners.remove(preferenceChangeListener);
    }
}
