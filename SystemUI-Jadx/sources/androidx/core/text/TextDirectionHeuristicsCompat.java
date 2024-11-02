package androidx.core.text;

import android.text.TextUtils;
import java.util.Locale;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicInternal FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristicInternal FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristicInternal LTR = new TextDirectionHeuristicInternal(null, false);
    public static final TextDirectionHeuristicInternal RTL = new TextDirectionHeuristicInternal(null, true);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class AnyStrong implements TextDirectionAlgorithm {
        public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        public final boolean mLookForRtl;

        private AnyStrong(boolean z) {
            this.mLookForRtl = z;
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        public final int checkRtl(int i, CharSequence charSequence) {
            int i2 = i + 0;
            int i3 = 0;
            boolean z = false;
            while (true) {
                char c = 2;
                boolean z2 = this.mLookForRtl;
                if (i3 < i2) {
                    byte directionality = Character.getDirectionality(charSequence.charAt(i3));
                    TextDirectionHeuristicInternal textDirectionHeuristicInternal = TextDirectionHeuristicsCompat.LTR;
                    if (directionality != 0) {
                        if (directionality == 1 || directionality == 2) {
                            c = 0;
                        }
                    } else {
                        c = 1;
                    }
                    if (c != 0) {
                        if (c == 1) {
                            if (!z2) {
                                return 1;
                            }
                        } else {
                            continue;
                            i3++;
                            z = z;
                        }
                    } else if (z2) {
                        return 0;
                    }
                    z = true;
                    i3++;
                    z = z;
                } else {
                    if (z) {
                        return z2 ? 1 : 0;
                    }
                    return 2;
                }
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        private FirstStrong() {
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        public final int checkRtl(int i, CharSequence charSequence) {
            int i2 = i + 0;
            int i3 = 2;
            for (int i4 = 0; i4 < i2 && i3 == 2; i4++) {
                byte directionality = Character.getDirectionality(charSequence.charAt(i4));
                TextDirectionHeuristicInternal textDirectionHeuristicInternal = TextDirectionHeuristicsCompat.LTR;
                if (directionality != 0) {
                    if (directionality != 1 && directionality != 2) {
                        switch (directionality) {
                            case 14:
                            case 15:
                                break;
                            case 16:
                            case 17:
                                break;
                            default:
                                i3 = 2;
                                break;
                        }
                    }
                    i3 = 0;
                }
                i3 = 1;
            }
            return i3;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface TextDirectionAlgorithm {
        int checkRtl(int i, CharSequence charSequence);
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        public final TextDirectionAlgorithm mAlgorithm;

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        public abstract boolean defaultIsRtl();

        public final boolean isRtl(int i, CharSequence charSequence) {
            if (charSequence != null && i >= 0 && charSequence.length() - i >= 0) {
                TextDirectionAlgorithm textDirectionAlgorithm = this.mAlgorithm;
                if (textDirectionAlgorithm == null) {
                    return defaultIsRtl();
                }
                int checkRtl = textDirectionAlgorithm.checkRtl(i, charSequence);
                if (checkRtl == 0) {
                    return true;
                }
                if (checkRtl != 1) {
                    return defaultIsRtl();
                }
                return false;
            }
            throw new IllegalArgumentException();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        public final boolean mDefaultIsRtl;

        public TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = z;
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        public final boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new TextDirectionHeuristicLocale();
        }

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        public final boolean defaultIsRtl() {
            Locale locale = Locale.getDefault();
            int i = TextUtilsCompat.$r8$clinit;
            if (TextUtils.getLayoutDirectionFromLocale(locale) == 1) {
                return true;
            }
            return false;
        }
    }

    static {
        FirstStrong firstStrong = FirstStrong.INSTANCE;
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(firstStrong, false);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(firstStrong, true);
        new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
        int i = TextDirectionHeuristicLocale.$r8$clinit;
    }

    private TextDirectionHeuristicsCompat() {
    }
}
