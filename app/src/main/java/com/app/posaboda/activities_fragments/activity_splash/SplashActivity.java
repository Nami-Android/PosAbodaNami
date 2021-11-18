package com.app.posaboda.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.app.posaboda.R;
import com.app.posaboda.activities_fragments.activity_home.HomeActivity;
import com.app.posaboda.activities_fragments.activity_login.LoginActivity;
import com.app.posaboda.databinding.ActivitySplashBinding;
import com.app.posaboda.language.Language;
import com.app.posaboda.models.UserModel;
import com.app.posaboda.preferences.Preferences;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private Preferences preference;
    private UserModel userModel;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = new Fade();
            transition.setInterpolator(new LinearInterpolator());
            transition.setDuration(500);
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);

        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);


        initView();
    }

    private void initView() {
        preference = Preferences.getInstance();
        userModel = preference.getUserData(this);
        new Handler().postDelayed(() -> {
            Intent intent;
            if (userModel == null) {
                intent = new Intent(this, LoginActivity.class);

            } else {
                  intent = new Intent(this, HomeActivity.class);

            }
            startActivity(intent);

            finish();

        }, 2000);
    }
}