package com.samsung.android.sivs.ai.sdkcommon.asr;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sivs.ai.sdkcommon.tts.TtsPackageInfo;
import com.samsung.android.sivs.ai.sdkcommon.tts.TtsSpeakerInfo;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class BTCLocaleInfo extends LocaleInfo {
    public static final Parcelable.Creator<LocaleInfo> CREATOR = new Parcelable.Creator<LocaleInfo>() { // from class: com.samsung.android.sivs.ai.sdkcommon.asr.BTCLocaleInfo.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel, reason: merged with bridge method [inline-methods] */
        public LocaleInfo createFromParcel2(Parcel parcel) {
            return new BTCLocaleInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray, reason: merged with bridge method [inline-methods] */
        public LocaleInfo[] newArray2(int i) {
            return new BTCLocaleInfo[i];
        }
    };
    private final TtsSpeakerInfo defaultSpeaker;
    private final List<TtsPackageInfo> ttsPackage;
    private final List<TtsSpeakerInfo> ttsSpeaker;

    public BTCLocaleInfo(Locale locale, String str, int i, TtsSpeakerInfo ttsSpeakerInfo) {
        super(locale, str, i);
        this.ttsSpeaker = new LinkedList();
        this.ttsPackage = new LinkedList();
        this.defaultSpeaker = ttsSpeakerInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getDefaultPackages$0(TtsPackageInfo ttsPackageInfo) {
        return ttsPackageInfo.getSpeakerInfo().contains(this.defaultSpeaker);
    }

    @Override // com.samsung.android.sivs.ai.sdkcommon.asr.LocaleInfo, android.os.Parcelable
    public int describeContents() {
        return super.describeContents();
    }

    public List<TtsPackageInfo> getDefaultPackages() {
        return (List) this.ttsPackage.stream().filter(new Predicate() { // from class: com.samsung.android.sivs.ai.sdkcommon.asr.BTCLocaleInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getDefaultPackages$0;
                lambda$getDefaultPackages$0 = BTCLocaleInfo.this.lambda$getDefaultPackages$0((TtsPackageInfo) obj);
                return lambda$getDefaultPackages$0;
            }
        }).distinct().collect(Collectors.toList());
    }

    public TtsSpeakerInfo getDefaultSpeaker() {
        return this.defaultSpeaker;
    }

    public List<TtsPackageInfo> getTtsPackages() {
        return this.ttsPackage;
    }

    public List<TtsSpeakerInfo> getTtsSpeakers() {
        return this.ttsSpeaker;
    }

    public void setPackageInfo(List<TtsPackageInfo> list) {
        if (list != null) {
            this.ttsPackage.clear();
            this.ttsPackage.addAll(list);
        }
    }

    public void setSpeakerInfo(List<TtsSpeakerInfo> list) {
        if (list != null) {
            this.ttsSpeaker.clear();
            this.ttsSpeaker.addAll(list);
        }
    }

    @Override // com.samsung.android.sivs.ai.sdkcommon.asr.LocaleInfo
    public String toString() {
        return "BTCLocaleInfo{localeInfo=" + super.toString() + ", defaultSpeaker=" + this.defaultSpeaker + ", speaker=" + this.ttsSpeaker + ", package=" + this.ttsPackage + '}';
    }

    @Override // com.samsung.android.sivs.ai.sdkcommon.asr.LocaleInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.defaultSpeaker, 0);
        parcel.writeList(this.ttsSpeaker);
        parcel.writeList(this.ttsPackage);
    }

    public BTCLocaleInfo(Parcel parcel) {
        super(parcel);
        LinkedList linkedList = new LinkedList();
        this.ttsSpeaker = linkedList;
        LinkedList linkedList2 = new LinkedList();
        this.ttsPackage = linkedList2;
        this.defaultSpeaker = (TtsSpeakerInfo) parcel.readParcelable(TtsSpeakerInfo.class.getClassLoader());
        parcel.readList(linkedList, TtsSpeakerInfo.class.getClassLoader());
        parcel.readList(linkedList2, TtsPackageInfo.class.getClassLoader());
    }
}
