package com.jeyarajaratnam.j.ihm.interfacecuisinier;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MissingIngredientActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ingredientList;
    private ArrayList<Spinner> spinnersIngredients=new ArrayList<Spinner>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_ingredient);

        ingredientList=(LinearLayout)findViewById(R.id.ll_ingredientList);

        Button add=(Button)findViewById(R.id.bt_add);
        Button cancel=(Button)findViewById(R.id.bt_cancel);
        Button ok=(Button)findViewById(R.id.bt_ok);
        cancel.setOnClickListener(this);
        add.setOnClickListener(this);
        ok.setOnClickListener(this);



        addIngredient();
    }

    private int getArrayIDbyCategorie(String categorie){
        int id;
        switch(categorie){
            case "Legumes":
                id=R.array.Legumes;
                break;
            case "Fruits":
                id= R.array.Fruits;
                break;
            case "Surgelés":
                id= R.array.Surgelés;
                break;
            case "Pâtisseries":
                id= R.array.Pâtisseries;
                break;
            default:
                id= R.array.Fruits;
                break;
        }
        return id;

    }
    @TargetApi(Build.VERSION_CODES.M)
    private void addIngredient(){

        //Set adapter for spinners
        ArrayAdapter<CharSequence> adapterI = ArrayAdapter.createFromResource(this,
                R.array.Legumes, android.R.layout.simple_spinner_item);
        adapterI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(this,
                R.array.categorie_array, android.R.layout.simple_spinner_item);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Set layout
        float txtSize=20;

        LinearLayout ll_new=new LinearLayout(this);
        ll_new.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(5,5,5,5);
        ll_new.setLayoutParams(llp);
        ll_new.setBackground(getResources().getDrawable(R.drawable.border_ingredient,null));

        LinearLayout ll_categorie=new LinearLayout(this);

        ll_categorie.setOrientation(LinearLayout.HORIZONTAL);
        llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        llp.weight=1;
        llp.setMargins(5,5,5,5);
        ll_categorie.setLayoutParams(llp);

        TextView tv=new TextView(this);
        llp=new LinearLayout.LayoutParams( 0,LinearLayout.LayoutParams.MATCH_PARENT);
        llp.weight=1;
        tv.setLayoutParams(llp);
        tv.setText(getResources().getString(R.string.categorie));
        tv.setTextSize(txtSize);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.taupe,null));

        TextView tv2=new TextView(this);
        tv2.setLayoutParams(llp);
        tv2.setText(getResources().getString(R.string.ingredients));
        tv2.setTextSize(txtSize);
        tv2.setGravity(Gravity.CENTER);

        tv2.setTextColor(getResources().getColor(R.color.taupe,null));

        final Spinner spin=new Spinner(this);
        llp=new LinearLayout.LayoutParams( 0,LinearLayout.LayoutParams.MATCH_PARENT);
        llp.weight=2;
        spin.setLayoutParams(llp);

        spin.setGravity(Gravity.CENTER);
        spin.setAdapter(adapterI);
        int idspinI=spin.generateViewId();
        spin.setId(idspinI);


        spinnersIngredients.add(spin);

        final Spinner spin2=new Spinner(this);
        spin2.setLayoutParams(llp);
        spin2.setGravity(Gravity.CENTER);
        spin2.setAdapter(adapterC);
        int idspinC=spin2.generateViewId();
        spin2.setId(idspinC);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                spin.setAdapter((new ArrayAdapter<String>(MissingIngredientActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(getArrayIDbyCategorie(parent.getItemAtPosition(i).toString())))));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ImageButton cancelBtn=new ImageButton(this);
        cancelBtn.setImageDrawable(getDrawable(R.drawable.cancel));
        cancelBtn.setTag(spin);
        cancelBtn.setAdjustViewBounds(true);
        cancelBtn.setScaleType(ImageButton.ScaleType.FIT_CENTER);


        cancelBtn.setLayoutParams(new LinearLayout.LayoutParams(30,30));
        cancelBtn.setBackgroundColor(Color.TRANSPARENT);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ingredientList.removeView((View)view.getParent());
                spinnersIngredients.remove((Spinner)view.getTag());
            }
        });
        ll_new.addView(cancelBtn);

        tv.setLabelFor(idspinC);
        tv2.setLabelFor(idspinI);

        ll_categorie.addView(tv);
        ll_categorie.addView(spin2);
        ll_new.addView(ll_categorie);


        LinearLayout ll_ingredient=new LinearLayout(this);
        ll_ingredient.setOrientation(LinearLayout.HORIZONTAL);
        llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        llp.setMargins(5,5,5,5);
        llp.weight=1;
        ll_ingredient.setLayoutParams(llp);

        ll_ingredient.addView(tv2);
        ll_ingredient.addView(spin);

        ll_new.addView(ll_ingredient);
        int idLL=ll_new.generateViewId();
        ll_new.setId(idLL);

        ingredientList.addView(ll_new);

    }

    public ArrayList<String> getSpinnerItems(){
        ArrayList<String> res=new ArrayList<String>();
        for (Spinner s : spinnersIngredients){
            res.add(s.getSelectedItem().toString());
        }
        return res;
    }

    public void showResume(String content,final Activity a){
        String title="CONFIRMATION";
        String positive="Signaler";
        String negative="Annuler";

        if(content.length()==0) {
            Toast toast = Toast.makeText(getApplicationContext(), "\nThere is no ingredients !!\n", Toast.LENGTH_SHORT);
            toast.show();
        }

        else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            content = "Signaler les ingrédients suivants ?\n" + content;
            builder.setTitle(title)
                    .setMessage(content)
                    .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void onClick(DialogInterface dialog, int which) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Ingrédients manquants signalés", Toast.LENGTH_SHORT);
                            toast.show();
                            dialog.cancel();
                            a.onBackPressed();
                        }
                    })
                    .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_add:
                addIngredient();
                break;

            case R.id.bt_cancel:
                this.onBackPressed();
                break;
            case R.id.bt_ok:
                ArrayList<String> listOfMissing=getSpinnerItems();
                //Remove duplication
                Set<String> hs = new HashSet<>();
                hs.addAll(listOfMissing);

                String txt="";
                for (String s:hs){

                    txt+=s+"\n";

                }

                showResume(txt,this);

                break;
            default:
                break;
        }
    }
}
