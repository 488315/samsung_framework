package android.os;

import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.ArrayMap;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/* loaded from: classes3.dex */
public class BaseBundle {
    static final int BUNDLE_MAGIC = 1279544898;
    private static final int BUNDLE_MAGIC_NATIVE = 1279544900;
    static final boolean DEBUG = false;
    static final int FLAG_DEFUSABLE = 1;
    private static final boolean LOG_DEFUSABLE = false;
    protected static final String TAG = "Bundle";
    private static volatile boolean sShouldDefuse = false;
    private ClassLoader mClassLoader;
    public int mFlags;
    private int mLazyValues;
    ArrayMap<String, Object> mMap;
    boolean mOwnsLazyValues;
    private boolean mParcelledByNative;
    volatile Parcel mParcelledData;
    private WeakReference<Parcel> mWeakParcelledData;

    public static void setShouldDefuse(boolean shouldDefuse) {
        sShouldDefuse = shouldDefuse;
    }

    static final class NoImagePreloadHolder {
        public static final Parcel EMPTY_PARCEL = Parcel.obtain();

        NoImagePreloadHolder() {
        }
    }

    BaseBundle(ClassLoader loader, int capacity) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        this.mMap = capacity > 0 ? new ArrayMap<>(capacity) : new ArrayMap<>();
        this.mClassLoader = loader == null ? getClass().getClassLoader() : loader;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BaseBundle() {
        this((ClassLoader) null, 0);
    }

    BaseBundle(Parcel parcelledData) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        readFromParcelInner(parcelledData);
    }

    BaseBundle(Parcel parcelledData, int length) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        readFromParcelInner(parcelledData, length);
    }

    BaseBundle(ClassLoader loader) {
        this(loader, 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BaseBundle(int capacity) {
        this((ClassLoader) null, capacity);
    }

    BaseBundle(BaseBundle b) {
        this(b, false);
    }

    BaseBundle(BaseBundle from, boolean deep) {
        Parcel parcelledData;
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        synchronized (from) {
            this.mClassLoader = from.mClassLoader;
            if (from.mMap != null) {
                this.mOwnsLazyValues = false;
                from.mOwnsLazyValues = false;
                if (!deep) {
                    this.mMap = new ArrayMap<>(from.mMap);
                } else {
                    ArrayMap<String, Object> fromMap = from.mMap;
                    int n = fromMap.size();
                    this.mMap = new ArrayMap<>(n);
                    for (int i = 0; i < n; i++) {
                        this.mMap.append(fromMap.keyAt(i), deepCopyValue(fromMap.valueAt(i)));
                    }
                }
            } else {
                this.mMap = null;
            }
            if (from.mParcelledData != null) {
                if (from.isEmptyParcel()) {
                    parcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
                    this.mParcelledByNative = false;
                } else {
                    parcelledData = Parcel.obtain();
                    parcelledData.appendFrom(from.mParcelledData, 0, from.mParcelledData.dataSize());
                    parcelledData.setDataPosition(0);
                    this.mParcelledByNative = from.mParcelledByNative;
                }
            } else {
                parcelledData = null;
                this.mParcelledByNative = false;
            }
            this.mParcelledData = parcelledData;
        }
    }

    public String getPairValue() {
        unparcel();
        int size = this.mMap.size();
        if (size > 1) {
            Log.w(TAG, "getPairValue() used on Bundle with multiple pairs.");
        }
        if (size == 0) {
            return null;
        }
        try {
            return (String) getValueAt(0, String.class, new Class[0]);
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning("getPairValue()", "String", e);
            return null;
        }
    }

    void setClassLoader(ClassLoader loader) {
        this.mClassLoader = loader;
    }

    ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    final void unparcel() {
        unparcel(false);
    }

    final void unparcel(boolean itemwise) {
        synchronized (this) {
            Parcel source = this.mParcelledData;
            if (source != null) {
                Preconditions.checkState(this.mOwnsLazyValues);
                initializeFromParcelLocked(source, true, this.mParcelledByNative);
            }
            if (itemwise) {
                int n = this.mMap.size();
                for (int i = 0; i < n; i++) {
                    getValueAt(i, null, new Class[0]);
                }
            }
        }
    }

    @Deprecated
    final Object getValue(String key) {
        return getValue(key, null);
    }

    final <T> T getValue(String str, Class<T> cls) {
        return (T) getValue(str, cls, null);
    }

    final <T> T getValue(String str, Class<T> cls, Class<?>... clsArr) {
        int indexOfKey = this.mMap.indexOfKey(str);
        if (indexOfKey >= 0) {
            return (T) getValueAt(indexOfKey, cls, clsArr);
        }
        return null;
    }

    final <T> T getValueAt(int i, Class<T> cls, Class<?>... clsArr) {
        Object valueAt = this.mMap.valueAt(i);
        if (valueAt instanceof BiFunction) {
            synchronized (this) {
                valueAt = unwrapLazyValueFromMapLocked(i, cls, clsArr);
            }
        }
        return cls != null ? cls.cast(valueAt) : (T) valueAt;
    }

    private Object unwrapLazyValueFromMapLocked(int i, Class<?> clazz, Class<?>... itemTypes) {
        Object object = this.mMap.valueAt(i);
        if (object instanceof BiFunction) {
            try {
                object = ((BiFunction) object).apply(clazz, itemTypes);
                this.mMap.setValueAt(i, object);
                this.mLazyValues--;
                if (this.mOwnsLazyValues) {
                    Preconditions.checkState(this.mLazyValues >= 0, "Lazy values ref count below 0");
                    Parcel parcel = this.mWeakParcelledData.get();
                    if (this.mLazyValues == 0 && parcel != null) {
                        recycleParcel(parcel);
                        this.mWeakParcelledData = null;
                    }
                }
            } catch (BadParcelableException e) {
                if (sShouldDefuse) {
                    Log.w(TAG, "Failed to parse item " + this.mMap.keyAt(i) + ", returning null.", e);
                    return null;
                }
                throw e;
            }
        }
        return object;
    }

    private void initializeFromParcelLocked(Parcel parcelledData, boolean ownsParcel, boolean parcelledByNative) {
        ArrayMap<String, Object> map;
        WeakReference<Parcel> weakReference;
        if (isEmptyParcel(parcelledData)) {
            if (this.mMap == null) {
                this.mMap = new ArrayMap<>(1);
            } else {
                this.mMap.erase();
            }
            this.mParcelledByNative = false;
            this.mParcelledData = null;
            return;
        }
        int count = parcelledData.readInt();
        if (count < 0) {
            return;
        }
        ArrayMap<String, Object> map2 = this.mMap;
        if (map2 == null) {
            map = new ArrayMap<>(count);
        } else {
            map2.erase();
            map2.ensureCapacity(count);
            map = map2;
        }
        int numLazyValues = 0;
        try {
            try {
                numLazyValues = parcelledData.readArrayMap(map, count, !parcelledByNative, ownsParcel, this.mClassLoader);
                this.mWeakParcelledData = null;
            } catch (BadParcelableException e) {
                if (!sShouldDefuse) {
                    throw e;
                }
                Log.w(TAG, "Failed to parse Bundle, but defusing quietly", e);
                map.erase();
                this.mWeakParcelledData = null;
                if (ownsParcel) {
                    if (0 != 0) {
                        weakReference = new WeakReference<>(parcelledData);
                    }
                }
            }
            if (ownsParcel) {
                if (numLazyValues != 0) {
                    weakReference = new WeakReference<>(parcelledData);
                    this.mWeakParcelledData = weakReference;
                }
                recycleParcel(parcelledData);
            }
            this.mLazyValues = numLazyValues;
            this.mParcelledByNative = false;
            this.mMap = map;
            this.mParcelledData = null;
        } catch (Throwable th) {
            this.mWeakParcelledData = null;
            if (ownsParcel) {
                if (0 == 0) {
                    recycleParcel(parcelledData);
                } else {
                    this.mWeakParcelledData = new WeakReference<>(parcelledData);
                }
            }
            this.mLazyValues = 0;
            this.mParcelledByNative = false;
            this.mMap = map;
            this.mParcelledData = null;
            throw th;
        }
    }

    public boolean isParcelled() {
        return this.mParcelledData != null;
    }

    public boolean isEmptyParcel() {
        return isEmptyParcel(this.mParcelledData);
    }

    private static boolean isEmptyParcel(Parcel p) {
        return p == NoImagePreloadHolder.EMPTY_PARCEL;
    }

    private static void recycleParcel(Parcel p) {
        if (p != null && !isEmptyParcel(p)) {
            p.recycle();
        }
    }

    ArrayMap<String, Object> getItemwiseMap() {
        unparcel(true);
        return this.mMap;
    }

    public int size() {
        unparcel();
        return this.mMap.size();
    }

    public boolean isEmpty() {
        unparcel();
        return this.mMap.isEmpty();
    }

    public boolean isDefinitelyEmpty() {
        if (isParcelled()) {
            return isEmptyParcel();
        }
        return isEmpty();
    }

    public static boolean kindofEquals(BaseBundle a, BaseBundle b) {
        return a == b || (a != null && a.kindofEquals(b));
    }

    public boolean kindofEquals(BaseBundle other) {
        if (other == null) {
            return false;
        }
        if (isDefinitelyEmpty() && other.isDefinitelyEmpty()) {
            return true;
        }
        if (isParcelled() != other.isParcelled()) {
            return false;
        }
        if (isParcelled()) {
            return this.mParcelledData.compareData(other.mParcelledData) == 0;
        }
        return this.mMap.equals(other.mMap);
    }

    public void clear() {
        unparcel();
        if (this.mOwnsLazyValues && this.mWeakParcelledData != null) {
            recycleParcel(this.mWeakParcelledData.get());
        }
        this.mWeakParcelledData = null;
        this.mLazyValues = 0;
        this.mOwnsLazyValues = true;
        this.mMap.clear();
    }

    private Object deepCopyValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Bundle) {
            return ((Bundle) value).deepCopy();
        }
        if (value instanceof PersistableBundle) {
            return ((PersistableBundle) value).deepCopy();
        }
        if (value instanceof ArrayList) {
            return deepcopyArrayList((ArrayList) value);
        }
        if (value.getClass().isArray()) {
            if (value instanceof int[]) {
                return ((int[]) value).clone();
            }
            if (value instanceof long[]) {
                return ((long[]) value).clone();
            }
            if (value instanceof float[]) {
                return ((float[]) value).clone();
            }
            if (value instanceof double[]) {
                return ((double[]) value).clone();
            }
            if (value instanceof Object[]) {
                return ((Object[]) value).clone();
            }
            if (value instanceof byte[]) {
                return ((byte[]) value).clone();
            }
            if (value instanceof short[]) {
                return ((short[]) value).clone();
            }
            if (value instanceof char[]) {
                return ((char[]) value).clone();
            }
        }
        return value;
    }

    private ArrayList deepcopyArrayList(ArrayList from) {
        int N = from.size();
        ArrayList out = new ArrayList(N);
        for (int i = 0; i < N; i++) {
            out.add(deepCopyValue(from.get(i)));
        }
        return out;
    }

    public boolean containsKey(String key) {
        unparcel();
        return this.mMap.containsKey(key);
    }

    @Deprecated
    public Object get(String key) {
        unparcel();
        return getValue(key);
    }

    <T> T get(String str, Class<T> cls) {
        unparcel();
        try {
            return (T) getValue(str, (Class) Objects.requireNonNull(cls));
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning(str, cls.getCanonicalName(), e);
            return null;
        }
    }

    public void remove(String key) {
        unparcel();
        this.mMap.remove(key);
    }

    public void putAll(PersistableBundle bundle) {
        unparcel();
        bundle.unparcel();
        this.mMap.putAll((ArrayMap<? extends String, ? extends Object>) bundle.mMap);
    }

    void putAll(ArrayMap map) {
        unparcel();
        this.mMap.putAll((ArrayMap<? extends String, ? extends Object>) map);
    }

    public Set<String> keySet() {
        unparcel();
        return this.mMap.keySet();
    }

    public void putObject(String key, Object value) {
        if (value == null) {
            putString(key, null);
            return;
        }
        if (value instanceof Boolean) {
            putBoolean(key, ((Boolean) value).booleanValue());
            return;
        }
        if (value instanceof Integer) {
            putInt(key, ((Integer) value).intValue());
            return;
        }
        if (value instanceof Long) {
            putLong(key, ((Long) value).longValue());
            return;
        }
        if (value instanceof Double) {
            putDouble(key, ((Double) value).doubleValue());
            return;
        }
        if (value instanceof String) {
            putString(key, (String) value);
            return;
        }
        if (value instanceof boolean[]) {
            putBooleanArray(key, (boolean[]) value);
            return;
        }
        if (value instanceof int[]) {
            putIntArray(key, (int[]) value);
            return;
        }
        if (value instanceof long[]) {
            putLongArray(key, (long[]) value);
        } else if (value instanceof double[]) {
            putDoubleArray(key, (double[]) value);
        } else {
            if (value instanceof String[]) {
                putStringArray(key, (String[]) value);
                return;
            }
            throw new IllegalArgumentException("Unsupported type " + value.getClass());
        }
    }

    public void putBoolean(String key, boolean value) {
        unparcel();
        this.mMap.put(key, Boolean.valueOf(value));
    }

    void putByte(String key, byte value) {
        unparcel();
        this.mMap.put(key, Byte.valueOf(value));
    }

    void putChar(String key, char value) {
        unparcel();
        this.mMap.put(key, Character.valueOf(value));
    }

    void putShort(String key, short value) {
        unparcel();
        this.mMap.put(key, Short.valueOf(value));
    }

    public void putInt(String key, int value) {
        unparcel();
        this.mMap.put(key, Integer.valueOf(value));
    }

    public void putLong(String key, long value) {
        unparcel();
        this.mMap.put(key, Long.valueOf(value));
    }

    void putFloat(String key, float value) {
        unparcel();
        this.mMap.put(key, Float.valueOf(value));
    }

    public void putDouble(String key, double value) {
        unparcel();
        this.mMap.put(key, Double.valueOf(value));
    }

    public void putString(String key, String value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putCharSequence(String key, CharSequence value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putIntegerArrayList(String key, ArrayList<Integer> value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putStringArrayList(String key, ArrayList<String> value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putSerializable(String key, Serializable value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putBooleanArray(String key, boolean[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putByteArray(String key, byte[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putShortArray(String key, short[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putCharArray(String key, char[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putIntArray(String key, int[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putLongArray(String key, long[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putFloatArray(String key, float[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putDoubleArray(String key, double[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public void putStringArray(String key, String[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    void putCharSequenceArray(String key, CharSequence[] value) {
        unparcel();
        this.mMap.put(key, value);
    }

    public boolean getBoolean(String key) {
        unparcel();
        return getBoolean(key, false);
    }

    void typeWarning(String key, Object value, String className, Object defaultValue, RuntimeException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("Key ");
        sb.append(key);
        sb.append(" expected ");
        sb.append(className);
        if (value != null) {
            sb.append(" but value was a ");
            sb.append(value.getClass().getName());
        } else {
            sb.append(" but value was of a different type");
        }
        sb.append(".  The default value ");
        sb.append(defaultValue);
        sb.append(" was returned.");
        Log.w(TAG, sb.toString());
        Log.w(TAG, "Attempt to cast generated internal exception:", e);
    }

    void typeWarning(String key, Object value, String className, RuntimeException e) {
        typeWarning(key, value, className, "<null>", e);
    }

    void typeWarning(String key, String className, RuntimeException e) {
        typeWarning(key, null, className, "<null>", e);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Boolean) o).booleanValue();
        } catch (ClassCastException e) {
            typeWarning(key, o, "Boolean", Boolean.valueOf(defaultValue), e);
            return defaultValue;
        }
    }

    byte getByte(String key) {
        unparcel();
        return getByte(key, (byte) 0).byteValue();
    }

    Byte getByte(String key, byte defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return Byte.valueOf(defaultValue);
        }
        try {
            return (Byte) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Byte", Byte.valueOf(defaultValue), e);
            return Byte.valueOf(defaultValue);
        }
    }

    char getChar(String key) {
        unparcel();
        return getChar(key, (char) 0);
    }

    char getChar(String key, char defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Character) o).charValue();
        } catch (ClassCastException e) {
            typeWarning(key, o, "Character", Character.valueOf(defaultValue), e);
            return defaultValue;
        }
    }

    short getShort(String key) {
        unparcel();
        return getShort(key, (short) 0);
    }

    short getShort(String key, short defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Short) o).shortValue();
        } catch (ClassCastException e) {
            typeWarning(key, o, "Short", Short.valueOf(defaultValue), e);
            return defaultValue;
        }
    }

    public int getInt(String key) {
        unparcel();
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Integer) o).intValue();
        } catch (ClassCastException e) {
            typeWarning(key, o, "Integer", Integer.valueOf(defaultValue), e);
            return defaultValue;
        }
    }

    public long getLong(String key) {
        unparcel();
        return getLong(key, 0L);
    }

    public long getLong(String key, long defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Long) o).longValue();
        } catch (ClassCastException e) {
            typeWarning(key, o, "Long", Long.valueOf(defaultValue), e);
            return defaultValue;
        }
    }

    float getFloat(String key) {
        unparcel();
        return getFloat(key, 0.0f);
    }

    float getFloat(String key, float defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Float) o).floatValue();
        } catch (ClassCastException e) {
            typeWarning(key, o, "Float", Float.valueOf(defaultValue), e);
            return defaultValue;
        }
    }

    public double getDouble(String key) {
        unparcel();
        return getDouble(key, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getDouble(String key, double defaultValue) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return ((Double) o).doubleValue();
        } catch (ClassCastException e) {
            typeWarning(key, o, "Double", Double.valueOf(defaultValue), e);
            return defaultValue;
        }
    }

    public String getString(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        try {
            return (String) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "String", e);
            return null;
        }
    }

    public String getString(String key, String defaultValue) {
        String s = getString(key);
        return s == null ? defaultValue : s;
    }

    CharSequence getCharSequence(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        try {
            return (CharSequence) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "CharSequence", e);
            return null;
        }
    }

    CharSequence getCharSequence(String key, CharSequence defaultValue) {
        CharSequence cs = getCharSequence(key);
        return cs == null ? defaultValue : cs;
    }

    @Deprecated
    Serializable getSerializable(String key) {
        unparcel();
        Object o = getValue(key);
        if (o == null) {
            return null;
        }
        try {
            return (Serializable) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "Serializable", e);
            return null;
        }
    }

    <T extends Serializable> T getSerializable(String key, Class<T> clazz) {
        return (T) get(key, clazz);
    }

    <T> ArrayList<T> getArrayList(String key, Class<? extends T> clazz) {
        unparcel();
        try {
            return (ArrayList) getValue(key, ArrayList.class, (Class) Objects.requireNonNull(clazz));
        } catch (BadTypeParcelableException | ClassCastException e) {
            typeWarning(key, "ArrayList<" + clazz.getCanonicalName() + ">", e);
            return null;
        }
    }

    ArrayList<Integer> getIntegerArrayList(String key) {
        return getArrayList(key, Integer.class);
    }

    ArrayList<String> getStringArrayList(String key) {
        return getArrayList(key, String.class);
    }

    ArrayList<CharSequence> getCharSequenceArrayList(String key) {
        return getArrayList(key, CharSequence.class);
    }

    public boolean[] getBooleanArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (boolean[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "byte[]", e);
            return null;
        }
    }

    byte[] getByteArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (byte[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "byte[]", e);
            return null;
        }
    }

    short[] getShortArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (short[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "short[]", e);
            return null;
        }
    }

    char[] getCharArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (char[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "char[]", e);
            return null;
        }
    }

    public int[] getIntArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (int[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "int[]", e);
            return null;
        }
    }

    public long[] getLongArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (long[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "long[]", e);
            return null;
        }
    }

    float[] getFloatArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (float[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "float[]", e);
            return null;
        }
    }

    public double[] getDoubleArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (double[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "double[]", e);
            return null;
        }
    }

    public String[] getStringArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (String[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "String[]", e);
            return null;
        }
    }

    CharSequence[] getCharSequenceArray(String key) {
        unparcel();
        Object o = this.mMap.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (CharSequence[]) o;
        } catch (ClassCastException e) {
            typeWarning(key, o, "CharSequence[]", e);
            return null;
        }
    }

    void writeToParcelInner(Parcel parcel, int flags) {
        if (parcel.hasReadWriteHelper()) {
            unparcel(true);
        }
        synchronized (this) {
            Parcel parcel2 = this.mParcelledData;
            int i = BUNDLE_MAGIC;
            if (parcel2 != null) {
                if (this.mParcelledData == NoImagePreloadHolder.EMPTY_PARCEL) {
                    parcel.writeInt(0);
                } else {
                    int length = this.mParcelledData.dataSize();
                    parcel.writeInt(length);
                    if (this.mParcelledByNative) {
                        i = BUNDLE_MAGIC_NATIVE;
                    }
                    parcel.writeInt(i);
                    parcel.appendFrom(this.mParcelledData, 0, length);
                }
                return;
            }
            ArrayMap<String, Object> map = this.mMap;
            if (map == null || map.size() <= 0) {
                parcel.writeInt(0);
                return;
            }
            int lengthPos = parcel.dataPosition();
            parcel.writeInt(-1);
            parcel.writeInt(BUNDLE_MAGIC);
            int startPos = parcel.dataPosition();
            parcel.writeArrayMapInternal(map);
            int endPos = parcel.dataPosition();
            parcel.setDataPosition(lengthPos);
            parcel.writeInt(endPos - startPos);
            parcel.setDataPosition(endPos);
        }
    }

    void readFromParcelInner(Parcel parcel) {
        int length = parcel.readInt();
        readFromParcelInner(parcel, length);
    }

    private void readFromParcelInner(Parcel parcel, int length) {
        if (length < 0) {
            throw new RuntimeException("Bad length in parcel: " + length);
        }
        if (length == 0) {
            this.mParcelledByNative = false;
            this.mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
            return;
        }
        if (length % 4 != 0) {
            throw new IllegalStateException("Bundle length is not aligned by 4: " + length);
        }
        int magic = parcel.readInt();
        boolean isJavaBundle = magic == BUNDLE_MAGIC;
        boolean isNativeBundle = magic == BUNDLE_MAGIC_NATIVE;
        if (!isJavaBundle && !isNativeBundle) {
            throw new IllegalStateException("Bad magic number for Bundle: 0x" + Integer.toHexString(magic));
        }
        if (parcel.hasReadWriteHelper()) {
            synchronized (this) {
                this.mOwnsLazyValues = false;
                initializeFromParcelLocked(parcel, false, isNativeBundle);
            }
            return;
        }
        int offset = parcel.dataPosition();
        parcel.setDataPosition(MathUtils.addOrThrow(offset, length));
        Parcel p = Parcel.obtain();
        p.setDataPosition(0);
        p.appendFrom(parcel, offset, length);
        p.adoptClassCookies(parcel);
        p.setDataPosition(0);
        this.mOwnsLazyValues = true;
        this.mParcelledByNative = isNativeBundle;
        this.mParcelledData = p;
    }

    public static void dumpStats(IndentingPrintWriter pw, String key, Object value) {
        Parcel tmp = Parcel.obtain();
        tmp.writeValue(value);
        int size = tmp.dataPosition();
        tmp.recycle();
        if (size > 1024) {
            pw.println(key + " [size=" + size + NavigationBarInflaterView.SIZE_MOD_END);
            if (value instanceof BaseBundle) {
                dumpStats(pw, (BaseBundle) value);
            } else if (value instanceof SparseArray) {
                dumpStats(pw, (SparseArray) value);
            }
        }
    }

    public static void dumpStats(IndentingPrintWriter pw, SparseArray array) {
        pw.increaseIndent();
        if (array == null) {
            pw.println("[null]");
            return;
        }
        for (int i = 0; i < array.size(); i++) {
            dumpStats(pw, "0x" + Integer.toHexString(array.keyAt(i)), array.valueAt(i));
        }
        pw.decreaseIndent();
    }

    public static void dumpStats(IndentingPrintWriter pw, BaseBundle bundle) {
        pw.increaseIndent();
        if (bundle == null) {
            pw.println("[null]");
            return;
        }
        ArrayMap<String, Object> map = bundle.getItemwiseMap();
        for (int i = 0; i < map.size(); i++) {
            dumpStats(pw, map.keyAt(i), map.valueAt(i));
        }
        pw.decreaseIndent();
    }
}
