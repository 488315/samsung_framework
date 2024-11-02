package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.SharedValues;
import androidx.constraintlayout.widget.StateSet;
import com.android.systemui.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class MotionScene {
    public final ArrayList mAbstractTransitionList;
    public final HashMap mConstraintSetIdMap;
    public final SparseArray mConstraintSetMap;
    public Transition mCurrentTransition;
    public int mDefaultDuration;
    public Transition mDefaultTransition;
    public final SparseIntArray mDeriveMap;
    public boolean mIgnoreTouch;
    public MotionEvent mLastTouchDown;
    public float mLastTouchX;
    public float mLastTouchY;
    public int mLayoutDuringTransition;
    public final MotionLayout mMotionLayout;
    public boolean mMotionOutsideRegion;
    public boolean mRtl;
    public StateSet mStateSet;
    public final ArrayList mTransitionList;
    public MotionLayout.MyTracker mVelocityTracker;
    public final ViewTransitionController mViewTransitionController;

    public MotionScene(MotionLayout motionLayout) {
        this.mStateSet = null;
        this.mCurrentTransition = null;
        this.mTransitionList = new ArrayList();
        this.mDefaultTransition = null;
        this.mAbstractTransitionList = new ArrayList();
        this.mConstraintSetMap = new SparseArray();
        this.mConstraintSetIdMap = new HashMap();
        this.mDeriveMap = new SparseIntArray();
        this.mDefaultDuration = 400;
        this.mLayoutDuringTransition = 0;
        this.mIgnoreTouch = false;
        this.mMotionOutsideRegion = false;
        this.mMotionLayout = motionLayout;
        this.mViewTransitionController = new ViewTransitionController(motionLayout);
    }

    public final boolean autoTransition(int i, MotionLayout motionLayout) {
        boolean z;
        boolean z2;
        if (this.mVelocityTracker != null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = (Transition) it.next();
            int i2 = transition.mAutoTransition;
            if (i2 != 0) {
                Transition transition2 = this.mCurrentTransition;
                if (transition2 == transition) {
                    if ((transition2.mTransitionFlags & 2) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        continue;
                    }
                }
                if (i == transition.mConstraintSetStart && (i2 == 4 || i2 == 2)) {
                    MotionLayout.TransitionState transitionState = MotionLayout.TransitionState.FINISHED;
                    motionLayout.setState(transitionState);
                    motionLayout.setTransition(transition);
                    if (transition.mAutoTransition == 4) {
                        motionLayout.animateTo(1.0f);
                        motionLayout.mOnComplete = null;
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    } else {
                        motionLayout.setProgress(1.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(transitionState);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                }
                if (i == transition.mConstraintSetEnd && (i2 == 3 || i2 == 1)) {
                    MotionLayout.TransitionState transitionState2 = MotionLayout.TransitionState.FINISHED;
                    motionLayout.setState(transitionState2);
                    motionLayout.setTransition(transition);
                    if (transition.mAutoTransition == 3) {
                        motionLayout.animateTo(0.0f);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    } else {
                        motionLayout.setProgress(0.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(transitionState2);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public final ConstraintSet getConstraintSet(int i) {
        int stateGetConstraintID;
        SparseArray sparseArray = this.mConstraintSetMap;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null && (stateGetConstraintID = stateSet.stateGetConstraintID(i)) != -1) {
            i = stateGetConstraintID;
        }
        if (sparseArray.get(i) == null) {
            Log.e("MotionScene", "Warning could not find ConstraintSet id/" + Debug.getName(i, this.mMotionLayout.getContext()) + " In MotionScene");
            return (ConstraintSet) sparseArray.get(sparseArray.keyAt(0));
        }
        return (ConstraintSet) sparseArray.get(i);
    }

    public final int getId(Context context, String str) {
        int i;
        if (str.contains("/")) {
            i = context.getResources().getIdentifier(str.substring(str.indexOf(47) + 1), "id", context.getPackageName());
        } else {
            i = -1;
        }
        if (i == -1) {
            if (str.length() > 1) {
                return Integer.parseInt(str.substring(1));
            }
            Log.e("MotionScene", "error in parsing id");
            return i;
        }
        return i;
    }

    public final void getKeyFrames(MotionController motionController) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            Transition transition2 = this.mDefaultTransition;
            if (transition2 != null) {
                Iterator it = transition2.mKeyFramesList.iterator();
                while (it.hasNext()) {
                    ((KeyFrames) it.next()).addFrames(motionController);
                }
                return;
            }
            return;
        }
        Iterator it2 = transition.mKeyFramesList.iterator();
        while (it2.hasNext()) {
            ((KeyFrames) it2.next()).addFrames(motionController);
        }
    }

    public final float getMaxAcceleration() {
        TouchResponse touchResponse;
        Transition transition = this.mCurrentTransition;
        if (transition != null && (touchResponse = transition.mTouchResponse) != null) {
            return touchResponse.mMaxAcceleration;
        }
        return 0.0f;
    }

    public final int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetStart;
    }

    public final int parseConstraintSet(Context context, XmlPullParser xmlPullParser) {
        boolean z;
        boolean z2;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.mForceId = false;
        int attributeCount = xmlPullParser.getAttributeCount();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < attributeCount; i3++) {
            String attributeName = xmlPullParser.getAttributeName(i3);
            String attributeValue = xmlPullParser.getAttributeValue(i3);
            attributeName.getClass();
            switch (attributeName.hashCode()) {
                case -1496482599:
                    if (attributeName.equals("deriveConstraintsFrom")) {
                        z = false;
                        break;
                    }
                    break;
                case -1153153640:
                    if (attributeName.equals("constraintRotate")) {
                        z = true;
                        break;
                    }
                    break;
                case 3355:
                    if (attributeName.equals("id")) {
                        z = 2;
                        break;
                    }
                    break;
            }
            z = -1;
            switch (z) {
                case false:
                    i2 = getId(context, attributeValue);
                    break;
                case true:
                    try {
                        constraintSet.mRotate = Integer.parseInt(attributeValue);
                        break;
                    } catch (NumberFormatException unused) {
                        attributeValue.getClass();
                        switch (attributeValue.hashCode()) {
                            case -768416914:
                                if (attributeValue.equals("x_left")) {
                                    z2 = false;
                                    break;
                                }
                                break;
                            case 3317767:
                                if (attributeValue.equals("left")) {
                                    z2 = true;
                                    break;
                                }
                                break;
                            case 3387192:
                                if (attributeValue.equals("none")) {
                                    z2 = 2;
                                    break;
                                }
                                break;
                            case 108511772:
                                if (attributeValue.equals("right")) {
                                    z2 = 3;
                                    break;
                                }
                                break;
                            case 1954540437:
                                if (attributeValue.equals("x_right")) {
                                    z2 = 4;
                                    break;
                                }
                                break;
                        }
                        z2 = -1;
                        switch (z2) {
                            case false:
                                constraintSet.mRotate = 4;
                                break;
                            case true:
                                constraintSet.mRotate = 2;
                                break;
                            case true:
                                constraintSet.mRotate = 0;
                                break;
                            case true:
                                constraintSet.mRotate = 1;
                                break;
                            case true:
                                constraintSet.mRotate = 3;
                                break;
                        }
                    }
                    break;
                case true:
                    i = getId(context, attributeValue);
                    int indexOf = attributeValue.indexOf(47);
                    if (indexOf >= 0) {
                        attributeValue = attributeValue.substring(indexOf + 1);
                    }
                    this.mConstraintSetIdMap.put(attributeValue, Integer.valueOf(i));
                    constraintSet.mIdString = Debug.getName(i, context);
                    break;
            }
        }
        if (i != -1) {
            int i4 = this.mMotionLayout.mDebugPath;
            constraintSet.load(context, xmlPullParser);
            if (i2 != -1) {
                this.mDeriveMap.put(i, i2);
            }
            this.mConstraintSetMap.put(i, constraintSet);
        }
        return i;
    }

    public final void parseInclude(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.include);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                parseInclude(obtainStyledAttributes.getResourceId(index, -1), context);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public final void parseMotionSceneTags(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.MotionScene);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                int i2 = obtainStyledAttributes.getInt(index, this.mDefaultDuration);
                this.mDefaultDuration = i2;
                if (i2 < 8) {
                    this.mDefaultDuration = 8;
                }
            } else if (index == 1) {
                this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public final void readConstraintChain(int i, MotionLayout motionLayout) {
        SparseArray sparseArray = this.mConstraintSetMap;
        ConstraintSet constraintSet = (ConstraintSet) sparseArray.get(i);
        constraintSet.derivedState = constraintSet.mIdString;
        int i2 = this.mDeriveMap.get(i);
        HashMap hashMap = constraintSet.mConstraints;
        if (i2 > 0) {
            readConstraintChain(i2, motionLayout);
            ConstraintSet constraintSet2 = (ConstraintSet) sparseArray.get(i2);
            if (constraintSet2 == null) {
                Log.e("MotionScene", "ERROR! invalid deriveConstraintsFrom: @id/" + Debug.getName(i2, this.mMotionLayout.getContext()));
                return;
            }
            constraintSet.derivedState += "/" + constraintSet2.derivedState;
            HashMap hashMap2 = constraintSet2.mConstraints;
            for (Integer num : hashMap2.keySet()) {
                int intValue = num.intValue();
                ConstraintSet.Constraint constraint = (ConstraintSet.Constraint) hashMap2.get(num);
                if (!hashMap.containsKey(Integer.valueOf(intValue))) {
                    hashMap.put(Integer.valueOf(intValue), new ConstraintSet.Constraint());
                }
                ConstraintSet.Constraint constraint2 = (ConstraintSet.Constraint) hashMap.get(Integer.valueOf(intValue));
                if (constraint2 != null) {
                    ConstraintSet.Layout layout = constraint2.layout;
                    if (!layout.mApply) {
                        layout.copyFrom(constraint.layout);
                    }
                    ConstraintSet.PropertySet propertySet = constraint2.propertySet;
                    if (!propertySet.mApply) {
                        ConstraintSet.PropertySet propertySet2 = constraint.propertySet;
                        propertySet.mApply = propertySet2.mApply;
                        propertySet.visibility = propertySet2.visibility;
                        propertySet.alpha = propertySet2.alpha;
                        propertySet.mProgress = propertySet2.mProgress;
                        propertySet.mVisibilityMode = propertySet2.mVisibilityMode;
                    }
                    ConstraintSet.Transform transform = constraint2.transform;
                    if (!transform.mApply) {
                        transform.copyFrom(constraint.transform);
                    }
                    ConstraintSet.Motion motion = constraint2.motion;
                    if (!motion.mApply) {
                        motion.copyFrom(constraint.motion);
                    }
                    for (String str : constraint.mCustomConstraints.keySet()) {
                        if (!constraint2.mCustomConstraints.containsKey(str)) {
                            constraint2.mCustomConstraints.put(str, (ConstraintAttribute) constraint.mCustomConstraints.get(str));
                        }
                    }
                }
            }
        } else {
            constraintSet.derivedState = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(new StringBuilder(), constraintSet.derivedState, "  layout");
            int childCount = motionLayout.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = motionLayout.getChildAt(i3);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                int id = childAt.getId();
                if (constraintSet.mForceId && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (!hashMap.containsKey(Integer.valueOf(id))) {
                    hashMap.put(Integer.valueOf(id), new ConstraintSet.Constraint());
                }
                ConstraintSet.Constraint constraint3 = (ConstraintSet.Constraint) hashMap.get(Integer.valueOf(id));
                if (constraint3 != null) {
                    ConstraintSet.Layout layout2 = constraint3.layout;
                    if (!layout2.mApply) {
                        constraint3.fillFrom(id, layoutParams);
                        if (childAt instanceof ConstraintHelper) {
                            ConstraintHelper constraintHelper = (ConstraintHelper) childAt;
                            layout2.mReferenceIds = Arrays.copyOf(constraintHelper.mIds, constraintHelper.mCount);
                            if (childAt instanceof Barrier) {
                                Barrier barrier = (Barrier) childAt;
                                androidx.constraintlayout.core.widgets.Barrier barrier2 = barrier.mBarrier;
                                layout2.mBarrierAllowsGoneWidgets = barrier2.mAllowsGoneWidget;
                                layout2.mBarrierDirection = barrier.mIndicatedType;
                                layout2.mBarrierMargin = barrier2.mMargin;
                            }
                        }
                        layout2.mApply = true;
                    }
                    ConstraintSet.PropertySet propertySet3 = constraint3.propertySet;
                    if (!propertySet3.mApply) {
                        propertySet3.visibility = childAt.getVisibility();
                        propertySet3.alpha = childAt.getAlpha();
                        propertySet3.mApply = true;
                    }
                    ConstraintSet.Transform transform2 = constraint3.transform;
                    if (!transform2.mApply) {
                        transform2.mApply = true;
                        transform2.rotation = childAt.getRotation();
                        transform2.rotationX = childAt.getRotationX();
                        transform2.rotationY = childAt.getRotationY();
                        transform2.scaleX = childAt.getScaleX();
                        transform2.scaleY = childAt.getScaleY();
                        float pivotX = childAt.getPivotX();
                        float pivotY = childAt.getPivotY();
                        if (pivotX != 0.0d || pivotY != 0.0d) {
                            transform2.transformPivotX = pivotX;
                            transform2.transformPivotY = pivotY;
                        }
                        transform2.translationX = childAt.getTranslationX();
                        transform2.translationY = childAt.getTranslationY();
                        transform2.translationZ = childAt.getTranslationZ();
                        if (transform2.applyElevation) {
                            transform2.elevation = childAt.getElevation();
                        }
                    }
                }
            }
        }
        for (ConstraintSet.Constraint constraint4 : hashMap.values()) {
            if (constraint4.mDelta != null) {
                if (constraint4.mTargetString != null) {
                    Iterator it = hashMap.keySet().iterator();
                    while (it.hasNext()) {
                        ConstraintSet.Constraint constraint5 = constraintSet.getConstraint(((Integer) it.next()).intValue());
                        String str2 = constraint5.layout.mConstraintTag;
                        if (str2 != null && constraint4.mTargetString.matches(str2)) {
                            constraint4.mDelta.applyDelta(constraint5);
                            constraint5.mCustomConstraints.putAll((HashMap) constraint4.mCustomConstraints.clone());
                        }
                    }
                } else {
                    constraint4.mDelta.applyDelta(constraintSet.getConstraint(constraint4.mViewId));
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:
    
        if (r2 != (-1)) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setTransition(int r9, int r10) {
        /*
            r8 = this;
            androidx.constraintlayout.widget.StateSet r0 = r8.mStateSet
            r1 = -1
            if (r0 == 0) goto L16
            int r0 = r0.stateGetConstraintID(r9)
            if (r0 == r1) goto Lc
            goto Ld
        Lc:
            r0 = r9
        Ld:
            androidx.constraintlayout.widget.StateSet r2 = r8.mStateSet
            int r2 = r2.stateGetConstraintID(r10)
            if (r2 == r1) goto L17
            goto L18
        L16:
            r0 = r9
        L17:
            r2 = r10
        L18:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r3 = r8.mCurrentTransition
            if (r3 == 0) goto L25
            int r4 = r3.mConstraintSetEnd
            if (r4 != r10) goto L25
            int r3 = r3.mConstraintSetStart
            if (r3 != r9) goto L25
            return
        L25:
            java.util.ArrayList r3 = r8.mTransitionList
            java.util.Iterator r4 = r3.iterator()
        L2b:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L51
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.motion.widget.MotionScene$Transition r5 = (androidx.constraintlayout.motion.widget.MotionScene.Transition) r5
            int r6 = r5.mConstraintSetEnd
            if (r6 != r2) goto L3f
            int r7 = r5.mConstraintSetStart
            if (r7 == r0) goto L45
        L3f:
            if (r6 != r10) goto L2b
            int r6 = r5.mConstraintSetStart
            if (r6 != r9) goto L2b
        L45:
            r8.mCurrentTransition = r5
            androidx.constraintlayout.motion.widget.TouchResponse r9 = r5.mTouchResponse
            if (r9 == 0) goto L50
            boolean r8 = r8.mRtl
            r9.setRTL(r8)
        L50:
            return
        L51:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r9 = r8.mDefaultTransition
            java.util.ArrayList r4 = r8.mAbstractTransitionList
            java.util.Iterator r4 = r4.iterator()
        L59:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L6b
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.motion.widget.MotionScene$Transition r5 = (androidx.constraintlayout.motion.widget.MotionScene.Transition) r5
            int r6 = r5.mConstraintSetEnd
            if (r6 != r10) goto L59
            r9 = r5
            goto L59
        L6b:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r10 = new androidx.constraintlayout.motion.widget.MotionScene$Transition
            r10.<init>(r8, r9)
            r10.mConstraintSetStart = r0
            r10.mConstraintSetEnd = r2
            if (r0 == r1) goto L79
            r3.add(r10)
        L79:
            r8.mCurrentTransition = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.setTransition(int, int):void");
    }

    public final boolean supportTouch() {
        Iterator it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            if (((Transition) it.next()).mTouchResponse != null) {
                return true;
            }
        }
        Transition transition = this.mCurrentTransition;
        if (transition != null && transition.mTouchResponse != null) {
            return true;
        }
        return false;
    }

    public final int parseInclude(int i, Context context) {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                String name = xml.getName();
                if (2 == eventType && "ConstraintSet".equals(name)) {
                    return parseConstraintSet(context, xml);
                }
            }
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Transition {
        public int mAutoTransition;
        public int mConstraintSetEnd;
        public int mConstraintSetStart;
        public int mDefaultInterpolator;
        public int mDefaultInterpolatorID;
        public String mDefaultInterpolatorString;
        public boolean mDisable;
        public int mDuration;
        public int mId;
        public boolean mIsAbstract;
        public final ArrayList mKeyFramesList;
        public int mLayoutDuringTransition;
        public final MotionScene mMotionScene;
        public final ArrayList mOnClicks;
        public int mPathMotionArc;
        public float mStagger;
        public TouchResponse mTouchResponse;
        public int mTransitionFlags;

        public Transition(MotionScene motionScene, Transition transition) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mMotionScene = motionScene;
            this.mDuration = motionScene.mDefaultDuration;
            if (transition != null) {
                this.mPathMotionArc = transition.mPathMotionArc;
                this.mDefaultInterpolator = transition.mDefaultInterpolator;
                this.mDefaultInterpolatorString = transition.mDefaultInterpolatorString;
                this.mDefaultInterpolatorID = transition.mDefaultInterpolatorID;
                this.mDuration = transition.mDuration;
                this.mKeyFramesList = transition.mKeyFramesList;
                this.mStagger = transition.mStagger;
                this.mLayoutDuringTransition = transition.mLayoutDuringTransition;
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class TransitionOnClick implements View.OnClickListener {
            public final int mMode;
            public final int mTargetId;
            public final Transition mTransition;

            public TransitionOnClick(Context context, Transition transition, XmlPullParser xmlPullParser) {
                this.mTargetId = -1;
                this.mMode = 17;
                this.mTransition = transition;
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.OnClick);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == 1) {
                        this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                    } else if (index == 0) {
                        this.mMode = obtainStyledAttributes.getInt(index, this.mMode);
                    }
                }
                obtainStyledAttributes.recycle();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r8v4, types: [android.view.View] */
            public final void addOnClickListeners(MotionLayout motionLayout, int i, Transition transition) {
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                int i2 = this.mTargetId;
                MotionLayout motionLayout2 = motionLayout;
                if (i2 != -1) {
                    motionLayout2 = motionLayout.findViewById(i2);
                }
                if (motionLayout2 == null) {
                    Log.e("MotionScene", "OnClick could not find id " + this.mTargetId);
                    return;
                }
                int i3 = transition.mConstraintSetStart;
                int i4 = transition.mConstraintSetEnd;
                if (i3 == -1) {
                    motionLayout2.setOnClickListener(this);
                    return;
                }
                int i5 = this.mMode;
                int i6 = i5 & 1;
                boolean z5 = true;
                if (i6 != 0 && i == i3) {
                    z = true;
                } else {
                    z = false;
                }
                if ((i5 & 256) != 0 && i == i3) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                boolean z6 = z | z2;
                if (i6 != 0 && i == i3) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                boolean z7 = z3 | z6;
                if ((i5 & 16) != 0 && i == i4) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                boolean z8 = z7 | z4;
                if ((i5 & 4096) == 0 || i != i4) {
                    z5 = false;
                }
                if (z8 | z5) {
                    motionLayout2.setOnClickListener(this);
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:49:0x00a0  */
            /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onClick(android.view.View r11) {
                /*
                    Method dump skipped, instructions count: 222
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick.onClick(android.view.View):void");
            }

            public final void removeOnClickListeners(MotionLayout motionLayout) {
                int i = this.mTargetId;
                if (i == -1) {
                    return;
                }
                View findViewById = motionLayout.findViewById(i);
                if (findViewById == null) {
                    Log.e("MotionScene", " (*)  could not find id " + this.mTargetId);
                    return;
                }
                findViewById.setOnClickListener(null);
            }

            public TransitionOnClick(Transition transition, int i, int i2) {
                this.mTargetId = -1;
                this.mMode = 17;
                this.mTransition = transition;
                this.mTargetId = i;
                this.mMode = i2;
            }
        }

        public Transition(int i, MotionScene motionScene, int i2, int i3) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mId = i;
            this.mMotionScene = motionScene;
            this.mConstraintSetStart = i2;
            this.mConstraintSetEnd = i3;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
        }

        public Transition(MotionScene motionScene, Context context, XmlPullParser xmlPullParser) {
            this.mId = -1;
            this.mIsAbstract = false;
            this.mConstraintSetEnd = -1;
            this.mConstraintSetStart = -1;
            this.mDefaultInterpolator = 0;
            this.mDefaultInterpolatorString = null;
            this.mDefaultInterpolatorID = -1;
            this.mDuration = 400;
            this.mStagger = 0.0f;
            this.mKeyFramesList = new ArrayList();
            this.mTouchResponse = null;
            this.mOnClicks = new ArrayList();
            this.mAutoTransition = 0;
            this.mDisable = false;
            this.mPathMotionArc = -1;
            this.mLayoutDuringTransition = 0;
            this.mTransitionFlags = 0;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
            this.mMotionScene = motionScene;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R$styleable.Transition);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                SparseArray sparseArray = motionScene.mConstraintSetMap;
                if (index == 2) {
                    this.mConstraintSetEnd = obtainStyledAttributes.getResourceId(index, -1);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintSetEnd);
                    if ("layout".equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.load(this.mConstraintSetEnd, context);
                        sparseArray.append(this.mConstraintSetEnd, constraintSet);
                    } else if ("xml".equals(resourceTypeName)) {
                        this.mConstraintSetEnd = motionScene.parseInclude(this.mConstraintSetEnd, context);
                    }
                } else if (index == 3) {
                    this.mConstraintSetStart = obtainStyledAttributes.getResourceId(index, this.mConstraintSetStart);
                    String resourceTypeName2 = context.getResources().getResourceTypeName(this.mConstraintSetStart);
                    if ("layout".equals(resourceTypeName2)) {
                        ConstraintSet constraintSet2 = new ConstraintSet();
                        constraintSet2.load(this.mConstraintSetStart, context);
                        sparseArray.append(this.mConstraintSetStart, constraintSet2);
                    } else if ("xml".equals(resourceTypeName2)) {
                        this.mConstraintSetStart = motionScene.parseInclude(this.mConstraintSetStart, context);
                    }
                } else if (index == 6) {
                    int i2 = obtainStyledAttributes.peekValue(index).type;
                    if (i2 == 1) {
                        int resourceId = obtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolatorID = resourceId;
                        if (resourceId != -1) {
                            this.mDefaultInterpolator = -2;
                        }
                    } else if (i2 == 3) {
                        String string = obtainStyledAttributes.getString(index);
                        this.mDefaultInterpolatorString = string;
                        if (string != null) {
                            if (string.indexOf("/") > 0) {
                                this.mDefaultInterpolatorID = obtainStyledAttributes.getResourceId(index, -1);
                                this.mDefaultInterpolator = -2;
                            } else {
                                this.mDefaultInterpolator = -1;
                            }
                        }
                    } else {
                        this.mDefaultInterpolator = obtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                    }
                } else if (index == 4) {
                    int i3 = obtainStyledAttributes.getInt(index, this.mDuration);
                    this.mDuration = i3;
                    if (i3 < 8) {
                        this.mDuration = 8;
                    }
                } else if (index == 8) {
                    this.mStagger = obtainStyledAttributes.getFloat(index, this.mStagger);
                } else if (index == 1) {
                    this.mAutoTransition = obtainStyledAttributes.getInteger(index, this.mAutoTransition);
                } else if (index == 0) {
                    this.mId = obtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == 9) {
                    this.mDisable = obtainStyledAttributes.getBoolean(index, this.mDisable);
                } else if (index == 7) {
                    this.mPathMotionArc = obtainStyledAttributes.getInteger(index, -1);
                } else if (index == 5) {
                    this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
                } else if (index == 10) {
                    this.mTransitionFlags = obtainStyledAttributes.getInteger(index, 0);
                }
            }
            if (this.mConstraintSetStart == -1) {
                this.mIsAbstract = true;
            }
            obtainStyledAttributes.recycle();
        }
    }

    public MotionScene(Context context, MotionLayout motionLayout, int i) {
        int eventType;
        Transition transition;
        this.mStateSet = null;
        this.mCurrentTransition = null;
        ArrayList arrayList = new ArrayList();
        this.mTransitionList = arrayList;
        this.mDefaultTransition = null;
        this.mAbstractTransitionList = new ArrayList();
        this.mConstraintSetMap = new SparseArray();
        this.mConstraintSetIdMap = new HashMap();
        this.mDeriveMap = new SparseIntArray();
        this.mDefaultDuration = 400;
        this.mLayoutDuringTransition = 0;
        this.mIgnoreTouch = false;
        this.mMotionOutsideRegion = false;
        this.mMotionLayout = motionLayout;
        this.mViewTransitionController = new ViewTransitionController(motionLayout);
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            eventType = xml.getEventType();
            transition = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
        while (true) {
            char c = 1;
            if (eventType != 1) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    switch (name.hashCode()) {
                        case -1349929691:
                            if (name.equals("ConstraintSet")) {
                                c = 5;
                                break;
                            }
                            break;
                        case -1239391468:
                            if (name.equals("KeyFrameSet")) {
                                c = '\b';
                                break;
                            }
                            break;
                        case -687739768:
                            if (name.equals("Include")) {
                                c = 7;
                                break;
                            }
                            break;
                        case 61998586:
                            if (name.equals("ViewTransition")) {
                                c = '\t';
                                break;
                            }
                            break;
                        case 269306229:
                            if (name.equals("Transition")) {
                                break;
                            }
                            break;
                        case 312750793:
                            if (name.equals("OnClick")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 327855227:
                            if (name.equals("OnSwipe")) {
                                c = 2;
                                break;
                            }
                            break;
                        case 793277014:
                            if (name.equals("MotionScene")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 1382829617:
                            if (name.equals("StateSet")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 1942574248:
                            if (name.equals("include")) {
                                c = 6;
                                break;
                            }
                            break;
                    }
                    c = 65535;
                    switch (c) {
                        case 0:
                            parseMotionSceneTags(context, xml);
                            break;
                        case 1:
                            transition = new Transition(this, context, xml);
                            arrayList.add(transition);
                            if (this.mCurrentTransition == null && !transition.mIsAbstract) {
                                this.mCurrentTransition = transition;
                                TouchResponse touchResponse = transition.mTouchResponse;
                                if (touchResponse != null) {
                                    touchResponse.setRTL(this.mRtl);
                                }
                            }
                            if (!transition.mIsAbstract) {
                                break;
                            } else {
                                if (transition.mConstraintSetEnd == -1) {
                                    this.mDefaultTransition = transition;
                                } else {
                                    this.mAbstractTransitionList.add(transition);
                                }
                                arrayList.remove(transition);
                                break;
                            }
                            break;
                        case 2:
                            if (transition == null) {
                                context.getResources().getResourceEntryName(i);
                                xml.getLineNumber();
                            }
                            if (transition == null) {
                                break;
                            } else {
                                transition.mTouchResponse = new TouchResponse(context, this.mMotionLayout, xml);
                                break;
                            }
                        case 3:
                            if (transition == null) {
                                break;
                            } else {
                                transition.mOnClicks.add(new Transition.TransitionOnClick(context, transition, xml));
                                break;
                            }
                        case 4:
                            this.mStateSet = new StateSet(context, xml);
                            break;
                        case 5:
                            parseConstraintSet(context, xml);
                            break;
                        case 6:
                        case 7:
                            parseInclude(context, xml);
                            break;
                        case '\b':
                            KeyFrames keyFrames = new KeyFrames(context, xml);
                            if (transition == null) {
                                break;
                            } else {
                                transition.mKeyFramesList.add(keyFrames);
                                break;
                            }
                        case '\t':
                            final ViewTransition viewTransition = new ViewTransition(context, xml);
                            final ViewTransitionController viewTransitionController = this.mViewTransitionController;
                            viewTransitionController.viewTransitions.add(viewTransition);
                            viewTransitionController.mRelatedViews = null;
                            int i2 = viewTransition.mOnStateTransition;
                            if (i2 != 4) {
                                if (i2 != 5) {
                                    break;
                                } else {
                                    final int i3 = viewTransition.mSharedValueID;
                                    final int i4 = viewTransition.mSharedValueTarget;
                                    if (ConstraintLayout.sSharedValues == null) {
                                        ConstraintLayout.sSharedValues = new SharedValues();
                                    }
                                    final boolean z = false;
                                    ConstraintLayout.sSharedValues.addListener(viewTransition.mSharedValueID, new SharedValues.SharedValuesListener(viewTransitionController, viewTransition, i3, z, i4) { // from class: androidx.constraintlayout.motion.widget.ViewTransitionController.1
                                        public AnonymousClass1(final ViewTransitionController viewTransitionController2, final ViewTransition viewTransition2, final int i32, final boolean z2, final int i42) {
                                        }
                                    });
                                    break;
                                }
                            } else {
                                final int i5 = viewTransition2.mSharedValueID;
                                final int i6 = viewTransition2.mSharedValueTarget;
                                if (ConstraintLayout.sSharedValues == null) {
                                    ConstraintLayout.sSharedValues = new SharedValues();
                                }
                                final boolean z2 = true;
                                ConstraintLayout.sSharedValues.addListener(viewTransition2.mSharedValueID, new SharedValues.SharedValuesListener(viewTransitionController2, viewTransition2, i5, z2, i6) { // from class: androidx.constraintlayout.motion.widget.ViewTransitionController.1
                                    public AnonymousClass1(final ViewTransitionController viewTransitionController2, final ViewTransition viewTransition2, final int i52, final boolean z22, final int i62) {
                                    }
                                });
                                break;
                            }
                    }
                }
                eventType = xml.next();
            } else {
                this.mConstraintSetMap.put(R.id.motion_base, new ConstraintSet());
                this.mConstraintSetIdMap.put("motion_base", Integer.valueOf(R.id.motion_base));
                return;
            }
        }
    }
}
