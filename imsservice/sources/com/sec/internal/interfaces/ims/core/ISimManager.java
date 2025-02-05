package com.sec.internal.interfaces.ims.core;

import android.content.ContentValues;
import android.os.Handler;
import android.os.Message;
import android.telephony.SubscriptionInfo;
import com.sec.internal.constants.Mno;
import java.util.List;

/* loaded from: classes.dex */
public interface ISimManager extends ISequentialInitializable {
    public static final int IMSSWITCH_DEFAULT = 2;
    public static final int IMSSWITCH_JSON = 4;
    public static final int IMSSWITCH_NOT_EXIST = -1;
    public static final int IMSSWITCH_NOT_USE = 0;
    public static final int IMSSWITCH_OFF = 5;
    public static final int IMSSWITCH_SIMMOBILITY = 3;
    public static final String KEY_GLOBALGC_ENABLED = "globalgcenabled";
    public static final String KEY_HAS_SIM = "hassim";
    public static final String KEY_IMSI = "imsi";
    public static final String KEY_IMSSWITCH_TYPE = "imsSwitchType";
    public static final String KEY_MNO_NAME = "mnoname";
    public static final String KEY_MVNO_NAME = "mvnoname";
    public static final String KEY_NW_NAME = "NetworkName";
    public static final String KEY_SIMMO_TYPE = "simMoType";
    public static final int SIMMOBILITY_NONE = 0;
    public static final int SIMMOBILITY_RCS = 2;
    public static final int SIMMOBILITY_VOLTE = 1;
    public static final int SIMMOBILITY_VOLTE_RCS = 3;

    void deRegisterSimCardEventListener(ISimEventListener iSimEventListener);

    void deregisterForSimReady(Handler handler);

    void deregisterForSimRefresh(Handler handler);

    void deregisterForSimRemoved(Handler handler);

    void deregisterForSimStateChanged(Handler handler);

    void dump();

    String getDerivedImpi();

    String getDerivedImpu();

    String getDerivedImpuFromMsisdn();

    Mno getDevMno();

    String getEmergencyImpu();

    String getGid1();

    String getHighestPriorityEhplmn();

    String getImpi();

    String getImpuFromSim();

    String getImsi();

    String getImsiFromImpi();

    String getIsimAuthentication(String str);

    String getLine1Number();

    String getLine1Number(int i);

    Mno getMnoFromNetworkPlmn(String str);

    ContentValues getMnoInfo();

    String getMsisdn();

    Mno getNetMno();

    List<String> getNetworkNames();

    String getSimCountryIso();

    Mno getSimMno();

    String getSimMnoName();

    String getSimOperator();

    String getSimOperatorFromImpi();

    String getSimSerialNumber();

    int getSimSlotIndex();

    int getSimState();

    int getSubscriptionId();

    boolean hasIsim();

    boolean hasNoSim();

    boolean hasVsim();

    boolean isEsim();

    boolean isGBASupported();

    boolean isISimDataValid();

    boolean isLabSimCard();

    boolean isOutBoundSIM();

    boolean isSimAvailable();

    boolean isSimLoaded();

    void onImsSwitchUpdated(int i);

    void registerForSimReady(Handler handler, int i, Object obj);

    void registerForSimRefresh(Handler handler, int i, Object obj);

    void registerForSimRemoved(Handler handler, int i, Object obj);

    void registerForSimStateChanged(Handler handler, int i, Object obj);

    void registerForUiccChanged(Handler handler, int i, Object obj);

    void registerSimCardEventListener(ISimEventListener iSimEventListener);

    void requestIsimAuthentication(String str, int i, Message message);

    void requestIsimAuthentication(String str, Message message);

    void requestSoftphoneAuthentication(String str, String str2, Message message, int i);

    void setIsimLoaded();

    void setSimRefreshed();

    void setSubscriptionInfo(SubscriptionInfo subscriptionInfo);
}
