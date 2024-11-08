package androidx.slice;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import androidx.core.util.Pair;
import androidx.slice.compat.SliceProviderCompat;
import androidx.versionedparcelable.VersionedParcelable;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class SliceItemHolder implements VersionedParcelable {
    public static SliceProviderCompat.AnonymousClass2 sHandler;
    public static final Object sSerializeLock = new Object();
    public Bundle mBundle;
    public int mInt;
    public long mLong;
    public Parcelable mParcelable;
    public final SliceItemPool mPool;
    public String mStr;
    public VersionedParcelable mVersionedParcelable;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class SliceItemPool {
        public final ArrayList mCached = new ArrayList();
    }

    public SliceItemHolder(SliceItemPool sliceItemPool) {
        this.mVersionedParcelable = null;
        this.mParcelable = null;
        this.mStr = null;
        this.mInt = 0;
        this.mLong = 0L;
        this.mBundle = null;
        this.mPool = sliceItemPool;
    }

    public SliceItemHolder(String str, Object obj, boolean z) {
        String str2;
        this.mVersionedParcelable = null;
        this.mParcelable = null;
        this.mStr = null;
        this.mInt = 0;
        this.mLong = 0L;
        this.mBundle = null;
        str.getClass();
        char c = 65535;
        switch (str.hashCode()) {
            case -1422950858:
                if (str.equals("action")) {
                    c = 0;
                    break;
                }
                break;
            case -1377881982:
                if (str.equals("bundle")) {
                    c = 1;
                    break;
                }
                break;
            case 104431:
                if (str.equals("int")) {
                    c = 2;
                    break;
                }
                break;
            case 3327612:
                if (str.equals("long")) {
                    c = 3;
                    break;
                }
                break;
            case 3556653:
                if (str.equals("text")) {
                    c = 4;
                    break;
                }
                break;
            case 100313435:
                if (str.equals("image")) {
                    c = 5;
                    break;
                }
                break;
            case 100358090:
                if (str.equals("input")) {
                    c = 6;
                    break;
                }
                break;
            case 109526418:
                if (str.equals("slice")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                Pair pair = (Pair) obj;
                Object obj2 = pair.first;
                if (obj2 instanceof PendingIntent) {
                    this.mParcelable = (Parcelable) obj2;
                } else if (!z) {
                    throw new IllegalArgumentException("Cannot write callback to parcel");
                }
                this.mVersionedParcelable = (VersionedParcelable) pair.second;
                break;
            case 1:
                this.mBundle = (Bundle) obj;
                break;
            case 2:
                this.mInt = ((Integer) obj).intValue();
                break;
            case 3:
                this.mLong = ((Long) obj).longValue();
                break;
            case 4:
                if (obj instanceof Spanned) {
                    str2 = Html.toHtml((Spanned) obj, 0);
                } else {
                    str2 = (String) obj;
                }
                this.mStr = str2;
                break;
            case 5:
            case 7:
                this.mVersionedParcelable = (VersionedParcelable) obj;
                break;
            case 6:
                this.mParcelable = (Parcelable) obj;
                break;
        }
        SliceProviderCompat.AnonymousClass2 anonymousClass2 = sHandler;
        if (anonymousClass2 != null) {
            anonymousClass2.handle(this);
        }
    }
}
