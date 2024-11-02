package com.samsung.android.knox.ddar;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import com.samsung.android.knox.dar.ddar.securesession.SecureClient;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class DualDARManager {
    private static final boolean DEBUG = "eng".equals(Build.TYPE);
    public static final String DUALDAR_AGENT = "KNOXCORE_PROXY_AGENT";
    public static final String DUALDAR_MGR_SERVICE = "DUALDAR_MGR_SERVICE";
    private static final String DUAL_DAR_CLIENT = "DUAL_DAR_CLIENT";
    public static final String FETCH_DUMPSTATE_REQUEST = "FETCH_DUMPSTATE_REQUEST";
    public static final String GET_CLIENT_VERSION_REQUEST = "GET_CLIENT_VERSION_REQUEST";
    public static final String GET_DUALDAR_USERS_REQUEST = "GET_DUALDAR_USERS_REQUEST";
    public static final String INSTALL_CLIENT_LIBRARY_REQUEST = "INSTALL_CLIENT_LIBRARY_REQUEST";
    private static final int LOAD_RETRY_COUNT = 5;
    public static final String ON_AGENT_RECONNECTED = "ON_AGENT_RECONNECTED";
    public static final String PUSH_SECRET_REQUEST = "PUSH_SECRET_REQUEST";
    private static final String TAG = "DualDarManager";
    private static DualDARManager mInstance;
    private Context mContext;
    private SecureClient mSecureClientOutAPI;

    private DualDARManager(Context context) {
        this.mContext = context;
    }

    private synchronized boolean fetchDumpState(String str) {
        ParcelFileDescriptor parcelFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptor2;
        boolean z;
        FileInfo fileInfo = null;
        try {
            try {
                FileInfo fdFromPathForWrite = getFdFromPathForWrite(str);
                if (fdFromPathForWrite != null && fdFromPathForWrite.fd != null) {
                    boolean z2 = DEBUG;
                    if (z2) {
                        Log.d(TAG, "FS Log File fd=" + fdFromPathForWrite.fd.getFd());
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("FSLOG_FILE_INFO", fdFromPathForWrite);
                    Bundle processCommand = processCommand(FETCH_DUMPSTATE_REQUEST, bundle);
                    if (processCommand != null && processCommand.getBoolean("dual_dar_response", true)) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z) {
                        Log.e(TAG, "Fetch DumpState failed !!");
                        ParcelFileDescriptor parcelFileDescriptor3 = fdFromPathForWrite.fd;
                        if (parcelFileDescriptor3 != null) {
                            try {
                                parcelFileDescriptor3.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                    if (z2) {
                        Log.d(TAG, "Fetch DumpState Success");
                    }
                    ParcelFileDescriptor parcelFileDescriptor4 = fdFromPathForWrite.fd;
                    if (parcelFileDescriptor4 != null) {
                        try {
                            parcelFileDescriptor4.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return true;
                }
                Log.e(TAG, "Error: Not able to open the Log files");
                if (fdFromPathForWrite != null && (parcelFileDescriptor2 = fdFromPathForWrite.fd) != null) {
                    try {
                        parcelFileDescriptor2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return false;
            } catch (Exception e4) {
                Log.e(TAG, "Exception at fetchDumpState - " + e4.getMessage());
                e4.printStackTrace();
                if (0 != 0 && (parcelFileDescriptor = fileInfo.fd) != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                return false;
            }
        } finally {
        }
    }

    private FileInfo getFdFromAsset(String str) {
        AssetManager assets = this.mContext.getAssets();
        if (assets == null) {
            return null;
        }
        try {
            boolean z = DEBUG;
            if (z) {
                Log.d(TAG, "FileName: " + str);
            }
            AssetFileDescriptor openFd = assets.openFd(str);
            if (z) {
                Log.d(TAG, "Found FSRelay file: " + str);
            }
            if (openFd == null) {
                return null;
            }
            return new FileInfo(str, openFd.getParcelFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
        } catch (FileNotFoundException unused) {
            Log.e(TAG, "FSRelay file not found: " + str);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "general exception");
            e.printStackTrace();
            return null;
        }
    }

    private FileInfo getFdFromPath(String str) {
        if (str != null && !str.isEmpty()) {
            File file = new File(str);
            try {
                return new FileInfo(str.substring(str.lastIndexOf(47) + 1), ParcelFileDescriptor.open(file, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), 0L, file.length());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private FileInfo getFdFromPathForWrite(String str) {
        if (str != null && !str.isEmpty()) {
            File file = new File(str);
            try {
                return new FileInfo(str.substring(str.lastIndexOf(47) + 1), ParcelFileDescriptor.open(file, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT), 0L, file.length());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static synchronized DualDARManager getInstance(Context context) {
        DualDARManager dualDARManager;
        synchronized (DualDARManager.class) {
            if (mInstance == null) {
                mInstance = new DualDARManager(context);
            }
            dualDARManager = mInstance;
        }
        return dualDARManager;
    }

    private synchronized boolean installLibraryInternal(String str, List<String> list, boolean z) {
        ParcelFileDescriptor parcelFileDescriptor;
        FileInfo fdFromPath;
        ParcelFileDescriptor parcelFileDescriptor2;
        ArrayList arrayList = new ArrayList();
        FileInfo fileInfo = null;
        try {
            try {
                if (z) {
                    fdFromPath = getFdFromAsset(str);
                } else {
                    fdFromPath = getFdFromPath(str);
                }
                if (fdFromPath == null) {
                    if (fdFromPath != null && (parcelFileDescriptor2 = fdFromPath.fd) != null) {
                        try {
                            parcelFileDescriptor2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        try {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                ((FileInfo) it.next()).fd.close();
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return false;
                }
                if (fdFromPath.fd != null && fdFromPath.offset >= 0 && fdFromPath.len >= 0) {
                    if (DEBUG) {
                        Log.d(TAG, "FSRelay fd=" + fdFromPath.fd.getFd() + " offset=" + fdFromPath.offset + " len=" + fdFromPath.len);
                    }
                    if (list != null && !list.isEmpty()) {
                        for (String str2 : list) {
                            if (z) {
                                arrayList.add(getFdFromAsset(str2));
                            } else {
                                arrayList.add(getFdFromPath(str2));
                            }
                        }
                    }
                    if (DEBUG) {
                        Log.d(TAG, "load FSRelay " + str + " from app");
                    }
                    boolean z2 = false;
                    for (int i = 0; i < 5; i++) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("RELAY_FILE_INFO", fdFromPath);
                        if (!arrayList.isEmpty()) {
                            bundle.putParcelableArray("CRYPTO_FILE_INFO", (Parcelable[]) arrayList.toArray(new FileInfo[0]));
                        }
                        Bundle processCommand = processCommand(INSTALL_CLIENT_LIBRARY_REQUEST, bundle);
                        if (processCommand != null && processCommand.getBoolean("dual_dar_response", true)) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z2) {
                            break;
                        }
                        Log.e(TAG, "FSRelay loading failure: " + i);
                    }
                    if (!z2) {
                        Log.e(TAG, "FSRelay Load failed !!");
                        ParcelFileDescriptor parcelFileDescriptor3 = fdFromPath.fd;
                        if (parcelFileDescriptor3 != null) {
                            try {
                                parcelFileDescriptor3.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            try {
                                Iterator it2 = arrayList.iterator();
                                while (it2.hasNext()) {
                                    ((FileInfo) it2.next()).fd.close();
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        return false;
                    }
                    if (DEBUG) {
                        Log.d(TAG, "FSRelay Loaded Successfully");
                    }
                    ParcelFileDescriptor parcelFileDescriptor4 = fdFromPath.fd;
                    if (parcelFileDescriptor4 != null) {
                        try {
                            parcelFileDescriptor4.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        try {
                            Iterator it3 = arrayList.iterator();
                            while (it3.hasNext()) {
                                ((FileInfo) it3.next()).fd.close();
                            }
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    return true;
                }
                Log.e(TAG, "pfd is null");
                ParcelFileDescriptor parcelFileDescriptor5 = fdFromPath.fd;
                if (parcelFileDescriptor5 != null) {
                    try {
                        parcelFileDescriptor5.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                if (!arrayList.isEmpty()) {
                    try {
                        Iterator it4 = arrayList.iterator();
                        while (it4.hasNext()) {
                            ((FileInfo) it4.next()).fd.close();
                        }
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                return false;
            } finally {
            }
        } catch (Exception e9) {
            Log.e(TAG, "Exception at installLibrary - " + e9.getMessage());
            e9.printStackTrace();
            if (0 != 0 && (parcelFileDescriptor = fileInfo.fd) != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e10) {
                    e10.printStackTrace();
                }
            }
            if (!arrayList.isEmpty()) {
                try {
                    Iterator it5 = arrayList.iterator();
                    while (it5.hasNext()) {
                        ((FileInfo) it5.next()).fd.close();
                    }
                } catch (IOException e11) {
                    e11.printStackTrace();
                }
            }
            return false;
        }
    }

    private Bundle processCommand(String str, Bundle bundle) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessage(DUALDAR_AGENT, DUALDAR_MGR_SERVICE, str, bundle);
    }

    private Bundle processCommandSecurely(String str, Bundle bundle) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessageSecurely(DUALDAR_AGENT, DUALDAR_MGR_SERVICE, str, bundle, this.mSecureClientOutAPI);
    }

    public IBinder bindClient(IDualDARClient iDualDARClient) {
        return DualDarClientManager.getInstance(this.mContext, iDualDARClient);
    }

    public void establishSecureSession() {
        try {
            this.mSecureClientOutAPI = KnoxProxyManager.getInstance(this.mContext).initializeSecureSession(DUAL_DAR_CLIENT, DUALDAR_AGENT, DUALDAR_MGR_SERVICE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to establish secure connection from SDK to KnoxCore");
            e.printStackTrace();
        }
    }

    public synchronized List<Integer> getDualDARUsers() {
        Bundle processCommand = processCommand(GET_DUALDAR_USERS_REQUEST, null);
        if (processCommand == null) {
            Log.e(TAG, "Failed to get service");
            return null;
        }
        return processCommand.getIntegerArrayList("USERS");
    }

    public synchronized boolean getFileSystemLog(String str) {
        return fetchDumpState(str);
    }

    public String getInstalledClientLibraryVersion() {
        Bundle processCommand = processCommand(GET_CLIENT_VERSION_REQUEST, null);
        if (processCommand == null) {
            Log.e(TAG, "Failed to get service");
            return null;
        }
        return processCommand.getString("CLIENT_VERSION");
    }

    public synchronized boolean installLibrary(String str, List<String> list, boolean z) {
        return installLibraryInternal(str, list, z);
    }

    public void onAgentReconnected() {
        processCommand(ON_AGENT_RECONNECTED, null);
    }

    public synchronized void setSecret(int i, List<Secret> list) {
        boolean z;
        byte[] bArr;
        Log.d(TAG, "setSecret() ");
        try {
            Bundle bundle = new Bundle();
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (Secret secret : list) {
                try {
                    bArr = this.mSecureClientOutAPI.encryptMessageFor(DUALDAR_MGR_SERVICE, secret.data);
                } catch (Exception e) {
                    Log.e(TAG, "PUSH_SECRET_REQUEST failed to encrypt secrets");
                    e.printStackTrace();
                    bArr = null;
                }
                Wiper.wipe(secret.data);
                arrayList.add(new Secret(secret.alias, bArr));
            }
            list.clear();
            bundle.putParcelableArrayList("INNER_LAYER_SECRET", arrayList);
            bundle.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, i);
            Bundle processCommandSecurely = processCommandSecurely(PUSH_SECRET_REQUEST, bundle);
            Iterator<? extends Parcelable> it = arrayList.iterator();
            while (it.hasNext()) {
                Wiper.wipe(((Secret) it.next()).data);
            }
            arrayList.clear();
            if (processCommandSecurely != null) {
                z = processCommandSecurely.getBoolean("dual_dar_response", true);
            } else {
                z = false;
            }
            Log.d(TAG, "PUSH_SECRET_REQUEST response: " + z);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void teardownSecureSession() {
        try {
            KnoxProxyManager.getInstance(this.mContext).terminateSecureSession(this.mSecureClientOutAPI, DUALDAR_AGENT, DUALDAR_MGR_SERVICE);
            this.mSecureClientOutAPI = null;
        } catch (Exception e) {
            Log.e(TAG, "Failed to teardown secure connection from SDK to KnoxCore");
            e.printStackTrace();
        }
    }
}
