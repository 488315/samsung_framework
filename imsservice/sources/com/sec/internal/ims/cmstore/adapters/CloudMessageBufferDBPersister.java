package com.sec.internal.ims.cmstore.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.sec.ims.util.ImsUri;
import com.sec.internal.constants.ims.cmstore.CloudMessageBufferDBConstants;
import com.sec.internal.constants.ims.cmstore.CloudMessageProviderContract;
import com.sec.internal.constants.ims.os.PhoneConstants;
import com.sec.internal.ims.cmstore.CloudMessageService;
import com.sec.internal.ims.cmstore.utils.Util;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CloudMessageBufferDBPersister {
    private static final String CALLLOG_TABLE = "calllog";
    private static final String CREATE_MMSADDR_MESSAGE_TABLE = "CREATE TABLE addr(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER, msg_id INTEGER, contact_id INTEGER, address TEXT, type INTEGER, charset INTEGER);";
    private static final String CREATE_MMSPART_MESSAGE_TABLE = "CREATE TABLE part(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER, mid INTEGER, seq INTEGER, ct TEXT, name TEXT, chset INTEGER, cd TEXT, fn TEXT, cid TEXT, cl TEXT, ctt_s INTEGER, ctt_t TEXT, _data TEXT, text TEXT, content_uri TEXT, payloadurl TEXT);";
    private static final String CREATE_MMSPDU_MESSAGE_TABLE = "CREATE TABLE pdu(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER, thread_id INTEGER, date INTEGER, date_sent INTEGER, msg_box INTEGER, read INTEGER DEFAULT 0, m_id TEXT, sub TEXT,sub_cs INTEGER, ct_t TEXT, ct_l TEXT, exp INTEGER, m_cls TEXT, m_type INTEGER, v INTEGER, m_size INTEGER, pri INTEGER, rr INTEGER, rpt_a INTEGER, resp_st INTEGER, st INTEGER, tr_id TEXT, retr_st INTEGER, retr_txt TEXT, retr_txt_cs INTEGER, read_status INTEGER, ct_cls INTEGER, resp_txt TEXT, d_tm INTEGER, d_rpt INTEGER, locked INTEGER DEFAULT 0, spam_type INTEGER DEFAULT 0, seen INTEGER, sim_slot INTEGER, sim_imsi TEXT, deletable INTEGER, hidden INTEGER, app_id INTEGER, msg_id INTEGER, callback_set INTEGER, reserved INTEGER, text_only INTEGER, spam_report INTEGER, safe_message INTEGER, from_address TEXT, correlation_id TEXT, res_url TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0, linenum TEXT DEFAULT NULL);";
    private static final String CREATE_MULTI_LINE_STATUS_TABLE = "CREATE TABLE multilinestatus(_bufferdbid INTEGER PRIMARY KEY,linenum TEXT,messagetype INTEGER,initsync_cusor TEXT DEFAULT NULL,initsync_status INTEGER DEFAULT 0,initupload_status INTEGER DEFAULT 0,sim_imsi TEXT DEFAULT NULL, syncmessage_status INTEGER DEFAULT 0);";
    private static final String CREATE_RCS_MESSAGE_TABLE = "CREATE TABLE rcsimft(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,is_filetransfer INTEGER,direction INTEGER,chat_id TEXT,remote_uri TEXT,sender_alias TEXT,content_type TEXT,inserted_timestamp LONG,ext_info TEXT,body TEXT,notification_disposition_mask INTEGER,notification_status INTEGER DEFAULT 0,disposition_notification_status INTEGER DEFAULT 0,sent_timestamp LONG,delivered_timestamp LONG,displayed_timestamp LONG,message_type INTEGER,message_isslm INTEGER,status INTEGER,locked INTEGER DEFAULT 0,spam_type INTEGER DEFAULT 0,not_displayed_counter INTEGER,imdn_message_id TEXT, imdn_original_to TEXT, conversation_id TEXT, contribution_id TEXT, sim_imsi TEXT DEFAULT '',file_path TEXT,file_name TEXT,file_size LONG,file_transfer_id TEXT,state INTEGER,reason INTEGER,bytes_transf LONG,ft_status INTEGER,thumbnail_path TEXT,is_resumable INTEGER,transfer_mech INTEGER DEFAULT 0,data_url TEXT,file_disposition INTEGER,playing_length INTEGER DEFAULT 0,request_message_id TEXT,is_resizable INTEGER DEFAULT 0, content_uri TEXT,thumbnail_uri TEXT,correlation_id TEXT, correlation_tag TEXT, res_url TEXT, parentfolder TEXT, payloadurl TEXT, payloadpartThumb TEXT, payloadpartThumb_filename TEXT, payloadpartFull TEXT, payloadencoding INTEGER DEFAULT 0, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, reference_id TEXT DEFAULT NULL,reference_type TEXT DEFAULT NULL,reference_value TEXT DEFAULT NULL,suggestion TEXT DEFAULT NULL,maap_traffic_type TEXT DEFAULT NULL,syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0, creator TEXT DEFAULT NULL,linenum TEXT DEFAULT NULL);";
    private static final String CREATE_RCS_NOTIFICATION_TABLE = "CREATE TABLE notification(_bufferdbid INTEGER PRIMARY KEY,id INTEGER,imdn_id TEXT, sender_uri TEXT,status INTEGER DEFAULT 0,timestamp LONG, res_url TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0,sim_imsi TEXT DEFAULT NULL);";
    private static final String CREATE_RCS_PARTICIPANT_TABLE = "CREATE TABLE participant(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,chat_id TEXT,status INTEGER,type INTEGER,uri TEXT,alias TEXT,sim_imsi TEXT DEFAULT NULL);";
    private static final String CREATE_RCS_SESSION_TABLE = "CREATE TABLE session(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,chat_id TEXT,own_sim_imsi TEXT,direction INTEGER, chat_type INTEGER, conversation_id TEXT, contribution_id TEXT, is_group_chat INTEGER,is_ft_group_chat INTEGER,status INTEGER,subject TEXT,is_muted INTEGER,max_participants_count INTEGER,imdn_notifications_availability INTEGER, session_uri TEXT DEFAULT NULL, preferred_uri TEXT DEFAULT NULL,linenum TEXT DEFAULT NULL,icon_path TEXT,icon_participant TEXT,sim_imsi TEXT DEFAULT NULL,subject_timestamp LONG,inserted_time_stamp LONG,icon_timestamp TEXT,subject_participant TEXT DEFAULT NULL,created_by TEXT DEFAULT NULL,invited_by TEXT DEFAULT NULL,syncdirection INTEGER DEFAULT 0,syncaction INTEGER DEFAULT 0);";
    private static final String CREATE_SMS_MESSAGE_TABLE = "CREATE TABLE sms(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,thread_id INTEGER, address TEXT, person INTEGER, date INTEGER, date_sent INTEGER, protocol INTEGER, read INTEGER DEFAULT 0, status INTEGER, type INTEGER, reply_path_present INTEGER, subject TEXT, body TEXT, service_center TEXT, locked INTEGER DEFAULT 0, spam_type INTEGER DEFAULT 0, error_code INTEGER, seen INTEGER, deletable INTEGER, sim_slot INTEGER, sim_imsi TEXT, hidden INTEGER, group_id INTEGER, group_type INTEGER, delivery_date INTEGER, app_id INTEGER, msg_id INTEGER, callback_number TEXT, reserved INTEGER, pri INTEGER, teleservice_id INTEGER, link_url TEXT, svc_cmd INTEGER, svc_cmd_content TEXT, roam_pending INTEGER, spam_report INTEGER, safe_message INTEGER, from_address TEXT, group_cotag TEXT, correlation_tag TEXT, res_url TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0, linenum TEXT DEFAULT NULL);";
    private static final String CREATE_SUMMARY_TABLE = "CREATE TABLE summarytable(_bufferdbid INTEGER PRIMARY KEY,messagetype INTEGER DEFAULT 0, correlation_id TEXT, correlation_tag TEXT, res_url TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, linenum TEXT, lastmodseq INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0,sim_imsi TEXT DEFAULT NULL);";
    private static final String CREATE_VVM_GREETING_TABLE = "CREATE TABLE vvm_greeting(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,fileName TEXT,mimeType TEXT,size INTEGER,filepath TEXT,flags INTEGER,duration INTEGER,account_number TEXT,messageId TEXT,greetingtype INTEGER,error_message TEXT,uploadstatus INTEGER,correlation_id TEXT, res_url TEXT, payloadurl TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, linenum TEXT, sim_imsi TEXT DEFAULT NULL, syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0, content_uri TEXT);";
    private static final String CREATE_VVM_MESSAGES_TABLE = "CREATE TABLE vvm_messages(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,timeStamp INTEGER,text TEXT,flagRead INTEGER,flags INTEGER,messageId TEXT,sender TEXT,recipient TEXT,fileName TEXT,mimeType INTEGER,size INTEGER,filepath TEXT,messageKey TEXT,importance TEXT, sensitivity TEXT, sim_imsi TEXT DEFAULT NULL, correlation_id TEXT, res_url TEXT, payloadurl TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, linenum TEXT, syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0, uploadstatus INTEGER,v2t_language TEXT,content_uri TEXT);";
    private static final String CREATE_VVM_PIN_TABLE = "CREATE TABLE vvm_pin(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,oldpwd TEXT, newpwd TEXT,error_message TEXT,uploadstatus INTEGER,res_url TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, linenum TEXT, sim_imsi TEXT DEFAULT NULL, syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0);";
    private static final String CREATE_VVM_PROFILE_TABLE = "CREATE TABLE vvm_profile(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,cos INTEGER,greeting_type TEXT,status TEXT NOT NULL DEFAULT U, password TEXT,nut TEXT,language TEXT,isblocked TEXT,vvmon TEXT,email_addr1 TEXT,email_addr2 TEXT,v2t_language TEXT,line_number TEXT,sim_imsi TEXT DEFAULT '',user_authenticated INTEGER NOT NULL DEFAULT 0, profile_changetype INTEGER, error_message TEXT,uploadstatus INTEGER,res_url TEXT, parentfolder TEXT, flagresourceurl TEXT, path TEXT, parentfolderpath TEXT, lastmodseq INTEGER DEFAULT 0, linenum TEXT, syncdirection INTEGER DEFAULT 0, syncaction INTEGER DEFAULT 0, v2t_sms TEXT,v2t_email TEXT);";
    private static final String CREATE_VVM_QUOTA_TABLE = "CREATE TABLE vvm_quota(_bufferdbid INTEGER PRIMARY KEY,_id INTEGER,TotalStorage LONG DEFAULT 0,OccupiedStorage LONG DEFAULT 0,VMMessagesQuota INTEGER DEFAULT 0,VMOccupiedMessages INTEGER DEFAULT 0,LastUpdated LONG DEFAULT 0,linenum TEXT, sim_imsi TEXT DEFAULT NULL, syncaction INTEGER DEFAULT 0);";
    private static final String DATABASE_NAME = "cloudmessagebuffertable.db";
    private static final String DATABASE_NAME2 = "cloudmessagebuffertable2.db";
    private static final int DATABASE_VERSION = 27;
    private static final String FAX_MESSAGE_TABLE = "fax_message";
    private static final String FAX_RECEIVER_TABLE = "fax_receivers";
    private static final String MMSADDR_MESSAGE_TABLE = "addr";
    private static final String MMSPART_MESSAGE_TABLE = "part";
    private static final String MMSPDU_MESSAGE_TABLE = "pdu";
    private static final String MULTI_LINE_STATUS_TABLE = "multilinestatus";
    private static final String NOTIFICATION_TABLE = "notification";
    private static final String PARTICIPANT_TABLE = "participant";
    private static final String RCS_MESSAGE_TABLE = "rcsimft";
    private static final String SESSION_TABLE = "session";
    private static final String SMS_MESSAGE_TABLE = "sms";
    private static final String SUMMARY_TABLE = "summarytable";
    private static final String VVM_GREETING_TABLE = "vvm_greeting";
    private static final String VVM_MESSAGES_TABLE = "vvm_messages";
    private static final String VVM_PIN_TABLE = "vvm_pin";
    private static final String VVM_PROFILE_TABLE = "vvm_profile";
    private static final String VVM_QUOTA_TABLE = "vvm_quota";
    private static boolean mDualDBRequired = false;
    private static CloudMessageBufferDBPersister sInstance;
    private static CloudMessageBufferDBPersister sInstance2;
    private String LOG_TAG;
    String database_name;
    public final Context mContext;
    private final DatabaseHelper mDatabaseHelper;
    private final Map<Integer, String> mMapUriTableName;

    private CloudMessageBufferDBPersister(Context context) {
        this.LOG_TAG = CloudMessageBufferDBPersister.class.getSimpleName();
        HashMap hashMap = new HashMap();
        this.mMapUriTableName = hashMap;
        this.database_name = DATABASE_NAME;
        Log.d(this.LOG_TAG, "onCreate()");
        this.mContext = context;
        this.database_name = DATABASE_NAME;
        this.mDatabaseHelper = new DatabaseHelper(context);
        hashMap.put(7, "summarytable");
        hashMap.put(3, SMS_MESSAGE_TABLE);
        hashMap.put(5, MMSADDR_MESSAGE_TABLE);
        hashMap.put(4, MMSPDU_MESSAGE_TABLE);
        hashMap.put(6, MMSPART_MESSAGE_TABLE);
        hashMap.put(1, RCS_MESSAGE_TABLE);
        hashMap.put(2, "participant");
        hashMap.put(13, "notification");
        hashMap.put(10, "session");
        hashMap.put(17, VVM_MESSAGES_TABLE);
        hashMap.put(19, VVM_PIN_TABLE);
        hashMap.put(18, VVM_GREETING_TABLE);
        hashMap.put(20, VVM_PROFILE_TABLE);
        hashMap.put(36, VVM_QUOTA_TABLE);
        hashMap.put(23, "multilinestatus");
        hashMap.put(31, SMS_MESSAGE_TABLE);
        hashMap.put(32, MMSPDU_MESSAGE_TABLE);
    }

    private CloudMessageBufferDBPersister(Context context, int i) {
        this.LOG_TAG = CloudMessageBufferDBPersister.class.getSimpleName();
        HashMap hashMap = new HashMap();
        this.mMapUriTableName = hashMap;
        this.database_name = DATABASE_NAME;
        String str = this.LOG_TAG + "[" + i + "]";
        this.LOG_TAG = str;
        Log.d(str, "onCreate()");
        this.mContext = context;
        if (i == 1) {
            this.database_name = DATABASE_NAME2;
        } else if (i == 0) {
            this.database_name = DATABASE_NAME;
        }
        this.mDatabaseHelper = new DatabaseHelper(context);
        hashMap.put(7, "summarytable");
        hashMap.put(3, SMS_MESSAGE_TABLE);
        hashMap.put(5, MMSADDR_MESSAGE_TABLE);
        hashMap.put(4, MMSPDU_MESSAGE_TABLE);
        hashMap.put(6, MMSPART_MESSAGE_TABLE);
        hashMap.put(1, RCS_MESSAGE_TABLE);
        hashMap.put(2, "participant");
        hashMap.put(13, "notification");
        hashMap.put(10, "session");
        hashMap.put(17, VVM_MESSAGES_TABLE);
        hashMap.put(19, VVM_PIN_TABLE);
        hashMap.put(18, VVM_GREETING_TABLE);
        hashMap.put(20, VVM_PROFILE_TABLE);
        hashMap.put(36, VVM_QUOTA_TABLE);
        hashMap.put(23, "multilinestatus");
        hashMap.put(31, SMS_MESSAGE_TABLE);
        hashMap.put(32, MMSPDU_MESSAGE_TABLE);
    }

    public static synchronized CloudMessageBufferDBPersister getInstance(Context context) {
        CloudMessageBufferDBPersister cloudMessageBufferDBPersister;
        synchronized (CloudMessageBufferDBPersister.class) {
            if (sInstance == null) {
                sInstance = new CloudMessageBufferDBPersister(context);
            }
            cloudMessageBufferDBPersister = sInstance;
        }
        return cloudMessageBufferDBPersister;
    }

    public static synchronized CloudMessageBufferDBPersister getInstance(Context context, int i, boolean z) {
        synchronized (CloudMessageBufferDBPersister.class) {
            if (z) {
                mDualDBRequired = z;
            }
            Log.i("CloudMessageBufferDBPersister", "getInstance mDualDBRequired: " + mDualDBRequired + ", slotID: " + i);
            if (mDualDBRequired && i == 1) {
                if (sInstance2 == null) {
                    sInstance2 = new CloudMessageBufferDBPersister(context, 1);
                }
                return sInstance2;
            }
            if (sInstance == null) {
                sInstance = new CloudMessageBufferDBPersister(context, 0);
            }
            return sInstance;
        }
    }

    public void load() {
        Log.d(this.LOG_TAG, "load");
        this.mDatabaseHelper.getReadableDatabase().close();
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        private final Context mContext;

        public DatabaseHelper(Context context) {
            super(context, CloudMessageBufferDBPersister.this.database_name, (SQLiteDatabase.CursorFactory) null, 27);
            this.mContext = context;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "DatabaseHelper onCreate()");
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_SMS_MESSAGE_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_MMSPDU_MESSAGE_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_MMSADDR_MESSAGE_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_MMSPART_MESSAGE_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_RCS_MESSAGE_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_RCS_PARTICIPANT_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_RCS_SESSION_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_RCS_NOTIFICATION_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_SUMMARY_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_MULTI_LINE_STATUS_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_VVM_MESSAGES_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_VVM_PIN_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_VVM_GREETING_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_VVM_PROFILE_TABLE);
            sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_VVM_QUOTA_TABLE);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "db upgrade: oldVersion=" + i + " newVersion=" + i2);
            String str = "";
            if (i == 1) {
                try {
                    dropAllAndReCreateTables(sQLiteDatabase);
                } catch (SQLiteException e) {
                    str = "version: " + i + " " + e;
                }
                i = 27;
            }
            if (i == 2) {
                i = 3;
            }
            if (i == 3) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN contribution_id  TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN conversation_id  TEXT DEFAULT NULL;");
                } catch (SQLiteException e2) {
                    str = str + " version:" + i + " " + e2;
                }
                i = 4;
            }
            if (i == 4) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN chat_type INTEGER;");
                } catch (SQLiteException e3) {
                    str = str + " version:" + i + " " + e3;
                }
                i = 5;
            }
            if (i == 5) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN icon_path TEXT;");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN icon_participant TEXT;");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN icon_timestamp TEXT;");
                } catch (SQLiteException e4) {
                    str = str + " version:" + i + " " + e4;
                }
                i = 6;
            }
            if (i == 6) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN payloadpartThumb_filename TEXT;");
                } catch (SQLiteException e5) {
                    str = str + " version:" + i + " " + e5;
                }
                i = 7;
            }
            if (i == 7) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN sim_imsi TEXT DEFAULT '';");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                } catch (SQLiteException e6) {
                    str = str + " version:" + i + " " + e6;
                }
                i = 8;
            }
            if (i == 8) {
                String subscriberId = ((TelephonyManager) this.mContext.getSystemService(PhoneConstants.PHONE_KEY)).getSubscriberId();
                Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "set sim imsi : " + subscriberId);
                try {
                    sQLiteDatabase.execSQL("UPDATE rcsimft SET sim_imsi = " + subscriberId + " WHERE sim_imsi IS '' ");
                } catch (SQLException unused) {
                    str = str + " version:" + i + " message sim imsi update failed ";
                }
                try {
                    sQLiteDatabase.execSQL("UPDATE session SET sim_imsi = " + subscriberId + " WHERE sim_imsi IS NULL");
                } catch (SQLException unused2) {
                    str = str + " version:" + i + " session sim imsi update failed ";
                }
                CloudMessageRCSStorageAdapter cloudMessageRCSStorageAdapter = new CloudMessageRCSStorageAdapter(this.mContext);
                Cursor queryAllSession = cloudMessageRCSStorageAdapter.queryAllSession(new String[]{"chat_id", "sim_imsi"});
                try {
                    Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "queryAllSession.");
                    if (queryAllSession != null) {
                        while (queryAllSession.moveToNext()) {
                            String string = queryAllSession.getString(queryAllSession.getColumnIndexOrThrow("chat_id"));
                            String string2 = queryAllSession.getString(queryAllSession.getColumnIndexOrThrow("sim_imsi"));
                            if (!TextUtils.isEmpty(string) && TextUtils.isEmpty(string2)) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("sim_imsi", subscriberId);
                                cloudMessageRCSStorageAdapter.updateSessionFromBufferDbToRCSDb(string, contentValues);
                            }
                        }
                    }
                    if (queryAllSession != null) {
                        queryAllSession.close();
                    }
                    Cursor queryAllMessage = cloudMessageRCSStorageAdapter.queryAllMessage(new String[]{"_id", "sim_imsi"});
                    try {
                        Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "queryAllMessage.");
                        if (queryAllMessage != null) {
                            while (queryAllMessage.moveToNext()) {
                                int i3 = queryAllMessage.getInt(queryAllMessage.getColumnIndexOrThrow("_id"));
                                if (TextUtils.isEmpty(queryAllMessage.getString(queryAllMessage.getColumnIndexOrThrow("sim_imsi")))) {
                                    ContentValues contentValues2 = new ContentValues();
                                    contentValues2.put("sim_imsi", subscriberId);
                                    cloudMessageRCSStorageAdapter.updateMessageFromBufferDb(i3, contentValues2);
                                }
                            }
                        }
                        if (queryAllMessage != null) {
                            queryAllMessage.close();
                        }
                        Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "upgrade sim imsi done.");
                        if (CloudMessageService.getClientByID(0) != null) {
                            CloudMessageService.getClientByID(0).getPrerenceManager().saveMigrateSuccessFlag(true);
                        } else if (CloudMessageService.getClientByID(1) != null) {
                            CloudMessageService.getClientByID(1).getPrerenceManager().saveMigrateSuccessFlag(true);
                        }
                        i = 9;
                    } catch (Throwable th) {
                        if (queryAllMessage != null) {
                            try {
                                queryAllMessage.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    if (queryAllSession != null) {
                        try {
                            queryAllSession.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            }
            if (i == 9) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN content_uri TEXT DEFAULT '';");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN thumbnail_uri TEXT DEFAULT '';");
                    sQLiteDatabase.execSQL("ALTER TABLE part ADD COLUMN content_uri TEXT DEFAULT '';");
                } catch (SQLiteException e7) {
                    str = str + " version:" + i + " " + e7;
                }
                i = 10;
            }
            if (i == 10) {
                try {
                    sQLiteDatabase.execSQL(CloudMessageBufferDBPersister.CREATE_VVM_QUOTA_TABLE);
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_messages ADD COLUMN importance TEXT DEFAULT ''");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_messages ADD COLUMN sensitivity TEXT DEFAULT ''");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_profile ADD COLUMN sim_imsi TEXT DEFAULT ''");
                } catch (SQLiteException e8) {
                    str = str + " version:" + i + " " + e8;
                }
                i = 11;
            }
            if (i == 11) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_quota ADD COLUMN LastUpdated LONG DEFAULT 0");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_quota ADD COLUMN syncaction INTEGER DEFAULT 0");
                } catch (SQLiteException e9) {
                    str = str + " version:" + i + " " + e9;
                }
                i = 12;
            }
            if (i == 12) {
                try {
                    sQLiteDatabase.execSQL("DROP TABLE calllog");
                    sQLiteDatabase.execSQL("DROP TABLE fax_message");
                    sQLiteDatabase.execSQL("DROP TABLE fax_receivers");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_quota ADD COLUMN syncaction INTEGER DEFAULT 0");
                } catch (SQLiteException e10) {
                    str = str + " version:" + i + " " + e10;
                }
                i = 13;
            }
            if (i == 13) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_messages ADD COLUMN content_uri TEXT DEFAULT '';");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_greeting ADD COLUMN content_uri TEXT DEFAULT '';");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN content_uri TEXT DEFAULT '';");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN thumbnail_uri TEXT DEFAULT '';");
                    sQLiteDatabase.execSQL("ALTER TABLE part ADD COLUMN content_uri TEXT DEFAULT '';");
                } catch (SQLiteException e11) {
                    str = str + " version:" + i + " " + e11;
                }
                i = 14;
            }
            if (i == 14) {
                try {
                    if (Util.isColumnNotExists(sQLiteDatabase, CloudMessageBufferDBPersister.RCS_MESSAGE_TABLE, CloudMessageProviderContract.BufferDBExtensionBase.FILE_URI)) {
                        sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN content_uri TEXT DEFAULT '';");
                    }
                    if (Util.isColumnNotExists(sQLiteDatabase, CloudMessageBufferDBPersister.RCS_MESSAGE_TABLE, CloudMessageProviderContract.BufferDBExtensionBase.THUMBNAIL_URI)) {
                        sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN thumbnail_uri TEXT DEFAULT '';");
                    }
                    if (Util.isColumnNotExists(sQLiteDatabase, CloudMessageBufferDBPersister.MMSPART_MESSAGE_TABLE, CloudMessageProviderContract.BufferDBExtensionBase.FILE_URI)) {
                        sQLiteDatabase.execSQL("ALTER TABLE part ADD COLUMN content_uri TEXT DEFAULT '';");
                    }
                } catch (SQLiteException e12) {
                    str = str + " version:" + i + " " + e12;
                }
                i = 15;
            }
            if (i == 15) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_messages ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_greeting ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_pin ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_quota ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_quota ADD COLUMN linenum TEXT DEFAULT '';");
                } catch (SQLiteException e13) {
                    str = str + " version:" + i + " " + e13;
                }
                i = 16;
            }
            if (i == 16) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_profile ADD COLUMN v2t_language TEXT DEFAULT NULL;");
                } catch (SQLiteException e14) {
                    str = str + " version:" + i + " " + e14;
                }
                i = 17;
            }
            if (i == 17) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN subject_timestamp LONG;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN creator TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN inserted_time_stamp LONG;");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN subject_participant TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN syncdirection INTEGER DEFAULT 0;");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN syncaction INTEGER DEFAULT 0;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN reference_id TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN reference_type TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN reference_value TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN suggestion TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN maap_traffic_type TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN file_disposition INTEGER DEFAULT 0;");
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN playing_length INTEGER DEFAULT 0;");
                } catch (SQLiteException e15) {
                    str = str + " version:" + i + " " + e15;
                }
                i = 18;
            }
            if (i == 18) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE multilinestatus ADD COLUMN initupload_status INTEGER DEFAULT 0;");
                } catch (SQLiteException e16) {
                    str = str + " version:" + i + " " + e16;
                }
                i = 19;
            }
            if (i == 19) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE notification ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE participant ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE multilinestatus ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE summarytable ADD COLUMN sim_imsi TEXT DEFAULT NULL;");
                } catch (SQLiteException e17) {
                    str = str + " version:" + i + " " + e17;
                }
                i = 20;
            }
            if (i == 20) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_profile ADD COLUMN v2t_sms TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_profile ADD COLUMN v2t_email TEXT DEFAULT NULL;");
                } catch (SQLiteException e18) {
                    str = str + " version:" + i + " " + e18;
                }
                i = 21;
            }
            if (i == 21) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_messages ADD COLUMN v2t_language TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE vvm_messages ADD COLUMN uploadstatus INTEGER DEFAULT 0;");
                } catch (SQLiteException e19) {
                    str = str + " version:" + i + " " + e19;
                }
                i = 22;
            }
            if (i == 22) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE sms ADD COLUMN group_cotag TEXT DEFAULT NULL;");
                } catch (SQLiteException e20) {
                    str = str + " version:" + i + " " + e20;
                }
                i = 23;
            }
            if (i == 23) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN invited_by TEXT DEFAULT NULL;");
                    sQLiteDatabase.execSQL("ALTER TABLE session ADD COLUMN created_by TEXT DEFAULT NULL;");
                } catch (SQLiteException e21) {
                    str = str + " version:" + i + " " + e21;
                }
                i = 24;
            }
            if (i == 24) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN locked INTEGER DEFAULT 0;");
                } catch (SQLiteException e22) {
                    str = str + " version:" + i + " " + e22;
                }
                i = 25;
            }
            if (i == 25) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE multilinestatus ADD COLUMN syncmessage_status INTEGER DEFAULT 0;");
                } catch (SQLException e23) {
                    str = str + " version:" + i + " " + e23;
                }
                i = 26;
            }
            if (i == 26) {
                try {
                    sQLiteDatabase.execSQL("ALTER TABLE rcsimft ADD COLUMN spam_type INTEGER DEFAULT 0;");
                    sQLiteDatabase.execSQL("ALTER TABLE pdu ADD COLUMN spam_type INTEGER DEFAULT 0;");
                    sQLiteDatabase.execSQL("ALTER TABLE sms ADD COLUMN spam_type INTEGER DEFAULT 0;");
                } catch (SQLiteException e24) {
                    str = str + " version:" + i + " " + e24;
                }
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Log.e(CloudMessageBufferDBPersister.this.LOG_TAG, "OnUpgrade error: " + str);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            String sQLiteException;
            try {
                dropAllAndReCreateTables(sQLiteDatabase);
                sQLiteException = null;
            } catch (SQLiteException e) {
                sQLiteException = e.toString();
            }
            Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "db downgrade: oldVersion=" + i + " newVersion=" + i2 + " error: " + sQLiteException);
        }

        private void dropAllAndReCreateTables(SQLiteDatabase sQLiteDatabase) {
            Log.d(CloudMessageBufferDBPersister.this.LOG_TAG, "dropAllAndReCreateTables");
            sQLiteDatabase.execSQL("DROP TABLE sms");
            sQLiteDatabase.execSQL("DROP TABLE pdu");
            sQLiteDatabase.execSQL("DROP TABLE addr");
            sQLiteDatabase.execSQL("DROP TABLE part");
            sQLiteDatabase.execSQL("DROP TABLE rcsimft");
            sQLiteDatabase.execSQL("DROP TABLE participant");
            sQLiteDatabase.execSQL("DROP TABLE session");
            sQLiteDatabase.execSQL("DROP TABLE notification");
            sQLiteDatabase.execSQL("DROP TABLE summarytable");
            sQLiteDatabase.execSQL("DROP TABLE multilinestatus");
            sQLiteDatabase.execSQL("DROP TABLE vvm_messages");
            sQLiteDatabase.execSQL("DROP TABLE vvm_pin");
            sQLiteDatabase.execSQL("DROP TABLE vvm_greeting");
            sQLiteDatabase.execSQL("DROP TABLE vvm_profile");
            sQLiteDatabase.execSQL("DROP TABLE vvm_quota");
            onCreate(sQLiteDatabase);
        }
    }

    public Cursor queryRCSMessages(String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query(RCS_MESSAGE_TABLE, strArr, str, strArr2, null, null, str2);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    public Cursor queryRCSImdnMessages(String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query("notification", strArr, str, strArr2, null, null, str2);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    public int updateRCSTable(ContentValues contentValues, String str, String[] strArr) {
        SQLiteDatabase database = getDatabase();
        int i = 0;
        if (database == null) {
            return 0;
        }
        database.beginTransaction();
        try {
            try {
                i = database.update(RCS_MESSAGE_TABLE, contentValues, str, strArr);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return i;
        } finally {
            database.endTransaction();
        }
    }

    public int updateRCSSessionTable(ContentValues contentValues, String str, String[] strArr) {
        SQLiteDatabase database = getDatabase();
        int i = 0;
        if (database == null) {
            return 0;
        }
        database.beginTransaction();
        try {
            try {
                i = database.update("session", contentValues, str, strArr);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return i;
        } finally {
            database.endTransaction();
        }
    }

    public int updateRCSParticipantsTable(ContentValues contentValues, String str, String[] strArr) {
        SQLiteDatabase database = getDatabase();
        int i = 0;
        if (database == null) {
            return 0;
        }
        database.beginTransaction();
        try {
            try {
                i = database.update("participant", contentValues, str, strArr);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return i;
        } finally {
            database.endTransaction();
        }
    }

    public Cursor querySMSMessages(String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query(SMS_MESSAGE_TABLE, strArr, str, strArr2, null, null, str2);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    public Cursor queryMMSPDUMessages(String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query(MMSPDU_MESSAGE_TABLE, strArr, str, strArr2, null, null, str2);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    public Cursor queryMMSPARTMessages(String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query(MMSPART_MESSAGE_TABLE, strArr, str, strArr2, null, null, str2);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    public Cursor querySummaryTable(String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query("summarytable", strArr, str, strArr2, null, null, str2);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    public int deleteTable(int i, String str, String[] strArr) {
        String str2 = this.mMapUriTableName.get(Integer.valueOf(i));
        SQLiteDatabase database = getDatabase();
        int i2 = 0;
        if (database == null) {
            return 0;
        }
        database.beginTransaction();
        try {
            try {
                i2 = database.delete(str2, str, strArr);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return i2;
        } finally {
            database.endTransaction();
        }
    }

    public int updateTable(int i, ContentValues contentValues, String str, String[] strArr) {
        String str2 = this.mMapUriTableName.get(Integer.valueOf(i));
        SQLiteDatabase database = getDatabase();
        int i2 = 0;
        if (database == null) {
            return 0;
        }
        database.beginTransaction();
        try {
            try {
                i2 = database.update(str2, contentValues, str, strArr);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return i2;
        } finally {
            database.endTransaction();
        }
    }

    public long insertTable(int i, ContentValues contentValues) {
        return commonInsertTable(i, contentValues);
    }

    public long insertDeviceMsgToBuffer(int i, ContentValues contentValues) {
        return commonInsertTable(i, contentValues);
    }

    private long commonInsertTable(int i, ContentValues contentValues) {
        String str = this.mMapUriTableName.get(Integer.valueOf(i));
        SQLiteDatabase database = getDatabase();
        long j = 0;
        if (database == null) {
            return 0L;
        }
        database.beginTransaction();
        try {
            try {
                j = database.insertOrThrow(str, null, contentValues);
                database.setTransactionSuccessful();
            } catch (SQLiteFullException e) {
                Log.e(this.LOG_TAG, "SQLiteFullException insertTable: " + e);
            } catch (SQLException e2) {
                Log.e(this.LOG_TAG, "SQL exception while insertTable " + e2);
            }
            return j;
        } finally {
            database.endTransaction();
        }
    }

    public Cursor queryTable(Uri uri, int i, String[] strArr, String str, String str2) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            Log.e(this.LOG_TAG, "buildMessageCursor: No last segment.");
            return null;
        }
        return commonQueryTable(i, strArr, str, new String[]{lastPathSegment}, str2);
    }

    public Cursor queryTable(int i, String[] strArr, String str, String[] strArr2, String str2) {
        return commonQueryTable(i, strArr, str, strArr2, str2);
    }

    private Cursor commonQueryTable(int i, String[] strArr, String str, String[] strArr2, String str2) {
        String str3 = this.mMapUriTableName.get(Integer.valueOf(i));
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query(str3, strArr, str, strArr2, null, null, str2);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying messages " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    private SQLiteDatabase getDatabase() {
        try {
            return this.mDatabaseHelper.getWritableDatabase();
        } catch (SQLiteDiskIOException e) {
            Log.e(this.LOG_TAG, "SQLiteDiskIOException : " + e);
            return null;
        }
    }

    public Cursor queryTablewithBufferDbId(int i, long j) {
        return queryTable(i, (String[]) null, "_bufferdbid=?", new String[]{Long.toString(j)}, (String) null);
    }

    public Cursor queryTablewithResUrl(int i, String str) {
        String extractObjIdFromResUrl = Util.extractObjIdFromResUrl(str);
        return queryTable(i, (String[]) null, "res_url GLOB ? AND linenum=?", new String[]{"*" + extractObjIdFromResUrl, Util.getLineTelUriFromObjUrl(str)}, (String) null);
    }

    public int deleteTablewithResUrl(int i, String str) {
        String extractObjIdFromResUrl = Util.extractObjIdFromResUrl(str);
        return deleteTable(i, "res_url GLOB ? AND linenum=?", new String[]{"*" + extractObjIdFromResUrl, Util.getLineTelUriFromObjUrl(str)});
    }

    public int deleteTablewithBufferDbId(int i, long j) {
        return deleteTable(i, "_bufferdbid=?", new String[]{Long.toString(j)});
    }

    public Cursor queryRCSImdnUseImdnId(String str) {
        return queryTable(13, (String[]) null, "imdn_id=?", new String[]{str}, (String) null);
    }

    public Cursor queryRCSMessageUsingImdnId(String str) {
        return queryTable(1, (String[]) null, "imdn_message_id=?", new String[]{str}, (String) null);
    }

    public Cursor queryRCSImdnUseImdnIdAndTelUri(String str, String str2) {
        return queryTable(13, (String[]) null, "imdn_id=? AND sender_uri=?", new String[]{str, str2}, (String) null);
    }

    public void cleanAllBufferDB() {
        Log.d(this.LOG_TAG, "cleanAllBufferDB");
        SQLiteDatabase database = getDatabase();
        if (database == null) {
            return;
        }
        database.beginTransaction();
        try {
            try {
                database.execSQL("delete from sms");
                database.execSQL("delete from pdu");
                database.execSQL("delete from addr");
                database.execSQL("delete from part");
                database.execSQL("delete from session");
                database.execSQL("delete from rcsimft");
                database.execSQL("delete from participant");
                database.execSQL("delete from notification");
                database.execSQL("delete from multilinestatus");
                database.execSQL("delete from vvm_messages");
                database.execSQL("delete from vvm_pin");
                database.execSQL("delete from vvm_greeting");
                database.execSQL("delete from vvm_profile");
                database.execSQL("delete from vvm_quota");
                database.execSQL("delete from summarytable");
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while deleting messages " + e);
            }
        } finally {
            database.endTransaction();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0148 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0149  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.database.Cursor querySessionByParticipants(java.util.Set<com.sec.ims.util.ImsUri> r16, com.sec.internal.constants.ims.servicemodules.im.ChatData.ChatType r17, com.sec.ims.util.ImsUri r18) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.cmstore.adapters.CloudMessageBufferDBPersister.querySessionByParticipants(java.util.Set, com.sec.internal.constants.ims.servicemodules.im.ChatData$ChatType, com.sec.ims.util.ImsUri):android.database.Cursor");
    }

    public Cursor querySessionByConversationId(String str) {
        Log.d(this.LOG_TAG, "querySessionByConversationId ConversationId = " + str);
        return querySession("conversation_id=?", new String[]{str});
    }

    private boolean matchPreferredUri(ImsUri imsUri, ImsUri imsUri2) {
        if (imsUri == null && imsUri2 == null) {
            return true;
        }
        return (imsUri == null || imsUri.equals(ImsUri.EMPTY) || !imsUri.equals(imsUri2)) ? false : true;
    }

    private Cursor querySession(String str, String[] strArr) {
        SQLiteDatabase database = getDatabase();
        Cursor cursor = null;
        if (database == null) {
            return null;
        }
        database.beginTransaction();
        try {
            try {
                cursor = database.query("session", null, str, strArr, null, null, null);
                database.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e(this.LOG_TAG, "SQL exception while querying session. " + e);
            }
            return cursor;
        } finally {
            database.endTransaction();
        }
    }

    public Cursor querySessionByChatId(String str) {
        Log.d(this.LOG_TAG, "querySessionByChatId: " + str);
        return querySession("chat_id=?", new String[]{str});
    }

    public Cursor queryGroupSession(String str) {
        return querySession("sim_imsi=? AND is_group_chat=? AND syncdirection=? AND status!=?", new String[]{str, "1", String.valueOf(CloudMessageBufferDBConstants.DirectionFlag.ToSendCloud.getId()), "-1"});
    }

    public Cursor queryOneToOneSession(String str) {
        return querySession("sim_imsi=? AND is_group_chat=?", new String[]{str, "0"});
    }

    public Cursor queryParticipant(String str) {
        return queryTable(2, (String[]) null, "chat_id=?", new String[]{str}, (String) null);
    }

    public Cursor queryParticipant(String str, String str2) {
        return queryTable(2, (String[]) null, "chat_id=? AND uri=?", new String[]{str, str2}, (String) null);
    }
}
