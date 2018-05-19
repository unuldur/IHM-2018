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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.TreeSet;

import ihm.tydrichova.upmc.fr.ihmclient.data.Clients;
import ihm.tydrichova.upmc.fr.ihmclient.model.Client;
import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

public class CarteActivity extends AppCompatActivity {


    private TextView text;
    private ListView listValues;
    MyCustomAdapter dataAdapter = null;
    private Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);
        Bundle data = getIntent().getExtras();
        /*ArrayList<Plat> plats = data.getParcelableArrayList("plats");
        //ArrayList<Plat> plats = getIntent().getParcelableArrayListExtra("plats");
        text = (TextView) findViewById(R.id.mainText);
        listValues = (ListView) findViewById(R.id.list);
        List<Plat>  lv = new ArrayList<Plat>();
        //listValues.addView(new TextView("Android"));
        for(Plat plat : plats){
            lv.add(plat);
        }


        // initiate the listadapter
        PlatArrayAdapter myAdapter = new PlatArrayAdapter(this, R.layout.carte_row_layout,lv);

        // assign the list adapter
        //setListAdapter(myAdapter);
        listValues.setAdapter(myAdapter);*/

        //Generate list View from ArrayList
        displayListView();

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);
    }

    private void displayListView() {

        //Array list of countries
        //Allergene[] initItemList = Allergene.values();
        //ArrayList<Allergene> allergeneList = new ArrayList<Allergene>(Arrays.asList(initItemList));
        Bundle data = getIntent().getExtras();
        ArrayList<Plat> plats = data.getParcelableArrayList("plats");

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.carte_row_layout, plats);
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
            RatingBar note;
            Button button;
            Button description;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.carte_row_layout, null);

                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.listText);
                holder.price = (TextView) convertView.findViewById(R.id.listPrice);
                holder.note =  convertView.findViewById(R.id.ratingBar_carte);
                holder.button = (Button) convertView.findViewById(R.id.button_commander);
                holder.description = (Button) convertView.findViewById(R.id.button_detailes);
                convertView.setTag(holder);

                holder.button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        Plat plat = (Plat) btn.getTag();
                        MainActivity.SessionData.addPlat(plat);
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
                                        Intent intent = new Intent(CarteActivity.this, CommandeActivity.class);
                                        startActivity(intent);
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }
                });
                holder.description.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        Plat plat = (Plat) btn.getTag();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        String description = plat.getDescription();
                        if(description== null || description.equals("")){
                            builder1.setMessage("Il n'y a pas de description spécifique.");
                        }
                        else {
                            builder1.setMessage(plat.getDescription());
                        }
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Plat plat = platList.get(position);
            holder.text.setText(plat.getName());
            holder.price.setText(String.valueOf(plat.getPrix()) + "€");

            holder.note.setRating(plat.getNote());
            //holder.name.setChecked(/*allergene.isSelected()*/MainActivity.SessionData.containsPlat(plat));
            holder.button.setTag(plat);
            holder.description.setTag(plat);
            return convertView;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        this.menu = menu;

        if (MainActivity.SessionData.clientConnected()) {
            menu.findItem(R.id.menu_client).setTitle(MainActivity.SessionData.getClient().getLogin());
        } else {
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
                return true;
            case R.id.menu_client:
                onClientClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToHomepage() {
        startActivity(new Intent(CarteActivity.this, MainActivity.class));
        finish();
    }

    protected void onResume() {
        super.onResume();
        if (menu != null) {
            menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
            menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);

            if (MainActivity.SessionData.clientConnected()) {
                menu.findItem(R.id.menu_client).setTitle(MainActivity.SessionData.getClient().getLogin());
            } else {
                menu.findItem(R.id.menu_client).setTitle("Se connecter");
            }
        }
    }

    public void goToCommande() {
        Intent intent = new Intent(CarteActivity.this, CommandeActivity.class);
        startActivity(intent);
    }

    protected void onClientClick() {
        if (MainActivity.SessionData.clientConnected()) {
            Intent intent = new Intent(CarteActivity.this, ClientActivity.class);
            startActivity(intent);
        } else {
            showLoginDialog();
        }
    }

    protected void showLoginDialog() {
        showLoginDialog(0);
    }

    protected void showLoginDialog(int id) {

        LayoutInflater li = LayoutInflater.from(this);
        final View prompt = li.inflate(R.layout.login_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);
        final EditText user = (EditText) prompt.findViewById(R.id.login_name);
        final EditText pass = (EditText) prompt.findViewById(R.id.login_password);

        if (id == -1) {
            ((TextView) prompt.findViewById(R.id.message)).setText("Nom d'utilisateur ou mot de passe inconnu !");
        }
        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)
        //pass.setText(Login_PASS);
        alertDialogBuilder.setTitle("Connectez vous :");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Connecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*
                        if (!Utils.hasConnectivity(MainActivity.this,false)) //check for internet connectivity
                        {
                            Toast.makeText(MainActivity.this,"No internet access... please connect.", Toast.LENGTH_LONG).show();
                            showLoginDialog();
                            return;
                        }*/

                        String password = pass.getText().toString();
                        String username = user.getText().toString();

                        TreeSet<Client> knownClients = new TreeSet<>();
                        knownClients.addAll(Clients.getInstance().getClients());
                        boolean found = false;

                        for (Client client : knownClients) {
                            if (client.getLogin().equals(username) && client.passwordCorrect(password)) {
                                MainActivity.SessionData.connectClient(client);
                                menu.findItem(R.id.menu_client).setTitle(client.getLogin());
                                found = true;
                            }
                        }
                        if (!found) {
                            showLoginDialog(-1);
                        }
                        /*
                        try
                        {
                            if ( username.length()<2 || password.length()<2)
                            {
                                Toast.makeText(MainActivity.this,"Invalid username or password", Toast.LENGTH_LONG).show();
                                showLoginDialog();
                            }
                            else
                            {
                                //password=MCrypt3DES.computeSHA1Hash(password); //password is hashed SHA1
                                //TODO here any local checks if password or user is valid

                                //this will do the actual check with my back-end server for valid user/pass and callback with the response
                                //new CheckLoginAsync(MainActivity.this,username,password).execute("","");
                            }
                        }catch(Exception e)
                        {
                            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                        }*/
                    }
                });

        alertDialogBuilder.setNegativeButton("Retour", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        alertDialogBuilder.show();
    }
}
