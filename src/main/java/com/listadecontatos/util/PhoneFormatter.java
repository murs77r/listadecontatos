package com.listadecontatos.util;

public final class PhoneFormatter {

    private PhoneFormatter() {}

    public static String format(String input) {
        if (input == null) return "";
        String digits = input.replaceAll("\\D", "");
        if (digits.isEmpty()) return "";

        // Limit to 11 digits (2 for DDD + up to 9 for number)
        if (digits.length() > 11) digits = digits.substring(0, 11);

        if (digits.length() <= 2) return "(" + digits;

        String ddd = digits.substring(0, 2);
        String rest = digits.substring(2);

        if (rest.length() <= 4) {
            return "(" + ddd + ") " + rest;
        }

        String prefix = rest.substring(0, rest.length() - 4);
        String suffix = rest.substring(rest.length() - 4);
        return "(" + ddd + ") " + prefix + "-" + suffix;
    }
}
