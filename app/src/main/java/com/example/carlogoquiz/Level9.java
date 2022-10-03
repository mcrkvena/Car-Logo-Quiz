package com.example.carlogoquiz;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.carlogoquiz.LevelSelector;
import com.example.carlogoquiz.R;

public class Level9 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences9 = "FirstAppStart9";
    final String level9dbName = "level9.db";
    final String level9dbTableName = "level9";
    final String Level9Pref = "CurrentLevel9";
    int CurrentLevel9;
    final int Maxlevel9= 135;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level9);
        SubmitButton = findViewById(R.id.submitbutton9);
        LogoImage = findViewById(R.id.logoimage9);
        LogoName = findViewById(R.id.logoname9);
        Correct = findViewById(R.id.correct9);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level9Pref, MODE_PRIVATE);
        CurrentLevel9 = LoadPref.getInt(Level9Pref, 121);
        Log.i("LEVEL", String.valueOf(CurrentLevel9));
        if(CurrentLevel9<=Maxlevel9){
            SQLiteDatabase level9db = openOrCreateDatabase(level9dbName, MODE_PRIVATE, null);
            Cursor cursor = level9db.rawQuery("SELECT * FROM " + level9dbTableName + " WHERE id = '" + CurrentLevel9 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level9db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel9 = 121;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel9));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level9Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level9Pref, CurrentLevel9);
        editor.commit();
    }

    public void CorrectAnswer(){
        LogoImage.setVisibility(View.INVISIBLE);
        LogoName.setVisibility(View.INVISIBLE);
        SubmitButton.setVisibility(View.INVISIBLE);
        Correct.setVisibility(View.VISIBLE);
        Correct.playAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load();
            }
        }, 1500);
    }

    public boolean FirstAppStart(){
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences9, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences9, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences9, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level9db = openOrCreateDatabase(level9dbName, MODE_PRIVATE, null);
        level9db.execSQL("CREATE TABLE " + level9dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('121', 'Artega', 'artega')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('122', 'NSU', 'nsu')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('123', 'Alpina', 'alpina')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('124', 'RUF', 'ruf')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('125', 'Pininfarina', 'pininfarina')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('126', 'Bertone', 'bertone')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('127', 'Innocenti', 'innocenti')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('128', 'Lagonda', 'lagonda')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('129', 'General Motors', 'generalmotors')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('130', 'AMC', 'amc')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('131', 'Hudson', 'hudson')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('132', 'Packard', 'packard')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('133', 'De Tomaso', 'detomaso')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('134', 'Matra', 'matra')");
        level9db.execSQL("INSERT INTO " + level9dbTableName + " VALUES('135', 'Simca', 'simca')");
        level9db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel9++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
