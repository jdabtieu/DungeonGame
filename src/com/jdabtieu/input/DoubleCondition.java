package com.jdabtieu.input;

/**
 * <p>Used with Input.readDouble(...)
 * 
 * <p>This functional interface should return true if <tt>in</tt> is a valid
 * double, or false otherwise.
 * @author Jonathan Wu (jonathan.wu3@outlook.com)
 * @see Input
 */
@FunctionalInterface
public interface DoubleCondition {
    public abstract boolean condition(double in);
}
