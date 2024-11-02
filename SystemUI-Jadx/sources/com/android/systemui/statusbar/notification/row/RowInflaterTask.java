package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutFactory;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.systemui.R;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class RowInflaterTask implements InflationTask, AsyncLayoutInflater.OnInflateFinishedListener {
    public boolean mCancelled;
    public NotificationEntry mEntry;
    public Throwable mInflateOrigin;
    public NotificationRowBinderImpl$$ExternalSyntheticLambda0 mListener;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public class RowAsyncLayoutInflater implements AsyncLayoutFactory {
        public final NotificationEntry mEntry;

        public RowAsyncLayoutInflater(NotificationEntry notificationEntry) {
            this.mEntry = notificationEntry;
        }

        @Override // android.view.LayoutInflater.Factory
        public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
            return null;
        }

        @Override // android.view.LayoutInflater.Factory2
        public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            if (str.equals(ExpandableNotificationRow.class.getName())) {
                return new ExpandableNotificationRow(context, attributeSet, this.mEntry);
            }
            return null;
        }
    }

    @Override // com.android.systemui.statusbar.InflationTask
    public final void abort() {
        this.mCancelled = true;
    }

    public final void inflate(Context context, NotificationStackScrollLayout notificationStackScrollLayout, NotificationEntry notificationEntry, NotificationRowBinderImpl$$ExternalSyntheticLambda0 notificationRowBinderImpl$$ExternalSyntheticLambda0) {
        this.mInflateOrigin = new Throwable("inflate requested here");
        this.mListener = notificationRowBinderImpl$$ExternalSyntheticLambda0;
        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(context, new RowAsyncLayoutInflater(notificationEntry));
        this.mEntry = notificationEntry;
        notificationEntry.abortTask();
        notificationEntry.mRunningTask = this;
        try {
            asyncLayoutInflater.inflate(R.layout.status_bar_notification_row, notificationStackScrollLayout, this);
        } catch (Throwable th) {
            Log.e("RowInflaterTask", "Error in AsyncLayoutInflation: " + th);
            throw th;
        }
    }

    @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
    public final void onInflateFinished(View view, ViewGroup viewGroup) {
        if (!this.mCancelled) {
            try {
                this.mEntry.mRunningTask = null;
                this.mListener.onInflationFinished((ExpandableNotificationRow) view);
            } catch (Throwable th) {
                if (this.mInflateOrigin != null) {
                    Log.e("RowInflaterTask", RowInflaterTask$$ExternalSyntheticOutline0.m("Error in inflation finished listener: ", th), this.mInflateOrigin);
                    th.addSuppressed(this.mInflateOrigin);
                }
                throw th;
            }
        }
    }
}
