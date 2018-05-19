package com.caum.marion.interfaceserveur;

import java.util.ArrayList;

public class Table {

    public String nom;
    public ArrayList<Plat> commande;

    public Table(String nom){
        this.nom = nom;
        commande = new ArrayList<Plat> ();
    }

    public Table(){
        this.nom = "";
        commande = new ArrayList<Plat> ();
    }

    @Override
    public String toString() {
        return nom+commande.size();
    }

    public boolean equals(Table obj) {
        boolean isEqual = false;
        if (obj.nom.equals(this.nom)){
            isEqual = true;
        }
        return isEqual;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Plat> getCommande() {
        return commande;
    }

    public void addPlat(String nomPlat){
        boolean trouver = false;
        for (Plat p:commande) {
            if (p.nom.equals(nomPlat)){
                p.quantite += 1;
                trouver = true;
            }
        }
        if (!trouver){
            Plat plat = new Plat(nomPlat, 1);
            commande.add(plat);
        }
    }

    public void setCommande(ArrayList<Plat> commande) {
        this.commande = commande;
    }
}
