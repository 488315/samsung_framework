package com.sec.internal.ims.servicemodules.tapi.service.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.gsma.services.rcs.RcsService;
import com.gsma.services.rcs.filetransfer.FileTransfer;
import com.gsma.services.rcs.filetransfer.FileTransferLog;
import com.sec.internal.constants.ims.cmstore.CloudMessageProviderContract;
import com.sec.internal.constants.ims.servicemodules.im.ImConstants;
import com.sec.internal.constants.ims.servicemodules.im.ImDirection;
import com.sec.internal.constants.ims.servicemodules.im.reason.CancelReason;
import com.sec.internal.ims.servicemodules.im.FtMessage;
import com.sec.internal.ims.servicemodules.im.ImCache;
import com.sec.internal.ims.servicemodules.tapi.service.api.FileTransferingServiceImpl;
import com.sec.internal.ims.servicemodules.tapi.service.utils.FileUtils;

/* loaded from: classes.dex */
public class FtProvider extends ContentProvider {
    public static final String AUTHORITY;
    private static final String LOG_TAG = FtProvider.class.getSimpleName();
    private static final int RCSAPI = 1;
    private static final int RCSAPI_ID = 2;
    private static final UriMatcher sUriMatcher;
    private ImCache mCache;

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        String authority = FileTransferLog.CONTENT_URI.getAuthority();
        AUTHORITY = authority;
        uriMatcher.addURI(authority, "filetransfer", 1);
        uriMatcher.addURI(authority, "filetransfer/#", 2);
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
        this.mCache = ImCache.getInstance();
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int match = sUriMatcher.match(uri);
        if (match == 1) {
            return buildMessagesCursor(null, strArr, str, strArr2, str2);
        }
        if (match == 2) {
            return buildMessagesCursor(uri, strArr, str, strArr2, str2);
        }
        Log.d(LOG_TAG, "return null");
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException();
    }

    private Cursor buildMessagesCursor(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String str3;
        if (uri != null) {
            String lastPathSegment = uri.getLastPathSegment();
            if (lastPathSegment == null) {
                Log.e(LOG_TAG, "buildMessageCursor: No last segment.");
                return null;
            }
            str3 = lastPathSegment;
        } else {
            str3 = null;
        }
        return fillMessageCursor(str3, strArr, str, strArr2, str2);
    }

    private MatrixCursor fillMessageCursor(String str, String[] strArr, String str2, String[] strArr2, String str3) {
        String str4;
        int i;
        if (str == null) {
            str4 = str2;
        } else if (TextUtils.isEmpty(str2)) {
            str4 = "ft_id = " + str;
        } else {
            str4 = "(" + str2 + ") AND ft_id = " + str;
        }
        String str5 = LOG_TAG;
        Log.d(str5, " soyeon : selection : " + str4 + " idString:" + str);
        Cursor queryFtMessagesForTapi = this.mCache.queryFtMessagesForTapi(strArr, str4, strArr2, str3);
        if (queryFtMessagesForTapi != null) {
            try {
                if (queryFtMessagesForTapi.getCount() != 0) {
                    String[] columnNames = queryFtMessagesForTapi.getColumnNames();
                    MatrixCursor matrixCursor = new MatrixCursor(columnNames);
                    while (queryFtMessagesForTapi.moveToNext()) {
                        MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
                        for (String str6 : columnNames) {
                            int columnIndex = queryFtMessagesForTapi.getColumnIndex(str6);
                            Log.d(LOG_TAG, "columnName : " + str6 + "columnType : " + queryFtMessagesForTapi.getType(columnIndex) + ", columnValue : " + queryFtMessagesForTapi.getString(columnIndex));
                            if ("state".equals(str6)) {
                                newRow.add(Integer.valueOf(transState(queryFtMessagesForTapi.getString(columnIndex))));
                            } else if ("reason_code".equals(str6)) {
                                CancelReason valueOf = CancelReason.valueOf(queryFtMessagesForTapi.getInt(columnIndex));
                                FileTransfer.ReasonCode reasonCode = FileTransfer.ReasonCode.UNSPECIFIED;
                                if (valueOf != null) {
                                    reasonCode = FileTransferingServiceImpl.ftCancelReasonTranslator(valueOf);
                                }
                                newRow.add(Integer.valueOf(reasonCode.ordinal()));
                            } else if (CloudMessageProviderContract.BufferDBMMSpdu.READ_STATUS.equals(str6)) {
                                if (ImConstants.Status.READ == ImConstants.Status.values()[queryFtMessagesForTapi.getInt(columnIndex)]) {
                                    newRow.add(Integer.valueOf(RcsService.ReadStatus.READ.ordinal()));
                                } else {
                                    newRow.add(Integer.valueOf(RcsService.ReadStatus.UNREAD.ordinal()));
                                }
                            } else if ("fileicon_mime_type".equals(str6)) {
                                newRow.add(FileUtils.getContentTypeFromFileName(queryFtMessagesForTapi.getString(columnIndex)));
                            } else {
                                if (!"file_expiration".equals(str6) && !"fileicon_expiration".equals(str6)) {
                                    int i2 = 1;
                                    if ("expired_delivery".equals(str6)) {
                                        if (queryFtMessagesForTapi.getLong(columnIndex) <= 0) {
                                            i2 = 0;
                                        }
                                        newRow.add(Integer.valueOf(i2));
                                    } else if ("fileicon".equals(str6)) {
                                        newRow.add(null);
                                    } else if ("file".equals(str6)) {
                                        String string = queryFtMessagesForTapi.getString(columnIndex);
                                        if (string.endsWith(".tmp")) {
                                            string = string.substring(0, string.length() - 4);
                                        }
                                        newRow.add("file://" + string);
                                    } else {
                                        int type = queryFtMessagesForTapi.getType(columnIndex);
                                        if (type == 1) {
                                            newRow.add(Long.valueOf(queryFtMessagesForTapi.getLong(columnIndex)));
                                        } else if (type == 2) {
                                            newRow.add(Float.valueOf(queryFtMessagesForTapi.getFloat(columnIndex)));
                                        } else if (type == 3) {
                                            newRow.add(queryFtMessagesForTapi.getString(columnIndex));
                                        } else if (type == 4) {
                                            newRow.add(Float.valueOf(queryFtMessagesForTapi.getFloat(columnIndex)));
                                        } else {
                                            newRow.add(null);
                                        }
                                    }
                                }
                                ImCache imCache = ImCache.getInstance();
                                if (str != null) {
                                    try {
                                        i = Integer.parseInt(str);
                                    } catch (NumberFormatException unused) {
                                        i = -1;
                                    }
                                } else {
                                    i = Integer.parseInt("ft_id");
                                }
                                FtMessage ftMessage = imCache.getFtMessage(i);
                                if (ftMessage != null) {
                                    Log.d(LOG_TAG, "FILE_EXPIRATION:" + ftMessage.getFileExpire());
                                    newRow.add(ftMessage.getFileExpire());
                                } else {
                                    newRow.add("");
                                    Log.d(LOG_TAG, "FILE_EXPIRATION:null");
                                }
                            }
                        }
                    }
                    queryFtMessagesForTapi.close();
                    return matrixCursor;
                }
            } finally {
            }
        }
        Log.e(str5, "buildMessageCursor: Message not found.");
        if (queryFtMessagesForTapi != null) {
            queryFtMessagesForTapi.close();
        }
        return null;
    }

    public int transState(String str) {
        int parseInt;
        String[] split = str.split(";");
        if (split.length != 2 || (parseInt = Integer.parseInt(split[1])) < 0 || parseInt >= ImDirection.values().length) {
            return -1;
        }
        ImDirection imDirection = ImDirection.values()[parseInt];
        int parseInt2 = Integer.parseInt(split[0]);
        if (parseInt2 != 0 && parseInt2 != 1) {
            if (parseInt2 == 2) {
                return FileTransfer.State.STARTED.ordinal();
            }
            if (parseInt2 == 3) {
                return FileTransfer.State.TRANSFERRED.ordinal();
            }
            if (parseInt2 != 4) {
                if (parseInt2 != 6) {
                    if (parseInt2 != 7) {
                        if (parseInt2 != 9) {
                            return FileTransfer.State.FAILED.ordinal();
                        }
                    }
                }
            }
            return FileTransfer.State.ABORTED.ordinal();
        }
        if (ImDirection.INCOMING == imDirection) {
            return FileTransfer.State.INVITED.ordinal();
        }
        if (ImDirection.OUTGOING == imDirection) {
            return FileTransfer.State.INITIATING.ordinal();
        }
        return -1;
    }
}
