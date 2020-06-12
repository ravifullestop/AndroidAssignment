package com.kotlinpaytaps

import android.content.Context
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinpaytaps.adpter.DetailAdapter
import com.kotlinpaytaps.model.DataModel
import com.kotlinpaytaps.model.DetailModel
import kotlinx.android.synthetic.main.activity_detail.*

import java.util.ArrayList

class DetailActivity : AppCompatActivity() {

    internal var context: Context = this
    internal var arrayList = ArrayList<DataModel>()
    internal var model: DetailModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        init()
    }

    private fun init() {
        getIntentData()
        setOnClickListeners()
    }

    /**
     * This method is used to set click listeners on views
     */
    private fun setOnClickListeners() {
        iv_back.setOnClickListener { v -> onBackPressed() }
    }


    /***
     * This method is used to get data from the previous activity
     */
    private fun getIntentData() {
        val inte = intent
        model = inte.getSerializableExtra("model") as DetailModel
        if (model != null) {
            arrayList.add(DataModel("Amount:-", model!!.amount))
            arrayList.add(DataModel("Order ID:-", model!!.orderID))
            arrayList.add(DataModel("Transaction Id:-", model!!.transactionId))
            arrayList.add(DataModel("Token:-", model!!.token))
            arrayList.add(DataModel("Transaction by:-", model!!.transactionTitle))
            arrayList.add(DataModel("Email:-", model!!.email))
            arrayList.add(DataModel("Number:-", model!!.number))

            recyclerView.setLayoutManager(
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )
            // Set the data in adapter
            recyclerView.setAdapter(DetailAdapter(context, arrayList))
        }
    }
}
