package ihm.tydrichova.upmc.fr.ihmclient.data;

import ihm.tydrichova.upmc.fr.ihmclient.model.Ingredient;
import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Plats {
    private static Plats ourInstance = new Plats();

    public static Plats getInstance() {
        return ourInstance;
    }

    private List<Plat> plats = new ArrayList<>();
    private Plats() {
        Plat p1 = new Plat("salade",15,"", new HashMap<Ingredient, Boolean>(), false);
        Plat p2 = new Plat("fraise",15,"",new HashMap<Ingredient, Boolean>(), false, 5);
        Plat p3 = new Plat("pate",15,"", new HashMap<Ingredient, Boolean>(), false);
        Plat p4 = new Plat("jabom",15,"", new HashMap<Ingredient, Boolean>(), false, 2);
        Plat p5 = new Plat("tartiflette",15,"", new HashMap<Ingredient, Boolean>(), true);
        Plat p6 = new Plat("miam",15,"", new HashMap<Ingredient, Boolean>(), false);
        Plat p7 = new Plat("pizza trois viande avec une en supplement",15,"", new HashMap<Ingredient, Boolean>(), false);
        Plat p8 = new Plat("chose verte",15,"", new HashMap<Ingredient, Boolean>(), true);
        plats.add(p1);
        plats.add(p2);
        plats.add(p3);
        plats.add(p4);
        plats.add(p5);
        plats.add(p6);
        plats.add(p7);
        plats.add(p8);
    }

    public List<Plat> getPlats() {
        return plats;
    }
}