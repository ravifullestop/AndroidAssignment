package com.kotlinpaytaps

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kotlinpaytaps.model.DetailModel
import com.kotlinpaytaps.utils.Constant
import java.util.*
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    val context = this
    var orderId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        init()
    }


    private fun init() {
        setOnClickListeners()
    }


    /**
     * This method is used to set click listeners on views
     */
    private fun setOnClickListeners() {
        tvCheckOut.setOnClickListener { v -> checkValidation() }
    }

    /**
     * Empty field validation check
     */
    private fun checkValidation() {
        if (ed_name.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter name.", Toast.LENGTH_SHORT).show()
        } else if (ed_email.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter email.", Toast.LENGTH_SHORT).show()
        } else if (ed_phone_number.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter phone number.", Toast.LENGTH_SHORT).show()
        } else if (ed_amount.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter amount.", Toast.LENGTH_SHORT).show()
        } else if (ed_shipping_address.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter shipping address.", Toast.LENGTH_SHORT).show()
        } else if (ed_state.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter shipping state.", Toast.LENGTH_SHORT).show()
        } else if (ed_city.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter shipping city.", Toast.LENGTH_SHORT).show()
        } else if (ed_billing_address.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter billing address.", Toast.LENGTH_SHORT).show()
        } else if (ed_billing_state.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter billing state.", Toast.LENGTH_SHORT).show()
        } else if (ed_billing_city.text.toString().isEmpty()) {
            Toast.makeText(context, "Enter billing city.", Toast.LENGTH_SHORT).show()
        } else {
            makePayment()
        }
    }

    private fun makePayment() {
        val i = Random().nextInt(900000) + 100000
        orderId = "" + i
        val intent = Intent(applicationContext, PayTabActivity::class.java)
        intent.putExtra(PaymentParams.MERCHANT_EMAIL, Constant.MERCHANT_EMAIL)
        intent.putExtra(PaymentParams.SECRET_KEY, Constant.SECRET_KEY)
        intent.putExtra(PaymentParams.LANGUAGE, Constant.LANGUAGE)
        intent.putExtra(PaymentParams.TRANSACTION_TITLE, ed_name.text.toString())
        intent.putExtra(
            PaymentParams.AMOUNT,
            java.lang.Double.parseDouble(ed_amount.text.toString())
        )
        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, ed_phone_number.text.toString())
        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, ed_email.text.toString())

        intent.putExtra(PaymentParams.CURRENCY_CODE, Constant.CURRENCY_CODE)
        intent.putExtra(PaymentParams.ORDER_ID, orderId)
        intent.putExtra(PaymentParams.PRODUCT_NAME, "Sample Test product")

        intent.putExtra(PaymentParams.ADDRESS_BILLING, ed_billing_address.text.toString())
        intent.putExtra(PaymentParams.COUNTRY_BILLING, Constant.COUNTRY_BILLING)
        intent.putExtra(PaymentParams.CITY_BILLING, ed_billing_city.text.toString())
        intent.putExtra(PaymentParams.STATE_BILLING, ed_billing_state.text.toString())
        intent.putExtra(PaymentParams.POSTAL_CODE_BILLING, Constant.POSTAL_CODE)

        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, ed_shipping_address.text.toString())
        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, Constant.COUNTRY_BILLING)
        intent.putExtra(PaymentParams.CITY_SHIPPING, ed_city.text.toString())
        intent.putExtra(PaymentParams.STATE_SHIPPING, ed_state.text.toString())
        intent.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, Constant.POSTAL_CODE)

        //PREAUTH
        //We have enabled the Pre-Auth but since it is not enabled in merchant panel it is not allowing to do. So we have commented the code.
        //intent.putExtra(PaymentParams.IS_PREAUTH, true)

        //Tokenization
        intent.putExtra(PaymentParams.IS_TOKENIZATION, true)

        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            if ("100" == data!!.getStringExtra(PaymentParams.RESPONSE_CODE)) {
                Toast.makeText(context, "The transaction is successful", Toast.LENGTH_SHORT).show()
                if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN)!!.isEmpty()) {
                    /////Set the payment result data in an Response model
                    val model = DetailModel()
                    model.orderID = orderId
                    model.transactionId = data.getStringExtra(PaymentParams.TRANSACTION_ID)
                    model.token = data.getStringExtra(PaymentParams.TOKEN)
                    model.email = data.getStringExtra(PaymentParams.CUSTOMER_EMAIL)
                    model.number = ed_phone_number.text.toString()
                    model.transactionId = ed_name.text.toString()
                    model.amount = "" + ed_amount.text.toString() + " BHD"

                    ed_name.setText("")
                    ed_email.setText("")
                    ed_phone_number.setText("")
                    ed_amount.setText("")
                    ed_shipping_address.setText("")
                    ed_city.setText("")
                    ed_state.setText("")
                    ed_billing_address.setText("")
                    ed_billing_state.setText("")
                    ed_billing_city.setText("")

                    //// Start the activity to show the payment result
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("model", model)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(
                    context,
                    data.getStringExtra(PaymentParams.RESULT_MESSAGE),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
