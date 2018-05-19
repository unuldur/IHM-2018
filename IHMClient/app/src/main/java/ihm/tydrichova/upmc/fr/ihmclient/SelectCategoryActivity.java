package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import ihm.tydrichova.upmc.fr.ihmclient.CarteActivity;
import ihm.tydrichova.upmc.fr.ihmclient.MainActivity;
import ihm.tydrichova.upmc.fr.ihmclient.R;
import ihm.tydrichova.upmc.fr.ihmclient.model.*;
import ihm.tydrichova.upmc.fr.ihmclient.data.*;

public class SelectCategoryActivity extends AppCompatActivity {

    private Menu menu = null;
    private String key = null;
    private TreeSet<Plat> allPlats = new TreeSet<>();
    private TreeSet<Plat> preselectedPlats = new TreeSet<>();
    private HashMap<Categorie, Set<Plat>> platsByCategories = new HashMap<>();
    private boolean isEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String layout = getIntent().getStringExtra("layout");
        System.out.print(layout);
        switch (layout) {
            case ("Plats"):
                setContentView(R.layout.activity_select_category_plats);
                key = "Plats";
                break;
            case ("Entrées"):
                setContentView(R.layout.activity_select_category_entrees);
                key = "Entrées";
                break;
            case ("Desserts"):
                setContentView(R.layout.activity_select_category_desserts);
                key = "Desserts";
                break;
            case ("Boissons"):
                setContentView(R.layout.activity_select_category_boissons);
                key = "Boissons";
                break;
            case ("Accompagnements"):
                setContentView(R.layout.activity_select_category_acc);
                key = "Accompagnements";
                break;
            default:
                throw new RuntimeException("Unknow layout");
        }
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);
        preselectPlats();

        Button valider = (Button) findViewById(R.id.button_valider);
        valider.setEnabled(false);
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
                return true;
            case R.id.menu_client:
                onClientClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void preselectPlats() {

        LinearLayout container = (LinearLayout) findViewById(R.id.layout_checkBoxes);
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;

                cb.setEnabled(false);
            }
        }
        allPlats.clear();

        ArrayList<Carte> cartes = Cartes.getInstance().getCartes();
        ArrayList<Carte> cartes2 = new ArrayList<>();
        TreeSet<Plat> plats = new TreeSet<>();
        for (Carte carte : cartes) {
            cartes2.add(carte);
            Set<Categorie> keys = carte.getCarte().keySet();
            for (Categorie cat : keys) {
                if (cat.getName().toLowerCase().startsWith(key.toLowerCase())) {
                    int nbPlats = 0;
                    Set<Plat> platsOfCat = carte.getCarte().get(cat);
                    Set<Plat> addToPlatsOfCategories = new TreeSet<>();

                    for (Plat plat : platsOfCat) {
                        if (noAllergene(plat) || noDiete(plat)) {
                            addToPlatsOfCategories.add(plat);
                            nbPlats++;
                        }
                    }

                    for (int i = 0; i < container.getChildCount(); i++) {
                        View v = container.getChildAt(i);
                        if (v instanceof CheckBox) {
                            CheckBox cb = (CheckBox) v;
                            cb.setEnabled((cb.isEnabled()) || (nbPlats > 0 && cat.getName().toLowerCase().endsWith(cb.getText().toString().toLowerCase())));
                            if (cat.getName().toLowerCase().endsWith(cb.getText().toString().toLowerCase())) {
                                platsByCategories.put(cat, addToPlatsOfCategories);
                            }
                        }
                    }

                }
            }
        }
    }


    protected boolean noDiete(Plat plat){
        for(String diete :MainActivity.SessionData.getDietesFrequentes()){
            if(diete.equalsIgnoreCase("végétarien") && !isVegetarian(plat)){
                return false;
            }
            if(diete.equalsIgnoreCase("vegan") && !isVegan(plat)){
                return false;
            }
        }
    return true;
    }

    protected boolean noAllergene(Plat plat) {
        for (Allergene allergene : MainActivity.SessionData.getAllergenes()) {
            for (Ingredient ingredient : plat.getIngredients().keySet()) {
                if (plat.getIngredients().get(ingredient)) {
                    for (Allergene allergene1 : ingredient.getAllergenes()) {
                        if (allergene.toString().equalsIgnoreCase(allergene1.toString())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    //renvoie true si aucune ingrédient n'est pas de type VIANDE ou POISSON
    protected boolean isVegetarian(Plat plat){
        for(Ingredient ingredient : plat.getIngredients().keySet()) {
            if (plat.getIngredients().get(ingredient)) {
                if (ingredient.getType().toString().equalsIgnoreCase("VIANDE") || ingredient.getType().toString().equalsIgnoreCase("POISSOn"))
                    return false;
            }
        }
        return true;
    }

    protected boolean isVegan(Plat plat){
        for(Ingredient ingredient : plat.getIngredients().keySet()) {
            if (plat.getIngredients().get(ingredient)) {
                if (ingredient.getType().toString().equalsIgnoreCase("VIANDE") || ingredient.getType().toString().equalsIgnoreCase("POISSOn") ||
                        ingredient.getType().toString().equalsIgnoreCase("VEGETARIEN"))
                    return false;
            }
        }
        return true;
    }

    public void enableValider(View v){
        Button valider = (Button) findViewById(R.id.button_valider);
        valider.setEnabled(false);


        LinearLayout container = (LinearLayout) findViewById(R.id.layout_checkBoxes);
        for (int i = 0; i < container.getChildCount(); i++) {
            View v2 = container.getChildAt(i);
            if (v2 instanceof CheckBox) {
                CheckBox cb = (CheckBox) v2;
                if(cb.isChecked()){
                    valider.setEnabled(true);
                }

                }

            }
    }


    public void goToHomepage() {
        startActivity(new Intent(SelectCategoryActivity.this, MainActivity.class));
        finish();
    }

    public void validerAction(View view) {
            Intent intent = new Intent(SelectCategoryActivity.this, CarteActivity.class);

            //ArrayList<Plat> plats = (ArrayList<Plat>)Plats.getInstance().getPlats();


            preselectedPlats.clear();
            LinearLayout container = (LinearLayout) findViewById(R.id.layout_checkBoxes);
            for (int i = 0; i < container.getChildCount(); i++) {
                View v = container.getChildAt(i);
                if (v instanceof CheckBox) {
                    CheckBox cb = (CheckBox) v;
                    for (Categorie cat : platsByCategories.keySet()) {
                        if (cb.isChecked() && cat.getName().toLowerCase().endsWith(cb.getText().toString().toLowerCase())) {
                            preselectedPlats.addAll(platsByCategories.get(cat));
                        }
                    }
                    // isChecked should be a boolean indicating if every Checkbox should be checked or not

                }

                ArrayList<Plat> platsToSend = new ArrayList<>();
                platsToSend.addAll(preselectedPlats);
                intent.putParcelableArrayListExtra("plats", platsToSend);
            }
            startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menu != null) {
            menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
            menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);
            if(MainActivity.SessionData.clientConnected()){
                menu.findItem(R.id.menu_client).setTitle(MainActivity.SessionData.getClient().getLogin());
            }
            else{
                menu.findItem(R.id.menu_client).setTitle("Se connecter");
            }
        }
        preselectPlats();
    }

    public void goToCommande() {
        Intent intent = new Intent(SelectCategoryActivity.this, CommandeActivity.class);
        startActivity(intent);
    }

    public void filtreActivity(View view) {
        Intent intent = new Intent(SelectCategoryActivity.this, FilterActivity.class);
        startActivity(intent);
    }

    protected void onClientClick() {
        if (MainActivity.SessionData.clientConnected()) {
            Intent intent = new Intent(SelectCategoryActivity.this, ClientActivity.class);
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
