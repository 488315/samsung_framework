package com.android.framework.protobuf;

import java.io.IOException;

@CheckReturnValue
/* loaded from: classes4.dex */
class UnknownFieldSetLiteSchema extends UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> {
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public boolean shouldDiscardUnknownFields(Reader reader) {
        return false;
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public UnknownFieldSetLite newBuilder() {
        return UnknownFieldSetLite.newInstance();
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addVarint(UnknownFieldSetLite fields, int number, long value) {
        fields.storeField(WireFormat.makeTag(number, 0), Long.valueOf(value));
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addFixed32(UnknownFieldSetLite fields, int number, int value) {
        fields.storeField(WireFormat.makeTag(number, 5), Integer.valueOf(value));
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addFixed64(UnknownFieldSetLite fields, int number, long value) {
        fields.storeField(WireFormat.makeTag(number, 1), Long.valueOf(value));
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addLengthDelimited(UnknownFieldSetLite fields, int number, ByteString value) {
        fields.storeField(WireFormat.makeTag(number, 2), value);
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addGroup(UnknownFieldSetLite fields, int number, UnknownFieldSetLite subFieldSet) {
        fields.storeField(WireFormat.makeTag(number, 3), subFieldSet);
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public UnknownFieldSetLite toImmutable(UnknownFieldSetLite fields) {
        fields.makeImmutable();
        return fields;
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void setToMessage(Object message, UnknownFieldSetLite fields) {
        ((GeneratedMessageLite) message).unknownFields = fields;
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public UnknownFieldSetLite getFromMessage(Object message) {
        return ((GeneratedMessageLite) message).unknownFields;
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public UnknownFieldSetLite getBuilderFromMessage(Object message) {
        UnknownFieldSetLite unknownFields = getFromMessage(message);
        if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            UnknownFieldSetLite unknownFields2 = UnknownFieldSetLite.newInstance();
            setToMessage(message, unknownFields2);
            return unknownFields2;
        }
        return unknownFields;
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void setBuilderToMessage(Object message, UnknownFieldSetLite fields) {
        setToMessage(message, fields);
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void makeImmutable(Object message) {
        getFromMessage(message).makeImmutable();
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void writeTo(UnknownFieldSetLite fields, Writer writer) throws IOException {
        fields.writeTo(writer);
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void writeAsMessageSetTo(UnknownFieldSetLite fields, Writer writer) throws IOException {
        fields.writeAsMessageSetTo(writer);
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public UnknownFieldSetLite merge(UnknownFieldSetLite target, UnknownFieldSetLite source) {
        if (UnknownFieldSetLite.getDefaultInstance().equals(source)) {
            return target;
        }
        if (UnknownFieldSetLite.getDefaultInstance().equals(target)) {
            return UnknownFieldSetLite.mutableCopyOf(target, source);
        }
        return target.mergeFrom(source);
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public int getSerializedSize(UnknownFieldSetLite unknowns) {
        return unknowns.getSerializedSize();
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public int getSerializedSizeAsMessageSet(UnknownFieldSetLite unknowns) {
        return unknowns.getSerializedSizeAsMessageSet();
    }
}
