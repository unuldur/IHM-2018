package ihm.tydrichova.upmc.fr.ihmclient.data;

import ihm.tydrichova.upmc.fr.ihmclient.model.Allergene;
import ihm.tydrichova.upmc.fr.ihmclient.model.Ingredient;
import ihm.tydrichova.upmc.fr.ihmclient.model.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class Ingredients {
    private static Ingredients ourInstance = new Ingredients();

    public static Ingredients getInstance() {
        return ourInstance;
    }

    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private Ingredients() {
        ingredients.add(new Ingredient("noix", new HashSet<>(Arrays.asList(Allergene.ARACHIDE)), Type.AUCUN));
        ingredients.add(new Ingredient("cote de porc", new HashSet<Allergene>(), Type.VIANDE));
        ingredients.add(new Ingredient("sel", new HashSet<Allergene>(), Type.VEGETARIEN));
        ingredients.add(new Ingredient("poivre", new HashSet<Allergene>(), Type.VEGAN));
        ingredients.add(new Ingredient("lait", new HashSet<>(Arrays.asList(Allergene.LACTOSE)), Type.AUCUN));
        ingredients.add(new Ingredient("lait de coco", new HashSet<>(Arrays.asList(Allergene.LACTOSE)), Type.VEGETARIEN));
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}