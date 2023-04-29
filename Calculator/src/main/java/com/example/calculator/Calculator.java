package com.example.calculator;

import java.util.Stack;
/*
Hamza Riaz
414577
BSCS12-C
*/




public class Calculator {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.run();

    }

    public static Object evaluate(String expression) {
        try {
            // stack of numbers with type double
            Stack<Double> numbers = new Stack<>();
            // stack of operators with type character
            Stack<Character> operators = new Stack<>();

            // tokenizing expression
            for (int i = 0; i < expression.length(); i++) {

                // assigning characters to c one at a time
                char c = expression.charAt(i);

                // if character is a digit, append to String sb. This includes decimal digits too
                if (Character.isDigit(c) || c == '.' ) {
                    StringBuilder sb = new StringBuilder();
                    // starting another loop that iterates over the remaining characters inside the expression
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        sb.append(expression.charAt(i++));
                    }
                    // if we encounter a closing parenthesis, we want to stop and evaluate the expression till there, so we decrement i by 1
                    i--;

                    // converting to double
                    double num = Double.parseDouble(sb.toString());
                    // pushing it into the stack of numbers
                    numbers.push(num);

                }
                else if (c == '(')
                {
                    // check if previous character is a number, and if it is, add a multiplication operator
                    if (i > 0 && Character.isDigit(expression.charAt(i-1)))
                    {
                        operators.push('*');
                    }
                    // if character c is left parenthesis, push it into the operators stack
                    operators.push(c);
                }
                // if character c is the right parenthesis, we pop off the last and second last numbers added into the numbers stack (on the top in stack),
                // along with the last operator added into the operator stack. Then we perform the required operation on it.
                else if (c == ')')
                {
                    if (i < expression.length() - 1 && Character.isDigit(expression.charAt(i+1)))
                    {
                        operators.push('*');
                    }
                    // while the opening parenthesis isn't reached (inside the stack)
                    while (operators.peek() != '(')
                    {
                        double result = performOperation(numbers.pop(), numbers.pop(), operators.pop());
                        // replacing the expression in ( ) with the result
                        numbers.push(result);
                    }
                    // after operation is done, we remove the last operator
                    operators.pop();
                }
                // if the character c is an operator, we repeatedly pop off operators and operands,
                // calculate result and push it back into number stack
                else if (c == '+' || c == '-' || c == '*' || c == '/')
                {
                    // Handle unary minus
                    // if we are at first character or the last character was '(' or any operator, add the minus to the String sb and then the remaining numbers
                    // (including decimals)
                    if (i == 0 || expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-' || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/') {
                        StringBuilder sb = new StringBuilder();
                        sb.append('-');
                        // going to the next character to get rest of the part
                        i++;
                        while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.'))
                        {
                            sb.append(expression.charAt(i++));
                        }
                        // coming back so that the next loop continues from where the last loop left
                        i--;
                        double num = Double.parseDouble(sb.toString());
                        numbers.push(num);
                    }
                    else
                    {
                        // first operator
                        if (operators.isEmpty() || operators.peek() == '(') {
                            operators.push(c);
                        }
                        // if current operator has equal or lower precedence than the one highest in stack, it pops it off and uses it to perform the operation between the two highest numbers
                        // if that isn't the case (c has higher precedence), it just pushes the operator into the stack
                        else {
                            while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                                double result = performOperation(numbers.pop(), numbers.pop(), operators.pop());
                                numbers.push(result);
                            }


                            operators.push(c);
                        }

                    }
                }
            }

            // performs all the operations until there are no more operators left
            while (!operators.isEmpty()) {
                double result = performOperation(numbers.pop(), numbers.pop(), operators.pop());
                numbers.push(result);
            }
            // last remaining number in numbers stack is the result, which is returned
            return numbers.pop();

        }
        // error handling at runtime
        catch (RuntimeException e) {
            if (expression.contains("/0"))
            {
                return ("MATH ERROR");
            }
             else return ("INCORRECT SYNTAX");
        }
    }



    private static boolean hasPrecedence(char op1, char op2) {
        // op1 will not have precedence over parenthesis
        if (op2 == '(' || op2 == ')')  return false;

        // if op1 is * or / AND op2 is either + or -, op2 will not have precedence over op1
        else if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))  return false;


        // if none of these conditions are present, then there will be no precedence, so true is returned
        else return true;
    }

    private static double performOperation(double num2, double num1, char operator) {
        // cases for simple operations
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return num1 / num2;
        }
        return 0;
    }


}


