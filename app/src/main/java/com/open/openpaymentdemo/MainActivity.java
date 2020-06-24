package com.open.openpaymentdemo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.open.open_web_sdk.OpenPayment;
import com.open.open_web_sdk.listener.PaymentStatusListener;
import com.open.open_web_sdk.model.TransactionDetails;

public class MainActivity extends AppCompatActivity implements PaymentStatusListener {

    private String mPaymentToken = "sb_pt_BRnS2ttBJTs3vte";
    private String mAccessKey = "b0adaaa0-be66-11e9-ac5e-3d22d7faebc8";

    private TextView mTextViewResponseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResponseText = findViewById(R.id.responseText);
        View buttonPay = findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewResponseText.setText("");
                setPaymentView(mPaymentToken, mAccessKey);
            }
        });
    }

    private void setPaymentView(String paymentToken, String accessKey) {
        OpenPayment openPayment = new OpenPayment.Builder()
                .with(this)
                .setPaymentToken(paymentToken)
                .setAccessKey(accessKey)
                .setEnvironment(OpenPayment.Environment.SANDBOX)
                .build();

        openPayment.setPaymentStatusListener(this);

        openPayment.startPayment();
    }

    @Override
    public void onTransactionCompleted(final TransactionDetails transactionDetails) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String response = "On Transaction Completed:\n" +
                        "\n Payment Id: " + transactionDetails.paymentId +
                        "\n Payment Token Id: " + transactionDetails.paymentTokenId +
                        "\n Status: " + transactionDetails.status;

                mTextViewResponseText.setText(response);
            }
        });
    }

    @Override
    public void onError(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextViewResponseText.setText(String.format("onError: \n%s", s));
            }
        });
    }
}