package com.jdabtieu.input;

/**
 * <p>Used with Input.readInt(...)
 * 
 * <p>This functional interface should return true if <tt>in</tt> is a valid
 * int, or false otherwise.
 * @author Jonathan Wu (jonathan.wu3@outlook.com)
 * @see Input
 */
@FunctionalInterface
public interface IntCondition {
    public abstract boolean condition(int in);
}
