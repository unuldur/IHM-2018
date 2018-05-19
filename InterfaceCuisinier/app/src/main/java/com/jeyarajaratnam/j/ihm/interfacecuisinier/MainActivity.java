package com.jeyarajaratnam.j.ihm.interfacecuisinier;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.jeyarajaratnam.j.ihm.interfacecuisinier.adapter.MenuAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Menu> menus =new ArrayList<>();
    private ListView commandList;
    private HashMap<Menu,TextView> contentByMenu=new HashMap<>();
    private ArrayList<LinearLayout> ll= new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView titleC=(TextView)findViewById(R.id.tv_titleCommand);
        commandList=(ListView)findViewById(R.id.commandList);

        generateMenus();

        MenuAdapter adapter = new MenuAdapter(MainActivity.this, menus);
        commandList.setAdapter(adapter);

        Button signal=(Button)findViewById(R.id.bt_signaler);
        signal.setOnClickListener(this);

        new CountDownTimer(900000000,60000) {

            public void onTick(long millisUntilFinished) {
                LinearLayout b ;
                Menu d;
                for(int i = 0; i< menus.size(); i++){
                    b=(LinearLayout)findViewById(i+1);
                    if(b!=null){
                        d= menus.get(i);
                        setTime(d);



                    }
                }
            }

            public void onFinish() {
                titleC.setText("done!");
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTime(Menu d){
        d.setWaitingTime(d.getWaitingTime()+1);

        TextView tv=(TextView)findViewById(d.getNumber());
        tv.setText(d.getWaitingTime() + "min");
        if(d.getWaitingTime()>15){
            tv.setTextColor(getResources().getColor(R.color.red,null));
        }
    }



    @Override
    public void onClick(View view) {
        switch(view.getTag().toString()){
            case "signal":
                Intent intent = new Intent(MainActivity.this,MissingIngredientActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void generateMenus(){
        Menu m=new Menu("Menu Steak Frites", 20, CommandState.ORDERED,35);
        ArrayList<String> dishes = new ArrayList<String>();

        dishes.add("Steak frites");
        dishes.add("Ile flottante");
        dishes.add("Orangino");

        m.setDishes(dishes);
        menus.add(m);

        dishes.clear();
        dishes.add("Salade thon");
        dishes.add("Paella");
        dishes.add("Fondant au chocolat");


        m=new Menu("Pizza 4 Fromages",21,CommandState.ORDERED,10);
        menus.add(m);
        m=new Menu("Menu Paella",24,CommandState.PREPARING,6);
        m.setDishes(dishes);
        menus.add(m);
        m=new Menu("Spaghetti Bolognaise",27,CommandState.PREPARING,2);
        menus.add(m);
        m=new Menu("Filet de boeuf",28,CommandState.PREPARING,0);
        menus.add(m);

    }

}
