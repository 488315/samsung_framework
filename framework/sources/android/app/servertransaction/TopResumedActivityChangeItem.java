package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class TopResumedActivityChangeItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<TopResumedActivityChangeItem> CREATOR = new Parcelable.Creator<TopResumedActivityChangeItem>() { // from class: android.app.servertransaction.TopResumedActivityChangeItem.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TopResumedActivityChangeItem createFromParcel(Parcel in) {
            return new TopResumedActivityChangeItem(in);
        }

        @Override // android.os.Parcelable.Creator
        public TopResumedActivityChangeItem[] newArray(int size) {
            return new TopResumedActivityChangeItem[size];
        }
    };
    private boolean mOnTop;

    /* synthetic */ TopResumedActivityChangeItem(Parcel parcel, TopResumedActivityChangeItemIA topResumedActivityChangeItemIA) {
        this(parcel);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler client, ActivityThread.ActivityClientRecord r, PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "topResumedActivityChangeItem");
        client.handleTopResumedActivityChanged(r, this.mOnTop, "topResumedActivityChangeItem");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler client, IBinder token, PendingTransactionActions pendingActions) {
        if (this.mOnTop) {
            return;
        }
        ActivityClient.getInstance().activityTopResumedStateLost();
    }

    private TopResumedActivityChangeItem() {
    }

    public static TopResumedActivityChangeItem obtain(boolean onTop) {
        TopResumedActivityChangeItem instance = (TopResumedActivityChangeItem) ObjectPool.obtain(TopResumedActivityChangeItem.class);
        if (instance == null) {
            instance = new TopResumedActivityChangeItem();
        }
        instance.mOnTop = onTop;
        return instance;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mOnTop = false;
        ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mOnTop);
    }

    private TopResumedActivityChangeItem(Parcel in) {
        this.mOnTop = in.readBoolean();
    }

    /* renamed from: android.app.servertransaction.TopResumedActivityChangeItem$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<TopResumedActivityChangeItem> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TopResumedActivityChangeItem createFromParcel(Parcel in) {
            return new TopResumedActivityChangeItem(in);
        }

        @Override // android.os.Parcelable.Creator
        public TopResumedActivityChangeItem[] newArray(int size) {
            return new TopResumedActivityChangeItem[size];
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopResumedActivityChangeItem other = (TopResumedActivityChangeItem) o;
        if (this.mOnTop == other.mOnTop) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (17 * 31) + (this.mOnTop ? 1 : 0);
    }

    public String toString() {
        return "TopResumedActivityChangeItem{onTop=" + this.mOnTop + "}";
    }
}
