package android.window;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.window.IOnBackInvokedCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class BackNavigationInfo implements Parcelable {
    public static final Parcelable.Creator<BackNavigationInfo> CREATOR = new Parcelable.Creator<BackNavigationInfo>() { // from class: android.window.BackNavigationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackNavigationInfo createFromParcel(Parcel in) {
            return new BackNavigationInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackNavigationInfo[] newArray(int size) {
            return new BackNavigationInfo[size];
        }
    };
    public static final String KEY_GESTURE_FINISHED = "GestureFinished";
    public static final String KEY_NAVIGATION_FINISHED = "NavigationFinished";
    public static final int TYPE_CALLBACK = 4;
    public static final int TYPE_CROSS_ACTIVITY = 2;
    public static final int TYPE_CROSS_TASK = 3;
    public static final int TYPE_DIALOG_CLOSE = 0;
    public static final int TYPE_RETURN_TO_HOME = 1;
    public static final int TYPE_UNDEFINED = -1;
    private final boolean mAnimationCallback;
    private final boolean mAppProgressGenerationAllowed;
    private final CustomAnimationInfo mCustomAnimationInfo;
    private final int mLetterboxColor;
    private final IOnBackInvokedCallback mOnBackInvokedCallback;
    private final RemoteCallback mOnBackNavigationDone;
    private final boolean mPrepareRemoteAnimation;
    private final Rect mTouchableRegion;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackTargetType {
    }

    private BackNavigationInfo(int type, RemoteCallback onBackNavigationDone, IOnBackInvokedCallback onBackInvokedCallback, boolean isPrepareRemoteAnimation, boolean isAnimationCallback, CustomAnimationInfo customAnimationInfo, int letterboxColor, Rect touchableRegion, boolean appProgressGenerationAllowed) {
        this.mType = type;
        this.mOnBackNavigationDone = onBackNavigationDone;
        this.mOnBackInvokedCallback = onBackInvokedCallback;
        this.mPrepareRemoteAnimation = isPrepareRemoteAnimation;
        this.mAnimationCallback = isAnimationCallback;
        this.mCustomAnimationInfo = customAnimationInfo;
        this.mLetterboxColor = letterboxColor;
        this.mTouchableRegion = new Rect(touchableRegion);
        this.mAppProgressGenerationAllowed = appProgressGenerationAllowed;
    }

    private BackNavigationInfo(Parcel in) {
        this.mType = in.readInt();
        this.mOnBackNavigationDone = (RemoteCallback) in.readTypedObject(RemoteCallback.CREATOR);
        this.mOnBackInvokedCallback = IOnBackInvokedCallback.Stub.asInterface(in.readStrongBinder());
        this.mPrepareRemoteAnimation = in.readBoolean();
        this.mAnimationCallback = in.readBoolean();
        this.mCustomAnimationInfo = (CustomAnimationInfo) in.readTypedObject(CustomAnimationInfo.CREATOR);
        this.mLetterboxColor = in.readInt();
        this.mTouchableRegion = (Rect) in.readTypedObject(Rect.CREATOR);
        this.mAppProgressGenerationAllowed = in.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeTypedObject(this.mOnBackNavigationDone, flags);
        dest.writeStrongInterface(this.mOnBackInvokedCallback);
        dest.writeBoolean(this.mPrepareRemoteAnimation);
        dest.writeBoolean(this.mAnimationCallback);
        dest.writeTypedObject(this.mCustomAnimationInfo, flags);
        dest.writeInt(this.mLetterboxColor);
        dest.writeTypedObject(this.mTouchableRegion, flags);
        dest.writeBoolean(this.mAppProgressGenerationAllowed);
    }

    public int getType() {
        return this.mType;
    }

    public IOnBackInvokedCallback getOnBackInvokedCallback() {
        return this.mOnBackInvokedCallback;
    }

    public boolean isPrepareRemoteAnimation() {
        return this.mPrepareRemoteAnimation;
    }

    public boolean isAnimationCallback() {
        return this.mAnimationCallback;
    }

    public int getLetterboxColor() {
        return this.mLetterboxColor;
    }

    public Rect getTouchableRegion() {
        return this.mTouchableRegion;
    }

    public boolean isAppProgressGenerationAllowed() {
        return this.mAppProgressGenerationAllowed;
    }

    public void onBackNavigationFinished(boolean triggerBack) {
        if (this.mOnBackNavigationDone != null) {
            Bundle result = new Bundle();
            result.putBoolean(KEY_NAVIGATION_FINISHED, triggerBack);
            this.mOnBackNavigationDone.sendResult(result);
        }
    }

    public void onBackGestureFinished(boolean triggerBack) {
        if (this.mOnBackNavigationDone != null) {
            Bundle result = new Bundle();
            result.putBoolean(KEY_GESTURE_FINISHED, triggerBack);
            this.mOnBackNavigationDone.sendResult(result);
        }
    }

    public CustomAnimationInfo getCustomAnimationInfo() {
        return this.mCustomAnimationInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "BackNavigationInfo{mType=" + typeToString(this.mType) + " (" + this.mType + "), mOnBackNavigationDone=" + this.mOnBackNavigationDone + ", mOnBackInvokedCallback=" + this.mOnBackInvokedCallback + ", mPrepareRemoteAnimation=" + this.mPrepareRemoteAnimation + ", mAnimationCallback=" + this.mAnimationCallback + ", mCustomizeAnimationInfo=" + this.mCustomAnimationInfo + '}';
    }

    public static String typeToString(int type) {
        switch (type) {
            case -1:
                return "TYPE_UNDEFINED";
            case 0:
                return "TYPE_DIALOG_CLOSE";
            case 1:
                return "TYPE_RETURN_TO_HOME";
            case 2:
                return "TYPE_CROSS_ACTIVITY";
            case 3:
                return "TYPE_CROSS_TASK";
            case 4:
                return "TYPE_CALLBACK";
            default:
                return String.valueOf(type);
        }
    }

    public static final class CustomAnimationInfo implements Parcelable {
        public static final Parcelable.Creator<CustomAnimationInfo> CREATOR = new Parcelable.Creator<CustomAnimationInfo>() { // from class: android.window.BackNavigationInfo.CustomAnimationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAnimationInfo createFromParcel(Parcel in) {
                return new CustomAnimationInfo(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAnimationInfo[] newArray(int size) {
                return new CustomAnimationInfo[size];
            }
        };
        private int mCustomBackground;
        private int mCustomEnterAnim;
        private int mCustomExitAnim;
        private final String mPackageName;
        private int mWindowAnimations;

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getWindowAnimations() {
            return this.mWindowAnimations;
        }

        public int getCustomExitAnim() {
            return this.mCustomExitAnim;
        }

        public int getCustomEnterAnim() {
            return this.mCustomEnterAnim;
        }

        public int getCustomBackground() {
            return this.mCustomBackground;
        }

        public CustomAnimationInfo(String packageName) {
            this.mPackageName = packageName;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString8(this.mPackageName);
            dest.writeInt(this.mWindowAnimations);
            dest.writeInt(this.mCustomEnterAnim);
            dest.writeInt(this.mCustomExitAnim);
            dest.writeInt(this.mCustomBackground);
        }

        private CustomAnimationInfo(Parcel in) {
            this.mPackageName = in.readString8();
            this.mWindowAnimations = in.readInt();
            this.mCustomEnterAnim = in.readInt();
            this.mCustomExitAnim = in.readInt();
            this.mCustomBackground = in.readInt();
        }

        public String toString() {
            return "CustomAnimationInfo, package name= " + this.mPackageName;
        }
    }

    public static class Builder {
        private boolean mAppProgressGenerationAllowed;
        private CustomAnimationInfo mCustomAnimationInfo;
        private boolean mPrepareRemoteAnimation;
        private Rect mTouchableRegion;
        private int mType = -1;
        private RemoteCallback mOnBackNavigationDone = null;
        private IOnBackInvokedCallback mOnBackInvokedCallback = null;
        private boolean mAnimationCallback = false;
        private int mLetterboxColor = 0;

        public Builder setType(int type) {
            this.mType = type;
            return this;
        }

        public Builder setOnBackNavigationDone(RemoteCallback onBackNavigationDone) {
            this.mOnBackNavigationDone = onBackNavigationDone;
            return this;
        }

        public Builder setOnBackInvokedCallback(IOnBackInvokedCallback onBackInvokedCallback) {
            this.mOnBackInvokedCallback = onBackInvokedCallback;
            return this;
        }

        public Builder setPrepareRemoteAnimation(boolean prepareRemoteAnimation) {
            this.mPrepareRemoteAnimation = prepareRemoteAnimation;
            return this;
        }

        public Builder setWindowAnimations(String packageName, int windowAnimations) {
            if (this.mCustomAnimationInfo == null) {
                this.mCustomAnimationInfo = new CustomAnimationInfo(packageName);
            }
            this.mCustomAnimationInfo.mWindowAnimations = windowAnimations;
            return this;
        }

        public Builder setCustomAnimation(String packageName, int enterResId, int exitResId, int backgroundColor) {
            if (this.mCustomAnimationInfo == null) {
                this.mCustomAnimationInfo = new CustomAnimationInfo(packageName);
            }
            this.mCustomAnimationInfo.mCustomExitAnim = exitResId;
            this.mCustomAnimationInfo.mCustomEnterAnim = enterResId;
            this.mCustomAnimationInfo.mCustomBackground = backgroundColor;
            return this;
        }

        public Builder setAnimationCallback(boolean isAnimationCallback) {
            this.mAnimationCallback = isAnimationCallback;
            return this;
        }

        public Builder setLetterboxColor(int color) {
            this.mLetterboxColor = color;
            return this;
        }

        public Builder setTouchableRegion(Rect rect) {
            this.mTouchableRegion = new Rect(rect);
            return this;
        }

        public Builder setAppProgressAllowed(boolean allowed) {
            this.mAppProgressGenerationAllowed = allowed;
            return this;
        }

        public BackNavigationInfo build() {
            return new BackNavigationInfo(this.mType, this.mOnBackNavigationDone, this.mOnBackInvokedCallback, this.mPrepareRemoteAnimation, this.mAnimationCallback, this.mCustomAnimationInfo, this.mLetterboxColor, this.mTouchableRegion, this.mAppProgressGenerationAllowed);
        }
    }
}
