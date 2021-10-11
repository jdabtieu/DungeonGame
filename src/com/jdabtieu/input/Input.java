package com.jdabtieu.input;

import java.io.Closeable;
import java.util.Scanner;
/**
 * <p>A simple wrapper for Scanner that will repeatedly prompt the user for
 * input until certain conditions are met.
 * 
 * <p>This wrapper also deals with formatting-related exceptions (e.g. entering
 * a string for an int input), avoiding the need to manually check for proper
 * input.
 * 
 * <p>The Input scanner reads one line at a time, and if reading the current
 * line results in an exception or does not pass the conditions, the line is
 * discarded and the scanner is advanced to the next line.
 * 
 * <p>To read without conditions, one can use:
 * <blockquote><pre>
 *     Input in = new Input();
 *     T value = in.readT();
 * </pre></blockquote>
 * where <tt>T</tt> is <tt>Int</tt>, <tt>Long</tt>, <tt>Double</tt>, or
 * <tt>Line</tt>, to read an <tt>int</tt>, <tt>long</tt>, <tt>double</tt>,
 * or <tt>String</tt>, respectively.
 * 
 * <p>To read with a condition, one can use lambda expression to specify the
 * conditions. The expression should return true if the conditions pass, and
 * false if they fail. For example, to read a nonnegative int, one can use:
 * <blockquote><pre>
 *     Input in = new Input();
 *     int value = in.readInt(e -> e >= 0);
 * </pre></blockquote>
 * 
 * <p>If an <tt>Input</tt> object is closed, its underlying Scanner will also
 * be closed, resulting in an <tt>IllegalStateException</tt> if it is read
 * from.
 * 
 * @author Jonathan Wu (jonathan.wu3@outlook.com)
 * @version v1.1.0 (2021-10-05)
 */
public class Input implements Closeable, AutoCloseable {
    // The underlying Scanner
    private Scanner in;
    
    /**
     * Message to display if the input is invalid. null = no message.
     */
    public String invalid;
    
    /**
     * Constucts a new Input that scans from System.in, with no error message
     * for invalid inputs.
     */
    public Input() {
        in = new Scanner(System.in);
        invalid = null;
    }
    
    /**
     * Constucts a new Input that scans from System.in, printing prompt if
     * the input is invalid
     * @param prompt    message to print if the input is invalid, or null to
     *                  print nothing.
     */
    public Input(String prompt) {
        in = new Scanner(System.in);
        invalid = prompt;
    }
    
    /**
     * Reads and returns the next int from System.in.
     * @return  the int that was read
     */
    public int readInt() {
        return readInt(e -> true);
    }
    
    /**
     * Reads and returns the next int from System.in that meets the specified
     * condition. The default invalid message is used if the condition fails.
     * @param cond  the condition that the int must satisfy
     * @return      the int that was read
     */
    public int readInt(IntCondition cond) {
        return readInt(cond, invalid);
    }
    
    /**
     * Reads and returns the next int from System.in that meets the specified
     * condition. condFailMsg is displayed if it is not null and the condition
     * fails. if it is null, no message is displayed if the condition fails.
     * @param cond          the condition that the int must satisfy
     * @param condFailMsg   the message to display if the condition fails, or
     *                      null for no message
     * @return              the int that was read
     */
    public int readInt(IntCondition cond, String condFailMsg) {
        while (true) {
            String line = in.nextLine();
            int res;
            try {
                res = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                if (invalid != null) System.out.println(invalid);
                continue;
            }
            if (!cond.condition(res)) {
                if (condFailMsg != null) System.out.println(condFailMsg);
                continue;
            }
            return res;
        }
    }
    
    /**
     * Reads and returns the next long from System.in.
     * @return  the long that was read
     */
    public long readLong() {
        return readLong(e -> true);
    }
    
    /**
     * Reads and returns the next long from System.in that meets the specified
     * condition. The default invalid message is used if the condition fails.
     * @param cond  the condition that the long must satisfy
     * @return      the long that was read
     */
    public long readLong(LongCondition cond) {
        return readLong(cond, invalid);
    }
    
    /**
     * Reads and returns the next long from System.in that meets the specified
     * condition. condFailMsg is displayed if it is not null and the condition
     * fails. if it is null, no message is displayed if the condition fails.
     * @param cond          the condition that the long must satisfy
     * @param condFailMsg   the message to display if the condition fails, or
     *                      null for no message
     * @return              the long that was read
     */
    public long readLong(LongCondition cond, String condFailMsg) {
        while (true) {
            String line = in.nextLine();
            long res;
            try {
                res = Long.parseLong(line);
            } catch (NumberFormatException e) {
                if (invalid != null) System.out.println(invalid);
                continue;
            }
            if (!cond.condition(res)) {
                if (condFailMsg != null) System.out.println(condFailMsg);
                continue;
            }
            return res;
        }
    }
    
    /**
     * Reads and returns the next double from System.in.
     * @return  the double that was read
     */
    public double readDouble() {
        return readDouble(e -> true);
    }
    
    /**
     * Reads and returns the next double from System.in that meets the
     * specified condition. The default invalid message is used if the
     * condition fails.
     * @param cond  the condition that the double must satisfy
     * @return      the double that was read
     */
    public double readDouble(DoubleCondition cond) {
        return readDouble(cond, invalid);
    }
    
    /**
     * Reads and returns the next double from System.in that meets the
     * specified condition. condFailMsg is displayed if it is not null and the
     * condition fails. if it is null, no message is displayed if the condition
     * fails.
     * @param cond          the condition that the double must satisfy
     * @param condFailMsg   the message to display if the condition fails, or
     *                      null for no message
     * @return              the double that was read
     */
    public double readDouble(DoubleCondition cond, String condFailMsg) {
        while (true) {
            String line = in.nextLine();
            double res;
            try {
                res = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                if (invalid != null) System.out.println(invalid);
                continue;
            }
            if (!cond.condition(res)) {
                if (condFailMsg != null) System.out.println(condFailMsg);
                continue;
            }
            return res;
        }
    }
    
    /**
     * Reads and returns the next line from System.in.
     * @return  the line that was read
     */
    public String readLine() {
        return readLine(e -> true);
    }
    
    /**
     * Reads and returns the next line from System.in that meets the specified
     * condition. The default invalid message is used if the condition fails.
     * @param cond  the condition that the line must satisfy
     * @return      the line that was read
     */
    public String readLine(StringCondition cond) {
        return readLine(cond, invalid);
    }
    
    /**
     * Reads and returns the next line from System.in that meets the specified
     * condition. condFailMsg is displayed if it is not null and the condition
     * fails. if it is null, no message is displayed if the condition fails.
     * @param cond          the condition that the line must satisfy
     * @param condFailMsg   the message to display if the condition fails, or
     *                      null for no message
     * @return              the line that was read
     */
    public String readLine(StringCondition cond, String condFailMsg) {
        while (true) {
            String line = in.nextLine();
            if (!cond.condition(line)) {
                if (condFailMsg != null) System.out.println(condFailMsg);
                continue;
            }
            return line;
        }
    }
    
    @Override
    public void close() {
        in.close();
    }
}
