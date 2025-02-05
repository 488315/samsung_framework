package android.view;

import android.graphics.Rect;
import android.view.WindowManager;
import android.window.ClientWindowFrames;

/* loaded from: classes4.dex */
public class WindowlessWindowLayout extends WindowLayout {
    @Override // android.view.WindowLayout
    public void computeFrames(WindowManager.LayoutParams attrs, InsetsState state, Rect displayCutoutSafe, Rect windowBounds, int windowingMode, int requestedWidth, int requestedHeight, int requestedVisibleTypes, float compatScale, ClientWindowFrames frames) {
        computeFrames(attrs, state, displayCutoutSafe, windowBounds, windowingMode, requestedWidth, requestedHeight, requestedVisibleTypes, compatScale, frames, 0);
    }

    @Override // android.view.WindowLayout
    public void computeFrames(WindowManager.LayoutParams attrs, InsetsState state, Rect displayCutoutSafe, Rect windowBounds, int windowingMode, int requestedWidth, int requestedHeight, int requestedVisibleTypes, float compatScale, ClientWindowFrames frames, int stageType) {
        if (frames.attachedFrame == null) {
            frames.frame.set(0, 0, attrs.width, attrs.height);
            frames.parentFrame.set(frames.frame);
            frames.displayFrame.set(frames.frame);
        } else {
            int height = calculateLength(attrs.height, requestedHeight, frames.attachedFrame.height());
            int width = calculateLength(attrs.width, requestedWidth, frames.attachedFrame.width());
            Gravity.apply(attrs.gravity, width, height, frames.attachedFrame, (int) (attrs.x + attrs.horizontalMargin), (int) (attrs.y + attrs.verticalMargin), frames.frame);
            frames.displayFrame.set(frames.frame);
            frames.parentFrame.set(frames.attachedFrame);
        }
    }

    private static int calculateLength(int attrLength, int requestedLength, int parentLength) {
        if (attrLength == -1) {
            return parentLength;
        }
        if (attrLength == -2) {
            return requestedLength;
        }
        return attrLength;
    }
}
