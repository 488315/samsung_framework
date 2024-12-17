package com.android.server.usb.descriptors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbACAudioControlEndpoint extends UsbACEndpoint {
    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        byteStream.getByte();
        byteStream.getByte();
        byteStream.unpackUsbShort();
        byteStream.getByte();
        return this.mLength;
    }
}
