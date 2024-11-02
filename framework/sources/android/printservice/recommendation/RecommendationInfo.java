package android.printservice.recommendation;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class RecommendationInfo implements Parcelable {
    public static final Parcelable.Creator<RecommendationInfo> CREATOR = new Parcelable.Creator<RecommendationInfo>() { // from class: android.printservice.recommendation.RecommendationInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public RecommendationInfo createFromParcel(Parcel in) {
            return new RecommendationInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public RecommendationInfo[] newArray(int size) {
            return new RecommendationInfo[size];
        }
    };
    private final List<InetAddress> mDiscoveredPrinters;
    private final CharSequence mName;
    private final CharSequence mPackageName;
    private final boolean mRecommendsMultiVendorService;

    /* synthetic */ RecommendationInfo(Parcel parcel, RecommendationInfoIA recommendationInfoIA) {
        this(parcel);
    }

    public RecommendationInfo(CharSequence packageName, CharSequence name, List<InetAddress> discoveredPrinters, boolean recommendsMultiVendorService) {
        this.mPackageName = Preconditions.checkStringNotEmpty(packageName);
        this.mName = Preconditions.checkStringNotEmpty(name);
        this.mDiscoveredPrinters = (List) Preconditions.checkCollectionElementsNotNull(discoveredPrinters, "discoveredPrinters");
        this.mRecommendsMultiVendorService = recommendsMultiVendorService;
    }

    @Deprecated
    public RecommendationInfo(CharSequence packageName, CharSequence name, int numDiscoveredPrinters, boolean recommendsMultiVendorService) {
        throw new IllegalArgumentException("This constructor has been deprecated");
    }

    private static ArrayList<InetAddress> readDiscoveredPrinters(Parcel parcel) {
        int numDiscoveredPrinters = parcel.readInt();
        ArrayList<InetAddress> discoveredPrinters = new ArrayList<>(numDiscoveredPrinters);
        for (int i = 0; i < numDiscoveredPrinters; i++) {
            try {
                discoveredPrinters.add(InetAddress.getByAddress(parcel.readBlob()));
            } catch (UnknownHostException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return discoveredPrinters;
    }

    private RecommendationInfo(Parcel parcel) {
        this(parcel.readCharSequence(), parcel.readCharSequence(), readDiscoveredPrinters(parcel), parcel.readByte() != 0);
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public boolean recommendsMultiVendorService() {
        return this.mRecommendsMultiVendorService;
    }

    public List<InetAddress> getDiscoveredPrinters() {
        return this.mDiscoveredPrinters;
    }

    public int getNumDiscoveredPrinters() {
        return this.mDiscoveredPrinters.size();
    }

    public CharSequence getName() {
        return this.mName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mPackageName);
        parcel.writeCharSequence(this.mName);
        parcel.writeInt(this.mDiscoveredPrinters.size());
        Iterator<InetAddress> it = this.mDiscoveredPrinters.iterator();
        while (it.hasNext()) {
            parcel.writeBlob(it.next().getAddress());
        }
        parcel.writeByte(this.mRecommendsMultiVendorService ? (byte) 1 : (byte) 0);
    }

    /* renamed from: android.printservice.recommendation.RecommendationInfo$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<RecommendationInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public RecommendationInfo createFromParcel(Parcel in) {
            return new RecommendationInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public RecommendationInfo[] newArray(int size) {
            return new RecommendationInfo[size];
        }
    }
}
