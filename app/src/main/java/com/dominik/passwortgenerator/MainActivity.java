package com.dominik.passwortgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private char[] lowerAlphabet;
    private char[] upperAlphabet;
    private char[] specials;

    private TextView pwLengthShown;
    private int seekBarOffset = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password);

        lowerAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        specials = "!§$%&/()=?-_+*#".toCharArray();
        pwLengthShown = findViewById(R.id.pwLength);

        // show seekbar length when changed
        final SeekBar seekbar = findViewById(R.id.pwLengthSlider);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean b) {
                TextView textview = findViewById(R.id.pwLength);
                textview.setText(String.valueOf(progress+seekBarOffset));

                setXPwLengthShown(seekbar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

        });
        seekbar.setProgress(5);
    }

    void setXPwLengthShown(SeekBar seekbar){

        float width = seekbar.getWidth() - seekbar.getPaddingLeft() - seekbar.getPaddingRight();
        float thumbPos = seekbar.getPaddingLeft() + width * seekbar.getProgress()/ seekbar.getMax() - pwLengthShown.getWidth()/2;

        pwLengthShown.setX(thumbPos);
    }

    void generateLower(int length, StringBuilder strBuilder) {
        for (int i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(0,
                    lowerAlphabet.length);
            strBuilder.append(lowerAlphabet[randomInt]);
        }
    }

    void generateUpper(int length, StringBuilder strBuilder) {
        for (int i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, upperAlphabet.length);
            strBuilder.append(upperAlphabet[randomInt]);
        }
    }

    void generateNumber(int length, StringBuilder strBuilder) {
        for (int i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, 9);
            strBuilder.append(randomInt);
        }
    }

    void generateSpecial(int length, StringBuilder strBuilder) {
        for (int i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, specials.length);
            strBuilder.append(specials[randomInt]);
        }
    }

    int getPwMin(int pwLength){
        int min = 1;
        if(pwLength <= 30 && pwLength > 10){
            min = 2;
        }
        else if(pwLength > 30){
            min = 6;
        }
        return min;
    }

    public static String shuffle(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        double rnd;
        for (char c: string.toCharArray()) {
            rnd = Math.random();
            if (rnd < 0.34)
                sb.append(c);
            else if (rnd < 0.67)
                sb.insert(sb.length() / 2, c);
            else
                sb.insert(0, c);
        }
        return sb.toString();
    }

    void onGenerateClicked() {
        int pwLength;
        int pwMin;

        boolean lower = ((Switch) findViewById(R.id.cbLowerCharacter)).isChecked();
        boolean upper = ((Switch) findViewById(R.id.cbUpperCharacter)).isChecked();
        boolean numbers = ((Switch) findViewById(R.id.cbNumbers)).isChecked();
        boolean special = ((Switch) findViewById(R.id.cbSpecial)).isChecked();
        pwLength = ((SeekBar)findViewById(R.id.pwLengthSlider)).getProgress() + seekBarOffset;

        final StringBuilder strBuilder = new StringBuilder();
        final TextView passwordText = findViewById(R.id.pwText);
        passwordText.setText("");

        pwMin = getPwMin(pwLength);

        int randLowerCase = 0;
        int randUpperCase = 0;
        int randNumber = 0;
        int randSpecial = 0;

        if (lower) {
            randLowerCase = pwLength;
        }

        if (upper) {
            if(lower) {
                randLowerCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - pwMin);
                randUpperCase = pwLength - randLowerCase;
            }
            else {
                randUpperCase = pwLength;
            }
        }

        if (numbers) {
            if(lower && upper){
                randLowerCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - 2*pwMin);
                randUpperCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - randLowerCase - pwMin);
                randNumber = pwLength - randLowerCase - randUpperCase;
            }
            else if(lower) {
                randLowerCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - pwMin);
                randNumber = pwLength - randLowerCase;
            }
            else if(upper) {
                randUpperCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength);
                randNumber = pwLength - randUpperCase;
            }
            else {
                randNumber = pwLength;
            }
        }

        if (special) {
            if(lower && upper && numbers) {
                randLowerCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - 3*pwMin);
                randUpperCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - randLowerCase - 2*pwMin);
                randNumber = ThreadLocalRandom.current().nextInt(pwMin, pwLength - randLowerCase - randUpperCase - pwMin);
                randSpecial = pwLength - randLowerCase - randUpperCase - randNumber;
            }
            else if(lower && upper) {
                randLowerCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - 2*pwMin);
                randUpperCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - randLowerCase - pwMin);
                randSpecial = pwLength - randLowerCase - randUpperCase;
            }
            else if(lower && numbers) {
                randLowerCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - 2 * pwMin);
                randNumber = ThreadLocalRandom.current().nextInt(pwMin, pwLength - randLowerCase - pwMin);
                randSpecial = pwLength - randLowerCase - randNumber;
            }
            else if(upper && numbers) {
                randUpperCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - randLowerCase - 2*pwMin);
                randNumber = ThreadLocalRandom.current().nextInt(pwMin, pwLength - randLowerCase - pwMin);
                randSpecial = pwLength - randUpperCase - randNumber;
            }
            else if(lower) {
                randLowerCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - pwMin);
                randSpecial = pwLength - randLowerCase;
            }
            else if(upper) {
                randUpperCase = ThreadLocalRandom.current().nextInt(pwMin, pwLength - pwMin);
                randSpecial = pwLength - randUpperCase;
            }
            else if(numbers) {
                randNumber = ThreadLocalRandom.current().nextInt(pwMin, pwLength - pwMin);
                randSpecial = pwLength - randNumber;
            }
            else {
                randSpecial = pwLength;
            }
        }

        generateLower(randLowerCase, strBuilder);
        generateUpper(randUpperCase, strBuilder);
        generateNumber(randNumber, strBuilder);
        generateSpecial(randSpecial, strBuilder);

        final String pwString = shuffle(strBuilder.toString());

        Toast toast = Toast.makeText(this, R.string.generatePwToast, LENGTH_SHORT);
        toast.show();

        //  simulated delay to calculate password
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after time

                passwordText.setText(pwString);
                passwordText.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    void onCopyClicked() {
        TextView pwTextBox = findViewById(R.id.pwText);
        String pwText = pwTextBox.getText().toString();

        if(pwText.length() > 4) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("test", pwText);
            assert clipboard != null;
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copyText, Toast.LENGTH_SHORT).show();
        }

    }

    public void clickHandler(View view) {

        switch(view.getId()) {
            case R.id.btnGenerate:
                onGenerateClicked();
                break;

            case R.id.btnCopy:
                onCopyClicked();
                break;
        }
    }

    public void onBackPressed() {

        Intent intent = new Intent(MainActivity.this, startScreen.class);
        startActivity( intent );
        finish();
    }
}
