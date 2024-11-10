package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import com.android.server.hdmi.HdmiControlService;

/* loaded from: classes2.dex */
public class ArcInitiationActionFromAvr extends HdmiCecFeatureAction {
    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    public ArcInitiationActionFromAvr(HdmiCecLocalDevice hdmiCecLocalDevice) {
        super(hdmiCecLocalDevice);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean start() {
        audioSystem().setArcStatus(true);
        this.mState = 1;
        addTimer(1, 1000);
        sendInitiateArc();
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1) {
            return false;
        }
        int opcode = hdmiCecMessage.getOpcode();
        if (opcode == 0) {
            if ((hdmiCecMessage.getParams()[0] & 255) != 192) {
                return false;
            }
            audioSystem().setArcStatus(false);
            finish();
            return true;
        }
        if (opcode == 193) {
            this.mState = 2;
            finish();
            return true;
        }
        if (opcode != 194) {
            return false;
        }
        audioSystem().setArcStatus(false);
        finish();
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 1) {
            handleInitiateArcTimeout();
        }
    }

    public void sendInitiateArc() {
        sendCommand(HdmiCecMessageBuilder.buildInitiateArc(getSourceAddress(), 0), new HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.ArcInitiationActionFromAvr$$ExternalSyntheticLambda0
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public final void onSendCompleted(int i) {
                ArcInitiationActionFromAvr.this.lambda$sendInitiateArc$0(i);
            }
        });
    }

    public /* synthetic */ void lambda$sendInitiateArc$0(int i) {
        if (i != 0) {
            audioSystem().setArcStatus(false);
            finish();
        }
    }

    public final void handleInitiateArcTimeout() {
        HdmiLogger.debug("handleInitiateArcTimeout", new Object[0]);
        finish();
    }
}
