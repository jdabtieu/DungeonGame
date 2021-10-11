package com.jdabtieu.input;

/**
 * <p>Used with Input.readLong(...)
 * 
 * <p>This functional interface should return true if <tt>in</tt> is a valid
 * long, or false otherwise.
 * @author Jonathan Wu (jonathan.wu3@outlook.com)
 * @see Input
 */
@FunctionalInterface
public interface LongCondition {
    public abstract boolean condition(long in);
}
