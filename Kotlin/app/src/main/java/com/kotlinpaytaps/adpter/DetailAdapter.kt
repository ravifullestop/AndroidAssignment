package com.kotlinpaytaps.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinpaytaps.R
import com.kotlinpaytaps.model.DataModel

import java.util.ArrayList

class DetailAdapter(
    internal var context: Context,
    internal var arrayList: ArrayList<DataModel>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.inflate_detail_list_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        val model = arrayList[pos]

        viewHolder.tv_title.text = model.title
        viewHolder.tv_value.text = model.value
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    /**
     * View Holder for represent the single Row views
     */
    inner class ViewHolder(internal var layout: View) : RecyclerView.ViewHolder(layout) {
        internal var tv_title: TextView
        internal var tv_value: TextView

        init {
            tv_title = layout.findViewById(R.id.tv_title)
            tv_value = layout.findViewById(R.id.tv_value)
        }
    }

    companion object {
        private val TAG = DetailAdapter::class.java.simpleName
    }

}
