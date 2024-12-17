package com.android.server.knox.zt.devicetrust.data;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcData extends EndpointDataUnion {
    public final int event;

    public ProcData(int i, EndpointData endpointData) {
        super(endpointData);
        this.event = i;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointDataUnion
    public final EndpointData getTypeChecked() {
        switch (this.event) {
            case 701:
                EndpointData endpointData = this.realData;
                if (endpointData instanceof SchedProcessExecData) {
                    return endpointData;
                }
            case EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_EXIT /* 702 */:
                EndpointData endpointData2 = this.realData;
                if (endpointData2 instanceof SchedProcessExitData) {
                    return endpointData2;
                }
            case EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_FORK /* 703 */:
                EndpointData endpointData3 = this.realData;
                if (endpointData3 instanceof SchedProcessForkData) {
                    return endpointData3;
                }
                return null;
            default:
                return null;
        }
    }
}
