package android.window;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AttachedSurfaceControl;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.window.SplashScreenView;
import com.android.internal.R;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.DecorView;
import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* loaded from: classes4.dex */
public final class SplashScreenView extends FrameLayout {
    private View mBrandingImageView;
    private RemoteCallback mClientCallback;
    private boolean mHasRemoved;
    private Duration mIconAnimationDuration;
    private Instant mIconAnimationStart;
    private View mIconView;
    private int mInitBackgroundColor;
    private boolean mIsCopied;
    private boolean mNotCopyable;
    private Bitmap mParceledBrandingBitmap;
    private Bitmap mParceledIconBackgroundBitmap;
    private Bitmap mParceledIconBitmap;
    private SurfaceControlViewHost mSurfaceHost;
    private SurfaceControlViewHost.SurfacePackage mSurfacePackage;
    private SurfaceControlViewHost.SurfacePackage mSurfacePackageCopy;
    private SurfaceView mSurfaceView;
    private final int[] mTmpPos;
    private final Rect mTmpRect;
    private Window mWindow;
    private static final String TAG = SplashScreenView.class.getSimpleName();
    private static final boolean DEBUG = Build.IS_DEBUGGABLE;

    public static class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean mAllowHandleSolidColor = true;
        private int mBackgroundColor;
        private Drawable mBrandingDrawable;
        private int mBrandingImageHeight;
        private int mBrandingImageWidth;
        private RemoteCallback mClientCallback;
        private final Context mContext;
        private Duration mIconAnimationDuration;
        private Instant mIconAnimationStart;
        private Drawable mIconBackground;
        private Drawable mIconDrawable;
        private int mIconSize;
        private Drawable mOverlayDrawable;
        private Bitmap mParceledBrandingBitmap;
        private Bitmap mParceledIconBackgroundBitmap;
        private Bitmap mParceledIconBitmap;
        private SurfaceControlViewHost.SurfacePackage mSurfacePackage;
        private Consumer<Runnable> mUiThreadInitTask;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder createFromParcel(SplashScreenViewParcelable parcelable) {
            this.mIconSize = parcelable.getIconSize();
            this.mBackgroundColor = parcelable.getBackgroundColor();
            this.mSurfacePackage = parcelable.mSurfacePackage;
            if (this.mSurfacePackage == null && parcelable.mIconBitmap != null) {
                this.mIconDrawable = new BitmapDrawable(this.mContext.getResources(), parcelable.mIconBitmap);
                this.mParceledIconBitmap = parcelable.mIconBitmap;
            }
            if (parcelable.mIconBackground != null) {
                this.mIconBackground = new BitmapDrawable(this.mContext.getResources(), parcelable.mIconBackground);
                this.mParceledIconBackgroundBitmap = parcelable.mIconBackground;
            }
            if (parcelable.mBrandingBitmap != null) {
                setBrandingDrawable(new BitmapDrawable(this.mContext.getResources(), parcelable.mBrandingBitmap), parcelable.mBrandingWidth, parcelable.mBrandingHeight);
                this.mParceledBrandingBitmap = parcelable.mBrandingBitmap;
            }
            this.mIconAnimationStart = Instant.ofEpochMilli(parcelable.mIconAnimationStartMillis);
            this.mIconAnimationDuration = Duration.ofMillis(parcelable.mIconAnimationDurationMillis);
            this.mClientCallback = parcelable.mClientCallback;
            if (SplashScreenView.DEBUG) {
                Log.d(SplashScreenView.TAG, String.format("Building from parcel drawable: %s", this.mIconDrawable));
            }
            return this;
        }

        public Builder setIconSize(int iconSize) {
            this.mIconSize = iconSize;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.mBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setOverlayDrawable(Drawable drawable) {
            this.mOverlayDrawable = drawable;
            return this;
        }

        public Builder setCenterViewDrawable(Drawable drawable) {
            this.mIconDrawable = drawable;
            return this;
        }

        public Builder setIconBackground(Drawable iconBackground) {
            this.mIconBackground = iconBackground;
            return this;
        }

        public Builder setUiThreadInitConsumer(Consumer<Runnable> uiThreadInitTask) {
            this.mUiThreadInitTask = uiThreadInitTask;
            return this;
        }

        public Builder setBrandingDrawable(Drawable branding, int width, int height) {
            this.mBrandingDrawable = branding;
            this.mBrandingImageWidth = width;
            this.mBrandingImageHeight = height;
            return this;
        }

        public Builder setAllowHandleSolidColor(boolean allowHandleSolidColor) {
            this.mAllowHandleSolidColor = allowHandleSolidColor;
            return this;
        }

        public SplashScreenView build() {
            Trace.traceBegin(32L, "SplashScreenView#build");
            LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
            final SplashScreenView view = (SplashScreenView) layoutInflater.inflate(R.layout.splash_screen_view, (ViewGroup) null, false);
            view.mInitBackgroundColor = this.mBackgroundColor;
            if (this.mOverlayDrawable != null) {
                view.setBackground(this.mOverlayDrawable);
            } else {
                view.setBackgroundColor(this.mBackgroundColor);
            }
            view.mClientCallback = this.mClientCallback;
            view.mBrandingImageView = view.findViewById(R.id.splashscreen_branding_view);
            boolean hasIcon = false;
            if ((this.mIconDrawable instanceof IconAnimateListener) || this.mSurfacePackage != null) {
                hasIcon = true;
                if (this.mUiThreadInitTask != null) {
                    this.mUiThreadInitTask.accept(new Runnable() { // from class: android.window.SplashScreenView$Builder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplashScreenView.Builder.this.lambda$build$0(view);
                        }
                    });
                } else {
                    view.mIconView = createSurfaceView(view);
                }
                view.initIconAnimation(this.mIconDrawable);
                view.mIconAnimationStart = this.mIconAnimationStart;
                view.mIconAnimationDuration = this.mIconAnimationDuration;
            } else if (this.mIconSize != 0) {
                ImageView imageView = (ImageView) view.findViewById(R.id.splashscreen_icon_view);
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.width = this.mIconSize;
                params.height = this.mIconSize;
                imageView.setLayoutParams(params);
                if (this.mIconDrawable != null) {
                    imageView.setImageDrawable(this.mIconDrawable);
                }
                if (this.mIconBackground != null) {
                    imageView.setBackground(this.mIconBackground);
                }
                hasIcon = true;
                view.mIconView = imageView;
            }
            if (this.mOverlayDrawable != null || (!hasIcon && !this.mAllowHandleSolidColor)) {
                view.setNotCopyable();
            }
            view.mParceledIconBackgroundBitmap = this.mParceledIconBackgroundBitmap;
            view.mParceledIconBitmap = this.mParceledIconBitmap;
            if (this.mBrandingImageHeight > 0 && this.mBrandingImageWidth > 0 && this.mBrandingDrawable != null) {
                ViewGroup.LayoutParams params2 = view.mBrandingImageView.getLayoutParams();
                params2.width = this.mBrandingImageWidth;
                params2.height = this.mBrandingImageHeight;
                view.mBrandingImageView.setLayoutParams(params2);
                view.mBrandingImageView.setBackground(this.mBrandingDrawable);
            } else {
                view.mBrandingImageView.setVisibility(8);
            }
            if (this.mParceledBrandingBitmap != null) {
                view.mParceledBrandingBitmap = this.mParceledBrandingBitmap;
            }
            if (SplashScreenView.DEBUG) {
                Log.d(SplashScreenView.TAG, "Build " + view + "\nIcon: view: " + view.mIconView + " drawable: " + this.mIconDrawable + " size: " + this.mIconSize + "\nBranding: view: " + view.mBrandingImageView + " drawable: " + this.mBrandingDrawable + " size w: " + this.mBrandingImageWidth + " h: " + this.mBrandingImageHeight);
            }
            Trace.traceEnd(32L);
            return view;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$0(SplashScreenView view) {
            view.mIconView = createSurfaceView(view);
        }

        private SurfaceView createSurfaceView(SplashScreenView splashScreenView) {
            Trace.traceBegin(32L, "SplashScreenView#createSurfaceView");
            Context viewContext = splashScreenView.getContext();
            SurfaceView surfaceView = new SurfaceView(viewContext);
            surfaceView.setPadding(0, 0, 0, 0);
            surfaceView.setBackground(this.mIconBackground);
            if (this.mSurfacePackage == null) {
                if (SplashScreenView.DEBUG) {
                    Log.d(SplashScreenView.TAG, "SurfaceControlViewHost created on thread " + Thread.currentThread().getId());
                }
                AttachedSurfaceControl attachedSurfaceControl = surfaceView.getRootSurfaceControl();
                SurfaceControlViewHost viewHost = new SurfaceControlViewHost(viewContext, viewContext.getDisplay(), attachedSurfaceControl == null ? null : attachedSurfaceControl.getInputTransferToken(), "SplashScreenView");
                ImageView imageView = new ImageView(viewContext);
                imageView.setBackground(this.mIconDrawable);
                viewHost.setView(imageView, new WindowManager.LayoutParams(this.mIconSize, this.mIconSize, 2, 131096, -2));
                SurfaceControlViewHost.SurfacePackage surfacePackage = viewHost.getSurfacePackage();
                surfaceView.setChildSurfacePackage(surfacePackage);
                splashScreenView.mSurfacePackage = surfacePackage;
                splashScreenView.mSurfaceHost = viewHost;
                splashScreenView.mSurfacePackageCopy = new SurfaceControlViewHost.SurfacePackage(surfacePackage);
            } else {
                if (SplashScreenView.DEBUG) {
                    Log.d(SplashScreenView.TAG, "Using copy of SurfacePackage in the client");
                }
                splashScreenView.mSurfacePackage = this.mSurfacePackage;
            }
            if (this.mIconSize != 0) {
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(this.mIconSize, this.mIconSize);
                lp.gravity = 17;
                surfaceView.setLayoutParams(lp);
                if (SplashScreenView.DEBUG) {
                    Log.d(SplashScreenView.TAG, "Icon size " + this.mIconSize);
                }
            }
            surfaceView.setUseAlpha();
            surfaceView.setZOrderOnTop(true);
            surfaceView.getHolder().setFormat(-3);
            splashScreenView.addView(surfaceView);
            splashScreenView.mSurfaceView = surfaceView;
            Trace.traceEnd(32L);
            return surfaceView;
        }
    }

    public SplashScreenView(Context context) {
        super(context);
        this.mTmpRect = new Rect();
        this.mTmpPos = new int[2];
    }

    public SplashScreenView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpRect = new Rect();
        this.mTmpPos = new int[2];
    }

    public void setNotCopyable() {
        this.mNotCopyable = true;
    }

    public boolean isCopyable() {
        return !this.mNotCopyable;
    }

    public void onCopied() {
        this.mIsCopied = true;
        if (this.mSurfaceView == null || this.mSurfacePackage == null) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "Setting SurfaceView's SurfacePackage to null.");
        }
        this.mSurfacePackage.release();
        this.mSurfacePackage = null;
    }

    public SurfaceControlViewHost getSurfaceHost() {
        return this.mSurfaceHost;
    }

    @Override // android.view.View
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        if (this.mSurfaceView != null) {
            this.mSurfaceView.setAlpha(this.mSurfaceView.getAlpha() * alpha);
        }
    }

    public Duration getIconAnimationDuration() {
        return this.mIconAnimationDuration;
    }

    public Instant getIconAnimationStart() {
        return this.mIconAnimationStart;
    }

    public void syncTransferSurfaceOnDraw() {
        if (this.mSurfacePackage == null) {
            return;
        }
        if (DEBUG) {
            this.mSurfacePackage.getSurfaceControl().addOnReparentListener(new SurfaceControl.OnReparentListener() { // from class: android.window.SplashScreenView$$ExternalSyntheticLambda0
                @Override // android.view.SurfaceControl.OnReparentListener
                public final void onReparent(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
                    Log.e(SplashScreenView.TAG, String.format("SurfacePackage'surface reparented to %s", surfaceControl));
                }
            });
            Log.d(TAG, "Transferring surface " + this.mSurfaceView.toString());
        }
        this.mSurfaceView.setChildSurfacePackage(this.mSurfacePackage);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void initIconAnimation(Drawable drawable) {
        if (!(drawable instanceof IconAnimateListener)) {
            return;
        }
        IconAnimateListener aniDrawable = (IconAnimateListener) drawable;
        aniDrawable.prepareAnimate(new LongConsumer() { // from class: android.window.SplashScreenView$$ExternalSyntheticLambda1
            @Override // java.util.function.LongConsumer
            public final void accept(long j) {
                SplashScreenView.this.animationStartCallback(j);
            }
        });
        aniDrawable.setAnimationJankMonitoring(new AnimatorListenerAdapter() { // from class: android.window.SplashScreenView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                InteractionJankMonitor.getInstance().cancel(38);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                InteractionJankMonitor.getInstance().end(38);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                InteractionJankMonitor.getInstance().begin(SplashScreenView.this, 38);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animationStartCallback(long animDuration) {
        this.mIconAnimationStart = Instant.now();
        if (animDuration >= 0) {
            this.mIconAnimationDuration = Duration.ofMillis(animDuration);
        }
    }

    public void remove() {
        if (this.mHasRemoved) {
            return;
        }
        setVisibility(8);
        if (this.mParceledIconBitmap != null) {
            if (this.mIconView instanceof ImageView) {
                ((ImageView) this.mIconView).setImageDrawable(null);
            } else if (this.mIconView != null) {
                this.mIconView.setBackground(null);
            }
            this.mParceledIconBitmap.recycle();
            this.mParceledIconBitmap = null;
        }
        if (this.mParceledBrandingBitmap != null) {
            this.mBrandingImageView.setBackground(null);
            this.mParceledBrandingBitmap.recycle();
            this.mParceledBrandingBitmap = null;
        }
        if (this.mParceledIconBackgroundBitmap != null) {
            if (this.mIconView != null) {
                this.mIconView.setBackground(null);
            }
            this.mParceledIconBackgroundBitmap.recycle();
            this.mParceledIconBackgroundBitmap = null;
        }
        if (this.mWindow != null) {
            DecorView decorView = (DecorView) this.mWindow.peekDecorView();
            if (DEBUG) {
                Log.d(TAG, "remove starting view");
            }
            if (decorView != null) {
                decorView.removeView(this);
            }
            this.mWindow = null;
        }
        this.mHasRemoved = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseAnimationSurfaceHost();
        View view = this.mIconView;
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            Object drawable = imageView.getDrawable();
            if (drawable instanceof Closeable) {
                Closeable closeableDrawable = (Closeable) drawable;
                try {
                    closeableDrawable.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mBrandingImageView.getDrawingRect(this.mTmpRect);
        int brandingHeight = this.mTmpRect.height();
        if (brandingHeight == 0 || this.mIconView == null) {
            return;
        }
        int visibility = this.mBrandingImageView.getVisibility();
        if (visibility != 0) {
            return;
        }
        int currentHeight = b - t;
        this.mIconView.getLocationInWindow(this.mTmpPos);
        this.mIconView.getDrawingRect(this.mTmpRect);
        int iconHeight = this.mTmpRect.height();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mBrandingImageView.getLayoutParams();
        if (params == null) {
            Log.e(TAG, "Unable to adjust branding image layout, layout changed?");
            return;
        }
        int marginBottom = params.bottomMargin;
        int remainingHeight = (currentHeight - this.mTmpPos[1]) - iconHeight;
        int remainingMaxMargin = remainingHeight - brandingHeight;
        if (remainingHeight < brandingHeight) {
            this.mBrandingImageView.setVisibility(8);
        } else if (remainingMaxMargin < marginBottom) {
            params.bottomMargin = (int) Math.round(remainingMaxMargin / 2.0d);
            this.mBrandingImageView.setLayoutParams(params);
        }
    }

    private void releaseAnimationSurfaceHost() {
        if (this.mSurfaceHost != null && !this.mIsCopied) {
            if (DEBUG) {
                Log.d(TAG, "Shell removed splash screen. Releasing SurfaceControlViewHost on thread #" + Thread.currentThread().getId());
            }
            releaseIconHost(this.mSurfaceHost);
            this.mSurfaceHost = null;
            return;
        }
        if (this.mSurfacePackage != null && this.mSurfaceHost == null) {
            this.mSurfacePackage = null;
            this.mClientCallback.sendResult(null);
        }
    }

    public static void releaseIconHost(SurfaceControlViewHost host) {
        Object background = host.getView().getBackground();
        if (background instanceof IconAnimateListener) {
            ((IconAnimateListener) background).stopAnimation();
        }
        host.release();
    }

    public void attachHostWindow(Window window) {
        this.mWindow = window;
    }

    public View getIconView() {
        return this.mIconView;
    }

    public View getBrandingView() {
        return this.mBrandingImageView;
    }

    public int getInitBackgroundColor() {
        return this.mInitBackgroundColor;
    }

    public interface IconAnimateListener {
        void prepareAnimate(LongConsumer longConsumer);

        void stopAnimation();

        default void setAnimationJankMonitoring(AnimatorListenerAdapter listener) {
        }
    }

    public static class SplashScreenViewParcelable implements Parcelable {
        public static final Parcelable.Creator<SplashScreenViewParcelable> CREATOR = new Parcelable.Creator<SplashScreenViewParcelable>() { // from class: android.window.SplashScreenView.SplashScreenViewParcelable.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SplashScreenViewParcelable createFromParcel(Parcel source) {
                return new SplashScreenViewParcelable(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SplashScreenViewParcelable[] newArray(int size) {
                return new SplashScreenViewParcelable[size];
            }
        };
        private int mBackgroundColor;
        private Bitmap mBrandingBitmap;
        private int mBrandingHeight;
        private int mBrandingWidth;
        private RemoteCallback mClientCallback;
        private long mIconAnimationDurationMillis;
        private long mIconAnimationStartMillis;
        private Bitmap mIconBackground;
        private Bitmap mIconBitmap;
        private int mIconSize;
        private SurfaceControlViewHost.SurfacePackage mSurfacePackage;

        public SplashScreenViewParcelable(SplashScreenView view) {
            this.mIconBitmap = null;
            View iconView = view.getIconView();
            this.mIconSize = iconView != null ? iconView.getWidth() : 0;
            this.mBackgroundColor = view.getInitBackgroundColor();
            this.mIconBackground = iconView != null ? copyDrawable(iconView.getBackground()) : null;
            this.mSurfacePackage = view.mSurfacePackageCopy;
            if (this.mSurfacePackage == null) {
                this.mIconBitmap = iconView != null ? copyDrawable(((ImageView) view.getIconView()).getDrawable()) : null;
            }
            this.mBrandingBitmap = copyDrawable(view.getBrandingView().getBackground());
            ViewGroup.LayoutParams params = view.getBrandingView().getLayoutParams();
            this.mBrandingWidth = params.width;
            this.mBrandingHeight = params.height;
            if (view.getIconAnimationStart() != null) {
                this.mIconAnimationStartMillis = view.getIconAnimationStart().toEpochMilli();
            }
            if (view.getIconAnimationDuration() != null) {
                this.mIconAnimationDurationMillis = view.getIconAnimationDuration().toMillis();
            }
        }

        private Bitmap copyDrawable(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            Rect initialBounds = drawable.copyBounds();
            int width = initialBounds.width();
            int height = initialBounds.height();
            if (width <= 0 || height <= 0) {
                return null;
            }
            Bitmap snapshot = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas bmpCanvas = new Canvas(snapshot);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(bmpCanvas);
            Bitmap copyBitmap = snapshot.createAshmemBitmap();
            snapshot.recycle();
            return copyBitmap;
        }

        private SplashScreenViewParcelable(Parcel source) {
            this.mIconBitmap = null;
            readParcel(source);
        }

        private void readParcel(Parcel source) {
            this.mIconSize = source.readInt();
            this.mBackgroundColor = source.readInt();
            this.mIconBitmap = (Bitmap) source.readTypedObject(Bitmap.CREATOR);
            this.mBrandingWidth = source.readInt();
            this.mBrandingHeight = source.readInt();
            this.mBrandingBitmap = (Bitmap) source.readTypedObject(Bitmap.CREATOR);
            this.mIconAnimationStartMillis = source.readLong();
            this.mIconAnimationDurationMillis = source.readLong();
            this.mIconBackground = (Bitmap) source.readTypedObject(Bitmap.CREATOR);
            this.mSurfacePackage = (SurfaceControlViewHost.SurfacePackage) source.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
            this.mClientCallback = (RemoteCallback) source.readTypedObject(RemoteCallback.CREATOR);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mIconSize);
            dest.writeInt(this.mBackgroundColor);
            dest.writeTypedObject(this.mIconBitmap, flags);
            dest.writeInt(this.mBrandingWidth);
            dest.writeInt(this.mBrandingHeight);
            dest.writeTypedObject(this.mBrandingBitmap, flags);
            dest.writeLong(this.mIconAnimationStartMillis);
            dest.writeLong(this.mIconAnimationDurationMillis);
            dest.writeTypedObject(this.mIconBackground, flags);
            dest.writeTypedObject(this.mSurfacePackage, flags);
            dest.writeTypedObject(this.mClientCallback, flags);
        }

        public void clearIfNeeded() {
            if (this.mIconBitmap != null) {
                this.mIconBitmap.recycle();
                this.mIconBitmap = null;
            }
            if (this.mBrandingBitmap != null) {
                this.mBrandingBitmap.recycle();
                this.mBrandingBitmap = null;
            }
        }

        int getIconSize() {
            return this.mIconSize;
        }

        int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public void setClientCallback(RemoteCallback clientCallback) {
            this.mClientCallback = clientCallback;
        }
    }
}
