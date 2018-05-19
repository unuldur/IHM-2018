package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ListView lv = findViewById(R.id.lv_notation);
        lv.setAdapter(new NoteAdapter(this, 0, MainActivity.SessionData.getPlatsCommandes()));
        Button b = findViewById(R.id.end_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(view.getContext(), MainActivity.class);
                MainActivity.SessionData.restart();
                startActivity(t);
            }
        });
    }
}
