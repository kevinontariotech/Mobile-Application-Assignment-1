package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button to call the calculation function
        final Button calculate = findViewById(R.id.Calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateMortgage();
            }
        });
        //Button to call the reset function
        final Button reset = findViewById(R.id.Reset);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetPage();
            }
        });
    }


    private void calculateMortgage() {
        //
        EditText mortgageValue = (EditText) findViewById(R.id.mortgageValue);
        EditText interestValue = (EditText) findViewById(R.id.interestValue);
        EditText periodValue = (EditText) findViewById(R.id.periodValue);

        //getting the values into usable double values
        double mortgage = getValue(mortgageValue);
        double interest = getValue(interestValue);
        int period = (int)getValue(periodValue);

        //check if fields are empty
        if (!isEmpty(mortgageValue) && !isEmpty(interestValue) && !isEmpty(periodValue)) {
            double monthly_payments = calculateMonthlyPayment(mortgage, interest, period);
            updateText(monthly_payments);
        }
    }
    //check if edittexts are empty
    private boolean isEmpty(EditText text){
        String textValue = text.getText().toString();

        if(TextUtils.isEmpty(textValue)) {
            return true;
        }
        return false;
    }
    //put edittext values into into integer variables
    private double getValue(EditText text) {
        //get values from edittexts

        //check if the boxes are empty
        if (!isEmpty(text)) {
            //get the values into the variables
            return Double.parseDouble(text.getText().toString());
        } else {
            Toast.makeText(MainActivity.this, "No Empty Fields Allowed", Toast.LENGTH_SHORT).show();
        }
        return 0.0;
    }

    private double calculateMonthlyPayment(double loan_amount, double annual_interestRate, int loan_term) {
        double monthlyInterestRate = annual_interestRate / 12 / 100;
        int numberOfPayments = loan_term * 12;
        return loan_amount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments))
                / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);
    }

    private void updateText(double monthlyPayment){
        TextView monthlyPaymentView = findViewById(R.id.paymentsValue);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format(monthlyPayment);
        monthlyPaymentView.setText(formattedValue);
    }

    private void resetPage(){
        EditText mortgageValue = (EditText) findViewById(R.id.mortgageValue);
        EditText interestValue = (EditText) findViewById(R.id.interestValue);
        EditText periodValue = (EditText) findViewById(R.id.periodValue);
        TextView monthlyPaymentView = findViewById(R.id.paymentsValue);

        mortgageValue.setText("");
        interestValue.setText("");
        periodValue.setText("");
        monthlyPaymentView.setText("");
    }

}