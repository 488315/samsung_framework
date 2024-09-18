package com.samsung.android.knox.knoxai;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.FileDescriptor;

/* loaded from: classes5.dex */
public class TaLoaderOptions implements Parcelable {
    public static final Parcelable.Creator<TaLoaderOptions> CREATOR = new Parcelable.Creator<TaLoaderOptions>() { // from class: com.samsung.android.knox.knoxai.TaLoaderOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaLoaderOptions createFromParcel(Parcel in) {
            return new TaLoaderOptions(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaLoaderOptions[] newArray(int size) {
            return new TaLoaderOptions[size];
        }
    };
    private FileDescriptor fd;
    private String process;
    private int processLength;
    private String root;
    private int rootLength;
    private int taOffset;
    private int taSize;

    public TaLoaderOptions() {
    }

    public void setRoot(String root) {
        this.root = root;
        this.rootLength = root.length();
    }

    public String getRoot() {
        return this.root;
    }

    public int getRootLength() {
        return this.rootLength;
    }

    public void setProcess(String process) {
        this.process = process;
        this.processLength = process.length();
    }

    public String getProcess() {
        return this.process;
    }

    public int getProcessLength() {
        return this.processLength;
    }

    public void setFd(ParcelFileDescriptor pfd) {
        this.fd = pfd.getFileDescriptor();
        this.taOffset = 0;
        this.taSize = (int) pfd.getStatSize();
    }

    public FileDescriptor getFd() {
        return this.fd;
    }

    public int getTaOffset() {
        return this.taOffset;
    }

    public int getTaSize() {
        return this.taSize;
    }

    private TaLoaderOptions(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.rootLength);
        out.writeString(this.root);
        out.writeInt(this.processLength);
        out.writeString(this.process);
        out.writeFileDescriptor(this.fd);
        out.writeInt(this.taOffset);
        out.writeInt(this.taSize);
    }

    public void readFromParcel(Parcel in) {
        this.rootLength = in.readInt();
        this.root = in.readString();
        this.processLength = in.readInt();
        this.process = in.readString();
        this.fd = in.readFileDescriptor().getFileDescriptor();
        this.taOffset = in.readInt();
        this.taSize = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
