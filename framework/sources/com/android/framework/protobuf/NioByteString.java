package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
final class NioByteString extends ByteString.LeafByteString {
    private final ByteBuffer buffer;

    NioByteString(ByteBuffer buffer) {
        Internal.checkNotNull(buffer, "buffer");
        this.buffer = buffer.slice().order(ByteOrder.nativeOrder());
    }

    private Object writeReplace() {
        return ByteString.copyFrom(this.buffer.slice());
    }

    private void readObject(ObjectInputStream in) throws IOException {
        throw new InvalidObjectException("NioByteString instances are not to be serialized directly");
    }

    @Override // com.android.framework.protobuf.ByteString
    public byte byteAt(int index) {
        try {
            return this.buffer.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    public byte internalByteAt(int index) {
        return byteAt(index);
    }

    @Override // com.android.framework.protobuf.ByteString
    public int size() {
        return this.buffer.remaining();
    }

    @Override // com.android.framework.protobuf.ByteString
    public ByteString substring(int beginIndex, int endIndex) {
        try {
            ByteBuffer slice = slice(beginIndex, endIndex);
            return new NioByteString(slice);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        ByteBuffer slice = this.buffer.slice();
        slice.position(sourceOffset);
        slice.get(target, targetOffset, numberToCopy);
    }

    @Override // com.android.framework.protobuf.ByteString
    public void copyTo(ByteBuffer target) {
        target.put(this.buffer.slice());
    }

    @Override // com.android.framework.protobuf.ByteString
    public void writeTo(OutputStream out) throws IOException {
        out.write(toByteArray());
    }

    @Override // com.android.framework.protobuf.ByteString.LeafByteString
    boolean equalsRange(ByteString other, int offset, int length) {
        return substring(0, length).equals(other.substring(offset, offset + length));
    }

    @Override // com.android.framework.protobuf.ByteString
    void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
        if (this.buffer.hasArray()) {
            int bufferOffset = this.buffer.arrayOffset() + this.buffer.position() + sourceOffset;
            out.write(this.buffer.array(), bufferOffset, numberToWrite);
        } else {
            int bufferOffset2 = sourceOffset + numberToWrite;
            ByteBufferWriter.write(slice(sourceOffset, bufferOffset2), out);
        }
    }

    @Override // com.android.framework.protobuf.ByteString
    void writeTo(ByteOutput output) throws IOException {
        output.writeLazy(this.buffer.slice());
    }

    @Override // com.android.framework.protobuf.ByteString
    public ByteBuffer asReadOnlyByteBuffer() {
        return this.buffer.asReadOnlyBuffer();
    }

    @Override // com.android.framework.protobuf.ByteString
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        return Collections.singletonList(asReadOnlyByteBuffer());
    }

    @Override // com.android.framework.protobuf.ByteString
    protected String toStringInternal(Charset charset) {
        byte[] bytes;
        int offset;
        int length;
        if (this.buffer.hasArray()) {
            bytes = this.buffer.array();
            offset = this.buffer.arrayOffset() + this.buffer.position();
            length = this.buffer.remaining();
        } else {
            bytes = toByteArray();
            offset = 0;
            length = bytes.length;
        }
        return new String(bytes, offset, length, charset);
    }

    @Override // com.android.framework.protobuf.ByteString
    public boolean isValidUtf8() {
        return Utf8.isValidUtf8(this.buffer);
    }

    @Override // com.android.framework.protobuf.ByteString
    protected int partialIsValidUtf8(int state, int offset, int length) {
        return Utf8.partialIsValidUtf8(state, this.buffer, offset, offset + length);
    }

    @Override // com.android.framework.protobuf.ByteString
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        ByteString otherString = (ByteString) other;
        if (size() != otherString.size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (other instanceof NioByteString) {
            return this.buffer.equals(((NioByteString) other).buffer);
        }
        if (other instanceof RopeByteString) {
            return other.equals(this);
        }
        return this.buffer.equals(otherString.asReadOnlyByteBuffer());
    }

    @Override // com.android.framework.protobuf.ByteString
    protected int partialHash(int h, int offset, int length) {
        for (int i = offset; i < offset + length; i++) {
            h = (h * 31) + this.buffer.get(i);
        }
        return h;
    }

    @Override // com.android.framework.protobuf.ByteString
    public InputStream newInput() {
        return new InputStream() { // from class: com.android.framework.protobuf.NioByteString.1
            private final ByteBuffer buf;

            {
                this.buf = NioByteString.this.buffer.slice();
            }

            @Override // java.io.InputStream
            public void mark(int readlimit) {
                this.buf.mark();
            }

            @Override // java.io.InputStream
            public boolean markSupported() {
                return true;
            }

            @Override // java.io.InputStream
            public void reset() throws IOException {
                try {
                    this.buf.reset();
                } catch (InvalidMarkException e) {
                    throw new IOException(e);
                }
            }

            @Override // java.io.InputStream
            public int available() throws IOException {
                return this.buf.remaining();
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                return this.buf.get() & 255;
            }

            @Override // java.io.InputStream
            public int read(byte[] bytes, int off, int len) throws IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                int len2 = Math.min(len, this.buf.remaining());
                this.buf.get(bytes, off, len2);
                return len2;
            }
        };
    }

    @Override // com.android.framework.protobuf.ByteString
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(this.buffer, true);
    }

    private ByteBuffer slice(int beginIndex, int endIndex) {
        if (beginIndex < this.buffer.position() || endIndex > this.buffer.limit() || beginIndex > endIndex) {
            throw new IllegalArgumentException(String.format("Invalid indices [%d, %d]", Integer.valueOf(beginIndex), Integer.valueOf(endIndex)));
        }
        ByteBuffer slice = this.buffer.slice();
        slice.position(beginIndex - this.buffer.position());
        slice.limit(endIndex - this.buffer.position());
        return slice;
    }
}
