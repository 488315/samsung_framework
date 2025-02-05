package android.app;

import android.annotation.SystemApi;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.Downloads;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public class DownloadManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE";

    @SystemApi
    public static final String ACTION_DOWNLOAD_COMPLETED = "android.intent.action.DOWNLOAD_COMPLETED";
    public static final String ACTION_NOTIFICATION_CLICKED = "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";
    public static final String ACTION_VIEW_DOWNLOADS = "android.intent.action.VIEW_DOWNLOADS";
    public static final String ACTION_VIEW_SEC_DOWNLOADS = "android.intent.action.VIEW_SEC_DOWNLOADS";
    public static final String COLUMN_ALLOW_WRITE = "allow_write";
    public static final String COLUMN_DD_CONTENT_SIZE = "dd_contentSize";
    public static final String COLUMN_DD_FILE_DESCRIPTION = "dd_description";
    public static final String COLUMN_DD_FILE_NAME = "dd_fileName";
    public static final String COLUMN_DD_OBJ_URL = "dd_objUrl";
    public static final String COLUMN_DD_PRIMARY_MIMETYPE = "dd_primaryMimeType";
    public static final String COLUMN_DD_VENDOR_NAME = "dd_vendor";
    public static final String COLUMN_DD_VERSION_NUMBER = "dd_majorVersion";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_DOWNLOAD_METHOD = "downloadmethod";
    public static final String COLUMN_DOWNLOAD_STATE = "state";
    public static final String COLUMN_FILE_NAME_HINT = "hint";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEDIAPROVIDER_URI = "mediaprovider_uri";
    public static final String COLUMN_MEDIASTORE_URI = "mediastore_uri";
    public static final String COLUMN_NOTIFICATION_PACKAGE = "notificationpackage";
    public static final String COLUMN_RANGE_END = "range_end";
    public static final String COLUMN_RANGE_FIRSTCHUNK_END = "range_first_end";
    public static final String COLUMN_RANGE_START = "range_start";
    public static final String COLUMN_REASON = "reason";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URI = "uri";
    public static final int ERROR_BLOCKED = 1010;
    public static final int ERROR_CANNOT_RESUME = 1008;
    public static final int ERROR_DEVICE_NOT_FOUND = 1007;
    public static final int ERROR_FILE_ALREADY_EXISTS = 1009;
    public static final int ERROR_FILE_ERROR = 1001;
    public static final int ERROR_HTTP_DATA_ERROR = 1004;
    public static final int ERROR_INSUFFICIENT_SPACE = 1006;
    public static final int ERROR_TOO_MANY_REDIRECTS = 1005;
    public static final int ERROR_UNHANDLED_HTTP_CODE = 1002;
    public static final int ERROR_UNKNOWN = 1000;
    public static final String EXTRA_DOWNLOAD_ID = "extra_download_id";
    public static final String EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS = "extra_click_download_ids";
    public static final String INTENT_EXTRAS_SORT_BY_SIZE = "android.app.DownloadManager.extra_sortBySize";
    private static final String NON_DOWNLOADMANAGER_DOWNLOAD = "non-dwnldmngr-download-dont-retry2download";
    public static final int PAUSED_BY_APP = 5;
    public static final int PAUSED_QUEUED_FOR_WIFI = 3;
    public static final int PAUSED_UNKNOWN = 4;
    public static final int PAUSED_WAITING_FOR_NETWORK = 2;
    public static final int PAUSED_WAITING_TO_RETRY = 1;
    private static final String SBROWSER_PACKAGE_NAME = "com.sec.android.app.sbrowser";
    public static final String SEM_COLUMN_DD_CONTENT_SIZE = "dd_contentSize";
    public static final String SEM_COLUMN_DD_FILE_DESCRIPTION = "dd_description";
    public static final String SEM_COLUMN_DD_FILE_NAME = "dd_fileName";
    public static final String SEM_COLUMN_DD_PRIMARY_MIMETYPE = "dd_primaryMimeType";
    public static final String SEM_COLUMN_DD_VENDOR_NAME = "dd_vendor";
    public static final String SEM_COLUMN_DD_VERSION_NUMBER = "dd_majorVersion";
    public static final int SEM_STATUS_OMA_PENDING = 65536;
    public static final int STATUS_FAILED = 16;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_SUCCESSFUL = 8;
    private boolean mAccessFilename;
    private final String mPackageName;
    private final ContentResolver mResolver;
    public static final String COLUMN_MEDIA_TYPE = "media_type";
    public static final String COLUMN_TOTAL_SIZE_BYTES = "total_size";
    public static final String COLUMN_LOCAL_URI = "local_uri";
    public static final String COLUMN_BYTES_DOWNLOADED_SO_FAR = "bytes_so_far";
    public static final String COLUMN_LAST_MODIFIED_TIMESTAMP = "last_modified_timestamp";

    @Deprecated
    public static final String COLUMN_LOCAL_FILENAME = "local_filename";
    public static final String COLUMN_STORAGE_TYPE = "storage_type";
    private static final String[] SEC_COLUMNS = {"_id", "mediaprovider_uri", "title", "description", "uri", COLUMN_MEDIA_TYPE, COLUMN_TOTAL_SIZE_BYTES, COLUMN_LOCAL_URI, "status", "reason", COLUMN_BYTES_DOWNLOADED_SO_FAR, COLUMN_LAST_MODIFIED_TIMESTAMP, "dd_fileName", "dd_vendor", "dd_description", "dd_majorVersion", "dd_primaryMimeType", "dd_contentSize", "state", "downloadmethod", COLUMN_LOCAL_FILENAME, COLUMN_STORAGE_TYPE};
    public static final String[] UNDERLYING_COLUMNS = {"_id", COLUMN_LOCAL_FILENAME, "mediaprovider_uri", "destination", "title", "description", "uri", "status", "hint", COLUMN_MEDIA_TYPE, COLUMN_TOTAL_SIZE_BYTES, COLUMN_LAST_MODIFIED_TIMESTAMP, COLUMN_BYTES_DOWNLOADED_SO_FAR, "allow_write", "notificationpackage", "dd_primaryMimeType", "dd_fileName", "dd_vendor", "dd_description", "dd_contentSize", "dd_objUrl", "dd_majorVersion", "range_start", "range_end", "range_first_end", COLUMN_LOCAL_URI, "reason"};
    private static final String[] SEC_UNDERLYING_COLUMNS = {"_id", "title", "status", "state", Downloads.Impl.COLUMN_TOTAL_BYTES, Downloads.Impl.COLUMN_CURRENT_BYTES, "_data", "description", "mimetype", Downloads.Impl.COLUMN_LAST_MODIFICATION, "visibility", "downloadmethod", "uri", "destination", "dd_primaryMimeType", Downloads.Impl.COLUMN_DD_SECONDARY_MIMETYPE1, Downloads.Impl.COLUMN_DD_SECONDARY_MIMETYPE2, "dd_fileName", "dd_vendor", "dd_description", "dd_contentSize", "dd_objUrl", Downloads.Impl.COLUMN_DD_NOTIFY_URL, "dd_majorVersion", Downloads.Impl.COLUMN_STORAGE_TYPE};
    private static final Set<String> LONG_COLUMNS = new HashSet(Arrays.asList("_id", COLUMN_TOTAL_SIZE_BYTES, "status", "reason", COLUMN_BYTES_DOWNLOADED_SO_FAR, COLUMN_LAST_MODIFIED_TIMESTAMP, "dd_contentSize", "state", "downloadmethod", COLUMN_STORAGE_TYPE));
    private Uri mBaseUri = Downloads.Impl.CONTENT_URI;
    private Uri mSecBaseUri = Downloads.Impl.CONTENT_CDURI;

    public static class Request {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Deprecated
        public static final int NETWORK_BLUETOOTH = 4;
        public static final int NETWORK_ETHERNET = 512;
        public static final int NETWORK_MOBILE = 1;
        public static final int NETWORK_WIFI = 2;
        private static final int SCANNABLE_VALUE_NO = 2;
        private static final int SCANNABLE_VALUE_YES = 0;
        public static final int VISIBILITY_HIDDEN = 2;
        public static final int VISIBILITY_VISIBLE = 0;
        public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
        public static final int VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION = 3;
        private CharSequence mDescription;
        private Uri mDestinationUri;
        private String mMimeType;
        private CharSequence mTitle;
        private Uri mUri;
        private List<Pair<String, String>> mRequestHeaders = new ArrayList();
        private int mStorageType = 0;
        private int mAllowedNetworkTypes = -1;
        private boolean mRoamingAllowed = true;
        private boolean mMeteredAllowed = true;
        private int mFlags = 0;
        private boolean mIsVisibleInDownloadsUi = true;
        private boolean mScannable = false;
        private boolean mUseSystemCache = false;
        private int mNotificationVisibility = 0;

        public Request(Uri uri) {
            if (uri == null) {
                throw new NullPointerException();
            }
            String scheme = uri.getScheme();
            if (scheme == null || (!scheme.equals(IntentFilter.SCHEME_HTTP) && !scheme.equals(IntentFilter.SCHEME_HTTPS))) {
                throw new IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + uri);
            }
            this.mUri = uri;
        }

        Request(String uriString) {
            this.mUri = Uri.parse(uriString);
        }

        public Request setDestinationUri(Uri uri) {
            this.mDestinationUri = uri;
            return this;
        }

        public Request setDestinationToSystemCache() {
            this.mUseSystemCache = true;
            return this;
        }

        public Request setDestinationInExternalFilesDir(Context context, String dirType, String subPath) {
            File file = context.getExternalFilesDir(dirType);
            if (file == null) {
                throw new IllegalStateException("Failed to get external storage files directory");
            }
            if (file.exists()) {
                if (!file.isDirectory()) {
                    throw new IllegalStateException(file.getAbsolutePath() + " already exists and is not a directory");
                }
            } else if (!file.mkdirs()) {
                throw new IllegalStateException("Unable to create directory: " + file.getAbsolutePath());
            }
            setDestinationFromBase(file, subPath);
            return this;
        }

        public Request setDestinationInExternalPublicDir(String dirType, String subPath) {
            File file = Environment.getExternalStoragePublicDirectory(dirType);
            if (file == null) {
                throw new IllegalStateException("Failed to get external storage public directory");
            }
            Context context = AppGlobals.getInitialApplication();
            if (context.getApplicationInfo().targetSdkVersion >= 29 || !Environment.isExternalStorageLegacy()) {
                try {
                    ContentProviderClient client = context.getContentResolver().acquireContentProviderClient("downloads");
                    try {
                        if (client == null) {
                            Log.i("DownloadManager", "client is null maybe due to download provider disabled");
                            if (client != null) {
                                client.close();
                            }
                            return null;
                        }
                        Bundle extras = new Bundle();
                        extras.putString(Downloads.DIR_TYPE, dirType);
                        client.call(Downloads.CALL_CREATE_EXTERNAL_PUBLIC_DIR, null, extras);
                        if (client != null) {
                            client.close();
                        }
                    } finally {
                    }
                } catch (RemoteException e) {
                    throw new IllegalStateException("Unable to create directory: " + file.getAbsolutePath(), e);
                }
            } else if (file.exists()) {
                if (!file.isDirectory()) {
                    throw new IllegalStateException(file.getAbsolutePath() + " already exists and is not a directory");
                }
            } else if (!file.mkdirs()) {
                throw new IllegalStateException("Unable to create directory: " + file.getAbsolutePath());
            }
            setDestinationFromBase(file, subPath);
            return this;
        }

        private void setDestinationFromBase(File base, String subPath) {
            if (subPath == null) {
                throw new NullPointerException("subPath cannot be null");
            }
            this.mDestinationUri = Uri.withAppendedPath(Uri.fromFile(base), subPath);
        }

        @Deprecated
        public void allowScanningByMediaScanner() {
            this.mScannable = true;
        }

        public Request addRequestHeader(String header, String value) {
            if (header == null) {
                throw new NullPointerException("header cannot be null");
            }
            if (header.contains(":")) {
                throw new IllegalArgumentException("header may not contain ':'");
            }
            if (value == null) {
                value = "";
            }
            this.mRequestHeaders.add(Pair.create(header, value));
            return this;
        }

        public Request setTitle(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public Request setDescription(CharSequence description) {
            this.mDescription = description;
            return this;
        }

        public Request setMimeType(String mimeType) {
            this.mMimeType = mimeType;
            return this;
        }

        public Request setStorageType(int storageType) {
            this.mStorageType = storageType;
            return this;
        }

        @Deprecated
        public Request setShowRunningNotification(boolean show) {
            return show ? setNotificationVisibility(0) : setNotificationVisibility(2);
        }

        public Request setNotificationVisibility(int visibility) {
            this.mNotificationVisibility = visibility;
            return this;
        }

        public Request setAllowedNetworkTypes(int flags) {
            this.mAllowedNetworkTypes = flags;
            return this;
        }

        public Request setAllowedOverRoaming(boolean allowed) {
            this.mRoamingAllowed = allowed;
            return this;
        }

        public Request setAllowedOverMetered(boolean allow) {
            this.mMeteredAllowed = allow;
            return this;
        }

        public Request setRequiresCharging(boolean requiresCharging) {
            if (requiresCharging) {
                this.mFlags |= 1;
            } else {
                this.mFlags &= -2;
            }
            return this;
        }

        public Request setRequiresDeviceIdle(boolean requiresDeviceIdle) {
            if (requiresDeviceIdle) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
            return this;
        }

        @Deprecated
        public Request setVisibleInDownloadsUi(boolean isVisible) {
            this.mIsVisibleInDownloadsUi = isVisible;
            return this;
        }

        ContentValues toContentValues(String packageName) {
            ContentValues values = new ContentValues();
            values.put("uri", this.mUri.toString());
            values.put(Downloads.Impl.COLUMN_IS_PUBLIC_API, (Boolean) true);
            values.put("notificationpackage", packageName);
            if (this.mDestinationUri != null) {
                values.put("destination", (Integer) 4);
                values.put("hint", this.mDestinationUri.toString());
            } else {
                values.put("destination", (Integer) 2);
            }
            values.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(this.mScannable ? 0 : 2));
            if (!this.mRequestHeaders.isEmpty()) {
                encodeHttpHeaders(values);
            }
            putIfNonNull(values, "title", this.mTitle);
            putIfNonNull(values, "description", this.mDescription);
            putIfNonNull(values, "mimetype", this.mMimeType);
            values.put("visibility", Integer.valueOf(this.mNotificationVisibility));
            values.put("allowed_network_types", Integer.valueOf(this.mAllowedNetworkTypes));
            values.put(Downloads.Impl.COLUMN_ALLOW_ROAMING, Boolean.valueOf(this.mRoamingAllowed));
            values.put("allow_metered", Boolean.valueOf(this.mMeteredAllowed));
            values.put("flags", Integer.valueOf(this.mFlags));
            values.put(Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI, Boolean.valueOf(this.mIsVisibleInDownloadsUi));
            return values;
        }

        ContentValues sectoContentValues(String packageName) {
            ContentValues values = new ContentValues();
            values.put("uri", this.mUri.toString());
            values.put(Downloads.Impl.COLUMN_IS_PUBLIC_API, (Boolean) true);
            values.put("notificationpackage", packageName);
            if (this.mDestinationUri != null) {
                values.put("hint", this.mDestinationUri.toString());
            }
            values.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(this.mScannable ? 0 : 2));
            if (!this.mRequestHeaders.isEmpty()) {
                encodeHttpHeaders(values);
            }
            putIfNonNull(values, "title", this.mTitle);
            putIfNonNull(values, "description", this.mDescription);
            putIfNonNull(values, "mimetype", this.mMimeType);
            values.put("visibility", Integer.valueOf(this.mNotificationVisibility));
            values.put("allowed_network_types", Integer.valueOf(this.mAllowedNetworkTypes));
            values.put(Downloads.Impl.COLUMN_ALLOW_ROAMING, Boolean.valueOf(this.mRoamingAllowed));
            values.put("allow_metered", Boolean.valueOf(this.mMeteredAllowed));
            values.put(Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI, Boolean.valueOf(this.mIsVisibleInDownloadsUi));
            values.put(Downloads.Impl.COLUMN_STORAGE_TYPE, Integer.valueOf(this.mStorageType));
            return values;
        }

        private void encodeHttpHeaders(ContentValues values) {
            int index = 0;
            for (Pair<String, String> header : this.mRequestHeaders) {
                String headerString = header.first + ": " + header.second;
                values.put(Downloads.Impl.RequestHeaders.INSERT_KEY_PREFIX + index, headerString);
                index++;
            }
        }

        private void putIfNonNull(ContentValues contentValues, String key, Object value) {
            if (value != null) {
                contentValues.put(key, value.toString());
            }
        }
    }

    public static class SecQuery {
        public static final int ORDER_ASCENDING = 1;
        public static final int ORDER_DESCENDING = 2;
        private long[] mIds = null;
        private Integer mStatusFlags = null;
        private String mFilterString = null;
        private String mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
        private int mOrderDirection = 2;
        private boolean mOnlyIncludeVisibleInDownloadsUi = false;

        public SecQuery setFilterById(long... ids) {
            this.mIds = ids;
            return this;
        }

        public SecQuery setFilterByString(String filter) {
            this.mFilterString = filter;
            return this;
        }

        public SecQuery setFilterByStatus(int flags) {
            this.mStatusFlags = Integer.valueOf(flags);
            return this;
        }

        public SecQuery orderBy(String column, int direction) {
            if (direction != 1 && direction != 2) {
                throw new IllegalArgumentException("Invalid direction: " + direction);
            }
            if (column.equals(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (column.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else if (column.equals("title")) {
                this.mOrderByColumn = "title COLLATE NOCASE";
            } else {
                throw new IllegalArgumentException("Cannot order by " + column);
            }
            this.mOrderDirection = direction;
            return this;
        }

        Cursor runQuery(ContentResolver resolver, String[] projection, Uri baseUri) {
            List<String> selectionParts = new ArrayList<>();
            int whereArgsCount = this.mIds == null ? 0 : this.mIds.length;
            int whereArgsCount2 = this.mFilterString == null ? whereArgsCount : whereArgsCount + 1;
            String[] selectionArgs = new String[whereArgsCount2];
            if (whereArgsCount2 > 0) {
                if (this.mIds != null) {
                    selectionParts.add(DownloadManager.getWhereClauseForIds(this.mIds));
                    DownloadManager.getWhereArgsForIds(this.mIds, selectionArgs);
                }
                if (this.mFilterString != null) {
                    selectionParts.add("title LIKE ?");
                    selectionArgs[selectionArgs.length - 1] = "%" + this.mFilterString + "%";
                }
            }
            String selection = joinStrings(" AND ", selectionParts);
            String orderDirection = this.mOrderDirection == 1 ? "ASC" : "DESC";
            String orderBy = this.mOrderByColumn + " " + orderDirection;
            return resolver.query(baseUri, projection, selection, selectionArgs, orderBy);
        }

        private String joinStrings(String joiner, Iterable<String> parts) {
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            for (String part : parts) {
                if (!first) {
                    builder.append(joiner);
                }
                builder.append(part);
                first = false;
            }
            return builder.toString();
        }

        private String statusClause(String operator, int value) {
            return "status" + operator + "'" + value + "'";
        }
    }

    public static class Query {
        public static final int ORDER_ASCENDING = 1;
        public static final int ORDER_DESCENDING = 2;
        private long[] mIds = null;
        private Integer mStatusFlags = null;
        private String mFilterString = null;
        private String mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
        private int mOrderDirection = 2;
        private boolean mOnlyIncludeVisibleInDownloadsUi = false;

        public Query setFilterById(long... ids) {
            this.mIds = ids;
            return this;
        }

        public Query setFilterByString(String filter) {
            this.mFilterString = filter;
            return this;
        }

        public Query setFilterByStatus(int flags) {
            this.mStatusFlags = Integer.valueOf(flags);
            return this;
        }

        public Query semSetOnlyIncludeVisibleInDownloadsUi(boolean value) {
            return setOnlyIncludeVisibleInDownloadsUi(value);
        }

        public Query setOnlyIncludeVisibleInDownloadsUi(boolean value) {
            this.mOnlyIncludeVisibleInDownloadsUi = value;
            return this;
        }

        public Query orderBy(String column, int direction) {
            if (direction != 1 && direction != 2) {
                throw new IllegalArgumentException("Invalid direction: " + direction);
            }
            if (column.equals(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (column.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else if (column.equals("title")) {
                this.mOrderByColumn = "title COLLATE NOCASE";
            } else {
                throw new IllegalArgumentException("Cannot order by " + column);
            }
            this.mOrderDirection = direction;
            return this;
        }

        public Query orderByLocalized(String column, int direction) {
            if (direction != 1 && direction != 2) {
                throw new IllegalArgumentException("Invalid direction: " + direction);
            }
            if (column.equals(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (column.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else if (column.equals("title")) {
                this.mOrderByColumn = "title COLLATE LOCALIZED";
            } else {
                throw new IllegalArgumentException("Cannot order Localized " + column);
            }
            this.mOrderDirection = direction;
            return this;
        }

        Cursor runQuery(ContentResolver resolver, String[] projection, Uri baseUri) {
            String[] selectionArgs;
            List<String> selectionParts = new ArrayList<>();
            if (this.mIds == null) {
                selectionArgs = null;
            } else {
                selectionParts.add(DownloadManager.getWhereClauseForIds(this.mIds));
                String[] selectionArgs2 = DownloadManager.getWhereArgsForIds(this.mIds);
                selectionArgs = selectionArgs2;
            }
            if (this.mStatusFlags != null) {
                List<String> parts = new ArrayList<>();
                if ((this.mStatusFlags.intValue() & 1) != 0) {
                    parts.add(statusClause("=", 190));
                }
                if ((this.mStatusFlags.intValue() & 2) != 0) {
                    parts.add(statusClause("=", 192));
                }
                if ((this.mStatusFlags.intValue() & 4) != 0) {
                    parts.add(statusClause("=", 193));
                    parts.add(statusClause("=", 194));
                    parts.add(statusClause("=", 195));
                    parts.add(statusClause("=", 196));
                }
                if ((this.mStatusFlags.intValue() & 8) != 0) {
                    parts.add(statusClause("=", 200));
                }
                if ((this.mStatusFlags.intValue() & 16) != 0) {
                    parts.add(NavigationBarInflaterView.KEY_CODE_START + statusClause(">=", 400) + " AND " + statusClause("<", 600) + NavigationBarInflaterView.KEY_CODE_END);
                }
                selectionParts.add(joinStrings(" OR ", parts));
            }
            if (this.mOnlyIncludeVisibleInDownloadsUi) {
                selectionParts.add("is_visible_in_downloads_ui != '0'");
            }
            selectionParts.add("deleted != '1'");
            String selection = joinStrings(" AND ", selectionParts);
            String orderDirection = this.mOrderDirection == 1 ? "ASC" : "DESC";
            String orderBy = this.mOrderByColumn + " " + orderDirection;
            return resolver.query(baseUri, projection, selection, selectionArgs, orderBy);
        }

        private String joinStrings(String joiner, Iterable<String> parts) {
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            for (String part : parts) {
                if (!first) {
                    builder.append(joiner);
                }
                builder.append(part);
                first = false;
            }
            return builder.toString();
        }

        private String statusClause(String operator, int value) {
            return "status" + operator + "'" + value + "'";
        }
    }

    public DownloadManager(Context context) {
        this.mResolver = context.getContentResolver();
        this.mPackageName = context.getPackageName();
        this.mAccessFilename = context.getApplicationInfo().targetSdkVersion < 24;
    }

    public void setAccessAllDownloads(boolean accessAllDownloads) {
        if (accessAllDownloads) {
            this.mBaseUri = Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI;
        } else {
            this.mBaseUri = Downloads.Impl.CONTENT_URI;
        }
    }

    public void setAccessFilename(boolean accessFilename) {
        this.mAccessFilename = accessFilename;
    }

    public void setSecDownloads(boolean accessAllDownloads) {
        if (accessAllDownloads) {
            this.mBaseUri = Downloads.Impl.CONTENT_CDURI;
        }
    }

    @SystemApi
    public void onMediaStoreDownloadsDeleted(LongSparseArray<String> idToMime) {
        try {
            ContentProviderClient client = this.mResolver.acquireUnstableContentProviderClient(this.mBaseUri);
            try {
                if (client == null) {
                    Log.i("DownloadManager", "client is null maybe due to download provider disabled");
                    if (client != null) {
                        client.close();
                        return;
                    }
                    return;
                }
                Bundle callExtras = new Bundle();
                long[] ids = new long[idToMime.size()];
                String[] mimeTypes = new String[idToMime.size()];
                for (int i = idToMime.size() - 1; i >= 0; i--) {
                    ids[i] = idToMime.keyAt(i);
                    mimeTypes[i] = idToMime.valueAt(i);
                }
                callExtras.putLongArray(Downloads.EXTRA_IDS, ids);
                callExtras.putStringArray("mime_types", mimeTypes);
                client.call(Downloads.CALL_MEDIASTORE_DOWNLOADS_DELETED, null, callExtras);
                if (client != null) {
                    client.close();
                }
            } finally {
            }
        } catch (RemoteException e) {
        }
    }

    public long enqueue(Request request) {
        ContentValues values = request.toContentValues(this.mPackageName);
        Uri downloadUri = this.mResolver.insert(Downloads.Impl.CONTENT_URI, values);
        if (downloadUri == null) {
            return -1L;
        }
        long id = Long.parseLong(downloadUri.getLastPathSegment());
        return id;
    }

    public int markRowDeleted(long... ids) {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("input param 'ids' can't be null");
        }
        return this.mResolver.delete(this.mBaseUri, getWhereClauseForIds(ids), getWhereArgsForIds(ids));
    }

    public int remove(long... ids) {
        return markRowDeleted(ids);
    }

    public int secmarkRowDeleted(long... ids) {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("input param 'ids' can't be null");
        }
        ContentValues values = new ContentValues();
        values.put("deleted", (Integer) 1);
        return this.mResolver.update(ContentUris.withAppendedId(Downloads.Impl.CONTENT_CDURI, ids[0]), values, null, null);
    }

    private String joinStrings(String joiner, Iterable<String> parts) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String part : parts) {
            if (!first) {
                builder.append(joiner);
            }
            builder.append(part);
            first = false;
        }
        return builder.toString();
    }

    public int secremove(long... ids) {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("input param 'ids' can't be null");
        }
        List<String> selectionParts = new ArrayList<>();
        selectionParts.add(getWhereClauseForIds(ids));
        String[] selectionArgs = getWhereArgsForIds(ids);
        String selection = joinStrings(" AND ", selectionParts);
        return this.mResolver.delete(this.mSecBaseUri, selection, selectionArgs);
    }

    public Cursor query(Query query) {
        return query(query, UNDERLYING_COLUMNS);
    }

    public Cursor query(Query query, String[] projection) {
        Cursor underlyingCursor = query.runQuery(this.mResolver, projection, this.mBaseUri);
        if (underlyingCursor == null) {
            return null;
        }
        return new CursorTranslator(underlyingCursor, this.mBaseUri, this.mAccessFilename);
    }

    public Cursor secquery(SecQuery query) {
        Cursor underlyingCursor = query.runQuery(this.mResolver, SEC_UNDERLYING_COLUMNS, this.mSecBaseUri);
        if (underlyingCursor == null) {
            return null;
        }
        return new SecCursorTranslator(underlyingCursor, this.mSecBaseUri);
    }

    public ParcelFileDescriptor openDownloadedFile(long id) throws FileNotFoundException {
        return this.mResolver.openFileDescriptor(getDownloadUri(id), "r");
    }

    public Uri getUriForDownloadedFile(long id) {
        Query query = new Query().setFilterById(id);
        Cursor cursor = null;
        try {
            cursor = query(query);
            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
                if (8 == status) {
                    Uri withAppendedId = ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, id);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return withAppendedId;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getMimeTypeForDownloadedFile(long id) {
        Query query = new Query().setFilterById(id);
        Cursor cursor = null;
        try {
            cursor = query(query);
            if (cursor == null) {
                return null;
            }
            if (!cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDIA_TYPE));
            if (cursor != null) {
                cursor.close();
            }
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public boolean restartDownload(long... ids) {
        Cursor cursor = query(new Query().setFilterById(ids));
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                if (status != 8 && status != 16) {
                    return false;
                }
                cursor.moveToNext();
            }
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(Downloads.Impl.COLUMN_CURRENT_BYTES, (Integer) 0);
            values.put(Downloads.Impl.COLUMN_TOTAL_BYTES, (Integer) (-1));
            values.putNull("_data");
            values.put("status", (Integer) 190);
            values.put(Downloads.Impl.COLUMN_FAILED_CONNECTIONS, (Integer) 0);
            values.put("state", (Integer) 0);
            values.put("range_start", (Long) 0L);
            values.put("range_end", (Long) 0L);
            values.put("range_first_end", (Long) 0L);
            this.mResolver.update(this.mBaseUri, values, getWhereClauseForIds(ids), getWhereArgsForIds(ids));
            return true;
        } finally {
            cursor.close();
        }
    }

    public void pauseDownload(long id) {
        ContentValues values = new ContentValues();
        values.put(Downloads.Impl.COLUMN_CONTROL, (Integer) 1);
        this.mResolver.update(ContentUris.withAppendedId(this.mBaseUri, id), values, null, null);
    }

    public void resumeDownload(long id) {
        ContentValues values = new ContentValues();
        values.put(Downloads.Impl.COLUMN_CONTROL, (Integer) 0);
        this.mResolver.update(ContentUris.withAppendedId(this.mBaseUri, id), values, null, null);
    }

    public boolean secrestartDownload(long... ids) {
        Cursor cursor = secquery(new SecQuery().setFilterById(ids));
        if (cursor == null) {
            return false;
        }
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                if (status != 8 && status != 16) {
                    return false;
                }
                cursor.moveToNext();
            }
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(Downloads.Impl.COLUMN_CURRENT_BYTES, (Integer) 0);
            values.put(Downloads.Impl.COLUMN_TOTAL_BYTES, (Integer) (-1));
            values.putNull("_data");
            values.put("state", (Integer) 0);
            values.put("visibility", (Integer) 1);
            values.put("status", (Integer) 190);
            this.mResolver.update(this.mSecBaseUri, values, getWhereClauseForIds(ids), getWhereArgsForIds(ids));
            return true;
        } finally {
            cursor.close();
        }
    }

    public void forceDownload(long... ids) {
        ContentValues values = new ContentValues();
        values.put("status", (Integer) 190);
        values.put(Downloads.Impl.COLUMN_CONTROL, (Integer) 0);
        values.put(Downloads.Impl.COLUMN_BYPASS_RECOMMENDED_SIZE_LIMIT, (Integer) 1);
        this.mResolver.update(this.mBaseUri, values, getWhereClauseForIds(ids), getWhereArgsForIds(ids));
    }

    public static Long getMaxBytesOverMobile(Context context) {
        try {
            return Long.valueOf(Settings.Global.getLong(context.getContentResolver(), Settings.Global.DOWNLOAD_MAX_BYTES_OVER_MOBILE));
        } catch (Settings.SettingNotFoundException e) {
            return null;
        }
    }

    public boolean rename(Context context, long id, String displayName) {
        if (!FileUtils.isValidFatFilename(displayName)) {
            throw new SecurityException(displayName + " is not a valid filename");
        }
        Query query = new Query().setFilterById(id);
        Cursor cursor = query(query);
        try {
            if (cursor == null) {
                throw new IllegalStateException("Missing cursor for download id=" + id);
            }
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));
                if (status != 8) {
                    throw new IllegalStateException("Download is not completed yet: " + DatabaseUtils.dumpCurrentRowToString(cursor));
                }
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCAL_FILENAME));
                if (filePath == null) {
                    throw new IllegalStateException("Download doesn't have a valid file path: " + DatabaseUtils.dumpCurrentRowToString(cursor));
                }
                if (!new File(filePath).exists()) {
                    throw new IllegalStateException("Downloaded file doesn't exist anymore: " + DatabaseUtils.dumpCurrentRowToString(cursor));
                }
                if (cursor != null) {
                    cursor.close();
                }
                File before = new File(filePath);
                File after = new File(before.getParentFile(), displayName);
                if (after.exists()) {
                    throw new IllegalStateException("File already exists: " + after);
                }
                if (!before.renameTo(after)) {
                    throw new IllegalStateException("Failed to rename file from " + before + " to " + after);
                }
                MediaStore.scanFile(this.mResolver, before);
                MediaStore.scanFile(this.mResolver, after);
                ContentValues values = new ContentValues();
                values.put("title", displayName);
                values.put("_data", after.toString());
                values.putNull("mediaprovider_uri");
                long[] ids = {id};
                return this.mResolver.update(this.mBaseUri, values, getWhereClauseForIds(ids), getWhereArgsForIds(ids)) == 1;
            }
            throw new IllegalStateException("Missing download id=" + id);
        } finally {
        }
    }

    public static Long getRecommendedMaxBytesOverMobile(Context context) {
        try {
            return Long.valueOf(Settings.Global.getLong(context.getContentResolver(), Settings.Global.DOWNLOAD_RECOMMENDED_MAX_BYTES_OVER_MOBILE));
        } catch (Settings.SettingNotFoundException e) {
            return null;
        }
    }

    public static boolean isActiveNetworkExpensive(Context context) {
        return false;
    }

    public static long getActiveNetworkWarningBytes(Context context) {
        return -1L;
    }

    @Deprecated
    public long addCompletedDownload(String title, String description, boolean isMediaScannerScannable, String mimeType, String path, long length, boolean showNotification) {
        return addCompletedDownload(title, description, isMediaScannerScannable, mimeType, path, length, showNotification, false, null, null);
    }

    @Deprecated
    public long addCompletedDownload(String title, String description, boolean isMediaScannerScannable, String mimeType, String path, long length, boolean showNotification, Uri uri, Uri referer) {
        return addCompletedDownload(title, description, isMediaScannerScannable, mimeType, path, length, showNotification, false, uri, referer);
    }

    @Deprecated
    public long addCompletedDownload(String title, String description, boolean isMediaScannerScannable, String mimeType, String path, long length, boolean showNotification, boolean allowWrite) {
        return addCompletedDownload(title, description, isMediaScannerScannable, mimeType, path, length, showNotification, allowWrite, null, null);
    }

    @Deprecated
    public long addCompletedDownload(String str, String str2, boolean z, String str3, String str4, long j, boolean z2, boolean z3, Uri uri, Uri uri2) {
        Request request;
        validateArgumentIsNonEmpty("title", str);
        validateArgumentIsNonEmpty("description", str2);
        validateArgumentIsNonEmpty("path", str4);
        validateArgumentIsNonEmpty("mimeType", str3);
        if (j < 0) {
            throw new IllegalArgumentException(" invalid value for param: totalBytes");
        }
        if (uri != null) {
            request = new Request(uri);
        } else {
            request = new Request(NON_DOWNLOADMANAGER_DOWNLOAD);
        }
        request.setTitle(str).setDescription(str2).setMimeType(str3);
        if (uri2 != null) {
            request.addRequestHeader("Referer", uri2.toString());
        }
        ContentValues contentValues = request.toContentValues(this.mPackageName.contains(SBROWSER_PACKAGE_NAME) ? this.mPackageName : null);
        contentValues.put("destination", (Integer) 6);
        contentValues.put("_data", str4);
        contentValues.put("mimetype", resolveMimeType(new File(str4)));
        contentValues.put("status", (Integer) 200);
        contentValues.put("state", (Integer) 10);
        contentValues.put(Downloads.Impl.COLUMN_TOTAL_BYTES, Long.valueOf(j));
        int i = 2;
        contentValues.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(z ? 0 : 2));
        if (z2) {
            i = 3;
        }
        contentValues.put("visibility", Integer.valueOf(i));
        contentValues.put("allow_write", Integer.valueOf(z3 ? 1 : 0));
        Uri insert = this.mResolver.insert(Downloads.Impl.CONTENT_URI, contentValues);
        if (insert == null) {
            return -1L;
        }
        return Long.parseLong(insert.getLastPathSegment());
    }

    private static String resolveMimeType(File file) {
        String mimeType;
        String extension = extractFileExtension(file.getPath());
        if (extension == null || (mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase(Locale.ROOT))) == null) {
            return "application/octet-stream";
        }
        return mimeType;
    }

    private static String extractDisplayName(String data) {
        if (data == null) {
            return null;
        }
        if (data.indexOf(47) == -1) {
            return data;
        }
        if (data.endsWith("/")) {
            data = data.substring(0, data.length() - 1);
        }
        return data.substring(data.lastIndexOf(47) + 1);
    }

    private static String extractFileExtension(String data) {
        String data2;
        int lastDot;
        if (data == null || (lastDot = (data2 = extractDisplayName(data)).lastIndexOf(46)) == -1) {
            return null;
        }
        return data2.substring(lastDot + 1);
    }

    public long secAddCompletedDownload(String title, String description, boolean isMediaScannerScannable, String mimeType, String path, long length, boolean showNotification) {
        validateArgumentIsNonEmpty("title", title);
        validateArgumentIsNonEmpty("description", description);
        validateArgumentIsNonEmpty("path", path);
        validateArgumentIsNonEmpty("mimeType", mimeType);
        if (length < 0) {
            throw new IllegalArgumentException(" invalid value for param: totalBytes");
        }
        Request request = new Request(NON_DOWNLOADMANAGER_DOWNLOAD).setTitle(title).setDescription(description).setMimeType(mimeType);
        ContentValues values = request.sectoContentValues(null);
        values.put("destination", (Integer) 0);
        values.put("_data", path);
        values.put("status", (Integer) 200);
        values.put("state", (Integer) 10);
        values.put(Downloads.Impl.COLUMN_STORAGE_TYPE, (Integer) 1);
        values.put(Downloads.Impl.COLUMN_TOTAL_BYTES, Long.valueOf(length));
        values.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(isMediaScannerScannable ? 0 : 2));
        values.put("visibility", Integer.valueOf(showNotification ? 1 : 2));
        Uri downloadUri = this.mResolver.insert(Downloads.Impl.CONTENT_CDURI, values);
        if (downloadUri == null) {
            return -1L;
        }
        return Long.parseLong(downloadUri.getLastPathSegment());
    }

    private static void validateArgumentIsNonEmpty(String paramName, String val) {
        if (TextUtils.isEmpty(val)) {
            throw new IllegalArgumentException(paramName + " can't be null");
        }
    }

    public Uri getDownloadUri(long id) {
        return ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, id);
    }

    static String getWhereClauseForIds(long[] ids) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append(NavigationBarInflaterView.KEY_CODE_START);
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                whereClause.append("OR ");
            }
            whereClause.append("_id");
            whereClause.append(" = ? ");
        }
        whereClause.append(NavigationBarInflaterView.KEY_CODE_END);
        return whereClause.toString();
    }

    static String[] getWhereArgsForIds(long[] ids) {
        String[] whereArgs = new String[ids.length];
        return getWhereArgsForIds(ids, whereArgs);
    }

    static String[] getWhereArgsForIds(long[] ids, String[] args) {
        for (int i = 0; i < ids.length; i++) {
            args[i] = Long.toString(ids[i]);
        }
        return args;
    }

    private static class CursorTranslator extends CursorWrapper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final boolean mAccessFilename;
        private final Uri mBaseUri;

        public CursorTranslator(Cursor cursor, Uri baseUri, boolean accessFilename) {
            super(cursor);
            this.mBaseUri = baseUri;
            this.mAccessFilename = accessFilename;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getInt(int columnIndex) {
            return (int) getLong(columnIndex);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public long getLong(int columnIndex) {
            if (getColumnName(columnIndex).equals("reason")) {
                return getReason(super.getInt(getColumnIndex("status")));
            }
            if (getColumnName(columnIndex).equals("status")) {
                return translateStatus(super.getInt(getColumnIndex("status")));
            }
            return super.getLong(columnIndex);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.database.CursorWrapper, android.database.Cursor
        public String getString(int columnIndex) {
            char c;
            String columnName = getColumnName(columnIndex);
            switch (columnName.hashCode()) {
                case -1204869480:
                    if (columnName.equals(DownloadManager.COLUMN_LOCAL_URI)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 22072411:
                    if (columnName.equals(DownloadManager.COLUMN_LOCAL_FILENAME)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return getLocalUri();
                case 1:
                    if (!this.mAccessFilename) {
                        throw new SecurityException("COLUMN_LOCAL_FILENAME is deprecated; use ContentResolver.openFileDescriptor() instead");
                    }
                    break;
            }
            return super.getString(columnIndex);
        }

        private String getLocalUri() {
            long destinationType = getLong(getColumnIndex("destination"));
            if (destinationType == 4 || destinationType == 0 || destinationType == 6) {
                String localPath = super.getString(getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                if (localPath == null) {
                    return null;
                }
                return Uri.fromFile(new File(localPath)).toString();
            }
            long downloadId = getLong(getColumnIndex("_id"));
            return ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, downloadId).toString();
        }

        private long getReason(int status) {
            switch (translateStatus(status)) {
                case 4:
                    return getPausedReason(status);
                case 16:
                    return getErrorCode(status);
                default:
                    return 0L;
            }
        }

        private long getPausedReason(int status) {
            switch (status) {
                case 193:
                    return 5L;
                case 194:
                    return 1L;
                case 195:
                    return 2L;
                case 196:
                    return 3L;
                default:
                    return 4L;
            }
        }

        private long getErrorCode(int status) {
            if ((400 <= status && status < 488) || (500 <= status && status < 700)) {
                return status;
            }
            switch (status) {
                case 198:
                    return 1006L;
                case 199:
                    return 1007L;
                case 488:
                    return 1009L;
                case 489:
                    return 1008L;
                case 492:
                    return 1001L;
                case 493:
                case 494:
                    return 1002L;
                case 495:
                    return 1004L;
                case 497:
                    return 1005L;
                default:
                    return 1000L;
            }
        }

        private int translateStatus(int status) {
            switch (status) {
            }
            return 2;
        }
    }

    private static class SecCursorTranslator extends CursorWrapper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Uri mBaseUri;

        public SecCursorTranslator(Cursor cursor, Uri baseUri) {
            super(cursor);
            this.mBaseUri = baseUri;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getColumnIndex(String columnName) {
            return Arrays.asList(DownloadManager.SEC_COLUMNS).indexOf(columnName);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
            int index = getColumnIndex(columnName);
            if (index == -1) {
                throw new IllegalArgumentException("No such column: " + columnName);
            }
            return index;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public String getColumnName(int columnIndex) {
            int numColumns = DownloadManager.SEC_COLUMNS.length;
            if (columnIndex < 0 || columnIndex >= numColumns) {
                throw new IllegalArgumentException("Invalid column index " + columnIndex + ", " + numColumns + " columns exist");
            }
            return DownloadManager.SEC_COLUMNS[columnIndex];
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public String[] getColumnNames() {
            String[] returnColumns = new String[DownloadManager.SEC_COLUMNS.length];
            System.arraycopy(DownloadManager.SEC_COLUMNS, 0, returnColumns, 0, DownloadManager.SEC_COLUMNS.length);
            return returnColumns;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getColumnCount() {
            return DownloadManager.SEC_COLUMNS.length;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public byte[] getBlob(int columnIndex) {
            throw new UnsupportedOperationException();
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public double getDouble(int columnIndex) {
            return getLong(columnIndex);
        }

        private boolean isLongColumn(String column) {
            return DownloadManager.LONG_COLUMNS.contains(column);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public float getFloat(int columnIndex) {
            return (float) getDouble(columnIndex);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getInt(int columnIndex) {
            return (int) getLong(columnIndex);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public long getLong(int columnIndex) {
            return translateLong(getColumnName(columnIndex));
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public short getShort(int columnIndex) {
            return (short) getLong(columnIndex);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public String getString(int columnIndex) {
            return translateString(getColumnName(columnIndex));
        }

        private String translateString(String column) {
            if (isLongColumn(column)) {
                return Long.toString(translateLong(column));
            }
            if (column.equals("title")) {
                return getUnderlyingString("title");
            }
            if (column.equals("description")) {
                return getUnderlyingString("description");
            }
            if (column.equals("uri")) {
                return getUnderlyingString("uri");
            }
            if (column.equals(DownloadManager.COLUMN_MEDIA_TYPE)) {
                return getUnderlyingString("mimetype");
            }
            if (column.equals(DownloadManager.COLUMN_LOCAL_FILENAME)) {
                return getUnderlyingString("_data");
            }
            if (column.equals("dd_fileName")) {
                return getUnderlyingString("dd_fileName");
            }
            if (column.equals("dd_vendor")) {
                return getUnderlyingString("dd_vendor");
            }
            if (column.equals("dd_majorVersion")) {
                return getUnderlyingString("dd_majorVersion");
            }
            if (column.equals("dd_primaryMimeType")) {
                return getUnderlyingString("dd_primaryMimeType");
            }
            if (column.equals("dd_description")) {
                return getUnderlyingString("dd_description");
            }
            return getLocalUri();
        }

        private String getLocalUri() {
            long destinationType = getUnderlyingLong("destination");
            if (destinationType == 4) {
                return getUnderlyingString("hint");
            }
            if (destinationType == 0) {
                String localPath = getUnderlyingString("_data");
                if (localPath == null) {
                    return null;
                }
                return Uri.fromFile(new File(localPath)).toString();
            }
            if (destinationType == 6) {
                String localPath2 = getString(getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                if (localPath2 == null) {
                    return null;
                }
                return Uri.fromFile(new File(localPath2)).toString();
            }
            long downloadId = getUnderlyingLong("_id");
            return ContentUris.withAppendedId(this.mBaseUri, downloadId).toString();
        }

        private long translateLong(String column) {
            if (!isLongColumn(column)) {
                return Long.valueOf(translateString(column)).longValue();
            }
            if (column.equals("_id")) {
                return getUnderlyingLong("_id");
            }
            if (column.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                return getUnderlyingLong(Downloads.Impl.COLUMN_TOTAL_BYTES);
            }
            if (column.equals("status")) {
                return translateStatus((int) getUnderlyingLong("status"));
            }
            if (column.equals("reason")) {
                return getReason((int) getUnderlyingLong("status"));
            }
            if (column.equals(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) {
                return getUnderlyingLong(Downloads.Impl.COLUMN_CURRENT_BYTES);
            }
            if (column.equals("dd_contentSize")) {
                return getUnderlyingLong("dd_contentSize");
            }
            if (column.equals("downloadmethod")) {
                return getUnderlyingLong("downloadmethod");
            }
            if (column.equals("state")) {
                return getUnderlyingLong("state");
            }
            if (column.equals(DownloadManager.COLUMN_STORAGE_TYPE)) {
                return getUnderlyingLong(Downloads.Impl.COLUMN_STORAGE_TYPE);
            }
            if (column.equals("range_start")) {
                return getUnderlyingLong("range_start");
            }
            if (column.equals("range_end")) {
                return getUnderlyingLong("range_end");
            }
            if (column.equals("range_first_end")) {
                return getUnderlyingLong("range_first_end");
            }
            return getUnderlyingLong(Downloads.Impl.COLUMN_LAST_MODIFICATION);
        }

        public long getReason(int status) {
            switch (translateStatus(status)) {
                case 4:
                    return getPausedReason(status);
                case 16:
                    return getErrorCode(status);
                default:
                    return 0L;
            }
        }

        private long getPausedReason(int status) {
            switch (status) {
                case 194:
                    return 1L;
                case 195:
                    return 2L;
                case 196:
                    return 3L;
                default:
                    return 4L;
            }
        }

        private long getErrorCode(int status) {
            if ((400 > status || status >= 488) && (500 > status || status >= 700)) {
                switch (status) {
                    case 198:
                        break;
                    case 199:
                        break;
                    case 488:
                        break;
                    case 489:
                        break;
                }
                return status;
            }
            return status;
        }

        private long getUnderlyingLong(String column) {
            return super.getLong(super.getColumnIndex(column));
        }

        private String getUnderlyingString(String column) {
            return super.getString(super.getColumnIndex(column));
        }

        public int translateStatus(int status) {
            switch (status) {
            }
            return 2;
        }
    }
}
