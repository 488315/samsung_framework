package com.android.systemui.wallpaper.video;

import android.app.WallpaperManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.video.VideoFileSaveService;
import java.io.File;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class IVideoFileSaveService$Stub extends Binder implements IInterface {
    public IVideoFileSaveService$Stub() {
        attachInterface(this, "com.android.systemui.wallpaper.video.IVideoFileSaveService");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        boolean z;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.wallpaper.video.IVideoFileSaveService");
        }
        if (i != 1598968902) {
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VideoFileSaveService videoFileSaveService = VideoFileSaveService.this;
                    videoFileSaveService.mVideoFileExt = readString;
                    videoFileSaveService.mUserId = readInt;
                    videoFileSaveService.mCurentWhich = readInt2;
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor videoFileDescriptorAsUserWithFilename = ((VideoFileSaveService.AnonymousClass2) this).getVideoFileDescriptorAsUserWithFilename(null, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(videoFileDescriptorAsUserWithFilename, 1);
                    return true;
                case 3:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isVideoFileExistsWithFilename = ((VideoFileSaveService.AnonymousClass2) this).isVideoFileExistsWithFilename(null, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoFileExistsWithFilename);
                    return true;
                case 4:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean deleteVideoFileWithFilename = ((VideoFileSaveService.AnonymousClass2) this).deleteVideoFileWithFilename(null, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteVideoFileWithFilename);
                    return true;
                case 5:
                    boolean renameVideoFileWithFilename = ((VideoFileSaveService.AnonymousClass2) this).renameVideoFileWithFilename(null);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(renameVideoFileWithFilename);
                    return true;
                case 6:
                    ((VideoFileSaveService.AnonymousClass2) this).setVideoLockscreenWallpaperAsOwnerWithFilename(null);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((VideoFileSaveService.AnonymousClass2) this).setVideoWallpaperAsOwnerWithFilename(bundle, null);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString2 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor videoFileDescriptorAsUserWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).getVideoFileDescriptorAsUserWithFilename(readString2, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(videoFileDescriptorAsUserWithFilename2, 1);
                    return true;
                case 9:
                    String readString3 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isVideoFileExistsWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).isVideoFileExistsWithFilename(readString3, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoFileExistsWithFilename2);
                    return true;
                case 10:
                    String readString4 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean deleteVideoFileWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).deleteVideoFileWithFilename(readString4, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteVideoFileWithFilename2);
                    return true;
                case 11:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    VideoFileSaveService.AnonymousClass2 anonymousClass2 = (VideoFileSaveService.AnonymousClass2) this;
                    if (UserHandle.semGetMyUserId() == 0) {
                        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
                            z = true;
                        } else {
                            z = false;
                        }
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(VideoFileSaveService.this.getApplicationContext());
                        String videoFilePath = wallpaperManager.getVideoFilePath(2);
                        String videoFilePath2 = wallpaperManager.getVideoFilePath(18);
                        for (File file : new File("/data/user_de/0/com.android.systemui/files").listFiles(VideoFileSaveService.this.mVideoWallpaperFileFilter)) {
                            String name = file.getName();
                            if ((TextUtils.isEmpty(videoFilePath) || !videoFilePath.contains(readString5)) && (!z || TextUtils.isEmpty(videoFilePath2) || !videoFilePath2.contains(readString5))) {
                                if (name.contains(readString5) && file.delete()) {
                                    Log.i("VideoFileCopyService", "deleteVideoFiles: ".concat(name));
                                } else {
                                    Log.w("VideoFileCopyService", "deleteVideoFiles, fail: ".concat(name));
                                }
                            }
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
                case 12:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean renameVideoFileWithFilename2 = ((VideoFileSaveService.AnonymousClass2) this).renameVideoFileWithFilename(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(renameVideoFileWithFilename2);
                    return true;
                case 13:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((VideoFileSaveService.AnonymousClass2) this).setVideoLockscreenWallpaperAsOwnerWithFilename(readString7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString8 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((VideoFileSaveService.AnonymousClass2) this).setVideoWallpaperAsOwnerWithFilename(bundle2, readString8);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
        parcel2.writeString("com.android.systemui.wallpaper.video.IVideoFileSaveService");
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
