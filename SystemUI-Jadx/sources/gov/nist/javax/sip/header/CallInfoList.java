package gov.nist.javax.sip.header;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class CallInfoList extends SIPHeaderList<CallInfo> {
    private static final long serialVersionUID = -4949850334388806423L;

    public CallInfoList() {
        super(CallInfo.class, "Call-Info");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        CallInfoList callInfoList = new CallInfoList();
        callInfoList.clonehlist(this.hlist);
        return callInfoList;
    }
}
