package iba.institute.android.drinkcounte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String STORAGE= "Alcoholics";
    public static final String MAX_POWER= "MAX_POWER";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void goesToStart(View view) {
        EditText editText = findViewById(R.id.main_max_power);
        int maxPower = Integer.parseInt(editText.getText().toString());

        sharedPreferences = getSharedPreferences(STORAGE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MAX_POWER,maxPower);
        editor.commit();

        Intent intent = new Intent(this,MainGameActivity.class);
        intent.putExtra(MAX_POWER,maxPower);
        startActivity(intent);
    }
}