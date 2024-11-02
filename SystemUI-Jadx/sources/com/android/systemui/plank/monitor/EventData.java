package com.android.systemui.plank.monitor;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class EventData {
    public final int endX;
    public final int endY;
    public final EventType eventType;
    public final int interval;
    public final int startX;
    public final int startY;
    public final int steps;

    public EventData(EventType eventType, int i, int i2, int i3, int i4, int i5, int i6) {
        this.eventType = eventType;
        this.startX = i;
        this.startY = i2;
        this.endX = i3;
        this.endY = i4;
        this.steps = i5;
        this.interval = i6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EventData)) {
            return false;
        }
        EventData eventData = (EventData) obj;
        if (this.eventType == eventData.eventType && this.startX == eventData.startX && this.startY == eventData.startY && this.endX == eventData.endX && this.endY == eventData.endY && this.steps == eventData.steps && this.interval == eventData.interval) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.interval) + AppInfoViewData$$ExternalSyntheticOutline0.m(this.steps, AppInfoViewData$$ExternalSyntheticOutline0.m(this.endY, AppInfoViewData$$ExternalSyntheticOutline0.m(this.endX, AppInfoViewData$$ExternalSyntheticOutline0.m(this.startY, AppInfoViewData$$ExternalSyntheticOutline0.m(this.startX, this.eventType.hashCode() * 31, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("EventData(eventType=");
        sb.append(this.eventType);
        sb.append(", startX=");
        sb.append(this.startX);
        sb.append(", startY=");
        sb.append(this.startY);
        sb.append(", endX=");
        sb.append(this.endX);
        sb.append(", endY=");
        sb.append(this.endY);
        sb.append(", steps=");
        sb.append(this.steps);
        sb.append(", interval=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m(sb, this.interval, ")");
    }
}
