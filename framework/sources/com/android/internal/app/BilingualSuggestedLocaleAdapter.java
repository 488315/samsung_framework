package com.android.internal.app;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.LocaleStore;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes5.dex */
public class BilingualSuggestedLocaleAdapter extends SuggestedLocaleAdapter {
    private final Locale mSecondaryLocale;
    private final int mSecondaryLocaleTextDir;
    private LocaleStore.LocaleInfo mSelectedLocaleInfo;
    private final boolean mShowSelection;

    public BilingualSuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> localeOptions, boolean countryMode, Locale secondaryLocale) {
        this(localeOptions, countryMode, secondaryLocale, false);
    }

    public BilingualSuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> localeOptions, boolean countryMode, Locale secondaryLocale, boolean showLastSelected) {
        super(localeOptions, countryMode);
        this.mSecondaryLocale = secondaryLocale;
        if (TextUtils.getLayoutDirectionFromLocale(secondaryLocale) == 1) {
            this.mSecondaryLocaleTextDir = 4;
        } else {
            this.mSecondaryLocaleTextDir = 3;
        }
        this.mShowSelection = showLastSelected;
    }

    @Override // com.android.internal.app.SuggestedLocaleAdapter, android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null && this.mInflater == null) {
            this.mInflater = LayoutInflater.from(parent.getContext());
        }
        int itemType = getItemViewType(position);
        switch (itemType) {
            case 0:
            case 1:
                if (!(convertView instanceof TextView)) {
                    convertView = this.mInflater.inflate(R.layout.language_picker_bilingual_section_header, parent, false);
                }
                TextView textView = (TextView) convertView;
                if (itemType == 0) {
                    setHeaderText(textView, R.string.language_picker_section_suggested_bilingual, R.string.region_picker_section_suggested_bilingual);
                } else {
                    setHeaderText(textView, R.string.language_picker_section_all, R.string.region_picker_section_all);
                }
                return convertView;
            default:
                if (!(convertView instanceof ViewGroup)) {
                    convertView = this.mInflater.inflate(R.layout.language_picker_bilingual_item, parent, false);
                }
                LocaleStore.LocaleInfo item = (LocaleStore.LocaleInfo) getItem(position);
                if (this.mShowSelection) {
                    setItemState(isSelectedLocaleInfo(item), convertView);
                }
                setLocaleToListItem(convertView, item);
                return convertView;
        }
    }

    public void setSelectedLocaleInfo(LocaleStore.LocaleInfo info) {
        this.mSelectedLocaleInfo = info;
        notifyDataSetChanged();
    }

    public LocaleStore.LocaleInfo getSelectedLocaleInfo() {
        return this.mSelectedLocaleInfo;
    }

    private boolean isSelectedLocaleInfo(LocaleStore.LocaleInfo item) {
        return (item == null || this.mSelectedLocaleInfo == null || !item.getId().equals(this.mSelectedLocaleInfo.getId())) ? false : true;
    }

    private void setItemState(boolean selected, View itemView) {
        RelativeLayout background = (RelativeLayout) itemView;
        ImageView indicator = (ImageView) itemView.findViewById(R.id.indicator);
        TextView textNative = (TextView) itemView.findViewById(R.id.locale_native);
        TextView textSecondary = (TextView) itemView.findViewById(R.id.locale_secondary);
        if (indicator == null || textNative == null || textSecondary == null) {
            return;
        }
        textNative.setSelected(selected);
        textSecondary.setSelected(selected);
        if (selected) {
            background.setBackgroundResource(R.drawable.language_picker_item_bg_selected);
            indicator.setVisibility(0);
        } else {
            background.setBackgroundResource(0);
            indicator.setVisibility(8);
        }
    }

    private void setHeaderText(TextView textView, int languageStringResourceId, int regionStringResourceId) {
        if (this.mCountryMode) {
            setTextTo(textView, regionStringResourceId);
        } else {
            setTextTo(textView, languageStringResourceId);
        }
    }

    private void setLocaleToListItem(View itemView, LocaleStore.LocaleInfo localeInfo) {
        int i;
        if (localeInfo == null) {
            throw new NullPointerException("Cannot set locale, locale info is null.");
        }
        TextView textNative = (TextView) itemView.findViewById(R.id.locale_native);
        textNative.lambda$setTextAsync$0(localeInfo.getLabel(this.mCountryMode));
        textNative.setTextLocale(localeInfo.getLocale());
        textNative.setContentDescription(localeInfo.getContentDescription(this.mCountryMode));
        TextView textSecondary = (TextView) itemView.findViewById(R.id.locale_secondary);
        textSecondary.lambda$setTextAsync$0(localeInfo.getLocale().getDisplayLanguage(this.mSecondaryLocale));
        textSecondary.setTextDirection(this.mSecondaryLocaleTextDir);
        if (this.mCountryMode) {
            int layoutDir = TextUtils.getLayoutDirectionFromLocale(localeInfo.getParent());
            itemView.setLayoutDirection(layoutDir);
            if (layoutDir == 1) {
                i = 4;
            } else {
                i = 3;
            }
            textNative.setTextDirection(i);
        }
    }
}
