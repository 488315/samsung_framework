package com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class RequestVshStartSession extends Table {
    public static RequestVshStartSession getRootAsRequestVshStartSession(ByteBuffer byteBuffer) {
        return getRootAsRequestVshStartSession(byteBuffer, new RequestVshStartSession());
    }

    public static RequestVshStartSession getRootAsRequestVshStartSession(ByteBuffer byteBuffer, RequestVshStartSession requestVshStartSession) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return requestVshStartSession.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public RequestVshStartSession __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public long registrationHandle() {
        if (__offset(4) != 0) {
            return this.bb.getInt(r0 + this.bb_pos) & 4294967295L;
        }
        return 0L;
    }

    public String remoteUri() {
        int __offset = __offset(6);
        if (__offset != 0) {
            return __string(__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer remoteUriAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public String filePath() {
        int __offset = __offset(8);
        if (__offset != 0) {
            return __string(__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer filePathAsByteBuffer() {
        return __vector_as_bytebuffer(8, 1);
    }

    public long size() {
        if (__offset(10) != 0) {
            return this.bb.getInt(r0 + this.bb_pos) & 4294967295L;
        }
        return 0L;
    }

    public String contentType() {
        int __offset = __offset(12);
        if (__offset != 0) {
            return __string(__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer contentTypeAsByteBuffer() {
        return __vector_as_bytebuffer(12, 1);
    }

    public static int createRequestVshStartSession(FlatBufferBuilder flatBufferBuilder, long j, int i, int i2, long j2, int i3) {
        flatBufferBuilder.startObject(5);
        addContentType(flatBufferBuilder, i3);
        addSize(flatBufferBuilder, j2);
        addFilePath(flatBufferBuilder, i2);
        addRemoteUri(flatBufferBuilder, i);
        addRegistrationHandle(flatBufferBuilder, j);
        return endRequestVshStartSession(flatBufferBuilder);
    }

    public static void startRequestVshStartSession(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(5);
    }

    public static void addRegistrationHandle(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addInt(0, (int) j, 0);
    }

    public static void addRemoteUri(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addFilePath(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addSize(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addInt(3, (int) j, 0);
    }

    public static void addContentType(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(4, i, 0);
    }

    public static int endRequestVshStartSession(FlatBufferBuilder flatBufferBuilder) {
        int endObject = flatBufferBuilder.endObject();
        flatBufferBuilder.required(endObject, 6);
        return endObject;
    }
}
