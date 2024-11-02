package com.android.systemui.media;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.bar.BarController;
import java.util.HashMap;
import java.util.function.BiConsumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda5 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Comparable f$1;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda5(Object obj, Comparable comparable, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = comparable;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i;
        boolean z;
        boolean z2;
        switch (this.$r8$classId) {
            case 0:
                SecMediaHost secMediaHost = (SecMediaHost) this.f$0;
                Configuration configuration = (Configuration) this.f$1;
                MediaType mediaType = (MediaType) obj;
                View view = (View) obj2;
                ViewPagerHelper viewPagerHelper = secMediaHost.mViewPagerHelper;
                ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
                if (viewPager != null) {
                    HashMap hashMap = secMediaHost.mMediaPlayerData;
                    SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) hashMap.get(mediaType);
                    if (secMediaPlayerData != null) {
                        i = secMediaPlayerData.getMediaData().size();
                    } else {
                        i = 0;
                    }
                    ((MediaLoggerImpl) secMediaHost.mLogger).onConfigChanged(i);
                    secMediaHost.mUpdatePlayers = true;
                    secMediaHost.mPagerMargin = secMediaHost.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_side_padding);
                    view.invalidateOutline();
                    if (mediaType.getSupportExpandable()) {
                        secMediaHost.mPlayerBarExpandHelper.setPlayerBarExpansion();
                    }
                    int i2 = configuration.orientation;
                    if (secMediaHost.mOrientation == i2 && !secMediaHost.mPlayerNeedForceUpdate) {
                        z = false;
                    } else {
                        secMediaHost.mOrientation = i2;
                        z = true;
                    }
                    if (z) {
                        SecMediaPlayerData secMediaPlayerData2 = (SecMediaPlayerData) hashMap.get(mediaType);
                        SecMediaHost$$ExternalSyntheticLambda6 secMediaHost$$ExternalSyntheticLambda6 = new SecMediaHost$$ExternalSyntheticLambda6(secMediaHost, mediaType, 1);
                        if (secMediaPlayerData2 != null) {
                            secMediaPlayerData2.getMediaData().entrySet().forEach(secMediaHost$$ExternalSyntheticLambda6);
                        }
                    }
                    SecMediaHost.iteratePlayers(secMediaPlayerData, new SecMediaHost$$ExternalSyntheticLambda15(configuration, 0));
                    int layoutDirection = configuration.getLayoutDirection();
                    if (secMediaHost.mIsRTL != layoutDirection) {
                        secMediaHost.mIsRTL = layoutDirection;
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        if (mediaType.getSupportCarousel()) {
                            CarouselHelper carouselHelper = secMediaHost.mCarouselHelper;
                            carouselHelper.indicator.setLayoutDirection(carouselHelper.isRTLSupplier.getAsInt());
                        }
                        viewPager.setAdapter(new ViewPagerHelper$createPageAdapter$1(mediaType, viewPagerHelper));
                    }
                    int currentItem = viewPager.getCurrentItem();
                    if (z2 && secMediaHost.mIsRTL != 1) {
                        currentItem = (secMediaHost.getMediaPlayerNum(false, mediaType) - 1) - currentItem;
                    }
                    viewPagerHelper.setCurrentPage(currentItem, false, mediaType);
                    secMediaHost.mUpdatePlayers = false;
                    return;
                }
                return;
            default:
                SecMediaHost.AnonymousClass2 anonymousClass2 = (SecMediaHost.AnonymousClass2) this.f$0;
                String str = (String) this.f$1;
                MediaType mediaType2 = (MediaType) obj;
                SecMediaPlayerData secMediaPlayerData3 = (SecMediaPlayerData) obj2;
                anonymousClass2.getClass();
                secMediaPlayerData3.getMediaData().remove(str);
                SecMediaHost secMediaHost2 = SecMediaHost.this;
                secMediaHost2.removePlayer(mediaType2, str);
                int size = secMediaPlayerData3.getMediaData().size();
                if (size == 0) {
                    if (mediaType2.getSupportCarousel()) {
                        secMediaHost2.mCarouselHelper.removeSentinels(secMediaPlayerData3);
                    }
                    secMediaHost2.onMediaVisibilityChanged(Boolean.FALSE);
                } else {
                    ViewPagerHelper viewPagerHelper2 = secMediaHost2.mViewPagerHelper;
                    if (viewPagerHelper2.getCurrentPage(mediaType2) > size) {
                        viewPagerHelper2.setCurrentPage(size, true, mediaType2);
                    }
                    if (mediaType2.getSupportCapsule()) {
                        secMediaHost2.updateCapsule((SecMediaControlPanel) secMediaPlayerData3.getSortedMediaPlayers().get(0), true);
                    }
                    if (mediaType2.getSupportCarousel()) {
                        CarouselHelper carouselHelper2 = secMediaHost2.mCarouselHelper;
                        int color = ((Context) carouselHelper2.contextSupplier.get()).getColor(R.color.media_primary_text);
                        int alphaComponent = ColorUtils.setAlphaComponent(color, 180);
                        SecPageIndicator secPageIndicator = carouselHelper2.indicator;
                        secPageIndicator.mSelectedColor = color;
                        secPageIndicator.mUnselectedColor = alphaComponent;
                        secMediaHost2.mCarouselHelper.updatePageIndicatorNumberPages();
                        secMediaHost2.mCarouselHelper.indicator.reset(viewPagerHelper2.getCurrentPage(mediaType2) - 1);
                    }
                }
                if (mediaType2.getSupportCarousel()) {
                    secMediaHost2.mCarouselHelper.updatePageIndicatorVisibility();
                }
                BarController.AnonymousClass4 anonymousClass4 = secMediaHost2.mMediaBarCallback;
                if (anonymousClass4 != null) {
                    anonymousClass4.onBarHeightChanged();
                    return;
                }
                return;
        }
    }
}
