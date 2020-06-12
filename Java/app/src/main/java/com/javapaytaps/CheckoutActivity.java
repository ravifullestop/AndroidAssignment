package com.javapaytaps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.javapaytaps.databinding.ActivityCheckoutBinding;
import com.javapaytaps.model.DataModel;
import com.javapaytaps.utility.Constant;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.util.Random;

public class CheckoutActivity extends AppCompatActivity {
    ActivityCheckoutBinding view;
    Context context = this;
    String itemId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_checkout);

        setOnClickListeners();
    }

    /**
     * This method is used to set click listeners on views
     */
    private void setOnClickListeners() {
        view.tvCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    /**
     * Empty field validation check
     */
    private void checkValidation() {
        if (view.edName.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter name.", Toast.LENGTH_SHORT).show();
        } else if (view.edEmail.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter email.", Toast.LENGTH_SHORT).show();
        } else if (view.edPhoneNumber.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
        } else if (view.edAmount.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter price.", Toast.LENGTH_SHORT).show();
        } else if (view.edShippingAddress.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter shipping address.", Toast.LENGTH_SHORT).show();
        } else if (view.edState.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter shipping state.", Toast.LENGTH_SHORT).show();
        } else if (view.edCity.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter shipping city.", Toast.LENGTH_SHORT).show();
        } else if (view.edBillingAddress.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter billing address.", Toast.LENGTH_SHORT).show();
        } else if (view.edBillingState.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter billing state.", Toast.LENGTH_SHORT).show();
        } else if (view.edBillingCity.getText().toString().isEmpty()) {
            Toast.makeText(CheckoutActivity.this, "Enter billing city.", Toast.LENGTH_SHORT).show();
        } else {
            getPayment();
        }
    }

    private void getPayment() {
        int i = new Random().nextInt(900000) + 100000;
        itemId = "" + i;
        Intent in = new Intent(getApplicationContext(), PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, Constant.MERCHANT_EMAIL);
        in.putExtra(PaymentParams.SECRET_KEY, Constant.SECRET_KEY);
        in.putExtra(PaymentParams.LANGUAGE, Constant.LANGUAGE);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, view.edName.getText().toString());
        in.putExtra(PaymentParams.AMOUNT, Double.parseDouble(view.edAmount.getText().toString()));
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, view.edPhoneNumber.getText().toString());
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, view.edEmail.getText().toString());

        in.putExtra(PaymentParams.CURRENCY_CODE, Constant.CURRENCY_CODE);
        in.putExtra(PaymentParams.ORDER_ID, itemId);
        in.putExtra(PaymentParams.PRODUCT_NAME, "Sample Test product");

        in.putExtra(PaymentParams.ADDRESS_BILLING, view.edBillingAddress.getText().toString());
        in.putExtra(PaymentParams.COUNTRY_BILLING, Constant.COUNTRY_BILLING);
        in.putExtra(PaymentParams.CITY_BILLING, view.edBillingCity.getText().toString());
        in.putExtra(PaymentParams.STATE_BILLING, view.edBillingState.getText().toString());
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, Constant.POSTAL_CODE);

        in.putExtra(PaymentParams.ADDRESS_SHIPPING, view.edShippingAddress.getText().toString());
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, Constant.COUNTRY_BILLING);
        in.putExtra(PaymentParams.CITY_SHIPPING, view.edCity.getText().toString());
        in.putExtra(PaymentParams.STATE_SHIPPING, view.edState.getText().toString());
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, Constant.POSTAL_CODE);

        //IS_PREAUTH
        //We have enabled the Pre-Auth but since it is not enabled in merchant panel it is not allowing to do. So we have commented the code.
        //in.putExtra(PaymentParams.IS_PREAUTH, true);

        //Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);

        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            if ("100".equals(data.getStringExtra(PaymentParams.RESPONSE_CODE))) {
                Toast.makeText(CheckoutActivity.this, "The transaction is successful", Toast.LENGTH_SHORT).show();
                if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {

                    DataModel model = new DataModel();
                    model.setOrderID(itemId);
                    model.setTransactionId(data.getStringExtra(PaymentParams.TRANSACTION_ID));
                    model.setToken(data.getStringExtra(PaymentParams.TOKEN));
                    model.setEmail(data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                    model.setNumber(view.edPhoneNumber.getText().toString());
                    model.setTransactionTitle(view.edName.getText().toString());
                    model.setAmount("" + view.edAmount.getText().toString() + " BHD");

                    view.edName.setText("");
                    view.edEmail.setText("");
                    view.edPhoneNumber.setText("");
                    view.edAmount.setText("");
                    view.edShippingAddress.setText("");
                    view.edCity.setText("");
                    view.edState.setText("");
                    view.edBillingAddress.setText("");
                    view.edBillingState.setText("");
                    view.edBillingCity.setText("");

                    //// Start the activity to show the payment result
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("model", model);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(CheckoutActivity.this, data.getStringExtra(PaymentParams.RESULT_MESSAGE), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
