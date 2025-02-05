package android.util;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import com.samsung.android.os.ReliableWrite;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class AtomicFile {
    private static final String LOG_TAG = "AtomicFile";
    private final File mBaseName;
    private SystemConfigFileCommitEventLogger mCommitEventLogger;
    private final File mLegacyBackupName;
    private final File mNewName;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AtomicFile(File baseName) {
        this(baseName, (SystemConfigFileCommitEventLogger) null);
    }

    public AtomicFile(File baseName, String commitTag) {
        this(baseName, new SystemConfigFileCommitEventLogger(commitTag));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public AtomicFile(File baseName, SystemConfigFileCommitEventLogger commitEventLogger) {
        this.mBaseName = baseName;
        this.mNewName = new File(baseName.getPath() + ".new");
        this.mLegacyBackupName = new File(baseName.getPath() + ".bak");
        this.mCommitEventLogger = commitEventLogger;
    }

    public File getBaseFile() {
        return this.mBaseName;
    }

    public void delete() {
        this.mBaseName.delete();
        this.mNewName.delete();
        this.mLegacyBackupName.delete();
    }

    public FileOutputStream startWrite() throws IOException {
        return startWrite(0L);
    }

    @Deprecated
    public FileOutputStream startWrite(long startTime) throws IOException {
        if (this.mCommitEventLogger != null) {
            this.mCommitEventLogger.setStartTime(startTime);
            this.mCommitEventLogger.onStartWrite();
        }
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(this.mNewName);
            ReliableWrite.setReliableWrite(outputStream);
            return outputStream;
        } catch (FileNotFoundException e) {
            File parent = this.mNewName.getParentFile();
            if (!parent.mkdirs()) {
                throw new IOException("Failed to create directory for " + this.mNewName);
            }
            FileUtils.setPermissions(parent.getPath(), 505, -1, -1);
            try {
                FileOutputStream outputStream2 = new FileOutputStream(this.mNewName);
                ReliableWrite.setReliableWrite(outputStream2);
                return outputStream2;
            } catch (FileNotFoundException e2) {
                throw new IOException("Failed to create new file " + this.mNewName, e2);
            }
        }
    }

    public void finishWrite(FileOutputStream str) {
        if (str == null) {
            return;
        }
        if (!FileUtils.sync(str)) {
            Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            str.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to close file output stream", e);
        }
        rename(this.mNewName, this.mBaseName);
        if (this.mCommitEventLogger != null) {
            this.mCommitEventLogger.onFinishWrite();
        }
    }

    public void failWrite(FileOutputStream str) {
        if (str == null) {
            return;
        }
        if (!FileUtils.sync(str)) {
            Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            str.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to close file output stream", e);
        }
        if (!this.mNewName.delete()) {
            Log.e(LOG_TAG, "Failed to delete new file " + this.mNewName);
        }
    }

    @Deprecated
    public void truncate() throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(this.mBaseName);
            FileUtils.sync(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new IOException("Couldn't append " + this.mBaseName);
        } catch (IOException e2) {
        }
    }

    @Deprecated
    public FileOutputStream openAppend() throws IOException {
        try {
            return new FileOutputStream(this.mBaseName, true);
        } catch (FileNotFoundException e) {
            throw new IOException("Couldn't append " + this.mBaseName);
        }
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        if (this.mNewName.exists() && this.mBaseName.exists() && !this.mNewName.delete()) {
            Log.e(LOG_TAG, "Failed to delete outdated new file " + this.mNewName);
        }
        return new FileInputStream(this.mBaseName);
    }

    public boolean exists() {
        return this.mBaseName.exists() || this.mLegacyBackupName.exists();
    }

    public long getLastModifiedTime() {
        if (this.mLegacyBackupName.exists()) {
            return this.mLegacyBackupName.lastModified();
        }
        return this.mBaseName.lastModified();
    }

    public byte[] readFully() throws IOException {
        FileInputStream stream = openRead();
        int pos = 0;
        try {
            byte[] data = new byte[stream.available()];
            while (true) {
                int amt = stream.read(data, pos, data.length - pos);
                if (amt <= 0) {
                    return data;
                }
                pos += amt;
                int avail = stream.available();
                if (avail > data.length - pos) {
                    byte[] newData = new byte[pos + avail];
                    System.arraycopy(data, 0, newData, 0, pos);
                    data = newData;
                }
            }
        } finally {
            stream.close();
        }
    }

    public void write(Consumer<FileOutputStream> writeContent) {
        FileOutputStream out = null;
        try {
            out = startWrite();
            writeContent.accept(out);
            finishWrite(out);
        } finally {
        }
    }

    public String toString() {
        return "AtomicFile[" + this.mBaseName + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static void rename(File source, File target) {
        if (target.isDirectory() && !target.delete()) {
            Log.e(LOG_TAG, "Failed to delete file which is a directory " + target);
        }
        if (!source.renameTo(target)) {
            Log.e(LOG_TAG, "Failed to rename " + source + " to " + target);
        }
    }
}
