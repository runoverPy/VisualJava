package com.visualjava.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Eval {
    public static void main(String[] args) {
        System.out.println(Eval.eval("0 - 7"));
    }

    private final Map<String, Integer> localVars;

    private Eval(Map<String, Integer> localVars) {
        this.localVars = localVars;
    }

    public static int eval(String expression) {
        return eval(expression, new HashMap<>());
    }

    public static int eval(String expression, Map<String, Integer> localVars) throws ExpressionFormatException {
        return new Eval(localVars)._eval(expression);
    }

    private int _eval(String expression) {
        Queue<Token> tokens = tokenize(expression);
        Queue<Token> rpnTokens = shunting(tokens);
        return calcRPN(rpnTokens);
    }

    private Queue<Token> tokenize(String expression) {
        Pattern exprPattern = Pattern.compile("(\\d+|[+\\-*/%^()]|[a-zA-Z_]+|\\()");
        Matcher exprMatcher = exprPattern.matcher(expression);
        Queue<Token> tokens = new LinkedList<>();
        while (exprMatcher.find()) {
            String token = exprMatcher.group();
            if (isInteger(token)) {
                tokens.add(new Constant(token));
            }
            else if (localVars.containsKey(token)) {
                tokens.add(new Variable(token));
            }
            else {
                tokens.add(new Operator(token));
            }
        }

        return tokens;
    }

    private static Queue<Token> shunting(Queue<Token> tokens) {
        Stack<Operator> operatorStack = new Stack<>();
        Queue<Token> output = new LinkedList<>();
        while (!tokens.isEmpty()) {
            Token token = tokens.remove();
            switch (token.getType()) {
                case CONSTANT, VARIABLE -> output.add(token);
                case OPERATOR -> {
                    Operator op1 = (Operator) token;
                    while (!operatorStack.isEmpty()) {
                        Operator op2 = operatorStack.pop();
                        if (!op2.getOperator().equals("(") && op2.getPrecedence() >= op1.getPrecedence()) {
                            output.add(op2);
                        } else {
                            operatorStack.push(op2);
                            break;
                        }
                    }
                    operatorStack.push(op1);
                }
                case LPARENTH ->
                        operatorStack.push((Operator) token);
                case RPARENTH -> {

                }
            }
        }
        while (!operatorStack.isEmpty()) output.add(operatorStack.pop());
        return output;
    }

    private int calcRPN(Queue<Token> rpnQueue) {
        Stack<Integer> calcStack = new Stack<>();
        for (Token token : rpnQueue) {
            switch (token.getType()) {
                case CONSTANT -> calcStack.push(((Constant) token).getValue());
                case VARIABLE -> calcStack.push(((Variable) token).getValue(localVars));
                case OPERATOR -> calcStack.push(((Operator) token).eval(calcStack.pop(), calcStack.pop()));
            }
        }
        return calcStack.pop();
    }

    private static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // UPN Elements
    private static abstract class Token {
        private final TokenType type;

        public Token(TokenType type) {
            this.type = type;
        }

        public TokenType getType() {
            return type;
        }
    }

    private static class Operator extends Token {
        private final String operator;

        public Operator(String operator) {
            super(TokenType.OPERATOR);
            this.operator = operator;
        }

        public int getPrecedence() {
            return Operator.getPrecedence(operator);
        }

        public static int getPrecedence(String operator) {
            return switch (operator) {
                case "+", "-" -> 2;
                case "*", "/" -> 1;
                default -> 0;
            };
        }

        public int eval(int right, int left) {
            return switch (operator) {
                case "+" -> left + right;
                case "-" -> left - right;
                case "*" -> left * right;
                case "/" -> left / right;
                default -> 0;
            };
        }

        public String getOperator() {
            return operator;
        }

        @Override
        public String toString() {
            return "OPERATOR: " + getOperator();
        }
    }

    private static class Constant extends Token {
        private final int value;

        public Constant(String constValue) {
            this(Integer.parseInt(constValue));
        }

        public Constant(int constValue) {
            super(TokenType.CONSTANT);
            this.value = constValue;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "CONSTANT: " + getValue();
        }
    }

    private class Variable extends Token {
        private final String handle;

        public Variable(String handle) {
            super(TokenType.VARIABLE);
            this.handle = handle;
        }

        public int getValue(Map<String, Integer> localVars) {
            if (!localVars.containsKey(getVarName())) throw new MissingVariableException();
            return localVars.get(this.handle);
        }

        public int getValue() {
            return getValue(localVars);
        }

        public String getVarName() {
            return this.handle;
        }

        @Override
        public String toString() {
            return "VARIABLE: " + getVarName() + "; local value " + getValue();
        }
    }

    private enum TokenType {
        VARIABLE,
        CONSTANT,
        OPERATOR,
        FUNCTION,
        LPARENTH,
        RPARENTH;
    }

    // Exceptions
    public static class ExpressionFormatException extends RuntimeException {

    }

    public static class MissingVariableException extends RuntimeException {

    }

    public static class MissingFunctionException extends RuntimeException {

    }
}
