package com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class RequestAcceptImSession extends Table {
    public static RequestAcceptImSession getRootAsRequestAcceptImSession(ByteBuffer byteBuffer) {
        return getRootAsRequestAcceptImSession(byteBuffer, new RequestAcceptImSession());
    }

    public static RequestAcceptImSession getRootAsRequestAcceptImSession(ByteBuffer byteBuffer, RequestAcceptImSession requestAcceptImSession) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return requestAcceptImSession.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public RequestAcceptImSession __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public long sessionId() {
        if (__offset(4) != 0) {
            return this.bb.getInt(r0 + this.bb_pos) & 4294967295L;
        }
        return 0L;
    }

    public String userAlias() {
        int __offset = __offset(6);
        if (__offset != 0) {
            return __string(__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer userAliasAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public static int createRequestAcceptImSession(FlatBufferBuilder flatBufferBuilder, long j, int i) {
        flatBufferBuilder.startObject(2);
        addUserAlias(flatBufferBuilder, i);
        addSessionId(flatBufferBuilder, j);
        return endRequestAcceptImSession(flatBufferBuilder);
    }

    public static void startRequestAcceptImSession(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public static void addSessionId(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addInt(0, (int) j, 0);
    }

    public static void addUserAlias(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static int endRequestAcceptImSession(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
