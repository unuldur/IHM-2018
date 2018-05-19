package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Filter;

import ihm.tydrichova.upmc.fr.ihmclient.data.Clients;
import ihm.tydrichova.upmc.fr.ihmclient.model.Allergene;
import ihm.tydrichova.upmc.fr.ihmclient.model.Client;

public class FilterActivity extends AppCompatActivity {

    private ListView listValues;

    private ArrayAdapter<String> adapter;
    MyCustomAdapter dataAdapter = null;

    private EditText inputSearch;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);
/*
        Allergene[] allergenes = Allergene.values();//Ingredients.getInstance().getIngredients();

        listValues = (ListView) findViewById(R.id.searchIngredientsListView);
        List<String> lv = new ArrayList<String>();

        for(Allergene allergene : allergenes){
            lv.add(allergene.toString());
        }

        adapter = new ArrayAdapter<String>(this, R.layout.filter_row_layout, R.id.listText ,lv);
        listValues.setAdapter(adapter);
        */

/*
        // Get listview checkbox.
        listValues = (ListView)findViewById(R.id.searchIngredientsListView);

        // Initiate listview data.
        Allergene[] initItemList = Allergene.values();

        // Create a custom list view adapter with checkbox control.
         FilterAdapter listViewDataAdapter = new FilterAdapter(getApplicationContext(), new ArrayList<Allergene>(Arrays.asList(initItemList)));

        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listValues.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        listValues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                Allergene itemDto = (Allergene) itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.ingredientCheckBox);

                // Reverse the checkbox and clicked item check state.
                //if(itemDto.isChecked())
                if(MainActivity.SessionData.containsAllergene(itemDto))
                {
                    itemCheckbox.setChecked(false);
                    MainActivity.SessionData.removeAllergene(itemDto);
                    //itemDto.setChecked(false);
                }else
                {
                    itemCheckbox.setChecked(true);
                    MainActivity.SessionData.addAllergene(itemDto);
                    //itemDto.setChecked(true);
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }
        });*/

        //Generate list View from ArrayList
        displayListView();

        inputSearch = (EditText) findViewById(R.id.searchIngredientsEditext);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

                FilterActivity.this.dataAdapter.getFilter().filter(cs.toString());
                //FilterActivity.this.dataAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        enableDietes();
    }

    private void displayListView() {

        //Array list of countries
        Allergene[] initItemList = Allergene.values();
        ArrayList<Allergene> allergeneList = new ArrayList<Allergene>(Arrays.asList(initItemList));

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.filter_row_layout, allergeneList);
        ListView listView = (ListView) findViewById(R.id.searchIngredientsListView);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Allergene allergene = (Allergene) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), "Clicked on Row: " + allergene.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<Allergene> implements Filterable{

        private ArrayList<Allergene> allergeneList;
        private List<String>originalData = null;
        private List<String>filteredData = null;
        private ItemFilter mFilter = new ItemFilter();

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Allergene> allergeneList) {
            super(context, textViewResourceId, allergeneList);

            ArrayList<String> allergenesString = new ArrayList<>();
            for(Allergene allergene : allergeneList){
                allergenesString.add(allergene.toString());
            }

            this.filteredData =  allergenesString;
            this.originalData = allergenesString ;
            this.allergeneList = new ArrayList<Allergene>();
            this.allergeneList.addAll(allergeneList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        public android.widget.Filter getFilter() {
            return mFilter;
        }

        private class ItemFilter extends android.widget.Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<String> list = originalData;

                int count = list.size();
                final ArrayList<String> nlist = new ArrayList<String>(count);

                String filterableString ;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.add(filterableString);
                    }
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.filter_row_layout, null);

                holder = new ViewHolder();

                holder.code = (TextView) convertView.findViewById(R.id.listText);
                holder.name = (CheckBox) convertView.findViewById(R.id.ingredientCheckBox);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Allergene allergene = (Allergene) cb.getTag();
                        //Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                        //allergene.setSelected(cb.isChecked());
                        if (cb.isChecked()) {
                            MainActivity.SessionData.addAllergene(allergene);
                        } else {
                            MainActivity.SessionData.removeAllergene(allergene);
                        }
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Allergene allergene = allergeneList.get(position);
            holder.code.setText(filteredData.get(position));
            //holder.code.setText(allergene.name());
            holder.name.setText("");
            holder.name.setChecked(/*allergene.isSelected()*/MainActivity.SessionData.containsAllergene(allergene));
            holder.name.setTag(allergene);

            return convertView;

        }

        public int getCount() {
            return filteredData.size();
        }

        public Allergene getItem(int position) {
            for(Allergene allergene :allergeneList){
                if(allergene.toString().equalsIgnoreCase(filteredData.get(position))){
                    return allergene;
                }
            }
            return  null;
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

    public void enableDietes() {
        TreeSet<String> dietes = MainActivity.SessionData.getDietesFrequentes();
        ArrayList<CheckBox> dietesCB = new ArrayList<>();

        dietesCB.add((CheckBox) findViewById(R.id.glutenCheckBox));
        dietesCB.add((CheckBox) findViewById(R.id.lactoseCheckBox));
        dietesCB.add((CheckBox) findViewById(R.id.vegetarianCheckBox));
        dietesCB.add((CheckBox) findViewById(R.id.veganCheckBox));

        for (CheckBox cb : dietesCB) {
            cb.setChecked(MainActivity.SessionData.containsDiete(cb.getText().toString()));
        }
        //findViewById(R.id.glutenCheckBox).setEnabled(MainActivity.SessionData.containsDiete());
    }

    public void updateDiets(View view){
        CheckBox diete = (CheckBox) findViewById(view.getId());

        if(diete.getText().toString().equalsIgnoreCase("végétarien") || diete.getText().toString().equalsIgnoreCase("vegan")){
            if(diete.isChecked()) {
                MainActivity.SessionData.addDiete(diete.getText().toString());
            }
            else{
                MainActivity.SessionData.removeDiete(diete.getText().toString());
            }
        }
        if(diete.getText().toString().equalsIgnoreCase(((CheckBox)findViewById(R.id.lactoseCheckBox)).getText().toString())){
            if(diete.isChecked()){
                MainActivity.SessionData.addAllergene(Allergene.LACTOSE);
            }
            else{
                MainActivity.SessionData.removeAllergene(Allergene.LACTOSE);
            }
        }
        if(diete.getText().toString().equalsIgnoreCase(((CheckBox)findViewById(R.id.glutenCheckBox)).getText().toString())){
            if(diete.isChecked()){
                MainActivity.SessionData.addAllergene(Allergene.GLUTEN);
            }
            else{
                MainActivity.SessionData.removeAllergene(Allergene.GLUTEN);
            }
        }
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
        startActivity(new Intent(FilterActivity.this, MainActivity.class));
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
        Intent intent = new Intent(FilterActivity.this, CommandeActivity.class);
        startActivity(intent);
    }

    protected void onClientClick() {
        if (MainActivity.SessionData.clientConnected()) {
            Intent intent = new Intent(FilterActivity.this, ClientActivity.class);
            startActivity(intent);
        } else {
            showLoginDialog();
        }
    }

    public void validerAction(View view){
        finish();
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
