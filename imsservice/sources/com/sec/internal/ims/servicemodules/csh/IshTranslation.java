package com.sec.internal.ims.servicemodules.csh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.sec.ims.extensions.ContextExt;
import com.sec.ims.util.ImsUri;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.helper.os.IntentUtil;
import com.sec.internal.helper.os.TelephonyUtilsWrapper;
import com.sec.internal.ims.servicemodules.csh.IshIntents;
import com.sec.internal.ims.servicemodules.csh.event.ICshConstants;

/* loaded from: classes.dex */
public class IshTranslation {
    private static final String LOG_TAG = "IshTranslation";
    private static final String mCranePackage = "com.samsung.crane";
    private static final IntentFilter sIntentFilter;
    private Context mContext;
    private BroadcastReceiver mIntentReceiver;
    private ImageShareModule mServiceModule;

    static {
        IntentFilter intentFilter = new IntentFilter();
        sIntentFilter = intentFilter;
        intentFilter.addAction(IshIntents.IshIntent.ACTION_SHARE_CONTENT);
        intentFilter.addAction(IshIntents.IshIntent.ACTION_SHARE_ACCEPT);
        intentFilter.addAction(IshIntents.IshIntent.ACTION_SHARE_CANCEL);
    }

    public IshTranslation(Context context, ImageShareModule imageShareModule) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.sec.internal.ims.servicemodules.csh.IshTranslation.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Log.i(IshTranslation.LOG_TAG, "Received intent: " + action);
                if (TextUtils.equals(action, IshIntents.IshIntent.ACTION_SHARE_CONTENT)) {
                    IshTranslation.this.requestNewShare(intent);
                } else if (TextUtils.equals(action, IshIntents.IshIntent.ACTION_SHARE_ACCEPT)) {
                    IshTranslation.this.requestAcceptShare(intent);
                } else if (TextUtils.equals(action, IshIntents.IshIntent.ACTION_SHARE_CANCEL)) {
                    IshTranslation.this.requestCancelShare(intent);
                }
            }
        };
        this.mIntentReceiver = broadcastReceiver;
        this.mContext = context;
        this.mServiceModule = imageShareModule;
        context.registerReceiver(broadcastReceiver, sIntentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestNewShare(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.d(LOG_TAG, "requestNewShare: extras " + extras);
        this.mServiceModule.createShare(ImsUri.parse(((Uri) extras.getParcelable(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI)).toString()), extras.getString(ICshConstants.ExtraInformation.EXTRA_FILE_PATH));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestAcceptShare(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.d(LOG_TAG, "requestAcceptShare: extras " + extras);
        this.mServiceModule.acceptShare(extras.getLong(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, -1L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCancelShare(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.d(LOG_TAG, "requestCancelShare: extras " + extras);
        this.mServiceModule.cancelShare(extras.getLong(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, -1L));
    }

    public void broadcastOutgoingSucceeded(long j, ImsUri imsUri, String str) {
        Intent intent = new Intent();
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_CREATED);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, j);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_FILE_PATH, str);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI, Uri.parse(imsUri.toString()));
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastIncomming(long j, ImsUri imsUri, String str, long j2) {
        Intent intent = new Intent();
        intent.setClassName(mCranePackage, "com.samsung.crane.callcomposer.receiver.SessionInvitationReceiver");
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_INCOMING);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, j);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_TYPE, 1);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_FILE_PATH, str);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI, Uri.parse(imsUri.toString()));
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_BYTES_TOTAL, Long.valueOf(j2).intValue());
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastProgress(long j, ImsUri imsUri, long j2, long j3) {
        Intent intent = new Intent();
        intent.setClassName(mCranePackage, "com.samsung.crane.receiver.CSNotificationReceiver");
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_PROGRESS);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, j);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI, Uri.parse(imsUri.toString()));
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_BYTES_DONE, Long.valueOf(j2).intValue());
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_BYTES_TOTAL, Long.valueOf(j3).intValue());
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastCompleted(long j, ImsUri imsUri) {
        Intent intent = new Intent();
        intent.setClassName(mCranePackage, "com.samsung.crane.receiver.CSNotificationReceiver");
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_COMPLETED);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, j);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI, Uri.parse(imsUri.toString()));
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastCanceled(long j, ImsUri imsUri, int i, int i2) {
        Intent intent = new Intent();
        intent.setClassName(mCranePackage, "com.samsung.crane.callcomposer.receiver.SessionInvitationReceiver");
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_CANCELED);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, j);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI, Uri.parse(imsUri.toString()));
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_REASON, i2);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_DIRECTION, i);
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastConnected(long j, ImsUri imsUri) {
        Intent intent = new Intent();
        intent.setClassName(mCranePackage, "com.samsung.crane.receiver.CSNotificationReceiver");
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_CONNECTED);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, j);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI, Uri.parse(imsUri.toString()));
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastLimitExceeded(long j, ImsUri imsUri) {
        Intent intent = new Intent();
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_LIMIT_EXCEEDED);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_SHARE_ID, j);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_CONTACT_URI, Uri.parse(imsUri.toString()));
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastCommunicationError() {
        Intent intent = new Intent();
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_COMMUNICATION_ERROR);
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastInvalidDataPath(String str) {
        Intent intent = new Intent();
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_FILE_PATH_ERROR);
        intent.putExtra(ICshConstants.ExtraInformation.EXTRA_FILE_PATH, str);
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastServiceReady() {
        Intent intent = new Intent();
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_SERVICE_READY);
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastServiceNotReady() {
        Intent intent = new Intent();
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_SHARE_SERVICE_NOT_READY);
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastCshServiceNotReady() {
        Intent intent = new Intent();
        intent.addCategory(IshIntents.IshNotificationIntent.CATEGORY_NOTIFICATION);
        intent.setAction(IshIntents.IshNotificationIntent.NOTIFICATION_CSH_SERVICE_NOT_READY);
        broadcastIntent(intent, this.mServiceModule.mActiveCallPhoneId);
    }

    public void broadcastSystemRefresh(String str) {
        MediaScannerConnection.scanFile(this.mContext, new String[]{"file://" + str}, null, null);
    }

    public void broadcastIntent(Intent intent) throws NullPointerException {
        Log.d(LOG_TAG, intent.toString() + intent.getExtras());
        IntentUtil.sendBroadcast(this.mContext, intent, ContextExt.CURRENT_OR_SELF);
    }

    private void broadcastIntent(Intent intent, int i) {
        Log.d(LOG_TAG, intent.toString() + intent.getExtras());
        UserHandle subscriptionUserHandle = TelephonyUtilsWrapper.getSubscriptionUserHandle(this.mContext, SimUtil.getSubId(i));
        if (subscriptionUserHandle != null) {
            IntentUtil.sendBroadcast(this.mContext, intent, subscriptionUserHandle);
        } else {
            IntentUtil.sendBroadcast(this.mContext, intent, ContextExt.CURRENT_OR_SELF);
        }
    }

    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        Log.i(LOG_TAG, "Received intent: " + action);
        if (TextUtils.equals(action, IshIntents.IshIntent.ACTION_SHARE_CONTENT)) {
            requestNewShare(intent);
        } else if (TextUtils.equals(action, IshIntents.IshIntent.ACTION_SHARE_ACCEPT)) {
            requestAcceptShare(intent);
        } else if (TextUtils.equals(action, IshIntents.IshIntent.ACTION_SHARE_CANCEL)) {
            requestCancelShare(intent);
        }
    }
}
