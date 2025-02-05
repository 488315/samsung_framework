package com.sec.internal.ims.util;

import android.os.Message;
import java.io.IOException;

/* loaded from: classes.dex */
public interface IThumbnailTool {
    void createThumb(String str, String str2, long j, Message message) throws NullPointerException, IllegalArgumentException, IOException, ThumbCreationException;

    void createThumbFromImage(String str, String str2, long j, int i, int i2, Message message) throws NullPointerException, IllegalArgumentException, IOException, ThumbCreationException;

    void createThumbFromImage(String str, String str2, long j, Message message) throws NullPointerException, IllegalArgumentException, IOException, ThumbCreationException;

    void createThumbFromVideo(String str, String str2, long j, int i, int i2, Message message) throws NullPointerException, IllegalArgumentException, IOException, ThumbCreationException;

    void createThumbFromVideo(String str, String str2, long j, Message message) throws NullPointerException, IllegalArgumentException, IOException, ThumbCreationException;

    String getThumbSavedDirectory();

    boolean isSupported(String str);

    public static class ThumbCreationException extends Exception {
        private static final long serialVersionUID = 1;

        public ThumbCreationException(String str) {
            super(str);
        }

        public ThumbCreationException(Throwable th) {
            super(th);
        }
    }
}
