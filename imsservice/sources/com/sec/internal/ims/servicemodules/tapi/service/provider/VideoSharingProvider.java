package com.sec.internal.ims.servicemodules.tapi.service.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.gsma.services.rcs.sharing.video.VideoSharingLog;
import com.sec.internal.constants.ims.cmstore.CloudMessageProviderContract;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.ims.servicemodules.csh.CshCache;
import com.sec.internal.ims.servicemodules.csh.VideoShare;
import com.sec.internal.ims.servicemodules.csh.event.CshInfo;
import com.sec.internal.ims.servicemodules.csh.event.IContentShare;
import com.sec.internal.ims.servicemodules.csh.event.ICshConstants;
import com.sec.internal.ims.servicemodules.tapi.service.api.ServerApiException;
import com.sec.internal.ims.servicemodules.tapi.service.api.VideoSharingImpl;
import com.sec.internal.ims.servicemodules.tapi.service.api.VideoSharingServiceImpl;
import com.sec.internal.ims.util.PhoneUtils;

/* loaded from: classes.dex */
public class VideoSharingProvider extends ContentProvider {
    public static final String AUTHORITY;
    private static final String LOG_TAG = "VideoSharingProvider";
    private static final int RCSAPI = 1;
    private static final int RCSAPI_ID = 2;
    private static final UriMatcher sUriMatcher;
    private CshCache mCache;
    private final String[] session_columns = {"_id", "sharing_id", ICshConstants.ShareDatabase.KEY_TARGET_CONTACT, "direction", "timestamp", "state", "reason_code", CloudMessageProviderContract.VVMGreetingColumns.DURATION, "video_encoding", "width", "height", "orientation"};

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        String authority = VideoSharingLog.CONTENT_URI.getAuthority();
        AUTHORITY = authority;
        uriMatcher.addURI(authority, "videoshare", 1);
        uriMatcher.addURI(authority, "videoshare/#", 2);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Log.d(LOG_TAG, "VshProvider : onCreate()");
        this.mCache = CshCache.getInstance();
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int i;
        RemoteException e;
        ServerApiException e2;
        int match = sUriMatcher.match(uri);
        MatrixCursor matrixCursor = new MatrixCursor(this.session_columns);
        Log.d(LOG_TAG, "mCache.getSize() = " + this.mCache.getSize());
        if (match == 1) {
            int i2 = 0;
            for (int i3 = 0; i3 < this.mCache.getSize(); i3++) {
                IContentShare sessionAt = this.mCache.getSessionAt(i3);
                if (sessionAt instanceof VideoShare) {
                    CshInfo content = sessionAt.getContent();
                    Log.d(LOG_TAG, content.toString());
                    VideoSharingImpl videoSharingByID = ((VideoSharingServiceImpl) ImsRegistry.getBinder("vsh_tapi")).getVideoSharingByID(String.valueOf(content.shareId));
                    try {
                        i = i2 + 1;
                    } catch (ServerApiException e3) {
                        i = i2;
                        e2 = e3;
                    } catch (RemoteException e4) {
                        i = i2;
                        e = e4;
                    }
                    try {
                        matrixCursor.newRow().add(Integer.valueOf(i2)).add(String.valueOf(content.shareId)).add(PhoneUtils.extractNumberFromUri(content.shareContactUri.toString())).add(videoSharingByID.getDirection()).add(Long.valueOf(videoSharingByID.getTimeStamp())).add(videoSharingByID.getState()).add(videoSharingByID.getVideoEncoding()).add(Integer.valueOf(content.videoWidth)).add(Integer.valueOf(content.videoHeight)).add(Integer.valueOf(videoSharingByID.getOrientation()));
                    } catch (ServerApiException e5) {
                        e2 = e5;
                        e2.printStackTrace();
                        i2 = i;
                    } catch (RemoteException e6) {
                        e = e6;
                        e.printStackTrace();
                        i2 = i;
                    }
                    i2 = i;
                }
            }
        } else if (match == 2) {
            String str3 = uri.getPathSegments().get(1);
            int i4 = 0;
            while (true) {
                if (i4 >= this.mCache.getSize()) {
                    break;
                }
                CshInfo content2 = this.mCache.getSessionAt(i4).getContent();
                if (str3 != null && content2 != null) {
                    VideoSharingImpl videoSharingByID2 = ((VideoSharingServiceImpl) ImsRegistry.getBinder("vsh_tapi")).getVideoSharingByID(String.valueOf(content2.shareId));
                    if (str3.equals(String.valueOf(content2.shareId))) {
                        Log.d(LOG_TAG, content2.toString());
                        try {
                            matrixCursor.newRow().add(0).add(String.valueOf(content2.shareId)).add(PhoneUtils.extractNumberFromUri(content2.shareContactUri.toString())).add(videoSharingByID2.getDirection()).add(Long.valueOf(videoSharingByID2.getTimeStamp())).add(videoSharingByID2.getState()).add(videoSharingByID2.getVideoEncoding()).add(Integer.valueOf(content2.videoWidth)).add(Integer.valueOf(content2.videoHeight)).add(Integer.valueOf(videoSharingByID2.getOrientation()));
                            break;
                        } catch (ServerApiException e7) {
                            e7.printStackTrace();
                        } catch (RemoteException e8) {
                            e8.printStackTrace();
                        }
                    }
                }
                i4++;
            }
        }
        Log.d(LOG_TAG, "cm.getCount() = " + matrixCursor.getCount());
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException();
    }
}
