package com.kaps.siliconstack.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.kaps.siliconstack.R;
import com.kaps.siliconstack.databinding.ActivitySplashBinding;
import com.kaps.siliconstack.viewmodel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        activitySplashBinding.setViewModel(new SplashViewModel(this));
        activitySplashBinding.executePendingBindings();
    }
}