package com.caum.marion.interfaceserveur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_table);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner);

        List<String> list = new ArrayList<String>();
        for (Table t : Repository.tables) {
            list.add(t.getNom());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setSelection(dataAdapter.getPosition(Repository.tableSelectionnee));
    }

    public void retourAccueil(View view) {
        Log.v("ALERT", "Retour a l'ecran d'accueil");

        Intent ii = new Intent(this, MainActivity.class);

        startActivity(ii);

        Repository.tables = new ArrayList<Table>();
        Repository.tableSelectionnee = "";
    }

    public void voirCommande(View view) {
        Log.v("ALERT", "Visualisation de la commande");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Repository.tableSelectionnee = String.valueOf(spinner.getSelectedItem());

        Intent ii = new Intent(this, CommandeActivity.class);

        startActivity(ii);
    }

    public void imprimeFacture(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Repository.tableSelectionnee = String.valueOf(spinner.getSelectedItem());

        Log.v("ALERT", "Impression de la facture de la "+Repository.tableSelectionnee);
    }

    public void register(View view) {
        Log.v("ALERT", "Enregistrement d'un client");

        Intent ii = new Intent(this, RegisterActivity.class);

        startActivity(ii);
    }

    public void viderTable(View view) {
        Log.v("ALERT", "Table vide");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Repository.tableSelectionnee = String.valueOf(spinner.getSelectedItem());

        for (Table t : Repository.tables){
            if (t.getNom().equals(Repository.tableSelectionnee)){
                t.setCommande(new ArrayList<Plat>());
                Log.v("ALERT", t.toString());
            }
        }


    }

    public void vote(View view) {
        Log.v("ALERT", "Les clients votent");
    }
}
