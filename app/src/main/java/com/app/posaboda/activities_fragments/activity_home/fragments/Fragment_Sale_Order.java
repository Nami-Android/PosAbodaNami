package com.app.posaboda.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_home.HomeActivity;
import com.app.posaboda.activities_fragments.activity_login.LoginActivity;
import com.app.posaboda.adapters.SaleOrderAdapter;
import com.app.posaboda.databinding.FragmentProfileBinding;
import com.app.posaboda.databinding.FragmentSaleOrderBinding;
import com.app.posaboda.models.SaleOrderDataModel;
import com.app.posaboda.models.SaleOrderModel;
import com.app.posaboda.models.UserModel;
import com.app.posaboda.preferences.Preferences;
import com.app.posaboda.remote.Api;
import com.app.posaboda.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Sale_Order extends Fragment {

    private HomeActivity activity;
    private FragmentSaleOrderBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private List<SaleOrderModel> list;
    private SaleOrderAdapter adapter;



    public static Fragment_Sale_Order newInstance() {
        return new Fragment_Sale_Order();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sale_order, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        list = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary));
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new SaleOrderAdapter(list,activity,this);
        binding.recView.setAdapter(adapter);

        getSaleOrder();

        binding.swipeRefresh.setOnRefreshListener(this::getSaleOrder);

    }

    private void getSaleOrder() {
        if (userModel==null){
            binding.swipeRefresh.setRefreshing(false);
            return;
        }


        Api.getService(Tags.base_url)
                .getSaleOrder("Bearer "+userModel.getData().getToken())
                .enqueue(new Callback<SaleOrderDataModel>() {
                    @Override
                    public void onResponse(Call<SaleOrderDataModel> call, Response<SaleOrderDataModel> response) {
                        binding.swipeRefresh.setRefreshing(false);
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200&&response.body()!=null) {
                                if (response.body().getData().size()>0){
                                    binding.tvNoData.setVisibility(View.GONE);
                                    list.clear();
                                    list.addAll(response.body().getData());
                                    adapter.notifyDataSetChanged();
                                }else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            binding.swipeRefresh.setRefreshing(false);
                            binding.progBar.setVisibility(View.GONE);
                            try {
                                Log.e("mmmmmmmmmm", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                // Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("mmmmmmmmmm", response.code() + "");

                                // Toast.makeText(LoginActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SaleOrderDataModel> call, Throwable t) {
                        try {
                            binding.swipeRefresh.setRefreshing(false);
                            binding.progBar.setVisibility(View.GONE);
                            if (t.getMessage() != null) {
                                Log.e("error", t.toString() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    // Toast.makeText(LoginActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });

    }


}
