package android.filterfw.core;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class Frame {
    public static final int NO_BINDING = 0;
    public static final long TIMESTAMP_NOT_SET = -2;
    public static final long TIMESTAMP_UNKNOWN = -1;
    private long mBindingId;
    private int mBindingType;
    private FrameFormat mFormat;
    private FrameManager mFrameManager;
    private boolean mReadOnly;
    private int mRefCount;
    private boolean mReusable;
    private long mTimestamp;

    public abstract Bitmap getBitmap();

    public abstract ByteBuffer getData();

    public abstract float[] getFloats();

    public abstract int[] getInts();

    public abstract Object getObjectValue();

    protected abstract boolean hasNativeAllocation();

    protected abstract void releaseNativeAllocation();

    public abstract void setBitmap(Bitmap bitmap);

    public abstract void setData(ByteBuffer byteBuffer, int i, int i2);

    public abstract void setFloats(float[] fArr);

    public abstract void setInts(int[] iArr);

    Frame(FrameFormat format, FrameManager frameManager) {
        this.mReadOnly = false;
        this.mReusable = false;
        this.mRefCount = 1;
        this.mBindingType = 0;
        this.mBindingId = 0L;
        this.mTimestamp = -2L;
        this.mFormat = format.mutableCopy();
        this.mFrameManager = frameManager;
    }

    Frame(FrameFormat format, FrameManager frameManager, int bindingType, long bindingId) {
        this.mReadOnly = false;
        this.mReusable = false;
        this.mRefCount = 1;
        this.mBindingType = 0;
        this.mBindingId = 0L;
        this.mTimestamp = -2L;
        this.mFormat = format.mutableCopy();
        this.mFrameManager = frameManager;
        this.mBindingType = bindingType;
        this.mBindingId = bindingId;
    }

    public FrameFormat getFormat() {
        return this.mFormat;
    }

    public int getCapacity() {
        return getFormat().getSize();
    }

    public boolean isReadOnly() {
        return this.mReadOnly;
    }

    public int getBindingType() {
        return this.mBindingType;
    }

    public long getBindingId() {
        return this.mBindingId;
    }

    public void setObjectValue(Object object) {
        assertFrameMutable();
        if (object instanceof int[]) {
            setInts((int[]) object);
            return;
        }
        if (object instanceof float[]) {
            setFloats((float[]) object);
            return;
        }
        if (object instanceof ByteBuffer) {
            setData((ByteBuffer) object);
        } else if (object instanceof Bitmap) {
            setBitmap((Bitmap) object);
        } else {
            setGenericObjectValue(object);
        }
    }

    public void setData(ByteBuffer buffer) {
        setData(buffer, 0, buffer.limit());
    }

    public void setData(byte[] bytes, int offset, int length) {
        setData(ByteBuffer.wrap(bytes, offset, length));
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setDataFromFrame(Frame frame) {
        setData(frame.getData());
    }

    protected boolean requestResize(int[] newDimensions) {
        return false;
    }

    public int getRefCount() {
        return this.mRefCount;
    }

    public Frame release() {
        if (this.mFrameManager != null) {
            return this.mFrameManager.releaseFrame(this);
        }
        return this;
    }

    public Frame retain() {
        if (this.mFrameManager != null) {
            return this.mFrameManager.retainFrame(this);
        }
        return this;
    }

    public FrameManager getFrameManager() {
        return this.mFrameManager;
    }

    protected void assertFrameMutable() {
        if (isReadOnly()) {
            throw new RuntimeException("Attempting to modify read-only frame!");
        }
    }

    protected void setReusable(boolean reusable) {
        this.mReusable = reusable;
    }

    protected void setFormat(FrameFormat format) {
        this.mFormat = format.mutableCopy();
    }

    protected void setGenericObjectValue(Object value) {
        throw new RuntimeException("Cannot set object value of unsupported type: " + value.getClass());
    }

    protected static Bitmap convertBitmapToRGBA(Bitmap bitmap) {
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            return bitmap;
        }
        Bitmap result = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        if (result == null) {
            throw new RuntimeException("Error converting bitmap to RGBA!");
        }
        if (result.getRowBytes() != result.getWidth() * 4) {
            throw new RuntimeException("Unsupported row byte count in bitmap!");
        }
        return result;
    }

    protected void reset(FrameFormat newFormat) {
        this.mFormat = newFormat.mutableCopy();
        this.mReadOnly = false;
        this.mRefCount = 1;
    }

    protected void onFrameStore() {
    }

    protected void onFrameFetch() {
    }

    final int incRefCount() {
        this.mRefCount++;
        return this.mRefCount;
    }

    final int decRefCount() {
        this.mRefCount--;
        return this.mRefCount;
    }

    final boolean isReusable() {
        return this.mReusable;
    }

    final void markReadOnly() {
        this.mReadOnly = true;
    }
}
