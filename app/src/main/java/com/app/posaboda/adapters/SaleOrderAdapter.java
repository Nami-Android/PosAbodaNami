package com.app.posaboda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_home.fragments.Fragment_Sale_Order;
import com.app.posaboda.databinding.SaleOrderRowBinding;
import com.app.posaboda.models.SaleOrderModel;

import java.util.List;

public class SaleOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SaleOrderModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment_Sale_Order fragment_sale_order;
    public SaleOrderAdapter(List<SaleOrderModel> list, Context context,Fragment_Sale_Order fragment_sale_order) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment_sale_order = fragment_sale_order;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SaleOrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.sale_order_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SaleOrderRowBinding binding;

        public MyHolder(SaleOrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }


}
