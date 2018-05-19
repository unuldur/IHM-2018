package com.caum.marion.interfaceserveur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AjoutPlatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_ajout_plat);
    }

    public void coca(View view) {
        Log.v("ALERT", "Coca");

        for (Table t : Repository.tables){
            if (t.getNom().equals(Repository.tableSelectionnee)){
                t.addPlat("Coca");
            }
        }

        Intent ii = new Intent(this, CommandeActivity.class);

        startActivity(ii);

    }

    public void biere(View view) {
        Log.v("ALERT", "Biere");

        for (Table t : Repository.tables){
            if (t.getNom().equals(Repository.tableSelectionnee)){
                t.addPlat("Biere");
            }
        }

        Intent ii = new Intent(this, CommandeActivity.class);

        startActivity(ii);
    }

    public void pizza(View view) {
        Log.v("ALERT", "Pizza");

        Repository.platAModifier = "Pizza 3 fromages";

        Intent ii = new Intent(this, ModifierPlatActivity.class);

        startActivity(ii);
    }
}
