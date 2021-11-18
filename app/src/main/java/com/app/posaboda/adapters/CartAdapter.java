package com.app.posaboda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_cart.CartActivity;
import com.app.posaboda.activities_fragments.activity_home.fragments.Fragment_Home;
import com.app.posaboda.cart_models.CartItemModel;
import com.app.posaboda.databinding.CartRowBinding;
import com.app.posaboda.databinding.ProductRowBinding;
import com.app.posaboda.models.ProductModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {

    private List<CartItemModel> list;
    private Context context;
    private CartActivity activity;


    public CartAdapter(List<CartItemModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (CartActivity) context;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cart_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        CartItemModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.imageIncrement.setOnClickListener(v -> {
            CartItemModel cartItemModel = list.get(holder.getAdapterPosition());
            int count = cartItemModel.getNeeded_amount();
            count++;
            if (count <= cartItemModel.getStock_amount()) {
                cartItemModel.setNeeded_amount(count);
                list.set(holder.getAdapterPosition(), cartItemModel);
                notifyItemChanged(holder.getAdapterPosition());
                activity.addUpdateItemCart(cartItemModel);
            }
        });

        holder.binding.imageDecrement.setOnClickListener(v -> {
            CartItemModel cartItemModel = list.get(holder.getAdapterPosition());
            int count = cartItemModel.getNeeded_amount();
            if (count > 1) {
                count--;
                cartItemModel.setNeeded_amount(count);
                list.set(holder.getAdapterPosition(), cartItemModel);
                notifyItemChanged(holder.getAdapterPosition());
                activity.addUpdateItemCart(cartItemModel);

            }
        });

        holder.binding.imageDelete.setOnClickListener(v -> {
            CartItemModel cartItemModel = list.get(holder.getAdapterPosition());
            activity.deleteItem(holder.getAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CartRowBinding binding;

        public MyHolder(CartRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }


}
