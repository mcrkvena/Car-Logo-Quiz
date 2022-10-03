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

public class Level4 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences4 = "FirstAppStart4";
    final String level4dbName = "level4.db";
    final String level4dbTableName = "level4";
    final String Level4Pref = "CurrentLevel4";
    int CurrentLevel4;
    final int Maxlevel4= 60;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level4);
        SubmitButton = findViewById(R.id.submitbutton4);
        LogoImage = findViewById(R.id.logoimage4);
        LogoName = findViewById(R.id.logoname4);
        Correct = findViewById(R.id.correct4);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level4Pref, MODE_PRIVATE);
        CurrentLevel4 = LoadPref.getInt(Level4Pref, 46);
        Log.i("LEVEL", String.valueOf(CurrentLevel4));
        if(CurrentLevel4<=Maxlevel4){
            SQLiteDatabase level4db = openOrCreateDatabase(level4dbName, MODE_PRIVATE, null);
            Cursor cursor = level4db.rawQuery("SELECT * FROM " + level4dbTableName + " WHERE id = '" + CurrentLevel4 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level4db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel4 = 46;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel4));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level4Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level4Pref, CurrentLevel4);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences4, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences4, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences4, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level4db = openOrCreateDatabase(level4dbName, MODE_PRIVATE, null);
        level4db.execSQL("CREATE TABLE " + level4dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('46', 'Lexus', 'lexus')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('47', 'Infiniti', 'infiniti')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('48', 'Pontiac', 'pontiac')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('49', 'Dodge', 'dodge')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('50', 'Acura', 'acura')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('51', 'Buick', 'buick')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('52', 'Isuzu', 'isuzu')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('53', 'Vauxhall', 'vauxhall')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('54', 'Hummer', 'hummer')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('55', 'Abarth', 'abarth')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('56', 'Koenigsegg', 'koenigsegg')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('57', 'Scion', 'scion')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('58', 'Maybach', 'maybach')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('59', 'Pagani', 'pagani')");
        level4db.execSQL("INSERT INTO " + level4dbTableName + " VALUES('60', 'Tata', 'tata')");
        level4db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel4++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
