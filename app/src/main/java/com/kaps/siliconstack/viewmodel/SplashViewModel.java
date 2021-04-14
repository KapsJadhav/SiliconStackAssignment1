package com.kaps.siliconstack.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.kaps.siliconstack.view.MainActivity;


public class SplashViewModel extends ViewModel {

    private Context context;

    public SplashViewModel(Context context) {
        this.context = context;
        onProgress();
    }


    public void onProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int progress = 0; progress <= 100; progress++) {
                    if (progress == 100) {
                        onSuccess();
                    }
                    try {
                        Thread.sleep(07);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void onSuccess() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
