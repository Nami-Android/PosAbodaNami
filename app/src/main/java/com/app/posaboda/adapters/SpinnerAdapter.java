package com.app.posaboda.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.app.posaboda.R;
import com.app.posaboda.databinding.SpinnerRowBinding;
import com.app.posaboda.models.DeptTypeBrandModel;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private List<DeptTypeBrandModel> list;
    private Context context;
    private LayoutInflater inflater;

    public SpinnerAdapter(List<DeptTypeBrandModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") SpinnerRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.spinner_row, parent, false);
        binding.setTitle(list.get(position).getTitle());
        return binding.getRoot();
    }
}
