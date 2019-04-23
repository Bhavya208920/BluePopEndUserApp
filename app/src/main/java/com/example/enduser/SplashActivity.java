package com.example.enduser;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.enduser.UI.FirstActivity;
import com.example.enduser.UI.LoginActivity;
import com.example.enduser.UI.RegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    TextToSpeech tts;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        tts();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        handler.sendEmptyMessageDelayed(101, 4000);
        if(user==null){
            handler.sendEmptyMessageDelayed(101, 4000);
        }else{
            handler.sendEmptyMessageDelayed(201, 4000);
        }
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 101){
                Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    void tts(){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(tts.getEngines().size() == 0){
                    Toast.makeText(SplashActivity.this, "Please enable TTS in your settings", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    tts.setLanguage(Locale.US);
                    speak("Welcome To Blue Pop");
                    //speak("Enter your name");
                }
            }
        });
    }


    private void speak(String s) {
        //if(Build.VERSION.SDK_INT >=21){
        tts.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        /*}else{
            tts.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();

    }
}
