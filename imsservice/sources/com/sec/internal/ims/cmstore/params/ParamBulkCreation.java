package com.sec.internal.ims.cmstore.params;

import android.util.Pair;
import com.sec.internal.helper.httpclient.HttpPostBody;
import com.sec.internal.omanetapi.nms.data.ObjectList;
import java.util.List;

/* loaded from: classes.dex */
public class ParamBulkCreation {
    public final BufferDBChangeParamList bufferDbParamList;
    public final String mLine;
    public final Pair<ObjectList, List<HttpPostBody>> uploadObjectInfo;

    public ParamBulkCreation(Pair<ObjectList, List<HttpPostBody>> pair, BufferDBChangeParamList bufferDBChangeParamList, String str) {
        this.uploadObjectInfo = pair;
        this.bufferDbParamList = bufferDBChangeParamList;
        this.mLine = str;
    }
}
