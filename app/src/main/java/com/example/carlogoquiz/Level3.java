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

import com.airbnb.lottie.LottieAnimationView;

public class Level3 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences3 = "FirstAppStart3";
    final String level3dbName = "level3.db";
    final String level3dbTableName = "level3";
    final String Level3Pref = "CurrentLevel3";
    int CurrentLevel3;
    final int Maxlevel3= 45;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level3);
        SubmitButton = findViewById(R.id.submitbutton3);
        LogoImage = findViewById(R.id.logoimage3);
        LogoName = findViewById(R.id.logoname3);
        Correct = findViewById(R.id.correct3);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level3Pref, MODE_PRIVATE);
        CurrentLevel3 = LoadPref.getInt(Level3Pref, 31);
        Log.i("LEVEL", String.valueOf(CurrentLevel3));
        if(CurrentLevel3<=Maxlevel3){
            SQLiteDatabase level3db = openOrCreateDatabase(level3dbName, MODE_PRIVATE, null);
            Cursor cursor = level3db.rawQuery("SELECT * FROM " + level3dbTableName + " WHERE id = '" + CurrentLevel3 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level3db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel3 = 31;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel3));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level3Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level3Pref, CurrentLevel3);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences3, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences3, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences3, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level3db = openOrCreateDatabase(level3dbName, MODE_PRIVATE, null);
        level3db.execSQL("CREATE TABLE " + level3dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('31', 'McLaren', 'mclaren')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('32', 'Bugatti', 'bugatti')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('33', 'Bentley', 'bentley')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('34', 'Rolls Royce', 'rollsroyce')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('35', 'Mini', 'mini')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('36', 'Lotus', 'lotus')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('37', 'Lada', 'lada')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('38', 'Land Rover', 'landrover')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('39', 'Daihatsu', 'daihatsu')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('40', 'Daewoo', 'daewoo')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('41', 'Smart', 'smart')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('42', 'Cadillac', 'cadillac')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('43', 'Rimac', 'rimac')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('44', 'Jaguar', 'jaguar')");
        level3db.execSQL("INSERT INTO " + level3dbTableName + " VALUES('45', 'Kia', 'kia')");
        level3db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel3++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
