package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButtonListenerMethod();
    }

    public void myButtonListenerMethod() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText heightText = findViewById(R.id.heightInput);
                String heightStr = heightText.getText().toString();
                double height = Double.parseDouble(heightStr);

                EditText weightText = findViewById(R.id.weightInput);
                String weightStr = weightText.getText().toString();
                double weight = Double.parseDouble(weightStr);

                double BMI = weight / (height * height);
                DecimalFormat df = new DecimalFormat("#.#");
                double BMI_trimmed = Double.parseDouble(df.format(BMI));

                EditText BMIResult = findViewById(R.id.BMIResult);
                BMIResult.setText(Double.toString(BMI_trimmed));

                // Categories with names + thresholds
                String BMI_Cat = "";
                String prevCat = "None";
                String nextCat = "None";
                double lower = 0, upper = 0;

                if (BMI < 15) {
                    BMI_Cat = "Very severely underweight";
                    nextCat = "Severely underweight";
                    lower = 0;
                    upper = 15;
                } else if (BMI < 16) {
                    BMI_Cat = "Severely underweight";
                    prevCat = "Very severely underweight";
                    nextCat = "Underweight";
                    lower = 15;
                    upper = 16;
                } else if (BMI < 18.5) {
                    BMI_Cat = "Underweight";
                    prevCat = "Severely underweight";
                    nextCat = "Normal";
                    lower = 16;
                    upper = 18.5;
                } else if (BMI < 25) {
                    BMI_Cat = "Normal";
                    prevCat = "Underweight";
                    nextCat = "Overweight";
                    lower = 18.5;
                    upper = 25;
                } else if (BMI < 30) {
                    BMI_Cat = "Overweight";
                    prevCat = "Normal";
                    nextCat = "Obese Class 1 – Moderately Obese";
                    lower = 25;
                    upper = 30;
                } else if (BMI < 35) {
                    BMI_Cat = "Obese Class 1 – Moderately Obese";
                    prevCat = "Overweight";
                    nextCat = "Obese Class 2 – Severely Obese";
                    lower = 30;
                    upper = 35;
                } else if (BMI < 40) {
                    BMI_Cat = "Obese Class 2 – Severely Obese";
                    prevCat = "Obese Class 1 – Moderately Obese";
                    nextCat = "Obese Class 3 – Very Severely Obese";
                    lower = 35;
                    upper = 40;
                } else {
                    BMI_Cat = "Obese Class 3 – Very Severely Obese";
                    prevCat = "Obese Class 2 – Severely Obese";
                    lower = 40;
                    upper = Double.MAX_VALUE;
                }

                TextView BMICategory = findViewById(R.id.BMICategory);
                BMICategory.setText(BMI_Cat);

                // How much to move to reach prev or next
                double toLower = (lower == 0) ? 0 : BMI_trimmed - lower;
                double toUpper = (upper == Double.MAX_VALUE) ? 0 : upper - BMI_trimmed;

                String msg = "Your BMI category: " + BMI_Cat + "\n\n";

                if (toLower > 0 && !prevCat.equals("None")) {
                    msg += "You need to lose " + df.format(toLower) + " BMI to reach the previous category (" + prevCat + ").\n";
                }
                if (toUpper > 0 && !nextCat.equals("None")) {
                    msg += "You need to gain " + df.format(toUpper) + " BMI to reach the next category (" + nextCat + ").";
                }
                if (msg.equals("")) {
                    msg = "You are already at the extreme category.";
                }

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("BMI Info")
                        .setMessage(msg)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
