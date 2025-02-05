package android.app.compat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
final class ChangeIdStateQuery {
    static final int QUERY_BY_PACKAGE_NAME = 0;
    static final int QUERY_BY_UID = 1;
    public long changeId;
    public String packageName;
    public int type;
    public int uid;
    public int userId;

    @Retention(RetentionPolicy.SOURCE)
    @interface QueryType {
    }

    private ChangeIdStateQuery(int type, long changeId, String packageName, int uid, int userId) {
        this.type = type;
        this.changeId = changeId;
        this.packageName = packageName;
        this.uid = uid;
        this.userId = userId;
    }

    static ChangeIdStateQuery byPackageName(long changeId, String packageName, int userId) {
        return new ChangeIdStateQuery(0, changeId, packageName, 0, userId);
    }

    static ChangeIdStateQuery byUid(long changeId, int uid) {
        return new ChangeIdStateQuery(1, changeId, null, uid, 0);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof ChangeIdStateQuery)) {
            return false;
        }
        ChangeIdStateQuery that = (ChangeIdStateQuery) other;
        if (this.type == that.type && this.changeId == that.changeId && Objects.equals(this.packageName, that.packageName) && this.uid == that.uid && this.userId == that.userId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = (((1 * 31) + this.type) * 31) + ((int) (this.changeId ^ (this.changeId >>> 32)));
        if (this.packageName != null) {
            result = (result * 31) + this.packageName.hashCode();
        }
        return (((result * 31) + this.uid) * 31) + this.userId;
    }
}
