package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class LastActivity extends AppCompatActivity {

    private Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(null);
        Button b = findViewById(R.id.button_go_notation);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(view.getContext(), NoteActivity.class);
                startActivity(t);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        menu.findItem(R.id.menu_return).setEnabled(false);
        menu.findItem(R.id.menu_homepage).setEnabled(false);
        menu.findItem(R.id.menu_price).setEnabled(false);
        menu.findItem(R.id.menu_commande).setEnabled(false);
        this.menu = menu;
        menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
        menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);

        if(MainActivity.SessionData.clientConnected()){
            menu.findItem(R.id.menu_client).setTitle(MainActivity.SessionData.getClient().getLogin());
        }
        else{
            menu.findItem(R.id.menu_client).setTitle("Se connecter");
        }
        menu.findItem(R.id.menu_client).setEnabled(false);
        return true;
    }


}
