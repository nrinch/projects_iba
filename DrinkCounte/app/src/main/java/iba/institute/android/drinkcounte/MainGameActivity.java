package iba.institute.android.drinkcounte;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainGameActivity extends AppCompatActivity implements AlcoAdapter.iClickListener {

    RecyclerView listAlcohol;
    List<Alcohol> list =new ArrayList<>();
    int currentState=0;
    int maxPower;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        list.add(new Alcohol("Vodka",10,R.color.white));
        list.add(new Alcohol("Beer",2,R.color.brown));
        list.add(new Alcohol("Whiskey",10,R.color.red));
        list.add(new Alcohol("Vine",4,R.color.rose));
        list.add(new Alcohol("Cocktail",7,R.color.green));
        list.add(new Alcohol("Abcent",15,R.color.lime));

        AlcoAdapter alcoAdapter = new AlcoAdapter(this,list,this);
        maxPower = getIntent().getIntExtra(MainActivity.MAX_POWER,0);

        listAlcohol = findViewById(R.id.list_alcohol);
        listAlcohol.setLayoutManager(new LinearLayoutManager(this));
        listAlcohol.setAdapter(alcoAdapter);


    }

    @Override
    public void onClick(Alcohol alcohol) {
        //здесь было         currentState =+ alcohol.power;
        currentState += alcohol.power;
        Log.d("click", "onClick: "+currentState+ " "+ maxPower);
        if(maxPower<currentState){
            Intent intent = new Intent(this,FinalActivity.class);
            startActivity(intent);
        }
    }
}
