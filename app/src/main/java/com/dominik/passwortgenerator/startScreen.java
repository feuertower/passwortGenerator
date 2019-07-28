package com.dominik.passwortgenerator;
import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dominik.passwortgenerator.MainActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class startScreen extends AppCompatActivity{

    long millis = 0;
    Toast toast = null;
    boolean showPWBtnState = true;

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
            case R.id.showPWBtn:
                showPWBtnPressed();
                break;

            case R.id.enterBtn:
                enterBtnPressed();
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

    public void enterBtnPressed() {

        final EditText appPasswortEditText = findViewById(R.id.appPasswordEditText);
        String pwString = appPasswortEditText.getText().toString();

        if(pwString.equals("annika"))
        {
            Toast.makeText( this, "correct Password!", LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "wrong Password!", LENGTH_SHORT).show();
        }

    }

    public void showPWBtnPressed() {
        EditText pwEditText = findViewById(R.id.appPasswordEditText);

        if( showPWBtnState )
        {
            pwEditText.setInputType( InputType.TYPE_CLASS_TEXT |
                                     InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            pwEditText.setSelection(pwEditText.getText().length()); // set cursor to the end of text
        }
        else
        {
            pwEditText.setInputType( InputType.TYPE_CLASS_TEXT |
                                     InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pwEditText.setSelection(pwEditText.getText().length()); // set cursor to the end of text
        }

        showPWBtnState = !showPWBtnState;
    }
}