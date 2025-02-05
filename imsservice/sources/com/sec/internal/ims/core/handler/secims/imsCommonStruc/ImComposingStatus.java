package com.sec.internal.ims.core.handler.secims.imsCommonStruc;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class ImComposingStatus extends Table {
    public static ImComposingStatus getRootAsImComposingStatus(ByteBuffer byteBuffer) {
        return getRootAsImComposingStatus(byteBuffer, new ImComposingStatus());
    }

    public static ImComposingStatus getRootAsImComposingStatus(ByteBuffer byteBuffer, ImComposingStatus imComposingStatus) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return imComposingStatus.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public ImComposingStatus __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public String contentType() {
        int __offset = __offset(4);
        if (__offset != 0) {
            return __string(__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer contentTypeAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public long interval() {
        if (__offset(6) != 0) {
            return this.bb.getInt(r0 + this.bb_pos) & 4294967295L;
        }
        return 0L;
    }

    public boolean isActive() {
        int __offset = __offset(8);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == 0) ? false : true;
    }

    public static int createImComposingStatus(FlatBufferBuilder flatBufferBuilder, int i, long j, boolean z) {
        flatBufferBuilder.startObject(3);
        addInterval(flatBufferBuilder, j);
        addContentType(flatBufferBuilder, i);
        addIsActive(flatBufferBuilder, z);
        return endImComposingStatus(flatBufferBuilder);
    }

    public static void startImComposingStatus(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(3);
    }

    public static void addContentType(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addInterval(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addInt(1, (int) j, 0);
    }

    public static void addIsActive(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(2, z, false);
    }

    public static int endImComposingStatus(FlatBufferBuilder flatBufferBuilder) {
        int endObject = flatBufferBuilder.endObject();
        flatBufferBuilder.required(endObject, 4);
        return endObject;
    }
}
