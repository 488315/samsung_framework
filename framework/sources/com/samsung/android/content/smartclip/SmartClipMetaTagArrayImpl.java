package com.samsung.android.content.smartclip;

import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.ims.options.SemCapabilities;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SmartClipMetaTagArrayImpl extends SemSmartClipMetaTagArray implements Parcelable {
    public static final Parcelable.Creator<SmartClipMetaTagArrayImpl> CREATOR = new Parcelable.Creator<SmartClipMetaTagArrayImpl>() { // from class: com.samsung.android.content.smartclip.SmartClipMetaTagArrayImpl.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SmartClipMetaTagArrayImpl createFromParcel(Parcel in) {
            Log.d(SmartClipMetaTagArrayImpl.TAG, "SmartClipMetaTagArrayImpl.createFromParcel called");
            SmartClipMetaTagArrayImpl data = new SmartClipMetaTagArrayImpl();
            data.readFromParcel(in);
            return data;
        }

        @Override // android.os.Parcelable.Creator
        public SmartClipMetaTagArrayImpl[] newArray(int size) {
            return new SmartClipMetaTagArrayImpl[size];
        }
    };
    private static final String TAG = "SmartClipMetaTagArrayImpl";

    @Override // com.samsung.android.content.smartclip.SemSmartClipMetaTagArray
    public int removeMetaTags(String tagType) {
        int removedCount = 0;
        int arrayLen = size();
        for (int i = arrayLen - 1; i >= 0; i--) {
            SemSmartClipMetaTag tag = (SemSmartClipMetaTag) get(i);
            String curTagType = tag.getType();
            if (curTagType != null && curTagType.equals(tagType)) {
                remove(i);
                removedCount++;
            }
        }
        return removedCount;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipMetaTagArray
    public SemSmartClipMetaTagArray getMetaTags(String tagType) {
        SmartClipMetaTagArrayImpl resultArray = new SmartClipMetaTagArrayImpl();
        int arrayLen = size();
        for (int i = 0; i < arrayLen; i++) {
            SemSmartClipMetaTag tag = (SemSmartClipMetaTag) get(i);
            String curTagType = tag.getType();
            if (curTagType != null && curTagType.equals(tagType)) {
                resultArray.add(tag);
            }
        }
        return resultArray;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipMetaTagArray
    public boolean addMetaTag(SemSmartClipMetaTag tag) {
        if (tag == null) {
            return false;
        }
        return add(tag);
    }

    public int removeTags(String tagType) {
        return removeMetaTags(tagType);
    }

    public SemSmartClipMetaTagArray getTags(String tagType) {
        return getMetaTags(tagType);
    }

    public boolean addTag(SemSmartClipMetaTag tag) {
        return addMetaTag(tag);
    }

    public void addTag(SemSmartClipMetaTagArray tagArray) {
        if (tagArray == null) {
            return;
        }
        Iterator<SemSmartClipMetaTag> it = tagArray.iterator();
        while (it.hasNext()) {
            SemSmartClipMetaTag curTag = it.next();
            add(curTag);
        }
    }

    public SmartClipMetaTagArrayImpl getCopy() {
        SmartClipMetaTagArrayImpl copy = new SmartClipMetaTagArrayImpl();
        int arrayLen = size();
        for (int i = 0; i < arrayLen; i++) {
            SemSmartClipMetaTag tag = (SemSmartClipMetaTag) get(i);
            copy.add(tag);
        }
        return copy;
    }

    public void dump() {
        int tagCount = size();
        for (int i = 0; i < tagCount; i++) {
            SemSmartClipMetaTag tag = (SemSmartClipMetaTag) get(i);
            String type = tag.getType();
            String value = tag.getValue();
            String extra = "";
            if (value == null) {
                value = SemCapabilities.FEATURE_TAG_NULL;
            }
            if (tag instanceof SemSmartClipExtendedMetaTag) {
                SemSmartClipExtendedMetaTag tagImpl = (SemSmartClipExtendedMetaTag) tag;
                if (tagImpl.getExtraData() != null) {
                    extra = ", Extra data size = " + tagImpl.getExtraData().length;
                }
            }
            Log.d(TAG, type + NavigationBarInflaterView.KEY_CODE_START + value + extra + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        int count = size();
        out.writeInt(count);
        for (int i = 0; i < count; i++) {
            SemSmartClipMetaTag tag = (SemSmartClipMetaTag) get(i);
            if (tag instanceof SemSmartClipExtendedMetaTag) {
                SemSmartClipExtendedMetaTag tagImpl = (SemSmartClipExtendedMetaTag) tag;
                out.writeString("ParcelableMetaTag");
                out.writeParcelable(tagImpl, 0);
            } else {
                out.writeString("BasicMetaTag");
                out.writeString(tag.getType());
                out.writeString(tag.getValue());
                out.writeParcelable(tag.getBitmap(), flags);
            }
        }
    }

    public void readFromParcel(Parcel in) {
        int tagCount = in.readInt();
        for (int i = 0; i < tagCount; i++) {
            String objId = in.readString();
            SemSmartClipMetaTag tag = null;
            if (objId.equals("BasicMetaTag")) {
                String type = in.readString();
                String value = in.readString();
                Bitmap bitmap = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
                tag = new SemSmartClipMetaTag(type, value, bitmap);
            } else if (objId.equals("ParcelableMetaTag")) {
                tag = (SemSmartClipMetaTag) in.readParcelable(null);
            }
            if (tag == null) {
                Log.e(TAG, "readFromParcel : Could not read tag!!");
                return;
            }
            add(tag);
        }
    }

    /* renamed from: com.samsung.android.content.smartclip.SmartClipMetaTagArrayImpl$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SmartClipMetaTagArrayImpl> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SmartClipMetaTagArrayImpl createFromParcel(Parcel in) {
            Log.d(SmartClipMetaTagArrayImpl.TAG, "SmartClipMetaTagArrayImpl.createFromParcel called");
            SmartClipMetaTagArrayImpl data = new SmartClipMetaTagArrayImpl();
            data.readFromParcel(in);
            return data;
        }

        @Override // android.os.Parcelable.Creator
        public SmartClipMetaTagArrayImpl[] newArray(int size) {
            return new SmartClipMetaTagArrayImpl[size];
        }
    }
}
