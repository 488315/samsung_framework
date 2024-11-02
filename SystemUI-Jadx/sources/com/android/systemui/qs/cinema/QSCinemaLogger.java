package com.android.systemui.qs.cinema;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.logging.PanelScreenShotLogger;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class QSCinemaLogger {
    public final QSCinemaProvider mProvider;
    public ArrayList mTmpLog = new ArrayList();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class ScreenShotLogProvider implements PanelScreenShotLogger.LogProvider {
        public /* synthetic */ ScreenShotLogProvider(QSCinemaLogger qSCinemaLogger, int i) {
            this();
        }

        @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
        public final ArrayList gatherState() {
            QSCinemaLogger qSCinemaLogger = QSCinemaLogger.this;
            qSCinemaLogger.getClass();
            qSCinemaLogger.mTmpLog = new ArrayList(Arrays.asList("QSCinemaLogger ## Quickpanel View State of Screen Capture ########"));
            View view = qSCinemaLogger.mProvider.mQs.getView();
            if (view != null && (view instanceof ViewGroup)) {
                qSCinemaLogger.visitLayoutTreeToAssembleLogLine((ViewGroup) view, 0);
            }
            return qSCinemaLogger.mTmpLog;
        }

        private ScreenShotLogProvider() {
        }
    }

    public QSCinemaLogger(QSCinemaProvider qSCinemaProvider) {
        this.mProvider = qSCinemaProvider;
    }

    public final void visitLayoutTreeToAssembleLogLine(ViewGroup viewGroup, int i) {
        for (int i2 = 0; i2 < viewGroup.getChildCount() && i2 <= 50 && i <= 20; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != null) {
                ArrayList arrayList = this.mTmpLog;
                StringBuilder sb = new StringBuilder("QSCinemaLogger ");
                for (int i3 = 0; i3 < i; i3++) {
                    sb.append("  | ");
                }
                sb.append(" idx=" + i2);
                sb.append(":::" + childAt.toString());
                sb.append(", w:" + childAt.getWidth());
                sb.append(", mw:" + childAt.getMeasuredWidth());
                sb.append(", x:" + childAt.getX());
                sb.append(", px:" + childAt.getPivotX());
                sb.append(", tx:" + childAt.getTranslationX());
                sb.append(", lr:" + childAt.isLayoutRequested());
                sb.append(", clickable:" + childAt.isClickable());
                sb.append(", focusable:" + childAt.isFocusable());
                sb.append(", a:" + childAt.getAlpha());
                arrayList.add(sb.toString());
                if (childAt instanceof ViewGroup) {
                    visitLayoutTreeToAssembleLogLine((ViewGroup) childAt, i + 1);
                }
            }
        }
    }
}
