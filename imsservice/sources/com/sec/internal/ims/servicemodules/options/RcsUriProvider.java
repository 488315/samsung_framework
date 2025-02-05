package com.sec.internal.ims.servicemodules.options;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.sec.ims.extensions.ContextExt;
import com.sec.ims.options.Capabilities;
import com.sec.ims.options.ICapabilityService;
import com.sec.internal.helper.UriUtil;
import com.sec.internal.helper.os.ImsFrameworkState;

/* loaded from: classes.dex */
public class RcsUriProvider extends ContentProvider {
    private static final String AUTHORITY = "com.sec.ims.android.rcsuriprovider";
    private static final String[] ENABLED_PROJECTION;
    private static final String LOG_TAG = "RcsUriProvider";
    static final int N_RCSENABLE_URIS = 1;
    static UriMatcher mMatcher;
    Context mContext = null;
    ICapabilityService mService = null;

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        mMatcher = uriMatcher;
        uriMatcher.addURI(AUTHORITY, "rcsenableduri", 1);
        ENABLED_PROJECTION = new String[]{"_id", "sip_uri", Columns.IS_ENABLED};
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Log.i(LOG_TAG, "onCreate()");
        Context context = getContext();
        this.mContext = context;
        ImsFrameworkState.getInstance(context).registerForFrameworkState(new ImsFrameworkState.FrameworkStateListener() { // from class: com.sec.internal.ims.servicemodules.options.RcsUriProvider$$ExternalSyntheticLambda0
            @Override // com.sec.internal.helper.os.ImsFrameworkState.FrameworkStateListener
            public final void onFwReady() {
                RcsUriProvider.this.lambda$onCreate$0();
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: initCapabilityService, reason: merged with bridge method [inline-methods] */
    public void lambda$onCreate$0() {
        Log.i(LOG_TAG, "Connecting to CapabilityDiscoveryService.");
        Intent intent = new Intent();
        intent.setClassName("com.sec.imsservice", "com.sec.internal.ims.imsservice.CapabilityService");
        ContextExt.bindServiceAsUser(this.mContext, intent, new ServiceConnection() { // from class: com.sec.internal.ims.servicemodules.options.RcsUriProvider.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i(RcsUriProvider.LOG_TAG, "Connected.");
                RcsUriProvider.this.mService = ICapabilityService.Stub.asInterface(iBinder);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(RcsUriProvider.LOG_TAG, "Disconnected.");
                RcsUriProvider.this.mService = null;
            }
        }, 1, ContextExt.CURRENT_OR_SELF);
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Capabilities[] allCapabilities;
        int simSlotFromUri = UriUtil.getSimSlotFromUri(uri);
        if (mMatcher.match(uri) == 1) {
            Log.i(LOG_TAG, "N_RCSENABLE_URIS | Operation for uri: ".concat(uri.toString()));
            MatrixCursor matrixCursor = new MatrixCursor(ENABLED_PROJECTION);
            ICapabilityService iCapabilityService = this.mService;
            if (iCapabilityService == null) {
                Log.e(LOG_TAG, "Binder is not initialized! Returning empty response");
                return matrixCursor;
            }
            try {
                allCapabilities = iCapabilityService.getAllCapabilities(simSlotFromUri);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (allCapabilities == null) {
                return matrixCursor;
            }
            if (allCapabilities.length == 0) {
                Log.i(LOG_TAG, "N_RCSENABLE_URIS: not found.");
                return matrixCursor;
            }
            int length = allCapabilities.length;
            int i = 0;
            int i2 = 1;
            while (i < length) {
                int i3 = i2 + 1;
                matrixCursor.addRow(new Object[]{Integer.valueOf(i2), allCapabilities[i].getUri().toString(), 1});
                i++;
                i2 = i3;
            }
            return matrixCursor;
        }
        Log.e(LOG_TAG, "UNDEFINED CATEGORY! | Operation for uri: ".concat(uri.toString()));
        throw new UnsupportedOperationException("Operation not supported for uri: ".concat(uri.toString()));
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Operation not supported for uri:".concat(uri.toString()));
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Operation not supported for uri:".concat(uri.toString()));
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Operation not supported for uri:".concat(uri.toString()));
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Operation not supported for uri:".concat(uri.toString()));
    }
}
