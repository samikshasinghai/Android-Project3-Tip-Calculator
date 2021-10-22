package edu.oakland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    EditText billAmountET;
    Button percentUpBtn;
    Button percentDownBtn;
    Button enterBtn;
    TextView tipTV;
    TextView totalTV;
    TextView percentTV;

    String billAmountTxt;
    float tipPercent = 0.15f;

    SharedPreferences savedValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        billAmountET = findViewById(R.id.billAmountEditText);
        percentDownBtn = findViewById(R.id.percentDownButton);
        percentUpBtn = findViewById(R.id.percentUpButton);
        enterBtn = findViewById(R.id.enterButton);
        percentTV = findViewById(R.id.percent);
        tipTV = findViewById(R.id.tip);
        totalTV = findViewById(R.id.total);
        billAmountET.setImeOptions(EditorInfo.IME_ACTION_GO);

        hideSoftKeyboard();

        enterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                calculateAndDisplay();

            }
        });

        percentUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipPercent = tipPercent + 0.01f;
                NumberFormat p = NumberFormat.getPercentInstance();
                percentTV.setText(p.format(tipPercent));
            }
        });

        percentDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipPercent = tipPercent - 0.01f;
                NumberFormat p = NumberFormat.getPercentInstance();
                percentTV.setText(p.format(tipPercent));

            }
        });

        billAmountET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    calculateAndDisplay();
                    return true;
                }
                return false;
            }
        });
    }

    public void calculateAndDisplay()
    {
        billAmountTxt = billAmountET.getText().toString();
        float billAmount;
        if(billAmountTxt.equals(""))
        {
            billAmount = 0;
        }else {
            billAmount = Float.parseFloat(billAmountTxt);
            float tipAmount = billAmount * tipPercent;

            NumberFormat c = NumberFormat.getCurrencyInstance();
            tipTV.setText(c.format(tipAmount));

            float total = billAmount + tipAmount;
            totalTV.setText(c.format(total));

            NumberFormat percent = NumberFormat.getPercentInstance();
            percentTV.setText(percent.format(tipPercent));
        }

    }

    public void hideSoftKeyboard()
    {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}