package ihm.tydrichova.upmc.fr.ihmclient;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import ihm.tydrichova.upmc.fr.ihmclient.data.Clients;
import ihm.tydrichova.upmc.fr.ihmclient.model.Client;
import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

public class CommandeActivity extends AppCompatActivity {

    private Menu menu = null;
    MyCustomAdapter dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);

        ArrayList<Plat> plats = MainActivity.SessionData.getPlatsCommandes();
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
            Button button;
            Button button2;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            MyCustomAdapter.ViewHolder holder = null;
            //Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.commande_row_layout, null);

                holder = new MyCustomAdapter.ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.listText);
                holder.price = (TextView) convertView.findViewById(R.id.listPrice);
                holder.button = (Button) convertView.findViewById(R.id.button_commander);
                holder.button2 = (Button) convertView.findViewById(R.id.button_details_commande);
                convertView.setTag(holder);

                holder.button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        Plat plat = (Plat) btn.getTag();
                        //Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                        //allergene.setSelected(cb.isChecked());
                        MainActivity.SessionData.removePlat(plat);
                        dataAdapter.notifyDataSetChanged();
                        menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
                        menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);
                    }
                });
                final View finalConvertView = convertView;
                holder.button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                        builder.setMessage(platList.get(position).getDescription())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        builder.create().show();
                    }
                });
            } else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
            }

            Plat plat = platList.get(position);
            holder.text.setText(plat.getName());
            holder.price.setText(((Float) plat.getPrix()).toString() + "€");
            //holder.name.setChecked(/*allergene.isSelected()*/MainActivity.SessionData.containsPlat(plat));
            holder.button.setTag(plat);

            return convertView;

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        this.menu = menu;
        menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
        menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);

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
            case R.id.menu_client:
                onClientClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToHomepage() {
        startActivity(new Intent(CommandeActivity.this, MainActivity.class));
        finish();
    }

    @Override
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

    public void appelerServeur(View view){
        Intent intent = new Intent(CommandeActivity.this, LastActivity.class);
        if(MainActivity.SessionData.getClient() != null){
            for (Plat plat:
                MainActivity.SessionData.getPlatsCommandes()){
                    MainActivity.SessionData.getClient().addPlat(plat);
            }}
        startActivity(intent);
    }

    protected void onClientClick() {
        if (MainActivity.SessionData.clientConnected()) {
            Intent intent = new Intent(CommandeActivity.this, ClientActivity.class);
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