package com.android.systemui;

import android.service.dreams.DreamService;
import com.android.systemui.DessertCaseView;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class DessertCaseDream extends DreamService {
    public DessertCaseView.RescalingContainer mContainer;
    public DessertCaseView mView;

    @Override // android.service.dreams.DreamService, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        this.mView = new DessertCaseView(this);
        DessertCaseView.RescalingContainer rescalingContainer = new DessertCaseView.RescalingContainer(this);
        this.mContainer = rescalingContainer;
        DessertCaseView dessertCaseView = this.mView;
        rescalingContainer.addView(dessertCaseView);
        rescalingContainer.mView = dessertCaseView;
        setContentView(this.mContainer);
    }

    @Override // android.service.dreams.DreamService
    public final void onDreamingStarted() {
        super.onDreamingStarted();
        this.mView.postDelayed(new Runnable() { // from class: com.android.systemui.DessertCaseDream.1
            @Override // java.lang.Runnable
            public final void run() {
                DessertCaseDream.this.mView.start();
            }
        }, 1000L);
    }

    @Override // android.service.dreams.DreamService
    public final void onDreamingStopped() {
        super.onDreamingStopped();
        DessertCaseView dessertCaseView = this.mView;
        dessertCaseView.mStarted = false;
        dessertCaseView.mHandler.removeCallbacks(dessertCaseView.mJuggle);
    }
}
