package com.samsung.android.hardware.secinputdev.hal;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceRawdataService;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SysinputHALCallback {
    private static final String TAG = "SysinputHALCallback";
    private static volatile SysinputHALCallback uniqueInstance = null;
    private SemInputDeviceRawdataService[] rawdataServices = new SemInputDeviceRawdataService[3];
    private final SysinputHALInterface sysinputHAL;

    private SysinputHALCallback(SysinputHALInterface hal) {
        this.sysinputHAL = hal;
    }

    public static SysinputHALCallback getInstance(SysinputHALInterface hal) {
        if (uniqueInstance == null) {
            synchronized (SysinputHALCallback.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SysinputHALCallback(hal);
                }
            }
        }
        return uniqueInstance;
    }

    public static void setRawdataService(int displayId, SemInputDeviceRawdataService rawdata) {
        if (uniqueInstance == null) {
            return;
        }
        uniqueInstance.rawdataServices[displayId] = rawdata;
    }

    public void onReportInformation(int type, String data) {
        SemInputDeviceRawdataService semInputDeviceRawdataService = this.rawdataServices[type];
        if (semInputDeviceRawdataService != null) {
            semInputDeviceRawdataService.onReportInformation(data);
        }
    }

    public void onReportInformationAidl(int type, String data) {
        if (SemInputDeviceManager.REPORT_INFO_HANDEDGE.equals(data)) {
            SemInputDeviceRawdataService semInputDeviceRawdataService = this.rawdataServices[type];
            if (semInputDeviceRawdataService != null) {
                semInputDeviceRawdataService.onReportInformation(data);
                return;
            }
            return;
        }
        SysinputHALInterface sysinputHALInterface = this.sysinputHAL;
        if (sysinputHALInterface != null) {
            sysinputHALInterface.onReportInformation(type, data);
        }
    }

    public void onReportRawData(int type, int count, ArrayList<Short> list) {
        if (this.rawdataServices[type] != null) {
            int[] intList = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                intList[i] = list.get(i).shortValue();
            }
            this.rawdataServices[type].onReportRawData(count, intList);
        }
    }

    public void onReportRawData(int type, int count, int[] list) {
        SemInputDeviceRawdataService semInputDeviceRawdataService = this.rawdataServices[type];
        if (semInputDeviceRawdataService != null) {
            semInputDeviceRawdataService.onReportRawData(count, list);
        }
    }
}
