package nl.tabuu.tabuucore.nms;

import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
import nl.tabuu.tabuucore.serialization.bytes.AbstractByteSerializer;
import nl.tabuu.tabuucore.serialization.bytes.Serializer;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

/**
 * TabuuCore representation of NBTTagType.
 */

// IMPORTANT: Do not change the order of this enum, as the ordinal is used as the common key.

public enum NBTTagType {

    END(null, null) {
        @Override
        public byte[] toByteArray(Object object) {
            return new byte[]{0};
        }

        @Override
        public Object fromBytes(byte[] bytes) {
            return (byte) 0;
        }
    },

    BYTE(Byte.class, Serializer.BYTE),

    SHORT(Short.class, Serializer.SHORT),

    INTEGER(Integer.class, Serializer.INTEGER),

    LONG(Long.class, Serializer.LONG),

    FLOAT(Float.class, Serializer.FLOAT),

    DOUBLE(Double.class, Serializer.DOUBLE),

    BYTE_ARRAY(byte[].class, Serializer.BYTE_ARRAY) {
        @Override
        public byte[] toByteArray(Object object) {
            byte[] data = (byte[]) object;
            byte[] count = Serializer.INTEGER.serialize(data.length);

            return ArrayUtils.addAll(count, data);
        }

        @Override
        public Object fromBytes(byte[] bytes) {
            return super.fromBytes(ArrayUtils.subarray(bytes, Integer.BYTES, bytes.length));
        }
    },

    STRING(String.class, Serializer.STRING),

    @Deprecated // Do not remove
    LIST(List.class, null),

    COMPOUND(INBTTagCompound.class, new CompoundSerializer()),

    INTEGER_ARRAY(int[].class, Serializer.INTEGER_ARRAY) {
        @Override
        public byte[] toByteArray(Object object) {
            byte[] data = Serializer.INTEGER_ARRAY.serialize((int[]) object);
            byte[] count = Serializer.INTEGER.serialize(data.length / Integer.BYTES);

            return ArrayUtils.addAll(count, data);
        }

        @Override
        public Object fromBytes(byte[] bytes) {
            return super.fromBytes(ArrayUtils.subarray(bytes, Integer.BYTES, bytes.length));
        }
    },

    LONG_ARRAY(long[].class, Serializer.LONG_ARRAY) {
        @Override
        public byte[] toByteArray(Object object) {
            byte[] data = Serializer.LONG_ARRAY.serialize((long[]) object);
            byte[] count = Serializer.INTEGER.serialize(data.length / Long.BYTES);

            return ArrayUtils.addAll(count, data);
        }

        @Override
        public Object fromBytes(byte[] bytes) {
            return super.fromBytes(ArrayUtils.subarray(bytes, Integer.BYTES, bytes.length));
        }
    };

    private Class<?> _class;
    @SuppressWarnings("rawtypes")
    private AbstractByteSerializer _serializer;

    <T> NBTTagType(Class<T> clazz, AbstractByteSerializer<T> serializer) {
        _class = clazz;
        _serializer = serializer;
    }

    @SuppressWarnings("unchecked")
    public byte[] toByteArray(Object object) {
        return _serializer != null ? _serializer.serialize(object) : new byte[0];
    }

    public Object fromBytes(byte[] bytes) {
        return _serializer != null ? _serializer.deserialize(bytes) : null;
    }

    public Class<?> getType() {
        return _class;
    }

    public static <T> NBTTagType valueOf(Class<T> clazz) {
        for (NBTTagType tagType : values())
            if (tagType.getType() != null && tagType.getType().isAssignableFrom(clazz)) return tagType;

        throw new IllegalArgumentException(String.format("Could not find NBTTagType with class type '%s'", clazz.getName()));
    }

    private static class CompoundSerializer extends AbstractByteSerializer<INBTTagCompound> {
        @Override
        public byte[] serialize(INBTTagCompound object) {
            return object.toRawByteArray();
        }

        @Override
        public INBTTagCompound deserialize(byte[] value) {
            return INBTTagCompound.get().copyRawByteArray(value);
        }
    }
}