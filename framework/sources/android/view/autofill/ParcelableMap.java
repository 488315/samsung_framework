package android.view.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ParcelableMap extends HashMap<AutofillId, AutofillValue> implements Parcelable {
    public static final Parcelable.Creator<ParcelableMap> CREATOR = new Parcelable.Creator<ParcelableMap>() { // from class: android.view.autofill.ParcelableMap.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ParcelableMap createFromParcel(Parcel source) {
            int size = source.readInt();
            ParcelableMap map = new ParcelableMap(size);
            for (int i = 0; i < size; i++) {
                AutofillId key = (AutofillId) source.readParcelable(null, AutofillId.class);
                AutofillValue value = (AutofillValue) source.readParcelable(null, AutofillValue.class);
                map.put(key, value);
            }
            return map;
        }

        @Override // android.os.Parcelable.Creator
        public ParcelableMap[] newArray(int size) {
            return new ParcelableMap[size];
        }
    };

    public ParcelableMap(int size) {
        super(size);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size());
        for (Map.Entry<AutofillId, AutofillValue> entry : entrySet()) {
            dest.writeParcelable(entry.getKey(), 0);
            dest.writeParcelable(entry.getValue(), 0);
        }
    }

    /* renamed from: android.view.autofill.ParcelableMap$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<ParcelableMap> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ParcelableMap createFromParcel(Parcel source) {
            int size = source.readInt();
            ParcelableMap map = new ParcelableMap(size);
            for (int i = 0; i < size; i++) {
                AutofillId key = (AutofillId) source.readParcelable(null, AutofillId.class);
                AutofillValue value = (AutofillValue) source.readParcelable(null, AutofillValue.class);
                map.put(key, value);
            }
            return map;
        }

        @Override // android.os.Parcelable.Creator
        public ParcelableMap[] newArray(int size) {
            return new ParcelableMap[size];
        }
    }
}
