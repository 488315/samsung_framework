package com.android.systemui.mediaprojection.appselector.view;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.core.content.ContextCompat;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.recents.utilities.PreviewPositionHelper;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class MediaProjectionTaskView extends View {
    public final Paint backgroundPaint;
    public BitmapShader bitmapShader;
    public final int cornerRadius;
    public final Paint paint;
    public final PreviewPositionHelper previewPositionHelper;
    public final Rect previewRect;
    public RecentTask task;
    public ThumbnailData thumbnailData;
    public final WindowManager windowManager;

    public MediaProjectionTaskView(Context context) {
        this(context, null, 0, 6, null);
    }

    public final void bindTask(RecentTask recentTask, ThumbnailData thumbnailData) {
        int i;
        Bitmap bitmap;
        Integer num;
        this.task = recentTask;
        this.thumbnailData = thumbnailData;
        if (recentTask != null && (num = recentTask.colorBackground) != null) {
            i = num.intValue();
        } else {
            i = -16777216;
        }
        int i2 = i | EmergencyPhoneWidget.BG_COLOR;
        this.paint.setColor(i2);
        this.backgroundPaint.setColor(i2);
        ThumbnailData thumbnailData2 = this.thumbnailData;
        if (thumbnailData2 != null) {
            bitmap = thumbnailData2.thumbnail;
        } else {
            bitmap = null;
        }
        if (bitmap != null) {
            bitmap.prepareToDraw();
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            this.bitmapShader = bitmapShader;
            this.paint.setShader(bitmapShader);
            updateThumbnailMatrix();
        } else {
            this.bitmapShader = null;
            this.paint.setShader(null);
        }
        invalidate();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float width = getWidth();
        boolean z = true;
        float height = getHeight() - 1;
        int i = this.cornerRadius;
        canvas.drawRoundRect(0.0f, 1.0f, width, height, i, i, this.backgroundPaint);
        if (this.task != null && this.bitmapShader != null && this.thumbnailData != null) {
            z = false;
        }
        if (z) {
            return;
        }
        float width2 = getWidth();
        float height2 = getHeight();
        int i2 = this.cornerRadius;
        canvas.drawRoundRect(0.0f, 0.0f, width2, height2, i2, i2, this.paint);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        updateThumbnailMatrix();
        invalidate();
    }

    public final void updateThumbnailMatrix() {
        ThumbnailData thumbnailData;
        Display display;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        float f;
        float f2;
        boolean z5;
        float height;
        int height2;
        boolean z6;
        float f3;
        boolean z7;
        boolean z8;
        boolean z9;
        float f4;
        float f5;
        this.previewPositionHelper.getClass();
        BitmapShader bitmapShader = this.bitmapShader;
        if (bitmapShader == null || (thumbnailData = this.thumbnailData) == null || (display = getContext().getDisplay()) == null) {
            return;
        }
        WindowMetrics maximumWindowMetrics = this.windowManager.getMaximumWindowMetrics();
        Rect rect = this.previewRect;
        Bitmap bitmap = thumbnailData.thumbnail;
        rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        int rotation = display.getRotation();
        maximumWindowMetrics.getBounds().width();
        maximumWindowMetrics.getBounds().height();
        if (getLayoutDirection() == 1) {
            z = true;
        } else {
            z = false;
        }
        boolean isLargeScreen = Utilities.isLargeScreen(getContext());
        if (isLargeScreen) {
            getResources().getDimensionPixelSize(17106195);
        }
        PreviewPositionHelper previewPositionHelper = this.previewPositionHelper;
        Rect rect2 = this.previewRect;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        previewPositionHelper.getClass();
        int i = thumbnailData.rotation - rotation;
        if (i < 0) {
            i += 4;
        }
        RectF rectF = new RectF();
        if (thumbnailData.windowingMode == 1 && !isLargeScreen) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (i != 1 && i != 3) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (z3 && z2) {
            z4 = true;
        } else {
            z4 = false;
        }
        float f6 = thumbnailData.scale;
        if (measuredWidth != 0 && measuredHeight != 0 && f6 != 0.0f) {
            if (i > 0 && z2) {
                z6 = true;
            } else {
                z6 = false;
            }
            float width = rect2.width() / f6;
            float height3 = rect2.height() / f6;
            float f7 = measuredWidth;
            float f8 = measuredHeight;
            float f9 = f7 / f8;
            if (z6) {
                f3 = height3 / width;
            } else {
                f3 = width / height3;
            }
            if (Math.abs(f9 - f3) / Math.abs((f9 + f3) / 2.0f) > 0.1f) {
                z7 = true;
            } else {
                z7 = false;
            }
            if (z6 && z7) {
                z6 = false;
                z8 = false;
            } else {
                z8 = z4;
            }
            if (z7) {
                Rect rect3 = thumbnailData.letterboxInsets;
                float f10 = rect3.left;
                rectF.left = f10;
                float f11 = rect3.right;
                rectF.right = f11;
                z9 = z6;
                float f12 = rect3.top;
                rectF.top = f12;
                float f13 = rect3.bottom;
                rectF.bottom = f13;
                f5 = width - (f10 + f11);
                f4 = height3 - (f12 + f13);
            } else {
                z9 = z6;
                f4 = height3;
                f5 = width;
            }
            if (z8) {
                f8 = f7;
                f7 = f8;
            }
            float f14 = f7 / f8;
            float f15 = f5 / f14;
            if (f15 > f4) {
                if (f4 < f8) {
                    f15 = Math.min(f8, height3);
                } else {
                    f15 = f4;
                }
                float f16 = f15 * f14;
                if (f16 > width) {
                    f15 = width / f14;
                } else {
                    width = f16;
                }
            } else {
                width = f5;
            }
            if (z) {
                float f17 = (f5 - width) + rectF.left;
                rectF.left = f17;
                float f18 = rectF.right;
                f = 0.0f;
                if (f18 < 0.0f) {
                    rectF.left = f17 + f18;
                    rectF.right = 0.0f;
                }
            } else {
                f = 0.0f;
                float f19 = (f5 - width) + rectF.right;
                rectF.right = f19;
                float f20 = rectF.left;
                if (f20 < 0.0f) {
                    rectF.right = f19 + f20;
                    rectF.left = 0.0f;
                }
            }
            float f21 = (f4 - f15) + rectF.bottom;
            rectF.bottom = f21;
            float f22 = rectF.top;
            if (f22 < f) {
                rectF.bottom = f21 + f22;
                rectF.top = f;
            } else if (f21 < f) {
                rectF.top = f22 + f21;
                rectF.bottom = f;
            }
            f2 = f7 / (width * f6);
            z5 = z9;
        } else {
            f = 0.0f;
            f2 = 0.0f;
            z5 = false;
        }
        Matrix matrix = previewPositionHelper.mMatrix;
        if (!z5) {
            matrix.setTranslate((-rectF.left) * f6, (-rectF.top) * f6);
        } else {
            matrix.setRotate(i * 90);
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        height = f;
                    } else {
                        height2 = rect2.width();
                        height = f;
                    }
                } else {
                    height = rect2.width();
                    height2 = rect2.height();
                }
                f = height2;
            } else {
                height = rect2.height();
            }
            matrix.postTranslate(height, f);
        }
        matrix.postScale(f2, f2);
        bitmapShader.setLocalMatrix(this.previewPositionHelper.mMatrix);
        this.paint.setShader(bitmapShader);
    }

    public MediaProjectionTaskView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ MediaProjectionTaskView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public MediaProjectionTaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorBackgroundFloating});
        int color = obtainStyledAttributes.getColor(0, EmergencyPhoneWidget.BG_COLOR);
        obtainStyledAttributes.recycle();
        Object obj = ContextCompat.sLock;
        Object systemService = context.getSystemService((Class<Object>) WindowManager.class);
        Intrinsics.checkNotNull(systemService);
        this.windowManager = (WindowManager) systemService;
        this.paint = new Paint(1);
        Paint paint = new Paint(1);
        paint.setColor(color);
        this.backgroundPaint = paint;
        this.cornerRadius = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.media_projection_app_selector_task_rounded_corners);
        this.previewPositionHelper = new PreviewPositionHelper();
        this.previewRect = new Rect();
    }
}
