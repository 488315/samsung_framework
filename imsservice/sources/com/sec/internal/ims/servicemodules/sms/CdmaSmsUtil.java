package com.sec.internal.ims.servicemodules.sms;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class CdmaSmsUtil {
    public static final String CONTENT_TYPE_3GPP2 = "application/vnd.3gpp2.sms";
    private static final String LOG_TAG = SmsServiceModule.class.getSimpleName();
    public static final int PARAM_ID_BEARER_DATA = 8;
    public static final int PARAM_ID_BEARER_REPLY_OPTION = 6;
    public static final int PARAM_ID_ORIGINATING_ADDRESS = 2;
    public static final int PARAM_ID_SERVICE_CATEGORY = 1;
    public static final int PARAM_ID_TELESERVICE = 0;
    public static final int SMS_MSG_TYPE_PP = 0;
    public static final int TELESERVICE_WAP = 4100;

    public static boolean isValid3GPP2PDU(byte[] bArr) {
        if (bArr == null || bArr.length < 6) {
            return false;
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        int i = 0;
        while (dataInputStream.available() > 0) {
            try {
                try {
                    try {
                        byte readByte = dataInputStream.readByte();
                        if (dataInputStream.available() <= 0) {
                            Log.e(LOG_TAG, "isValid3GPP2PDU() no data after paramId");
                            return false;
                        }
                        int readByte2 = dataInputStream.readByte() & 255;
                        if (dataInputStream.available() > 0 && readByte == 0) {
                            readByte2 = dataInputStream.readByte() & 255;
                        }
                        if (dataInputStream.available() < readByte2) {
                            Log.e(LOG_TAG, "isValid3GPP2PDU() wrong after PARAM" + ((int) readByte));
                            try {
                                dataInputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                        if (readByte == 0) {
                            i |= 1;
                        } else if (readByte != 1) {
                            if (readByte == 2) {
                                i |= 2;
                            } else if (readByte != 6) {
                                if (readByte != 8) {
                                    Log.e(LOG_TAG, "isValid3GPP2PDU() Invalid paramID [" + ((int) readByte) + "]");
                                    try {
                                        dataInputStream.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                    return false;
                                }
                                i |= 8;
                            } else if (dataInputStream.skip(readByte2) < 0) {
                                try {
                                    dataInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                                return false;
                            }
                        }
                        if (readByte != 6 && dataInputStream.skip(readByte2) < 0) {
                            try {
                                dataInputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                            return false;
                        }
                    } finally {
                        try {
                            dataInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                } catch (IOException e6) {
                    e6.printStackTrace();
                    dataInputStream.close();
                }
            } catch (IOException e7) {
                e7.printStackTrace();
            }
        }
        dataInputStream.close();
        if (i == 11) {
            return true;
        }
        Log.e(LOG_TAG, "isValid3GPP2PDU() PDU doesn't have mandatory paramId");
        return false;
    }

    public static boolean isAdminMsg(byte[] bArr) {
        if (bArr == null || bArr.length < 5) {
            return false;
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        try {
            try {
                try {
                    byte readByte = dataInputStream.readByte();
                    byte readByte2 = dataInputStream.readByte();
                    if (readByte == 0 && readByte2 == 0) {
                        if ((dataInputStream.readUnsignedByte() << 8) + dataInputStream.readUnsignedByte() == 4100) {
                            try {
                                dataInputStream.close();
                                return true;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return true;
                            }
                        }
                    }
                    dataInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                    dataInputStream.close();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            return false;
        } catch (Throwable th) {
            try {
                dataInputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }
}
