package com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.BaseSessionData;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.ImExtension;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.ImMessageParam;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class SlmMessageIncoming extends Table {
    public static SlmMessageIncoming getRootAsSlmMessageIncoming(ByteBuffer byteBuffer) {
        return getRootAsSlmMessageIncoming(byteBuffer, new SlmMessageIncoming());
    }

    public static SlmMessageIncoming getRootAsSlmMessageIncoming(ByteBuffer byteBuffer, SlmMessageIncoming slmMessageIncoming) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return slmMessageIncoming.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public SlmMessageIncoming __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public BaseSessionData sessionData() {
        return sessionData(new BaseSessionData());
    }

    public BaseSessionData sessionData(BaseSessionData baseSessionData) {
        int __offset = __offset(4);
        if (__offset != 0) {
            return baseSessionData.__assign(__indirect(__offset + this.bb_pos), this.bb);
        }
        return null;
    }

    public ImMessageParam msg() {
        return msg(new ImMessageParam());
    }

    public ImMessageParam msg(ImMessageParam imMessageParam) {
        int __offset = __offset(6);
        if (__offset != 0) {
            return imMessageParam.__assign(__indirect(__offset + this.bb_pos), this.bb);
        }
        return null;
    }

    public long userHandle() {
        if (__offset(8) != 0) {
            return this.bb.getInt(r0 + this.bb_pos) & 4294967295L;
        }
        return 0L;
    }

    public ImExtension extension() {
        return extension(new ImExtension());
    }

    public ImExtension extension(ImExtension imExtension) {
        int __offset = __offset(10);
        if (__offset != 0) {
            return imExtension.__assign(__indirect(__offset + this.bb_pos), this.bb);
        }
        return null;
    }

    public boolean isLmm() {
        int __offset = __offset(12);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == 0) ? false : true;
    }

    public static int createSlmMessageIncoming(FlatBufferBuilder flatBufferBuilder, int i, int i2, long j, int i3, boolean z) {
        flatBufferBuilder.startObject(5);
        addExtension(flatBufferBuilder, i3);
        addUserHandle(flatBufferBuilder, j);
        addMsg(flatBufferBuilder, i2);
        addSessionData(flatBufferBuilder, i);
        addIsLmm(flatBufferBuilder, z);
        return endSlmMessageIncoming(flatBufferBuilder);
    }

    public static void startSlmMessageIncoming(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(5);
    }

    public static void addSessionData(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addMsg(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addUserHandle(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addInt(2, (int) j, 0);
    }

    public static void addExtension(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static void addIsLmm(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(4, z, false);
    }

    public static int endSlmMessageIncoming(FlatBufferBuilder flatBufferBuilder) {
        int endObject = flatBufferBuilder.endObject();
        flatBufferBuilder.required(endObject, 4);
        flatBufferBuilder.required(endObject, 6);
        return endObject;
    }
}
