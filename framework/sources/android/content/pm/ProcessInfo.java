package android.content.pm;

import android.annotation.NonNull;
import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Annotation;

/* loaded from: classes.dex */
public class ProcessInfo implements Parcelable {
    public static final Parcelable.Creator<ProcessInfo> CREATOR;
    static Parcelling<ArraySet<String>> sParcellingForDeniedPermissions;
    public ArraySet<String> deniedPermissions;
    public int gwpAsanMode;
    public int memtagMode;
    public String name;
    public int nativeHeapZeroInitialized;
    public boolean useEmbeddedDex;

    @Deprecated
    public ProcessInfo(ProcessInfo orig) {
        this.name = orig.name;
        this.deniedPermissions = orig.deniedPermissions;
        this.gwpAsanMode = orig.gwpAsanMode;
        this.memtagMode = orig.memtagMode;
        this.nativeHeapZeroInitialized = orig.nativeHeapZeroInitialized;
        this.useEmbeddedDex = orig.useEmbeddedDex;
    }

    public ProcessInfo(String name, ArraySet<String> deniedPermissions, int gwpAsanMode, int memtagMode, int nativeHeapZeroInitialized, boolean useEmbeddedDex) {
        this.name = name;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) name);
        this.deniedPermissions = deniedPermissions;
        this.gwpAsanMode = gwpAsanMode;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.GwpAsanMode.class, (Annotation) null, gwpAsanMode);
        this.memtagMode = memtagMode;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.MemtagMode.class, (Annotation) null, memtagMode);
        this.nativeHeapZeroInitialized = nativeHeapZeroInitialized;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.NativeHeapZeroInitialized.class, (Annotation) null, nativeHeapZeroInitialized);
        this.useEmbeddedDex = useEmbeddedDex;
    }

    static {
        sParcellingForDeniedPermissions = Parcelling.Cache.get(Parcelling.BuiltIn.ForInternedStringArraySet.class);
        if (sParcellingForDeniedPermissions == null) {
            sParcellingForDeniedPermissions = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInternedStringArraySet());
        }
        CREATOR = new Parcelable.Creator<ProcessInfo>() { // from class: android.content.pm.ProcessInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessInfo[] newArray(int size) {
                return new ProcessInfo[size];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessInfo createFromParcel(Parcel in) {
                return new ProcessInfo(in);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.useEmbeddedDex ? (byte) (0 | 32) : (byte) 0;
        if (this.deniedPermissions != null) {
            flg = (byte) (flg | 2);
        }
        dest.writeByte(flg);
        dest.writeString(this.name);
        sParcellingForDeniedPermissions.parcel(this.deniedPermissions, dest, flags);
        dest.writeInt(this.gwpAsanMode);
        dest.writeInt(this.memtagMode);
        dest.writeInt(this.nativeHeapZeroInitialized);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ProcessInfo(Parcel in) {
        byte flg = in.readByte();
        boolean _useEmbeddedDex = (flg & 32) != 0;
        String _name = in.readString();
        ArraySet<String> _deniedPermissions = sParcellingForDeniedPermissions.unparcel(in);
        int _gwpAsanMode = in.readInt();
        int _memtagMode = in.readInt();
        int _nativeHeapZeroInitialized = in.readInt();
        this.name = _name;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.name);
        this.deniedPermissions = _deniedPermissions;
        this.gwpAsanMode = _gwpAsanMode;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.GwpAsanMode.class, (Annotation) null, this.gwpAsanMode);
        this.memtagMode = _memtagMode;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.MemtagMode.class, (Annotation) null, this.memtagMode);
        this.nativeHeapZeroInitialized = _nativeHeapZeroInitialized;
        AnnotationValidations.validate((Class<? extends Annotation>) ApplicationInfo.NativeHeapZeroInitialized.class, (Annotation) null, this.nativeHeapZeroInitialized);
        this.useEmbeddedDex = _useEmbeddedDex;
    }

    @Deprecated
    private void __metadata() {
    }
}
