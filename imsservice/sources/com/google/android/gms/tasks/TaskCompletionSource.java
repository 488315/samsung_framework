package com.google.android.gms.tasks;

/* loaded from: classes.dex */
public class TaskCompletionSource<TResult> {
    private final zzp<TResult> zzlel = new zzp<>();

    public Task<TResult> getTask() {
        return this.zzlel;
    }

    public void setException(Exception exc) {
        this.zzlel.setException(exc);
    }

    public void setResult(TResult tresult) {
        this.zzlel.setResult(tresult);
    }
}
