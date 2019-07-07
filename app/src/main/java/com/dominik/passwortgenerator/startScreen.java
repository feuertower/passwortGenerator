package com.dominik.passwortgenerator;
import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dominik.passwortgenerator.MainActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class startScreen extends AppCompatActivity{

    long millis = 0;
    Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

    }

    private void onNewPasswordClicked() {

        Intent intent = new Intent(startScreen.this, MainActivity.class);
        startActivity( intent );
        finish();
    }

    public void clickHandler(View view) {

        switch(view.getId()) {
            case R.id.btnNewPassword:
                onNewPasswordClicked();
                break;
        }
    }

    public void onBackPressed() {
        if( (System.currentTimeMillis() - millis) < 1000 ){
            toast.cancel();
            finish();
        }
        else
        {
            toast = Toast.makeText(this, R.string.double_press_back, LENGTH_SHORT);
            toast.show();
        }

        millis = System.currentTimeMillis();
    }
}