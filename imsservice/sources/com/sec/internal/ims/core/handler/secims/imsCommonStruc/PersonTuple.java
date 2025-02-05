package com.sec.internal.ims.core.handler.secims.imsCommonStruc;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class PersonTuple extends Table {
    public static PersonTuple getRootAsPersonTuple(ByteBuffer byteBuffer) {
        return getRootAsPersonTuple(byteBuffer, new PersonTuple());
    }

    public static PersonTuple getRootAsPersonTuple(ByteBuffer byteBuffer, PersonTuple personTuple) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return personTuple.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public PersonTuple __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public String statusIcon() {
        int __offset = __offset(4);
        if (__offset != 0) {
            return __string(__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer statusIconAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public Element extensions(int i) {
        return extensions(new Element(), i);
    }

    public Element extensions(Element element, int i) {
        int __offset = __offset(6);
        if (__offset != 0) {
            return element.__assign(__indirect(__vector(__offset) + (i * 4)), this.bb);
        }
        return null;
    }

    public int extensionsLength() {
        int __offset = __offset(6);
        if (__offset != 0) {
            return __vector_len(__offset);
        }
        return 0;
    }

    public Element notes(int i) {
        return notes(new Element(), i);
    }

    public Element notes(Element element, int i) {
        int __offset = __offset(8);
        if (__offset != 0) {
            return element.__assign(__indirect(__vector(__offset) + (i * 4)), this.bb);
        }
        return null;
    }

    public int notesLength() {
        int __offset = __offset(8);
        if (__offset != 0) {
            return __vector_len(__offset);
        }
        return 0;
    }

    public String timestamp() {
        int __offset = __offset(10);
        if (__offset != 0) {
            return __string(__offset + this.bb_pos);
        }
        return null;
    }

    public ByteBuffer timestampAsByteBuffer() {
        return __vector_as_bytebuffer(10, 1);
    }

    public static int createPersonTuple(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4) {
        flatBufferBuilder.startObject(4);
        addTimestamp(flatBufferBuilder, i4);
        addNotes(flatBufferBuilder, i3);
        addExtensions(flatBufferBuilder, i2);
        addStatusIcon(flatBufferBuilder, i);
        return endPersonTuple(flatBufferBuilder);
    }

    public static void startPersonTuple(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(4);
    }

    public static void addStatusIcon(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addExtensions(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static int createExtensionsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static void startExtensionsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void addNotes(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static int createNotesVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static void startNotesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void addTimestamp(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static int endPersonTuple(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }
}
