package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractMessageLite;
import com.android.framework.protobuf.ArrayDecoders;
import com.android.framework.protobuf.FieldSet;
import com.android.framework.protobuf.GeneratedMessageLite;
import com.android.framework.protobuf.GeneratedMessageLite.Builder;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite<MessageType, BuilderType> {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Object, GeneratedMessageLite<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends MessageLiteOrBuilder {
        <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i);

        <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite);

        <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite);
    }

    public enum MethodToInvoke {
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        BUILD_MESSAGE_INFO,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    protected abstract Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2);

    boolean isMutable() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    void markImmutable() {
        this.memoizedSerializedSize &= Integer.MAX_VALUE;
    }

    int getMemoizedHashCode() {
        return this.memoizedHashCode;
    }

    void setMemoizedHashCode(int value) {
        this.memoizedHashCode = value;
    }

    void clearMemoizedHashCode() {
        this.memoizedHashCode = 0;
    }

    boolean hashCodeIsNotMemoized() {
        return getMemoizedHashCode() == 0;
    }

    @Override // com.android.framework.protobuf.MessageLite
    public final Parser<MessageType> getParserForType() {
        return (Parser) dynamicMethod(MethodToInvoke.GET_PARSER);
    }

    @Override // com.android.framework.protobuf.MessageLiteOrBuilder
    public final MessageType getDefaultInstanceForType() {
        return (MessageType) dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
    }

    @Override // com.android.framework.protobuf.MessageLite
    public final BuilderType newBuilderForType() {
        return (BuilderType) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    MessageType newMutableInstance() {
        return (MessageType) dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }

    public String toString() {
        return MessageLiteToString.toString(this, super.toString());
    }

    public int hashCode() {
        if (isMutable()) {
            return computeHashCode();
        }
        if (hashCodeIsNotMemoized()) {
            setMemoizedHashCode(computeHashCode());
        }
        return getMemoizedHashCode();
    }

    int computeHashCode() {
        return Protobuf.getInstance().schemaFor((Protobuf) this).hashCode(this);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return Protobuf.getInstance().schemaFor((Protobuf) this).equals(this, (GeneratedMessageLite) other);
    }

    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.newInstance();
        }
    }

    protected boolean parseUnknownField(int tag, CodedInputStream input) throws IOException {
        if (WireFormat.getTagWireType(tag) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.mergeFieldFrom(tag, input);
    }

    protected void mergeVarintField(int tag, int value) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeVarintField(tag, value);
    }

    protected void mergeLengthDelimitedField(int fieldNumber, ByteString value) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeLengthDelimitedField(fieldNumber, value);
    }

    protected void makeImmutable() {
        Protobuf.getInstance().schemaFor((Protobuf) this).makeImmutable(this);
        markImmutable();
    }

    protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder() {
        return (BuilderType) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder(MessageType messagetype) {
        return (BuilderType) createBuilder().mergeFrom(messagetype);
    }

    @Override // com.android.framework.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        return isInitialized(this, Boolean.TRUE.booleanValue());
    }

    @Override // com.android.framework.protobuf.MessageLite
    public final BuilderType toBuilder() {
        return (BuilderType) ((Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER)).mergeFrom((Builder) this);
    }

    protected Object dynamicMethod(MethodToInvoke method, Object arg0) {
        return dynamicMethod(method, arg0, null);
    }

    protected Object dynamicMethod(MethodToInvoke method) {
        return dynamicMethod(method, null, null);
    }

    void clearMemoizedSerializedSize() {
        setMemoizedSerializedSize(Integer.MAX_VALUE);
    }

    @Override // com.android.framework.protobuf.AbstractMessageLite
    int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize & Integer.MAX_VALUE;
    }

    @Override // com.android.framework.protobuf.AbstractMessageLite
    void setMemoizedSerializedSize(int size) {
        if (size < 0) {
            throw new IllegalStateException("serialized size must be non-negative, was " + size);
        }
        this.memoizedSerializedSize = (this.memoizedSerializedSize & Integer.MIN_VALUE) | (Integer.MAX_VALUE & size);
    }

    @Override // com.android.framework.protobuf.MessageLite
    public void writeTo(CodedOutputStream output) throws IOException {
        Protobuf.getInstance().schemaFor((Protobuf) this).writeTo(this, CodedOutputStreamWriter.forCodedOutput(output));
    }

    @Override // com.android.framework.protobuf.AbstractMessageLite
    int getSerializedSize(Schema schema) {
        if (isMutable()) {
            int size = computeSerializedSize(schema);
            if (size < 0) {
                throw new IllegalStateException("serialized size must be non-negative, was " + size);
            }
            return size;
        }
        if (getMemoizedSerializedSize() != Integer.MAX_VALUE) {
            return getMemoizedSerializedSize();
        }
        int size2 = computeSerializedSize(schema);
        setMemoizedSerializedSize(size2);
        return size2;
    }

    @Override // com.android.framework.protobuf.MessageLite
    public int getSerializedSize() {
        return getSerializedSize(null);
    }

    private int computeSerializedSize(Schema<?> nullableSchema) {
        if (nullableSchema == null) {
            return Protobuf.getInstance().schemaFor((Protobuf) this).getSerializedSize(this);
        }
        return nullableSchema.getSerializedSize(this);
    }

    Object buildMessageInfo() throws Exception {
        return dynamicMethod(MethodToInvoke.BUILD_MESSAGE_INFO);
    }

    static <T extends GeneratedMessageLite<?, ?>> T getDefaultInstance(Class<T> cls) {
        GeneratedMessageLite<?, ?> generatedMessageLite = defaultInstanceMap.get(cls);
        if (generatedMessageLite == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                generatedMessageLite = defaultInstanceMap.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (generatedMessageLite == null) {
            generatedMessageLite = (T) ((GeneratedMessageLite) UnsafeUtil.allocateInstance(cls)).getDefaultInstanceForType();
            if (generatedMessageLite == null) {
                throw new IllegalStateException();
            }
            defaultInstanceMap.put(cls, generatedMessageLite);
        }
        return (T) generatedMessageLite;
    }

    protected static <T extends GeneratedMessageLite<?, ?>> void registerDefaultInstance(Class<T> clazz, T defaultInstance) {
        defaultInstance.markImmutable();
        defaultInstanceMap.put(clazz, defaultInstance);
    }

    protected static Object newMessageInfo(MessageLite defaultInstance, String info, Object[] objects) {
        return new RawMessageInfo(defaultInstance, info, objects);
    }

    protected final void mergeUnknownFields(UnknownFieldSetLite unknownFields) {
        this.unknownFields = UnknownFieldSetLite.mutableCopyOf(this.unknownFields, unknownFields);
    }

    public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite.Builder<MessageType, BuilderType> {
        private final MessageType defaultInstance;
        protected MessageType instance;

        protected Builder(MessageType defaultInstance) {
            this.defaultInstance = defaultInstance;
            if (defaultInstance.isMutable()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.instance = newMutableInstance();
        }

        private MessageType newMutableInstance() {
            return (MessageType) this.defaultInstance.newMutableInstance();
        }

        protected final void copyOnWrite() {
            if (!this.instance.isMutable()) {
                copyOnWriteInternal();
            }
        }

        protected void copyOnWriteInternal() {
            MessageType newInstance = newMutableInstance();
            mergeFromInstance(newInstance, this.instance);
            this.instance = newInstance;
        }

        @Override // com.android.framework.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return GeneratedMessageLite.isInitialized(this.instance, false);
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public final BuilderType clear() {
            if (this.defaultInstance.isMutable()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.instance = newMutableInstance();
            return this;
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public BuilderType mo7416clone() {
            BuilderType buildertype = (BuilderType) getDefaultInstanceForType().newBuilderForType();
            buildertype.instance = buildPartial();
            return buildertype;
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public MessageType buildPartial() {
            if (!this.instance.isMutable()) {
                return this.instance;
            }
            this.instance.makeImmutable();
            return this.instance;
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public final MessageType build() {
            MessageType result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder
        public BuilderType internalMergeFrom(MessageType message) {
            return mergeFrom((Builder<MessageType, BuilderType>) message);
        }

        public BuilderType mergeFrom(MessageType message) {
            if (getDefaultInstanceForType().equals(message)) {
                return this;
            }
            copyOnWrite();
            mergeFromInstance(this.instance, message);
            return this;
        }

        private static <MessageType> void mergeFromInstance(MessageType dest, MessageType src) {
            Protobuf.getInstance().schemaFor((Protobuf) dest).mergeFrom(dest, src);
        }

        @Override // com.android.framework.protobuf.MessageLiteOrBuilder
        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] input, int offset, int length, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            copyOnWrite();
            try {
                Protobuf.getInstance().schemaFor((Protobuf) this.instance).mergeFrom(this.instance, input, offset, offset + length, new ArrayDecoders.Registers(extensionRegistry));
                return this;
            } catch (InvalidProtocolBufferException e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            } catch (IndexOutOfBoundsException e3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] input, int offset, int length) throws InvalidProtocolBufferException {
            return mergeFrom(input, offset, length, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            copyOnWrite();
            try {
                Protobuf.getInstance().schemaFor((Protobuf) this.instance).mergeFrom(this.instance, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends GeneratedMessageLite<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected FieldSet<ExtensionDescriptor> extensions = FieldSet.emptySet();

        protected final void mergeExtensionFields(MessageType other) {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.m7417clone();
            }
            this.extensions.mergeFrom(other.extensions);
        }

        protected <MessageType extends MessageLite> boolean parseUnknownField(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            int fieldNumber = WireFormat.getTagFieldNumber(tag);
            return parseExtension(input, extensionRegistry, extensionRegistry.findLiteExtensionByNumber(defaultInstance, fieldNumber), tag, fieldNumber);
        }

        private boolean parseExtension(CodedInputStream input, ExtensionRegistryLite extensionRegistry, GeneratedExtension<?, ?> extension, int tag, int fieldNumber) throws IOException {
            Object value;
            MessageLite existingValue;
            int wireType = WireFormat.getTagWireType(tag);
            boolean unknown = false;
            boolean packed = false;
            boolean z = true;
            if (extension == null) {
                unknown = true;
            } else if (wireType == FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), false)) {
                packed = false;
            } else if (extension.descriptor.isRepeated && extension.descriptor.type.isPackable() && wireType == FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), true)) {
                packed = true;
            } else {
                unknown = true;
            }
            if (unknown) {
                return parseUnknownField(tag, input);
            }
            ensureExtensionsAreMutable();
            if (packed) {
                int length = input.readRawVarint32();
                int limit = input.pushLimit(length);
                if (extension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
                    while (input.getBytesUntilLimit() > 0) {
                        Object value2 = extension.descriptor.getEnumType().findValueByNumber(input.readEnum());
                        if (value2 == null) {
                            return z;
                        }
                        this.extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(value2));
                        z = true;
                    }
                } else {
                    while (input.getBytesUntilLimit() > 0) {
                        Object value3 = FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false);
                        this.extensions.addRepeatedField(extension.descriptor, value3);
                    }
                }
                input.popLimit(limit);
                return true;
            }
            switch (extension.descriptor.getLiteJavaType()) {
                case MESSAGE:
                    MessageLite.Builder subBuilder = null;
                    if (!extension.descriptor.isRepeated() && (existingValue = (MessageLite) this.extensions.getField(extension.descriptor)) != null) {
                        subBuilder = existingValue.toBuilder();
                    }
                    if (subBuilder == null) {
                        subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
                    }
                    if (extension.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
                        input.readGroup(extension.getNumber(), subBuilder, extensionRegistry);
                    } else {
                        input.readMessage(subBuilder, extensionRegistry);
                    }
                    value = subBuilder.build();
                    break;
                case ENUM:
                    int rawValue = input.readEnum();
                    value = extension.descriptor.getEnumType().findValueByNumber(rawValue);
                    if (value != null) {
                        break;
                    } else {
                        mergeVarintField(fieldNumber, rawValue);
                        return true;
                    }
                default:
                    value = FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false);
                    break;
            }
            if (extension.descriptor.isRepeated()) {
                this.extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(value));
                return true;
            }
            this.extensions.setField(extension.descriptor, extension.singularToFieldSetType(value));
            return true;
        }

        protected <MessageType extends MessageLite> boolean parseUnknownFieldAsMessageSet(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            if (tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
                mergeMessageSetExtensionFromCodedStream(defaultInstance, input, extensionRegistry);
                return true;
            }
            int wireType = WireFormat.getTagWireType(tag);
            if (wireType == 2) {
                return parseUnknownField(defaultInstance, input, extensionRegistry, tag);
            }
            return input.skipField(tag);
        }

        private <MessageType extends MessageLite> void mergeMessageSetExtensionFromCodedStream(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            int typeId = 0;
            ByteString rawBytes = null;
            GeneratedExtension<?, ?> extension = null;
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    typeId = input.readUInt32();
                    if (typeId != 0) {
                        extension = extensionRegistry.findLiteExtensionByNumber(defaultInstance, typeId);
                    }
                } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (typeId != 0 && extension != null) {
                        eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, typeId);
                        rawBytes = null;
                    } else {
                        rawBytes = input.readBytes();
                    }
                } else if (!input.skipField(tag)) {
                    break;
                }
            }
            input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
            if (rawBytes != null && typeId != 0) {
                if (extension != null) {
                    mergeMessageSetExtensionFromBytes(rawBytes, extensionRegistry, extension);
                } else if (rawBytes != null) {
                    mergeLengthDelimitedField(typeId, rawBytes);
                }
            }
        }

        private void eagerlyMergeMessageSetExtension(CodedInputStream input, GeneratedExtension<?, ?> extension, ExtensionRegistryLite extensionRegistry, int typeId) throws IOException {
            int tag = WireFormat.makeTag(typeId, 2);
            parseExtension(input, extensionRegistry, extension, tag, typeId);
        }

        private void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionRegistryLite extensionRegistry, GeneratedExtension<?, ?> extension) throws IOException {
            MessageLite.Builder subBuilder = null;
            MessageLite existingValue = (MessageLite) this.extensions.getField(extension.descriptor);
            if (existingValue != null) {
                subBuilder = existingValue.toBuilder();
            }
            if (subBuilder == null) {
                subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
            }
            subBuilder.mergeFrom(rawBytes, extensionRegistry);
            MessageLite value = subBuilder.build();
            ensureExtensionsAreMutable().setField(extension.descriptor, extension.singularToFieldSetType(value));
        }

        FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.m7417clone();
            }
            return this.extensions;
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
            GeneratedExtension<MessageType, ?> checkIsLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(checkIsLite);
            return this.extensions.hasField(checkIsLite.descriptor);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            return this.extensions.getRepeatedFieldCount(extensionLite.descriptor);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            GeneratedExtension<MessageType, ?> checkIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            Object field = this.extensions.getField(checkIsLite.descriptor);
            if (field == null) {
                return checkIsLite.defaultValue;
            }
            return (Type) checkIsLite.fromFieldSetType(field);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i) {
            GeneratedExtension<MessageType, ?> checkIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            return (Type) checkIsLite.singularFromFieldSetType(this.extensions.getRepeatedField(checkIsLite.descriptor, i));
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<ExtensionDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Map.Entry<ExtensionDescriptor, Object> next;

            private ExtensionWriter(boolean messageSetWireFormat) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = this.iter.next();
                }
                this.messageSetWireFormat = messageSetWireFormat;
            }

            public void writeUntil(int end, CodedOutputStream output) throws IOException {
                while (this.next != null && this.next.getKey().getNumber() < end) {
                    ExtensionDescriptor extension = this.next.getKey();
                    if (this.messageSetWireFormat && extension.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !extension.isRepeated()) {
                        output.writeMessageSetExtension(extension.getNumber(), (MessageLite) this.next.getValue());
                    } else {
                        FieldSet.writeField(extension, this.next.getValue(), output);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        protected ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected ExtendableBuilder(MessageType defaultInstance) {
            super(defaultInstance);
        }

        void internalSetExtensionSet(FieldSet<ExtensionDescriptor> extensions) {
            copyOnWrite();
            ((ExtendableMessage) this.instance).extensions = extensions;
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.Builder
        protected void copyOnWriteInternal() {
            super.copyOnWriteInternal();
            if (((ExtendableMessage) this.instance).extensions != FieldSet.emptySet()) {
                ((ExtendableMessage) this.instance).extensions = ((ExtendableMessage) this.instance).extensions.m7417clone();
            }
        }

        private FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            FieldSet<ExtensionDescriptor> extensions = ((ExtendableMessage) this.instance).extensions;
            if (extensions.isImmutable()) {
                FieldSet<ExtensionDescriptor> extensions2 = extensions.m7417clone();
                ((ExtendableMessage) this.instance).extensions = extensions2;
                return extensions2;
            }
            return extensions;
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public final MessageType buildPartial() {
            if (!((ExtendableMessage) this.instance).isMutable()) {
                return (MessageType) this.instance;
            }
            ((ExtendableMessage) this.instance).extensions.makeImmutable();
            return (MessageType) super.buildPartial();
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
            return ((ExtendableMessage) this.instance).hasExtension(extension);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
            return ((ExtendableMessage) this.instance).getExtensionCount(extension);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return (Type) ((ExtendableMessage) this.instance).getExtension(extensionLite);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i) {
            return (Type) ((ExtendableMessage) this.instance).getExtension(extensionLite, i);
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extension, Type value) {
            GeneratedExtension<MessageType, ?> checkIsLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(checkIsLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setField(checkIsLite.descriptor, checkIsLite.toFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extension, int index, Type value) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setRepeatedField(extensionLite.descriptor, index, extensionLite.singularToFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extension, Type value) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().addRepeatedField(extensionLite.descriptor, extensionLite.singularToFieldSetType(value));
            return this;
        }

        public final BuilderType clearExtension(ExtensionLite<MessageType, ?> extension) {
            GeneratedExtension<MessageType, ?> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().clearField(extensionLite.descriptor);
            return this;
        }
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, Class singularType) {
        return new GeneratedExtension<>(containingTypeDefaultInstance, defaultValue, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, false, false), singularType);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingTypeDefaultInstance, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, boolean isPacked, Class singularType) {
        return new GeneratedExtension<>(containingTypeDefaultInstance, Collections.emptyList(), messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, true, isPacked), singularType);
    }

    static final class ExtensionDescriptor implements FieldSet.FieldDescriptorLite<ExtensionDescriptor> {
        final Internal.EnumLiteMap<?> enumTypeMap;
        final boolean isPacked;
        final boolean isRepeated;
        final int number;
        final WireFormat.FieldType type;

        ExtensionDescriptor(Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, boolean isRepeated, boolean isPacked) {
            this.enumTypeMap = enumTypeMap;
            this.number = number;
            this.type = type;
            this.isRepeated = isRepeated;
            this.isPacked = isPacked;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public int getNumber() {
            return this.number;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.FieldType getLiteType() {
            return this.type;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.JavaType getLiteJavaType() {
            return this.type.getJavaType();
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public boolean isRepeated() {
            return this.isRepeated;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public boolean isPacked() {
            return this.isPacked;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public Internal.EnumLiteMap<?> getEnumType() {
            return this.enumTypeMap;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public MessageLite.Builder internalMergeFrom(MessageLite.Builder to, MessageLite from) {
            return ((Builder) to).mergeFrom((Builder) from);
        }

        @Override // java.lang.Comparable
        public int compareTo(ExtensionDescriptor other) {
            return this.number - other.number;
        }
    }

    static Method getMethodOrDie(Class clazz, String name, Class... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Generated message class \"" + clazz.getName() + "\" missing method \"" + name + "\".", e);
        }
    }

    static Object invokeOrDie(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {
        final ContainingType containingTypeDefaultInstance;
        final Type defaultValue;
        final ExtensionDescriptor descriptor;
        final MessageLite messageDefaultInstance;

        GeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, ExtensionDescriptor descriptor, Class singularType) {
            if (containingTypeDefaultInstance == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (descriptor.getLiteType() == WireFormat.FieldType.MESSAGE && messageDefaultInstance == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            }
            this.containingTypeDefaultInstance = containingTypeDefaultInstance;
            this.defaultValue = defaultValue;
            this.messageDefaultInstance = messageDefaultInstance;
            this.descriptor = descriptor;
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return this.containingTypeDefaultInstance;
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public int getNumber() {
            return this.descriptor.getNumber();
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public MessageLite getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        Object fromFieldSetType(Object value) {
            if (this.descriptor.isRepeated()) {
                if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                    List result = new ArrayList();
                    for (Object element : (List) value) {
                        result.add(singularFromFieldSetType(element));
                    }
                    return result;
                }
                return value;
            }
            return singularFromFieldSetType(value);
        }

        Object singularFromFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return this.descriptor.enumTypeMap.findValueByNumber(((Integer) value).intValue());
            }
            return value;
        }

        Object toFieldSetType(Object value) {
            if (this.descriptor.isRepeated()) {
                if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                    List result = new ArrayList();
                    for (Object element : (List) value) {
                        result.add(singularToFieldSetType(element));
                    }
                    return result;
                }
                return value;
            }
            return singularToFieldSetType(value);
        }

        Object singularToFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return Integer.valueOf(((Internal.EnumLite) value).getNumber());
            }
            return value;
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public WireFormat.FieldType getLiteType() {
            return this.descriptor.getLiteType();
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public boolean isRepeated() {
            return this.descriptor.isRepeated;
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public Type getDefaultValue() {
            return this.defaultValue;
        }
    }

    protected static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final byte[] asBytes;
        private final Class<?> messageClass;
        private final String messageClassName;

        public static SerializedForm of(MessageLite message) {
            return new SerializedForm(message);
        }

        SerializedForm(MessageLite regularForm) {
            this.messageClass = regularForm.getClass();
            this.messageClassName = this.messageClass.getName();
            this.asBytes = regularForm.toByteArray();
        }

        protected Object readResolve() throws ObjectStreamException {
            try {
                Class<?> messageClass = resolveMessageClass();
                Field defaultInstanceField = messageClass.getDeclaredField("DEFAULT_INSTANCE");
                defaultInstanceField.setAccessible(true);
                MessageLite defaultInstance = (MessageLite) defaultInstanceField.get(null);
                return defaultInstance.newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException("Unable to understand proto buffer", e);
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("Unable to call parsePartialFrom", e3);
            } catch (NoSuchFieldException e4) {
                return readResolveFallback();
            } catch (SecurityException e5) {
                throw new RuntimeException("Unable to call DEFAULT_INSTANCE in " + this.messageClassName, e5);
            }
        }

        @Deprecated
        private Object readResolveFallback() throws ObjectStreamException {
            try {
                Class<?> messageClass = resolveMessageClass();
                Field defaultInstanceField = messageClass.getDeclaredField("defaultInstance");
                defaultInstanceField.setAccessible(true);
                MessageLite defaultInstance = (MessageLite) defaultInstanceField.get(null);
                return defaultInstance.newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException("Unable to understand proto buffer", e);
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("Unable to call parsePartialFrom", e3);
            } catch (NoSuchFieldException e4) {
                throw new RuntimeException("Unable to find defaultInstance in " + this.messageClassName, e4);
            } catch (SecurityException e5) {
                throw new RuntimeException("Unable to call defaultInstance in " + this.messageClassName, e5);
            }
        }

        private Class<?> resolveMessageClass() throws ClassNotFoundException {
            return this.messageClass != null ? this.messageClass : Class.forName(this.messageClassName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> checkIsLite(ExtensionLite<MessageType, T> extension) {
        if (!extension.isLite()) {
            throw new IllegalArgumentException("Expected a lite extension.");
        }
        return (GeneratedExtension) extension;
    }

    protected static final <T extends GeneratedMessageLite<T, ?>> boolean isInitialized(T message, boolean shouldMemoize) {
        byte memoizedIsInitialized = ((Byte) message.dynamicMethod(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED)).byteValue();
        if (memoizedIsInitialized == 1) {
            return true;
        }
        if (memoizedIsInitialized == 0) {
            return false;
        }
        boolean isInitialized = Protobuf.getInstance().schemaFor((Protobuf) message).isInitialized(message);
        if (shouldMemoize) {
            message.dynamicMethod(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, isInitialized ? message : null);
        }
        return isInitialized;
    }

    protected static Internal.IntList emptyIntList() {
        return IntArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.framework.protobuf.Internal$IntList] */
    protected static Internal.IntList mutableCopy(Internal.IntList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static Internal.LongList emptyLongList() {
        return LongArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.framework.protobuf.Internal$LongList] */
    protected static Internal.LongList mutableCopy(Internal.LongList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static Internal.FloatList emptyFloatList() {
        return FloatArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.framework.protobuf.Internal$FloatList] */
    protected static Internal.FloatList mutableCopy(Internal.FloatList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static Internal.DoubleList emptyDoubleList() {
        return DoubleArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.framework.protobuf.Internal$DoubleList] */
    protected static Internal.DoubleList mutableCopy(Internal.DoubleList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static Internal.BooleanList emptyBooleanList() {
        return BooleanArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.framework.protobuf.Internal$BooleanList] */
    protected static Internal.BooleanList mutableCopy(Internal.BooleanList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static <E> Internal.ProtobufList<E> emptyProtobufList() {
        return ProtobufArrayList.emptyList();
    }

    protected static <E> Internal.ProtobufList<E> mutableCopy(Internal.ProtobufList<E> list) {
        int size = list.size();
        return list.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T> {
        private final T defaultInstance;

        public DefaultInstanceBasedParser(T defaultInstance) {
            this.defaultInstance = defaultInstance;
        }

        @Override // com.android.framework.protobuf.Parser
        public T parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.parsePartialFrom(this.defaultInstance, codedInputStream, extensionRegistryLite);
        }

        @Override // com.android.framework.protobuf.AbstractParser, com.android.framework.protobuf.Parser
        public T parsePartialFrom(byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.parsePartialFrom(this.defaultInstance, bArr, i, i2, extensionRegistryLite);
        }
    }

    static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        T t2 = (T) t.newMutableInstance();
        try {
            Schema schemaFor = Protobuf.getInstance().schemaFor((Protobuf) t2);
            schemaFor.mergeFrom(t2, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
            schemaFor.makeImmutable(t2);
            return t2;
        } catch (InvalidProtocolBufferException e) {
            e = e;
            if (e.getThrownFromInputStream()) {
                e = new InvalidProtocolBufferException((IOException) e);
            }
            throw e.setUnfinishedMessage(t2);
        } catch (UninitializedMessageException e2) {
            throw e2.asInvalidProtocolBufferException().setUnfinishedMessage(t2);
        } catch (IOException e3) {
            if (e3.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e3.getCause());
            }
            throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(t2);
        } catch (RuntimeException e4) {
            if (e4.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e4.getCause());
            }
            throw e4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        T t2 = (T) t.newMutableInstance();
        try {
            Schema schemaFor = Protobuf.getInstance().schemaFor((Protobuf) t2);
            schemaFor.mergeFrom(t2, bArr, i, i + i2, new ArrayDecoders.Registers(extensionRegistryLite));
            schemaFor.makeImmutable(t2);
            return t2;
        } catch (InvalidProtocolBufferException e) {
            e = e;
            if (e.getThrownFromInputStream()) {
                e = new InvalidProtocolBufferException((IOException) e);
            }
            throw e.setUnfinishedMessage(t2);
        } catch (UninitializedMessageException e2) {
            throw e2.asInvalidProtocolBufferException().setUnfinishedMessage(t2);
        } catch (IOException e3) {
            if (e3.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e3.getCause());
            }
            throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(t2);
        } catch (IndexOutOfBoundsException e4) {
            throw InvalidProtocolBufferException.truncatedMessage().setUnfinishedMessage(t2);
        }
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (T) parsePartialFrom(t, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(T message) throws InvalidProtocolBufferException {
        if (message != null && !message.isInitialized()) {
            throw message.newUninitializedMessageException().asInvalidProtocolBufferException().setUnfinishedMessage(message);
        }
        return message;
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parseFrom(t, CodedInputStream.newInstance(byteBuffer), extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (T) parseFrom(t, byteBuffer, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteString byteString) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parseFrom(t, byteString, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, byteString, extensionRegistryLite));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream newCodedInput = byteString.newCodedInput();
        T t2 = (T) parsePartialFrom(t, newCodedInput, extensionRegistryLite);
        try {
            newCodedInput.checkLastTagWas(0);
            return t2;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(t2);
        }
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] bArr) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, bArr, 0, bArr.length, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, bArr, 0, bArr.length, extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, InputStream inputStream) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, CodedInputStream.newInstance(inputStream), ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, CodedInputStream.newInstance(inputStream), extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (T) parseFrom(t, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, codedInputStream, extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialDelimitedFrom(t, inputStream, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialDelimitedFrom(t, inputStream, extensionRegistryLite));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        try {
            int read = inputStream.read();
            if (read == -1) {
                return null;
            }
            CodedInputStream newInstance = CodedInputStream.newInstance(new AbstractMessageLite.Builder.LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(read, inputStream)));
            T t2 = (T) parsePartialFrom(t, newInstance, extensionRegistryLite);
            try {
                newInstance.checkLastTagWas(0);
                return t2;
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(t2);
            }
        } catch (InvalidProtocolBufferException e2) {
            if (e2.getThrownFromInputStream()) {
                throw new InvalidProtocolBufferException((IOException) e2);
            }
            throw e2;
        } catch (IOException e3) {
            throw new InvalidProtocolBufferException(e3);
        }
    }
}
