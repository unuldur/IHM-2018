package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

public class ClientActivity extends AppCompatActivity {

    private Menu menu = null;
    private MyCustomAdapter dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);

        displayListView();
    }

    private void displayListView() {

        //Array list of countries
        //Allergene[] initItemList = Allergene.values();
        //ArrayList<Allergene> allergeneList = new ArrayList<Allergene>(Arrays.asList(initItemList));
        ArrayList<Plat> plats = new ArrayList<>(MainActivity.SessionData.getClient().getPlatsWithNotes().keySet());

        //create an ArrayAdaptar from the String Array
        dataAdapter = new ClientActivity.MyCustomAdapter(this, R.layout.carte_row_layout, plats);
        ListView listView = (ListView) findViewById(R.id.list);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Plat plat = (Plat) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), "Clicked on Row: " + allergene.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        this.menu = menu;
        menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
        menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);

        if(MainActivity.SessionData.clientConnected()){
            menu.findItem(R.id.menu_client).setTitle(MainActivity.SessionData.getClient().getLogin());
        }
        else{
            menu.findItem(R.id.menu_client).setTitle("Se connecter");
        }
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_return:
                finish();
                return true;
            case R.id.menu_homepage:
                goToHomepage();
                return true;
            case R.id.menu_commande:
                goToCommande();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToHomepage() {
        startActivity(new Intent(ClientActivity.this, MainActivity.class));
        finish();
    }

    public void goToCommande() {
        Intent intent = new Intent(ClientActivity.this, CommandeActivity.class);
        startActivity(intent);
    }


    public void deconnect(View view){
        MainActivity.SessionData.singOut();
        finish();
    }

    private class MyCustomAdapter extends ArrayAdapter<Plat> {

        private ArrayList<Plat> platList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Plat> platList) {
            super(context, textViewResourceId, platList);
            this.platList = new ArrayList<Plat>();
            this.platList.addAll(platList);
        }

        private class ViewHolder {
            TextView text;
            TextView price;
            Button button;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ClientActivity.MyCustomAdapter.ViewHolder holder = null;
            //Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.client_history_low_layout, null);

                holder = new ClientActivity.MyCustomAdapter.ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.listText);
                holder.price = (TextView) convertView.findViewById(R.id.listPrice);
                holder.button = (Button) convertView.findViewById(R.id.button_commander);
                convertView.setTag(holder);

                holder.button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        Plat plat = (Plat) btn.getTag();
                        //Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                        //allergene.setSelected(cb.isChecked());
                        MainActivity.SessionData.addPlat(plat);
                        //dataAdapter.notifyDataSetChanged();
                        menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
                        menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage(plat.getName()+" a été ajouté(e) à la commande");

                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder1.setNegativeButton(
                                "Consulter la commande",  new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(ClientActivity.this, CommandeActivity.class);
                                        startActivity(intent);
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }
                });
            } else {
                holder = (ClientActivity.MyCustomAdapter.ViewHolder) convertView.getTag();
            }

            Plat plat = platList.get(position);
            holder.text.setText(plat.getName());
            holder.price.setText(((Float) plat.getPrix()).toString() + "€");
            //holder.name.setChecked(/*allergene.isSelected()*/MainActivity.SessionData.containsPlat(plat));
            holder.button.setTag(plat);

            return convertView;

        }

    }
}
