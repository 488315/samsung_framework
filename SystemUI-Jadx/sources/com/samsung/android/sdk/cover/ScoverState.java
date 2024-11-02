package com.samsung.android.sdk.cover;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class ScoverState {
    public final boolean attached;
    public final int color;
    public final boolean fakeCover;
    public final int fotaMode;
    public final int heightPixel;
    public final boolean switchState;
    public final int type;
    public final int widthPixel;

    public ScoverState() {
        this.switchState = true;
        this.type = 2;
        this.color = 0;
        this.widthPixel = 0;
        this.heightPixel = 0;
        this.attached = false;
        this.fakeCover = false;
        this.fotaMode = 0;
    }

    public final String toString() {
        return String.format("ScoverState(switchState=%b type=%d color=%d widthPixel=%d heightPixel=%d attached=%b fakeCover=%b fotaMode=%d)", Boolean.valueOf(this.switchState), Integer.valueOf(this.type), Integer.valueOf(this.color), Integer.valueOf(this.widthPixel), Integer.valueOf(this.heightPixel), Boolean.valueOf(this.attached), Boolean.valueOf(this.fakeCover), Integer.valueOf(this.fotaMode));
    }

    public ScoverState(boolean z, int i, int i2, int i3, int i4) {
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = false;
        this.fakeCover = false;
        this.fotaMode = 0;
    }

    public ScoverState(boolean z, int i, int i2, int i3, int i4, boolean z2) {
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z2;
        this.fakeCover = false;
        this.fotaMode = 0;
    }

    public ScoverState(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5) {
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z2;
        this.fakeCover = false;
        this.fotaMode = 0;
    }

    public ScoverState(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, boolean z3) {
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z2;
        this.fakeCover = z3;
        this.fotaMode = 0;
    }

    public ScoverState(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5, boolean z3, int i6) {
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z2;
        this.fakeCover = z3;
        this.fotaMode = i6;
    }
}
