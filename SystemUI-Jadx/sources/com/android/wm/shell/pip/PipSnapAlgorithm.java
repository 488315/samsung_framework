package com.android.wm.shell.pip;

import android.graphics.Rect;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class PipSnapAlgorithm {
    public static void applySnapFraction(float f, Rect rect, Rect rect2) {
        if (f < 1.0f) {
            rect.offsetTo(rect2.left + ((int) (f * rect2.width())), rect2.top);
            return;
        }
        if (f < 2.0f) {
            rect.offsetTo(rect2.right, rect2.top + ((int) ((f - 1.0f) * rect2.height())));
        } else if (f < 3.0f) {
            rect.offsetTo(rect2.left + ((int) ((1.0f - (f - 2.0f)) * rect2.width())), rect2.bottom);
        } else {
            rect.offsetTo(rect2.left, rect2.top + ((int) ((1.0f - (f - 3.0f)) * rect2.height())));
        }
    }

    public final float getSnapFraction(int i, Rect rect, Rect rect2) {
        Rect rect3 = new Rect();
        snapRectToClosestEdge(rect, rect2, rect3, i);
        float width = (rect3.left - rect2.left) / rect2.width();
        float height = (rect3.top - rect2.top) / rect2.height();
        int i2 = rect3.top;
        if (i2 == rect2.top) {
            return width;
        }
        if (rect3.left == rect2.right) {
            return height + 1.0f;
        }
        if (i2 == rect2.bottom) {
            return (1.0f - width) + 2.0f;
        }
        return (1.0f - height) + 3.0f;
    }

    public void snapRectToClosestEdge(Rect rect, Rect rect2, Rect rect3, int i) {
        int i2 = rect.left;
        if (i == 1) {
            i2 = rect2.left;
        } else if (i == 2) {
            i2 = rect2.right;
        }
        int max = Math.max(rect2.left, Math.min(rect2.right, i2));
        int max2 = Math.max(rect2.top, Math.min(rect2.bottom, rect.top));
        rect3.set(rect);
        int abs = Math.abs(i2 - rect2.left);
        int abs2 = Math.abs(rect.top - rect2.top);
        int abs3 = Math.abs(rect2.right - i2);
        int min = Math.min(Math.min(abs, abs3), Math.min(abs2, Math.abs(rect2.bottom - rect.top)));
        if (min == abs) {
            rect3.offsetTo(rect2.left, max2);
            return;
        }
        if (min == abs2) {
            rect3.offsetTo(max, rect2.top);
        } else if (min == abs3) {
            rect3.offsetTo(rect2.right, max2);
        } else {
            rect3.offsetTo(max, rect2.bottom);
        }
    }

    public static void applySnapFraction(Rect rect, Rect rect2, float f, int i, int i2, Rect rect3, Rect rect4) {
        int i3;
        applySnapFraction(f, rect, rect2);
        if (i != 0) {
            if (i == 1) {
                i3 = (i2 - rect.width()) + rect4.left;
            } else {
                i3 = (rect3.right - i2) - rect4.right;
            }
            rect.offsetTo(i3, rect.top);
        }
    }
}
