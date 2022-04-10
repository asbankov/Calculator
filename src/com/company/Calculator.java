package com.company;

public class Calculator {
    private String input;
    private String inputModified;
    private final String ISACCEPTARAB = "^([1-9]|10)[+\\-*/]([1-9]|10)$";
    private final String ISACCEPTROM = "^[iIvVxX]{1,4}[+\\-*/][iIvVxX]{1,4}$";
    private final String SPLITTER = "[+\\-*/]";
    private final String EXCEPTARABROM = "^([1-9]|10)[+\\-*/][iIvVxX]{1,4}$";
    private final String EXCEPTROMARAB = "^[iIvVxX]{1,4}[+\\-*/]([1-9]|10)$";
    private final String EXCEPTMATHOP = "^[^+\\-*/]*$";


    public Calculator (String input) {
        this.input = input;
        inputModified = input.replaceAll(" ", "");
    }

    public void calculate () {
        boolean isArab = inputModified.matches(ISACCEPTARAB);
        boolean isRom = inputModified.matches(ISACCEPTROM);
        if (isArab) {
            calculateArab();
        } else if (isRom) {
            try {
                calculateRom();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            boolean isArabRom = inputModified.matches(EXCEPTARABROM);
            boolean isRomArab = inputModified.matches(EXCEPTROMARAB);
            boolean isMathOp = inputModified.matches(EXCEPTMATHOP);
            if (isArabRom || isRomArab) {
                System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
            } else if (isMathOp) {
                System.out.println("throws Exception //т.к. строка не является математической операцией");
            } else {
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию");
            }
        }
    }

    private void calculateArab () {
        String[] operands = inputModified.split(SPLITTER);
        int firOp = Integer.parseInt(operands[0]);
        int secOp = Integer.parseInt(operands[1]);
        char operator = inputModified.charAt(operands[0].length());
        int result = operations(firOp, secOp, operator);
        System.out.println(result);
    }

    private void calculateRom () throws Exception {
        String[] operands = inputModified.split(SPLITTER);
        String fOp = operands[0];
        String sOp = operands[1];
        int firOp, secOp;
        try {
            firOp = Conversion.romToArab(fOp);
        } catch (Exception ex) {
            throw ex;
        }
        try {
            secOp = Conversion.romToArab(sOp);
        } catch (Exception ex) {
            throw ex;
        }
        char operator = inputModified.charAt(operands[0].length());
        int result = operations(firOp, secOp, operator);
        if (result <= 0) {
            throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
        } else {
            System.out.println(Conversion.arabToRom(result));
        }
    }

    private int operations (int firOp, int secOp, char operator) {
        int result = 0;
        switch (operator) {
            case ('+') :
                result = firOp + secOp;
                break;
            case ('-') :
                result = firOp - secOp;
                break;
            case ('*') :
                result = firOp * secOp;
                break;
            case ('/') :
                result = firOp / secOp;
                break;
        }
        return result;
    }
}
