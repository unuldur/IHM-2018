package com.caum.marion.interfaceserveur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class CommandeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_commande);
        ArrayList<Plat> commandes = new ArrayList<Plat>();
        for (Table t : Repository.tables){
            if (t.getNom().equals(Repository.tableSelectionnee)){
                commandes = t.getCommande();
            }
        }
        MonAdapteur malisteAdapter = new MonAdapteur(this, commandes);

        ListView listView = (ListView) findViewById(R.id.lv_commande);
        listView.setAdapter(malisteAdapter);
    }

    public void recupCommande(View view) {
        Log.v("ALERT", "Recuperation de la commande");
    }

    public void ajouterPlat(View view) {
        Log.v("ALERT", "Ajout d'un plat");

        Intent ii = new Intent(this, AjoutPlatActivity.class);

        startActivity(ii);
    }

    public void valideCommande(View view) {
        Log.v("ALERT", "Valide la commande");

        Intent ii = new Intent(this, TableActivity.class);

        startActivity(ii);
    }
}
