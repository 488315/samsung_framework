package com.android.server.speech;

import android.speech.IRecognitionService;
import com.android.internal.infra.ServiceConnector;
import com.android.server.speech.RemoteSpeechRecognitionService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteSpeechRecognitionService$$ExternalSyntheticLambda2 implements ServiceConnector.VoidJob {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RemoteSpeechRecognitionService$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void runNoResult(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                int i2 = RemoteSpeechRecognitionService.$r8$clinit;
                ((RemoteSpeechRecognitionService) obj2).unbind();
                break;
            default:
                int i3 = RemoteSpeechRecognitionService.$r8$clinit;
                ((IRecognitionService) obj).stopListening((RemoteSpeechRecognitionService.DelegatingListener) obj2);
                break;
        }
    }
}
