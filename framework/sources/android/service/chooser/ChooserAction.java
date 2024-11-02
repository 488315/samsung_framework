package android.service.chooser;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ChooserAction implements Parcelable {
    public static final Parcelable.Creator<ChooserAction> CREATOR = new Parcelable.Creator<ChooserAction>() { // from class: android.service.chooser.ChooserAction.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ChooserAction createFromParcel(Parcel source) {
            return new ChooserAction(Icon.CREATOR.createFromParcel(source), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source), PendingIntent.CREATOR.createFromParcel(source));
        }

        @Override // android.os.Parcelable.Creator
        public ChooserAction[] newArray(int size) {
            return new ChooserAction[size];
        }
    };
    private final PendingIntent mAction;
    private final Icon mIcon;
    private final CharSequence mLabel;

    /* synthetic */ ChooserAction(Icon icon, CharSequence charSequence, PendingIntent pendingIntent, ChooserActionIA chooserActionIA) {
        this(icon, charSequence, pendingIntent);
    }

    private ChooserAction(Icon icon, CharSequence label, PendingIntent action) {
        this.mIcon = icon;
        this.mLabel = label;
        this.mAction = action;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public PendingIntent getAction() {
        return this.mAction;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mIcon.writeToParcel(dest, flags);
        TextUtils.writeToParcel(this.mLabel, dest, flags);
        this.mAction.writeToParcel(dest, flags);
    }

    public String toString() {
        return "ChooserAction {label=" + ((Object) this.mLabel) + ", intent=" + this.mAction + "}";
    }

    /* renamed from: android.service.chooser.ChooserAction$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<ChooserAction> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ChooserAction createFromParcel(Parcel source) {
            return new ChooserAction(Icon.CREATOR.createFromParcel(source), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source), PendingIntent.CREATOR.createFromParcel(source));
        }

        @Override // android.os.Parcelable.Creator
        public ChooserAction[] newArray(int size) {
            return new ChooserAction[size];
        }
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private final PendingIntent mAction;
        private final Icon mIcon;
        private final CharSequence mLabel;

        public Builder(Icon icon, CharSequence label, PendingIntent action) {
            Objects.requireNonNull(icon, "icon can not be null");
            Objects.requireNonNull(label, "label can not be null");
            Objects.requireNonNull(action, "pending intent can not be null");
            this.mIcon = icon;
            this.mLabel = label;
            this.mAction = action;
        }

        public ChooserAction build() {
            return new ChooserAction(this.mIcon, this.mLabel, this.mAction);
        }
    }
}
