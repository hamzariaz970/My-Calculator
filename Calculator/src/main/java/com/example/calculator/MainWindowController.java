package com.example.calculator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import javax.script.ScriptException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {


    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private Label lbResult;
    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot;

    @FXML
    private Button btnPlus, btnMin, btnMul, btnDiv, btnEquals, btnLeftCurly, btnRightCurly, btnDel, btnClr;

    //array of number buttons
    private Button[] numButtons = new Button[11];

    // array of operator buttons
    private Button[] operButtons = new Button[7];

    private int count = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // creating linear gradient
        // Stop[] will create the spectrum of colors (from first given argument to the second given argument)
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#A1C4FD")), new Stop(1, Color.web("#C2E9FB"))};
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        BackgroundFill backgroundFill = new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY);
        myAnchorPane.setBackground(new Background(backgroundFill));

        //#D8B5FF -> #1EAE98
        //#A1C4FD → #C2E9FB
    }

    @FXML
    public void onNumberClicked(MouseEvent event) {
        // initializing array of number buttons
        numButtons[0] = btn0;
        numButtons[1] = btn1;
        numButtons[2] = btn2;
        numButtons[3] = btn3;
        numButtons[4] = btn4;
        numButtons[5] = btn5;
        numButtons[6] = btn6;
        numButtons[7] = btn7;
        numButtons[8] = btn8;
        numButtons[9] = btn9;
        numButtons[10] = btnDot;

        // adding operator buttons to an array
        operButtons[0] = btnPlus;
        operButtons[1] = btnMin;
        operButtons[2] = btnMul;
        operButtons[3] = btnDiv;
        operButtons[4] = btnEquals;
        operButtons[5] = btnLeftCurly;
        operButtons[6] = btnRightCurly;


        // clearing label if it was default 0.0 or had one of the errors
        if (lbResult.getText().equals("0.0") || lbResult.getText().equals("MATH ERROR") || lbResult.getText().equals("SYNTAX ERROR")) {
            lbResult.setText("");
        }

        // adding number pressed to label
        for (int i = 0; i < numButtons.length; i++) {
            if (event.getSource() == numButtons[i]) {
                lbResult.setText(lbResult.getText() + numButtons[i].getText());
            }
        }


    }

    @FXML
    public void onSymbolClicked(MouseEvent event) {
        for (int i = 0; i < operButtons.length; i++) {
            // getting text from array of buttons
            if (event.getSource() == operButtons[i]) {

                // if curly brackets or +/- was clicked as first input
                if ((operButtons[i].getText().equals("(") || operButtons[i].getText().equals("+") || operButtons[i].getText().equals("-")) && lbResult.getText().equals("0.0"))
                {

                    lbResult.setText(operButtons[i].getText());
                }


                else lbResult.setText(lbResult.getText() + operButtons[i].getText());
            }
        }

    }


    @FXML
    public void equalsResult(MouseEvent event) throws ScriptException {
     String expression = lbResult.getText();

         Object result = Calculator.evaluate(expression);
         if (result.toString().contains("/0"))
        {
            lbResult.setText("MATH ERROR");
        }

        lbResult.setText(result + "");

    }

    @FXML
    public void clearButtonClicked(MouseEvent event) {
        lbResult.setText("0.0");

    }

    @FXML
    public void delButtonClicked(MouseEvent event) {
        String temp = lbResult.getText();
        String temp2 = "";
        for (int i = 0; i < temp.length() - 1; i++) {
            temp2 += temp.charAt(i);
        }

        lbResult.setText(temp2);
        if (lbResult.getText().equals("")) {
            lbResult.setText("0.0");
        }

    }


}