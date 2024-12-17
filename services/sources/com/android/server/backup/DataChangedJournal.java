package com.android.server.backup;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DataChangedJournal {
    public final File mFile;

    public DataChangedJournal(File file) {
        Objects.requireNonNull(file);
        this.mFile = file;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof DataChangedJournal) {
            return this.mFile.equals(((DataChangedJournal) obj).mFile);
        }
        return false;
    }

    public final void forEach(UserBackupManagerService$$ExternalSyntheticLambda13 userBackupManagerService$$ExternalSyntheticLambda13) {
        try {
            try {
                try {
                    while (true) {
                        try {
                            userBackupManagerService$$ExternalSyntheticLambda13.accept(new DataInputStream(new BufferedInputStream(new FileInputStream(this.mFile), 8192)).readUTF());
                        } finally {
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (EOFException unused) {
        }
    }

    public final int hashCode() {
        return this.mFile.hashCode();
    }

    public final String toString() {
        return this.mFile.toString();
    }
}
