package com.samsung.vekit.Common.Object;

import java.util.Vector;

/* loaded from: classes6.dex */
public class Vector4<T> {
    private final int W;
    private final int X;
    private final int Y;
    private final int Z;
    Vector<T> data;

    public Vector4(T x, T y, T z, T w) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        this.W = 3;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(x);
        this.data.add(y);
        this.data.add(z);
        this.data.add(w);
    }

    public Vector4(T[] array) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        this.W = 3;
        Vector<T> vector = new Vector<>();
        this.data = vector;
        vector.add(array[0]);
        this.data.add(array[1]);
        this.data.add(array[2]);
        this.data.add(array[3]);
    }

    public Vector4(Vector4<T> array) {
        this.X = 0;
        this.Y = 1;
        this.Z = 2;
        this.W = 3;
        this.data = new Vector<>();
        set(array.getX(), array.getY(), array.getZ(), array.getW());
    }

    public T[] toArray() {
        return (T[]) this.data.stream().toArray();
    }

    public void set(T x, T y, T z, T w) {
        this.data.clear();
        this.data.add(x);
        this.data.add(y);
        this.data.add(z);
        this.data.add(w);
    }

    public T getX() {
        return this.data.get(0);
    }

    public void setX(T x) {
        this.data.set(0, x);
    }

    public T getY() {
        return this.data.get(1);
    }

    public void setY(T y) {
        this.data.set(1, y);
    }

    public T getZ() {
        return this.data.get(2);
    }

    public void setZ(T z) {
        this.data.set(2, z);
    }

    public T getW() {
        return this.data.get(3);
    }

    public void setW(T w) {
        this.data.set(3, w);
    }
}