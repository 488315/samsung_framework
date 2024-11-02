package androidx.recyclerview.widget;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class BatchingListUpdateCallback implements ListUpdateCallback {
    public final ListUpdateCallback mWrapped;
    public int mLastEventType = 0;
    public int mLastEventPosition = -1;
    public int mLastEventCount = -1;
    public Object mLastEventPayload = null;

    public BatchingListUpdateCallback(ListUpdateCallback listUpdateCallback) {
        this.mWrapped = listUpdateCallback;
    }

    public final void dispatchLastEvent() {
        int i = this.mLastEventType;
        if (i == 0) {
            return;
        }
        ListUpdateCallback listUpdateCallback = this.mWrapped;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    listUpdateCallback.onChanged(this.mLastEventPosition, this.mLastEventCount, this.mLastEventPayload);
                }
            } else {
                listUpdateCallback.onRemoved(this.mLastEventPosition, this.mLastEventCount);
            }
        } else {
            listUpdateCallback.onInserted(this.mLastEventPosition, this.mLastEventCount);
        }
        this.mLastEventPayload = null;
        this.mLastEventType = 0;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onChanged(int i, int i2, Object obj) {
        int i3;
        if (this.mLastEventType == 3) {
            int i4 = this.mLastEventPosition;
            int i5 = this.mLastEventCount;
            if (i <= i4 + i5 && (i3 = i + i2) >= i4 && this.mLastEventPayload == obj) {
                this.mLastEventPosition = Math.min(i, i4);
                this.mLastEventCount = Math.max(i5 + i4, i3) - this.mLastEventPosition;
                return;
            }
        }
        dispatchLastEvent();
        this.mLastEventPosition = i;
        this.mLastEventCount = i2;
        this.mLastEventPayload = obj;
        this.mLastEventType = 3;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onInserted(int i, int i2) {
        int i3;
        if (this.mLastEventType == 1 && i >= (i3 = this.mLastEventPosition)) {
            int i4 = this.mLastEventCount;
            if (i <= i3 + i4) {
                this.mLastEventCount = i4 + i2;
                this.mLastEventPosition = Math.min(i, i3);
                return;
            }
        }
        dispatchLastEvent();
        this.mLastEventPosition = i;
        this.mLastEventCount = i2;
        this.mLastEventType = 1;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onMoved(int i, int i2) {
        dispatchLastEvent();
        this.mWrapped.onMoved(i, i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public final void onRemoved(int i, int i2) {
        int i3;
        if (this.mLastEventType == 2 && (i3 = this.mLastEventPosition) >= i && i3 <= i + i2) {
            this.mLastEventCount += i2;
            this.mLastEventPosition = i;
        } else {
            dispatchLastEvent();
            this.mLastEventPosition = i;
            this.mLastEventCount = i2;
            this.mLastEventType = 2;
        }
    }
}
