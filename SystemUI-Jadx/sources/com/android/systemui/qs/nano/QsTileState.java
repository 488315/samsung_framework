package com.android.systemui.qs.nano;

import com.android.systemui.util.nano.ComponentNameProto;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class QsTileState extends MessageNano {
    private static volatile QsTileState[] _emptyArray;
    private Object identifier_;
    private Object optionalBooleanState_;
    private Object optionalLabel_;
    private Object optionalSecondaryLabel_;
    public int state;
    private int identifierCase_ = 0;
    private int optionalBooleanStateCase_ = 0;
    private int optionalLabelCase_ = 0;
    private int optionalSecondaryLabelCase_ = 0;

    public QsTileState() {
        clear();
    }

    public static QsTileState[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new QsTileState[0];
                }
            }
        }
        return _emptyArray;
    }

    public QsTileState clear() {
        this.state = 0;
        clearIdentifier();
        clearOptionalBooleanState();
        clearOptionalLabel();
        clearOptionalSecondaryLabel();
        this.cachedSize = -1;
        return this;
    }

    public QsTileState clearIdentifier() {
        this.identifierCase_ = 0;
        this.identifier_ = null;
        return this;
    }

    public QsTileState clearOptionalBooleanState() {
        this.optionalBooleanStateCase_ = 0;
        this.optionalBooleanState_ = null;
        return this;
    }

    public QsTileState clearOptionalLabel() {
        this.optionalLabelCase_ = 0;
        this.optionalLabel_ = null;
        return this;
    }

    public QsTileState clearOptionalSecondaryLabel() {
        this.optionalSecondaryLabelCase_ = 0;
        this.optionalSecondaryLabel_ = null;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int i = 0;
        if (this.identifierCase_ == 1) {
            i = 0 + CodedOutputByteBufferNano.computeStringSize(1, (String) this.identifier_);
        }
        if (this.identifierCase_ == 2) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.identifier_);
        }
        int i2 = this.state;
        if (i2 != 0) {
            i += CodedOutputByteBufferNano.computeInt32Size(3, i2);
        }
        if (this.optionalBooleanStateCase_ == 4) {
            ((Boolean) this.optionalBooleanState_).booleanValue();
            i += CodedOutputByteBufferNano.computeTagSize(4) + 1;
        }
        if (this.optionalLabelCase_ == 5) {
            i += CodedOutputByteBufferNano.computeStringSize(5, (String) this.optionalLabel_);
        }
        if (this.optionalSecondaryLabelCase_ == 6) {
            return i + CodedOutputByteBufferNano.computeStringSize(6, (String) this.optionalSecondaryLabel_);
        }
        return i;
    }

    public QsTileState setBooleanState(boolean z) {
        this.optionalBooleanStateCase_ = 4;
        this.optionalBooleanState_ = Boolean.valueOf(z);
        return this;
    }

    public QsTileState setComponentName(ComponentNameProto componentNameProto) {
        componentNameProto.getClass();
        this.identifierCase_ = 2;
        this.identifier_ = componentNameProto;
        return this;
    }

    public QsTileState setLabel(String str) {
        this.optionalLabelCase_ = 5;
        this.optionalLabel_ = str;
        return this;
    }

    public QsTileState setSecondaryLabel(String str) {
        this.optionalSecondaryLabelCase_ = 6;
        this.optionalSecondaryLabel_ = str;
        return this;
    }

    public QsTileState setSpec(String str) {
        this.identifierCase_ = 1;
        this.identifier_ = str;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        if (this.identifierCase_ == 1) {
            codedOutputByteBufferNano.writeString(1, (String) this.identifier_);
        }
        if (this.identifierCase_ == 2) {
            codedOutputByteBufferNano.writeMessage(2, (MessageNano) this.identifier_);
        }
        int i = this.state;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(3, i);
        }
        if (this.optionalBooleanStateCase_ == 4) {
            boolean booleanValue = ((Boolean) this.optionalBooleanState_).booleanValue();
            codedOutputByteBufferNano.writeTag(4, 0);
            codedOutputByteBufferNano.writeRawByte(booleanValue ? 1 : 0);
        }
        if (this.optionalLabelCase_ == 5) {
            codedOutputByteBufferNano.writeString(5, (String) this.optionalLabel_);
        }
        if (this.optionalSecondaryLabelCase_ == 6) {
            codedOutputByteBufferNano.writeString(6, (String) this.optionalSecondaryLabel_);
        }
    }
}
