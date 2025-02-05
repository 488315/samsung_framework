package com.sec.internal.constants.ims.servicemodules.volte2;

/* loaded from: classes.dex */
public class RrcConnectionEvent {
    RrcEvent mEvent;

    public enum RrcEvent {
        REJECTED,
        TIMER_EXPIRED
    }

    public RrcConnectionEvent(RrcEvent rrcEvent) {
        this.mEvent = rrcEvent;
    }

    public RrcEvent getEvent() {
        return this.mEvent;
    }
}
