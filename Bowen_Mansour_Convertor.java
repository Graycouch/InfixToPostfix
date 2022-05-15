package project3csc4101;

import java.util.Stack;

public class Bowen_Mansour_Convertor {

    public static String convertToPostfix(String infix) {
        String[] infixArray = infix.split(" ");
        StringBuilder postfix = new StringBuilder();
        Stack<String> infixStack = new Stack<>();

        for (String infixValue : infixArray) {
            if (Character.isLetterOrDigit(infixValue.charAt(0))) {
                postfix.append(infixValue).append(" ");
            } else if (infixValue.equals("(")) {
                infixStack.push(infixValue);
            } else if (infixValue.equals(")")) {
                while (!infixStack.isEmpty() && !"(".equals(infixStack.peek())) {
                    postfix.append(infixStack.pop()).append(" ");
                }
                infixStack.pop();
            } else {
                while (!infixStack.isEmpty() && operatorCheck(infixValue) <= operatorCheck(infixStack.peek())) {
                    postfix.append(infixStack.pop()).append(" ");
                }
                infixStack.push(infixValue);
            }
        }

        while (!infixStack.isEmpty()) {
            if ("(".equals(infixStack.peek())) {
                return "This expression is not valid, try again!";
            }
            postfix.append(infixStack.pop()).append(" ");
        }
        
        return postfix.substring(0, postfix.length() - 1);
    }

    public static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();

        char next;
        String temp = "";
        double tempNum;
        while (postfix.length() != 0) {

            next = postfix.charAt(0);

            while (next == ' ') {

                postfix = postfix.substring(1, postfix.length());
                next = postfix.charAt(0);
            }
            
            if (next != '+' && next != '-' && next != '/' && next != '*') {
                int i = 0;
                temp = "";
                while (postfix.charAt(i) != ' ' && next != '+' && next != '-' && next != '/' && next != '*') {
                    temp = temp + postfix.charAt(i);
                    i++;
                }
                i++;
                tempNum = Double.parseDouble(temp);

                stack.push(tempNum);
                postfix = postfix.substring(i, postfix.length());

            } else {
                Double first = stack.pop();
                Double second = stack.pop();
                switch (next) {
                    case '+' -> stack.push(second + first);
                    case '-' -> stack.push(second - first);
                    case '/' -> stack.push(second / first);
                    case '*' -> stack.push(second * first);
                    default -> {
                            System.out.println("error");
                            return -42069;
                    }
                }
                postfix = postfix.substring(1, postfix.length());

            }

        }
        return stack.pop();

    }

    private static int operatorCheck(String operator) {
        switch (operator) {
            case "+", "-" -> {
                return 1;
            }
            case "*", "/" -> {
                return 2;
            }
        }
        return -1;
    }
}
