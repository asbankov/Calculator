package com.company;

public class Conversion {
    private static final String[] TENS = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] DIGITS = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    public static String arabToRom (int n) {
        if (n == 100) {
            return "C";
        }
        int ten = n / 10;
        int digit = n % 10;
        return TENS[ten] + DIGITS[digit];
    }

    //В метод romToArab надо передавать строку с обрезанными пробелами
    public static int romToArab (String s) throws Exception {
        for (int i = 1; i <= 10; ++i) {
            if (s.equalsIgnoreCase(DIGITS[i])) {
                return i;
            }
        }
        throw new Exception("throws Exception //т.к. операнд " + s + " некорректен");
    }
}
