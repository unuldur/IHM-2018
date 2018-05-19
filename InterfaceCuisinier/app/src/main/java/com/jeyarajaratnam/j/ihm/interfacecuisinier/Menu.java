package com.jeyarajaratnam.j.ihm.interfacecuisinier;

import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Jehyanka on 03/04/2018.
 */

//Concerne juste les plats commandés
public class Menu {

    private String name;
    private int number;
    private CommandState state;
    private ArrayList<String> dishes=new ArrayList<>();
    //Dans le projet finalisé, waitingTime serait calculé
    private int waitingTime;

    public Menu(String name, int number, int waitingTime) {
        this.name = name;
        this.number=number;
        this.waitingTime = waitingTime;
        this.state=CommandState.ORDERED;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<String> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<String> dishes) {

        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommandState getState() {
        return state;
    }

    public void setState(CommandState state) {
        this.state = state;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Menu(String name, int number, CommandState state, int waitingTime) {

        this.name = name;
        this.number=number;
        this.state = state;
        this.waitingTime = waitingTime;
    }

    public String getContent(){
        String content="";
        for(String dish:dishes){
            content+=dish+"\n";
        }
        return content;
    }
}


;
