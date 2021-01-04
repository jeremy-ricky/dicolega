package com.getsoft.dicolega;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
