package android.app;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionSet;
import android.transition.Visibility;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes.dex */
abstract class ActivityTransitionCoordinator extends ResultReceiver {
    protected static final String KEY_ELEVATION = "shared_element:elevation";
    protected static final String KEY_IMAGE_MATRIX = "shared_element:imageMatrix";
    static final String KEY_REMOTE_RECEIVER = "android:remoteReceiver";
    protected static final String KEY_SCALE_TYPE = "shared_element:scaleType";
    protected static final String KEY_SCREEN_BOTTOM = "shared_element:screenBottom";
    protected static final String KEY_SCREEN_LEFT = "shared_element:screenLeft";
    protected static final String KEY_SCREEN_RIGHT = "shared_element:screenRight";
    protected static final String KEY_SCREEN_TOP = "shared_element:screenTop";
    protected static final String KEY_SNAPSHOT = "shared_element:bitmap";
    protected static final String KEY_TRANSLATION_Z = "shared_element:translationZ";
    public static final int MSG_ALLOW_RETURN_TRANSITION = 108;
    public static final int MSG_CANCEL = 106;
    public static final int MSG_EXIT_TRANSITION_COMPLETE = 104;
    public static final int MSG_HIDE_SHARED_ELEMENTS = 101;
    public static final int MSG_SET_REMOTE_RECEIVER = 100;
    public static final int MSG_SHARED_ELEMENT_DESTINATION = 107;
    public static final int MSG_START_EXIT_TRANSITION = 105;
    public static final int MSG_TAKE_SHARED_ELEMENTS = 103;
    protected static final ImageView.ScaleType[] SCALE_TYPE_VALUES = ImageView.ScaleType.values();
    private static final String TAG = "ActivityTransitionCoordinator";
    protected final ArrayList<String> mAllSharedElementNames;
    private boolean mBackgroundAnimatorComplete;
    private final FixedEpicenterCallback mEpicenterCallback;
    private ArrayList<GhostViewListeners> mGhostViewListeners;
    protected final boolean mIsReturning;
    private boolean mIsStartingTransition;
    protected SharedElementCallback mListener;
    private ArrayMap<View, Float> mOriginalAlphas;
    private Runnable mPendingTransition;
    protected ResultReceiver mResultReceiver;
    protected final ArrayList<String> mSharedElementNames;
    private ArrayList<Matrix> mSharedElementParentMatrices;
    private boolean mSharedElementTransitionComplete;
    protected final ArrayList<View> mSharedElements;
    private ArrayList<View> mStrippedTransitioningViews;
    protected ArrayList<View> mTransitioningViews;
    private boolean mViewsTransitionComplete;
    private Window mWindow;

    protected abstract Transition getViewsTransition();

    public ActivityTransitionCoordinator(Window window, ArrayList<String> allSharedElementNames, SharedElementCallback listener, boolean isReturning) {
        super(new Handler());
        this.mSharedElements = new ArrayList<>();
        this.mSharedElementNames = new ArrayList<>();
        this.mTransitioningViews = new ArrayList<>();
        this.mEpicenterCallback = new FixedEpicenterCallback();
        this.mGhostViewListeners = new ArrayList<>();
        this.mOriginalAlphas = new ArrayMap<>();
        this.mStrippedTransitioningViews = new ArrayList<>();
        this.mWindow = window;
        this.mListener = listener;
        this.mAllSharedElementNames = allSharedElementNames;
        this.mIsReturning = isReturning;
    }

    protected void viewsReady(ArrayMap<String, View> sharedElements) {
        sharedElements.retainAll(this.mAllSharedElementNames);
        if (this.mListener != null) {
            this.mListener.onMapSharedElements(this.mAllSharedElementNames, sharedElements);
        }
        setSharedElements(sharedElements);
        if (getViewsTransition() != null && this.mTransitioningViews != null) {
            ViewGroup decorView = getDecor();
            if (decorView != null) {
                decorView.captureTransitioningViews(this.mTransitioningViews);
            }
            this.mTransitioningViews.removeAll(this.mSharedElements);
        }
        setEpicenter();
    }

    private void setSharedElements(ArrayMap<String, View> sharedElements) {
        boolean isFirstRun = true;
        while (!sharedElements.isEmpty()) {
            int numSharedElements = sharedElements.size();
            for (int i = numSharedElements - 1; i >= 0; i--) {
                View view = sharedElements.valueAt(i);
                String name = sharedElements.keyAt(i);
                if (isFirstRun && (view == null || !view.isAttachedToWindow() || name == null)) {
                    sharedElements.removeAt(i);
                } else if (!isNested(view, sharedElements)) {
                    this.mSharedElementNames.add(name);
                    this.mSharedElements.add(view);
                    sharedElements.removeAt(i);
                }
            }
            isFirstRun = false;
        }
    }

    private static boolean isNested(View view, ArrayMap<String, View> sharedElements) {
        ViewParent parent = view.getParent();
        while (parent instanceof View) {
            View parentView = (View) parent;
            if (sharedElements.containsValue(parentView)) {
                return true;
            }
            parent = parentView.getParent();
        }
        return false;
    }

    protected void stripOffscreenViews() {
        if (this.mTransitioningViews == null) {
            return;
        }
        Rect r = new Rect();
        for (int i = this.mTransitioningViews.size() - 1; i >= 0; i--) {
            View view = this.mTransitioningViews.get(i);
            if (!view.getGlobalVisibleRect(r)) {
                this.mTransitioningViews.remove(i);
                this.mStrippedTransitioningViews.add(view);
            }
        }
    }

    protected Window getWindow() {
        return this.mWindow;
    }

    public ViewGroup getDecor() {
        if (this.mWindow == null) {
            return null;
        }
        return (ViewGroup) this.mWindow.getDecorView();
    }

    protected void setEpicenter() {
        int index;
        View epicenter = null;
        if (!this.mAllSharedElementNames.isEmpty() && !this.mSharedElementNames.isEmpty() && (index = this.mSharedElementNames.indexOf(this.mAllSharedElementNames.get(0))) >= 0) {
            epicenter = this.mSharedElements.get(index);
        }
        setEpicenter(epicenter);
    }

    private void setEpicenter(View view) {
        if (view == null) {
            this.mEpicenterCallback.setEpicenter(null);
            return;
        }
        Rect epicenter = new Rect();
        view.getBoundsOnScreen(epicenter);
        this.mEpicenterCallback.setEpicenter(epicenter);
    }

    public ArrayList<String> getAcceptedNames() {
        return this.mSharedElementNames;
    }

    public ArrayList<String> getMappedNames() {
        ArrayList<String> names = new ArrayList<>(this.mSharedElements.size());
        for (int i = 0; i < this.mSharedElements.size(); i++) {
            names.add(this.mSharedElements.get(i).getTransitionName());
        }
        return names;
    }

    public ArrayList<View> copyMappedViews() {
        return new ArrayList<>(this.mSharedElements);
    }

    protected Transition setTargets(Transition transition, boolean add) {
        if (transition != null) {
            if (add && (this.mTransitioningViews == null || this.mTransitioningViews.isEmpty())) {
                return null;
            }
            TransitionSet set = new TransitionSet();
            if (this.mTransitioningViews != null) {
                for (int i = this.mTransitioningViews.size() - 1; i >= 0; i--) {
                    View view = this.mTransitioningViews.get(i);
                    if (add) {
                        set.addTarget(view);
                    } else {
                        set.excludeTarget(view, true);
                    }
                }
            }
            if (this.mStrippedTransitioningViews != null) {
                for (int i2 = this.mStrippedTransitioningViews.size() - 1; i2 >= 0; i2--) {
                    set.excludeTarget(this.mStrippedTransitioningViews.get(i2), true);
                }
            }
            set.addTransition(transition);
            if (!add && this.mTransitioningViews != null && !this.mTransitioningViews.isEmpty()) {
                return new TransitionSet().addTransition(set);
            }
            return set;
        }
        return null;
    }

    protected Transition configureTransition(Transition transition, boolean includeTransitioningViews) {
        if (transition != null) {
            Transition transition2 = transition.mo5201clone();
            transition2.setEpicenterCallback(this.mEpicenterCallback);
            transition = setTargets(transition2, includeTransitioningViews);
        }
        noLayoutSuppressionForVisibilityTransitions(transition);
        return transition;
    }

    protected static void removeExcludedViews(Transition transition, ArrayList<View> views) {
        ArraySet<View> included = new ArraySet<>();
        findIncludedViews(transition, views, included);
        views.clear();
        views.addAll(included);
    }

    private static void findIncludedViews(Transition transition, ArrayList<View> views, ArraySet<View> included) {
        if (transition instanceof TransitionSet) {
            TransitionSet set = (TransitionSet) transition;
            ArrayList<View> includedViews = new ArrayList<>();
            int numViews = views.size();
            for (int i = 0; i < numViews; i++) {
                View view = views.get(i);
                if (transition.isValidTarget(view)) {
                    includedViews.add(view);
                }
            }
            int count = set.getTransitionCount();
            for (int i2 = 0; i2 < count; i2++) {
                findIncludedViews(set.getTransitionAt(i2), includedViews, included);
            }
            return;
        }
        int numViews2 = views.size();
        for (int i3 = 0; i3 < numViews2; i3++) {
            View view2 = views.get(i3);
            if (transition.isValidTarget(view2)) {
                included.add(view2);
            }
        }
    }

    protected static Transition mergeTransitions(Transition transition1, Transition transition2) {
        if (transition1 == null) {
            return transition2;
        }
        if (transition2 == null) {
            return transition1;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transition1);
        transitionSet.addTransition(transition2);
        return transitionSet;
    }

    protected ArrayMap<String, View> mapSharedElements(ArrayList<String> accepted, ArrayList<View> localViews) {
        ArrayMap<String, View> sharedElements = new ArrayMap<>();
        if (accepted != null) {
            for (int i = 0; i < accepted.size(); i++) {
                sharedElements.put(accepted.get(i), localViews.get(i));
            }
        } else {
            ViewGroup decorView = getDecor();
            if (decorView != null) {
                decorView.findNamedViews(sharedElements);
            }
        }
        return sharedElements;
    }

    protected void setResultReceiver(ResultReceiver resultReceiver) {
        this.mResultReceiver = resultReceiver;
    }

    private void setSharedElementState(View view, String name, Bundle transitionArgs, Matrix tempMatrix, RectF tempRect, int[] decorLoc) {
        float left;
        float top;
        float right;
        float bottom;
        int scaleTypeInt;
        Bundle sharedElementBundle = transitionArgs.getBundle(name);
        if (sharedElementBundle == null) {
            return;
        }
        if ((view instanceof ImageView) && (scaleTypeInt = sharedElementBundle.getInt(KEY_SCALE_TYPE, -1)) >= 0) {
            ImageView imageView = (ImageView) view;
            ImageView.ScaleType scaleType = SCALE_TYPE_VALUES[scaleTypeInt];
            imageView.setScaleType(scaleType);
            if (scaleType == ImageView.ScaleType.MATRIX) {
                float[] matrixValues = sharedElementBundle.getFloatArray(KEY_IMAGE_MATRIX);
                tempMatrix.setValues(matrixValues);
                imageView.setImageMatrix(tempMatrix);
            }
        }
        float z = sharedElementBundle.getFloat(KEY_TRANSLATION_Z);
        view.setTranslationZ(z);
        float elevation = sharedElementBundle.getFloat(KEY_ELEVATION);
        view.setElevation(elevation);
        float left2 = sharedElementBundle.getFloat(KEY_SCREEN_LEFT);
        float top2 = sharedElementBundle.getFloat(KEY_SCREEN_TOP);
        float right2 = sharedElementBundle.getFloat(KEY_SCREEN_RIGHT);
        float bottom2 = sharedElementBundle.getFloat(KEY_SCREEN_BOTTOM);
        if (decorLoc == null) {
            getSharedElementParentMatrix(view, tempMatrix);
            tempRect.set(left2, top2, right2, bottom2);
            tempMatrix.mapRect(tempRect);
            float leftInParent = tempRect.left;
            float topInParent = tempRect.top;
            view.getInverseMatrix().mapRect(tempRect);
            float width = tempRect.width();
            float height = tempRect.height();
            view.setLeft(0);
            view.setTop(0);
            view.setRight(Math.round(width));
            view.setBottom(Math.round(height));
            tempRect.set(0.0f, 0.0f, width, height);
            view.getMatrix().mapRect(tempRect);
            left = leftInParent - tempRect.left;
            top = topInParent - tempRect.top;
            right = left + width;
            bottom = top + height;
        } else {
            left = left2 - decorLoc[0];
            top = top2 - decorLoc[1];
            right = right2 - decorLoc[0];
            bottom = bottom2 - decorLoc[1];
        }
        int x = Math.round(left);
        int y = Math.round(top);
        int width2 = Math.round(right) - x;
        int height2 = Math.round(bottom) - y;
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width2, 1073741824);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height2, 1073741824);
        view.measure(widthSpec, heightSpec);
        int widthSpec2 = x + width2;
        view.layout(x, y, widthSpec2, y + height2);
    }

    private void setSharedElementMatrices() {
        int numSharedElements = this.mSharedElements.size();
        if (numSharedElements > 0) {
            this.mSharedElementParentMatrices = new ArrayList<>(numSharedElements);
        }
        for (int i = 0; i < numSharedElements; i++) {
            View view = this.mSharedElements.get(i);
            ViewGroup parent = (ViewGroup) view.getParent();
            Matrix matrix = new Matrix();
            if (parent != null) {
                parent.transformMatrixToLocal(matrix);
                matrix.postTranslate(parent.getScrollX(), parent.getScrollY());
            }
            this.mSharedElementParentMatrices.add(matrix);
        }
    }

    private void getSharedElementParentMatrix(View view, Matrix matrix) {
        int index = this.mSharedElementParentMatrices == null ? -1 : this.mSharedElements.indexOf(view);
        if (index < 0) {
            matrix.reset();
            ViewParent viewParent = view.getParent();
            if (viewParent instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) viewParent;
                parent.transformMatrixToLocal(matrix);
                matrix.postTranslate(parent.getScrollX(), parent.getScrollY());
                return;
            }
            return;
        }
        Matrix parentMatrix = this.mSharedElementParentMatrices.get(index);
        matrix.set(parentMatrix);
    }

    protected ArrayList<SharedElementOriginalState> setSharedElementState(Bundle sharedElementState, ArrayList<View> snapshots) {
        ArrayList<SharedElementOriginalState> originalImageState = new ArrayList<>();
        if (sharedElementState != null) {
            Matrix tempMatrix = new Matrix();
            RectF tempRect = new RectF();
            int numSharedElements = this.mSharedElements.size();
            for (int i = 0; i < numSharedElements; i++) {
                View sharedElement = this.mSharedElements.get(i);
                String name = this.mSharedElementNames.get(i);
                SharedElementOriginalState originalState = getOldSharedElementState(sharedElement, name, sharedElementState);
                originalImageState.add(originalState);
                setSharedElementState(sharedElement, name, sharedElementState, tempMatrix, tempRect, null);
            }
        }
        if (this.mListener != null) {
            this.mListener.onSharedElementStart(this.mSharedElementNames, this.mSharedElements, snapshots);
        }
        return originalImageState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: notifySharedElementEnd, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleSetSharedElementEnd$0(ArrayList<View> snapshots) {
        if (this.mListener != null) {
            this.mListener.onSharedElementEnd(this.mSharedElementNames, this.mSharedElements, snapshots);
        }
    }

    protected void scheduleSetSharedElementEnd(final ArrayList<View> snapshots) {
        View decorView = getDecor();
        if (decorView != null) {
            OneShotPreDrawListener.add(decorView, new Runnable() { // from class: android.app.ActivityTransitionCoordinator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityTransitionCoordinator.this.lambda$scheduleSetSharedElementEnd$0(snapshots);
                }
            });
        }
    }

    private static SharedElementOriginalState getOldSharedElementState(View view, String name, Bundle transitionArgs) {
        SharedElementOriginalState state = new SharedElementOriginalState();
        state.mLeft = view.getLeft();
        state.mTop = view.getTop();
        state.mRight = view.getRight();
        state.mBottom = view.getBottom();
        state.mMeasuredWidth = view.getMeasuredWidth();
        state.mMeasuredHeight = view.getMeasuredHeight();
        state.mTranslationZ = view.getTranslationZ();
        state.mElevation = view.getElevation();
        if (!(view instanceof ImageView)) {
            return state;
        }
        Bundle bundle = transitionArgs.getBundle(name);
        if (bundle == null) {
            return state;
        }
        int scaleTypeInt = bundle.getInt(KEY_SCALE_TYPE, -1);
        if (scaleTypeInt < 0) {
            return state;
        }
        ImageView imageView = (ImageView) view;
        state.mScaleType = imageView.getScaleType();
        if (state.mScaleType == ImageView.ScaleType.MATRIX) {
            state.mMatrix = new Matrix(imageView.getImageMatrix());
        }
        return state;
    }

    protected ArrayList<View> createSnapshots(Bundle state, Collection<String> names) {
        View snapshot;
        int numSharedElements = names.size();
        ArrayList<View> snapshots = new ArrayList<>(numSharedElements);
        if (numSharedElements == 0) {
            return snapshots;
        }
        Context context = getWindow().getContext();
        int[] decorLoc = new int[2];
        ViewGroup decorView = getDecor();
        if (decorView != null) {
            decorView.getLocationOnScreen(decorLoc);
        }
        Matrix tempMatrix = new Matrix();
        for (String name : names) {
            Bundle sharedElementBundle = state.getBundle(name);
            View snapshot2 = null;
            if (sharedElementBundle != null) {
                Parcelable parcelable = sharedElementBundle.getParcelable(KEY_SNAPSHOT);
                if (parcelable != null && this.mListener != null) {
                    View snapshot3 = this.mListener.onCreateSnapshotView(context, parcelable);
                    snapshot = snapshot3;
                } else {
                    snapshot = null;
                }
                if (snapshot != null) {
                    setSharedElementState(snapshot, name, state, tempMatrix, null, decorLoc);
                }
                snapshot2 = snapshot;
            }
            snapshots.add(snapshot2);
        }
        return snapshots;
    }

    protected static void setOriginalSharedElementState(ArrayList<View> sharedElements, ArrayList<SharedElementOriginalState> originalState) {
        for (int i = 0; i < originalState.size(); i++) {
            View view = sharedElements.get(i);
            SharedElementOriginalState state = originalState.get(i);
            if ((view instanceof ImageView) && state.mScaleType != null) {
                ImageView imageView = (ImageView) view;
                imageView.setScaleType(state.mScaleType);
                if (state.mScaleType == ImageView.ScaleType.MATRIX) {
                    imageView.setImageMatrix(state.mMatrix);
                }
            }
            view.setElevation(state.mElevation);
            view.setTranslationZ(state.mTranslationZ);
            int widthSpec = View.MeasureSpec.makeMeasureSpec(state.mMeasuredWidth, 1073741824);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(state.mMeasuredHeight, 1073741824);
            view.measure(widthSpec, heightSpec);
            view.layout(state.mLeft, state.mTop, state.mRight, state.mBottom);
        }
    }

    protected Bundle captureSharedElementState() {
        Bundle bundle = new Bundle();
        RectF tempBounds = new RectF();
        Matrix tempMatrix = new Matrix();
        for (int i = 0; i < this.mSharedElements.size(); i++) {
            View sharedElement = this.mSharedElements.get(i);
            String name = this.mSharedElementNames.get(i);
            captureSharedElementState(sharedElement, name, bundle, tempMatrix, tempBounds);
        }
        return bundle;
    }

    protected void clearState() {
        View decor = getDecor();
        ViewRootImpl viewRoot = decor != null ? decor.getViewRootImpl() : null;
        if (viewRoot != null) {
            viewRoot.setPausedForTransition(false);
        }
        this.mWindow = null;
        this.mSharedElements.clear();
        this.mTransitioningViews = null;
        this.mStrippedTransitioningViews = null;
        this.mOriginalAlphas.clear();
        this.mResultReceiver = null;
        this.mPendingTransition = null;
        this.mListener = null;
        this.mSharedElementParentMatrices = null;
    }

    protected long getFadeDuration() {
        return getWindow().getTransitionBackgroundFadeDuration();
    }

    protected void hideViews(ArrayList<View> views) {
        int count = views.size();
        for (int i = 0; i < count; i++) {
            View view = views.get(i);
            if (!this.mOriginalAlphas.containsKey(view)) {
                this.mOriginalAlphas.put(view, Float.valueOf(view.getAlpha()));
            }
            view.setAlpha(0.0f);
        }
    }

    protected void showViews(ArrayList<View> views, boolean setTransitionAlpha) {
        int count = views.size();
        for (int i = 0; i < count; i++) {
            showView(views.get(i), setTransitionAlpha);
        }
    }

    private void showView(View view, boolean setTransitionAlpha) {
        Float alpha = this.mOriginalAlphas.remove(view);
        if (alpha != null) {
            view.setAlpha(alpha.floatValue());
        }
        if (setTransitionAlpha) {
            view.setTransitionAlpha(1.0f);
        }
    }

    protected void captureSharedElementState(View view, String name, Bundle transitionArgs, Matrix tempMatrix, RectF tempBounds) {
        Bundle sharedElementBundle = new Bundle();
        tempMatrix.reset();
        view.transformMatrixToGlobal(tempMatrix);
        tempBounds.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        tempMatrix.mapRect(tempBounds);
        sharedElementBundle.putFloat(KEY_SCREEN_LEFT, tempBounds.left);
        sharedElementBundle.putFloat(KEY_SCREEN_RIGHT, tempBounds.right);
        sharedElementBundle.putFloat(KEY_SCREEN_TOP, tempBounds.top);
        sharedElementBundle.putFloat(KEY_SCREEN_BOTTOM, tempBounds.bottom);
        sharedElementBundle.putFloat(KEY_TRANSLATION_Z, view.getTranslationZ());
        sharedElementBundle.putFloat(KEY_ELEVATION, view.getElevation());
        Parcelable bitmap = null;
        if (this.mListener != null) {
            bitmap = this.mListener.onCaptureSharedElementSnapshot(view, tempMatrix, tempBounds);
        }
        if (bitmap != null) {
            sharedElementBundle.putParcelable(KEY_SNAPSHOT, bitmap);
        }
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            int scaleTypeInt = scaleTypeToInt(imageView.getScaleType());
            sharedElementBundle.putInt(KEY_SCALE_TYPE, scaleTypeInt);
            if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
                float[] matrix = new float[9];
                imageView.getImageMatrix().getValues(matrix);
                sharedElementBundle.putFloatArray(KEY_IMAGE_MATRIX, matrix);
            }
        }
        transitionArgs.putBundle(name, sharedElementBundle);
    }

    protected void startTransition(Runnable runnable) {
        if (this.mIsStartingTransition) {
            this.mPendingTransition = runnable;
        } else {
            this.mIsStartingTransition = true;
            runnable.run();
        }
    }

    protected void transitionStarted() {
        this.mIsStartingTransition = false;
    }

    protected boolean cancelPendingTransitions() {
        this.mPendingTransition = null;
        return this.mIsStartingTransition;
    }

    protected void moveSharedElementsToOverlay() {
        if (this.mWindow == null || !this.mWindow.getSharedElementsUseOverlay()) {
            return;
        }
        setSharedElementMatrices();
        int numSharedElements = this.mSharedElements.size();
        ViewGroup decor = getDecor();
        if (decor != null) {
            boolean moveWithParent = moveSharedElementWithParent();
            Matrix tempMatrix = new Matrix();
            for (int i = 0; i < numSharedElements; i++) {
                View view = this.mSharedElements.get(i);
                if (view.isAttachedToWindow()) {
                    tempMatrix.reset();
                    this.mSharedElementParentMatrices.get(i).invert(tempMatrix);
                    decor.transformMatrixToLocal(tempMatrix);
                    GhostView.addGhost(view, decor, tempMatrix);
                    ViewGroup parent = (ViewGroup) view.getParent();
                    if (moveWithParent && !isInTransitionGroup(parent, decor)) {
                        GhostViewListeners listener = new GhostViewListeners(view, parent, decor);
                        parent.getViewTreeObserver().addOnPreDrawListener(listener);
                        parent.addOnAttachStateChangeListener(listener);
                        this.mGhostViewListeners.add(listener);
                    }
                }
            }
        }
    }

    protected boolean moveSharedElementWithParent() {
        return true;
    }

    public static boolean isInTransitionGroup(ViewParent viewParent, ViewGroup decor) {
        if (viewParent == decor || !(viewParent instanceof ViewGroup)) {
            return false;
        }
        ViewGroup parent = (ViewGroup) viewParent;
        if (parent.isTransitionGroup()) {
            return true;
        }
        return isInTransitionGroup(parent.getParent(), decor);
    }

    protected void moveSharedElementsFromOverlay() {
        ViewGroup decor;
        int numListeners = this.mGhostViewListeners.size();
        for (int i = 0; i < numListeners; i++) {
            GhostViewListeners listener = this.mGhostViewListeners.get(i);
            listener.removeListener();
        }
        this.mGhostViewListeners.clear();
        if (this.mWindow != null && this.mWindow.getSharedElementsUseOverlay() && (decor = getDecor()) != null) {
            decor.getOverlay();
            int count = this.mSharedElements.size();
            for (int i2 = 0; i2 < count; i2++) {
                View sharedElement = this.mSharedElements.get(i2);
                GhostView.removeGhost(sharedElement);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: setGhostVisibility, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleGhostVisibilityChange$1(int visibility) {
        int numSharedElements = this.mSharedElements.size();
        for (int i = 0; i < numSharedElements; i++) {
            GhostView ghostView = GhostView.getGhost(this.mSharedElements.get(i));
            if (ghostView != null) {
                ghostView.setVisibility(visibility);
            }
        }
    }

    protected void scheduleGhostVisibilityChange(final int visibility) {
        View decorView = getDecor();
        if (decorView != null) {
            OneShotPreDrawListener.add(decorView, new Runnable() { // from class: android.app.ActivityTransitionCoordinator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityTransitionCoordinator.this.lambda$scheduleGhostVisibilityChange$1(visibility);
                }
            });
        }
    }

    protected boolean isViewsTransitionComplete() {
        return this.mViewsTransitionComplete;
    }

    protected void viewsTransitionComplete() {
        this.mViewsTransitionComplete = true;
        startInputWhenTransitionsComplete();
    }

    protected void backgroundAnimatorComplete() {
        this.mBackgroundAnimatorComplete = true;
    }

    protected void sharedElementTransitionComplete() {
        this.mSharedElementTransitionComplete = true;
        startInputWhenTransitionsComplete();
    }

    private void startInputWhenTransitionsComplete() {
        ViewRootImpl viewRoot;
        if (this.mViewsTransitionComplete && this.mSharedElementTransitionComplete) {
            View decor = getDecor();
            if (decor != null && (viewRoot = decor.getViewRootImpl()) != null) {
                viewRoot.setPausedForTransition(false);
            }
            onTransitionsComplete();
        }
    }

    protected void pauseInput() {
        View decor = getDecor();
        ViewRootImpl viewRoot = decor == null ? null : decor.getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.setPausedForTransition(true);
        }
    }

    protected void onTransitionsComplete() {
    }

    protected class ContinueTransitionListener extends TransitionListenerAdapter {
        protected ContinueTransitionListener() {
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionStart(Transition transition) {
            ActivityTransitionCoordinator.this.mIsStartingTransition = false;
            Runnable pending = ActivityTransitionCoordinator.this.mPendingTransition;
            ActivityTransitionCoordinator.this.mPendingTransition = null;
            if (pending != null) {
                ActivityTransitionCoordinator.this.startTransition(pending);
            }
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }
    }

    private static int scaleTypeToInt(ImageView.ScaleType scaleType) {
        for (int i = 0; i < SCALE_TYPE_VALUES.length; i++) {
            if (scaleType == SCALE_TYPE_VALUES[i]) {
                return i;
            }
        }
        return -1;
    }

    protected void setTransitioningViewsVisiblity(int visiblity, boolean invalidate) {
        int numElements = this.mTransitioningViews == null ? 0 : this.mTransitioningViews.size();
        for (int i = 0; i < numElements; i++) {
            View view = this.mTransitioningViews.get(i);
            if (invalidate) {
                view.setVisibility(visiblity);
            } else {
                view.setTransitionVisibility(visiblity);
            }
        }
    }

    private static void noLayoutSuppressionForVisibilityTransitions(Transition transition) {
        if (transition instanceof Visibility) {
            Visibility visibility = (Visibility) transition;
            visibility.setSuppressLayout(false);
        } else if (transition instanceof TransitionSet) {
            TransitionSet set = (TransitionSet) transition;
            int count = set.getTransitionCount();
            for (int i = 0; i < count; i++) {
                noLayoutSuppressionForVisibilityTransitions(set.getTransitionAt(i));
            }
        }
    }

    public boolean isTransitionRunning() {
        return (this.mViewsTransitionComplete && this.mSharedElementTransitionComplete && this.mBackgroundAnimatorComplete) ? false : true;
    }

    private static class FixedEpicenterCallback extends Transition.EpicenterCallback {
        private Rect mEpicenter;

        private FixedEpicenterCallback() {
        }

        public void setEpicenter(Rect epicenter) {
            this.mEpicenter = epicenter;
        }

        @Override // android.transition.Transition.EpicenterCallback
        public Rect onGetEpicenter(Transition transition) {
            return this.mEpicenter;
        }
    }

    private static class GhostViewListeners implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        private ViewGroup mDecor;
        private Matrix mMatrix = new Matrix();
        private View mParent;
        private View mView;
        private ViewTreeObserver mViewTreeObserver;

        public GhostViewListeners(View view, View parent, ViewGroup decor) {
            this.mView = view;
            this.mParent = parent;
            this.mDecor = decor;
            this.mViewTreeObserver = parent.getViewTreeObserver();
        }

        public View getView() {
            return this.mView;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            GhostView ghostView = GhostView.getGhost(this.mView);
            if (ghostView == null || !this.mView.isAttachedToWindow()) {
                removeListener();
                return true;
            }
            GhostView.calculateMatrix(this.mView, this.mDecor, this.mMatrix);
            ghostView.setMatrix(this.mMatrix);
            return true;
        }

        public void removeListener() {
            if (this.mViewTreeObserver.isAlive()) {
                this.mViewTreeObserver.removeOnPreDrawListener(this);
            } else {
                this.mParent.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            this.mParent.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View v) {
            this.mViewTreeObserver = v.getViewTreeObserver();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View v) {
            removeListener();
        }
    }

    static class SharedElementOriginalState {
        int mBottom;
        float mElevation;
        int mLeft;
        Matrix mMatrix;
        int mMeasuredHeight;
        int mMeasuredWidth;
        int mRight;
        ImageView.ScaleType mScaleType;
        int mTop;
        float mTranslationZ;

        SharedElementOriginalState() {
        }
    }
}
