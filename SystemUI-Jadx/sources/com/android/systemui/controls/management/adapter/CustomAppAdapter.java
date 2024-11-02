package com.android.systemui.controls.management.adapter;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.BasicRune;
import com.android.systemui.Prefs;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.adapter.CustomAppAdapter;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.SALogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class CustomAppAdapter extends RecyclerView.Adapter {
    public final BadgeProvider badgeProvider;
    public final Context context;
    public final ControlsUtil controlsUtil;
    public final CustomFavoritesRenderer favoritesRenderer;
    public final boolean isOOBE;
    public final LayoutInflater layoutInflater;
    public List listOfServices;
    public final Function1 onAppSelected;
    public final Resources resources;
    public final SALogger saLogger;
    public final Function1 switchCallback;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class CustomHolder extends RecyclerView.ViewHolder {
        public final LinearLayout appInfoContainer;
        public final View badge;
        public final BadgeProvider badgeProvider;
        public final CustomFavoritesRenderer favRenderer;
        public final TextView favorites;
        public final ImageView icon;
        public final boolean isOOBE;
        public final SwitchCompat onOff;
        public final LinearLayout onOffLayout;
        public final SALogger saLogger;
        public final Function1 switchCallback;
        public final View switchDivder;
        public final TextView title;

        public CustomHolder(View view, CustomFavoritesRenderer customFavoritesRenderer, Function1 function1, boolean z, SALogger sALogger, BadgeProvider badgeProvider) {
            super(view);
            this.favRenderer = customFavoritesRenderer;
            this.switchCallback = function1;
            this.isOOBE = z;
            this.saLogger = sALogger;
            this.badgeProvider = badgeProvider;
            this.icon = (ImageView) this.itemView.requireViewById(R.id.icon);
            this.title = (TextView) this.itemView.requireViewById(R.id.title);
            this.favorites = (TextView) this.itemView.requireViewById(com.android.systemui.R.id.favorites);
            this.onOff = (SwitchCompat) this.itemView.requireViewById(com.android.systemui.R.id.on_off_switch);
            this.onOffLayout = (LinearLayout) this.itemView.requireViewById(com.android.systemui.R.id.on_off_switch_layout);
            this.switchDivder = this.itemView.requireViewById(com.android.systemui.R.id.switch_divider);
            this.appInfoContainer = (LinearLayout) this.itemView.requireViewById(com.android.systemui.R.id.app_info_container);
            this.badge = this.itemView.requireViewById(com.android.systemui.R.id.badge);
        }
    }

    public CustomAppAdapter(Executor executor, Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater, Function1 function1, CustomFavoritesRenderer customFavoritesRenderer, Context context, ControlsUtil controlsUtil, SALogger sALogger, BadgeProvider badgeProvider, Resources resources, Set set, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(executor, executor2, lifecycle, controlsListingController, layoutInflater, (i & 32) != 0 ? new Function1() { // from class: com.android.systemui.controls.management.adapter.CustomAppAdapter.1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        } : function1, customFavoritesRenderer, context, controlsUtil, sALogger, badgeProvider, resources, (i & 4096) != 0 ? EmptySet.INSTANCE : set, function12);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.listOfServices.size();
    }

    public final int getTotalFavoriteAndActiveAppCount() {
        boolean z;
        List list = this.listOfServices;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
            ComponentName componentName = controlsServiceInfo.componentName;
            CustomFavoritesRenderer customFavoritesRenderer = this.favoritesRenderer;
            if ((((Number) customFavoritesRenderer.favoriteFunction.invoke(componentName)).intValue() > 0 && ((Boolean) customFavoritesRenderer.getActiveFlag.invoke(controlsServiceInfo.componentName)).booleanValue()) || controlsServiceInfo.panelActivity != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                arrayList.add(obj);
            }
        }
        return arrayList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        final boolean z;
        String str;
        boolean z2;
        boolean z3;
        final CustomHolder customHolder = (CustomHolder) viewHolder;
        final ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) this.listOfServices.get(i);
        customHolder.icon.setImageDrawable(controlsServiceInfo.loadIcon());
        CharSequence loadLabel = controlsServiceInfo.loadLabel();
        TextView textView = customHolder.title;
        textView.setText(loadLabel);
        if (controlsServiceInfo.panelActivity != null) {
            z = true;
        } else {
            z = false;
        }
        CustomFavoritesRenderer customFavoritesRenderer = customHolder.favRenderer;
        ComponentName componentName = controlsServiceInfo.componentName;
        if (!z) {
            int intValue = ((Number) customFavoritesRenderer.favoriteFunction.invoke(componentName)).intValue();
            Resources resources = customFavoritesRenderer.resources;
            if (intValue != 0) {
                str = resources.getQuantityString(com.android.systemui.R.plurals.controls_item_number_of_favorites, intValue, Integer.valueOf(intValue));
            } else {
                str = resources.getString(com.android.systemui.R.string.controls_no_items_favorites);
            }
        } else {
            str = null;
        }
        TextView textView2 = customHolder.favorites;
        textView2.setText(str);
        textView2.setVisibility(0);
        CharSequence loadLabel2 = controlsServiceInfo.loadLabel();
        SwitchCompat switchCompat = customHolder.onOff;
        switchCompat.setContentDescription(loadLabel2);
        if (((Number) customFavoritesRenderer.favoriteFunction.invoke(componentName)).intValue() <= 0 && !z) {
            z2 = false;
        } else {
            z2 = true;
        }
        switchCompat.setEnabled(z2);
        boolean isEnabled = switchCompat.isEnabled();
        Function1 function1 = customFavoritesRenderer.getActiveFlag;
        if (isEnabled && ((Boolean) function1.invoke(componentName)).booleanValue()) {
            z3 = true;
        } else {
            z3 = false;
        }
        switchCompat.setChecked(z3);
        if (z) {
            customHolder.switchDivder.setVisibility(8);
            int dimensionPixelSize = CustomAppAdapter.this.resources.getDimensionPixelSize(com.android.systemui.R.dimen.control_provider_title_top_bottom_padding_for_panel);
            customHolder.appInfoContainer.setPaddingRelative(0, dimensionPixelSize, 0, dimensionPixelSize);
            textView2.setVisibility(8);
        }
        if (!z && ((Number) customFavoritesRenderer.favoriteFunction.invoke(componentName)).intValue() == 0 && ((Boolean) function1.invoke(componentName)).booleanValue()) {
            customFavoritesRenderer.setActiveFlag.invoke(componentName, Boolean.FALSE);
        }
        customHolder.onOffLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.CustomAppAdapter$CustomHolder$bindData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SALogger.Event chooseAppOnOffOnManageApps;
                if (CustomAppAdapter.CustomHolder.this.onOff.isEnabled()) {
                    boolean z4 = !CustomAppAdapter.CustomHolder.this.onOff.isChecked();
                    CustomAppAdapter.CustomHolder.this.onOff.setChecked(z4);
                    if (z) {
                        CustomAppAdapter.CustomHolder.this.favRenderer.setActivePanelFlag.invoke(controlsServiceInfo.componentName, Boolean.valueOf(z4));
                    } else {
                        CustomAppAdapter.CustomHolder.this.favRenderer.setActiveFlag.invoke(controlsServiceInfo.componentName, Boolean.valueOf(z4));
                    }
                    CustomAppAdapter.CustomHolder customHolder2 = CustomAppAdapter.CustomHolder.this;
                    customHolder2.switchCallback.invoke(customHolder2.title.getText().toString());
                    if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                        CustomAppAdapter.CustomHolder customHolder3 = CustomAppAdapter.CustomHolder.this;
                        SALogger sALogger = customHolder3.saLogger;
                        if (customHolder3.isOOBE) {
                            chooseAppOnOffOnManageApps = new SALogger.Event.ChooseAppOnOff(z4);
                        } else {
                            chooseAppOnOffOnManageApps = new SALogger.Event.ChooseAppOnOffOnManageApps(z4);
                        }
                        sALogger.sendEvent(chooseAppOnOffOnManageApps);
                        return;
                    }
                    return;
                }
                CustomAppAdapter.CustomHolder.this.itemView.performClick();
            }
        });
        boolean z4 = BasicRune.CONTROLS_BADGE;
        View view = customHolder.itemView;
        if (z4) {
            BadgeProviderImpl badgeProviderImpl = (BadgeProviderImpl) customHolder.badgeProvider;
            boolean contains = badgeProviderImpl.badgeRequiredSet.contains(componentName.getPackageName());
            View view2 = customHolder.badge;
            if (contains) {
                view2.setVisibility(0);
            } else {
                view2.setVisibility(8);
            }
            badgeProviderImpl.setDescription(componentName, view, ((Object) textView.getText()) + ", " + ((Object) textView2.getText()));
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.CustomAppAdapter$onBindViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                String str2;
                SALogger.Event event;
                if (((ControlsServiceInfo) CustomAppAdapter.this.listOfServices.get(i)).panelActivity != null) {
                    SwitchCompat switchCompat2 = (SwitchCompat) customHolder.itemView.requireViewById(com.android.systemui.R.id.on_off_switch);
                    boolean z5 = !switchCompat2.isChecked();
                    switchCompat2.setChecked(z5);
                    CustomAppAdapter customAppAdapter = CustomAppAdapter.this;
                    customAppAdapter.favoritesRenderer.setActivePanelFlag.invoke(((ControlsServiceInfo) customAppAdapter.listOfServices.get(i)).componentName, Boolean.valueOf(z5));
                } else {
                    CustomAppAdapter customAppAdapter2 = CustomAppAdapter.this;
                    Function1 function12 = customAppAdapter2.onAppSelected;
                    ControlsServiceInfo controlsServiceInfo2 = (ControlsServiceInfo) customAppAdapter2.listOfServices.get(i);
                    ComponentName componentName2 = controlsServiceInfo2.componentName;
                    if (componentName2 != null) {
                        str2 = componentName2.flattenToString();
                    } else {
                        PackageItemInfo packageItemInfo = controlsServiceInfo2.packageItemInfo;
                        if (packageItemInfo != null) {
                            str2 = packageItemInfo.packageName;
                        } else {
                            str2 = null;
                        }
                    }
                    function12.invoke(ComponentName.unflattenFromString(str2));
                }
                if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                    CustomAppAdapter customAppAdapter3 = CustomAppAdapter.this;
                    SALogger sALogger = customAppAdapter3.saLogger;
                    if (customAppAdapter3.isOOBE) {
                        event = SALogger.Event.TapAppList.INSTANCE;
                    } else {
                        event = SALogger.Event.TapAppListOnManageApps.INSTANCE;
                    }
                    sALogger.sendEvent(event);
                }
            }
        });
        if (this.listOfServices.size() <= i + 1) {
            view.requireViewById(com.android.systemui.R.id.list_divider).setVisibility(8);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new CustomHolder(this.layoutInflater.inflate(com.android.systemui.R.layout.controls_custom_app_item, (ViewGroup) recyclerView, false), this.favoritesRenderer, this.switchCallback, this.isOOBE, this.saLogger, this.badgeProvider);
    }

    public CustomAppAdapter(final Executor executor, final Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater, Function1 function1, CustomFavoritesRenderer customFavoritesRenderer, Context context, ControlsUtil controlsUtil, SALogger sALogger, BadgeProvider badgeProvider, Resources resources, Set<String> set, Function1 function12) {
        this.layoutInflater = layoutInflater;
        this.onAppSelected = function1;
        this.favoritesRenderer = customFavoritesRenderer;
        this.context = context;
        this.controlsUtil = controlsUtil;
        this.saLogger = sALogger;
        this.badgeProvider = badgeProvider;
        this.resources = resources;
        this.switchCallback = function12;
        this.listOfServices = EmptyList.INSTANCE;
        controlsListingController.observe(lifecycle, new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.adapter.CustomAppAdapter$callback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public final void onServicesUpdated(final List list) {
                final CustomAppAdapter customAppAdapter = this;
                final Executor executor3 = executor2;
                executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.adapter.CustomAppAdapter$callback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CustomAppAdapter customAppAdapter2 = CustomAppAdapter.this;
                        ControlsUtil controlsUtil2 = customAppAdapter2.controlsUtil;
                        List list2 = list;
                        controlsUtil2.getClass();
                        customAppAdapter2.listOfServices = ControlsUtil.getListOfServices(customAppAdapter2.context, list2);
                        ListPopupWindow$$ExternalSyntheticOutline0.m("onServiceUpdated listOfServices = ", CustomAppAdapter.this.listOfServices.size(), "CustomAppAdapter");
                        Executor executor4 = executor3;
                        final CustomAppAdapter customAppAdapter3 = CustomAppAdapter.this;
                        executor4.execute(new Runnable() { // from class: com.android.systemui.controls.management.adapter.CustomAppAdapter$callback$1$onServicesUpdated$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CustomAppAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
        controlsUtil.getClass();
        this.isOOBE = !Prefs.getBoolean(context, "ControlsOOBEManageAppsCompleted", false);
    }
}
