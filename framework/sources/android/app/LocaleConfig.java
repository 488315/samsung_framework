package android.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class LocaleConfig implements Parcelable {
    public static final Parcelable.Creator<LocaleConfig> CREATOR = new Parcelable.Creator<LocaleConfig>() { // from class: android.app.LocaleConfig.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public LocaleConfig createFromParcel(Parcel source) {
            return new LocaleConfig(source);
        }

        @Override // android.os.Parcelable.Creator
        public LocaleConfig[] newArray(int size) {
            return new LocaleConfig[size];
        }
    };
    public static final int STATUS_NOT_SPECIFIED = 1;
    public static final int STATUS_PARSING_FAILED = 2;
    public static final int STATUS_SUCCESS = 0;
    private static final String TAG = "LocaleConfig";
    public static final String TAG_LOCALE = "locale";
    public static final String TAG_LOCALE_CONFIG = "locale-config";
    private LocaleList mLocales;
    private int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Status {
    }

    /* synthetic */ LocaleConfig(Parcel parcel, LocaleConfigIA localeConfigIA) {
        this(parcel);
    }

    public LocaleConfig(Context context) {
        this(context, true);
    }

    public static LocaleConfig fromContextIgnoringOverride(Context context) {
        return new LocaleConfig(context, false);
    }

    private LocaleConfig(Context context, boolean allowOverride) {
        this.mStatus = 1;
        if (allowOverride) {
            LocaleManager localeManager = (LocaleManager) context.getSystemService(LocaleManager.class);
            if (localeManager == null) {
                Slog.w(TAG, "LocaleManager is null, cannot get the override LocaleConfig");
                this.mStatus = 1;
                return;
            }
            LocaleConfig localeConfig = localeManager.getOverrideLocaleConfig();
            if (localeConfig != null) {
                Slog.d(TAG, "Has the override LocaleConfig");
                this.mStatus = localeConfig.getStatus();
                this.mLocales = localeConfig.getSupportedLocales();
                return;
            }
        }
        int resId = 0;
        Resources res = context.getResources();
        try {
            resId = new ApplicationInfo(context.getApplicationInfo()).getLocaleConfigRes();
            XmlResourceParser parser = res.getXml(resId);
            parseLocaleConfig(parser, res);
        } catch (Resources.NotFoundException e) {
            Slog.w(TAG, "The resource file pointed to by the given resource ID isn't found.");
            this.mStatus = 1;
        } catch (IOException | XmlPullParserException e2) {
            Slog.w(TAG, "Failed to parse XML configuration from " + res.getResourceEntryName(resId), e2);
            this.mStatus = 2;
        }
    }

    public LocaleConfig(LocaleList locales) {
        this.mStatus = 1;
        this.mStatus = 0;
        this.mLocales = locales;
    }

    private LocaleConfig(Parcel in) {
        this.mStatus = 1;
        this.mStatus = in.readInt();
        this.mLocales = (LocaleList) in.readTypedObject(LocaleList.CREATOR);
    }

    private void parseLocaleConfig(XmlResourceParser parser, Resources res) throws IOException, XmlPullParserException {
        XmlUtils.beginDocument(parser, TAG_LOCALE_CONFIG);
        int outerDepth = parser.getDepth();
        AttributeSet attrs = Xml.asAttributeSet(parser);
        Set<String> localeNames = new HashSet<>();
        while (XmlUtils.nextElementWithin(parser, outerDepth)) {
            if ("locale".equals(parser.getName())) {
                TypedArray attributes = res.obtainAttributes(attrs, R.styleable.LocaleConfig_Locale);
                String nameAttr = attributes.getString(0);
                localeNames.add(nameAttr);
                attributes.recycle();
            } else {
                XmlUtils.skipCurrentTag(parser);
            }
        }
        this.mStatus = 0;
        this.mLocales = LocaleList.forLanguageTags(String.join(",", localeNames));
    }

    public LocaleList getSupportedLocales() {
        return this.mLocales;
    }

    public int getStatus() {
        return this.mStatus;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mStatus);
        dest.writeTypedObject(this.mLocales, flags);
    }

    /* renamed from: android.app.LocaleConfig$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<LocaleConfig> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public LocaleConfig createFromParcel(Parcel source) {
            return new LocaleConfig(source);
        }

        @Override // android.os.Parcelable.Creator
        public LocaleConfig[] newArray(int size) {
            return new LocaleConfig[size];
        }
    }

    public boolean isSameLocaleConfig(LocaleConfig other) {
        if (other == this) {
            return true;
        }
        if (other == null || this.mStatus != other.mStatus) {
            return false;
        }
        LocaleList otherLocales = other.mLocales;
        LocaleList localeList = this.mLocales;
        if (localeList == null && otherLocales == null) {
            return true;
        }
        if (localeList != null && otherLocales != null) {
            List<String> hostStrList = Arrays.asList(localeList.toLanguageTags().split(","));
            List<String> targetStrList = Arrays.asList(otherLocales.toLanguageTags().split(","));
            Collections.sort(hostStrList);
            Collections.sort(targetStrList);
            return hostStrList.equals(targetStrList);
        }
        return false;
    }

    public boolean containsLocale(Locale locale) {
        if (this.mLocales == null) {
            return false;
        }
        for (int i = 0; i < this.mLocales.size(); i++) {
            if (LocaleList.matchesLanguageAndScript(this.mLocales.get(i), locale)) {
                return true;
            }
        }
        return false;
    }
}
