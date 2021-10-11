package com.jdabtieu.input;

/**
 * <p>Used with Input.readLine(...)
 * 
 * <p>This functional interface should return true if <tt>in</tt> is a valid
 * String, or false otherwise.
 * @author Jonathan Wu (jonathan.wu3@outlook.com)
 * @see Input
 */
@FunctionalInterface
public interface StringCondition {
    public abstract boolean condition(String in);
}
