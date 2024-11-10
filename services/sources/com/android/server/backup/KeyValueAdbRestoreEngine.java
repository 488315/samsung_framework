package com.android.server.backup;

import android.app.IBackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FullBackup;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.backup.keyvalue.KeyValueBackupTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class KeyValueAdbRestoreEngine implements Runnable {
    public final IBackupAgent mAgent;
    public final UserBackupManagerService mBackupManagerService;
    public final File mDataDir;
    public final ParcelFileDescriptor mInFD;
    public final FileMetadata mInfo;
    public final int mToken;

    public KeyValueAdbRestoreEngine(UserBackupManagerService userBackupManagerService, File file, FileMetadata fileMetadata, ParcelFileDescriptor parcelFileDescriptor, IBackupAgent iBackupAgent, int i) {
        this.mBackupManagerService = userBackupManagerService;
        this.mDataDir = file;
        this.mInfo = fileMetadata;
        this.mInFD = parcelFileDescriptor;
        this.mAgent = iBackupAgent;
        this.mToken = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            invokeAgentForAdbRestore(this.mAgent, this.mInfo, prepareRestoreData(this.mInfo, this.mInFD));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final File prepareRestoreData(FileMetadata fileMetadata, ParcelFileDescriptor parcelFileDescriptor) {
        String str = fileMetadata.packageName;
        File file = new File(this.mDataDir, str + ".restore");
        File file2 = new File(this.mDataDir, str + ".sorted");
        FullBackup.restoreFile(parcelFileDescriptor, fileMetadata.size, fileMetadata.type, fileMetadata.mode, fileMetadata.mtime, file);
        sortKeyValueData(file, file2);
        return file2;
    }

    public final void invokeAgentForAdbRestore(IBackupAgent iBackupAgent, FileMetadata fileMetadata, File file) {
        String str = fileMetadata.packageName;
        try {
            iBackupAgent.doRestore(ParcelFileDescriptor.open(file, 268435456), fileMetadata.version, ParcelFileDescriptor.open(new File(this.mDataDir, str + KeyValueBackupTask.NEW_STATE_FILE_SUFFIX), 1006632960), this.mToken, this.mBackupManagerService.getBackupManagerBinder());
        } catch (RemoteException e) {
            Slog.e("KeyValueAdbRestoreEngine", "Exception calling doRestore on agent: " + e);
        } catch (IOException e2) {
            Slog.e("KeyValueAdbRestoreEngine", "Exception opening file. " + e2);
        }
    }

    public final void sortKeyValueData(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            copyKeysInLexicalOrder(new BackupDataInput(fileInputStream.getFD()), new BackupDataOutput(fileOutputStream.getFD()));
            IoUtils.closeQuietly(fileInputStream);
            IoUtils.closeQuietly(fileOutputStream);
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                IoUtils.closeQuietly(fileInputStream2);
            }
            if (fileOutputStream != null) {
                IoUtils.closeQuietly(fileOutputStream);
            }
            throw th;
        }
    }

    public final void copyKeysInLexicalOrder(BackupDataInput backupDataInput, BackupDataOutput backupDataOutput) {
        HashMap hashMap = new HashMap();
        while (backupDataInput.readNextHeader()) {
            String key = backupDataInput.getKey();
            int dataSize = backupDataInput.getDataSize();
            if (dataSize < 0) {
                backupDataInput.skipEntityData();
            } else {
                byte[] bArr = new byte[dataSize];
                backupDataInput.readEntityData(bArr, 0, dataSize);
                hashMap.put(key, bArr);
            }
        }
        ArrayList<String> arrayList = new ArrayList(hashMap.keySet());
        Collections.sort(arrayList);
        for (String str : arrayList) {
            byte[] bArr2 = (byte[]) hashMap.get(str);
            backupDataOutput.writeEntityHeader(str, bArr2.length);
            backupDataOutput.writeEntityData(bArr2, bArr2.length);
        }
    }
}
