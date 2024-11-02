package com.google.protobuf;

import com.google.protobuf.ByteString;
import java.util.Arrays;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class CodedInputStream {
    public int recursionDepth;
    public final int recursionLimit;
    public CodedInputStreamReader wrapper;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class ArrayDecoder extends CodedInputStream {
        public final byte[] buffer;
        public int bufferSizeAfterLimit;
        public int currentLimit;
        public final boolean immutable;
        public int lastTag;
        public int limit;
        public int pos;
        public final int startPos;

        @Override // com.google.protobuf.CodedInputStream
        public final void checkLastTagWas(int i) {
            if (this.lastTag == i) {
            } else {
                throw new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int getTotalBytesRead() {
            return this.pos - this.startPos;
        }

        @Override // com.google.protobuf.CodedInputStream
        public final boolean isAtEnd() {
            if (this.pos == this.limit) {
                return true;
            }
            return false;
        }

        @Override // com.google.protobuf.CodedInputStream
        public final void popLimit(int i) {
            this.currentLimit = i;
            int i2 = this.limit + this.bufferSizeAfterLimit;
            this.limit = i2;
            int i3 = i2 - this.startPos;
            if (i3 > i) {
                int i4 = i3 - i;
                this.bufferSizeAfterLimit = i4;
                this.limit = i2 - i4;
                return;
            }
            this.bufferSizeAfterLimit = 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int pushLimit(int i) {
            if (i >= 0) {
                int i2 = this.pos;
                int i3 = this.startPos;
                int i4 = (i2 - i3) + i;
                if (i4 >= 0) {
                    int i5 = this.currentLimit;
                    if (i4 <= i5) {
                        this.currentLimit = i4;
                        int i6 = this.limit + this.bufferSizeAfterLimit;
                        this.limit = i6;
                        int i7 = i6 - i3;
                        if (i7 > i4) {
                            int i8 = i7 - i4;
                            this.bufferSizeAfterLimit = i8;
                            this.limit = i6 - i8;
                        } else {
                            this.bufferSizeAfterLimit = 0;
                        }
                        return i5;
                    }
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                throw InvalidProtocolBufferException.parseFailure();
            }
            throw InvalidProtocolBufferException.negativeSize();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final boolean readBool() {
            if (readRawVarint64() != 0) {
                return true;
            }
            return false;
        }

        @Override // com.google.protobuf.CodedInputStream
        public final ByteString readBytes() {
            byte[] bArr;
            int readRawVarint32 = readRawVarint32();
            byte[] bArr2 = this.buffer;
            if (readRawVarint32 > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (readRawVarint32 <= i - i2) {
                    ByteString copyFrom = ByteString.copyFrom(bArr2, i2, readRawVarint32);
                    this.pos += readRawVarint32;
                    return copyFrom;
                }
            }
            if (readRawVarint32 == 0) {
                return ByteString.EMPTY;
            }
            if (readRawVarint32 > 0) {
                int i3 = this.limit;
                int i4 = this.pos;
                if (readRawVarint32 <= i3 - i4) {
                    int i5 = readRawVarint32 + i4;
                    this.pos = i5;
                    bArr = Arrays.copyOfRange(bArr2, i4, i5);
                    ByteString byteString = ByteString.EMPTY;
                    return new ByteString.LiteralByteString(bArr);
                }
            }
            if (readRawVarint32 <= 0) {
                if (readRawVarint32 == 0) {
                    bArr = Internal.EMPTY_BYTE_ARRAY;
                    ByteString byteString2 = ByteString.EMPTY;
                    return new ByteString.LiteralByteString(bArr);
                }
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final double readDouble() {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int readEnum() {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int readFixed32() {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final long readFixed64() {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final float readFloat() {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int readInt32() {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final long readInt64() {
            return readRawVarint64();
        }

        public final int readRawLittleEndian32() {
            int i = this.pos;
            if (this.limit - i >= 4) {
                this.pos = i + 4;
                byte[] bArr = this.buffer;
                return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        public final long readRawLittleEndian64() {
            int i = this.pos;
            if (this.limit - i >= 8) {
                this.pos = i + 8;
                byte[] bArr = this.buffer;
                return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48) | ((bArr[i + 7] & 255) << 56);
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
        
            if (r3[r2] < 0) goto L34;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int readRawVarint32() {
            /*
                r5 = this;
                int r0 = r5.pos
                int r1 = r5.limit
                if (r1 != r0) goto L7
                goto L6a
            L7:
                int r2 = r0 + 1
                byte[] r3 = r5.buffer
                r0 = r3[r0]
                if (r0 < 0) goto L12
                r5.pos = r2
                return r0
            L12:
                int r1 = r1 - r2
                r4 = 9
                if (r1 >= r4) goto L18
                goto L6a
            L18:
                int r1 = r2 + 1
                r2 = r3[r2]
                int r2 = r2 << 7
                r0 = r0 ^ r2
                if (r0 >= 0) goto L24
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L70
            L24:
                int r2 = r1 + 1
                r1 = r3[r1]
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L31
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            L2f:
                r1 = r2
                goto L70
            L31:
                int r1 = r2 + 1
                r2 = r3[r2]
                int r2 = r2 << 21
                r0 = r0 ^ r2
                if (r0 >= 0) goto L3f
                r2 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L70
            L3f:
                int r2 = r1 + 1
                r1 = r3[r1]
                int r4 = r1 << 28
                r0 = r0 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r4
                if (r1 >= 0) goto L2f
                int r1 = r2 + 1
                r2 = r3[r2]
                if (r2 >= 0) goto L70
                int r2 = r1 + 1
                r1 = r3[r1]
                if (r1 >= 0) goto L2f
                int r1 = r2 + 1
                r2 = r3[r2]
                if (r2 >= 0) goto L70
                int r2 = r1 + 1
                r1 = r3[r1]
                if (r1 >= 0) goto L2f
                int r1 = r2 + 1
                r2 = r3[r2]
                if (r2 >= 0) goto L70
            L6a:
                long r0 = r5.readRawVarint64SlowPath()
                int r5 = (int) r0
                return r5
            L70:
                r5.pos = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.ArrayDecoder.readRawVarint32():int");
        }

        public final long readRawVarint64() {
            long j;
            long j2;
            long j3;
            int i;
            int i2 = this.pos;
            int i3 = this.limit;
            if (i3 != i2) {
                int i4 = i2 + 1;
                byte[] bArr = this.buffer;
                byte b = bArr[i2];
                if (b >= 0) {
                    this.pos = i4;
                    return b;
                }
                if (i3 - i4 >= 9) {
                    int i5 = i4 + 1;
                    int i6 = b ^ (bArr[i4] << 7);
                    if (i6 < 0) {
                        i = i6 ^ (-128);
                    } else {
                        int i7 = i5 + 1;
                        int i8 = i6 ^ (bArr[i5] << 14);
                        if (i8 >= 0) {
                            j = i8 ^ 16256;
                        } else {
                            i5 = i7 + 1;
                            int i9 = i8 ^ (bArr[i7] << 21);
                            if (i9 < 0) {
                                i = i9 ^ (-2080896);
                            } else {
                                long j4 = i9;
                                int i10 = i5 + 1;
                                long j5 = (bArr[i5] << 28) ^ j4;
                                if (j5 >= 0) {
                                    j2 = j5 ^ 266354560;
                                    i5 = i10;
                                } else {
                                    int i11 = i10 + 1;
                                    long j6 = j5 ^ (bArr[i10] << 35);
                                    if (j6 < 0) {
                                        j3 = -34093383808L;
                                    } else {
                                        i7 = i11 + 1;
                                        long j7 = j6 ^ (bArr[i11] << 42);
                                        if (j7 >= 0) {
                                            j = j7 ^ 4363953127296L;
                                        } else {
                                            i11 = i7 + 1;
                                            j6 = j7 ^ (bArr[i7] << 49);
                                            if (j6 < 0) {
                                                j3 = -558586000294016L;
                                            } else {
                                                i7 = i11 + 1;
                                                j = (j6 ^ (bArr[i11] << 56)) ^ 71499008037633920L;
                                                if (j < 0) {
                                                    i11 = i7 + 1;
                                                    if (bArr[i7] >= 0) {
                                                        j2 = j;
                                                        i5 = i11;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    j2 = j3 ^ j6;
                                    i5 = i11;
                                }
                                this.pos = i5;
                                return j2;
                            }
                        }
                        i5 = i7;
                        j2 = j;
                        this.pos = i5;
                        return j2;
                    }
                    j2 = i;
                    this.pos = i5;
                    return j2;
                }
            }
            return readRawVarint64SlowPath();
        }

        public final long readRawVarint64SlowPath() {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                int i2 = this.pos;
                if (i2 != this.limit) {
                    this.pos = i2 + 1;
                    j |= (r3 & Byte.MAX_VALUE) << i;
                    if ((this.buffer[i2] & 128) == 0) {
                        return j;
                    }
                } else {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            }
            throw new InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int readSFixed32() {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final long readSFixed64() {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int readSInt32() {
            return CodedInputStream.decodeZigZag32(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public final long readSInt64() {
            return CodedInputStream.decodeZigZag64(readRawVarint64());
        }

        @Override // com.google.protobuf.CodedInputStream
        public final String readString() {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (readRawVarint32 <= i - i2) {
                    String str = new String(this.buffer, i2, readRawVarint32, Internal.UTF_8);
                    this.pos += readRawVarint32;
                    return str;
                }
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final String readStringRequireUtf8() {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                int i = this.limit;
                int i2 = this.pos;
                if (readRawVarint32 <= i - i2) {
                    String decodeUtf8 = Utf8.processor.decodeUtf8(this.buffer, i2, readRawVarint32);
                    this.pos += readRawVarint32;
                    return decodeUtf8;
                }
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int readTag() {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            int readRawVarint32 = readRawVarint32();
            this.lastTag = readRawVarint32;
            if ((readRawVarint32 >>> 3) != 0) {
                return readRawVarint32;
            }
            throw new InvalidProtocolBufferException("Protocol message contained an invalid tag (zero).");
        }

        @Override // com.google.protobuf.CodedInputStream
        public final int readUInt32() {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final long readUInt64() {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public final boolean skipField(int i) {
            int readTag;
            int i2 = i & 7;
            int i3 = 0;
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 != 3) {
                            if (i2 == 4) {
                                return false;
                            }
                            if (i2 == 5) {
                                skipRawBytes(4);
                                return true;
                            }
                            throw InvalidProtocolBufferException.invalidWireType();
                        }
                        do {
                            readTag = readTag();
                            if (readTag == 0) {
                                break;
                            }
                        } while (skipField(readTag));
                        checkLastTagWas(((i >>> 3) << 3) | 4);
                        return true;
                    }
                    skipRawBytes(readRawVarint32());
                    return true;
                }
                skipRawBytes(8);
                return true;
            }
            int i4 = this.limit - this.pos;
            byte[] bArr = this.buffer;
            if (i4 >= 10) {
                while (i3 < 10) {
                    int i5 = this.pos;
                    this.pos = i5 + 1;
                    if (bArr[i5] < 0) {
                        i3++;
                    }
                }
                throw new InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
            }
            while (i3 < 10) {
                int i6 = this.pos;
                if (i6 != this.limit) {
                    this.pos = i6 + 1;
                    if (bArr[i6] < 0) {
                        i3++;
                    }
                } else {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
            }
            throw new InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
            return true;
        }

        public final void skipRawBytes(int i) {
            if (i >= 0) {
                int i2 = this.limit;
                int i3 = this.pos;
                if (i <= i2 - i3) {
                    this.pos = i3 + i;
                    return;
                }
            }
            if (i < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }

        private ArrayDecoder(byte[] bArr, int i, int i2, boolean z) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = bArr;
            this.limit = i2 + i;
            this.pos = i;
            this.startPos = i;
            this.immutable = z;
        }
    }

    public static int decodeZigZag32(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long decodeZigZag64(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public abstract void checkLastTagWas(int i);

    public abstract int getTotalBytesRead();

    public abstract boolean isAtEnd();

    public abstract void popLimit(int i);

    public abstract int pushLimit(int i);

    public abstract boolean readBool();

    public abstract ByteString readBytes();

    public abstract double readDouble();

    public abstract int readEnum();

    public abstract int readFixed32();

    public abstract long readFixed64();

    public abstract float readFloat();

    public abstract int readInt32();

    public abstract long readInt64();

    public abstract int readSFixed32();

    public abstract long readSFixed64();

    public abstract int readSInt32();

    public abstract long readSInt64();

    public abstract String readString();

    public abstract String readStringRequireUtf8();

    public abstract int readTag();

    public abstract int readUInt32();

    public abstract long readUInt64();

    public abstract boolean skipField(int i);

    private CodedInputStream() {
        this.recursionLimit = 100;
    }
}
