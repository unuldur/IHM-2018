package com.caum.marion.interfaceserveur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class ModifierPlatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_modifier_plat);
        CheckBox checkBoxOlive = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox checkBoxFromage = (CheckBox) findViewById(R.id.checkBox7);
        if (Repository.platAModifier.contains("olive")){
            checkBoxOlive.setChecked(true);
        }
        if (Repository.platAModifier.contains("supplement")){
            checkBoxFromage.setChecked(true);
        }
    }

    public void onOlive(View view){
        if (Repository.platAModifier.contains("olives")){
            if (Repository.platAModifier.contains("supplement")){
                Repository.platAModifier = "Pizza 3 fromages supplement fromage";
            }
            else{
                Repository.platAModifier = "Pizza 3 fromages";
            }
        }
        else{
            if (Repository.platAModifier.contains("supplement")){
                Repository.platAModifier = "Pizza 3 fromages sans olives supplement fromage";
            }
            else{
                Repository.platAModifier = "Pizza 3 fromages sans olives";
            }
        }
    }

    public void onFromage(View view){
        if (Repository.platAModifier.contains("supplement")){
            if (Repository.platAModifier.contains("olive")){
                Repository.platAModifier = "Pizza 3 fromages sans olives";
            }
            else{
                Repository.platAModifier = "Pizza 3 fromages";
            }
        }
        else{
            if (Repository.platAModifier.contains("olive")){
                Repository.platAModifier = "Pizza 3 fromages sans olives supplement fromage";
            }
            else{
                Repository.platAModifier = "Pizza 3 fromages supplement fromage";
            }
        }
    }

    public void validModif(View view) {
        Log.v("ALERT", "Valide les modificaions");

        for (Table t : Repository.tables){
            if (t.getNom().equals(Repository.tableSelectionnee)){
                t.addPlat(Repository.platAModifier);
            }
        }
        Repository.platAModifier = "";

        Intent ii = new Intent(this, CommandeActivity.class);

        startActivity(ii);
    }
}
