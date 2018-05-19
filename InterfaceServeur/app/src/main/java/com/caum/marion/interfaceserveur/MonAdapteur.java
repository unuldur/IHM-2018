package com.caum.marion.interfaceserveur;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class MonAdapteur extends ArrayAdapter<Plat> implements View.OnClickListener{
    public MonAdapteur(Context context, ArrayList<Plat> commande) {
        super(context, 0, commande);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Plat plat = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_plat, parent, false);
        }
        // Lookup view for data population
        TextView tvNom = (TextView) convertView.findViewById(R.id.tvNom);
        TextView tvQuantite = (TextView) convertView.findViewById(R.id.tvQuantite);

        Button btnp = (Button) convertView.findViewById(R.id.btnp);
        btnp.setTag(position);

        Button btnm = (Button) convertView.findViewById(R.id.btnm);
        btnm.setTag(position);

        Button btnmodif = (Button) convertView.findViewById(R.id.btnmodif);
        btnmodif.setTag(position);


        btnp.setOnClickListener(this);
        btnm.setOnClickListener(this);
        btnmodif.setOnClickListener(this);

        // Populate the data into the template view using the data object
        tvNom.setText(plat.nom);
        tvQuantite.setText("x"+plat.quantite);
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Log.v("ALERT", "test coco 1");
        if (v.getId() == R.id.btnmodif){
            Plat plat = getItem((int)v.getTag());
            Log.v("ALERT", "test coco 2 " + plat.nom);

            Log.v("ALERT", "modif ");
            if (plat.nom.contains("Pizza")){
                if (plat.quantite > 1){
                    plat.quantite--;
                }
                else{
                    remove(plat);
                }
                Repository.platAModifier = plat.nom;
                Intent ii = new Intent(this.getContext(), ModifierPlatActivity.class);
                this.getContext().startActivity(ii);
            }
        }
        if (v.getId() == R.id.btnp) {
            Log.v("ALERT", "plat ++ " + v.toString());
            Plat plat = getItem((int)v.getTag());
            plat.quantite++;
            this.notifyDataSetChanged();
        }
        if (v.getId() == R.id.btnm) {
            Log.v("ALERT", "plat -- " + v.toString());
            Plat plat = getItem((int)v.getTag());
            if (plat.quantite > 1){
                plat.quantite--;
            }
            else{
                remove(plat);
            }
            this.notifyDataSetChanged();
        }

    }



}
