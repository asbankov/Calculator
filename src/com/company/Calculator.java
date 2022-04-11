package com.company;

public class Calculator {
    private String input;
    private String inputModified;
    private final String ISACCEPTARAB = "^([1-9]|10)[+\\-*/]([1-9]|10)$";
    private final String ISACCEPTROM = "^[iIvVxX]{1,4}[+\\-*/][iIvVxX]{1,4}$";
    private final String SPLITTER = "[+\\-*/]";
    private final String EXCEPTARABROM = "^([1-9]|10)[+\\-*/][iIvVxX]{1,4}$";
    private final String EXCEPTROMARAB = "^[iIvVxX]{1,4}[+\\-*/]([1-9]|10)$";
    private final String ISACCEPTARABNUM = "^([1-9]|10)$";
    private final String ISACCEPTROMNUM = "^[iIvVxX]{1,4}$";


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
            exceptionsHandling();
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

    private void exceptionsHandling() {
        String[] splittedInput = inputModified.split(SPLITTER);
        int quantOfOperands = splittedInput.length;
        boolean isArabRom = inputModified.matches(EXCEPTARABROM);
        boolean isRomArab = inputModified.matches(EXCEPTROMARAB);
        if (quantOfOperands == 1) {
            if (splittedInput[0].length() < inputModified.length()) { // 2 +
                System.out.println("throws Exception //т.к. введённая строка содержит только один операнд");
            } else { // 123
                System.out.println("throws Exception //т.к. введённая строка не содержит ни одного корректного оператора (+, -, *, /)");
            }
        } else if (quantOfOperands >= 3) { // 1 + 2 + 3
            System.out.println("throws Exception //т.к. введённая строка содержит более одного корректного оператора (+, -, *, /)");
        } else {
            if (splittedInput[0].isEmpty()) { // + 2
                System.out.println("throws Exception //т.к. введённая строка содержит только один операнд");
            } else if (isArabRom) { // 2 + XX
                try {
                    int tmp = Conversion.romToArab(splittedInput[1]);
                    System.out.println("throws Exception //т.к. введенные операнды корректны, но используются одновременно разные системы счисления");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (isRomArab) { // XX + 2
                try {
                    int tmp = Conversion.romToArab(splittedInput[0]);
                    System.out.println("throws Exception //т.к. введенные операнды корректны, но используются одновременно разные системы счисления");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else { // abc + 1, 1 + abc, X + abc, XX + abc
                boolean isFOpAccArabNum = splittedInput[0].matches(ISACCEPTARABNUM);
                boolean isFOpAccRomNum = splittedInput[0].matches(ISACCEPTROMNUM);
                if (isFOpAccArabNum) {
                    System.out.println("throws Exception //т.к. операнд " + splittedInput[1] + " не является числом от 1 до 10 ни в одной из допустимых систем счисления");
                } else if (isFOpAccRomNum) {
                    try {
                        int tmp = Conversion.romToArab(splittedInput[0]);
                        System.out.println("throws Exception //т.к. операнд " + splittedInput[1] + " не является числом от 1 до 10 ни в одной из допустимых систем счисления");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    System.out.println("throws Exception //т.к. операнд " + splittedInput[0] + " не является числом от 1 до 10 ни в одной из допустимых систем счисления");
                }
            }
        }
    }
}
