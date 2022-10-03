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

public class Level8 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences8 = "FirstAppStart8";
    final String level8dbName = "level8.db";
    final String level8dbTableName = "level8";
    final String Level8Pref = "CurrentLevel8";
    int CurrentLevel8;
    final int Maxlevel8= 120;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level8);
        SubmitButton = findViewById(R.id.submitbutton8);
        LogoImage = findViewById(R.id.logoimage8);
        LogoName = findViewById(R.id.logoname8);
        Correct = findViewById(R.id.correct8);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level8Pref, MODE_PRIVATE);
        CurrentLevel8 = LoadPref.getInt(Level8Pref, 106);
        Log.i("LEVEL", String.valueOf(CurrentLevel8));
        if(CurrentLevel8<=Maxlevel8){
            SQLiteDatabase level8db = openOrCreateDatabase(level8dbName, MODE_PRIVATE, null);
            Cursor cursor = level8db.rawQuery("SELECT * FROM " + level8dbTableName + " WHERE id = '" + CurrentLevel8 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level8db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel8 = 106;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel8));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level8Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level8Pref, CurrentLevel8);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences8, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences8, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences8, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level8db = openOrCreateDatabase(level8dbName, MODE_PRIVATE, null);
        level8db.execSQL("CREATE TABLE " + level8dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('106', 'Radical', 'radical')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('107', 'BAC', 'bac')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('108', 'AC Cars', 'accars')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('109', 'Lister', 'lister')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('110', 'Hennessey', 'hennessey')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('111', 'SSC', 'ssc')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('112', 'Plymouth', 'plymouth')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('113', 'Studebaker', 'studebaker')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('114', 'Eagle', 'eagle')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('115', 'Puch', 'puch')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('116', 'DOK ING', 'doking')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('117', 'DS', 'ds')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('118', 'Talbot', 'talbot')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('119', 'Delahaye', 'delahaye')");
        level8db.execSQL("INSERT INTO " + level8dbTableName + " VALUES('120', 'Wartburg', 'wartburg')");
        level8db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel8++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
