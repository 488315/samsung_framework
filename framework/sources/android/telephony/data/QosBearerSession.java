package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class QosBearerSession implements Parcelable {
    public static final Parcelable.Creator<QosBearerSession> CREATOR = new Parcelable.Creator<QosBearerSession>() { // from class: android.telephony.data.QosBearerSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBearerSession createFromParcel(Parcel source) {
            return new QosBearerSession(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBearerSession[] newArray(int size) {
            return new QosBearerSession[size];
        }
    };
    final Qos qos;
    final List<QosBearerFilter> qosBearerFilterList;
    final int qosBearerSessionId;

    public QosBearerSession(int qosBearerSessionId, Qos qos, List<QosBearerFilter> qosBearerFilterList) {
        this.qosBearerSessionId = qosBearerSessionId;
        this.qos = qos;
        this.qosBearerFilterList = new ArrayList();
        this.qosBearerFilterList.addAll(qosBearerFilterList);
    }

    private QosBearerSession(Parcel source) {
        this.qosBearerSessionId = source.readInt();
        this.qos = (Qos) source.readParcelable(Qos.class.getClassLoader(), Qos.class);
        this.qosBearerFilterList = new ArrayList();
        source.readList(this.qosBearerFilterList, QosBearerFilter.class.getClassLoader(), QosBearerFilter.class);
    }

    public int getQosBearerSessionId() {
        return this.qosBearerSessionId;
    }

    public Qos getQos() {
        return this.qos;
    }

    public List<QosBearerFilter> getQosBearerFilterList() {
        return this.qosBearerFilterList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.qosBearerSessionId);
        if (this.qos.getType() == 1) {
            dest.writeParcelable((EpsQos) this.qos, flags);
        } else {
            dest.writeParcelable((NrQos) this.qos, flags);
        }
        dest.writeList(this.qosBearerFilterList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "QosBearerSession { qosBearerSessionId=" + this.qosBearerSessionId + " qos=" + this.qos + " qosBearerFilterList=" + this.qosBearerFilterList + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.qosBearerSessionId), this.qos, this.qosBearerFilterList);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof QosBearerSession)) {
            return false;
        }
        QosBearerSession other = (QosBearerSession) o;
        if (this.qosBearerSessionId == other.qosBearerSessionId && Objects.equals(this.qos, other.qos) && this.qosBearerFilterList.size() == other.qosBearerFilterList.size() && this.qosBearerFilterList.containsAll(other.qosBearerFilterList)) {
            return true;
        }
        return false;
    }
}
