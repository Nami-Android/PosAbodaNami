package com.app.posaboda.activities_fragments.activity_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Toast;

import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_home.HomeActivity;
import com.app.posaboda.activities_fragments.activity_login.LoginActivity;
import com.app.posaboda.adapters.CartAdapter;
import com.app.posaboda.adapters.ClientSpinnerAdapter;
import com.app.posaboda.cart_models.CartDataModel;
import com.app.posaboda.cart_models.CartItemModel;
import com.app.posaboda.cart_models.ManageCartModel;
import com.app.posaboda.databinding.ActivityCartBinding;
import com.app.posaboda.databinding.ActivitySplashBinding;
import com.app.posaboda.databinding.ClientDialogBinding;
import com.app.posaboda.databinding.DialogAlertBinding;
import com.app.posaboda.language.Language;
import com.app.posaboda.models.AddClientModel;
import com.app.posaboda.models.ClientDataModel;
import com.app.posaboda.models.SaleOrderModel;
import com.app.posaboda.models.SingleClientModel;
import com.app.posaboda.models.StatusResponse;
import com.app.posaboda.models.StockDataModel;
import com.app.posaboda.models.UserModel;
import com.app.posaboda.preferences.Preferences;
import com.app.posaboda.remote.Api;
import com.app.posaboda.share.Common;
import com.app.posaboda.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private ManageCartModel manageCartModel;
    private String lang = "ar";
    private List<CartItemModel> list;
    private CartAdapter adapter;
    private List<SaleOrderModel.Client> clientList;
    private ClientSpinnerAdapter clientSpinnerAdapter;
    private AlertDialog dialog;
    private Preferences preferences;
    private UserModel userModel;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);


        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        clientList = new ArrayList<>();
        SaleOrderModel.Client client = new SaleOrderModel.Client();
        client.setId(0);
        client.setName(getString(R.string.choose_client));
        clientList.add(client);
        list = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        manageCartModel = ManageCartModel.newInstance();
        binding.setLang(lang);
        binding.setTotal(manageCartModel.getTotalCost(this));
        list.addAll(manageCartModel.getCartDataModel(this).getItems());
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(list, this);
        binding.recView.setAdapter(adapter);
        binding.llBack.setOnClickListener(v -> finish());
        createClientDialogAlert();
        getClients();
        binding.btnClient.setOnClickListener(v -> {
            dialog.show();
        });


    }

    private void getClients() {
        Api.getService(Tags.base_url)
                .getClients()
                .enqueue(new Callback<ClientDataModel>() {
                    @Override
                    public void onResponse(Call<ClientDataModel> call, Response<ClientDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 && response.body() != null) {
                                clientList.addAll(response.body().getData());
                                runOnUiThread(() -> clientSpinnerAdapter.notifyDataSetChanged());
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
                    public void onFailure(Call<ClientDataModel> call, Throwable t) {
                        try {

                            if (t.getMessage() != null) {
                                Log.e("error", t.toString() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(CartActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
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

    public void addUpdateItemCart(CartItemModel cartItemModel) {
        double total_item_price = cartItemModel.getNeeded_amount() * cartItemModel.getPrice();
        cartItemModel.setTotal_item_cost(total_item_price);
        manageCartModel.addItem(cartItemModel, this);
        binding.setTotal(manageCartModel.getTotalCost(this));


    }

    public void deleteItem(int adapterPosition) {
        manageCartModel.removeItem(this, manageCartModel.getCartDataModel(this), list.get(adapterPosition).getItem_id());
        list.remove(adapterPosition);
        adapter.notifyItemRemoved(adapterPosition);
        binding.setTotal(manageCartModel.getTotalCost(this));

    }

    private void createClientDialogAlert() {
        dialog = new AlertDialog.Builder(this)
                .create();

        ClientDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.client_dialog, null, false);
        clientSpinnerAdapter = new ClientSpinnerAdapter(clientList, this);
        binding.spinner.setAdapter(clientSpinnerAdapter);
        binding.imageClose.setOnClickListener(v -> dialog.dismiss()

        );
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaleOrderModel.Client client = clientList.get(position);
                if (client.getId() != 0) {
                    dialog.dismiss();
                    addOrder(client.getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AddClientModel addClientModel = new AddClientModel();
        binding.setModel(addClientModel);
        binding.btnAdd.setOnClickListener(v -> {
            if (addClientModel.isDataValid(this)) {
                addClient(addClientModel, binding);
            }
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());

    }

    private void addClient(AddClientModel addClientModel, ClientDialogBinding binding) {
        if (userModel == null) {
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .addClient("Bearer " + userModel.getData().getToken(), addClientModel.getName(), addClientModel.getPhone())
                .enqueue(new Callback<SingleClientModel>() {
                    @Override
                    public void onResponse(Call<SingleClientModel> call, Response<SingleClientModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 && response.body() != null) {
                                binding.setModel(new AddClientModel());
                                clientList.add(1, response.body().getData());
                                runOnUiThread(() -> clientSpinnerAdapter.notifyDataSetChanged());
                            }
                        } else {
                            dialog.dismiss();

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
                    public void onFailure(Call<SingleClientModel> call, Throwable t) {
                        try {
                            dialog.dismiss();

                            if (t.getMessage() != null) {
                                Log.e("error", t.toString() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(CartActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
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

    private void addOrder(int client_id) {
        CartDataModel cartDataModel = manageCartModel.getCartDataModel(this);
        cartDataModel.setNotes("");
        cartDataModel.setClient_id(client_id);

        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .addOrder("Bearer " + userModel.getData().getToken(), cartDataModel)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200 && response.body() != null) {
                                manageCartModel.clearData(CartActivity.this);
                                binding.setTotal(0.0);
                                list.clear();
                                adapter.notifyDataSetChanged();
                                createDialogAlert();

                            }
                        } else {
                            dialog.dismiss();

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
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        try {
                            dialog.dismiss();

                            if (t.getMessage() != null) {
                                Log.e("error", t.toString() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(CartActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
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

    private  void createDialogAlert() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogAlertBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_alert, null, false);

        binding.tvMsg.setText(R.string.order_added);
        binding.btnCancel.setOnClickListener(v -> {
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    finish();
                }

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }


}