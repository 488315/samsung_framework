package com.google.android.gms.internal;

import com.sec.internal.constants.ims.cmstore.utils.OMAGlobalVariables;
import com.sec.internal.constants.ims.entitilement.SoftphoneContract;
import com.sec.internal.ims.core.cmc.CmcConstants;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class zzflt {
    private static void zza(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        if (obj != null) {
            if (!(obj instanceof zzfls)) {
                String zzub = zzub(str);
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(zzub);
                stringBuffer2.append(": ");
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (!str2.startsWith(OMAGlobalVariables.HTTP) && str2.length() > 200) {
                        str2 = String.valueOf(str2.substring(0, 200)).concat("[...]");
                    }
                    String zzha = zzha(str2);
                    stringBuffer2.append(CmcConstants.E_NUM_STR_QUOTE);
                    stringBuffer2.append(zzha);
                    stringBuffer2.append(CmcConstants.E_NUM_STR_QUOTE);
                } else if (obj instanceof byte[]) {
                    zza((byte[]) obj, stringBuffer2);
                } else {
                    stringBuffer2.append(obj);
                }
                stringBuffer2.append("\n");
                return;
            }
            int length = stringBuffer.length();
            if (str != null) {
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(zzub(str));
                stringBuffer2.append(" <\n");
                stringBuffer.append("  ");
            }
            Class<?> cls = obj.getClass();
            for (Field field : cls.getFields()) {
                int modifiers = field.getModifiers();
                String name = field.getName();
                if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith("_") && !name.endsWith("_")) {
                    Class<?> type = field.getType();
                    Object obj2 = field.get(obj);
                    if (!type.isArray() || type.getComponentType() == Byte.TYPE) {
                        zza(name, obj2, stringBuffer, stringBuffer2);
                    } else {
                        int length2 = obj2 == null ? 0 : Array.getLength(obj2);
                        for (int i = 0; i < length2; i++) {
                            zza(name, Array.get(obj2, i), stringBuffer, stringBuffer2);
                        }
                    }
                }
            }
            for (Method method : cls.getMethods()) {
                String name2 = method.getName();
                if (name2.startsWith(SoftphoneContract.SoftphoneAddress.SET)) {
                    String substring = name2.substring(3);
                    try {
                        String valueOf = String.valueOf(substring);
                        if (((Boolean) cls.getMethod(valueOf.length() != 0 ? "has".concat(valueOf) : new String("has"), new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                            String valueOf2 = String.valueOf(substring);
                            zza(substring, cls.getMethod(valueOf2.length() != 0 ? "get".concat(valueOf2) : new String("get"), new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                        }
                    } catch (NoSuchMethodException unused) {
                    }
                }
            }
            if (str != null) {
                stringBuffer.setLength(length);
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(">\n");
            }
        }
    }

    private static void zza(byte[] bArr, StringBuffer stringBuffer) {
        if (bArr == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        for (byte b : bArr) {
            int i = b & 255;
            if (i == 92 || i == 34) {
                stringBuffer.append('\\');
            } else if (i < 32 || i >= 127) {
                stringBuffer.append(String.format("\\%03o", Integer.valueOf(i)));
            }
            stringBuffer.append((char) i);
        }
        stringBuffer.append('\"');
    }

    public static <T extends zzfls> String zzd(T t) {
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            String valueOf = String.valueOf(e.getMessage());
            return valueOf.length() != 0 ? "Error printing proto: ".concat(valueOf) : new String("Error printing proto: ");
        } catch (InvocationTargetException e2) {
            String valueOf2 = String.valueOf(e2.getMessage());
            return valueOf2.length() != 0 ? "Error printing proto: ".concat(valueOf2) : new String("Error printing proto: ");
        }
    }

    private static String zzha(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < ' ' || charAt > '~' || charAt == '\"' || charAt == '\'') {
                sb.append(String.format("\\u%04x", Integer.valueOf(charAt)));
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    private static String zzub(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i != 0) {
                if (Character.isUpperCase(charAt)) {
                    stringBuffer.append('_');
                }
                stringBuffer.append(charAt);
            }
            charAt = Character.toLowerCase(charAt);
            stringBuffer.append(charAt);
        }
        return stringBuffer.toString();
    }
}
