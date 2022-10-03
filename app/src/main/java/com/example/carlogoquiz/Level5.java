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

public class Level5 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences5 = "FirstAppStart5";
    final String level5dbName = "level5.db";
    final String level5dbTableName = "level5";
    final String Level5Pref = "CurrentLevel5";
    int CurrentLevel5;
    final int Maxlevel5= 75;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level5);
        SubmitButton = findViewById(R.id.submitbutton5);
        LogoImage = findViewById(R.id.logoimage5);
        LogoName = findViewById(R.id.logoname5);
        Correct = findViewById(R.id.correct5);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level5Pref, MODE_PRIVATE);
        CurrentLevel5 = LoadPref.getInt(Level5Pref, 61);
        Log.i("LEVEL", String.valueOf(CurrentLevel5));
        if(CurrentLevel5<=Maxlevel5){
            SQLiteDatabase level5db = openOrCreateDatabase(level5dbName, MODE_PRIVATE, null);
            Cursor cursor = level5db.rawQuery("SELECT * FROM " + level5dbTableName + " WHERE id = '" + CurrentLevel5 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level5db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel5 = 61;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel5));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level5Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level5Pref, CurrentLevel5);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences5, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences5, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences5, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level5db = openOrCreateDatabase(level5dbName, MODE_PRIVATE, null);
        level5db.execSQL("CREATE TABLE " + level5dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('61', 'MG', 'mg')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('62', 'Ssangyong', 'ssangyong')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('63', 'Alpine', 'alpine')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('64', 'Aston Martin', 'astonmartin')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('65', 'Mahindra', 'mahindra')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('66', 'Ascari', 'ascari')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('67', 'Chrysler', 'chrysler')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('68', 'Gumpert', 'gumpert')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('69', 'TVR', 'tvr')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('70', 'Lancia', 'lancia')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('71', 'Saab', 'saab')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('72', 'Datsun', 'datsun')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('73', 'Zastava', 'zastava')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('74', 'Ginetta', 'ginetta')");
        level5db.execSQL("INSERT INTO " + level5dbTableName + " VALUES('75', 'Shelby', 'shelby')");
        level5db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel5++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
