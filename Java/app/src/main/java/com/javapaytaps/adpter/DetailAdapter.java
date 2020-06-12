package com.javapaytaps.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javapaytaps.R;
import com.javapaytaps.model.DetailModel;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private static final String TAG = DetailAdapter.class.getSimpleName();
    Context context;
    ArrayList<DetailModel> arrayList;

    public DetailAdapter(Context context, ArrayList<DetailModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflate_detail_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        DetailModel model = arrayList.get(pos);

        viewHolder.tv_title.setText(model.getTitle());
        viewHolder.tv_value.setText(model.getValue());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * View Holder for represent the single Row views
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_value;
        View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            tv_title = layout.findViewById(R.id.tv_title);
            tv_value = layout.findViewById(R.id.tv_value);
        }
    }

}
