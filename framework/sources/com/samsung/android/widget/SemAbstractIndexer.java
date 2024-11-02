package com.samsung.android.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import java.text.Collator;
import java.util.HashMap;

/* loaded from: classes6.dex */
public abstract class SemAbstractIndexer extends DataSetObserver {
    private static final char DIGIT_CHAR = '#';
    public static final char FAVORITE_CHAR = 9733;
    private static final String GROUP_CHAR = "👥︎";
    private static final char GROUP_CHECKER = 55357;
    protected static final String INDEXSCROLL_INDEX_COUNTS = "indexscroll_index_counts";
    protected static final String INDEXSCROLL_INDEX_TITLES = "indexscroll_index_titles";
    private static final char SYMBOL_BASE_CHAR = '!';
    private static final char SYMBOL_CHAR = '&';
    protected SparseIntArray mAlphaMap;
    protected CharSequence mAlphabet;
    protected String[] mAlphabetArray;
    protected int mAlphabetLength;
    private Bundle mBundle;
    private int[] mCachingValue;
    protected Collator mCollator;
    private int mFavoriteItemCount;
    protected String[] mLangAlphabetArray;
    private int mProfileItemCount;
    private boolean mUseFavoriteIndex;
    private final String TAG = "SemAbstractIndexer";
    private final boolean debug = false;
    private int mGroupItemCount = 0;
    private int mDigitItemCount = 0;
    private boolean mUseGroupIndex = false;
    private boolean mUseDigitIndex = false;
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    private boolean mRegisteredDataSetObservable = false;
    private HashMap<Integer, Integer> mLangIndexMap = new HashMap<>();
    private int mCurrentLang = -1;

    protected abstract Bundle getBundle();

    protected abstract String getItemAt(int i);

    protected abstract int getItemCount();

    protected abstract boolean isDataToBeIndexedAvailable();

    public SemAbstractIndexer(CharSequence indexCharacters) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = false;
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        initIndexer(indexCharacters);
    }

    public SemAbstractIndexer(String[] indexCharacters, int aLangIndex) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = false;
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mLangAlphabetArray = indexCharacters;
        setMultiLangIndexer(aLangIndex);
    }

    public SemAbstractIndexer(CharSequence indexCharacters, int profileCount, int favoriteCount) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = true;
        this.mProfileItemCount = profileCount;
        this.mFavoriteItemCount = favoriteCount;
        initIndexer(indexCharacters);
    }

    public SemAbstractIndexer(String[] indexCharacters, int aLangIndex, int profileCount, int favoriteCount) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = true;
        this.mProfileItemCount = profileCount;
        this.mFavoriteItemCount = favoriteCount;
        this.mLangAlphabetArray = indexCharacters;
        setMultiLangIndexer(aLangIndex);
    }

    public String[] getLangAlphabetArray() {
        return this.mLangAlphabetArray;
    }

    public int getCachingValue(int index) {
        if (index < 0 || index >= this.mAlphabetLength) {
            return -1;
        }
        return this.mCachingValue[index];
    }

    public void setMultiLangIndexer(int aLangIndex) {
        StringBuilder indexerString;
        this.mCurrentLang = aLangIndex;
        if (this.mUseFavoriteIndex) {
            if (this.mUseGroupIndex) {
                indexerString = new StringBuilder(String.valueOf(FAVORITE_CHAR) + GROUP_CHECKER);
                indexerString.append(SYMBOL_CHAR);
            } else {
                indexerString = new StringBuilder(String.valueOf(FAVORITE_CHAR) + SYMBOL_CHAR);
            }
        } else {
            indexerString = new StringBuilder(String.valueOf(SYMBOL_CHAR));
        }
        int langIndex = 0;
        while (langIndex < this.mLangAlphabetArray.length) {
            for (int j = 0; j < this.mLangAlphabetArray[langIndex].length(); j++) {
                this.mLangIndexMap.put(Integer.valueOf(indexerString.length()), Integer.valueOf(langIndex));
                indexerString.append(this.mLangAlphabetArray[langIndex].charAt(j));
            }
            langIndex++;
        }
        if (this.mUseDigitIndex) {
            this.mLangIndexMap.put(Integer.valueOf(indexerString.length()), Integer.valueOf(langIndex - 1));
            indexerString.append(DIGIT_CHAR);
        }
        initIndexer(indexerString.toString());
    }

    public void setProfileItem(int count) {
        if (count >= 0) {
            this.mProfileItemCount = count;
        }
    }

    public void setFavoriteItem(int count) {
        if (count >= 0) {
            this.mFavoriteItemCount = count;
            this.mUseFavoriteIndex = true;
            setMultiLangIndexer(this.mCurrentLang);
        }
    }

    public void setGroupItem(int count) {
        if (count >= 0) {
            this.mGroupItemCount = count;
            this.mUseGroupIndex = true;
            setMultiLangIndexer(this.mCurrentLang);
        }
    }

    public void setDigitItem(int count) {
        if (count >= 0) {
            this.mDigitItemCount = count;
            this.mUseDigitIndex = true;
            setMultiLangIndexer(this.mCurrentLang);
        }
    }

    public boolean isUseDigitIndex() {
        return this.mUseDigitIndex;
    }

    public int getCurrentLang() {
        return this.mCurrentLang;
    }

    public int getLangbyIndex(int aIndex) {
        if (aIndex >= 0 && this.mLangIndexMap != null) {
            Integer lIndexVal = new Integer(aIndex);
            if (this.mLangIndexMap.containsKey(lIndexVal)) {
                return this.mLangIndexMap.get(lIndexVal).intValue();
            }
            return -1;
        }
        return -1;
    }

    private void initIndexer(CharSequence alphabet) {
        if (alphabet == null || alphabet.length() == 0) {
            throw new IllegalArgumentException("Invalid indexString :" + ((Object) alphabet));
        }
        this.mAlphabet = alphabet;
        int length = alphabet.length();
        this.mAlphabetLength = length;
        this.mCachingValue = new int[length];
        this.mAlphabetArray = new String[length];
        int i = 0;
        while (true) {
            int i2 = this.mAlphabetLength;
            if (i < i2) {
                if (this.mUseGroupIndex && this.mAlphabet.charAt(i) == 55357) {
                    this.mAlphabetArray[i] = GROUP_CHAR;
                } else {
                    this.mAlphabetArray[i] = Character.toString(this.mAlphabet.charAt(i));
                }
                i++;
            } else {
                this.mAlphaMap = new SparseIntArray(i2);
                Collator collator = Collator.getInstance();
                this.mCollator = collator;
                collator.setStrength(0);
                return;
            }
        }
    }

    public String[] getAlphabetArray() {
        return this.mAlphabetArray;
    }

    protected int compare(String word, String indexString) {
        return this.mCollator.compare(word, indexString);
    }

    public void cacheIndexInfo() {
        if (!isDataToBeIndexedAvailable() || getItemCount() == 0) {
            return;
        }
        Bundle bundle = getBundle();
        this.mBundle = bundle;
        if (bundle != null && bundle.containsKey("indexscroll_index_titles") && this.mBundle.containsKey("indexscroll_index_counts")) {
            getBundleInfo();
            return;
        }
        onBeginTransaction();
        for (int sectionIndex = 0; sectionIndex < this.mAlphabetLength; sectionIndex++) {
            String searchString = "" + this.mAlphabet.charAt(sectionIndex);
            this.mCachingValue[sectionIndex] = getPositionForString(searchString);
        }
        onEndTransaction();
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00e0, code lost:
    
        if (r4 != '#') goto L157;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getPositionForString(java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemAbstractIndexer.getPositionForString(java.lang.String):int");
    }

    private void getBundleInfo() {
        String[] sections = this.mBundle.getStringArray("indexscroll_index_titles");
        int[] counts = this.mBundle.getIntArray("indexscroll_index_counts");
        int basePosition = this.mProfileItemCount;
        int baseSectionIndex = 0;
        for (int index = 0; index < this.mAlphabetLength; index++) {
            char targetChar = this.mAlphabet.charAt(index);
            this.mCachingValue[index] = basePosition;
            Log.d("SemAbstractIndexer", "Get index info from bundle (" + index + ") : " + targetChar + " = " + basePosition);
            if (targetChar == 9733) {
                basePosition += this.mFavoriteItemCount;
            } else if (targetChar == 55357) {
                basePosition += this.mGroupItemCount;
            }
            int sectionIndex = baseSectionIndex;
            while (true) {
                if (sectionIndex >= sections.length) {
                    break;
                }
                if (targetChar != sections[sectionIndex].charAt(0)) {
                    sectionIndex++;
                } else {
                    basePosition += counts[sectionIndex];
                    baseSectionIndex = sectionIndex;
                    break;
                }
            }
            if (targetChar == "#".charAt(0)) {
                this.mCachingValue[index] = (getItemCount() + this.mProfileItemCount) - this.mDigitItemCount;
            }
        }
    }

    @Override // android.database.DataSetObserver
    public void onChanged() {
        super.onChanged();
        this.mAlphaMap.clear();
        this.mDataSetObservable.notifyChanged();
    }

    @Override // android.database.DataSetObserver
    public void onInvalidated() {
        super.onInvalidated();
        this.mAlphaMap.clear();
        this.mDataSetObservable.notifyInvalidated();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        if (!this.mRegisteredDataSetObservable) {
            this.mDataSetObservable.registerObserver(observer);
            this.mRegisteredDataSetObservable = true;
        } else {
            Log.e("SemAbstractIndexer", "Observer " + observer + " is already registered.");
        }
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (this.mRegisteredDataSetObservable) {
            this.mDataSetObservable.unregisterObserver(observer);
            this.mRegisteredDataSetObservable = false;
        } else {
            Log.e("SemAbstractIndexer", "Observer " + observer + " was not registered.");
        }
    }

    protected void onBeginTransaction() {
    }

    protected void onEndTransaction() {
    }
}
