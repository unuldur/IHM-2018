package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import ihm.tydrichova.upmc.fr.ihmclient.data.Clients;
import ihm.tydrichova.upmc.fr.ihmclient.data.Formules;
import ihm.tydrichova.upmc.fr.ihmclient.model.Client;
import ihm.tydrichova.upmc.fr.ihmclient.model.Formule;
import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

/**
 * Created by Unuldur on 12/05/2018.
 */

public class MenuActivity extends AppCompatActivity {
    private Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        displayListView();

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);
    }

    private void displayListView() {

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
        ExpandableListView lv = findViewById(R.id.formules);
        ExpandableListAdapter adapter  = new FormuleListAdapater(this, new ArrayList<>(Formules.getInstance().getFormules()), menu);
        lv.setAdapter(adapter);
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
        startActivity(new Intent(this, MainActivity.class));
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
        Intent intent = new Intent(this, CommandeActivity.class);
        startActivity(intent);
    }

    protected void onClientClick() {
        if (MainActivity.SessionData.clientConnected()) {
            Intent intent = new Intent(this, ClientActivity.class);
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
