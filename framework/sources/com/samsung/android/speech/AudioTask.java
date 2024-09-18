package com.samsung.android.speech;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.samsung.android.speech.SemSpeechRecognizer;
import java.io.DataOutputStream;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public class AudioTask implements Runnable {
    static final int DEFAULT_BLOCK_SIZE = 320;
    private String mTAG = AudioTask.class.getSimpleName();
    private LinkedBlockingQueue<short[]> q = null;
    public AudioRecord rec = null;
    private int block_size = 0;
    private boolean done = false;
    private MMUIRecognizer aMMUIRecognizer = null;
    private long consoleInitReturn = -1;
    public float[] cmResult = {0.0f};
    public String[] strResult = new String[3];
    public String[] utfResult = new String[1];
    public short[] BargeinAct = {-1};
    private int numRecogResult = 0;
    private float CMscore = 0.0f;
    private boolean isMakePCM = false;
    private SemSpeechRecognizer.ResultListener m_listener = null;
    private String loadPath = null;
    private int mCommandType = 0;
    private String loadNameList = Config.GetSamsungNameList(0);
    private String defaultloadNameList = Config.GetSamsungNameList(0);
    private int mLanguage = 1;
    private int mEmbeddedEngineLanguage = 1;
    private String modelPath = Config.GetSamsungPath(1) + "param";
    private String wordListPath = Config.GetSamsungPath(1);
    protected int AUDIO_RECORD_FOR_BARGE_IN = MediaRecorder.semGetInputSource(3);
    protected int AUDIO_RECORD_FOR_BARGE_IN_OEM = MediaRecorder.semGetInputSource(7);
    protected int AUDIO_RECORD_FOR_VOICE_RECOGNITION = 1999;
    private int totalReadCount = 0;
    private int AUDIO_START = 0;
    private int recogAfterReadCount = 0;
    private final int RECOGNITION_WAIT_TIME = 100;
    private File f = null;
    private DataOutputStream mDataOutputStream = null;
    private double THscore = -1.5d;
    private String acousticModelPathname = Config.GetOEMAM(0, 2) + ".bin";
    private String acousticModelPathname_sub = Config.GetOEMAM(0, 2) + ".bin";
    private int readNshorts = -1;
    private Handler mStopHandler = null;
    private boolean isEnableSamsungOOVResult = false;
    private boolean isOEMCameraBargeIn = false;
    private boolean isOEMResult = false;
    private int dualThresholdFlag = 0;
    private Handler handler = new Handler() { // from class: com.samsung.android.speech.AudioTask.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            String[] result = msg.getData().getStringArray("recognition_result");
            if (AudioTask.this.m_listener != null) {
                AudioTask.this.m_listener.onResults(result);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public AudioTask(SemSpeechRecognizer.ResultListener listener, String path, int command, int language, boolean samsungOOVResult) {
    }

    void init(LinkedBlockingQueue<short[]> q, int block_size, SemSpeechRecognizer.ResultListener listener, String path, int command, int Language, boolean samsungOOVResult) {
        String simpleName = AudioTask.class.getSimpleName();
        this.mTAG = simpleName;
        Log.i(simpleName, "AudioTask init()");
        Log.i(this.mTAG, "command : " + command);
        Log.i(this.mTAG, "Language : " + Language);
        this.done = false;
        this.q = q;
        this.block_size = block_size;
        this.mCommandType = command;
        this.m_listener = listener;
        this.loadPath = path;
        this.mLanguage = Language;
        this.BargeinAct[0] = -1;
    }

    public void stopPhraseSpotter() {
    }

    public int getBlockSize() {
        return this.block_size;
    }

    public void setBlockSize(int block_size) {
        this.block_size = block_size;
    }

    public LinkedBlockingQueue<short[]> getQueue() {
        return this.q;
    }

    public void stop() {
        Log.i(this.mTAG, "AudioTask : stop start");
        this.mStopHandler = null;
        this.done = true;
        this.readNshorts = -1;
        Log.i(this.mTAG, "AudioTask : stop end");
    }

    public void stopBargeInAudioRecord() {
        Log.i(this.mTAG, "stopBargeInAudioRecord start");
        if (this.rec != null) {
            Log.d(this.mTAG, "Call rec.stop start");
            this.rec.stop();
            Log.d(this.mTAG, "Call rec.stop end");
            Log.d(this.mTAG, "Call rec.release start");
            this.rec.release();
            Log.d(this.mTAG, "Call rec.release end");
            this.rec = null;
            Log.d(this.mTAG, "rec = null");
        }
        Log.i(this.mTAG, "stopBargeInAudioRecord end");
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.i(this.mTAG, "run start");
    }

    private int getMMUIRecognitionResult(short[] speech, int readNshorts) {
        MMUIRecognizer mMUIRecognizer;
        int result = 0;
        MMUIRecognizer mMUIRecognizer2 = this.aMMUIRecognizer;
        if (mMUIRecognizer2 != null) {
            result = mMUIRecognizer2.RECThread(speech);
        }
        if (result == -2) {
            if (this.done) {
                Log.e(this.mTAG, "readByteBlock return -1 : getMMUIRecognitionResult - Section1");
                return -1;
            }
            if (this.aMMUIRecognizer != null) {
                Log.d(this.mTAG, "Barge-in : Too long input so Reset");
                this.aMMUIRecognizer.ResetFx();
                this.aMMUIRecognizer.SASRReset();
            }
        }
        boolean z = this.done;
        if (z) {
            Log.e(this.mTAG, "readByteBlock return -1 : getMMUIRecognitionResult - Section2");
            return -1;
        }
        if (result == 2 && (mMUIRecognizer = this.aMMUIRecognizer) != null) {
            if (z) {
                Log.e(this.mTAG, "readByteBlock return -1 : getMMUIRecognitionResult - Section3");
                return -1;
            }
            mMUIRecognizer.ResetFx();
            this.numRecogResult = this.aMMUIRecognizer.SASRDoRecognition(this.cmResult, this.strResult, "/system/voicecommanddata/sasr/input.txt", this.BargeinAct, this.utfResult);
            String[] strArr = this.strResult;
            strArr[0] = strArr[0].replace('_', ' ');
            int i = this.mEmbeddedEngineLanguage;
            if (i == 0 || i == 2) {
                String[] strArr2 = this.utfResult;
                strArr2[0] = strArr2[0].replace('_', ' ');
                this.strResult[0] = this.utfResult[0];
            }
            Log.i(this.mTAG, "numResult[0] : " + this.cmResult[0]);
            Log.i(this.mTAG, "strResult[0] : " + this.strResult[0]);
            Log.i(this.mTAG, "BargeinAct[0] : " + ((int) this.BargeinAct[0]));
            int i2 = this.mCommandType;
            if (i2 == 3 && this.BargeinAct[0] == 2) {
                this.THscore = -1.8d;
            } else if (i2 == 7) {
                this.THscore = -1.0d;
            } else {
                this.THscore = -1.5d;
            }
            Log.i(this.mTAG, "THscore : " + this.THscore);
            if (this.done) {
                Log.e(this.mTAG, "readByteBlock return -1 : getMMUIRecognitionResult - Section4");
                return -1;
            }
            if (this.isOEMCameraBargeIn && this.isEnableSamsungOOVResult) {
                if (this.isOEMResult) {
                    Log.i(this.mTAG, "isOEMCameraBargeIn is true and isOEMResult is true");
                    Log.d(this.mTAG, "EmbeddedEngine Recognizer : " + ((int) this.BargeinAct[0]));
                    this.isOEMResult = false;
                    Log.i(this.mTAG, "Set isOEMResult = false. So isOEMResult : " + this.isOEMResult);
                } else {
                    Log.i(this.mTAG, "isOEMCameraBargeIn is true and keyword is not detected by OEM and keyword or non-keyword is detected by embeddedEngine.");
                    String[] strArr3 = this.strResult;
                    strArr3[0] = "TH-Reject";
                    this.BargeinAct[0] = -1;
                    SendHandlerMessage(strArr3);
                }
            } else if (this.cmResult[0] > this.THscore) {
                SendHandlerMessage(this.strResult);
            } else {
                String[] strArr4 = this.strResult;
                strArr4[0] = "TH-Reject";
                this.BargeinAct[0] = -1;
                SendHandlerMessage(strArr4);
            }
            if (this.done) {
                Log.e(this.mTAG, "readByteBlock return -1 : Section13");
                return -1;
            }
            this.aMMUIRecognizer.SASRReset();
        }
        return readNshorts;
    }

    public static short twoBytesToShort(byte b1, byte b2) {
        return (short) ((b1 & 255) | (b2 << 8));
    }

    public void setHandler(Handler stopHandler) {
        this.mStopHandler = stopHandler;
    }

    private void SendHandlerMessage(String[] result) {
        Message msg = this.handler.obtainMessage();
        Bundle b = new Bundle();
        b.putStringArray("recognition_result", result);
        msg.setData(b);
        try {
            this.handler.sendMessage(msg);
        } catch (IllegalStateException e) {
            Log.e(this.mTAG, "IllegalStateException " + e.getMessage());
            stop();
        }
    }

    public void setSamsungFilePath(int language, int domain) {
        this.wordListPath = Config.GetSamsungPath(language);
        this.modelPath = this.wordListPath + "param";
        this.loadNameList = Config.GetSamsungNameList(domain);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AudioRecord getAudioRecord(int source) {
        AudioRecord retAudioRecord = null;
        Log.i(this.mTAG, "getAudioRecord modified by jy");
        try {
            AudioRecord retAudioRecord2 = new AudioRecord.Builder().semSetConcurrentCapture(true).setAudioFormat(new AudioFormat.Builder().setChannelMask(16).setEncoding(2).setSampleRate(16000).build()).setBufferSizeInBytes(8192).build();
            if (retAudioRecord2 == null || retAudioRecord2.getState() == 1) {
                Log.d(this.mTAG, "got AudioRecord using source=" + source + ", also 16000 16 2 8192");
                Log.i(this.mTAG, "getAudioRecord for " + source + "=true");
                return retAudioRecord2;
            }
            Log.d(this.mTAG, "getAudioRecord for " + source + "=false, got !initialized");
            retAudioRecord2.release();
            return null;
        } catch (IllegalArgumentException e) {
            Log.e(this.mTAG, "getAudioRecord for " + source + "=false, IllegalArgumentException");
            Log.e(this.mTAG, "got IllegalArgumentException using source=" + source + ", also 16000 16 2 8192");
            if (0 != 0) {
                retAudioRecord.release();
            }
            return null;
        }
    }

    public void setEmbeddedEngineLanguage() {
        int i = this.mLanguage;
        this.mEmbeddedEngineLanguage = i;
        if (this.isOEMCameraBargeIn && this.isEnableSamsungOOVResult) {
            this.mEmbeddedEngineLanguage = 0;
        } else if (i == 10) {
            this.mEmbeddedEngineLanguage = 1;
        } else if (i == 11) {
            this.mEmbeddedEngineLanguage = 3;
        } else if (i == 9) {
            this.mEmbeddedEngineLanguage = 1;
        } else if (i == 13) {
            this.mEmbeddedEngineLanguage = 2;
        } else if (i == 12) {
            this.mEmbeddedEngineLanguage = 2;
        } else if (i == 14) {
            this.mEmbeddedEngineLanguage = 2;
        }
        Log.i(this.mTAG, "mEmbeddedEngineLanguage : " + this.mEmbeddedEngineLanguage);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isBargeInFile(String mFilePath) {
        if (new File(mFilePath).exists()) {
            return true;
        }
        return false;
    }

    public static void swap(short[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = swap(array[i]);
        }
    }

    public static short swap(short value) {
        int b1 = value & 255;
        int b2 = (value >> 8) & 255;
        return (short) ((b1 << 8) | (b2 << 0));
    }
}
