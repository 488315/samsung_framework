package com.samsung.android.allshare.media;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class ContentInfo implements Parcelable {
    public static final Parcelable.Creator<ContentInfo> CREATOR = new Parcelable.Creator<ContentInfo>() { // from class: com.samsung.android.allshare.media.ContentInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ContentInfo createFromParcel(Parcel src) {
            return new ContentInfo(src);
        }

        @Override // android.os.Parcelable.Creator
        public ContentInfo[] newArray(int size) {
            return new ContentInfo[size];
        }
    };
    private long mStartingPosition;

    /* synthetic */ ContentInfo(Parcel parcel, ContentInfoIA contentInfoIA) {
        this(parcel);
    }

    /* synthetic */ ContentInfo(Builder builder, ContentInfoIA contentInfoIA) {
        this(builder);
    }

    private ContentInfo() {
    }

    private ContentInfo(Builder builder) {
        this.mStartingPosition = builder.mStartingPosition;
    }

    public long getStartingPosition() {
        return this.mStartingPosition;
    }

    /* loaded from: classes5.dex */
    public static class Builder {
        private long mStartingPosition = 0;

        public Builder setStartingPosition(long position) {
            this.mStartingPosition = position;
            return this;
        }

        public ContentInfo build() {
            if (this.mStartingPosition < 0) {
                return null;
            }
            return new ContentInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mStartingPosition);
    }

    private void readFromParcel(Parcel src) {
        this.mStartingPosition = src.readLong();
    }

    private ContentInfo(Parcel src) {
        readFromParcel(src);
    }

    /* renamed from: com.samsung.android.allshare.media.ContentInfo$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<ContentInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ContentInfo createFromParcel(Parcel src) {
            return new ContentInfo(src);
        }

        @Override // android.os.Parcelable.Creator
        public ContentInfo[] newArray(int size) {
            return new ContentInfo[size];
        }
    }
}
