package com.android.systemui.qs.customize;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArraySet;
import android.widget.Button;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SecTileQueryHelper {
    public final Executor mBgExecutor;
    public final Context mContext;
    public boolean mFinished;
    public TileStateListener mListener;
    public final Executor mMainExecutor;
    public boolean mTileQueryFinished;
    public final UserTracker mUserTracker;
    public final ArrayList mTiles = new ArrayList();
    public final ArraySet mSpecs = new ArraySet();
    public final SecQSPanelResourcePicker mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class TileCollector implements QSTile.Callback {
        public final boolean mIsTopEdit;
        public final QSHost mQSHost;
        public final List mQSTileList = new ArrayList();

        public TileCollector(List<QSTile> list, QSHost qSHost, boolean z) {
            this.mIsTopEdit = z;
            Iterator<QSTile> it = list.iterator();
            while (it.hasNext()) {
                ((ArrayList) this.mQSTileList).add(new TilePair(it.next(), 0));
            }
            this.mQSHost = qSHost;
            if (list.isEmpty()) {
                SecTileQueryHelper.this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.customize.SecTileQueryHelper$TileCollector$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecTileQueryHelper.TileCollector tileCollector = SecTileQueryHelper.TileCollector.this;
                        SecTileQueryHelper secTileQueryHelper = SecTileQueryHelper.this;
                        secTileQueryHelper.getClass();
                        secTileQueryHelper.mMainExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, false, new ArrayList(secTileQueryHelper.mTiles)));
                        secTileQueryHelper.mBgExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, tileCollector.mQSHost, tileCollector.mIsTopEdit));
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Callback
        public final void onStateChanged(QSTile.State state) {
            boolean z;
            ArrayList arrayList = (ArrayList) this.mQSTileList;
            Iterator it = arrayList.iterator();
            boolean z2 = true;
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                }
                TilePair tilePair = (TilePair) it.next();
                if (!tilePair.mReady) {
                    QSTile qSTile = tilePair.mTile;
                    if (qSTile.isTileReady()) {
                        qSTile.removeCallback(this);
                        qSTile.setListening(this, false);
                        tilePair.mReady = true;
                    }
                }
                if (!tilePair.mReady) {
                    z2 = false;
                }
            }
            if (z2) {
                Iterator it2 = arrayList.iterator();
                while (true) {
                    boolean hasNext = it2.hasNext();
                    SecTileQueryHelper secTileQueryHelper = SecTileQueryHelper.this;
                    if (hasNext) {
                        QSTile qSTile2 = ((TilePair) it2.next()).mTile;
                        QSTile.State copy = qSTile2.getState().copy();
                        copy.label = qSTile2.getTileLabel();
                        qSTile2.destroy();
                        secTileQueryHelper.addTile(qSTile2.getTileSpec(), null, copy, true);
                    } else {
                        secTileQueryHelper.getClass();
                        secTileQueryHelper.mMainExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, z, new ArrayList(secTileQueryHelper.mTiles)));
                        secTileQueryHelper.mBgExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, this.mQSHost, this.mIsTopEdit));
                        return;
                    }
                }
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public class TileInfo {
        public boolean isActive;
        public String spec;
        public QSTile.State state;

        public TileInfo() {
        }

        public TileInfo(String str, QSTile.State state, boolean z) {
            this.spec = str;
            this.state = state;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class TilePair {
        public boolean mReady;
        public final QSTile mTile;

        public /* synthetic */ TilePair(QSTile qSTile, int i) {
            this(qSTile);
        }

        private TilePair(QSTile qSTile) {
            this.mReady = false;
            this.mTile = qSTile;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface TileStateListener {
    }

    public SecTileQueryHelper(Context context, UserTracker userTracker, Executor executor, Executor executor2) {
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mUserTracker = userTracker;
    }

    public final void addTile(String str, CharSequence charSequence, QSTile.State state, boolean z) {
        ArraySet arraySet = this.mSpecs;
        if (arraySet.contains(str)) {
            return;
        }
        state.dualTarget = false;
        state.expandedAccessibilityClassName = Button.class.getName();
        if (z || TextUtils.equals(state.label, charSequence)) {
            charSequence = null;
        }
        state.secondaryLabel = charSequence;
        this.mTiles.add(new TileInfo(str, state, z));
        arraySet.add(str);
    }
}
