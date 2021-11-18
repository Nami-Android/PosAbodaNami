package com.app.posaboda.activities_fragments.activity_login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_home.HomeActivity;
import com.app.posaboda.databinding.ActivityLoginBinding;
import com.app.posaboda.language.Language;
import com.app.posaboda.models.LoginModel;
import com.app.posaboda.models.UserModel;
import com.app.posaboda.preferences.Preferences;
import com.app.posaboda.remote.Api;
import com.app.posaboda.share.Common;
import com.app.posaboda.tags.Tags;

import java.io.IOException;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String lang;
    private LoginModel loginModel;
    private Preferences preferences;


    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        preferences=Preferences.getInstance();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        loginModel = new LoginModel();
        binding.setModel(loginModel);


        binding.btnLogin.setOnClickListener(view -> {
            if (loginModel.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtUserName);
                login();
            }
        });



    }


    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void login() {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .login(loginModel.getUser_name(),loginModel.getPassword())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                    preferences.create_update_userdata(LoginActivity.this, response.body());
                                    preferences.create_update_session(LoginActivity.this, Tags.session_login);
                                    navigateToHomeActivity();

                            } else if (response.body().getStatus() == 404) {
                                Toast.makeText(LoginActivity.this, R.string.inc_user_pass,Toast.LENGTH_LONG).show();
                            }
                            else if (response.body().getStatus() == 406) {
                                Toast.makeText(LoginActivity.this, R.string.user_blocked,Toast.LENGTH_LONG).show();
                            }else if (response.body().getStatus() == 407) {
                                Toast.makeText(LoginActivity.this, R.string.user_not_have_perm,Toast.LENGTH_LONG).show();
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
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.toString() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(LoginActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
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