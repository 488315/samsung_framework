package com.samsung.android.sdk.scs.base.tasks;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class Task {
    public abstract Exception getException();

    public abstract Object getResult();

    public abstract boolean isComplete();

    public abstract boolean isSuccessful();
}