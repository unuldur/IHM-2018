package model;

import java.util.*;

public class Formule implements ICompoPlat, Comparable{
    private String name;
    private String description;
    private float prix;
    private Map<Categorie, Set<Plat>> formule = new TreeMap<>();

    public Formule(String name, String description, float prix, Map<Categorie, Set<Plat>> formule) {
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.formule = formule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Formule() {
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public Map<Categorie, Set<Plat>> getCompo() {
        return formule;
    }

    @Override
    public int compareTo(Object o) {
        return getName().compareTo(((Formule)o).getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formule formule = (Formule) o;
        return Objects.equals(name, formule.name);
    }

    public void setFormule(Map<Categorie, Set<Plat>> formule) {
        this.formule = formule;
    }

    @Override

    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }


}
