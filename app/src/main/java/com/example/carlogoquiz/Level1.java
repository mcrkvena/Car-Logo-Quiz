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

public class Level1 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences = "FirstAppStart";
    final String level1dbName = "level1.db";
    final String level1dbTableName = "level1";
    final String Level1Pref = "CurrentLevel";
    int CurrentLevel;
    final int Maxlevel= 15;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level1);
        SubmitButton = findViewById(R.id.submitbutton);
        LogoImage = findViewById(R.id.logoimage);
        LogoName = findViewById(R.id.logoname);
        Correct = findViewById(R.id.correct);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level1Pref, MODE_PRIVATE);
        CurrentLevel = LoadPref.getInt(Level1Pref, 1);
        Log.i("LEVEL", String.valueOf(CurrentLevel));
        if(CurrentLevel<=Maxlevel){
            SQLiteDatabase level1db = openOrCreateDatabase(level1dbName, MODE_PRIVATE, null);
            Cursor cursor = level1db.rawQuery("SELECT * FROM " + level1dbTableName + " WHERE id = '" + CurrentLevel + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level1db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel = 1;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level1Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level1Pref, CurrentLevel);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level1db = openOrCreateDatabase(level1dbName, MODE_PRIVATE, null);
        level1db.execSQL("CREATE TABLE " + level1dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('1', 'Audi', 'audi')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('2', 'BMW', 'bmw')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('3', 'Honda', 'honda')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('4', 'Toyota', 'toyota')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('5', 'Nissan', 'nissan')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('6', 'Volkswagen', 'volkswagen')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('7', 'Mercedes Benz', 'mercedesbenz')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('8', 'Opel', 'opel')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('9', 'Peugeot', 'peugeot')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('10', 'Citroen', 'citroen')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('11', 'Renault', 'renault')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('12', 'Volvo', 'volvo')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('13', 'Chevrolet', 'chevrolet')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('14', 'Hyundai', 'hyundai')");
        level1db.execSQL("INSERT INTO " + level1dbTableName + " VALUES('15', 'Mitsubishi', 'mitsubishi')");
        level1db.close();
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
