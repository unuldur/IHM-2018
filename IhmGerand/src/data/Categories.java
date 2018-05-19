package data;

import model.Categorie;

import java.util.ArrayList;
import java.util.List;

public class Categories {
    private static Categories ourInstance = new Categories();

    public static Categories getInstance() {
        return ourInstance;
    }

    private List<Categorie> categories = new ArrayList<>();
    private Categories() {
        Categorie cat1 = new Categorie("Entres");
        Categorie cat2 = new Categorie("Plats");
        Categorie cat3 = new Categorie("Desserts");
        Categorie cat4 = new Categorie("Euh..");
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        categories.add(cat4);
    }

    public List<Categorie> getCategories() {
        return categories;
    }
}
