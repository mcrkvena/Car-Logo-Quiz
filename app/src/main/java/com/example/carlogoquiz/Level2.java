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

public class Level2 extends AppCompatActivity implements View.OnClickListener {

    Button SubmitButton;
    ImageView LogoImage;
    EditText LogoName;
    final String FirstAppStartPreferences2 = "FirstAppStart2";
    final String level2dbName = "level2.db";
    final String level2dbTableName = "level2";
    final String Level2Pref = "CurrentLevel2";
    int CurrentLevel2;
    final int Maxlevel2= 30;
    String ManufacturerName;
    String ImageName;
    LottieAnimationView Correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level2);
        SubmitButton = findViewById(R.id.submitbutton2);
        LogoImage = findViewById(R.id.logoimage2);
        LogoName = findViewById(R.id.logoname2);
        Correct = findViewById(R.id.correct2);
        SubmitButton.setOnClickListener(this);
        if(FirstAppStart()){
            CreateDatabase();
        }
        load();
    }

    public void load(){
        SharedPreferences LoadPref = getSharedPreferences(Level2Pref, MODE_PRIVATE);
        CurrentLevel2 = LoadPref.getInt(Level2Pref, 16);
        Log.i("LEVEL", String.valueOf(CurrentLevel2));
        if(CurrentLevel2<=Maxlevel2){
            SQLiteDatabase level2db = openOrCreateDatabase(level2dbName, MODE_PRIVATE, null);
            Cursor cursor = level2db.rawQuery("SELECT * FROM " + level2dbTableName + " WHERE id = '" + CurrentLevel2 + "'", null);
            cursor.moveToFirst();
            if(cursor.getCount()==1){
                ManufacturerName = cursor.getString(1);
                ImageName = cursor.getString(2);
                cursor.close();
                level2db.close();
            }
            int LogoID = getResources().getIdentifier(ImageName, "drawable", getPackageName());
            LogoImage.setImageResource(LogoID);
        } else {
            CurrentLevel2 = 16;
            save();
            Intent intent = new Intent(this, LevelSelector.class);
            startActivity(intent);
        }
        Log.i("LEVEL", String.valueOf(CurrentLevel2));
        Correct.setVisibility(View.INVISIBLE);
        LogoImage.setVisibility(View.VISIBLE);
        LogoName.setVisibility(View.VISIBLE);
        SubmitButton.setVisibility(View.VISIBLE);
        LogoName.setText("");
    }

    public void save(){
        SharedPreferences lvlpref = getSharedPreferences(Level2Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = lvlpref.edit();
        editor.putInt(Level2Pref, CurrentLevel2);
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
        SharedPreferences preferences = getSharedPreferences(FirstAppStartPreferences2, MODE_PRIVATE);
        if(preferences.getBoolean(FirstAppStartPreferences2, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FirstAppStartPreferences2, false);
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public void CreateDatabase(){
        SQLiteDatabase level2db = openOrCreateDatabase(level2dbName, MODE_PRIVATE, null);
        level2db.execSQL("CREATE TABLE " + level2dbTableName + "(id INTEGER, manufacturer TEXT, imageName TEXT)");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('16', 'Ford', 'ford')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('17', 'Ferrari', 'ferrari')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('18', 'Mazda', 'mazda')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('19', 'Alfa Romeo', 'alfaromeo')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('20', 'Fiat', 'fiat')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('21', 'Suzuki', 'suzuki')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('22', 'Tesla', 'tesla')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('23', 'Lamborghini', 'lamborghini')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('24', 'Porsche', 'porsche')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('25', 'Jeep', 'jeep')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('26', 'Subaru', 'subaru')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('27', 'Skoda', 'skoda')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('28', 'Dacia', 'dacia')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('29', 'Seat', 'seat')");
        level2db.execSQL("INSERT INTO " + level2dbTableName + " VALUES('30', 'Maserati', 'maserati')");
        level2db.close();
        Log.i("DB", " created");
    }

    @Override
    public void onClick(View v) {
        if(LogoName.getText().toString().equalsIgnoreCase(ManufacturerName)){
            CurrentLevel2++;
            save();
            CorrectAnswer();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
