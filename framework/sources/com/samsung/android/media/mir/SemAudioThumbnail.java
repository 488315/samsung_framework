package com.samsung.android.media.mir;

import java.io.FileDescriptor;

/* loaded from: classes5.dex */
public class SemAudioThumbnail {
    public static final int ERROR_INVALID_ARG = -4;
    public static final int ERROR_INVALID_PATH = -7;
    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_UNSUPPORTED = -3;
    private static final int SMAT_ERR = -1;
    private static final int SMAT_ERR_INSUFF_MEM = -2;
    private static final int SMAT_ERR_INVALID_ARG = -4;
    private static final int SMAT_ERR_NOT_OPEN_FILE = -7;
    private static final int SMAT_ERR_UNSUPPORT = -3;
    private static final int SMAT_EXTRACT_DONE = 5;
    private static final int SMAT_OK = 0;
    private static final int SMAT_QUIT_DONE = 6;
    private static final int SMAT_READY = 1;
    private static boolean isNativeLibraryReady = false;
    private ResultListener mListener = null;
    private int lastError = -1;
    private int mHandle = -1;

    /* loaded from: classes5.dex */
    public interface ResultListener {
        void onDone(long j);

        void onError(int i);
    }

    public native int deinit(int i);

    private native int extract(int i);

    public native long getInfo(int i);

    public native int getStat(int i);

    private native int init(String str, int i);

    private native int initialize(FileDescriptor fileDescriptor, int i);

    public SemAudioThumbnail() {
        try {
            System.loadLibrary("smat");
            isNativeLibraryReady = true;
        } catch (Exception e) {
            isNativeLibraryReady = false;
        } catch (UnsatisfiedLinkError e2) {
            isNativeLibraryReady = false;
        }
    }

    public boolean checkFile(String path) {
        if (!isNativeLibraryReady || path == null) {
            return false;
        }
        try {
            int handle = init(path, 0);
            if (handle < 0) {
                return false;
            }
            deinit(handle);
            return true;
        } catch (Exception e) {
            return false;
        } catch (UnsatisfiedLinkError e2) {
            return false;
        }
    }

    public void extract(String path, int duration, ResultListener listener) {
        if (listener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (path == null) {
            sendErrorMessage(listener, -7);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(listener, -1);
            return;
        }
        if (duration < 0) {
            sendErrorMessage(listener, -4);
            return;
        }
        try {
            int init = init(path, duration);
            this.mHandle = init;
            this.mListener = listener;
            if (init >= 0) {
                if (extract(init) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.1
                        AnonymousClass1(String name) {
                            super(name);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:
                        
                            continue;
                         */
                        @Override // java.lang.Thread, java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void run() {
                            /*
                                r7 = this;
                                r0 = -1
                                r1 = -1
                                r2 = 0
                            L3:
                                if (r2 != 0) goto L8b
                                r3 = 100
                                sleep(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8554$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                r1 = r3
                                if (r0 == r1) goto L7a
                                r0 = r1
                                switch(r1) {
                                    case -4: goto L61;
                                    case -1: goto L61;
                                    case 5: goto L3b;
                                    case 6: goto L1d;
                                    default: goto L1c;
                                }
                            L1c:
                                goto L7a
                            L1d:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r3 == 0) goto L39
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r4 = 0
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                            L39:
                                r2 = 1
                                goto L7a
                            L3b:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8553$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r5 == 0) goto L5f
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r5.onDone(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                            L5f:
                                r2 = 1
                                goto L7a
                            L61:
                                r2 = 1
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r3 == 0) goto L7a
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r4 = -1
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                goto L7a
                            L76:
                                r3 = move-exception
                                goto L8b
                            L78:
                                r3 = move-exception
                                goto L8b
                            L7a:
                                goto L3
                            L7b:
                                r3 = move-exception
                                goto L8b
                            L7d:
                                r3 = move-exception
                                r3.printStackTrace()
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r4)
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r4, r5)
                            L8b:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass1.run():void");
                        }
                    }.start();
                } else {
                    sendErrorMessage(listener, -1);
                }
            } else {
                this.lastError = init;
                switch (init) {
                    case -7:
                        sendErrorMessage(listener, -7);
                        break;
                    case -3:
                        sendErrorMessage(listener, -3);
                        break;
                    default:
                        sendErrorMessage(listener, -1);
                        break;
                }
            }
        } catch (Exception e) {
        } catch (UnsatisfiedLinkError e2) {
            sendErrorMessage(listener, -1);
        }
    }

    /* renamed from: com.samsung.android.media.mir.SemAudioThumbnail$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 extends Thread {
        AnonymousClass1(String name) {
            super(name);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            /*  JADX ERROR: Method code generation error
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.IContainer.get(jadx.api.plugins.input.data.attributes.IJadxAttrType)" because "cont" is null
                	at jadx.core.codegen.RegionGen.declareVars(RegionGen.java:70)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:65)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                */
            /*
                this = this;
                r0 = -1
                r1 = -1
                r2 = 0
            L3:
                if (r2 != 0) goto L8b
                r3 = 100
                sleep(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8554$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                r1 = r3
                if (r0 == r1) goto L7a
                r0 = r1
                switch(r1) {
                    case -4: goto L61;
                    case -1: goto L61;
                    case 5: goto L3b;
                    case 6: goto L1d;
                    default: goto L1c;
                }
            L1c:
                goto L7a
            L1d:
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r3 == 0) goto L39
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r4 = 0
                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
            L39:
                r2 = 1
                goto L7a
            L3b:
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8553$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r5 == 0) goto L5f
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r5.onDone(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
            L5f:
                r2 = 1
                goto L7a
            L61:
                r2 = 1
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r3 == 0) goto L7a
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r4 = -1
                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                goto L7a
            L76:
                r3 = move-exception
                goto L8b
            L78:
                r3 = move-exception
                goto L8b
            L7a:
                goto L3
            L7b:
                r3 = move-exception
                goto L8b
            L7d:
                r3 = move-exception
                r3.printStackTrace()
                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r4)
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r4, r5)
            L8b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass1.run():void");
        }
    }

    public void extract(String path, ResultListener listener) {
        if (listener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (path == null) {
            sendErrorMessage(listener, -7);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(listener, -1);
            return;
        }
        try {
            int init = init(path, 0);
            this.mHandle = init;
            this.mListener = listener;
            if (init >= 0) {
                if (extract(init) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.2
                        AnonymousClass2(String name) {
                            super(name);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:
                        
                            continue;
                         */
                        @Override // java.lang.Thread, java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void run() {
                            /*
                                r7 = this;
                                r0 = -1
                                r1 = -1
                                r2 = 0
                            L3:
                                if (r2 != 0) goto L8b
                                r3 = 100
                                sleep(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8554$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                r1 = r3
                                if (r0 == r1) goto L7a
                                r0 = r1
                                switch(r1) {
                                    case -4: goto L61;
                                    case -1: goto L61;
                                    case 5: goto L3b;
                                    case 6: goto L1d;
                                    default: goto L1c;
                                }
                            L1c:
                                goto L7a
                            L1d:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r3 == 0) goto L39
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r4 = 0
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                            L39:
                                r2 = 1
                                goto L7a
                            L3b:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8553$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r5 == 0) goto L5f
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r5.onDone(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                            L5f:
                                r2 = 1
                                goto L7a
                            L61:
                                r2 = 1
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r3 == 0) goto L7a
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r4 = -1
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                goto L7a
                            L76:
                                r3 = move-exception
                                goto L8b
                            L78:
                                r3 = move-exception
                                goto L8b
                            L7a:
                                goto L3
                            L7b:
                                r3 = move-exception
                                goto L8b
                            L7d:
                                r3 = move-exception
                                r3.printStackTrace()
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r4)
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r4, r5)
                            L8b:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass2.run():void");
                        }
                    }.start();
                } else {
                    sendErrorMessage(listener, -1);
                }
            } else {
                this.lastError = init;
                switch (init) {
                    case -7:
                        sendErrorMessage(listener, -7);
                        break;
                    case -3:
                        sendErrorMessage(listener, -3);
                        break;
                    default:
                        sendErrorMessage(listener, -1);
                        break;
                }
            }
        } catch (Exception e) {
        } catch (UnsatisfiedLinkError e2) {
            sendErrorMessage(listener, -1);
        }
    }

    /* renamed from: com.samsung.android.media.mir.SemAudioThumbnail$2 */
    /* loaded from: classes5.dex */
    class AnonymousClass2 extends Thread {
        AnonymousClass2(String name) {
            super(name);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            /*  JADX ERROR: Method code generation error
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.IContainer.get(jadx.api.plugins.input.data.attributes.IJadxAttrType)" because "cont" is null
                	at jadx.core.codegen.RegionGen.declareVars(RegionGen.java:70)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:65)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                */
            /*
                this = this;
                r0 = -1
                r1 = -1
                r2 = 0
            L3:
                if (r2 != 0) goto L8b
                r3 = 100
                sleep(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8554$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                r1 = r3
                if (r0 == r1) goto L7a
                r0 = r1
                switch(r1) {
                    case -4: goto L61;
                    case -1: goto L61;
                    case 5: goto L3b;
                    case 6: goto L1d;
                    default: goto L1c;
                }
            L1c:
                goto L7a
            L1d:
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r3 == 0) goto L39
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r4 = 0
                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
            L39:
                r2 = 1
                goto L7a
            L3b:
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8553$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r5 == 0) goto L5f
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r5.onDone(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
            L5f:
                r2 = 1
                goto L7a
            L61:
                r2 = 1
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r3 == 0) goto L7a
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r4 = -1
                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                goto L7a
            L76:
                r3 = move-exception
                goto L8b
            L78:
                r3 = move-exception
                goto L8b
            L7a:
                goto L3
            L7b:
                r3 = move-exception
                goto L8b
            L7d:
                r3 = move-exception
                r3.printStackTrace()
                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r4)
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r4, r5)
            L8b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass2.run():void");
        }
    }

    public void extract(FileDescriptor fd, ResultListener listener) {
        if (listener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (fd == null) {
            sendErrorMessage(listener, -4);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(listener, -1);
            return;
        }
        try {
            int initialize = initialize(fd, 0);
            this.mHandle = initialize;
            this.mListener = listener;
            if (initialize >= 0) {
                if (extract(initialize) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.3
                        AnonymousClass3(String name) {
                            super(name);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:
                        
                            continue;
                         */
                        @Override // java.lang.Thread, java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void run() {
                            /*
                                r7 = this;
                                r0 = -1
                                r1 = -1
                                r2 = 0
                            L3:
                                if (r2 != 0) goto L8b
                                r3 = 100
                                sleep(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8554$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                                r1 = r3
                                if (r0 == r1) goto L7a
                                r0 = r1
                                switch(r1) {
                                    case -4: goto L61;
                                    case -1: goto L61;
                                    case 5: goto L3b;
                                    case 6: goto L1d;
                                    default: goto L1c;
                                }
                            L1c:
                                goto L7a
                            L1d:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r3 == 0) goto L39
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r4 = 0
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                            L39:
                                r2 = 1
                                goto L7a
                            L3b:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8553$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r5 == 0) goto L5f
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r5.onDone(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                            L5f:
                                r2 = 1
                                goto L7a
                            L61:
                                r2 = 1
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                if (r3 == 0) goto L7a
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                r4 = -1
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                                goto L7a
                            L76:
                                r3 = move-exception
                                goto L8b
                            L78:
                                r3 = move-exception
                                goto L8b
                            L7a:
                                goto L3
                            L7b:
                                r3 = move-exception
                                goto L8b
                            L7d:
                                r3 = move-exception
                                r3.printStackTrace()
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r4)
                                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r4, r5)
                            L8b:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass3.run():void");
                        }
                    }.start();
                } else {
                    sendErrorMessage(listener, -1);
                }
            } else {
                this.lastError = initialize;
                switch (initialize) {
                    case -7:
                        sendErrorMessage(listener, -4);
                        break;
                    case -3:
                        sendErrorMessage(listener, -3);
                        break;
                    default:
                        sendErrorMessage(listener, -1);
                        break;
                }
            }
        } catch (Exception e) {
        } catch (UnsatisfiedLinkError e2) {
            sendErrorMessage(listener, -1);
        }
    }

    /* renamed from: com.samsung.android.media.mir.SemAudioThumbnail$3 */
    /* loaded from: classes5.dex */
    class AnonymousClass3 extends Thread {
        AnonymousClass3(String name) {
            super(name);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            /*  JADX ERROR: Method code generation error
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.IContainer.get(jadx.api.plugins.input.data.attributes.IJadxAttrType)" because "cont" is null
                	at jadx.core.codegen.RegionGen.declareVars(RegionGen.java:70)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:65)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                */
            /*
                this = this;
                r0 = -1
                r1 = -1
                r2 = 0
            L3:
                if (r2 != 0) goto L8b
                r3 = 100
                sleep(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8554$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L7b java.lang.InterruptedException -> L7d
                r1 = r3
                if (r0 == r1) goto L7a
                r0 = r1
                switch(r1) {
                    case -4: goto L61;
                    case -1: goto L61;
                    case 5: goto L3b;
                    case 6: goto L1d;
                    default: goto L1c;
                }
            L1c:
                goto L7a
            L1d:
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r3 == 0) goto L39
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r4 = 0
                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
            L39:
                r2 = 1
                goto L7a
            L3b:
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8553$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r5 == 0) goto L5f
                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r5.onDone(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
            L5f:
                r2 = 1
                goto L7a
            L61:
                r2 = 1
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                if (r3 == 0) goto L7a
                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8551$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                r4 = -1
                r3.onDone(r4)     // Catch: java.lang.Exception -> L76 java.lang.NullPointerException -> L78
                goto L7a
            L76:
                r3 = move-exception
                goto L8b
            L78:
                r3 = move-exception
                goto L8b
            L7a:
                goto L3
            L7b:
                r3 = move-exception
                goto L8b
            L7d:
                r3 = move-exception
                r3.printStackTrace()
                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8550$$Nest$fgetmHandle(r4)
                com.samsung.android.media.mir.SemAudioThumbnail.m8552$$Nest$mdeinit(r4, r5)
            L8b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass3.run():void");
        }
    }

    private void sendErrorMessage(ResultListener listener, int errorType) {
        this.mListener = listener;
        this.lastError = errorType;
        new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.4
            AnonymousClass4(String name) {
                super(name);
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    if (SemAudioThumbnail.this.mListener != null) {
                        SemAudioThumbnail.this.mListener.onError(SemAudioThumbnail.this.lastError);
                    }
                } catch (NullPointerException e) {
                } catch (Exception e2) {
                }
            }
        }.start();
    }

    /* renamed from: com.samsung.android.media.mir.SemAudioThumbnail$4 */
    /* loaded from: classes5.dex */
    public class AnonymousClass4 extends Thread {
        AnonymousClass4(String name) {
            super(name);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (SemAudioThumbnail.this.mListener != null) {
                    SemAudioThumbnail.this.mListener.onError(SemAudioThumbnail.this.lastError);
                }
            } catch (NullPointerException e) {
            } catch (Exception e2) {
            }
        }
    }
}
