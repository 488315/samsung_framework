package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.operations.DrawBase4;

/* loaded from: classes5.dex */
public class DrawOval extends DrawBase4 {
    public static final DrawBase4.Companion COMPANION = new DrawBase4.Companion(56) { // from class: com.android.internal.widget.remotecompose.core.operations.DrawOval.1
        @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Companion
        public Operation construct(float x1, float y1, float x2, float y2) {
            return new DrawOval(x1, y1, x2, y2);
        }
    };

    public DrawOval(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        this.mName = "DrawOval";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawOval(this.mX1, this.mY1, this.mX2, this.mY2);
    }
}
