package android.hardware.input;

import android.app.blob.XmlTags;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class KeyboardLayout implements Parcelable, Comparable<KeyboardLayout> {
    public static final Parcelable.Creator<KeyboardLayout> CREATOR = new Parcelable.Creator<KeyboardLayout>() { // from class: android.hardware.input.KeyboardLayout.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardLayout createFromParcel(Parcel source) {
            return new KeyboardLayout(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardLayout[] newArray(int size) {
            return new KeyboardLayout[size];
        }
    };
    public static final String LAYOUT_TYPE_AZERTY = "azerty";
    public static final String LAYOUT_TYPE_COLEMAK = "colemak";
    public static final String LAYOUT_TYPE_DVORAK = "dvorak";
    public static final String LAYOUT_TYPE_EXTENDED = "extended";
    public static final String LAYOUT_TYPE_QWERTY = "qwerty";
    public static final String LAYOUT_TYPE_QWERTZ = "qwertz";
    public static final String LAYOUT_TYPE_TURKISH_F = "turkish_f";
    public static final String LAYOUT_TYPE_TURKISH_Q = "turkish_q";
    public static final String LAYOUT_TYPE_UNDEFINED = "undefined";
    public static final String LAYOUT_TYPE_WORKMAN = "workman";
    private final String mCollection;
    private final String mDescriptor;
    private final String mLabel;
    private final LayoutType mLayoutType;
    private final LocaleList mLocales;
    private final int mPriority;
    private final int mProductId;
    private final int mVendorId;

    public enum LayoutType {
        UNDEFINED(0, KeyboardLayout.LAYOUT_TYPE_UNDEFINED),
        QWERTY(1, "qwerty"),
        QWERTZ(2, KeyboardLayout.LAYOUT_TYPE_QWERTZ),
        AZERTY(3, KeyboardLayout.LAYOUT_TYPE_AZERTY),
        DVORAK(4, KeyboardLayout.LAYOUT_TYPE_DVORAK),
        COLEMAK(5, KeyboardLayout.LAYOUT_TYPE_COLEMAK),
        WORKMAN(6, KeyboardLayout.LAYOUT_TYPE_WORKMAN),
        TURKISH_Q(7, KeyboardLayout.LAYOUT_TYPE_TURKISH_Q),
        TURKISH_F(8, KeyboardLayout.LAYOUT_TYPE_TURKISH_F),
        EXTENDED(9, KeyboardLayout.LAYOUT_TYPE_EXTENDED);

        private final String mName;
        private final int mValue;
        private static final Map<Integer, LayoutType> VALUE_TO_ENUM_MAP = new HashMap();
        private static final Map<String, LayoutType> NAME_TO_ENUM_MAP = new HashMap();

        static {
            for (LayoutType type : values()) {
                VALUE_TO_ENUM_MAP.put(Integer.valueOf(type.mValue), type);
                NAME_TO_ENUM_MAP.put(type.mName, type);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static LayoutType of(int value) {
            return VALUE_TO_ENUM_MAP.getOrDefault(Integer.valueOf(value), UNDEFINED);
        }

        LayoutType(int value, String name) {
            this.mValue = value;
            this.mName = name;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getValue() {
            return this.mValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getName() {
            return this.mName;
        }

        public static int getLayoutTypeEnumValue(String layoutName) {
            return NAME_TO_ENUM_MAP.getOrDefault(layoutName, UNDEFINED).getValue();
        }

        public static String getLayoutNameFromValue(int enumValue) {
            return VALUE_TO_ENUM_MAP.getOrDefault(Integer.valueOf(enumValue), UNDEFINED).getName();
        }
    }

    public KeyboardLayout(String descriptor, String label, String collection, int priority, LocaleList locales, int layoutValue, int vid, int pid) {
        this.mDescriptor = descriptor;
        this.mLabel = label;
        this.mCollection = collection;
        this.mPriority = priority;
        this.mLocales = locales;
        this.mLayoutType = LayoutType.of(layoutValue);
        this.mVendorId = vid;
        this.mProductId = pid;
    }

    private KeyboardLayout(Parcel source) {
        this.mDescriptor = source.readString();
        this.mLabel = source.readString();
        this.mCollection = source.readString();
        this.mPriority = source.readInt();
        this.mLocales = LocaleList.CREATOR.createFromParcel(source);
        this.mLayoutType = LayoutType.of(source.readInt());
        this.mVendorId = source.readInt();
        this.mProductId = source.readInt();
    }

    public String getDescriptor() {
        return this.mDescriptor;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public String getCollection() {
        return this.mCollection;
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    public String getLayoutType() {
        return this.mLayoutType.getName();
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public boolean isAnsiLayout() {
        for (int i = 0; i < this.mLocales.size(); i++) {
            Locale locale = this.mLocales.get(i);
            if (locale != null && locale.getCountry().equalsIgnoreCase(XmlTags.ATTR_USER_ID) && this.mLayoutType != LayoutType.EXTENDED) {
                return true;
            }
        }
        return false;
    }

    public boolean isJisLayout() {
        for (int i = 0; i < this.mLocales.size(); i++) {
            Locale locale = this.mLocales.get(i);
            if (locale != null && locale.getCountry().equalsIgnoreCase("jp")) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDescriptor);
        dest.writeString(this.mLabel);
        dest.writeString(this.mCollection);
        dest.writeInt(this.mPriority);
        this.mLocales.writeToParcel(dest, 0);
        dest.writeInt(this.mLayoutType.getValue());
        dest.writeInt(this.mVendorId);
        dest.writeInt(this.mProductId);
    }

    @Override // java.lang.Comparable
    public int compareTo(KeyboardLayout another) {
        int result = Integer.compare(another.mPriority, this.mPriority);
        if (result == 0) {
            result = Integer.compare(this.mLayoutType.mValue, another.mLayoutType.mValue);
        }
        if (result == 0) {
            result = this.mLabel.compareToIgnoreCase(another.mLabel);
        }
        if (result == 0) {
            return this.mCollection.compareToIgnoreCase(another.mCollection);
        }
        return result;
    }

    public String toString() {
        String collectionString = this.mCollection.isEmpty() ? "" : " - " + this.mCollection;
        return "KeyboardLayout " + this.mLabel + collectionString + ", descriptor: " + this.mDescriptor + ", priority: " + this.mPriority + ", locales: " + this.mLocales.toString() + ", layout type: " + this.mLayoutType.getName() + ", vendorId: " + this.mVendorId + ", productId: " + this.mProductId;
    }

    public static boolean isLayoutTypeValid(String layoutName) {
        Objects.requireNonNull(layoutName, "Provided layout name should not be null");
        for (LayoutType layoutType : LayoutType.values()) {
            if (layoutName.equals(layoutType.getName())) {
                return true;
            }
        }
        return false;
    }
}
