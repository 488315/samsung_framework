package com.android.keyguard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternView;
import com.android.systemui.R;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class SecLockPatternView extends LockPatternView {
    public Bitmap mBitmapError;
    public int mBitmapHeight;
    public Bitmap mBitmapRegular;
    public Bitmap mBitmapSuccess;
    public int mBitmapWidth;
    public final Matrix mCircleMatrix;
    public boolean mDecoPatternEnabled;
    public boolean mIsWhiteWp;

    public SecLockPatternView(Context context) {
        this(context, null);
    }

    public final void addCellToPattern(LockPatternView.Cell cell) {
        super.addCellToPattern(cell);
    }

    public final Bitmap getScaledBitmapFor(int i) {
        Bitmap decodeResource = BitmapFactory.decodeResource(getContext().getResources(), i);
        int dimension = (int) getContext().getResources().getDimension(R.dimen.theme_keyguard_pattern_dot_size);
        if (decodeResource == null) {
            NestedScrollView$$ExternalSyntheticOutline0.m("getScaledBitmapFor() return null - bitmap is null resId = ", i, "SecLockPatternView");
            return null;
        }
        return Bitmap.createScaledBitmap(decodeResource, dimension, dimension, true);
    }

    public final void handleActionMove(MotionEvent motionEvent) {
        float x;
        float y;
        if (!this.mDecoPatternEnabled) {
            super.handleActionMove(motionEvent);
            return;
        }
        float f = ((LockPatternView) this).mSquareWidth * 0.05f;
        int historySize = motionEvent.getHistorySize();
        ((LockPatternView) this).mTmpInvalidateRect.setEmpty();
        boolean z = false;
        for (int i = 0; i < historySize + 1; i++) {
            if (i < historySize) {
                x = motionEvent.getHistoricalX(i);
            } else {
                x = motionEvent.getX();
            }
            if (i < historySize) {
                y = motionEvent.getHistoricalY(i);
            } else {
                y = motionEvent.getY();
            }
            LockPatternView.Cell detectAndAddHit = detectAndAddHit(x, y);
            int size = ((LockPatternView) this).mPattern.size();
            if (detectAndAddHit != null && size == 1) {
                ((LockPatternView) this).mPatternInProgress = true;
                notifyPatternStarted();
            }
            float abs = Math.abs(x - ((LockPatternView) this).mInProgressX);
            float abs2 = Math.abs(y - ((LockPatternView) this).mInProgressY);
            if (abs > 0.0f || abs2 > 0.0f) {
                z = true;
            }
            if (((LockPatternView) this).mPatternInProgress && size > 0) {
                LockPatternView.Cell cell = (LockPatternView.Cell) ((LockPatternView) this).mPattern.get(size - 1);
                float centerXForColumn = getCenterXForColumn(cell.getColumn());
                float centerYForRow = getCenterYForRow(cell.getRow());
                float min = (Math.min(centerXForColumn, x) - f) - 20.0f;
                float max = Math.max(centerXForColumn, x) + f + 20.0f;
                float min2 = (Math.min(centerYForRow, y) - f) - 20.0f;
                float max2 = Math.max(centerYForRow, y) + f + 20.0f;
                if (detectAndAddHit != null) {
                    float f2 = ((LockPatternView) this).mSquareWidth * 0.5f;
                    float f3 = ((LockPatternView) this).mSquareHeight * 0.5f;
                    float centerXForColumn2 = getCenterXForColumn(detectAndAddHit.getColumn());
                    float centerYForRow2 = getCenterYForRow(detectAndAddHit.getRow());
                    min = Math.min(centerXForColumn2 - f2, min);
                    max = Math.max(centerXForColumn2 + f2, max);
                    min2 = Math.min(centerYForRow2 - f3, min2);
                    max2 = Math.max(centerYForRow2 + f3, max2);
                }
                ((LockPatternView) this).mTmpInvalidateRect.union(Math.round(min), Math.round(min2), Math.round(max), Math.round(max2));
            }
        }
        ((LockPatternView) this).mInProgressX = motionEvent.getX();
        ((LockPatternView) this).mInProgressY = motionEvent.getY();
        if (z) {
            ((LockPatternView) this).mInvalidate.union(((LockPatternView) this).mTmpInvalidateRect);
            invalidate(((LockPatternView) this).mInvalidate);
            ((LockPatternView) this).mInvalidate.set(((LockPatternView) this).mTmpInvalidateRect);
        }
    }

    public final void onDraw(Canvas canvas) {
        Bitmap bitmap;
        boolean z;
        if (!this.mDecoPatternEnabled) {
            super.onDraw(canvas);
            return;
        }
        ArrayList arrayList = ((LockPatternView) this).mPattern;
        int size = arrayList.size();
        boolean[][] zArr = ((LockPatternView) this).mPatternDrawLookup;
        if (((LockPatternView) this).mPatternDisplayMode == LockPatternView.DisplayMode.Animate) {
            int elapsedRealtime = (((int) (SystemClock.elapsedRealtime() - ((LockPatternView) this).mAnimatingPeriodStart)) % ((size + 1) * KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED)) / KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED;
            clearPatternDrawLookup();
            for (int i = 0; i < elapsedRealtime; i++) {
                LockPatternView.Cell cell = (LockPatternView.Cell) arrayList.get(i);
                zArr[cell.getRow()][cell.getColumn()] = true;
            }
            if (elapsedRealtime > 0 && elapsedRealtime < size) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                float f = (r6 % KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED) / 700.0f;
                LockPatternView.Cell cell2 = (LockPatternView.Cell) arrayList.get(elapsedRealtime - 1);
                float centerXForColumn = getCenterXForColumn(cell2.getColumn());
                float centerYForRow = getCenterYForRow(cell2.getRow());
                LockPatternView.Cell cell3 = (LockPatternView.Cell) arrayList.get(elapsedRealtime);
                float centerXForColumn2 = (getCenterXForColumn(cell3.getColumn()) - centerXForColumn) * f;
                float centerYForRow2 = (getCenterYForRow(cell3.getRow()) - centerYForRow) * f;
                ((LockPatternView) this).mInProgressX = centerXForColumn + centerXForColumn2;
                ((LockPatternView) this).mInProgressY = centerYForRow + centerYForRow2;
            }
            invalidate();
        }
        float f2 = ((LockPatternView) this).mSquareWidth;
        float f3 = ((LockPatternView) this).mSquareHeight;
        ((LockPatternView) this).mPathPaint.setStrokeWidth(0.05f * f2);
        ((LockPatternView) this).mPathPaint.setAlpha(128);
        Path path = ((LockPatternView) this).mCurrentPath;
        path.rewind();
        boolean z2 = !((LockPatternView) this).mInStealthMode;
        if (z2) {
            if (this.mIsWhiteWp) {
                ((LockPatternView) this).mPathPaint.setColor(getResources().getColor(R.color.theme_pattern_line_color_black, null));
            } else {
                ((LockPatternView) this).mPathPaint.setColor(getResources().getColor(R.color.theme_pattern_line_color, null));
            }
        }
        if (z2) {
            float f4 = 0.0f;
            float f5 = 0.0f;
            int i2 = 0;
            boolean z3 = false;
            while (i2 < size) {
                LockPatternView.Cell cell4 = (LockPatternView.Cell) arrayList.get(i2);
                if (!zArr[cell4.getRow()][cell4.getColumn()]) {
                    break;
                }
                float centerXForColumn3 = getCenterXForColumn(cell4.getColumn());
                float centerYForRow3 = getCenterYForRow(cell4.getRow());
                if (i2 != 0) {
                    LockPatternView.CellState cellState = ((LockPatternView) this).mCellStates[cell4.getRow()][cell4.getColumn()];
                    path.rewind();
                    path.moveTo(f4, f5);
                    if (Float.compare(cellState.lineEndX, Float.MIN_VALUE) != 0 && Float.compare(cellState.lineEndY, Float.MIN_VALUE) != 0) {
                        path.lineTo(cellState.lineEndX, cellState.lineEndY);
                    } else {
                        path.lineTo(centerXForColumn3, centerYForRow3);
                    }
                    canvas.drawPath(path, ((LockPatternView) this).mPathPaint);
                }
                i2++;
                f4 = centerXForColumn3;
                f5 = centerYForRow3;
                z3 = true;
            }
            if ((((LockPatternView) this).mPatternInProgress || ((LockPatternView) this).mPatternDisplayMode == LockPatternView.DisplayMode.Animate) && z3) {
                path.rewind();
                path.moveTo(f4, f5);
                path.lineTo(((LockPatternView) this).mInProgressX, ((LockPatternView) this).mInProgressY);
                path.lineTo(((LockPatternView) this).mInProgressX, ((LockPatternView) this).mInProgressY);
                canvas.drawPath(path, ((LockPatternView) this).mPathPaint);
            }
        }
        int i3 = ((LockPatternView) this).mPaddingTop;
        int i4 = ((LockPatternView) this).mPaddingLeft;
        int i5 = 0;
        while (true) {
            if (i5 < 3) {
                float f6 = (i5 * f3) + i3;
                int i6 = 0;
                for (int i7 = 3; i6 < i7; i7 = 3) {
                    int i8 = (int) ((i6 * f2) + i4);
                    int i9 = (int) f6;
                    if (zArr[i5][i6] && !((LockPatternView) this).mInStealthMode) {
                        if (((LockPatternView) this).mPatternInProgress) {
                            bitmap = this.mBitmapSuccess;
                        } else {
                            LockPatternView.DisplayMode displayMode = ((LockPatternView) this).mPatternDisplayMode;
                            if (displayMode == LockPatternView.DisplayMode.Wrong) {
                                bitmap = this.mBitmapError;
                                if (bitmap == null) {
                                    bitmap = this.mBitmapRegular;
                                }
                            } else {
                                if (displayMode != LockPatternView.DisplayMode.Correct && displayMode != LockPatternView.DisplayMode.Animate) {
                                    throw new IllegalStateException("unknown display mode " + ((LockPatternView) this).mPatternDisplayMode);
                                }
                                bitmap = this.mBitmapSuccess;
                            }
                        }
                    } else {
                        bitmap = this.mBitmapRegular;
                    }
                    int i10 = this.mBitmapWidth;
                    int i11 = this.mBitmapHeight;
                    float f7 = ((LockPatternView) this).mSquareWidth;
                    int i12 = i3;
                    float f8 = i10;
                    int i13 = i4;
                    int i14 = (int) ((f7 - f8) / 2.0f);
                    int i15 = (int) ((((LockPatternView) this).mSquareHeight - i11) / 2.0f);
                    float min = Math.min(f7 / f8, 1.0f);
                    float min2 = Math.min(((LockPatternView) this).mSquareHeight / this.mBitmapHeight, 1.0f);
                    this.mCircleMatrix.setTranslate(i8 + i14, i9 + i15);
                    this.mCircleMatrix.preTranslate(this.mBitmapWidth / 2.0f, this.mBitmapHeight / 2.0f);
                    this.mCircleMatrix.preScale(min, min2);
                    this.mCircleMatrix.preTranslate((-this.mBitmapWidth) / 2.0f, (-this.mBitmapHeight) / 2.0f);
                    if (bitmap != null) {
                        canvas.drawBitmap(bitmap, this.mCircleMatrix, ((LockPatternView) this).mPaint);
                    }
                    i6++;
                    i3 = i12;
                    i4 = i13;
                }
                i5++;
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateViewStyle(int r12, long r13) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.SecLockPatternView.updateViewStyle(int, long):void");
    }

    public SecLockPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDecoPatternEnabled = false;
        this.mCircleMatrix = new Matrix();
        this.mIsWhiteWp = false;
    }
}
