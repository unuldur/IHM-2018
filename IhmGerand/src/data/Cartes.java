package data;

import model.Carte;
import model.Categorie;
import model.Plat;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Cartes {
    private static Cartes ourInstance = new Cartes();

    public static Cartes getInstance() {
        return ourInstance;
    }

    private ArrayList<Carte> cartes = new ArrayList<>();
    private Cartes() {
        Carte carteTest = new Carte("test carte");
        Categorie cat1 = Categories.getInstance().getCategories().get(0);
        Categorie cat2 = Categories.getInstance().getCategories().get(1);
        Set<Plat> a = new TreeSet<>();
        a.add(Plats.getInstance().getPlats().get(0));
        a.add(Plats.getInstance().getPlats().get(1));
        Set<Plat> b = new TreeSet<>();
        b.add(Plats.getInstance().getPlats().get(1));
        b.add(Plats.getInstance().getPlats().get(2));
        b.add(Plats.getInstance().getPlats().get(3));
        carteTest.getCarte().put(cat1, a);
        carteTest.getCarte().put(cat2, b);
        cartes.add(carteTest);
    }

    public ArrayList<Carte> getCartes() {
        return cartes;
    }
}
