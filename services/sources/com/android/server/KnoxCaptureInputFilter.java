package com.android.server;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Slog;
import android.view.InputEvent;
import android.view.InputFilter;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.android.internal.util.FrameworkStatsLog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class KnoxCaptureInputFilter extends InputFilter {
    public int activeScanDeviceId;
    public StringBuilder activeScanInput;
    public final Context context;
    public boolean installed;
    public final ScanEventHandler scanEventHandler;
    public Set scannerDevices;
    public static final String TAG = KnoxCaptureInputFilter.class.getSimpleName();
    public static final boolean DEBUG = !"user".equals(SystemProperties.get("ro.build.type"));

    public KnoxCaptureInputFilter(Context context) {
        super(context.getMainLooper());
        this.activeScanDeviceId = -1;
        this.context = context;
        HandlerThread handlerThread = new HandlerThread("ScanEventThread");
        handlerThread.start();
        this.scanEventHandler = new ScanEventHandler(handlerThread.getLooper());
        this.scannerDevices = new HashSet();
        this.activeScanInput = new StringBuilder();
    }

    public void markInputDeviceAsScanner(int i) {
        if (DEBUG) {
            Slog.d(TAG, "markInputDeviceAsScanner, inputDevice: " + i);
        }
        if (this.scannerDevices.contains(Integer.valueOf(i))) {
            return;
        }
        this.scannerDevices.add(Integer.valueOf(i));
    }

    public void unmarkInputDeviceAsScanner(int i) {
        if (DEBUG) {
            Slog.d(TAG, "unmarkInputDeviceAsScanner, inputDevice: " + i);
        }
        this.scannerDevices.remove(Integer.valueOf(i));
    }

    public void onInstalled() {
        if (DEBUG) {
            Slog.d(TAG, "KnoxCapture input filter installed");
        }
        this.installed = true;
        super.onInstalled();
    }

    public void onUninstalled() {
        if (DEBUG) {
            Slog.d(TAG, "KnoxCapture input filter uninstalled");
        }
        this.installed = false;
        super.onUninstalled();
    }

    public void onInputEvent(InputEvent inputEvent, int i) {
        if (DEBUG) {
            Slog.d(TAG, "Received event: " + inputEvent + ", policyFlags=0x" + Integer.toHexString(i));
        }
        if (inputEvent instanceof MotionEvent) {
            super.onInputEvent(inputEvent, i);
        } else if (inputEvent instanceof KeyEvent) {
            if (this.scannerDevices.contains(Integer.valueOf(inputEvent.getDeviceId()))) {
                sendKeyEvent((KeyEvent) inputEvent);
            } else {
                super.onInputEvent(inputEvent, i);
            }
        }
    }

    public final void sendKeyEvent(KeyEvent keyEvent) {
        if (this.activeScanDeviceId == -1) {
            this.activeScanDeviceId = keyEvent.getDeviceId();
        }
        this.scanEventHandler.removeMessages(852);
        if (keyEvent.getAction() == 0 && keyEvent.getDeviceId() == this.activeScanDeviceId) {
            ScanEventHandler scanEventHandler = this.scanEventHandler;
            scanEventHandler.sendMessage(Message.obtain(scanEventHandler, 851, new KeyEvent(keyEvent)));
        }
        this.scanEventHandler.sendEmptyMessageDelayed(852, 100L);
    }

    public final void updateBarcodeDataFromhw(int i, byte[] bArr) {
        Uri parse = Uri.parse("content://com.samsung.android.bbc.bbcagent/knoxCapture/updateBarcodeDataFromhw");
        ContentValues contentValues = new ContentValues();
        contentValues.put("deviceId", Integer.valueOf(i));
        contentValues.put("scanInput", bArr);
        this.context.getContentResolver().update(parse, contentValues, null, null);
    }

    /* loaded from: classes.dex */
    public final class ScanEventHandler extends Handler {
        public List activeScanInput;
        public StringBuilder unicodeCodepoint;

        public ScanEventHandler(Looper looper) {
            super(looper);
            this.activeScanInput = new ArrayList();
            this.unicodeCodepoint = new StringBuilder();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 851) {
                if (KnoxCaptureInputFilter.DEBUG) {
                    Slog.d(KnoxCaptureInputFilter.TAG, "ScanEventHandler, MSG_NEXT_KEY_EVENT, new key event");
                }
                this.activeScanInput.add((KeyEvent) message.obj);
                return;
            }
            if (i != 852) {
                return;
            }
            if (KnoxCaptureInputFilter.DEBUG) {
                Slog.d(KnoxCaptureInputFilter.TAG, "ScanEventHandler, END, activeScanDeviceId =" + KnoxCaptureInputFilter.this.activeScanDeviceId);
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = this.activeScanInput.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                byte[] processKeyEvent = processKeyEvent((KeyEvent) it.next());
                if (processKeyEvent != null) {
                    for (byte b : processKeyEvent) {
                        arrayList.add(Byte.valueOf(b));
                    }
                }
            }
            if (arrayList.size() > 0) {
                byte[] bArr = new byte[arrayList.size()];
                Iterator it2 = arrayList.iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    bArr[i2] = ((Byte) it2.next()).byteValue();
                    i2++;
                }
                if (KnoxCaptureInputFilter.DEBUG) {
                    Slog.d(KnoxCaptureInputFilter.TAG, "ScanEventHandler, END, scannedInput: " + new String(bArr));
                }
                KnoxCaptureInputFilter knoxCaptureInputFilter = KnoxCaptureInputFilter.this;
                knoxCaptureInputFilter.updateBarcodeDataFromhw(knoxCaptureInputFilter.activeScanDeviceId, bArr);
            }
            if (KnoxCaptureInputFilter.DEBUG) {
                Slog.d(KnoxCaptureInputFilter.TAG, "ScanEventHandler, END, clearing active scan input");
            }
            this.activeScanInput.clear();
            KnoxCaptureInputFilter.this.activeScanDeviceId = -1;
            if (this.unicodeCodepoint.length() > 0) {
                StringBuilder sb = this.unicodeCodepoint;
                sb.delete(0, sb.length());
            }
        }

        public final byte[] processKeyEvent(KeyEvent keyEvent) {
            if (KnoxCaptureInputFilter.DEBUG) {
                Slog.d(KnoxCaptureInputFilter.TAG, "processKeyEvent event: " + keyEvent);
            }
            int keyCode = keyEvent.getKeyCode();
            byte[] bArr = null;
            if (KeyEvent.isModifierKey(keyCode)) {
                return null;
            }
            boolean z = true;
            if (isUnicodeCodepoint(keyEvent)) {
                if (KnoxCaptureInputFilter.DEBUG) {
                    Slog.d(KnoxCaptureInputFilter.TAG, " processKeyEvent, unicode codepoint");
                }
                this.unicodeCodepoint.append(convertNumpadToString(keyEvent));
                if (this.unicodeCodepoint.length() == 4) {
                    bArr = getUnicodeChar(this.unicodeCodepoint.toString());
                } else {
                    z = false;
                }
            } else if (isAsciiControlCharacter(keyEvent)) {
                bArr = getAsciiControlChar(keyEvent);
                KeyCharacterMap load = KeyCharacterMap.load(keyEvent.getDeviceId());
                if (KnoxCaptureInputFilter.DEBUG) {
                    Slog.d(KnoxCaptureInputFilter.TAG, "processKeyEvent, ascii ctrl char=" + load.get(keyCode, keyEvent.getMetaState()));
                }
            } else {
                if (KnoxCaptureInputFilter.DEBUG) {
                    Slog.d(KnoxCaptureInputFilter.TAG, " processKeyEvent, any other character");
                }
                bArr = new byte[]{(byte) KeyCharacterMap.load(keyEvent.getDeviceId()).get(keyCode, keyEvent.getMetaState())};
            }
            if (z) {
                if (KnoxCaptureInputFilter.DEBUG) {
                    Slog.d(KnoxCaptureInputFilter.TAG, " processKeyEvent, clear unicode codepoint buffer");
                }
                if (this.unicodeCodepoint.length() > 0) {
                    StringBuilder sb = this.unicodeCodepoint;
                    sb.delete(0, sb.length());
                }
            }
            return bArr;
        }

        public final boolean isUnicodeCodepoint(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            return keyCode >= 144 && keyCode <= 153 && keyEvent.isAltPressed();
        }

        public final boolean isAsciiControlCharacter(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            return ((keyCode >= 29 && keyCode <= 54) || keyCode == 71 || keyCode == 72 || keyCode == 73 || keyCode == 13 || keyCode == 69) && keyEvent.isCtrlPressed();
        }

        public final String convertNumpadToString(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case 144:
                    return "0";
                case 145:
                    return "1";
                case 146:
                    return "2";
                case 147:
                    return "3";
                case 148:
                    return "4";
                case 149:
                    return "5";
                case 150:
                    return "6";
                case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE /* 151 */:
                    return "7";
                case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_STARTED /* 152 */:
                    return "8";
                case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED /* 153 */:
                    return "9";
                default:
                    return "\u0000";
            }
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:5:0x000e. Please report as an issue. */
        /* JADX WARN: Failed to find 'out' block for switch in B:6:0x0011. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0171  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x017e  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0164  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final byte[] getAsciiControlChar(android.view.KeyEvent r5) {
            /*
                Method dump skipped, instructions count: 498
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.KnoxCaptureInputFilter.ScanEventHandler.getAsciiControlChar(android.view.KeyEvent):byte[]");
        }

        public final byte[] getUnicodeChar(String str) {
            try {
                int parseInt = Integer.parseInt(str);
                char[] chars = Character.toChars(parseInt);
                if (KnoxCaptureInputFilter.DEBUG) {
                    Slog.d(KnoxCaptureInputFilter.TAG, " getUnicodeChar codePointStr: " + str + " codePoint: " + parseInt + " unicodeChars: " + new String(chars));
                }
                byte[] bArr = new byte[chars.length];
                int length = chars.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    int i3 = i2 + 1;
                    bArr[i2] = (byte) chars[i];
                    i++;
                    i2 = i3;
                }
                return bArr;
            } catch (Exception e) {
                Slog.d(KnoxCaptureInputFilter.TAG, " getUnicodeChar exception: " + e);
                return null;
            }
        }
    }
}
