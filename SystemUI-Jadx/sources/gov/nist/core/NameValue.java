package gov.nist.core;

import java.util.Map;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class NameValue extends GenericObject implements Map.Entry<String, String> {
    private static final long serialVersionUID = -1857729012596437950L;
    protected final boolean isFlagParameter;
    protected boolean isQuotedString;
    private String name;
    private String quotes;
    private String separator;
    private Object value;

    public NameValue() {
        this.name = null;
        this.value = "";
        this.separator = "=";
        this.quotes = "";
        this.isFlagParameter = false;
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        NameValue nameValue = (NameValue) super.clone();
        Object obj = this.value;
        if (obj != null) {
            nameValue.value = GenericObject.makeClone(obj);
        }
        return nameValue;
    }

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        return encode(new StringBuffer()).toString();
    }

    @Override // gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        String str;
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        NameValue nameValue = (NameValue) obj;
        if (this == nameValue) {
            return true;
        }
        String str2 = this.name;
        if ((str2 == null && nameValue.name != null) || (str2 != null && nameValue.name == null)) {
            return false;
        }
        if (str2 != null && (str = nameValue.name) != null && str2.compareToIgnoreCase(str) != 0) {
            return false;
        }
        Object obj2 = this.value;
        if ((obj2 != null && nameValue.value == null) || (obj2 == null && nameValue.value != null)) {
            return false;
        }
        Object obj3 = nameValue.value;
        if (obj2 == obj3) {
            return true;
        }
        if (obj2 instanceof String) {
            if (this.isQuotedString) {
                return obj2.equals(obj3);
            }
            if (((String) obj2).compareToIgnoreCase((String) obj3) != 0) {
                return false;
            }
            return true;
        }
        return obj2.equals(obj3);
    }

    @Override // java.util.Map.Entry
    public final String getKey() {
        return this.name;
    }

    public final String getName() {
        return this.name;
    }

    @Override // java.util.Map.Entry
    public final String getValue() {
        Object obj = this.value;
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public final Object getValueAsObject() {
        if (this.isFlagParameter) {
            return "";
        }
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return encode().toLowerCase().hashCode();
    }

    public final void setQuotedValue() {
        this.isQuotedString = true;
        this.quotes = "\"";
    }

    @Override // java.util.Map.Entry
    public final String setValue(String str) {
        String str2;
        String str3 = str;
        if (this.value == null) {
            str2 = null;
        } else {
            str2 = str3;
        }
        this.value = str3;
        return str2;
    }

    public final void setValueAsObject(Object obj) {
        this.value = obj;
    }

    @Override // gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        Object obj;
        Object obj2;
        String str = this.name;
        if (str == null || (obj2 = this.value) == null || this.isFlagParameter) {
            if (str == null && (obj = this.value) != null) {
                if (GenericObject.class.isAssignableFrom(obj.getClass())) {
                    ((GenericObject) this.value).encode(stringBuffer);
                    return stringBuffer;
                }
                Class<?> cls = this.value.getClass();
                int i = GenericObjectList.$r8$clinit;
                if (GenericObjectList.class.isAssignableFrom(cls)) {
                    stringBuffer.append(((GenericObjectList) this.value).encode());
                    return stringBuffer;
                }
                stringBuffer.append(this.quotes);
                stringBuffer.append(this.value.toString());
                stringBuffer.append(this.quotes);
                return stringBuffer;
            }
            if (str != null && (this.value == null || this.isFlagParameter)) {
                stringBuffer.append(str);
            }
            return stringBuffer;
        }
        if (GenericObject.class.isAssignableFrom(obj2.getClass())) {
            GenericObject genericObject = (GenericObject) this.value;
            stringBuffer.append(this.name);
            stringBuffer.append(this.separator);
            stringBuffer.append(this.quotes);
            genericObject.encode(stringBuffer);
            stringBuffer.append(this.quotes);
            return stringBuffer;
        }
        Class<?> cls2 = this.value.getClass();
        int i2 = GenericObjectList.$r8$clinit;
        if (GenericObjectList.class.isAssignableFrom(cls2)) {
            GenericObjectList genericObjectList = (GenericObjectList) this.value;
            stringBuffer.append(this.name);
            stringBuffer.append(this.separator);
            stringBuffer.append(genericObjectList.encode());
            return stringBuffer;
        }
        if (this.value.toString().length() == 0) {
            if (this.isQuotedString) {
                stringBuffer.append(this.name);
                stringBuffer.append(this.separator);
                stringBuffer.append(this.quotes);
                stringBuffer.append(this.quotes);
                return stringBuffer;
            }
            stringBuffer.append(this.name);
            stringBuffer.append(this.separator);
            return stringBuffer;
        }
        stringBuffer.append(this.name);
        stringBuffer.append(this.separator);
        stringBuffer.append(this.quotes);
        stringBuffer.append(this.value.toString());
        stringBuffer.append(this.quotes);
        return stringBuffer;
    }

    public NameValue(String str, Object obj, boolean z) {
        this.name = str;
        this.value = obj;
        this.separator = "=";
        this.quotes = "";
        this.isFlagParameter = z;
    }

    public NameValue(String str, Object obj) {
        this(str, obj, false);
    }
}
