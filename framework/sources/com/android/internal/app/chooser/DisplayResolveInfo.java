package com.android.internal.app.chooser;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverListAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class DisplayResolveInfo implements TargetInfo, Parcelable {
    public static final Parcelable.Creator<DisplayResolveInfo> CREATOR = new Parcelable.Creator<DisplayResolveInfo>() { // from class: com.android.internal.app.chooser.DisplayResolveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayResolveInfo createFromParcel(Parcel in) {
            return new DisplayResolveInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayResolveInfo[] newArray(int size) {
            return new DisplayResolveInfo[size];
        }
    };
    private Drawable mDisplayIcon;
    private CharSequence mDisplayLabel;
    private CharSequence mExtendedInfo;
    private boolean mIsSkipFixUris;
    private boolean mIsSuspended;
    private boolean mPinned;
    private final ResolveInfo mResolveInfo;
    private ResolverListAdapter.ResolveInfoPresentationGetter mResolveInfoPresentationGetter;
    private final Intent mResolvedIntent;
    private ArrayList<DisplayResolveInfo> mSimilarList;
    private final List<Intent> mSourceIntents;

    public DisplayResolveInfo(Intent originalIntent, ResolveInfo pri, Intent pOrigIntent, ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        this(originalIntent, pri, null, null, pOrigIntent, resolveInfoPresentationGetter);
    }

    public DisplayResolveInfo(Intent originalIntent, ResolveInfo pri, CharSequence pLabel, CharSequence pInfo, Intent resolvedIntent, ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        this.mSourceIntents = new ArrayList();
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        this.mSourceIntents.add(originalIntent);
        this.mResolveInfo = pri;
        this.mDisplayLabel = pLabel;
        this.mExtendedInfo = pInfo;
        this.mResolveInfoPresentationGetter = resolveInfoPresentationGetter;
        Intent intent = new Intent(resolvedIntent);
        intent.addFlags(50331648);
        ActivityInfo ai = this.mResolveInfo.activityInfo;
        intent.setComponent(new ComponentName(ai.applicationInfo.packageName, ai.name));
        this.mIsSuspended = (ai.applicationInfo.flags & 1073741824) != 0;
        this.mResolvedIntent = intent;
    }

    private DisplayResolveInfo(DisplayResolveInfo other, Intent fillInIntent, int flags, ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        this.mSourceIntents = new ArrayList();
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        this.mSourceIntents.addAll(other.getAllSourceIntents());
        this.mResolveInfo = other.mResolveInfo;
        this.mDisplayLabel = other.mDisplayLabel;
        this.mDisplayIcon = other.mDisplayIcon;
        this.mExtendedInfo = other.mExtendedInfo;
        this.mResolvedIntent = new Intent(other.mResolvedIntent);
        this.mResolvedIntent.fillIn(fillInIntent, flags);
        this.mResolveInfoPresentationGetter = resolveInfoPresentationGetter;
    }

    DisplayResolveInfo(DisplayResolveInfo other) {
        this.mSourceIntents = new ArrayList();
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        this.mSourceIntents.addAll(other.getAllSourceIntents());
        this.mResolveInfo = other.mResolveInfo;
        this.mDisplayLabel = other.mDisplayLabel;
        this.mDisplayIcon = other.mDisplayIcon;
        this.mExtendedInfo = other.mExtendedInfo;
        this.mResolvedIntent = other.mResolvedIntent;
        this.mResolveInfoPresentationGetter = other.mResolveInfoPresentationGetter;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public CharSequence getDisplayLabel() {
        if (this.mDisplayLabel == null && this.mResolveInfoPresentationGetter != null) {
            this.mDisplayLabel = this.mResolveInfoPresentationGetter.getLabel();
            this.mExtendedInfo = this.mResolveInfoPresentationGetter.getSubLabel();
        }
        return this.mDisplayLabel;
    }

    public boolean hasDisplayLabel() {
        return this.mDisplayLabel != null;
    }

    public void setDisplayLabel(CharSequence displayLabel) {
        this.mDisplayLabel = displayLabel;
    }

    public void setExtendedInfo(CharSequence extendedInfo) {
        this.mExtendedInfo = extendedInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public Drawable getDisplayIcon(Context context) {
        return this.mDisplayIcon;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public TargetInfo cloneFilledIn(Intent fillInIntent, int flags) {
        return new DisplayResolveInfo(this, fillInIntent, flags, this.mResolveInfoPresentationGetter);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public List<Intent> getAllSourceIntents() {
        return this.mSourceIntents;
    }

    public void addAlternateSourceIntent(Intent alt) {
        this.mSourceIntents.add(alt);
    }

    public void setDisplayIcon(Drawable icon) {
        this.mDisplayIcon = icon;
    }

    public boolean hasDisplayIcon() {
        return this.mDisplayIcon != null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public CharSequence getExtendedInfo() {
        return this.mExtendedInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public Intent getResolvedIntent() {
        return this.mResolvedIntent;
    }

    public ArrayList<DisplayResolveInfo> getSimilarList() {
        return this.mSimilarList;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public ComponentName getResolvedComponentName() {
        return new ComponentName(this.mResolveInfo.activityInfo.packageName, this.mResolveInfo.activityInfo.name);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean start(Activity activity, Bundle options) {
        activity.startActivity(this.mResolvedIntent, options);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsCaller(ResolverActivity activity, Bundle options, int userId) {
        if (!this.mIsSkipFixUris) {
            TargetInfo.prepareIntentForCrossProfileLaunch(this.mResolvedIntent, userId);
        }
        activity.startActivityAsCaller(this.mResolvedIntent, options, false, userId);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsUser(Activity activity, Bundle options, UserHandle user) {
        if (!this.mIsSkipFixUris) {
            TargetInfo.prepareIntentForCrossProfileLaunch(this.mResolvedIntent, user.getIdentifier());
        }
        activity.startActivityAsUser(this.mResolvedIntent, options, user);
        return false;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isPinned() {
        return this.mPinned;
    }

    public void setPinned(boolean pinned) {
        this.mPinned = pinned;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeCharSequence(this.mDisplayLabel);
        dest.writeCharSequence(this.mExtendedInfo);
        dest.writeParcelable(this.mResolvedIntent, 0);
        dest.writeTypedList(this.mSourceIntents);
        dest.writeBoolean(this.mIsSuspended);
        dest.writeBoolean(this.mPinned);
        dest.writeParcelable(this.mResolveInfo, 0);
    }

    private DisplayResolveInfo(Parcel in) {
        this.mSourceIntents = new ArrayList();
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        this.mDisplayLabel = in.readCharSequence();
        this.mExtendedInfo = in.readCharSequence();
        this.mResolvedIntent = (Intent) in.readParcelable(null, Intent.class);
        in.readTypedList(this.mSourceIntents, Intent.CREATOR);
        this.mIsSuspended = in.readBoolean();
        this.mPinned = in.readBoolean();
        this.mResolveInfo = (ResolveInfo) in.readParcelable(null, ResolveInfo.class);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public void setSkipFixUris(boolean skipFixUris) {
        this.mIsSkipFixUris = true;
    }
}
