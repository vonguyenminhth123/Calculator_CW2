package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTV, solutionTV;
    MaterialButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn_divide, btn_multiply,
            btn_subtract, btn_add, btnC, btn_equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTV = (TextView) findViewById(R.id.tv_result);
        solutionTV = (TextView) findViewById(R.id.tv_solution);

        assignId(btn0, R.id.button_0);
        assignId(btn1,R.id.button_1);
        assignId(btn2,R.id.button_2);
        assignId(btn3,R.id.button_3);
        assignId(btn4,R.id.button_4);
        assignId(btn5,R.id.button_5);
        assignId(btn6,R.id.button_6);
        assignId(btn7,R.id.button_7);
        assignId(btn8,R.id.button_8);
        assignId(btn9,R.id.button_9);
        assignId(btnC,R.id.button_c);
        assignId(btn_equal,R.id.button_equal);
        assignId(btn_divide,R.id.button_divide);
        assignId(btn_multiply,R.id.button_multiply);
        assignId(btn_subtract,R.id.button_subtract);
        assignId(btn_add,R.id.button_add);
    }
    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();
        if (dataToCalculate.startsWith("*") || dataToCalculate.startsWith("/")){
            Toast toast= Toast.makeText(MainActivity.this,"Invalid Input",Toast.LENGTH_LONG);
            toast.show();
            solutionTV.setText("");
            return;
        } else if (buttonText.equals("=")){
            solutionTV.setText(resultTV.getText());
            return;
        } else if (buttonText.equals("C")){
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTV.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")){
            resultTV.setText(finalResult);
        }
    }
    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript",
                    1, null).toString();
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        } catch (Exception e){
            return "Err";
        }
    }
}