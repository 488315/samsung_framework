package com.samsung.android.sdk.routines.automationservice.internal;

import android.database.Cursor;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class AutomationServiceImpl implements AutomationService {
    public static final Companion Companion = new Companion(null);
    public final ContentHandler contentHandler;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final boolean access$isValidRequest(Companion companion, AutomationService.SystemRoutineType systemRoutineType) {
            companion.getClass();
            return systemRoutineType.getValue().startsWith("system");
        }
    }

    public AutomationServiceImpl(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    public static String createContentValue(ParameterValues parameterValues) {
        String str;
        boolean z;
        ParameterValues.ParameterValue parameterValue;
        if (parameterValues == null || (parameterValue = (ParameterValues.ParameterValue) ((HashMap) parameterValues.parameterValueMap).get("v2IntentParam")) == null || parameterValue.getValue() == null) {
            str = "";
        } else {
            str = (String) parameterValue.getValue();
        }
        if (str.length() > 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return str;
        }
        if (parameterValues == null) {
            return "";
        }
        return parameterValues.toJsonString();
    }

    public static ParameterValues getParameterValues(Cursor cursor) {
        boolean z;
        ParameterValues parameterValues;
        ParameterValues.ParameterValue.ValueType valueType;
        String string = cursor.getString(cursor.getColumnIndex("intent_param"));
        if (string == null) {
            Log.INSTANCE.getClass();
            Log.e("AutomationServiceImpl@SDK", "getParameterValues(parameter is null) - ");
            ParameterValues.Companion.getClass();
            return new ParameterValues();
        }
        ParameterValues.Companion.getClass();
        HashMap hashMap = new HashMap();
        if (string.length() == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            parameterValues = new ParameterValues(hashMap, null);
        } else {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    ParameterValues.ParameterValue.Companion companion = ParameterValues.ParameterValue.Companion;
                    String string2 = jSONObject.getString(next);
                    companion.getClass();
                    ParameterValues.ParameterValue parameterValue = new ParameterValues.ParameterValue((DefaultConstructorMarker) null);
                    try {
                        JSONObject jSONObject2 = new JSONObject(string2);
                        parameterValue.mValueType = ParameterValues.ParameterValue.Companion.parseType(jSONObject2);
                        valueType = parameterValue.mValueType;
                        if (valueType == null) {
                            valueType = null;
                        }
                        parameterValue.value = ParameterValues.ParameterValue.Companion.parseValue(jSONObject2, valueType);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    hashMap.put(next, parameterValue);
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            parameterValues = new ParameterValues(hashMap, null);
        }
        HashMap hashMap2 = (HashMap) parameterValues.parameterValueMap;
        if (hashMap2.isEmpty()) {
            hashMap2.put("v2IsNegative", new ParameterValues.ParameterValue(cursor.getInt(cursor.getColumnIndex("is_negative"))));
            parameterValues.put("v2IntentParam", string);
        }
        return parameterValues;
    }
}
