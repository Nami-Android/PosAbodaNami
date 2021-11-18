package com.app.posaboda.activities_fragments.activity_home.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_home.HomeActivity;
import com.app.posaboda.adapters.BrandAdapter;
import com.app.posaboda.adapters.ProductAdapter;
import com.app.posaboda.adapters.SpinnerAdapter;
import com.app.posaboda.adapters.StockAdapter;
import com.app.posaboda.adapters.TypeAdapter;
import com.app.posaboda.cart_models.CartItemModel;
import com.app.posaboda.cart_models.ManageCartModel;
import com.app.posaboda.databinding.FragmentHomeBinding;
import com.app.posaboda.models.DeptTypeBrandDataModel;
import com.app.posaboda.models.DeptTypeBrandModel;
import com.app.posaboda.models.ProductDataModel;
import com.app.posaboda.models.ProductModel;
import com.app.posaboda.models.SaleOrderDataModel;
import com.app.posaboda.models.StockDataModel;
import com.app.posaboda.models.StockModel;
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

public class Fragment_Home extends Fragment {

    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
    private List<DeptTypeBrandModel> categoryList, typeList, brandModelList;
    private SpinnerAdapter categoryAdapter;
    private List<StockModel> stockModelList;
    private StockAdapter stockAdapter;
    private TypeAdapter typeAdapter;
    private BrandAdapter brandAdapter;
    private ProductAdapter productAdapter;
    private List<ProductModel> productModelList;
    private String query = null;
    private int stock_id = 0;
    private String category_id = null;
    private String type_id = null;
    private String brand_id = null;
    private Call<ProductDataModel> call;
    private ManageCartModel manageCartModel;

    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initView();

        return binding.getRoot();
    }

    public void initView() {
        manageCartModel = ManageCartModel.newInstance();
        categoryList = new ArrayList<>();
        typeList = new ArrayList<>();
        brandModelList = new ArrayList<>();
        stockModelList = new ArrayList<>();
        productModelList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);

        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        categoryList.add(new DeptTypeBrandModel(0, getString(R.string.ch_dept)));
        categoryAdapter = new SpinnerAdapter(categoryList, activity);
        binding.spinnerCategory.setAdapter(categoryAdapter);

        stockAdapter = new StockAdapter(stockModelList, activity);
        binding.spinnerStock.setAdapter(stockAdapter);

        binding.spinnerStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stock_id = stockModelList.get(position).getId();

                getProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (categoryList.get(position).getId() == 0) {
                    category_id = null;
                } else {
                    category_id = String.valueOf(categoryList.get(position).getId());

                }
                getProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.recViewType.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        typeAdapter = new TypeAdapter(typeList, activity, this);
        binding.recViewType.setAdapter(typeAdapter);

        binding.recViewBrand.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        brandAdapter = new BrandAdapter(brandModelList, activity, this);
        binding.recViewBrand.setAdapter(brandAdapter);

        boolean isDeviceTablet = getDeviceType();
        if (isDeviceTablet) {
            binding.recViewProducts.setLayoutManager(new GridLayoutManager(activity, 3));
        } else {
            binding.recViewProducts.setLayoutManager(new GridLayoutManager(activity, 2));


        }
        productAdapter = new ProductAdapter(productModelList, activity, this);
        binding.recViewProducts.setAdapter(productAdapter);

        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                getProducts();
            }
            return false;
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()==0){
                    getProducts();
                }
            }
        });

        getStock();
        getCategory();
        getType();
        getBrand();

    }


    private boolean getDeviceType() {
        boolean xLarge = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return xLarge || large;

    }

    private void getCategory() {
        Api.getService(Tags.base_url)
                .getCategory()
                .enqueue(new Callback<DeptTypeBrandDataModel>() {
                    @Override
                    public void onResponse(Call<DeptTypeBrandDataModel> call, Response<DeptTypeBrandDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 && response.body() != null) {
                                categoryList.addAll(response.body().getData());
                                activity.runOnUiThread(() -> categoryAdapter.notifyDataSetChanged());
                            }
                        } else {

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
                    public void onFailure(Call<DeptTypeBrandDataModel> call, Throwable t) {
                        try {

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

    private void getStock() {
        Api.getService(Tags.base_url)
                .getStock()
                .enqueue(new Callback<StockDataModel>() {
                    @Override
                    public void onResponse(Call<StockDataModel> call, Response<StockDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 && response.body() != null) {
                                stockModelList.addAll(response.body().getData());
                                activity.runOnUiThread(() -> stockAdapter.notifyDataSetChanged());
                            }
                        } else {

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
                    public void onFailure(Call<StockDataModel> call, Throwable t) {
                        try {

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

    private void getType() {
        Api.getService(Tags.base_url)
                .getCategory()
                .enqueue(new Callback<DeptTypeBrandDataModel>() {
                    @Override
                    public void onResponse(Call<DeptTypeBrandDataModel> call, Response<DeptTypeBrandDataModel> response) {
                        binding.progBarType.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 && response.body() != null) {
                                if (response.body().getData().size() > 0) {
                                    typeList.clear();
                                    typeList.addAll(response.body().getData());
                                    typeAdapter.notifyDataSetChanged();
                                    binding.flType.setVisibility(View.VISIBLE);

                                } else {
                                    binding.flType.setVisibility(View.GONE);

                                }

                            }
                        } else {
                            binding.progBarType.setVisibility(View.GONE);

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
                    public void onFailure(Call<DeptTypeBrandDataModel> call, Throwable t) {
                        try {
                            binding.progBarType.setVisibility(View.GONE);

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

    private void getBrand() {
        Api.getService(Tags.base_url)
                .getBrand()
                .enqueue(new Callback<DeptTypeBrandDataModel>() {
                    @Override
                    public void onResponse(Call<DeptTypeBrandDataModel> call, Response<DeptTypeBrandDataModel> response) {
                        binding.progBarBrand.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 && response.body() != null) {
                                if (response.body().getData().size() > 0) {
                                    brandModelList.clear();
                                    brandModelList.addAll(response.body().getData());
                                    brandAdapter.notifyDataSetChanged();
                                    binding.flBrand.setVisibility(View.VISIBLE);

                                } else {
                                    binding.flBrand.setVisibility(View.GONE);

                                }

                            }
                        } else {
                            binding.progBarBrand.setVisibility(View.GONE);

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
                    public void onFailure(Call<DeptTypeBrandDataModel> call, Throwable t) {
                        try {
                            binding.progBarBrand.setVisibility(View.GONE);

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

    private void getProducts() {
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBarProduct.setVisibility(View.VISIBLE);
        productModelList.clear();
        productAdapter.notifyDataSetChanged();

         query = binding.edtSearch.getText().toString();
        if (query.isEmpty()) {
            query = null;
        }

        if (call!=null){
            call.cancel();
        }

        call = Api.getService(Tags.base_url)
                .getProducts("Bearer " + userModel.getData().getToken(), stock_id, category_id, type_id, brand_id, query);

        call.enqueue(new Callback<ProductDataModel>() {
            @Override
            public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                binding.progBarProduct.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 200 && response.body() != null) {
                        if (response.body().getData().size() > 0) {
                            productModelList.clear();
                            productModelList.addAll(response.body().getData());
                            productAdapter.notifyDataSetChanged();
                            binding.tvNoData.setVisibility(View.GONE);

                        } else {
                            binding.tvNoData.setVisibility(View.VISIBLE);

                        }

                    }
                } else {
                    binding.progBarProduct.setVisibility(View.GONE);

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
            public void onFailure(Call<ProductDataModel> call, Throwable t) {
                try {
                    binding.progBarProduct.setVisibility(View.GONE);

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

    public void setTypeItemData(DeptTypeBrandModel model) {
        if (model.isSelected()){
            type_id = String.valueOf(model.getId());
        }else {
            type_id = null;
        }
        getProducts();
    }

    public void setBrandItemData(DeptTypeBrandModel model) {
        if (model.isSelected()){
            brand_id = String.valueOf(model.getId());
        }else {
            brand_id = null;
        }
        getProducts();
    }

    public void addUpdateItemCart(ProductModel productModel) {
        double total_item_price = productModel.getCount()*productModel.getItem_price_for_cal();
        CartItemModel cartItemModel = new CartItemModel(productModel.getId(),productModel.getWarehouse_id(),productModel.getCount(),productModel.getTitle(),productModel.getMain_image(),productModel.getItem_price_for_cal(),productModel.getAmount(),total_item_price);
        manageCartModel.addItem(cartItemModel,activity);
        activity.updateCartCount(manageCartModel.getCartDataModel(activity).getItems().size());

    }
}
