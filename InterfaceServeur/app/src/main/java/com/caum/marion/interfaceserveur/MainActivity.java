package com.caum.marion.interfaceserveur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    public void validerTable(View view) {
        Log.v("ALERT", "Lancement de la gestion des tables");

        Intent ii = new Intent(this, TableActivity.class);

        startActivity(ii);
    }

    public void onTable1(View view) {
        boolean contain = false;

        for (Table t : Repository.tables){
            if(t.getNom().equals("Table 1")){
                contain = true;
                Repository.tables.remove(t);
                Log.v("ALERT", "deselection table 1");
            }
        }

        if (!contain){
            Repository.tables.add(new Table("Table 1"));
            Log.v("ALERT", "selection table 1");
        }
    }

    public void onTable2(View view) {
        boolean contain = false;

        for (Table t : Repository.tables){
            if(t.getNom().equals("Table 2")){
                contain = true;
                Repository.tables.remove(t);
                Log.v("ALERT", "deselection table 2");
            }
        }

        if (!contain){
            Repository.tables.add(new Table("Table 2"));
            Log.v("ALERT", "selection table 2");
        }
    }

    public void onTable3(View view) {
        boolean contain = false;

        for (Table t : Repository.tables){
            if(t.getNom().equals("Table 3")){
                contain = true;
                Repository.tables.remove(t);
                Log.v("ALERT", "deselection table 3");
            }
        }

        if (!contain){
            Repository.tables.add(new Table("Table 3"));
            Log.v("ALERT", "selection table 3");
        }
    }

    public void onTable4(View view) {
        boolean contain = false;

        for (Table t : Repository.tables){
            if(t.getNom().equals("Table 4")){
                contain = true;
                Repository.tables.remove(t);
                Log.v("ALERT", "deselection table 4");
            }
        }

        if (!contain){
            Repository.tables.add(new Table("Table 4"));
            Log.v("ALERT", "selection table 4");
        }
    }

    public void onTable5(View view) {
        boolean contain = false;

        for (Table t : Repository.tables){
            if(t.getNom().equals("Table 5")){
                contain = true;
                Repository.tables.remove(t);
                Log.v("ALERT", "deselection table 5");
            }
        }

        if (!contain){
            Repository.tables.add(new Table("Table 5"));
            Log.v("ALERT", "selection table 5");
        }
    }
}
