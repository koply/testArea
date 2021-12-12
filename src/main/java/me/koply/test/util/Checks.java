package me.koply.test.util;

public class Checks {

    /**
     * @param seq the object to check
     * @return if param is blank, returns false
     */
    public static boolean notBlank(CharSequence seq) {
        return seq != null && !(seq.length() == 0);
    }

    /**
     * @param seq the object to check
     * @return if param is blank, returns true
     */
    public static boolean isBlank(CharSequence seq) {
        return seq == null || seq.length() == 0;
    }

    /**
     * @param args arguments
     * @return if arguments have null value returns true
     */
    public static boolean isAnyNull(Object...args) {
        for (var t : args) {
            if (t == null) return true;
        }
        return false;
    }

}