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

public class Level6 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences6 = "FirstAppStart6";
    final String level6dbName = "level6.db";
    final String level6dbTableName = "level6";
    final String Level6Pref = "CurrentLevel6";
    int CurrentLevel6;
    final int Maxlevel6= 90;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level6);
        SubmitButton = findViewById(R.id.submitbutton6);
        LogoImage = findViewById(R.id.logoimage6);
        LogoName = findViewById(R.id.logoname6);
        Correct = findViewById(R.id.correct6);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level6Pref, MODE_PRIVATE);
        CurrentLevel6 = LoadPref.getInt(Level6Pref, 76);
        Log.i("LEVEL", String.valueOf(CurrentLevel6));
        if(CurrentLevel6<=Maxlevel6){
            SQLiteDatabase level6db = openOrCreateDatabase(level6dbName, MODE_PRIVATE, null);
            Cursor cursor = level6db.rawQuery("SELECT * FROM " + level6dbTableName + " WHERE id = '" + CurrentLevel6 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level6db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel6 = 76;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel6));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level6Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level6Pref, CurrentLevel6);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences6, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences6, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences6, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level6db = openOrCreateDatabase(level6dbName, MODE_PRIVATE, null);
        level6db.execSQL("CREATE TABLE " + level6dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('76', 'Proton', 'proton')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('77', 'Foton', 'foton')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('78', 'Praga', 'praga')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('79', 'Lincoln', 'lincoln')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('80', 'Noble', 'noble')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('81', 'Spyker', 'spyker')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('82', 'GMC', 'gmc')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('83', 'Genesis', 'genesis')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('84', 'KTM', 'ktm')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('85', 'Rover', 'rover')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('86', 'FPV', 'fpv')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('87', 'GAZ', 'gaz')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('88', 'Gillet', 'gillet')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('89', 'Geely', 'geely')");
        level6db.execSQL("INSERT INTO " + level6dbTableName + " VALUES('90', 'Great Wall', 'greatwall')");
        level6db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel6++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
