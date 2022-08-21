package com.visualjava.parser;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Eval {
    private Eval() {}

    public static int eval(String expression) {
        return eval(expression, new HashMap<>());
    }

    public static int eval(String expression, Map<String, Integer> localVars) throws ExpressionFormatException {
        Queue<String> fragments = tokenize(expression);
        Queue<Token> tokens = new LinkedList<>();

        for (String fragment : fragments) {
            if (isInteger(fragment))
                tokens.add(new Constant(fragment));
            else if (localVars.containsKey(fragment))
                tokens.add(new Constant(localVars.get(fragment)));
            else
                tokens.add(new Operator(fragment));
        }

        return calcRPN(shunting(tokens), localVars, null);
    }

    private static Queue<String> tokenize(String expression) {
        Pattern exprPattern = Pattern.compile("(\\d+|[+\\-*/%^()]|[a-zA-Z_]+|\\()");
        Matcher exprMatcher = exprPattern.matcher(expression);
        exprMatcher.results();

        return new LinkedList<>();
    }

    private static Queue<Token> shunting(Queue<Token> tokens) {
        Stack<Operator> operatorStack = new Stack<>();
        Queue<Token> output = new LinkedList<>();
        while (!tokens.isEmpty()) {
            Token token = tokens.remove();
            switch (token.getType()) {
                case CONSTANT -> {
                    output.add(token);
                    break;
                }
                case VARIABLE -> {
                    output.add(token);
                    break;
                }
                case OPERATOR -> {
                    Operator op1 = (Operator) token;
                    while (!operatorStack.isEmpty()) {
                        Operator op2 = operatorStack.pop();
                        if (op2.getOperator() != "(" && op2.getPrecedence() >= op1.getPrecedence()) {
                            output.add(op2);
                        } else {
                            operatorStack.push(op2);
                            break;
                        }
                    }
                    operatorStack.push(op1);
                }
                case LPARENTH -> {
                    operatorStack.push((Operator) token);
                }
                case RPARENTH -> {

                }
            }
        }
        return output;
    }

    private static int calcRPN(Queue<Token> rpnStack, Map<String, Integer> localVars, Map<String, Function<Integer, Integer>> localFuns) {
        Stack<Integer> calcStack = new Stack<>();
        for (Token token : rpnStack) {
            switch (token.getType()) {
                case CONSTANT:
                    calcStack.push(((Constant) token).getValue());
                    break;
                case VARIABLE:
                    calcStack.push(((Variable) token).getValue(localVars));
                    break;
                case OPERATOR:
                    calcStack.push(((Operator) token).eval(calcStack.pop(), calcStack.pop()));
                    break;
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

    private static int toInteger(String number) {
        return Integer.parseInt(number);
    }

    private static abstract class Token {
        private final TokenType type;

        public Token(TokenType type) {
            this.type = type;
        }

        public TokenType getType() {
            return type;
        }

        public static Token wrap(String value) {
            return null;
        }
    }


    private static class Operator extends Token {
        private static final String[] acceptedOperators = new String[] {"+", "-", "*", "/"};

        private final String operator;

        public Operator(String operator) {
            super(TokenType.OPERATOR);
            this.operator = operator;
        }

        public int getPrecedence() {
            switch (operator) {
                case "+": return 2;
                case "-": return 2;
                case "*": return 1;
                case "/": return 1;
                default: return 0;
            }
        }

        public int eval(int left, int right) {
            switch (operator) {
                case "+": return left + right;
                case "-": return left - right;
                case "*": return left * right;
                case "/": return left / right;
                default: return 0;
            }
        }

        public String getOperator() {
            return operator;
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
    }

    private static class Variable extends Token {
        private final String handle;

        public Variable(String handle) {
            super(TokenType.VARIABLE);
            this.handle = handle;
        }

        public int getValue(Map<String, Integer> localVars) {
            return localVars.get(this.handle);
        }
    }

    private static enum TokenType {
        VARIABLE,
        CONSTANT,
        OPERATOR,
        FUNCTION,
        LPARENTH,
        RPARENTH;
    }

    public static class ExpressionFormatException extends RuntimeException {

    }

    public static class MissingVariableException extends RuntimeException {

    }

    public static class MissingFunctionException extends RuntimeException {

    }

    private abstract class MathExpr {
        public abstract int eval();
    }

    private class NumericConstant extends MathExpr {
        private final int value;

        public NumericConstant(int value) {
            this.value = value;
        }

        @Override
        public int eval() {
            return value;
        }
    }

    private class BinaryOperator extends MathExpr {
        private final String operator;
        private final MathExpr left, right;

        public BinaryOperator(String operator, MathExpr left, MathExpr right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }


        @Override
        public int eval() {
            int left = this.left.eval(), right = this.right.eval();

            switch (operator) {
                case "+": return left + right;
                case "-": return left - right;
                case "*": return left * right;
                case "/": return left / right;
                default: return 0;
            }
        }
    }


    private static enum OpType {
        POW ("^", 4, Assoc.RIGHT),
        MUL ("*", 3, Assoc.LEFT),
        DIV ("/", 3, Assoc.LEFT),
        ADD ("+", 2, Assoc.LEFT),
        SUB ("-", 2, Assoc.LEFT);

        private static final Map<String, OpType> opNames = new HashMap<>();

        private String opChar;
        private int precedence;
        private Assoc assoc;

        OpType(String opChar, int precedence, Assoc assoc) {
            this.assoc = assoc;
            this.precedence = precedence;
            this.assoc = assoc;

            register();
        }

        private void register() {
            OpType.opNames.put(opChar, this);
        }
    }

    private static enum Assoc {
        LEFT, RIGHT
    }
}
