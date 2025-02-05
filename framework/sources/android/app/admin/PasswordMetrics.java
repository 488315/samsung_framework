package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.PasswordValidationError;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class PasswordMetrics implements Parcelable {
    private static final int CHAR_DIGIT = 2;
    private static final int CHAR_LOWER_CASE = 0;
    private static final int CHAR_SYMBOL = 3;
    private static final int CHAR_UPPER_CASE = 1;
    public static final Parcelable.Creator<PasswordMetrics> CREATOR = new Parcelable.Creator<PasswordMetrics>() { // from class: android.app.admin.PasswordMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordMetrics createFromParcel(Parcel in) {
            int credType = in.readInt();
            int length = in.readInt();
            int letters = in.readInt();
            int upperCase = in.readInt();
            int lowerCase = in.readInt();
            int numeric = in.readInt();
            int symbols = in.readInt();
            int nonLetter = in.readInt();
            int nonNumeric = in.readInt();
            int seqLength = in.readInt();
            return new PasswordMetrics(credType, length, letters, upperCase, lowerCase, numeric, symbols, nonLetter, nonNumeric, seqLength);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordMetrics[] newArray(int size) {
            return new PasswordMetrics[size];
        }
    };
    public static final int MAX_ALLOWED_SEQUENCE = 3;
    private static final String TAG = "PasswordMetrics";
    public int credType;
    public int length;
    public int letters;
    public int lowerCase;
    public int nonLetter;
    public int nonNumeric;
    public int numeric;
    public int seqLength;
    public int symbols;
    public int upperCase;

    @Retention(RetentionPolicy.SOURCE)
    private @interface CharacterCatagory {
    }

    public PasswordMetrics(int credType) {
        this.length = 0;
        this.letters = 0;
        this.upperCase = 0;
        this.lowerCase = 0;
        this.numeric = 0;
        this.symbols = 0;
        this.nonLetter = 0;
        this.nonNumeric = 0;
        this.seqLength = Integer.MAX_VALUE;
        this.credType = credType;
    }

    public PasswordMetrics(int credType, int length, int letters, int upperCase, int lowerCase, int numeric, int symbols, int nonLetter, int nonNumeric, int seqLength) {
        this.length = 0;
        this.letters = 0;
        this.upperCase = 0;
        this.lowerCase = 0;
        this.numeric = 0;
        this.symbols = 0;
        this.nonLetter = 0;
        this.nonNumeric = 0;
        this.seqLength = Integer.MAX_VALUE;
        this.credType = credType;
        this.length = length;
        this.letters = letters;
        this.upperCase = upperCase;
        this.lowerCase = lowerCase;
        this.numeric = numeric;
        this.symbols = symbols;
        this.nonLetter = nonLetter;
        this.nonNumeric = nonNumeric;
        this.seqLength = seqLength;
    }

    private PasswordMetrics(PasswordMetrics other) {
        this(other.credType, other.length, other.letters, other.upperCase, other.lowerCase, other.numeric, other.symbols, other.nonLetter, other.nonNumeric, other.seqLength);
    }

    public static int sanitizeComplexityLevel(int complexityLevel) {
        switch (complexityLevel) {
            case 0:
            case 65536:
            case 196608:
            case 327680:
                return complexityLevel;
            default:
                Log.w(TAG, "Invalid password complexity used: " + complexityLevel);
                return 0;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.credType);
        dest.writeInt(this.length);
        dest.writeInt(this.letters);
        dest.writeInt(this.upperCase);
        dest.writeInt(this.lowerCase);
        dest.writeInt(this.numeric);
        dest.writeInt(this.symbols);
        dest.writeInt(this.nonLetter);
        dest.writeInt(this.nonNumeric);
        dest.writeInt(this.seqLength);
    }

    public static PasswordMetrics computeForCredential(LockscreenCredential credential) {
        if (credential.isPassword() || credential.isPin()) {
            return computeForPasswordOrPin(credential.getCredential(), credential.isPin());
        }
        if (credential.isPattern()) {
            PasswordMetrics metrics = new PasswordMetrics(1);
            metrics.length = credential.size();
            return metrics;
        }
        if (credential.isNone()) {
            return new PasswordMetrics(-1);
        }
        if (credential.isUCM()) {
            return new PasswordMetrics(6);
        }
        throw new IllegalArgumentException("Unknown credential type " + credential.getType());
    }

    private static PasswordMetrics computeForPasswordOrPin(byte[] credential, boolean isPin) {
        int letters = 0;
        int upperCase = 0;
        int lowerCase = 0;
        int numeric = 0;
        int symbols = 0;
        int nonLetter = 0;
        int nonNumeric = 0;
        int length = credential.length;
        for (byte b : credential) {
            switch (categoryChar((char) b)) {
                case 0:
                    letters++;
                    lowerCase++;
                    nonNumeric++;
                    break;
                case 1:
                    letters++;
                    upperCase++;
                    nonNumeric++;
                    break;
                case 2:
                    numeric++;
                    nonLetter++;
                    break;
                case 3:
                    symbols++;
                    nonLetter++;
                    nonNumeric++;
                    break;
            }
        }
        int credType = isPin ? 3 : 4;
        int seqLength = maxLengthSequence(credential);
        int length2 = symbols;
        return new PasswordMetrics(credType, length, letters, upperCase, lowerCase, numeric, length2, nonLetter, nonNumeric, seqLength);
    }

    public static int maxLengthSequence(byte[] bytes) {
        if (bytes.length == 0) {
            return 0;
        }
        char previousChar = (char) bytes[0];
        int category = categoryChar(previousChar);
        int diff = 0;
        boolean hasDiff = false;
        int maxLength = 0;
        int startSequence = 0;
        for (int current = 1; current < bytes.length; current++) {
            char currentChar = (char) bytes[current];
            int categoryCurrent = categoryChar(currentChar);
            int currentDiff = currentChar - previousChar;
            if (categoryCurrent != category || Math.abs(currentDiff) > maxDiffCategory(category)) {
                maxLength = Math.max(maxLength, current - startSequence);
                startSequence = current;
                hasDiff = false;
                category = categoryCurrent;
            } else {
                if (hasDiff && currentDiff != diff) {
                    maxLength = Math.max(maxLength, current - startSequence);
                    startSequence = current - 1;
                }
                diff = currentDiff;
                hasDiff = true;
            }
            previousChar = currentChar;
        }
        int current2 = bytes.length;
        return Math.max(maxLength, current2 - startSequence);
    }

    private static int categoryChar(char c) {
        if ('a' <= c && c <= 'z') {
            return 0;
        }
        if ('A' > c || c > 'Z') {
            return ('0' > c || c > '9') ? 3 : 2;
        }
        return 1;
    }

    private static int maxDiffCategory(int category) {
        switch (category) {
            case 0:
            case 1:
                return 1;
            case 2:
                return 10;
            default:
                return 0;
        }
    }

    public static PasswordMetrics merge(List<PasswordMetrics> metrics) {
        PasswordMetrics result = new PasswordMetrics(-1);
        for (PasswordMetrics m : metrics) {
            result.maxWith(m);
        }
        return result;
    }

    public void maxWith(PasswordMetrics other) {
        this.credType = Math.max(this.credType, other.credType);
        if (this.credType != 4 && this.credType != 3) {
            return;
        }
        this.length = Math.max(this.length, other.length);
        this.letters = Math.max(this.letters, other.letters);
        this.upperCase = Math.max(this.upperCase, other.upperCase);
        this.lowerCase = Math.max(this.lowerCase, other.lowerCase);
        this.numeric = Math.max(this.numeric, other.numeric);
        this.symbols = Math.max(this.symbols, other.symbols);
        this.nonLetter = Math.max(this.nonLetter, other.nonLetter);
        this.nonNumeric = Math.max(this.nonNumeric, other.nonNumeric);
        this.seqLength = Math.min(this.seqLength, other.seqLength);
    }

    public static int complexityLevelToMinQuality(int complexity) {
        switch (complexity) {
            case 65536:
                return 65536;
            case 196608:
            case 327680:
                return 196608;
            default:
                return 0;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    private static abstract class ComplexityBucket {
        public static final ComplexityBucket BUCKET_HIGH;
        public static final ComplexityBucket BUCKET_NONE;
        int mComplexityLevel;
        public static final ComplexityBucket BUCKET_MEDIUM = new AnonymousClass2("BUCKET_MEDIUM", 1, 196608);
        public static final ComplexityBucket BUCKET_LOW = new AnonymousClass3("BUCKET_LOW", 2, 65536);
        private static final /* synthetic */ ComplexityBucket[] $VALUES = $values();

        abstract boolean allowsCredType(int i);

        abstract boolean canHaveSequence();

        abstract int getMinimumLength(boolean z);

        private static /* synthetic */ ComplexityBucket[] $values() {
            return new ComplexityBucket[]{BUCKET_HIGH, BUCKET_MEDIUM, BUCKET_LOW, BUCKET_NONE};
        }

        public static ComplexityBucket valueOf(String name) {
            return (ComplexityBucket) Enum.valueOf(ComplexityBucket.class, name);
        }

        public static ComplexityBucket[] values() {
            return (ComplexityBucket[]) $VALUES.clone();
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$1, reason: invalid class name */
        enum AnonymousClass1 extends ComplexityBucket {
            private AnonymousClass1(String str, int i, int complexityLevel) {
                super(str, i, complexityLevel);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return false;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean containsNonNumeric) {
                return containsNonNumeric ? 6 : 8;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int credType) {
                return credType == 4 || credType == 3;
            }
        }

        static {
            int i = 0;
            BUCKET_HIGH = new AnonymousClass1("BUCKET_HIGH", i, 327680);
            BUCKET_NONE = new AnonymousClass4("BUCKET_NONE", 3, i);
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$2, reason: invalid class name */
        enum AnonymousClass2 extends ComplexityBucket {
            private AnonymousClass2(String str, int i, int complexityLevel) {
                super(str, i, complexityLevel);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return false;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean containsNonNumeric) {
                return 4;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int credType) {
                return credType == 4 || credType == 3;
            }
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$3, reason: invalid class name */
        enum AnonymousClass3 extends ComplexityBucket {
            private AnonymousClass3(String str, int i, int complexityLevel) {
                super(str, i, complexityLevel);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return true;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean containsNonNumeric) {
                return 0;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int credType) {
                return credType != -1;
            }
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$4, reason: invalid class name */
        enum AnonymousClass4 extends ComplexityBucket {
            private AnonymousClass4(String str, int i, int complexityLevel) {
                super(str, i, complexityLevel);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return true;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean containsNonNumeric) {
                return 0;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int credType) {
                return true;
            }
        }

        private ComplexityBucket(String str, int i, int complexityLevel) {
            this.mComplexityLevel = complexityLevel;
        }

        static ComplexityBucket forComplexity(int complexityLevel) {
            for (ComplexityBucket bucket : values()) {
                if (bucket.mComplexityLevel == complexityLevel) {
                    return bucket;
                }
            }
            throw new IllegalArgumentException("Invalid complexity level: " + complexityLevel);
        }
    }

    private boolean satisfiesBucket(ComplexityBucket bucket) {
        if (!bucket.allowsCredType(this.credType)) {
            return false;
        }
        if (this.credType != 4 && this.credType != 3) {
            return true;
        }
        if (bucket.canHaveSequence() || this.seqLength <= 3) {
            return this.length >= bucket.getMinimumLength(this.nonNumeric > 0);
        }
        return false;
    }

    public int determineComplexity() {
        for (ComplexityBucket bucket : ComplexityBucket.values()) {
            if (satisfiesBucket(bucket)) {
                return bucket.mComplexityLevel;
            }
        }
        throw new IllegalStateException("Failed to figure out complexity for a given metrics");
    }

    public static List<PasswordValidationError> validateCredential(PasswordMetrics adminMetrics, int minComplexity, LockscreenCredential credential) {
        if (credential.hasInvalidChars()) {
            return Collections.singletonList(new PasswordValidationError(2, 0));
        }
        PasswordMetrics actualMetrics = computeForCredential(credential);
        return validatePasswordMetrics(adminMetrics, minComplexity, actualMetrics);
    }

    public static List<PasswordValidationError> validatePasswordMetrics(PasswordMetrics adminMetrics, int minComplexity, PasswordMetrics actualMetrics) {
        ComplexityBucket bucket = ComplexityBucket.forComplexity(minComplexity);
        if (actualMetrics.credType < adminMetrics.credType || !bucket.allowsCredType(actualMetrics.credType)) {
            return Collections.singletonList(new PasswordValidationError(1, 0));
        }
        if (actualMetrics.credType == 1) {
            if (actualMetrics.length != 0 && actualMetrics.length < 4) {
                return Collections.singletonList(new PasswordValidationError(3, 4));
            }
            return Collections.emptyList();
        }
        if (actualMetrics.credType == -1) {
            return Collections.emptyList();
        }
        if (actualMetrics.credType == 3 && actualMetrics.nonNumeric > 0) {
            return Collections.singletonList(new PasswordValidationError(2, 0));
        }
        ArrayList<PasswordValidationError> result = new ArrayList<>();
        if (actualMetrics.length > 256) {
            result.add(new PasswordValidationError(5, 256));
        }
        PasswordMetrics minMetrics = applyComplexity(adminMetrics, actualMetrics.credType == 3, bucket);
        minMetrics.length = Math.min(256, Math.max(minMetrics.length, 4));
        minMetrics.removeOverlapping();
        comparePasswordMetrics(minMetrics, bucket, actualMetrics, result);
        return result;
    }

    private static void comparePasswordMetrics(PasswordMetrics minMetrics, ComplexityBucket bucket, PasswordMetrics actualMetrics, ArrayList<PasswordValidationError> result) {
        int allNumericMinimumLength;
        if (actualMetrics.length < minMetrics.length) {
            result.add(new PasswordValidationError(3, minMetrics.length));
        }
        if (actualMetrics.nonNumeric == 0 && minMetrics.nonNumeric == 0 && minMetrics.letters == 0 && minMetrics.lowerCase == 0 && minMetrics.upperCase == 0 && minMetrics.symbols == 0 && (allNumericMinimumLength = bucket.getMinimumLength(false)) > minMetrics.length && allNumericMinimumLength > minMetrics.numeric && actualMetrics.length < allNumericMinimumLength) {
            result.add(new PasswordValidationError(4, allNumericMinimumLength));
        }
        if (actualMetrics.letters < minMetrics.letters) {
            result.add(new PasswordValidationError(7, minMetrics.letters));
        }
        if (actualMetrics.upperCase < minMetrics.upperCase) {
            result.add(new PasswordValidationError(8, minMetrics.upperCase));
        }
        if (actualMetrics.lowerCase < minMetrics.lowerCase) {
            result.add(new PasswordValidationError(9, minMetrics.lowerCase));
        }
        if (actualMetrics.numeric < minMetrics.numeric) {
            result.add(new PasswordValidationError(10, minMetrics.numeric));
        }
        if (actualMetrics.symbols < minMetrics.symbols) {
            result.add(new PasswordValidationError(11, minMetrics.symbols));
        }
        if (actualMetrics.nonLetter < minMetrics.nonLetter) {
            result.add(new PasswordValidationError(12, minMetrics.nonLetter));
        }
        if (actualMetrics.nonNumeric < minMetrics.nonNumeric) {
            result.add(new PasswordValidationError(13, minMetrics.nonNumeric));
        }
        if (actualMetrics.seqLength > minMetrics.seqLength) {
            result.add(new PasswordValidationError(6, 0));
        }
    }

    private void removeOverlapping() {
        int indirectLetters = this.upperCase + this.lowerCase;
        int indirectNonLetter = this.numeric + this.symbols;
        int effectiveLetters = Math.max(this.letters, indirectLetters);
        int indirectNonNumeric = this.symbols + effectiveLetters;
        int effectiveNonLetter = Math.max(this.nonLetter, indirectNonLetter);
        int effectiveNonNumeric = Math.max(this.nonNumeric, indirectNonNumeric);
        int indirectLength = Math.max(effectiveLetters + effectiveNonLetter, this.numeric + effectiveNonNumeric);
        if (indirectLetters >= this.letters) {
            this.letters = 0;
        }
        if (indirectNonLetter >= this.nonLetter) {
            this.nonLetter = 0;
        }
        if (indirectNonNumeric >= this.nonNumeric) {
            this.nonNumeric = 0;
        }
        if (indirectLength >= this.length) {
            this.length = 0;
        }
    }

    public static PasswordMetrics applyComplexity(PasswordMetrics adminMetrics, boolean isPin, int complexity) {
        return applyComplexity(adminMetrics, isPin, ComplexityBucket.forComplexity(complexity));
    }

    private static PasswordMetrics applyComplexity(PasswordMetrics adminMetrics, boolean isPin, ComplexityBucket bucket) {
        PasswordMetrics minMetrics = new PasswordMetrics(adminMetrics);
        if (!bucket.canHaveSequence()) {
            minMetrics.seqLength = Math.min(minMetrics.seqLength, 3);
        }
        minMetrics.length = Math.max(minMetrics.length, bucket.getMinimumLength(!isPin));
        return minMetrics;
    }

    public static boolean isNumericOnly(String password) {
        if (password.length() == 0) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (categoryChar(password.charAt(i)) != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PasswordMetrics that = (PasswordMetrics) o;
        if (this.credType == that.credType && this.length == that.length && this.letters == that.letters && this.upperCase == that.upperCase && this.lowerCase == that.lowerCase && this.numeric == that.numeric && this.symbols == that.symbols && this.nonLetter == that.nonLetter && this.nonNumeric == that.nonNumeric && this.seqLength == that.seqLength) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.credType), Integer.valueOf(this.length), Integer.valueOf(this.letters), Integer.valueOf(this.upperCase), Integer.valueOf(this.lowerCase), Integer.valueOf(this.numeric), Integer.valueOf(this.symbols), Integer.valueOf(this.nonLetter), Integer.valueOf(this.nonNumeric), Integer.valueOf(this.seqLength));
    }
}
