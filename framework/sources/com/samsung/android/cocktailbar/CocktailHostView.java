package com.samsung.android.cocktailbar;

import android.content.Context;
import android.os.Process;
import android.os.UserHandle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RemoteViewsAdapter;

/* loaded from: classes5.dex */
public class CocktailHostView extends FrameLayout {
    static final String TAG = "CocktailHostView";
    private Cocktail mCocktail;
    private int mCocktailId;
    private UserHandle mUser;

    public CocktailHostView(Context context, Cocktail cocktail) {
        super(context);
        this.mUser = Process.myUserHandle();
        setIsRootNamespace(true);
        setCocktail(cocktail);
    }

    public void setUserId(int userId) {
        this.mUser = new UserHandle(userId);
    }

    public int getCocktailId() {
        return this.mCocktailId;
    }

    public Cocktail getCocktail() {
        return this.mCocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.mCocktailId = 0;
        this.mCocktail = cocktail;
        if (cocktail != null) {
            this.mCocktailId = cocktail.getCocktailId();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void viewDataChanged(int viewId) {
        View v = findViewById(viewId);
        if (v != null && (v instanceof AdapterView)) {
            AdapterView adapterView = (AdapterView) v;
            Adapter adapter = adapterView.getAdapter();
            if (adapter instanceof BaseAdapter) {
                BaseAdapter baseAdapter = (BaseAdapter) adapter;
                baseAdapter.notifyDataSetChanged();
            } else if (adapter == null && (adapterView instanceof RemoteViewsAdapter.RemoteAdapterConnectionCallback)) {
                ((RemoteViewsAdapter.RemoteAdapterConnectionCallback) adapterView).deferNotifyDataSetChanged();
            }
        }
    }
}
