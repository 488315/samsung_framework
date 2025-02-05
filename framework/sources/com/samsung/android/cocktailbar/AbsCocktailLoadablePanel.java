package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/* loaded from: classes5.dex */
public abstract class AbsCocktailLoadablePanel {
    public static final String LOADABLE_CONTENT_CLASS = "content";
    public static final String PACKAGE_NAME = "package";
    public static final int PANEL_STATE_HIDE = 1;
    public static final int PANEL_STATE_VISIBLE = 0;
    private static final String TAG = AbsCocktailLoadablePanel.class.getSimpleName();
    protected Context mCocktailContext;
    protected Context mContext;
    private CocktailLoadablePanelListener mListener;
    private OnCocktailClickHandler mOnCocktailClickHandler;

    public interface CocktailLoadablePanelListener {
        void sendOptions(Bundle bundle);
    }

    public abstract View getView();

    public abstract void onClosePanel();

    public static class OnCocktailClickHandler {
        public boolean onClickHandler(View view, PendingIntent pendingIntent) {
            try {
                Context context = view.getContext();
                context.startIntentSender(pendingIntent.getIntentSender(), new Intent(), 268435456, 268435456, 0);
                return true;
            } catch (IntentSender.SendIntentException e) {
                Log.e(AbsCocktailLoadablePanel.TAG, "Cannot send pending intent: ", e);
                return false;
            } catch (Exception e2) {
                Log.e(AbsCocktailLoadablePanel.TAG, "Cannot send pending intent due to unknown exception: ", e2);
                return false;
            }
        }
    }

    public AbsCocktailLoadablePanel(Context context) {
        this.mContext = null;
        this.mCocktailContext = null;
        this.mListener = null;
        this.mOnCocktailClickHandler = null;
        this.mContext = context;
    }

    public AbsCocktailLoadablePanel(Context appContext, Context cocktailContext) {
        this.mContext = null;
        this.mCocktailContext = null;
        this.mListener = null;
        this.mOnCocktailClickHandler = null;
        this.mContext = appContext;
        this.mCocktailContext = cocktailContext;
    }

    public void setListener(CocktailLoadablePanelListener listener) {
        this.mListener = listener;
    }

    public CocktailLoadablePanelListener getListener() {
        return this.mListener;
    }

    public void setOnCocktailClickHandler(OnCocktailClickHandler onCocktailClickHandler) {
        this.mOnCocktailClickHandler = onCocktailClickHandler;
    }

    public OnCocktailClickHandler getOnCocktailClickHander() {
        return this.mOnCocktailClickHandler;
    }

    public void onPanelVisibilityChanged(int visibility) {
    }

    public void setData(Bundle bundle) {
    }

    @Deprecated
    public void onOrientationChanged(int orientation) {
    }

    @Deprecated
    public void onChangedDisplayPolicy(int newDisplayPolicy) {
    }

    @Deprecated
    public void onChangedReversedView(boolean isReversedViewMode) {
    }
}
