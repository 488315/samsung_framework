package com.sec.internal.ims.servicemodules.euc.data;

/* loaded from: classes.dex */
public interface IEucQuery extends Iterable<IDialogData> {
    void addDialogData(IDialogData iDialogData);

    IDialogData getDialogData(String str);

    IEucData getEucData();

    boolean hasDialog(String str);
}
