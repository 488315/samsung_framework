package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ScCloseData extends TracepointData {
    public final int fd;
    public final long ret;

    public ScCloseData(int i, int i2, long j, long j2, long j3, long j4, String str) {
        super(i, j2, j3, j4, str);
        this.fd = i2;
        this.ret = j;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public String toLine() {
        return String.format(Locale.US, "when : %d | what : %d | pid : %d | uid : %d | comm : %s | fd : %d | ret : %d%s", Long.valueOf(getTime()), Integer.valueOf(getEvent()), Integer.valueOf(getPid()), Integer.valueOf(getUid()), getComm(), Integer.valueOf(this.fd), Long.valueOf(this.ret), readExtras(true));
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(getTime()));
        hashMap.put("what", Integer.toString(getEvent()));
        hashMap.put("pid", Integer.toString(getPid()));
        hashMap.put("uid", Integer.toString(getUid()));
        hashMap.put("comm", getComm());
        hashMap.put("fd", Integer.toString(this.fd));
        hashMap.put("ret", Long.toString(this.ret));
        return hashMap;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", getTime());
        bundle.putInt("what", getEvent());
        bundle.putInt("pid", getPid());
        bundle.putInt("uid", getUid());
        bundle.putString("comm", getComm());
        bundle.putInt("fd", this.fd);
        bundle.putLong("ret", this.ret);
        readExtras(bundle);
        return bundle;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("when", getTime());
            jSONObject.put("what", getEvent());
            jSONObject.put("pid", getPid());
            jSONObject.put("uid", getUid());
            jSONObject.put("comm", getComm());
            jSONObject.put("fd", this.fd);
            jSONObject.put("ret", this.ret);
            readExtras(jSONObject);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }
}
