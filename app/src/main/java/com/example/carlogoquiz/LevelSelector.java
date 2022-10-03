package com.example.carlogoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelector extends AppCompatActivity implements View.OnClickListener {

    Button level1Button;
    Button level2Button;
    Button level3Button;
    Button level4Button;
    Button level5Button;
    Button level6Button;
    Button level7Button;
    Button level8Button;
    Button level9Button;
    Button level10Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselector);
        level1Button = findViewById(R.id.lvl1button);
        level1Button.setOnClickListener(LevelSelector.this);
        level2Button = findViewById(R.id.lvl2button);
        level2Button.setOnClickListener(LevelSelector.this);
        level3Button = findViewById(R.id.lvl3button);
        level3Button.setOnClickListener(LevelSelector.this);
        level4Button = findViewById(R.id.lvl4button);
        level4Button.setOnClickListener(LevelSelector.this);
        level5Button = findViewById(R.id.lvl5button);
        level5Button.setOnClickListener(LevelSelector.this);
        level6Button = findViewById(R.id.lvl6button);
        level6Button.setOnClickListener(LevelSelector.this);
        level7Button = findViewById(R.id.lvl7button);
        level7Button.setOnClickListener(LevelSelector.this);
        level8Button = findViewById(R.id.lvl8button);
        level8Button.setOnClickListener(LevelSelector.this);
        level9Button = findViewById(R.id.lvl9button);
        level9Button.setOnClickListener(LevelSelector.this);
        level10Button = findViewById(R.id.lvl10button);
        level10Button.setOnClickListener(LevelSelector.this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.lvl1button:
                Intent intent = new Intent(this, Level1.class);
                startActivity(intent);
                break;
            case R.id.lvl2button:
                Intent intent2 = new Intent(this, Level2.class);
                startActivity(intent2);
                break;
            case R.id.lvl3button:
                Intent intent3 = new Intent(this, Level3.class);
                startActivity(intent3);
                break;
            case R.id.lvl4button:
                Intent intent4 = new Intent(this, Level4.class);
                startActivity(intent4);
                break;
            case R.id.lvl5button:
                Intent intent5 = new Intent(this, Level5.class);
                startActivity(intent5);
                break;
            case R.id.lvl6button:
                Intent intent6 = new Intent(this, Level6.class);
                startActivity(intent6);
                break;
            case R.id.lvl7button:
                Intent intent7 = new Intent(this, Level7.class);
                startActivity(intent7);
                break;
            case R.id.lvl8button:
                Intent intent8 = new Intent(this, Level8.class);
                startActivity(intent8);
                break;
            case R.id.lvl9button:
                Intent intent9 = new Intent(this, Level9.class);
                startActivity(intent9);
                break;
            case R.id.lvl10button:
                Intent intent10 = new Intent(this, Level10.class);
                startActivity(intent10);
                break;
        }
    }
}
