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

public class Level7 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences7 = "FirstAppStart7";
    final String level7dbName = "level7.db";
    final String level7dbTableName = "level7";
    final String Level7Pref = "CurrentLevel7";
    int CurrentLevel7;
    final int Maxlevel7= 105;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level7);
        SubmitButton = findViewById(R.id.submitbutton7);
        LogoImage = findViewById(R.id.logoimage7);
        LogoName = findViewById(R.id.logoname7);
        Correct = findViewById(R.id.correct7);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level7Pref, MODE_PRIVATE);
        CurrentLevel7 = LoadPref.getInt(Level7Pref, 91);
        Log.i("LEVEL", String.valueOf(CurrentLevel7));
        if(CurrentLevel7<=Maxlevel7){
            SQLiteDatabase level7db = openOrCreateDatabase(level7dbName, MODE_PRIVATE, null);
            Cursor cursor = level7db.rawQuery("SELECT * FROM " + level7dbTableName + " WHERE id = '" + CurrentLevel7 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level7db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel7 = 91;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel7));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level7Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level7Pref, CurrentLevel7);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences7, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences7, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences7, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level7db = openOrCreateDatabase(level7dbName, MODE_PRIVATE, null);
        level7db.execSQL("CREATE TABLE " + level7dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('91', 'Tatra', 'tatra')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('92', 'Zenvo', 'zenvo')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('93', 'Ligier', 'ligier')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('94', 'Wiesmann', 'wiesmann')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('95', 'Trabant', 'trabant')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('96', 'Ariel', 'ariel')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('97', 'Panoz', 'panoz')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('98', 'Autobianchi', 'autobianchi')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('99', 'Mitsuoka', 'mitsuoka')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('100', 'Polski Fiat', 'polskifiat')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('101', 'UAZ', 'uaz')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('102', 'ZiL', 'zil')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('103', 'Hispano Suiza', 'hispanosuiza')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('104', 'Caterham', 'caterham')");
        level7db.execSQL("INSERT INTO " + level7dbTableName + " VALUES('105', 'Morgan', 'morgan')");
        level7db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel7++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
