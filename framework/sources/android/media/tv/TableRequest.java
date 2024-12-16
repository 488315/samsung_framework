package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class TableRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<TableRequest> CREATOR = new Parcelable.Creator<TableRequest>() { // from class: android.media.tv.TableRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableRequest createFromParcel(Parcel source) {
            source.readInt();
            return TableRequest.createFromParcelBody(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableRequest[] newArray(int size) {
            return new TableRequest[size];
        }
    };
    private static final int REQUEST_TYPE = 2;
    public static final int TABLE_NAME_BAT = 4;
    public static final int TABLE_NAME_CAT = 2;
    public static final int TABLE_NAME_EIT = 6;
    public static final int TABLE_NAME_NIT = 3;
    public static final int TABLE_NAME_PAT = 0;
    public static final int TABLE_NAME_PMT = 1;
    public static final int TABLE_NAME_SDT = 5;
    public static final int TABLE_NAME_SIT = 9;
    public static final int TABLE_NAME_TDT = 7;
    public static final int TABLE_NAME_TOT = 8;
    private final int mTableId;
    private final int mTableName;
    private final int mVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TableName {
    }

    static TableRequest createFromParcelBody(Parcel in) {
        return new TableRequest(in);
    }

    public TableRequest(int requestId, int option, int tableId, int tableName, int version) {
        super(2, requestId, option);
        this.mTableId = tableId;
        this.mTableName = tableName;
        this.mVersion = version;
    }

    TableRequest(Parcel source) {
        super(2, source);
        this.mTableId = source.readInt();
        this.mTableName = source.readInt();
        this.mVersion = source.readInt();
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getTableName() {
        return this.mTableName;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mTableId);
        dest.writeInt(this.mTableName);
        dest.writeInt(this.mVersion);
    }
}
