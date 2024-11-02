package com.samsung.android.nexus.base.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLES20;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.samsung.android.nexus.base.DrawRequester;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.context.NexusContext;
import com.samsung.android.nexus.base.utils.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class LayerContainer extends BaseLayer {
    public final List mEffectLayer = new ArrayList();
    public boolean mIsReadyToCreate = false;

    public LayerContainer(Context context, Object obj) {
        Log.i("LayerContainer", "LayerContainer() : create LayerContainer");
        setNexusContext(new NexusContext(context));
        getNexusContext().mAnimatorCore.mDrawRequester = new DrawRequester(obj);
    }

    public final void addLayer(BaseLayer baseLayer) {
        baseLayer.setNexusContext(getNexusContext());
        if (this.mIsReadyToCreate) {
            baseLayer.onCreate();
        }
        ((ArrayList) this.mEffectLayer).add(baseLayer);
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void draw() {
        GLES20.glClear(16384);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        onDraw();
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onCreate() {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onCreate();
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDestroy() {
        Log.i("LayerContainer", "destroy()");
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onDestroy();
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDraw() {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onDraw();
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onSizeChanged(int i, int i2) {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onSizeChanged(i, i2);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onTapEvent(int i, int i2, long j) {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onTapEvent(i, i2, j);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onTouchCancelEvent(int i, int i2, long j) {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onTouchCancelEvent(i, i2, j);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onTouchEvent(int i, int i2, long j) {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onTouchEvent(i, i2, j);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onVisibilityChanged(Boolean bool) {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onVisibilityChanged(bool);
        }
    }

    public final void removeAllLayers() {
        setRenderMode(0);
        List list = this.mEffectLayer;
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onDestroy();
        }
        ((ArrayList) list).clear();
    }

    public final void setRenderMode(int i) {
        Log.i("LayerContainer", "setRenderMode() : " + i);
        NexusContext nexusContext = getNexusContext();
        nexusContext.getClass();
        Log.i("NexusContext", "setRenderMode() : " + i);
        AnimatorCore animatorCore = nexusContext.mAnimatorCore;
        animatorCore.getClass();
        Log.i("AnimatorCore", "setRenderMode() : " + i);
        animatorCore.mRenderMode = i;
        animatorCore.startAnimator();
    }

    public final void setSize(int i, int i2) {
        getNexusContext().mWidth = i;
        getNexusContext().mHeight = i2;
        if (!this.mIsReadyToCreate) {
            onCreate();
        }
        onSizeChanged(i, i2);
        this.mIsReadyToCreate = true;
    }

    public final void tapCommand(int i, int i2, int i3, long j) {
        StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m("topCommand() : ", i, " , ", i2, " , ");
        m.append(i3);
        m.append(" , ");
        m.append(j);
        Log.i("LayerContainer", m.toString());
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    onTapEvent(i2, i3, j);
                    return;
                }
                return;
            }
            onTouchCancelEvent(i2, i3, j);
            return;
        }
        onTouchEvent(i2, i3, j);
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDraw(Canvas canvas) {
        Iterator it = ((ArrayList) this.mEffectLayer).iterator();
        while (it.hasNext()) {
            ((BaseLayer) it.next()).onDraw(canvas);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void draw(Canvas canvas) {
        onDraw(canvas);
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onLayerParamsChanged(NexusLayerParams nexusLayerParams) {
    }
}
