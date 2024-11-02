package com.samsung.android.sume.core.evaluate;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.Comparable;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class GreaterEqual<T extends Comparable<T>> extends GenericEvaluator<T> {
    public static final Parcelable.Creator<GreaterEqual<?>> CREATOR = new Parcelable.Creator<GreaterEqual<?>>() { // from class: com.samsung.android.sume.core.evaluate.GreaterEqual.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GreaterEqual<?> createFromParcel(Parcel in) {
            return new GreaterEqual<>(in);
        }

        @Override // android.os.Parcelable.Creator
        public GreaterEqual<?>[] newArray(int size) {
            return new GreaterEqual[size];
        }
    };

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Evaluator and(Evaluator evaluator) {
        return super.and(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator
    public /* bridge */ /* synthetic */ int compareTo(Evaluator evaluator) {
        return super.compareTo(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, android.os.Parcelable
    public /* bridge */ /* synthetic */ int describeContents() {
        return super.describeContents();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Comparable getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Class getValueType() {
        return super.getValueType();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Evaluator or(Evaluator evaluator) {
        return super.or(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Stream stream() {
        return super.stream();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, android.os.Parcelable
    public /* bridge */ /* synthetic */ void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public GreaterEqual(T value) {
        super(value);
    }

    GreaterEqual(Parcel in) {
        super(in);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(V value) {
        return ((Comparable) value).compareTo(getValue()) >= 0;
    }

    /* renamed from: com.samsung.android.sume.core.evaluate.GreaterEqual$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<GreaterEqual<?>> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GreaterEqual<?> createFromParcel(Parcel in) {
            return new GreaterEqual<>(in);
        }

        @Override // android.os.Parcelable.Creator
        public GreaterEqual<?>[] newArray(int size) {
            return new GreaterEqual[size];
        }
    }
}
