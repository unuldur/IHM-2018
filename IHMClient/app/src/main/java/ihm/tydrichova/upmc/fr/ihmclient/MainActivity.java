package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ihm.tydrichova.upmc.fr.ihmclient.data.Clients;
import ihm.tydrichova.upmc.fr.ihmclient.data.Plats;
import ihm.tydrichova.upmc.fr.ihmclient.model.Allergene;
import ihm.tydrichova.upmc.fr.ihmclient.model.Client;
import ihm.tydrichova.upmc.fr.ihmclient.model.Formule;
import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;


public class MainActivity extends AppCompatActivity {

    Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);
        //setupEvenlyDistributedToolbar();
        //mainToolbar.getMenu().getItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        menu.findItem(R.id.menu_return).setEnabled(false);
        menu.findItem(R.id.menu_homepage).setEnabled(false);
        //menu.findItem(R.id.menu_price).setEnabled(false);
        //menu.findItem(R.id.menu_commande).setEnabled(SessionData.getPrix() > 0);
        //menu.findItem(R.id.menu_commande).setEnabled(!menu.findItem(R.id.menu_price).getTitle().toString().equalsIgnoreCase("0 eur"));
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

    public void filtreActivity(View view){
        Intent intent = new Intent(MainActivity.this, FilterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(menu != null) {
            menu.findItem(R.id.menu_price).setTitle(((Float) SessionData.getPrix()).toString());
            menu.findItem(R.id.menu_commande).setEnabled(SessionData.getPrix() > 0);

            if(MainActivity.SessionData.clientConnected()){
                menu.findItem(R.id.menu_client).setTitle(MainActivity.SessionData.getClient().getLogin());
            }
            else{
                menu.findItem(R.id.menu_client).setTitle("Se connecter");
            }
        }
    }

    public void goToCommande(){
        Intent intent = new Intent(MainActivity.this, CommandeActivity.class);
        startActivity(intent);
    }

    protected void onClientClick(){
        if(SessionData.clientConnected()){
            Intent intent = new Intent(MainActivity.this, ClientActivity.class);
            startActivity(intent);
        }
        else{
            showLoginDialog();
        }
    }

    public void categoryClicked(View view){
        Intent intent = new Intent(MainActivity.this, SelectCategoryActivity.class);
        String category = ((Button)findViewById(view.getId())).getText().toString();
        intent.putExtra("layout", category);
        startActivity(intent);
    }

    protected void showLoginDialog(){
        showLoginDialog(0);
    }

    protected void showLoginDialog(int id)
    {

        LayoutInflater li = LayoutInflater.from(this);
        final View prompt = li.inflate(R.layout.login_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);
        final EditText user = (EditText) prompt.findViewById(R.id.login_name);
        final EditText pass = (EditText) prompt.findViewById(R.id.login_password);

        if(id == -1){
            ((TextView) prompt.findViewById(R.id.message)).setText("Nom d'utilisateur ou mot de passe inconnu !");
        }
        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)
        //pass.setText(Login_PASS);
        alertDialogBuilder.setTitle("Connectez vous :");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Connecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
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

                        for(Client client : knownClients){
                            if(client.getLogin().equals(username) && client.passwordCorrect(password)) {
                                SessionData.client = client;
                                menu.findItem(R.id.menu_client).setTitle(client.getLogin());
                                found = true;
                            }
                        }
                        if(!found){
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
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();

            }
        });

        alertDialogBuilder.show();
        //if (Login_USER.length()>1) //if we have the username saved then focus on password field, be user friendly :-)
            //pass.requestFocus();
    }

    public void menuCliked(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void platJourCliked(View view) {
        ArrayList<Plat> platsSpec = new ArrayList<>();
        for(Plat p :Plats.getInstance().getPlats()){
            if(p.isSpec()){
                platsSpec.add(p);
            }
        }
        Intent intent = new Intent(this, CarteActivity.class);
        intent.putParcelableArrayListExtra("plats",platsSpec);
        startActivity(intent);

    }


    public static class SessionData {

        private static TreeSet<Allergene> allergenes = new TreeSet<>();
        private static TreeSet<String> dietesFrequentes = new TreeSet<>();
        private static ArrayList<Plat> platsCommandes = new ArrayList<>();
        private static ArrayList<Formule> formulesCommandees = new ArrayList<>();
        private static Client client = null;

        public static void addAllergene(Allergene allergene){
            allergenes.add(allergene);
        }

        public static void addDiete(String diete){
            dietesFrequentes.add(diete);
        }

        public static void addPlat(Plat plat){
            platsCommandes.add(plat);
        }

        public static void addFormule(Formule formule){
            formulesCommandees.add(formule);
        }

        public static void removeAllergene(Allergene allergene){
            allergenes.remove(allergene);
        }

        public static void removeDiete(String diete){
            dietesFrequentes.remove(diete);
        }

        public static void removePlat(Plat plat){
            platsCommandes.remove(plat);
        }

        public static void removeFormule(Formule formule){
            formulesCommandees.remove(formule);
        }

        public static TreeSet<Allergene> getAllergenes(){
            return allergenes;
        }

        public static TreeSet<String> getDietesFrequentes(){
            return dietesFrequentes;
        }

        public static ArrayList<Plat> getPlatsCommandes(){
            return platsCommandes;
        }

        public static ArrayList<Formule> getFormulesCommandees(){
            return formulesCommandees;
        }

        public static float getPrix(){
            float prix = 0;

            for(Plat plat:platsCommandes){
                prix += plat.getPrix();
            }

            for(Formule formule: formulesCommandees){
                prix+= formule.getPrix();
            }

            return prix;
        }

        public static boolean containsDiete(String diete){
            for(String d : dietesFrequentes){
                if(d.equalsIgnoreCase(diete)){
                    return true;
                }
            }
            return false;

        }

        public static boolean containsAllergene(Allergene allergene){
            for(Allergene a: allergenes){
                if(a.toString().equalsIgnoreCase(allergene.toString())){
                    return true;
                }
            }
            return false;
        }

        public static Client getClient(){
            return client;
        }

        public static void connectClient(Client newClient){
            client = newClient;
        }

        public static void singOut(){
            client = null;
        }

        public static void restart(){
            allergenes = new TreeSet<>();
            dietesFrequentes = new TreeSet<>();
            platsCommandes = new ArrayList<>();
            formulesCommandees = new ArrayList<>();
            client = null;
        }

        public static boolean clientConnected(){
            return client != null;
        }
    }



}
