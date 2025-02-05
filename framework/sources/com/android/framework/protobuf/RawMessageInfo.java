package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes3.dex */
final class RawMessageInfo implements MessageInfo {
    private final MessageLite defaultInstance;
    private final int flags;
    private final String info;
    private final Object[] objects;

    RawMessageInfo(MessageLite defaultInstance, String info, Object[] objects) {
        this.defaultInstance = defaultInstance;
        this.info = info;
        this.objects = objects;
        int position = 0 + 1;
        int value = info.charAt(0);
        if (value < 55296) {
            this.flags = value;
            return;
        }
        int result = value & 8191;
        int shift = 13;
        while (true) {
            int position2 = position + 1;
            int value2 = info.charAt(position);
            if (value2 >= 55296) {
                result |= (value2 & 8191) << shift;
                shift += 13;
                position = position2;
            } else {
                this.flags = (value2 << shift) | result;
                return;
            }
        }
    }

    String getStringInfo() {
        return this.info;
    }

    Object[] getObjects() {
        return this.objects;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public ProtoSyntax getSyntax() {
        return (this.flags & 1) == 1 ? ProtoSyntax.PROTO2 : ProtoSyntax.PROTO3;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public boolean isMessageSetWireFormat() {
        return (this.flags & 2) == 2;
    }
}
