package androidx.core.animation;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Property;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.PropertyValuesHolder;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ObjectAnimator extends ValueAnimator {
    public Property mProperty;
    public String mPropertyName;
    public WeakReference mTarget;

    public ObjectAnimator() {
    }

    public static ObjectAnimator ofFloat(Object obj, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, "translationX");
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void animateValue(float f) {
        Object obj;
        WeakReference weakReference = this.mTarget;
        if (weakReference == null) {
            obj = null;
        } else {
            obj = weakReference.get();
        }
        if (this.mTarget != null && obj == null) {
            cancel();
            return;
        }
        super.animateValue(f);
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].setAnimatedValue(obj);
        }
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    /* renamed from: clone */
    public final Animator mo5clone() {
        return (ObjectAnimator) super.mo5clone();
    }

    @Override // androidx.core.animation.ValueAnimator
    public final String getNameForTrace() {
        String m;
        StringBuilder sb = new StringBuilder("animator:");
        String str = this.mPropertyName;
        if (str == null) {
            Property property = this.mProperty;
            if (property != null) {
                str = property.getName();
            } else {
                PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
                String str2 = null;
                if (propertyValuesHolderArr != null && propertyValuesHolderArr.length > 0) {
                    for (int i = 0; i < this.mValues.length; i++) {
                        if (i == 0) {
                            m = "";
                        } else {
                            m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, ",");
                        }
                        StringBuilder m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m(m);
                        m2.append(this.mValues[i].mPropertyName);
                        str2 = m2.toString();
                    }
                }
                str = str2;
            }
        }
        sb.append(str);
        return sb.toString();
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void initAnimation() {
        Object obj;
        int size;
        int size2;
        if (!this.mInitialized) {
            WeakReference weakReference = this.mTarget;
            if (weakReference == null) {
                obj = null;
            } else {
                obj = weakReference.get();
            }
            if (obj != null) {
                int length = this.mValues.length;
                for (int i = 0; i < length; i++) {
                    PropertyValuesHolder propertyValuesHolder = this.mValues[i];
                    if (propertyValuesHolder.mProperty != null) {
                        try {
                            List list = ((KeyframeSet) propertyValuesHolder.mKeyframes).mKeyframes;
                            if (list == null) {
                                size = 0;
                            } else {
                                size = list.size();
                            }
                            Object obj2 = null;
                            for (int i2 = 0; i2 < size; i2++) {
                                Keyframe keyframe = (Keyframe) list.get(i2);
                                if (!keyframe.mHasValue || keyframe.mValueWasSetOnStart) {
                                    if (obj2 == null) {
                                        obj2 = propertyValuesHolder.mProperty.get(obj);
                                    }
                                    keyframe.setValue(obj2);
                                    keyframe.mValueWasSetOnStart = true;
                                }
                            }
                        } catch (ClassCastException unused) {
                            Log.w("PropertyValuesHolder", "No such property (" + propertyValuesHolder.mProperty.getName() + ") on target object " + obj + ". Trying reflection instead");
                            propertyValuesHolder.mProperty = null;
                        }
                    }
                    if (propertyValuesHolder.mProperty == null) {
                        Class<?> cls = obj.getClass();
                        if (propertyValuesHolder.mSetter == null) {
                            propertyValuesHolder.mSetter = propertyValuesHolder.setupSetterOrGetter(cls, PropertyValuesHolder.sSetterPropertyMap, "set", propertyValuesHolder.mValueType);
                        }
                        List list2 = ((KeyframeSet) propertyValuesHolder.mKeyframes).mKeyframes;
                        if (list2 == null) {
                            size2 = 0;
                        } else {
                            size2 = list2.size();
                        }
                        for (int i3 = 0; i3 < size2; i3++) {
                            Keyframe keyframe2 = (Keyframe) list2.get(i3);
                            if (!keyframe2.mHasValue || keyframe2.mValueWasSetOnStart) {
                                if (propertyValuesHolder.mGetter == null) {
                                    Method method = propertyValuesHolder.setupSetterOrGetter(cls, PropertyValuesHolder.sGetterPropertyMap, "get", null);
                                    propertyValuesHolder.mGetter = method;
                                    if (method == null) {
                                        break;
                                    }
                                }
                                try {
                                    keyframe2.setValue(propertyValuesHolder.mGetter.invoke(obj, new Object[0]));
                                    keyframe2.mValueWasSetOnStart = true;
                                } catch (IllegalAccessException e) {
                                    Log.e("PropertyValuesHolder", e.toString());
                                } catch (InvocationTargetException e2) {
                                    Log.e("PropertyValuesHolder", e2.toString());
                                }
                            }
                        }
                    }
                }
            }
            super.initAnimation();
        }
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final Animator setDuration(long j) {
        super.setDuration(j);
        return this;
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void setFloatValues(float... fArr) {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null && propertyValuesHolderArr.length != 0) {
            super.setFloatValues(fArr);
            return;
        }
        Property property = this.mProperty;
        if (property != null) {
            Class[] clsArr = PropertyValuesHolder.FLOAT_VARIANTS;
            setValues(new PropertyValuesHolder.FloatPropertyValuesHolder(property, fArr));
        } else {
            String str = this.mPropertyName;
            Class[] clsArr2 = PropertyValuesHolder.FLOAT_VARIANTS;
            setValues(new PropertyValuesHolder.FloatPropertyValuesHolder(str, fArr));
        }
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void setIntValues(int... iArr) {
        throw null;
    }

    public final void setTarget(Object obj) {
        Object obj2;
        WeakReference weakReference = this.mTarget;
        WeakReference weakReference2 = null;
        if (weakReference == null) {
            obj2 = null;
        } else {
            obj2 = weakReference.get();
        }
        if (obj2 != obj) {
            if (this.mStarted) {
                cancel();
            }
            if (obj != null) {
                weakReference2 = new WeakReference(obj);
            }
            this.mTarget = weakReference2;
            this.mInitialized = false;
        }
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final void start() {
        ArrayList arrayList = AnimationHandler.getInstance().mAnimationCallbacks;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            AnimationHandler.AnimationFrameCallback animationFrameCallback = (AnimationHandler.AnimationFrameCallback) arrayList.get(size);
            if (animationFrameCallback != null && (animationFrameCallback instanceof ObjectAnimator)) {
            }
        }
        start(false);
    }

    @Override // androidx.core.animation.ValueAnimator
    public final String toString() {
        Object obj;
        StringBuilder sb = new StringBuilder("ObjectAnimator@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append(", target ");
        WeakReference weakReference = this.mTarget;
        if (weakReference == null) {
            obj = null;
        } else {
            obj = weakReference.get();
        }
        sb.append(obj);
        String sb2 = sb.toString();
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb2, "\n    ");
                m.append(this.mValues[i].toString());
                sb2 = m.toString();
            }
        }
        return sb2;
    }

    private ObjectAnimator(Object obj, String str) {
        setTarget(obj);
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArr[0];
            String str2 = propertyValuesHolder.mPropertyName;
            propertyValuesHolder.mPropertyName = str;
            this.mValuesMap.remove(str2);
            this.mValuesMap.put(str, propertyValuesHolder);
        }
        this.mPropertyName = str;
        this.mInitialized = false;
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    /* renamed from: clone */
    public final ValueAnimator mo5clone() {
        return (ObjectAnimator) super.mo5clone();
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final ValueAnimator setDuration(long j) {
        super.setDuration(j);
        return this;
    }

    public static ObjectAnimator ofFloat(Object obj, Property property, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, (Property<Object, ?>) property);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    /* renamed from: clone */
    public final Object mo5clone() {
        return (ObjectAnimator) super.mo5clone();
    }

    /* renamed from: setDuration, reason: collision with other method in class */
    public final void m9setDuration(long j) {
        super.setDuration(j);
    }

    private <T> ObjectAnimator(T t, Property<T, ?> property) {
        setTarget(t);
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArr[0];
            String str = propertyValuesHolder.mPropertyName;
            propertyValuesHolder.setProperty(property);
            this.mValuesMap.remove(str);
            this.mValuesMap.put(this.mPropertyName, propertyValuesHolder);
        }
        if (this.mProperty != null) {
            this.mPropertyName = property.getName();
        }
        this.mProperty = property;
        this.mInitialized = false;
    }
}
