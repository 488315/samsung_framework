package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R$styleable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/* loaded from: classes.dex */
public final class KeyTrigger extends Key {
    private float mFireLastPos;
    float mTriggerSlack = 0.1f;
    int mViewTransitionOnNegativeCross = -1;
    int mViewTransitionOnPositiveCross = -1;
    int mViewTransitionOnCross = -1;
    RectF mCollisionRect = new RectF();
    RectF mTargetRect = new RectF();
    HashMap<String, Method> mMethodHashMap = new HashMap<>();
    private String mCross = null;
    private int mTriggerReceiver = -1;
    private String mNegativeCross = null;
    private String mPositiveCross = null;
    private int mTriggerID = -1;
    private int mTriggerCollisionId = -1;
    private View mTriggerCollisionView = null;
    private boolean mFireCrossReset = true;
    private boolean mFireNegativeReset = true;
    private boolean mFirePositiveReset = true;
    private float mFireThreshold = Float.NaN;
    private boolean mPostLayout = false;

    private static class Loader {
        private static SparseIntArray sAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sAttrMap = sparseIntArray;
            sparseIntArray.append(0, 8);
            sAttrMap.append(4, 4);
            sAttrMap.append(5, 1);
            sAttrMap.append(6, 2);
            sAttrMap.append(1, 7);
            sAttrMap.append(7, 6);
            sAttrMap.append(9, 5);
            sAttrMap.append(3, 9);
            sAttrMap.append(2, 10);
            sAttrMap.append(8, 11);
            sAttrMap.append(10, 12);
            sAttrMap.append(11, 13);
            sAttrMap.append(12, 14);
        }

        public static void read(KeyTrigger keyTrigger, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (sAttrMap.get(index)) {
                    case 1:
                        keyTrigger.mNegativeCross = typedArray.getString(index);
                        break;
                    case 2:
                        keyTrigger.mPositiveCross = typedArray.getString(index);
                        break;
                    case 3:
                    default:
                        Log.e("KeyTrigger", "unused attribute 0x" + Integer.toHexString(index) + "   " + sAttrMap.get(index));
                        break;
                    case 4:
                        keyTrigger.mCross = typedArray.getString(index);
                        break;
                    case 5:
                        keyTrigger.mTriggerSlack = typedArray.getFloat(index, keyTrigger.mTriggerSlack);
                        break;
                    case 6:
                        keyTrigger.mTriggerID = typedArray.getResourceId(index, keyTrigger.mTriggerID);
                        break;
                    case 7:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            int resourceId = typedArray.getResourceId(index, keyTrigger.mTargetId);
                            keyTrigger.mTargetId = resourceId;
                            if (resourceId == -1) {
                                keyTrigger.mTargetString = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyTrigger.mTargetString = typedArray.getString(index);
                            break;
                        } else {
                            keyTrigger.mTargetId = typedArray.getResourceId(index, keyTrigger.mTargetId);
                            break;
                        }
                    case 8:
                        int integer = typedArray.getInteger(index, keyTrigger.mFramePosition);
                        keyTrigger.mFramePosition = integer;
                        keyTrigger.mFireThreshold = (integer + 0.5f) / 100.0f;
                        break;
                    case 9:
                        keyTrigger.mTriggerCollisionId = typedArray.getResourceId(index, keyTrigger.mTriggerCollisionId);
                        break;
                    case 10:
                        keyTrigger.mPostLayout = typedArray.getBoolean(index, keyTrigger.mPostLayout);
                        break;
                    case 11:
                        keyTrigger.mTriggerReceiver = typedArray.getResourceId(index, keyTrigger.mTriggerReceiver);
                        break;
                    case 12:
                        keyTrigger.mViewTransitionOnCross = typedArray.getResourceId(index, keyTrigger.mViewTransitionOnCross);
                        break;
                    case 13:
                        keyTrigger.mViewTransitionOnNegativeCross = typedArray.getResourceId(index, keyTrigger.mViewTransitionOnNegativeCross);
                        break;
                    case 14:
                        keyTrigger.mViewTransitionOnPositiveCross = typedArray.getResourceId(index, keyTrigger.mViewTransitionOnPositiveCross);
                        break;
                }
            }
        }
    }

    public KeyTrigger() {
        this.mCustomConstraints = new HashMap<>();
    }

    private void fire(View view, String str) {
        Method method;
        if (str == null) {
            return;
        }
        if (str.startsWith(".")) {
            boolean z = str.length() == 1;
            if (!z) {
                str = str.substring(1).toLowerCase(Locale.ROOT);
            }
            for (String str2 : this.mCustomConstraints.keySet()) {
                String lowerCase = str2.toLowerCase(Locale.ROOT);
                if (z || lowerCase.matches(str)) {
                    ConstraintAttribute constraintAttribute = this.mCustomConstraints.get(str2);
                    if (constraintAttribute != null) {
                        constraintAttribute.applyCustom(view);
                    }
                }
            }
            return;
        }
        if (this.mMethodHashMap.containsKey(str)) {
            method = this.mMethodHashMap.get(str);
            if (method == null) {
                return;
            }
        } else {
            method = null;
        }
        if (method == null) {
            try {
                method = view.getClass().getMethod(str, new Class[0]);
                this.mMethodHashMap.put(str, method);
            } catch (NoSuchMethodException unused) {
                this.mMethodHashMap.put(str, null);
                Log.e("KeyTrigger", "Could not find method \"" + str + "\"on class " + view.getClass().getSimpleName() + " " + Debug.getName(view));
                return;
            }
        }
        try {
            method.invoke(view, new Object[0]);
        } catch (Exception unused2) {
            Log.e("KeyTrigger", "Exception in call \"" + this.mCross + "\"on class " + view.getClass().getSimpleName() + " " + Debug.getName(view));
        }
    }

    private static void setUpRect(RectF rectF, View view, boolean z) {
        rectF.top = view.getTop();
        rectF.bottom = view.getBottom();
        rectF.left = view.getLeft();
        rectF.right = view.getRight();
        if (z) {
            view.getMatrix().mapRect(rectF);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void conditionallyFire(android.view.View r11, float r12) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyTrigger.conditionallyFire(android.view.View, float):void");
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R$styleable.KeyTrigger));
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo4clone() {
        KeyTrigger keyTrigger = new KeyTrigger();
        super.copy(this);
        keyTrigger.mCross = this.mCross;
        keyTrigger.mTriggerReceiver = this.mTriggerReceiver;
        keyTrigger.mNegativeCross = this.mNegativeCross;
        keyTrigger.mPositiveCross = this.mPositiveCross;
        keyTrigger.mTriggerID = this.mTriggerID;
        keyTrigger.mTriggerCollisionId = this.mTriggerCollisionId;
        keyTrigger.mTriggerCollisionView = this.mTriggerCollisionView;
        keyTrigger.mTriggerSlack = this.mTriggerSlack;
        keyTrigger.mFireCrossReset = this.mFireCrossReset;
        keyTrigger.mFireNegativeReset = this.mFireNegativeReset;
        keyTrigger.mFirePositiveReset = this.mFirePositiveReset;
        keyTrigger.mFireThreshold = this.mFireThreshold;
        keyTrigger.mFireLastPos = this.mFireLastPos;
        keyTrigger.mPostLayout = this.mPostLayout;
        keyTrigger.mCollisionRect = this.mCollisionRect;
        keyTrigger.mTargetRect = this.mTargetRect;
        keyTrigger.mMethodHashMap = this.mMethodHashMap;
        return keyTrigger;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void addValues(HashMap<String, ViewSpline> hashMap) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void getAttributeNames(HashSet<String> hashSet) {
    }
}
