package nl.tabuu.tabuucore.util.vector;

import java.io.Serializable;
import java.util.Arrays;

public abstract class Vectorf implements Serializable {

    private float[] _values;

    public Vectorf(float... values) {
        _values = values;
    }

    public <T extends Vectorf> T add(T vector) {
        for (int i = 0; i < vector.getValues().length && i < getValues().length; i++)
            _values[i] += vector.getValues()[i];

        return (T) this;
    }

    public <T extends Vectorf> T subtract(T vector) {
        for (int i = 0; i < vector.getValues().length && i < getValues().length; i++)
            _values[i] -= vector.getValues()[i];

        return (T) this;
    }

    public <T extends Vectorf> T multiply(T vector) {
        for (int i = 0; i < vector.getValues().length && i < getValues().length; i++)
            _values[i] *= vector.getValues()[i];

        return (T) this;
    }

    public <T extends Vectorf> T divide(T vector) {
        for (int i = 0; i < vector.getValues().length && i < getValues().length; i++)
            _values[i] /= vector.getValues()[i];

        return (T) this;
    }

    public float magnitude() {
        float total = 0;
        for (int i = 0; i < getValues().length; i++)
            total += getValues()[i] * getValues()[i];

        return total;
    }

    public float length() {
        return (float) Math.sqrt(magnitude());
    }

    public <T extends Vectorf> float dot(T other) {
        float total = 0;
        for (int i = 0; i < getValues().length; i++)
            total += getValues()[i] * other.getValues()[i];

        return total;
    }

    public <T extends Vectorf> T normalize() {
        float length = length();
        for (int i = 0; i < getValues().length; i++)
            getValues()[i] /= length;

        return (T) this;
    }

    public <T extends Vectorf> float angle(T other) {
        float dot = dot(other) / (length() * other.length());
        return (float) Math.acos(dot);
    }

    public <T extends Vectorf> T zero() {
        for (int i = 0; i < getValues().length; i++)
            getValues()[i] = 0;

        return (T) this;
    }

    public <T extends Vectorf> float distance(T other) {
        float total = 0;
        for (int i = 0; i < getValues().length; i++)
            total += getValues()[i] - other.getValues()[i];

        return (float) Math.sqrt(total);
    }

    public <T extends Vectorf> T copy(T vector) {
        for (int i = 0; i < vector.getValues().length && i < getValues().length; i++)
            _values[i] = vector.getValues()[i];

        return (T) this;
    }

    public float[] getValues() {
        return _values;
    }

    private void setValues(float... values) {
        _values = values;
    }

    @Override
    public String toString() {
        return "Vectorf{" +
                Arrays.toString(_values) +
                '}';
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Vectorf))
            return false;

        Vectorf other = (Vectorf) object;

        if (other.getValues().length != getValues().length)
            return false;

        for (int i = 0; i < getValues().length; i++) {
            if (getValues()[i] != other.getValues()[i])
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        for (int i = 0; i < getValues().length; i++)
            hash = 79 * hash + (int) (Double.doubleToLongBits(getValues()[i]) ^ (Double.doubleToLongBits(getValues()[i]) >>> 32));

        return hash;
    }
}
