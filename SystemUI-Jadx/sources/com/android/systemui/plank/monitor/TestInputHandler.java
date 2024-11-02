package com.android.systemui.plank.monitor;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import com.android.systemui.plank.monitor.EventFactory;
import com.android.systemui.plank.monitor.TestInputMonitor;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TestInputHandler implements TestInputMonitor.EventHandler {
    public final Point displaySize;
    public final Context mContext;
    public final List mEventHistory;
    public long mLastEventTime;
    public long mStartEventTime;
    public int mStartX;
    public int mStartY;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TestInputHandler(Context context) {
        this.mContext = context;
        Point point = new Point();
        this.displaySize = point;
        this.mEventHistory = new ArrayList();
        SemWindowManager.getInstance().getInitialDisplaySize(point);
    }

    public final void addEvent(EventData eventData) {
        synchronized (this.mEventHistory) {
            ((ArrayList) this.mEventHistory).add(eventData);
        }
    }

    public final void onEventHandler(MotionEvent motionEvent) {
        int eventTime;
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            boolean z = true;
            if (action == 1) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                int abs = Math.abs(this.mStartX - rawX);
                int abs2 = Math.abs(rawY - this.mStartY);
                if (abs <= 10 && abs2 <= 10) {
                    if (this.mContext.getResources().getConfiguration().orientation != 2) {
                        z = false;
                    }
                    Point point = this.displaySize;
                    if ((!z && (this.mStartX > point.x || this.mStartY > point.y)) || (z && (this.mStartX > point.y || this.mStartY > point.x))) {
                        EventFactory.Companion companion = EventFactory.Companion;
                        int i = this.mStartX;
                        int i2 = this.mStartY;
                        companion.getClass();
                        addEvent(new EventData(EventType.SWIPE, i, i2, i, i2, 1, 0));
                    } else {
                        EventFactory.Companion companion2 = EventFactory.Companion;
                        int i3 = this.mStartX;
                        int i4 = this.mStartY;
                        companion2.getClass();
                        addEvent(new EventData(EventType.TOUCH, i3, i4, 0, 0, 0, 0));
                    }
                } else {
                    int eventTime2 = (int) ((motionEvent.getEventTime() - this.mStartEventTime) / 25);
                    EventFactory.Companion companion3 = EventFactory.Companion;
                    int i5 = this.mStartX;
                    int i6 = this.mStartY;
                    companion3.getClass();
                    addEvent(new EventData(EventType.SWIPE, i5, i6, rawX, rawY, eventTime2, 0));
                }
                this.mLastEventTime = motionEvent.getEventTime();
                return;
            }
            return;
        }
        this.mStartX = (int) motionEvent.getRawX();
        this.mStartY = (int) motionEvent.getRawY();
        if (this.mLastEventTime > 0 && (eventTime = (int) (motionEvent.getEventTime() - this.mLastEventTime)) >= 10) {
            EventFactory.Companion.getClass();
            addEvent(new EventData(EventType.SLEEP, 0, 0, 0, 0, 0, eventTime));
        }
        this.mStartEventTime = motionEvent.getEventTime();
    }
}
