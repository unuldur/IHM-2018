package ihm.tydrichova.upmc.fr.ihmclient.data;

import ihm.tydrichova.upmc.fr.ihmclient.model.Categorie;

import java.util.ArrayList;
import java.util.List;

public class Categories {
    private static Categories ourInstance = new Categories();

    public static Categories getInstance() {
        return ourInstance;
    }

    private List<Categorie> categories = new ArrayList<>();
    private Categories() {
        Categorie ent1 = new Categorie("Entrées : Salades");
        Categorie ent2 = new Categorie("Entrées : Entrées chaudes");
        Categorie plt1 = new Categorie("Plats : Vollaile");
        Categorie plt2 = new Categorie("Plats : Porc");
        Categorie plt3 = new Categorie("Plats : Mouton");
        Categorie plt4 = new Categorie("Plats : Bovin");
        Categorie plt5 = new Categorie("Plats : Poisson");
        Categorie plt6 = new Categorie("Plats : Végétarien");
        Categorie des1 = new Categorie("Desserts : Les Tartes");
        Categorie des2 = new Categorie("Desserts : Les Glaces");
        Categorie des3 = new Categorie("Desserts : Les Fruits");
        Categorie des4 = new Categorie("Desserts : Les Coupes");
        Categorie des5 = new Categorie("Desserts : Les Autres");

        Categorie boi1 = new Categorie("Boissons : Boissons chauds");
        Categorie boi2 = new Categorie("Boissons : Boissons sans alcool");
        Categorie boi3 = new Categorie("Boissons : Boissons alcoolisés");

        Categorie acc1 = new Categorie("Accompagnements : Pommes de terre");
        Categorie acc2 = new Categorie("Accompagnements : Riz");
        Categorie acc3 = new Categorie("Accompagnements : Pates");
        Categorie acc4 = new Categorie("Accompagnements : Legumes");

        categories.add(ent1);
        categories.add(ent2);
        categories.add(plt1);
        categories.add(plt2);
        categories.add(plt3);
        categories.add(plt4);
        categories.add(plt5);
        categories.add(plt6);
        categories.add(des1);
        categories.add(des3);
        categories.add(des2);
        categories.add(des4);
        categories.add(des5);
        categories.add(boi1);
        categories.add(boi2);
        categories.add(boi3);
        categories.add(acc1);
        categories.add(acc2);
        categories.add(acc3);
        categories.add(acc4);
    }

    public List<Categorie> getCategories() {
        return categories;
    }
}