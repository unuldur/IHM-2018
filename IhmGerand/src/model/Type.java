package model;

public enum Type {
    VEGETARIEN,
    POISSON,
    VIANDE,
    VEGAN,
    AUCUN;

    @Override
    public String toString() {
        return this.name().toLowerCase().replaceFirst(
                String.valueOf(this.name().charAt(0)).toLowerCase(), String.valueOf(this.name().charAt(0)));
    }
}
