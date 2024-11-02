package gov.nist.javax.sip.header;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class ErrorInfoList extends SIPHeaderList<ErrorInfo> {
    private static final long serialVersionUID = 1;

    public ErrorInfoList() {
        super(ErrorInfo.class, "Error-Info");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ErrorInfoList errorInfoList = new ErrorInfoList();
        errorInfoList.clonehlist(this.hlist);
        return errorInfoList;
    }
}
