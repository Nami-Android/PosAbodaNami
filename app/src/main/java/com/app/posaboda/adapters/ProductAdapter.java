package com.app.posaboda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_home.fragments.Fragment_Home;
import com.app.posaboda.databinding.ProductRowBinding;
import com.app.posaboda.databinding.TypeRowBinding;
import com.app.posaboda.models.DeptTypeBrandModel;
import com.app.posaboda.models.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    private List<ProductModel> list;
    private Context context;
    private Fragment_Home fragment_home;


    public ProductAdapter(List<ProductModel> list, Context context, Fragment_Home fragment_home) {
        this.list = list;
        this.context = context;
        this.fragment_home = fragment_home;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.imageIncrement.setOnClickListener(v -> {
            ProductModel productModel = list.get(holder.getAdapterPosition());
            int count = productModel.getCount();
            count++;
            if (count<=productModel.getAmount()){
                productModel.setCount(count);
                list.set(holder.getAdapterPosition(),productModel);
                notifyItemChanged(holder.getAdapterPosition());
                fragment_home.addUpdateItemCart(productModel);
            }
        });

        holder.binding.imageDecrement.setOnClickListener(v -> {
            ProductModel productModel = list.get(holder.getAdapterPosition());
            int count = productModel.getCount();
            if (count>0){
                count--;
                productModel.setCount(count);
                list.set(holder.getAdapterPosition(),productModel);
                notifyItemChanged(holder.getAdapterPosition());
                fragment_home.addUpdateItemCart(productModel);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductRowBinding binding;

        public MyHolder(ProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }


}
