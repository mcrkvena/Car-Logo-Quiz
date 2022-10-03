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

public class Level10 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences10 = "FirstAppStart10";
    final String level10dbName = "level10.db";
    final String level10dbTableName = "level10";
    final String Level10Pref = "CurrentLevel10";
    int CurrentLevel10;
    final int Maxlevel10= 150;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level10);
        SubmitButton = findViewById(R.id.submitbutton10);
        LogoImage = findViewById(R.id.logoimage10);
        LogoName = findViewById(R.id.logoname10);
        Correct = findViewById(R.id.correct10);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level10Pref, MODE_PRIVATE);
        CurrentLevel10 = LoadPref.getInt(Level10Pref, 136);
        Log.i("LEVEL", String.valueOf(CurrentLevel10));
        if(CurrentLevel10<=Maxlevel10){
            SQLiteDatabase level10db = openOrCreateDatabase(level10dbName, MODE_PRIVATE, null);
            Cursor cursor = level10db.rawQuery("SELECT * FROM " + level10dbTableName + " WHERE id = '" + CurrentLevel10 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level10db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel10 = 136;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel10));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level10Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level10Pref, CurrentLevel10);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences10, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences10, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences10, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level10db = openOrCreateDatabase(level10dbName, MODE_PRIVATE, null);
        level10db.execSQL("CREATE TABLE " + level10dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('136', 'Saturn', 'saturn')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('137', 'Holden', 'holden')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('138', 'Saleen', 'saleen')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('139', 'BYD', 'byd')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('140', 'Polestar', 'polestar')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('141', 'Duesenberg', 'duesenberg')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('142', 'Chaparral', 'chaparral')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('143', 'Callaway', 'callaway')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('144', 'Marcos', 'marcos')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('145', 'Hommell', 'hommell')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('146', 'Tommykaira', 'tommykaira')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('147', 'Triumph', 'triumph')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('148', 'DMC', 'dmc')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('149', 'Mosler', 'mosler')");
        level10db.execSQL("INSERT INTO " + level10dbTableName + " VALUES('150', 'Dome', 'dome')");
        level10db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel10++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
