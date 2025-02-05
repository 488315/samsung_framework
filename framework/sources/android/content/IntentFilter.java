package android.content;

import android.annotation.SystemApi;
import android.hardware.scontext.SContextConstants;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.ArraySet;
import android.util.Log;
import android.util.Printer;
import android.util.proto.ProtoOutputStream;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class IntentFilter implements Parcelable {
    private static final String ACTION_STR = "action";
    private static final String AGLOB_STR = "aglob";
    private static final String AUTH_STR = "auth";
    private static final String AUTO_VERIFY_STR = "autoVerify";
    public static final long BLOCK_NULL_ACTION_INTENTS = 293560872;
    private static final String CAT_STR = "cat";
    private static final String EXTRAS_STR = "extras";
    private static final String GROUP_STR = "group";
    private static final String HOST_STR = "host";
    private static final String LITERAL_STR = "literal";
    public static final int MATCH_ADJUSTMENT_MASK = 65535;
    public static final int MATCH_ADJUSTMENT_NORMAL = 32768;
    public static final int MATCH_CATEGORY_EMPTY = 1048576;
    public static final int MATCH_CATEGORY_HOST = 3145728;
    public static final int MATCH_CATEGORY_MASK = 268369920;
    public static final int MATCH_CATEGORY_PATH = 5242880;
    public static final int MATCH_CATEGORY_PORT = 4194304;
    public static final int MATCH_CATEGORY_SCHEME = 2097152;
    public static final int MATCH_CATEGORY_SCHEME_SPECIFIC_PART = 5767168;
    public static final int MATCH_CATEGORY_TYPE = 6291456;
    private static final String NAME_STR = "name";
    public static final int NO_MATCH_ACTION = -3;
    public static final int NO_MATCH_CATEGORY = -4;
    public static final int NO_MATCH_DATA = -2;
    public static final int NO_MATCH_EXTRAS = -5;
    public static final int NO_MATCH_TYPE = -1;
    private static final String PATH_STR = "path";
    private static final String PORT_STR = "port";
    private static final String PREFIX_STR = "prefix";
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    public static final String SCHEME_PACKAGE = "package";
    private static final String SCHEME_STR = "scheme";
    private static final String SGLOB_STR = "sglob";
    private static final String SSP_STR = "ssp";
    private static final int STATE_NEED_VERIFY = 16;
    private static final int STATE_NEED_VERIFY_CHECKED = 256;
    private static final int STATE_VERIFIED = 4096;
    private static final int STATE_VERIFY_AUTO = 1;
    private static final String STATIC_TYPE_STR = "staticType";
    private static final String SUFFIX_STR = "suffix";
    public static final int SYSTEM_HIGH_PRIORITY = 1000;
    public static final int SYSTEM_LOW_PRIORITY = -1000;
    private static final String TAG = "IntentFilter";
    private static final String TYPE_STR = "type";
    private static final String URI_RELATIVE_FILTER_GROUP_STR = "uriRelativeFilterGroup";
    public static final int VISIBILITY_EXPLICIT = 1;
    public static final int VISIBILITY_IMPLICIT = 2;
    public static final int VISIBILITY_NONE = 0;
    public static final String WILDCARD = "*";
    public static final String WILDCARD_PATH = "/*";
    private final ArraySet<String> mActions;
    private ArrayList<String> mCategories;
    private ArrayList<AuthorityEntry> mDataAuthorities;
    private ArrayList<PatternMatcher> mDataPaths;
    private ArrayList<PatternMatcher> mDataSchemeSpecificParts;
    private ArrayList<String> mDataSchemes;
    private ArrayList<String> mDataTypes;
    private PersistableBundle mExtras;
    private boolean mHasDynamicPartialTypes;
    private boolean mHasStaticPartialTypes;
    private int mInstantAppVisibility;
    private ArrayList<String> mMimeGroups;
    private int mOrder;
    private int mPriority;
    private ArrayList<String> mStaticDataTypes;
    private ArrayList<UriRelativeFilterGroup> mUriRelativeFilterGroups;
    private int mVerifyState;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Parcelable.Creator<IntentFilter> CREATOR = new Parcelable.Creator<IntentFilter>() { // from class: android.content.IntentFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilter createFromParcel(Parcel source) {
            return new IntentFilter(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilter[] newArray(int size) {
            return new IntentFilter[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstantAppVisibility {
    }

    private static int findStringInSet(String[] set, String string, int[] lengths, int lenPos) {
        if (set == null) {
            return -1;
        }
        int N = lengths[lenPos];
        for (int i = 0; i < N; i++) {
            if (set[i].equals(string)) {
                return i;
            }
        }
        return -1;
    }

    private static String[] addStringToSet(String[] set, String string, int[] lengths, int lenPos) {
        if (findStringInSet(set, string, lengths, lenPos) >= 0) {
            return set;
        }
        if (set == null) {
            String[] set2 = new String[2];
            set2[0] = string;
            lengths[lenPos] = 1;
            return set2;
        }
        int N = lengths[lenPos];
        if (N < set.length) {
            set[N] = string;
            lengths[lenPos] = N + 1;
            return set;
        }
        String[] newSet = new String[((N * 3) / 2) + 2];
        System.arraycopy(set, 0, newSet, 0, N);
        newSet[N] = string;
        lengths[lenPos] = N + 1;
        return newSet;
    }

    private static String[] removeStringFromSet(String[] set, String string, int[] lengths, int lenPos) {
        int pos = findStringInSet(set, string, lengths, lenPos);
        if (pos < 0) {
            return set;
        }
        int N = lengths[lenPos];
        if (N > set.length / 4) {
            int copyLen = N - (pos + 1);
            if (copyLen > 0) {
                System.arraycopy(set, pos + 1, set, pos, copyLen);
            }
            set[N - 1] = null;
            lengths[lenPos] = N - 1;
            return set;
        }
        String[] newSet = new String[set.length / 3];
        if (pos > 0) {
            System.arraycopy(set, 0, newSet, 0, pos);
        }
        if (pos + 1 < N) {
            System.arraycopy(set, pos + 1, newSet, pos, N - (pos + 1));
        }
        return newSet;
    }

    public static class MalformedMimeTypeException extends AndroidException {
        public MalformedMimeTypeException() {
        }

        public MalformedMimeTypeException(String name) {
            super(name);
        }
    }

    public static IntentFilter create(String action, String dataType) {
        try {
            return new IntentFilter(action, dataType);
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("Bad MIME type", e);
        }
    }

    public IntentFilter() {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new ArraySet<>();
    }

    public IntentFilter(String action) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new ArraySet<>();
        addAction(action);
    }

    public IntentFilter(String action, String dataType) throws MalformedMimeTypeException {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = 0;
        this.mActions = new ArraySet<>();
        addAction(action);
        addDataType(dataType);
    }

    public IntentFilter(IntentFilter o) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        this.mPriority = o.mPriority;
        this.mOrder = o.mOrder;
        this.mActions = new ArraySet<>((ArraySet) o.mActions);
        if (o.mCategories != null) {
            this.mCategories = new ArrayList<>(o.mCategories);
        }
        if (o.mStaticDataTypes != null) {
            this.mStaticDataTypes = new ArrayList<>(o.mStaticDataTypes);
        }
        if (o.mDataTypes != null) {
            this.mDataTypes = new ArrayList<>(o.mDataTypes);
        }
        if (o.mDataSchemes != null) {
            this.mDataSchemes = new ArrayList<>(o.mDataSchemes);
        }
        if (o.mDataSchemeSpecificParts != null) {
            this.mDataSchemeSpecificParts = new ArrayList<>(o.mDataSchemeSpecificParts);
        }
        if (o.mDataAuthorities != null) {
            this.mDataAuthorities = new ArrayList<>(o.mDataAuthorities);
        }
        if (o.mDataPaths != null) {
            this.mDataPaths = new ArrayList<>(o.mDataPaths);
        }
        if (o.mUriRelativeFilterGroups != null) {
            this.mUriRelativeFilterGroups = new ArrayList<>(o.mUriRelativeFilterGroups);
        }
        if (o.mMimeGroups != null) {
            this.mMimeGroups = new ArrayList<>(o.mMimeGroups);
        }
        if (o.mExtras != null) {
            this.mExtras = new PersistableBundle(o.mExtras);
        }
        this.mHasStaticPartialTypes = o.mHasStaticPartialTypes;
        this.mHasDynamicPartialTypes = o.mHasDynamicPartialTypes;
        this.mVerifyState = o.mVerifyState;
        this.mInstantAppVisibility = o.mInstantAppVisibility;
    }

    public String toLongString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IntentFilter {");
        sb.append(" pri=");
        sb.append(this.mPriority);
        if (countActions() > 0) {
            sb.append(" act=");
            sb.append(this.mActions.toString());
        }
        if (countCategories() > 0) {
            sb.append(" cat=");
            sb.append(this.mCategories.toString());
        }
        if (countDataSchemes() > 0) {
            sb.append(" sch=");
            sb.append(this.mDataSchemes.toString());
        }
        if (Flags.relativeReferenceIntentFilters() && countUriRelativeFilterGroups() > 0) {
            sb.append(" grp=");
            sb.append(this.mUriRelativeFilterGroups.toString());
        }
        sb.append(" }");
        return sb.toString();
    }

    public final void setPriority(int priority) {
        this.mPriority = priority;
    }

    public final int getPriority() {
        return this.mPriority;
    }

    @SystemApi
    public final void setOrder(int order) {
        this.mOrder = order;
    }

    @SystemApi
    public final int getOrder() {
        return this.mOrder;
    }

    public final void setAutoVerify(boolean autoVerify) {
        this.mVerifyState &= -2;
        if (autoVerify) {
            this.mVerifyState |= 1;
        }
    }

    public final boolean getAutoVerify() {
        return (this.mVerifyState & 1) == 1;
    }

    public final boolean handleAllWebDataURI() {
        return hasCategory(Intent.CATEGORY_APP_BROWSER) || (handlesWebUris(false) && countDataAuthorities() == 0);
    }

    public final boolean handlesWebUris(boolean onlyWebSchemes) {
        if (!hasAction("android.intent.action.VIEW") || !hasCategory(Intent.CATEGORY_BROWSABLE) || this.mDataSchemes == null || this.mDataSchemes.size() == 0) {
            return false;
        }
        int N = this.mDataSchemes.size();
        for (int i = 0; i < N; i++) {
            String scheme = this.mDataSchemes.get(i);
            boolean isWebScheme = SCHEME_HTTP.equals(scheme) || SCHEME_HTTPS.equals(scheme);
            if (onlyWebSchemes) {
                if (!isWebScheme) {
                    return false;
                }
            } else if (isWebScheme) {
                return true;
            }
        }
        return onlyWebSchemes;
    }

    public final boolean needsVerification() {
        return getAutoVerify() && handlesWebUris(true);
    }

    public final boolean isVerified() {
        return (this.mVerifyState & 256) == 256 && (this.mVerifyState & 16) == 16;
    }

    public void setVerified(boolean verified) {
        this.mVerifyState |= 256;
        this.mVerifyState &= -4097;
        if (verified) {
            this.mVerifyState |= 4096;
        }
    }

    public void setVisibilityToInstantApp(int visibility) {
        this.mInstantAppVisibility = visibility;
    }

    public int getVisibilityToInstantApp() {
        return this.mInstantAppVisibility;
    }

    public boolean isVisibleToInstantApp() {
        return this.mInstantAppVisibility != 0;
    }

    public boolean isExplicitlyVisibleToInstantApp() {
        return this.mInstantAppVisibility == 1;
    }

    public boolean isImplicitlyVisibleToInstantApp() {
        return this.mInstantAppVisibility == 2;
    }

    public final void addAction(String action) {
        this.mActions.add(action.intern());
    }

    public final void removeAction(String action) {
        if (this.mActions.contains(action)) {
            this.mActions.remove(action.intern());
        }
    }

    public final int countActions() {
        return this.mActions.size();
    }

    public final int safeCountActions() {
        if (this.mActions == null) {
            return 0;
        }
        return this.mActions.size();
    }

    public final String getAction(int index) {
        return this.mActions.valueAt(index);
    }

    public final boolean hasAction(String action) {
        return action != null && this.mActions.contains(action);
    }

    public final boolean matchAction(String action) {
        return matchAction(action, false, null);
    }

    private boolean matchAction(String action, boolean wildcardSupported, Collection<String> ignoreActions) {
        if (!wildcardSupported || !"*".equals(action)) {
            if (ignoreActions != null && ignoreActions.contains(action)) {
                return false;
            }
            return hasAction(action);
        }
        if (ignoreActions == null) {
            return !this.mActions.isEmpty();
        }
        if (this.mActions.size() > ignoreActions.size()) {
            return true;
        }
        for (int i = this.mActions.size() - 1; i >= 0; i--) {
            if (!ignoreActions.contains(this.mActions.valueAt(i))) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<String> actionsIterator() {
        if (this.mActions != null) {
            return this.mActions.iterator();
        }
        return null;
    }

    public final void addDataType(String type) throws MalformedMimeTypeException {
        processMimeType(type, new BiConsumer() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                IntentFilter.this.lambda$addDataType$0((String) obj, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDataType$0(String internalType, Boolean isPartial) {
        if (this.mDataTypes == null) {
            this.mDataTypes = new ArrayList<>();
        }
        if (this.mStaticDataTypes == null) {
            this.mStaticDataTypes = new ArrayList<>();
        }
        if (this.mDataTypes.contains(internalType)) {
            return;
        }
        this.mDataTypes.add(internalType.intern());
        this.mStaticDataTypes.add(internalType.intern());
        this.mHasStaticPartialTypes = this.mHasStaticPartialTypes || isPartial.booleanValue();
    }

    public final void addDynamicDataType(String type) throws MalformedMimeTypeException {
        processMimeType(type, new BiConsumer() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                IntentFilter.this.lambda$addDynamicDataType$1((String) obj, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDynamicDataType$1(String internalType, Boolean isPartial) {
        if (this.mDataTypes == null) {
            this.mDataTypes = new ArrayList<>();
        }
        if (!this.mDataTypes.contains(internalType)) {
            this.mDataTypes.add(internalType.intern());
            this.mHasDynamicPartialTypes = this.mHasDynamicPartialTypes || isPartial.booleanValue();
        }
    }

    private void processMimeType(String type, BiConsumer<String, Boolean> action) throws MalformedMimeTypeException {
        int slashpos = type.indexOf(47);
        int typelen = type.length();
        if (slashpos <= 0 || typelen < slashpos + 2) {
            throw new MalformedMimeTypeException(type);
        }
        String internalType = type;
        boolean isPartialType = false;
        if (typelen == slashpos + 2 && type.charAt(slashpos + 1) == '*') {
            internalType = type.substring(0, slashpos);
            isPartialType = true;
        }
        action.accept(internalType, Boolean.valueOf(isPartialType));
    }

    public final void clearDynamicDataTypes() {
        if (this.mDataTypes == null) {
            return;
        }
        if (this.mStaticDataTypes != null) {
            this.mDataTypes.clear();
            this.mDataTypes.addAll(this.mStaticDataTypes);
        } else {
            this.mDataTypes = null;
        }
        this.mHasDynamicPartialTypes = false;
    }

    public int countStaticDataTypes() {
        if (this.mStaticDataTypes != null) {
            return this.mStaticDataTypes.size();
        }
        return 0;
    }

    public final boolean hasDataType(String type) {
        return this.mDataTypes != null && findMimeType(type);
    }

    public final boolean hasExactDataType(String type) {
        return this.mDataTypes != null && this.mDataTypes.contains(type);
    }

    public final boolean hasExactDynamicDataType(String type) {
        return hasExactDataType(type) && !hasExactStaticDataType(type);
    }

    public final boolean hasExactStaticDataType(String type) {
        return this.mStaticDataTypes != null && this.mStaticDataTypes.contains(type);
    }

    public final int countDataTypes() {
        if (this.mDataTypes != null) {
            return this.mDataTypes.size();
        }
        return 0;
    }

    public final String getDataType(int index) {
        return this.mDataTypes.get(index);
    }

    public final Iterator<String> typesIterator() {
        if (this.mDataTypes != null) {
            return this.mDataTypes.iterator();
        }
        return null;
    }

    public final List<String> dataTypes() {
        if (this.mDataTypes != null) {
            return new ArrayList(this.mDataTypes);
        }
        return null;
    }

    public final void addMimeGroup(String name) {
        if (this.mMimeGroups == null) {
            this.mMimeGroups = new ArrayList<>();
        }
        if (!this.mMimeGroups.contains(name)) {
            this.mMimeGroups.add(name);
        }
    }

    public final boolean hasMimeGroup(String name) {
        return this.mMimeGroups != null && this.mMimeGroups.contains(name);
    }

    public final String getMimeGroup(int index) {
        return this.mMimeGroups.get(index);
    }

    public final int countMimeGroups() {
        if (this.mMimeGroups != null) {
            return this.mMimeGroups.size();
        }
        return 0;
    }

    public final Iterator<String> mimeGroupsIterator() {
        if (this.mMimeGroups != null) {
            return this.mMimeGroups.iterator();
        }
        return null;
    }

    public final void addDataScheme(String scheme) {
        if (this.mDataSchemes == null) {
            this.mDataSchemes = new ArrayList<>();
        }
        if (!this.mDataSchemes.contains(scheme)) {
            this.mDataSchemes.add(scheme.intern());
        }
    }

    public final int countDataSchemes() {
        if (this.mDataSchemes != null) {
            return this.mDataSchemes.size();
        }
        return 0;
    }

    public final String getDataScheme(int index) {
        return this.mDataSchemes.get(index);
    }

    public final boolean hasDataScheme(String scheme) {
        return this.mDataSchemes != null && this.mDataSchemes.contains(scheme);
    }

    public final Iterator<String> schemesIterator() {
        if (this.mDataSchemes != null) {
            return this.mDataSchemes.iterator();
        }
        return null;
    }

    public static final class AuthorityEntry {
        private final String mHost;
        private final String mOrigHost;
        private final int mPort;
        private final boolean mWild;

        public AuthorityEntry(String host, String port) {
            this.mOrigHost = host;
            boolean z = false;
            if (host.length() > 0 && host.charAt(0) == '*') {
                z = true;
            }
            this.mWild = z;
            this.mHost = this.mWild ? host.substring(1).intern() : host;
            this.mPort = port != null ? Integer.parseInt(port) : -1;
        }

        AuthorityEntry(Parcel src) {
            this.mOrigHost = src.readString();
            this.mHost = src.readString();
            this.mWild = src.readInt() != 0;
            this.mPort = src.readInt();
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeString(this.mOrigHost);
            parcel.writeString(this.mHost);
            parcel.writeInt(this.mWild ? 1 : 0);
            parcel.writeInt(this.mPort);
        }

        void dumpDebug(ProtoOutputStream proto, long fieldId) {
            long token = proto.start(fieldId);
            proto.write(1138166333441L, this.mHost);
            proto.write(1133871366146L, this.mWild);
            proto.write(1120986464259L, this.mPort);
            proto.end(token);
        }

        public String getHost() {
            return this.mOrigHost;
        }

        public int getPort() {
            return this.mPort;
        }

        public boolean match(AuthorityEntry other) {
            return this.mWild == other.mWild && this.mHost.equals(other.mHost) && this.mPort == other.mPort;
        }

        public boolean equals(Object obj) {
            if (obj instanceof AuthorityEntry) {
                AuthorityEntry other = (AuthorityEntry) obj;
                return match(other);
            }
            return false;
        }

        public int match(Uri data) {
            return match(data, false);
        }

        public int match(Uri data, boolean wildcardSupported) {
            String host = data.getHost();
            if (host == null) {
                if (wildcardSupported && this.mWild && this.mHost.isEmpty()) {
                    return IntentFilter.MATCH_CATEGORY_HOST;
                }
                return -2;
            }
            if (!wildcardSupported || !"*".equals(host)) {
                if (this.mWild) {
                    if (host.length() < this.mHost.length()) {
                        return -2;
                    }
                    host = host.substring(host.length() - this.mHost.length());
                }
                if (host.compareToIgnoreCase(this.mHost) != 0) {
                    return -2;
                }
            }
            if (wildcardSupported || this.mPort < 0) {
                return IntentFilter.MATCH_CATEGORY_HOST;
            }
            return this.mPort != data.getPort() ? -2 : 4194304;
        }
    }

    public final void addDataSchemeSpecificPart(String ssp, int type) {
        addDataSchemeSpecificPart(new PatternMatcher(ssp, type));
    }

    public final void addDataSchemeSpecificPart(PatternMatcher ssp) {
        if (this.mDataSchemeSpecificParts == null) {
            this.mDataSchemeSpecificParts = new ArrayList<>();
        }
        this.mDataSchemeSpecificParts.add(ssp);
    }

    public final int countDataSchemeSpecificParts() {
        if (this.mDataSchemeSpecificParts != null) {
            return this.mDataSchemeSpecificParts.size();
        }
        return 0;
    }

    public final PatternMatcher getDataSchemeSpecificPart(int index) {
        return this.mDataSchemeSpecificParts.get(index);
    }

    public final boolean hasDataSchemeSpecificPart(String data) {
        return hasDataSchemeSpecificPart(data, false);
    }

    private boolean hasDataSchemeSpecificPart(String data, boolean supportWildcards) {
        if (this.mDataSchemeSpecificParts == null) {
            return false;
        }
        if (supportWildcards && "*".equals(data) && this.mDataSchemeSpecificParts.size() > 0) {
            return true;
        }
        int numDataSchemeSpecificParts = this.mDataSchemeSpecificParts.size();
        for (int i = 0; i < numDataSchemeSpecificParts; i++) {
            PatternMatcher pe = this.mDataSchemeSpecificParts.get(i);
            if (pe.match(data)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDataSchemeSpecificPart(PatternMatcher ssp) {
        if (this.mDataSchemeSpecificParts == null) {
            return false;
        }
        int numDataSchemeSpecificParts = this.mDataSchemeSpecificParts.size();
        for (int i = 0; i < numDataSchemeSpecificParts; i++) {
            PatternMatcher pe = this.mDataSchemeSpecificParts.get(i);
            if (pe.getType() == ssp.getType() && pe.getPath().equals(ssp.getPath())) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<PatternMatcher> schemeSpecificPartsIterator() {
        if (this.mDataSchemeSpecificParts != null) {
            return this.mDataSchemeSpecificParts.iterator();
        }
        return null;
    }

    public final void addDataAuthority(String host, String port) {
        if (port != null) {
            port = port.intern();
        }
        addDataAuthority(new AuthorityEntry(host.intern(), port));
    }

    public final void addDataAuthority(AuthorityEntry ent) {
        if (this.mDataAuthorities == null) {
            this.mDataAuthorities = new ArrayList<>();
        }
        this.mDataAuthorities.add(ent);
    }

    public final int countDataAuthorities() {
        if (this.mDataAuthorities != null) {
            return this.mDataAuthorities.size();
        }
        return 0;
    }

    public final AuthorityEntry getDataAuthority(int index) {
        return this.mDataAuthorities.get(index);
    }

    public final boolean hasDataAuthority(Uri data) {
        return matchDataAuthority(data) >= 0;
    }

    public final boolean hasDataAuthority(AuthorityEntry auth) {
        if (this.mDataAuthorities == null) {
            return false;
        }
        int numDataAuthorities = this.mDataAuthorities.size();
        for (int i = 0; i < numDataAuthorities; i++) {
            if (this.mDataAuthorities.get(i).match(auth)) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<AuthorityEntry> authoritiesIterator() {
        if (this.mDataAuthorities != null) {
            return this.mDataAuthorities.iterator();
        }
        return null;
    }

    public final void addDataPath(String path, int type) {
        addDataPath(new PatternMatcher(path.intern(), type));
    }

    public final void addDataPath(PatternMatcher path) {
        if (this.mDataPaths == null) {
            this.mDataPaths = new ArrayList<>();
        }
        this.mDataPaths.add(path);
    }

    public final int countDataPaths() {
        if (this.mDataPaths != null) {
            return this.mDataPaths.size();
        }
        return 0;
    }

    public final PatternMatcher getDataPath(int index) {
        return this.mDataPaths.get(index);
    }

    public final boolean hasDataPath(String data) {
        return hasDataPath(data, false);
    }

    private boolean hasDataPath(String data, boolean wildcardSupported) {
        if (this.mDataPaths == null) {
            return false;
        }
        if (wildcardSupported && WILDCARD_PATH.equals(data)) {
            return true;
        }
        int numDataPaths = this.mDataPaths.size();
        for (int i = 0; i < numDataPaths; i++) {
            PatternMatcher pe = this.mDataPaths.get(i);
            if (pe.match(data)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDataPath(PatternMatcher path) {
        if (this.mDataPaths == null) {
            return false;
        }
        int numDataPaths = this.mDataPaths.size();
        for (int i = 0; i < numDataPaths; i++) {
            PatternMatcher pe = this.mDataPaths.get(i);
            if (pe.getType() == path.getType() && pe.getPath().equals(path.getPath())) {
                return true;
            }
        }
        return false;
    }

    public final Iterator<PatternMatcher> pathsIterator() {
        if (this.mDataPaths != null) {
            return this.mDataPaths.iterator();
        }
        return null;
    }

    public final void addUriRelativeFilterGroup(UriRelativeFilterGroup group) {
        Objects.requireNonNull(group);
        if (this.mUriRelativeFilterGroups == null) {
            this.mUriRelativeFilterGroups = new ArrayList<>();
        }
        this.mUriRelativeFilterGroups.add(group);
    }

    public final int countUriRelativeFilterGroups() {
        if (this.mUriRelativeFilterGroups == null) {
            return 0;
        }
        return this.mUriRelativeFilterGroups.size();
    }

    public final UriRelativeFilterGroup getUriRelativeFilterGroup(int index) {
        return this.mUriRelativeFilterGroups.get(index);
    }

    public final void clearUriRelativeFilterGroups() {
        this.mUriRelativeFilterGroups = null;
    }

    public final int matchDataAuthority(Uri data) {
        return matchDataAuthority(data, false);
    }

    public final int matchDataAuthority(Uri data, boolean wildcardSupported) {
        if (data == null || this.mDataAuthorities == null) {
            return -2;
        }
        int numDataAuthorities = this.mDataAuthorities.size();
        for (int i = 0; i < numDataAuthorities; i++) {
            AuthorityEntry ae = this.mDataAuthorities.get(i);
            int match = ae.match(data, wildcardSupported);
            if (match >= 0) {
                return match;
            }
        }
        return -2;
    }

    public final int matchData(String type, String scheme, Uri data) {
        return matchData(type, scheme, data, false);
    }

    private int matchData(String type, String scheme, Uri data, boolean wildcardSupported) {
        int i;
        boolean wildcardWithMimegroups = wildcardSupported && countMimeGroups() != 0;
        List<String> types = this.mDataTypes;
        ArrayList<String> schemes = this.mDataSchemes;
        int match = 1048576;
        if (!wildcardWithMimegroups && types == null && schemes == null) {
            if (type != null || data != null) {
                return -2;
            }
            return 1081344;
        }
        if (schemes != null) {
            if (!schemes.contains(scheme != null ? scheme : "") && (!wildcardSupported || !"*".equals(scheme))) {
                return -2;
            }
            match = 2097152;
            ArrayList<PatternMatcher> schemeSpecificParts = this.mDataSchemeSpecificParts;
            if (schemeSpecificParts != null && data != null) {
                if (!hasDataSchemeSpecificPart(data.getSchemeSpecificPart(), wildcardSupported)) {
                    i = -2;
                } else {
                    i = 5767168;
                }
                match = i;
            }
            if (match != 5767168) {
                ArrayList<AuthorityEntry> authorities = this.mDataAuthorities;
                if (authorities != null) {
                    int authMatch = matchDataAuthority(data, wildcardSupported);
                    if (authMatch < 0) {
                        return -2;
                    }
                    ArrayList<PatternMatcher> paths = this.mDataPaths;
                    ArrayList<UriRelativeFilterGroup> groups = this.mUriRelativeFilterGroups;
                    if (Flags.relativeReferenceIntentFilters()) {
                        if (paths == null && groups == null) {
                            match = authMatch;
                        } else {
                            if (!hasDataPath(data.getPath(), wildcardSupported) && !matchRelRefGroups(data)) {
                                return -2;
                            }
                            match = 5242880;
                        }
                    } else if (paths == null) {
                        match = authMatch;
                    } else {
                        if (!hasDataPath(data.getPath(), wildcardSupported)) {
                            return -2;
                        }
                        match = 5242880;
                    }
                }
            }
            if (match == -2) {
                return -2;
            }
        } else if (scheme != null && !"".equals(scheme) && !"content".equals(scheme) && !"file".equals(scheme) && (!wildcardSupported || !"*".equals(scheme))) {
            return -2;
        }
        if (wildcardWithMimegroups) {
            return 6291456;
        }
        if (types != null) {
            if (!findMimeType(type)) {
                return -1;
            }
            match = 6291456;
        } else if (type != null) {
            return -1;
        }
        return 32768 + match;
    }

    private boolean matchRelRefGroups(Uri data) {
        if (this.mUriRelativeFilterGroups == null) {
            return false;
        }
        return UriRelativeFilterGroup.matchGroupsToUri(this.mUriRelativeFilterGroups, data);
    }

    public final void addCategory(String category) {
        if (this.mCategories == null) {
            this.mCategories = new ArrayList<>();
        }
        if (!this.mCategories.contains(category)) {
            this.mCategories.add(category.intern());
        }
    }

    public final int countCategories() {
        if (this.mCategories != null) {
            return this.mCategories.size();
        }
        return 0;
    }

    public final String getCategory(int index) {
        return this.mCategories.get(index);
    }

    public final boolean hasCategory(String category) {
        return this.mCategories != null && this.mCategories.contains(category);
    }

    public final Iterator<String> categoriesIterator() {
        if (this.mCategories != null) {
            return this.mCategories.iterator();
        }
        return null;
    }

    public final String matchCategories(Set<String> categories) {
        if (categories == null) {
            return null;
        }
        Iterator<String> it = categories.iterator();
        if (this.mCategories == null) {
            if (it.hasNext()) {
                return it.next();
            }
            return null;
        }
        while (it.hasNext()) {
            String category = it.next();
            if (!this.mCategories.contains(category)) {
                return category;
            }
        }
        return null;
    }

    private String matchExtras(Bundle extras) {
        if (this.mExtras == null) {
            return null;
        }
        Set<String> keys = this.mExtras.keySet();
        for (String key : keys) {
            if (extras == null) {
                return key;
            }
            Object value = this.mExtras.get(key);
            Object otherValue = extras.get(key);
            if (otherValue == null || value.getClass() != otherValue.getClass() || !Objects.deepEquals(value, otherValue)) {
                return key;
            }
        }
        return null;
    }

    public final void addExtra(String name, int value) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putInt(name, value);
    }

    public final int getIntExtra(String name) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            return 0;
        }
        return this.mExtras.getInt(name);
    }

    public final void addExtra(String name, int[] value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putIntArray(name, value);
    }

    public final int[] getIntArrayExtra(String name) {
        Objects.requireNonNull(name);
        return this.mExtras == null ? EMPTY_INT_ARRAY : this.mExtras.getIntArray(name);
    }

    public final void addExtra(String name, long value) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putLong(name, value);
    }

    public final long getLongExtra(String name) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            return 0L;
        }
        return this.mExtras.getLong(name);
    }

    public final void addExtra(String name, long[] value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putLongArray(name, value);
    }

    public final long[] getLongArrayExtra(String name) {
        Objects.requireNonNull(name);
        return this.mExtras == null ? EMPTY_LONG_ARRAY : this.mExtras.getLongArray(name);
    }

    public final void addExtra(String name, double value) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putDouble(name, value);
    }

    public final double getDoubleExtra(String name) {
        Objects.requireNonNull(name);
        return this.mExtras == null ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : this.mExtras.getDouble(name);
    }

    public final void addExtra(String name, double[] value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putDoubleArray(name, value);
    }

    public final double[] getDoubleArrayExtra(String name) {
        Objects.requireNonNull(name);
        return this.mExtras == null ? EMPTY_DOUBLE_ARRAY : this.mExtras.getDoubleArray(name);
    }

    public final void addExtra(String name, String value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putString(name, value);
    }

    public final String getStringExtra(String name) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getString(name);
    }

    public final void addExtra(String name, String[] value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putStringArray(name, value);
    }

    public final String[] getStringArrayExtra(String name) {
        Objects.requireNonNull(name);
        return this.mExtras == null ? EMPTY_STRING_ARRAY : this.mExtras.getStringArray(name);
    }

    public final void addExtra(String name, boolean value) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putBoolean(name, value);
    }

    public final boolean getBooleanExtra(String name) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            return false;
        }
        return this.mExtras.getBoolean(name);
    }

    public final void addExtra(String name, boolean[] value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putBooleanArray(name, value);
    }

    public final boolean[] getBooleanArrayExtra(String name) {
        Objects.requireNonNull(name);
        return this.mExtras == null ? EMPTY_BOOLEAN_ARRAY : this.mExtras.getBooleanArray(name);
    }

    public final boolean hasExtra(String name) {
        Objects.requireNonNull(name);
        if (this.mExtras == null) {
            return false;
        }
        return this.mExtras.containsKey(name);
    }

    public final void setExtras(PersistableBundle extras) {
        this.mExtras = extras;
    }

    public final PersistableBundle getExtras() {
        return this.mExtras == null ? new PersistableBundle() : this.mExtras;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$asPredicate$2(Intent i) {
        return match(null, i, false, TAG) >= 0;
    }

    public Predicate<Intent> asPredicate() {
        return new Predicate() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$asPredicate$2;
                lambda$asPredicate$2 = IntentFilter.this.lambda$asPredicate$2((Intent) obj);
                return lambda$asPredicate$2;
            }
        };
    }

    public Predicate<Intent> asPredicateWithTypeResolution(final ContentResolver resolver) {
        Objects.requireNonNull(resolver);
        return new Predicate() { // from class: android.content.IntentFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$asPredicateWithTypeResolution$3;
                lambda$asPredicateWithTypeResolution$3 = IntentFilter.this.lambda$asPredicateWithTypeResolution$3(resolver, (Intent) obj);
                return lambda$asPredicateWithTypeResolution$3;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$asPredicateWithTypeResolution$3(ContentResolver resolver, Intent i) {
        return match(resolver, i, true, TAG) >= 0;
    }

    public final int match(ContentResolver resolver, Intent intent, boolean resolve, String logTag) {
        String type = resolve ? intent.resolveType(resolver) : intent.getType();
        return match(intent.getAction(), type, intent.getScheme(), intent.getData(), intent.getCategories(), logTag, false, null, intent.getExtras());
    }

    public final int match(String action, String type, String scheme, Uri data, Set<String> categories, String logTag) {
        return match(action, type, scheme, data, categories, logTag, false, null);
    }

    public final int match(String action, String type, String scheme, Uri data, Set<String> categories, String logTag, boolean supportWildcards, Collection<String> ignoreActions) {
        return match(action, type, scheme, data, categories, logTag, supportWildcards, ignoreActions, null);
    }

    public final int match(String action, String type, String scheme, Uri data, Set<String> categories, String logTag, boolean supportWildcards, Collection<String> ignoreActions, Bundle extras) {
        if (action != null && !matchAction(action, supportWildcards, ignoreActions)) {
            return -3;
        }
        int dataMatch = matchData(type, scheme, data, supportWildcards);
        if (dataMatch < 0) {
            return dataMatch;
        }
        String categoryMismatch = matchCategories(categories);
        if (categoryMismatch != null) {
            return -4;
        }
        String extraMismatch = matchExtras(extras);
        if (extraMismatch != null) {
            return -5;
        }
        return dataMatch;
    }

    public void writeToXml(XmlSerializer serializer) throws IOException {
        if (getAutoVerify()) {
            serializer.attribute(null, AUTO_VERIFY_STR, Boolean.toString(true));
        }
        int N = countActions();
        for (int i = 0; i < N; i++) {
            serializer.startTag(null, "action");
            serializer.attribute(null, "name", this.mActions.valueAt(i));
            serializer.endTag(null, "action");
        }
        int N2 = countCategories();
        for (int i2 = 0; i2 < N2; i2++) {
            serializer.startTag(null, CAT_STR);
            serializer.attribute(null, "name", this.mCategories.get(i2));
            serializer.endTag(null, CAT_STR);
        }
        writeDataTypesToXml(serializer);
        int N3 = countMimeGroups();
        for (int i3 = 0; i3 < N3; i3++) {
            serializer.startTag(null, GROUP_STR);
            serializer.attribute(null, "name", this.mMimeGroups.get(i3));
            serializer.endTag(null, GROUP_STR);
        }
        int N4 = countDataSchemes();
        for (int i4 = 0; i4 < N4; i4++) {
            serializer.startTag(null, SCHEME_STR);
            serializer.attribute(null, "name", this.mDataSchemes.get(i4));
            serializer.endTag(null, SCHEME_STR);
        }
        int N5 = countDataSchemeSpecificParts();
        for (int i5 = 0; i5 < N5; i5++) {
            serializer.startTag(null, SSP_STR);
            PatternMatcher pe = this.mDataSchemeSpecificParts.get(i5);
            switch (pe.getType()) {
                case 0:
                    serializer.attribute(null, LITERAL_STR, pe.getPath());
                    break;
                case 1:
                    serializer.attribute(null, PREFIX_STR, pe.getPath());
                    break;
                case 2:
                    serializer.attribute(null, SGLOB_STR, pe.getPath());
                    break;
                case 3:
                    serializer.attribute(null, AGLOB_STR, pe.getPath());
                    break;
                case 4:
                    serializer.attribute(null, SUFFIX_STR, pe.getPath());
                    break;
            }
            serializer.endTag(null, SSP_STR);
        }
        int N6 = countDataAuthorities();
        for (int i6 = 0; i6 < N6; i6++) {
            serializer.startTag(null, "auth");
            AuthorityEntry ae = this.mDataAuthorities.get(i6);
            serializer.attribute(null, HOST_STR, ae.getHost());
            if (ae.getPort() >= 0) {
                serializer.attribute(null, "port", Integer.toString(ae.getPort()));
            }
            serializer.endTag(null, "auth");
        }
        int N7 = countDataPaths();
        for (int i7 = 0; i7 < N7; i7++) {
            serializer.startTag(null, "path");
            PatternMatcher pe2 = this.mDataPaths.get(i7);
            switch (pe2.getType()) {
                case 0:
                    serializer.attribute(null, LITERAL_STR, pe2.getPath());
                    break;
                case 1:
                    serializer.attribute(null, PREFIX_STR, pe2.getPath());
                    break;
                case 2:
                    serializer.attribute(null, SGLOB_STR, pe2.getPath());
                    break;
                case 3:
                    serializer.attribute(null, AGLOB_STR, pe2.getPath());
                    break;
                case 4:
                    serializer.attribute(null, SUFFIX_STR, pe2.getPath());
                    break;
            }
            serializer.endTag(null, "path");
        }
        if (this.mExtras != null) {
            serializer.startTag(null, "extras");
            try {
                this.mExtras.saveToXml(serializer);
                serializer.endTag(null, "extras");
            } catch (XmlPullParserException e) {
                throw new IllegalStateException("Failed to write extras: " + this.mExtras.toString(), e);
            }
        }
        if (Flags.relativeReferenceIntentFilters()) {
            int N8 = countUriRelativeFilterGroups();
            for (int i8 = 0; i8 < N8; i8++) {
                this.mUriRelativeFilterGroups.get(i8).writeToXml(serializer);
            }
        }
    }

    private void writeDataTypesToXml(XmlSerializer serializer) throws IOException {
        if (this.mStaticDataTypes == null) {
            return;
        }
        int i = 0;
        Iterator<String> it = this.mStaticDataTypes.iterator();
        while (it.hasNext()) {
            String staticType = it.next();
            while (!this.mDataTypes.get(i).equals(staticType)) {
                writeDataTypeToXml(serializer, this.mDataTypes.get(i), "type");
                i++;
            }
            writeDataTypeToXml(serializer, staticType, STATIC_TYPE_STR);
            i++;
        }
        while (i < this.mDataTypes.size()) {
            writeDataTypeToXml(serializer, this.mDataTypes.get(i), "type");
            i++;
        }
    }

    private void writeDataTypeToXml(XmlSerializer serializer, String type, String tag) throws IOException {
        serializer.startTag(null, tag);
        if (type.indexOf(47) < 0) {
            type = type + WILDCARD_PATH;
        }
        serializer.attribute(null, "name", type);
        serializer.endTag(null, tag);
    }

    public void readFromXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        String autoVerify = parser.getAttributeValue(null, AUTO_VERIFY_STR);
        setAutoVerify(TextUtils.isEmpty(autoVerify) ? false : Boolean.getBoolean(autoVerify));
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != 1) {
                if (type != 3 || parser.getDepth() > outerDepth) {
                    if (type != 3 && type != 4) {
                        String tagName = parser.getName();
                        if (tagName.equals("action")) {
                            String name = parser.getAttributeValue(null, "name");
                            if (name != null) {
                                addAction(name);
                            }
                        } else if (tagName.equals(CAT_STR)) {
                            String name2 = parser.getAttributeValue(null, "name");
                            if (name2 != null) {
                                addCategory(name2);
                            }
                        } else if (tagName.equals(STATIC_TYPE_STR)) {
                            String name3 = parser.getAttributeValue(null, "name");
                            if (name3 != null) {
                                try {
                                    addDataType(name3);
                                } catch (MalformedMimeTypeException e) {
                                }
                            }
                        } else if (tagName.equals("type")) {
                            String name4 = parser.getAttributeValue(null, "name");
                            if (name4 != null) {
                                try {
                                    addDynamicDataType(name4);
                                } catch (MalformedMimeTypeException e2) {
                                }
                            }
                        } else if (tagName.equals(GROUP_STR)) {
                            String name5 = parser.getAttributeValue(null, "name");
                            if (name5 != null) {
                                addMimeGroup(name5);
                            }
                        } else if (tagName.equals(SCHEME_STR)) {
                            String name6 = parser.getAttributeValue(null, "name");
                            if (name6 != null) {
                                addDataScheme(name6);
                            }
                        } else if (tagName.equals(SSP_STR)) {
                            String ssp = parser.getAttributeValue(null, LITERAL_STR);
                            if (ssp != null) {
                                addDataSchemeSpecificPart(ssp, 0);
                            } else {
                                String ssp2 = parser.getAttributeValue(null, PREFIX_STR);
                                if (ssp2 != null) {
                                    addDataSchemeSpecificPart(ssp2, 1);
                                } else {
                                    String ssp3 = parser.getAttributeValue(null, SGLOB_STR);
                                    if (ssp3 != null) {
                                        addDataSchemeSpecificPart(ssp3, 2);
                                    } else {
                                        String ssp4 = parser.getAttributeValue(null, AGLOB_STR);
                                        if (ssp4 != null) {
                                            addDataSchemeSpecificPart(ssp4, 3);
                                        } else {
                                            String ssp5 = parser.getAttributeValue(null, SUFFIX_STR);
                                            if (ssp5 != null) {
                                                addDataSchemeSpecificPart(ssp5, 4);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (tagName.equals("auth")) {
                            String host = parser.getAttributeValue(null, HOST_STR);
                            String port = parser.getAttributeValue(null, "port");
                            if (host != null) {
                                addDataAuthority(host, port);
                            }
                        } else if (tagName.equals("path")) {
                            String path = parser.getAttributeValue(null, LITERAL_STR);
                            if (path != null) {
                                addDataPath(path, 0);
                            } else {
                                String path2 = parser.getAttributeValue(null, PREFIX_STR);
                                if (path2 != null) {
                                    addDataPath(path2, 1);
                                } else {
                                    String path3 = parser.getAttributeValue(null, SGLOB_STR);
                                    if (path3 != null) {
                                        addDataPath(path3, 2);
                                    } else {
                                        String path4 = parser.getAttributeValue(null, AGLOB_STR);
                                        if (path4 != null) {
                                            addDataPath(path4, 3);
                                        } else {
                                            String path5 = parser.getAttributeValue(null, SUFFIX_STR);
                                            if (path5 != null) {
                                                addDataPath(path5, 4);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (tagName.equals("extras")) {
                            this.mExtras = PersistableBundle.restoreFromXml(parser);
                        } else if (Flags.relativeReferenceIntentFilters() && URI_RELATIVE_FILTER_GROUP_STR.equals(tagName)) {
                            addUriRelativeFilterGroup(new UriRelativeFilterGroup(parser));
                        } else {
                            Log.w(TAG, "Unknown tag parsing IntentFilter: " + tagName);
                        }
                        XmlUtils.skipCurrentTag(parser);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        if (this.mActions.size() > 0) {
            Iterator<String> it = this.mActions.iterator();
            while (it.hasNext()) {
                proto.write(2237677961217L, it.next());
            }
        }
        if (this.mCategories != null) {
            Iterator<String> it2 = this.mCategories.iterator();
            while (it2.hasNext()) {
                proto.write(2237677961218L, it2.next());
            }
        }
        if (this.mDataSchemes != null) {
            Iterator<String> it3 = this.mDataSchemes.iterator();
            while (it3.hasNext()) {
                proto.write(2237677961219L, it3.next());
            }
        }
        if (this.mDataSchemeSpecificParts != null) {
            Iterator<PatternMatcher> it4 = this.mDataSchemeSpecificParts.iterator();
            while (it4.hasNext()) {
                it4.next().dumpDebug(proto, 2246267895812L);
            }
        }
        if (this.mDataAuthorities != null) {
            Iterator<AuthorityEntry> it5 = this.mDataAuthorities.iterator();
            while (it5.hasNext()) {
                it5.next().dumpDebug(proto, 2246267895813L);
            }
        }
        if (this.mDataPaths != null) {
            Iterator<PatternMatcher> it6 = this.mDataPaths.iterator();
            while (it6.hasNext()) {
                it6.next().dumpDebug(proto, 2246267895814L);
            }
        }
        if (this.mDataTypes != null) {
            Iterator<String> it7 = this.mDataTypes.iterator();
            while (it7.hasNext()) {
                proto.write(2237677961223L, it7.next());
            }
        }
        if (this.mMimeGroups != null) {
            Iterator<String> it8 = this.mMimeGroups.iterator();
            while (it8.hasNext()) {
                proto.write(2237677961227L, it8.next());
            }
        }
        if (this.mPriority != 0 || hasPartialTypes()) {
            proto.write(1120986464264L, this.mPriority);
            proto.write(1133871366153L, hasPartialTypes());
        }
        proto.write(1133871366154L, getAutoVerify());
        if (this.mExtras != null) {
            this.mExtras.dumpDebug(proto, 1146756268044L);
        }
        if (Flags.relativeReferenceIntentFilters() && this.mUriRelativeFilterGroups != null) {
            Iterator<UriRelativeFilterGroup> it9 = this.mUriRelativeFilterGroups.iterator();
            while (it9.hasNext()) {
                it9.next().dumpDebug(proto, 2246267895821L);
            }
        }
        proto.end(token);
    }

    public void dump(Printer du, String prefix) {
        StringBuilder sb = new StringBuilder(256);
        if (this.mActions.size() > 0) {
            Iterator<String> it = this.mActions.iterator();
            while (it.hasNext()) {
                sb.setLength(0);
                sb.append(prefix);
                sb.append("Action: \"");
                sb.append(it.next());
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mCategories != null) {
            Iterator<String> it2 = this.mCategories.iterator();
            while (it2.hasNext()) {
                sb.setLength(0);
                sb.append(prefix);
                sb.append("Category: \"");
                sb.append(it2.next());
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mDataSchemes != null) {
            Iterator<String> it3 = this.mDataSchemes.iterator();
            while (it3.hasNext()) {
                sb.setLength(0);
                sb.append(prefix);
                sb.append("Scheme: \"");
                sb.append(it3.next());
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mDataSchemeSpecificParts != null) {
            Iterator<PatternMatcher> it4 = this.mDataSchemeSpecificParts.iterator();
            while (it4.hasNext()) {
                PatternMatcher pe = it4.next();
                sb.setLength(0);
                sb.append(prefix);
                sb.append("Ssp: \"");
                sb.append(pe);
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mDataAuthorities != null) {
            Iterator<AuthorityEntry> it5 = this.mDataAuthorities.iterator();
            while (it5.hasNext()) {
                AuthorityEntry ae = it5.next();
                sb.setLength(0);
                sb.append(prefix);
                sb.append("Authority: \"");
                sb.append(ae.mHost);
                sb.append("\": ");
                sb.append(ae.mPort);
                if (ae.mWild) {
                    sb.append(" WILD");
                }
                du.println(sb.toString());
            }
        }
        if (this.mDataPaths != null) {
            Iterator<PatternMatcher> it6 = this.mDataPaths.iterator();
            while (it6.hasNext()) {
                PatternMatcher pe2 = it6.next();
                sb.setLength(0);
                sb.append(prefix);
                sb.append("Path: \"");
                sb.append(pe2);
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mUriRelativeFilterGroups != null) {
            Iterator<UriRelativeFilterGroup> it7 = this.mUriRelativeFilterGroups.iterator();
            while (it7.hasNext()) {
                sb.setLength(0);
                sb.append(prefix);
                sb.append("UriRelativeFilterGroup: \"");
                sb.append(it7.next());
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mStaticDataTypes != null) {
            Iterator<String> it8 = this.mStaticDataTypes.iterator();
            while (it8.hasNext()) {
                sb.setLength(0);
                sb.append(prefix);
                sb.append("StaticType: \"");
                sb.append(it8.next());
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mDataTypes != null) {
            Iterator<String> it9 = this.mDataTypes.iterator();
            while (it9.hasNext()) {
                String dataType = it9.next();
                if (!hasExactStaticDataType(dataType)) {
                    sb.setLength(0);
                    sb.append(prefix);
                    sb.append("Type: \"");
                    sb.append(dataType);
                    sb.append("\"");
                    du.println(sb.toString());
                }
            }
        }
        if (this.mMimeGroups != null) {
            Iterator<String> it10 = this.mMimeGroups.iterator();
            while (it10.hasNext()) {
                sb.setLength(0);
                sb.append(prefix);
                sb.append("MimeGroup: \"");
                sb.append(it10.next());
                sb.append("\"");
                du.println(sb.toString());
            }
        }
        if (this.mPriority != 0 || this.mOrder != 0 || hasPartialTypes()) {
            sb.setLength(0);
            sb.append(prefix);
            sb.append("mPriority=");
            sb.append(this.mPriority);
            sb.append(", mOrder=");
            sb.append(this.mOrder);
            sb.append(", mHasStaticPartialTypes=");
            sb.append(this.mHasStaticPartialTypes);
            sb.append(", mHasDynamicPartialTypes=");
            sb.append(this.mHasDynamicPartialTypes);
            du.println(sb.toString());
        }
        if (getAutoVerify()) {
            sb.setLength(0);
            sb.append(prefix);
            sb.append("AutoVerify=");
            sb.append(getAutoVerify());
            du.println(sb.toString());
        }
        if (this.mExtras != null) {
            sb.setLength(0);
            sb.append(prefix);
            sb.append("mExtras=");
            sb.append(this.mExtras.toString());
            du.println(sb.toString());
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray((String[]) this.mActions.toArray(new String[this.mActions.size()]));
        if (this.mCategories != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mCategories);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataSchemes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataSchemes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mStaticDataTypes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mStaticDataTypes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataTypes != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDataTypes);
        } else {
            parcel.writeInt(0);
        }
        if (this.mMimeGroups != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mMimeGroups);
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataSchemeSpecificParts != null) {
            int size = this.mDataSchemeSpecificParts.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.mDataSchemeSpecificParts.get(i2).writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataAuthorities != null) {
            int size2 = this.mDataAuthorities.size();
            parcel.writeInt(size2);
            for (int i3 = 0; i3 < size2; i3++) {
                this.mDataAuthorities.get(i3).writeToParcel(parcel);
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.mDataPaths != null) {
            int size3 = this.mDataPaths.size();
            parcel.writeInt(size3);
            for (int i4 = 0; i4 < size3; i4++) {
                this.mDataPaths.get(i4).writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mPriority);
        parcel.writeInt(this.mHasStaticPartialTypes ? 1 : 0);
        parcel.writeInt(this.mHasDynamicPartialTypes ? 1 : 0);
        parcel.writeInt(getAutoVerify() ? 1 : 0);
        parcel.writeInt(this.mInstantAppVisibility);
        parcel.writeInt(this.mOrder);
        if (this.mExtras != null) {
            parcel.writeInt(1);
            this.mExtras.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (Flags.relativeReferenceIntentFilters() && this.mUriRelativeFilterGroups != null) {
            int size4 = this.mUriRelativeFilterGroups.size();
            parcel.writeInt(size4);
            for (int i5 = 0; i5 < size4; i5++) {
                this.mUriRelativeFilterGroups.get(i5).writeToParcel(parcel, i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public boolean debugCheck() {
        return true;
    }

    public boolean checkDataPathAndSchemeSpecificParts() {
        int numDataPath = this.mDataPaths == null ? 0 : this.mDataPaths.size();
        int numDataSchemeSpecificParts = this.mDataSchemeSpecificParts == null ? 0 : this.mDataSchemeSpecificParts.size();
        for (int i = 0; i < numDataPath; i++) {
            if (!this.mDataPaths.get(i).check()) {
                return false;
            }
        }
        for (int i2 = 0; i2 < numDataSchemeSpecificParts; i2++) {
            if (!this.mDataSchemeSpecificParts.get(i2).check()) {
                return false;
            }
        }
        return true;
    }

    public IntentFilter(Parcel source) {
        this.mCategories = null;
        this.mDataSchemes = null;
        this.mDataSchemeSpecificParts = null;
        this.mDataAuthorities = null;
        this.mDataPaths = null;
        this.mUriRelativeFilterGroups = null;
        this.mStaticDataTypes = null;
        this.mDataTypes = null;
        this.mMimeGroups = null;
        this.mHasStaticPartialTypes = false;
        this.mHasDynamicPartialTypes = false;
        this.mExtras = null;
        List<String> actions = new ArrayList<>();
        source.readStringList(actions);
        this.mActions = new ArraySet<>(actions);
        if (source.readInt() != 0) {
            this.mCategories = new ArrayList<>();
            source.readStringList(this.mCategories);
        }
        if (source.readInt() != 0) {
            this.mDataSchemes = new ArrayList<>();
            source.readStringList(this.mDataSchemes);
        }
        if (source.readInt() != 0) {
            this.mStaticDataTypes = new ArrayList<>();
            source.readStringList(this.mStaticDataTypes);
        }
        if (source.readInt() != 0) {
            this.mDataTypes = new ArrayList<>();
            source.readStringList(this.mDataTypes);
        }
        if (source.readInt() != 0) {
            this.mMimeGroups = new ArrayList<>();
            source.readStringList(this.mMimeGroups);
        }
        int N = source.readInt();
        if (N > 0) {
            this.mDataSchemeSpecificParts = new ArrayList<>(N);
            for (int i = 0; i < N; i++) {
                this.mDataSchemeSpecificParts.add(new PatternMatcher(source));
            }
        }
        int N2 = source.readInt();
        if (N2 > 0) {
            this.mDataAuthorities = new ArrayList<>(N2);
            for (int i2 = 0; i2 < N2; i2++) {
                this.mDataAuthorities.add(new AuthorityEntry(source));
            }
        }
        int N3 = source.readInt();
        if (N3 > 0) {
            this.mDataPaths = new ArrayList<>(N3);
            for (int i3 = 0; i3 < N3; i3++) {
                this.mDataPaths.add(new PatternMatcher(source));
            }
        }
        int i4 = source.readInt();
        this.mPriority = i4;
        this.mHasStaticPartialTypes = source.readInt() > 0;
        this.mHasDynamicPartialTypes = source.readInt() > 0;
        setAutoVerify(source.readInt() > 0);
        setVisibilityToInstantApp(source.readInt());
        this.mOrder = source.readInt();
        if (source.readInt() != 0) {
            this.mExtras = PersistableBundle.CREATOR.createFromParcel(source);
        }
        int N4 = source.readInt();
        if (Flags.relativeReferenceIntentFilters() && N4 > 0) {
            this.mUriRelativeFilterGroups = new ArrayList<>(N4);
            for (int i5 = 0; i5 < N4; i5++) {
                this.mUriRelativeFilterGroups.add(new UriRelativeFilterGroup(source));
            }
        }
    }

    private boolean hasPartialTypes() {
        return this.mHasStaticPartialTypes || this.mHasDynamicPartialTypes;
    }

    private final boolean findMimeType(String type) {
        ArrayList<String> t = this.mDataTypes;
        if (type == null) {
            return false;
        }
        if (t.contains(type)) {
            return true;
        }
        int typeLength = type.length();
        if (typeLength == 3 && type.equals("*/*")) {
            return !t.isEmpty();
        }
        if (hasPartialTypes() && t.contains("*")) {
            return true;
        }
        int slashpos = type.indexOf(47);
        if (slashpos > 0) {
            if (hasPartialTypes() && t.contains(type.substring(0, slashpos))) {
                return true;
            }
            if (typeLength == slashpos + 2 && type.charAt(slashpos + 1) == '*') {
                int numTypes = t.size();
                for (int i = 0; i < numTypes; i++) {
                    String v = t.get(i);
                    if (type.regionMatches(0, v, 0, slashpos + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<String> getHostsList() {
        ArrayList<String> result = new ArrayList<>();
        Iterator<AuthorityEntry> it = authoritiesIterator();
        if (it != null) {
            while (it.hasNext()) {
                AuthorityEntry entry = it.next();
                result.add(entry.getHost());
            }
        }
        return result;
    }

    public String[] getHosts() {
        ArrayList<String> list = getHostsList();
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static boolean filterEquals(IntentFilter f1, IntentFilter f2) {
        int s1 = f1.countActions();
        int s2 = f2.countActions();
        if (s1 != s2) {
            return false;
        }
        for (int i = 0; i < s1; i++) {
            if (!f2.hasAction(f1.getAction(i))) {
                return false;
            }
        }
        int s12 = f1.countCategories();
        int s22 = f2.countCategories();
        if (s12 != s22) {
            return false;
        }
        for (int i2 = 0; i2 < s12; i2++) {
            if (!f2.hasCategory(f1.getCategory(i2))) {
                return false;
            }
        }
        int s13 = f1.countDataTypes();
        int s23 = f2.countDataTypes();
        if (s13 != s23) {
            return false;
        }
        for (int i3 = 0; i3 < s13; i3++) {
            if (!f2.hasExactDataType(f1.getDataType(i3))) {
                return false;
            }
        }
        int s14 = f1.countDataSchemes();
        int s24 = f2.countDataSchemes();
        if (s14 != s24) {
            return false;
        }
        for (int i4 = 0; i4 < s14; i4++) {
            if (!f2.hasDataScheme(f1.getDataScheme(i4))) {
                return false;
            }
        }
        int s15 = f1.countDataAuthorities();
        int s25 = f2.countDataAuthorities();
        if (s15 != s25) {
            return false;
        }
        for (int i5 = 0; i5 < s15; i5++) {
            if (!f2.hasDataAuthority(f1.getDataAuthority(i5))) {
                return false;
            }
        }
        int s16 = f1.countDataPaths();
        int s26 = f2.countDataPaths();
        if (s16 != s26) {
            return false;
        }
        for (int i6 = 0; i6 < s16; i6++) {
            if (!f2.hasDataPath(f1.getDataPath(i6))) {
                return false;
            }
        }
        int s17 = f1.countDataSchemeSpecificParts();
        int s27 = f2.countDataSchemeSpecificParts();
        if (s17 != s27) {
            return false;
        }
        for (int i7 = 0; i7 < s17; i7++) {
            if (!f2.hasDataSchemeSpecificPart(f1.getDataSchemeSpecificPart(i7))) {
                return false;
            }
        }
        return true;
    }
}
