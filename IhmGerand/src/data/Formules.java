package data;

import model.Categorie;
import model.Formule;
import model.Plat;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Formules {
    private static Formules ourInstance = new Formules();

    public static Formules getInstance() {
        return ourInstance;
    }

    private Set<Formule> formules = new HashSet<>();
    private Formules() {
        Formule f = new Formule();
        f.setName("Simple formule");
        f.setDescription("");
        f.setPrix(15f);
        Categorie cat1 = Categories.getInstance().getCategories().get(0);
        Categorie cat2 = Categories.getInstance().getCategories().get(1);
        Set<Plat> a = new TreeSet<>();
        a.add(Plats.getInstance().getPlats().get(0));
        a.add(Plats.getInstance().getPlats().get(1));
        Set<Plat> b = new TreeSet<>();
        b.add(Plats.getInstance().getPlats().get(1));
        b.add(Plats.getInstance().getPlats().get(2));
        b.add(Plats.getInstance().getPlats().get(3));
        f.getCompo().put(cat1, a);
        f.getCompo().put(cat2, b);
        formules.add(f);
    }

    public Set<Formule> getFormules() {
        return formules;
    }
}
